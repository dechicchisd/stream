package it.uniroma3.siw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.ProdottoValidator;
import it.uniroma3.siw.controller.validator.VotoValidator;
import it.uniroma3.siw.model.Attore;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Servizio;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Voto;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.RegistaService;
import it.uniroma3.siw.service.ServizioService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.service.VotoService;
import it.uniroma3.siw.utils.UtilsStream;

@Controller
public class ProdottoController {

	@Autowired
	private ProdottoService prodottoService;

	@Autowired
	private RegistaService registaService;

	@Autowired
	private VotoService votoService;

	@Autowired
	private ServizioService serviziService;

	@Autowired
	private ServizioService servizioService;

	@Autowired
	private UserService userService;

	@Autowired
	private VotoValidator votoValidator;

	@Autowired
	private ProdottoValidator prodottoValidator;
	
	
	@RequestMapping(value="/getDocumentari", method=RequestMethod.GET)
	private String getDocumentari(Model model) {
		model.addAttribute("prodotti", this.prodottoService.tuttiDocumentari());
		model.addAttribute("tipo", "DOCUMENTARI");
		return "index.html";
		}
	
	@RequestMapping(value="/getFilm", method=RequestMethod.GET)
	public String getFilms(Model model) {
		model.addAttribute("prodotti", this.prodottoService.tuttiFilm());
		model.addAttribute("tipo", "FILM");
		return "index.html";
	}
	
	@RequestMapping(value="/getProdotto/{idProdotto}", method=RequestMethod.GET)
	public String getProdotto(Model model, 
							  @PathVariable("idProdotto") Long idProdotto,
							  HttpSession session) {
		Credentials credentials = (Credentials)session.getAttribute("credentials");
		
		Long idUser;
		Voto votoUtente;
		
		if(credentials != null) {
			idUser = credentials.getUser().getId();
			votoUtente = votoService.votoUtenteProdotto(idProdotto, idUser);
			model.addAttribute("votoUtente", votoUtente);
			
			if(votoUtente != null)
				System.out.println(votoUtente.getVoto() + "\n\n\n\n");
		}
		
		List<Voto> votiProdotto = votoService.votiPerIdProdotto(idProdotto);
		
		model.addAttribute("voto", new Voto());
		model.addAttribute("prodotto", this.prodottoService.ProdottoPerId(idProdotto));
		model.addAttribute("votoMedio", UtilsStream.mediaVoti(votiProdotto));

		return "prodotto.html";
	}

	@RequestMapping(value="/getSeries", method=RequestMethod.GET)
	public String getSeries(Model model) {
		model.addAttribute("prodotti", prodottoService.tutteSerie());
		model.addAttribute("tipo", "SERIE TV");
		return "index.html";
	}
	
	@RequestMapping(value="/addProdotto", method=RequestMethod.POST)
	public String addProdotto(Model model,
						   	  @ModelAttribute("prodotto") Prodotto prodotto,
						   	  BindingResult bindingResult,
						      @RequestParam("file") MultipartFile file) {
		
		this.prodottoValidator.validate(prodotto, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			String path = "/img/" + file.getOriginalFilename();
	    	prodotto.setPath(path);
	    	prodotto.setRegista(registaService.RegistaPerId(prodotto.getRegista().getId()));
	    	
	    	prodottoService.inserisci(prodotto);
	    	
	    	if(prodotto.getTipo().equals("Documentario")) {
				model.addAttribute("prodotti", prodottoService.tuttiDocumentari());
				model.addAttribute("tipo", "DOCUMENTARI");
	    	}
	    	
	    	else if(prodotto.getTipo().equals("Film")) {
	    		model.addAttribute("prodotti", prodottoService.tuttiFilm());
				model.addAttribute("tipo", "FILM");
	    	}
	    	
	    	else if(prodotto.getTipo().equals("SerieTV")) {
	    		model.addAttribute("prodotti", prodottoService.tutteSerie());
				model.addAttribute("tipo", "SERIE TV");
	    	}
	    	
	    	return "index.html";
	    	
		}
		model.addAttribute("prodotti", prodottoService.tutti());
		model.addAttribute("tipo", "TUTTI I PRODOTTI");
		
		return "index.html";
	}
	
