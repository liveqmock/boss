package com.dtv.oss.web.view;

import java.util.HashMap;

public class TableResource {

    private String name = null;
    private String display = null;
    private HashMap fields = null;

    public TableResource (String stablename, String stabledisplay, HashMap lstfields) {

        this.name = stablename;
        this.display = stabledisplay;
        this.fields = lstfields;
    }

    public String getName() {
        return name;
    }

    public String getDisplay() {
        return display;
    }

    public HashMap getFields() {
        return fields;
    }

    public String toString() {
        return "TableResource [ name=" + name + ", fields=" + fields + "]";
    }

}