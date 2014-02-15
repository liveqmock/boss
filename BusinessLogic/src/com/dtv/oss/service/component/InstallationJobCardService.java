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
     * 预约或者门店开户时生成安装工单
     * @param csiid 
     * @param customerID 如果是预约时调用该方法，则该参数为0
     * @param serviceAcctID 如果是预约时调用该方法，则该参数为0
     * @param nciDto
     * @param custAddrDto客户地址，其中需包含addressID和districtID
     * @return 工单ID
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
		
			//工单：关联单据类型，创建操作员，创建组织机构，创建时间，创建来源  2007-08-09增加
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
			throw new ServiceException("创建工单对象出现错误");
		
		} catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("创建工单对象出现错误");
		
		}
		return getJobCardNo();
    }

    /**
     * 新增业务账户时调用的方法(由杨晨追加)
     * @param csiid 受理单id
     * @param customerDto 客户信息dto
     * @param custAddrDto 客户地址信息dto
     * @param nextorgid 流转部门id
     * @return 工单号
     * @throws ServiceException
     */
    public int createJobCardForCustomerOperation(CustServiceInteractionDTO csiDto,
            				 		   CustomerDTO customerDto, 
            				 		   AddressDTO custAddrDto,
									   int nextorgid) 
		throws ServiceException {
		//创建工单
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
			
			//工单：关联单据类型，创建操作员，创建组织机构，创建时间，创建来源  2007-08-09增加
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
			throw new ServiceException("创建工单对象出现错误");
		
		} catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("创建工单对象出现错误");
		
		}
		return getJobCardNo();
    }    
    public Object getOriginalSheet() throws ServiceException {
        load();
        return new Integer(jc.getReferSheetId());
    }
    /**
     * 修改业务账户时调用的方法
     * @param csiDto
     * @throws HomeFactoryException 
     * @throws FinderException 
     * @throws ServiceException 
     */
    public void updateJobCardForCustomerOperation(CustServiceInteractionDTO csiDto) throws  ServiceException{
        try {
  	       CustomerHome customerHome =HomeLocater.getCustomerHome();
           Customer customer=customerHome.findByPrimaryKey(new Integer(csiDto.getCustomerID()));
           //客户地址信息
  	       AddressHome addressHome =HomeLocater.getAddressHome();
  	       Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));

          //更新工单
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
			throw new ServiceException("创建工单对象出现错误");
		
		} catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("创建工单对象出现错误");
		
		}
    }
    
    //此方法暂时不完成任何功能
    public void relateWithOriginalSheet(Object cps) throws ServiceException {
    
    }   
    
    //在子类实现中增加对工单类型的判断
    protected void load() throws ServiceException {
        super.load();
        if (!(jc.getJobType().equals(CommonConstDefinition.JOBCARD_TYPE_INSTALLATION))
        		&& !(jc.getJobType().equals(CommonConstDefinition.JOBCARD_TYPE_CATV)))
            throw new ServiceException("只有安装工单才能进行此项操作！");
    }
    
    /*
     * 模拟开户工单处理
     * @param csiid 
     * @param customerID 如果是预约时调用该方法，则该参数为0
     * @param serviceAcctID 如果是预约时调用该方法，则该参数为0
     * @param nciDto
     * @param custAddrDto客户地址，其中需包含addressID和districtID
     * @return 工单ID
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
		
    	//如果catvID存在，则生成恢复开通作业单
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
    			throw new ServiceException("查找客户终端信息出错");
    		}
    		
    		if(addPortNum>0){
    			jcDto.setSubType(CommonConstDefinition.CATV_JOBCARD_SUBTYPE_Z);
    			jcDto.setDescription("模拟电视开户，已有终端，进行增端操作");
    		}
    		else{
    			jcDto.setSubType(CommonConstDefinition.CATV_JOBCARD_SUBTYPE_H);
    			jcDto.setDescription("模拟电视开户，已有终端恢复开通");
    		}
    	}
    	//否则生成安装单
    	else{
    		String strNewCatvid=(String)context.get(Service.OPEN_CATV_NEW_CATVID);
    		if(strNewCatvid==null)
    			strNewCatvid="";
    		jcDto.setCatvID(strNewCatvid);
    		jcDto.setOldPortNumber(0);
    		jcDto.setJobType(CommonConstDefinition.JOBCARD_TYPE_INSTALLATION);
    		//jcDto.setSubType(CommonConstDefinition.CATV_JOBCARD_SUBTYPE_Z);
    		jcDto.setDescription("模拟电视开户，新增终端");
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
			throw new ServiceException("创建工单对象出现错误");
		
		} 
		
		return getJobCardNo();
    }
    
    public static void main(String[] args) {
    }
}
