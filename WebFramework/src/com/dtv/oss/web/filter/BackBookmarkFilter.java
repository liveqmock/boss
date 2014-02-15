package com.dtv.oss.web.filter;

import java.io.IOException;
import java.util.*;

// J2EE imports
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.RequestDispatcher;


import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebPrint;

public class BackBookmarkFilter implements Filter{
    private FilterConfig config = null;

    public void init(FilterConfig config) throws ServletException {
        this.config = config;

    }

    public void destroy() {
        config = null;
    }

    protected int LocateBookmark(List lstBookmark, String strURL) {

        if (lstBookmark==null)
        {
            return -1;
        }

        for (int i=(lstBookmark.size()-1); i>=0; i--)
        {
            BookmarkedResource booked = (BookmarkedResource)lstBookmark.get(i);
            if (booked.getAction().equals(strURL)) return i;
        }

        return -1;

    }

    protected void RefreshBookmarkPool(List lstBookmark, int iBackPos) {

        if (lstBookmark==null) return;

        for (int i=(lstBookmark.size()-1); i>=iBackPos; i--)
        {
            lstBookmark.remove(i);
        }

    }

    public  void doFilter(ServletRequest request, ServletResponse  response, FilterChain chain)
        throws IOException, ServletException {

         HttpServletRequest hreq = (HttpServletRequest)request;
         HttpServletResponse hres = (HttpServletResponse)response;

         //WebPrint.PrintDebugInfo(this.getClass().getName(),"invoke doFilter...");

         List lstBookmark = (List)hreq.getSession().getAttribute(WebKeys.FILTER_BOOKMARK);
         String newURL = hreq.getParameter("backTo");

         int iPos = LocateBookmark(lstBookmark, newURL);
         if (iPos>=0)
         {
             BookmarkedResource book = (BookmarkedResource)lstBookmark.get(iPos);
             hreq = new ParamsModifiedRequest(hreq, book.getParameters());
             RefreshBookmarkPool(lstBookmark, iPos);
         }
         else 
         {
         	WebPrint.PrintErrorInfo(this.getClass().getName(),"can not locate :"+newURL);
         	newURL = "page_overpast.htm";
         }

         RequestDispatcher rd = config.getServletContext().getRequestDispatcher("/" +newURL);
         rd.forward(hreq, hres);
    }


    private class ParamsModifiedRequest extends HttpServletRequestWrapper
    {
        private Map params;

        public ParamsModifiedRequest(HttpServletRequest request, Map mapParameters)
        {
            super(request);
            params = new HashMap(mapParameters);
            params = Collections.unmodifiableMap(params);
        }

        public String getParameter(String name)
        {
            String[] values = getParameterValues(name);
            return (values != null) ? values[0] : null;
        }

        public Map getParameterMap()
        {
            return params;
        }

        public Enumeration getParameterNames()
        {
            return Collections.enumeration(params.keySet());
        }

        public String[] getParameterValues(String name)
        {
            return (String[])params.get(name);
        }
    }

}

