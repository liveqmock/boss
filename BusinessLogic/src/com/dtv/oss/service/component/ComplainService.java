/*
 * Created on 2005-11-7
 *
 * @author whq
 */
package com.dtv.oss.service.component;

import java.util.ArrayList;
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
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.dto.*;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ejbevent.csr.CustomerComplainEJBEvent;

public class ComplainService {
	private static final Class clazz = ComplainService.class;

	private int custComplainID = 0;

	private CustomerComplain cc;

	private ServiceContext context;

	private int operatorID;

	public ComplainService(ServiceContext context) {
		this.context = context;
		setOperatorID();
	}

	public ComplainService(int custComplainID, ServiceContext context) {
		this.custComplainID = custComplainID;
		this.context = context;
		setOperatorID();
	}

	private void setOperatorID() {
		if (context.get(Service.OPERATIOR_ID) != null)
			operatorID = ((Integer) context.get(Service.OPERATIOR_ID))
					.intValue();
	}

	private boolean canAssign() throws ServiceException {
		load();
		return cc.getStatus().equals("W"); //="待处理"
	}

	private boolean canProcess() throws ServiceException {
		load();
		return (!((cc.getStatus()
				.equals(CommonConstDefinition.CUSTOMER_COMPLAIN_FALI)) || (cc
				.getStatus()
				.equals(CommonConstDefinition.CUSTOMER_COMPLAIN_SUCESS)))); //="待处理" or ="正在处理"
	}

	private boolean canCallForComplain() throws ServiceException {
		load();
		return cc.getStatus().equals("D"); //="处理完毕"
	}

	private int getProcessOrgID(CustomerComplainDTO dto) throws ServiceException {
		int orgID = dto.getComplainedOrgId();
		if (!(orgID > 0)) {
			orgID = BusinessUtility.getAutoNextOrgID(CommonConstDefinition.ROLEORGNIZATIONORGROLE_TS,dto,null);
		}
		return orgID;
	}

	//投诉回访
	public void callForComplain(CustomerComplainDTO cpDto,
			CustComplainProcessDTO ccpDto) throws ServiceException {
		load();
		if (canCallForComplain() == false)
			throw new ServiceException("只有处理完毕的投诉单才可以回访，投诉单ID："
					+ custComplainID);
		cpDto.setCallBackFlag("Y"); //已回访
		if (cc.ejbUpdate(cpDto) == -1)
			throw new RuntimeException("更新投诉单时出错，投诉单ID：" + custComplainID);

		ccpDto.setAction(""); //回访
		ccpDto.setCurrentOperatorId(operatorID);
		ccpDto.setCurrentOrgId(new OperatorService(context, operatorID)
				.getOrgID());
		createCustComplainProcess(ccpDto);
	}

