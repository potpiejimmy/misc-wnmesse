package com.wincor.bcon.sampleapp.server.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.wincor.bcon.sampleapp.server.db.entity.SampleEntity;

@Stateless
public class SampleEJB implements SampleEJBLocal, SampleEJBRemote {

    @PersistenceContext(unitName = "EJBsPU")
    private EntityManager em;
    
	public List<SampleEntity> getEntities() {
		@SuppressWarnings("unchecked")
		List<SampleEntity> result = em.createNamedQuery("SampleEntity.findAll").getResultList();
		return result;
	}

	public SampleEntity getSingleEntity(Object id) {
		return em.find(SampleEntity.class, id);
	}
	
	public void insertOrUpdate(SampleEntity entity) {
		
		if (entity.getId() != null)
			em.merge(entity);  // update the entity
		else
			em.persist(entity);  // insert a new entity
	}
}
