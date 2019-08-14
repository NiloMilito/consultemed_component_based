package br.com.consultemed.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public class Pessoa extends AbstractEntity<Long> {
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="email")
	private String email;
	
	@Column(name="cpf", unique=true)
	private String cpf;
	
	public Pessoa() {
		
	}

	public Pessoa(Long id, String nome, String cpf) {		
		super.setId(id);
		this.nome = nome;
		this.cpf = cpf;
	}
	
}
