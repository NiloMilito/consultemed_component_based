package br.com.consultemed.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.repository.repositories.ConsultaDao;
import br.com.consultemed.repository.repositories.IConsultaDao;

public class ConsultaService implements IConsultaService{	
	
	@Inject
	private IConsultaDao repository;
	
	public ConsultaService() {
		this.repository = new ConsultaDao();
	}

	@Override
	public void salvar(Consulta object) throws Exception {
		this.repository.save(object);
	}

	@Override
	public void alterar(Consulta object) throws Exception {
		this.repository.update(object);
	}

	@Override
	public void remover(Long id) throws Exception {
		this.repository.deleteById(id);
	}

	@Override
	public Consulta buscar(Long id) throws Exception {		
		return this.repository.findById(id);
	}

	@Override
	public Collection<Consulta> listar() throws Exception {
		return this.repository.listAll();
	}	

	@Override
	public boolean podeFazerAgendamento(Date data, Long id) {
		return this.repository.podeFazerAgendamento(data, id);
	}

	@Override
	public List<Consulta> buscarPorPeriodo(Date inicio, Date fim) {		
		return this.repository.buscarPorPeriodo(inicio, fim);
	}

	@Override
	public void cancelarConsulta(Consulta consulta) {
		this.repository.cancelarConsulta(consulta);
	}

	@Override
	public List<Consulta> buscaConsultasNoMes(int mes) {	
		return this.repository.buscaConsultasNoMes(mes);
	}

	@Override
	public List<Consulta> buscaConsultasPorPaciente(Long id) {		
		return this.repository.buscaConsultasPorPaciente(id);
	}

	@Override
	public Paciente maisCancelouConsulta() {		
		return this.repository.maisCancelouConsulta();
	}

}
