package com.dtv.oss.service.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.BatchJob;
import com.dtv.oss.domain.BatchJobHome;
import com.dtv.oss.domain.BatchJobItem;
import com.dtv.oss.domain.BatchJobItemHome;
import com.dtv.oss.domain.BatchJobProcessLogHome;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustServiceInteractionHome;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.BatchJobItemDTO;
import com.dtv.oss.dto.BatchJobProcessLogDTO;
import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.TimestampUtility;

public class ScheduleService extends AbstractService {

	private ServiceContext context=null;
	private static final Class clazz = ScheduleService.class;
	
	public ScheduleService(ServiceContext inContext){
		this.context=inContext;
	}
	
	
	public void create(Collection psIDList,BatchJobDTO batchJobDTO)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ų�");

		int opID=0;
		if(this.context.get(Service.OPERATIOR_ID)!=null){
			opID=((Integer)context.get(Service.OPERATIOR_ID)).intValue();
		}
		
		try{
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=null;
			
			BatchJobItemHome batchJobItemHome=HomeLocater.getBatchJobItemHome();
			BatchJobItem batchJobItem=null;
			BatchJobItemDTO batchJobItemDTO;
			CustomerCampaignDTO customerCampaignDTO=null;
			
			// 1�������ų̼�¼
			BatchJobHome batchJobHome=HomeLocater.getBatchJobHome();
			batchJobDTO.setCreateOpId(opID);
			batchJobDTO.setCreateTime(TimestampUtility.getCurrentDate());
			//ԭ���Ǳ�ע�͵���,Ϊʲô?,����2007-10-22����ע��.Ϊ����������(�ͻ���)�������ų̺���������ų�
			batchJobDTO.setReferType(CommonConstDefinition.BATCH_JOB_REFER_TYPE_C);
			batchJobDTO.setScheduleType(CommonConstDefinition.SCHEDULE_TYPE_S);
			batchJobDTO.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_WAIT);
			batchJobDTO.setTotoalRecordNo(psIDList.size());
			BatchJob bj=batchJobHome.create(batchJobDTO);
			
			context.put(Service.BATCH_JOB,bj);
			Iterator itPSID=psIDList.iterator();
			while(itPSID.hasNext()){
				//2�������ų���ϸ
				cp=cpHome.findByPrimaryKey((Integer)itPSID.next());
				
				batchJobItemDTO=new BatchJobItemDTO();
				batchJobItemDTO.setAccountId(cp.getAccountID());
				batchJobItemDTO.setBatchId(bj.getBatchId().intValue());
				
				customerCampaignDTO=BusinessUtility.getCustomerCampaignByPsID(cp.getPsID().intValue());
				if(customerCampaignDTO!=null)
					batchJobItemDTO.setCcId(customerCampaignDTO.getCampaignID());
				
				batchJobItemDTO.setCustomerId(cp.getCustomerID());
				batchJobItemDTO.setCustPackageId(cp.getReferPackageID());
				batchJobItemDTO.setPsId(cp.getPsID().intValue());
				batchJobItemDTO.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_WAIT);
				batchJobItem=batchJobItemHome.create(batchJobItemDTO);
				
