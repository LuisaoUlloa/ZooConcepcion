package com.ZooConcepcion.web.DAO;

import org.springframework.data.repository.CrudRepository;

import com.ZooConcepcion.web.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Integer> {

}
