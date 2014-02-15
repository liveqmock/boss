package com.dtv.oss.web.filter;

import java.util.Map;

public class BookmarkedResource implements java.io.Serializable{
    private String action = null;
    private Map parameters = null;

    public BookmarkedResource(String action, Map parameters) {
        this.action = action;
        this.parameters = parameters;
    }

    public String getAction() {
        return action;
    }

    public Map getParameters() {
        return parameters;
    }

    public String toString() {
        return "BookmarkedResource [ action=" + action + ", parameters=" + parameters + "]";
    }

}