/*
 * Created on 2005-09-21
 *
 * @author Leon Liu
 */
package com.dtv.oss.service.command.csr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dtv.oss.service.command.*;
import com.dtv.oss.service.commandresponse.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.service.util.*;
import com.dtv.oss.service.*;
import com.dtv.oss.domain.CatvTerminal;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CatvTermProcessDTO;
import com.dtv.oss.dto.CatvTermProcessItemDTO;
import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.ConstructionBatchDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.component.*;
import com.dtv.oss.util.TimestampUtility;

/**
 * 开户流程相关的操作
 * author     ：zhouxushun 
 * date       : 2005-10-14
 * description:
 * @author 250713z
 *
 */
public class BookCommand extends Command {
	private static final Class clazz = BookCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
  
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    BookEJBEvent inEvent = (BookEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"传入的csidto为： " + inEvent.getCsiDto().toString());
	    
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.BOOKING:
	    			booking(inEvent);
	    			break;
	    		case EJBEvent.OPENACCOUNTFORBOOKING:
	    			generalOpenAccount(inEvent);
	    			break;
	    		case EJBEvent.OPENACCOUNTINBRANCH:
	    			generalOpenAccount(inEvent);
	    			break;
	    		case EJBEvent.CANCELBOOKING:
	    			cancelBooking(inEvent);
	    		    break;
	    		case EJBEvent.ALTERBOOKING:
	    			alterBooking(inEvent);
	    			break;
                case EJBEvent.AGENT_BOOKING:
                    booking(inEvent);
                    break;
                case EJBEvent.CHECK_AGENT_BOOKING:
                	confirmAgentBook(inEvent);
                    break;
                //安装失败返回费用
	    		case EJBEvent.RETURNFEE4FAILINSTALLATION:
	    			returnFeeForFailureInInstallation(inEvent);
	    			break;
	    		case EJBEvent.OPENACCOUNTDIRECTLY:
	    			openAccountDirectly(inEvent);
	    			break;
	    		//模拟电视开户
	    		case EJBEvent.OPEN_CATV_ACCOUNT:
	    			generalOpenCATVAccount(inEvent);
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}
	
	/**
	 * 预约、代理商预约
	 * @param event
	 * @throws CommandException
	 */
	private void booking(BookEJBEvent event) throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(event);
	    
	    //置参数于ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    serviceContext.put(Service.DEVICESERIALNO_DEVICECLASS_MAP,event.getDeviceTable());
	    
	    //进行参数、业务逻辑检查4g4l
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    businessRuleService.checkOpenInfo(event);
	    //创建业务对象
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.book(event);
	    //返回CSIID给前台
	    CustServiceInteraction csiEJB=(CustServiceInteraction)serviceContext.get(Service.CSI);
	    this.response.setPayload(csiEJB.getId());
	    //创建系统日志
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext), 0, 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "预约", "预约，受理单的ID："+csiEJB.getId(), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 预约开户、门店开户
	 * @param event
	 * @param openType
	 * @throws CommandException
	 */
	private void generalOpenAccount(BookEJBEvent event)	throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(event);
		//置参数于ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
			serviceContext.put(Service.CSI_INSTALLATION_TYPE,event.getCsiDto().getInstallationType());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    serviceContext.put(Service.DEVICESERIALNO_DEVICECLASS_MAP,event.getDeviceTable());
	    
	    //进行参数、业务逻辑检查
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    businessRuleService.checkOpenInfo(event);
	    	
	    //创建业务对象(创建客户对象、创建帐户已经在这个业务对象的openAccount（）方法中完成)
	    if(event.getNewCustInfo().getLoginID()!=null&&(!event.getNewCustInfo().getLoginID().equals(""))){
	       	if(BusinessUtility.isExitMultiLoginID(0,event.getNewCustInfo().getLoginID())){
	       		throw new ServiceException("请更换客户登陆ID，该ID号已经存在！");
	    	}
	    }	    
	    CSIService csiService=new CSIService(serviceContext);	    
	    csiService.openAccount(event); 
	    
	    String csiType = (String)serviceContext.get(Service.CSI_TYPE);
	    //得到 预约时选择的电话号码
	    String serviceCodeStr = event.getServiceCodeList();
	    String [] oldPhone = null;
    	ArrayList serviceCodeList = new ArrayList();
    	if(serviceCodeStr != null)
    	{
    		oldPhone = serviceCodeStr.split(",");
    		for(int i=0;i<oldPhone.length;i++)
    			serviceCodeList.add(oldPhone[i]);
    	}
	    boolean isBookingOpen = false;
	    if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiType))
	    	isBookingOpen = true;
	    //创建业务帐户
	    ServiceAccountService saService=new ServiceAccountService(serviceContext);
	    /*********************add by yangchen 2008/07/23 start***************************************************/
	    saService.setCustomerBillingRuleMap(event.getCustomerBillingRuleMap());
	    /*********************add by yangchen 2008/07/23 end***************************************************/
	    saService.create(event.getCsiPackageArray(),event.getCsiCampaignArray(),event.getPhoneNo(),event.getItemID(), event.getProductPropertyMap(),isBookingOpen,serviceCodeList);
	   
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(event.getCsiDto(),event.getCsiFeeList(),event.getCsiPaymentList(), event.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,event.getCsiDto().getPaymentStatus());
	    //返回CustomerID给前台
		Map returnMap = new HashMap();
	    returnMap.put("CustServiceInteractionID",serviceContext.get(Service.CSI_ID));
	    returnMap.put("CustomerID",serviceContext.get(Service.CUSTOMER_ID));
	    
	    this.response.setPayload(returnMap);
	    
	    //记录系统日志
	    int customerID=((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue();
	    int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
	    
	    String operation="";
	    String logDesc="";
	    if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiType)){
	    	operation="门店开户";
	    	logDesc="门店开户，客户ID ："+customerID+" 受理单ID:"+csiID;
	    }
	    else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiType)){
	    	operation="预约开户";
	    	logDesc="预约开户，客户ID ："+customerID+" 受理单ID:"+csiID;
	    }
	    
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}


	/**
	 * 取消预约，做和预约相反的操作
	 */
	private void cancelBooking(BookEJBEvent event) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(event);

	    // 置参数于ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    
	    //进行参数、业务规则 检查
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkCancelBook(event);
	    
	    //调用CSIService对象
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.cancelBookSheet(event);
	    String operation="预约取消";
	    String logDesc="对受理单预约进行取消，预约单id："+event.getCsiDto().getId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), 0, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 修改代理商预约单
	 * @param event
	 * @throws ServiceException
	 */
	private void alterBooking(BookEJBEvent event) throws ServiceException {
		//合并套餐或者促销对应的产品包到event中
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(event);

	    // 置参数于ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    
	    //进行参数、业务规则 检查
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOpenInfo(event);
	    
	    //调用CSIService对象
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.updateBookObject(event);
	    String operation="预约修改";
	    String logDesc="对创建的预约受理单信息进行修改，预约单id："+event.getCsiDto().getId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), 0, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	

	/**
	 * 对代理商创建的预约受理单进行确认操作
	 * @param event
	 * @throws ServiceException
	 */
	private void confirmAgentBook(BookEJBEvent event) throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(event);
	    // 置参数于ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    //进行参数、业务规则 检查
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkOpenInfo(event);
	    
		//调用CSIService对象
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.confirmBook(event);
	    String operation="代理商预约确认";
	    String logDesc="对代理商创建的预约受理单进行确认，预约单id："+event.getCsiDto().getId();
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
    			PublicService.getCurrentOperatorID(serviceContext), 0, 
				SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 安装失败返回费用
	 * @param inEvent
	 */
	private void returnFeeForFailureInInstallation(BookEJBEvent inEvent)throws ServiceException{
		LogUtility.log(CustomerCommand.class,LogLevel.DEBUG,"■■进入安装失败返回费用■■");
		ServiceContext serviceContext=initServiceContext(inEvent);
		//处理受理单
		CSIService cSIService=new CSIService(serviceContext);
		cSIService.updateCustServiceInteractionInfo(inEvent.getCsiDto(),inEvent.getActionType());
		
		//处理客户/帐户/业务帐户/团购券/优惠/客户产品/系统事件信息
		CustomerService customerService=new CustomerService(serviceContext);
		CustServiceInteractionDTO custServiceInteractionDTO =BusinessUtility.getCSIDTOByID(inEvent.getCsiDto().getId());
		customerService.returnFeeForCustomer(custServiceInteractionDTO);	
		
	    //退费的时候更新受理单关联的客户化促销
	    CampaignService campaignService=new CampaignService(serviceContext);
	    campaignService.cancelCustCampaignForNew(inEvent.getCsiDto().getId());
		//获取参数并设置到ServiceContext对象中
		//安装不成功退费
		FeeService.returnFeeForFailureInInstallation(custServiceInteractionDTO,inEvent.getOperatorID());
		//创建系统日志
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext), custServiceInteractionDTO.getCustomerID(), 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "安装不成功退费", "安装不成功退费，受理单的ID："+inEvent.getCsiDto().getId(), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 初始化ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(BookEJBEvent event){
		ServiceContext serviceContext=new ServiceContext();
		String description="";
		String action="";
		switch (event.getActionType()) {
			case EJBEvent.BOOKING:
				description="预约";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.OPENACCOUNTFORBOOKING:
				description="预约开户";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.OPENACCOUNTINBRANCH:
				description="门店开户";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
			case EJBEvent.CANCELBOOKING:
				description="取消预约";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_CANCEL;
			    break;
			case EJBEvent.ALTERBOOKING:
				description="修改预约信息";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_UPDATE;
				break;
	        case EJBEvent.AGENT_BOOKING:
	        	description="代理商预约";
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
	            break;
	        case EJBEvent.CHECK_AGENT_BOOKING:
	        	description="确认代理商预约信息"; 
	        	action=CommonConstDefinition.CSIPROCESSLOG_ACTION_APPLY;
	            break;
	        //安装失败返回费用
			case EJBEvent.RETURNFEE4FAILINSTALLATION:
				description="安装失败返回费用";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				serviceContext.put(Service.CUSTOMER_ID,new Integer(event.getCsiDto().getCustomerID()));
				break;
			case EJBEvent.OPENACCOUNTDIRECTLY:
				description="客户开户";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			case EJBEvent.OPEN_CATV_ACCOUNT:
				description="模拟电视开户";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW;
				break;
		}
		serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
	
	/**
	 * 客户开户
	 * @param event
	 * @param openType
	 * @throws CommandException
	 */
	private void openAccountDirectly(BookEJBEvent event) throws ServiceException{
		
		ServiceContext serviceContext=initServiceContext(event);
		//置参数于ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
			
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    serviceContext.put(Service.DEVICESERIALNO_DEVICECLASS_MAP,event.getDeviceTable());
	    
	    //进行参数、业务逻辑检查
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    businessRuleService.checkOpenInfoForDirectly(event);
	    	
	    //创建业务对象(创建客户对象、创建帐户已经在这个业务对象的openAccount（）方法中完成)
	    CSIService csiService=new CSIService(serviceContext);
	    csiService.openAccountDirectly(event);	    
	    //费用支付处理
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(event.getCsiDto(),event.getCsiFeeList(),event.getCsiPaymentList(), event.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,event.getCsiDto().getPaymentStatus());
	    
	    //返回CustomerID给前台
		Map returnMap = new HashMap();
	    returnMap.put("CustServiceInteractionID",serviceContext.get(Service.CSI_ID));
	    returnMap.put("CustomerID",serviceContext.get(Service.CUSTOMER_ID));
	    
	    this.response.setPayload(returnMap);
	    
	    //记录系统日志
	    int customerID=((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue();
	    String operation="";
	    String logDesc="";
	    operation="客户开户";
	    logDesc="客户开户，客户ID ："+customerID;
	    
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CUSTSERV, operation, logDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	
	/**
	 * 模拟电视开户
	 * @param event
	 * @param openType
	 * @throws CommandException
	 */
	private void generalOpenCATVAccount(BookEJBEvent event)	throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		//模拟电视产品包固定为9999
		//event.getCsiPackageArray().add(new Integer(9999));

		ServiceContext serviceContext=initServiceContext(event);
		//置参数于ServiceContext
		if(event.getCsiDto()!=null){
			serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());
			serviceContext.put(Service.CSI_DTO,event.getCsiDto());
			serviceContext.put(Service.CSI_INSTALLATION_TYPE,event.getCsiDto().getInstallationType());
		}
	    if(event.getNewCustInfo()!=null){
	    	serviceContext.put(Service.CUSTOMER_TYPE,event.getNewCustInfo().getType());
	    	serviceContext.put(Service.OPEN_CUSTOMER_TYPE,event.getNewCustInfo().getOpenSourceType());
	    }
	    if(event.getAcctAddressDTO()!=null)
	    	serviceContext.put(Service.CUSTOMER_DISTRICT_ID,new Integer(event.getAcctAddressDTO().getDistrictID()));
	    serviceContext.put(Service.DEVICESERIALNO_DEVICECLASS_MAP,event.getDeviceTable());
	    
	    //进行参数、业务逻辑检查
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    businessRuleService.checkOpenInfo(event);
	    	
	    //创建业务对象(创建客户对象、创建帐户已经在这个业务对象的openAccount（）方法中完成)
	    if(event.getNewCustInfo().getLoginID()!=null&&(!event.getNewCustInfo().getLoginID().equals(""))){
	       	if(BusinessUtility.isExitMultiLoginID(0,event.getNewCustInfo().getLoginID())){
	       		throw new ServiceException("请更换客户登陆ID，该ID号已经存在！");
	    	}
	    }	 
	    
		//如果catvid不存在，则创建终端
		if(!BusinessUtility.IsExistCatvID(event.getCatvID())){
			AddressDTO addrDTO=event.getAcctAddressDTO();
			if(addrDTO==null)
				throw new ServiceException("没有地址信息，无法创建终端信息");
			CatvTerminalDTO catvTerminalDTO=event.getCatvTerminalDTO();
			catvTerminalDTO.setId(BusinessUtility.getCatvID(addrDTO.getDistrictID()));
			catvTerminalDTO.setRecordSource(CommonConstDefinition.CATV_TERMINAL_RECORDSOURCE_S);
			catvTerminalDTO.setStatus(CommonConstDefinition.CATV_TERMINAL_STATUS_I);
			catvTerminalDTO.setPostcode(addrDTO.getPostcode());
			catvTerminalDTO.setDistrictID(addrDTO.getDistrictID());
			catvTerminalDTO.setDetailedAddress(addrDTO.getDetailAddress());
			catvTerminalDTO.setPortNo(event.getAddPortNum());
			catvTerminalDTO.setCreateDate(TimestampUtility.getCurrentTimestamp());
			catvTerminalDTO.setComments("模拟电视开户新增终端");
			CatvService catvService=new CatvService(serviceContext);
			CatvTerminal catvTerminal=catvService.createCatvTerminal(catvTerminalDTO);

			serviceContext.put(Service.OPEN_CATV_NEW_CATVID, catvTerminal.getId());
			
			//创建终端处理记录
			CatvTermProcessDTO catvTermProcessDTO=new CatvTermProcessDTO();
			catvTermProcessDTO.setCreateDate(TimestampUtility.getCurrentTimestamp());
			catvTermProcessDTO.setCreateOperatorId(PublicService.getCurrentOperatorID(serviceContext));
			catvTermProcessDTO.setCreateOrgId(BusinessUtility.getOpOrgIDByOperatorID(catvTermProcessDTO.getCreateOperatorId()));
			catvTermProcessDTO.setDescription("模拟电视开户创建终端");
			catvTermProcessDTO.setProcessType(CommonConstDefinition.CATV_TERMINAL_PROCESSTYPE_D);
			catvTermProcessDTO.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			
			CatvTermProcessItemDTO catvTermProcessItemDTO=new CatvTermProcessItemDTO();
			catvTermProcessItemDTO.setCatvId(catvTerminalDTO.getId());
			catvTermProcessItemDTO.setComments("模拟电视开户创建终端");
			catvTermProcessItemDTO.setCreateDate(TimestampUtility.getCurrentTimestamp());
			catvTermProcessItemDTO.setCreateOperatorId(catvTermProcessDTO.getCreateOperatorId());
			catvTermProcessItemDTO.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			
			Collection itemCol=new ArrayList();
			itemCol.add(catvTermProcessItemDTO);
			catvService.createCatvProcess(catvTermProcessDTO,itemCol);
		}
		//创建T_NetworkTerminalUserMap
		else{
			//检查  已经关联到客户 的终端 不能用于重新进行模拟电视开户。
			if(BusinessUtility.IsCustUseCatvID(event.getCatvID())){
				String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
				if(catvName==null || "".equals(catvName))
					catvName="终端编号";
				throw new ServiceException(catvName +"已经被使用");
			}
		}
		
		
	    CSIService csiService=new CSIService(serviceContext);	    
	    csiService.openAccount(event); 
	    
	    String csiType = (String)serviceContext.get(Service.CSI_TYPE);
	    //得到 预约时选择的电话号码
	    String serviceCodeStr = event.getServiceCodeList();
	    String [] oldPhone = null;
    	ArrayList serviceCodeList = new ArrayList();
    	if(serviceCodeStr != null)
    	{
    		oldPhone = serviceCodeStr.split(",");
    		for(int i=0;i<oldPhone.length;i++)
    			serviceCodeList.add(oldPhone[i]);
    	}
	    boolean isBookingOpen = false;
	    if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiType))
	    	isBookingOpen = true;
	    //创建业务帐户
	    ServiceAccountService saService=new ServiceAccountService(serviceContext);
	    saService.create(event.getCsiPackageArray(),event.getCsiCampaignArray(),event.getPhoneNo(),event.getItemID(), event.getProductPropertyMap(),isBookingOpen,serviceCodeList);
	 
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(event.getCsiDto(),event.getCsiFeeList(),event.getCsiPaymentList(), event.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,event.getCsiDto().getPaymentStatus());
	    
		Customer customer=(Customer)serviceContext.get(Service.CUSTOMER);
		if(customer!=null){
			if(customer.getCatvID()==null ||"".equals(customer.getCatvID())){
				String strCATVID=(String)serviceContext.get(Service.OPEN_CATV_NEW_CATVID);
				if(strCATVID!=null)
					customer.setCatvID(strCATVID);
			}
		}
		
		//返回CustomerID给前台
		Map returnMap = new HashMap();
	    returnMap.put("CustServiceInteractionID",serviceContext.get(Service.CSI_ID));
	    returnMap.put("CustomerID",serviceContext.get(Service.CUSTOMER_ID));
	    
	    this.response.setPayload(returnMap);
	    
	    //记录系统日志
	    int customerID=((Integer)serviceContext.get(Service.CUSTOMER_ID)).intValue();
	    int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
	    
	    String operation="模拟电视开户";
	    String logDesc="模拟电视开户，客户ID ："+customerID+" 受理单ID:"+csiID;
	    
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    			PublicService.getCurrentOperatorID(serviceContext), customerID, 
					SystemLogRecorder.LOGMODULE_CATV, operation, logDesc, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
}
