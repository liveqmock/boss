/*
 * Created on 2004-8-12
 * 
 * @author yanjian
 */
package com.dtv.oss.service.command.system;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustomerCampaign;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.domain.ServiceAccount;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.custom.BatchNoDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
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
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.CampaignService;
import com.dtv.oss.service.component.CustomerProductService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import com.dtv.oss.service.component.ImmediateCountFeeService;
import com.dtv.oss.service.component.ImmediatePayFeeService;
import com.dtv.oss.service.component.ServiceAccountService;
// import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.system.CustCampaignEJBEvent;
import com.dtv.oss.service.ejbevent.system.SystemEJBEvent;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.InfoFeeImmediateBilling;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeCalculator;
import com.dtv.oss.util.TimestampUtility;

public class CustCampaignCommand extends Command {
	private static final String commandName = "CustCampaignCommand";

	private static final Class clazz = CustCampaignCommand.class;

	HomeFactory homeFac = null;

	private int operatorID = 0;

	private String machineName = "";

	private ServiceContext serviceContext = null;

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		CustCampaignEJBEvent inEvent = (CustCampaignEJBEvent) ev;
		response = new CommandResponseImp(null);
		operatorID = inEvent.getOperatorID();
		machineName = inEvent.getRemoteHostAddress();
		serviceContext = initServiceContext(inEvent);
		try {
			homeFac = HomeFactory.getFactory();
			switch (inEvent.getActionType()) {
			// begin fiona
			// 取消促销
			case SystemEJBEvent.CUST_CAMAPAIGN_CANCEL:
				CustCampaignCancel(inEvent);
				break;
			// 客户产品促销维护
			case SystemEJBEvent.CUST_CAMAPAIGN_MODIFY:
				CustCampaignModify(inEvent);
				break;
			// 延迟促销
			case SystemEJBEvent.CUST_CAMAPAIGN_DELAY:
				custBundleDelay(inEvent);
				break;
			// 手工授予促销
			case SystemEJBEvent.CUST_CAMAPAIGN_MANUAL_GRANT:
				manualGrantCampaign(inEvent);
				break;
			// 套餐维护
			case SystemEJBEvent.CUST_BUNDLE_MODIFY:
				custBundleModify(inEvent);
				break;
			// 套餐取消
			case SystemEJBEvent.CUST_BUNDLE_CANCEL:
				custBundleCancel(inEvent);
				break;
			// 套餐停止
			case SystemEJBEvent.CUST_BUNDLE_STOP:
				custBundleStop(inEvent);
				break;
			// 套餐续费
			case SystemEJBEvent.CUST_BUNDLE_PREPAYMENT:
				custBundlePrePayment(inEvent);
				break;
			// 套餐转换
			case SystemEJBEvent.CUST_BUNDLE_TRANSFER:
				custBundleTransfer(inEvent);
				break;
			//协议客户续费
			case SystemEJBEvent.PROTOCOL_BUNDLE_PREPAYMENT:
			    protocolPrePayment(inEvent);
			    break;
			default:
				throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException("发生未知的错误。");
		}
		return response;
	}

