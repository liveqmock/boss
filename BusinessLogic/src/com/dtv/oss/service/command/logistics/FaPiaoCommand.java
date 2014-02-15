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
			//发票运入
			case LogisticsEJBEvent.FAPIAO_IN:
				faPiaoInStock(inEvent);
				break;
			//发票回库
			case LogisticsEJBEvent.FAPIAO_BACK:
				faPiaoBack(inEvent);
				break;
			//发票回库查询
			case LogisticsEJBEvent.FAPIAO_BACK_QUERY:
				faPiaoBackQuery(inEvent);
				break;
			//发票调拨
			case LogisticsEJBEvent.FAPIAO_MOVE:
				faPiaoMove(inEvent);
				break;
			//发票领用
			case LogisticsEJBEvent.FAPIAO_USE:
				faPiaoUse(inEvent);
				break;
			//发票作废
			case LogisticsEJBEvent.FAPIAO_ABANDON:
				faPiaoAbandon(inEvent);
				break;
			//发票报废
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
			throw new CommandException("未知错误。");
		}
		return response;
	}
	
	/**
	 * 发票运入
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
		
		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		String title = "发票运入";
		StringBuffer logDes = new StringBuffer("运入到仓库:"+BusinessUtility.getFapiaoDeportByID(event.getFaPiaoDTO().getAddressID()));
		if(event.isDirUse())
		{
			title = "发票运入并立即领用";
			logDes.append(",领用组织机构:"+BusinessUtility.getOrgNameByID(event.getOutOrgID()));
		}
		logDes.append(".发票类型:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
		if(event.isVolumnManage())
			logDes.append(",发票册序列号:"+event.getFapiaoVolumnDTO().getVolumnSn());
		logDes = logDes.append(",发票序列号:[");
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String tempDes = it.next().toString()+",";
			if(logDes.length()<500){
				logDes.append(tempDes);
			}
		}
		logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:"
				+ logDes.toString());

		if (event.isDoPost())
		{
			if(logDes.length()>=500)logDes.append("...");
		SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
				title, logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}
	/**
	 * 发票作废
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoAbandon(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException, ServiceException , CreateException{
		
		this.context = new ServiceContext();
		new FaPiaoService(this.context).faPiaoAbandon(event);
//返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		String title = "发票作废";
		StringBuffer logDes=new StringBuffer("发票类型:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getCommDTO().getSpareStr2()));
		if(event.isVolumnManage())
			logDes.append(",发票册序列号:"+event.getCommDTO().getSpareStr3());
		logDes = logDes.append(",发票序列号:[");
//日志
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
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:"
						+ logDes.toString());
					if(logDes.length()>500)logDes.append("...");
				SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
						title, logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
	}	
	/**
	 * 发票报废
	 * 
	 * @param event
	 * @throws ServiceException
	 * @throws FinderException
	 * @throws HomeFactoryException
	 */
	private void faPiaoDiscard(FaPiaoEJBEvent event) throws HomeFactoryException, FinderException, ServiceException , CreateException{
		
		this.context = new ServiceContext();
		new FaPiaoService(this.context).faPiaoDiscard(event);
//		 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);
			this.response.setPayload(lstRet);
		}
//		日志
		if (event.isDoPost())
		{
			String title = "发票报废";
			StringBuffer logDes=new StringBuffer("发票类型:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
			if(event.isVolumnManage())
				logDes.append(",发票册序列号:"+event.getFapiaoVolumnDTO().getVolumnSn());
			logDes = logDes.append(",发票序列号:[");
				Iterator it=lstRet.iterator();
				while(it.hasNext()){
					String trmDes =((FaPiaoDTO)it.next()).getSerailNo()+",";
					if(logDes.length()<500){
						logDes.append(trmDes);
					}
				}
				logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:"
						+ logDes.toString());
					if(logDes.length()>500)logDes.append("...");
				SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
						title, logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}	;
	}
	/**
	 * 发票回库
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
		
		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		StringBuffer logDes=new StringBuffer("回库到仓库:"+BusinessUtility.getFapiaoDeportByID(event.getFaPiaoDTO().getAddressID())+".发票类型:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
		if(event.isVolumnManage())
			logDes.append(",发票册序列号:"+event.getFapiaoVolumnDTO().getVolumnSn());
		logDes = logDes.append(",回库发票序列号:[");
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String tempDes = it.next().toString()+",";
			if(logDes.length()<500){
				logDes.append(tempDes);
			}
		}
		logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:"
				+ logDes.toString());

		if (event.isDoPost())
		{
			if(logDes.length()>500)logDes.append("...");
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
					"发票回库", logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}
	
	
	
	/**
	 * 发票回库 查询
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
		// 返回值
		String reStr = "";
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			reStr = (String) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(reStr);
		}
	}
	/**
	 * 发票调拨
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

		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		
		StringBuffer logDes=new StringBuffer("调拨到仓库:"+BusinessUtility.getFapiaoDeportByID(event.getFaPiaoDTO().getAddressID())+".发票类型:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
		if(event.isVolumnManage())
			logDes.append(";发票册序列号:"+event.getFapiaoVolumnDTO().getVolumnSn());
		logDes = logDes.append(";调拨发票序列号:[");
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String tempDes = it.next().toString()+",";
			if(logDes.length()<500){
				logDes.append(tempDes);
			}
		}
		logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:"
				+ logDes.toString());
		if (event.isDoPost())
		{
			if(logDes.length()>500)logDes.append("...");
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
					"发票调拨", logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}
	/**
	 * 发票领用
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
		
		// 返回值
		Collection lstRet = null;
		if (this.context.get(com.dtv.oss.service.Service.PROCESS_RESULT) != null) {
			lstRet = (Collection) this.context
					.get(com.dtv.oss.service.Service.PROCESS_RESULT);

			this.response.setPayload(lstRet);
		}
		StringBuffer logDes=new StringBuffer("领用组织机构:"+BusinessUtility.getOrgNameByID(event.getFaPiaoDTO().getAddressID())+".发票类型:"+BusinessUtility.getCommonNameByKey("SET_FP_FPTYPE",event.getFaPiaoDTO().getType()));
		if(event.isVolumnManage())
			logDes.append(";发票册序列号:"+event.getFapiaoVolumnDTO().getVolumnSn());
		logDes = logDes.append(";领用发票序列号:[");
		Iterator it=lstRet.iterator();
		while(it.hasNext())
		{
			String tempDes = it.next().toString()+",";
			if(logDes.length()<500){
				logDes.append(tempDes);
			}
		}
		logDes = new StringBuffer(logDes.substring(0,logDes.length()-1)+"]");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "返回结果:"
				+ logDes.toString());

		if (event.isDoPost())
		{
			if(logDes.length()>500)logDes.append("...");
			SystemLogRecorder.createSystemLog(event.getRemoteHostAddress(), operatorID, 0,SystemLogRecorder.LOGMODULE_LOGISTICS, 
					"发票领用", logDes.toString(),SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		}
		
	}
}
