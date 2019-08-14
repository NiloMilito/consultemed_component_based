package br.com.consultemed.services;

import java.util.Collection;

import javax.inject.Inject;

import br.com.consultemed.models.Funcionario;
import br.com.consultemed.repository.repositories.IFuncionarioDao;

public class FuncionarioService implements IFuncionarioService{
	
	@Inject
	private IFuncionarioDao dao;

	@Override
	public void salvar(Funcionario object) throws Exception {
		this.dao.save(object);
	}

	@Override
	public void alterar(Funcionario object) throws Exception {
		this.dao.update(object);
	}

	@Override
	public void remover(Long id) throws Exception {
		this.dao.deleteById(id);
	}

	@Override
	public Funcionario buscar(Long id) throws Exception {		
		return this.dao.findById(id);
	}

	@Override
	public Collection<Funcionario> listar() throws Exception {		
		return this.dao.listAll();
	}

}
