package br.com.consultemed.webServices;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class EnderecoWebService {
	public final static String URL = "http://cep.republicavirtual.com.br/web_cep.php?cep=";
	public final static String FIM = "&formato=json";	
		
	public EnderecoJSON getBuscaCEP(String cep) {
		EnderecoJSON endereco = new EnderecoJSON();
		
		Client client = ClientBuilder.newClient();
	
		WebTarget target = client.target(URL + cep + FIM);
		Response response = target.request().get();	

		endereco = response.readEntity(EnderecoJSON.class);
		  
		return endereco;
	}

	
}
