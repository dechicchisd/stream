package it.uniroma3.siw.utils;

import java.util.List;

import it.uniroma3.siw.model.Voto;

public class UtilsStream {
	
	public static Float mediaVoti(List<Voto> voti) {
		int size_voti = voti.size();
		float somma_voti = 0;
		
		for(Voto v : voti) {
			somma_voti += v.getVoto();
		}
		
		if(size_voti == 0)
			return (float) 0;
		
		float media = somma_voti/(float)size_voti;
		
		media = (float) (Math.floor(media * 10)/10);
		
		return media;
		
	}
}
