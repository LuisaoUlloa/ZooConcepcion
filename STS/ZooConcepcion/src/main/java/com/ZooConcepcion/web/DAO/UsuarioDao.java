package com.ZooConcepcion.web.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ZooConcepcion.web.entity.Usuario;

@Repository
@Transactional
public class UsuarioDao {

	@Autowired
	private IUsuarioDao crud;

	@PersistenceContext
	private EntityManager em;

	public IUsuarioDao crud() {
		return this.crud;
	}

	public Usuario buscarPorNombreUsuario(String nombreUsuario) {

		String hql = "select u from Usuario u where u.usuario = :user";

		return (Usuario) em.createQuery(hql, Usuario.class).setParameter("user", nombreUsuario).getSingleResult();
	}

}
