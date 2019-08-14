package br.com.consultemed.repository.repositories;



import br.com.consultemed.models.Paciente;

public interface IPacienteDao extends GenericDao<Paciente, Long> {	

	public Paciente buscarPorNome(String nome);
	
	public Paciente buscarPorCpf(String cpf);
}
