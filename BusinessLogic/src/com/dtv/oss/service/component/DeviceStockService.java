package com.dtv.oss.service.component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.RemoveException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;


import com.dtv.oss.domain.DeviceBatchPreauth;
import com.dtv.oss.domain.DeviceBatchPreauthHome;
import com.dtv.oss.domain.DevicePreauthRecordHome;
import com.dtv.oss.domain.DevicePrematchRecordHome;
import com.dtv.oss.dto.DevicePreauthRecordDTO;
import com.dtv.oss.dto.DevicePrematchRecordDTO;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.dto.DeviceBatchPreauthDTO;
import com.dtv.oss.domain.DeviceTransition;
import com.dtv.oss.domain.DeviceTransitionDetail;
import com.dtv.oss.domain.DeviceTransitionDetailHome;
import com.dtv.oss.domain.DeviceTransitionHome;
import com.dtv.oss.domain.OssAuthorization;
import com.dtv.oss.domain.OssAuthorizationHome;
import com.dtv.oss.domain.TerminalDevice;
import com.dtv.oss.domain.TerminalDeviceHome;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.dto.OssAuthorizationDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

/**
 * 设备流转类操作 
 * 里面乱七八糟的，希望有时间好好整理下
 * 特别是设备流转没有好好的复用
 * @author 
 * 
 */
public class DeviceStockService extends AbstractService {

	private static final Class clazz = DeviceStockService.class;

	private ServiceContext context;

	/**
	 * 构造函数
	 * 
	 * @param s
	 */
	public DeviceStockService(ServiceContext s) {
		this.context = s;
	}

	/**
	 * /** 设备回库
	 * 
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	// public void devInStock(DeviceStockEJBEvent event) throws
	// ServiceException{
	// DeviceTransitionDTO devTransDto=event.getDeviceTransitionDTO();
	// Collection deviceIDList=event.getDeviceArray();
	// int opID=event.getOperatorID();
	/***************************************************************************
	 * 这个设备入库还要重新判断*********
	 * 
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	// String devTransAction=devTransDto.getAction();
	// String deviceToStatus="";
	// deviceTransition(event.getActionType(),deviceIDList,opID,"",devTransAction,
	// deviceToStatus,devTransDto.getFromType(),devTransDto.getToType(),devTransDto.getStatusReason(),
	// devTransDto.getFromID(),devTransDto.getToID(),"","设备入库",true,event.getRemoteHostAddress());
	// }
	public void devInStock(DeviceStockEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {

		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		String serialsStr = event.getDevArrayStr();
		if (serialsStr == null || "".equals(serialsStr))
			throw new ServiceException("没有扫描需要回库的设备");

		//System.out.println("=====Description====["+serialsStr+"]");
		String[] serialsArray = serialsStr.split("\r\n");
		//for(int i=0;i<serialsArray.length;i++)
		//System.out.println("=========["+serialsArray[i]+"]");


		ArrayList serialsList = new ArrayList();
		for (int i = 0; i < serialsArray.length; i++) {
			//serialsArray[i] = serialsArray[i].replaceAll("\r\n","");
			if(serialsArray[i]!=null && !"".equals(serialsArray[i].trim()))
			serialsList.add(serialsArray[i].trim());
		}
		checkExistBackStock(serialsList, event,null);

		int opID = event.getOperatorID();
		if (event.isDoPost())
			deviceTransitionForReturn(devTransDto, serialsList, opID);
		this.context.put(Service.PROCESS_RESULT, serialsList);

	}

	/**
	 * @param serialsList
	 * @param opID
	 */
	private void deviceTransitionForReturn(DeviceTransitionDTO devTransDto,
			ArrayList serialsList, int opID) throws ServiceException {
		TerminalDeviceHome devHome;
		DeviceTransitionHome devTransHome;
		DeviceTransitionDetailHome devTransDetailHome;
		DeviceTransitionDetail detail;
		TerminalDevice dev;
		DeviceTransition devTrans;
		Collection devCol = null;
		ArrayList deviceList = new ArrayList();
		try {
			// 创建设备流转头记录
			// 设置创建时间
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			// 设置状态
			devTransDto.setStatus("V");
			devTransDto.setOperatorID(opID);
			devTransDto.setAction("T");
			devTransDto.setDeviceNumber(serialsList.size());
			devTransDto.setToType("D");

			devTransHome = HomeLocater.getDeviceTransitionHome();
			devTrans = devTransHome.create(devTransDto);
			Iterator ite = serialsList.iterator();
			while (ite.hasNext()) {

				devHome = HomeLocater.getTerminalDeviceHome();
				devCol = devHome.findBySerialNo((String) ite.next());
				Iterator ite1 = devCol.iterator();
				while (ite1.hasNext()) {
					dev = (TerminalDevice) ite1.next();
					String newStatus = dev.getStatus();
					//待售/送修 设备 变为 库存，其余状态的设备状态不变
					if("W".equals(newStatus) || "M".equals(newStatus))
						newStatus = CommonConstDefinition.DEVICE_STATUS_STOCK;

					// 创建设别流转明细记录
					devTransDetailHome = HomeLocater
							.getDeviceTransitionDetailHome();
					DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
					detailDto.setBatchID(devTrans.getBatchID().intValue());
					detailDto.setDeviceID(dev.getDeviceID().intValue());
					detailDto.setSerialNo(dev.getSerialNo());
					detailDto.setStatus("V");
					detailDto.setFromDeviceStatus(dev.getStatus());
					detailDto.setFromType(dev.getAddressType());
					detailDto.setFromID(dev.getAddressID());
					detailDto.setToDeviceStatus(newStatus);
					detailDto.setToType(devTrans.getToType());
					detailDto.setToID(devTrans.getToID());
					devTrans.setFromType(dev.getAddressType());
					devTrans.setFromID(dev.getAddressID());
					DeviceTransitionDetailHome detailHome = HomeLocater
							.getDeviceTransitionDetailHome();
					detail = devTransDetailHome.create(detailDto);

					// 修改设备状态
					dev.setStatus(newStatus);
					dev.setAddressType("D");// 侯8-02改T为D
					dev.setAddressID(devTransDto.getToID());// 侯8-02加
					dev.setDepotID(devTransDto.getToID());
				}
			}
			context.put(com.dtv.oss.service.Service.PROCESS_RESULT, "");

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		}
	}

