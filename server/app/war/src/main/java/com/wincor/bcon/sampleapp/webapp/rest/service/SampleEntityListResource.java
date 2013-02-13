package com.wincor.bcon.sampleapp.webapp.rest.service;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.wincor.bcon.sampleapp.server.db.entity.SampleEntity;
import com.wincor.bcon.sampleapp.server.ejb.SampleEJBLocal;

@Path("/sampleentities")
@RequestScoped
public class SampleEntityListResource {

	@EJB
	private SampleEJBLocal sampleEJB;
	
    @GET
    @Produces({"application/xml","application/json"})
    public List<SampleEntity> get() {
        return sampleEJB.getEntities();
    }
    
    @Path("{id}")
    public SampleEntityResource getSingleResource(@PathParam("id") Integer id) {
    	return new SampleEntityResource(sampleEJB, id);
    }
}
