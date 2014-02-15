package com.dtv.oss.web.exception;

/**
 * This exception will be thrown when there is an error processing a flow handler
 */
public class WebActionException extends Exception
    implements java.io.Serializable {

    public WebActionException() {}

    public WebActionException(String str) {
        super(str);
    }
}

