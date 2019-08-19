/**
 * 
 */
package br.com.consultemed.beans;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

/**
 * @author carlosbarbosagomesfilho
 *
 */

@ViewScoped
@ManagedBean
@Getter
@Setter
public class BeanFlash {

	public String redirection() {		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Realizado com sucesso"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return "/pages/medicos/medicos.xhtml?faces-redirect=true";
	}
	
	public String redirectionAviso(String message, String url) {		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, "Info!"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return url;
	}
	
	public String redirectionAlerta(String message, String url) {		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, "Warning!"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return url;
	}
	
	public String redirectionErro(String message, String url) {		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "Error!"));
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		return url;
	}
	
	public void exibirPaciente() {							          
		Map<String,Object> options = new HashMap<String, Object>();
	    options.put("resizable", false);
	    options.put("draggable", false);
	    options.put("modal", true);
	    PrimeFaces.current().dialog().openDynamic("pacienteDialog", options, null);
	    
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}
	
		
}
