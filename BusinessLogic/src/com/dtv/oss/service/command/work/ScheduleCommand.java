package com.dtv.oss.service.command.work;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.dtv.oss.domain.BatchJob;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.component.ScheduleService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.ScheduleEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ScheduleCommand extends Command {

	private static final Class clazz = ScheduleCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	private ServiceContext serviceContext;
	CommandResponseImp response = null;
	
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		ScheduleEJBEvent inEvent = (ScheduleEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"�����csidtoΪ�� " );
	    
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.SCHEDULE_CREATE:
	    			createSchedule(inEvent);
	    			break;
	    		case EJBEvent.SCHEDULE_MODIFY:
	    			modifySchedule(inEvent);
	    			break;
	    		case EJBEvent.SCHEDULE_CANCEL:
	    			cancelSchedule(inEvent);
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("δ֪����");
		}
		return response;
	}

	private void createSchedule(ScheduleEJBEvent inEvent) throws ServiceException{
		checkSechedule(inEvent);
		
		if (!inEvent.isDoPost()) return ;

		Collection psIDList = inEvent.getCpIDList();
		ScheduleService ss=new ScheduleService(serviceContext);
		ss.create(psIDList,inEvent.getBatchJobDTO());
		
		String psIDs ="";
		Iterator itPSID =psIDList.iterator();
		while (itPSID.hasNext()){
			psIDs =psIDs +((Integer)itPSID.next()).intValue()+",";
		}
		
		BatchJob   batchJob =(BatchJob)serviceContext.get(Service.BATCH_JOB);
		//add by chenjiang 
		String eventType=batchJob.getJobType();
	    String typeName=getTypeName(eventType);
		 
        // ����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext),((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue() , 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�����ų�", "�����ų�,�ų�ID��"+batchJob.getBatchId().intValue()
				+",�ͻ���Ʒ:"+BusinessUtility.getProductDescListByPSIDList(psIDs)
				+",ִ���¼���"+typeName
				+",ִ��ʱ��:"+inEvent.getBatchJobDTO().getScheduleTime(),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);		
	}
	
	/**
	 * @param eventType
	 * @return
	 */
	private String getTypeName(String eventType) {
		String typeName="";
		if("PS".equalsIgnoreCase(eventType))
			typeName ="��ͣ";
		if("PR".equalsIgnoreCase(eventType))
			typeName ="�ָ�";
		if("PC".equalsIgnoreCase(eventType))
			typeName ="ȡ��";
		return typeName;
	}

	private void modifySchedule(ScheduleEJBEvent inEvent)throws ServiceException{
		initServiceContext(inEvent);
	    
		ScheduleService ss=new ScheduleService(serviceContext);
		ss.modify(inEvent.getSheduleIDList(),inEvent.getBatchJobDTO(),inEvent.getCpIDList());
			
		BatchJob   batchJob =(BatchJob)serviceContext.get(Service.BATCH_JOB);
		ArrayList cpList=(ArrayList)serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		int customerID=0;
		String psIDs ="";
		Iterator itPSID =cpList.iterator();
		while (itPSID.hasNext()){
			CustomerProduct cp =(CustomerProduct) itPSID.next();
			customerID=cp.getCustomerID();
			psIDs =psIDs +cp.getPsID()+",";
		}
		String eventType=batchJob.getJobType();
	    String typeName=getTypeName(eventType);
        // ����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext),customerID , 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "�޸��ų�", "�޸��ų�,�ų�ID��"+batchJob.getBatchId().intValue()
				+",�ͻ���Ʒ:"+BusinessUtility.getProductDescListByPSIDList(psIDs)
				+",ִ���¼���"+typeName
				+",ִ��ʱ��:"+inEvent.getBatchJobDTO().getScheduleTime(),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);		
	}
	
	private void cancelSchedule(ScheduleEJBEvent inEvent)throws ServiceException{
		initServiceContext(inEvent);
		//�����ų�,������ȡ������
		ScheduleService ss=new ScheduleService(serviceContext);
		ss.cancel(inEvent.getSheduleIDList());
		
		ArrayList cpList=(ArrayList)serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		int customerID=0;
		String psIDs ="";
		Iterator itPSID =cpList.iterator();
		while (itPSID.hasNext()){
			CustomerProduct cp =(CustomerProduct) itPSID.next();
			customerID=cp.getCustomerID();
			psIDs =psIDs +cp.getPsID()+",";
		}
		BatchJob   batchJob =(BatchJob)serviceContext.get(Service.BATCH_JOB);
		String eventType=batchJob.getJobType();
	    String typeName=getTypeName(eventType);
        // ����ϵͳ��־
    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext),customerID , 
				SystemLogRecorder.LOGMODULE_CUSTSERV, "ȡ���ų�", "ȡ���ų�,�ų�ID��"+batchJob.getBatchId().intValue()
				+",�ͻ���Ʒ:"+BusinessUtility.getProductDescListByPSIDList(psIDs)
				+",ִ���¼���"+typeName
				+",ִ��ʱ��:"+batchJob.getScheduleTime(),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);	
	}
	
	
	private void initServiceContext(ScheduleEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
		switch (event.getActionType()) {
			case EJBEvent.SCHEDULE_CREATE:
				description="�����ų�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.SCHEDULE_MODIFY:
				description="�޸��ų�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.SCHEDULE_CANCEL:
				description="ȡ���ų�";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
		}
		serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    this.serviceContext =serviceContext;
	}
	
	private int getAction(String JobType) {
		int checkAction =0;
		if (JobType.equals(CommonConstDefinition.BATCH_JOB_TYPE_PS)){
			checkAction =1;
		} else if (JobType.equals(CommonConstDefinition.BATCH_JOB_TYPE_PC)){
			checkAction =5;
		} 
		return checkAction;
	}
	
	private void checkSechedule(ScheduleEJBEvent inEvent) throws ServiceException{
		initServiceContext(inEvent);
		Collection psIDList =inEvent.getCpIDList();
		if (psIDList ==null || psIDList.isEmpty()) 
			throw new ServiceException("�ͻ���Ʒ����Ϊ��!");
		BatchJobDTO batchJobDto =inEvent.getBatchJobDTO();
		if (batchJobDto !=null){
			if (batchJobDto.getJobType() ==null || batchJobDto.getJobType().equals("")){
				throw new ServiceException("�ͻ���Ʒִ���¼�����Ϊ��!");
			}
		}else {
			throw new ServiceException("û���ų����ݵ�DTO!");
		}	
		Iterator itPSID=psIDList.iterator();
		
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkCampainByCpId(((Integer) itPSID.next()).intValue(),
				getAction(batchJobDto.getJobType()), inEvent.getBatchJobDTO()
						.getScheduleTime());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
