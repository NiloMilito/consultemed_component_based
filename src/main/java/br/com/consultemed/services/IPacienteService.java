package br.com.consultemed.services;

import br.com.consultemed.models.Paciente;

public interface IPacienteService extends IGeneric<Paciente, Long>{
	
	public Paciente buscarPorNome(String nome);
	
	public Paciente buscarPorCpf(String cpf);

}
