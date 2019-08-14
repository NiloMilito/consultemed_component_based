package br.com.consultemed.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name="tb_paciente")
@Entity
@NamedQueries({ @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p")})
@Getter
@Setter
public class Paciente extends Pessoa {	
	
	@Embedded
	private Endereco endereco;
	
	public Paciente() {
		this.endereco = new Endereco();
	}
	

}
