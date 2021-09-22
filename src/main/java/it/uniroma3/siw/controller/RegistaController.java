package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.RegistaValidator;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Regista;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.RegistaService;

@Controller
public class RegistaController {

	@Autowired
	private RegistaService registaService;
	
	@Autowired
	private ProdottoService prodottoService;
	
	@Autowired
	private RegistaValidator registaValidator;
	
	@RequestMapping(value="/addRegista", method=RequestMethod.GET)
	private String getRegistaForm(Model model){
		model.addAttribute("regista", new Regista());
		return "/admin/addRegistaForm.html";
	}
	
	@RequestMapping(value="/addRegista", method=RequestMethod.POST)
	private String getRegistaForm(Model model,
								  @ModelAttribute("regista") Regista regista,
								  BindingResult bindingResult,
								  @ModelAttribute("file") MultipartFile file){
		
		this.registaValidator.validate(regista, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			String path = "/img/" + file.getOriginalFilename();
	    	regista.setPath(path);
	    	registaService.inserisci(regista);
	    
	    	model.addAttribute("prodotti", prodottoService.tutti());
			model.addAttribute("tipo", "TUTTI I PRODOTTI");
	    	return "index";
		}
		
		
		return "/admin/addRegistaForm";
	}
	
	@RequestMapping(value="/deleteRegista", method=RequestMethod.GET)
	private String deleteRegista(Model model, @RequestParam("nome") String nome, @RequestParam("cognome") String cognome){
		
		Regista regista = this.registaService.registaPerNomeECognome(nome, cognome);
			
		if(regista != null) {
			List<Prodotto> prodottiDelRegista = this.prodottoService.prodottiPerRegistaId(regista.getId());
			
			for(Prodotto p : prodottiDelRegista) {
				p.setRegista(null);
				this.prodottoService.inserisci(p);
			}
			
			this.registaService.deleteRegista(regista);
		}
			
			model.addAttribute("prodotti", prodottoService.tutti());
			model.addAttribute("tipo", "TUTTI I PRODOTTI");
			
			return "index.html";
			
	}
	
	@RequestMapping(value="/deleteRegistaForm", method=RequestMethod.GET)
	private String deleteRegista(Model model){
		
		return "/admin/deleteRegistaForm";
	}
	
	
}
