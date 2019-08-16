/**
 * 
 */
package br.com.consultemed.beans;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.models.Usuario;
import br.com.consultemed.services.UsuarioService;
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
public class UsuarioController{
	
	@Inject
	private UsuarioService uservice;
	
	@Inject
	private Usuario usuario;
	
	private Usuario usuarioEditar;
	
	private BeanFlash flash = new BeanFlash();
	
	public String editar() {
		this.usuario = this.usuarioEditar;
		return "/pages/usuarios/addUsuarios.xhtml";
	}
	
	public String excluir() throws Exception {
		this.usuario = this.usuarioEditar;
		this.uservice.remover(this.usuario.getId());
		return this.flash.redirectionAviso("Removido com Sucesso!", "/pages/usuarios/usuarios.xhtml?faces-redirect=true");		
	}
	
	public String novoUsuario() {
		this.usuario = new Usuario();
		return "/pages/usuarios/addUsuarios.xhtml?faces-redirect=true";
	}
	
	public String addUsuario() throws Exception {
		Usuario antigo = this.uservice.buscarPorLogin(this.usuario.getLogin());					
		if(antigo == null) {			
			this.uservice.salvar(this.usuario);
			return this.flash.redirectionAviso("Cadastrado com Sucesso!.", "/pages/usuarios/usuarios.xhtml?faces-redirect=true");			
		} else {
			return this.flash.redirectionAlerta("Login já cadastrado!", "/pages/usuarios/addUsuarios.xhtml?faces-redirect=true");
		}			
	}
	
	public Collection<Usuario> listaUsuarios() throws Exception{
		return this.uservice.listar();	
	}
	
}
