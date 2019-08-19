package br.com.consultemed.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Table(name="tb_consulta")
@Entity
@Getter
@Setter
@NamedQueries({ @NamedQuery(name = "Consulta.findAll", query = "SELECT c FROM Consulta c")})
public class Consulta extends AbstractEntity<Long> {	
	
	@OneToOne
	private Paciente paciente;
	
	@ManyToOne
	private Medico medico;
	
	@Enumerated(EnumType.ORDINAL)
	private StatusConsulta statusConsulta;	

	public Consulta() {
		this.paciente = new Paciente();
		this.medico = new Medico();
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_consulta")
	private Date dataConsulta;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "consulta")
	private List<Exame> exames = new ArrayList<>();
	

}
