package br.com.consultemed.beans;

import java.util.Collection;
import java.util.List;

// import javax.faces.bean.RequestScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.models.Funcionario;
import br.com.consultemed.services.IFuncionarioService;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter	
public class FuncionarioController {

	private List<Funcionario> funcionarios;

	@Inject
	private Funcionario funcionario;

	private Funcionario funcionarioEditar;
	
	@Inject
	private IFuncionarioService service;
	
	private BeanFlash flash = new BeanFlash();
	
	public String editar() {
		this.funcionario = this.funcionarioEditar;
		return "/pages/funcionarios/addFuncionarios.xhtml";
	}
	
	public String excluir() throws Exception {
		this.funcionario = this.funcionarioEditar;
		this.service.remover(this.funcionario.getId());
		return this.flash.redirectionAviso("Removido com Sucesso!", "/pages/funcionarios/funcionarios.xhtml?faces-redirect=true");		
	}
	
	public String novoFuncionario() {
		this.funcionario = new Funcionario();
		return "/pages/funcionarios/addFuncionarios.xhtml?faces-redirect=true";
	}
	
	public String addFuncionario() throws Exception {
		Funcionario antigo = this.service.buscarFuncionarioPorEmail(this.funcionario.getEmail());			
		
		if(antigo == null) {
			this.service.salvar(this.funcionario);
			return this.flash.redirectionAviso("Cadastrado com Sucesso!", "/pages/funcionarios/funcionarios.xhtml?faces-redirect=true");
		} else {
			return this.flash.redirectionAlerta("Email já cadastrado!", "/pages/funcionarios/addFuncionarios.xhtml?faces-redirect=true");
		}
	}
	
	public Collection<Funcionario> listaFuncionarios() throws Exception{
		return this.service.listar();		
	}
}
