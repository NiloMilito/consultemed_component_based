package br.com.consultemed.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.consultemed.models.Paciente;
import br.com.consultemed.repository.repositories.IPacienteDao;
import br.com.consultemed.repository.repositories.PacienteDao;

@FacesConverter("pacienteConverter")
public class PacienteConverter implements Converter{
	
	@Inject
	private IPacienteDao service = new PacienteDao();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Paciente paciente = new Paciente();
		if(value == null || value.isEmpty()){
            return null;
        }else{
        	System.out.println(value);
        	Long id = Long.parseLong(value);
        		
        	try {
        		paciente = this.service.findById(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return paciente;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {			
		if(value != null)
		return value.toString();
		else return null;
	}
	

}
