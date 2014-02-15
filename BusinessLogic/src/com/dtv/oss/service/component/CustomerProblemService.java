/*
 * Created on 2005-9-21
 *
 * @author whq
 * 
 * ���޵����󣬷�װ�Ա��޵�ʵ��Bean�Ĳ���
 */
package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.domain.*;
import com.dtv.oss.service.*;
import com.dtv.oss.service.component.OperatorService;
import com.dtv.oss.service.util.*;
import com.dtv.oss.service.ejbevent.work.RepairEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.dto.*;
import com.dtv.oss.service.Service;

public class CustomerProblemService {
    private static final Class clazz = CustomerProblemService.class;
    private int cpNo;
    private CustomerProblem cp;
    private ServiceContext context;
    private int operatorID;
    
    public CustomerProblemService(ServiceContext context) {
        this(0, context);
    }
    
    public CustomerProblemService(int cpNo, ServiceContext context) {
        this.cpNo = cpNo;
        this.context = context;
        setOperatorID();
    }
    
    public boolean canManualTransfer() throws ServiceException {
        load();
        return (cp.getStatus().equals(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT) || 
                cp.getStatus().equals(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_FAIL));
    }
    
    public boolean canCallCustomerForRepair() throws ServiceException {
        load();
        return ((cp.getStatus().equals(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS) || 
                cp.getStatus().equals(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_TERMINAL))&&
				!CommonConstDefinition.CCPCALLBACKFLAG_YES.equals(cp.getCallBackFlag()));
    }
    
    public boolean canManualClose() throws ServiceException {
        load();
        return cp.getStatus().equals(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT);
    }

    public boolean canTerminate() throws ServiceException {
        load();
        return cp.getStatus().equals(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_FAIL)||cp.getStatus().equals(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT);
    }
    
    /*
     * �÷�����������޸ı��޵�״̬�Ĺ��ܣ������������޵������¼��
     * �õ��÷�����������
     * ¼��ά����Ϣ, ����ά�޹���, ȡ��ά�޹���
     */
    public void setStatus(String newStatus) throws ServiceException {
        load();
        String oldStatus = cp.getStatus();
        cp.setStatus(newStatus);
        cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        
        //createCustomerProblemProcess record
        createCustProblemProcess(null, oldStatus);
    }
    
