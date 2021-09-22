package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;


import lombok.Data;

@Entity
@Data
public class Regista {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private LocalDate dataDiNascita;
	
	private String path;
	
	@OneToMany(mappedBy="regista")
	private List<Prodotto> prodotti;

}
