/*
 * Created on 2005-9-22
 *
 * @author whq
 */
package com.dtv.oss.service.component;

import java.sql.Timestamp;

import javax.ejb.FinderException;

import com.dtv.oss.dto.*;
import com.dtv.oss.service.*;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.domain.Address;
import com.dtv.oss.domain.AddressHome;
import com.dtv.oss.domain.CatvTerminal;
import com.dtv.oss.domain.CatvTerminalHome;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.domain.CustomerHome;
import com.dtv.oss.domain.JobCard;
import com.dtv.oss.domain.JobCardHome;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.domain.Operator;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.factory.HomeFactoryException;

public class InstallationJobCardService extends JobCardService {
    private static final Class clazz = InstallationJobCardService.class;
    public InstallationJobCardService(ServiceContext context) {
        super(context);
    }
    
    public InstallationJobCardService(int jobCardNo, ServiceContext context) {
        super(jobCardNo, context);
    }
    
    public void setJobCardType(JobCardDTO jcDto) {
        jcDto.setJobType(CommonConstDefinition.JOBCARD_TYPE_INSTALLATION);     
    }
    /*
     * ԤԼ�����ŵ꿪��ʱ���ɰ�װ����
     * @param csiid 
     * @param customerID �����ԤԼʱ���ø÷�������ò���Ϊ0
     * @param serviceAcctID �����ԤԼʱ���ø÷�������ò���Ϊ0
     * @param nciDto
     * @param custAddrDto�ͻ���ַ�����������addressID��districtID
     * @return ����ID
     */
    public int createJobCardForBooking(CustServiceInteractionDTO csiDto,
            						   int customerID,
            						   int serviceAcctID,
            						   NewCustomerInfoDTO nciDto, 
            				 		   AddressDTO custAddrDto,
									   int nextorgid) 
		throws ServiceException {
		//create JobCard
		JobCardDTO jcDto = new JobCardDTO();
		jcDto.setJobType(CommonConstDefinition.JOBCARD_TYPE_INSTALLATION);
		jcDto.setReferSheetId(csiDto.getId());
		jcDto.setCustId(customerID);
		jcDto.setCustServiceAccountId(serviceAcctID);
		jcDto.setPreferedDate(csiDto.getPreferedDate());
		jcDto.setPreferedTime(csiDto.getPreferedTime());
		
		jcDto.setContactPerson(nciDto.getName());
		String currentPhone = "";
		if (nciDto.getTelephone() != null) {
		    currentPhone = nciDto.getTelephone();
		}
		if (nciDto.getMobileNumber() != null && nciDto.getMobileNumber().equals("")==false) {
		    if (currentPhone.equals("") == false) {
		        currentPhone = currentPhone + "/" + nciDto.getMobileNumber();
		    } else {
		        currentPhone = nciDto.getMobileNumber();
		    }
		}
		
		jcDto.setContactPhone(currentPhone);
		jcDto.setAddressId(custAddrDto.getAddressID());
		jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_WAIT);
		jcDto.setProcessOrgId(nextorgid);
		
