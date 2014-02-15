package com.dtv.oss.web.util;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Iterator;

import com.dtv.oss.service.ejbevent.EJBEvent;

public class WebPrint {

    public static void PrintDebugInfo(String strModuleName, String strInfo) {
        if (WebKeys.DEBUG_ENVIRONMENT_FLAG)
          System.out.println("** "+strModuleName+" : "+ strInfo);
    }

    public static void PrintActionDebugInfo(String strModuleName, int action) {
        if (WebKeys.DEBUG_ENVIRONMENT_FLAG)
        {
            String strInfo = " action -- ";
            switch (action)
            {
              /*case EJBEvent.ACTIVE:
                strInfo +="Active";
                break;
               */
             /* case EJBEvent.CREATE:
                strInfo +="Create";
                break;*/
              /*
              case EJBEvent.D_RETURN:
                strInfo +="Return";
                break;
              case EJBEvent.DELETE:
                strInfo +="Delete";
                break;
              case EJBEvent.EXCHANGE:
                strInfo +="Exchange";
                break;
              case EJBEvent.TRANSFER:
                strInfo +="Transfer";
                break;
              case EJBEvent.JOBCARD_ASSIGN:
                strInfo +="Jobcard assign";
                break;
              case EJBEvent.JOBCARD_COMPLETE:
                strInfo +="Jobcard complete";
                break;
              case EJBEvent.LOGIN:
                strInfo +="Login";
                break;
              case EJBEvent.LOGOUT:
                strInfo +="Logout";
                break;
              case EJBEvent.ORDER:
                strInfo +="Order";
                break;
              case EJBEvent.PAYBYBILL:
                strInfo +="Pay bill";
                break;
              case EJBEvent.PAYRIGHTNOW:
                strInfo +="Pay right now";
                break;
              case EJBEvent.PRESAVE:
                strInfo +="Presave";
                break;
              case EJBEvent.PURCHASE:
                strInfo +="Puchase";
                break;
              case EJBEvent.RESUME:
                strInfo +="Resume";
                break;
              case EJBEvent.SEARCH:
                strInfo +="Search";
                break;
              case EJBEvent.SUSPEND:
                strInfo +="Suspend";
                break;
              case EJBEvent.UPDATE:
                strInfo +="Update";
                break;
              case EJBEvent.FAIL:
                strInfo +="Fail";
                break;
              case EJBEvent.CLOSE:
                strInfo +="Close";
                break;
              case EJBEvent.BATCHCOMPLETE:
                strInfo +="BatchComplete";
                break;
              case EJBEvent.BATCHUPDATEPROBLEMLEVEL:
                strInfo +="BatchUpdateProblemLevel";
                break;
              */
              default:
                strInfo +="Unknown";
            }
            PrintDebugInfo(strModuleName, strInfo);
        }
    }

    public static void PrintPrivilegeDebugInfo(String strInfo) {
        if (WebKeys.DEBUG_PRIVILEGE_FLAG)
          System.out.println("@@ Privilege : "+ strInfo);

    }

    public static void PrintTagDebugInfo(String strModuleName, String strInfo) {
        if (WebKeys.DEBUG_TAG_DEBUG_FLAG)
          System.out.println("## Tag : "+ strModuleName + "--" + strInfo);
    }

    public static void PrintErrorInfo(String strModuleName, String strInfo) {
        if (WebKeys.EXPORT_ERROR_FLAG)
          System.out.println("!Error "+strModuleName+" : "+ strInfo);
    }

    public static void SetRequestExceptionErrorInfo(String strModuleName, HttpServletRequest request, Throwable e) {
        /*2003-12-16
        request.setAttribute(WebKeys.PAGE_ERROR_CODE_REQUEST_ATTRIBUTE, new Integer(ErrorCode.SYSTEM_ERROR));
        if (e!=null)
        {
            request.setAttribute(WebKeys.PAGE_ERROR_MESSAGE_REQUEST_ATTRIBUTE, "未知错误:" + e.getMessage());
            PrintErrorInfo(strModuleName,"未知错误:" + e.getMessage());
        }
        else
        {
            request.setAttribute(WebKeys.PAGE_ERROR_MESSAGE_REQUEST_ATTRIBUTE, "错误原因不明。");
            PrintErrorInfo(strModuleName,"错误原因不明。");
        }*/
        if (e==null) return;

        request.setAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE, e);

        PrintErrorInfo(strModuleName,"错误:" + e.getClass().getName());
        PrintErrorInfo(strModuleName,"错误说明:" + e.getMessage());
    }

    public static void PrintRequestParameter(String strModuleName, HttpServletRequest request) {
        if (!WebKeys.WEBACTION_GET_PARAMETER_FLAG) return;
        if (request==null) return;

        System.out.println(" --"+strModuleName+": all parameters begin ...");

        try
        {
            Map mapParam = request.getParameterMap();
            Iterator itParam = mapParam.entrySet().iterator();
            while (itParam.hasNext())
            {
                Map.Entry itemParam = (Map.Entry) itParam.next();
                String strKey = (String) itemParam.getKey();
                String[] strValue = (String[]) itemParam.getValue();

                System.out.print("\t" + strKey);
                for (int i = 0; i < strValue.length; i++)
                  System.out.println("=" + strValue[i]);
            }
        }catch(Exception e) {//normal process for unknown exception
            PrintErrorInfo(strModuleName, "Unkown error--"+e.getMessage());
        }

        System.out.println(" --"+strModuleName+": all parameters end");
    }

}