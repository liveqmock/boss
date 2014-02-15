package com.dtv.oss.web.controller;

/**
 * This class represents the mapping between an EJBEvent and the necessary EJB Command Class
*/

public class EventMapping implements java.io.Serializable {

    private String eventClass = null;
    private String ejbCommandClass = null;

    public EventMapping(String eventClassName, String ejbCommandClass ) {
        this.eventClass = eventClassName;
        this.ejbCommandClass = ejbCommandClass;

    }

   public String getEJBCommandClassName() {
        return ejbCommandClass;
    }

   public String getEventClassName() {
        return eventClass;
    }


    public String toString() {
        return "[EventMapping:" +
                " eventClass=" + eventClass +
                ", ejbActionClass=" + ejbCommandClass +
                "]";
    }
}


