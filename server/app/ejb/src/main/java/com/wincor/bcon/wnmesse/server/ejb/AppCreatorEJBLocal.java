package com.wincor.bcon.wnmesse.server.ejb;

import javax.ejb.Local;

@Local
public interface AppCreatorEJBLocal {

	public String createApplication();
}