				//3�������ų���־
				BatchJobProcessLogHome bjplHome=HomeLocater.getBatchJobProcessLogHome();
				BatchJobProcessLogDTO bjplDTO=new BatchJobProcessLogDTO();
				bjplDTO.setAction(CommonConstDefinition.BATCHJOBPROCESSACTION_CREATE);
				bjplDTO.setComments(batchJobDTO.getDescription());
				bjplDTO.setCreateTime(TimestampUtility.getCurrentDate());
				bjplDTO.setItemNO(batchJobItem.getItemNO().intValue());
				bjplDTO.setBatchId(bj.getBatchId().intValue());
				bjplDTO.setOperatorId(opID);
				bjplDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(opID));
				//bjplDTO.setResult(CommonConstDefinition.PROCESSSTATUS_SUCESS);
				bjplHome.create(bjplDTO);
				
				if (context.get(Service.CUSTOMER_ID) ==null){
					context.put(Service.CUSTOMER_ID, new Integer(cp.getCustomerID()));
				}		
			}			
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "�ų̶�λ����");                    
            throw new ServiceException("�ų̶�λ����");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "�ų̲��ҳ���");                    
            throw new ServiceException("�ų̲��ҳ���");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "�ų̴�������");                    
            throw new ServiceException("�ų̴�������");
		}
	}
	
	public void modify(Collection scheduleIDList,BatchJobDTO batchJobDTO,Collection psIDList)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"�޸��ų�");
		
		if(scheduleIDList==null || scheduleIDList.size()==0){
			LogUtility.log(clazz,LogLevel.DEBUG,"�ų��б�Ϊ��");
			throw new ServiceException("�ų��б�Ϊ��!");
		}
		
		try{
			BatchJobHome batchJobHome=null;
			BatchJob batchJob=null;
			
			BatchJobProcessLogHome bjplHome=null;
			BatchJobProcessLogDTO bjplDTO=null;		
			Iterator itBatchJobID=scheduleIDList.iterator();
			
			while(itBatchJobID.hasNext()){
				//�޸�������ʱ��
				Integer batchID=(Integer)itBatchJobID.next();
				batchJobHome=HomeLocater.getBatchJobHome();
				batchJob=batchJobHome.findByPrimaryKey(batchID);
				
				context.put(Service.BATCH_JOB,batchJob);
				
				batchJob.setScheduleTime(batchJobDTO.getScheduleTime());
				batchJob.setReasonCode(batchJobDTO.getReasonCode());
				batchJob.setDescription(batchJobDTO.getDescription());
				
				//ֻ��״̬Ϊ�ȴ�ִ�в����޸�
				if(!CommonConstDefinition.BATCH_JOB_STATUS_WAIT.equals(batchJob.getStatus()))
					throw new ServiceException("���ų�״̬�������������޸ģ�");
								
				//�����ų���־
				bjplHome=HomeLocater.getBatchJobProcessLogHome();
				bjplDTO=new BatchJobProcessLogDTO();
				bjplDTO.setAction(CommonConstDefinition.BATCHJOBPROCESSACTION_MODIFY);
				bjplDTO.setComments(batchJob.getDescription());
				bjplDTO.setCreateTime(TimestampUtility.getCurrentDate());
				bjplDTO.setItemNO(0);
				bjplDTO.setBatchId(batchJob.getBatchId().intValue());
				bjplDTO.setOperatorId(PublicService.getCurrentOperatorID(context));
				bjplDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(batchJob.getCreateOpId()));
			//	bjplDTO.setResult(CommonConstDefinition.PROCESSSTATUS_SUCESS);
				bjplHome.create(bjplDTO);
			}
			
			ArrayList productList=new ArrayList();
			Collection itemIDList=BusinessUtility.getScheduleItemIDsByScheduleID(batchJob.getBatchId().intValue());
			Iterator itItemID=itemIDList.iterator();
			while (itItemID.hasNext()){
				BatchJobItemHome batchJobItemHome=HomeLocater.getBatchJobItemHome();
				BatchJobItem batchJobItem=batchJobItemHome.findByPrimaryKey((Integer)itItemID.next());
				
				CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
				CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(batchJobItem.getPsId()));
				
				productList.add(cp);
					
			}
			context.put(Service.CUSTOMER_PRODUCT_LIST, productList);
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "�޸��ų̶�λ����");                    
            throw new ServiceException("�޸��ų̶�λ����");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "�޸��ų̲��ҳ���");                    
            throw new ServiceException("�޸��ų̲��ҳ���");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "�����ų���־����");                    
            throw new ServiceException("�����ų���־����");
		}
	}
	
	public void cancel(Collection scheduleIDList)throws ServiceException {
		LogUtility.log(clazz,LogLevel.DEBUG,"ȡ���ų�");
		
		if(scheduleIDList==null || scheduleIDList.size()==0){
			LogUtility.log(clazz,LogLevel.DEBUG,"�ų��б�Ϊ��");
			throw new ServiceException("�ų��б�Ϊ��!");
		}
		
		try{
			BatchJobHome batchJobHome=null;
			BatchJob batchJob=null;
			
			BatchJobItemHome batchJobItemHome=null;
			BatchJobItem batchJobItem=null;
    		
			BatchJobProcessLogHome bjplHome=null;
			BatchJobProcessLogDTO bjplDTO=null;

			Iterator itBatchJobID=scheduleIDList.iterator();
			
			while(itBatchJobID.hasNext()){
				//ȡ�����޸�����
				Integer batchID=(Integer)itBatchJobID.next();
				batchJobHome=HomeLocater.getBatchJobHome();
				batchJob=batchJobHome.findByPrimaryKey(batchID);
				context.put(Service.BATCH_JOB,batchJob);
				
				//ֻ��״̬Ϊ�ȴ�ִ�в���ȡ��
				if(!CommonConstDefinition.BATCH_JOB_STATUS_WAIT.equals(batchJob.getStatus()))
					throw new ServiceException("���ų�״̬������������ȡ����");
				
				batchJob.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_CANCEL);
								
				//�����ų���־
				bjplHome=HomeLocater.getBatchJobProcessLogHome();
				bjplDTO=new BatchJobProcessLogDTO();
				bjplDTO.setAction(CommonConstDefinition.BATCHJOBPROCESSACTION_CANCEL);
				bjplDTO.setComments("�û�ȡ���ų�");
				bjplDTO.setCreateTime(TimestampUtility.getCurrentDate());
				bjplDTO.setItemNO(0);
				bjplDTO.setBatchId(batchJob.getBatchId().intValue());
				bjplDTO.setOperatorId(PublicService.getCurrentOperatorID(context));
				bjplDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(batchJob.getCreateOpId()));
			//	bjplDTO.setResult(CommonConstDefinition.PROCESSSTATUS_SUCESS);
				bjplHome.create(bjplDTO);
				
				//ȡ�������������
				ArrayList productList=new ArrayList();
				Collection itemIDList=BusinessUtility.getScheduleItemIDsByScheduleID(batchID.intValue());
				Iterator itItemID=itemIDList.iterator();
				while (itItemID.hasNext()) {
					batchJobItemHome = HomeLocater.getBatchJobItemHome();
					batchJobItem = batchJobItemHome
							.findByPrimaryKey((Integer) itItemID.next());
					batchJobItem
							.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_CANCEL);

					CustomerProductHome cpHome = HomeLocater
							.getCustomerProductHome();
					CustomerProduct cp = cpHome.findByPrimaryKey(new Integer(
							batchJobItem.getPsId()));

					productList.add(cp);
				}
				context.put(Service.CUSTOMER_PRODUCT_LIST, productList);
			}
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "ȡ���ų̶�λ����");                    
            throw new ServiceException("ȡ���ų̶�λ����");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ȡ���ų̲��ҳ���");                    
            throw new ServiceException("ȡ���ų̲��ҳ���");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "ȡ���ų���־����");                    
            throw new ServiceException("ȡ���ų���־����");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
