/**
 * 
 */
package br.com.consultemed.services;

import java.util.Collection;

import javax.inject.Inject;

import br.com.consultemed.models.Usuario;
import br.com.consultemed.repository.repositories.UsuarioRepository;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class UsuarioService implements IGeneric<Usuario, Long>{

	@Inject
	private UsuarioRepository dao;

	@Override
	public void remover(Long id) throws Exception {
		this.dao.deleteById(id);
	}

	@Override
	public Collection<Usuario> listar() throws Exception {
		return this.dao.listAll();
	}

	@Override
	public void salvar(Usuario object) throws Exception {
		this.dao.save(object);
	}

	@Override
	public void alterar(Usuario object) throws Exception {
		this.dao.update(object);
	}

	@Override
	public Usuario buscar(Long id) throws Exception {		
		return this.dao.findById(id);
	}

	public Usuario buscarPorLogin(String login) {
		return this.dao.buscarPorLogin(login);
	}
	
	
}
