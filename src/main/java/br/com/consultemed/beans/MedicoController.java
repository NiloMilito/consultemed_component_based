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

import br.com.consultemed.models.DiasAtendimento;
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
	
	private String[] diasSemana;
	
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
			this.medico.setDiasAtendimento(this.preencheDiasSemana());
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
	
	private DiasAtendimento preencheDiasSemana() {
		Boolean segunda = false, terca = false, quarta = false, quinta = false, sexta = false, sabado = false, domingo = false;
		for(int i = 0; i < this.diasSemana.length; i++) {
			switch (this.diasSemana[i]) {
			case "1":
				segunda = true;
				break;			
			case "2":
				terca = true;
				break;			
			case "3":
				quarta = true;
				break;
			case "4":
				quinta = true;
				break;
			case "5":
				sexta = true;
			case "6":
				sabado = true;
				break;
			case "7":
				domingo = true;				
				break;
			}
		}

		DiasAtendimento dias = new DiasAtendimento(segunda, terca, quarta, quinta, sexta, sabado, domingo);
		return dias;
	}

	public List<Medico> listaMedicos(){
		this.medicos = this.service.listaMedico();
		return medicos;
	}
}
