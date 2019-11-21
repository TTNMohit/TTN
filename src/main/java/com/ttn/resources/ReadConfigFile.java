package com.ttn.resources;

import java.io.FileInputStream;

public class ReadConfigFile {
	

	public void ReadMyConfigFile()
	{	
		try{
			String xmlPath;
			xmlPath = new java.io.File( "." ).getCanonicalPath();
			xmlPath = xmlPath  + "\\src\\test\\resources\\" +"config.properties";
			Variables.input = new FileInputStream(xmlPath);
			Variables.prop.load(Variables.input);
			}
		catch(Exception e){
			//// File not found exception
			}
	}
}
