/*
 * Created on 2005-9-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service;


/**
 * @author Leon Liu
 *
 * 业务组件抛出的异常
 */
public class ServiceException extends Exception {
    Exception innerException;

    public ServiceException()
    {    	
    }
    public ServiceException(Exception e)
    {
        this.innerException = e;
    }

    Exception getInnerException()
    {
        return innerException;
    }

    public ServiceException(String s) {
        super(s);
    }
    
    public String getMessage() {
  		return super.getMessage();
    }
}
