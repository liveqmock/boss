/*
 * Created on 2005-9-22
 *
 * @author whq
 * 
 * 表示工单对象的抽象类
 */
package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.CustProblemProcessHome;
import com.dtv.oss.domain.CustomerProblem;
import com.dtv.oss.domain.CustomerProblemHome;
import com.dtv.oss.domain.JobCard;
import com.dtv.oss.domain.JobCardHome;
import com.dtv.oss.domain.JobCardProcessHome;
import com.dtv.oss.domain.MaterialUsageHome;
import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustProblemProcessDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.dto.MaterialUsageDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.domain.*;
import com.dtv.oss.domain.JobCard;
import com.dtv.oss.domain.JobCardHome;
import com.dtv.oss.service.util.HomeLocater;

public abstract class JobCardService {
    private static final Class clazz = JobCardService.class;
    protected ServiceContext context;
    protected int jobCardNo;
    protected JobCard jc;
    protected int operatorID;
    protected CustomerProblem cp;
    
    public JobCardService(ServiceContext context) {
        this.context = context;
        setOperatorID();
    }
    
    public JobCardService(int jobCardNo, ServiceContext context) {
        this.jobCardNo = jobCardNo;
        this.context = context;
        setOperatorID();
    }
    /**
     * 设置关联的受理单或者报修单号
     * @param referSheetId
     */
    public void setsetReferSheetId(int  referSheetId) throws ServiceException{
    	load();
    	jc.setReferSheetId(referSheetId);     
    }
    /**
     * 设置customerID到jobcard中
     * @param custId
     */
    public void setReferCustomerId(int custId) throws ServiceException{
    	load();
    	jc.setCustId(custId);     
    }
       
    /**
     * 设置serviceAcctID到jobcard中
     * @param serviceAcctID
     */
    public void setServiceAcctID(int serviceAcctID) throws ServiceException{
    	load();
    	jc.setCustServiceAccountId(serviceAcctID);     
    }
    public abstract void setJobCardType(JobCardDTO jcDto) throws ServiceException;
    //详见子类说明
    public abstract void relateWithOriginalSheet(Object cps) throws ServiceException ;
    /*
     * 获得维修工单关联的报修单对象CustomerProblemService
     * 或安装单对应的CSI对象(csiid对应的Integer)
     */
    public abstract Object getOriginalSheet() throws ServiceException;
    
