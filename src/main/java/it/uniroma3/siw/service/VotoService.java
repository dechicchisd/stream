package it.uniroma3.siw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Voto;
import it.uniroma3.siw.repository.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Transactional
	public List<Voto> tutti(){
		return (List<Voto>) votoRepository.findAll();
	}
	
	@Transactional
	public Voto inserisci(Voto voto) {
		return votoRepository.save(voto);
	}

	@Transactional
	public List<Voto> votiPerIdProdotto(Long id) {

		return this.votoRepository.votiByProdottoId(id);
	}

	@Transactional
	public Voto votoUtenteProdotto(Long idProdotto, Long idUser) {
		List<Voto> voti = this.votoRepository.votoUtenteProdotto(idProdotto, idUser);
		if(voti.size() > 0)
			return voti.get(voti.size()-1);
		
		return null;
	}

	@Transactional
	public void cancellaVotoUtente(Long idUser) {
		this.votoRepository.deleteByUserId(idUser);
	}
}
