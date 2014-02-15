package com.dtv.oss.service.command.logistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.FinderException;

import com.dtv.oss.domain.TerminalDevice;
import com.dtv.oss.domain.TerminalDeviceHome;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.DeviceStockService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class DeviceStockCommand extends Command {

	private static final Class clazz = DeviceStockCommand.class;

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	private ServiceContext context;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);

		DeviceStockEJBEvent inEvent = (DeviceStockEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();

		try {
			switch (inEvent.getActionType()) {
			// 设备入库
			case LogisticsEJBEvent.DEVICE_IN_STOCK:
				devInStock(inEvent);
				break;

			// 设备出库
			case LogisticsEJBEvent.DEVICE_OUT_STOCK:
				devOutStock(inEvent);
				break;

			// 设备流转
			case LogisticsEJBEvent.DEVICE_TRANSITION:
				devTrans(inEvent);
				break;

			// 设备报废
			case LogisticsEJBEvent.DEVICE_INVALIDATE:
				deviceScrap(inEvent);
				break;

			// 设备手工修改状态
			case LogisticsEJBEvent.DEVICE_MANULCHANGE_STATUS:
				devStatusChange(inEvent);
				break;

			// 设备送修
			case LogisticsEJBEvent.DEVICE_REPAIR:
				deviceRepair(inEvent);
				break;
			// 设备配对与授权
			case LogisticsEJBEvent.DEVICE_PREAUTH_MATCH:
				System.out.println("设备配对与授权");
				matchPreAuth(inEvent);
				break;
			// 对于单个设备授权
			case LogisticsEJBEvent.DEVICE_PREAUTH_1:
				everyDevicePreAuth(inEvent);
				break;
			// 设备解除配对
			case LogisticsEJBEvent.UNMATCH_DEVICE:
				unmatchDevice(inEvent);
				break;
			// 设备解除预授权
			case LogisticsEJBEvent.DEVICE_RELEASEPREAUTH:
				deviceReleasePreauth(inEvent);
				break;
			//修改设备信息
			case LogisticsEJBEvent.DEVICE_MODIFY:
				deviceInfoModify(inEvent);
				break;
			case LogisticsEJBEvent.DEVICE_PREAUTH:
				devicePreAuth(inEvent);	
				break;
			case LogisticsEJBEvent.DEVICE_DISAUTH:
				deviceDisauth(inEvent);
				break;
			//刷新授权	
			case LogisticsEJBEvent.DEVICE_REFRESHPREAUTH:
				deviceRefreshPreauth(inEvent);
				break;	
			default:
				throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException("未知错误。");
		}
		return response;
	}

	/**
	 * 设备回库
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void devInStock(DeviceStockEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devInStock(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		String logDes="";
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String temDes = it.next().toString();
			if(logDes.length()<400){
				if(!"".equals(logDes))logDes=logDes+";";
				logDes=logDes+temDes;
			}
		}
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:"
				+ logDes.toString());

		//记录回库原因:
		String txtStatusReason = BusinessUtility.getCommonNameByKey("SET_D_STOCKINREASON",event.getDevTransDTO().getStatusReason());
		
		if (event.isDoPost())
		{
			if(logDes.length()>400)logDes=logDes.substring(0,400) +"...";
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
				"设备回库", "回库原因:"+txtStatusReason+",回库设备编号:" +logDes,SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}

	/**
	 * 设备出库
	 * ADD BY JASON
	 * @param event
	 * @throws ServiceException
	 */
	private void devOutStock(DeviceStockEJBEvent event) throws ServiceException {
		this.context = new ServiceContext();
		
		//检查设备序号是否符合条件
		if(event.getDevTransDTO()==null || event.getDevArrayStr()==null ||
				"".equals(event.getDevArrayStr()))
			throw new ServiceException("设备信息不全，无法进行出库操作！");
		if(event.getDevTransDTO().getToID()==0)
			throw new ServiceException("设备转入的组织未知，无法进行出库操作！");
		
		String deviceSeriaNoList[]=event.getDevArrayStr().split("\r\n");
		DeviceTransitionDTO dto=event.getDevTransDTO();
		dto.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_INORGANIZATION);
		
		TerminalDeviceHome tdHome=null;
		Collection deviceIDList=new ArrayList();
		
		for(int i=0;i<deviceSeriaNoList.length;i++){
			try{
				if(tdHome==null)
					tdHome=HomeLocater.getTerminalDeviceHome();
				Collection tdList=tdHome.findBySerialNo(deviceSeriaNoList[i].trim());
				
				if(tdList==null || tdList.size()==0)
					throw new ServiceException("设备序列号[" + deviceSeriaNoList[i] +"]不存在");
				
				TerminalDevice td=(TerminalDevice)tdList.toArray()[0];
				if(!CommonConstDefinition.DEVICE_STATUS_STOCK.equals(td.getStatus()))
					throw new ServiceException("设备序列号[" + deviceSeriaNoList[i] +"]的状态不为库存");
				deviceIDList.add(td.getPrimaryKey());
				
				if(dto.getFromID()==0 || dto.getFromType()==null || "".equals(dto.getFromType())){
					dto.setFromID(td.getAddressID());
					dto.setFromType(td.getAddressType());
				}
			}
			catch(HomeFactoryException e){
				LogUtility.log(clazz, LogLevel.WARN, e);
				throw new ServiceException("设备序列号[" + deviceSeriaNoList[i] +"]不存在");
			}
			catch(FinderException e){
				LogUtility.log(clazz, LogLevel.WARN, e);
				throw new ServiceException("设备序列号[" + deviceSeriaNoList[i] +"]不存在");
			}
		}
		
		if(!event.isDoPost())
			return;
		
		event.setDevArray(deviceIDList);
		DeviceStockService dss=new DeviceStockService(this.context);
		dss.deviceOutStock(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
		this.response.setPayload(lstRet);
	}

	/**
	 * 设备报废
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void deviceScrap(DeviceStockEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException{
		this.context = new ServiceContext();
		new DeviceStockService(this.context).deviceScrap(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		StringBuffer strRe=new StringBuffer();
		Iterator it=lstRet.iterator();
		while(it.hasNext())
			strRe.append(it.next().toString()).append("//");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:"
				+ strRe.toString());

		String logDes = strRe.toString();
		if(logDes.length()>400)logDes=logDes.substring(0,400) +"...";
		if (event.isDoPost())
		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "设备报废", "报废设备编号:"
						+ logDes,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 设备送修
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void devRepair(DeviceStockEJBEvent event) throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devRepair(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}

	/**
	 * 设备状态修改
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void devStatusChange(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devStatusChange(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}

	/**
	 * 设备调拨
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void devTrans(DeviceStockEJBEvent event) throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devTrans(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}

	/**
	 * 设备匹配与授权
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void matchPreAuth(DeviceStockEJBEvent event)
			throws ServiceException {
		
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devMatchAndPreauthTrans(event);
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
		if (!event.isDoPost()){
			 this.response.setPayload(lstRet);
			 return;
		}

		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
						SystemLogRecorder.LOGMODULE_LOGISTICS, "机卡配对/预授权",
						"机卡配对/预授权:"+event.getDeviceBatchPreauthDTO().getReferSheetSerialNO(),
						SystemLogRecorder.LOGCLASS_NORMAL,
						SystemLogRecorder.LOGTYPE_APP);

		/*String serialNoOfDevices=event.getDeviceTransitionDTO().getFromType();
		if(serialNoOfDevices==null)
			serialNoOfDevices="";
		
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devMatchAndPreauthTrans(event);
		
		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
		if (!event.isDoPost()){
			 this.response.setPayload(lstRet);
			 return;
		}

		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
						SystemLogRecorder.LOGMODULE_LOGISTICS, "机卡配对/预授权",
						"机卡配对/预授权的设备编号:"+ serialNoOfDevices,
						SystemLogRecorder.LOGCLASS_NORMAL,
						SystemLogRecorder.LOGTYPE_APP);*/

	}
	/**
	 * 设备授权
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void devicePreAuth(DeviceStockEJBEvent event)
			throws ServiceException {	
		if (!event.isDoPost()){
			 this.response.setPayload(event.getDeviceCheckList());
			 return;
		}
		
		this.context = new ServiceContext();
	    new DeviceStockService(this.context).devPreAuth(event);

		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "智能卡预授权","智能卡预授权:"
						+ event.getDeviceBatchPreauthDTO().getReferSheetSerialNO(),
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 单个设备授权
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void everyDevicePreAuth(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devPreauthTrans(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}

	/**
	 * 设备配对解除
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void unmatchDevice(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devMatchTrans(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
		if (!event.isDoPost()){
			 this.response.setPayload(lstRet);
			 return;
		}
		
		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "机卡解配对","机卡解配对:"
						+ event.getDeviceBatchPreauthDTO().getReferSheetSerialNO(),
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 设备预授权解除
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void deviceReleasePreauth(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).scdevReleasePreauthTrans(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}
	
	

	/**
	 * 设备送修
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void deviceRepair(DeviceStockEJBEvent event)throws HomeFactoryException, FinderException,
								ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devSentToRepair(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
			this.response.setPayload(lstRet);
		}
		StringBuffer strRe=new StringBuffer();
		Iterator it=lstRet.iterator();
		while(it.hasNext())
			strRe.append(it.next().toString()).append("//");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:" + strRe.toString());

		//日志已记录
//		if (event.isDoPost())
//			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
//					"设备送修", "送修设备编号:"+ strRe.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 设备信息修改
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void deviceInfoModify(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).deviceInfoModify(event);

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}
	/**
	 * 设备解授权
	 */
	private void deviceDisauth(DeviceStockEJBEvent event)
	throws ServiceException{
		if (!event.isDoPost()){
			 this.response.setPayload(event.getDeviceCheckList());
			 return;
		}
		
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devDisauth(event);
		
		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "智能卡解授权","智能卡解授权:"
						+ event.getDeviceBatchPreauthDTO().getReferSheetSerialNO(),
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);

	}
	
	
	/**
	 * 刷新设备预授权
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void deviceRefreshPreauth(DeviceStockEJBEvent event)
			throws ServiceException {
		TerminalDeviceDTO terminalDeviceDTO = event.getTerDeviceDTO();
		ArrayList arrayList = new ArrayList();
		arrayList.add(terminalDeviceDTO.getSerialNo());
		this.context = new ServiceContext();
		new DeviceStockService(this.context).refurbishPreAuthorization(arrayList,operatorID);
		
		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "智能卡刷新预授权","智能卡刷新预授权,智能卡序列号:"
						+ event.getTerDeviceDTO().getSerialNo(),
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}
}