    //实际完成创建工单的方法
    public void createJobCard(JobCardDTO jcDto) throws ServiceException {
        createJobCard(jcDto, 0);
    }    
    public void createJobCard(JobCardDTO jcDto,int nextOrgID) throws ServiceException {
    	setJobCardType(jcDto);  
    	try {
    		OperatorHome opeHome = HomeLocater.getOperatorHome();
    		Operator operator = opeHome.findByPrimaryKey(new Integer(operatorID));
    		jcDto.setCreateOpID(operatorID);
    		jcDto.setCreateOrgID(operator.getOrgID());
    		
    	}catch(javax.ejb.FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("创建工单对象出现错误");
		} catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("创建工单对象出现错误");
		}
		save(jcDto);
		//load();
    	jc.setProcessOrgId(nextOrgID);
		if(nextOrgID == 0)
			createJobCardProcess(null,"");
		else
			createJobCardProcess(null, "", nextOrgID);
    }
    public void canCancel() throws ServiceException {
        load();
        if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_WAIT) == false && (CommonConstDefinition.JOBCARD_STATUS_BOOKED).equals(jc.getStatus()) == false)
            throw new ServiceException("只有状态为待处理的工单才可以取消。");       
    }
    
    public void canClose() throws ServiceException {
        load();
        if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_FAIL) == false)
            throw new ServiceException("只有状态为处理不成功的工单才可以结束。");
    }
    
    public void canRegisterInfo() throws ServiceException {
        load();
        if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_BOOKED) == false)
            throw new ServiceException("只有状态为已预约的工单才可以录入维修信息。");
    }
    /**
     * 只有状态为无法继续处理的工单才能进行退费(杨晨追加)
     * @throws ServiceException
     */
    public void canReturnFeeForInstallFail() throws ServiceException {
        load();
        if (!(CommonConstDefinition.JOBCARD_STATUS_TERMINAL.equals(jc.getStatus())
        		||CommonConstDefinition.JOBCARD_STATUS_CANCEL.equals(jc.getStatus())
        		||CommonConstDefinition.JOBCARD_STATUS_FAIL.equals(jc.getStatus())
        		||CommonConstDefinition.JOBCARD_STATUS_C_INSTALL.equals(jc.getStatus())
        		||CommonConstDefinition.JOBCARD_STATUS_CANCEL_ACCEPT.equals(jc.getStatus())))
            throw new ServiceException("只有无法继续处理的工单才可以退费。");
    }
    public void canContactUser() throws ServiceException {
        load();
        if ((jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_WAIT) == false) &&
            (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_FAIL) == false))
            throw  new ServiceException("只有状态是待处理或者处理不成功的工单才能预约。");
    }
    // 重新预约条件判断
    public void canReContactUser() throws ServiceException {
        load();
        if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_BOOKED) == false) 
           
            throw  new ServiceException("只有状态是已预约的工单才能重预约。");
    }
    
    
    public int getCustomerID() throws ServiceException {
        load();
        return jc.getCustId();
    }
    
    //录入维修/安装信息
    public void registerInfo(boolean isSuccess,
            				 JobCardDTO jcDto,
            				 JobCardProcessDTO jcpDto) throws ServiceException {
        load();
        
        String oldStatus = jc.getStatus();
        int workCount = jc.getWorkCount();
        jcDto.setWorkCount(jc.getWorkCount()+1);
        if(workCount==0){
        	jcDto.setFirstWorkDate(jcDto.getWorkDate());
        }
        if(jcpDto.getOutOfDateReason()!=null)
        jcDto.setOutOfDateReason(jcpDto.getOutOfDateReason());
    //    if(jc.getFirstWorkDate() == null) jcDto.setFirstWorkDate(TimestampUtility.getCurrentDate());
        if(isSuccess) {
        	jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_SUCCESS);
        	jcDto.setWorkResult(CommonConstDefinition.JOBCARD_WORKRESULT_SUCCESS);
        }
        else 
        	{
        	jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_FAIL);
        	jcDto.setWorkResult(CommonConstDefinition.JOBCARD_WORKRESULT_FAIL);
        	}
        if (jc.ejbUpdate(jcDto) == -1)
            throw new RuntimeException("JobCard update fail, jobCardID:"+jobCardNo+". Please make sure that dt_lastmod of JobCardDTO is set.");
         
        jcpDto.setWorkResult((isSuccess)?CommonConstDefinition.JOBCARD_WORKRESULT_SUCCESS:CommonConstDefinition.JOBCARD_WORKRESULT_FAIL);
        createJobCardProcess(jcpDto, oldStatus);
    }
    
    /*
     * 创建诊断信息记录，详细实现见其子类
     */
    public void recordDiagnosisInfo(java.util.Collection diagnosisInfoDtos) throws ServiceException {      
    }

    public void recordMaterialUsage(java.util.Collection MaterialUsageDtos) throws ServiceException {      
   	    load();
        Iterator it = MaterialUsageDtos.iterator();
        while (it.hasNext()) {
            MaterialUsageDTO  materialUsageDto = (MaterialUsageDTO)it.next();
            try {
           	 MaterialUsageHome home = HomeLocater.getMaterialUsageHome();
                home.create(materialUsageDto);
            } catch (HomeFactoryException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("创建诊断信息记录时出错。");
            } catch (CreateException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("创建诊断信息记录时出错。");
            }
            
        }        
   }
    
    public void close(JobCardProcessDTO jcpDto) throws ServiceException {
        load();
        canClose();
        updateStatus(jcpDto, CommonConstDefinition.JOBCARD_STATUS_TERMINAL);
    }

    public void contactUser(JobCardDTO jcDto, JobCardProcessDTO jcpDto)throws ServiceException {
        load();
        if("B".equalsIgnoreCase(jcDto.getStatus())) 
            canReContactUser();	
        else
            canContactUser();
        String oldStatus = jc.getStatus();
        CustProblemProcessDTO cpDTO=null;
        String workResult = jcpDto.getWorkResult();

        //电话联系已好,与电话支持排障
        if(CommonConstDefinition.JOBCARD_CONTACT_RESULT_RESOLVED_BY_PHONE.equals(workResult)||
           CommonConstDefinition.JOBCARD_CONTACT_RESULT_RESOLVED_BYSLEFPHONE.equals(workResult)){
           jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_SUCCESS);
           LogUtility.log(JobCardService.class,LogLevel.DEBUG,"■■报修单号码■■"+jc.getReferSheetId());
           cp= getCustomerProblem(jc.getReferSheetId());
           if(cp!=null){
              LogUtility.log(JobCardService.class,LogLevel.DEBUG,"■■报修远程接口■■"+cp);
              cpDTO=BusinessUtility.getCustProblemProcessDTOByID(jc.getReferSheetId());
              //取得报修单以前的状态
              String cpOldStatus= cp.getStatus();
              cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS);
              FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_P,cp.getId().intValue());
              createCustProblemProcess(cpDTO, cpOldStatus);
           }
           //无法联系用户
        } else if (CommonConstDefinition.JOBCARD_CONTACT_RESULT_CANT_CONTACT_USER.equals(workResult)){
            jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_WAIT);
        } else if (CommonConstDefinition.JOBCARD_CONTACT_RESULT_REFUSE_PAYMENT.equals(workResult)){
            jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_CANCEL);
            LogUtility.log(JobCardService.class,LogLevel.DEBUG,"■■报修单号码■■"+jc.getReferSheetId());
            cp= getCustomerProblem(jc.getReferSheetId());
            if (cp!=null){
               LogUtility.log(JobCardService.class,LogLevel.DEBUG,"■■报修远程接口■■"+cp);
               cpDTO=BusinessUtility.getCustProblemProcessDTOByID(jc.getReferSheetId());
               //取得报修单以前的状态
               String cpOldStatus= cp.getStatus();
               cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS);
               FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_P,cp.getId().intValue());
               createCustProblemProcess(cpDTO, cpOldStatus);
            }
        }else
            jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_BOOKED);

        //jcDto.setPreferedDate(jcpDto.getWorkDate());
        if (jc.ejbUpdate(jcDto) == -1)
            throw new RuntimeException("更新工单失败，工单ID："+jobCardNo+". Please make sure that dt_lastmod of JobCardDTO is set.");
        // 设置动作为预约
        jcpDto.setAction("B");
        createJobCardProcess(jcpDto, oldStatus);
    }

   
    public CustomerProblem getCustomerProblem(int id){
    	try {
    		CustomerProblemHome home = (CustomerProblemHome) HomeLocater.getCustomerProblemHome();
    		CustomerProblem cp = home.findByPrimaryKey(new Integer(id));
    		 return cp;
           
		} catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
          
        }
		return null;
         
    }
    /*
     * createCustomerProblemProcess record(NextOrgId == CurrentOrgId)
     * 
     * @param 
     * custProblemProcessDto: if null, new a empty CustProblemProcessDTO
     * oldStatus: 报修单修改前状态
     */
    private void createCustProblemProcess(CustProblemProcessDTO custProblemProcessDto, String oldStatus) throws ServiceException {
        createCustProblemProcessWithNextOrgId(custProblemProcessDto, oldStatus, 0);
    }
    /*
     * createCustomerProblemProcess record
     * @param
     * custProblemProcessDto: if null, new a empty CustProblemProcessDTO
     * oldStatus: 报修单修改前状态
     * nextOrgId: (nextOrgId==0)?setNextOrgId(currentOrgId):setNextOrgId(nextOrgId)
     */
    private void createCustProblemProcessWithNextOrgId(CustProblemProcessDTO custProblemProcessDto, 
            												 String oldStatus, 
            												 int nextOrgId) 
    	throws ServiceException {
        if (custProblemProcessDto == null)
            custProblemProcessDto = new CustProblemProcessDTO();
        int orgId = new OperatorService(context, operatorID).getOrgID(); //get orgid from operatorid
        custProblemProcessDto.setCurrentorgId(orgId);
        custProblemProcessDto.setCurrentOperatorId(operatorID);
        custProblemProcessDto.setAction(Action.getAction4Repair(oldStatus, cp.getStatus()));
        
        custProblemProcessDto.setNextOrgId((nextOrgId == 0)?orgId:nextOrgId);
       
        custProblemProcessDto.setCreateDate(TimestampUtility.getCurrentDate());
                
        save(custProblemProcessDto);
        
        //更新t_CustomerProblem的ProcessOrgID字段
        cp.setProcessOrgId(custProblemProcessDto.getNextOrgId());
        cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
    }
    /*
     * call entity bean to save CustProblemProcessDTO
     */
    private void save(CustProblemProcessDTO cppDto) throws ServiceException {
        try {
            CustProblemProcessHome cppHome = HomeLocater.getCustProblemProcessHome();
            cppHome.create(cppDto);
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建报修单处理记录时出错。");
        } catch (CreateException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("创建报修单处理记录时出错。");
        }
    }
    public void cancel(JobCardProcessDTO jcpDto) throws ServiceException {
        load();
        canCancel();
        updateStatus(jcpDto, CommonConstDefinition.JOBCARD_STATUS_CANCEL);
        
    }
    
    /*
     * 修改工单
     * 该功能有一处未定，修改工单是是否需要记录工单处理的历史记录，
     * 如果需要，那么action如何设置
     */
    public static void updateJobCard(JobCardDTO jobCardDTO) throws ServiceException {
        if ((jobCardDTO == null) || (jobCardDTO.getJobCardId() == 0)) throw new IllegalArgumentException("jobCardDTO is not initialized.");
        int jobCardNo = jobCardDTO.getJobCardId();
        
		try {
		    JobCardHome home = HomeLocater.getJobCardHome();
            JobCard jc = home.findByPrimaryKey(new Integer(jobCardNo));
            if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_WAIT) == false)
                throw new ServiceException("只有新建的工单才能修改");
            if (jc.ejbUpdate(jobCardDTO) == -1)
                throw new RuntimeException("JobCard update fail, jobCardID:"+jobCardNo+". Please make sure that dt_lastmod of JobCardDTO is set.");
