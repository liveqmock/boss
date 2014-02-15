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
			// �豸���
			case LogisticsEJBEvent.DEVICE_IN_STOCK:
				devInStock(inEvent);
				break;

			// �豸����
			case LogisticsEJBEvent.DEVICE_OUT_STOCK:
				devOutStock(inEvent);
				break;

			// �豸��ת
			case LogisticsEJBEvent.DEVICE_TRANSITION:
				devTrans(inEvent);
				break;

			// �豸����
			case LogisticsEJBEvent.DEVICE_INVALIDATE:
				deviceScrap(inEvent);
				break;

			// �豸�ֹ��޸�״̬
			case LogisticsEJBEvent.DEVICE_MANULCHANGE_STATUS:
				devStatusChange(inEvent);
				break;

			// �豸����
			case LogisticsEJBEvent.DEVICE_REPAIR:
				deviceRepair(inEvent);
				break;
			// �豸�������Ȩ
			case LogisticsEJBEvent.DEVICE_PREAUTH_MATCH:
				System.out.println("�豸�������Ȩ");
				matchPreAuth(inEvent);
				break;
			// ���ڵ����豸��Ȩ
			case LogisticsEJBEvent.DEVICE_PREAUTH_1:
				everyDevicePreAuth(inEvent);
				break;
			// �豸������
			case LogisticsEJBEvent.UNMATCH_DEVICE:
				unmatchDevice(inEvent);
				break;
			// �豸���Ԥ��Ȩ
			case LogisticsEJBEvent.DEVICE_RELEASEPREAUTH:
				deviceReleasePreauth(inEvent);
				break;
			//�޸��豸��Ϣ
			case LogisticsEJBEvent.DEVICE_MODIFY:
				deviceInfoModify(inEvent);
				break;
			case LogisticsEJBEvent.DEVICE_PREAUTH:
				devicePreAuth(inEvent);	
				break;
			case LogisticsEJBEvent.DEVICE_DISAUTH:
				deviceDisauth(inEvent);
				break;
			//ˢ����Ȩ	
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
			throw new CommandException("δ֪����");
		}
		return response;
	}

	/**
	 * �豸�ؿ�
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

		// ����ֵ
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:"
				+ logDes.toString());

		//��¼�ؿ�ԭ��:
		String txtStatusReason = BusinessUtility.getCommonNameByKey("SET_D_STOCKINREASON",event.getDevTransDTO().getStatusReason());
		
		if (event.isDoPost())
		{
			if(logDes.length()>400)logDes=logDes.substring(0,400) +"...";
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
				"�豸�ؿ�", "�ؿ�ԭ��:"+txtStatusReason+",�ؿ��豸���:" +logDes,SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}

	/**
	 * �豸����
	 * ADD BY JASON
	 * @param event
	 * @throws ServiceException
	 */
	private void devOutStock(DeviceStockEJBEvent event) throws ServiceException {
		this.context = new ServiceContext();
		
		//����豸����Ƿ��������
		if(event.getDevTransDTO()==null || event.getDevArrayStr()==null ||
				"".equals(event.getDevArrayStr()))
			throw new ServiceException("�豸��Ϣ��ȫ���޷����г��������");
		if(event.getDevTransDTO().getToID()==0)
			throw new ServiceException("�豸ת�����֯δ֪���޷����г��������");
		
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
					throw new ServiceException("�豸���к�[" + deviceSeriaNoList[i] +"]������");
				
				TerminalDevice td=(TerminalDevice)tdList.toArray()[0];
				if(!CommonConstDefinition.DEVICE_STATUS_STOCK.equals(td.getStatus()))
					throw new ServiceException("�豸���к�[" + deviceSeriaNoList[i] +"]��״̬��Ϊ���");
				deviceIDList.add(td.getPrimaryKey());
				
				if(dto.getFromID()==0 || dto.getFromType()==null || "".equals(dto.getFromType())){
					dto.setFromID(td.getAddressID());
					dto.setFromType(td.getAddressType());
				}
			}
			catch(HomeFactoryException e){
				LogUtility.log(clazz, LogLevel.WARN, e);
				throw new ServiceException("�豸���к�[" + deviceSeriaNoList[i] +"]������");
			}
			catch(FinderException e){
				LogUtility.log(clazz, LogLevel.WARN, e);
				throw new ServiceException("�豸���к�[" + deviceSeriaNoList[i] +"]������");
			}
		}
		
		if(!event.isDoPost())
			return;
		
		event.setDevArray(deviceIDList);
		DeviceStockService dss=new DeviceStockService(this.context);
		dss.deviceOutStock(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
		this.response.setPayload(lstRet);
	}

	/**
	 * �豸����
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void deviceScrap(DeviceStockEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException{
		this.context = new ServiceContext();
		new DeviceStockService(this.context).deviceScrap(event);

		// ����ֵ
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:"
				+ strRe.toString());

		String logDes = strRe.toString();
		if(logDes.length()>400)logDes=logDes.substring(0,400) +"...";
		if (event.isDoPost())
		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "�豸����", "�����豸���:"
						+ logDes,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * �豸����
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void devRepair(DeviceStockEJBEvent event) throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devRepair(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}

	/**
	 * �豸״̬�޸�
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void devStatusChange(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devStatusChange(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}

	/**
	 * �豸����
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void devTrans(DeviceStockEJBEvent event) throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devTrans(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}

	/**
	 * �豸ƥ������Ȩ
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
						SystemLogRecorder.LOGMODULE_LOGISTICS, "�������/Ԥ��Ȩ",
						"�������/Ԥ��Ȩ:"+event.getDeviceBatchPreauthDTO().getReferSheetSerialNO(),
						SystemLogRecorder.LOGCLASS_NORMAL,
						SystemLogRecorder.LOGTYPE_APP);

		/*String serialNoOfDevices=event.getDeviceTransitionDTO().getFromType();
		if(serialNoOfDevices==null)
			serialNoOfDevices="";
		
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devMatchAndPreauthTrans(event);
		
		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
		if (!event.isDoPost()){
			 this.response.setPayload(lstRet);
			 return;
		}

		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
						SystemLogRecorder.LOGMODULE_LOGISTICS, "�������/Ԥ��Ȩ",
						"�������/Ԥ��Ȩ���豸���:"+ serialNoOfDevices,
						SystemLogRecorder.LOGCLASS_NORMAL,
						SystemLogRecorder.LOGTYPE_APP);*/

	}
	/**
	 * �豸��Ȩ
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
				SystemLogRecorder.LOGMODULE_LOGISTICS, "���ܿ�Ԥ��Ȩ","���ܿ�Ԥ��Ȩ:"
						+ event.getDeviceBatchPreauthDTO().getReferSheetSerialNO(),
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * �����豸��Ȩ
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void everyDevicePreAuth(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devPreauthTrans(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}

	/**
	 * �豸��Խ��
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void unmatchDevice(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).devMatchTrans(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
		if (!event.isDoPost()){
			 this.response.setPayload(lstRet);
			 return;
		}
		
		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				SystemLogRecorder.LOGMODULE_LOGISTICS, "���������","���������:"
						+ event.getDeviceBatchPreauthDTO().getReferSheetSerialNO(),
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * �豸Ԥ��Ȩ���
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void deviceReleasePreauth(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).scdevReleasePreauthTrans(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}
	
	

	/**
	 * �豸����
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

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT);
			this.response.setPayload(lstRet);
		}
		StringBuffer strRe=new StringBuffer();
		Iterator it=lstRet.iterator();
		while(it.hasNext())
			strRe.append(it.next().toString()).append("//");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:" + strRe.toString());

		//��־�Ѽ�¼
//		if (event.isDoPost())
//			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
//					"�豸����", "�����豸���:"+ strRe.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * �豸��Ϣ�޸�
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	private void deviceInfoModify(DeviceStockEJBEvent event)
			throws ServiceException {
		this.context = new ServiceContext();
		new DeviceStockService(this.context).deviceInfoModify(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null)
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

		this.response.setPayload(lstRet);
	}
	/**
	 * �豸����Ȩ
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
				SystemLogRecorder.LOGMODULE_LOGISTICS, "���ܿ�����Ȩ","���ܿ�����Ȩ:"
						+ event.getDeviceBatchPreauthDTO().getReferSheetSerialNO(),
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);

	}
	
	
	/**
	 * ˢ���豸Ԥ��Ȩ
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
				SystemLogRecorder.LOGMODULE_LOGISTICS, "���ܿ�ˢ��Ԥ��Ȩ","���ܿ�ˢ��Ԥ��Ȩ,���ܿ����к�:"
						+ event.getTerDeviceDTO().getSerialNo(),
				SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
	}
}
