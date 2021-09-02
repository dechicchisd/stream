package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Attore;
import it.uniroma3.siw.repository.AttoreRepository;

@Service
public class AttoreService {
	@Autowired
	private AttoreRepository AttoreRepository; 
	
	@Transactional
	public Attore inserisci(Attore attore) {
		return AttoreRepository.save(attore);
	}
	
	@Transactional
	public List<Attore> tutti() {
		return (List<Attore>) AttoreRepository.findAll();
	}

	@Transactional
	public Attore AttorePerId(Long id) {
		Optional<Attore> optional = AttoreRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Attore attore) {
		List<Attore> attori = this.AttoreRepository.findByNomeAndCognome(attore.getNome(), attore.getCognome());
		if (attori.size() > 0)
			return true;
		else 
			return false;
	}

	public void deleteAttore(Attore attore) {

		this.AttoreRepository.delete(attore);
	}
}
