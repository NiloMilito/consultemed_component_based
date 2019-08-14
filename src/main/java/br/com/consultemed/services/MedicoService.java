/**
 * 
 */
package br.com.consultemed.services;

import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.models.Medico;
import br.com.consultemed.repository.repositories.MedicoRepository;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class MedicoService {

	@Inject
	private MedicoRepository dao;
	
	
	public MedicoService() {
		this.dao = new MedicoRepository();
	}

	public List<Medico> listaMedico(){
		return this.dao.listaMedicos();
	}
	
	public void salvarMedico(Medico medico) {		
		this.dao.salvarMedico(medico);	
	}
	
	public void deletarMedico(Long id) throws Exception {
		this.dao.deleteById(id);
	}
	
	public Medico buscarPorCrm(String crm) {
		return this.dao.buscarPorCRM(crm);
	}
	
	public Medico buscarPorNome(String nome) {
		return this.dao.buscarPorNome(nome);
	}
	
	public Medico buscarPorId(Long id) {
		return this.dao.buscarPorId(id);
	}
	
}
