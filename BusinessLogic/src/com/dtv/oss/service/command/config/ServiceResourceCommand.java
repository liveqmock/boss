package com.dtv.oss.service.command.config;

import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.domain.ResourcePhoneNo;
import com.dtv.oss.domain.ResourcePhoneNoHome;
import com.dtv.oss.domain.ServiceResource;
import com.dtv.oss.domain.ServiceResourceHome;
import com.dtv.oss.dto.PhoneNoUseLogDTO;
import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.component.SystemConfigModifyServiceLoggerTool;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ServiceResourceEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.TimestampUtility;

public class ServiceResourceCommand extends Command {
	private SystemConfigModifyServiceLoggerTool loggerTool = SystemConfigModifyServiceLoggerTool
			.getInstance(null, null, 0);

	private int operatorID = 0;

	// loggerTool.setRemoteHostAddress(remoteHostAddress);
	// loggerTool.setOperatorID(operatorID);

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		if (ev == null || !(ev instanceof ServiceResourceEJBEvent)) {
			return null;
		}
		String remoteHostAddress = ev.getRemoteHostAddress();
		int operatorID = ev.getOperatorID();
		this.operatorID = operatorID;
		loggerTool.setRemoteHostAddress(remoteHostAddress);
		loggerTool.setOperatorID(operatorID);

