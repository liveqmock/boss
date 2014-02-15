package com.dtv.oss.service.command;

import java.util.*;

public class ErrorMessage {
	private Map errorMessage;
	private static ErrorMessage instance = null;
	private ResourceBundle resource = null;
	
	private ErrorMessage() {
		try {
			errorMessage = Collections.synchronizedMap(new HashMap());
			//Locale defaultLocale = Locale.CHINA;
			Locale defaultLocale = new Locale("zh", "CN");
			resource =
	                ResourceBundle.getBundle("com.dtv.oss.service.command.resource.ErrorMessage",defaultLocale);
		} catch (Exception e) {
			
		}
	}
	
	public static ErrorMessage getInstance() {
		if (instance == null) {
			instance = new ErrorMessage();
		}
		return instance;
		
	}

	public String getMessage(int errorCode) {
		String result;
		
		if (errorMessage.containsKey(new Integer(errorCode))) {
			result = (String)errorMessage.get(new Integer(errorCode));
		} else {
			if (resource == null) {
				result = "找不到错误定义文件！";
			}
	        try {
				result = resource.getString(errorCode+"");
				errorMessage.put(new Integer(errorCode), result);
			} catch (MissingResourceException e) {
				result = "未知错误";
			}
		}
		return result;
	}
	
	//for test
	public static void main(String[] args) {
		
		System.out.println(ErrorMessage.getInstance().getMessage(new Integer(args[0]).intValue()));
	}
}