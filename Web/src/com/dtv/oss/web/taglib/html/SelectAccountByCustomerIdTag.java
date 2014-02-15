package com.dtv.oss.web.taglib.html;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import javax.servlet.jsp.JspException;
import java.util.*;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.CurrentCustomer;
import com.dtv.oss.web.taglib.html.SelectTag;

public class SelectAccountByCustomerIdTag extends SelectTag {
        protected String mapName = null;
        protected boolean canDirect=true;	  
        public String getMapName() {

            return mapName;
        }

        public void setCanDirect(boolean pcanDirect) {
        	canDirect = pcanDirect;
        }
        public boolean getCanDirect() {

            return canDirect;
        }

        public void setMapName(String pMapName) {
            mapName = pMapName;
        }
        public int doStartTag() throws JspException {
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");
            if (mapName!=null)
            {
                String sPgName="";
                if (super.set!=null) sPgName=super.set;
                else
                {
                    sPgName=mapName+"_forStore_100";
                    setSet(sPgName);
                }
                Map map=null;
                if(!canDirect){
                	map=Postern.getAccountDataCache(mapName);
                }else{
                	String customerid=String.valueOf(CurrentCustomer.getCurrentCustomerDTO(pageContext.getSession()).getCustomerID());
                	 
                    map=Postern.getAccountDataCache(customerid);
                }
                
                if (map!=null){
                	Map matchMapOfAccount=new LinkedHashMap();
                	Set sMapKey=map.keySet();
                	Iterator itMapKey=sMapKey.iterator();
                	while(itMapKey.hasNext()){
                		String strKey=(String)itMapKey.next();
                	//	String strValue=map.get(strKey).toString() +" £º" + strKey;
                		String strValue=strKey+"£º"+map.get(strKey).toString();
                		matchMapOfAccount.put(strKey,strValue);
                	}
                	pageContext.setAttribute(sPgName, matchMapOfAccount);
                }

            }

            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

            return super.doStartTag();

        }

    /**
    * Release any acquired resources.
    */
    public void release() {

        super.release();
        mapName = null;

    }


}