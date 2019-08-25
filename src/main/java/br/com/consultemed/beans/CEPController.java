package br.com.consultemed.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.models.Endereco;
import br.com.consultemed.webServices.EnderecoJSON;
import br.com.consultemed.webServices.EnderecoWebService;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter	
public class CEPController {
	
	@Inject
	private EnderecoWebService webService;
	
	public CEPController() {	
		this.webService = new EnderecoWebService();
	}
	
	public Endereco buscaCep(String cep) {
		Endereco endereco = ParseEntyToJSON(this.webService.getBuscaCEP(cep));
		
		return endereco;
	}

	private Endereco ParseEntyToJSON(EnderecoJSON enderecoJSON) {
		Endereco endereco = new Endereco(
			enderecoJSON.getLogradouro(), enderecoJSON.getBairro(), enderecoJSON.getCidade(), enderecoJSON.getUf());
		
		return endereco;
	}

	

}
