package com.wincor.bcon.wnmesse.server.ejb;

import javax.ejb.Stateless;

@Stateless
public class AppCreatorEJB implements AppCreatorEJBLocal {

	private final static String BUILD_FILE = "/Users/Shared/eclipse_workspace/misc/wnmesse/client/android/app/wnmessebuild.sh";
	private final static String OUTPUT_LINK = "http://192.168.178.21:8080/TheSoftwareKitchen.apk";
	
	@Override
	public String createApplication() {
		try {
			Process p = Runtime.getRuntime().exec(BUILD_FILE);
			System.out.println("Exit code: "+p.waitFor());
			return OUTPUT_LINK;
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

}
