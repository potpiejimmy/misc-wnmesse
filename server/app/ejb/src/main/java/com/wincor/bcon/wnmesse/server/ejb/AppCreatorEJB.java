package com.wincor.bcon.wnmesse.server.ejb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.ejb.Stateless;

import com.wincor.bcon.wnmesse.server.util.Utils;
import com.wincor.bcon.wnmesse.server.vo.AppCreationResult;
import com.wincor.bcon.wnmesse.server.vo.AppCustomization;

@Stateless
public class AppCreatorEJB implements AppCreatorEJBLocal {

	//private final static String BUILD_CMD = "/Users/Shared/eclipse_workspace/misc/wnmesse/client/android/app/wnmessebuild.sh";
	//private final static String OUTPUT_LINK = "http://192.168.178.21:8080/TheSoftwareKitchen.apk";
	private final static String SOURCE_ROOT = "C:\\develop\\potpiejimmy.de\\misc\\wnmesse\\client\\android\\app";
	private final static String BUILD_CMD = "cmd /c C:\\develop\\potpiejimmy.de\\misc\\wnmesse\\client\\android\\app\\wnmessebuild.bat";
	private final static String OUTPUT_LINK = "http://potpiejimmy.de/download/TheSoftwareKitchen_";
	
	@Override
	public AppCreationResult createApplication(AppCustomization customization) {
		try {
			/**
			 * Customize picture:
			 */
			FileOutputStream fos = new FileOutputStream(new File(SOURCE_ROOT, "res\\drawable-hdpi\\portrait.jpg"));
			fos.write(customization.getImg());
			fos.close();
			
			/**
			 * Name
			 */
			fos = new FileOutputStream(new File(SOURCE_ROOT, "res\\values\\customstrings.xml"));
			OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
			writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><resources><string name=\"username\">");
			writer.write(customization.getName());
			writer.write("</string></resources>");
			writer.close();
			
			/**
			 * BUILD
			 */
			String appId = appId(customization);
			Process p = Runtime.getRuntime().exec(BUILD_CMD + " " + appId);
			p.getOutputStream().close();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Utils.writeThrough(p.getInputStream(), baos);
			baos.close();
			String result = new String(baos.toByteArray());
			System.out.println("Exit code: "+p.waitFor());
			return new AppCreationResult(OUTPUT_LINK + appId + ".apk", result);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	protected static String appId(AppCustomization customization) throws Exception {
		String id = customization.getName().toLowerCase();
		id = id.replace(' ', '_').
				replace('ä', 'a').
				replace('ö', 'o').
				replace('ü', 'u').
				replace("ß","ss");
		return id;
	}
}
