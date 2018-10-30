package com.ZooConcepcion.web.DAO;

import org.springframework.data.repository.CrudRepository;

import com.ZooConcepcion.web.entity.Animal;

public interface IAnimalDao extends CrudRepository<Animal, Integer> {

	
}
