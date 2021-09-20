package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Regista;
import it.uniroma3.siw.repository.RegistaRepository;

@Service
public class RegistaService {

	@Autowired
	private RegistaRepository RegistaRepository; 
	
	@Transactional
	public Regista inserisci(Regista regista) {
		return RegistaRepository.save(regista);
	}
	
	@Transactional
	public List<Regista> tutti() {
		return (List<Regista>) RegistaRepository.findAll();
	}

	@Transactional
	public Regista RegistaPerId(Long id) {
		if(id!=null) {
			Optional<Regista> optional = RegistaRepository.findById(id);
		
			if (optional.isPresent())
				return optional.get();
		}
		
		return null;
	}

	@Transactional
	public boolean alreadyExists(Regista regista) {
		List<Regista> registi = this.RegistaRepository.findByNomeAndCognome(regista.getNome(), regista.getCognome());
		if (registi.size() > 0)
			return true;
		else 
			return false;
	}
}
