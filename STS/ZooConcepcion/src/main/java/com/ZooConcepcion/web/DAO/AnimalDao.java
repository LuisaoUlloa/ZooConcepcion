package com.ZooConcepcion.web.DAO;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ZooConcepcion.web.entity.Animal;

@Repository
@Transactional
public class AnimalDao {

	@Autowired
	private IAnimalDao crud;

	@PersistenceContext
	private EntityManager em;

	public IAnimalDao crud() {
		return this.crud;
	}

	public List<Animal> buscarAnimalesTipo(int tipo) {
		List<Animal> animales = null;

		String hql = "select u from Animal u where u.tipo.id = :tip";

		animales = em.createQuery(hql, Animal.class).setParameter("tip", tipo).getResultList();

		return animales;

	}

	public List<Animal> buscarAnimalesFecha(Date fechaInicio, Date fechaFin) {
		
		List<Animal> animales = null;
		
		String hql ="select u from Animal u where u.fechaIngreso >= :fInicio and u.fechaIngreso <=:fTermino";
		
		animales = em.createQuery(hql,Animal.class)
				.setParameter("fInicio", fechaInicio)
				.setParameter("fTermino", fechaFin).getResultList();
		
		return animales;

	}
}
