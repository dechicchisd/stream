package it.uniroma3.siw.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.ProdottoService;

@Component
public class ProdottoValidator implements Validator{

	@Autowired
	private ProdottoService prodottoService;

    private static final Logger logger = LoggerFactory.getLogger(ProdottoValidator.class);

	
	@Override
    public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titolo", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipo", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.prodottoService.alreadyExists((Prodotto) o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		} 
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Prodotto.class.equals(clazz);
    }
}
