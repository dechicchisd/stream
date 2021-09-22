package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Attore;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.AttoreRepository;

@Service
public class AttoreService {
	@Autowired
	private AttoreRepository attoreRepository; 
	
	@Transactional
	public Attore inserisci(Attore attore) {
		return attoreRepository.save(attore);
	}
	
	@Transactional
	public List<Attore> tutti() {
		return (List<Attore>) attoreRepository.findAll();
	}

	@Transactional
	public Attore AttorePerId(Long id) {
		Optional<Attore> optional = attoreRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Attore attore) {
		List<Attore> attori = this.attoreRepository.findByNomeAndCognome(attore.getNome(), attore.getCognome());
		if (attori.size() > 0)
			return true;
		else 
			return false;
	}

	@Transactional
	public void deleteAttore(Attore attore) {

		this.attoreRepository.delete(attore);
	}

	@Transactional
	public Attore attorePerNomeECognome(String nome, String cognome) {
		List<Attore> attori = this.attoreRepository.findByNomeAndCognome(nome, cognome);
		
		if(attori.size()>0)
			return attori.get(0);
		
		return null;
	}

}
