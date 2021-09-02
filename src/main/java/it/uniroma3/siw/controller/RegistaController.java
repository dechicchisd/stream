package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Regista;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.RegistaService;

@Controller
public class RegistaController {

	@Autowired
	private RegistaService registaService;
	@Autowired
	private ProdottoService prodottoService;
	
	@RequestMapping(value="/addRegista", method=RequestMethod.GET)
	private String getRegistaForm(Model model){
		model.addAttribute("regista", new Regista());
		return "/admin/addRegistaForm.html";
	}
	
	@RequestMapping(value="/addRegista", method=RequestMethod.POST)
	private String getRegistaForm(Model model,
								  @ModelAttribute("regista") Regista regista,
								  @ModelAttribute("file") MultipartFile file){
		
		String path = "/img/" + file.getOriginalFilename();
    	regista.setPath(path);
    	registaService.inserisci(regista);
		model.addAttribute("prodotti", prodottoService.tutti());
		
		return "index.html";
	}
}
