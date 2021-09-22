package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long>{

	List<Prodotto> findByTitolo(String titolo);

	@Query("SELECT pr FROM Prodotto pr where pr.tipo='Documentario'")
	List<Prodotto> findAllDocumentari();
	
	@Query("SELECT pr FROM Prodotto pr where pr.tipo='Film'")
	List<Prodotto> findAllFilms();
	
	@Query("SELECT pr FROM Prodotto pr where pr.tipo='SerieTV'")
	List<Prodotto> findAllSeries();

	@Modifying
	@Query("DELETE FROM Prodotto p WHERE p.id = ?1")
	public void deleteProdotto(Long id);

	public List<Prodotto> findProdottoByTitolo(String nome);

	@Query("SELECT pr FROM Prodotto pr WHERE pr.regista.id = ?1")
	List<Prodotto> findByRegistaId(Long registaId);
	
}
