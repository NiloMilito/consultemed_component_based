/**
 * 
 */
package br.com.consultemed.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

/**
 * @author carlosbarbosagomesfilho
 *
 */

@Named
@RequestScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Inject
	private UsuarioLogado usuarioLogado;
		
	
	public LoginController() {
		this.usuarioLogado = new UsuarioLogado();
	}

	public String loginUsuario() {		
		return this.usuarioLogado.logar();
	}
	
	public String logout() {
		this.usuarioLogado.logout();		
		return "/login?faces-redirect=true";
	}
	
//	Fase 1: Restore View (Restauração da visão);
//	Fase 2: Apply Request Values (Aplicar valores da requisição);
//	Fase 3: Process Validation (Processar as validações);
//	Fase 4: Update Model Values (Atualizar valores de modelo);
//	Fase 5: Invoke Application (Invocar aplicação);
//	Fase 6: Render Response (Renderizar a resposta).

	
	
}
