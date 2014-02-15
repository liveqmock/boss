/*
 * Created on 2005-9-22
 *
 * @author whq
 * 
 * ��ʾ��������ĳ�����
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
     * ���ù������������߱��޵���
     * @param referSheetId
     */
    public void setsetReferSheetId(int  referSheetId) throws ServiceException{
    	load();
    	jc.setReferSheetId(referSheetId);     
    }
    /**
     * ����customerID��jobcard��
     * @param custId
     */
    public void setReferCustomerId(int custId) throws ServiceException{
    	load();
    	jc.setCustId(custId);     
    }
       
    /**
     * ����serviceAcctID��jobcard��
     * @param serviceAcctID
     */
    public void setServiceAcctID(int serviceAcctID) throws ServiceException{
    	load();
    	jc.setCustServiceAccountId(serviceAcctID);     
    }
    public abstract void setJobCardType(JobCardDTO jcDto) throws ServiceException;
    //�������˵��
    public abstract void relateWithOriginalSheet(Object cps) throws ServiceException ;
    /*
     * ���ά�޹��������ı��޵�����CustomerProblemService
     * ��װ����Ӧ��CSI����(csiid��Ӧ��Integer)
     */
    public abstract Object getOriginalSheet() throws ServiceException;
    
    //ʵ����ɴ��������ķ���
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
			throw new ServiceException("��������������ִ���");
		} catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("��������������ִ���");
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
            throw new ServiceException("ֻ��״̬Ϊ������Ĺ����ſ���ȡ����");       
    }
    
    public void canClose() throws ServiceException {
        load();
        if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_FAIL) == false)
            throw new ServiceException("ֻ��״̬Ϊ�����ɹ��Ĺ����ſ��Խ�����");
    }
    
    public void canRegisterInfo() throws ServiceException {
        load();
        if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_BOOKED) == false)
            throw new ServiceException("ֻ��״̬Ϊ��ԤԼ�Ĺ����ſ���¼��ά����Ϣ��");
    }
    /**
     * ֻ��״̬Ϊ�޷���������Ĺ������ܽ����˷�(�׷��)
     * @throws ServiceException
     */
    public void canReturnFeeForInstallFail() throws ServiceException {
        load();
        if (!(CommonConstDefinition.JOBCARD_STATUS_TERMINAL.equals(jc.getStatus())
        		||CommonConstDefinition.JOBCARD_STATUS_CANCEL.equals(jc.getStatus())
        		||CommonConstDefinition.JOBCARD_STATUS_FAIL.equals(jc.getStatus())
        		||CommonConstDefinition.JOBCARD_STATUS_C_INSTALL.equals(jc.getStatus())
        		||CommonConstDefinition.JOBCARD_STATUS_CANCEL_ACCEPT.equals(jc.getStatus())))
            throw new ServiceException("ֻ���޷���������Ĺ����ſ����˷ѡ�");
    }
    public void canContactUser() throws ServiceException {
        load();
        if ((jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_WAIT) == false) &&
            (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_FAIL) == false))
            throw  new ServiceException("ֻ��״̬�Ǵ�������ߴ����ɹ��Ĺ�������ԤԼ��");
    }
    // ����ԤԼ�����ж�
    public void canReContactUser() throws ServiceException {
        load();
        if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_BOOKED) == false) 
           
            throw  new ServiceException("ֻ��״̬����ԤԼ�Ĺ���������ԤԼ��");
    }
    
    
    public int getCustomerID() throws ServiceException {
        load();
        return jc.getCustId();
    }
    
    //¼��ά��/��װ��Ϣ
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
     * ���������Ϣ��¼����ϸʵ�ּ�������
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
                throw new ServiceException("���������Ϣ��¼ʱ����");
            } catch (CreateException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("���������Ϣ��¼ʱ����");
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

        //�绰��ϵ�Ѻ�,��绰֧������
        if(CommonConstDefinition.JOBCARD_CONTACT_RESULT_RESOLVED_BY_PHONE.equals(workResult)||
           CommonConstDefinition.JOBCARD_CONTACT_RESULT_RESOLVED_BYSLEFPHONE.equals(workResult)){
           jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_SUCCESS);
           LogUtility.log(JobCardService.class,LogLevel.DEBUG,"�������޵��������"+jc.getReferSheetId());
           cp= getCustomerProblem(jc.getReferSheetId());
           if(cp!=null){
              LogUtility.log(JobCardService.class,LogLevel.DEBUG,"��������Զ�̽ӿڡ���"+cp);
              cpDTO=BusinessUtility.getCustProblemProcessDTOByID(jc.getReferSheetId());
              //ȡ�ñ��޵���ǰ��״̬
              String cpOldStatus= cp.getStatus();
              cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS);
              FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_P,cp.getId().intValue());
              createCustProblemProcess(cpDTO, cpOldStatus);
           }
           //�޷���ϵ�û�
        } else if (CommonConstDefinition.JOBCARD_CONTACT_RESULT_CANT_CONTACT_USER.equals(workResult)){
            jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_WAIT);
        } else if (CommonConstDefinition.JOBCARD_CONTACT_RESULT_REFUSE_PAYMENT.equals(workResult)){
            jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_CANCEL);
            LogUtility.log(JobCardService.class,LogLevel.DEBUG,"�������޵��������"+jc.getReferSheetId());
            cp= getCustomerProblem(jc.getReferSheetId());
            if (cp!=null){
               LogUtility.log(JobCardService.class,LogLevel.DEBUG,"��������Զ�̽ӿڡ���"+cp);
               cpDTO=BusinessUtility.getCustProblemProcessDTOByID(jc.getReferSheetId());
               //ȡ�ñ��޵���ǰ��״̬
               String cpOldStatus= cp.getStatus();
               cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS);
               FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_P,cp.getId().intValue());
               createCustProblemProcess(cpDTO, cpOldStatus);
            }
        }else
            jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_BOOKED);

        //jcDto.setPreferedDate(jcpDto.getWorkDate());
        if (jc.ejbUpdate(jcDto) == -1)
            throw new RuntimeException("���¹���ʧ�ܣ�����ID��"+jobCardNo+". Please make sure that dt_lastmod of JobCardDTO is set.");
        // ���ö���ΪԤԼ
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
     * oldStatus: ���޵��޸�ǰ״̬
     */
    private void createCustProblemProcess(CustProblemProcessDTO custProblemProcessDto, String oldStatus) throws ServiceException {
        createCustProblemProcessWithNextOrgId(custProblemProcessDto, oldStatus, 0);
    }
    /*
     * createCustomerProblemProcess record
     * @param
     * custProblemProcessDto: if null, new a empty CustProblemProcessDTO
     * oldStatus: ���޵��޸�ǰ״̬
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
        
        //����t_CustomerProblem��ProcessOrgID�ֶ�
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
            throw new ServiceException("�������޵������¼ʱ����");
        } catch (CreateException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("�������޵������¼ʱ����");
        }
    }
    public void cancel(JobCardProcessDTO jcpDto) throws ServiceException {
        load();
        canCancel();
        updateStatus(jcpDto, CommonConstDefinition.JOBCARD_STATUS_CANCEL);
        
    }
    
    /*
     * �޸Ĺ���
     * �ù�����һ��δ�����޸Ĺ������Ƿ���Ҫ��¼�����������ʷ��¼��
     * �����Ҫ����ôaction�������
     */
    public static void updateJobCard(JobCardDTO jobCardDTO) throws ServiceException {
        if ((jobCardDTO == null) || (jobCardDTO.getJobCardId() == 0)) throw new IllegalArgumentException("jobCardDTO is not initialized.");
        int jobCardNo = jobCardDTO.getJobCardId();
        
		try {
		    JobCardHome home = HomeLocater.getJobCardHome();
            JobCard jc = home.findByPrimaryKey(new Integer(jobCardNo));
            if (jc.getStatus().equals(CommonConstDefinition.JOBCARD_STATUS_WAIT) == false)
                throw new ServiceException("ֻ���½��Ĺ��������޸�");
            if (jc.ejbUpdate(jobCardDTO) == -1)
                throw new RuntimeException("JobCard update fail, jobCardID:"+jobCardNo+". Please make sure that dt_lastmod of JobCardDTO is set.");
//          ��¼�����������ʷ��¼

		} catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("���ҹ���ʱ��������ID��"+jobCardNo);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("���ҹ���ʱ��������ID��"+jobCardNo);
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
            throw new ServiceException("�������������¼ʱ����");
        } catch (CreateException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("�������������¼ʱ����");
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
            throw new ServiceException("����������¼ʱ����");
        } catch (CreateException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("����������¼ʱ����");
        }
    }
    
    /*
     * 1)load JobCard by jobCardNo
     * ������ʵ�������ӶԹ������͵��ж�
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
            throw new ServiceException("���ҹ���ʱ��������ID��"+jobCardNo);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("���ҹ���ʱ��������ID��"+jobCardNo);
        }
	} 
	
    private void setOperatorID() {
        if (context.get(Service.OPERATIOR_ID) != null)
            operatorID=((Integer)context.get(Service.OPERATIOR_ID)).intValue();
    }
    
    /*
     * �޸Ĺ���״̬������JobCardProcess��¼
     */
    private void updateStatus(JobCardProcessDTO jcpDto, String newStatus) throws ServiceException {
        String oldStatus = jc.getStatus();
        jc.setStatus(newStatus);
        jc.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        
        //create JobCardProcess
        createJobCardProcess(jcpDto, oldStatus);
    }

    /*
     * �������������¼
     * @param
     * jcpDto: ���web��û�д��룬��Ϊnull
     * oldStatus: ����������±���������Ϊ""
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
				//������ַ
					LogUtility.log(clazz,LogLevel.DEBUG,"������ַ");
					AddressHome addressHome=HomeLocater.getAddressHome();
					Address address = addressHome.create(addressDto);
					addressid = Integer.parseInt(address.getAddressID()+"");
				}
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("�޷��ҵ�address ��home �ӿ�");
		}catch(CreateException e) {
			LogUtility.log(clazz,LogLevel.ERROR, e);
			throw new ServiceException("�޷�����ַ����");
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
			
			LogUtility.log(clazz,LogLevel.DEBUG,"����ԤԼȡ���˷�ϵͳ�¼�");
			
			context.put(Service.CUSTOMER_ID,new Integer(jcDto.getCustId()));			
			
			Collection accountItemList = BusinessUtility.getFeeListByTypeAndID(jcDto.getJobCardId(),CommonConstDefinition.FTREFERTYPE_J,true);;
			Collection paymentRecordList = BusinessUtility.getPaymentListByTypeAndID(jcDto.getJobCardId(),CommonConstDefinition.FTREFERTYPE_J,true);
			Collection prePaymentDeductionRecordList = BusinessUtility.getPreDeductionRecordListByTypeAndID(jcDto.getJobCardId(),CommonConstDefinition.FTREFERTYPE_J,true);
			
			FeeService.createFeeAndPaymentAndPreDuciton(accountItemList, paymentRecordList, prePaymentDeductionRecordList,null);
		}catch(ServiceException e){
			LogUtility.log(clazz,LogLevel.WARN,"����ȡ���˷Ѷ�λ����:"+e);
			throw new ServiceException("����ȡ���˷Ѷ�λ����");
		}		
	}
}

/*
 * ����Ա����
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
            throw new ServiceException("��λ����Աʱ��������ԱID��"+operatorID);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("��λ����Աʱ��������ԱID��"+operatorID);
        }
	}
	
	public int getOrgID() throws ServiceException {
	    load();
	    return operator.getOrgID();
	}
		
}
