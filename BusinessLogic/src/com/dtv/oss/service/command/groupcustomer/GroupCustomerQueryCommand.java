/*
 * Created on 2006-11-17
 *
 * @author Chen jiang
 */
package com.dtv.oss.service.command.groupcustomer;
import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.QueryGroupCustomerEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.groupcustomer.ContractListHandler;
 
import com.dtv.oss.service.listhandler.groupcustomer.ContractProcessLogListHandler;
 
import com.dtv.oss.service.listhandler.groupcustomer.GroupCustomerProductListHandler;
import com.dtv.oss.service.listhandler.groupcustomer.GroupPakageListHandler;
 
public class GroupCustomerQueryCommand extends QueryCommand {
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		
		switch(event.getType()) {
		 
		 
	   
	        case QueryGroupCustomerEJBEvent.QUERY_CONTRACT:
	        	handler=new ContractListHandler();
	        	break;
 
	        case QueryGroupCustomerEJBEvent.QUERY_CONTRACT_LOG:
	        	handler=new ContractProcessLogListHandler();
	        	break; 	
 
	        case QueryGroupCustomerEJBEvent.QUERY_GROUP_PRODUCTLIST:
				return new GroupCustomerProductListHandler();
			case QueryGroupCustomerEJBEvent.QUERY_GROUP_PACKAGELIST:
				return new GroupPakageListHandler();	
 
	       
			default:
				break;
		}
		return handler;
	}
}