	/**
	 * 设备出库
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devOutStock(DeviceStockEJBEvent event) throws ServiceException {
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		Collection deviceIDList = event.getDeviceArray();
		int opID = event.getOperatorID();

		String devTransAction = CommonConstDefinition.DEVICE_TRANSACTION_ACTION_O;
		String deviceToStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;

		deviceTransition(event.getActionType(), deviceIDList, opID, "",
				devTransAction, deviceToStatus, devTransDto.getFromType(),
				devTransDto.getToType(), devTransDto.getStatusReason(),
				devTransDto.getFromID(), devTransDto.getToID(), "", "设备出库",
				true, event.getRemoteHostAddress(),0);
	}

	/**
	 * 设备出库
	 * 库存设备进行出库 ，update by 2007-8-27
	 * @param event
	 * @throws ServiceException
	 */
	public void deviceOutStock(DeviceStockEJBEvent event) throws ServiceException {
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();

		Collection deviceIDList = event.getDeviceArray();
		int opID = event.getOperatorID();

		String devTransAction = CommonConstDefinition.DEVICE_TRANSACTION_ACTION_O;
		String deviceToStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
		
		// 返回值
		ArrayList lstRet = new ArrayList();
		String logDes="";
		TerminalDeviceHome devHome;
		DeviceTransitionHome devTransHome;
		DeviceTransitionDetailHome devTransDetailHome;
		TerminalDevice dev;
		DeviceTransition devTrans;

		int idevOK = 0;
		int idevError = 0;
		int batchNO = 0;

		// 设备流转T_DeviceTransition
		try {
			// 创建设备流转
			DeviceTransitionDTO devTransDto2 = new DeviceTransitionDTO();
			devTransDto2.setAction(devTransAction);
			devTransDto2.setBatchNo(devTransDto.getBatchNo());
			devTransDto2.setCreateTime(TimestampUtility.getCurrentTimestamp());
			devTransDto2.setDeviceNumber(deviceIDList.size());
			devTransDto2.setFromID(devTransDto.getFromID());
			devTransDto2.setFromType(devTransDto.getFromType());
			devTransDto2.setOperatorID(opID);
			devTransDto2.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			devTransDto2.setToID(devTransDto.getToID());
			devTransDto2.setToType(devTransDto.getToType());
			devTransDto2.setDescription(devTransDto.getDescription());

			devTransHome = HomeLocater.getDeviceTransitionHome();
			devTrans = devTransHome.create(devTransDto2);

			// 记录设备操作批处理头信息
			lstRet.add(new String("执行 " + deviceIDList.size() + " 个" + "设备出库"));

			// 创建设备流转明细T_DeviceTransitionDetail
			Iterator it = deviceIDList.iterator();
			int deviceID;
			while (it.hasNext()) {
				deviceID = ((Integer) it.next()).intValue();
				devHome = HomeLocater.getTerminalDeviceHome();
				
				//修改明细和流转记录明细
				dev = devHome.findByPrimaryKey(new Integer(deviceID));

				// 判断是否可以改变设备的状态
				if (canChangeDeviceStatus(LogisticsEJBEvent.DEVICE_OUT_STOCK, deviceToStatus,"")) {
					DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
					detailDto.setBatchID(devTrans.getBatchID().intValue());
					detailDto.setDeviceID(deviceID);
					detailDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
					detailDto.setFromID(dev.getAddressID());
					detailDto.setFromType(dev.getAddressType());
					detailDto.setFromDeviceStatus(dev.getStatus());
					detailDto.setSerialNo(dev.getSerialNo());
					if (!"".equals(deviceToStatus))
						detailDto.setToDeviceStatus(deviceToStatus);
					detailDto.setToID(devTransDto.getToID());
					detailDto.setToType(devTransDto.getToType());
					devTransDetailHome = HomeLocater.getDeviceTransitionDetailHome();
					devTransDetailHome.create(detailDto);

					if(!"".equals(logDes))logDes=logDes +"；";
					logDes=logDes +"设备" + dev.getDeviceID() +"，序列号"+ dev.getSerialNo()+ "，状态从" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",dev.getStatus()) +"修改到" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",deviceToStatus) ;
					
					// 修改设备的资料
					dev.setStatus(deviceToStatus);
					dev.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					dev.setAddressID(devTransDto.getToID());
					dev.setDepotID(0);
					dev.setAddressType(devTransDto.getToType());
					
					lstRet.add(new String("设备: " + dev.getSerialNo() + " 设备出库成功!"));
					idevOK++;
				} else { // 状态检验未通过
					lstRet.add(new String("设备: " + dev.getSerialNo() + "设备出库失败"));
					idevError++;
				}
			}

			devTrans.setDeviceNumber(idevOK); // 设置实际成功的设备数
			devTrans.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			lstRet.add(new String("成功设备出库: " + idevOK + "个；发生错误设备： " + idevError + "个"));

			batchNO = devTrans.getBatchID().intValue();
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "设备出库定位出错：" + e);
			throw new ServiceException("设备出库定位出错！");
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.WARN,  "设备出库查找出错：" + e2);
			throw new ServiceException("设备出库查找出错！");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "设备出库创建记录出错：" + e3);
			throw new ServiceException( "设备出库创建记录出错！");
		}

		// 创建系统日志记录
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), opID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "设备出库", "设备出库" + ",批号:"	+ batchNO + "； 成功：" + idevOK + "；失败：" + idevError,
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
			
		LogUtility.log(clazz, LogLevel.DEBUG, "设备流转结束");
		LogUtility.log(clazz, LogLevel.DEBUG, "执行结果为：" + lstRet);		
		
		// 把执行结果放到ServiceContext里
		this.context.put(Service.PROCESS_RESULT, lstRet);
		this.context.put(Service.SYSTEM_LOG, logDes);
	}

	
	/**
	 * 设备报废
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void deviceScrap(DeviceStockEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException{
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		String serialsStr = event.getDevArrayStr();

		if (serialsStr == null || "".equals(serialsStr))
			throw new ServiceException("没有扫描需要报废的设备");
		String[] serialsArray = serialsStr.split("\r\n");
	

		ArrayList serialsList = new ArrayList();
		for (int i = 0; i < serialsArray.length; i++) {
			if(serialsArray[i]!=null && !"".equals(serialsArray[i].trim()))
			serialsList.add(serialsArray[i].trim());
		}
		checkDeviceScrap(serialsList, event,null);

		int opID = event.getOperatorID();
		if (event.isDoPost())
			deviceTransitionForScrap(devTransDto, serialsList, opID);
		this.context.put(Service.PROCESS_RESULT, serialsList);
	}

	/**
	 * 设备送修
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devRepair(DeviceStockEJBEvent event) throws ServiceException {
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		Collection deviceIDList = event.getDeviceArray();
		int opID = event.getOperatorID();

		String devTransAction = CommonConstDefinition.DEVICE_TRANSACTION_ACTION_R;
		String deviceToStatus = CommonConstDefinition.DEVICE_STATUS_WAIT4REPAIR;

		deviceTransition(event.getActionType(), deviceIDList, opID, "",
				devTransAction, deviceToStatus, devTransDto.getFromType(),
				devTransDto.getToType(), devTransDto.getStatusReason(),
				devTransDto.getFromID(), devTransDto.getToID(), "", "设备送修",
				true, event.getRemoteHostAddress(),-1);
	}

	/**
	 * 设备送修
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	public void devSentToRepair(DeviceStockEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		
		String logDes="";
		
		String serialsStr = event.getDevArrayStr();
		if (serialsStr == null || "".equals(serialsStr))
			throw new ServiceException("没有扫描需要报修的设备");

		ArrayList serialsList = new ArrayList(); 
		String serials = serialsStr.trim();
		
		String[] arrTerminals = serials.split("\n");
		int deviceNumber = arrTerminals.length;
		for (int i = 0; i < deviceNumber; i++){
			String[] arrNOs = arrTerminals[i].trim().split("\\|");
			serialsList.add(arrNOs[0]);
			
			if(logDes.length()<400){
				if(!"".equals(logDes))logDes=logDes+";";
				logDes=logDes+arrNOs[0];
			}
		} 
		for(int i=0;i<serialsList.size();i++){
	     	System.out.print("**********the device no*********** "+serialsList.get(i));
		}
		//侯8-02加入对头信息的验证
		checkExistToRepair(serialsList, event.getActionType(),devTransDto.getDataFileName());
		int opID = event.getOperatorID();
		if (event.isDoPost()){
			deviceTransitionForRepair(devTransDto, serialsList, opID);
			
			if(logDes.length()>400)logDes=logDes +"...";
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), opID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
					"设备送修", "送修设备序列号：" +logDes,SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		this.context.put(Service.PROCESS_RESULT, serialsList);
	}


	private void checkExistToRepair(ArrayList serialsList, int actionType,
			String deviceModel) throws HomeFactoryException, FinderException,
			ServiceException {
		StringBuffer errMsgNone = new StringBuffer(1024);
		StringBuffer errMsgModel = new StringBuffer(1024);
		StringBuffer errMsgValid = new StringBuffer(1024);
		TerminalDeviceHome devHome = HomeLocater.getTerminalDeviceHome();;
		Collection devCol;
		for (int i=0;i<serialsList.size();i++) {
			TerminalDevice dev = null;
			String serialNo = (String) serialsList.get(i);
			//System.out.println("_______serialNo="+serialNo);
			devCol = devHome.findBySerialNo(serialNo);
			Iterator ite1 = devCol.iterator();
			while (ite1.hasNext())
				dev = (TerminalDevice) ite1.next();
			if (dev == null)
			{
				errMsgNone.append(serialNo+",");
				continue;
			}
			if(deviceModel!=null&&!deviceModel.equals("")&&!dev.getDeviceModel().equalsIgnoreCase(deviceModel)){
				errMsgModel.append(serialNo+",");
				continue;
			}
			//送修设备检查
			if (actionType == LogisticsEJBEvent.DEVICE_REPAIR) {
				if (!"R".equals(dev.getStatus()))
					errMsgValid.append(serialNo+",");
			}
		}
		
		String errMsgNoneStr = errMsgNone.toString();
		String errMsgModelStr = errMsgModel.toString();
		String errMsgValidStr = errMsgValid.toString();
		if(errMsgNoneStr != null && !"".equals(errMsgNoneStr))
		{
			throw new ServiceException("设备["+errMsgNoneStr.substring(0,errMsgNoneStr.length()-1)+"]不存在");
		}
		if(errMsgModelStr != null && !"".equals(errMsgModelStr))
		{
			throw new ServiceException("设备["+errMsgModelStr.substring(0,errMsgModelStr.length()-1)+"]的设备型号不匹配");
		}
		if(errMsgValidStr != null && !"".equals(errMsgValidStr))
		{
			throw new ServiceException("设备["+errMsgValidStr.substring(0,errMsgValidStr.length()-1)+"]不是待修的设备不能送修");
		}
	}
	
	private void checkExistBackStock(ArrayList serialsList, DeviceStockEJBEvent event,
			String deviceModel) throws HomeFactoryException, FinderException,
			ServiceException {
		StringBuffer errMsgNone = new StringBuffer(1024);
		StringBuffer errMsgValidD = new StringBuffer(1024);
		StringBuffer errMsgValidB = new StringBuffer(1024);
		StringBuffer errMsgValidR = new StringBuffer(1024);
		TerminalDeviceHome devHome = HomeLocater.getTerminalDeviceHome();;
		Collection devCol;
		for (int i=0;i<serialsList.size();i++) {
			TerminalDevice dev = null;
			String serialNo = (String) serialsList.get(i);
			//System.out.println("_______serialNo="+serialNo);
			devCol = devHome.findBySerialNo(serialNo);
			Iterator ite1 = devCol.iterator();
			while (ite1.hasNext())
				dev = (TerminalDevice) ite1.next();
			if (dev == null)
			{
				errMsgNone.append(serialNo+",");
				continue;
			}
			/**
			 回库设备检查:
			 如果回库原因为 “损坏” ， 则所有的设备都必须为 “待修” 状态
		     如果回库原因为 “退回” ， 则所有的设备都必须为 “待售” 状态
		     如果回库原因为 “修复” ， 则所有的设备都必须为 “送修” 状态   
			  
		   */ 
			if (event.getActionType() == LogisticsEJBEvent.DEVICE_IN_STOCK) {
				
				if("D".equals(event.getDevTransDTO().getStatusReason()))
				{
					if(!"R".equals(dev.getStatus()))
						errMsgValidD.append(serialNo+",");
				}
				if("B".equals(event.getDevTransDTO().getStatusReason()))
				{
					if(!"W".equals(dev.getStatus()))
						errMsgValidB.append(serialNo+",");
				}
				if("R".equals(event.getDevTransDTO().getStatusReason()))
				{
					if(!"M".equals(dev.getStatus()))
						errMsgValidR.append(serialNo+",");
				}
				// 侯8-02,加入送修设备回为库条件
//				if (!("W".equalsIgnoreCase(dev.getStatus()) || "M"
//						.equalsIgnoreCase(dev.getStatus())))
//					errMsgValid.append(serialNo+",");
			}
		}
		
		String errMsgNoneStr = errMsgNone.toString();
		String errMsgValidDStr = errMsgValidD.toString();
		String errMsgValidBStr = errMsgValidB.toString();
		String errMsgValidRStr = errMsgValidR.toString();
		if(errMsgNoneStr != null && !"".equals(errMsgNoneStr))
		{
			throw new ServiceException("设备["+errMsgNoneStr.substring(0,errMsgNoneStr.length()-1)+"]不存在");
		}
		if(errMsgValidDStr != null && !"".equals(errMsgValidDStr))
		{
			throw new ServiceException("设备["+errMsgValidDStr.substring(0,errMsgValidDStr.length()-1)+"]的状态不是“待修”,不能以“损坏”的原因回库.");
		}
		if(errMsgValidBStr != null && !"".equals(errMsgValidBStr))
		{
			throw new ServiceException("设备["+errMsgValidBStr.substring(0,errMsgValidBStr.length()-1)+"]的状态不是“待售”,不能以“退回”的原因回库.");
		}
		if(errMsgValidRStr != null && !"".equals(errMsgValidRStr))
		{
			throw new ServiceException("设备["+errMsgValidRStr.substring(0,errMsgValidRStr.length()-1)+"]的状态不是“送修”,不能以“修复”的原因回库.");
		}
	}

	/**
	 * @param serialsList
	 * @param opID
	 */
	private void deviceTransitionForRepair(DeviceTransitionDTO devTransDto,
			ArrayList serialsList, int opID) throws ServiceException {
		String newStatus = CommonConstDefinition.DEVICE_STATUS_REPAIRING;
		TerminalDeviceHome devHome;
		DeviceTransitionHome devTransHome;
		DeviceTransitionDetailHome devTransDetailHome;
		DeviceTransitionDetail detail;
		TerminalDevice dev;
		DeviceTransition devTrans;
		Collection devCol = null;
		ArrayList deviceList = new ArrayList();
		try {
			// 创建设备流转头记录
			// 设置创建时间
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			// 设置状态
			devTransDto.setStatus("V");
			devTransDto.setOperatorID(opID);
			devTransDto.setAction("R");
			devTransDto.setDeviceNumber(serialsList.size());
			devTransDto.setToType("T");
			// 得到设备型号
			String deviceModel = devTransDto.getDataFileName();
			devTransDto.setDataFileName("");

			// 取得厂商
			int providerId = BusinessUtility
					.getDeviceProviderByDeviceModel(deviceModel);

			devTransDto.setToID(providerId);
			devTransHome = HomeLocater.getDeviceTransitionHome();
			devTrans = devTransHome.create(devTransDto);
			Iterator ite = serialsList.iterator();
			while (ite.hasNext()) {

				devHome = HomeLocater.getTerminalDeviceHome();
				devCol = devHome.findBySerialNo((String) ite.next());
				Iterator ite1 = devCol.iterator();
				while (ite1.hasNext()) {
					dev = (TerminalDevice) ite1.next();

					// 创建设别流转明细记录
					devTransDetailHome = HomeLocater
							.getDeviceTransitionDetailHome();
					DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
					detailDto.setBatchID(devTrans.getBatchID().intValue());
					detailDto.setDeviceID(dev.getDeviceID().intValue());
					detailDto.setSerialNo(dev.getSerialNo());
					detailDto.setStatus("V");
					detailDto.setFromDeviceStatus(dev.getStatus());// 取得设备原有状态
					detailDto.setFromType(dev.getAddressType());
					detailDto.setFromID(dev.getAddressID());
					detailDto.setToDeviceStatus(newStatus);// 设置设备新的状态
					detailDto.setToType(devTrans.getToType());
					detailDto.setToID(devTrans.getToID());
					devTrans.setFromType(dev.getAddressType());
					devTrans.setFromID(dev.getAddressID());
					DeviceTransitionDetailHome detailHome = HomeLocater
							.getDeviceTransitionDetailHome();
					detail = devTransDetailHome.create(detailDto);
					// 修改设备状态
					dev.setStatus(newStatus);
					dev.setAddressType("T");
					dev.setAddressID(providerId);

				}
			}
			

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		}
	}

	/**
	 * 设备状态修改 侯,7-27,加了流转批号
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devStatusChange(DeviceStockEJBEvent event) throws ServiceException {
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		Collection deviceIDList = event.getDeviceArray();
		int opID = event.getOperatorID();

		String devTransAction = CommonConstDefinition.DEVICE_TRANSACTION_ACTION_M;
		String deviceToStatus = event.getToStatus();

		//人工改变设备的状态中待售和锁定状态的设备不允许修改:modify by jason 2007-2-11
		if(deviceIDList==null || deviceIDList.size()<1)
			throw new ServiceException("没有需要操作的设备，请重新选择设备！");
		String strNotAllowedOPDevice="";
		TerminalDeviceHome devHome=null;
		TerminalDevice dev=null;
		Iterator itDeviceIDList=deviceIDList.iterator();
		try{
			devHome = HomeLocater.getTerminalDeviceHome();
			while(itDeviceIDList.hasNext()){
				Integer deviceID=(Integer)itDeviceIDList.next();
				dev = devHome.findByPrimaryKey(deviceID);
				if(CommonConstDefinition.DEVICE_STATUS_SOLD.equals(dev.getStatus()) || 
						CommonConstDefinition.DEVICE_STATUS_LOCK.equals(dev.getStatus())){
					if(!"".equals(strNotAllowedOPDevice))strNotAllowedOPDevice=strNotAllowedOPDevice +",";
					strNotAllowedOPDevice=strNotAllowedOPDevice + dev.getDeviceID();
				}
			}
		}
		catch(Exception e){
			LogUtility.log(clazz, LogLevel.INFO,"查找设备出错：" + e);
			throw new ServiceException("查找设备出错！");
		}
		if(!(strNotAllowedOPDevice==null || "".equals(strNotAllowedOPDevice))){
			throw new ServiceException("设备编号（"+ strNotAllowedOPDevice +"）的状态不允许进行本次操作，已售和锁定状态的设备是不允许该操作的！");
		}
		//end
		
		deviceTransition(event.getActionType(), deviceIDList, opID, devTransDto.getBatchNo(), devTransAction, 
				deviceToStatus, devTransDto.getFromType(), devTransDto.getToType(), devTransDto.getStatusReason(), 
				devTransDto.getFromID(), devTransDto.getToID(), "", "设备状态修改", false, event.getRemoteHostAddress(),-1);
		
		//创建系统日志记录
		String logDes=(String)context.get(Service.SYSTEM_LOG);
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), opID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
				"人工修改设备状态", logDes,SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 设备调拨
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devTrans(DeviceStockEJBEvent event) throws ServiceException {
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		Collection deviceIDList = event.getDeviceArray();
		int opID = event.getOperatorID();

		String devTransAction = CommonConstDefinition.DEVICE_TRANSACTION_ACTION_B;
		String deviceToStatus = "";

		deviceTransition(event.getActionType(), deviceIDList, opID, "",
				devTransAction, deviceToStatus, devTransDto.getFromType(),
				devTransDto.getToType(), devTransDto.getStatusReason(),
				devTransDto.getFromID(), devTransDto.getToID(), "", "设备调拨",
				true, event.getRemoteHostAddress(),-1);
	}

	/**
	 * 设备流转
	 * 
	 * @param deviceIDList :
	 *            设备ID的列表
	 * @param opID :
	 *            当前操作员的ID
	 * @param opBatchNO
	 *            ：当前操作的批处理NO
	 * @param deviceTransAction
	 *            ：设备流转的动作类型
	 * @param deviceToStatus ：
	 *            设备目标状态
	 * @param deviceFromType
	 *            ：设备原来的类型
	 * @param deviceToType
	 *            ：设备目标类型
	 * @param toStatusReason :
	 *            流转原因
	 * @param deviceAddressFromID
	 *            ：设备原来存储地
	 * @param deviceAddressToID ：
	 *            设备目的存储地
	 * @param useFlag
	 *            ：是否使用标志
	 * @param des
	 *            ：当前操作描叙
	 * @param createSysLog ：
	 *            是否创建日志
	 * @param machineName
	 *            ：操作机器名
	 * @param depotID
	 * 			  ：目标仓库，如果为-1，则不进行任何的仓库修改
	 * @return Collection
	 * @throws ServiceException
	 */
	private void deviceTransition(int actionType, Collection deviceIDList,
			int opID, String opBatchNO, String deviceTransAction,
			String deviceToStatus, String deviceFromType, String deviceToType,
			String toStatusReason, int deviceAddressFromID,
			int deviceAddressToID, String useFlag, String des,
			boolean createSysLog, String machineName,int depotID) throws ServiceException {
		
		LogUtility.log(clazz, LogLevel.DEBUG, "设备流转开始");

		// 检查参数
		if (deviceIDList == null || deviceIDList.size() == 0 || opID == 0|| "".equals(deviceTransAction)) {
			LogUtility.log(clazz, LogLevel.WARN, "设备流转参数错误！");
			throw new ServiceException("设备流转参数错误！");
		}

		// 返回值
		ArrayList lstRet = new ArrayList();
		
		String logDes="";
		
		TerminalDeviceHome devHome;
		DeviceTransitionHome devTransHome;
		DeviceTransitionDetailHome devTransDetailHome;
		TerminalDevice dev;
		DeviceTransition devTrans;

		// 记录成功执行的设备
		int idevOK = 0;
		// 记录执行部成功的设备
		int idevError = 0;
		// 批处理ID
		int batchNO = 0;

		// 设备流转T_DeviceTransition
		try {
			// 创建设备流转
			DeviceTransitionDTO devTransDto = new DeviceTransitionDTO();
			devTransDto.setAction(deviceTransAction);
			devTransDto.setBatchNo(opBatchNO);
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			devTransDto.setDeviceNumber(deviceIDList.size());
			devTransDto.setFromID(deviceAddressFromID);
			devTransDto.setFromType(deviceFromType);
			devTransDto.setOperatorID(opID);
			devTransDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			devTransDto.setToID(deviceAddressToID);
			devTransDto.setToType(deviceToType);
	

			devTransHome = HomeLocater.getDeviceTransitionHome();
			devTrans = devTransHome.create(devTransDto);

			// 记录设备操作批处理头信息
			lstRet.add(new String("执行 " + deviceIDList.size() + " 个" + des));

			// 创建设备流转明细T_DeviceTransitionDetail
			Iterator it = deviceIDList.iterator();
			int deviceID;
			while (it.hasNext()) {
				deviceID = ((Integer) it.next()).intValue();
				devHome = HomeLocater.getTerminalDeviceHome();
				dev = devHome.findByPrimaryKey(new Integer(deviceID));

				if(depotID>-1)
					dev.setDepotID(depotID);
				
				// 判断是否可以改变设备的状态
				if (canChangeDeviceStatus(actionType, deviceToStatus,toStatusReason)) {
					// create new detail dto object and set the dto fields
					DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
					detailDto.setBatchID(devTrans.getBatchID().intValue());
					detailDto.setDeviceID(deviceID);
					detailDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
					detailDto.setFromID(dev.getAddressID());
					detailDto.setFromType(dev.getAddressType());
					detailDto.setFromDeviceStatus(dev.getStatus());
					detailDto.setSerialNo(dev.getSerialNo());
					if (!"".equals(deviceToStatus))
						detailDto.setToDeviceStatus(deviceToStatus);

					detailDto.setToID(deviceAddressToID);
					detailDto.setToType(deviceToType);

					devTransDetailHome = HomeLocater.getDeviceTransitionDetailHome();
					devTransDetailHome.create(detailDto);

					if(!"".equals(logDes))logDes=logDes +"；";
					logDes=logDes +"设备" + dev.getDeviceID() +"，序列号"+ dev.getSerialNo()+ "，状态从" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",dev.getStatus()) +"修改到" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",deviceToStatus) ;

					
					// 修改设备的资料
					dev.setStatus(deviceToStatus);
					dev.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					// 好像前台没有更改属主的内容,会导致原有属主信息丢失,注释掉,
					// dev.setAddressID(deviceAddressToID);
					// dev.setDepotID(deviceAddressToID);
					// dev.setAddressType(deviceToType);
					if (!"".equals(useFlag))
						dev.setUseFlag(useFlag);

					lstRet.add(new String("设备: " + dev.getSerialNo() + des+ " 成功!"));
					idevOK++;
				} else { // 状态检验未通过
					lstRet.add(new String("设备: " + dev.getSerialNo() + des+ "失败"));
					idevError++;
				}
			}

			devTrans.setDeviceNumber(idevOK); // 设置实际成功的设备数
			devTrans.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			lstRet.add(new String("成功" + des + ": " + idevOK + "个；发生错误设备： " + idevError + "个"));

			batchNO = devTrans.getBatchID().intValue();
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, des + "定位出错：" + e);
			throw new ServiceException(des + "定位出错！");
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.WARN, des + "查找出错：" + e2);
			throw new ServiceException(des + "查找出错！");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, des + "创建记录出错：" + e3);
			throw new ServiceException(des + "创建记录出错！");
		}

		// 创建系统日志记录
		if (createSysLog) {
			SystemLogRecorder.createSystemLog(machineName, opID, 0,
					SystemLogRecorder.LOGMODULE_LOGISTICS, "修改", des + ",批号:"	+ batchNO + "； 成功：" + idevOK + "；失败：" + idevError,
					SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}

			
		LogUtility.log(clazz, LogLevel.DEBUG, "设备流转结束");
		LogUtility.log(clazz, LogLevel.DEBUG, "执行结果为：" + lstRet);

		// 把执行结果放到ServiceContext里
		this.context.put(Service.PROCESS_RESULT, lstRet);
		this.context.put(Service.SYSTEM_LOG, logDes);
	}
	
	//设备流转 （设备信息修改用,可用于成批修改时记录设备流转）,
	//只记录流转，修改设备在该方法外面操作
	private void deviceTransitionForDeviceInfoModify(int actionType, Collection deviceIDList,
			int opID, String opBatchNO, String deviceTransAction,
			String deviceToStatus, String deviceFromType, String deviceToType,
			String toStatusReason, int deviceAddressFromID,
			int deviceAddressToID, String useFlag, String des,
			boolean createSysLog, String machineName,int depotID) throws ServiceException {
		
		LogUtility.log(clazz, LogLevel.DEBUG, "设备流转开始");

		// 检查参数
		if (deviceIDList == null || deviceIDList.size() == 0 || opID == 0|| "".equals(deviceTransAction)) {
			LogUtility.log(clazz, LogLevel.WARN, "设备流转参数错误！");
			throw new ServiceException("设备流转参数错误！");
		}

		// 返回值
		ArrayList lstRet = new ArrayList();
		
		String logDes="";
		
		TerminalDeviceHome devHome;
		DeviceTransitionHome devTransHome;
		DeviceTransitionDetailHome devTransDetailHome;
		TerminalDevice dev;
		DeviceTransition devTrans;

		// 记录成功执行的设备
		int idevOK = 0;
		// 记录执行部成功的设备
		int idevError = 0;
		// 批处理ID
		int batchNO = 0;

		// 设备流转T_DeviceTransition
		try {
			// 创建设备流转
			DeviceTransitionDTO devTransDto = new DeviceTransitionDTO();
			devTransDto.setAction(deviceTransAction);
			devTransDto.setBatchNo(opBatchNO);
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			devTransDto.setDeviceNumber(deviceIDList.size());
			devTransDto.setFromID(deviceAddressFromID);
			devTransDto.setFromType(deviceFromType);
			devTransDto.setOperatorID(opID);
			devTransDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			devTransDto.setToID(deviceAddressToID);
			devTransDto.setToType(deviceToType);
	

			devTransHome = HomeLocater.getDeviceTransitionHome();
			devTrans = devTransHome.create(devTransDto);

			// 记录设备操作批处理头信息
			lstRet.add(new String("执行 " + deviceIDList.size() + " 个" + des));

			// 创建设备流转明细T_DeviceTransitionDetail
			Iterator it = deviceIDList.iterator();
			int deviceID;
			while (it.hasNext()) {
				deviceID = ((Integer) it.next()).intValue();
				devHome = HomeLocater.getTerminalDeviceHome();
				dev = devHome.findByPrimaryKey(new Integer(deviceID));
				
				// 判断是否可以改变设备的状态
				if (canChangeDeviceStatus(actionType, deviceToStatus,toStatusReason)) {
					// create new detail dto object and set the dto fields
					DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
					detailDto.setBatchID(devTrans.getBatchID().intValue());
					detailDto.setDeviceID(deviceID);
					detailDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
					detailDto.setFromID(dev.getAddressID());
					detailDto.setFromType(dev.getAddressType());
					detailDto.setFromDeviceStatus(dev.getStatus());
					detailDto.setSerialNo(dev.getSerialNo());
					if (!"".equals(deviceToStatus))
						detailDto.setToDeviceStatus(deviceToStatus);

					detailDto.setToID(deviceAddressToID);
					detailDto.setToType(deviceToType);

					devTransDetailHome = HomeLocater.getDeviceTransitionDetailHome();
					devTransDetailHome.create(detailDto);

					if(!"".equals(logDes))logDes=logDes +"；";
					logDes=logDes +"设备" + dev.getDeviceID() +"，序列号"+ dev.getSerialNo()+ "，状态从" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",dev.getStatus()) +"修改到" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",deviceToStatus) ;

					lstRet.add(new String("设备: " + dev.getSerialNo() + des+ " 成功!"));
					idevOK++;
				} else { // 状态检验未通过
					lstRet.add(new String("设备: " + dev.getSerialNo() + des+ "失败"));
					idevError++;
				}
			}

			devTrans.setDeviceNumber(idevOK); // 设置实际成功的设备数
			devTrans.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			lstRet.add(new String("成功" + des + ": " + idevOK + "个；发生错误设备： " + idevError + "个"));

			batchNO = devTrans.getBatchID().intValue();
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, des + "定位出错：" + e);
			throw new ServiceException(des + "定位出错！");
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.WARN, des + "查找出错：" + e2);
			throw new ServiceException(des + "查找出错！");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, des + "创建记录出错：" + e3);
			throw new ServiceException(des + "创建记录出错！");
		}

		// 创建系统日志记录
		if (createSysLog) {
			SystemLogRecorder.createSystemLog(machineName, opID, 0,
					SystemLogRecorder.LOGMODULE_LOGISTICS, "修改", des + ",批号:"	+ batchNO + "； 成功：" + idevOK + "；失败：" + idevError,
					SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		// 把执行结果放到ServiceContext里
		this.context.put(Service.PROCESS_RESULT, lstRet);
		this.context.put(Service.SYSTEM_LOG, logDes);
	}

	/**
	 * 根据D网项目业务规则制定的设备状态跳转图
	 * 
	 * @param actionType :
	 *            操作类型
	 * @param devStatus :
	 *            目标状态
	 * @param statusReason ：
	 *            流转原因
	 * @return
	 */
	private boolean canChangeDeviceStatus(int actionType, String devStatus,
			String statusReason) {
		boolean bRet = true;

		if (actionType == 0) {
			bRet = true;
			return bRet;
		}
		if (devStatus == null) {
			bRet = false;
			return bRet;
		}
		if (devStatus.length() != 1) {
			bRet = false;
			return bRet;
		}

		String allowStatus = "";

		// 找到对应Action & StatusReason 组合所允许的设备状态范围
		switch (actionType) {
		// 入库
		case LogisticsEJBEvent.DEVICE_IN_STOCK:
			if (statusReason
					.equals(CommonConstDefinition.DEVICE_STOCKINREASON_BACK)) { // 退回
				allowStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
			} else if (statusReason
					.equals(CommonConstDefinition.DEVICE_STOCKINREASON_REPAIR)) { // 修复
				allowStatus = CommonConstDefinition.DEVICE_STATUS_STOCK;
			} else if (statusReason
					.equals(CommonConstDefinition.DEVICE_STOCKINREASON_REPAIR)) { // 损坏
				allowStatus = CommonConstDefinition.DEVICE_STATUS_REPAIRING; // 损坏不进行状态检验
			} else { // 其他statusreason，未定义，不进行状态检验
				allowStatus = devStatus;
			}
			break;

		// 出库
		case LogisticsEJBEvent.DEVICE_OUT_STOCK:
			allowStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
			break;

		// 送修
		case LogisticsEJBEvent.DEVICE_REPAIR:
			allowStatus = CommonConstDefinition.DEVICE_STATUS_WAIT4REPAIR;
			break;

		// 报废
		case LogisticsEJBEvent.DEVICE_INVALIDATE:
			allowStatus = CommonConstDefinition.DEVICE_STATUS_STOCK + "|"
					+ CommonConstDefinition.DEVICE_STATUS_WAIT4REPAIR + "|"
					+ CommonConstDefinition.DEVICE_STATUS_REPAIRING;
			break;

		// 调拨
		case LogisticsEJBEvent.DEVICE_TRANSITION:
			allowStatus = devStatus; // 不判断原因
			break;

		// 手工改变状态
		case LogisticsEJBEvent.DEVICE_MANULCHANGE_STATUS:
			allowStatus = devStatus; // 不判断原因
			break;
		
		//修改设备信息
		case LogisticsEJBEvent.DEVICE_MODIFY:
			allowStatus = devStatus; // 不判断原因
			break;
		}

		if (allowStatus.indexOf(devStatus) == -1) {
			bRet = false;
		}
		return bRet;
	}

	/**
	 * 设备配对与授权
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devMatchAndPreauthTrans(DeviceStockEJBEvent event)
	throws ServiceException {
//DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
DeviceBatchPreauthDTO deviceBatchPreauthDTO = event.getDeviceBatchPreauthDTO();
HashMap deviceMap = null;
ArrayList allList = new ArrayList();
int opID = event.getOperatorID();
deviceBatchPreauthDTO.setOpId(opID);

// 得到配对的序列号
String strSerials = deviceBatchPreauthDTO.getFileName();
String strProductList = "";
boolean isCheckPreAuth = true; //是否检查授权标志？
if(strSerials.indexOf("$")!= -1)
{
	strProductList = strSerials.substring(strSerials.indexOf("$") + 1,strSerials.length());
	strSerials = strSerials.substring(0,strSerials.indexOf("$"));
}
else
    isCheckPreAuth = false;

String[] serialsArray = strSerials.split("\r\n");

for (int i = 0; i < serialsArray.length; i++) {
	String doubleDevice = serialsArray[i].toString();

	deviceMap = checkExist(doubleDevice,isCheckPreAuth);

	allList.add(deviceMap);

}
if (event.isDoPost())
	createDeviceAuth(event,deviceBatchPreauthDTO, allList,strProductList);

// 把执行结果放到ServiceContext里
this.context.put(Service.PROCESS_RESULT, allList);

}
	/*
	产生设备授权记录.
*/
private void createDeviceAuth(DeviceStockEJBEvent event,DeviceBatchPreauthDTO devBatchPreAuthDto,ArrayList allList,String productList)throws ServiceException 
{
	boolean bPreAuth = false;
	if(productList.length()>0)
		bPreAuth = true;
	String[] productArray = productList.split(";");
	
	DeviceBatchPreauthHome devBatchPreauthHome;
	
	
	DeviceBatchPreauth devBatchPreauth = null;
	
	LogUtility.log(clazz,LogLevel.DEBUG,"设备授权开始");
	if (allList == null || allList.size() == 0) {
		LogUtility.log(clazz, LogLevel.WARN, "设备授权参数错误！");
		throw new ServiceException("设备授权参数错误！");
	}
	devBatchPreAuthDto.setStatus("V");
	devBatchPreAuthDto.setFileName("");
	
	int deviceNumber = allList.size();
	devBatchPreAuthDto.setTotalDeviceNum(deviceNumber);
	
	if (devBatchPreAuthDto.getOperationType() == null	|| "".equals(devBatchPreAuthDto.getOperationType()))
		devBatchPreAuthDto.setOperationType("AM");
	devBatchPreAuthDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
	devBatchPreAuthDto.setOpId(event.getOperatorID());
	devBatchPreAuthDto.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(event.getOperatorID()));
	try
	{
		DeviceBatchPreauth devBatchPreauth2 = null;
		if(bPreAuth)
		{
			DeviceBatchPreauthHome devBatchPreauthHome2;
			DeviceBatchPreauthDTO batchPreauthDto = new DeviceBatchPreauthDTO();

			if (batchPreauthDto.getOperationType() == null	|| "".equals(batchPreauthDto.getOperationType()))
				batchPreauthDto.setOperationType("AP");
			batchPreauthDto.setTotalDeviceNum(deviceNumber);
			batchPreauthDto.setStatus("V");
			batchPreauthDto.setFileName("");		
			batchPreauthDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			batchPreauthDto.setOpId(event.getOperatorID());
			batchPreauthDto.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(event.getOperatorID()));
			batchPreauthDto.setReferSheetSerialNO(devBatchPreAuthDto.getReferSheetSerialNO());
			batchPreauthDto.setDescription(devBatchPreAuthDto.getDescription());
			devBatchPreauthHome2 = HomeLocater.getDeviceBatchPreauthHome();
			LogUtility.log(clazz, LogLevel.DEBUG, "设备配对dto为" + devBatchPreAuthDto);
			devBatchPreauth2 = devBatchPreauthHome2.create(batchPreauthDto);
		}

		devBatchPreauthHome = HomeLocater.getDeviceBatchPreauthHome();
		LogUtility.log(clazz, LogLevel.DEBUG, "设备授权dto为" + devBatchPreAuthDto);
		devBatchPreauth = devBatchPreauthHome.create(devBatchPreAuthDto);
		for (int i = 0; i < allList.size(); i++) {
			HashMap deviceMap = (HashMap) allList.get(i);
			Iterator itDevice = deviceMap.entrySet().iterator();
			while (itDevice.hasNext()) {

				Map.Entry deviceMapSet = (Map.Entry) itDevice.next();
				
				TerminalDeviceDTO mainDto = (TerminalDeviceDTO) deviceMapSet.getKey();
				// 初始化配对设备dto
				TerminalDeviceDTO subDto = (TerminalDeviceDTO) deviceMapSet.getValue();
				createMatchOperation(devBatchPreauth, mainDto, subDto);
				if(bPreAuth)
					createPreauthOperation(devBatchPreauth2,mainDto,productArray);
			}
		}
	}
	catch (HomeFactoryException e) {
		LogUtility.log(clazz, LogLevel.ERROR, e);
		throw new ServiceException("设备序列号定位错误");
	}
	catch (CreateException e3) {
		LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
		throw new ServiceException("创建记录出错！");
	} catch (FinderException e) {
		LogUtility.log(clazz, LogLevel.ERROR, e);
		throw new ServiceException("找不到相关的设备");

	}
}
private void createMatchOperation(DeviceBatchPreauth devBatchPreauth,
		TerminalDeviceDTO mainDto, TerminalDeviceDTO subDto)
		throws HomeFactoryException, CreateException, FinderException {
	// 初始化设备流转明细dto
	DevicePrematchRecordDTO detailDto1 = new DevicePrematchRecordDTO();
	DevicePrematchRecordHome devPrematchHome1;
	TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
  	TerminalDevice terminalDevice1=terminalDeviceHome.findByPrimaryKey(new Integer(mainDto.getDeviceID()));
  	// add by chenjiang
  	String deviceModel=terminalDevice1.getDeviceModel();
  	 
  	int productId = BusinessUtility.getProductIDByModel(deviceModel);

	detailDto1.setBatchId(devBatchPreauth.getBatchId().intValue());
	detailDto1.setOperationtype(devBatchPreauth.getOperationType());
	detailDto1.setOpId(devBatchPreauth.getOpId());
	detailDto1.setOrgId(devBatchPreauth.getOrgId());
	detailDto1.setScDeviceID(mainDto.getDeviceID());
	detailDto1.setScSerialNo(mainDto.getSerialNo());
	detailDto1.setScdeviceModel(mainDto.getDeviceModel());
	detailDto1.setStbDeviceID(subDto.getDeviceID());
	detailDto1.setStbSerialNO(subDto.getSerialNo());
	detailDto1.setStbDeviceModel(subDto.getDeviceModel());
	detailDto1.setCreateTime(TimestampUtility.getCurrentTimestamp());
	detailDto1.setStatus("V");
	
	devPrematchHome1 = HomeLocater.getDevicePrematchRecordHome();

	LogUtility.log(clazz, LogLevel.DEBUG, "设备配对DTO为" + detailDto1);
	devPrematchHome1.create(detailDto1);
	// 更新设备的配对标志
	TerminalDeviceHome devHome1 = HomeLocater.getTerminalDeviceHome();
	TerminalDevice dev1 = devHome1.findByPrimaryKey(new Integer(mainDto
			.getDeviceID()));
	dev1.setMatchFlag("Y");
	// 设置主设备的配对设备id
	dev1.setMatchDeviceID(subDto.getDeviceID());

	// 配对设备	
	
	TerminalDevice dev2 = devHome1.findByPrimaryKey(new Integer(subDto.getDeviceID()));
	dev2.setMatchFlag("Y");
	dev2.setMatchDeviceID(mainDto.getDeviceID());
	// 生成配对事件
	int customerID = 0;
	int accountID = 0;
	int serviceAccountID = 0;
	if (devBatchPreauth.getOperationType().equalsIgnoreCase("AM")) {
		SystemEventRecorder.addEvent4DeviceMatch(
				SystemEventRecorder.SE_CA_MATCH, customerID, accountID,
				serviceAccountID, mainDto.getDeviceID(), mainDto
						.getSerialNo(), subDto.getDeviceID(), subDto
						.getSerialNo(), devBatchPreauth.getOpId(),productId, "C");
	}
}

	/**
	 * @param devTransDto
	 * @param allList
	 */
	private void creatDeviceTran(DeviceTransitionDTO devTransDto,ArrayList allList) throws ServiceException {
		DeviceTransitionHome devTransHome;
		DeviceTransition devTrans = null;
		String description = new String();
		String[] productArray = null;
		LogUtility.log(clazz, LogLevel.DEBUG, "设备流转开始");

		// 检查参数
		if (allList == null || allList.size() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "设备流转参数错误！");
			throw new ServiceException("设备流转参数错误！");
		}
		devTransDto.setFromType(null);
		// 设备数量
		int deviceNumber = allList.size();

		// 设置action
		if (devTransDto.getAction() == null	|| "".equals(devTransDto.getAction()))
			devTransDto.setAction("DM");
		// 如果action=DP,则需要把产品名称设置到description中
		if (devTransDto.getDescription() != null&& !"".equals(devTransDto.getDescription())) {
			productArray = devTransDto.getDescription().split(";");
			for (int i = 0; i < productArray.length; i++) {
				String productName = BusinessUtility.getProductDTOByProductID(Integer
						.valueOf(productArray[i]).intValue()).getProductName();		 
				description = description + productName + ";";
			}
			devTransDto.setDescription(description);
		}
		
		devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
		devTransDto.setStatus("V");

		try {
			devTransHome = HomeLocater.getDeviceTransitionHome();
			LogUtility.log(clazz, LogLevel.DEBUG, "设备交易dto为" + devTransDto);
			// 如果action="DP",表示进行了2种操作，配对与预授权，所以要分别创建2条流转记录
			if ("DP".equals(devTransDto.getAction())) {
				// 配对流转记录
				devTransDto.setAction("DM");
				devTransDto.setDeviceNumber(deviceNumber * 2);

				devTrans = devTransHome.create(devTransDto);
				devTransDto.setAction("DP");

				// 生成配对明细纪律

				for (int i = 0; i < allList.size(); i++) {
					HashMap deviceMap = (HashMap) allList.get(i);
					Iterator itDevice = deviceMap.entrySet().iterator();
					while (itDevice.hasNext()) {

						Map.Entry deviceMapSet = (Map.Entry) itDevice.next();
						
						TerminalDeviceDTO mainDto = (TerminalDeviceDTO) deviceMapSet.getKey();
						// 初始化配对设备dto
						TerminalDeviceDTO subDto = (TerminalDeviceDTO) deviceMapSet.getValue();
						createMatchOperation(devTrans, mainDto, subDto);

					}
				}
			}
			if ("DP".equals(devTransDto.getAction())) {
				devTransDto.setDeviceNumber(deviceNumber);
				devTrans = devTransHome.create(devTransDto);
				// 生成预授权明细纪律

				for (int i = 0; i < allList.size(); i++) {
					HashMap deviceMap = (HashMap) allList.get(i);
					Iterator itDevice = deviceMap.entrySet().iterator();

					while (itDevice.hasNext()) {

						Map.Entry deviceMapSet = (Map.Entry) itDevice.next();
						// 初始化设备流转明细dto
						TerminalDeviceDTO mainDto = (TerminalDeviceDTO) deviceMapSet.getKey();
						// 初始化配对设备dto
						TerminalDeviceDTO subDto = (TerminalDeviceDTO) deviceMapSet.getValue();
						createPreauthOperation(devTrans, mainDto, subDto,productArray);
					}
				}
			}
			if ("DM".equals(devTransDto.getAction())) {
				devTransDto.setDeviceNumber(deviceNumber * 2);
				devTrans = devTransHome.create(devTransDto);
				for (int i = 0; i < allList.size(); i++) {
					HashMap deviceMap = (HashMap) allList.get(i);
					Iterator itDevice = deviceMap.entrySet().iterator();

					while (itDevice.hasNext()) {

						Map.Entry deviceMapSet = (Map.Entry) itDevice.next();
		
						TerminalDeviceDTO mainDto = (TerminalDeviceDTO) deviceMapSet.getKey();
						// 初始化配对设备dto
						TerminalDeviceDTO subDto = (TerminalDeviceDTO) deviceMapSet.getValue();
						createMatchOperation(devTrans, mainDto, subDto);
					}
				}

			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		}
	}

	/**
	 * 配对生成明细流转与系统事件
	 * 
	 * @param devTrans
	 *            设备流转远程接口
	 * @param mainDto
	 *            主设备
	 * @param subDto
	 *            配对设备
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */
	private void createMatchOperation(DeviceTransition devTrans,
			TerminalDeviceDTO mainDto, TerminalDeviceDTO subDto)
			throws HomeFactoryException, CreateException, FinderException {
		// 初始化设备流转明细dto
		DeviceTransitionDetailDTO detailDto1 = new DeviceTransitionDetailDTO();
		DeviceTransitionDetailHome devTransDetailHome1;
		DeviceTransitionDetailHome devTransDetailHome2;
		TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
	  	TerminalDevice terminalDevice1=terminalDeviceHome.findByPrimaryKey(new Integer(mainDto.getDeviceID()));
	  	// add by chenjiang
	  	String deviceModel=terminalDevice1.getDeviceModel();
	  	 
	  	int productId = BusinessUtility.getProductIDByModel(deviceModel);
	  	 
		detailDto1.setDeviceID(mainDto.getDeviceID());
		detailDto1.setBatchID(devTrans.getBatchID().intValue());
		detailDto1.setSerialNo(mainDto.getSerialNo());
		detailDto1.setStatus("V");
		
		detailDto1.setFromType(terminalDevice1.getAddressType());
		detailDto1.setFromID(terminalDevice1.getAddressID());
		detailDto1.setFromDeviceStatus(terminalDevice1.getStatus());
		detailDto1.setToType(terminalDevice1.getAddressType());
		detailDto1.setToID(terminalDevice1.getAddressID());
		detailDto1.setToDeviceStatus(terminalDevice1.getStatus());
		

		devTransDetailHome1 = HomeLocater.getDeviceTransitionDetailHome();
		LogUtility.log(clazz, LogLevel.DEBUG, "主要设备交易明细dto为" + detailDto1);
		devTransDetailHome1.create(detailDto1);
		// 更新设备的配对标志
		TerminalDeviceHome devHome1 = HomeLocater.getTerminalDeviceHome();
		TerminalDevice dev1 = devHome1.findByPrimaryKey(new Integer(mainDto
				.getDeviceID()));
		dev1.setMatchFlag("Y");
		// 设置主设备的配对设备id
		dev1.setMatchDeviceID(subDto.getDeviceID());

		// 配对设备
		TerminalDevice terminalDevice2=terminalDeviceHome.findByPrimaryKey(new Integer(subDto.getDeviceID()));
		DeviceTransitionDetailDTO detailDto2 = new DeviceTransitionDetailDTO();
		detailDto2.setBatchID(devTrans.getBatchID().intValue());
		detailDto2.setDeviceID(subDto.getDeviceID());

		detailDto2.setSerialNo(subDto.getSerialNo());
		detailDto2.setStatus("V");
		
		detailDto2.setFromType(terminalDevice2.getAddressType());
		detailDto2.setFromID(terminalDevice2.getAddressID());
		detailDto2.setFromDeviceStatus(terminalDevice2.getStatus());
		detailDto2.setToType(terminalDevice2.getAddressType());
		detailDto2.setToID(terminalDevice2.getAddressID());
		detailDto2.setToDeviceStatus(terminalDevice2.getStatus());
		
		
		
		devTransDetailHome2 = HomeLocater.getDeviceTransitionDetailHome();
		LogUtility.log(clazz, LogLevel.DEBUG, "配对设备交易明细dto为" + detailDto2);
		devTransDetailHome2.create(detailDto2);
		TerminalDevice dev2 = devHome1.findByPrimaryKey(new Integer(subDto.getDeviceID()));
		dev2.setMatchFlag("Y");
		dev2.setMatchDeviceID(mainDto.getDeviceID());
		// 生成配对事件
		int customerID = 0;
		int accountID = 0;
		int serviceAccountID = 0;
		if (devTrans.getAction().equalsIgnoreCase("DM")) {
			SystemEventRecorder.addEvent4DeviceMatch(
					SystemEventRecorder.SE_CA_MATCH, customerID, accountID,
					serviceAccountID, mainDto.getDeviceID(), mainDto
							.getSerialNo(), subDto.getDeviceID(), subDto
							.getSerialNo(), devTrans.getOperatorID(),productId, "C");
		}
	}

	/**
	 * 预授权生成明细流转与系统事件
	 * 
	 * @param devTrans
	 *            设备流转远程接口
	 * @param mainDto
	 *            主设备
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 * @throws ServiceException 
	 */
	private void createPreauthOperation(DeviceTransition devTrans,
			TerminalDeviceDTO mainDto, TerminalDeviceDTO subDto,
			String[] productArray) throws HomeFactoryException,
			CreateException, FinderException, ServiceException {
		TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
	  	TerminalDevice terminalDevice1=terminalDeviceHome.findByPrimaryKey(new Integer(mainDto.getDeviceID()));
		// 初始化设备流转明细dto
		DeviceTransitionDetailDTO detailDto1 = new DeviceTransitionDetailDTO();
		DeviceTransitionDetailHome devTransDetailHome1;

		detailDto1.setDeviceID(mainDto.getDeviceID());
		detailDto1.setBatchID(devTrans.getBatchID().intValue());
		detailDto1.setSerialNo(mainDto.getSerialNo());
		detailDto1.setStatus("V");
		
		detailDto1.setFromType(terminalDevice1.getAddressType());
		detailDto1.setFromID(terminalDevice1.getAddressID());
		detailDto1.setFromDeviceStatus(terminalDevice1.getStatus());
		detailDto1.setToType(terminalDevice1.getAddressType());
		detailDto1.setToID(terminalDevice1.getAddressID());
		detailDto1.setToDeviceStatus(terminalDevice1.getStatus());
		
		devTransDetailHome1 = HomeLocater.getDeviceTransitionDetailHome();
		LogUtility.log(clazz, LogLevel.DEBUG, "主要设备交易明细dto为" + detailDto1);
		devTransDetailHome1.create(detailDto1);
		// 更新设备的配对标志
		TerminalDeviceHome devHome1 = HomeLocater.getTerminalDeviceHome();
		TerminalDevice dev1 = devHome1.findByPrimaryKey(new Integer(mainDto
				.getDeviceID()));
		dev1.setPreAuthorization("Y");
		
		//产生事件和记录授权 modify by jason 2007-2-7
		checkSCStatusCanOP(mainDto.getDeviceID());
		OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
		if (productArray != null) {
			for (int j = 0; j < productArray.length; j++) {
				int productID=Integer.valueOf(productArray[j]).intValue();
				LogUtility.log(clazz, LogLevel.DEBUG, "其中每一个产品ID是" + productID);
				// 预授权事件
				SystemEventRecorder.addEvent4DeviceMatchPreauth(
						SystemEventRecorder.DEVICE_PREAUTH, productID, mainDto.getDeviceID(), mainDto.getSerialNo(), 
						subDto.getDeviceID(), subDto.getSerialNo(), devTrans.getOperatorID(), "C");
			}
		}

	}

	/**
	 * @param doubleDevice
	 * @throws ServiceException
	 * @throws FinderException
	 */
	private HashMap checkExist(String doubleDevice,boolean isCheckPreAuth) throws ServiceException {

		try{
			HashMap deviceMap = null;
			System.out.println(doubleDevice);
			String[] deviceSerials = doubleDevice.split("/");
			System.out.println("OrgMapfrom");
			deviceMap = orgMap(deviceSerials[0], deviceSerials[1],isCheckPreAuth);
			System.out.println("OrgMapTo");
			return deviceMap;
		}
		catch(ServiceException ex)
		{
			throw ex;
		}
		catch(Exception e){
			throw new ServiceException("您输入的设备信息有误，无法区分设备！");
		}
	}

	private HashMap orgMap(String keyDevice, String subDevice,boolean isCheckPreAuth)
	throws ServiceException {
try {
	Collection dev1 = null;
	Collection dev2 = null;
	HashMap deviceMap = new HashMap();
	TerminalDeviceHome devHome = HomeLocater.getTerminalDeviceHome();
	dev1 = devHome.findBySerialNo(keyDevice);
	TerminalDeviceDTO keyTerminalDeviceDto = new TerminalDeviceDTO();
	TerminalDeviceDTO subTerminalDeviceDto = new TerminalDeviceDTO();

	if (dev1 == null || dev1.isEmpty())
		throw new ServiceException("序列号为 " + keyDevice + "的设备不存在");
	Iterator currentOldIterator = dev1.iterator();
	while (currentOldIterator.hasNext()) {

		TerminalDevice terminaDevice = (TerminalDevice) currentOldIterator
				.next();
		String status = terminaDevice.getStatus();
		String matchFlag = terminaDevice.getMatchFlag();
		String preAuthFlag = terminaDevice.getPreAuthorization();

		String deviceClass1 = terminaDevice.getDeviceClass();

		if (!"SC".equalsIgnoreCase(deviceClass1))
			throw new ServiceException("序列号为"
					+ terminaDevice.getSerialNo() + "的设备不是智能卡");

		if (status == null || "".equals(status))
			throw new ServiceException("序列号为"
					+ terminaDevice.getSerialNo() + "的设备状态为空");

		else if (!"W".equalsIgnoreCase(status.trim())
				&& !"S".equalsIgnoreCase(status.trim()))
			throw new ServiceException("序列号为"
					+ terminaDevice.getSerialNo() + "的设备状态不对");

		if ("Y".equals(matchFlag)) {
			throw new ServiceException("序列号为"
					+ terminaDevice.getSerialNo() + "的设备已经配对过了");
		}
		if(isCheckPreAuth)
		{

		    if ("Y".equals(preAuthFlag)) {
			throw new ServiceException("序列号为"
					+ terminaDevice.getSerialNo() + "的设备已经授权过了");
		    }
		}

		keyTerminalDeviceDto.setDeviceID(terminaDevice.getDeviceID()
				.intValue());
		keyTerminalDeviceDto.setSerialNo(terminaDevice.getSerialNo());
		keyTerminalDeviceDto.setStatus(terminaDevice.getStatus());

		// 为了判断该设备所在的地址类型:B,用户;D,仓库;T,组织机构
		keyTerminalDeviceDto.setAddressType(terminaDevice
				.getAddressType());
		keyTerminalDeviceDto.setAddressID(terminaDevice.getAddressID());
		keyTerminalDeviceDto.setDeviceModel(terminaDevice
				.getDeviceModel());

	}
	dev2 = devHome.findBySerialNo(subDevice);

	if (dev2 == null || dev2.isEmpty())
		throw new ServiceException("序列号为 " + subDevice + "的设备不存在");

	Iterator currentOldIterator1 = dev2.iterator();
	while (currentOldIterator1.hasNext()) {

		TerminalDevice terminaDevice = (TerminalDevice) currentOldIterator1
				.next();
		String status = terminaDevice.getStatus();
		String matchFlag = terminaDevice.getMatchFlag();
		String deviceClass2 = terminaDevice.getDeviceClass();
		if (!"STB".equalsIgnoreCase(deviceClass2))
			throw new ServiceException("序列号为"
					+ terminaDevice.getSerialNo() + "的设备不是机顶盒");
		if (!status.equalsIgnoreCase("W")
				&& !status.equalsIgnoreCase("S"))
			throw new ServiceException("序列号为"
					+ terminaDevice.getSerialNo() + "的设备状态不对");
		if ("Y".equals(matchFlag)) {
			throw new ServiceException("序列号为"
					+ terminaDevice.getSerialNo() + "的设备已经配对过了");
		}
	
		subTerminalDeviceDto.setDeviceID(terminaDevice.getDeviceID()
				.intValue());
		subTerminalDeviceDto.setSerialNo(terminaDevice.getSerialNo());
		subTerminalDeviceDto.setStatus(terminaDevice.getStatus());
		subTerminalDeviceDto.setDeviceModel(terminaDevice
				.getDeviceModel());
		subTerminalDeviceDto.setAddressType(terminaDevice
				.getAddressType());

		subTerminalDeviceDto.setAddressID(terminaDevice.getAddressID());

	}
	deviceMap.put(keyTerminalDeviceDto, subTerminalDeviceDto);

	return deviceMap;
	} catch (HomeFactoryException e) {
		LogUtility.log(clazz, LogLevel.ERROR, e);
		throw new ServiceException("设备序列号定位错误");
	} catch (FinderException e) {
		LogUtility.log(clazz, LogLevel.ERROR, e);
		throw new ServiceException("找不到相关的设备");
	
	}
}
	/**
	 * 用于开户、增机、设备更换清除设备的预授权记录的时候,先取出其中的产品id 
	 * @param deviceID
	 * @throws ServiceException
	 */
	public List loadDeviceList(int deviceID)
		throws ServiceException {
		List devicelist=new ArrayList();
		try{
			OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
			Collection authroizeRecordCol=ossAuthorizationHome.findByDeviceID(deviceID);
			if(authroizeRecordCol!=null && !authroizeRecordCol.isEmpty()){
				Iterator authroizeRecordIter=authroizeRecordCol.iterator();
				while(authroizeRecordIter.hasNext()){
					devicelist.add(new Integer(((OssAuthorization)authroizeRecordIter.next()).getProductID()));
				}
			}
			TerminalDeviceHome tdhome=HomeLocater.getTerminalDeviceHome();
			TerminalDevice td=tdhome.findByPrimaryKey(new Integer(deviceID));
			td.setPreAuthorization(null);
			return devicelist;
		}catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备预授权记录定位错误");
		}catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到设备预授权记录");
		}
	}
	/**
	 * 用于开户、增机、设备更换的时候清除设备的预授权记录 add by Yangchen 2008/06/20
	 * @param deviceID
	 * @throws ServiceException
	 */
	public void devPreauthTrans(int deviceID)
		throws ServiceException {
		try{
			OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
			Collection authroizeRecordCol=ossAuthorizationHome.findByDeviceID(deviceID);
			if(authroizeRecordCol!=null && !authroizeRecordCol.isEmpty()){
				Iterator authroizeRecordIter=authroizeRecordCol.iterator();
				while(authroizeRecordIter.hasNext()){
					((OssAuthorization)authroizeRecordIter.next()).remove();
				}
			}
		}catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备预授权记录定位错误");
		}
		catch (RemoveException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("删除设备预授权记录搓搓");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到设备预授权记录");
		}
	}
	/**
	 * 单个设备授权
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devPreauthTrans(DeviceStockEJBEvent event)
			throws ServiceException {
		// 设备交易home接口
		DeviceTransitionHome devTransHome;
		// 远程接口
		DeviceTransition devTrans;

		TerminalDevice terminalDevice;
		// 设备交易明细接口
		DeviceTransitionDetailHome devTransDetailHome;

		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();

		String[] productArray = null;
		// 取出deviceId
		int deviceId = devTransDto.getDeviceNumber();
		int opID = event.getOperatorID();

		devTransDto.setOperatorID(opID);

		// 得到智能卡的序列号
		String strSerials = devTransDto.getFromType();
		devTransDto.setFromType(null);

		// 设备个数
		devTransDto.setDeviceNumber(1);
		// 设置action
		devTransDto.setAction("DP");

		// 设置创建时间
		devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
		// 设置状态
		devTransDto.setStatus("V");
		// 分析productlist
		/*****************************add by yangchen 2008/06/20 start ***************************************/
		String productFroPreAuth=devTransDto.getDescription();
		/*****************************add by yangchen 2008/06/20 start ***************************************/
		if (devTransDto.getDescription() != null
				&& !"".equals(devTransDto.getDescription())) {
			productArray = devTransDto.getDescription().split(";");
			String description = new String();
			for (int i = 0; i < productArray.length; i++) {
				String productName = BusinessUtility.getProductDTOByProductID(Integer
						.valueOf(productArray[i]).intValue()).getProductName();
				description = description + productName + ";";
			}
			devTransDto.setDescription(description);
		}

		try {
			// 取得设备home 接口
			TerminalDeviceHome devHome = HomeLocater.getTerminalDeviceHome();

			// 得到设备的远程接口
			terminalDevice = devHome.findByPrimaryKey(new Integer(deviceId));
			/*****************************remove start  YANGCHEN 2008/06/20***************************************/
			//授权处理去更新该标志
			// 更新与授权标志
			//terminalDevice.setPreAuthorization("Y");
			/*****************************remove END YANGCHEN 2008/06/20***************************************/
			devTransHome = HomeLocater.getDeviceTransitionHome();
			LogUtility.log(clazz, LogLevel.DEBUG, "设备交易dto为" + devTransDto);
			// 创建设备流转记录头记录
			devTrans = devTransHome.create(devTransDto);
			// 创建设备流转明细纪录
			DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO(); // initialize
			// the
			// detail
			// dto
			// 设置batchId
			detailDto.setBatchID(devTrans.getBatchID().intValue());
			// 设置 deviceId
			detailDto.setDeviceID(deviceId);
			// 设置序列号
			detailDto.setSerialNo(strSerials);
			// 设置状态
			detailDto.setStatus("V");
			
			detailDto.setFromType(terminalDevice.getAddressType());
			detailDto.setFromID(terminalDevice.getAddressID());
			detailDto.setFromDeviceStatus(terminalDevice.getStatus());
			detailDto.setToType(terminalDevice.getAddressType());
			detailDto.setToID(terminalDevice.getAddressID());
			detailDto.setToDeviceStatus(terminalDevice.getStatus());
			
			LogUtility.log(clazz, LogLevel.DEBUG, "设备明细交易dto为" + detailDto);
			devTransDetailHome = HomeLocater.getDeviceTransitionDetailHome();
			// 创建明细纪录
			devTransDetailHome.create(detailDto);
			/*****************************modify by yangchen 2008/06/20 start ***************************************/
			/*****************************remove start ***************************************/
			
			//创建系统事件和记录授权记录
			/*checkSCStatusCanOP(deviceId);
			OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
			if (productArray != null) {
				for (int j = 0; j < productArray.length; j++) {
					int productID=Integer.valueOf(productArray[j]).intValue();
					LogUtility.log(clazz, LogLevel.DEBUG, "其中每一个产品ID是"	+ productID);
					SystemEventRecorder.addEvent4DevicePreauth(SystemEventRecorder.DEVICE_PREAUTH, 
							productID,detailDto.getDeviceID(), detailDto.getSerialNo(),devTransDto.getOperatorID(), "C");
					
//					Collection ossRes=ossAuthorizationHome.findByDeviceSNAndProductID("" +deviceId, productID, 
//							CommonConstDefinition.GENERALSTATUS_VALIDATE);
//					if(ossRes!=null && ossRes.size()>0){
//						OssAuthorization ossca=(OssAuthorization)ossRes.iterator().next();
//						ossca.setOperate(CommonConstDefinition.OSS_AUTHORIZATION_OPERATE_A);
//						ossca.setDtLastmod(TimestampUtility.getCurrentTimestamp());
//					}
//					else{
//						//支持设备授权状态管理
//						OssAuthorizationDTO ossAuthorizationDTO=new OssAuthorizationDTO();
//						ossAuthorizationDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
//						ossAuthorizationDTO.setDeviceSN("" +deviceId);
//						ossAuthorizationDTO.setOperate(CommonConstDefinition.OSS_AUTHORIZATION_OPERATE_A);
//						ossAuthorizationDTO.setOpID(opID);
//						ossAuthorizationDTO.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(opID));
//						ossAuthorizationDTO.setProductID(productID);
//						ossAuthorizationDTO.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
//						ossAuthorizationHome.create(ossAuthorizationDTO);
//					}
				}
			}
			*/
			/*****************************remove end ***************************************/
			/*****************************add start ***************************************/
			//记录设备的预授权
			DeviceBatchPreauthDTO deviceBatchPreauthDTO = new DeviceBatchPreauthDTO();
			deviceBatchPreauthDTO.setReferSheetSerialNO(devTransDto.getBatchNo());
			//由于这个值在后面的处理中会被清空，所以这里不设置值
			deviceBatchPreauthDTO.setFileName("");
			deviceBatchPreauthDTO.setDescription(devTransDto.getDescription());
			deviceBatchPreauthDTO.setOpId(opID);
			
			if(devTransDto.getDescription()==null ||"".equals(devTransDto.getDescription()))
				throw new ServiceException("没有指定授权的产品");
			ArrayList allList = new ArrayList();
			allList.add(checkAuth(strSerials));
			//创建授权事件已经授权记录
			createAuthRecord(event,deviceBatchPreauthDTO, allList,productFroPreAuth);
			/*****************************remove end ***************************************/
			/*****************************modify by yangchen 2008/06/20 end ***************************************/
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		}
	}

	/**
	 * 设备配对解除
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devMatchTrans(DeviceStockEJBEvent event)
			throws ServiceException {

			ArrayList allList = new ArrayList();
			String strSNArray = event.getDeviceBatchPreauthDTO().getFileName();
			String[] serialsArray = strSNArray.split("\r\n");
			
			for(int i=0;i<serialsArray.length;i++)
			{		
				if(serialsArray[i].length()>0)
				{
					String DeviceSN = serialsArray[i];				
					allList.add(checkUnmatch(DeviceSN));
				}
			}
			if (event.isDoPost())
				createDeviceUnmatch(event,allList);
			this.context.put(Service.PROCESS_RESULT, allList);
	}
	public TerminalDeviceDTO checkUnmatch(String deviceSN)throws ServiceException
	{
		try
		{
			TerminalDeviceHome devHome = HomeLocater.getTerminalDeviceHome();
			Collection devCol = devHome.findBySerialNo(deviceSN);
			Iterator itDev = devCol.iterator();
			if(!itDev.hasNext())
				throw new ServiceException("该设备不存在");
			TerminalDevice terDeviceSC = (TerminalDevice)itDev.next();
			if (terDeviceSC == null)
				throw new ServiceException("该设备不存在 序列号=" + terDeviceSC.getSerialNo());
			if (!"Y".equals(terDeviceSC.getMatchFlag()))
				throw new ServiceException("该设备未配对 序列号=" + terDeviceSC.getSerialNo());
			if (!"S".equals(terDeviceSC.getStatus()) && !"W".equals(terDeviceSC.getStatus()))
				throw new ServiceException("该设备状态不为待售或者库存 序列号=" + terDeviceSC.getSerialNo());
			if (terDeviceSC.getDeviceClass().equalsIgnoreCase("STB")) {
				throw new ServiceException("该设备类型不正确,请选择智能卡 序列号=" + terDeviceSC.getSerialNo());
			}
	
			TerminalDeviceDTO terDto= new TerminalDeviceDTO();
			terDto.setDeviceID(terDeviceSC.getDeviceID().intValue());
			terDto.setMatchDeviceID(terDeviceSC.getMatchDeviceID());
			terDto.setStatus(terDeviceSC.getStatus());
			terDto.setSerialNo(terDeviceSC.getSerialNo());
			terDto.setDeviceModel(terDeviceSC.getDeviceModel());
			return terDto;
		}catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		}		
	}
	/**
	 * 对于智能卡解除预授权
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void scdevReleasePreauthTrans(DeviceStockEJBEvent event)
			throws ServiceException {

		try {
			// 设备交易home接口
			DeviceTransitionHome devTransHome;
			// 远程接口
			DeviceTransition devTrans;
			// 设备交易明细接口
			DeviceTransitionDetailHome devTransDetailHome;
			// stb deviceid
			int scDeviceID = 0;

			String scSerialNo = null;

			DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
			int opID = event.getOperatorID();
			// create devicetranction recorder

			devTransDto.setBatchNo(devTransDto.getBatchNo());
			TerminalDeviceHome devHome = HomeLocater.getTerminalDeviceHome();
			TerminalDevice terDevice = devHome.findByPrimaryKey(new Integer(
					devTransDto.getDeviceNumber()));
			scDeviceID = terDevice.getDeviceID().intValue();
			scSerialNo = terDevice.getSerialNo();
			if (terDevice == null)
				throw new ServiceException("该设备不存在");
			/*****************************remove start yangchen 2008/06/23 ***************************************/
			// 修改与授权标志
			//terDevice.setPreAuthorization("N");
			/*****************************remove end yangchen 2008/06/23 ***************************************/

			// 设备个数
			devTransDto.setDeviceNumber(1);
			// 设置action
			devTransDto.setAction("DR");
			// 设置创建时间
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			// 设置状态
			devTransDto.setStatus("V");
			devTransDto.setOperatorID(opID);
			devTransHome = HomeLocater.getDeviceTransitionHome();
			LogUtility.log(clazz, LogLevel.DEBUG, "设备交易dto为" + devTransDto);
			// 创建设备流转记录头记录
			devTrans = devTransHome.create(devTransDto);

			DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
			// 创建设备流转明细记录
			detailDto.setBatchID(devTrans.getBatchID().intValue());

			detailDto.setSerialNo(terDevice.getSerialNo());
			detailDto.setDeviceID(terDevice.getDeviceID().intValue());
			devTransDetailHome = HomeLocater.getDeviceTransitionDetailHome();
			// 创建明细纪录
			devTransDetailHome.create(detailDto);
			/*****************************remove start yangchen 2008/06/23 ***************************************/
			//支持设备授权状态管理，add by jason 2007-2-7
			/*
			checkSCStatusCanOP(scDeviceID);
			OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
			Collection ossAuthorizationList=ossAuthorizationHome.findByDeviceID(scDeviceID);
			if(ossAuthorizationList!=null && !ossAuthorizationList.isEmpty()){
				Iterator itOssAuthorization=ossAuthorizationList.iterator();
				while(itOssAuthorization.hasNext()){
					OssAuthorization ossAuthorization=(OssAuthorization)itOssAuthorization.next();
					
					ossAuthorization.remove();
					
//					if(CommonConstDefinition.GENERALSTATUS_VALIDATE.equals(ossAuthorization.getStatus())){
////						ossAuthorization.setOperate(CommonConstDefinition.OSS_AUTHORIZATION_OPERATE_D);
//						ossAuthorization.setDtLastmod(TimestampUtility.getCurrentTimestamp());
//					}
				}
				
			}
			
			// create system event
			SystemEventRecorder.addEvent4ReleaseDevicePreauth(
					SystemEventRecorder.DEVICE_RELEASEPREAUTH, scDeviceID,
					scSerialNo, opID, "C");
			*/
			/*****************************remove end yangchen 2008/06/23***************************************/
			/*****************************add start yangchen 2008/06/23***************************************/
			DeviceBatchPreauthDTO deviceBatchPreauthDTO = new DeviceBatchPreauthDTO();
			event.setDeviceBatchPreauthDTO(deviceBatchPreauthDTO);
			deviceBatchPreauthDTO.setReferSheetSerialNO(devTransDto.getBatchNo());
			deviceBatchPreauthDTO.setDescription(devTransDto.getDescription());
			ArrayList allList = new ArrayList();
			allList.add(checkUnauth(terDevice.getSerialNo()));
			createDeviceUnauth(event,allList);
			/*****************************add end yangchen 2008/06/23***************************************/

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		}

	}
	
	/**
	 * 该方法主要用于智能卡授权和解授权操作的检查
	 * @param deviceID
	 * @throws ServiceException 
	 */
	private void checkSCStatusCanOP(int deviceID) throws ServiceException{
		try{	
			TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
			TerminalDevice device=terminalDeviceHome.findByPrimaryKey(new Integer(deviceID));
			
			if(!(CommonConstDefinition.DEVICE_STATUS_WAITFORSELL.equals(device.getStatus()) || 
					CommonConstDefinition.DEVICE_STATUS_STOCK.equals(device.getStatus()))){
				throw new ServiceException("设备" + deviceID + "状态不为待售或者库存，无法完成此操作！");
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(this.getClass(), LogLevel.ERROR, "检查设备状态出错，对应的设备ID为" + deviceID);
			throw new ServiceException("检查设备状态出错，对应的设备ID为" + deviceID);
		}
		catch(FinderException e2){
			LogUtility.log(this.getClass(), LogLevel.ERROR, "检查设备状态出错，对应的设备ID为" + deviceID);
			throw new ServiceException("检查设备状态出错，对应的设备ID为" + deviceID);
		}
		
		
	}
	/**
	 * 设备信息修改
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void deviceInfoModify(DeviceStockEJBEvent event) throws ServiceException {
		//更新设备信息
		TerminalDeviceDTO terminalDeviceDTO = event.getTerDeviceDTO();
	
		int opID = event.getOperatorID();

		String devTransAction = CommonConstDefinition.DEVICE_TRANSACTION_ACTION_X;
		//String deviceToStatus = event.getToStatus();

		TerminalDeviceDTO oldDTO = BusinessUtility.getDeviceByDeviceID(terminalDeviceDTO.getDeviceID());
		List deviceIDList = new ArrayList();
		deviceIDList.add(new Integer(terminalDeviceDTO.getDeviceID()));
		//记录设备流转明细
		deviceTransitionForDeviceInfoModify(event.getActionType(), deviceIDList,opID, event.getDeviceTransitionDTO().getBatchNo(), devTransAction, 
				terminalDeviceDTO.getStatus(), oldDTO.getAddressType(), terminalDeviceDTO.getAddressType(), "", 
				oldDTO.getAddressID(), terminalDeviceDTO.getAddressID(), "", "修改设备信息", false, event.getRemoteHostAddress(),terminalDeviceDTO.getDepotID());

		TerminalDeviceHome devHome = null;
		TerminalDevice dev=null;
		try{
			devHome = HomeLocater.getTerminalDeviceHome();
			dev = devHome.findByPrimaryKey(new Integer(terminalDeviceDTO.getDeviceID()));
			if(dev.ejbUpdate(terminalDeviceDTO)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改设备信息出错，原因：ejbUpdate(terminalDeviceDTO)==-1");
	    		throw new ServiceException("修改设备信息出错！");
			}
			
		}
		catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"修改设备信息出错，原因：设备定位出错！");
    		throw new ServiceException("更改设备信息出错，原因：设备定位出错！");
    		
    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.WARN,"更改设备信息出错，原因：设备查找出错！");
    		throw new ServiceException("更改设备信息出错，原因：设备查找出错！");
    	}    	
		
		
		//创建系统日志记录
		StringBuffer logDesc=new StringBuffer();
	
		logDesc.append("修改设备信息,设备ID:");
		logDesc.append(terminalDeviceDTO.getDeviceID());
		logDesc.append(SystemLogRecorder.appendDescString(";序列号:",
				oldDTO.getSerialNo(),terminalDeviceDTO.getSerialNo()));
		logDesc.append(SystemLogRecorder.appendDescString(";类型:",
				""+BusinessUtility.getDeiceClassMop().get(oldDTO.getDeviceClass()),
				""+BusinessUtility.getDeiceClassMop().get(terminalDeviceDTO.getDeviceClass())));
		logDesc.append(SystemLogRecorder.appendDescString(";型号:",
				oldDTO.getDeviceModel(),terminalDeviceDTO.getDeviceModel()));
		//logDesc.append(SystemLogRecorder.appendDescString(";批号:",
		//		""+oldDTO.getBatchID(),""+terminalDeviceDTO.getBatchID()));
		logDesc.append(SystemLogRecorder.appendDescString(";仓库:",
				""+BusinessUtility.getDepotMop().get(new Integer(oldDTO.getDepotID())),
				""+BusinessUtility.getDepotMop().get(new Integer(terminalDeviceDTO.getDepotID()))));
		logDesc.append(SystemLogRecorder.appendDescString(";属主类型:",
				""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICEADDRESSTYPE", oldDTO
						.getAddressType()),""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICEADDRESSTYPE", terminalDeviceDTO
						.getAddressType())));
		logDesc.append(SystemLogRecorder.appendDescString(";属主:",
				""+oldDTO.getAddressID(),""+terminalDeviceDTO.getAddressID()));
		logDesc.append(SystemLogRecorder.appendDescString(";租售:",
				""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICESELLMETHOD", oldDTO
						.getLeaseBuy()),""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICESELLMETHOD", terminalDeviceDTO
						.getLeaseBuy())));
		logDesc.append(SystemLogRecorder.appendDescString(";租售日期:",
				""+oldDTO.getDateFrom(),""+terminalDeviceDTO.getDateFrom()));
		logDesc.append(SystemLogRecorder.appendDescString(";配对:",
				oldDTO.getMatchFlag(),terminalDeviceDTO
						.getMatchFlag()));
		logDesc.append(SystemLogRecorder.appendDescString(";配对设备号:",
				""+oldDTO.getMatchDeviceID(),""+terminalDeviceDTO.getMatchDeviceID()));
		logDesc.append(SystemLogRecorder.appendDescString(";置位标记:",
				oldDTO.getCaSetFlag(),terminalDeviceDTO.getCaSetFlag()));
		logDesc.append(SystemLogRecorder.appendDescString(";置位日期:",
				""+oldDTO.getCaSetDate(),""+terminalDeviceDTO.getCaSetDate()));
		logDesc.append(SystemLogRecorder.appendDescString(";保修截止日期:",
				""+oldDTO.getDateTo(),""+terminalDeviceDTO.getDateTo()));
		logDesc.append(SystemLogRecorder.appendDescString(";保修期的长度:",
				""+oldDTO.getGuaranteeLength(),""+terminalDeviceDTO.getGuaranteeLength()));
		logDesc.append(SystemLogRecorder.appendDescString(";CM MAC地址:",
				oldDTO.getMACAddress(),terminalDeviceDTO.getMACAddress()));
		logDesc.append(SystemLogRecorder.appendDescString(";终端MAC地址:",
				oldDTO.getInterMACAddress(),terminalDeviceDTO.getInterMACAddress()));
		logDesc.append(SystemLogRecorder.appendDescString(";是否二手:",
				""+BusinessUtility
				.getCommonNameByKey("SET_D_USEFLAG", oldDTO
						.getUseFlag()),""+BusinessUtility
				.getCommonNameByKey("SET_D_USEFLAG", terminalDeviceDTO
						.getUseFlag())));
		logDesc.append(SystemLogRecorder.appendDescString(";状态:",
				""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICESTATUS", oldDTO
						.getStatus()),""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICESTATUS", terminalDeviceDTO
						.getStatus())));
		logDesc.append(SystemLogRecorder.appendDescString(";设备预授权:",
				oldDTO.getPreAuthorization(),terminalDeviceDTO.getPreAuthorization()));
		
		
		logDesc.append(SystemLogRecorder.appendDescString(";设备用途:",
				BusinessUtility.getDevicePurposeStrListDes(oldDTO.getPurposeStrList()),
				BusinessUtility.getDevicePurposeStrListDes(terminalDeviceDTO.getPurposeStrList())));
		String logStr = logDesc.toString();
		if(logStr.length()>1000)
		{
			logStr = logStr.substring(0,1000)+"...";
		}
		
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), opID,
				0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "修改设备信息",
				logStr,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 检查待报废设备是否合法
	 * @param serialsList
	 * @param event
	 * @param deviceModel
	 * @throws HomeFactoryException
	 * @throws FinderException
	 * @throws ServiceException
	 */
	private void checkDeviceScrap(ArrayList serialsList, DeviceStockEJBEvent event,
			String deviceModel) throws HomeFactoryException, FinderException,
			ServiceException {
		StringBuffer errMsgNone = new StringBuffer(1024);
		StringBuffer errMsgValid = new StringBuffer(1024);
		
		TerminalDeviceHome devHome = HomeLocater.getTerminalDeviceHome();;
		Collection devCol;
		for (int i=0;i<serialsList.size();i++) {
			TerminalDevice dev = null;
			String serialNo = (String) serialsList.get(i);
			devCol = devHome.findBySerialNo(serialNo);
			Iterator ite1 = devCol.iterator();
			while (ite1.hasNext())
				dev = (TerminalDevice) ite1.next();
			if (dev == null)
			{
				errMsgNone.append(serialNo+",");
				continue;
			}
			/**
			 报废设备检查:
			 送修/待修 的设备才能够报废 
		   */ 
				
		if(!("R".equals(dev.getStatus())||"M".equals(dev.getStatus())))
			errMsgValid.append(serialNo+",");
			
			
		}
		
		String errMsgNoneStr = errMsgNone.toString();
		String errMsgValidStr = errMsgValid.toString();
	
		if(errMsgNoneStr != null && !"".equals(errMsgNoneStr))
		{
			throw new ServiceException("设备["+errMsgNoneStr.substring(0,errMsgNoneStr.length()-1)+"]不存在");
		}
		if(errMsgValidStr != null && !"".equals(errMsgValidStr))
		{
			throw new ServiceException("设备["+errMsgValidStr.substring(0,errMsgValidStr.length()-1)+"]的状态不是“送修”或“待修”,不能报废!");
		}
	}
	
	/**
	 * 设备报废
	 * @param serialsList
	 * @param opID
	 */
	private void deviceTransitionForScrap(DeviceTransitionDTO devTransDto,
			ArrayList serialsList, int opID) throws ServiceException {
		TerminalDeviceHome devHome;
		DeviceTransitionHome devTransHome;
		DeviceTransitionDetailHome devTransDetailHome;
		TerminalDevice dev;
		DeviceTransition devTrans;
		Collection devCol = null;
		try {
			// 创建设备流转头记录
			// 设置创建时间
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			// 设置状态
			devTransDto.setStatus("V");
			devTransDto.setOperatorID(opID);
			devTransDto.setAction("I");
			devTransDto.setDeviceNumber(serialsList.size());
			//头记录中的fromType fromId toType toId 各个设备可能不同,没法填写
			
			devTransHome = HomeLocater.getDeviceTransitionHome();
			devTrans = devTransHome.create(devTransDto);
			Iterator ite = serialsList.iterator();
			while (ite.hasNext()) {

				devHome = HomeLocater.getTerminalDeviceHome();
				devCol = devHome.findBySerialNo((String) ite.next());
				Iterator ite1 = devCol.iterator();
				while (ite1.hasNext()) {
					dev = (TerminalDevice) ite1.next();
					String newStatus = CommonConstDefinition.DEVICE_STATUS_DISCARD;
					// 创建设备流转明细记录
					devTransDetailHome = HomeLocater
							.getDeviceTransitionDetailHome();
					DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
					detailDto.setBatchID(devTrans.getBatchID().intValue());
					detailDto.setDeviceID(dev.getDeviceID().intValue());
					detailDto.setSerialNo(dev.getSerialNo());
					detailDto.setStatus("V");
					detailDto.setFromDeviceStatus(dev.getStatus());
					detailDto.setFromType(dev.getAddressType());
					detailDto.setFromID(dev.getAddressID());
					detailDto.setToDeviceStatus(newStatus);
					detailDto.setToType(dev.getAddressType());
					detailDto.setToID(dev.getAddressID());
					//反填头记录[这样就是以最后一条记录为准了]
					/*
					devTrans.setFromType(dev.getAddressType());
					devTrans.setFromID(dev.getAddressID());
					devTrans.setToType(dev.getAddressType());
					devTrans.setToID(dev.getAddressID());
					*/
					//执行创建设备流转明细记录
					devTransDetailHome.create(detailDto);

					// 修改设备状态
					dev.setStatus(newStatus);
				}
			}
			context.put(com.dtv.oss.service.Service.PROCESS_RESULT, "");

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		}
	}
	public void devPreAuth(DeviceStockEJBEvent event)
	throws ServiceException {
		DeviceBatchPreauthDTO deviceBatchPreauthDTO = event.getDeviceBatchPreauthDTO();
		ArrayList allList = new ArrayList();
		int opID = event.getOperatorID();
		deviceBatchPreauthDTO.setOpId(opID);

		// 得到配对的序列号
		String strSerials = deviceBatchPreauthDTO.getFileName();
		String strProductList = "";
		if(strSerials.indexOf("$")!= -1){
			strProductList = strSerials.substring(strSerials.indexOf("$") + 1,strSerials.length());
			strSerials = strSerials.substring(0,strSerials.indexOf("$"));
		}
		else
			throw new ServiceException("没有授权的产品");

		String[] serialsArray = strSerials.split("\r\n");

		for (int i = 0; i < serialsArray.length; i++) {
			if(serialsArray[i].length()>0)
				allList.add(checkAuth(serialsArray[i]));

		}
		if (event.isDoPost())
			createAuthRecord(event,deviceBatchPreauthDTO, allList,strProductList);

		// 把执行结果放到ServiceContext里,因界面修改了，这里没有必要再存储
		//this.context.put(Service.PROCESS_RESULT, allList); 
	}
	/**
	 * 设备配对解除授权
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devDisauth(DeviceStockEJBEvent event)
			throws ServiceException {

			ArrayList allList = new ArrayList();
			String strSNArray = event.getDeviceBatchPreauthDTO().getFileName();
			String[] serialsArray = strSNArray.split("\r\n");
			
			for(int i=0;i<serialsArray.length;i++){		
				if(serialsArray[i].length()>0){
					String DeviceSN = serialsArray[i];				
					allList.add(checkUnauth(DeviceSN));
				}
			}
			if (event.isDoPost())
				createDeviceUnauth(event,allList);
			this.context.put(Service.PROCESS_RESULT, allList);
	}
	public void createDeviceUnmatch(DeviceStockEJBEvent event,ArrayList allList)throws ServiceException{
		try{
			DeviceBatchPreauthHome devBatchPreauthHome;
			DeviceBatchPreauth devPreauthBatch;
			
			DevicePrematchRecordHome devPrematchHome;
			DeviceBatchPreauthDTO devBatchPreauthDto = event.getDeviceBatchPreauthDTO();
			
			devBatchPreauthDto.setFileName("");
			devBatchPreauthDto.setOpId(event.getOperatorID());
			devBatchPreauthDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			devBatchPreauthDto.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(event.getOperatorID()));
			devBatchPreauthDto.setStatus("V");
			devBatchPreauthDto.setTotalDeviceNum(allList.size());
			if (devBatchPreauthDto.getOperationType() == null	|| "".equals(devBatchPreauthDto.getOperationType()))
				devBatchPreauthDto.setOperationType("DM");
			
			devBatchPreauthHome = HomeLocater.getDeviceBatchPreauthHome();
			
			LogUtility.log(clazz, LogLevel.DEBUG, "设备交易dto为" + devBatchPreauthDto);
			// 创建设备流转记录头记录
			devPreauthBatch = devBatchPreauthHome.create(devBatchPreauthDto);
			int scDeviceID = 0;
			String scSerialNo= "";
			int stbDeviceID = 0;
			String stbSerialNo="";
			for(int i=0;i<allList.size();i++)
			{
				TerminalDeviceDTO dto = (TerminalDeviceDTO)allList.get(i);
				
				TerminalDeviceHome devHome = HomeLocater
						.getTerminalDeviceHome();
				TerminalDevice terDeviceSC = devHome
						.findByPrimaryKey(new Integer(dto.getDeviceID()));
				if (terDeviceSC == null)
					throw new ServiceException("设备不存在　序列号="+terDeviceSC.getSerialNo());
				
				
				if (!"Y".equals(terDeviceSC.getMatchFlag()))
					throw new ServiceException("设备配对状态不正确　序列号="+terDeviceSC.getSerialNo());
				
				if (terDeviceSC.getDeviceClass().equalsIgnoreCase("SC")) {
					scDeviceID = terDeviceSC.getDeviceID().intValue();
					scSerialNo = terDeviceSC.getSerialNo();
					stbDeviceID = terDeviceSC.getMatchDeviceID();
					stbSerialNo = BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(stbDeviceID)); 
				}
				if (terDeviceSC.getDeviceClass().equalsIgnoreCase("STB")) {
					throw new ServiceException("设备类型不正确,请选择智能卡 序列号="+terDeviceSC.getSerialNo());
				}

				// 更改设备配对状态
				terDeviceSC.setMatchFlag("N");
				// 更改配对设备ID
				terDeviceSC.setMatchDeviceID(0);
				TerminalDevice terDeviceSTB = devHome.findByPrimaryKey(new Integer(stbDeviceID));
				if (terDeviceSTB == null)
					throw new ServiceException("设备对应的机顶盒不存在 序列号="+terDeviceSC.getSerialNo());
				//更新合子的状态和配对ＩＤ
				terDeviceSTB.setMatchFlag("N");
				terDeviceSTB.setMatchDeviceID(0);
				
				DevicePrematchRecordDTO devPrematchDto = new DevicePrematchRecordDTO();
				devPrematchDto.setBatchId(devPreauthBatch.getBatchId().intValue());
				devPrematchDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
				devPrematchDto.setOperationtype(devBatchPreauthDto.getOperationType());
				devPrematchDto.setOpId(devBatchPreauthDto.getOpId());
				devPrematchDto.setOrgId(devBatchPreauthDto.getOrgId());
				devPrematchDto.setScDeviceID(scDeviceID);
				devPrematchDto.setScdeviceModel(terDeviceSC.getDeviceModel());
				devPrematchDto.setScSerialNo(scSerialNo);
				devPrematchDto.setStbDeviceID(stbDeviceID);
				devPrematchDto.setStbDeviceModel(terDeviceSTB.getDeviceModel());
				devPrematchDto.setStbSerialNO(stbSerialNo);
				devPrematchDto.setStatus("V");

				devPrematchHome =HomeLocater.getDevicePrematchRecordHome();
				devPrematchHome.create(devPrematchDto);
				// create system event
				SystemEventRecorder.addEvent4DeviceUnmatch(
						SystemEventRecorder.SE_CA_UNMATCH, scDeviceID, scSerialNo,
						stbDeviceID, stbSerialNo, event.getOperatorID(), "C");

			}

		}catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		}
	}
	public TerminalDeviceDTO checkUnauth(String deviceSN)throws ServiceException{
		TerminalDeviceDTO terDto =BusinessUtility.getDeviceBySerialNo(deviceSN);
		if (!"Y".equals(terDto.getPreAuthorization()))
			throw new ServiceException("该设备未经预授权 序列号=" + terDto.getSerialNo());
		if (!"S".equals(terDto.getStatus()) && !"W".equals(terDto.getStatus()))
			throw new ServiceException("该设备状态不为待售或者库存 序列号=" + terDto.getSerialNo());
	
		return terDto;
	}
	
	public TerminalDeviceDTO checkAuth(String deviceSN)throws ServiceException{
	   TerminalDeviceDTO terDto =BusinessUtility.getDeviceBySerialNo(deviceSN);
	   if ("Y".equals(terDto.getPreAuthorization()))
		  throw new ServiceException("该设备已经预授权 序列号="+terDto.getSerialNo());
	   if (!"S".equals(terDto.getStatus()) && !"W".equals(terDto.getStatus()))
		  throw new ServiceException("该设备状态不为待售或者库存 序列号="+terDto.getSerialNo());
				
	   return terDto;	
	}
	
	public void createDeviceUnauth(DeviceStockEJBEvent event,ArrayList allList)throws ServiceException{
		try{		
			DeviceBatchPreauthDTO devBatchPreauthDto = event.getDeviceBatchPreauthDTO();
			
			devBatchPreauthDto.setFileName("");
			devBatchPreauthDto.setOpId(event.getOperatorID());
			devBatchPreauthDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			devBatchPreauthDto.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(event.getOperatorID()));
			devBatchPreauthDto.setStatus("V");
			devBatchPreauthDto.setTotalDeviceNum(allList.size());
			if (devBatchPreauthDto.getOperationType() == null	|| "".equals(devBatchPreauthDto.getOperationType()))
				devBatchPreauthDto.setOperationType("DA");
			
			DeviceBatchPreauthHome devBatchPreauthHome = HomeLocater.getDeviceBatchPreauthHome();
			
			LogUtility.log(clazz, LogLevel.DEBUG, "设备交易dto为" + devBatchPreauthDto);
			// 创建设备流转记录头记录
			DeviceBatchPreauth  devPreauthBatch = devBatchPreauthHome.create(devBatchPreauthDto);
			String scSerialNo= "";
			for(int i=0;i<allList.size();i++){
				TerminalDeviceDTO dto = (TerminalDeviceDTO)allList.get(i);
				// 更新设备的配对标志
				BusinessUtility.updatePreAuthorizationForTerminalDevice("N",dto
						.getDeviceID());
				
				DevicePreauthRecordDTO devPreauthDto = new DevicePreauthRecordDTO();
				devPreauthDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
				devPreauthDto.setOperationType(devBatchPreauthDto.getOperationType());
				devPreauthDto.setOpId(devBatchPreauthDto.getOpId());
				devPreauthDto.setOrgId(devBatchPreauthDto.getOrgId());
				devPreauthDto.setBatchId(devPreauthBatch.getBatchId().intValue());
				devPreauthDto.setDeviceID(dto.getDeviceID());
				devPreauthDto.setDeviceModel(dto.getDeviceModel());
				devPreauthDto.setProductId(0);
				devPreauthDto.setSerialNo(dto.getSerialNo());
				devPreauthDto.setStatus("V");
				devPreauthDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
				devPreauthDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());

				BusinessUtility.insertDevicepreauthrecord(devPreauthDto);
				
				OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
				Collection ossAuthorizationList=ossAuthorizationHome.findByDeviceID(dto.getDeviceID());
				if(ossAuthorizationList!=null && !ossAuthorizationList.isEmpty()){
					Iterator itOssAuthorization=ossAuthorizationList.iterator();
					while(itOssAuthorization.hasNext()){
						OssAuthorization ossAuthorization=(OssAuthorization)itOssAuthorization.next();
						ossAuthorization.remove();
					}
				}

				Collection execCol =new ArrayList();
				ArrayList element =new ArrayList();
				element.add("0");  //客户证号
				element.add("0");  //账户号
			  	element.add("0"); //业务账户号
				element.add(String.valueOf(BusinessUtility.getProductIDByModel(dto.getDeviceModel()))); //产品id
				element.add("0"); //psid
				if ("STB".equals(dto.getDeviceClass())){
				   element.add(String.valueOf(dto.getDeviceID()));  //STBDEVICEID
				   element.add(String.valueOf(dto.getSerialNo()));  //STBSERIALNO
				   element.add("0");                    //SCDEVICEID
				   element.add("");                     //SCSERIALNO
				}else{
				   element.add("0");                    //STBDEVICEID
				   element.add("");                     //STBSERIALNO
				   element.add(String.valueOf(dto.getDeviceID()));  //SCDEVICEID
				   element.add(String.valueOf(dto.getSerialNo()));  //SCSERIALNO
				}
				execCol.add(element);
				BusinessUtility.insertSystemEventRecord(execCol,SystemEventRecorder.DEVICE_RELEASEPREAUTH,SystemEventRecorder.SE_STATUS_CREATE,getOperatorID());
			}
		}catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		}catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		}catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		}catch(RemoveException e4){
			LogUtility.log(clazz, LogLevel.WARN, "删除记录出错：" + e4);
			throw new ServiceException("删除记录出错！");
		}
	}
	public void createAuthRecord(DeviceStockEJBEvent event,DeviceBatchPreauthDTO devBatchPreAuthDto,ArrayList allList,String productList)throws ServiceException
	{
		if(productList.length()==0)
			throw new ServiceException("没有预授权产品");
		String[] productArray = productList.split(";");
		
		DeviceBatchPreauthHome devBatchPreauthHome;
		DeviceBatchPreauth devBatchPreauth = null;
		LogUtility.log(clazz,LogLevel.DEBUG,"设备授权开始");
		if (allList == null || allList.size() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "设备授权参数错误！");
			throw new ServiceException("设备授权参数错误！");
		}
		devBatchPreAuthDto.setStatus("V");
		devBatchPreAuthDto.setFileName("");
		
		int deviceNumber = allList.size();
		devBatchPreAuthDto.setTotalDeviceNum(deviceNumber);
		
		if (devBatchPreAuthDto.getOperationType() == null	|| "".equals(devBatchPreAuthDto.getOperationType()))
			devBatchPreAuthDto.setOperationType("AP");
		devBatchPreAuthDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
		devBatchPreAuthDto.setOpId(event.getOperatorID());
		devBatchPreAuthDto.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(event.getOperatorID()));
		try{
			devBatchPreauthHome = HomeLocater.getDeviceBatchPreauthHome();
			LogUtility.log(clazz, LogLevel.DEBUG, "设备授权dto为" + devBatchPreAuthDto);
			devBatchPreauth = devBatchPreauthHome.create(devBatchPreAuthDto);
			for (int i = 0; i < allList.size(); i++) {
				TerminalDeviceDTO mainDto = (TerminalDeviceDTO) allList.get(i);
				createPreauthOperation(devBatchPreauth,mainDto,productArray);
			}
		}catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("设备序列号定位错误");
		}catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "创建记录出错：" + e3);
			throw new ServiceException("创建记录出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("找不到相关的设备");

		}
	}
	
	private void createPreauthOperation(DeviceBatchPreauth devBatchPreauth,
			TerminalDeviceDTO mainDto,
			String[] productArray) throws HomeFactoryException,
			CreateException, FinderException, ServiceException {

		TerminalDeviceHome terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
		
		//应丽江要求增加
		Collection execCol =new ArrayList();
		ArrayList element =new ArrayList();
		element.add("0");  //客户证号
		element.add("0");  //账户号
	  	element.add("0"); //业务账户号
		element.add(String.valueOf(BusinessUtility.getProductIDByModel(mainDto.getDeviceModel()))); //产品id
		element.add("0"); //psid
		if ("STB".equals(mainDto.getDeviceClass())){
		   element.add(String.valueOf(mainDto.getDeviceID()));  //STBDEVICEID
		   element.add(String.valueOf(mainDto.getSerialNo()));  //STBSERIALNO
		   element.add("0");                    //SCDEVICEID
		   element.add("");                     //SCSERIALNO
		}else{
		   element.add("0");                    //STBDEVICEID
		   element.add("");                     //STBSERIALNO
		   element.add(String.valueOf(mainDto.getDeviceID()));  //SCDEVICEID
		   element.add(String.valueOf(mainDto.getSerialNo()));  //SCSERIALNO
		}
		execCol.add(element);
		BusinessUtility.insertSystemEventRecord(execCol,SystemEventRecorder.SE_CUSTDEV_PURCHASE,SystemEventRecorder.SE_STATUS_CREATE,getOperatorID());
		for (int i=0;i<productArray.length;i++){
			int productID=Integer.valueOf(productArray[i]).intValue();
			if(productID<=0) continue;
			// 初始化设备授权明细dto
		  	DevicePreauthRecordDTO detailDto1 = new DevicePreauthRecordDTO();
		  		
			detailDto1.setDeviceID(mainDto.getDeviceID());
			detailDto1.setBatchId(devBatchPreauth.getBatchId().intValue());
			detailDto1.setSerialNo(mainDto.getSerialNo());
			detailDto1.setDeviceModel(mainDto.getDeviceModel());
			detailDto1.setCreateTime(TimestampUtility.getCurrentTimestamp());
			detailDto1.setOperationType(devBatchPreauth.getOperationType());
			detailDto1.setOpId(devBatchPreauth.getOpId());
			detailDto1.setOrgId(devBatchPreauth.getOrgId());
			detailDto1.setProductId(productID);
			detailDto1.setStatus("V");
			BusinessUtility.insertDevicepreauthrecord(detailDto1);
			
			LogUtility.log(clazz, LogLevel.DEBUG, "主要设备授权明细dto为" + detailDto1);

			//记录系统事件
			LogUtility.log(clazz, LogLevel.DEBUG, "产品ID是:" + productID);
			
			Collection systemEventCol =new ArrayList();
			ArrayList systemElement =new ArrayList();
			systemElement.add("0");  //客户证号
			systemElement.add("0");  //账户号
			systemElement.add("0");  //业务账户号
			systemElement.add(String.valueOf(productID)); //产品id
			systemElement.add("0");  //psid
			if ("STB".equals(mainDto.getDeviceClass())){
				systemElement.add(String.valueOf(mainDto.getDeviceID()));  //STBDEVICEID
				systemElement.add(String.valueOf(mainDto.getSerialNo()));  //STBSERIALNO
				systemElement.add("0");                    //SCDEVICEID
				systemElement.add("");                     //SCSERIALNO
			}else{
				systemElement.add("0");                    //STBDEVICEID
				systemElement.add("");                     //STBSERIALNO
				systemElement.add(String.valueOf(mainDto.getDeviceID()));  //SCDEVICEID
				systemElement.add(String.valueOf(mainDto.getSerialNo()));  //SCSERIALNO
			}
			systemEventCol.add(systemElement);
			BusinessUtility.insertSystemEventRecord(systemEventCol,SystemEventRecorder.DEVICE_PREAUTH,SystemEventRecorder.SE_STATUS_CREATE,getOperatorID());
						
			OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
			Collection ossRes=ossAuthorizationHome.findByDeviceIDAndProductID(mainDto.getDeviceID(), productID);
			if(ossRes!=null && ossRes.size()>0){				
				OssAuthorization ossca=(OssAuthorization)ossRes.iterator().next();
				ossca.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			}
			else{
				//支持设备授权状态管理
				OssAuthorizationDTO ossAuthorizationDTO=new OssAuthorizationDTO();
				ossAuthorizationDTO.setDeviceID(mainDto.getDeviceID());
				ossAuthorizationDTO.setDeviceSerialNo(mainDto.getSerialNo());
				ossAuthorizationDTO.setProductID(productID);
				BusinessUtility.insertOssAuthorization(ossAuthorizationDTO);
			}
		}
		// 更新设备的配对标志
		BusinessUtility.updatePreAuthorizationForTerminalDevice("Y",mainDto
				.getDeviceID());
	}
	/**
	 * 设备预授权刷新,
	 * 先清除旧的授权,再根据记录重新生成授权信息.
	 * @param serialNoList
	 * @param operatorid
	 * @throws ServiceException
	 */
	public void refurbishPreAuthorization(ArrayList serialNoList,int operatorid) throws ServiceException{
		if(serialNoList==null||serialNoList.isEmpty()){
			throw new ServiceException("没有相关的设备信息");
		}
		for(Iterator it=serialNoList.iterator();it.hasNext();){
			String serialNo=(String) it.next();
			TerminalDeviceDTO terDto=BusinessUtility.getDeviceBySerialNo(serialNo);
			if(terDto==null){
				throw new ServiceException("无效的设备:"+serialNo);
			}
			if(!"Y".equals(terDto.getPreAuthorization())){
				throw new ServiceException("尚未进行过预授权的设备:"+serialNo);
			}

			try {
				SystemEventRecorder.addEvent4ReleaseDevicePreauth(
						SystemEventRecorder.DEVICE_RELEASEPREAUTH, terDto.getDeviceID(),
						serialNo, operatorid, "C");
			} catch (HomeFactoryException e) {
				LogUtility.log(clazz, LogLevel.ERROR, "刷新授权出错,设备序列号:"+serialNo+",错误描述:"+e);				
				throw new ServiceException("刷新授权出错:"+serialNo);
			} catch (CreateException e) {
				LogUtility.log(clazz, LogLevel.ERROR, "刷新授权出错,设备序列号:"+serialNo+",错误描述:"+e);				
				throw new ServiceException("刷新授权出错:"+serialNo);
			}
			List authList=BusinessUtility.getAuthorizationInfoWithSerialNo(serialNo);
			if(authList==null||authList.isEmpty()){
				continue;
			}
			for(Iterator auIt=authList.iterator();auIt.hasNext();){
				OssAuthorizationDTO authDto=(OssAuthorizationDTO) auIt.next();
				int productId=authDto.getProductID();
				try {
					SystemEventRecorder.addEvent4DeviceMatchPreauth(
							SystemEventRecorder.DEVICE_PREAUTH, productId, terDto.getDeviceID(), serialNo, 
							0, "", operatorid, "C");
				} catch (HomeFactoryException e) {
					LogUtility.log(clazz, LogLevel.ERROR, "刷新授权出错,设备序列号:"+serialNo+",产品ID:"+productId+",错误描述:"+e);
					throw new ServiceException("刷新授权出错:"+serialNo);
				} catch (CreateException e) {
					LogUtility.log(clazz, LogLevel.ERROR, "刷新授权出错,设备序列号:"+serialNo+",产品ID:"+productId+",错误描述:"+e);
					throw new ServiceException("刷新授权出错:"+serialNo);
				}
			}
		}
	}
	
	public static void main(String [] args)
	{
		StringBuffer errMsgNone = new StringBuffer(256);
		if("".equals(errMsgNone.toString()))
		System.out.println("hehe"+errMsgNone.toString());
	}

}
