package com.ZooConcepcion.web.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SectorDao {
	
	@Autowired
	private ISectorDao crud;
	
	public ISectorDao crud()
	{
		return this.crud;
	}

}
