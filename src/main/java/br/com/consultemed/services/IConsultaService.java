package br.com.consultemed.services;

import br.com.consultemed.models.Consulta;

public interface IConsultaService extends IGeneric<Consulta, Long>{
	
	public boolean podeFazerAgendamento(Consulta consulta);

}
