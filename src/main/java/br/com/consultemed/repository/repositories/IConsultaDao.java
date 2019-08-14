package br.com.consultemed.repository.repositories;

import br.com.consultemed.models.Consulta;

public interface IConsultaDao extends GenericDao<Consulta, Long>{
	
	public boolean podeFazerAgendamento(Consulta consulta);

}
