package br.com.consultemed.webServices;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoJSON {
	private String bairro;
	private String cidade;
	private String logradouro;
	private String resultado;
	private String resultado_txt;
	private String tipo_logradouro;
	private String uf;

}
