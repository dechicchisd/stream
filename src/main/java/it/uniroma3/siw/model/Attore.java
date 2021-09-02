package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "attore")
public class Attore {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private String path;
	
	@ManyToMany(mappedBy="attori")
	private List<Prodotto> prodotti;

	public Attore() {
		prodotti = new ArrayList<Prodotto>();
	}
	
	public void addProdotto(Prodotto prodotto) {
		prodotti.add(prodotto);
	}
}
