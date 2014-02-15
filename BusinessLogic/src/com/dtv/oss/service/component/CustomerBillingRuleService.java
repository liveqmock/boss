/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import com.dtv.oss.domain.CustomerBillingRule;
import com.dtv.oss.domain.CustomerBillingRuleHome;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.dto.CustomerBillingRuleDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class CustomerBillingRuleService extends AbstractService{
	private static final Class clazz = CustomerBillingRuleService.class;
	private ServiceContext context=null;
	public CustomerBillingRuleService(ServiceContext cxt){
		context=cxt;
	}
	
	/**
	 * 创建客户化计费规则
	 * @param dto
	 * @throws ServiceException 
	 */
	public void createCustomerBillingRuleService(CustomerBillingRuleDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"创建客户化计费规则,CustomerBillingRuleDTO==" + dto);
		//检查数据
		if(dto==null)
			throw new ServiceException("参数传输错误！");
		
		//检查数据的完整性
		if(dto.getPsID()==0)
			throw new ServiceException("目标产品未知！");
		if(dto.getFeeType()==null || "".equals(dto.getFeeType()))
			throw new ServiceException("费用类型不能为空！");
		if(dto.getAcctItemTypeID()==null || "".equals(dto.getAcctItemTypeID()))
			throw new ServiceException("帐目类型ID不能为空！");
		if(dto.getStartDate()==null || dto.getEndDate()==null)
			throw new ServiceException("起止时间不能为空！");
		
		//检查是否存在重叠的时间区间
		if(BusinessUtility.IsCustBillingRuleConflict(dto.getStartDate(),dto.getEndDate(),dto.getPsID(),0))
			throw new ServiceException("客户化计费周期存在时间上的重叠！");
		
		//创建
		dto.setBrRateType(CommonConstDefinition.BILLING_RULE_RATE_TYPE_V);
		//缺省为1，以便将来扩展
		dto.setEventClass(1);
		try{
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(dto.getPsID()));
			dto.setCustPackageID(cp.getReferPackageID());
			CustomerBillingRuleHome home=HomeLocater.getCustomerBillingRuleHome();
			CustomerBillingRule cbr=home.create(dto);
			
			StringBuffer logDes=new StringBuffer();
			logDes.append("个性化费率流水号：" + cbr.getId().intValue());
			logDes.append("，客户产品号：" + cbr.getPsID());
			logDes.append("，费用类型：" + BusinessUtility	.getCommonNameByKey("SET_F_BRFEETYPE",cbr.getFeeType()));
			logDes.append("，帐目类型ID：" + cbr.getAcctItemTypeID());
			logDes.append("，费率：" + cbr.getValue());
			logDes.append("，开始时间：" + cbr.getStartDate().toString());
			logDes.append("，结束时间：" + cbr.getEndDate().toString());
			
			//日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), cp.getCustomerID(), 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "新建个性化费率", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("创建客户化计费规则出错，请重新再试！");
		}		
	}
	/**
	 * 创建客户化计费规则
	 * @param dto
	 * @throws ServiceException 
	 */
	public void createCustBillRuleServiceForOpen(CustomerBillingRuleDTO dto) throws ServiceException{
		//检查数据
		if(dto==null)
			throw new ServiceException("参数传输错误！");
		//创建
		dto.setBrRateType(CommonConstDefinition.BILLING_RULE_RATE_TYPE_V);
		//缺省为1，以便将来扩展
		dto.setEventClass(1);
		try{
			CustomerBillingRuleHome home=HomeLocater.getCustomerBillingRuleHome();
			CustomerBillingRule cbr=home.create(dto);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("创建客户化计费规则出错!");
		}		
	}
		
	/**
	 * 修改客户化计费规则
	 * @param dto
	 * @throws ServiceException 
	 */
	public void updateCustomerBillingRuleService(CustomerBillingRuleDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"计,CustomerBillingRuleDTO==" + dto);
		//检查数据
		if(dto==null)
			throw new ServiceException("参数传输错误！");
		
		//检查数据的完整性
		if(dto.getId()==0)
			throw new ServiceException("没有个性化费率标志，无法完成修改！");
		if(dto.getStatus()==null ||"".equals(dto.getStatus()))
			throw new ServiceException("没有目标状态，无法完成修改！");
		if(!CommonConstDefinition.GENERALSTATUS_INVALID.equals(dto.getStatus())){
			if(dto.getPsID()==0)
				throw new ServiceException("目标产品未知！");
			if(dto.getFeeType()==null || "".equals(dto.getFeeType()))
				throw new ServiceException("费用类型不能为空！");
			if(dto.getAcctItemTypeID()==null || "".equals(dto.getAcctItemTypeID()))
				throw new ServiceException("帐目类型ID不能为空！");
			if(dto.getStartDate()==null || dto.getEndDate()==null)
				throw new ServiceException("起止时间不能为空！");
			//检查是否存在重叠的时间区间
			if(BusinessUtility.IsCustBillingRuleConflict(dto.getStartDate(),dto.getEndDate(),dto.getPsID(),dto.getId()))
				throw new ServiceException("客户化计费周期存在时间上的重叠！");
		}		
		
		//修改
		int result=1;
		StringBuffer logDes=new StringBuffer();
		try{
			CustomerBillingRuleHome home=HomeLocater.getCustomerBillingRuleHome();
			CustomerBillingRule cbr=home.findByPrimaryKey(new Integer(dto.getId()));
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(cbr.getPsID()));
			
			logDes.append("个性化费率流水号：" + dto.getId());
			if(CommonConstDefinition.GENERALSTATUS_INVALID.equals(dto.getStatus()))
				logDes.append("，取消个性化费率");
			else{
				logDes.append(SystemLogRecorder.appendDescString(";费用类型:",
						BusinessUtility.getCommonNameByKey("SET_F_BRFEETYPE",cbr.getFeeType()), 
						BusinessUtility.getCommonNameByKey("SET_F_BRFEETYPE",dto.getFeeType())));
				logDes.append(SystemLogRecorder.appendDescString(";帐目类型ID:",cbr.getAcctItemTypeID(), dto.getAcctItemTypeID()));
				logDes.append(SystemLogRecorder.appendDescString(";费率:","" + cbr.getValue(), "" + dto.getValue()));
				logDes.append(SystemLogRecorder.appendDescString(";开始时间:",cbr.getStartDate().toString(),dto.getStartDate().toString()));
				logDes.append(SystemLogRecorder.appendDescString(";结束时间:",cbr.getEndDate().toString(),dto.getEndDate().toString()));
				logDes.append(SystemLogRecorder.appendDescString(";状态:",
						BusinessUtility.getCommonNameByKey("SET_G_GENERALSTATUS",cbr.getStatus()), 
						BusinessUtility.getCommonNameByKey("SET_G_GENERALSTATUS",dto.getStatus())));
			}
			
			result=cbr.ejbUpdate(dto);
			
			//日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), cp.getCustomerID(), 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "修改个性化费率", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("修改客户化计费规则出错，请重新再试！");
		}
		if(result==-1)
			throw new ServiceException("个性化费率数据不是最新数据，无法更新，请重试！");
	}
}
