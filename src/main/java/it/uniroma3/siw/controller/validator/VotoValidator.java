package it.uniroma3.siw.controller.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Voto;

@Component
public class VotoValidator implements Validator{

	 @Override
	    public void validate(Object o, Errors errors) {
	        Voto voto = (Voto) o;
	        
	        if(voto.getVoto()<0 || voto.getVoto()>10)
	        	errors.rejectValue("voto", "bounds");
	    }

	    @Override
	    public boolean supports(Class<?> clazz) {
	        return Voto.class.equals(clazz);
	    }
}