//          记录工单处理的历史记录

		} catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找工单时出错，工单ID："+jobCardNo);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找工单时出错，工单ID："+jobCardNo);
        }
    }
    
    public int getJobCardNo() {
        return jobCardNo;
    }
    public void setJobCardNo(int jobCardNo) {
        this.jobCardNo = jobCardNo;
    }
    
    protected void save(JobCardProcessDTO jcpDto) throws ServiceException {
        try {
            JobCardProcessHome home = HomeLocater.getJobCardProcessHome();
            home.create(jcpDto);
        } catch (HomeFactoryException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("创建工单处理记录时出错。");
        } catch (CreateException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("创建工单处理记录时出错。");
        }
    }
    
    protected void save(JobCardDTO jcDto) throws ServiceException {
        jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_WAIT);
        //jcDto.setProcessOrgId(BusinessUtility.FindOrgIDByOperatorID(operatorID));
        try {
            JobCardHome home = HomeLocater.getJobCardHome();
      
            jc = home.create(jcDto);
            jobCardNo = jc.getJobCardId().intValue();
            jcDto.setJobCardId(jc.getJobCardId().intValue());
        } catch (HomeFactoryException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("创建工单记录时出错。");
        } catch (CreateException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("创建工单记录时出错。");
        }
    }
    
    /*
     * 1)load JobCard by jobCardNo
     * 在子类实现中增加对工单类型的判断
     */    
	protected void load() throws ServiceException {
	    if (jc != null) return;
	    if (jobCardNo == 0) throw new IllegalArgumentException("jobCardNo is not initialized.");
		try {
		    JobCardHome home = HomeLocater.getJobCardHome();
            jc = home.findByPrimaryKey(new Integer(jobCardNo));
            jobCardNo = jc.getJobCardId().intValue();
        
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找工单时出错，工单ID："+jobCardNo);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("查找工单时出错，工单ID："+jobCardNo);
        }
	} 
	
    private void setOperatorID() {
        if (context.get(Service.OPERATIOR_ID) != null)
            operatorID=((Integer)context.get(Service.OPERATIOR_ID)).intValue();
    }
    
    /*
     * 修改工单状态，创建JobCardProcess记录
     */
    private void updateStatus(JobCardProcessDTO jcpDto, String newStatus) throws ServiceException {
        String oldStatus = jc.getStatus();
        jc.setStatus(newStatus);
        jc.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        
        //create JobCardProcess
        createJobCardProcess(jcpDto, oldStatus);
    }

    /*
     * 创建工单处理记录
     * @param
     * jcpDto: 如果web端没有传入，则为null
     * oldStatus: 如果工单是新被创建，则为""
     */
    private void createJobCardProcess(JobCardProcessDTO jcpDto, String oldStatus) throws ServiceException {
        createJobCardProcess(jcpDto, oldStatus, 0);
    }
    private void createJobCardProcess(JobCardProcessDTO jcpDto, String oldStatus, int nextOrgID) throws ServiceException {
        if (jcpDto == null)
            jcpDto = new JobCardProcessDTO();
        jcpDto.setJcId(jobCardNo);
		jcpDto.setCurrentOperatorId(operatorID);
		jcpDto.setCurrentOrgId(new OperatorService(context, operatorID).getOrgID()); 
		jcpDto.setAction(Action.getAction4JobCard(oldStatus, jc.getStatus()));
		jcpDto.setActionTime(TimestampUtility.getCurrentTimestamp());
		jcpDto.setNextOrgId(nextOrgID);
		save(jcpDto);
    }
	public int createCatvJobCard(JobCardDTO jcDto,int nextOrgID)throws ServiceException{		
		createJobCard(jcDto,nextOrgID);
		return jobCardNo;
	}
	public int createAddressForCatvConstruction(AddressDTO addressDto)throws ServiceException{
		int addressid =0;
		try{
				if(addressDto!=null){
				//创建地址
					LogUtility.log(clazz,LogLevel.DEBUG,"创建地址");
					AddressHome addressHome=HomeLocater.getAddressHome();
					Address address = addressHome.create(addressDto);
					addressid = Integer.parseInt(address.getAddressID()+"");
				}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("无法找到address 的home 接口");
		}catch(CreateException e) {
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("无法创地址对象");
		}
		return addressid;
	}

	/**
	 * @param jcDto
	 */
	public void returnFeeForCancel(JobCardDTO jcDto) throws ServiceException {
		try{
			ServiceContext context = new ServiceContext();
			context.put(Service.OPERATIOR_ID,new Integer(operatorID));
			
			LogUtility.log(clazz,LogLevel.DEBUG,"工单预约取消退费系统事件");
			
			context.put(Service.CUSTOMER_ID,new Integer(jcDto.getCustId()));			
			
			Collection accountItemList = BusinessUtility.getFeeListByTypeAndID(jcDto.getJobCardId(),CommonConstDefinition.FTREFERTYPE_J,true);;
			Collection paymentRecordList = BusinessUtility.getPaymentListByTypeAndID(jcDto.getJobCardId(),CommonConstDefinition.FTREFERTYPE_J,true);
			Collection prePaymentDeductionRecordList = BusinessUtility.getPreDeductionRecordListByTypeAndID(jcDto.getJobCardId(),CommonConstDefinition.FTREFERTYPE_J,true);
			
			FeeService.createFeeAndPaymentAndPreDuciton(accountItemList, paymentRecordList, prePaymentDeductionRecordList,null);
		}catch(ServiceException e){
			LogUtility.log(clazz,LogLevel.WARN,"工单取消退费定位出错:"+e);
			throw new ServiceException("工单取消退费定位出错！");
		}		
	}
}

/*
 * 操作员对象
 */
class OperatorService {
    private static final Class clazz = OperatorService.class;
    private ServiceContext context;
    private int operatorID;    
    private Operator operator;    
    
    public OperatorService(ServiceContext context, int operatorID) {
        super();
        this.context = context;
        this.operatorID = operatorID;
    }
    
	protected void load() throws ServiceException {
	    if (operator != null) return;
	    if (operatorID == 0) throw new IllegalArgumentException("operatorID is not initialized.");
		try {
		    OperatorHome home = HomeLocater.getOperatorHome();
            operator = home.findByPrimaryKey(new Integer(operatorID));
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("定位操作员时出错，操作员ID："+operatorID);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("定位操作员时出错，操作员ID："+operatorID);
        }
	}
	
	public int getOrgID() throws ServiceException {
	    load();
	    return operator.getOrgID();
	}
		
}
