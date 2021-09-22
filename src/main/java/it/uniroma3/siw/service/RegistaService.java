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
	private RegistaRepository registaRepository; 
	
	@Transactional
	public Regista inserisci(Regista regista) {
		return registaRepository.save(regista);
	}
	
	@Transactional
	public List<Regista> tutti() {
		return (List<Regista>) registaRepository.findAll();
	}

	@Transactional
	public Regista RegistaPerId(Long id) {
		if(id!=null) {
			Optional<Regista> optional = registaRepository.findById(id);
		
			if (optional.isPresent())
				return optional.get();
		}
		
		return null;
	}

	@Transactional
	public boolean alreadyExists(Regista regista) {
		List<Regista> registi = this.registaRepository.findByNomeAndCognome(regista.getNome(), regista.getCognome());
		if (registi.size() > 0)
			return true;
		else 
			return false;
	}

	@Transactional
	public Regista registaPerNomeECognome(String nome, String cognome) {
		
		List<Regista> registi = this.registaRepository.findByNomeAndCognome(nome, cognome);
		
		if(registi.size()>0)
			return registi.get(0);
		
		return null;
	}
	
	@Transactional
	public void deleteRegista(Regista regista) {
		this.registaRepository.delete(regista);
	}
}
