package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.uniroma3.siw.model.Servizio;
import it.uniroma3.siw.repository.ServizioRepository;

@Service
public class ServizioService {

	@Autowired
	private ServizioRepository servizioRepository;
	
	public List<Servizio> tutti(){
		return (List<Servizio>) this.servizioRepository.findAll();
	}
	
	public Servizio inserisci(Servizio servizio) {
		return this.servizioRepository.save(servizio);
	}

	public Servizio servizioPerdId(Long id) {
		Optional<Servizio> optional = this.servizioRepository.findById(id);
		
		if (optional.isPresent())
			return optional.get();
		
		else
			return null;
	}
}

