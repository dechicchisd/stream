package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;
	
	private String cognome;
	
	@OneToMany(mappedBy="utente")
	private List<Voto> voti;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Prodotto> watchlist;
	
	
	public User() {
		this.voti = new ArrayList<Voto>();
		this.watchlist = new ArrayList<Prodotto>();
	}

	public void addToWatchlist(Prodotto prodotto) {
		this.watchlist.add(prodotto);
	}
	
	public void removeFromWatchlist(Prodotto prodotto) {
		this.watchlist.remove(prodotto);
	}
	
	
}
