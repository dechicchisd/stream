package it.uniroma3.siw.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Voto;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.service.VotoService;
import it.uniroma3.siw.utils.UtilsStream;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProdottoService prodottoService;

	@Autowired
	private VotoService votoService;
	
	@RequestMapping(value="/getWatchlist/{id}", method = RequestMethod.GET)
	public String getWatchlist(Model model, @PathVariable("id") Long id, HttpSession session) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		
		if(id.equals(credentials.getUser().getId())) {
			System.out.println(id + "\n\n\n\n" + credentials.getUser().getId());
			model.addAttribute("tipo", "WATCHLIST");
			model.addAttribute("prodotti", this.userService.getUser(id).getWatchlist());
			return "index";
		}
		
		model.addAttribute("tipo", "TUTTI I PRODOTTI");
		model.addAttribute("prodotti", this.prodottoService.tutti());
		
		return "index";
	}
	
	@RequestMapping(value="/addWatchlist/{idProdotto}", method = RequestMethod.GET)
	public String addWatchlist(Model model, @PathVariable("idProdotto") Long idProdotto, HttpSession session) {
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		User user = credentials.getUser();
		Prodotto prodotto = this.prodottoService.ProdottoPerId(idProdotto);
		
		if(!user.getWatchlist().contains(prodotto)) {
			user.addToWatchlist(prodotto);
			this.userService.saveUser(user);
		}
		List<Voto> votiProdotto = votoService.votiPerIdProdotto(idProdotto);	
		
		model.addAttribute("votoMedio", UtilsStream.mediaVoti(votiProdotto));
		model.addAttribute("voto", new Voto());
		model.addAttribute("prodotto", prodotto);
		return "prodotto";
		
	}
}
