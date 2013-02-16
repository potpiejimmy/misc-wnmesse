package com.wincor.bcon.wnmesse.server.ejb;

import javax.ejb.Local;

import com.wincor.bcon.wnmesse.server.vo.AppCreationResult;
import com.wincor.bcon.wnmesse.server.vo.AppCustomization;

@Local
public interface AppCreatorEJBLocal {

	public AppCreationResult createApplication(AppCustomization customization);
}
