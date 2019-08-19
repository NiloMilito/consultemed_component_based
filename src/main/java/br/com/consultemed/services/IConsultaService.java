package br.com.consultemed.services;

import java.util.Date;
import java.util.List;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Paciente;

public interface IConsultaService extends IGeneric<Consulta, Long>{
	
	public boolean podeFazerAgendamento(Date date, Long id);

	public List<Consulta> buscarPorPeriodo(Date inicio, Date fim);

	public void cancelarConsulta(Consulta consulta);
	
	public List<Consulta> buscaConsultasNoMes(int mes);
	
	public List<Consulta> buscaConsultasPorPaciente(Long id);
	
	public Paciente maisCancelouConsulta();

}
