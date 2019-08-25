package br.com.consultemed.beans;

import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.consultemed.models.Endereco;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.services.PacienteService;
import br.com.consultemed.webServices.EnderecoJSON;
import br.com.consultemed.webServices.EnderecoWebService;
import lombok.Getter;
import lombok.Setter;

@ManagedBean
@ViewScoped
@Getter
@Setter
public class PacienteController {

	@Inject
	private Paciente paciente;
	@Inject
	private PacienteService service;
	@Inject
	private EnderecoWebService webService;
	
	private Paciente pacienteEditar;
	private List<Paciente> pacientes;	
	private BeanFlash flash = new BeanFlash();
	private String cep;
	
	@Inject
	public PacienteController() {
		this.paciente = new Paciente();
		this.service = new PacienteService();
		this.webService = new  EnderecoWebService();
	}

	public void buscaCep() {
		Endereco endereco = ParseEntyToJSON(this.webService.getBuscaCEP(this.cep));	
		this.paciente.setEndereco(endereco);
	}

	private Endereco ParseEntyToJSON(EnderecoJSON enderecoJSON) {
		Endereco endereco = new Endereco(
			enderecoJSON.getLogradouro(), enderecoJSON.getBairro(), enderecoJSON.getCidade(), enderecoJSON.getUf());		
		return endereco;
	}
	
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
