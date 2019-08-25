package br.com.consultemed.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Endereco{	
	
	public Endereco(String rua, String bairro, String cidade, String estado) {
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	public Endereco() {}

	@Column(name="cep")
	private String cep;
	
	@Column(name="rua")
	private String rua;
	
	@Column(name="numero")
	private String numero;
	
	@Column(name="bairro")
	private String bairro;
	
	@Column(name="cidade")
	private String cidade;
	
	@Column(name="estado")
	private String estado;
	

}
