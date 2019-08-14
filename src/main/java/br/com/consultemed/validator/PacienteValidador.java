package br.com.consultemed.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("br.com.consultemed.validador.PacienteValidador")
public class PacienteValidador implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) 
			throws ValidatorException {
		// TODO Auto-generated method stub
		
	}

}
