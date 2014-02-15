/*
 * Created on 2006-4-24
 * 
 * @author chen jiang
 * 
 * 二维配置信息command
 */
package com.dtv.oss.service.command.config;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.BidimConfigSetting;
import com.dtv.oss.domain.BidimConfigSettingHome;
import com.dtv.oss.domain.BidimConfigSettingValue;
import com.dtv.oss.domain.BidimConfigSettingValueHome;
import com.dtv.oss.dto.BidimConfigSettingDTO;
import com.dtv.oss.dto.BidimConfigSettingValueDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigBidimEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigBidimCommand extends Command {
	private static final Class clazz = ConfigBidimCommand.class;

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		ConfigBidimEJBEvent inEvent = (ConfigBidimEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");

		try {
			switch (inEvent.getActionType()) {

			case EJBEvent.CONFIG_BIDIM_CREATE: // 二维配置信息创建
				createBidim(inEvent.getBidimDto(), inEvent
						.getNewAppendedBidimValuesList());
				break;
			case EJBEvent.CONFIG_BIDIM_EDIT:
				// updateBidim(inEvent.getBidimDto()); //二维配置信息更改
				break;
			case EJBEvent.CONFIG_BIDIM_UPDATE:
				updateBidim(inEvent.getBidimDto(), inEvent
						.getNewAppendedBidimValuesList(), inEvent
						.getToBeDeletedBidimValuesList()); // 二维配置信息更改
				break;
			case EJBEvent.CONFIG_BIDIM_DELETE:
				deleteBidim(inEvent.getBidimDto()); // 二维配置信息删除
				break;
			case EJBEvent.CONFIG_VALUE_CREATE: // 二维配置信息选项值创建
				createValueBidim(inEvent.getBidimValueDto());
				break;
			case EJBEvent.CONFIG_VALUE_EDIT:
				updateValueBidim(inEvent.getBidimValueDto()); // 二维配置信息选项值更改
				break;
			case EJBEvent.CONFIG_VALUE_DELETE:
				deleteValueBidim(inEvent.getBidimValueDto()); // 二维配置信息选项值删除
				break;

			default:
				throw new IllegalArgumentException(
						"ConfigBidimEJBEvent中actionType的设置不正确。");
			}
		} catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}


	/**
	 * 修改二维配置信息
	 * 
	 * @param BidimConfigSettingDTO
	 */
	private void updateBidim(BidimConfigSettingDTO dto,
			List newAppendedValuesList, List toBeDeletedValuesList)
			throws ServiceException {

		try {

			ServiceContext context = initServiceContext();

			BidimConfigSettingHome home = HomeLocater
					.getBidimConfigSettingHome();

			BidimConfigSetting bidim = home.findByPrimaryKey(new Integer(dto
					.getId()));
			//dto.setDtCreate(bidim.getDtCreate());
			//dto.setDtLastmod(bidim.getDtLastmod());
			System.out.println("BidimConfigSettingDTO:" + dto);
			System.out.println("dto.getDtLastmod():" + dto.getDtLastmod());
			System.out.println("bidim.getDtLastmod():" + bidim.getDtLastmod());
			System.out
					.println("dto.getDtLastmod().equals(bidim.getDtLastmod()):"
							+ dto.getDtLastmod().equals(bidim.getDtLastmod()));		
			dto.setDtLastmod(bidim.getDtLastmod());
			if (bidim.ejbUpdate(dto) == -1) {
				SystemLogRecorder.keyLog("更新二维配置信息失败", "二维配置信息", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"BidimConfigSettingDTO update fail, dto:"
								+ dto
								+ ". Please make sure that dt_lastmod of BidimConfigSettingDTO is set.");
			}
			//删除文本框类型的值
			
			if("M".equalsIgnoreCase(dto.getValueType())){
				BidimConfigSettingValueHome bidimValueHome = HomeLocater
				.getBidimConfigSettingValueHome();
				Collection valueCol1 = bidimValueHome.findBySettingID(dto.getId());
				Iterator iter = valueCol1.iterator();
				while (iter.hasNext()) {
					BidimConfigSettingValue romoteObj=(BidimConfigSettingValue) iter.next();
					romoteObj.remove();
				}
			}
			if ((toBeDeletedValuesList != null && (!toBeDeletedValuesList
					.isEmpty()))
					|| (newAppendedValuesList != null && (!newAppendedValuesList
							.isEmpty()))) {
				BidimConfigSettingValueHome bidimValueHome = HomeLocater
						.getBidimConfigSettingValueHome();
				BidimConfigSettingValue bidimValue = null;
				BidimConfigSettingValueDTO valueDTO = null;
				Collection valueCol = bidimValueHome.findBySettingID(dto.getId());
				
				System.out.println("========valueCol=================="+valueCol.size());
				System.out.println("========toBeDeletedValuesList=================="+toBeDeletedValuesList);
				System.out.println("========newAppendedValuesList=================="+newAppendedValuesList);
				checkUpdateValue(valueCol,toBeDeletedValuesList,newAppendedValuesList);
				// delete
				if (toBeDeletedValuesList != null
						&& (!toBeDeletedValuesList.isEmpty())) {
					Iterator iter = toBeDeletedValuesList.iterator();
					while (iter.hasNext()) {
						valueDTO = (BidimConfigSettingValueDTO) iter.next();
						if (valueDTO != null) {
							bidimValue = bidimValueHome
									.findByPrimaryKey(new Integer(valueDTO
											.getId()));
							bidimValue.remove();
						}
					}
				}
				// append
				if (newAppendedValuesList != null
						&& (!newAppendedValuesList.isEmpty())) {
					Iterator iter = newAppendedValuesList.iterator();
					while (iter.hasNext()) {
						valueDTO = (BidimConfigSettingValueDTO) iter.next();
						if (valueDTO != null) {
							valueDTO.setSettingId(bidim.getId().intValue());
							bidimValueHome.create(valueDTO);
						}

					}

				}
			}
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context),0,
					SystemLogRecorder.LOGMODULE_CONFIG, "修改二维配置信息",
					"修改二维配置信息，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找二维配置信息时出错，活动ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找二维配置信息出错，活动ID：" + dto.getId());
		} catch (RemoveException re) {
			LogUtility.log(clazz, LogLevel.ERROR, re);
			throw new ServiceException("删除二维配置信息值出错，活动ID：" + dto.getId());
		} catch (CreateException ce) {

			LogUtility.log(clazz, LogLevel.ERROR, ce);
			throw new ServiceException("创建二维配置信息值出错，活动ID：" + dto.getId());

		}
	}

	/**
	 * @param toBeDeletedValuesList
	 * @param newAppendedValuesList
	 */
	private void checkUpdateValue(Collection col,List toBeDeletedValuesList, List newAppendedValuesList) throws ServiceException {
		
		String defaltValueDel="";
		String defaltValueAdd="";
		String existValue="";
		
		//本身有"Y",不删除
		if(!col.isEmpty()|| col!=null){
			Iterator iter = col.iterator();
			BidimConfigSettingValue bidimValue = null;
			while(iter.hasNext()){
				bidimValue=(BidimConfigSettingValue) iter.next();
				 
			   if(CommonConstDefinition.BIDIM_STATUS_AVA.equalsIgnoreCase(bidimValue.getStatus()))
			 	existValue=existValue+bidimValue.getDefaultOrNot();  
			}
		}
		 
		if (toBeDeletedValuesList != null
				&& (!toBeDeletedValuesList.isEmpty())) {
			Iterator iter = toBeDeletedValuesList.iterator();
			BidimConfigSettingValueDTO valueDTO = null;
			
			while (iter.hasNext()) {
				valueDTO = (BidimConfigSettingValueDTO) iter.next();
				if (valueDTO != null) {
					if(CommonConstDefinition.BIDIM_STATUS_AVA.equalsIgnoreCase(valueDTO.getStatus()))
					defaltValueDel=defaltValueDel+valueDTO.getDefaultOrNot();    
				} 
		}
			
		
	 }   
		 
		if (newAppendedValuesList != null
				&& (!newAppendedValuesList.isEmpty())) {
			Iterator iter = newAppendedValuesList.iterator();
			BidimConfigSettingValueDTO valueDTO = null;
			while (iter.hasNext()) {
				valueDTO = (BidimConfigSettingValueDTO) iter.next();
				if (valueDTO != null) {
					if(CommonConstDefinition.BIDIM_STATUS_AVA.equalsIgnoreCase(valueDTO.getStatus()))
						defaltValueAdd=defaltValueAdd+valueDTO.getDefaultOrNot();    
				} 
		}
	}
		System.out.println("***********defaltValueAdd*********************"+defaltValueAdd);
		if(existValue.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)!=-1 && defaltValueDel.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)==-1 && defaltValueAdd.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)!=-1)
			throw new ServiceException("不允许两条有效记录都被设置成默认值。");
			 
		if(existValue.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)==-1)
			checkValueDto(newAppendedValuesList);
		
		if(existValue.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)!=-1 && defaltValueDel.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)!=-1)
			checkValueDto(newAppendedValuesList);
			 
		
	}

	/**
	 * 创建二维配置信息
	 * 
	 * @param BidimConfigSettingDTO
	 *            dto
	 * @throws ServiceException
	 */
	private void createBidim(BidimConfigSettingDTO dto,
			List newAppendedValuesList) throws ServiceException {

		try {
			
			ServiceContext context = initServiceContext();

			BidimConfigSettingHome home = HomeLocater
					.getBidimConfigSettingHome();

			BidimConfigSetting bidimConfig = home.create(dto);
			if (newAppendedValuesList != null
					&& !newAppendedValuesList.isEmpty()) {
				checkValueDto(newAppendedValuesList);
				BidimConfigSettingValueHome valueHome = HomeLocater
						.getBidimConfigSettingValueHome();
				BidimConfigSettingValueDTO valueDTO = null;
				Iterator iter = newAppendedValuesList.iterator();
				
				while (iter.hasNext()) {
					valueDTO = (BidimConfigSettingValueDTO) iter.next();
					if (valueDTO != null) {
						valueDTO.setSettingId(bidimConfig.getId().intValue());
						valueHome.create(valueDTO);
					}
				}
			}
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context),0, SystemLogRecorder.LOGMODULE_CONFIG,
					"创建二维配置信息", "创建二维配置信息，ID：" + bidimConfig.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位二维配置信息出错。");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("二维配置信息出错。");
		}
	}

	/**
	 * @param newAppendedValuesList
	 */
	private void checkValueDto(List newAppendedValuesList) throws ServiceException {
		 
		Iterator iter = newAppendedValuesList.iterator();
		String defaltValue="";
		BidimConfigSettingValueDTO valueDTO=null;
		while (iter.hasNext()) {
			valueDTO = (BidimConfigSettingValueDTO) iter.next();
			 
			if(CommonConstDefinition.BIDIM_STATUS_AVA.equalsIgnoreCase(valueDTO.getStatus()))
			  defaltValue=defaltValue+valueDTO.getDefaultOrNot(); 
		}
		 
		if(defaltValue.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)!=-1 && (defaltValue.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES) != defaltValue.lastIndexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES))){
			throw new ServiceException("不允许两条有效记录都被设置成默认值。");
		}
	}


	/**
	 * 删除二维配置信息
	 * 
	 * @param BidimConfigSettingDTO
	 *            dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws
	 */
	private void deleteBidim(BidimConfigSettingDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();

			BidimConfigSettingHome bidimHome = HomeLocater
					.getBidimConfigSettingHome();
			BidimConfigSetting bidim = bidimHome.findByPrimaryKey(new Integer(
					dto.getId()));
			// delete values
			BidimConfigSettingValueHome valueHome = HomeLocater
					.getBidimConfigSettingValueHome();
			Collection valueCol = valueHome.findBySettingID(dto.getId());
			Iterator valueIte = valueCol.iterator();
			BidimConfigSettingValue value = null;
			while (valueIte.hasNext()) {
				value = (BidimConfigSettingValue) valueIte.next();
				value.remove();
			}
			// delete config setting
			bidim.remove();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), dto.getId(),
					SystemLogRecorder.LOGMODULE_CONFIG, "删除二维配置信息",
					"删除二维配置信息，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位二维配置信息出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找二维配置信息出错，活动ID：" + dto.getId());
		}
	}

	/**
	 * 删除二维配置信息
	 * 
	 * @param BidimConfigSettingDTO
	 *            dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws
	 */
	private void deleteValueBidim(BidimConfigSettingValueDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();
			LogUtility.log(clazz, LogLevel.DEBUG,
					"BidimConfigSettingValueDTO：******** " + dto);
			BidimConfigSettingValueHome valueHome = HomeLocater
					.getBidimConfigSettingValueHome();

			BidimConfigSettingValue bidimValue = valueHome
					.findByPrimaryKey(new Integer(dto.getId()));
			bidimValue.remove();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), dto.getId(),
					SystemLogRecorder.LOGMODULE_CONFIG, "删除二维配置值信息",
					"删除二维配置值信息，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位二维配置信息值出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找二维配置信息值出错，值ID：" + dto.getId());
		}
	}

	/**
	 * 创建二维配置值信息
	 * 
	 * @param BidimConfigSettingValueDTO
	 *            dto
	 * @throws ServiceException
	 */
	private void createValueBidim(BidimConfigSettingValueDTO dto)
			throws ServiceException {

		try {

			ServiceContext context = initServiceContext();
			LogUtility.log(clazz, LogLevel.DEBUG,
					"BidimConfigSettingValueDTO：******** " + dto);
			BidimConfigSettingValueHome valueHome = HomeLocater
					.getBidimConfigSettingValueHome();

			BidimConfigSettingValue bidimConfigValue = valueHome.create(dto);

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), bidimConfigValue.getId()
					.intValue(), SystemLogRecorder.LOGMODULE_CONFIG,
					"创建二维配置值信息", "创建二维配置值信息，ID：" + bidimConfigValue.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位二维配置信息值出错。");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("二维配置信息值出错。");
		}
	}

	/**
	 * 修改二维配置值信息
	 * 
	 * @param BidimConfigSettingDTO
	 */
	private void updateValueBidim(BidimConfigSettingValueDTO dto)
			throws ServiceException {

		try {

			ServiceContext context = initServiceContext();

			BidimConfigSettingValueHome valueHome = HomeLocater
					.getBidimConfigSettingValueHome();

			BidimConfigSettingValue bidimValue = valueHome
					.findByPrimaryKey(new Integer(dto.getId()));

			if (bidimValue.ejbUpdate(dto) == -1) {
				SystemLogRecorder.keyLog("更新二维配置信息失败", "二维配置信息", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"BidimConfigSettingValueDTO update fail, ID:"
								+ dto.getId()
								+ ". Please make sure that dt_lastmod of BidimConfigSettingValueDTO is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), dto.getId(),
					SystemLogRecorder.LOGMODULE_CONFIG, "修改二维配置值信息",
					"修改二维配置值信息，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找二维配置信息值时出错，活动ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找二维配置信息值出错，活动ID：" + dto.getId());
		}
	}

	/**
	 * 初始化ServiceContext 将一些通用的信息放入ServiceContext
	 */
	private ServiceContext initServiceContext() {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));

		return serviceContext;
	}

	public static void main(String[] args) {
	}
}