		ServiceResourceEJBEvent event = (ServiceResourceEJBEvent) ev;
		int actionType = event.getActionType();
		switch (actionType) {

		case (ServiceResourceEJBEvent.RESOURCE_NEW): {
			createServiceResource((ServiceResourceDTO) event.getDto());
			break;
		}

		case (ServiceResourceEJBEvent.RESOURCE_UPDATE): {
			updateServiceResource((ServiceResourceDTO) event.getDto());
			break;
		}
		case (ServiceResourceEJBEvent.RESOURCE_DELETE): {
			deleteServiceResource((ServiceResourceDTO) event.getDto());
			break;
		}
		case (ServiceResourceEJBEvent.RESOURCE_DETAIL_NEW): {
			createResourcePhoneNo((List) event.getDto());
			break;
		}
		case (ServiceResourceEJBEvent.RESOURCE_DETAIL_UPDATE): {
			updateResourcePhoneNo((ResourcePhoneNoDTO) event.getDto());
			break;
		}
		case (ServiceResourceEJBEvent.RESOURCE_DETAIL_DELETE): {
			deleteResourcePhoneNo((ResourcePhoneNoDTO) event.getDto());
			break;
		}
		case (ServiceResourceEJBEvent.RESOURCE_DETAIL_DELETE_MULTIPLY): {
			deleteResourcePhoneNos((List) event.getDto());
			break;
		}
		}
		return null;
	}

	private void createServiceResource(ServiceResourceDTO dto)
			throws CommandException {
		try {
			ServiceResourceHome home = HomeLocater.getServiceResourceHome();
			home.create(dto);
			loggerTool.logCreate("资源", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("创建资源时出错。" + e);
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("创建资源时出错。" + e);
		} catch (ServiceException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
		}
	}

	private void updateServiceResource(ServiceResourceDTO dto)
			throws CommandException {
		try {
			ServiceResourceHome home = HomeLocater.getServiceResourceHome();
			ServiceResource bean = home.findByPrimaryKey(dto.getResourceName());
			if(bean.ejbUpdate(dto)==-1)
			    throw new CommandException("修改资源时出错。");
			loggerTool.logUpdate("资源", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("修改资源时出错。" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("修改资源时出错。" + e);
		} catch (ServiceException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
		}
	}

	private void deleteServiceResource(ServiceResourceDTO dto)
			throws CommandException {
		try {
			ServiceResourceHome home = HomeLocater.getServiceResourceHome();
			ServiceResource bean = home.findByPrimaryKey(dto.getResourceName());
			bean.remove();
			loggerTool.logDelete("资源", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("删除资源时出错。" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("资源时出错。" + e);
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("删除资源时出错。" + e);
		} catch (ServiceException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
		}
	}

	private void createResourcePhoneNo(List dtoList) throws CommandException {
		if (dtoList == null || dtoList.isEmpty()) {
			return;
		}
		try {
			ResourcePhoneNoHome home = HomeLocater.getResourcePhoneNoHome();
			ResourcePhoneNoDTO dto = null;
			ResourcePhoneNo bean = null;
			Iterator iter = dtoList.iterator();
			while (iter.hasNext()) {
				dto = (ResourcePhoneNoDTO) iter.next();
				if (dto == null) {
					continue;
				}
				bean = home.create(dto);
				fillPhoneNoUsedLogForCreate(bean);

			}
			ResourcePhoneNoDTO firstDto=(ResourcePhoneNoDTO)dtoList.get(0);
			loggerTool.logCreate("电话号码资源", "批量增加,共"+dtoList.size()+"条记录,第一个号码为:"+firstDto.getPhoneNo()+"国家代码:"+firstDto.getCountryCode()+"地区代码:"+firstDto.getAreaCode());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("创建电话号码资源时出错。" + e);
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("创建电话号码资源时出错。" + e);
		} catch (ServiceException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
		}
	}

	private void updateResourcePhoneNo(ResourcePhoneNoDTO dto)
			throws CommandException {
	}

	private void deleteResourcePhoneNo(ResourcePhoneNoDTO dto)
			throws CommandException {
	}

	private void deleteResourcePhoneNos(List itemIDList)
			throws CommandException {
		if (itemIDList == null || itemIDList.isEmpty()) {
			return;
		}
		try {
			Integer itemID = null;
			ResourcePhoneNoHome home = HomeLocater.getResourcePhoneNoHome();
			ResourcePhoneNo bean = null;
			Iterator iter = itemIDList.iterator();
			while (iter.hasNext()) {
				itemID = (Integer) iter.next();
				if (itemID == null) {
					continue;
				}
				bean = home.findByPrimaryKey(itemID);
				fillPhoneNoUsedLogForDelete(bean);
				bean.remove();

			}
			loggerTool.logDelete("电话号码资源", " ItemIDs' List:" + itemIDList);
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("删除电话号码资源时出错。" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("删除电话号码资源时出错。" + e);
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new CommandException("删除电话号码资源时出错。" + e);
		} catch (ServiceException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
		}
	}

	private boolean fillPhoneNoUsedLogForCreate(ResourcePhoneNo bean)
			throws CommandException {

		// ResourcePhoneNoHome phoneHome;
		// ResourcePhoneNo phone;
		try {
			// phoneHome = HomeLocater.getResourcePhoneNoHome();
			// phone = phoneHome.findByPrimaryKey(new Integer(itemID));

			PhoneNoUseLogDTO phoneUseDto = new PhoneNoUseLogDTO();
			phoneUseDto
					.setAction(CommonConstDefinition.PHONENOUSELOG_ACTION_CREATE);
			phoneUseDto.setAreaCode(bean.getAreaCode());
			phoneUseDto.setCountryCode(bean.getCountryCode());
			phoneUseDto.setCreateTime(TimestampUtility.getCurrentDate());
			phoneUseDto.setCustID(operatorID);
			phoneUseDto.setDtCreate(TimestampUtility.getCurrentDate());
			phoneUseDto.setDtLastmod(TimestampUtility.getCurrentDate());
			phoneUseDto.setOpID(operatorID);

			OperatorHome homeOpe = HomeLocater.getOperatorHome();
			Operator operator = homeOpe
					.findByPrimaryKey(new Integer(operatorID));
			phoneUseDto.setOrgID(operator.getOrgID());
			phoneUseDto.setPhoneNo(bean.getPhoneNo());
			phoneUseDto.setResourceItemID(bean.getItemID().intValue());
			phoneUseDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);

			// Integer objSAID = (Integer) context
			// .get(Service.SERVICE_ACCOUNT_ID);
			// phoneUseDto.setUserID(objSAID.intValue());
			// phoneUseDto.setUserID(operatorID);
			phoneUseDto.setDescription("电话号码被使用");
			phoneUseDto.setNetworkID("");
			phoneUseDto.setPsID(0);

			BusinessUtility.addPhoneUseLog(phoneUseDto);

		} catch (HomeFactoryException e) {
			LogUtility.log(getClass(), LogLevel.WARN, "电话号码资源定位错误!");
			throw new CommandException("电话号码资源定位错误，无法更新电话号码资源");
		} catch (FinderException e) {
			LogUtility.log(getClass(), LogLevel.WARN, "电话号码资源查找出错，无法更新!");
			throw new CommandException("电话号码资源查找出错，无法更新电话号码资源");
		}
		return true;
	}

	private boolean fillPhoneNoUsedLogForDelete(ResourcePhoneNo bean)
			throws CommandException {

		// ResourcePhoneNoHome phoneHome;
		// ResourcePhoneNo phone;
		try {
			// phoneHome = HomeLocater.getResourcePhoneNoHome();
			// phone = phoneHome.findByPrimaryKey(new Integer(itemID));

			PhoneNoUseLogDTO phoneUseDto = new PhoneNoUseLogDTO();
			phoneUseDto
					.setAction(CommonConstDefinition.PHONENOUSELOG_ACTION_XIAO);
			phoneUseDto.setAreaCode(bean.getAreaCode());
			phoneUseDto.setCountryCode(bean.getCountryCode());
			phoneUseDto.setCreateTime(TimestampUtility.getCurrentDate());
			phoneUseDto.setCustID(operatorID);
			phoneUseDto.setDtCreate(TimestampUtility.getCurrentDate());
			phoneUseDto.setDtLastmod(TimestampUtility.getCurrentDate());
			phoneUseDto.setOpID(operatorID);

			OperatorHome homeOpe = HomeLocater.getOperatorHome();
			Operator operator = homeOpe
					.findByPrimaryKey(new Integer(operatorID));
			phoneUseDto.setOrgID(operator.getOrgID());
			phoneUseDto.setPhoneNo(bean.getPhoneNo());
			phoneUseDto.setResourceItemID(bean.getItemID().intValue());
			phoneUseDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);

			// Integer objSAID = (Integer) context
			// .get(Service.SERVICE_ACCOUNT_ID);
			// phoneUseDto.setUserID(objSAID.intValue());
			// phoneUseDto.setUserID(operatorID);
			phoneUseDto.setDescription("电话号码被使用");
			phoneUseDto.setNetworkID("");
			phoneUseDto.setPsID(0);
			
			BusinessUtility.addPhoneUseLog(phoneUseDto);

		} catch (HomeFactoryException e) {
			LogUtility.log(getClass(), LogLevel.WARN, "电话号码资源定位错误!");
			throw new CommandException("电话号码资源定位错误，无法更新电话号码资源");
		} catch (FinderException e) {
			LogUtility.log(getClass(), LogLevel.WARN, "电话号码资源查找出错，无法更新!");
			throw new CommandException("电话号码资源查找出错，无法更新电话号码资源");
		}
		return true;
	}
}
