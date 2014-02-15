package com.dtv.oss.service.command.system;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.system.BillBoardListHandler;
import com.dtv.oss.service.listhandler.system.CustCampaignAllListHandler;
import com.dtv.oss.service.listhandler.system.CustCampaignMapListHandler;
import com.dtv.oss.service.listhandler.system.CustCampaignListHandler;
import com.dtv.oss.service.listhandler.system.FutureRightListHandler;

public class SystemQueryCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch (event.getType()) {
		    case SystemQueryEJBEvent.QUERY_CUSTOMER_CAMPAIGN:
         	    handler=new CustCampaignListHandler();
         	    break;
		    case SystemQueryEJBEvent.QUERY_CUSTOMER_CAMPAIGN_MAP:
	            handler=new CustCampaignMapListHandler();
	            break;
		    case SystemQueryEJBEvent.QUERY_CUSTOMER_BUNDLE:
         	    handler=new CustCampaignListHandler();
         	    break;
		    case SystemQueryEJBEvent.QUERY_CUSTOMER_BUNDLE_MAP:
         	    handler=new CustCampaignMapListHandler();
         	    break;
		    case SystemQueryEJBEvent.QUERY_FUTURERIGHT:
            	handler = new FutureRightListHandler();
            	break; 
		    case SystemQueryEJBEvent.QUERY_BILLBOARD:
		    	handler = new BillBoardListHandler();
		    	break;
	        case CsrQueryEJBEvent.QUERY_CAMPAIGN_LIST:
	        	handler=new CustCampaignAllListHandler();
	        	break;		    	
		    default :
				break;
		}

		return handler;
	}
}
