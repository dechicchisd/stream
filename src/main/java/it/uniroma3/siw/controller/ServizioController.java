package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Servizio;
import it.uniroma3.siw.model.Voto;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.ServizioService;
import it.uniroma3.siw.service.VotoService;
import it.uniroma3.siw.utils.UtilsStream;

@Controller
public class ServizioController {

	@Autowired
	private ServizioService servizioService;
	
	@Autowired
	private ProdottoService prodottoService;

	@Autowired
	private VotoService votoService;

	@RequestMapping(value = "/addServizio", method= RequestMethod.GET)
	public String getAddServizio(Model model) {
		model.addAttribute("servizio", new Servizio());
		return "/admin/addServizioForm.html";
	}
	
	@RequestMapping(value = "/addServizio", method= RequestMethod.POST)
	public String addServizio(Model model, 
							  @ModelAttribute("servizio") Servizio servizio,
							  @RequestParam("file") MultipartFile file) {
		
		String path = "/img/" + file.getOriginalFilename();
    	servizio.setPath(path);
    	
		this.servizioService.inserisci(servizio);
		model.addAttribute("prodotti", this.prodottoService.tutti());
		return "index.html";
	}
	
	@RequestMapping(value="/addServizio/{id}", method = RequestMethod.GET)
	public String addSerivizio(Model model,
							   @PathVariable("id") Long idProdotto) {
		model.addAttribute("prodotto", prodottoService.ProdottoPerId(idProdotto));
		model.addAttribute("servizi", servizioService.tutti());
		return "/admin/addServizioAProdottoForm.html";
	}
	
	@RequestMapping(value="/addServizioAProdotto/{prodottoID}", method=RequestMethod.POST)
	public String addAttoreCast(Model model, 
								@PathVariable("prodottoID") Long prodottoId,
								@RequestParam("servizioID") Long servizioId) {
		
		Servizio servizio = servizioService.servizioPerdId(servizioId);
		Prodotto prodotto = prodottoService.ProdottoPerId(prodottoId);
		List<Voto> votiProdotto = votoService.votiPerIdProdotto(prodottoId);

		prodotto.addServizio(servizio);
		prodottoService.inserisci(prodotto);
		model.addAttribute("prodotto", prodotto);
		model.addAttribute("voto", new Voto());
		model.addAttribute("votoMedio", UtilsStream.mediaVoti(votiProdotto));
		
		return "prodotto.html";
	}
}
