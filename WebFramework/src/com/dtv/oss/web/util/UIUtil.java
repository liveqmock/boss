package com.dtv.oss.web.util;

import javax.servlet.http.HttpSession;

public class UIUtil {
    public static boolean equalSessionAttribute(HttpSession session, String attrName, String sMatch)
    {
        if (session == null) return false;

        if (session.getAttribute(attrName)==null) return false;

        if (session.getAttribute(attrName).equals(sMatch)) return true;

        return false;
    }

    public static boolean indexOfSessionAttribute(HttpSession session, String attrName, String sMatch)
    {
        if (session == null) return false;
        Object attr = session.getAttribute(attrName);
        if (attr==null) return false;

        if (attr instanceof String)
        {
            if (((String)attr).indexOf(sMatch)>=0) return true;
        }

        return false;
    }

}