package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Prodotto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	private String tipo;
	
	private String titolo;
	
	@ManyToOne
	private Regista regista;
	
	@ManyToMany
	private List<Attore> attori;
	
	@ManyToMany
	private List<Servizio> servizi;
	
	private String genere;
	
	private String path;
	
	@ManyToMany(mappedBy = "watchlist")
	private List<User> utentiWatchlist; 
	
	
	@OneToMany(mappedBy="prodotto")
	private List<Voto> voti;
	
	
	
	public Prodotto() {
		attori = new ArrayList<Attore>();
		voti = new ArrayList<Voto>();		
		servizi = new ArrayList<Servizio>();
		utentiWatchlist = new ArrayList<User>();
	}
	
	public void addAttoreCast(Attore attore) {
		attori.add(attore);
	}
	
	public void removeAttoreCast(Attore attore) {
		attori.remove(attore);
	}
	
	public void addVoto(Voto voto) {
		voti.add(voto);
	}
	
	public void addServizio(Servizio servizio) {
		servizi.add(servizio);
	}

	public void removeServizioProdotto(Servizio servizio) {
		servizi.remove(servizio);
	}
	
	@Override
	public boolean equals(Object o) {
		Prodotto p = (Prodotto) o;
		return p.getTitolo().equals(this.getTitolo());
	}
	
}
