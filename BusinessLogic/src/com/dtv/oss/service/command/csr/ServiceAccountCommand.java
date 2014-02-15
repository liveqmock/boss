package com.dtv.oss.service.command.csr;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustServiceInteractionHome;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.domain.ServiceAccount;
import com.dtv.oss.domain.ServiceAccountHome;
import com.dtv.oss.domain.TerminalDevice;
import com.dtv.oss.domain.TerminalDeviceHome;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.dto.custom.BatchNoDTO;
import com.dtv.oss.dto.custom.CommonBusinessParamDTO;
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
import com.dtv.oss.service.component.AccountService;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.CustomerProductService;
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.CampaignService;
import com.dtv.oss.service.component.ConcreteJobCardService;
import com.dtv.oss.service.component.CustomerService;
import com.dtv.oss.service.component.FeeService;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.component.ServiceAccountService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.OpenAccountGeneralEJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

/**
 * 业务帐户相关的操作
 * author     ：zhouxushun
 * date       : 2005-11-14
 * description:
 * @author 250713z
 *
 */
public class ServiceAccountCommand extends Command {
	private static final Class clazz = ServiceAccountCommand.class;
	private int operatorID = 0;
	private String machineName = "";
	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    ServiceAccountEJBEvent inEvent = (ServiceAccountEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();

	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");

	    try {
	    	switch (inEvent.getActionType()) {
	    		//检查客户产品和优惠信息
	    		case EJBEvent.CHECK_PRODUCTPG_CAMPAINPG:
	    			checkServiceAccountParam(inEvent);
	    			break;
	    		case EJBEvent.CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG:
	    			checkServiceAccountParam(inEvent);
	    			break;
	    	    //检查硬件产品信息
	    		case EJBEvent.CHECK_HARDPRODUCTINFO:
	    			checkServiceAccountParam(inEvent);
	    			break;
	    		//检查费用信息
	    		case EJBEvent.CHECK_FREEINFO:
	    			checkServiceAccountParam(inEvent);
	    			break;
                 // 新增业务帐户 --预约增机的预约提交	
	    		case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING:
	    			createSubscriberForBooking(inEvent);
	    			break;	
				//新增业务帐户 --预约增机的预约修改	
	    		case EJBEvent.CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER:
	    			subscriberForBookingUpdate(inEvent);
	    			break;
	    		
	    		//新增业务帐户 --预约增机的预约取消	
	    		case EJBEvent.CUSTOMER_BOOKINGUSER_CANCEL_SUBSCRIBER:
	    			subscribeForBookingCancel(inEvent);
	    			break;
	    			
	    		//新增业务帐户 |门店增机
	    		case EJBEvent.CUSTOMER_ADD_SUBSCRIBER:
	    			addServiceAccount(inEvent);
	    			break;
	    		case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER:
	    			addServiceAccount(inEvent);
	    			break;
	    		//暂停业务帐户
	    		case EJBEvent.SUSPEND:
	    			pauseServiceAccount(inEvent);
	    			break;
	    		//设备转租
	    		case EJBEvent.RENT:
	    			rentServiceAccount(inEvent);
	    			break;
	    		//销业务帐户
	    		case EJBEvent.CLOSE:
	    			closeServiceAccount(inEvent);
	    			break;
	    		//预退业务帐户
	    		case EJBEvent.BEFOREHAND_CLOSE:
	    			beforehandCloseServiceAccount(inEvent);
	    			break;
	    		//实退业务帐户
	    		case EJBEvent.REAL_CLOSE:
	    			realCloseServiceAccount(inEvent);
	    			break;
	    		//恢复业务帐户
	    		case EJBEvent.RESUME:
	    			resumeServiceAccount(inEvent);
	    			break;
	    		//客户产品重授权
	    		case EJBEvent.REAUTHORIZEPRODUCT:
	    			resendEMMForCustProduct(inEvent);
	    			break;
	    		//业务账户重授权
	    		case EJBEvent.RESEND_EMM_BY_SERVICE_ACCOUNT:
	    			resendEMMForServiceAccount(inEvent);
	    			break;	
	    		//业务帐户过户
	    		case EJBEvent.TRANSFER:
	    			if(CommonConstDefinition.SA_TRANFER_TYPE_I.equals(inEvent.getTranferSAType()))
	    				transferServiceAccount(inEvent);
	    			else if(CommonConstDefinition.SA_TRANFER_TYPE_O.equals(inEvent.getTranferSAType()))
	    				transferServiceAccountNotExistUser(inEvent);
	    			else
	    				throw new ServiceException("参数错误：未知业务帐户过户类型");
	    			break;
	    		//机卡匹配
	    		case EJBEvent.PARTNERSHIP:
	    			matchSCtoSTB(inEvent);
	    			break;
	    		//机卡解配对
	    		case EJBEvent.CANCEL_PARTNERSHIP:
	    			matchSCtoSTB(inEvent);
	    			break;
				//清除密码
				case EJBEvent.CLEARPASSWORD:
					matchSCtoSTB(inEvent);
					break;
				//发送消息
				case EJBEvent.SEND_EMAIL_CA:
					matchSCtoSTB(inEvent);
					break;
					//服务号码修改
				case EJBEvent.SERVICE_ACCOUNT_CODE_UPDATE:
					updateServiceAccountCode(inEvent);
					SystemEventDTO sysDto=inEvent.getSysEventDto();
					sysDto.setOperatorID(operatorID);
					sysDto.setEventClass(SystemEventRecorder.SERVICE_CODE_UPDATE);
					addSystemEvent(sysDto);
					break;
	    			//重复购买选择产品时的异步检查,只检查一组产品
	    		case EJBEvent.CHECK_BUY_PRODUCT:
	    			checkBuyProduct(inEvent);
	    			break;
	    			//重复购买检查,检查选择的全部信息,客户/帐户/产品/促销/硬件等
	    		case EJBEvent.CHECK_BATCHBUY_PRODUCTINFO:
	    			checkServiceAccountBatchBuyParam(inEvent);
	    			break;
	    			//重复购买门店开启提交.
	    		case EJBEvent.CUSTOMER_BATCHBUY_ADD_SUBSCRIBER:
	    			batchAddServiceAccount(inEvent);
	    			break;
	    		//批量暂停业务帐户
	    	    case EJBEvent.BATCH_SUSPEND:
	    	    	batchPauseServiceAccount(inEvent);
	    	    	break;
	    	    //批量恢复业务帐户
	    		case EJBEvent.BATCH_RESUME:
	    			batchResumeServiceAccount(inEvent);
	    			break;	
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    }
	    catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }
	    catch(Throwable unkown) {
	    	unkown.printStackTrace();
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}
	/**
	 * 重复购买时的异步检查用.只检查一组产品/促销
	 * @param event
	 * @throws ServiceException
	 */
	private void checkBuyProduct(ServiceAccountEJBEvent event)
			throws ServiceException {

			LogUtility.log(clazz, LogLevel.DEBUG, "开始购买检查");
			CustomerDTO customerDto = event.getCustomerDTO();
			CustServiceInteractionDTO csiDto = event.getCsiDto();
			AddressDTO addrDto = event.getAddressDTO();
			BusinessRuleService brService = new BusinessRuleService(
					new ServiceContext());
			CommonBusinessParamDTO paramDTO = new CommonBusinessParamDTO(
					customerDto.getCustomerType(), customerDto
							.getOpenSourceType(), csiDto.getType(), event
							.getOperatorID());
			brService.checkBatchBuyProductInfo(event.getBuyProductList(),
					csiDto, paramDTO, addrDto);

		LogUtility.log(clazz, LogLevel.DEBUG, "结束购买检查");
	}
	/**
	 * 重复购买有效性检查,检查客户/帐户/产品/促销/硬件等全部信息,返回费用
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void checkServiceAccountBatchBuyParam(ServiceAccountEJBEvent inEvent)throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		ServiceContext serviceContext=initServiceContext(inEvent);

	    //检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkBatchCreateServiceAccount(inEvent);
		//费用列表
		this.response.setPayload(calculateImmediateFeeWithBatchBuy(inEvent));
		this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
		LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■重复购买计算的费用.■■",response.getPayload());
	}
	/**
	 * 门店增机,重复购买支持,
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void batchAddServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int customerID = inEvent.getCustomerID();
		boolean doPost = inEvent.isDoPost();

		//进行参数处理
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }
	    if(inEvent.getCustAddrDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(inEvent.getCustAddrDTO().getDistrictID()));

	    //检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkBatchCreateServiceAccount(inEvent);

	    //创建受理单和相关对象
	    String logOpeatorDesc="";
		CSIService csiService=new CSIService(serviceContext);
		logOpeatorDesc="门店增机";
	    csiService.createServiceAccountCsiInfoWithBatchBuy(inEvent);
		

		//创建业务账户
		ServiceAccountService saService=new ServiceAccountService(serviceContext,BusinessUtility.getAllProductDepentDefineList());
		saService.setUsedMonth(inEvent.getUsedMonth());
		//处理重复购买.
		ArrayList buyProductList=inEvent.getBuyProductList();
		
		String saIdList="";
		for (Iterator buyIt = buyProductList.iterator(); buyIt.hasNext();) {
			Map buyInfo = (Map) buyIt.next();
			Integer buyNum = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYNUM);
			Integer buyIndex = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYINDEX);
			ArrayList buyPackageList = (ArrayList) buyInfo
					.get(OpenAccountGeneralEJBEvent.KEY_MPACKAGE);
			ArrayList buyCampaignList = (ArrayList) buyInfo
					.get(OpenAccountGeneralEJBEvent.KEY_CAMPAIGN);
			Map buyDeviceMap = (Map) buyInfo
			.get(OpenAccountGeneralEJBEvent.KEY_DEVICES);
			Map phonesMap=(Map) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_PHONE);
			Map propertysMap=(Map) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_PROPERTYS);
			
			//这个循环，可以放到accountservice.create中去，
			for (int i = 1; i <= buyNum.intValue(); i++) {
				Integer buySheaf = new Integer(i);
				Map curDeviceMap = buyDeviceMap != null
						&& buyDeviceMap.containsKey(buySheaf) ? (Map) buyDeviceMap
						.get(buySheaf)
						: null;
				Map curPropertyMap = propertysMap != null
						&& propertysMap.containsKey(buySheaf) ? (Map) propertysMap
						.get(buySheaf)
						: null;
				Map curPhoneMap = phonesMap != null
						&& phonesMap.containsKey(buySheaf) ? (Map) phonesMap
						.get(buySheaf) : null;
				String phoneNo = curPhoneMap != null && !curPhoneMap.isEmpty() ? (String) curPhoneMap
						.keySet().iterator().next()
						: "";
				Integer itemId = curPhoneMap != null && !curPhoneMap.isEmpty() ? (Integer) curPhoneMap
						.values().iterator().next()
						: new Integer(0);
						if(itemId.intValue()==0){
							itemId=new Integer(BusinessUtility.getResoucePhoneIDByPhoneNo(phoneNo));
						}
						saIdList=saIdList+","+saService.batchCreate(buyPackageList, buyCampaignList, phoneNo,
						itemId.intValue(), curPropertyMap, buyIndex.intValue(),buySheaf.intValue());
			}
		}
		

	    //费用支付处理
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();

		//创建系统日志记录
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, logOpeatorDesc,logOpeatorDesc+"，受理单ID:"+csiID+";业务帐户ID ："+saIdList,
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

		//创建工单，设置业务对象之间关系在CSIService里完成

		//返回业务帐户ID
		//int saID=0;
		//if(serviceContext.get(Service.SERVICE_ACCOUNT_ID)!=null)
		//	saID=((Integer)serviceContext.get(Service.SERVICE_ACCOUNT_ID)).intValue();
		Map returnMap = new HashMap();
		returnMap.put("CustServiceInteractionID",((CustServiceInteraction)serviceContext.get(Service.CSI)).getId());
	    //returnMap.put("saID",new Integer(saID));
		returnMap.put("saID",saIdList);
 
		this.response.setPayload(returnMap);
	}
	private Collection calculateImmediateFeeWithBatchBuy(ServiceAccountEJBEvent event)throws ServiceException{
		//如果是协议价客户走协议价计费  add by david.Yang begin
		ArrayList buyProductList=event.getBuyProductList();
		Map buyInfo =new HashMap();		
		for (Iterator buyIt = buyProductList.iterator(); buyIt.hasNext();) {
			buyInfo = (Map) buyIt.next();
		}
		ArrayList buyPackageList = (ArrayList) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_MPACKAGE);
		Integer buyNum = (Integer) buyInfo.get(OpenAccountGeneralEJBEvent.KEY_BUYNUM);
		Collection protocolList =BusinessUtility.CaculateFeeForProtocol(buyNum.intValue(),buyPackageList,event.getUsedMonth(),event.getCustomerID());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(event.getCsiDto(),event.getCustomerID(),event.getAccountID());
		Map protocolMap =BusinessUtility.getProtocolDTOColByCustID(event.getCustomerID());
		if (protocolMap.isEmpty()){
		   //押金
		   commonObj.setDeposit(event.getForcedDeposit());
		   Collection currentFeeList=FeeService.calculateImmediateFeeWithBatchBuy(event.getCsiDto(),event.getBuyProductList(),commonObj);
		   return currentFeeList;
		}else{
			ImmediateCountFeeInfo immediateCountFeeInfo= FeeService.encapsulateFeeInfoAndAccountInfo(event.getCsiDto().getAccountID() ,event.getCsiDto().getType(),null,null);
			immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
			immediateCountFeeInfo.setAccountid(commonObj.getAccountID());
			immediateCountFeeInfo.setAcctItemList(protocolList);
			Collection currentFeeList =new ArrayList();
			currentFeeList.add(immediateCountFeeInfo);
			return currentFeeList;
		}
		//如果是协议价客户走协议价计费  add by david.Yang end	
	}
	/**
	 * 系统事件记录,服务号码变更用到,
	 * @param dto
	 * @throws HomeFactoryException
	 * @throws CreateException
	 */
	private void addSystemEvent(SystemEventDTO dto) throws HomeFactoryException, CreateException{
		SystemEventRecorder.addEvent4CpConfigedProperty(dto);
	}
	/**
	 * 电话号码修改
	 * @param inEvent
	 * @throws ServiceException 
	 */
private void updateServiceAccountCode(ServiceAccountEJBEvent inEvent) throws ServiceException{
    LogUtility.log(clazz,LogLevel.DEBUG,"updateServiceAccountCode\n"+inEvent);

	ServiceContext serviceContext=initServiceContext(inEvent);
	ServiceAccountService saService=new ServiceAccountService(serviceContext);
	
	saService.updatePhoneNo(inEvent.getServiceAccountID(),inEvent.getItemID(),inEvent.getOldServiceAccountCode(),inEvent.getServiceAccountCode());
	//创建系统日志记录
	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(),
			SystemLogRecorder.LOGMODULE_CUSTSERV, "修改服务号码","修改服务号码,业务帐户ID ："+serviceContext.get(Service.SERVICE_ACCOUNT_ID)+";新的服务号码:"+inEvent.getServiceAccountCode()+";旧的服务号码:"+inEvent.getOldServiceAccountCode(),
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
}
	/**
	 * 检查参数
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void checkServiceAccountParam(ServiceAccountEJBEvent inEvent)throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		ServiceContext serviceContext=initServiceContext(inEvent);

	    //检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkCreateServiceAccount(inEvent);
	    //如果是检查客户产品和优惠信息，还要返回设备类型
		if(EJBEvent.CHECK_PRODUCTPG_CAMPAINPG==inEvent.getActionType()){
			Collection  deviceClassList =PublicService.getDependencyDeviceClassBygetCsiPackageArray(inEvent.getCsiDto(),null,
					inEvent.getCsiPackageArray());
			
			//2008-04-21 获得产品属性列表 ，只要产品属性有设置，就会显示“选择相应硬件设备”页面
			Collection  productPropertyList =PublicService.getProductPropertyArray(inEvent.getCsiPackageArray());
			
			//如果有硬件设备列表，返回设备列表。没有返回费用列表 add by david.Yang   2008-04-21 只要产品属性有设置，就会显示“选择相应硬件设备”页面
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■deviceClassList■■"+deviceClassList);
			if ((deviceClassList !=null && !deviceClassList.isEmpty())  || (productPropertyList !=null && !productPropertyList.isEmpty()))
			    this.response.setPayload(deviceClassList);
			else
			{
				this.response.setPayload(calculateImmediateFee(inEvent));
				this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
			}
			return;
		}
		if(EJBEvent.CHECK_HARDPRODUCTINFO==inEvent.getActionType() ||
				EJBEvent.CHECK_BOOKINGUSER_PRODUCTPG_CAMPAINPG ==inEvent.getActionType()){
			//费用列表
			this.response.setPayload(calculateImmediateFee(inEvent));
			this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■checkServiceAccountParam■■");
			return;
		}
	}
	/**
	 * 预约增机的预约单创建
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void createSubscriberForBooking(ServiceAccountEJBEvent inEvent)throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);
		//检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkCreateServiceAccount(inEvent);
	    ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.createSubScriberForBooking(inEvent);
        //创建系统日志记录
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "预约增机","预约增机--预约，受理单ID ："+ ((CustServiceInteraction)serviceContext.get(Service.CSI)).getId().toString(),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 预约增机的预约单修改
	 * @param inEvent
	 * @throws ServiceException 
	 * @throws FinderException 
	 * @throws HomeFactoryException 
	 */
	private void subscriberForBookingUpdate(ServiceAccountEJBEvent inEvent) throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);
		//检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkUpdateServiceAccount(inEvent.getCsiDto().getId());
	    brService.checkCreateServiceAccount(inEvent);
	    ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.updateSubscriberForBooking(inEvent);
		//创建系统日志记录
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCsiDto().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "预约增机", "预约增机--预约单修改，受理单ID："
						+ inEvent.getCsiDto().getId(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 预约增机的预约单取消
	 * @param inEvent
	 * @throws ServiceException 
	 * @throws FinderException 
	 * @throws HomeFactoryException 
	 */
	private void subscribeForBookingCancel(ServiceAccountEJBEvent inEvent) throws ServiceException, HomeFactoryException, FinderException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		//检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkUpdateServiceAccount(inEvent.getCsiDto().getId());	    		
		CSIService csiService = new CSIService(serviceContext);
		//更改受理单状态为取消
		csiService.updateCustServiceInteractionInfo(inEvent.getCsiDto(),inEvent.getActionType());
		//创建系统日志记录
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCsiDto().getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "预约增机", "预约增机--预约单取消，受理单ID："
						+ inEvent.getCsiDto().getId(), SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 新增业务帐户|门店增机
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void addServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);

		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int customerID = inEvent.getCustomerID();
		boolean doPost = inEvent.isDoPost();

