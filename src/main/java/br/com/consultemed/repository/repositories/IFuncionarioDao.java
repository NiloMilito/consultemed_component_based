package br.com.consultemed.repository.repositories;

import br.com.consultemed.models.Funcionario;

public interface IFuncionarioDao extends GenericDao<Funcionario, Long>{
	
	public Funcionario buscarPorEmail(String email);

}
