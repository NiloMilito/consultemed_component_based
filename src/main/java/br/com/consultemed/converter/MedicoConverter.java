package br.com.consultemed.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.consultemed.models.Medico;
import br.com.consultemed.repository.repositories.MedicoRepository;

@FacesConverter("medicoConverter")
public class MedicoConverter implements Converter{
	
	@Inject
	private MedicoRepository dao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Medico medico = new Medico();
		if(value == null || value.isEmpty()){
            return medico;
        }else{
			Long id = Long.parseLong(value);
			this.dao = new MedicoRepository();
			medico = this.dao.buscarPorId(id);
			return medico;
        }
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {		
		if(value != null) 
		return value.toString();
		else return null;
	}

}
