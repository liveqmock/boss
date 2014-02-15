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
 * �豸��ת����� 
 * �������߰���ģ�ϣ����ʱ��ú�������
 * �ر����豸��תû�кúõĸ���
 * @author 
 * 
 */
public class DeviceStockService extends AbstractService {

	private static final Class clazz = DeviceStockService.class;

	private ServiceContext context;

	/**
	 * ���캯��
	 * 
	 * @param s
	 */
	public DeviceStockService(ServiceContext s) {
		this.context = s;
	}

	/**
	 * /** �豸�ؿ�
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
	 * ����豸��⻹Ҫ�����ж�*********
	 * 
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	// String devTransAction=devTransDto.getAction();
	// String deviceToStatus="";
	// deviceTransition(event.getActionType(),deviceIDList,opID,"",devTransAction,
	// deviceToStatus,devTransDto.getFromType(),devTransDto.getToType(),devTransDto.getStatusReason(),
	// devTransDto.getFromID(),devTransDto.getToID(),"","�豸���",true,event.getRemoteHostAddress());
	// }
	public void devInStock(DeviceStockEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {

		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		String serialsStr = event.getDevArrayStr();
		if (serialsStr == null || "".equals(serialsStr))
			throw new ServiceException("û��ɨ����Ҫ�ؿ���豸");

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
			// �����豸��תͷ��¼
			// ���ô���ʱ��
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			// ����״̬
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
					//����/���� �豸 ��Ϊ ��棬����״̬���豸״̬����
					if("W".equals(newStatus) || "M".equals(newStatus))
						newStatus = CommonConstDefinition.DEVICE_STATUS_STOCK;

					// ���������ת��ϸ��¼
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

					// �޸��豸״̬
					dev.setStatus(newStatus);
					dev.setAddressType("D");// ��8-02��TΪD
					dev.setAddressID(devTransDto.getToID());// ��8-02��
					dev.setDepotID(devTransDto.getToID());
				}
			}
			context.put(com.dtv.oss.service.Service.PROCESS_RESULT, "");

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�豸���кŶ�λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		}
	}

	/**
	 * �豸����
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
				devTransDto.getFromID(), devTransDto.getToID(), "", "�豸����",
				true, event.getRemoteHostAddress(),0);
	}

	/**
	 * �豸����
	 * ����豸���г��� ��update by 2007-8-27
	 * @param event
	 * @throws ServiceException
	 */
	public void deviceOutStock(DeviceStockEJBEvent event) throws ServiceException {
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();

		Collection deviceIDList = event.getDeviceArray();
		int opID = event.getOperatorID();

		String devTransAction = CommonConstDefinition.DEVICE_TRANSACTION_ACTION_O;
		String deviceToStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
		
		// ����ֵ
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

		// �豸��תT_DeviceTransition
		try {
			// �����豸��ת
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

			// ��¼�豸����������ͷ��Ϣ
			lstRet.add(new String("ִ�� " + deviceIDList.size() + " ��" + "�豸����"));

			// �����豸��ת��ϸT_DeviceTransitionDetail
			Iterator it = deviceIDList.iterator();
			int deviceID;
			while (it.hasNext()) {
				deviceID = ((Integer) it.next()).intValue();
				devHome = HomeLocater.getTerminalDeviceHome();
				
				//�޸���ϸ����ת��¼��ϸ
				dev = devHome.findByPrimaryKey(new Integer(deviceID));

				// �ж��Ƿ���Ըı��豸��״̬
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

					if(!"".equals(logDes))logDes=logDes +"��";
					logDes=logDes +"�豸" + dev.getDeviceID() +"�����к�"+ dev.getSerialNo()+ "��״̬��" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",dev.getStatus()) +"�޸ĵ�" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",deviceToStatus) ;
					
					// �޸��豸������
					dev.setStatus(deviceToStatus);
					dev.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					dev.setAddressID(devTransDto.getToID());
					dev.setDepotID(0);
					dev.setAddressType(devTransDto.getToType());
					
					lstRet.add(new String("�豸: " + dev.getSerialNo() + " �豸����ɹ�!"));
					idevOK++;
				} else { // ״̬����δͨ��
					lstRet.add(new String("�豸: " + dev.getSerialNo() + "�豸����ʧ��"));
					idevError++;
				}
			}

			devTrans.setDeviceNumber(idevOK); // ����ʵ�ʳɹ����豸��
			devTrans.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			lstRet.add(new String("�ɹ��豸����: " + idevOK + "�������������豸�� " + idevError + "��"));