	@RequestMapping(value="/addProdotto", method=RequestMethod.GET)
	public String getAddDocumentario(Model model) {
		model.addAttribute("prodotto", new Prodotto());
		model.addAttribute("registi", registaService.tutti());
		model.addAttribute("servizi", serviziService.tutti());
		return "/admin/addProdottoForm.html";
	}
	
	@RequestMapping(value="/modificaProdotto/{id}", method=RequestMethod.GET)
	public String getModificaProdotto(Model model, @PathVariable("id") Long id) {
		model.addAttribute("prodotto", this.prodottoService.ProdottoPerId(id));
		model.addAttribute("registi", registaService.tutti());
		return "/admin/editProdottoForm.html";
	}
	
	@RequestMapping(value="/modificaProdotto/{id}", method=RequestMethod.POST)
	public String modificaProdotto(Model model, 
								   @PathVariable("id") Long id, 
								   @ModelAttribute("prodotto") Prodotto prodotto,
								   @RequestParam("file") MultipartFile file) {
		String path = "/img/" + file.getOriginalFilename();
    	prodotto.setPath(path);
    	prodotto.setRegista(registaService.RegistaPerId(prodotto.getRegista().getId()));
    	
    	prodottoService.deleteProdotto(id);
    	prodottoService.inserisci(prodotto);
    	
		List<Voto> voti = votoService.tutti();
    	
		Float voto = UtilsStream.mediaVoti(voti);
		
    	Prodotto prodottoModificato = prodottoService.tutti().get(prodottoService.tutti().size()-1);
		model.addAttribute("prodotto", prodottoModificato);
		model.addAttribute("votoMedio", voto);
		model.addAttribute("voto", new Voto());
		
		return "prodotto.html";
	}
	
	@RequestMapping(value="/eliminaProdotto/{id}", method=RequestMethod.GET)
	public String modificaProdotto(Model model, @PathVariable("id") Long id) {
		System.out.println(id + "\n\n\n\n");
		
		System.out.println(prodottoService.ProdottoPerId(id).getTitolo());
		prodottoService.deleteProdotto(id);
		
		model.addAttribute("prodotti", prodottoService.tutti());
		model.addAttribute("tipo", "TUTTI I PRODOTTI");
		
		return "index.html";
	}
	
	@RequestMapping(value="/addVoto/{id}", method=RequestMethod.POST)
	public String addVoto(Model model, 
						  @ModelAttribute("voto") Voto voto, 
						  BindingResult bindingResult,
						  @PathVariable("id") Long prodottoId,
						  HttpServletRequest request) {
		
		Credentials credentials = (Credentials)request.getSession().getAttribute("credentials");
		Prodotto prodotto = prodottoService.ProdottoPerId(prodottoId);
		List<Voto> votiProdotto = votoService.votiPerIdProdotto(prodottoId);
		
		this.votoValidator.validate(voto, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			Long userId = credentials.getUser().getId();
			voto.setUtente(userService.getUser(userId));
			
			votoService.cancellaVotoUtente(userId);
			
			voto.setProdotto(prodotto);
			prodotto.addVoto(voto);
			
			votoService.inserisci(voto);
			prodottoService.inserisci(prodotto);
			
			votiProdotto = votoService.votiPerIdProdotto(prodottoId);
			
			model.addAttribute("votoMedio", UtilsStream.mediaVoti(votiProdotto));
			model.addAttribute("voto", new Voto());
			model.addAttribute("prodotto", prodotto);
			return "prodotto.html";
		}
		
		model.addAttribute("voto", new Voto());
		model.addAttribute("votoMedio", UtilsStream.mediaVoti(votiProdotto));
		model.addAttribute("prodotto", prodotto);
		return "prodotto.html";
	}
	
}
