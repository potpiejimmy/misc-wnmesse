package com.wincor.bcon.wnmesse.server.ejb;

import java.io.IOException;

import javax.ejb.Stateless;

@Stateless
public class AppCreatorEJB implements AppCreatorEJBLocal {

	@Override
	public void createApplication() {
		try {
			Runtime.getRuntime().exec("notepad.exe");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
