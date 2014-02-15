package com.dtv.oss.web.taglib.bookmark;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import java.util.*;
import javax.servlet.jsp.JspException;

import com.dtv.oss.web.taglib.util.RequestUtils;
import com.dtv.oss.web.taglib.logic.BaseCompareTag;
import com.dtv.oss.web.util.*;
import com.dtv.oss.web.filter.BookmarkedResource;

public class CanBackTag extends BaseCompareTag {
  private String url = null;

  public String getUrl(){
          return url;

  }
  public void setUrl(String pUrl){
          url = pUrl;
  }

  protected boolean condition() throws JspException {
        if (url == null) return false;

        String[] lstPossibleEvents = url.split("/");
        Map mapPossibleEvents = new HashMap();
        for (int i = 0; i < lstPossibleEvents.length; i++)
           mapPossibleEvents.put(lstPossibleEvents[i], lstPossibleEvents[i]);

        //checking whether the page which is to be back is expired
        //while the backing page is alternative, the nearest page in the list of Bookmark is chosen.
        List lstBookmark = (List)pageContext.getSession().getAttribute(WebKeys.FILTER_BOOKMARK);
        if (lstBookmark ==null) return false;
        for (int i=(lstBookmark.size()-1); i>=0; i--)
        {
            BookmarkedResource booked = (BookmarkedResource)lstBookmark.get(i);
            if (mapPossibleEvents.get(booked.getAction())!=null)
            {
                    return true;
            }
        }

        return false;
  }

      public void release() {

          super.release();
          url = null;

      }

}