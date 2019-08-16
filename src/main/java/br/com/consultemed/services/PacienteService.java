package br.com.consultemed.services;

import java.util.Collection;

import javax.inject.Inject;

import br.com.consultemed.models.Paciente;
import br.com.consultemed.repository.repositories.IPacienteDao;
import br.com.consultemed.repository.repositories.PacienteDao;

public class PacienteService implements IPacienteService{
	
	@Inject
	private IPacienteDao pacienteDao;
	
	
	public PacienteService() {
		this.pacienteDao =  new PacienteDao();
	}

	@Override
	public void salvar(Paciente object) throws Exception {
		this.pacienteDao.save(object);		
	}

	@Override
	public void alterar(Paciente object) throws Exception {		
		this.pacienteDao.update(object);
	}

	@Override
	public void remover(Long id) throws Exception {
		this.pacienteDao.deleteById(id);
	}

	@Override
	public Paciente buscar(Long id) throws Exception {
		return this.pacienteDao.findById(id);
	}

	@Override
	public Paciente buscarPorNome(String nome) {		
		return this.pacienteDao.buscarPorNome(nome);
	}

	@Override
	public Paciente buscarPorCpf(String cpf) {		
		return this.pacienteDao.buscarPorCpf(cpf);
	}

	@Override
	public Collection<Paciente> listar() throws Exception {
		return this.pacienteDao.listAll();
	}

	public Paciente buscarPacientePorCpf(String cpf) {
		return this.pacienteDao.buscarPorCpf(cpf);
	}

}
