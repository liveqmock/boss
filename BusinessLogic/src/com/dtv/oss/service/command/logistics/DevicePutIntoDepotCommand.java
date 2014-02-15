package com.dtv.oss.service.command.logistics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.DeviceTransition;
import com.dtv.oss.domain.DeviceTransitionDetail;
import com.dtv.oss.domain.DeviceTransitionDetailHome;
import com.dtv.oss.domain.DeviceTransitionHome;
import com.dtv.oss.domain.LogisticsSetting;
import com.dtv.oss.domain.LogisticsSettingHome;
import com.dtv.oss.domain.TerminalDevice;
import com.dtv.oss.domain.TerminalDeviceHome;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.dto.VodstbDeviceImportHeadDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DevicePutIntoDepotEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

public class DevicePutIntoDepotCommand extends Command {
	private static final Class clazz = DevicePutIntoDepotCommand.class;

	CommandResponseImp response = null;

	private int operatorID = 0;

	private String machineName = "";

	private CommonQueryConditionDTO devConDTO;

	ServiceContext context = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {

		response = new CommandResponseImp(null);
		DevicePutIntoDepotEJBEvent inEvent=(DevicePutIntoDepotEJBEvent)ev;
		this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");

		try {
			switch (inEvent.getActionType()) {
    		  case DevicePutIntoDepotEJBEvent.DEVICE_PUT_INTO_DEPOT:
    		 	   DevicePutIntoDepot(ev);
                   break;
    		  case DevicePutIntoDepotEJBEvent.VOD_DEVICE_PUT_INTO_DEPOT:
    			   VodDevicePutIntoDepot(inEvent);
    			   break;
    	      default:
    	    	  throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} 
		catch (HomeFactoryException he) {
			throw new CommandException(ErrorCode.HOMEFAC_CANNT_LOOKUP);
		} 
		catch (CreateException ce) {
			throw new CommandException(ErrorCode.ENTITYBEAN_CANNT_CREATE);
		} 
		catch(ServiceException se){
			throw new CommandException(se.getMessage());
		}
		catch (FinderException fe) {
			throw new CommandException(ErrorCode.ENTITYBEAN_HOMEINTERFACE_CANNTFIND);
		}

		return response;
	}
	
	/*
	 * ˫���豸��¼,�Ȳ��뵽vod_stbdevice_import_head,vod_stbdevice_import_detail��,
	 * ����Sp:   ʵ���豸���������,��Sp��ʵ��STB��CM�İ�
	 * */
	private void VodDevicePutIntoDepot(DevicePutIntoDepotEJBEvent event) throws CommandException{
		try{
		  if (!event.isDoPost()){
		     VodstbDeviceImportHeadDTO importHeadDto =event.getVodstbDeviceHead();
		     importHeadDto.setOperatorID(event.getOperatorID());
		     Collection detailCols =event.getVodDevicePutInfoDepotList();
		     if (detailCols.isEmpty()){
				throw new CommandException("xls�ļ��޵�������!");
		     } 
		     int  batchID = BusinessUtility.getSequenceKey("vod_stbdevice_import_SEQNO");
		     importHeadDto.setBatchID(batchID);
		     //����vod�豸ͷ��Ϣ
		     BusinessUtility.importVodDeviceHead(importHeadDto);
		     //����vod�豸��ϸ��Ϣ
		     BusinessUtility.importVodDeviceDetail(batchID, detailCols);
		     response.setPayload(importHeadDto);
			
		  }else{
			   LogUtility.log(clazz, LogLevel.DEBUG, "inEvent.getVodstbDeviceHead().getBatchID()--->"+event.getVodstbDeviceHead().getBatchID());
			   BusinessUtility.callProcedureForVodDevicePut(event.getVodstbDeviceHead().getBatchID());
		  }
		} catch(ServiceException e){
			   throw new CommandException(e);
		}
	}

	/**
	 * �����豸���
	 * 
	 * @param event
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 * @throws ServiceException 
	 */

	private void DevicePutIntoDepot(EJBEvent ev) throws CommandException,
			HomeFactoryException, CreateException, FinderException, ServiceException {
		DevicePutIntoDepotEJBEvent inEvent = (DevicePutIntoDepotEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = inEvent.getRemoteHostAddress();
		if (inEvent.getDevConDTO() != null) {
			devConDTO = inEvent.getDevConDTO();
		}
		List terminalDeviceList = new ArrayList();
		// �ٶ�����Ĳ�����Ϊ��

		String serials = devConDTO.getSpareStr7().trim();
		String[] arrTerminals = serials.split("\n");
		//�������������Щ�ֶ�,Ӧ�úͱ�Ŷ�Ӧ
		String[] arrFields = devConDTO.getSpareStr8().trim().split("\\|");

		int deviceNumber = arrTerminals.length;
		DeviceTransition devTrans;
		TerminalDevice terDev = null;
		;
		String idList = "";

		// ���һ����¼����T_DeviceTransition��һ���豸¼��һ����¼
		devTrans = createDeviceTransition(operatorID, devConDTO, deviceNumber);
		// ��ת
		int batchID = devTrans.getBatchID().intValue();
		for (int i = 0; i < arrTerminals.length; i++) {
			/*
			 * ���һ����¼����T_TerminalDevice��һ�ζ���豸��һ���豸һ����¼
			 * һ���豸�ı��,���ܻ���������Ϣ(���к�|MAC��ַ|�ڲ�MAC��ַ|OK��ַ)
			 */
			//ȡ��һ���豸��ȫ�����
			String[] arrNOs = arrTerminals[i].trim().split("\\|");
			//arrFields�в��������к�

			if(arrFields!=null&&!arrFields[0].equalsIgnoreCase("")&&arrNOs.length!=arrFields.length+1)
				throw new CommandException("����ı����Ŀ��Ҫ������д�Ĳ���Ӧ,��������!");

			terDev = createTerminalDevice(devConDTO, arrFields,arrNOs, batchID);
			LogUtility.log(this.getClass(), LogLevel.DEBUG,"���úõ�DTO:"+terDev.toString());
			
			int deviceID = terDev.getDeviceID().intValue();
			idList += deviceID + "/";
			// ���һ����¼����T_DeviceTransitionDetail,��¼�豸��ת��ϸ��һ�ζ���豸��һ���豸һ����¼
			createDeviceTransitionDetail(devConDTO, arrNOs[0], batchID, deviceID);
					
			terminalDeviceList.add(terDev);
		}
		
		Iterator itDevice=terminalDeviceList.iterator();
		while(itDevice.hasNext()){
			TerminalDevice device=(TerminalDevice) itDevice.next();
			
			//�����豸����¼�(620�¼�) ֻ������豸�е����ܿ��ͻ����м�¼�¼� add by chaoqiu 2007-05-21 begin
			if("SC".equals(device.getDeviceClass()))
				SystemEventRecorder.addEvent4DevicePut(SystemEventRecorder.DEVICE_PUT, 
						device.getDeviceID().intValue(), device.getSerialNo(),0,"",operatorID, "C");
			else if("STB".equals(devConDTO.getSpareStr10()))
				SystemEventRecorder.addEvent4DevicePut(SystemEventRecorder.DEVICE_PUT, 
						0,"",device.getDeviceID().intValue(), device.getSerialNo(),operatorID, "C");
			//�����豸����¼�(620�¼�) add by chaoqiu 2007-05-21 end
		}
		
		if(idList.length()>400)
			idList=idList.substring(0, 400)+"...(�������,���豸��:"+deviceNumber+",�ڴ˼�¼����ID.)";
		try {
			// record system log
			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
					SystemLogRecorder.LOGMODULE_LOGISTICS, "�豸���",
					"�豸���,����豸ID:" + idList, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new CommandException("ϵͳ��־д�����!");
		}

		// ���²������豸����Ĳ��� add by chenjiang
		//modify by jason 2007-3-11,�ϸ��汾��1.9
		TerminalDevice currentTerDev;
		if(CommonConstDefinition.YESNOFLAG_YES.equals(devConDTO.getSpareStr12())){
			System.out.println("******** ����������Ҫ����***********��Ŀ����֯ID��"	+ devConDTO.getSpareStr13());
			if(devConDTO.getSpareStr13()==null || "".equals(devConDTO.getSpareStr13()))
				throw new ServiceException("���������Ŀ����֯����Ϊ�գ�");
			
			int outToOrg=Integer.parseInt(devConDTO.getSpareStr13());
			devConDTO.setFiliale(outToOrg);
			devTrans = createOutDeviceTransition(operatorID, devConDTO,deviceNumber);
			
			int outBatchID = devTrans.getBatchID().intValue();
			for (int i = 0; i < terminalDeviceList.size(); i++) {
				currentTerDev = (TerminalDevice) terminalDeviceList.get(i);
				currentTerDev.setAddressType("T");
				//currentTerDev.setAddressID(orgid);
				currentTerDev.setAddressID(outToOrg);
				currentTerDev.setStatus("W");
				currentTerDev.setDepotID(0);
				currentTerDev.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				int deviceID = currentTerDev.getDeviceID().intValue();
				createOutDeviceTransitionDetail(devConDTO, currentTerDev.getSerialNo(), outBatchID, deviceID, outToOrg);
				//createOutDeviceTransitionDetail(devConDTO, currentTerDev.getSerialNo(), outBatchID, deviceID, outBatchID);

			}
			try {
				SystemLogRecorder.createSystemLog(machineName, operatorID,0, 
						SystemLogRecorder.LOGMODULE_LOGISTICS, "�豸����","�豸����,�����豸ID:" + idList,
						SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
					
			} catch (ServiceException e) {
				throw new CommandException("ϵͳ��־д�����!");
			}
		}
	}

	/**
	 * ���һ����¼����T_DeviceTransition
	 * 
	 * @param operatorID
	 *            ����ԱID
	 * @param devConDTO
	 *            ǰ̨���������
	 * @param deviceNumber
	 *            ��������豸����
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */

	private DeviceTransition createDeviceTransition(int operatorID,
			CommonQueryConditionDTO devConDTO, int deviceNumber)
			throws CommandException, HomeFactoryException, CreateException,
			FinderException {
		DeviceTransition devTrans;
		DeviceTransitionHome dtHome;
		dtHome = HomeLocater.getDeviceTransitionHome();
		DeviceTransitionDTO devTraDTO = new DeviceTransitionDTO();
		devTraDTO.setAction("N"); // "���豸���"
		devTraDTO.setBatchNo(devConDTO.getSpareStr5()); // ����
		devTraDTO.setCreateTime(TimestampUtility.getCurrentDate()); // ������������
		devTraDTO.setDescription(devConDTO.getSpareStr6()); // ��ע
		devTraDTO.setDeviceNumber(deviceNumber); // �豸����
		devTraDTO.setDtCreate(TimestampUtility.getCurrentDate()); // ��¼��������
		devTraDTO.setDtLastmod(TimestampUtility.getCurrentDate()); // ��¼�޸�����
		devTraDTO.setFromType("T"); // "������"
		devTraDTO.setFromID(Integer.parseInt(devConDTO.getSpareStr2())); // ������ID
		devTraDTO.setToType("D"); // "�ֿ�"
		devTraDTO.setToID(Integer.parseInt(devConDTO.getSpareStr4())); // �ֿ�ID
		devTraDTO.setOperatorID(operatorID); // ����ԱID
		devTraDTO.setStatus("V"); // ��Ч
		devTraDTO.setStatusReason(devConDTO.getSpareStr11());//���ԭ��
		devTrans = dtHome.create(devTraDTO);
		return devTrans;
	}

	/**
	 * ���һ����¼����T_DeviceTransition
	 * 
	 * @param operatorID
	 *            ����ԱID
	 * @param devConDTO
	 *            ǰ̨���������
	 * @param deviceNumber
	 *            ���γ����豸����
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */

	private DeviceTransition createOutDeviceTransition(int operatorID,
			CommonQueryConditionDTO devConDTO, int deviceNumber)
			throws CommandException, HomeFactoryException, CreateException,
			FinderException {
		DeviceTransition devTrans;
		DeviceTransitionHome dtHome;
		dtHome = HomeLocater.getDeviceTransitionHome();
		DeviceTransitionDTO devTraDTO = new DeviceTransitionDTO();
		devTraDTO.setAction("O"); // ���豸����
		devTraDTO.setBatchNo(devConDTO.getSpareStr5()); // ����
		devTraDTO.setCreateTime(TimestampUtility.getCurrentDate()); // ������������
		devTraDTO.setDescription(devConDTO.getSpareStr6()); // ��ע
		devTraDTO.setDeviceNumber(deviceNumber); // �豸����
		devTraDTO.setDtCreate(TimestampUtility.getCurrentDate()); // ��¼��������
		devTraDTO.setDtLastmod(TimestampUtility.getCurrentDate()); // ��¼�޸�����
		devTraDTO.setFromType("D"); // �ܹ�˾�ֿ�
		devTraDTO.setFromID(Integer.parseInt(devConDTO.getSpareStr4())); // �ܹ�˾�ֿ�ID
		devTraDTO.setToType("T"); // �ܹ�˾
		devTraDTO.setToID(devConDTO.getFiliale()); // �����ID
		devTraDTO.setOperatorID(operatorID); // ����ԱID
		devTraDTO.setStatus("V"); // ��Ч
		devTrans = dtHome.create(devTraDTO);
		return devTrans;
	}

	/**
	 * ���һ����¼����T_TerminalDevice
	 * 
	 * @param devConDTO
	 *            ǰ̨���������
	 * @param serialNo
	 *            ���к�
	 * @param batchID
	 *            ����ID��T_DeviceTransition.BatchID
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */
	private TerminalDevice createTerminalDevice(
			CommonQueryConditionDTO devConDTO, String[] arrFields,String[] arrNOs, int batchID)
			throws CommandException, HomeFactoryException, CreateException,
			FinderException {
		TerminalDevice terDev = null;
		TerminalDeviceHome terDevHome;
		terDevHome = HomeLocater.getTerminalDeviceHome();

		//���к��ڵ�һ��λ��,ǰ̨��˷�װ,�����ж�,���ȡ
		String serialNO = arrNOs[0];

		Collection terCol = terDevHome.findBySerialNo(serialNO);
		if (!terCol.isEmpty()) {
			throw new CommandException("�豸���["+serialNO+"]�ظ�,�ñ����¼�롣");
		}
		TerminalDeviceDTO terDevDTO = new TerminalDeviceDTO();



		/*
		 * ��������к�/MAC��ַ��˳�������ö���,���̶�,�˴�����...,
		 */
		try {
			for (int j = 0; j < arrFields.length; j++) {
				if(arrFields[j].trim().equalsIgnoreCase(""))continue;
				boolean i=false;
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "��ǰ��װ���ֶ�:"
						+ arrFields[j]);
				Method[] methods = terDevDTO.getClass().getMethods();
				for (int k = 0; k < methods.length; k++) {
					Method curMethod = methods[k];
					if (curMethod.getName().equalsIgnoreCase(
							"set" + arrFields[j])) {
						i=true;
						Object[] para = new String[1];
						para[0] = arrNOs[j+1];

						LogUtility.log(this.getClass(), LogLevel.DEBUG,
								"������:" + curMethod.getName() + "   ���õ�ֵ"
										+ arrNOs[j+1]);

						curMethod.invoke(terDevDTO, para);
					}
				}
				if(!i){
					throw new CommandException("�Ҳ���"+arrFields[j]+"���������,��������.");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtility.log(this.getClass(), LogLevel.DEBUG,"������װ����,��������..");
			e.printStackTrace();
			throw new CommandException("������װ����,��������.");
		}
		
		String purposeStrList = devConDTO.getSpareStr14();
		if(purposeStrList != null && !purposeStrList.trim().equals(""))
		{
			purposeStrList = ","+devConDTO.getSpareStr14()+",";
		}
		
		
		terDevDTO.setDeviceClass(devConDTO.getSpareStr10()); // �豸���
		terDevDTO.setDeviceModel(devConDTO.getSpareStr1());// �豸�ͺ�
		terDevDTO.setSerialNo(serialNO);// ���к�
		terDevDTO.setStatus("S"); // ���
		terDevDTO.setAddressType("D");// "�ֿ�"
		terDevDTO.setAddressID(Integer.parseInt(devConDTO.getSpareStr4()));// �ֿ�ID
		terDevDTO.setBatchID(batchID);// ����ID,T_DeviceTransition.BatchID
		terDevDTO
				.setGuaranteeLength(Integer.parseInt(devConDTO.getSpareStr3()));// ������
		terDevDTO.setDepotID(Integer.parseInt(devConDTO.getSpareStr4())); // �ֿ�ID
		terDevDTO.setPalletID(0);// ����ID
		terDevDTO.setUseFlag("N");// "���豸"
		terDevDTO.setMatchFlag("N"); // ��δ���
		terDevDTO.setPreAuthorization("N");
		terDevDTO.setCaSetFlag("N");
		terDevDTO.setCaSetDate(null);
		terDevDTO.setDtCreate(TimestampUtility.getCurrentDate()); // ��¼��������
		terDevDTO.setDtLastmod(TimestampUtility.getCurrentDate()); // ��¼�޸�����
		terDevDTO.setPurposeStrList(purposeStrList); // ��¼�豸��;

		terDev = terDevHome.create(terDevDTO);

		return terDev;
	}

	/**
	 * ���һ����¼����T_DeviceTransitionDetail
	 * 
	 * @param devConDTO
	 *            ǰ̨���������
	 * @param serialNo
	 *            ���к�
	 * @param batchID
	 *            ����ID��T_DeviceTransition.BatchID
	 * @param deviceID
	 *            �豸ID,T_TerminalDevice.DeviceID
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */
	private void createDeviceTransitionDetail(
			CommonQueryConditionDTO devConDTO, String serialNo, int batchID,
			int deviceID) throws CommandException, HomeFactoryException,
			CreateException, FinderException {
		DeviceTransitionDetail devDet;
		DeviceTransitionDetailHome devTranDetailHome;
		devTranDetailHome = HomeLocater.getDeviceTransitionDetailHome();
		DeviceTransitionDetailDTO devDetDTO = new DeviceTransitionDetailDTO();
		devDetDTO.setBatchID(batchID);// ����ID,T_DeviceTransition.BatchID
		devDetDTO.setDeviceID(deviceID);// �豸ID,T_TerminalDevice.DeviceID
		devDetDTO.setSerialNo(serialNo);// ���к�
		devDetDTO.setStatus("V"); // ��Ч
		devDetDTO.setFromType("T"); // "������"
		devDetDTO.setFromID(Integer.parseInt(devConDTO.getSpareStr2())); // ������ID
		devDetDTO.setToType("D"); // "�ֿ�"
		devDetDTO.setToDeviceStatus("S");
		devDetDTO.setToID(Integer.parseInt(devConDTO.getSpareStr4())); // �ֿ�ID
		devDetDTO.setDtCreate(TimestampUtility.getCurrentDate()); // ��¼��������
		devDetDTO.setDtLastmod(TimestampUtility.getCurrentDate()); // ��¼�޸�����

		devDet = devTranDetailHome.create(devDetDTO);
	}

	/**
	 * ���һ����¼����T_DeviceTransitionDetail
	 * 
	 * @param devConDTO
	 *            ǰ̨���������
	 * @param serialNo
	 *            ���к�
	 * @param batchID
	 *            ����ID��T_DeviceTransition.BatchID
	 * @param deviceID
	 *            �豸ID,T_TerminalDevice.DeviceID
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */
	private void createOutDeviceTransitionDetail(
			CommonQueryConditionDTO devConDTO, String serialNo, int batchID,
			int deviceID, int orgid) throws CommandException,
			HomeFactoryException, CreateException, FinderException {
		DeviceTransitionDetail devDet;
		DeviceTransitionDetailHome devTranDetailHome;
		devTranDetailHome = HomeLocater.getDeviceTransitionDetailHome();
		DeviceTransitionDetailDTO devDetDTO = new DeviceTransitionDetailDTO();
		devDetDTO.setBatchID(batchID);// ����ID,T_DeviceTransition.BatchID
		devDetDTO.setDeviceID(deviceID);// �豸ID,T_TerminalDevice.DeviceID
		devDetDTO.setSerialNo(serialNo);// ���к�
		devDetDTO.setStatus("V"); // ��Ч
		devDetDTO.setFromType("D"); // �ܹ�˾�ֿ�
		devDetDTO.setFromID(Integer.parseInt(devConDTO.getSpareStr4())); // �ܹ�˾�ֿ�
		devDetDTO.setFromDeviceStatus("S");
		devDetDTO.setToType("T"); // �ܹ�˾
		devDetDTO.setToID(orgid); // �ܹ�˾
		devDetDTO.setToDeviceStatus("W");
		devDetDTO.setDtCreate(TimestampUtility.getCurrentDate()); // ��¼��������
		devDetDTO.setDtLastmod(TimestampUtility.getCurrentDate()); //��¼�޸�����

		devDet = devTranDetailHome.create(devDetDTO);
	}

}
