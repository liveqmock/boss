package com.dtv.oss.web.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.web.filter.BookmarkedResource;
import com.dtv.oss.web.taglib.util.QueryPageProp;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * Implements the range of the page, and the way of 'order-by'
 * and push an bookmark into the bookmark pool for the current request.
 * @author Horace Lin
 * @version 1.0
 */

public abstract class QueryWebAction extends GeneralWebAction{
  /**
   * Avoiding too much data returned, QueryWebAction will check the length of resultset.
   * If the length of resultset is much more than MAX_PAGE_LENGTH, the system will truncate pageTo.
   */
    public final int MAX_PAGE_LENGTH = 500;
    /**
   * define the length of the bookmark pool
   */

    public final int BOOKMARK_HISTRORY_LENGTH = 6;

    protected int pageFrom = 1;
    protected int pageTo = 10;
    protected String orderByFieldName = null;
    protected boolean orderDescFlag = true;;

    protected String getResponseAttributeName()
    {
        return "ResponseQueryResult";
    }

    protected int getRecordsetSize(CommandResponse cmdResponse) {
        if (cmdResponse == null) return -1;

        Object objLoad=cmdResponse.getPayload();
        if (objLoad instanceof Collection)
        {
            Collection lstRep = (Collection) objLoad;
            return lstRep.size();
        }
        return -2;

    }

    protected void deleteOldBookmark(List lstBookmark) {
        if (lstBookmark==null) return;

        if (lstBookmark.size()>=BOOKMARK_HISTRORY_LENGTH)
        {
            //rule1, delete something which are existed over twice
            Map mapAction = new HashMap();
            for (int i=(lstBookmark.size()-1); i>=0; i--)
            {
                BookmarkedResource tmpbook = (BookmarkedResource)lstBookmark.get(i);
                if (mapAction.get(tmpbook.getAction())!=null) lstBookmark.remove(i);
                 else mapAction.put(tmpbook.getAction(), tmpbook.getAction());
            }

            if (lstBookmark.size()>=BOOKMARK_HISTRORY_LENGTH)
            {
                //rule2, delete something which are visited earlier
                for (int i=(lstBookmark.size()-BOOKMARK_HISTRORY_LENGTH); i>=0; i--)
                   lstBookmark.remove(i);
            }

        }

    }

    protected void updateBookmarkPool(HttpServletRequest request) {
        if (request == null) return;

        List lstBookmark = (List)request.getSession().getAttribute(WebKeys.FILTER_BOOKMARK);
        if (lstBookmark==null)
        {
            lstBookmark = new ArrayList();
        }
        else deleteOldBookmark(lstBookmark);

        String actionName = null;
        String selectedUrl = request.getRequestURI();



        // get the screen name
        int lastPathSeparator = selectedUrl.lastIndexOf("/") ;

        if (lastPathSeparator != -1)
            actionName = selectedUrl.substring(lastPathSeparator + 1);
        else
            actionName = selectedUrl;

        BookmarkedResource booked =new BookmarkedResource(actionName, request.getParameterMap());
        lstBookmark.add(booked);

        for (int i=0; i<lstBookmark.size(); i++)
        {
          BookmarkedResource tmpbooked = (BookmarkedResource)lstBookmark.get(i);
          System.out.println(tmpbooked.getAction());
        }

        request.getSession().setAttribute(WebKeys.FILTER_BOOKMARK, lstBookmark);
        return;

    }

    public void doStart(HttpServletRequest request)
    {
        String strFrom,strTo,strPage;
        int page=0;

        super.doStart(request);

        //order by
        orderByFieldName = request.getParameter("selOrder");

        if (WebUtil.StringHaveContent(request.getParameter("chkOrderDesc"))) orderDescFlag=true;
          else orderDescFlag=false;

        //from and to
        strFrom = request.getParameter("txtFrom");
        strTo = request.getParameter("txtTo");
        strPage = request.getParameter("txtPage");

        if (!WebUtil.StringHaveContent(strFrom)) strFrom="1";

        if (!WebUtil.StringHaveContent(strTo)) strTo="1000";

        if (WebUtil.StringHaveContent(strPage)) page = WebUtil.StringToInt(strPage);

        pageFrom = Integer.valueOf(strFrom.trim()).intValue();
        pageTo = Integer.valueOf(strTo).intValue();

        //if page>0, strfrom and strTo just denote page size. So it will adjust the value of pageFrom and the value of pageTo
        if (page>0)
        {
            int pageSize = pageTo - pageFrom + 1;
            if (pageSize<=0) pageSize = 10;
            pageFrom = (page - 1) * pageSize + 1;
            pageTo = page * pageSize;
        }

        //If the length of resultset is much more than MAX_PAGE_LENGTH, the system will truncate pageTo.
        if ((pageTo-pageFrom)>=MAX_PAGE_LENGTH)  pageTo =pageFrom + MAX_PAGE_LENGTH - 1;

    }

    protected boolean NeedProcessResultOneCodition()
    {
        return false;
    }

    protected void ProcessResultOneCodition(HttpServletRequest request, CommandResponse cmdResponse, Object CurObj)
    {}

    protected void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse)
    {
        if (cmdResponse == null) return;

        updateBookmarkPool(request);

        if (cmdResponse instanceof QueryCommandResponseImpl)
        {
            QueryCommandResponseImpl qryResponse = (QueryCommandResponseImpl)cmdResponse;

            int iCurResultSetSize = -1;

            Object objLoad= cmdResponse.getPayload();
            Collection lstRep = null;

            if (objLoad instanceof Collection)
            {
                lstRep = (Collection) objLoad;
                iCurResultSetSize = lstRep.size();
            }
            else WebPrint.PrintDebugInfo(this.getClass().getName(), "getPayload() is not a collection");

            //iCurResultSetSize 说明无法获取返回结果集的大小
            request.setAttribute(WebKeys.CURRENT_RESULTSET_PROPERTY,
                                 new QueryPageProp(qryResponse.getFrom(),
                                                   qryResponse.getTo() - qryResponse.getFrom() + 1,
                                                   qryResponse.getSize(),
                                                   iCurResultSetSize)
                                 );

            if ((iCurResultSetSize==1)&&NeedProcessResultOneCodition())
            {
                 Object obj=lstRep.iterator().next();
                 ProcessResultOneCodition(request, cmdResponse, obj);
            }
        }


    }

}