	/**
	 * 取消客户促销
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void CustCampaignCancel(CustCampaignEJBEvent inEvent)
			throws ServiceException {

		CustomerCampaignDTO dto = inEvent.getCampaignDTO();
		// CustomerCampaignDTO oldDto =
		// BusinessUtility.getCustomerCampaignDTOByID(dto.getCcID());
		// 检查产品是否符合取消条件
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCusCampaign(dto.getCcID());
		// 取消客户产品促销
		CampaignService campaignService = new CampaignService(serviceContext);
		CustomerCampaign cc=campaignService.custCampaignCancel(dto);
		// 创建系统日志
		StringBuffer logDesc = new StringBuffer();
		List cancelProduct=BusinessUtility.getPsIDListByCcID(dto.getCcID());
		logDesc
				.append("客户促销维护—促销取消;客户促销ID:")
				.append(dto.getCcID())
				.append(";促销名称:")
				.append(BusinessUtility.getCampaignNameByCCId(dto.getCcID()))
				.append((cancelProduct!=null&&!cancelProduct.isEmpty()?(";相关客户产品:"+cancelProduct.toString()):"")) ;

		SystemLogRecorder.createSystemLog(machineName, operatorID, cc
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"客户促销维护", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 客户产品促销维护
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void CustCampaignModify(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		CustomerCampaignDTO dto = inEvent.getCampaignDTO();
		CustomerCampaignDTO oldDto = BusinessUtility
				.getCustomerCampaignDTOByID(dto.getCcID());
		// 检查产品是否符合修改条件
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCusCampaign(dto.getCcID());
		// 更新客户产品促销信息
		CampaignService campaignService = new CampaignService(serviceContext);
		campaignService.CustCampaignModify(dto);
		// 创建系统日志
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("客户促销维护—促销维护;客户促销ID:" + dto.getCcID() + ";促销名称:"
				+ BusinessUtility.getCampaignNameByCCId(dto.getCcID()));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许变更:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowAlter()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowAlter())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许暂停:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowPause()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowPause())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许迁移:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowTransition()), BusinessUtility
						.getCommonNameByKey("SET_G_YESNOFLAG", dto
								.getAllowTransition())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许过户:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowTransfer()), BusinessUtility
						.getCommonNameByKey("SET_G_YESNOFLAG", dto
								.getAllowTransfer())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许取消:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowClose()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowClose())));
		logDesc.append(SystemLogRecorder.appendDescString(";备注:", oldDto
				.getComments(), dto.getComments()));

		SystemLogRecorder.createSystemLog(machineName, operatorID, oldDto
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"客户促销维护", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	// 延迟客户促销产品
	private void custBundleDelay(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		// BusinessRuleService businessRuleService=new
		// BusinessRuleService(serviceContext);
		// 检查产品是否符合延迟条件
		// businessRuleService.checkCusCampaign(inEvent.getCampaignDTO().getCcID());
		// 检查是否多重产品
		// businessRuleService.checkNoExistMultiCampaign(inEvent.getCampaignDTO(),CommonConstDefinition.YESNOFLAG_NO);
		int customerCampaignID = inEvent.getCampaignDTO().getCcID();
		Timestamp endDateTo =inEvent.getCampaignDTO().getDateTo();
		String    comments  =inEvent.getCampaignDTO().getComments();

		// 延迟产品，更新记录
		CampaignService campaignService = new CampaignService(serviceContext);
		CustomerCampaign cc = campaignService.custBundleDelay(
				customerCampaignID, endDateTo,comments);

		// 创建系统日志
		// CampaignDTO
		// ccDTO=BusinessUtility.getCampaignDTOByID(cc.getCampaignID());
		SystemLogRecorder.createSystemLog(machineName, operatorID, cc.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "客户套餐维护",
				"客户套餐维护--套餐延期,客户套餐ID："
						+ cc.getCcID()
						+ ",套餐名称:"
						+ BusinessUtility.getCampaignNameByCCId(cc.getCcID()
								.intValue()) + ",延期" + TimestampUtility.TimestampToDateString(endDateTo)
						, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}



	/**
	 * 手工授予促销活动
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void manualGrantCampaign(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		
//		LogUtility.log(clazz, LogLevel.DEBUG, "operatorID>>>>>>>>>>1"+operatorID);
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		CustomerCampaignDTO customerCampaignDTO = inEvent.getCampaignDTO();
		// 检查业务帐户、帐户产品是否满足促销条件
		businessRuleService.checkGrantCampaign(customerCampaignDTO, true);
		// 创建新的纪录
		//封装受理单信息
		CustServiceInteractionDTO csiDto=encapsulateCustomerCampaignCSIInfo(0,inEvent);
		if (!inEvent.isConfirmPost()) {
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.campaignid:>>>>>" + customerCampaignDTO.getCampaignID());
			ImmediateCountFeeInfo feeInfo = FeeService.calculateManualGrantCampaignFeeInfo(csiDto, customerCampaignDTO); 
		    
			Collection immediateCountFeeCols = new ArrayList();
			immediateCountFeeCols.add(feeInfo);
			this.response.setPayload(immediateCountFeeCols);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.custBundlePrePayment.feeList:>>>>>"
							+ feeInfo);
			return;
		}
		
		CSIService csiService=new CSIService(serviceContext);
		//创建受理单对象
		csiService.createCustServiceInteraction(csiDto);
		
		CampaignService campaignService = new CampaignService(serviceContext);
		CustomerCampaign cc = campaignService
				.grantNewCampaign(customerCampaignDTO);
		//取出受理单对象
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		cc.setCsiID(csiEJB.getId().intValue());
		//取出相关客户产品
		Collection psidList=(Collection) serviceContext.get(Service.PSID);
		//创建 受理单相关的客户产品
		campaignService.createCsiCustProductInfoWithCampaign(csiEJB.getId().intValue(),cc,CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT);
	    //费用支付处理
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.inEvent.getFeeList():>>>>>" + inEvent.getFeeList());
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.inEvent.getPaymentList():>>>>>" + inEvent.getPaymentList());
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.inEvent.getCsiPrePaymentDeductionList():>>>>>" + inEvent.getCsiPrePaymentDeductionList());

		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);

		//更新受理单付费状态
		csiService.updateCSIPayStatus(serviceContext, CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		// 创建系统日志
		String logDesc = "";
		CampaignDTO ccDTO = BusinessUtility
				.getCampaignDTOByID(customerCampaignDTO.getCampaignID());
		if (customerCampaignDTO.getServiceAccountID() != 0) {
			logDesc = "授予业务帐户:" + customerCampaignDTO.getServiceAccountID();
		} else {
			logDesc = "授予帐户:" + customerCampaignDTO.getAccountID();
		}
		csiEJB.setComments(logDesc+",客户促销:"+ccDTO.getCampaignName());
		SystemLogRecorder.createSystemLog(machineName, operatorID,
				cc.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "手工授予促销活动", "手工授予促销活动,"
						+ "客户促销ID:" + cc.getCcID() + ",促销名称:"
						+ ccDTO.getCampaignName() 
						+ ",受理单ID:"+csiEJB.getId().intValue()+"," + logDesc,
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 维护客户套餐
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void custBundleModify(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		CustomerCampaignDTO dto = inEvent.getCampaignDTO();
		CustomerCampaignDTO oldDto = BusinessUtility
				.getCustomerCampaignDTOByID(dto.getCcID());
		// 检查产品是否符合修改条件
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCusCampaign(dto.getCcID());
		// 更新客户产品促销信息
		CampaignService campaignService = new CampaignService(serviceContext);
		campaignService.CustCampaignModify(dto);
		// 创建系统日志
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("客户套餐维护—套餐维护;客户套餐ID:" + dto.getCcID() + ";套餐名称:"
				+ BusinessUtility.getCampaignNameByCCId(dto.getCcID()));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许变更:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowAlter()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowAlter())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许暂停:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowPause()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowPause())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许迁移:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowTransition()), BusinessUtility
						.getCommonNameByKey("SET_G_YESNOFLAG", dto
								.getAllowTransition())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许过户:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowTransfer()), BusinessUtility
						.getCommonNameByKey("SET_G_YESNOFLAG", dto
								.getAllowTransfer())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否允许取消:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAllowClose()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAllowClose())));
		logDesc.append(SystemLogRecorder.appendDescString(";是否自动延伸:",
				BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldDto
						.getAutoExtendFlag()), BusinessUtility.getCommonNameByKey(
						"SET_G_YESNOFLAG", dto.getAutoExtendFlag())));
		logDesc.append(SystemLogRecorder.appendDescString(";备注:", oldDto
				.getComments(), dto.getComments()));

		SystemLogRecorder.createSystemLog(machineName, operatorID, oldDto
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"客户套餐维护", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 套餐取消
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void custBundleCancel(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.CustBundleCancel:>>>>>");

		int ccid=inEvent.getCampaignDTO().getCcID();
		CustomerCampaignDTO dto = BusinessUtility.getCustomerCampaignDTOByID(ccid);
		serviceContext.put(Service.CUSTOMER_ID, new Integer(dto.getCustomerID()));
		serviceContext.put(Service.ACCOUNT_ID, new Integer(dto.getAccountID()));
		// 检查套餐是否符合取消条件
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		//进行检查,看能否取消套餐.
		Map saMap = businessRuleService.checkCustomerBundleCancel(ccid);
		CustServiceInteractionDTO csiDto=encapsulateCustomerCampaignCSIInfo(ccid,inEvent);
		if (!inEvent.isConfirmPost()) {
			Collection shouldPayFeeList=new ArrayList();

			AccountItemDTO deviceFee=FeeService.encapsulateDeviceReturnFee(inEvent.getDeviceFee(), dto.getCustomerID(), dto.getAccountID(), 0);
			ArrayList deviceFeeList=new ArrayList();
			if(deviceFee!=null){
				deviceFeeList.add(deviceFee);
			}
			shouldPayFeeList=FeeService.customerBundleCancelImmediateFeeCalculator(csiDto,ccid,saMap,deviceFeeList);

			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.CustBundleCancel.shouldPayFeeList:>>>>>"
							+ shouldPayFeeList.size());
			return;
		}
		boolean isReturnDevice=false;
		if(inEvent.getIsReturnDevice()!=null&&CommonConstDefinition.YESNOFLAG_YES.equals(inEvent.getIsReturnDevice())){
			isReturnDevice=true;
		}
		//创建受理单
		CSIService csiService=new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDto);
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		
		ArrayList csiPsidList=new ArrayList();
		//取消客户产品
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		//进行依赖检查,以业务帐户为单位, 
		java.util.Iterator sait=saMap.keySet().iterator();
		StringBuffer saLogInfo=new StringBuffer();
		while(sait.hasNext()){
			Integer said=(Integer) sait.next();
			ArrayList cpList=(ArrayList) saMap.get(said);
			ArrayList psidList=new ArrayList();
			StringBuffer psidLogInfo=new StringBuffer();
			for(int i=0;i<cpList.size();i++){
				int psid=((CustomerProductDTO)cpList.get(i)).getPsID();
				psidList.add(new Integer(psid));
				psidLogInfo.append(psid).append(",");
			}
			csiPsidList.addAll(psidList);
    	    ArrayList saCpList = BusinessUtility.getCustProdDTOListBySaAndPsID(
					said.intValue(), 0, "");
			// 如果当前业务帐户下套餐产品信息等于业务帐户有效产品集合,调用取消业务帐户的方法,否则调用取消客户产品的方法,
			if (saCpList.size() == psidList.size()) {
				saLogInfo.append(",业务帐户ID:").append(said.intValue()).append(
						",该业务帐户被取消,业务帐户下被取消的客户产品ID(").append(psidLogInfo)
						.append(")");
				LogUtility.log(clazz, LogLevel.DEBUG,
						"custBundleCancel.取消业务帐户:" + +said.intValue());
				
				if(CommonConstDefinition.YESNOFLAG_YES.equalsIgnoreCase(inEvent.getIsReturnDevice()))
					isReturnDevice=true;
				saService.close(said.intValue(),isReturnDevice);
			} else {
				saLogInfo.append(",业务帐户ID:").append(said.intValue()).append(
						",该业务帐户被保留,业务帐户下被取消的客户产品ID(").append(psidLogInfo)
						.append(")");
				LogUtility.log(clazz, LogLevel.DEBUG,
						"custBundleCancel.取消客户产品:" + psidList);
				CustomerProductService cpService = new CustomerProductService(
						serviceContext, dto.getCustomerID(),
						dto.getAccountID(), said.intValue());
				cpService.cancelCustomerProduct(psidList, isReturnDevice);
			}
		}
		//计算信息费
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		
		/*注释掉 by david.Yang ,计算费用的时候没有该费用。这里也不应该有
		Collection infoFeeList=InfoFeeImmediateBilling
		.callImmediateBillingCampaign(
				ccid+"",
				CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_TRANSFE,
				csiDto.getCreateOperatorId(), csiDto.getCreateORGId(),batchNoDTO);
		LogUtility.log(clazz,LogLevel.DEBUG,"infoFeeList.size>>>"+infoFeeList.size());
		*/
		
