package com.dtv.oss.service.controller.exception;

public class EJBEventException  extends Exception implements java.io.Serializable{
    public EJBEventException() {}

    public EJBEventException(String str) {
        super(str);
    }
}