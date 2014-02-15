/*
 * Created on 2006-4-24
 * 
 * @author chen jiang
 * 
 * ��ά������Ϣcommand
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

			case EJBEvent.CONFIG_BIDIM_CREATE: // ��ά������Ϣ����
				createBidim(inEvent.getBidimDto(), inEvent
						.getNewAppendedBidimValuesList());
				break;
			case EJBEvent.CONFIG_BIDIM_EDIT:
				// updateBidim(inEvent.getBidimDto()); //��ά������Ϣ����
				break;
			case EJBEvent.CONFIG_BIDIM_UPDATE:
				updateBidim(inEvent.getBidimDto(), inEvent
						.getNewAppendedBidimValuesList(), inEvent
						.getToBeDeletedBidimValuesList()); // ��ά������Ϣ����
				break;
			case EJBEvent.CONFIG_BIDIM_DELETE:
				deleteBidim(inEvent.getBidimDto()); // ��ά������Ϣɾ��
				break;
			case EJBEvent.CONFIG_VALUE_CREATE: // ��ά������Ϣѡ��ֵ����
				createValueBidim(inEvent.getBidimValueDto());
				break;
			case EJBEvent.CONFIG_VALUE_EDIT:
				updateValueBidim(inEvent.getBidimValueDto()); // ��ά������Ϣѡ��ֵ����
				break;
			case EJBEvent.CONFIG_VALUE_DELETE:
				deleteValueBidim(inEvent.getBidimValueDto()); // ��ά������Ϣѡ��ֵɾ��
				break;

			default:
				throw new IllegalArgumentException(
						"ConfigBidimEJBEvent��actionType�����ò���ȷ��");
			}
		} catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}


	/**
	 * �޸Ķ�ά������Ϣ
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
				SystemLogRecorder.keyLog("���¶�ά������Ϣʧ��", "��ά������Ϣ", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"BidimConfigSettingDTO update fail, dto:"
								+ dto
								+ ". Please make sure that dt_lastmod of BidimConfigSettingDTO is set.");
			}
			//ɾ���ı������͵�ֵ
			
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
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ķ�ά������Ϣ",
					"�޸Ķ�ά������Ϣ��ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҷ�ά������Ϣʱ�����ID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҷ�ά������Ϣ�����ID��" + dto.getId());
		} catch (RemoveException re) {
			LogUtility.log(clazz, LogLevel.ERROR, re);
			throw new ServiceException("ɾ����ά������Ϣֵ�����ID��" + dto.getId());
		} catch (CreateException ce) {

			LogUtility.log(clazz, LogLevel.ERROR, ce);
			throw new ServiceException("������ά������Ϣֵ�����ID��" + dto.getId());

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
		
		//������"Y",��ɾ��
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
			throw new ServiceException("������������Ч��¼�������ó�Ĭ��ֵ��");
			 
		if(existValue.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)==-1)
			checkValueDto(newAppendedValuesList);
		
		if(existValue.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)!=-1 && defaltValueDel.indexOf(CommonConstDefinition.BIDIM_DEFALT_VALUE_YES)!=-1)
			checkValueDto(newAppendedValuesList);
			 
		
	}

	/**
	 * ������ά������Ϣ
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
					"������ά������Ϣ", "������ά������Ϣ��ID��" + bidimConfig.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ��ά������Ϣ����");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��ά������Ϣ����");
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
			throw new ServiceException("������������Ч��¼�������ó�Ĭ��ֵ��");
		}
	}


	/**
	 * ɾ����ά������Ϣ
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
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ����ά������Ϣ",
					"ɾ����ά������Ϣ��ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ��ά������Ϣ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҷ�ά������Ϣ�����ID��" + dto.getId());
		}
	}

	/**
	 * ɾ����ά������Ϣ
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
					"BidimConfigSettingValueDTO��******** " + dto);
			BidimConfigSettingValueHome valueHome = HomeLocater
					.getBidimConfigSettingValueHome();

			BidimConfigSettingValue bidimValue = valueHome
					.findByPrimaryKey(new Integer(dto.getId()));
			bidimValue.remove();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), dto.getId(),
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ����ά����ֵ��Ϣ",
					"ɾ����ά����ֵ��Ϣ��ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ��ά������Ϣֵ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҷ�ά������Ϣֵ����ֵID��" + dto.getId());
		}
	}

	/**
	 * ������ά����ֵ��Ϣ
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
					"BidimConfigSettingValueDTO��******** " + dto);
			BidimConfigSettingValueHome valueHome = HomeLocater
					.getBidimConfigSettingValueHome();

			BidimConfigSettingValue bidimConfigValue = valueHome.create(dto);

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), bidimConfigValue.getId()
					.intValue(), SystemLogRecorder.LOGMODULE_CONFIG,
					"������ά����ֵ��Ϣ", "������ά����ֵ��Ϣ��ID��" + bidimConfigValue.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ��ά������Ϣֵ����");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��ά������Ϣֵ����");
		}
	}

	/**
	 * �޸Ķ�ά����ֵ��Ϣ
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
				SystemLogRecorder.keyLog("���¶�ά������Ϣʧ��", "��ά������Ϣ", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"BidimConfigSettingValueDTO update fail, ID:"
								+ dto.getId()
								+ ". Please make sure that dt_lastmod of BidimConfigSettingValueDTO is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), dto.getId(),
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ķ�ά����ֵ��Ϣ",
					"�޸Ķ�ά����ֵ��Ϣ��ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҷ�ά������Ϣֵʱ�����ID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҷ�ά������Ϣֵ�����ID��" + dto.getId());
		}
	}

	/**
	 * ��ʼ��ServiceContext ��һЩͨ�õ���Ϣ����ServiceContext
	 */
	private ServiceContext initServiceContext() {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));

		return serviceContext;
	}

	public static void main(String[] args) {
	}
}
