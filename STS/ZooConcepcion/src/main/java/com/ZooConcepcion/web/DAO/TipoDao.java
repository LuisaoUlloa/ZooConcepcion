package com.ZooConcepcion.web.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TipoDao {
	
	@Autowired
	private ITipoDao crud;
	
	public ITipoDao crud()
	{
		return this.crud;
	}

}
