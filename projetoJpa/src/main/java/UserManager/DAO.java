package UserManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import UserModel.User;

public class DAO<E> {
	// DAO (Data Access Object): responsável pelo acesso aos dados.
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Class<E> entity;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("jpaHibernate");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public DAO(Class<E> entity) {
		this.entity = entity;
		em = emf.createEntityManager();
	}
	
	public DAO<E> open() {
		em.getTransaction().begin();
		return this;
	}
	
	public DAO<E> close() {
		em.getTransaction().commit();
		return this;
	}
	
//	CREATE
	public DAO<E> create(E entity) {
		em.persist(entity);
		return this;
	}

//	GET
	public E findById(Object id) {
		return em.find(entity, id);
	}

//	UPDATE
	public User update(int id, String name, String email, String password) {
		DAO<E> dao = new DAO<E>(entity);
		dao.open();
		
		User user = (User) dao.findById(id);
		user.setId(id);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		
		em.merge(user);
		
		dao.close();
		
		return user;
	}

// DELETE
	public DAO<E> delete(int id) {
		DAO<E> dao = new DAO<E>(entity);
		E findUser = dao.findById(id);
		// Verificamos se o user se encontra no EM atual
		em.remove(em.contains(findUser) ? findUser: em.merge(findUser));
		return this;
	}
}
