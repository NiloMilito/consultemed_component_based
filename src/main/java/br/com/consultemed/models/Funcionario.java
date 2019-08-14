package br.com.consultemed.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name="tb_funcionario")
@Entity
@NamedQueries({ @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f")})
@Getter
@Setter
public class Funcionario extends Pessoa{
	
	@Column(name="matricula")
	private String matricula;

}
