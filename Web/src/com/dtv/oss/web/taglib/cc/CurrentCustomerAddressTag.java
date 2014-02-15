package com.dtv.oss.web.taglib.cc;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
import com.dtv.oss.web.taglib.util.ResponseUtils;

import com.dtv.oss.util.Postern;
import com.dtv.oss.dto.AddressDTO;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.CurrentCustomer;

public class CurrentCustomerAddressTag extends BodyTagSupport{
      private String property = null;

      public String getProperty(){
              return property;
      }
      public void setProperty(String pProperty){
              property = pProperty;
      }

      private String typeName = null;

      public String getTypeName(){
              return typeName;
      }
      public void setTypeName(String pTypeName){
              typeName = pTypeName;
      }


      private String style=null;
      public String getStyle(){
              return style;
      }
      public void setStyle(String pStyle){
              style = pStyle;
      }

      public int doStartTag() throws JspException {
          AddressDTO dtoAddress = CurrentCustomer.getCurrentCustomerAddressDTO(pageContext.getSession());
          if (dtoAddress==null)
          {
              WebPrint.PrintErrorInfo(this.getClass().getName(), "current customer address dto is null");
              return SKIP_BODY;
          }

          String value = WebUtil.excuteMethodReturnString(dtoAddress, WebUtil.Name2GetMethod(property));
          if (value==null)
          {
              WebPrint.PrintErrorInfo(this.getClass().getName(), "can not get property --"+property);
              return SKIP_BODY;
          }

          if (WebUtil.StringHaveContent(typeName))
          {
              if (value instanceof String)
              {
                  String strVal = (String)value;
                  value = Postern.getHashValueByNameKey(typeName, strVal);
              }
              else
                WebPrint.PrintErrorInfo(this.getClass().getName(), "the value of typeName --"+typeName+" is invalid");
          }

          if (WebUtil.StringHaveContent(style))
          {
              if (value instanceof String)
              {
                  String strVal = (String)value;

                  //bit0=1 denotes chars of return which will be erased
                  if ( (WebUtil.StringToInt(style) & 1) != 0)
                    value = WebUtil.RemoveAwayReturn(strVal);
              }
              else
                WebPrint.PrintErrorInfo(this.getClass().getName(), "the value of style --"+style+" is invalid");

          }

          ResponseUtils.write(pageContext, value);

          return (SKIP_BODY);
      }


      public void release() {

          super.release();
          typeName = null;
          property = null;
          style=null;
      }

}