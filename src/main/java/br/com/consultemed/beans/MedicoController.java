/**
 * 
 */
package br.com.consultemed.beans;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.models.Medico;
import br.com.consultemed.services.MedicoService;
import lombok.Getter;
import lombok.Setter;

/**
 * @author carlosbarbosagomesfilho
 *
 */
@Named
@Getter
@Setter
@RequestScoped
public class MedicoController{
		
	private List<Medico> medicos;

	@Inject
	private Medico medico;
	
	private Medico medicoEditar;
	
	@Inject
	private MedicoService service;	
	
	public String editar() {
		this.medico = this.medicoEditar;
		return "/pages/medicos/addMedicos.xhtml";
	}
	
	public String excluir() throws Exception {
		this.medico = this.medicoEditar;
		this.service.deletarMedico(this.medico.getId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
		return "/pages/medicos/medicos.xhtml?faces-redirect=true";
	}
	
	public String novoMedico() {
		this.medico = new Medico();
		return "/pages/medicos/addMedicos.xhtml?faces-redirect=true";
	}
	
	public String addMedico() throws IOException {
		Medico antigo = this.service.buscarPorCrm(medico.getCrm());			
		
		if(antigo == null) {
			this.service.salvarMedico(this.medico);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Info", "Cadastrado com Sucesso!."));			
			
			return "/pages/medicos/medicos.xhtml?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Aviso!", "CRM já cadastrado!"));	    					
			return "/pages/medicos/addMedicos.xhtml?faces-redirect=true";
		}			
	}
	
	public List<Medico> listaMedicos(){
		this.medicos = this.service.listaMedico();
		return medicos;
	}
}
