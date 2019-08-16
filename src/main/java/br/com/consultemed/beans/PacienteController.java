package br.com.consultemed.beans;

import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.models.Paciente;
import br.com.consultemed.services.PacienteService;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class PacienteController {

	private List<Paciente> pacientes;

	@Inject
	private Paciente paciente;
	
	private Paciente pacienteEditar;
	
	@Inject
	private PacienteService service;
	
	private BeanFlash flash = new BeanFlash();
	
	public String editar() {
		this.paciente = this.pacienteEditar;
		return "/pages/pacientes/addPacientes.xhtml";
	}
	
	public String excluir() throws Exception {
		this.paciente = this.pacienteEditar;
		this.service.remover(this.paciente.getId());
		return this.flash.redirectionAviso("Removido com Sucesso!", "/pages/pacientes/pacientes.xhtml?faces-redirect=true");				
	}
	
	public String novoPaciente() {
		this.paciente = new Paciente();
		return "/pages/pacientes/addPacientes.xhtml?faces-redirect=true";
	}
	
	public String addPaciente() throws Exception {
		Paciente antigo = this.service.buscarPacientePorCpf(this.paciente.getCpf());
		if(antigo == null) {			
			this.service.salvar(this.paciente);
			return this.flash.redirectionAviso("Cadastrado com Sucesso!", "/pages/pacientes/pacientes.xhtml?faces-redirect=true");
		} else {
			return this.flash.redirectionAlerta("CPF já cadastrado!", "/pages/pacientes/addPacientes.xhtml?faces-redirect=true");
		}
	}
	
	public Collection<Paciente> listaPacientes() throws Exception{
		return this.service.listar();
	}

}
