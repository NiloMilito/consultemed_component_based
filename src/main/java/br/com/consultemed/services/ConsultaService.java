package br.com.consultemed.services;

import java.util.Collection;

import javax.inject.Inject;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.repository.repositories.IConsultaDao;

public class ConsultaService implements IConsultaService{
	
	@Inject
	private IConsultaDao repository;

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
	public boolean podeFazerAgendamento(Consulta consulta) {
		return this.repository.podeFazerAgendamento(consulta);
	}

}