		try {
			OperatorHome opeHome = HomeLocater.getOperatorHome();
			Operator operator = opeHome.findByPrimaryKey(new Integer(operatorID));
		
			//�����������������ͣ���������Ա��������֯����������ʱ�䣬������Դ  2007-08-09����
			jcDto.setCreateOpID(operatorID);
			jcDto.setCreateOrgID(operator.getOrgID());
			jcDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			jcDto.setCreateMethod(CommonConstDefinition.JOBCARDCREATEMETHOD_A);
			jcDto.setReferType(CommonConstDefinition.JOBCARD_REFERTYPE_S);
			save(jcDto);
			
			JobCardProcessDTO jcpDto = new JobCardProcessDTO();
			jcpDto.setJcId(getJobCardNo());
			jcpDto.setCurrentOperatorId(operatorID);
			jcpDto.setCurrentOrgId(operator.getOrgID());
			jcpDto.setAction(Action.getAction4JobCard("", jc.getStatus())); 
			jcpDto.setActionTime(TimestampUtility.getCurrentTimestamp());
			jcpDto.setProcessMan(operator.getOperatorName());
			jcpDto.setProcessOrgId((new Integer(operator.getOrgID())).toString());
			jcpDto.setWorkResult(CommonConstDefinition.JOBCARD_WORKRESULT_SUCCESS);
			
			//int nextorgid = PublicService.getInstance().locateOrgIdByDistrictID(custAddrDto.getDistrictID(), CommonConstDefinition.ROLEORGNIZATIONORGROLE_INSTALL);
			jcpDto.setNextOrgId(nextorgid);
			
			save(jcpDto);
		
		} catch(javax.ejb.FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("��������������ִ���");
		
		} catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("��������������ִ���");
		
		}
		return getJobCardNo();
    }

    /**
     * ����ҵ���˻�ʱ���õķ���(���׷��)
     * @param csiid ����id
     * @param customerDto �ͻ���Ϣdto
     * @param custAddrDto �ͻ���ַ��Ϣdto
     * @param nextorgid ��ת����id
     * @return ������
     * @throws ServiceException
     */
    public int createJobCardForCustomerOperation(CustServiceInteractionDTO csiDto,
            				 		   CustomerDTO customerDto, 
            				 		   AddressDTO custAddrDto,
									   int nextorgid) 
		throws ServiceException {
		//��������
		JobCardDTO jcDto = new JobCardDTO();
		jcDto.setJobType(CommonConstDefinition.JOBCARD_TYPE_INSTALLATION);
		jcDto.setReferSheetId(csiDto.getId());
		jcDto.setCustId(customerDto.getCustomerID());
		jcDto.setCustServiceAccountId(0);
		jcDto.setContactPerson(csiDto.getContactPerson());
		jcDto.setContactPhone(csiDto.getContactPhone());
		jcDto.setAddressId(custAddrDto.getAddressID());
		jcDto.setPreferedDate(csiDto.getPreferedDate());
		jcDto.setPreferedTime(csiDto.getPreferedTime());
		jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_WAIT);
		jcDto.setProcessOrgId(nextorgid);
		

		try {
			OperatorHome opeHome = HomeLocater.getOperatorHome();
			Operator operator = opeHome.findByPrimaryKey(new Integer(operatorID));
			
			//�����������������ͣ���������Ա��������֯����������ʱ�䣬������Դ  2007-08-09����
			jcDto.setCreateOpID(operatorID);
			jcDto.setCreateOrgID(operator.getOrgID());
			jcDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			jcDto.setCreateMethod(CommonConstDefinition.JOBCARDCREATEMETHOD_A);
			jcDto.setReferType(CommonConstDefinition.JOBCARD_REFERTYPE_S);
			save(jcDto);
			
			JobCardProcessDTO jcpDto = new JobCardProcessDTO();
			jcpDto.setJcId(getJobCardNo());
			jcpDto.setCurrentOperatorId(operatorID);
			jcpDto.setCurrentOrgId(operator.getOrgID());
			jcpDto.setAction(Action.getAction4JobCard("", jc.getStatus())); 
			jcpDto.setActionTime(TimestampUtility.getCurrentTimestamp());
			jcpDto.setProcessMan(operator.getOperatorName());
			jcpDto.setProcessOrgId((new Integer(operator.getOrgID())).toString());
			jcpDto.setWorkResult(CommonConstDefinition.JOBCARD_WORKRESULT_SUCCESS);
			//int nextorgid = PublicService.getInstance().locateOrgIdByDistrictID(custAddrDto.getDistrictID(), CommonConstDefinition.ROLEORGNIZATIONORGROLE_INSTALL);
			jcpDto.setNextOrgId(nextorgid);			
			save(jcpDto);
		
		} catch(javax.ejb.FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("��������������ִ���");
		
		} catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("��������������ִ���");
		
		}
		return getJobCardNo();
    }    
    public Object getOriginalSheet() throws ServiceException {
        load();
        return new Integer(jc.getReferSheetId());
    }
    /**
     * �޸�ҵ���˻�ʱ���õķ���
     * @param csiDto
     * @throws HomeFactoryException 
     * @throws FinderException 
     * @throws ServiceException 
     */
    public void updateJobCardForCustomerOperation(CustServiceInteractionDTO csiDto) throws  ServiceException{
        try {
  	       CustomerHome customerHome =HomeLocater.getCustomerHome();
           Customer customer=customerHome.findByPrimaryKey(new Integer(csiDto.getCustomerID()));
           //�ͻ���ַ��Ϣ
  	       AddressHome addressHome =HomeLocater.getAddressHome();
  	       Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));

          //���¹���
  	      JobCardHome jobCardHome =HomeLocater.getJobCardHome();
  	      JobCard jobCard=jobCardHome.findByPrimaryKey(new Integer(csiDto.getReferJobCardID()));
  	      jobCard.setContactPerson(csiDto.getContactPerson());
  	      jobCard.setContactPhone(csiDto.getContactPhone());
  	      jobCard.setPreferedDate(csiDto.getPreferedDate());
  	      jobCard.setPreferedTime(csiDto.getPreferedTime());
  	   
		  OperatorHome opeHome = HomeLocater.getOperatorHome();
		  Operator operator = opeHome.findByPrimaryKey(new Integer(operatorID));
		  JobCardProcessDTO jcpDto = new JobCardProcessDTO();
		  jcpDto.setJcId(getJobCardNo());
		  jcpDto.setCurrentOperatorId(operatorID);
		  jcpDto.setCurrentOrgId(operator.getOrgID());
		  jcpDto.setAction(CommonConstDefinition.JOBCARDPROCESS_ACTION_MODIFY); 
		  jcpDto.setActionTime(TimestampUtility.getCurrentTimestamp());
		  jcpDto.setProcessMan(operator.getOperatorName());
		  jcpDto.setProcessOrgId((new Integer(operator.getOrgID())).toString());
		  jcpDto.setWorkResult(CommonConstDefinition.JOBCARD_WORKRESULT_SUCCESS);
			
		  int nextorgid = PublicService.getInstance().locateOrgIdByDistrictID(address.getDistrictID(), CommonConstDefinition.ROLEORGNIZATIONORGROLE_INSTALL);
			jcpDto.setNextOrgId(nextorgid);
			
			save(jcpDto);
		
		} catch(javax.ejb.FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("��������������ִ���");
		
		} catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("��������������ִ���");
		
		}
    }
    
    //�˷�����ʱ������κι���
    public void relateWithOriginalSheet(Object cps) throws ServiceException {
    
    }   
    
    //������ʵ�������ӶԹ������͵��ж�
    protected void load() throws ServiceException {
        super.load();
        if (!(jc.getJobType().equals(CommonConstDefinition.JOBCARD_TYPE_INSTALLATION))
        		&& !(jc.getJobType().equals(CommonConstDefinition.JOBCARD_TYPE_CATV)))
            throw new ServiceException("ֻ�а�װ�������ܽ��д��������");
    }
    
    /*
     * ģ�⿪����������
     * @param csiid 
     * @param customerID �����ԤԼʱ���ø÷�������ò���Ϊ0
     * @param serviceAcctID �����ԤԼʱ���ø÷�������ò���Ϊ0
     * @param nciDto
     * @param custAddrDto�ͻ���ַ�����������addressID��districtID
     * @return ����ID
     */
    public int createJobCardForCATVOpenAccount(CustServiceInteractionDTO csiDto,
            						   int customerID,
            						   int serviceAcctID,
            						   NewCustomerInfoDTO nciDto, 
            				 		   AddressDTO custAddrDto,
									   int nextorgid,String catvID,int addPortNum) 
		throws ServiceException {
    	
    	JobCardDTO jcDto = new JobCardDTO();
		jcDto.setReferSheetId(csiDto.getId());
		jcDto.setCustId(customerID);
		jcDto.setCustServiceAccountId(serviceAcctID);
		jcDto.setPreferedDate(csiDto.getPreferedDate());
		jcDto.setPreferedTime(csiDto.getPreferedTime());	
		jcDto.setCreateOpID(PublicService.getCurrentOperatorID(context));
		jcDto.setCreateOrgID(BusinessUtility.getOpOrgIDByOperatorID(jcDto.getCreateOpID()));
		jcDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
		jcDto.setCreateMethod(CommonConstDefinition.JOBCARDCREATEMETHOD_A);
		jcDto.setContactPerson(nciDto.getName());
		jcDto.setReferType(CommonConstDefinition.JOBCARD_REFERTYPE_S);
		String currentPhone = "";
		if (nciDto.getTelephone() != null) 
		    currentPhone = nciDto.getTelephone();
		if (nciDto.getMobileNumber() != null && nciDto.getMobileNumber().equals("")==false) {
		    if (currentPhone.equals("") == false) {
		        currentPhone = currentPhone + "/" + nciDto.getMobileNumber();
		    } else {
		        currentPhone = nciDto.getMobileNumber();
		    }
		}
		jcDto.setContactPhone(currentPhone);
		jcDto.setAddressId(custAddrDto.getAddressID());
		jcDto.setStatus(CommonConstDefinition.JOBCARD_STATUS_WAIT);
		jcDto.setProcessOrgId(nextorgid);
		jcDto.setAddPortNumber(addPortNum);
		
    	//���catvID���ڣ������ɻָ���ͨ��ҵ��
    	if(BusinessUtility.IsExistCatvID(catvID)){
    		jcDto.setJobType(CommonConstDefinition.JOBCARD_TYPE_CATV);
    		jcDto.setCatvID(catvID);
    		
    		try{
    			CatvTerminalHome catvTerminalHome=HomeLocater.getCatvTerminalHome();
    			CatvTerminal catvTerminal=catvTerminalHome.findByPrimaryKey(catvID);
    			jcDto.setOldPortNumber(catvTerminal.getPortNo());
    		}
    		catch(Exception e){
    			LogUtility.log(clazz, LogLevel.ERROR,e);
    			throw new ServiceException("���ҿͻ��ն���Ϣ����");
    		}
    		
    		if(addPortNum>0){
    			jcDto.setSubType(CommonConstDefinition.CATV_JOBCARD_SUBTYPE_Z);
    			jcDto.setDescription("ģ����ӿ����������նˣ��������˲���");
    		}
    		else{
    			jcDto.setSubType(CommonConstDefinition.CATV_JOBCARD_SUBTYPE_H);
    			jcDto.setDescription("ģ����ӿ����������ն˻ָ���ͨ");
    		}
    	}
    	//�������ɰ�װ��
    	else{
    		String strNewCatvid=(String)context.get(Service.OPEN_CATV_NEW_CATVID);
    		if(strNewCatvid==null)
    			strNewCatvid="";
    		jcDto.setCatvID(strNewCatvid);
    		jcDto.setOldPortNumber(0);
    		jcDto.setJobType(CommonConstDefinition.JOBCARD_TYPE_INSTALLATION);
    		//jcDto.setSubType(CommonConstDefinition.CATV_JOBCARD_SUBTYPE_Z);
    		jcDto.setDescription("ģ����ӿ����������ն�");
    	}
		save(jcDto);

		try {
			OperatorHome opeHome = HomeLocater.getOperatorHome();
			Operator operator = opeHome.findByPrimaryKey(new Integer(operatorID));
			JobCardProcessDTO jcpDto = new JobCardProcessDTO();
			jcpDto.setJcId(getJobCardNo());
			jcpDto.setCurrentOperatorId(operatorID);
			jcpDto.setCurrentOrgId(operator.getOrgID());
			jcpDto.setAction(Action.getAction4JobCard("", jc.getStatus())); 
			jcpDto.setActionTime(TimestampUtility.getCurrentTimestamp());
			jcpDto.setProcessMan(operator.getOperatorName());
			jcpDto.setProcessOrgId((new Integer(operator.getOrgID())).toString());
			jcpDto.setWorkResult(CommonConstDefinition.JOBCARD_WORKRESULT_SUCCESS);
			
			//int nextorgid = PublicService.getInstance().locateOrgIdByDistrictID(custAddrDto.getDistrictID(), CommonConstDefinition.ROLEORGNIZATIONORGROLE_INSTALL);
			jcpDto.setNextOrgId(nextorgid);
			
			save(jcpDto);
		
		} catch(Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("��������������ִ���");
		
		} 
		
		return getJobCardNo();
    }
    
    public static void main(String[] args) {
    }
}
