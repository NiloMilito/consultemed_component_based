/**
 * 
 */
package br.com.consultemed.repository.repositories;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.consultemed.models.Usuario;
import br.com.consultemed.utils.JPAUtils;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class UsuarioRepository implements GenericDao<Usuario, Long>{

	EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
	EntityManager manager = emf.createEntityManager();

	@Override
	public void save(Usuario usuario) throws Exception {
		this.manager.getTransaction().begin();
		this.manager.persist(usuario);
		this.manager.getTransaction().commit();
		this.manager.close();
	}

	@Override
	public Usuario findById(Long id) throws Exception {		
		return this.manager.find(Usuario.class, id);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		this.manager.getTransaction().begin();
		Usuario usuario = this.findById(id);
		this.manager.remove(usuario);
		this.manager.getTransaction().commit();
		this.manager.close();
	}

	@Override
	public void update(Usuario usuario) throws Exception {
		this.manager.getTransaction().begin();
		this.manager.merge(usuario);
		this.manager.getTransaction().commit();
		this.manager.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Usuario> listAll() throws Exception {
		Query query = this.manager.createQuery("SELECT object(u) FROM Usuario as u");
		return query.getResultList();
	}

	public Usuario buscarPorLogin(String login) {
		Usuario usuario = null;
		try {
			Query query = this.manager.createQuery("SELECT u FROM Usuario u Where u.login LIKE :login ");
			query.setParameter("login", login);	
			usuario = (Usuario) query.getSingleResult();
		} catch (Exception ex) {
			
		}
		return usuario;
	}

}
