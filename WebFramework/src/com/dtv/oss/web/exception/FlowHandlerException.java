package com.dtv.oss.web.exception;

/**
 * This exception will be thrown when there is an error processing a flow handler
 */
public class FlowHandlerException extends Exception
    implements java.io.Serializable {

    public FlowHandlerException() {}

    public FlowHandlerException(String str) {
        super(str);
    }
}

