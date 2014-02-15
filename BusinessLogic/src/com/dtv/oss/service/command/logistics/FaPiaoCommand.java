package com.dtv.oss.service.command.logistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.FaPiaoDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.FaPiaoService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.FaPiaoEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.SystemLogRecorder;

public class FaPiaoCommand extends Command {

	private static final Class clazz = FaPiaoCommand.class;

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	private ServiceContext context;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);

		FaPiaoEJBEvent inEvent = (FaPiaoEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();

		try {
			switch (inEvent.getActionType()) {
			//��Ʊ����
			case LogisticsEJBEvent.FAPIAO_IN:
				faPiaoInStock(inEvent);
				break;
			//��Ʊ�ؿ�
			case LogisticsEJBEvent.FAPIAO_BACK:
				faPiaoBack(inEvent);
				break;
			//��Ʊ�ؿ��ѯ
			case LogisticsEJBEvent.FAPIAO_BACK_QUERY:
				faPiaoBackQuery(inEvent);
				break;
			//��Ʊ����
			case LogisticsEJBEvent.FAPIAO_MOVE:
				faPiaoMove(inEvent);
				break;
			//��Ʊ����
			case LogisticsEJBEvent.FAPIAO_USE:
				faPiaoUse(inEvent);
				break;
			//��Ʊ����
			case LogisticsEJBEvent.FAPIAO_ABANDON:
				faPiaoAbandon(inEvent);
				break;
			//��Ʊ����
			case LogisticsEJBEvent.FAPIAO_DISCARD:
				faPiaoDiscard(inEvent);
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
	 * ��Ʊ����
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoInStock(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		this.context = new ServiceContext();
		new FaPiaoService(this.context).faPiaoInStock(event);
		
		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		String title = "��Ʊ����";
		StringBuffer logDes = new StringBuffer("���뵽�ֿ�:"+BusinessUtility.getFapiaoDeportByID(event.getFaPiaoDTO().getAddressID()));
		if(event.isDirUse())
		{
			title = "��Ʊ���벢��������";
			logDes.append(",������֯����:"+BusinessUtility.getOrgNameByID(event.getOutOrgID()));
		}
		logDes.append(".��Ʊ����:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
		if(event.isVolumnManage())
			logDes.append(",��Ʊ�����к�:"+event.getFapiaoVolumnDTO().getVolumnSn());
		logDes = logDes.append(",��Ʊ���к�:[");
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String tempDes = it.next().toString()+",";
			if(logDes.length()<500){
				logDes.append(tempDes);
			}
		}
		logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:"
				+ logDes.toString());

		if (event.isDoPost())
		{
			if(logDes.length()>=500)logDes.append("...");
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
				title, logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}
	/**
	 * ��Ʊ����
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoAbandon(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException, ServiceException , CreateException{
		
		this.context = new ServiceContext();
		new FaPiaoService(this.context).faPiaoAbandon(event);
//����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		String title = "��Ʊ����";
		StringBuffer logDes=new StringBuffer("��Ʊ����:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getCommDTO().getSpareStr2()));
		if(event.isVolumnManage())
			logDes.append(",��Ʊ�����к�:"+event.getCommDTO().getSpareStr3());
		logDes = logDes.append(",��Ʊ���к�:[");
//��־
		if (event.isDoPost())
		{		
				String 	serialsStr=event.getCommDTO().getSpareStr1();
				String[] serialsArray = serialsStr.split("\n");
				ArrayList serialsList = new ArrayList();
				for (int i = 0; i < serialsArray.length; i++) {
					if (serialsArray[i] != null && !"".equals(serialsArray[i].trim()))
						serialsList.add(serialsArray[i].trim());
				}
				Iterator it=serialsList.iterator();
				while(it.hasNext()){
					String trmDes = it.next().toString()+",";
					if(logDes.length()<500){
						logDes.append(trmDes);
					}
				}
				logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:"
						+ logDes.toString());
					if(logDes.length()>500)logDes.append("...");
				SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
						title, logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
	}	
	/**
	 * ��Ʊ����
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoDiscard(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException, ServiceException , CreateException{
		
		this.context = new ServiceContext();
		new FaPiaoService(this.context).faPiaoDiscard(event);
//		 ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);
			this.response.setPayload(lstRet);
		}
//		��־
		if (event.isDoPost())
		{
			String title = "��Ʊ����";
			StringBuffer logDes=new StringBuffer("��Ʊ����:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
			if(event.isVolumnManage())
				logDes.append(",��Ʊ�����к�:"+event.getFapiaoVolumnDTO().getVolumnSn());
			logDes = logDes.append(",��Ʊ���к�:[");
				Iterator it=lstRet.iterator();
				while(it.hasNext()){
					String trmDes =((FaPiaoDTO)it.next()).getSerailNo()+",";
					if(logDes.length()<500){
						logDes.append(trmDes);
					}
				}
				logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:"
						+ logDes.toString());
					if(logDes.length()>500)logDes.append("...");
				SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
						title, logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}	;
	}
	/**
	 * ��Ʊ�ؿ�
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoBack(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		this.context = new ServiceContext();
		//LogUtility.log(this.getClass(), LogLevel.DEBUG, "__3_txtSerailnos="+event.getCommDTO().getSpareStr7());
		new FaPiaoService(this.context).faPiaoBack(event);
		
		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		StringBuffer logDes=new StringBuffer("�ؿ⵽�ֿ�:"+BusinessUtility.getFapiaoDeportByID(event.getFaPiaoDTO().getAddressID())+".��Ʊ����:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
		if(event.isVolumnManage())
			logDes.append(",��Ʊ�����к�:"+event.getFapiaoVolumnDTO().getVolumnSn());
		logDes = logDes.append(",�ؿⷢƱ���к�:[");
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String tempDes = it.next().toString()+",";
			if(logDes.length()<500){
				logDes.append(tempDes);
			}
		}
		logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:"
				+ logDes.toString());

		if (event.isDoPost())
		{
			if(logDes.length()>500)logDes.append("...");
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
					"��Ʊ�ؿ�", logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}
	
	
	
	/**
	 * ��Ʊ�ؿ� ��ѯ
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoBackQuery(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		this.context = new ServiceContext();
		new FaPiaoService(this.context).faPiaoBackQuery(event);
		// ����ֵ
		String reStr = "";
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			reStr = (String) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(reStr);
		}
	}
	/**
	 * ��Ʊ����
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoMove(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		this.context = new ServiceContext();
		new FaPiaoService(this.context).faPiaoMove(event);

		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		
		StringBuffer logDes=new StringBuffer("�������ֿ�:"+BusinessUtility.getFapiaoDeportByID(event.getFaPiaoDTO().getAddressID())+".��Ʊ����:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
		if(event.isVolumnManage())
			logDes.append(";��Ʊ�����к�:"+event.getFapiaoVolumnDTO().getVolumnSn());
		logDes = logDes.append(";������Ʊ���к�:[");
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String tempDes = it.next().toString()+",";
			if(logDes.length()<500){
				logDes.append(tempDes);
			}
		}
		logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:"
				+ logDes.toString());
		if (event.isDoPost())
		{
			if(logDes.length()>500)logDes.append("...");
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
					"��Ʊ����", logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}
	/**
	 * ��Ʊ����
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoUse(FaPiaoEJBEvent event)
			throws HomeFactoryException, FinderException, ServiceException {
		this.context = new ServiceContext();
		//LogUtility.log(this.getClass(), LogLevel.DEBUG, "__3_txtSerailnos="+event.getCommDTO().getSpareStr7());
		new FaPiaoService(this.context).faPiaoUse(event);
		
		// ����ֵ
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		StringBuffer logDes=new StringBuffer("������֯����:"+BusinessUtility.getOrgNameByID(event.getFaPiaoDTO().getAddressID())+".��Ʊ����:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
		if(event.isVolumnManage())
			logDes.append(";��Ʊ�����к�:"+event.getFapiaoVolumnDTO().getVolumnSn());
		logDes = logDes.append(";���÷�Ʊ���к�:[");
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String tempDes = it.next().toString()+",";
			if(logDes.length()<500){
				logDes.append(tempDes);
			}
		}
		logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ؽ��:"
				+ logDes.toString());

		if (event.isDoPost())
		{
			if(logDes.length()>500)logDes.append("...");
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
					"��Ʊ����", logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}
}
