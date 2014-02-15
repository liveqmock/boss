/**
 * FinancialSettingCommand.java
 */
package com.dtv.oss.service.command.config;

import java.util.HashMap;
import java.util.Map;

import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.dto.CustAcctCycleCfgDTO;
import com.dtv.oss.dto.FinancialSettingDTO;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.FinancialConfigAccountCycleTypeService;
import com.dtv.oss.service.component.FinancialConfigAccountTypeService;
import com.dtv.oss.service.component.FinancialConfigChargeCycleTypeService;
import com.dtv.oss.service.component.FinancialConfigCustomerAccountCycleService;
import com.dtv.oss.service.component.FinancialConfigGeneralSettingService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.FinancialSettingEJBEvent;

/**
 * @author 260425w 2006-5-30 17:21:39
 */
public class ConfigFinancialSettingCommand extends Command {
	private static final Map serviceMap = new HashMap();

	private String remoteHostAddress;
	CommandResponseImp response = null;
	private int operatorID;
	

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		remoteHostAddress = ev.getRemoteHostAddress();
		operatorID = ev.getOperatorID();
		if (ev == null || !(ev instanceof FinancialSettingEJBEvent)) {
			return null;
		}
		FinancialSettingEJBEvent fsEvent = (FinancialSettingEJBEvent) ev;
		int actionType = fsEvent.getActionType();
		 
		 
	try {
		switch (actionType) {
		    case FinancialSettingEJBEvent.UPDATE_GENERAL_SETTING:  
			updateGeneralSetting(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.NEW_CHARGE_CYCLE_TYPE: 
			createChargeCycleType(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.UPDATE_CHARGE_CYCLE_TYPE:  
			updateChargeCycleType(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.DELETE_CHARGE_CYCLE_TYPE:  
			deleteChargeCycleType(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.NEW_ACCOUNT_CYCLE_TYPE:  
			createAccountCycleType(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.UPDATE_ACCOUNT_CYCLE_TYPE:  
			updateAccountCycleType(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.DELETE_ACCOUNT_CYCLE_TYPE:  
			deleteAccountCycleType(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.NEW_CUSTOMER_ACCOUNT_CYCLE:  
			createCustomerAccountCycle(fsEvent);
			break;
		  
		case FinancialSettingEJBEvent.UPDATE_CUSTOMER_ACCOUNT_CYCLE:  
			updateCustomerAccountCycle(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.DELETE_CUSTOMER_ACCOUNT_CYCLE:  
			deleteCustomerAccountCycle(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.NEW_ACCOUNT_TYPE:  
			createAccountType(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.UPDATE_ACCOUNT_TYPE:  
			updateAccountType(fsEvent);
			break;
		 
		case FinancialSettingEJBEvent.DELETE_ACCOUNT_TYPE:  
			deleteAccountType(fsEvent);
			 break;
		 
          default :
        	break;
		}
		}catch(ServiceException ce){
	       
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		   
		    throw new CommandException("Î´Öª´íÎó¡£");
		}
		return response;
	}

	private FinancialConfigGeneralSettingService getFinancialConfigGeneralSettingService(){
		FinancialConfigGeneralSettingService service = (FinancialConfigGeneralSettingService) serviceMap
				.get("generalSettingService");
		if (service == null) {
			service = new FinancialConfigGeneralSettingService(
					remoteHostAddress, operatorID);
			serviceMap.put("generalSettingService", service);
		}

		return service;
	}

	private FinancialConfigChargeCycleTypeService getFinancialConfigChargeCycleTypeService() {
		FinancialConfigChargeCycleTypeService service = (FinancialConfigChargeCycleTypeService) serviceMap
				.get("FinancialConfigChargeCycleService");
		if (service == null) {
			service = new FinancialConfigChargeCycleTypeService(
					remoteHostAddress, operatorID);
			serviceMap.put("FinancialConfigChargeCycleService", service);
		}

		return service;
	}

	private FinancialConfigAccountCycleTypeService getFinancialConfigAccountCycleTypeService() {
		FinancialConfigAccountCycleTypeService service = (FinancialConfigAccountCycleTypeService) serviceMap
				.get("FinancialConfigAccountCycleTypeService");
		if (service == null) {
			service = new FinancialConfigAccountCycleTypeService(
					remoteHostAddress, operatorID);
			serviceMap.put("FinancialConfigAccountCycleTypeService", service);
		}

		return service;
	}

	private FinancialConfigCustomerAccountCycleService getFinancialConfigCustomerAccountCycleService() {
		FinancialConfigCustomerAccountCycleService service = (FinancialConfigCustomerAccountCycleService) serviceMap
				.get("FinancialConfigCustomerAccountCycleService");
		if (service == null) {
			service = new FinancialConfigCustomerAccountCycleService(
					remoteHostAddress, operatorID);
			serviceMap.put("FinancialConfigCustomerAccountCycleService",
					service);
		}

		return service;
	}

	private FinancialConfigAccountTypeService getFinancialConfigAccountTypeService() {
		FinancialConfigAccountTypeService service = (FinancialConfigAccountTypeService) serviceMap
				.get("FinancialConfigAccountTypeService");
		if (service == null) {
			service = new FinancialConfigAccountTypeService(
					remoteHostAddress, operatorID);
			serviceMap.put("FinancialConfigAccountTypeService", service);
		}

		return service;
	}

	private void updateGeneralSetting(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null)  
				return;
			 
			FinancialSettingDTO dto = (FinancialSettingDTO) event.getDto();
			getFinancialConfigGeneralSettingService().updateObject(dto);
		 
	}

	private void createAccountType(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null)  
				return;
			 
			AcctItemTypeDTO dto = (AcctItemTypeDTO) event.getDto();
			getFinancialConfigAccountTypeService().createObject(dto);
		 
	}

	private void updateAccountType(FinancialSettingEJBEvent event)
			throws ServiceException {
	 
			if (event.getDto() == null)  
				return;
			 
			AcctItemTypeDTO dto = (AcctItemTypeDTO) event.getDto();
			getFinancialConfigAccountTypeService().updateObject(dto);
		 
	}

	private void deleteAccountType(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null) 
				return;
			 
			AcctItemTypeDTO dto = (AcctItemTypeDTO) event.getDto();
			getFinancialConfigAccountTypeService().deleteObject(dto);
		 
	}

	private void createAccountCycleType(FinancialSettingEJBEvent event)
			throws ServiceException {
	 
			if (event.getDto() == null)  
				return;
			 
			BillingCycleTypeDTO dto = (BillingCycleTypeDTO) event.getDto();
			getFinancialConfigAccountCycleTypeService().createObject(dto);
	 
	}

	private void updateAccountCycleType(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null)  
				return;
		 
			BillingCycleTypeDTO dto = (BillingCycleTypeDTO) event.getDto();
			getFinancialConfigAccountCycleTypeService().updateObject(dto);
		 
	}

	private void deleteAccountCycleType(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null) 
				return;
			 
			BillingCycleTypeDTO dto = (BillingCycleTypeDTO) event.getDto();
			getFinancialConfigAccountCycleTypeService().deleteObject(dto);
		 
	}

	private void createChargeCycleType(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null) 
				return;
			 
			BillingCycleTypeDTO dto = (BillingCycleTypeDTO) event.getDto();
			getFinancialConfigChargeCycleTypeService().createObject(dto);
		 
	}

	private void updateChargeCycleType(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null)  
				return;
			 
			BillingCycleTypeDTO dto = (BillingCycleTypeDTO) event.getDto();
			getFinancialConfigChargeCycleTypeService().updateObject(dto);
		 
	}

	private void deleteChargeCycleType(FinancialSettingEJBEvent event)
			throws ServiceException {
	 
			if (event.getDto() == null) 
				return;
			 
			BillingCycleTypeDTO dto = (BillingCycleTypeDTO) event.getDto();
			getFinancialConfigChargeCycleTypeService().deleteObject(dto);
		 
	}

	private void createCustomerAccountCycle(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null)  
				return;
		 
			CustAcctCycleCfgDTO dto = (CustAcctCycleCfgDTO) event.getDto();
			getFinancialConfigCustomerAccountCycleService().createObject(dto);
		 
	}

	private void updateCustomerAccountCycle(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null)  
				return;
		 
			CustAcctCycleCfgDTO dto = (CustAcctCycleCfgDTO) event.getDto();
			getFinancialConfigCustomerAccountCycleService().updateObject(dto);
		 
	}

	private void deleteCustomerAccountCycle(FinancialSettingEJBEvent event)
			throws ServiceException {
		 
			if (event.getDto() == null)  
				return;
			 
			CustAcctCycleCfgDTO dto = (CustAcctCycleCfgDTO) event.getDto();
			getFinancialConfigCustomerAccountCycleService().deleteObject(dto);
		 
	}
}
