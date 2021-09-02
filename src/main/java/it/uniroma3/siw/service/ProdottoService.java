package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.ProdottoRepository;

@Service
public class ProdottoService {
	
	@Autowired
	private ProdottoRepository prodottoRepository; 
	
	@Transactional
	public Prodotto inserisci(Prodotto prodotto) {
		return prodottoRepository.save(prodotto);
	}
	
	@Transactional
	public List<Prodotto> tutti() {
		return (List<Prodotto>) prodottoRepository.findAll();
	}
	
	@Transactional
	public List<Prodotto> tuttiDocumentari() {
		return (List<Prodotto>) prodottoRepository.findAllDocumentari();
	}
	
	@Transactional
	public List<Prodotto> tuttiFilm() {
		return (List<Prodotto>) prodottoRepository.findAllFilms();
	}
	
	@Transactional
	public List<Prodotto> tutteSerie() {
		return (List<Prodotto>) prodottoRepository.findAllSeries();
	}

	@Transactional
	public Prodotto ProdottoPerId(Long id) {
		Optional<Prodotto> optional = prodottoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Prodotto prodotto) {
		List<Prodotto> prodotti = this.prodottoRepository.findByTitolo(prodotto.getTitolo());
		if (prodotti.size() > 0)
			return true;
		else 
			return false;
	}

	@Transactional
	public void deleteProdotto(Long id) {
		prodottoRepository.deleteProdotto(id);
	}
}