	//投诉处理
	public void processComplain(CustomerComplainDTO cpDto,
			CustComplainProcessDTO ccpDto, int type) throws ServiceException {
		if (cpDto == null) {
			LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空，不能处理投诉信息!");
			return;
		}
		load();
		if (canProcess() == false)
			throw new ServiceException("只有待处理的投诉单才可以处理，投诉单ID：" + custComplainID);
		switch (type) {
		case CustomerComplainEJBEvent.COMPLAIN_CREATE_NOPRE:
			cc.setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_WAIT);
			cc.setCurrentWorkOrgID(ccpDto.getNextOrgId());
			//        	ccpDto.setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_WAIT);
			break;
		case CustomerComplainEJBEvent.COMPLAIN_CREATE_PRE:
			cc.setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_SUCESS);
			ccpDto.setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_SUCESS);
			break;
		case CustomerComplainEJBEvent.COMPLAIN_PROCESS_SUCCESS:
			cc.setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_SUCESS);
			ccpDto.setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_SUCESS);
			break;
		case CustomerComplainEJBEvent.COMPLAIN_PROCESS_FAILURE:
			cc.setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_WAIT);
			ccpDto.setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_FALI);
			break;
		}
		cc.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		cpDto.setStatus(cc.getStatus());
		ccpDto.setCurrentOperatorId(operatorID);
		ccpDto.setCurrentOrgId(new OperatorService(context, operatorID)
				.getOrgID());
		ccpDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		ccpDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		System.out.println("ccstatus===" + cpDto.getStatus() + "ccpstatus===="
				+ ccpDto);
		createCustComplainProcess(ccpDto);
	}

	//投诉分派
	public void assignComplain(CustComplainProcessDTO ccpDto)
			throws ServiceException {
		load();
		if (canAssign() == false)
			throw new ServiceException("只有待处理的投诉单才可以分派，投诉单ID：" + custComplainID);

		ccpDto.setAction(""); //分派
		ccpDto.setCurrentOperatorId(operatorID);
		ccpDto.setCurrentOrgId(new OperatorService(context, operatorID)
				.getOrgID());
		createCustComplainProcess(ccpDto);
	}

	//投诉受理
	public int acceptComplain(CustomerComplainDTO ccDto)
			throws ServiceException {
		if (ccDto == null) {
			LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空，不能创建投诉信息!");
			return -1;
		}
		if (ccDto.getComplainedOrgId() <= 0) {
			int nextOrgID = getProcessOrgID(ccDto);
			if (nextOrgID <= 0) {
				LogUtility.log(clazz, LogLevel.WARN,
						"选择的处理部门和默认处理部门不能都为空，不能创建投诉信息!");
				return -1;
			} else
				ccDto.setCurrentWorkOrgID(nextOrgID);
		} else
			ccDto.setCurrentWorkOrgID(ccDto.getComplainedOrgId());
		int customerID = ccDto.getCustomerId();
		if (customerID == 0)
			throw new IllegalArgumentException("投诉人的客户号必须设置。");
		try {
			Customer customer = HomeLocater.getCustomerHome().findByPrimaryKey(
					new Integer(customerID));
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找客户时出错，客户号：" + customerID);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找客户时出错，客户号：" + customerID);
		}
		ccDto.setCallBackFlag(CommonConstDefinition.CPCALLBACKFLAG_NO);
		ccDto.setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_WAIT); //待处理
		save(ccDto);
		/*
		 ccpDto.setCurrentOperatorId(operatorID);
		 ccpDto.setCurrentOrgId(new OperatorService(context, operatorID).getOrgID());
		 //ccpDto.setAction(Action.getAction4Repair("", cc.getStatus()));
		 //int districtID = getDistrictIDByCustomerID(customerID);
		 int nextOrgID=this.getProcessOrgID(ccDto);
		 //ccDto.setComplainedOrgId(orgID);
		 //int nextOrgID = PublicService.getInstance().locateOrgIdByDistrictID(districtID, CommonConstDefinition.ROLEORGNIZATIONORGROLE_TS);
		 ccpDto.setNextOrgId(nextOrgID);
		 createCustComplainProcess(ccpDto);
		 */
		return custComplainID;
	}

	private void createCustComplainProcess(CustComplainProcessDTO ccpDto)
			throws ServiceException {
		if (ccpDto == null) {
			LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空，不能创建投诉处理信息!");
			return;
		}
		try {
			ccpDto.setCustComplainId(custComplainID);
			ccpDto.setCreateDate(TimestampUtility.getCurrentDate());
			CustComplainProcessHome ccpHome = HomeLocater
					.getCustComplainProcessHome();
			ccpHome.create(ccpDto);

			int customerID = BusinessUtility
			.getCustomerIDBycustComplainID(ccpDto.getCustComplainId());
			if(customerID==0){
				customerID=context.get(Service.CUSTOMER_ID)!=null?((Integer)context.get(Service.CUSTOMER_ID)).intValue():0;
			}
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), customerID,
					SystemLogRecorder.LOGMODULE_CUSTSERV, "客户投诉", "客户投诉--投诉处理"
							+ ",投诉单ID：" + custComplainID + ",客户ID："
							+ customerID + ",客户姓名:"
							+ BusinessUtility.getCustomerNameById(customerID),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建投诉处理记录时出错。");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建投诉处理记录时出错。");
		}
	}

	/*
	 * 通过客户ID获得客户所属的区域ID，这个方法应该放入CustomerService（或AddressService）中
	 * @param customerID
	 * @return districtID
	 */
	private int getDistrictIDByCustomerID(int customerID)
			throws ServiceException {
		try {
			Customer customer = HomeLocater.getCustomerHome().findByPrimaryKey(
					new Integer(customerID));
			Address address = HomeLocater.getAddressHome().findByPrimaryKey(
					new Integer(customer.getAddressID()));
			return address.getDistrictID();
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找客户所属的区域ID时出错，客户ID：" + customerID);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找客户所属的区域ID时出错，客户ID：" + customerID);
		}
	}

	/*
	 * 1)load entity bean by entity id
	 * 
	 */
	private void load() throws ServiceException {
		if (cc != null)
			return;
		if (custComplainID == 0)
			throw new IllegalArgumentException(
					"custComplainID is not initialized.");
		try {
			CustomerComplainHome home = HomeLocater.getCustomerComplainHome();

			cc = home.findByPrimaryKey(new Integer(custComplainID));
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找投诉单时出错，投诉单ID：" + custComplainID);
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找投诉单时出错，投诉单ID：" + custComplainID);
		}
	}

	private void save(CustomerComplainDTO ccDto) throws ServiceException {
		try {
			CustomerComplainHome home = HomeLocater.getCustomerComplainHome();
			cc = home.create(ccDto);
			custComplainID = cc.getCustComplainId().intValue();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), ccDto.getCustomerId(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "客户投诉", "客户投诉--投诉受理"
							+ ",投诉单ID："
							+ custComplainID
							+ ",客户ID："
							+ ccDto.getCustomerId()
							+ ",客户姓名:"
							+ BusinessUtility.getCustomerNameById(ccDto
									.getCustomerId()),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建客户投诉出错");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建客户投诉出错");
		}
	}

	public static void main(String[] args) {
	}

	public int getCustomerID() throws ServiceException {
		load();
		return cc.getCustomerId();
	}

	public void processPrepare(CustomerComplainDTO ccDto,
			CustComplainProcessDTO cpDto, int type) throws ServiceException {
		//load();
		switch (type) {
		case CustomerComplainEJBEvent.COMPLAIN_CREATE_NOPRE:
			//cc.setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_WAIT);
			cpDto.setAction(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_CR);
			//int nextOrgID = getProcessOrgID(ccDto);
		/*	if (cpDto.getNextOrgId() <= 0) {
					LogUtility.log(clazz, LogLevel.WARN, "处理部门为空，不能创建投诉处理信息!");
					return;								
			}*/
			//cpDto.setNextOrgId(nextOrgID);
			cpDto.setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_WAIT);
			break;
		case CustomerComplainEJBEvent.COMPLAIN_CREATE_PRE:
			//cc.setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_SUCESS);
			cpDto.setStatus(CommonConstDefinition.CUSTCOMPLAIN_PROCESS_SUCESS);
			break;
		case CustomerComplainEJBEvent.COMPLAIN_PROCESS_SUCCESS:
			custComplainID = cpDto.getCustComplainId();
			break;
		case CustomerComplainEJBEvent.COMPLAIN_PROCESS_FAILURE:
			custComplainID = cpDto.getCustComplainId();
			//nextOrgID = getProcessOrgID(ccDto);
			if (cpDto.getNextOrgId() <= 0) {
				LogUtility.log(clazz, LogLevel.WARN, "处理部门为空，不能创建投诉处理信息!");
				return;
			} //else {
			//	cpDto.setNextOrgId(nextOrgID);
			//}
			break;
		default:
			break;
		}
	}

	/*public void changeStatus(CustomerComplainDTO ccDto,CustComplainProcessDTO cpDto,int type)throws ServiceException{
	 load();
	 switch(type){
	 case CustomerComplainEJBEvent.COMPLAIN_CREATE_NOPRE:
	 cc.setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_WAIT);
	 break;
	 case CustomerComplainEJBEvent.COMPLAIN_CREATE_PRE:
	 cc.setStatus(CommonConstDefinition.CUSTOMER_COMPLAIN_SUCESS);
	 break;
	 default:
	 break;
	 }
	 }*/
	//fionabegin
	/**
	 * 客户投诉流转/手工结束投诉
	 * @param inEvent
	 */
	public void transferAndManualendService(CustomerComplainEJBEvent inEvent)
			throws ServiceException {
		try {
			CustComplainProcessHome custComplainProcessHome = HomeLocater
					.getCustComplainProcessHome();
			custComplainProcessHome.create(inEvent.getPdto());
			CustomerComplainHome customerComplainHome = HomeLocater
					.getCustomerComplainHome();
			CustomerComplain customerComplain = customerComplainHome
					.findByPrimaryKey(new Integer(inEvent.getDto()
							.getCustComplainId()));
			customerComplain.ejbUpdate(inEvent.getDto());
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "checkProductDependency", e1);
			throw new ServiceException("客户投诉流转定位错误。");
		} catch (CreateException e2) {
			LogUtility.log(clazz, LogLevel.ERROR, "checkProductDependency", e2);
			throw new ServiceException("客户投诉流转创建错误。");
		} catch (FinderException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "checkProductDependency", e3);
			throw new ServiceException("客户投诉流转创建错误。");
		}
	}

	/**
	 * 投诉回访
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void callbackCustComplain(CustomerComplainEJBEvent inEvent)
			throws ServiceException {
		try {
			CallBackInfoHome callBackInfoHome = HomeLocater
					.getCallBackInfoHome();
			//删除老的投诉回访信息
			int complainId = inEvent.getDto().getCustComplainId();
			Collection haveExitCallBackInfo = callBackInfoHome
					.findByReferTypeAndReferSourceId(
							CommonConstDefinition.CALLBACKINFOTYPE_C,
							complainId);
			if (haveExitCallBackInfo != null && !haveExitCallBackInfo.isEmpty()) {
				Iterator haveExitIterator = haveExitCallBackInfo.iterator();
				while (haveExitIterator.hasNext()) {
					CallBackInfo callBackInfo = (CallBackInfo) haveExitIterator
							.next();
					callBackInfo.remove();
				}
			}
			ArrayList callBackInfoList = (ArrayList) inEvent
					.getCallBackInfoList();
			System.out.println("***********callBackInfoList="
					+ callBackInfoList.size() + callBackInfoList.toString());
			//创建新的投诉回访信息
			if (callBackInfoList != null && !callBackInfoList.isEmpty()) {
				Iterator callbackInfoIter = callBackInfoList.iterator();
				while (callbackInfoIter.hasNext()) {
					CallBackInfoDTO callbackInfoDTO = (CallBackInfoDTO) callbackInfoIter
							.next();
					callbackInfoDTO.setReferSourceId(complainId);
					callbackInfoDTO
							.setReferSourceType(CommonConstDefinition.CALLBACKINFOTYPE_C);
					CallBackInfo callBackInfo = callBackInfoHome
							.create(callbackInfoDTO);
					callbackInfoDTO
							.setSeqNo(callBackInfo.getSeqNo().intValue());
				}
			}
			transferAndManualendService(inEvent);
		} catch (HomeFactoryException e) {
			LogUtility
					.log(clazz, LogLevel.ERROR, "createCsiCustProductInfo", e);
			throw new ServiceException("创建投诉回访信息定位错误");
		} catch (CreateException e) {
			LogUtility
					.log(clazz, LogLevel.ERROR, "createCsiCustProductInfo", e);
			throw new ServiceException("创建投诉回访信息时创建错误");
		} catch (FinderException e) {
			LogUtility
					.log(clazz, LogLevel.ERROR, "createCsiCustProductInfo", e);
			throw new ServiceException("创建投诉回访信息时查找错误");
		} catch (RemoveException e) {
			LogUtility
					.log(clazz, LogLevel.ERROR, "createCustomerBackupInfo", e);
			throw new ServiceException("删除旧的投诉回访信息时错误");
		}
	}
}
