package com.dtv.oss.service.command.csr;

import java.util.ArrayList;

import javax.ejb.CreateException;

import com.dtv.oss.dto.CpConfigedPropertyDTO;
import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CpConfigedPropertyEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.SystemEventRecorder;


/**
 * 客户产品相关的操作
 * author     ：zhouxushun
 * date       : 2005-11-14
 * description:
 * @author 250713z
 *
 */
public class CpConfigedPropertyCommand extends Command {
	private static final Class clazz = CpConfigedPropertyCommand.class;
	private int operatorID = 0;
	private String machineName = "";
	CommandResponseImp response = null;
	ServiceContext serviceContext=null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CpConfigedPropertyEJBEvent inEvent = (CpConfigedPropertyEJBEvent)ev;
		this.operatorID = inEvent.getOperatorID();
		this.machineName = inEvent.getRemoteHostAddress();
		serviceContext=initServiceContext(inEvent);
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");

	    
	    try {
		    SystemEventDTO sysDto=inEvent.getSystemEventDto();

	    	switch (inEvent.getActionType()) {
	    		case CpConfigedPropertyEJBEvent.CPCONFIGEDPROPERTY_ADD:
	    			this.addCpConfigedProperty(inEvent.getDto());
	    			break;
	    		case CpConfigedPropertyEJBEvent.CPCONFIGEDPROPERTY_DELETE:
	    			this.deleteCpConfigedProperty(inEvent.getDto());
	    			break;
				case CpConfigedPropertyEJBEvent.CPCONFIGEDPROPERTY_UPDATE:
					this.updateCpConfigedPropertys(inEvent);
					break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
			sysDto.setEventClass(SystemEventRecorder.PRODUCT_PROPERTY_UPDATE);
		    sysDto.setOperatorID(operatorID);
		    addSystemEvent(sysDto);
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}


	private void addSystemEvent(SystemEventDTO dto) throws HomeFactoryException, CreateException{
		SystemEventRecorder.addEvent4CpConfigedProperty(dto);
	}
	
	private void addCpConfigedProperty(CpConfigedPropertyDTO dto) throws ServiceException{
		CPConfigedPropertyService service=new CPConfigedPropertyService(serviceContext);
	    service.propertyValueAdd(dto);
	}
	
	private void deleteCpConfigedPropertys(CpConfigedPropertyEJBEvent inEvent) throws ServiceException{
		ArrayList dtoList=inEvent.getDtoList();
		for(int i=0;i<dtoList.size();i++){
			CpConfigedPropertyDTO dto=(CpConfigedPropertyDTO) dtoList.get(i);
			deleteCpConfigedProperty(dto);
		}
	}
	
	private void updateCpConfigedPropertys(CpConfigedPropertyEJBEvent inEvent) throws ServiceException{
		ArrayList dtoList=inEvent.getDtoList();
		for(int i=0;i<dtoList.size();i++){
			CpConfigedPropertyDTO dto=(CpConfigedPropertyDTO) dtoList.get(i);
			LogUtility.log(clazz,LogLevel.DEBUG,"修改的DTO:"+dto);
			updateCpConfigedProperty(dto,inEvent.getSystemEventDto().getProductID());
		}
	}

	private void deleteCpConfigedProperty(CpConfigedPropertyDTO dto)throws ServiceException {
		CPConfigedPropertyService service=new CPConfigedPropertyService(serviceContext);
		service.propertyValueDelete(dto);
	}
	private void updateCpConfigedProperty(CpConfigedPropertyDTO dto,int productID)throws ServiceException {
		CPConfigedPropertyService service=new CPConfigedPropertyService(serviceContext);
		service.propertyValueModify(dto,productID);
	}
	private ServiceContext initServiceContext(CpConfigedPropertyEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
/*		switch (event.getActionType()) {
			case EJBEvent:
				description="";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
		}*/
		serviceContext.put(Service.ACTION_DESCRTIPION,description);
		serviceContext.put(Service.CUSTOMER_ID,new Integer(event.getSystemEventDto().getCustomerID()));
	    serviceContext.put(Service.ACTION_DEFITION,action);
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}


}