    /*
     * ȡ��ά�޹���������ά�޵��޸�
     */
    public void cancelByJobCard() throws ServiceException {
        load();
        //------------� ׷��-----------------
        CustomerProblemDTO currnentProblemDTO=BusinessUtility.getCustomerProblemDTOByID(cp.getId().intValue());
        FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_P,currnentProblemDTO.getId());
        //------------� ׷��-----------------
        //cp.setReferJobCardID(0);
        //setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT);
        //add by liyanchun
        setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_CANCEL);
    }
    
    public void checkDiagnosis(java.util.Collection diagnosisInfoDtos) throws ServiceException, FinderException, HomeFactoryException {
        Iterator it = diagnosisInfoDtos.iterator();
        BidimConfigSetting bidim=null;
        BidimConfigSettingHome bidimHome=null;
        while (it.hasNext()) {
            DiagnosisInfoDTO diDto = (DiagnosisInfoDTO)it.next();
         
            bidimHome = HomeLocater.getBidimConfigSettingHome();
            bidim = bidimHome.findByPrimaryKey(new Integer(diDto.getInfoSettingId()));
            LogUtility.log(clazz,LogLevel.DEBUG,"������infosettingid *********������"+diDto.getInfoSettingId());
            LogUtility.log(clazz,LogLevel.DEBUG,"������  ���ֱ*********������"+diDto.getMemo());
            if("N".equalsIgnoreCase(bidim.getAllowNull())){
            	if(diDto.getMemo()==null && diDto.getInfoValueId() ==0) 
            		  throw new ServiceException("�����Ϣ����Ϊ��");	
            }
            
            }      
        } 
    public void recordDiagnosisInfo(java.util.Collection diagnosisInfoDtos) throws ServiceException, FinderException, HomeFactoryException {
        Iterator it = diagnosisInfoDtos.iterator();
        BidimConfigSetting bidim=null;
        BidimConfigSettingHome bidimHome=null;
        while (it.hasNext()) {
            DiagnosisInfoDTO diDto = (DiagnosisInfoDTO)it.next();
            System.out.println("$$$$$$$$$zhenduan:"+diDto.getReferSourceId());
            bidimHome = HomeLocater.getBidimConfigSettingHome();
            bidim = bidimHome.findByPrimaryKey(new Integer(diDto.getInfoSettingId()));
            LogUtility.log(clazz,LogLevel.DEBUG,"������infosettingid *********������"+diDto.getInfoSettingId());
            LogUtility.log(clazz,LogLevel.DEBUG,"������  ���ֱ*********������"+diDto.getMemo());
           
            diDto.setReferSourceType(CommonConstDefinition.DIAGNOSISINFOTYPE_EARLY);
            LogUtility.log(clazz,LogLevel.DEBUG,"���������޵��š�����"+cpNo);
            diDto.setReferSourceId(cpNo);
            try {
                DiagnosisInfoHome home = HomeLocater.getDiagnosisInfoHome();
                home.create(diDto);
            } catch (HomeFactoryException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("���������Ϣ��¼ʱ����");
            } catch (CreateException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("���������Ϣ��¼ʱ����");
            }
            
        }        
    }
    
    public void manualTransfer(CustProblemProcessDTO custProblemProcessDto) 
    	throws ServiceException {
        load();
        String oldStatus = cp.getStatus();
        cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT);
        cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        cp.setProcessOrgId(custProblemProcessDto.getNextOrgId());
        cp.setIsManualTransfer("Y");
        int orgid = BusinessUtility.FindOrgIDByOperatorID(operatorID);
        cp.setManualTransferFromOrgID(orgid);
        //      createCustomerProblemProcess record
        createCustProblemProcessWithNextOrgId(custProblemProcessDto, oldStatus, custProblemProcessDto.getNextOrgId());
    }
    
    public void manualCloseRepairSheet(CustProblemProcessDTO custProblemProcessDto) throws ServiceException {
        load();
        String oldStatus = cp.getStatus();
        cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS);
        cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        LogUtility.log(FeeService.class,LogLevel.DEBUG,"����manualCloseRepairSheet����"+cp.getId().intValue());
        //------------� ׷��-----------------
        CustomerProblemDTO currnentProblemDTO=BusinessUtility.getCustomerProblemDTOByID(cp.getId().intValue());
        FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_P,currnentProblemDTO.getId());
        //------------� ׷��-----------------        
        createCustProblemProcess(custProblemProcessDto, oldStatus);
    }
    
    //��ֹά��
    public void terminateRepairInfo(CustProblemProcessDTO custProblemProcessDto) throws ServiceException {
        load();
        LogUtility.log(FeeService.class,LogLevel.DEBUG,"����terminateRepairInfo����"+cp.getId().intValue());
        //------------� ׷��-----------------
        CustomerProblemDTO currnentProblemDTO=BusinessUtility.getCustomerProblemDTOByID(cp.getId().intValue());
        FeeService.closeFeeInfo(CommonConstDefinition.FTREFERTYPE_P,currnentProblemDTO.getId());
        //------------� ׷��-----------------
        String oldStatus = cp.getStatus();
        cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_TERMINAL);
        cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        
        //createCustomerProblemProcess record
        createCustProblemProcess(custProblemProcessDto, oldStatus);
    }
    
    public boolean canBeAssigned() throws ServiceException {
        load();
    	return CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT.equals(cp.getStatus()); 
        
    }
    
    //�Ƿ��Ǵ��������
    private boolean isBigAreaTrouble() throws ServiceException {
        load();
        return cp.getProblemLevel().equals(CommonConstDefinition.CPPROBLEMLEVEL_MUCH_AREA);
    }
    
    public void assign(int jobcardid) throws ServiceException {
            assign(jobcardid, 0);
    }
    public void assign(int jobcardid, int nextOrgID)throws ServiceException {
    	load();
        String oldStatus = cp.getStatus();
        cp.setReferJobCardID(jobcardid);
        cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_PROCESSING);
        cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        //cp.setProcessOrgId(nextOrgID);   
        //createCustomerProblemProcess record
        createCustProblemProcess(null, oldStatus,0); 
		
	} 
    
    public void callCustomerForRepair() throws ServiceException {
        load();
        cp.setCallBackFlag("Y");
        cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        
        //createCustomerProblemProcess record
        CustProblemProcessDTO cppDto = new CustProblemProcessDTO();
        cppDto.setCurrentOperatorId(operatorID);
        cppDto.setCurrentorgId(new OperatorService(context, operatorID).getOrgID());
        cppDto.setAction(Action.getCallBackAction4Repair());
        cppDto.setCustProblemId(cpNo);
        cppDto.setCreateDate(TimestampUtility.getCurrentDate());
        
        save(cppDto);
        
    }
    
    /**
     * �������޻ط���Ϣ(���׷��)
     * @param callBackInfoList �ط���Ϣ�б�
     * @param cpNo ���޵����
     */
    public void createCallbackInfoForRepair(Collection callBackInfoList,int cpNo)throws ServiceException{
    	if(!canCallCustomerForRepair()) throw new ServiceException("��״̬�µı��޵�������طò���!");
    	try{
		    //�������޵�������־
		    String actionDesc = (String)context.get(Service.ACTION_DESCRTIPION);
		    String action =(String)context.get(Service.ACTION_DEFITION);
		    Integer curOperatorID=(Integer)context.get(Service.OPERATIOR_ID);
        	//�޸ı��޵��Ļطñ�־��������־
    		if(CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CALLBACK.equals(action)){
    			cp.setCallBackFlag(CommonConstDefinition.CCPCALLBACKFLAG_YES);
    		}else  if(CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CALLBACK_CACHE.equals(action)){
    			cp.setCallBackFlag(CommonConstDefinition.CPCALLBACKFLAG_T);
    		}else{
    			cp.setCallBackFlag(CommonConstDefinition.CPCALLBACKFLAG_NO);
    		}
            cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
            //add by chenjiang 2006/09/20
            cp.setCallBackDate(TimestampUtility.getCurrentDate());
            CustProblemProcessDTO cppDto = new CustProblemProcessDTO();
            cppDto.setCurrentOperatorId(curOperatorID.intValue());
            cppDto.setCurrentorgId(new OperatorService(context, curOperatorID.intValue()).getOrgID());
            cppDto.setAction(action);
            cppDto.setCustProblemId(cpNo);
            cppDto.setCreateDate(TimestampUtility.getCurrentDate());
            
        	//�������޻ط���Ϣ
    		CallBackInfoHome callBackInfoHome=HomeLocater.getCallBackInfoHome();
    		Collection haveExitCallBackInfo=callBackInfoHome.findByReferTypeAndReferSourceId(CommonConstDefinition.CALLBACKINFOTYPE_REPAIR,cpNo);
    		if(haveExitCallBackInfo!=null && !haveExitCallBackInfo.isEmpty()){
    			Iterator haveExitIterator=haveExitCallBackInfo.iterator();
    			while(haveExitIterator.hasNext()){
    				CallBackInfo callBackInfo=(CallBackInfo)haveExitIterator.next();
    				callBackInfo.remove();
    			}
    		}
    		//�����µı��޻ط���Ϣ
	    	if(callBackInfoList!=null && !callBackInfoList.isEmpty()){
	    		
	    		Iterator callbackInfoIter=callBackInfoList.iterator() ;
	    		while(callbackInfoIter.hasNext()){
	    			CallBackInfoDTO callbackInfoDTO=(CallBackInfoDTO)callbackInfoIter.next();
	    			callbackInfoDTO.setReferSourceId(cpNo);
	    			callbackInfoDTO.setReferSourceType(CommonConstDefinition.CALLBACKINFOTYPE_REPAIR);
	    			CallBackInfo callBackInfo=callBackInfoHome.create(callbackInfoDTO);
	    			callbackInfoDTO.setSeqNo(callBackInfo.getSeqNo().intValue());
	    		}
	    	}
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCallbackInfoForRepair",e);
		    throw new ServiceException("�������޻ط���Ϣ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCallbackInfoForRepair",e);
		    throw new ServiceException("�������޻ط���Ϣʱ��������");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCallbackInfoForRepair",e);
		    throw new ServiceException("�������޻ط���Ϣʱ���Ҵ���");
    	}catch(RemoveException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCallbackInfoForRepair",e);
		    throw new ServiceException("ɾ���ɵı��޻ط���Ϣʱ����");
		}
    	
    }
    
    //�������޵�
    public void createInstance(CustomerProblemDTO cpDto, int nextOrgID) throws ServiceException {
		cpDto.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT);
		cpDto.setCreateDate(TimestampUtility.getCurrentDate());
		cpDto.setDueDate(TimestampUtility.TimestampMove(cpDto.getCreateDate(), 0, 0, 1));
		cpDto.setCallBackFlag(CommonConstDefinition.CPCALLBACKFLAG_NO);
		cpDto.setAddressID(BusinessUtility.getCustomerDTOByCustomerID(cpDto.getCustID()).getAddressID());
		cpDto.setCreateOrgID(BusinessUtility.FindOrgIDByOperatorID(operatorID));
		cpDto.setCreateOpId(operatorID);
		save(cpDto);
		
		/*if (isBigAreaTrouble() && nextOrgID == 0)
            throw new ServiceException("����Ǵ�������ϣ�����ԱӦ���ֹ�������ת��ָ����֯������֯�ɲ���Ա�˹�ѡ�񣩡�");
        else {
            int districtID = getDistrictIDByCustomerID(cp.getCustID());
        */    
            //nextOrgID = PublicService.getInstance().locateOrgIdByDistrictID(districtID, CommonConstDefinition.ROLEORGNIZATIONORGROLE_REPAIR);
       /* }*/
        createCustProblemProcessWithNextOrgId(null, "", nextOrgID);
    }    
    
    public int getCpNo() {
        return cpNo;
    }
    public void setCpNo(int cpNo) {
        this.cpNo = cpNo;
    }
    public int getCustomerID() throws ServiceException {
        load();
        return cp.getCustID();
    }
    
    public CustomerProblemDTO getDTO() throws ServiceException {
        load();
        //fill dto with ejb
        CustomerProblemDTO cpDto = new CustomerProblemDTO();
        cpDto.setId(cp.getId().intValue());
        cpDto.setProblemLevel(cp.getProblemLevel());
        cpDto.setProblemCategory(cp.getProblemCategory());
        cpDto.setReferJobCardID(cp.getReferJobCardID());
        cpDto.setCustID(cp.getCustID());
        cpDto.setCustServiceAccountID(cp.getCustServiceAccountID());
        cpDto.setContactPerson(cp.getContactPerson());
        cpDto.setContactphone(cp.getContactphone());
        cpDto.setAddressID(cp.getAddressID());
        cpDto.setProblemDesc(cp.getProblemDesc());
        cpDto.setCreateDate(cp.getCreateDate());
        cpDto.setDueDate(cp.getDueDate());
        cpDto.setFeeClass(cp.getFeeClass());
        cpDto.setCallBackFlag(cp.getCallBackFlag());
        cpDto.setStatus(cp.getStatus());
        cpDto.setDtCreate(cp.getDtCreate());
        cpDto.setDtLastmod(cp.getDtLastmod());
        cpDto.setCustAccountID(cp.getCustAccountID());
        return cpDto;
    }
    
    /*
     * 1)load entity bean by entity id
     * 
     */
	private void load() throws ServiceException {
	    if (cp != null) return;
	    if (cpNo == 0) throw new IllegalArgumentException("customerProblem ID is not initialized.");
		try {
            CustomerProblemHome home = HomeLocater.getCustomerProblemHome();
           
            cp = home.findByPrimaryKey(new Integer(cpNo));
            cpNo = cp.getId().intValue();
            
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("���ұ��޵�ʱ�������޵�ID��"+cpNo);
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("���ұ��޵�ʱ�������޵�ID��"+cpNo);
        }
	}    
	
	/*
	 * call entity bean to save CustomerProblemDTO
	 */
    private void save(CustomerProblemDTO cpDto) throws ServiceException {
        try {
            CustomerProblemHome home = HomeLocater.getCustomerProblemHome();
      
            cp = home.create(cpDto);
            cpDto.setId(cp.getId().intValue());
            cpNo = cp.getId().intValue();
        } catch (HomeFactoryException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("�������޵�����");
        } catch (CreateException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("�������޵�����");
        }
    }
    /*
     * createCustomerProblemProcess record(NextOrgId == CurrentOrgId)
     * 
     * @param 
     * custProblemProcessDto: if null, new a empty CustProblemProcessDTO
     * oldStatus: ���޵��޸�ǰ״̬
     */
    private void createCustProblemProcess(CustProblemProcessDTO custProblemProcessDto, String oldStatus) throws ServiceException {
        createCustProblemProcess(custProblemProcessDto, oldStatus, 0);
    }
    private void createCustProblemProcess(CustProblemProcessDTO custProblemProcessDto, String oldStatus, int nextOrgID) throws ServiceException {
        createCustProblemProcessWithNextOrgId(custProblemProcessDto, oldStatus, nextOrgID);
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
        createCustProblemProcessWithNextOrgId(custProblemProcessDto,oldStatus,nextOrgId,null,null);
    }
    private void createCustProblemProcessWithNextOrgId(CustProblemProcessDTO custProblemProcessDto, 
            												 String oldStatus, 
            												 int nextOrgId,String action,String memo)
    throws ServiceException{
    	if (custProblemProcessDto == null)
            custProblemProcessDto = new CustProblemProcessDTO();
        int orgId = new OperatorService(context, operatorID).getOrgID(); //get orgid from operatorid
        custProblemProcessDto.setCurrentorgId(orgId);
        custProblemProcessDto.setCurrentOperatorId(operatorID);
        if(action != null)
        	custProblemProcessDto.setAction(action);
        else
        	custProblemProcessDto.setAction(Action.getAction4Repair(oldStatus, cp.getStatus()));
        custProblemProcessDto.setNextOrgId((nextOrgId == 0)?orgId:nextOrgId);
        custProblemProcessDto.setCustProblemId(cpNo);
        custProblemProcessDto.setCreateDate(TimestampUtility.getCurrentDate());
        if(memo != null)
        	custProblemProcessDto.setMemo(memo);
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

    /*
     * ͨ���ͻ�ID��ÿͻ�����������ID���������Ӧ�÷���CustomerService����AddressService����
     * @param customerID
     * @return districtID
     */
    private int getDistrictIDByCustomerID(int customerID) throws ServiceException {
        try {
            Customer customer = HomeLocater.getCustomerHome().findByPrimaryKey(new Integer(customerID));
            Address address = HomeLocater.getAddressHome().findByPrimaryKey(new Integer(customer.getAddressID()));
            return address.getDistrictID();
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("���ҿͻ�����������IDʱ�����ͻ�ID��"+customerID);
        } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("���ҿͻ�����������IDʱ�����ͻ�ID��"+customerID);
        }
    }
    
    private void setOperatorID() {
        if (context.get(Service.OPERATIOR_ID) != null)
            operatorID=((Integer)context.get(Service.OPERATIOR_ID)).intValue();
    }   
    public void diagnosisForRepair(RepairEJBEvent event,int nextOrgID) throws ServiceException {
    	load();
    	cp.setProcessOrgId(nextOrgID);
    	cp.setDtLastmod(TimestampUtility.getCurrentDate());
    	//cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS);
    	LogUtility.log(FeeService.class,LogLevel.DEBUG,"����diagnosisForRepair����"+cp.getId().intValue());
    	createCustProblemProcessWithNextOrgId(null,"",nextOrgID,CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_DIAGNOSIS,event.getMemo());
    	//cp.setStatus(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT);    	
    }
	   
}