			batchNO = devTrans.getBatchID().intValue();
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "�豸���ⶨλ����" + e);
			throw new ServiceException("�豸���ⶨλ����");
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.WARN,  "�豸������ҳ���" + e2);
			throw new ServiceException("�豸������ҳ���");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "�豸���ⴴ����¼����" + e3);
			throw new ServiceException( "�豸���ⴴ����¼����");
		}

		// ����ϵͳ��־��¼
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), opID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "�豸����", "�豸����" + ",����:"	+ batchNO + "�� �ɹ���" + idevOK + "��ʧ�ܣ�" + idevError,
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
			
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸��ת����");
		LogUtility.log(clazz, LogLevel.DEBUG, "ִ�н��Ϊ��" + lstRet);		
		
		// ��ִ�н���ŵ�ServiceContext��
		this.context.put(Service.PROCESS_RESULT, lstRet);
		this.context.put(Service.SYSTEM_LOG, logDes);
	}

	
	/**
	 * �豸����
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void deviceScrap(DeviceStockEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException{
		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();
		String serialsStr = event.getDevArrayStr();

		if (serialsStr == null || "".equals(serialsStr))
			throw new ServiceException("û��ɨ����Ҫ���ϵ��豸");
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
	 * �豸����
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
				devTransDto.getFromID(), devTransDto.getToID(), "", "�豸����",
				true, event.getRemoteHostAddress(),-1);
	}

	/**
	 * �豸����
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
			throw new ServiceException("û��ɨ����Ҫ���޵��豸");

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
		//��8-02�����ͷ��Ϣ����֤
		checkExistToRepair(serialsList, event.getActionType(),devTransDto.getDataFileName());
		int opID = event.getOperatorID();
		if (event.isDoPost()){
			deviceTransitionForRepair(devTransDto, serialsList, opID);
			
			if(logDes.length()>400)logDes=logDes +"...";
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), opID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
					"�豸����", "�����豸���кţ�" +logDes,SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
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
			//�����豸���
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
			throw new ServiceException("�豸["+errMsgNoneStr.substring(0,errMsgNoneStr.length()-1)+"]������");
		}
		if(errMsgModelStr != null && !"".equals(errMsgModelStr))
		{
			throw new ServiceException("�豸["+errMsgModelStr.substring(0,errMsgModelStr.length()-1)+"]���豸�ͺŲ�ƥ��");
		}
		if(errMsgValidStr != null && !"".equals(errMsgValidStr))
		{
			throw new ServiceException("�豸["+errMsgValidStr.substring(0,errMsgValidStr.length()-1)+"]���Ǵ��޵��豸��������");
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
			 �ؿ��豸���:
			 ����ؿ�ԭ��Ϊ ���𻵡� �� �����е��豸������Ϊ �����ޡ� ״̬
		     ����ؿ�ԭ��Ϊ ���˻ء� �� �����е��豸������Ϊ �����ۡ� ״̬
		     ����ؿ�ԭ��Ϊ ���޸��� �� �����е��豸������Ϊ �����ޡ� ״̬   
			  
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
				// ��8-02,���������豸��Ϊ������
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
			throw new ServiceException("�豸["+errMsgNoneStr.substring(0,errMsgNoneStr.length()-1)+"]������");
		}
		if(errMsgValidDStr != null && !"".equals(errMsgValidDStr))
		{
			throw new ServiceException("�豸["+errMsgValidDStr.substring(0,errMsgValidDStr.length()-1)+"]��״̬���ǡ����ޡ�,�����ԡ��𻵡���ԭ��ؿ�.");
		}
		if(errMsgValidBStr != null && !"".equals(errMsgValidBStr))
		{
			throw new ServiceException("�豸["+errMsgValidBStr.substring(0,errMsgValidBStr.length()-1)+"]��״̬���ǡ����ۡ�,�����ԡ��˻ء���ԭ��ؿ�.");
		}
		if(errMsgValidRStr != null && !"".equals(errMsgValidRStr))
		{
			throw new ServiceException("�豸["+errMsgValidRStr.substring(0,errMsgValidRStr.length()-1)+"]��״̬���ǡ����ޡ�,�����ԡ��޸�����ԭ��ؿ�.");
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
			// �����豸��תͷ��¼
			// ���ô���ʱ��
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			// ����״̬
			devTransDto.setStatus("V");
			devTransDto.setOperatorID(opID);
			devTransDto.setAction("R");
			devTransDto.setDeviceNumber(serialsList.size());
			devTransDto.setToType("T");
			// �õ��豸�ͺ�
			String deviceModel = devTransDto.getDataFileName();
			devTransDto.setDataFileName("");

			// ȡ�ó���
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

					// ���������ת��ϸ��¼
					devTransDetailHome = HomeLocater
							.getDeviceTransitionDetailHome();
					DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
					detailDto.setBatchID(devTrans.getBatchID().intValue());
					detailDto.setDeviceID(dev.getDeviceID().intValue());
					detailDto.setSerialNo(dev.getSerialNo());
					detailDto.setStatus("V");
					detailDto.setFromDeviceStatus(dev.getStatus());// ȡ���豸ԭ��״̬
					detailDto.setFromType(dev.getAddressType());
					detailDto.setFromID(dev.getAddressID());
					detailDto.setToDeviceStatus(newStatus);// �����豸�µ�״̬
					detailDto.setToType(devTrans.getToType());
					detailDto.setToID(devTrans.getToID());
					devTrans.setFromType(dev.getAddressType());
					devTrans.setFromID(dev.getAddressID());
					DeviceTransitionDetailHome detailHome = HomeLocater
							.getDeviceTransitionDetailHome();
					detail = devTransDetailHome.create(detailDto);
					// �޸��豸״̬
					dev.setStatus(newStatus);
					dev.setAddressType("T");
					dev.setAddressID(providerId);

				}
			}
			

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�豸���кŶ�λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		}
	}

	/**
	 * �豸״̬�޸� ��,7-27,������ת����
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

		//�˹��ı��豸��״̬�д��ۺ�����״̬���豸�������޸�:modify by jason 2007-2-11
		if(deviceIDList==null || deviceIDList.size()<1)
			throw new ServiceException("û����Ҫ�������豸��������ѡ���豸��");
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
			LogUtility.log(clazz, LogLevel.INFO,"�����豸����" + e);
			throw new ServiceException("�����豸����");
		}
		if(!(strNotAllowedOPDevice==null || "".equals(strNotAllowedOPDevice))){
			throw new ServiceException("�豸��ţ�"+ strNotAllowedOPDevice +"����״̬��������б��β��������ۺ�����״̬���豸�ǲ�����ò����ģ�");
		}
		//end
		
		deviceTransition(event.getActionType(), deviceIDList, opID, devTransDto.getBatchNo(), devTransAction, 
				deviceToStatus, devTransDto.getFromType(), devTransDto.getToType(), devTransDto.getStatusReason(), 
				devTransDto.getFromID(), devTransDto.getToID(), "", "�豸״̬�޸�", false, event.getRemoteHostAddress(),-1);
		
		//����ϵͳ��־��¼
		String logDes=(String)context.get(Service.SYSTEM_LOG);
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), opID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
				"�˹��޸��豸״̬", logDes,SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * �豸����
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
				devTransDto.getFromID(), devTransDto.getToID(), "", "�豸����",
				true, event.getRemoteHostAddress(),-1);
	}

	/**
	 * �豸��ת
	 * 
	 * @param deviceIDList :
	 *            �豸ID���б�
	 * @param opID :
	 *            ��ǰ����Ա��ID
	 * @param opBatchNO
	 *            ����ǰ������������NO
	 * @param deviceTransAction
	 *            ���豸��ת�Ķ�������
	 * @param deviceToStatus ��
	 *            �豸Ŀ��״̬
	 * @param deviceFromType
	 *            ���豸ԭ��������
	 * @param deviceToType
	 *            ���豸Ŀ������
	 * @param toStatusReason :
	 *            ��תԭ��
	 * @param deviceAddressFromID
	 *            ���豸ԭ���洢��
	 * @param deviceAddressToID ��
	 *            �豸Ŀ�Ĵ洢��
	 * @param useFlag
	 *            ���Ƿ�ʹ�ñ�־
	 * @param des
	 *            ����ǰ��������
	 * @param createSysLog ��
	 *            �Ƿ񴴽���־
	 * @param machineName
	 *            ������������
	 * @param depotID
	 * 			  ��Ŀ��ֿ⣬���Ϊ-1���򲻽����κεĲֿ��޸�
	 * @return Collection
	 * @throws ServiceException
	 */
	private void deviceTransition(int actionType, Collection deviceIDList,
			int opID, String opBatchNO, String deviceTransAction,
			String deviceToStatus, String deviceFromType, String deviceToType,
			String toStatusReason, int deviceAddressFromID,
			int deviceAddressToID, String useFlag, String des,
			boolean createSysLog, String machineName,int depotID) throws ServiceException {
		
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸��ת��ʼ");

		// ������
		if (deviceIDList == null || deviceIDList.size() == 0 || opID == 0|| "".equals(deviceTransAction)) {
			LogUtility.log(clazz, LogLevel.WARN, "�豸��ת��������");
			throw new ServiceException("�豸��ת��������");
		}

		// ����ֵ
		ArrayList lstRet = new ArrayList();
		
		String logDes="";
		
		TerminalDeviceHome devHome;
		DeviceTransitionHome devTransHome;
		DeviceTransitionDetailHome devTransDetailHome;
		TerminalDevice dev;
		DeviceTransition devTrans;

		// ��¼�ɹ�ִ�е��豸
		int idevOK = 0;
		// ��¼ִ�в��ɹ����豸
		int idevError = 0;
		// ������ID
		int batchNO = 0;

		// �豸��תT_DeviceTransition
		try {
			// �����豸��ת
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

			// ��¼�豸����������ͷ��Ϣ
			lstRet.add(new String("ִ�� " + deviceIDList.size() + " ��" + des));

			// �����豸��ת��ϸT_DeviceTransitionDetail
			Iterator it = deviceIDList.iterator();
			int deviceID;
			while (it.hasNext()) {
				deviceID = ((Integer) it.next()).intValue();
				devHome = HomeLocater.getTerminalDeviceHome();
				dev = devHome.findByPrimaryKey(new Integer(deviceID));

				if(depotID>-1)
					dev.setDepotID(depotID);
				
				// �ж��Ƿ���Ըı��豸��״̬
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

					if(!"".equals(logDes))logDes=logDes +"��";
					logDes=logDes +"�豸" + dev.getDeviceID() +"�����к�"+ dev.getSerialNo()+ "��״̬��" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",dev.getStatus()) +"�޸ĵ�" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",deviceToStatus) ;

					
					// �޸��豸������
					dev.setStatus(deviceToStatus);
					dev.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					// ����ǰ̨û�и�������������,�ᵼ��ԭ��������Ϣ��ʧ,ע�͵�,
					// dev.setAddressID(deviceAddressToID);
					// dev.setDepotID(deviceAddressToID);
					// dev.setAddressType(deviceToType);
					if (!"".equals(useFlag))
						dev.setUseFlag(useFlag);

					lstRet.add(new String("�豸: " + dev.getSerialNo() + des+ " �ɹ�!"));
					idevOK++;
				} else { // ״̬����δͨ��
					lstRet.add(new String("�豸: " + dev.getSerialNo() + des+ "ʧ��"));
					idevError++;
				}
			}

			devTrans.setDeviceNumber(idevOK); // ����ʵ�ʳɹ����豸��
			devTrans.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			lstRet.add(new String("�ɹ�" + des + ": " + idevOK + "�������������豸�� " + idevError + "��"));

			batchNO = devTrans.getBatchID().intValue();
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, des + "��λ����" + e);
			throw new ServiceException(des + "��λ����");
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.WARN, des + "���ҳ���" + e2);
			throw new ServiceException(des + "���ҳ���");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, des + "������¼����" + e3);
			throw new ServiceException(des + "������¼����");
		}

		// ����ϵͳ��־��¼
		if (createSysLog) {
			SystemLogRecorder.createSystemLog(machineName, opID, 0,
					SystemLogRecorder.LOGMODULE_LOGISTICS, "�޸�", des + ",����:"	+ batchNO + "�� �ɹ���" + idevOK + "��ʧ�ܣ�" + idevError,
					SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}

			
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸��ת����");
		LogUtility.log(clazz, LogLevel.DEBUG, "ִ�н��Ϊ��" + lstRet);

		// ��ִ�н���ŵ�ServiceContext��
		this.context.put(Service.PROCESS_RESULT, lstRet);
		this.context.put(Service.SYSTEM_LOG, logDes);
	}
	
	//�豸��ת ���豸��Ϣ�޸���,�����ڳ����޸�ʱ��¼�豸��ת��,
	//ֻ��¼��ת���޸��豸�ڸ÷����������
	private void deviceTransitionForDeviceInfoModify(int actionType, Collection deviceIDList,
			int opID, String opBatchNO, String deviceTransAction,
			String deviceToStatus, String deviceFromType, String deviceToType,
			String toStatusReason, int deviceAddressFromID,
			int deviceAddressToID, String useFlag, String des,
			boolean createSysLog, String machineName,int depotID) throws ServiceException {
		
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸��ת��ʼ");

		// ������
		if (deviceIDList == null || deviceIDList.size() == 0 || opID == 0|| "".equals(deviceTransAction)) {
			LogUtility.log(clazz, LogLevel.WARN, "�豸��ת��������");
			throw new ServiceException("�豸��ת��������");
		}

		// ����ֵ
		ArrayList lstRet = new ArrayList();
		
		String logDes="";
		
		TerminalDeviceHome devHome;
		DeviceTransitionHome devTransHome;
		DeviceTransitionDetailHome devTransDetailHome;
		TerminalDevice dev;
		DeviceTransition devTrans;

		// ��¼�ɹ�ִ�е��豸
		int idevOK = 0;
		// ��¼ִ�в��ɹ����豸
		int idevError = 0;
		// ������ID
		int batchNO = 0;

		// �豸��תT_DeviceTransition
		try {
			// �����豸��ת
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

			// ��¼�豸����������ͷ��Ϣ
			lstRet.add(new String("ִ�� " + deviceIDList.size() + " ��" + des));

			// �����豸��ת��ϸT_DeviceTransitionDetail
			Iterator it = deviceIDList.iterator();
			int deviceID;
			while (it.hasNext()) {
				deviceID = ((Integer) it.next()).intValue();
				devHome = HomeLocater.getTerminalDeviceHome();
				dev = devHome.findByPrimaryKey(new Integer(deviceID));
				
				// �ж��Ƿ���Ըı��豸��״̬
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

					if(!"".equals(logDes))logDes=logDes +"��";
					logDes=logDes +"�豸" + dev.getDeviceID() +"�����к�"+ dev.getSerialNo()+ "��״̬��" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",dev.getStatus()) +"�޸ĵ�" + 
					BusinessUtility.getCommomSettingValue("SET_D_DEVICESTATUS",deviceToStatus) ;

					lstRet.add(new String("�豸: " + dev.getSerialNo() + des+ " �ɹ�!"));
					idevOK++;
				} else { // ״̬����δͨ��
					lstRet.add(new String("�豸: " + dev.getSerialNo() + des+ "ʧ��"));
					idevError++;
				}
			}

			devTrans.setDeviceNumber(idevOK); // ����ʵ�ʳɹ����豸��
			devTrans.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			lstRet.add(new String("�ɹ�" + des + ": " + idevOK + "�������������豸�� " + idevError + "��"));

			batchNO = devTrans.getBatchID().intValue();
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, des + "��λ����" + e);
			throw new ServiceException(des + "��λ����");
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.WARN, des + "���ҳ���" + e2);
			throw new ServiceException(des + "���ҳ���");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, des + "������¼����" + e3);
			throw new ServiceException(des + "������¼����");
		}

		// ����ϵͳ��־��¼
		if (createSysLog) {
			SystemLogRecorder.createSystemLog(machineName, opID, 0,
					SystemLogRecorder.LOGMODULE_LOGISTICS, "�޸�", des + ",����:"	+ batchNO + "�� �ɹ���" + idevOK + "��ʧ�ܣ�" + idevError,
					SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		// ��ִ�н���ŵ�ServiceContext��
		this.context.put(Service.PROCESS_RESULT, lstRet);
		this.context.put(Service.SYSTEM_LOG, logDes);
	}

	/**
	 * ����D����Ŀҵ������ƶ����豸״̬��תͼ
	 * 
	 * @param actionType :
	 *            ��������
	 * @param devStatus :
	 *            Ŀ��״̬
	 * @param statusReason ��
	 *            ��תԭ��
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

		// �ҵ���ӦAction & StatusReason �����������豸״̬��Χ
		switch (actionType) {
		// ���
		case LogisticsEJBEvent.DEVICE_IN_STOCK:
			if (statusReason
					.equals(CommonConstDefinition.DEVICE_STOCKINREASON_BACK)) { // �˻�
				allowStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
			} else if (statusReason
					.equals(CommonConstDefinition.DEVICE_STOCKINREASON_REPAIR)) { // �޸�
				allowStatus = CommonConstDefinition.DEVICE_STATUS_STOCK;
			} else if (statusReason
					.equals(CommonConstDefinition.DEVICE_STOCKINREASON_REPAIR)) { // ��
				allowStatus = CommonConstDefinition.DEVICE_STATUS_REPAIRING; // �𻵲�����״̬����
			} else { // ����statusreason��δ���壬������״̬����
				allowStatus = devStatus;
			}
			break;

		// ����
		case LogisticsEJBEvent.DEVICE_OUT_STOCK:
			allowStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
			break;

		// ����
		case LogisticsEJBEvent.DEVICE_REPAIR:
			allowStatus = CommonConstDefinition.DEVICE_STATUS_WAIT4REPAIR;
			break;

		// ����
		case LogisticsEJBEvent.DEVICE_INVALIDATE:
			allowStatus = CommonConstDefinition.DEVICE_STATUS_STOCK + "|"
					+ CommonConstDefinition.DEVICE_STATUS_WAIT4REPAIR + "|"
					+ CommonConstDefinition.DEVICE_STATUS_REPAIRING;
			break;

		// ����
		case LogisticsEJBEvent.DEVICE_TRANSITION:
			allowStatus = devStatus; // ���ж�ԭ��
			break;

		// �ֹ��ı�״̬
		case LogisticsEJBEvent.DEVICE_MANULCHANGE_STATUS:
			allowStatus = devStatus; // ���ж�ԭ��
			break;
		
		//�޸��豸��Ϣ
		case LogisticsEJBEvent.DEVICE_MODIFY:
			allowStatus = devStatus; // ���ж�ԭ��
			break;
		}

		if (allowStatus.indexOf(devStatus) == -1) {
			bRet = false;
		}
		return bRet;
	}

	/**
	 * �豸�������Ȩ
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

// �õ���Ե����к�
String strSerials = deviceBatchPreauthDTO.getFileName();
String strProductList = "";
boolean isCheckPreAuth = true; //�Ƿ�����Ȩ��־��
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

// ��ִ�н���ŵ�ServiceContext��
this.context.put(Service.PROCESS_RESULT, allList);

}
	/*
	�����豸��Ȩ��¼.
*/
private void createDeviceAuth(DeviceStockEJBEvent event,DeviceBatchPreauthDTO devBatchPreAuthDto,ArrayList allList,String productList)throws ServiceException 
{
	boolean bPreAuth = false;
	if(productList.length()>0)
		bPreAuth = true;
	String[] productArray = productList.split(";");
	
	DeviceBatchPreauthHome devBatchPreauthHome;
	
	
	DeviceBatchPreauth devBatchPreauth = null;
	
	LogUtility.log(clazz,LogLevel.DEBUG,"�豸��Ȩ��ʼ");
	if (allList == null || allList.size() == 0) {
		LogUtility.log(clazz, LogLevel.WARN, "�豸��Ȩ��������");
		throw new ServiceException("�豸��Ȩ��������");
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
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸���dtoΪ" + devBatchPreAuthDto);
			devBatchPreauth2 = devBatchPreauthHome2.create(batchPreauthDto);
		}

		devBatchPreauthHome = HomeLocater.getDeviceBatchPreauthHome();
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸��ȨdtoΪ" + devBatchPreAuthDto);
		devBatchPreauth = devBatchPreauthHome.create(devBatchPreAuthDto);
		for (int i = 0; i < allList.size(); i++) {
			HashMap deviceMap = (HashMap) allList.get(i);
			Iterator itDevice = deviceMap.entrySet().iterator();
			while (itDevice.hasNext()) {

				Map.Entry deviceMapSet = (Map.Entry) itDevice.next();
				
				TerminalDeviceDTO mainDto = (TerminalDeviceDTO) deviceMapSet.getKey();
				// ��ʼ������豸dto
				TerminalDeviceDTO subDto = (TerminalDeviceDTO) deviceMapSet.getValue();
				createMatchOperation(devBatchPreauth, mainDto, subDto);
				if(bPreAuth)
					createPreauthOperation(devBatchPreauth2,mainDto,productArray);
			}
		}
	}
	catch (HomeFactoryException e) {
		LogUtility.log(clazz, LogLevel.ERROR, e);
		throw new ServiceException("�豸���кŶ�λ����");
	}
	catch (CreateException e3) {
		LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
		throw new ServiceException("������¼����");
	} catch (FinderException e) {
		LogUtility.log(clazz, LogLevel.ERROR, e);
		throw new ServiceException("�Ҳ�����ص��豸");

	}
}
private void createMatchOperation(DeviceBatchPreauth devBatchPreauth,
		TerminalDeviceDTO mainDto, TerminalDeviceDTO subDto)
		throws HomeFactoryException, CreateException, FinderException {
	// ��ʼ���豸��ת��ϸdto
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

	LogUtility.log(clazz, LogLevel.DEBUG, "�豸���DTOΪ" + detailDto1);
	devPrematchHome1.create(detailDto1);
	// �����豸����Ա�־
	TerminalDeviceHome devHome1 = HomeLocater.getTerminalDeviceHome();
	TerminalDevice dev1 = devHome1.findByPrimaryKey(new Integer(mainDto
			.getDeviceID()));
	dev1.setMatchFlag("Y");
	// �������豸������豸id
	dev1.setMatchDeviceID(subDto.getDeviceID());

	// ����豸	
	
	TerminalDevice dev2 = devHome1.findByPrimaryKey(new Integer(subDto.getDeviceID()));
	dev2.setMatchFlag("Y");
	dev2.setMatchDeviceID(mainDto.getDeviceID());
	// ��������¼�
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
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸��ת��ʼ");

		// ������
		if (allList == null || allList.size() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "�豸��ת��������");
			throw new ServiceException("�豸��ת��������");
		}
		devTransDto.setFromType(null);
		// �豸����
		int deviceNumber = allList.size();

		// ����action
		if (devTransDto.getAction() == null	|| "".equals(devTransDto.getAction()))
			devTransDto.setAction("DM");
		// ���action=DP,����Ҫ�Ѳ�Ʒ�������õ�description��
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
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����dtoΪ" + devTransDto);
			// ���action="DP",��ʾ������2�ֲ����������Ԥ��Ȩ������Ҫ�ֱ𴴽�2����ת��¼
			if ("DP".equals(devTransDto.getAction())) {
				// �����ת��¼
				devTransDto.setAction("DM");
				devTransDto.setDeviceNumber(deviceNumber * 2);

				devTrans = devTransHome.create(devTransDto);
				devTransDto.setAction("DP");

				// ���������ϸ����

				for (int i = 0; i < allList.size(); i++) {
					HashMap deviceMap = (HashMap) allList.get(i);
					Iterator itDevice = deviceMap.entrySet().iterator();
					while (itDevice.hasNext()) {

						Map.Entry deviceMapSet = (Map.Entry) itDevice.next();
						
						TerminalDeviceDTO mainDto = (TerminalDeviceDTO) deviceMapSet.getKey();
						// ��ʼ������豸dto
						TerminalDeviceDTO subDto = (TerminalDeviceDTO) deviceMapSet.getValue();
						createMatchOperation(devTrans, mainDto, subDto);

					}
				}
			}
			if ("DP".equals(devTransDto.getAction())) {
				devTransDto.setDeviceNumber(deviceNumber);
				devTrans = devTransHome.create(devTransDto);
				// ����Ԥ��Ȩ��ϸ����

				for (int i = 0; i < allList.size(); i++) {
					HashMap deviceMap = (HashMap) allList.get(i);
					Iterator itDevice = deviceMap.entrySet().iterator();

					while (itDevice.hasNext()) {

						Map.Entry deviceMapSet = (Map.Entry) itDevice.next();
						// ��ʼ���豸��ת��ϸdto
						TerminalDeviceDTO mainDto = (TerminalDeviceDTO) deviceMapSet.getKey();
						// ��ʼ������豸dto
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
						// ��ʼ������豸dto
						TerminalDeviceDTO subDto = (TerminalDeviceDTO) deviceMapSet.getValue();
						createMatchOperation(devTrans, mainDto, subDto);
					}
				}

			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�豸���кŶ�λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		}
	}

	/**
	 * ���������ϸ��ת��ϵͳ�¼�
	 * 
	 * @param devTrans
	 *            �豸��תԶ�̽ӿ�
	 * @param mainDto
	 *            ���豸
	 * @param subDto
	 *            ����豸
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */
	private void createMatchOperation(DeviceTransition devTrans,
			TerminalDeviceDTO mainDto, TerminalDeviceDTO subDto)
			throws HomeFactoryException, CreateException, FinderException {
		// ��ʼ���豸��ת��ϸdto
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
		LogUtility.log(clazz, LogLevel.DEBUG, "��Ҫ�豸������ϸdtoΪ" + detailDto1);
		devTransDetailHome1.create(detailDto1);
		// �����豸����Ա�־
		TerminalDeviceHome devHome1 = HomeLocater.getTerminalDeviceHome();
		TerminalDevice dev1 = devHome1.findByPrimaryKey(new Integer(mainDto
				.getDeviceID()));
		dev1.setMatchFlag("Y");
		// �������豸������豸id
		dev1.setMatchDeviceID(subDto.getDeviceID());

		// ����豸
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
		LogUtility.log(clazz, LogLevel.DEBUG, "����豸������ϸdtoΪ" + detailDto2);
		devTransDetailHome2.create(detailDto2);
		TerminalDevice dev2 = devHome1.findByPrimaryKey(new Integer(subDto.getDeviceID()));
		dev2.setMatchFlag("Y");
		dev2.setMatchDeviceID(mainDto.getDeviceID());
		// ��������¼�
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
	 * Ԥ��Ȩ������ϸ��ת��ϵͳ�¼�
	 * 
	 * @param devTrans
	 *            �豸��תԶ�̽ӿ�
	 * @param mainDto
	 *            ���豸
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
		// ��ʼ���豸��ת��ϸdto
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
		LogUtility.log(clazz, LogLevel.DEBUG, "��Ҫ�豸������ϸdtoΪ" + detailDto1);
		devTransDetailHome1.create(detailDto1);
		// �����豸����Ա�־
		TerminalDeviceHome devHome1 = HomeLocater.getTerminalDeviceHome();
		TerminalDevice dev1 = devHome1.findByPrimaryKey(new Integer(mainDto
				.getDeviceID()));
		dev1.setPreAuthorization("Y");
		
		//�����¼��ͼ�¼��Ȩ modify by jason 2007-2-7
		checkSCStatusCanOP(mainDto.getDeviceID());
		OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
		if (productArray != null) {
			for (int j = 0; j < productArray.length; j++) {
				int productID=Integer.valueOf(productArray[j]).intValue();
				LogUtility.log(clazz, LogLevel.DEBUG, "����ÿһ����ƷID��" + productID);
				// Ԥ��Ȩ�¼�
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
			throw new ServiceException("��������豸��Ϣ�����޷������豸��");
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
		throw new ServiceException("���к�Ϊ " + keyDevice + "���豸������");
	Iterator currentOldIterator = dev1.iterator();
	while (currentOldIterator.hasNext()) {

		TerminalDevice terminaDevice = (TerminalDevice) currentOldIterator
				.next();
		String status = terminaDevice.getStatus();
		String matchFlag = terminaDevice.getMatchFlag();
		String preAuthFlag = terminaDevice.getPreAuthorization();

		String deviceClass1 = terminaDevice.getDeviceClass();

		if (!"SC".equalsIgnoreCase(deviceClass1))
			throw new ServiceException("���к�Ϊ"
					+ terminaDevice.getSerialNo() + "���豸�������ܿ�");

		if (status == null || "".equals(status))
			throw new ServiceException("���к�Ϊ"
					+ terminaDevice.getSerialNo() + "���豸״̬Ϊ��");

		else if (!"W".equalsIgnoreCase(status.trim())
				&& !"S".equalsIgnoreCase(status.trim()))
			throw new ServiceException("���к�Ϊ"
					+ terminaDevice.getSerialNo() + "���豸״̬����");

		if ("Y".equals(matchFlag)) {
			throw new ServiceException("���к�Ϊ"
					+ terminaDevice.getSerialNo() + "���豸�Ѿ���Թ���");
		}
		if(isCheckPreAuth)
		{

		    if ("Y".equals(preAuthFlag)) {
			throw new ServiceException("���к�Ϊ"
					+ terminaDevice.getSerialNo() + "���豸�Ѿ���Ȩ����");
		    }
		}

		keyTerminalDeviceDto.setDeviceID(terminaDevice.getDeviceID()
				.intValue());
		keyTerminalDeviceDto.setSerialNo(terminaDevice.getSerialNo());
		keyTerminalDeviceDto.setStatus(terminaDevice.getStatus());

		// Ϊ���жϸ��豸���ڵĵ�ַ����:B,�û�;D,�ֿ�;T,��֯����
		keyTerminalDeviceDto.setAddressType(terminaDevice
				.getAddressType());
		keyTerminalDeviceDto.setAddressID(terminaDevice.getAddressID());
		keyTerminalDeviceDto.setDeviceModel(terminaDevice
				.getDeviceModel());

	}
	dev2 = devHome.findBySerialNo(subDevice);

	if (dev2 == null || dev2.isEmpty())
		throw new ServiceException("���к�Ϊ " + subDevice + "���豸������");

	Iterator currentOldIterator1 = dev2.iterator();
	while (currentOldIterator1.hasNext()) {

		TerminalDevice terminaDevice = (TerminalDevice) currentOldIterator1
				.next();
		String status = terminaDevice.getStatus();
		String matchFlag = terminaDevice.getMatchFlag();
		String deviceClass2 = terminaDevice.getDeviceClass();
		if (!"STB".equalsIgnoreCase(deviceClass2))
			throw new ServiceException("���к�Ϊ"
					+ terminaDevice.getSerialNo() + "���豸���ǻ�����");
		if (!status.equalsIgnoreCase("W")
				&& !status.equalsIgnoreCase("S"))
			throw new ServiceException("���к�Ϊ"
					+ terminaDevice.getSerialNo() + "���豸״̬����");
		if ("Y".equals(matchFlag)) {
			throw new ServiceException("���к�Ϊ"
					+ terminaDevice.getSerialNo() + "���豸�Ѿ���Թ���");
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
		throw new ServiceException("�豸���кŶ�λ����");
	} catch (FinderException e) {
		LogUtility.log(clazz, LogLevel.ERROR, e);
		throw new ServiceException("�Ҳ�����ص��豸");
	
	}
}
	/**
	 * ���ڿ������������豸��������豸��Ԥ��Ȩ��¼��ʱ��,��ȡ�����еĲ�Ʒid 
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
			throw new ServiceException("�豸Ԥ��Ȩ��¼��λ����");
		}catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ����豸Ԥ��Ȩ��¼");
		}
	}
	/**
	 * ���ڿ������������豸������ʱ������豸��Ԥ��Ȩ��¼ add by Yangchen 2008/06/20
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
			throw new ServiceException("�豸Ԥ��Ȩ��¼��λ����");
		}
		catch (RemoveException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("ɾ���豸Ԥ��Ȩ��¼���");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ����豸Ԥ��Ȩ��¼");
		}
	}
	/**
	 * �����豸��Ȩ
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void devPreauthTrans(DeviceStockEJBEvent event)
			throws ServiceException {
		// �豸����home�ӿ�
		DeviceTransitionHome devTransHome;
		// Զ�̽ӿ�
		DeviceTransition devTrans;

		TerminalDevice terminalDevice;
		// �豸������ϸ�ӿ�
		DeviceTransitionDetailHome devTransDetailHome;

		DeviceTransitionDTO devTransDto = event.getDeviceTransitionDTO();

		String[] productArray = null;
		// ȡ��deviceId
		int deviceId = devTransDto.getDeviceNumber();
		int opID = event.getOperatorID();

		devTransDto.setOperatorID(opID);

		// �õ����ܿ������к�
		String strSerials = devTransDto.getFromType();
		devTransDto.setFromType(null);

		// �豸����
		devTransDto.setDeviceNumber(1);
		// ����action
		devTransDto.setAction("DP");

		// ���ô���ʱ��
		devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
		// ����״̬
		devTransDto.setStatus("V");
		// ����productlist
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
			// ȡ���豸home �ӿ�
			TerminalDeviceHome devHome = HomeLocater.getTerminalDeviceHome();

			// �õ��豸��Զ�̽ӿ�
			terminalDevice = devHome.findByPrimaryKey(new Integer(deviceId));
			/*****************************remove start  YANGCHEN 2008/06/20***************************************/
			//��Ȩ����ȥ���¸ñ�־
			// ��������Ȩ��־
			//terminalDevice.setPreAuthorization("Y");
			/*****************************remove END YANGCHEN 2008/06/20***************************************/
			devTransHome = HomeLocater.getDeviceTransitionHome();
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����dtoΪ" + devTransDto);
			// �����豸��ת��¼ͷ��¼
			devTrans = devTransHome.create(devTransDto);
			// �����豸��ת��ϸ��¼
			DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO(); // initialize
			// the
			// detail
			// dto
			// ����batchId
			detailDto.setBatchID(devTrans.getBatchID().intValue());
			// ���� deviceId
			detailDto.setDeviceID(deviceId);
			// �������к�
			detailDto.setSerialNo(strSerials);
			// ����״̬
			detailDto.setStatus("V");
			
			detailDto.setFromType(terminalDevice.getAddressType());
			detailDto.setFromID(terminalDevice.getAddressID());
			detailDto.setFromDeviceStatus(terminalDevice.getStatus());
			detailDto.setToType(terminalDevice.getAddressType());
			detailDto.setToID(terminalDevice.getAddressID());
			detailDto.setToDeviceStatus(terminalDevice.getStatus());
			
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸��ϸ����dtoΪ" + detailDto);
			devTransDetailHome = HomeLocater.getDeviceTransitionDetailHome();
			// ������ϸ��¼
			devTransDetailHome.create(detailDto);
			/*****************************modify by yangchen 2008/06/20 start ***************************************/
			/*****************************remove start ***************************************/
			
			//����ϵͳ�¼��ͼ�¼��Ȩ��¼
			/*checkSCStatusCanOP(deviceId);
			OssAuthorizationHome ossAuthorizationHome=HomeLocater.getOssAuthorizationHome();
			if (productArray != null) {
				for (int j = 0; j < productArray.length; j++) {
					int productID=Integer.valueOf(productArray[j]).intValue();
					LogUtility.log(clazz, LogLevel.DEBUG, "����ÿһ����ƷID��"	+ productID);
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
//						//֧���豸��Ȩ״̬����
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
			//��¼�豸��Ԥ��Ȩ
			DeviceBatchPreauthDTO deviceBatchPreauthDTO = new DeviceBatchPreauthDTO();
			deviceBatchPreauthDTO.setReferSheetSerialNO(devTransDto.getBatchNo());
			//�������ֵ�ں���Ĵ����лᱻ��գ��������ﲻ����ֵ
			deviceBatchPreauthDTO.setFileName("");
			deviceBatchPreauthDTO.setDescription(devTransDto.getDescription());
			deviceBatchPreauthDTO.setOpId(opID);
			
			if(devTransDto.getDescription()==null ||"".equals(devTransDto.getDescription()))
				throw new ServiceException("û��ָ����Ȩ�Ĳ�Ʒ");
			ArrayList allList = new ArrayList();
			allList.add(checkAuth(strSerials));
			//������Ȩ�¼��Ѿ���Ȩ��¼
			createAuthRecord(event,deviceBatchPreauthDTO, allList,productFroPreAuth);
			/*****************************remove end ***************************************/
			/*****************************modify by yangchen 2008/06/20 end ***************************************/
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�豸���кŶ�λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		}
	}

	/**
	 * �豸��Խ��
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
				throw new ServiceException("���豸������");
			TerminalDevice terDeviceSC = (TerminalDevice)itDev.next();
			if (terDeviceSC == null)
				throw new ServiceException("���豸������ ���к�=" + terDeviceSC.getSerialNo());
			if (!"Y".equals(terDeviceSC.getMatchFlag()))
				throw new ServiceException("���豸δ��� ���к�=" + terDeviceSC.getSerialNo());
			if (!"S".equals(terDeviceSC.getStatus()) && !"W".equals(terDeviceSC.getStatus()))
				throw new ServiceException("���豸״̬��Ϊ���ۻ��߿�� ���к�=" + terDeviceSC.getSerialNo());
			if (terDeviceSC.getDeviceClass().equalsIgnoreCase("STB")) {
				throw new ServiceException("���豸���Ͳ���ȷ,��ѡ�����ܿ� ���к�=" + terDeviceSC.getSerialNo());
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
			throw new ServiceException("�豸���кŶ�λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		}		
	}
	/**
	 * �������ܿ����Ԥ��Ȩ
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void scdevReleasePreauthTrans(DeviceStockEJBEvent event)
			throws ServiceException {

		try {
			// �豸����home�ӿ�
			DeviceTransitionHome devTransHome;
			// Զ�̽ӿ�
			DeviceTransition devTrans;
			// �豸������ϸ�ӿ�
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
				throw new ServiceException("���豸������");
			/*****************************remove start yangchen 2008/06/23 ***************************************/
			// �޸�����Ȩ��־
			//terDevice.setPreAuthorization("N");
			/*****************************remove end yangchen 2008/06/23 ***************************************/

			// �豸����
			devTransDto.setDeviceNumber(1);
			// ����action
			devTransDto.setAction("DR");
			// ���ô���ʱ��
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			// ����״̬
			devTransDto.setStatus("V");
			devTransDto.setOperatorID(opID);
			devTransHome = HomeLocater.getDeviceTransitionHome();
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����dtoΪ" + devTransDto);
			// �����豸��ת��¼ͷ��¼
			devTrans = devTransHome.create(devTransDto);

			DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
			// �����豸��ת��ϸ��¼
			detailDto.setBatchID(devTrans.getBatchID().intValue());

			detailDto.setSerialNo(terDevice.getSerialNo());
			detailDto.setDeviceID(terDevice.getDeviceID().intValue());
			devTransDetailHome = HomeLocater.getDeviceTransitionDetailHome();
			// ������ϸ��¼
			devTransDetailHome.create(detailDto);
			/*****************************remove start yangchen 2008/06/23 ***************************************/
			//֧���豸��Ȩ״̬����add by jason 2007-2-7
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
			throw new ServiceException("�豸���кŶ�λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		}

	}
	
	/**
	 * �÷�����Ҫ�������ܿ���Ȩ�ͽ���Ȩ�����ļ��
	 * @param deviceID
	 * @throws ServiceException 
	 */
	private void checkSCStatusCanOP(int deviceID) throws ServiceException{
		try{	
			TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
			TerminalDevice device=terminalDeviceHome.findByPrimaryKey(new Integer(deviceID));
			
			if(!(CommonConstDefinition.DEVICE_STATUS_WAITFORSELL.equals(device.getStatus()) || 
					CommonConstDefinition.DEVICE_STATUS_STOCK.equals(device.getStatus()))){
				throw new ServiceException("�豸" + deviceID + "״̬��Ϊ���ۻ��߿�棬�޷���ɴ˲�����");
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(this.getClass(), LogLevel.ERROR, "����豸״̬������Ӧ���豸IDΪ" + deviceID);
			throw new ServiceException("����豸״̬������Ӧ���豸IDΪ" + deviceID);
		}
		catch(FinderException e2){
			LogUtility.log(this.getClass(), LogLevel.ERROR, "����豸״̬������Ӧ���豸IDΪ" + deviceID);
			throw new ServiceException("����豸״̬������Ӧ���豸IDΪ" + deviceID);
		}
		
		
	}
	/**
	 * �豸��Ϣ�޸�
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void deviceInfoModify(DeviceStockEJBEvent event) throws ServiceException {
		//�����豸��Ϣ
		TerminalDeviceDTO terminalDeviceDTO = event.getTerDeviceDTO();
	
		int opID = event.getOperatorID();

		String devTransAction = CommonConstDefinition.DEVICE_TRANSACTION_ACTION_X;
		//String deviceToStatus = event.getToStatus();

		TerminalDeviceDTO oldDTO = BusinessUtility.getDeviceByDeviceID(terminalDeviceDTO.getDeviceID());
		List deviceIDList = new ArrayList();
		deviceIDList.add(new Integer(terminalDeviceDTO.getDeviceID()));
		//��¼�豸��ת��ϸ
		deviceTransitionForDeviceInfoModify(event.getActionType(), deviceIDList,opID, event.getDeviceTransitionDTO().getBatchNo(), devTransAction, 
				terminalDeviceDTO.getStatus(), oldDTO.getAddressType(), terminalDeviceDTO.getAddressType(), "", 
				oldDTO.getAddressID(), terminalDeviceDTO.getAddressID(), "", "�޸��豸��Ϣ", false, event.getRemoteHostAddress(),terminalDeviceDTO.getDepotID());

		TerminalDeviceHome devHome = null;
		TerminalDevice dev=null;
		try{
			devHome = HomeLocater.getTerminalDeviceHome();
			dev = devHome.findByPrimaryKey(new Integer(terminalDeviceDTO.getDeviceID()));
			if(dev.ejbUpdate(terminalDeviceDTO)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸��豸��Ϣ����ԭ��ejbUpdate(terminalDeviceDTO)==-1");
	    		throw new ServiceException("�޸��豸��Ϣ����");
			}
			
		}
		catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"�޸��豸��Ϣ����ԭ���豸��λ����");
    		throw new ServiceException("�����豸��Ϣ����ԭ���豸��λ����");
    		
    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.WARN,"�����豸��Ϣ����ԭ���豸���ҳ���");
    		throw new ServiceException("�����豸��Ϣ����ԭ���豸���ҳ���");
    	}    	
		
		
		//����ϵͳ��־��¼
		StringBuffer logDesc=new StringBuffer();
	
		logDesc.append("�޸��豸��Ϣ,�豸ID:");
		logDesc.append(terminalDeviceDTO.getDeviceID());
		logDesc.append(SystemLogRecorder.appendDescString(";���к�:",
				oldDTO.getSerialNo(),terminalDeviceDTO.getSerialNo()));
		logDesc.append(SystemLogRecorder.appendDescString(";����:",
				""+BusinessUtility.getDeiceClassMop().get(oldDTO.getDeviceClass()),
				""+BusinessUtility.getDeiceClassMop().get(terminalDeviceDTO.getDeviceClass())));
		logDesc.append(SystemLogRecorder.appendDescString(";�ͺ�:",
				oldDTO.getDeviceModel(),terminalDeviceDTO.getDeviceModel()));
		//logDesc.append(SystemLogRecorder.appendDescString(";����:",
		//		""+oldDTO.getBatchID(),""+terminalDeviceDTO.getBatchID()));
		logDesc.append(SystemLogRecorder.appendDescString(";�ֿ�:",
				""+BusinessUtility.getDepotMop().get(new Integer(oldDTO.getDepotID())),
				""+BusinessUtility.getDepotMop().get(new Integer(terminalDeviceDTO.getDepotID()))));
		logDesc.append(SystemLogRecorder.appendDescString(";��������:",
				""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICEADDRESSTYPE", oldDTO
						.getAddressType()),""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICEADDRESSTYPE", terminalDeviceDTO
						.getAddressType())));
		logDesc.append(SystemLogRecorder.appendDescString(";����:",
				""+oldDTO.getAddressID(),""+terminalDeviceDTO.getAddressID()));
		logDesc.append(SystemLogRecorder.appendDescString(";����:",
				""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICESELLMETHOD", oldDTO
						.getLeaseBuy()),""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICESELLMETHOD", terminalDeviceDTO
						.getLeaseBuy())));
		logDesc.append(SystemLogRecorder.appendDescString(";��������:",
				""+oldDTO.getDateFrom(),""+terminalDeviceDTO.getDateFrom()));
		logDesc.append(SystemLogRecorder.appendDescString(";���:",
				oldDTO.getMatchFlag(),terminalDeviceDTO
						.getMatchFlag()));
		logDesc.append(SystemLogRecorder.appendDescString(";����豸��:",
				""+oldDTO.getMatchDeviceID(),""+terminalDeviceDTO.getMatchDeviceID()));
		logDesc.append(SystemLogRecorder.appendDescString(";��λ���:",
				oldDTO.getCaSetFlag(),terminalDeviceDTO.getCaSetFlag()));
		logDesc.append(SystemLogRecorder.appendDescString(";��λ����:",
				""+oldDTO.getCaSetDate(),""+terminalDeviceDTO.getCaSetDate()));
		logDesc.append(SystemLogRecorder.appendDescString(";���޽�ֹ����:",
				""+oldDTO.getDateTo(),""+terminalDeviceDTO.getDateTo()));
		logDesc.append(SystemLogRecorder.appendDescString(";�����ڵĳ���:",
				""+oldDTO.getGuaranteeLength(),""+terminalDeviceDTO.getGuaranteeLength()));
		logDesc.append(SystemLogRecorder.appendDescString(";CM MAC��ַ:",
				oldDTO.getMACAddress(),terminalDeviceDTO.getMACAddress()));
		logDesc.append(SystemLogRecorder.appendDescString(";�ն�MAC��ַ:",
				oldDTO.getInterMACAddress(),terminalDeviceDTO.getInterMACAddress()));
		logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ����:",
				""+BusinessUtility
				.getCommonNameByKey("SET_D_USEFLAG", oldDTO
						.getUseFlag()),""+BusinessUtility
				.getCommonNameByKey("SET_D_USEFLAG", terminalDeviceDTO
						.getUseFlag())));
		logDesc.append(SystemLogRecorder.appendDescString(";״̬:",
				""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICESTATUS", oldDTO
						.getStatus()),""+BusinessUtility
				.getCommonNameByKey("SET_D_DEVICESTATUS", terminalDeviceDTO
						.getStatus())));
		logDesc.append(SystemLogRecorder.appendDescString(";�豸Ԥ��Ȩ:",
				oldDTO.getPreAuthorization(),terminalDeviceDTO.getPreAuthorization()));
		
		
		logDesc.append(SystemLogRecorder.appendDescString(";�豸��;:",
				BusinessUtility.getDevicePurposeStrListDes(oldDTO.getPurposeStrList()),
				BusinessUtility.getDevicePurposeStrListDes(terminalDeviceDTO.getPurposeStrList())));
		String logStr = logDesc.toString();
		if(logStr.length()>1000)
		{
			logStr = logStr.substring(0,1000)+"...";
		}
		
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), opID,
				0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "�޸��豸��Ϣ",
				logStr,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * ���������豸�Ƿ�Ϸ�
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
			 �����豸���:
			 ����/���� ���豸���ܹ����� 
		   */ 
				
		if(!("R".equals(dev.getStatus())||"M".equals(dev.getStatus())))
			errMsgValid.append(serialNo+",");
			
			
		}
		
		String errMsgNoneStr = errMsgNone.toString();
		String errMsgValidStr = errMsgValid.toString();
	
		if(errMsgNoneStr != null && !"".equals(errMsgNoneStr))
		{
			throw new ServiceException("�豸["+errMsgNoneStr.substring(0,errMsgNoneStr.length()-1)+"]������");
		}
		if(errMsgValidStr != null && !"".equals(errMsgValidStr))
		{
			throw new ServiceException("�豸["+errMsgValidStr.substring(0,errMsgValidStr.length()-1)+"]��״̬���ǡ����ޡ��򡰴��ޡ�,���ܱ���!");
		}
	}
	
	/**
	 * �豸����
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
			// �����豸��תͷ��¼
			// ���ô���ʱ��
			devTransDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			// ����״̬
			devTransDto.setStatus("V");
			devTransDto.setOperatorID(opID);
			devTransDto.setAction("I");
			devTransDto.setDeviceNumber(serialsList.size());
			//ͷ��¼�е�fromType fromId toType toId �����豸���ܲ�ͬ,û����д
			
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
					// �����豸��ת��ϸ��¼
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
					//����ͷ��¼[�������������һ����¼Ϊ׼��]
					/*
					devTrans.setFromType(dev.getAddressType());
					devTrans.setFromID(dev.getAddressID());
					devTrans.setToType(dev.getAddressType());
					devTrans.setToID(dev.getAddressID());
					*/
					//ִ�д����豸��ת��ϸ��¼
					devTransDetailHome.create(detailDto);

					// �޸��豸״̬
					dev.setStatus(newStatus);
				}
			}
			context.put(com.dtv.oss.service.Service.PROCESS_RESULT, "");

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�豸���кŶ�λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		}
	}
	public void devPreAuth(DeviceStockEJBEvent event)
	throws ServiceException {
		DeviceBatchPreauthDTO deviceBatchPreauthDTO = event.getDeviceBatchPreauthDTO();
		ArrayList allList = new ArrayList();
		int opID = event.getOperatorID();
		deviceBatchPreauthDTO.setOpId(opID);

		// �õ���Ե����к�
		String strSerials = deviceBatchPreauthDTO.getFileName();
		String strProductList = "";
		if(strSerials.indexOf("$")!= -1){
			strProductList = strSerials.substring(strSerials.indexOf("$") + 1,strSerials.length());
			strSerials = strSerials.substring(0,strSerials.indexOf("$"));
		}
		else
			throw new ServiceException("û����Ȩ�Ĳ�Ʒ");

		String[] serialsArray = strSerials.split("\r\n");

		for (int i = 0; i < serialsArray.length; i++) {
			if(serialsArray[i].length()>0)
				allList.add(checkAuth(serialsArray[i]));

		}
		if (event.isDoPost())
			createAuthRecord(event,deviceBatchPreauthDTO, allList,strProductList);

		// ��ִ�н���ŵ�ServiceContext��,������޸��ˣ�����û�б�Ҫ�ٴ洢
		//this.context.put(Service.PROCESS_RESULT, allList); 
	}
	/**
	 * �豸��Խ����Ȩ
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
			
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����dtoΪ" + devBatchPreauthDto);
			// �����豸��ת��¼ͷ��¼
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
					throw new ServiceException("�豸�����ڡ����к�="+terDeviceSC.getSerialNo());
				
				
				if (!"Y".equals(terDeviceSC.getMatchFlag()))
					throw new ServiceException("�豸���״̬����ȷ�����к�="+terDeviceSC.getSerialNo());
				
				if (terDeviceSC.getDeviceClass().equalsIgnoreCase("SC")) {
					scDeviceID = terDeviceSC.getDeviceID().intValue();
					scSerialNo = terDeviceSC.getSerialNo();
					stbDeviceID = terDeviceSC.getMatchDeviceID();
					stbSerialNo = BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(stbDeviceID)); 
				}
				if (terDeviceSC.getDeviceClass().equalsIgnoreCase("STB")) {
					throw new ServiceException("�豸���Ͳ���ȷ,��ѡ�����ܿ� ���к�="+terDeviceSC.getSerialNo());
				}

				// �����豸���״̬
				terDeviceSC.setMatchFlag("N");
				// ��������豸ID
				terDeviceSC.setMatchDeviceID(0);
				TerminalDevice terDeviceSTB = devHome.findByPrimaryKey(new Integer(stbDeviceID));
				if (terDeviceSTB == null)
					throw new ServiceException("�豸��Ӧ�Ļ����в����� ���к�="+terDeviceSC.getSerialNo());
				//���º��ӵ�״̬����ԣɣ�
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
			throw new ServiceException("�豸���кŶ�λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		}
	}
	public TerminalDeviceDTO checkUnauth(String deviceSN)throws ServiceException{
		TerminalDeviceDTO terDto =BusinessUtility.getDeviceBySerialNo(deviceSN);
		if (!"Y".equals(terDto.getPreAuthorization()))
			throw new ServiceException("���豸δ��Ԥ��Ȩ ���к�=" + terDto.getSerialNo());
		if (!"S".equals(terDto.getStatus()) && !"W".equals(terDto.getStatus()))
			throw new ServiceException("���豸״̬��Ϊ���ۻ��߿�� ���к�=" + terDto.getSerialNo());
	
		return terDto;
	}
	
	public TerminalDeviceDTO checkAuth(String deviceSN)throws ServiceException{
	   TerminalDeviceDTO terDto =BusinessUtility.getDeviceBySerialNo(deviceSN);
	   if ("Y".equals(terDto.getPreAuthorization()))
		  throw new ServiceException("���豸�Ѿ�Ԥ��Ȩ ���к�="+terDto.getSerialNo());
	   if (!"S".equals(terDto.getStatus()) && !"W".equals(terDto.getStatus()))
		  throw new ServiceException("���豸״̬��Ϊ���ۻ��߿�� ���к�="+terDto.getSerialNo());
				
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
			
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����dtoΪ" + devBatchPreauthDto);
			// �����豸��ת��¼ͷ��¼
			DeviceBatchPreauth  devPreauthBatch = devBatchPreauthHome.create(devBatchPreauthDto);
			String scSerialNo= "";
			for(int i=0;i<allList.size();i++){
				TerminalDeviceDTO dto = (TerminalDeviceDTO)allList.get(i);
				// �����豸����Ա�־
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
				element.add("0");  //�ͻ�֤��
				element.add("0");  //�˻���
			  	element.add("0"); //ҵ���˻���
				element.add(String.valueOf(BusinessUtility.getProductIDByModel(dto.getDeviceModel()))); //��Ʒid
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
			throw new ServiceException("�豸���кŶ�λ����");
		}catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		}catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		}catch(RemoveException e4){
			LogUtility.log(clazz, LogLevel.WARN, "ɾ����¼����" + e4);
			throw new ServiceException("ɾ����¼����");
		}
	}
	public void createAuthRecord(DeviceStockEJBEvent event,DeviceBatchPreauthDTO devBatchPreAuthDto,ArrayList allList,String productList)throws ServiceException
	{
		if(productList.length()==0)
			throw new ServiceException("û��Ԥ��Ȩ��Ʒ");
		String[] productArray = productList.split(";");
		
		DeviceBatchPreauthHome devBatchPreauthHome;
		DeviceBatchPreauth devBatchPreauth = null;
		LogUtility.log(clazz,LogLevel.DEBUG,"�豸��Ȩ��ʼ");
		if (allList == null || allList.size() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "�豸��Ȩ��������");
			throw new ServiceException("�豸��Ȩ��������");
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
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸��ȨdtoΪ" + devBatchPreAuthDto);
			devBatchPreauth = devBatchPreauthHome.create(devBatchPreAuthDto);
			for (int i = 0; i < allList.size(); i++) {
				TerminalDeviceDTO mainDto = (TerminalDeviceDTO) allList.get(i);
				createPreauthOperation(devBatchPreauth,mainDto,productArray);
			}
		}catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�豸���кŶ�λ����");
		}catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.WARN, "������¼����" + e3);
			throw new ServiceException("������¼����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�Ҳ�����ص��豸");

		}
	}
	
	private void createPreauthOperation(DeviceBatchPreauth devBatchPreauth,
			TerminalDeviceDTO mainDto,
			String[] productArray) throws HomeFactoryException,
			CreateException, FinderException, ServiceException {

		TerminalDeviceHome terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
		
		//Ӧ����Ҫ������
		Collection execCol =new ArrayList();
		ArrayList element =new ArrayList();
		element.add("0");  //�ͻ�֤��
		element.add("0");  //�˻���
	  	element.add("0"); //ҵ���˻���
		element.add(String.valueOf(BusinessUtility.getProductIDByModel(mainDto.getDeviceModel()))); //��Ʒid
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
			// ��ʼ���豸��Ȩ��ϸdto
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
			
			LogUtility.log(clazz, LogLevel.DEBUG, "��Ҫ�豸��Ȩ��ϸdtoΪ" + detailDto1);

			//��¼ϵͳ�¼�
			LogUtility.log(clazz, LogLevel.DEBUG, "��ƷID��:" + productID);
			
			Collection systemEventCol =new ArrayList();
			ArrayList systemElement =new ArrayList();
			systemElement.add("0");  //�ͻ�֤��
			systemElement.add("0");  //�˻���
			systemElement.add("0");  //ҵ���˻���
			systemElement.add(String.valueOf(productID)); //��Ʒid
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
				//֧���豸��Ȩ״̬����
				OssAuthorizationDTO ossAuthorizationDTO=new OssAuthorizationDTO();
				ossAuthorizationDTO.setDeviceID(mainDto.getDeviceID());
				ossAuthorizationDTO.setDeviceSerialNo(mainDto.getSerialNo());
				ossAuthorizationDTO.setProductID(productID);
				BusinessUtility.insertOssAuthorization(ossAuthorizationDTO);
			}
		}
		// �����豸����Ա�־
		BusinessUtility.updatePreAuthorizationForTerminalDevice("Y",mainDto
				.getDeviceID());
	}
	/**
	 * �豸Ԥ��Ȩˢ��,
	 * ������ɵ���Ȩ,�ٸ��ݼ�¼����������Ȩ��Ϣ.
	 * @param serialNoList
	 * @param operatorid
	 * @throws ServiceException
	 */
	public void refurbishPreAuthorization(ArrayList serialNoList,int operatorid) throws ServiceException{
		if(serialNoList==null||serialNoList.isEmpty()){
			throw new ServiceException("û����ص��豸��Ϣ");
		}
		for(Iterator it=serialNoList.iterator();it.hasNext();){
			String serialNo=(String) it.next();
			TerminalDeviceDTO terDto=BusinessUtility.getDeviceBySerialNo(serialNo);
			if(terDto==null){
				throw new ServiceException("��Ч���豸:"+serialNo);
			}
			if(!"Y".equals(terDto.getPreAuthorization())){
				throw new ServiceException("��δ���й�Ԥ��Ȩ���豸:"+serialNo);
			}

			try {
				SystemEventRecorder.addEvent4ReleaseDevicePreauth(
						SystemEventRecorder.DEVICE_RELEASEPREAUTH, terDto.getDeviceID(),
						serialNo, operatorid, "C");
			} catch (HomeFactoryException e) {
				LogUtility.log(clazz, LogLevel.ERROR, "ˢ����Ȩ����,�豸���к�:"+serialNo+",��������:"+e);				
				throw new ServiceException("ˢ����Ȩ����:"+serialNo);
			} catch (CreateException e) {
				LogUtility.log(clazz, LogLevel.ERROR, "ˢ����Ȩ����,�豸���к�:"+serialNo+",��������:"+e);				
				throw new ServiceException("ˢ����Ȩ����:"+serialNo);
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
					LogUtility.log(clazz, LogLevel.ERROR, "ˢ����Ȩ����,�豸���к�:"+serialNo+",��ƷID:"+productId+",��������:"+e);
					throw new ServiceException("ˢ����Ȩ����:"+serialNo);
				} catch (CreateException e) {
					LogUtility.log(clazz, LogLevel.ERROR, "ˢ����Ȩ����,�豸���к�:"+serialNo+",��ƷID:"+productId+",��������:"+e);
					throw new ServiceException("ˢ����Ȩ����:"+serialNo);
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
