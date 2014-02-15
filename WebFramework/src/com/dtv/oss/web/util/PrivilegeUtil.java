package com.dtv.oss.web.util;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import java.util.*;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.filter.ProtectedResource;

public class PrivilegeUtil {
  /**
   * @param strUrlPattern  xxx.do, xxx.screen or Priv_XXXX:Y
   * @return: return true when it is defined in ProtectedResource
   */
    public static boolean isProtectedResource(HttpSession session, String strUrlPattern) {
        if ((session == null)||(strUrlPattern==null)) return true;

        //all privilege list defined in signon-config-p4.xml
        Map mapPriDefined = (Map)session.getServletContext().getAttribute(WebKeys.FILTER_PRIVILEGE);
        if (mapPriDefined == null) return false;

        ProtectedResource checkedPri = (ProtectedResource) mapPriDefined.get(strUrlPattern);
        if (checkedPri != null) return true;

        return false;
    }

    public static boolean isProtectedResource(HttpServletRequest request, String strUrlPattern) {
        if (request==null) return false;
        return isProtectedResource(request.getSession(), strUrlPattern);
    }

  /**
   * @param lstUrlPattern  xxx.do, xxx.screen or Priv_XXXX:Y
   * while putting multiple UrlPatterns into lstUrlPattern together, use "/" to seperate it
   * @return: return true when more than one of multiple UrlPatterns are owned by the current operator
   */
    public static boolean isAuthorized(HttpSession session, String lstUrlPattern) {
        if ((session == null)||(lstUrlPattern==null)) return false;

        //all privilege list defined in signon-config-p4.xml
        Map mapPriDefined = (Map)session.getServletContext().getAttribute(WebKeys.FILTER_PRIVILEGE);
        if (mapPriDefined == null)
        {
          WebPrint.PrintErrorInfo("com.dtv.oss.web.util.PrivilegeUtil", "the list of privilege is not set!");
          return false;
        }

        //current operator's privilege list
        Map mapCurPrivilege = (Map) session.getAttribute(WebKeys.PRIVILEGE_SESSION_NAME);
        if (mapCurPrivilege == null)
        {
          WebPrint.PrintErrorInfo("com.dtv.oss.web.util.PrivilegeUtil",
              "did not find the list of privilege of the current operator");
          return false;
        }

        String[] lstPossibleUrlPattern = lstUrlPattern.split("/");
        for (int i = 0; i < lstPossibleUrlPattern.length; i++)
        {
          ProtectedResource checkedPri = (ProtectedResource) mapPriDefined.get(lstPossibleUrlPattern[i]);

          if (checkedPri != null)
          {
            ArrayList listRole = checkedPri.getRoles();
            //notice: Role is not defined, need not check concrete privileges
            if ((listRole == null) || (listRole.size() <= 0)) return true;

            for (int j = 0; j < listRole.size(); j++)
            {
              Object strRole = listRole.get(j);
              Object strPriv = mapCurPrivilege.get(strRole);
              if (strPriv != null) return true;
            }
          }
        }

        return false;
    }

    public static boolean isAuthorized(HttpServletRequest request, String lstUrlPattern) {
        if (request==null) return false;
        return isAuthorized(request.getSession(), lstUrlPattern);
    }


}