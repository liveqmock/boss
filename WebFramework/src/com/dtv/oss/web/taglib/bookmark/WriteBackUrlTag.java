package com.dtv.oss.web.taglib.bookmark;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;

import javax.servlet.jsp.PageContext;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.filter.BookmarkedResource;

public class WriteBackUrlTag extends TagSupport{
  private String property = null;

  public String getProperty(){
          return property;

  }
  public void setProperty(String pProperty){
          property = pProperty;
  }

      public int doStartTag() throws JspTagException {

          if (property == null) {
              WebPrint.PrintErrorInfo(this.getClass().getName(),
                                  "doStartTag error : without setting property ");
              return SKIP_BODY;
          }

          String[] lstPossibleEvents = property.split("/");
          String strSuitedEvent = null;


          Map mapPossibleEvents = new HashMap();
          for (int i = 0; i < lstPossibleEvents.length; i++)
             mapPossibleEvents.put(lstPossibleEvents[i], lstPossibleEvents[i]);

          //checking whether the page which is to be back is expired
          //while the backing page is alternative, the nearest page in the list of Bookmark is chosen.
          List lstBookmark = (List)pageContext.getSession().getAttribute(WebKeys.FILTER_BOOKMARK);
          //6-27侯改,加了下面一行.
          if(lstBookmark==null)lstBookmark=new ArrayList();
          for (int i=(lstBookmark.size()-1); i>=0; i--)
          {
              BookmarkedResource booked = (BookmarkedResource)lstBookmark.get(i);
              if (mapPossibleEvents.get(booked.getAction())!=null)
              {
                      strSuitedEvent = booked.getAction();
                      break;
              }
          }

          if ((strSuitedEvent==null)||strSuitedEvent.equals(""))
          {
              WebPrint.PrintErrorInfo(this.getClass().getName(),
                              "doStartTag error : Invalid property : "+property);
              return SKIP_BODY;

          }

          try
          {
                ResponseUtils.write(pageContext, "bookmark.back?backTo="+strSuitedEvent);

          }
          catch(Exception e)
          {
              WebPrint.PrintErrorInfo(this.getClass().getName(),  "doStartTag exception "+e.getMessage());
          }

          return SKIP_BODY;
      }

      public void release() {

          super.release();
          property = null;

      }


}