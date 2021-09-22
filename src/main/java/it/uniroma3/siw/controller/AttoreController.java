package it.uniroma3.siw.controller;

import java.util.ArrayList;
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

import it.uniroma3.siw.model.Attore;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.AttoreService;
import it.uniroma3.siw.service.ProdottoService;

@Controller
public class AttoreController {
	
	@Autowired
	private AttoreService attoreService;
	
	@Autowired
	private ProdottoService prodottoService;
	
	@RequestMapping(value="/addAttore", method=RequestMethod.GET)
	public String getAddAttore(Model model) {
		model.addAttribute("attore", new Attore());
		return "/admin/addAttoreForm.html";
	}
	
	@RequestMapping(value="/addAttore", method=RequestMethod.POST)
	public String addAttore(Model model, 
							@ModelAttribute("attore") Attore attore,
							@RequestParam("file") MultipartFile file) {
		
		String path = "/img/" + file.getOriginalFilename();
    	
		attore.setPath(path);
		attoreService.inserisci(attore);
		
		model.addAttribute("prodotti", prodottoService.tutti());
		model.addAttribute("tipo", "TUTTI I PRODOTTI");
		
		return "index.html";
	}
	
	@RequestMapping(value="/addAttoreCast/{id}", method=RequestMethod.GET)
	public String getAddAttoreCast(Model model, @PathVariable("id") Long id) {
		Prodotto prodotto = prodottoService.ProdottoPerId(id);
		model.addAttribute("prodotto", prodotto);
		model.addAttribute("attori", attoreService.tutti());
		model.addAttribute("indice", prodotto.getAttori().size());
		return "/admin/addAttoreCastForm.html";
	}

	@RequestMapping(value="/addAttoreCast/{prodottoID}", method=RequestMethod.POST)
	public String addAttoreCast(Model model, 
								@PathVariable("prodottoID") Long prodottoId,
								@RequestParam("attoreID") Long attoreId) {
		
		Attore attore = attoreService.AttorePerId(attoreId);
		Prodotto prodotto = prodottoService.ProdottoPerId(prodottoId);
		
		prodotto.addAttoreCast(attore);
		prodottoService.inserisci(prodotto);
		model.addAttribute("prodotti", prodottoService.tutti());
		model.addAttribute("tipo", "TUTTI I PRODOTTI");
		
		return "index.html";
	}
	
	@RequestMapping(value="/eliminaAttoreCast/{attoreID}/{prodottoID}", method=RequestMethod.GET)
	public String eliminaAttoreCast(Model model, 
			@PathVariable("attoreID") Long attoreId,
			@PathVariable("prodottoID") Long prodottoId) {
		
		Attore attore = attoreService.AttorePerId(attoreId);
		Prodotto prodotto = prodottoService.ProdottoPerId(prodottoId);
		
		prodotto.removeAttoreCast(attore);
		prodottoService.inserisci(prodotto);
		model.addAttribute("prodotti", prodottoService.tutti());
		model.addAttribute("tipo", "TUTTI I PRODOTTI");
		
		return "index.html";
	}

	@RequestMapping(value="/deleteAttore", method=RequestMethod.GET)
	public String eliminaAttore(Model model, @RequestParam("nome") String nome, @RequestParam("cognome") String cognome) {
		
		Attore attore = this.attoreService.attorePerNomeECognome(nome, cognome);
		List<Prodotto> tuttiProdotti = this.prodottoService.tutti();
		List<Prodotto> prodottiAttore = new ArrayList<Prodotto>();

		for(Prodotto p : tuttiProdotti) {
			if(p.getAttori().contains(attore)) {
				prodottiAttore.add(p);
			}
		}
		
		for(Prodotto p : prodottiAttore) {
			p.removeAttoreCast(attore);
			this.prodottoService.inserisci(p);
		}
		
		this.attoreService.deleteAttore(attore);
		
		model.addAttribute("prodotti", prodottoService.tutti());
		model.addAttribute("tipo", "TUTTI I PRODOTTI");
		
		return "index.html";
	}
	
	@RequestMapping(value="/deleteAttoreForm", method=RequestMethod.GET)
	public String eliminaAttoreForm(Model model) {
		
		return "/admin/deleteAttoreForm";
	}
}
