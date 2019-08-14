package br.com.consultemed.repository.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.utils.JPAUtils;

public class ConsultaDao implements IConsultaDao{
	
	private EntityManagerFactory factory = JPAUtils.getEntityManagerFactory();
	private EntityManager manager = null;

	@Override
	public void save(Consulta consulta) throws Exception {
		this.manager = factory.createEntityManager();
		this.manager.getTransaction().begin();
		this.manager.persist(consulta);
		this.manager.getTransaction().commit();
		this.manager.close();
	}

	@Override
	public Consulta findById(Long id) throws Exception {		
		this.manager = factory.createEntityManager();
		return this.manager.find(Consulta.class, id);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		this.manager = factory.createEntityManager();
		this.manager.getTransaction().begin();
		Consulta consulta = this.findById(id);
		this.manager.remove(consulta);
		this.manager.getTransaction().commit();
		this.manager.close();	
	}

	@Override
	public void update(Consulta consulta) throws Exception {
		this.manager = factory.createEntityManager();
		this.manager.getTransaction().begin();
		this.manager.merge(consulta);
		this.manager.getTransaction().commit();
		this.manager.close();
	}

	@Override
	public Collection<Consulta> listAll() throws Exception {
		this.manager = factory.createEntityManager();
		List<Consulta> consultas = new ArrayList<>();
		
		try {
			manager.getTransaction().begin();
			TypedQuery<Consulta> query = manager.createNamedQuery("Consulta.findAll", Consulta.class);
			consultas = query.getResultList();
			manager.getTransaction().commit();

		} catch (Exception e) {
			e.getMessage();
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
		return consultas;
	}

	@Override
	public boolean podeFazerAgendamento(Consulta consulta) {
		this.manager = factory.createEntityManager();
		Consulta anterior = new Consulta();
		Query query = this.manager.createQuery("SELECT c FROM Consulta c WHERE c.medico.id = :idMedico AND "
				+ "c.dataConsulta = :dataConsulta");
		try {		
			query.setParameter("idMedico", consulta.getMedico().getId());
			query.setParameter("dataConsulta", consulta.getDataConsulta());
			anterior = (Consulta) query.getSingleResult();
		} catch (Exception ex) {
			
		}
		return anterior != null;
	}

}