		//进行参数处理
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }
	    if(inEvent.getCustAddrDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(inEvent.getCustAddrDTO().getDistrictID()));

	    //检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkCreateServiceAccount(inEvent);

	    if(!doPost){
	    	this.response.setPayload(calculateImmediateFee(inEvent));
	    	this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
	    	return;
	    }
	    //创建受理单和相关对象
	    String logOpeatorDesc="";
		CSIService csiService=new CSIService(serviceContext);
		if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType())){
			logOpeatorDesc="门店增机";
		    csiService.createServiceAccountCsiInfo(inEvent);
		}else if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType())){
			logOpeatorDesc="预约增机";
			csiService.updateCustServiceInteractionInfo(csiDTO,inEvent.getActionType());
		    csiService.delectOldCsiCustProductInfo(csiDTO);
		    csiService.createCsiCustProductInfo(inEvent.getCsiPackageArray(),null,inEvent.getCsiCampaignArray(),csiDTO);
		}
		
		 //获得预约时选择的电话号码：
		  String serviceCodeStr = inEvent.getServiceCodeList();
		    String [] oldPhone = null;
	    	ArrayList serviceCodeList = new ArrayList();
	    	if(serviceCodeStr != null)
	    	{
	    		oldPhone = serviceCodeStr.split(",");
	    		for(int i=0;i<oldPhone.length;i++)
	    			serviceCodeList.add(oldPhone[i]);
	    	}
	    	//是否预约新开业务帐户
		    boolean isBookingOpen = false;
		    if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType()))
		    	isBookingOpen = true;

		//创建业务账户
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
	    /*********************add by yangchen 2008/07/23 start***************************************************/
	    saService.setCustomerBillingRuleMap(inEvent.getCustomerBillingRuleMap());
	    /*********************add by yangchen 2008/07/23 end***************************************************/
		saService.create(inEvent.getCsiPackageArray(),inEvent.getCsiCampaignArray(),inEvent.getPhoneNo(),inEvent.getItemID(), inEvent.getProductPropertyMap(),isBookingOpen,serviceCodeList);
	    //费用支付处理
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();

		//创建系统日志记录
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, logOpeatorDesc,logOpeatorDesc+"，受理单ID:"+csiID+";业务帐户ID ："+serviceContext.get(Service.SERVICE_ACCOUNT_ID),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

		//创建工单，设置业务对象之间关系在CSIService里完成

		//返回业务帐户ID
		//int saID=0;
		//if(serviceContext.get(Service.SERVICE_ACCOUNT_ID)!=null)
		//	saID=((Integer)serviceContext.get(Service.SERVICE_ACCOUNT_ID)).intValue();
		Map returnMap = new HashMap();
		returnMap.put("CustServiceInteractionID",((CustServiceInteraction)serviceContext.get(Service.CSI)).getId());
	    //returnMap.put("saID",new Integer(saID));
		returnMap.put("saID",serviceContext.get(Service.SERVICE_ACCOUNT_ID));
	    
	    
		this.response.setPayload(returnMap);
	}

	/**
	 * 计算受理费用
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	private Collection calculateImmediateFee(ServiceAccountEJBEvent event)throws ServiceException{
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(event.getCsiDto(),event.getCustomerID(),event.getAccountID());
		//押金
		commonObj.setDeposit(event.getForcedDeposit());
		Collection currentFeeList=FeeService.calculateImmediateFee(event.getCsiDto(),event.getCsiPackageArray(), event.getCsiCampaignArray(),commonObj);
		return currentFeeList;
	}

	/**
	 * 业务帐户主动暂停
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void pauseServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		
		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();
		
		//检查将要暂停的业务帐号下有无软件产品，wangpeng@20080305	
		//参考 ServiceAccountService Line808~835
		//Step1.得到相同业务帐户下的所有的产品
		ServiceAccountHome saHome;
		Collection cpIDList=null;
		try {
			saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(saID));
			
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			//调用组件CustomerProductService的pauseCustomerProduct方法和创建系统事件
			CustomerProductService cpService = new CustomerProductService(
					serviceContext, 0, 0, saID);			
			Iterator itCP = cpList.iterator();
			cpIDList = new ArrayList();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
                if (cp.getDeviceID() !=0) continue;
				//只有不为取消状态的产品才能暂停
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
					cpIDList.add(cp.getPsID());
			}
		} catch (HomeFactoryException e) {
			throw new ServiceException("暂停业务帐号_取得业务帐号下软件产品错误");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Step2.判断有无软件产品，没有则抛出异常，wangpeng@20080305
		if(cpIDList==null||cpIDList.isEmpty()){
    		LogUtility.log(clazz, LogLevel.ERROR, "没有软件产品,不能做停机操作");
        	throw new ServiceException("没有软件产品,不能做停机操作");
        }
		
		
		
		
		//进行参数处理
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }

	    if ("UP_ZD".equals(csiDTO.getCreateReason())){
	    	//检查参数
		    BusinessRuleService brService=new BusinessRuleService(serviceContext);
		    brService.checkOperationServiceAccount(inEvent);
	    }
	    if(!doPost){
		    //费用列表
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■pauseServiceAccount■■");
			return;

	    }
	    Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID,  " STATUS='"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL+"'");
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFee(psidCol,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,batchNoDTO);
	    //创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);

		//处理业务帐户
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.suspend(saID);

		//处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());

		//创建系统日志记录, 在ServiceAccountService里已经有了
	}
	/**
	 * 业务帐户设备转租
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void rentServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		
		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();
		boolean isExsitSTB=false;
		//检查将要设备转租的业务帐号下有无整转的硬件设备	
		//参考 ServiceAccountService Line808~835
		//Step1.得到相同业务帐户下的所有的硬件设备
		ServiceAccountHome saHome;
		String status=null;
		try {
			saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(saID));
			status=sa.getStatus();
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
						
			Iterator itCP = cpList.iterator();
			TerminalDeviceHome tdhome=HomeLocater.getTerminalDeviceHome();
			TerminalDevice terDev=null;
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				//判断是否有机顶盒，且用途是否为整转                          
                if(cp.getDeviceID()!=0){
                	terDev=tdhome.findByPrimaryKey(new Integer(cp.getDeviceID()));         
                	if(CommonConstDefinition.DEVICECALSS_STB.equalsIgnoreCase(terDev.getDeviceClass())&&",C,".equalsIgnoreCase(terDev.getPurposeStrList()))
                	isExsitSTB=true;
                }				
			}
		} catch (HomeFactoryException e) {
			throw new ServiceException("无机顶盒，或用途不为整转,不能做设备转租操作");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR, "设备转租出错");
			e.printStackTrace();
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "isExsitSTB:"+isExsitSTB);
		//如果无机顶盒，或用途不为整转
		if(!isExsitSTB&&!doPost){
   		LogUtility.log(clazz, LogLevel.ERROR, "无机顶盒，或用途不为整转,不能做设备转租操作");
        	throw new ServiceException("无机顶盒，或用途不为整转,不能做设备转租操作");
        }
		
		
		
		
		//进行参数处理
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }

	    	//检查参数
		    BusinessRuleService brService=new BusinessRuleService(serviceContext);
		    brService.checkOperationServiceAccount(inEvent);
	    if(!doPost){
		    //费用列表
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■rentServiceAccount■■");
			return;

	    }
	    Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID,  " STATUS='"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL+"'");
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFee(psidCol,operatorID,status,batchNoDTO);
	    //创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);

		//处理业务帐户设备信息和创建系统日志记录
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.rent(saID,doPost);

		//处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());

		//创建系统日志记录, 在ServiceAccountService里已经有了
	}
	
	/**
	 * 业务帐户销户
	 * @param inEvent
	 * @throws ServiceException
	 * @ 2007-5-22 根据前台参数传递（是否归还设备）修改设备，并处理设备流转
	 */
	private void closeServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();
		//得到销户后的设备状态
		String deviceStatus = CommonConstDefinition.DEVICE_STATUS_SOLD;
		if(CommonConstDefinition.YESNOFLAG_YES.equals(inEvent.getIsSendBack()))
		{
			deviceStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
		}
		else if(CommonConstDefinition.YESNOFLAG_NO.equals(inEvent.getIsSendBack()))
		{
			deviceStatus = CommonConstDefinition.DEVICE_STATUS_OFFLINE;
		}

		//进行参数处理
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    }
    	//检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
	    if(!doPost)
	    {
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),this.operatorID,inEvent.getDeviceFeeList());

			this.response.setPayload(shouldPayFeeList);
	    	return;
	    }
	    //Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID, " STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
	    //用于业务帐户取消的时候计算业务帐户下的产品涉及到的信息费
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFeeForServiceAccountClose(saID,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
	    
	    //记录状态变化原因
	    //inEvent.getCsiDto().setStatusReason(inEvent.getCsiDto().getCreateReason());
	    //创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);
		//取消客户正常的套餐或者促销
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(csiDTO.getCustomerID(),0,saID);
		
		//处理业务帐户
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		boolean isReturnDevice=false;
		if(CommonConstDefinition.YESNOFLAG_YES.equalsIgnoreCase(inEvent.getIsSendBack()))
			isReturnDevice=true;
		//saService.closeOnly(saID);
		saService.close(saID,isReturnDevice);

		//处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		Collection cpList=(Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		
		
		Collection deviceIDList = new ArrayList();
		//产品依赖的硬件设备;修改硬件设备的状态，并记录流转记录??
		int linkToDevice1 = 0;
		int linkToDevice2 = 0;
		String linkToDevice1SerialNo = "";
		String linkToDevice2SerialNo = "";
		String deviceIDLog = "";
		try {
			TerminalDeviceHome terminalDeviceHome;
			terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			Iterator itCP = cpList.iterator();
			int i = 0;
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				//过滤硬件产品
				//根据前台参数传递（是否归还设备）修改设备，并处理设备流转
				if (cp.getDeviceID() >0)
				{
					TerminalDevice terminalDev = terminalDeviceHome.findByPrimaryKey(new Integer(cp.getDeviceID()));
					terminalDev.setStatus(deviceStatus);
					terminalDev.setDtLastmod(TimestampUtility.getCurrentDate());
					deviceIDLog = deviceIDLog + "("+(i+1)+")" +terminalDev.getSerialNo()+",";
					deviceIDList.add(terminalDev.getDeviceID());
					continue;
				}
				
				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
			}
			
			/**
			if (linkToDevice1 != 0) {
				linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice1)).getSerialNo();
			}
			if (linkToDevice2 != 0) {
				terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice2)).getSerialNo();
			}
			**/
			
			//业务帐户销户的时候,解除设备配对关系 2007-12-27
			CustomerProductService customerProductService = new CustomerProductService(serviceContext);
			customerProductService.unchainDeviceMatch(deviceIDList);
		} catch (HomeFactoryException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String logDesc="业务帐户进行销户,受理单ID:"+serviceContext.get(Service.CSI_ID)+",业务帐户id："+saID;
		if(!"".equals(deviceIDLog))
			logDesc=logDesc+",涉及的设备序列号:"+deviceIDLog;
		/**
		if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
			logDesc=logDesc+",涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
		}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
			logDesc=logDesc+",涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",";
		}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
			logDesc=logDesc+",涉及的设备序列号:"+"(1)"+linkToDevice2SerialNo+",";
		}
		**/
		
		// 创建系统日志记录
		String operation="业务帐户销户";
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 业务帐户预退户
	 * @param inEvent
	 * @throws ServiceException
	 * @ 2007-5-22 根据前台参数传递（是否归还设备）修改设备，并处理设备流转
	 */
	private void beforehandCloseServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();

		//进行参数处理
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    }
    	//检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
    	if(!doPost)
	    {
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),this.operatorID,inEvent.getDeviceFeeList());
			this.response.setPayload(shouldPayFeeList);
	    	return;
	    }
	    //用于业务帐户取消的时候计算业务帐户下的产品涉及到的信息费
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFeeForServiceAccountClose(saID,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
	    
	    //创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);
		//取消客户正常的套餐或者促销
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(csiDTO.getCustomerID(),0,saID);
		
		//处理业务帐户
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		boolean isReturnDevice=false;
		if(CommonConstDefinition.YESNOFLAG_YES.equalsIgnoreCase(inEvent.getIsSendBack()))
			isReturnDevice=true;
		saService.beforehandClose(saID,isReturnDevice);

		//处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		Collection cpList=(Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		
		

		String logDesc="业务帐户进行预退户,受理单ID:"+serviceContext.get(Service.CSI_ID)+",业务帐户id："+saID;
			
		// 创建系统日志记录
		String operation="业务帐户预退户";
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 业务帐户实退户
	 * @param inEvent
	 * @throws ServiceException
	 * 
	 */
	private void realCloseServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();
		//得到销户后的设备状态
		String deviceStatus = CommonConstDefinition.DEVICE_STATUS_SOLD;
		if(CommonConstDefinition.YESNOFLAG_YES.equals(inEvent.getIsSendBack()))
		{
			deviceStatus = CommonConstDefinition.DEVICE_STATUS_WAITFORSELL;
		}
		else if(CommonConstDefinition.YESNOFLAG_NO.equals(inEvent.getIsSendBack()))
		{
			deviceStatus = CommonConstDefinition.DEVICE_STATUS_OFFLINE;
		}

		//进行参数处理
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    }
    	//检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
	    
	    //用于业务帐户取消的时候计算业务帐户下的产品涉及到的信息费
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFeeForServiceAccountClose(saID,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
	    
	    //创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);
		//取消客户正常的套餐或者促销
		CampaignService campaignService= new CampaignService(serviceContext);
		campaignService.cancelCustCampaignForCust(csiDTO.getCustomerID(),0,saID);
		
		//处理业务帐户
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		boolean isReturnDevice=false;
		if(CommonConstDefinition.YESNOFLAG_YES.equalsIgnoreCase(inEvent.getIsSendBack()))
			isReturnDevice=true;
		saService.close(saID,isReturnDevice);

		Collection cpList=(Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		
		Collection deviceIDList = new ArrayList();
		//产品依赖的硬件设备;修改硬件设备的状态，并记录流转记录??
		int linkToDevice1 = 0;
		int linkToDevice2 = 0;
		String linkToDevice1SerialNo = "";
		String linkToDevice2SerialNo = "";
		String deviceIDLog = "";
		try {
			TerminalDeviceHome terminalDeviceHome;
			terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			Iterator itCP = cpList.iterator();
			int i = 0;
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				//过滤硬件产品
				//根据前台参数传递（是否归还设备）修改设备，并处理设备流转
				if (cp.getDeviceID() >0)
				{
					TerminalDevice terminalDev = terminalDeviceHome.findByPrimaryKey(new Integer(cp.getDeviceID()));
					terminalDev.setStatus(deviceStatus);
					terminalDev.setDtLastmod(TimestampUtility.getCurrentDate());
					deviceIDLog = deviceIDLog + "("+(i+1)+")" +terminalDev.getSerialNo()+",";
					deviceIDList.add(terminalDev.getDeviceID());
					continue;
				}
				
				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
			}
					
			//业务帐户销户的时候,解除设备配对关系 2007-12-27
			CustomerProductService customerProductService = new CustomerProductService(serviceContext);
			customerProductService.unchainDeviceMatch(deviceIDList);
		} catch (HomeFactoryException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String logDesc="业务帐户进行销户,受理单ID:"+serviceContext.get(Service.CSI_ID)+",业务帐户id："+saID;
		if(!"".equals(deviceIDLog))
			logDesc=logDesc+",涉及的设备序列号:"+deviceIDLog;
		
		// 创建系统日志记录
		String operation="业务帐户销户";
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 业务帐户恢复
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void resumeServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		int saID=inEvent.getServiceAccountID();
		boolean doPost = inEvent.isDoPost();

		//进行参数处理
		if(csiDTO!=null)
			serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
	    if(inEvent.getCustomerDTO()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,inEvent.getCustomerDTO().getCustomerType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,inEvent.getCustomerDTO().getOpenSourceType());
	    }


	   //检查参数
	   BusinessRuleService brService=new BusinessRuleService(serviceContext);
	   brService.checkOperationServiceAccount(inEvent);
	   if(!doPost){
			//费用列表
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■pauseServiceAccount■■");
	    	return;
	    }
	    //Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID, null);
	    //由于恢复的时候允许选择要恢复的产品,恢复时的查品时直接从页面传来的
	    BatchNoDTO  batchNoDTO=new BatchNoDTO();
	    Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,batchNoDTO);
	    //创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.serviceAccountOperation(inEvent);

		//处理业务帐户
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.resume(inEvent);

		//处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
	}
	
	/**客户产品重授权
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void resendEMMForCustProduct(ServiceAccountEJBEvent inEvent)throws ServiceException{
		resendEMM(inEvent,SystemEventRecorder.SE_CA_RESENDEMM);
	}
	/**
	 * 业务帐户重授权
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void resendEMMForServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		resendEMM(inEvent,SystemEventRecorder.SE_CA_SERVICEACCOUNT_RESENDEMM);
	}
	/**
	 * 重发授权
	 * @param inEvent ServiceAccountEJBEvent
	 * @throws ServiceException
	 */
	private void resendEMM(ServiceAccountEJBEvent inEvent,int eventClass)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		int customerID = inEvent.getCustomerID();
		int saID=inEvent.getServiceAccountID();

		//处理重授权
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.setOperatorID(operatorID);
		String result=saService.resendEMM(saID,eventClass);
		
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "产品重授权", result, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 系统内用户业务帐户过户
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void transferServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{

		ServiceContext serviceContext=initServiceContext(inEvent);

	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
	    if(!inEvent.isDoPost()){
			//费用列表
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■transferServiceAccount■■");
	    	return;
	    }
		if(inEvent.getNewAccountID()==0 || inEvent.getNewCustomerID()==0){
			LogUtility.log(clazz,LogLevel.WARN,"参数错误！");
			throw new ServiceException("参数错误,目标客户不明确");
		}

		//取得的初始信息
		int saID=inEvent.getServiceAccountID();
		//目标客户ID
		int customerID=inEvent.getNewCustomerID();
		//目标帐户ID
		int accountID=inEvent.getNewAccountID();

	    //创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		//给源客户设置受理单对象
		csiService.serviceAccountOperation(inEvent);
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		//给目标客户设置受理单对象
		CustServiceInteractionDTO toCsiDTO=	new CustServiceInteractionDTO();
		toCsiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT);
		toCsiDTO.setCustomerID(inEvent.getNewCustomerID());
		toCsiDTO.setAccountID(inEvent.getNewAccountID());
		toCsiDTO.setBillCollectionMethod(inEvent.getCsiDto().getBillCollectionMethod());
		csiService.serviceAccountOperationForTransfer(toCsiDTO,inEvent.getServiceAccountID());
		int newCsiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();

		//进行用户过户
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.transfer(saID,customerID,accountID);

		//处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		//过户到的目标客户受理单没有费用,所以其状态设置为已付
		csiService.updateCSIPayStatus(toCsiDTO.getId(),CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		//老客户的受理单由于上面有费用,其状态根据实际情况来决定
		csiService.updateCSIPayStatus(inEvent.getCsiDto().getId(),inEvent.getCsiDto().getPaymentStatus());
		// 创建源客户系统日志记录
		String logDesc=",业务帐户:" + inEvent.getServiceAccountID() + "从("
		+ inEvent.getCustomerID() + ")"
		+BusinessUtility.getCustomerNameById(inEvent.getCustomerID())+"过户到系统内客户("
		+ inEvent.getNewCustomerID()+")"
		+BusinessUtility.getCustomerNameById(inEvent.getNewCustomerID())+",付费帐户 "+inEvent.getAccountID();
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "业务帐户过户", 
				"业务帐户过户--系统内客户,受理单ID:"+csiID+logDesc+";"+BusinessUtility.getCustomerDeviceProductInfoBySaID(saID),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
		// 创建目标客户的系统日志记录
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent
				.getNewCustomerID(), SystemLogRecorder.LOGMODULE_CUSTSERV, "业务帐户过户",
				"业务帐户过户--系统内客户,受理单ID:"+newCsiID+logDesc+";"+BusinessUtility.getCustomerDeviceProductInfoBySaID(saID),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);		
	}

	/**
	 * 系统外用户业务帐户过户
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void transferServiceAccountNotExistUser(ServiceAccountEJBEvent inEvent)throws ServiceException{

		ServiceContext serviceContext=initServiceContext(inEvent);
		
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOperationServiceAccount(inEvent);
	    
		CustomerDTO custDTO=inEvent.getCustomerDTO();
		AccountDTO acctDTO=inEvent.getAccountDTO();
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		AddressDTO custAddrDTO=inEvent.getCustAddrDTO();
	    
	    if(!inEvent.isDoPost()){
			//费用列表
			Collection shouldPayFeeList=FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getServiceAccountID(),inEvent.getOperatorID(),null);
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■pauseServiceAccount■■");
			
			//开始检查银行帐号,wangpeng@20080303
			if(acctDTO.getBankAccount()!=null && !"".equals(acctDTO.getBankAccount())){
				if(acctDTO.getMopID()!=0){
					String functionResult = BusinessUtility.callFunctionForCheckBankAccount(acctDTO.getMopID(),acctDTO.getBankAccount());
					if(!"true".equals(functionResult))
						throw new ServiceException(functionResult);
				}
			}
			
			
	    	return;
	    }
	    

		
		if(custAddrDTO==null){
			LogUtility.log(clazz,LogLevel.WARN,"业务帐户过户参数错误：用户地址信息未知！");
			throw new ServiceException("业务帐户过户参数错误：用户地址信息未知！");
		}
		if(inEvent.getServiceAccountID()==0 ){
			LogUtility.log(clazz,LogLevel.WARN,"业务帐户过户参数错误：业务帐户号未知！");
			throw new ServiceException("业务帐户过户参数错误：业务帐户号未知！");
		}
		//设置DTO相关的参数
		custDTO.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
		custDTO.setOrgID(BusinessUtility.getGovernedOrgIDByDistrictID(inEvent.getCustAddrDTO().getDistrictID()));
		acctDTO.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
		csiDTO.setCreateORGId(inEvent.getOperatorID());
		

		
		int newCustID=0;
		int newAcctID=0;
		
		//1.创建源客户受理单,
		CSIService csiService=new CSIService(serviceContext);
		//给源客户设置受理单对象
		csiService.serviceAccountOperation(inEvent);
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		
		//2。创建受理单以及创建受理单的相关产品信息
		//给目标客户设置受理单对象
		CustServiceInteractionDTO toCsiDTO=	new CustServiceInteractionDTO();
		toCsiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT);
		csiService.serviceAccountOperationForTransfer(toCsiDTO,inEvent.getServiceAccountID());
		int newCsiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		
		//3。创建客户信息以及备份信息、以及地址
		CustomerService custService=new CustomerService(serviceContext);
		custService.create(custDTO,custAddrDTO,inEvent.getCustMarketInfoList(),true);
		
		if(serviceContext.get(Service.CUSTOMER_ID)!=null)
			newCustID=((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue();
		
		//4。创建帐户信息以及备份信息、创建用户地址信息
		acctDTO.setCustomerID(newCustID);
		
		
		AccountService acctService=new AccountService(serviceContext);
		acctService.create(acctDTO,inEvent.getAccAddrDTO(),true);
		
		if(serviceContext.get(Service.ACCOUNT_ID)!=null)
			newAcctID=((Integer)serviceContext.get(Service.ACCOUNT_ID)).intValue();		

		//5。业务帐户过户
		//取得的初始信息
		int saID=inEvent.getServiceAccountID();
		//进行用户过户
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.transfer(saID,newCustID,newAcctID);

		//6。处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());

		//feeService.calculateCustProductFee();

		// 创建源客户系统日志记录
		String logDesc=",业务帐户:" + inEvent.getServiceAccountID() + "从("
		+ inEvent.getCustomerID() + ")"
		+BusinessUtility.getCustomerNameById(inEvent.getCustomerID())+"过户到新客户("
		+ newCustID+")"
		+BusinessUtility.getCustomerNameById(newCustID)+",付费帐户 "+newAcctID;
		
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "业务帐户过户", 
				"业务帐户过户--过户给新客户,受理单ID:"+csiID
				+ logDesc+";"+BusinessUtility.getCustomerDeviceProductInfoBySaID(saID), 
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
		// 创建目标客户的系统日志记录
		SystemLogRecorder.createSystemLog(PublicService
				.getRemoteHostAddress(serviceContext), PublicService
				.getCurrentOperatorID(serviceContext), newCustID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "业务帐户过户", 
				"业务帐户过户--过户给新客户,受理单ID:"+newCsiID
				+ logDesc+";"+BusinessUtility.getCustomerDeviceProductInfoBySaID(saID), 
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);		
		}

	/**
	 * 机卡配对、解配对、消除密码、发送消息
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void matchSCtoSTB(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext =initServiceContext(inEvent);
		int saID=inEvent.getServiceAccountID();

		//机卡配对/机卡解配对/消除密码/发送消息
		ServiceAccountService saService=new ServiceAccountService(serviceContext);
		saService.setOperatorID(operatorID);
		if(inEvent.getActionType()==EJBEvent.PARTNERSHIP)
			saService.matchSCtoSTB(saID,inEvent.getContent()
								   ,CommonConstDefinition.MATCH_SC_TO_STB_M);
		else if(inEvent.getActionType()==EJBEvent.CANCEL_PARTNERSHIP)
			saService.matchSCtoSTB(saID,inEvent.getContent()
								   ,CommonConstDefinition.MATCH_SC_TO_STB_D);
		else if(inEvent.getActionType()==EJBEvent.CLEARPASSWORD)
			saService.matchSCtoSTB(saID,inEvent.getContent()
								   ,CommonConstDefinition.MATCH_SE_CA_CLEARPWD);
		else if(inEvent.getActionType()==EJBEvent.SEND_EMAIL_CA)
			saService.matchSCtoSTB(saID,inEvent.getContent()
								   ,CommonConstDefinition.MATCH_SE_CA_SENDMAIL);
	}
	
	/**
	 * 客户业务帐户批量暂停
	 */
	
	private void batchPauseServiceAccount(ServiceAccountEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		//取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
		boolean doPost = inEvent.isDoPost();
		String[] serviceAcctStr =inEvent.getServiceAcctIdStr();
		if ("UP_ZD".equals(csiDTO.getCreateReason())){
	        //检查参数
		    BusinessRuleService brService=new BusinessRuleService(serviceContext);
		    for (int i=0;i<serviceAcctStr.length; i++){
		    	inEvent.setServiceAccountID(Integer.parseInt(serviceAcctStr[i]));
		    	inEvent.setActionType(EJBEvent.SUSPEND);
		        brService.checkOperationServiceAccount(inEvent);
		    }
		    inEvent.setServiceAccountID(0);
		    inEvent.setActionType(EJBEvent.BATCH_SUSPEND);
	    }
		if(!doPost){
		    //费用列表
			Collection shouldPayFeeList=new ArrayList();
			for (int i=0;i<serviceAcctStr.length; i++){
				Collection tempFeeList = FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),Integer.parseInt(serviceAcctStr[i]),inEvent.getOperatorID(),null);
				Iterator tempItr =tempFeeList.iterator();
				while (tempItr.hasNext()){
					 ImmediateCountFeeInfo imCtFeeInfo =(ImmediateCountFeeInfo)tempItr.next();
					 if (shouldPayFeeList.isEmpty()){
						 shouldPayFeeList.add(imCtFeeInfo);
					 }else{
						 if (imCtFeeInfo.getAcctItemList() !=null && !imCtFeeInfo.getAcctItemList().isEmpty()){
						    Iterator shouldPayFeeItr =shouldPayFeeList.iterator();
						    ImmediateCountFeeInfo shouldImmediateCountFeeInfo =(ImmediateCountFeeInfo)shouldPayFeeItr.next();
						    shouldImmediateCountFeeInfo.getAcctItemList().addAll(imCtFeeInfo.getAcctItemList());
						    Iterator  imCtFeeItr =imCtFeeInfo.getAcctItemList().iterator(); 
						    while (imCtFeeItr.hasNext()){
						    	AccountItemDTO accountItemDTO=(AccountItemDTO)imCtFeeItr.next();
						    	shouldImmediateCountFeeInfo.setTotalValueAccountItem(shouldImmediateCountFeeInfo.getTotalValueAccountItem()+accountItemDTO.getValue());
						    }
						 }
					 }
				 }		 
			}
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■batchPauseServiceAccount■■");
			return;
	    }
        // 创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.batchServiceAccountOperation(inEvent);
		if (doPost){
			this.response.setPayload(new Integer(inEvent.getCsiDto().getId()));
		}
		
		for (int i=0;i<serviceAcctStr.length; i++){
			int saID =Integer.parseInt(serviceAcctStr[i]);
		    Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID, " STATUS='"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL+"'");
	        BatchNoDTO  batchNoDTO=new BatchNoDTO();
		    Collection infoFeeList=FeeService.calculateInfoFee(psidCol,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,batchNoDTO);
	        CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
			commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
	        FeeService.createFeeAndPaymentAndPreDuciton(infoFeeList,null,null,commonObj.getSpCache()); 
	        //	处理业务帐户
			ServiceAccountService saService=new ServiceAccountService(serviceContext);
			saService.suspend(saID);
		}
		
        //  处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());

	}
	
	/**
	 * 客户业务帐户批量恢复
	 */
	private void batchResumeServiceAccount(ServiceAccountEJBEvent inEvent)throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		// 取得的初始信息
		CustServiceInteractionDTO csiDTO = inEvent.getCsiDto();
		serviceContext.put(Service.CSI_TYPE,csiDTO.getType());
		boolean doPost = inEvent.isDoPost();
        // 检查参数
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		String[] serviceAcctStr =inEvent.getServiceAcctIdStr();
		for (int i=0;i<serviceAcctStr.length; i++){
			inEvent.setServiceAccountID(Integer.parseInt(serviceAcctStr[i]));
			inEvent.setActionType(EJBEvent.RESUME);
		    brService.checkOperationServiceAccount(inEvent);
		}
		inEvent.setServiceAccountID(0);
		inEvent.setActionType(EJBEvent.BATCH_RESUME);
		if(!doPost){
			//费用列表
			Collection shouldPayFeeList=new ArrayList();
			for (int i=0;i<serviceAcctStr.length; i++){
				 Collection tempFeeList = FeeService.customerOpImmediateFeeCalculator(inEvent.getCsiDto(),Integer.parseInt(serviceAcctStr[i]),inEvent.getOperatorID(),null);
				 Iterator tempItr =tempFeeList.iterator();
				 while (tempItr.hasNext()){
					 ImmediateCountFeeInfo imCtFeeInfo =(ImmediateCountFeeInfo)tempItr.next();
					 if (shouldPayFeeList.isEmpty()){
						 shouldPayFeeList.add(imCtFeeInfo);
					 }else{
						 if (imCtFeeInfo.getAcctItemList() !=null && !imCtFeeInfo.getAcctItemList().isEmpty()){
						    Iterator shouldPayFeeItr =shouldPayFeeList.iterator();
						    ImmediateCountFeeInfo shouldImmediateCountFeeInfo =(ImmediateCountFeeInfo)shouldPayFeeItr.next();
						    shouldImmediateCountFeeInfo.getAcctItemList().addAll(imCtFeeInfo.getAcctItemList());
						    Iterator  imCtFeeItr =imCtFeeInfo.getAcctItemList().iterator(); 
						    while (imCtFeeItr.hasNext()){
						    	AccountItemDTO accountItemDTO=(AccountItemDTO)imCtFeeItr.next();
						    	shouldImmediateCountFeeInfo.setTotalValueAccountItem(shouldImmediateCountFeeInfo.getTotalValueAccountItem()+accountItemDTO.getValue());
						    }
						 }
					 }
				 }		 
			}
			this.response.setPayload(shouldPayFeeList);
			LogUtility.log(ServiceAccountCommand.class,LogLevel.DEBUG,"■■batchResumeServiceAccount■■");
		    return;
	    }
        //  创建受理单和相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.batchServiceAccountOperation(inEvent);
		if (doPost){
			this.response.setPayload(new Integer(inEvent.getCsiDto().getId()));
		}
		
		for (int i=0;i<serviceAcctStr.length; i++){
			int saID =Integer.parseInt(serviceAcctStr[i]);
		    Collection psidCol=BusinessUtility.getPsIDListByServiceAccount(saID, " STATUS='"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL+"'");
	        BatchNoDTO  batchNoDTO=new BatchNoDTO();
		    Collection infoFeeList=FeeService.calculateInfoFee(psidCol,operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,batchNoDTO);
	        CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
			commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
	        FeeService.createFeeAndPaymentAndPreDuciton(infoFeeList,null,null,commonObj.getSpCache()); 
	        //	处理业务帐户
			ServiceAccountService saService=new ServiceAccountService(serviceContext);
			inEvent.setServiceAccountID(saID);
			saService.resume(inEvent);
		}
		inEvent.setServiceAccountID(0);
		
		//  处理受理费用、缴费
		CommonFeeAndPayObject commonObj =new CommonFeeAndPayObject();
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getFeeList(),inEvent.getPaymentList(),null,inEvent.getCsiPrePaymentDeductionList(),commonObj,inEvent.getOperatorID());
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());		
	}
	/* 初始化ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(ServiceAccountEJBEvent event){
		String description="";
		String action="";

		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    int customerID=event.getCustomerID();
	    int accountID=event.getAccountID();
	    serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
	    serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

	    switch(event.getActionType()){
			//门店增机
			case EJBEvent.CUSTOMER_ADD_SUBSCRIBER:
				description="新增业务帐户--门店增机";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER:
				description="新增业务帐户--预约增机";
			    action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			    break;
			case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER_FORBOOKING:
				description="新增业务帐户--预约增机的预约";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
			    break;
			//暂停业务帐户
			case EJBEvent.SUSPEND:
				description="暂停业务帐户";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//销业务帐户
			case EJBEvent.CLOSE:
				description="取消业务帐户";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//恢复业务帐户
			case EJBEvent.RESUME:
				description="恢复业务帐户";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//重授权
			case EJBEvent.RESEND_EMM_BY_SERVICE_ACCOUNT:
				description="重授权";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//业务帐户过户
			case EJBEvent.TRANSFER:
				description="业务帐户过户";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//机卡匹配
			case EJBEvent.PARTNERSHIP:
				description="机卡匹配";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//机卡解配对
			case EJBEvent.CANCEL_PARTNERSHIP:
				description="机卡解配对";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
				//号码更改
			case EJBEvent.SERVICE_ACCOUNT_CODE_UPDATE:
				description="服务号码更改";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			    serviceContext.put(Service.SERVICE_ACCOUNT_ID,new Integer(event.getServiceAccountID()));
				break;

			default:
				description="";
				action="";
	    }

	    serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);

	    return serviceContext;
	}
}
