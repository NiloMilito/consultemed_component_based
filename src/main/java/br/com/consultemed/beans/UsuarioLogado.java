/**
 * 
 */
package br.com.consultemed.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import br.com.consultemed.models.Usuario;
import br.com.consultemed.security.AutenticadorService;
import lombok.Getter;
import lombok.Setter;

/**
 * @author carlosbarbosagomesfilho
 *
 */
@ManagedBean
@ViewScoped
@Getter
@Setter
public class UsuarioLogado implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private AutenticadorService autenticador;
	
	@Inject
	public UsuarioLogado() {
		this.usuario = new Usuario();
		this.autenticador = new AutenticadorService();
	}	

	public String logar() {	
		String pagRetorno = "/login.xhtml";
	    
		Usuario usuario = this.autenticador.autenticador(this.usuario.getLogin(), this.usuario.getSenha());
		    
	    if(usuario == null){
	    	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta","Login ou senha inválidos!");		         
		    PrimeFaces.current().dialog().showMessageDynamic(message);	
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login ou senha inválidos!", "Warn!"));
		    return pagRetorno;	    
	    }else{
	    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("usuario", usuario.getNome());
            pagRetorno = "/home.xhtml";
        }        
        return pagRetorno;		
	}

	public void logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();	
	}	
	
	public boolean isLogado() {
		boolean isLogeded = false;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if(usuario != null) {
			isLogeded = true;
		}
		return isLogeded;
	}
	
	


}
