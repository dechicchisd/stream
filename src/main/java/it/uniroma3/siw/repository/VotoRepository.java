package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Voto;

public interface VotoRepository extends CrudRepository<Voto, Long>{

	@Query("SELECT v FROM Voto v WHERE v.prodotto.id = ?1")
	List<Voto> votiByProdottoId(Long id);

	@Query("SELECT v FROM Voto v WHERE v.prodotto.id = ?1 AND v.utente.id = ?2")
	List<Voto> votoUtenteProdotto(Long idProdotto, Long idUser);

	@Modifying
	@Query("DELETE FROM Voto v WHERE v.utente.id = ?1")
	void deleteByUserId(Long idUser);
	
}
