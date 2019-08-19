package br.com.consultemed.dto;

import java.util.Date;

import br.com.consultemed.models.Medico;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.models.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaDto {

	private Paciente paciente;
	
	private Medico medico;
	
	private StatusConsulta statusConsulta;
	
	private Date inicio, fim, data, mes;

	public ConsultaDto(Paciente paciente, Medico medico, StatusConsulta statusConsulta) {
		super();
		this.paciente = paciente;
		this.medico = medico;
		this.statusConsulta = statusConsulta;
	}

	public ConsultaDto() {
		super();
	}
	

}
