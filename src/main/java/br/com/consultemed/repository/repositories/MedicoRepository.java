/**
 * 
 */
package br.com.consultemed.repository.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.consultemed.models.Medico;
import br.com.consultemed.utils.JPAUtils;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class MedicoRepository {

	EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
	EntityManager factory = null;

	@SuppressWarnings("unchecked")
	public List<Medico> listaMedicos() {
		this.factory = emf.createEntityManager();
		Query query = this.factory.createQuery("SELECT object(m) FROM Medico as m");
		return query.getResultList();
	}
	
	public Medico buscarPorCRM(String crm) {
		this.factory = emf.createEntityManager();
		Medico medico = null;
		try {
			Query query = this.factory.createQuery("SELECT m FROM Medico m WHERE m.crm LIKE :crm ");
			query.setParameter("crm", crm);			
			medico = (Medico) query.getSingleResult();		
		} catch(Exception e) {
			
		}
		return medico;
	}
	
	public Medico buscarPorNome(String nome) {
		this.factory = emf.createEntityManager();
		Medico medico = null;
		try {
			Query query = this.factory.createQuery("SELECT m FROM Medico m WHERE m.nome LIKE :nome ");
			query.setParameter("nome", nome);			
			medico = (Medico) query.getSingleResult();		
		} catch(Exception e) {
			
		}
		return medico;
	}

	public Collection<Medico> listarMedicos() throws Exception {
		this.factory = emf.createEntityManager();
		List<Medico> contatos = new ArrayList<Medico>();
		try {
			factory.getTransaction().begin();
			TypedQuery<Medico> query = factory.createNamedQuery("Medico.findAll", Medico.class);
			contatos = query.getResultList();
			factory.getTransaction().commit();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}

		return contatos;
	}

	public void salvarMedico(Medico medico) {
		this.factory = emf.createEntityManager();
		try {
			factory.getTransaction().begin();
			if (medico.getId() == null) {
				factory.persist(medico);
			} else {
				factory.merge(medico);
			}
			factory.getTransaction().commit();
		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();

		} finally {
			factory.close();
		}
	}

	public void deleteById(Long id) throws Exception {
		this.factory = emf.createEntityManager();
		Medico medico = new Medico();

		try {
			medico = factory.find(Medico.class, id);
			factory.getTransaction().begin();
			factory.remove(medico);
			factory.getTransaction().commit();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}

	}

	public Medico buscarPorId(Long id) {
		this.factory = emf.createEntityManager();
		return this.factory.find(Medico.class, id);
	}

}
