package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.service.ProdottoService;

@Controller
public class MainController {
	@Autowired
	ProdottoService prodottoService;
	@RequestMapping(value="/getAdmin", method=RequestMethod.GET)
	public String getAdmin() {
		return "/admin/operazioni.html";
	}
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getHome(Model model) {
		model.addAttribute("prodotti", prodottoService.tutti());
		model.addAttribute("tipo", "TUTTI I PRODOTTI");
		return "index.html";
	}
}
