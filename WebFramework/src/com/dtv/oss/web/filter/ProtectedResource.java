

package com.dtv.oss.web.filter;


import java.util.ArrayList;

/**
 * This class is an object representation of a protected resource

 */

public class ProtectedResource implements java.io.Serializable {

    //private String name = null;
    private String urlPattern = null;
    private ArrayList roles = null;

    public ProtectedResource (String urlPattern, ArrayList roles) {
        //this.name = name;
        this.urlPattern = urlPattern;
        this.roles = roles;
    }

    /*public String getName() {
        return name;
    }
    */
    public String getURLPattern() {
        return urlPattern;
    }

    public ArrayList getRoles() {
        return roles;
    }

    public String toString() {
        return "ProtectedResource [ urlPattern=" + urlPattern + ", roles=" + roles + "]";
    }
}