		Collection infoFeeList=new ArrayList();
		
		// 取消客户产品套餐
		CampaignService campaignService = new CampaignService(serviceContext);
		CustomerCampaign cc=campaignService.custBundleCancel(ccid);
		//创建受理单产品信息
		csiService.createCsiCustProductInfo(csiEJB,csiPsidList);
//		campaignService.createCsiCustProductInfoWithCampaign(csiEJB.getId().intValue(),cc,CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_CANCEL);
		//处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(),FeeService.reencapsulateFeeInfo(csiDto,infoFeeList,operatorID),null,commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext, csiDto.getPaymentStatus());

		// 创建系统日志
		SystemLogRecorder.createSystemLog(machineName, operatorID, dto
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"客户套餐取消", "客户套餐取消;"+
				"受理单ID:" + csiDto.getId()
				+"客户套餐ID:" + dto.getCcID() + ";套餐名称:"
						+ BusinessUtility.getCampaignNameByCCId(dto.getCcID())
						+ saLogInfo.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
		
		//返回受理单ID,用于打印
		this.response.setPayload(new Integer(csiDto.getId()));
	}

	/**
	 * 套餐终止
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void custBundleStop(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		CustomerCampaignDTO dto = inEvent.getCampaignDTO();
		CampaignService campaignService = new CampaignService(serviceContext);
		campaignService.close();

		SystemLogRecorder.createSystemLog(machineName, operatorID, dto
				.getCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV,
				"客户套餐终止", "客户套餐终止—套餐终止;客户套餐ID:" + dto.getCcID() + ";套餐名称:"
						+ BusinessUtility.getCampaignNameByCCId(dto.getCcID())
						+ "相关客户产品:"
						+ BusinessUtility.getPsIDListByCcID(dto.getCcID()),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 套餐续费
	 * 
	 * @param ev
	 * @throws ServiceException
	 */
	private void custBundlePrePayment(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment:>>>>>");
		int ccid = inEvent.getCampaignDTO().getCcID();
		String rfBillingCycleFlag = inEvent.getRfBillingCycleFlag();
		CustomerCampaignDTO custCampaignDto = BusinessUtility
				.getCustomerCampaignDTOByID(ccid);

		// 检查套餐是否符合取消条件
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCustomerCampaignPrePayment(ccid);
		CustServiceInteractionDTO csiDto=encapsulateCustomerCampaignCSIInfo(ccid,inEvent);
		if (!inEvent.isConfirmPost()) {
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.ccid:>>>>>" + ccid + "\nrfBillingCycleFlag:>>>>"
							+ rfBillingCycleFlag);

			Collection feeList = InfoFeeImmediateBilling
					.callCustomerCampaignResume(ccid, rfBillingCycleFlag, operatorID, BusinessUtility
							.getOpOrgIDByOperatorID(operatorID)); 
			
			ImmediateCountFeeInfo  immediateCountFeeInfo=ImmediateCountFeeService.countFee(csiDto.getAccountID(),csiDto.getType(),null,null,feeList);
		    
			Collection immediateCountFeeCols = new ArrayList();
			immediateCountFeeCols.add(immediateCountFeeInfo);
			this.response.setPayload(immediateCountFeeCols);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.custBundlePrePayment.feeList:>>>>>"
							+ feeList);
			return;
		}
		CSIService csiService=new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDto);
		// 套餐续费
		CampaignService campaignService = new CampaignService(serviceContext);
		BundlePaymentMethodDTO paymentMethodDto = BusinessUtility
		.getBundlePaymentMethodDTO(custCampaignDto.getCampaignID(), rfBillingCycleFlag);
		CustomerCampaign cc=campaignService.custBundlePrePayment(ccid, paymentMethodDto);

		// 持久化费用、支付、预存抵扣
		Collection acctItemList =new ArrayList();
		Collection feeList = inEvent.getFeeList();
		Iterator   feeIter =feeList.iterator();
		if (feeIter.hasNext()){
			ImmediateCountFeeInfo imm = (ImmediateCountFeeInfo)feeIter.next();
			acctItemList =imm.getAcctItemList();
		}
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.acctItemList:>>>>>"
						, acctItemList);
		
	    ImmediatePayFeeService feeService = new ImmediatePayFeeService(
		FeeService.reencapsulateFeeInfo(csiDto,cc,custCampaignDto,rfBillingCycleFlag, acctItemList), 
				        FeeService.reencapsulatePaymentInfo(csiDto, inEvent.getPaymentList(), operatorID), 
						FeeService.reecapsulatePrePaymentInfo(csiDto,inEvent.getCsiPrePaymentDeductionList(),null,operatorID),
				        null);
		FeeService.createFeeAndPaymentAndPreDuciton(feeService.getFeeList(),
				feeService.getPaymentList(), feeService.getPrePaymentList(),null);
		
		boolean mustPay = FeeService.mustCreateFinancesetOffMap(csiDto.getType(), false,
				feeService.getFeeList(), feeService.getPaymentList(),
				feeService.getPrePaymentList());
		if(mustPay)csiDto.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		feeService.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
		feeService.setId(csiDto.getId());
		PayFeeUtil.updatePrepaymentList(feeService.getPaymentList());
		PayFeeUtil.updatePrepaymentDuctionList(feeService.getPrePaymentList());		
		feeService.payFeeDB(feeService.payFee());
		//更新受理单付费状态
		csiService.updateCSIPayStatus(serviceContext, csiDto.getPaymentStatus());
		
		//2008-01-24 用于套餐续费的收费单据打印（数据配置的打印），需要查询条件受理单id。
		this.response.setPayload(new Integer(csiDto.getId()));
		
		// 创建系统日志
		SystemLogRecorder.createSystemLog(machineName, operatorID,
				cc.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "套餐续费", "套餐续费;"
				+ "受理单ID:" + csiDto.getId()
						+";客户套餐ID:"
						+ ccid
						+ ";套餐名称:"
						+ BusinessUtility.getCampaignNameByCCId(ccid)
						+ ";续费时间长度:"
						+ paymentMethodDto.getTimeUnitLengthNumber()
						+ BusinessUtility.getCommonNameByKey("SET_M_CAMPAIGNTIMELENGTHUNITTYPE",paymentMethodDto.getTimeUnitLengthType())
						+ ";NID日期:" 
						+ TimestampUtility.TimestampToDateString(custCampaignDto.getNbrDate())
						+ "-->" 
						+ TimestampUtility.TimestampToDateString(cc.getNbrDate())
						+ ";续费后协议有效期间:"
						+ TimestampUtility.TimestampToDateString(cc.getDateFrom()) 
						+ "-->" 
						+ TimestampUtility.TimestampToDateString(cc.getDateTo()),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	
	/*
	 * 协议价客户续费
	 */
	private void protocolPrePayment(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.protocolPrePayment:>>>>>");
		if(inEvent.getCsiPackageArray()==null)
 			inEvent.setCsiPackageArray(new ArrayList());
    	 inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));

		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkCustProtocolPackage(inEvent.getCsiDto().getCustomerID(),inEvent.getCsiPackageArray());
		System.out.println("inEvent.getCsiDto().getAccountID()------>"+inEvent.getCsiDto().getAccountID());
		if (inEvent.getCsiDto().getAccountID() ==0)	return;

		if (!inEvent.isConfirmPost()) {
			String saIdStr =inEvent.getSaId_indexs();
						
			Collection protocolList =BusinessUtility.CaculateFeeForProtocol(saIdStr,inEvent.getDateLength(),inEvent.getCsiPackageArray());
			ImmediateCountFeeInfo immediateCountFeeInfo= FeeService.encapsulateFeeInfoAndAccountInfo(inEvent.getCsiDto().getAccountID() ,inEvent.getCsiDto().getType(),null,null);
			immediateCountFeeInfo.setAccountid(inEvent.getCsiDto().getAccountID());
			immediateCountFeeInfo.setAcctItemList(protocolList);
			if(protocolList!=null && !protocolList.isEmpty()){
				Iterator protocolIter=protocolList.iterator();
				long count=0l;
				while(protocolIter.hasNext()){
					AccountItemDTO accountItemDTO=(AccountItemDTO)protocolIter.next();
					count=count+ImmediatePayFeeService.double2long(accountItemDTO.getValue());
				}
				immediateCountFeeInfo.setTotalValueAccountItem((double)count/100);
			}
			Collection currentFeeList =new ArrayList();
			currentFeeList.add(immediateCountFeeInfo);
			this.response.setPayload(currentFeeList);
		}else{
			CSIService csiService=new CSIService(serviceContext);	
			csiService.createCustServiceInteraction(inEvent.getCsiDto());
		    //修改客户产品的结束时间
		    if(inEvent.getSaIDList()!=null &&!inEvent.getSaIDList().isEmpty()){
		    	Iterator saIDIter=inEvent.getSaIDList().iterator();
		    	while(saIDIter.hasNext()){
		    		CustomerProductService cpService = new CustomerProductService(serviceContext);
				    cpService.changCustProdEndTimeByExtend(inEvent.getCsiPackageArray(),((Integer)saIDIter.next()).intValue(),inEvent.getDateLength(),inEvent.getCsiDto().getId());
		    	}
		    }
		   
			FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,null,new CommonFeeAndPayObject(),inEvent.getOperatorID());
			csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
			
			this.response.setPayload(new Integer(inEvent.getCsiDto().getId()));
			// 创建系统日志
			SystemLogRecorder.createSystemLog(machineName, operatorID,
					inEvent.getCsiDto().getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "协议续费", "协议续费;"
					+ "受理单ID:" + inEvent.getCsiDto().getId()
							+";受理业务账户:"
							+ inEvent.getSaId_indexs()
							+ ";产品包名称:"
							+ inEvent.getCsiPackageArray()
							+ ";续费时间长度:"
							+ inEvent.getDateLength()
							,
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
			
		}
		
		
	}
	
	private void custBundleTransfer(CustCampaignEJBEvent inEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment:>>>>>");
		int ccid = inEvent.getCcid();
		int newCampaignID=inEvent.getNewCampaignID();
		Map addProductMap=inEvent.getAddProductMap();
		ArrayList cancelCpList=inEvent.getCancelProductList();
		Map productPropertyMap=inEvent.getProductPropertyMap();
		HashMap terminalMap=inEvent.getTerminalMap();
		String phoneNo=inEvent.getPhoneNo();
		int itemID=inEvent.getItemID();
		CustomerCampaignDTO custCampaignDto = BusinessUtility
				.getCustomerCampaignDTOByID(ccid);
		
		java.sql.Timestamp dateFrom = custCampaignDto.getDateFrom();
		java.sql.Timestamp dateTo   = custCampaignDto.getDateTo();
		java.sql.Timestamp nbrDate  = custCampaignDto.getNbrDate();
		LogUtility.log(clazz, LogLevel.DEBUG,
		"CustCampaignCommand.custBundlePrePayment.ccid>>>>>"+ccid);
		LogUtility.log(clazz, LogLevel.DEBUG,
		"CustCampaignCommand.custBundlePrePayment.newCampaignID>>>>>"+newCampaignID);
		LogUtility.log(clazz, LogLevel.DEBUG,
		"CustCampaignCommand.custBundlePrePayment.addProductMap>>>>>"+addProductMap);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.cancelCpList>>>>>"+cancelCpList);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.productPropertyMap>>>>>"+productPropertyMap);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.terminalList>>>>>"+terminalMap);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"CustCampaignCommand.custBundlePrePayment.phoneNo>>>>>"+phoneNo+">>>>itemID"+itemID);
		
		// 检查套餐是否符合取消条件
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		businessRuleService.checkBundleTransfer(inEvent.getCsiDto(),custCampaignDto,newCampaignID,addProductMap,cancelCpList,terminalMap);
		//封装受理单数据.
		CustServiceInteractionDTO csiDto = encapsulateCustomerCampaignCSIInfo(
				ccid, inEvent);
		//计费
		if (!inEvent.isConfirmPost()) {
			/*  暂时取消计费。因丽江的主机包转副机包，不收钱，只是每个月的信息费用不同而已 modify by david.Yang
			CustomerDTO custDto=BusinessUtility.getCustomerDTOByCustomerID(custCampaignDto.getCustomerID());
			AccountDTO acctDto=BusinessUtility.getAcctDTOByAcctID(custCampaignDto.getAccountID());
			int oldPackageID=BusinessUtility.getProductPackageIDWithCampaignID(custCampaignDto.getCampaignID());
			int newPackageID=BusinessUtility.getProductPackageIDWithCampaignID(newCampaignID);
			ImmediateCountFeeInfo feeInfo=FeeService.boundleTrans(csiDto,custDto.getCustomerType(),acctDto.getAccountType(),ccid,newCampaignID,oldPackageID,newPackageID,addProductMap.keySet(),operatorID);
			AccountItemDTO deviceFee=FeeService.encapsulateDeviceReturnFee(inEvent.getDeviceFee(), custCampaignDto.getCustomerID(), custCampaignDto.getAccountID(), 0);
			if(deviceFee!=null){
				feeInfo.getAcctItemList().add(deviceFee);
				feeInfo.setTotalValueAccountItem(FeeService.countFee(feeInfo.getAcctItemList()));
			}
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CustCampaignCommand.custBundlePrePayment.feeInfo>>>>>",feeInfo);
			ArrayList feeList=new ArrayList();
			feeList.add(feeInfo);
			this.response.setPayload(feeList);
			*/
			return ;
		}
		boolean isReturnDevice=false;
		if(inEvent.getIsReturnDevice()!=null&&CommonConstDefinition.YESNOFLAG_YES.equals(inEvent.getIsReturnDevice())){
			isReturnDevice=true;
		}
		CSIService csiService=new CSIService(serviceContext);
		csiService.createCustServiceInteraction(csiDto);
		ArrayList campaignList=new ArrayList();
		campaignList.add(new Integer(newCampaignID));
		csiService.createCsiCustProductInfo( addProductMap.values(), null, campaignList, csiDto);
		CampaignService campaignService=new CampaignService(serviceContext);
		CustomerCampaign cc=campaignService.custBundleTransfer(custCampaignDto, newCampaignID,cancelCpList,addProductMap,productPropertyMap,phoneNo,itemID,isReturnDevice);
		cc.setDateFrom(dateFrom);
		cc.setDateTo(dateTo);
		cc.setNbrDate(nbrDate);
		//处理费用.
		LogUtility.log(clazz, LogLevel.DEBUG,
				"inEvent.getFeeList()>>>>>",inEvent.getFeeList());		
		LogUtility.log(clazz, LogLevel.DEBUG,
				"inEvent.getPaymentList()>>>>>",inEvent.getPaymentList());		
		FeeService.payFeeOperationForOpen(csiDto,inEvent.getFeeList(),inEvent.getPaymentList(),inEvent.getCsiPrePaymentDeductionList(),new CommonFeeAndPayObject(),inEvent.getOperatorID());
		
		csiService.updateCSIPayStatus(serviceContext, csiDto.getPaymentStatus());
		// 创建系统日志
		ArrayList newSaList=serviceContext.get(Service.NEWSERVICEACCOUNTIDLIST)!=null?(ArrayList)serviceContext.get(Service.NEWSERVICEACCOUNTIDLIST):null;
		ArrayList cancelSaList=serviceContext.get(Service.CANCELSERVICEACCOUNTIDLIST)!=null?(ArrayList)serviceContext.get(Service.CANCELSERVICEACCOUNTIDLIST):null;
		ArrayList addProductCol = new ArrayList();
		addProductCol.add(addProductMap.keySet());
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("套餐转换;");
		logDesc.append("受理单ID:");
		logDesc.append(csiDto.getId());
		logDesc.append(";旧的客户套餐ID:");
		logDesc.append(ccid);
		logDesc.append(";新的客户套餐ID:");
		logDesc.append(cc.getCcID());
		logDesc.append(SystemLogRecorder.appendDescString(";套餐:",
				BusinessUtility.getCampaignNameByCCId(ccid), BusinessUtility
						.getCampaignDTOByID(newCampaignID).getCampaignName()));
		if (addProductMap != null && !addProductMap.isEmpty()) {
			logDesc.append(";新增加的产品:");
			logDesc.append(BusinessUtility.getProductDesByProductID(addProductCol));
		}
		if (cancelCpList != null && !cancelCpList.isEmpty()) {
			logDesc.append(";取消的客户产品:");
			logDesc.append(BusinessUtility
							.getProductDescListByPSIDList(cancelCpList));
		}
		if(newSaList!=null&&!newSaList.isEmpty()){
			logDesc.append("新增加的业务帐户:");
			logDesc.append(newSaList.toString());
		}
		if(cancelSaList!=null&&!cancelSaList.isEmpty()){
			logDesc.append("删除的业务帐户:");
			logDesc.append(cancelSaList.toString());
		}
		SystemLogRecorder.createSystemLog(machineName, operatorID,
				custCampaignDto.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "套餐转换", logDesc
						.toString(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private ServiceContext initServiceContext(CustCampaignEJBEvent event) {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(event.getOperatorID()));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS, event
				.getRemoteHostAddress());
		return serviceContext;
	}
