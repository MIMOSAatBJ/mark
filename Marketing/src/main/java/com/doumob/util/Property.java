package com.doumob.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {
	
	private static Properties prop=new Properties();
	
	static{
		InputStream is=Property.class.getResourceAsStream("/config.text");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		return prop.getProperty(key);
	}
	
}
