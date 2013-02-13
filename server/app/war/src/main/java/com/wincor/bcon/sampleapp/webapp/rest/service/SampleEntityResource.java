package com.wincor.bcon.sampleapp.webapp.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import com.wincor.bcon.sampleapp.server.db.entity.SampleEntity;
import com.wincor.bcon.sampleapp.server.ejb.SampleEJBLocal;

public class SampleEntityResource {

	private SampleEJBLocal sampleEJB = null;
	private Integer id = null;
	
	public SampleEntityResource(SampleEJBLocal sampleEJB, Integer id) {
		this.sampleEJB = sampleEJB;
		this.id = id;
	}
	
    @GET
    @Produces({"application/xml","application/json"})
    public SampleEntity get() {
        return sampleEJB.getSingleEntity(id);
    }
}