//	private void updateCSIStatus(CustServiceInteractionDTO csiDto)
//			throws ServiceException {
////		boolean mustPay = mustCreateFinancesetOffMap(csiDto.getType(), false,
////				feeService.getFeeList(), feeService.getPaymentList(),
////				feeService.getPrePaymentList());
//		CSIService csiService = new CSIService(serviceContext);
//		csiService
//				.updateCSIPayStatus(serviceContext, csiDto.getPaymentStatus());
//	}
	private CustServiceInteractionDTO encapsulateCustomerCampaignCSIInfo(
			int ccid, CustCampaignEJBEvent inEvent) {
		CustServiceInteractionDTO csiDto = inEvent.getCsiDto();
		if(inEvent.getCsiDto()==null){
			csiDto = new CustServiceInteractionDTO();
		}
		
		CustomerCampaignDTO ccDto = null;
		if(ccid!=0){
			ccDto=BusinessUtility
			.getCustomerCampaignDTOByID(ccid);
		}else{
			ccDto=inEvent.getCampaignDTO();
		}
		csiDto.setCreateOperatorId(operatorID);
		csiDto.setCreateORGId(BusinessUtility
						.getOpOrgIDByOperatorID(operatorID));
		csiDto.setAccountID(ccDto.getAccountID());
		csiDto.setCustomerID(ccDto.getCustomerID());
//		csiDto
//		.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		csiDto.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
		csiDto.setComments(ccid+"");
		int actionType=inEvent.getActionType();
		switch (actionType) {
			// 套餐延期
		case SystemEJBEvent.CUST_BUNDLE_DELAY:
			csiDto
					.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDE);
			break;
			//套餐转换
		case SystemEJBEvent.CUST_BUNDLE_TRANSFER:
			csiDto
					.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDS);
			break;
			//套餐续费
		case SystemEJBEvent.CUST_BUNDLE_PREPAYMENT:
			csiDto
					.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDP);
			break;
			//套餐取消
		case SystemEJBEvent.CUST_BUNDLE_CANCEL:
			csiDto
					.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDC);
			break;
			//手工授予促销
		case SystemEJBEvent.CUST_CAMAPAIGN_MANUAL_GRANT:
			csiDto
			.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_MAC);
			break;
		default:
			return null;
		}
		return csiDto;
	}
}
