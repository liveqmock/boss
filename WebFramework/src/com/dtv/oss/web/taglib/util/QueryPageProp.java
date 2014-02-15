package com.dtv.oss.web.taglib.util;


import java.io.IOException;
import javax.servlet.jsp.PageContext;


public class QueryPageProp implements java.io.Serializable{
        private int from=1;
        private int pageSize=10;
        private int recordCount=0;
        private int curPageRecordCount=0;

        public QueryPageProp () {}

        public QueryPageProp (int pFrom, int pPageSize, int pRecordCount, int pCurPageRecordCount)
        {
            from = pFrom;
            pageSize = pPageSize;
            recordCount = pRecordCount;
            curPageRecordCount = pCurPageRecordCount;
        }

        public int getFrom()
        {
            return from;
        }
        public void setFrom(int pFrom)
        {
            from = pFrom;
        }

        public int getPageSize()
        {
            return pageSize;
        }
        public void setPageSize(int pPageSize)
        {
            pageSize = pPageSize;
        }

        public int getRecordCount()
        {
            return recordCount;
        }
        public void setRecordCount(int pRecordCount)
        {
            recordCount = pRecordCount;
        }

        public int getCurPageRecordCount()
        {
            return curPageRecordCount;
        }
        public void setCurPageRecordCount(int pCurPageRecordCount)
        {
            curPageRecordCount = pCurPageRecordCount;
        }

        public int getTo()
        {
            return from+pageSize-1;
        }

        public int getFirstPageFrom()
        {
            return 1;
        }
        public int getFirstPageTo()
        {
            return pageSize;
        }

        public int getPreviousPageFrom()
        {
            if ((from-pageSize)>0)
              return from-pageSize;
            else
              return 1;
        }
        public int getPreviousPageTo()
        {
            if (from>1) return from-1;
            else return 1;
        }

        public int getNextPageFrom()
        {
            if ((from+pageSize)>recordCount)
              return from;
            else
              return from+pageSize;
        }
        public int getNextPageTo()
        {
            if ((from+pageSize)>recordCount)
              return  from+pageSize-1;
            else return from+pageSize+pageSize-1;
        }

        public int getLastPageFrom()
        {
            return (getPageAmount()-1)*pageSize+1;
        }
        public int getLastPageTo()
        {
            return getPageAmount()*pageSize;
        }

        public boolean isFirstPage()
        {
            if (from>1) return false;
            else return true;
        }

        public boolean isLastPage()
        {
            if ((from+pageSize)>recordCount) return true;
            else return false;
        }

        public int getPageAmount()
        {
            return (recordCount-1)/pageSize+1;
        }

        public int getCurPageNo()
        {
            return from/pageSize+1;
        }


}