package com.dtv.oss.service.command;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Shanghai DiviVision Technology Co. Ltd</p>
 * @author Leon Liu
 * @version 1.0
 */

public class CommandException extends Exception  {
    public CommandException()
    {    	
    }
    public CommandException(Exception e)
    {
        super(e);
    }

    public CommandException(String s) {
        super(s);
    }
    
    /*add by Mac*/
    private int errorCode = 0;

    public CommandException(int newErrorCode) {
    	this(newErrorCode, "");
    }
    public CommandException(int newErrorCode, String newDescription) {
    	super(newDescription);
    	errorCode = newErrorCode;
    }
        
    public int getErrorCode() {
    	return errorCode;
    }
    public String getMessage() {
    	if (super.getMessage()==null||super.getMessage().trim().equals("")) {
    		return ErrorMessage.getInstance().getMessage(errorCode);
    	} else {
    		return super.getMessage();
    	}
    }
}