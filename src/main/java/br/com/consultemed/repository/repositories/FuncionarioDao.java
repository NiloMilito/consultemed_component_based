package br.com.consultemed.repository.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.consultemed.models.Funcionario;
import br.com.consultemed.utils.JPAUtils;

public class FuncionarioDao implements IFuncionarioDao{
	
	private EntityManagerFactory factory = JPAUtils.getEntityManagerFactory();
	private EntityManager manager = null;

	@Override
	public void save(Funcionario funcionario) throws Exception {
		this.manager = factory.createEntityManager();
		this.manager.getTransaction().begin();
		this.manager.persist(funcionario);
		this.manager.getTransaction().commit();
		this.manager.close();
		
	}

	@Override
	public Funcionario findById(Long id) throws Exception {
		this.manager = factory.createEntityManager();
		return this.manager.find(Funcionario.class, id);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		this.manager = factory.createEntityManager();
		this.manager.getTransaction().begin();
		Funcionario funcionario = this.findById(id);
		this.manager.remove(funcionario);
		this.manager.getTransaction().commit();
		this.manager.close();	
	}

	@Override
	public void update(Funcionario funcionario) throws Exception {
		this.manager = factory.createEntityManager();
		this.manager.getTransaction().begin();
		this.manager.merge(funcionario);
		this.manager.getTransaction().commit();
		this.manager.close();
	}

	@Override
	public Collection<Funcionario> listAll() throws Exception {
		this.manager = factory.createEntityManager();
		List<Funcionario> funcionarios = new ArrayList<>();
		
		try {
			manager.getTransaction().begin();
			TypedQuery<Funcionario> query = manager.createNamedQuery("Funcionario.findAll", Funcionario.class);
			funcionarios = query.getResultList();
			manager.getTransaction().commit();

		} catch (Exception e) {
			e.getMessage();
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
		return funcionarios;
	}

	@Override
	public Funcionario buscarPorEmail(String email) {		
		Query query = this.manager.createQuery("SELECT f FROM Fiuncionario f WHERE f.email LIKE :email");
		query.setParameter("email", email);		
		return (Funcionario) query.getSingleResult();
	}

}
