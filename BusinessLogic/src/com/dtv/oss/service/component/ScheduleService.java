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
		LogUtility.log(clazz,LogLevel.DEBUG,"创建排程");

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
			
			// 1）创建排程记录
			BatchJobHome batchJobHome=HomeLocater.getBatchJobHome();
			batchJobDTO.setCreateOpId(opID);
			batchJobDTO.setCreateTime(TimestampUtility.getCurrentDate());
			//原来是被注释掉的,为什么?,侯于2007-10-22打开了注释.为了区分受理单(客户树)而来的排程和批处理的排程
			batchJobDTO.setReferType(CommonConstDefinition.BATCH_JOB_REFER_TYPE_C);
			batchJobDTO.setScheduleType(CommonConstDefinition.SCHEDULE_TYPE_S);
			batchJobDTO.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_WAIT);
			batchJobDTO.setTotoalRecordNo(psIDList.size());
			BatchJob bj=batchJobHome.create(batchJobDTO);
			
			context.put(Service.BATCH_JOB,bj);
			Iterator itPSID=psIDList.iterator();
			while(itPSID.hasNext()){
				//2）创建排程明细
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
				
				//3）创建排程日志
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
			LogUtility.log(clazz,LogLevel.ERROR, "排程定位出错！");                    
            throw new ServiceException("排程定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "排程查找出错！");                    
            throw new ServiceException("排程查找出错！");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "排程创建出错！");                    
            throw new ServiceException("排程创建出错！");
		}
	}
	
	public void modify(Collection scheduleIDList,BatchJobDTO batchJobDTO,Collection psIDList)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"修改排程");
		
		if(scheduleIDList==null || scheduleIDList.size()==0){
			LogUtility.log(clazz,LogLevel.DEBUG,"排程列表为空");
			throw new ServiceException("排程列表为空!");
		}
		
		try{
			BatchJobHome batchJobHome=null;
			BatchJob batchJob=null;
			
			BatchJobProcessLogHome bjplHome=null;
			BatchJobProcessLogDTO bjplDTO=null;		
			Iterator itBatchJobID=scheduleIDList.iterator();
			
			while(itBatchJobID.hasNext()){
				//修改批处理时间
				Integer batchID=(Integer)itBatchJobID.next();
				batchJobHome=HomeLocater.getBatchJobHome();
				batchJob=batchJobHome.findByPrimaryKey(batchID);
				
				context.put(Service.BATCH_JOB,batchJob);
				
				batchJob.setScheduleTime(batchJobDTO.getScheduleTime());
				batchJob.setReasonCode(batchJobDTO.getReasonCode());
				batchJob.setDescription(batchJobDTO.getDescription());
				
				//只有状态为等待执行才能修改
				if(!CommonConstDefinition.BATCH_JOB_STATUS_WAIT.equals(batchJob.getStatus()))
					throw new ServiceException("该排程状态不正常，不能修改！");
								
				//创建排程日志
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
			LogUtility.log(clazz,LogLevel.ERROR, "修改排程定位出错！");                    
            throw new ServiceException("修改排程定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "修改排程查找出错！");                    
            throw new ServiceException("修改排程查找出错！");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "创建排程日志出错！");                    
            throw new ServiceException("创建排程日志出错！");
		}
	}
	
	public void cancel(Collection scheduleIDList)throws ServiceException {
		LogUtility.log(clazz,LogLevel.DEBUG,"取消排程");
		
		if(scheduleIDList==null || scheduleIDList.size()==0){
			LogUtility.log(clazz,LogLevel.DEBUG,"排程列表为空");
			throw new ServiceException("排程列表为空!");
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
				//取消：修改批处
				Integer batchID=(Integer)itBatchJobID.next();
				batchJobHome=HomeLocater.getBatchJobHome();
				batchJob=batchJobHome.findByPrimaryKey(batchID);
				context.put(Service.BATCH_JOB,batchJob);
				
				//只有状态为等待执行才能取消
				if(!CommonConstDefinition.BATCH_JOB_STATUS_WAIT.equals(batchJob.getStatus()))
					throw new ServiceException("该排程状态不正常，不能取消！");
				
				batchJob.setStatus(CommonConstDefinition.BATCH_JOB_STATUS_CANCEL);
								
				//创建排程日志
				bjplHome=HomeLocater.getBatchJobProcessLogHome();
				bjplDTO=new BatchJobProcessLogDTO();
				bjplDTO.setAction(CommonConstDefinition.BATCHJOBPROCESSACTION_CANCEL);
				bjplDTO.setComments("用户取消排程");
				bjplDTO.setCreateTime(TimestampUtility.getCurrentDate());
				bjplDTO.setItemNO(0);
				bjplDTO.setBatchId(batchJob.getBatchId().intValue());
				bjplDTO.setOperatorId(PublicService.getCurrentOperatorID(context));
				bjplDTO.setOrgId(BusinessUtility.getOpOrgIDByOperatorID(batchJob.getCreateOpId()));
			//	bjplDTO.setResult(CommonConstDefinition.PROCESSSTATUS_SUCESS);
				bjplHome.create(bjplDTO);
				
				//取消批处理的子项
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
			LogUtility.log(clazz,LogLevel.ERROR, "取消排程定位出错！");                    
            throw new ServiceException("取消排程定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "取消排程查找出错！");                    
            throw new ServiceException("取消排程查找出错！");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "取消排程日志出错！");                    
            throw new ServiceException("取消排程日志出错！");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
