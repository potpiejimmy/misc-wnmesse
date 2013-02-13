package com.wincor.bcon.sampleapp.server.ejb;

import java.util.List;

import javax.ejb.Local;

import com.wincor.bcon.sampleapp.server.db.entity.SampleEntity;

@Local
public interface SampleEJBLocal {
	
	public List<SampleEntity> getEntities();
	
	public SampleEntity getSingleEntity(Object id);
	
	public void insertOrUpdate(SampleEntity entity);
}
