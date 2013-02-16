package com.wincor.bcon.wnmesse.server.ejb;

import java.io.ByteArrayOutputStream;

import javax.ejb.Stateless;

import com.wincor.bcon.wnmesse.server.util.Utils;
import com.wincor.bcon.wnmesse.server.vo.AppCreationResult;
import com.wincor.bcon.wnmesse.server.vo.AppCustomization;

@Stateless
public class AppCreatorEJB implements AppCreatorEJBLocal {

	//private final static String BUILD_CMD = "/Users/Shared/eclipse_workspace/misc/wnmesse/client/android/app/wnmessebuild.sh";
	//private final static String OUTPUT_LINK = "http://192.168.178.21:8080/TheSoftwareKitchen.apk";
	private final static String BUILD_CMD = "cmd /c C:\\develop\\potpiejimmy.de\\misc\\wnmesse\\client\\android\\app\\wnmessebuild.bat";
	private final static String OUTPUT_LINK = "http://potpiejimmy.de/download/TheSoftwareKitchen.apk";
	
	@Override
	public AppCreationResult createApplication(AppCustomization customization) {
		try {
			Process p = Runtime.getRuntime().exec(BUILD_CMD);
			p.getOutputStream().close();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Utils.writeThrough(p.getInputStream(), baos);
			baos.close();
			String result = new String(baos.toByteArray());
			System.out.println("Exit code: "+p.waitFor());
			return new AppCreationResult(OUTPUT_LINK, result);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

}
