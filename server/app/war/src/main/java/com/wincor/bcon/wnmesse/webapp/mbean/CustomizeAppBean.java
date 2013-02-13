package com.wincor.bcon.wnmesse.webapp.mbean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.wincor.bcon.wnmesse.server.ejb.AppCreatorEJBLocal;

/**
 * This is a sample JSF managed bean.
 * 
 * Pleas note that no configuration in faces-config.xml is required, the
 * bean can be referenced in a JSF faces file (XHTML) using the name
 * specified in the Named annotation. Also note the scope annotation
 * that specified the managed bean's scope.    
 */
@Named
@SessionScoped
public class CustomizeAppBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private AppCreatorEJBLocal creatorEJB;

	/**
	 * Called from JSF if save button is pressed.
	 */
	public void createApp() {
		creatorEJB.createApplication();
	}

}
