package br.com.consultemed.repository.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.consultemed.models.Consulta;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.models.StatusConsulta;
import br.com.consultemed.utils.JPAUtils;

public class ConsultaDao implements IConsultaDao{
	
	private EntityManagerFactory factory = JPAUtils.getEntityManagerFactory();
	private EntityManager manager = null;

	@Override
	public void save(Consulta consulta) throws Exception {
		this.manager = factory.createEntityManager();
		consulta.setStatusConsulta(StatusConsulta.AGENDADA);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Consulta> buscarPorPeriodo(Date inicio, Date fim) {
		this.manager = factory.createEntityManager();
		List<Consulta> lst = new ArrayList<>();
		try {
			String sql = "SELECT c FROM Consulta c WHERE c.dataConsulta BETWEEN :inicio AND :fim ";					
			Query query = this.manager.createQuery(sql);
		
			query.setParameter("inicio", inicio);
			query.setParameter("fim", fim);			
			
			lst = query.getResultList();
		} catch (Exception ex) {}
		return lst;
	}

	@Override
	public void cancelarConsulta(Consulta consulta) {	
		try {
			consulta.setStatusConsulta(StatusConsulta.CANCELADA);
			this.update(consulta);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@Override
	public boolean podeFazerAgendamento(Date data, Long id) {
		this.manager = factory.createEntityManager();
		String sql = "SELECT case when (count (c) > 0) then true else false end FROM Consulta c WHERE c.medico.id = :id AND c.dataConsulta = :data";					
		TypedQuery<Boolean> query = this.manager.createQuery(sql, Boolean.class);	
		query.setParameter("id", id);
		query.setParameter("data", data);	
		boolean existe =  query.getSingleResult();		
		return existe;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Consulta> buscaConsultasNoMes(int mes) {
		this.manager = factory.createEntityManager();
		List<Consulta> lst = new ArrayList<>();
		try {
			String sql = "SELECT c FROM Consulta c WHERE MONTH(c.dataConsulta) = :mes";
			Query query = this.manager.createQuery(sql);		
			query.setParameter("mes", mes);		
			
			lst = query.getResultList();
		} catch (Exception ex) {}
		return lst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Consulta> buscaConsultasPorPaciente(Long id) {
		this.manager = factory.createEntityManager();
		List<Consulta> lst = new ArrayList<>();
		try {
			String sql = "SELECT c FROM Consulta c WHERE c.paciente.id = :idPaciente";
			Query query = this.manager.createQuery(sql);		
			query.setParameter("idPaciente", id);
			
			lst = query.getResultList();
		} catch (Exception ex) {}
		return lst;
	}

	@Override
	public Paciente maisCancelouConsulta() {
		this.manager = factory.createEntityManager();
		Paciente paciente = new Paciente();
		try {
			Query query = this.manager.createQuery("SELECT c.paciente FROM Consulta c WHERE c.statusConsulta = :status");
			query.setParameter("status", StatusConsulta.CANCELADA);	
			paciente = (Paciente) query.getSingleResult();
		} catch (Exception ex) {}
		return paciente;
	}

}
