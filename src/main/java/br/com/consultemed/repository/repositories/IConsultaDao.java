package br.com.consultemed.repository.repositories;

import java.util.Date;
import java.util.List;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Paciente;

public interface IConsultaDao extends GenericDao<Consulta, Long>{
	
	public boolean podeFazerAgendamento(Date data, Long id);

	public List<Consulta> buscarPorPeriodo(Date inicio, Date fim);

	public void cancelarConsulta(Consulta consulta);
	
	public List<Consulta> buscaConsultasNoMes(int mes);
	
	public List<Consulta> buscaConsultasPorPaciente(Long id);
	
	public Paciente maisCancelouConsulta();
	

}
