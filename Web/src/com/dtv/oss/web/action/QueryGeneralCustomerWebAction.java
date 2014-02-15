package com.dtv.oss.web.action;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CurrentCustomer;

public abstract class QueryGeneralCustomerWebAction extends DownloadWebAction{


  protected int getCurrentCustomerID(Object obj){
      //if return 0,
      if (obj==null) return 0;
      Integer iID = null;

      try
      {
          iID = (Integer)WebUtil.excuteMethod(obj, WebUtil.Name2GetMethod("CustomerID"));
      }
      catch(Exception e)
      {
          WebPrint.PrintErrorInfo(this.getClass().getName(),"getCustomerID exception: "+e.getMessage());
      }

      if (iID==null) return 0;
       else return iID.intValue();

  }

  protected boolean NeedProcessResultOneCodition()
  {
      return true;
  }

  protected boolean NeedUpdateCurrentCustomer()
  {
      return true;
  }

  protected void ProcessResultOneCodition(HttpServletRequest request, CommandResponse cmdResponse, Object CurObj)
  {
      if (!NeedUpdateCurrentCustomer()) return;

      WebPrint.PrintDebugInfo(this.getClass().getName(), "begin getting current customer id...");

      if (CurObj==null)
      {
          WebPrint.PrintErrorInfo(this.getClass().getName(), "ProcessResultOneCodition need an object");
          return;
      }

      //get customer id and then set this customer as the current cusomter
      int iCustomerID=0;

      try
      {
          iCustomerID = getCurrentCustomerID(CurObj);
      }catch(Exception e)
      {
          //normal process for unknown exception
          WebPrint.PrintErrorInfo(this.getClass().getName(), e.getMessage());
          return;
      }

      if (iCustomerID!=0)
      {
          CurrentCustomer.UpdateCurrentCustomer(request, iCustomerID);
          WebPrint.PrintDebugInfo(this.getClass().getName(), "Set Current Customer = "+iCustomerID);
      }

  }




}