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

public class CurrentOperator implements java.io.Serializable{
    public static WebCurrentOperatorData GetCurrentOperator(HttpSession session)
    {
        WebCurrentOperatorData dtoOp = null;

        try
        {
            dtoOp = (WebCurrentOperatorData)session.getAttribute(WebKeys.OPERATOR_SESSION_NAME);
        }
        catch(Exception e)
        {
            System.err.println("CurrentOperatorInfo : can not get current operator dto in the relevant request");
            return null;
        }

        return dtoOp;

    }

    public static WebCurrentOperatorData GetCurrentOperator(HttpServletRequest request)
    {
        return GetCurrentOperator(request.getSession());
    }

    public static int GetCurrentOperatorID(HttpServletRequest request)
    {
        WebCurrentOperatorData dtoOp = GetCurrentOperator(request);
        if (dtoOp==null) return 0;
         else return dtoOp.GetCurrentOperatorID();
    }

    public static Map GetCurrentOperatorPrivilege(HttpSession session)
    {
        Map map = null;

        try
        {
            map = (Map)session.getAttribute(WebKeys.PRIVILEGE_SESSION_NAME);
        }
        catch(Exception e)
        {
            System.err.println("CurrentOperator : can not get current operator privilege in the relevant request");
            return null;
        }

        return map;

    }

    public static Map GetCurrentOperatorPrivilege(HttpServletRequest request)
    {
        return GetCurrentOperatorPrivilege(request.getSession());
    }


}