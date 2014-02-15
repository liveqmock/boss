package com.dtv.oss.service.command.csr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;

import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.domain.Product;
import com.dtv.oss.domain.ProductHome;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.Bundle2CampaignDTO;
import com.dtv.oss.dto.CAWalletDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProductDTO;
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
import com.dtv.oss.service.component.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeCalculator;
import com.dtv.oss.service.util.ImmediateFee.ImmediateFeeList;
import com.dtv.oss.util.TimestampUtility;


/**
 * 客户产品相关的操作
 * author     ：zhouxushun
 * date       : 2005-11-14
 * description:
 * @author 250713z
 *
 */
public class CustomerProductCommand extends Command {
	private static final Class clazz = CustomerProductCommand.class;
	private int operatorID = 0;
	private String machineName = "";
	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CustomerProductEJBEvent inEvent = (CustomerProductEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");

	    try {
	    	switch (inEvent.getActionType()) {
	    		//检查
	    		case EJBEvent.CHECK_PRODUCTPG_CAMPAINPG:
	    			checkCustProductParam(inEvent);
	    			break;
	    		case EJBEvent.CHECK_HARDPRODUCTINFO:
	    			checkCustProductParam(inEvent);
	    			break;
	    		//添加客户产品
	    		case EJBEvent.PURCHASE:
	    			addCustomerProduct(inEvent);
	    			break;
	    		//客户产品暂停
	    		case EJBEvent.SUSPEND:
	    			suspendCustomerProduct(inEvent);
	    			break;
	    		//客户产品恢复
	    		case EJBEvent.RESUME:
	    			resumeCustomerProduct(inEvent);
	    			break;
	    		//客户产品升级
	    		case EJBEvent.UPGRADE:
	    			alterCustomerProduct(inEvent);
	    			break;
                //客户产品降级
	    		case EJBEvent.DOWNGRADE:
	    			alterCustomerProduct(inEvent);
	    			break;
	    		//客户取消产品
	    		case EJBEvent.CANCEL:
	    			cancelCustomerProduct(inEvent);
	    			break;
	    		//客户产品迁移
	    		case EJBEvent.MOVE:
	    			moveCustomerProduct(inEvent);
	    			break;
	    		//设备更换
	    		case EJBEvent.DEVICESWAP:
	    			swapCustomerDevice(inEvent);
	    			break;
	    		//设备升级
	    		case EJBEvent.DEVICEUPGRADE:
	    			upgradeCustomerDevice(inEvent);
	    			break;
				//客户产品升级、降级(不做后台处理)
				case EJBEvent.UPDOWNGRADE:
					break;
				case EJBEvent.DEVICEPROVIDEMODIFY:
					cpDeviceProvideModify(inEvent);
					break;
				case EJBEvent.PRODUCTACCOUNTMODIFY:
					cpAccountModify(inEvent);
					break;
                //客户预约产品确认
				case EJBEvent.BOOKING:
					bookingAddCustomerProduct(inEvent);
					break;
				//客户预约产品修改
				case EJBEvent.ALTERBOOKING:
					modifyBookingAddCustomerProduct(inEvent);
					break;
				//客户预约产品取消
				case EJBEvent.CANCELBOOKING:
					cancelBookingAddCustomerProduct(inEvent);
					break;
				//创建客户化计费规则
				case EJBEvent.CUSTOMER_BILLING_RULE_NEW:
					createCustomerBillingRule(inEvent);
					break;
				//修改客户化计费规则
				case EJBEvent.CUSTOMER_BILLING_RULE_UPDATE:
					updateCustomerBillingRule(inEvent);
					break;
				//创建终端
				case EJBEvent.CATV_TERMINAL_NEW:
					createCatvTerminal(inEvent);
					break;
				//修改终端
				case EJBEvent.CATV_TERMINAL_UPDATE:
					updateCatvTerminal(inEvent);
					break;	
				//批量增加产品包
				case EJBEvent.BATCHADD_PRODUCTPACKAGE:
					batchAddCustProduct(inEvent);
					break;
				//协议截止日期修改
				case EJBEvent.PROTOCOL_DATE_MODIFY:
					protocolDateModify(inEvent);
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
	
	//add by david.Yang
	private void protocolDateModify(CustomerProductEJBEvent inEvent) throws ServiceException {
		CustomerProductService cpService=new CustomerProductService();
	    Collection colPsid = inEvent.getColPsid();
		cpService.changCustProdEndTime(colPsid);
		String descrption ="";
		Iterator psIdItr =colPsid.iterator();
		while (psIdItr.hasNext()){
			CustomerProductDTO cpDto =(CustomerProductDTO)psIdItr.next();
			descrption =descrption +"原cpID:"+cpDto.getPsID()+" 截止日期为："+ TimestampUtility.TimestampToDateString(cpDto.getStartTime())
			                       +" 现修改为:"+TimestampUtility.TimestampToDateString(cpDto.getEndTime())+" ; ";
		}
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "协议客户截止日期修改","协议客户截止日期修改，客户ID ："
				+inEvent.getCustomerID()+" 业务帐户ID: "+inEvent.getSaID()
				+descrption,
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	private void bookingAddCustomerProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);
		serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW);
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkAddCustomerProduct(inEvent);
		CSIService cService=new CSIService(serviceContext);
		cService.createAddCustomerProductCsiInfo(inEvent);
		
		Collection csiPackageArray=inEvent.getCsiPackageArray();
		Collection cpList=new ArrayList();	
		Iterator itr=csiPackageArray.iterator();
		while(itr.hasNext()){
			int packageId = ((Integer) itr.next()).intValue();
			cpList.add(BusinessUtility.getProductIDsByPackageID(packageId));
		}
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "客户预约新增产品","客户预约新增产品预约，客户ID ："
				+inEvent.getCustomerID()+" 业务帐户ID: "+((Integer)serviceContext.get(Service.CSI_ID)).intValue()
				+"预约新增产品："+BusinessUtility.getProductDesByProductID(cpList),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	private void cancelBookingAddCustomerProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_BOOKINGCANCEL);
		CSIService csiService=new CSIService(serviceContext);
		boolean canCancel=csiService.canCancelBookingAddCustProduct(inEvent);
		if(canCancel){
			csiService.cancelBookingAddCustomerProduct(inEvent);
			
			Collection csiPackageArray=inEvent.getCsiPackageArray();
			String  prodDes="";
			if(csiPackageArray!=null && !csiPackageArray.isEmpty()){
				Collection cpList=new ArrayList();
				Iterator itr=csiPackageArray.iterator();
				while(itr.hasNext()){
					int packageId = ((Integer) itr.next()).intValue();
					cpList.add(BusinessUtility.getProductIDsByPackageID(packageId));
				}
				prodDes="取消预约的产品："+BusinessUtility.getProductDesByProductID(cpList);
			}
			SystemLogRecorder.createSystemLog(machineName,
					operatorID, inEvent.getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "客户预约新增产品","客户预约新增产品取消，客户ID ："
					+inEvent.getCustomerID()+" 业务帐户ID: "+((Integer)serviceContext.get(Service.CSI_ID)).intValue()
					+prodDes,
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}else 
			throw new ServiceException("只有待处理状态的受理单才能取消！");		
	}

	private void modifyBookingAddCustomerProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		ServiceContext serviceContext=initServiceContext(inEvent);
		serviceContext.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_UPDATE);
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkAddCustomerProduct(inEvent);
		CSIService cService=new CSIService(serviceContext);
		cService.modifyAddCustomerProductCsiInfo(inEvent);	
		
		Collection csiPackageArray=inEvent.getCsiPackageArray();
		Collection cpList=new ArrayList();	
		Iterator itr=csiPackageArray.iterator();
		while(itr.hasNext()){
			int packageId = ((Integer) itr.next()).intValue();
			cpList.add(BusinessUtility.getProductIDsByPackageID(packageId));
		}
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, inEvent.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "客户预约新增产品","客户预约新增产品修改，客户ID ："
				+inEvent.getCustomerID()+" 业务帐户ID: "+((Integer)serviceContext.get(Service.CSI_ID)).intValue()
				+"修改预约的产品："+BusinessUtility.getProductDesByProductID(cpList),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	/**
	 * 修改客户设备来源,
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void cpDeviceProvideModify(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		CustomerProductService cpService=new CustomerProductService(serviceContext);
		CustomerProduct cp=cpService.cpDeviceProvideModify(inEvent.getCustomerProductDTO());
		String oldDeviceProvideFlag =(String)serviceContext.get(Service.PROCESS_RESULT);
		String deviceProvideFlag=inEvent.getCustomerProductDTO().getDeviceProvideFlag();
		oldDeviceProvideFlag=BusinessUtility.getCommonNameByKey("SET_C_CP_DEVICEPROVIDEFLAG",oldDeviceProvideFlag);
		deviceProvideFlag=BusinessUtility.getCommonNameByKey("SET_C_CP_DEVICEPROVIDEFLAG",deviceProvideFlag);
		//创建系统日志记录
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, cp.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "客户设备来源修改","客户设备来源修改，客户ID ："
				+cp.getCustomerID()+" PSID: "+cp.getPsID()
				+SystemLogRecorder.appendDescString(",客户设备来源:", oldDeviceProvideFlag, deviceProvideFlag),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

	}
	
	/**
	 * 修改客户产品付费帐户
	 * * @param inEvent
	 * @throws ServiceException
	 */
	private void cpAccountModify(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		CustomerProductService cpService=new CustomerProductService(serviceContext);
		
		CustomerProduct cp=cpService.cpAccountModify(inEvent.getCustomerProductDTO());
		String oldAccountID =(String)serviceContext.get(Service.PROCESS_RESULT);
		//创建系统日志记录
		SystemLogRecorder.createSystemLog(machineName,
				operatorID, cp.getCustomerID(),
				SystemLogRecorder.LOGMODULE_CUSTSERV, "客户产品付费帐户修改",
				"客户产品付费帐户修改"
				+",客户产品: "+BusinessUtility.getProductDescListByPSIDList(cp.getPsID()+"")
				+SystemLogRecorder.appendDescString(",付费帐户:", oldAccountID, cp.getAccountID()+""),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

	}
	/**
	 * 检查参数
	 * )@param inEvent
	 * @throws ServiceException
	 */
	private void checkCustProductParam(CustomerProductEJBEvent inEvent) throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		
		ServiceContext serviceContext=initServiceContext(inEvent);

	    //检查参数
	    BusinessRuleService brService=new BusinessRuleService(serviceContext);
	    brService.checkAddCustomerProduct(inEvent);
	    if(inEvent.getActionType()==EJBEvent.CHECK_PRODUCTPG_CAMPAINPG){
	    	Collection  deviceClassList =PublicService.getDependencyDeviceClassBygetCsiPackageArray(inEvent.getCsiDto(),null,
					inEvent.getCsiPackageArray());
	        //如果有硬件设备列表，返回设备列表。没有返回费用列表 
			if (deviceClassList !=null && !deviceClassList.isEmpty())
			{
			    this.response.setPayload(deviceClassList);
			}
			else 
			{
				this.response.setPayload(calculateImmediateFee(inEvent));
				this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
			}
	    }
	    if(inEvent.getActionType()==EJBEvent.CHECK_HARDPRODUCTINFO)
	    {
	    	this.response.setPayload(calculateImmediateFee(inEvent));
	    	this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
	    }
	    
		

	}
	
	/**
	 * 该方法针对特定的客户，做产品新增。如果客户下的业务账户已经有了新增的软件，则不增加也不发CA
	 */
     private void batchAddCustProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
    	 if(inEvent.getCsiPackageArray()==null)
 			inEvent.setCsiPackageArray(new ArrayList());
    	 inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
    	 ServiceContext serviceContext=initServiceContext(inEvent);
    	 BusinessRuleService brService=new BusinessRuleService(serviceContext);
    	 brService.checkBatchAddCustomerProduct(inEvent);
    	 Map sa_cp_Mp =new LinkedHashMap();
    	 Iterator saItr =inEvent.getColServiceAccountInfo().iterator();
    	 while (saItr.hasNext()){
    		String saId =(String)saItr.next(); 
    	    Collection cpDTOList=BusinessUtility.getCustomerProductDTOListByPackageIDList(inEvent.getCsiPackageArray());
			Collection currentAllProductCol =BusinessUtility.getProductIDListByServiceAccount(Integer.parseInt(saId)," status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"' ");
    	    //去掉当前已有的客户产品
			Collection existcpCol =new ArrayList();
			Iterator cpDtoItr =cpDTOList.iterator();
			while (cpDtoItr.hasNext()){
			   CustomerProductDTO cpDto =(CustomerProductDTO)cpDtoItr.next();
			   if (currentAllProductCol.contains(new Integer(cpDto.getProductID()))) existcpCol.add(cpDto);
			}
			
			Iterator existcpItr =existcpCol.iterator();
			while (existcpItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)existcpItr.next();
				cpDTOList.remove(cpDto);
			}
			//去掉不符合依赖关系的产品
			Collection productCol =new ArrayList();
			cpDtoItr =cpDTOList.iterator();
			while (cpDtoItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)cpDtoItr.next();
				productCol.add(new Integer(cpDto.getProductID()));
			}
			Collection productAllCol =new ArrayList();
			productAllCol.addAll(currentAllProductCol);
			productAllCol.addAll(productCol);
			
            Collection errorcpDtoCol =new ArrayList();
			Collection errorProductCol =brService.getNoMatchProductDependency(productCol,productAllCol);
			cpDtoItr =cpDTOList.iterator();
			while (cpDtoItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)cpDtoItr.next();
				if (errorProductCol.contains(new Integer(cpDto.getProductID()))) errorcpDtoCol.add(cpDto);
			}
			
			Iterator errorcpDtoItr =errorcpDtoCol.iterator();
			while (errorcpDtoItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)errorcpDtoItr.next();
				cpDTOList.remove(cpDto);
			}
			sa_cp_Mp.put(saId, cpDTOList);			
    	 } 
    	 
    	 if (!inEvent.isDoPost()){
    		//费用列表: 分协议用户和非协议用户的计费
    		this.response.setPayload(calculateImmediateFeeWithBatchBuy(inEvent.getCsiDto(),sa_cp_Mp,inEvent.getUsedMonth()));
    	 }else{
    		 Collection csiCampaignArray = inEvent.getCsiCampaignArray();
        	 int customerID =inEvent.getCustomerID();
        	 int accountID =inEvent.getAccountID();
          	 
     	     //创建受理单相关对象
    		 CSIService csiService=new CSIService(serviceContext);
    		 CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
 	    	 csiDTO.setCustomerID(customerID);
 	    	 csiDTO.setAccountID(accountID);
 	    	 // 创建受理单信息
 	    	 csiService.createCustServiceInteraction(csiDTO);
 	    	 //返回受理单ID
      		 int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
       		 csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
    		 
  		     Map packageCampaignMap=BusinessUtility.getPackageCampaignMap(inEvent.getCsiPackageArray(), csiCampaignArray);
  		     int ct =1;
  		     Iterator sa_cp_Itr =sa_cp_Mp.keySet().iterator();
        	 while (sa_cp_Itr.hasNext()){
        		 String saId =(String)sa_cp_Itr.next();
        		 Collection cpDTOList =(Collection)sa_cp_Mp.get(saId);
        		 Collection buyPackageList =new ArrayList();
        		 Iterator   cpItr =cpDTOList.iterator();
        		 while (cpItr.hasNext()){
        			 CustomerProductDTO cpDto =(CustomerProductDTO)cpItr.next();
        			 if (cpDto.getReferPackageID() ==0) continue;
        			 if (!buyPackageList.contains(new Integer(cpDto.getReferPackageID()))){
        				 buyPackageList.add(new Integer(cpDto.getReferPackageID()));
        			 }
        		 }
        		 
        		 System.out.println("saId---------->"+saId);
        		 System.out.println("buyPackageList------>"+buyPackageList);
        		 System.out.println("csiCampaignArray-------->"+csiCampaignArray);
        		 
        		// 创建受理单涉及的客户产品相关信息
        		 Collection csiProductList = csiService.createCsiCustProductInfo(buyPackageList,null,csiCampaignArray,csiDTO,null,ct,ct);  
        		
        		 //创建客户产品实例
        		 CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
      					inEvent.getAccountID(),Integer.parseInt(saId));
        		 cpService.setUsedMonth(inEvent.getUsedMonth());
        		 cpService.create(cpDTOList,accountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,ct,ct);
        		 ct =ct+1;
        		 
        		 ArrayList serviceAccountList = new ArrayList();
      			 serviceAccountList.add(new Integer(saId));
      			 Collection cpCampaignList =new ArrayList();
      			 Iterator cpDtoitr =cpDTOList.iterator();
      			
      			 while (cpDtoitr.hasNext()){
      				CustomerProductDTO dto =(CustomerProductDTO)cpDtoitr.next();
      				Bundle2CampaignDTO bdDto=BusinessUtility.getBundle2CampaignDTOByPackageID(dto.getReferPackageID());
   		    		if (bdDto !=null && bdDto.getCampaignID()!=0){
   		    			if (!cpCampaignList.contains(new Integer(bdDto.getCampaignID()))){
   		    			    cpCampaignList.add(new Integer(bdDto.getCampaignID()));
   		    			}
   		    		}
      			}
      		    //创建客户促销实例
      			CampaignService campaignService=new CampaignService(serviceContext);
      			campaignService.createCustomerCampaign(cpDTOList, cpCampaignList,serviceAccountList,inEvent.getCustomerID(),inEvent.getAccountID(),csiID);
      			Collection col = (Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
      			if (col !=null && !col.isEmpty()){
      			   Iterator ite = col.iterator();
      			   String cpIDstr="";
      			   while (ite.hasNext()){
      				  CustomerProduct cp = (CustomerProduct) ite.next();
      				  cpIDstr=cpIDstr+cp.getPsID()+",";
      			   }
      			
      			   String description = "客户新增产品,受理单ID:"+csiID
      		       +",业务帐户:"+saId 
      		       +",添加的产品:"+BusinessUtility.getProductDescListByPSIDList(cpIDstr);
      			   //创建系统日志
      			   SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
      	    			PublicService.getCurrentOperatorID(serviceContext), customerID,
      					SystemLogRecorder.LOGMODULE_CUSTSERV, "新增产品",description,
      					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
      			}
         	}
         	 
         	//费用支付处理
      		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
      		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
        	this.response.setPayload(serviceContext.get(Service.CSI_ID));
    	 }
         
     }
     
     private Collection calculateImmediateFeeWithBatchBuy(CustServiceInteractionDTO csiDto,Map sa_cp_Mp,int usedMonth)throws  ServiceException{
    	//如果是协议价客户走协议价计费  add by david.Yang begin
    	Collection currentFeeList =new ArrayList();
    	CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(csiDto,csiDto.getCustomerID(),csiDto.getAccountID());
 		Map protocolMap =BusinessUtility.getProtocolDTOColByCustID(csiDto.getCustomerID());
	    Iterator sa_cp_itr = sa_cp_Mp.keySet().iterator();
		while(sa_cp_itr.hasNext()){
			String saId =(String)sa_cp_itr.next();
   		    Collection cpDTOList =(Collection)sa_cp_Mp.get(saId);
   		    
   		    System.out.println("saId--->"+saId+":  cpDTOList---->"+cpDTOList);
   		    
   		    Collection csiPackageList =new ArrayList();
   		    Collection csiCampaignArray =new ArrayList();
   		    Iterator   cpDTOItr =cpDTOList.iterator();
   		    while (cpDTOItr.hasNext()){
   		    	CustomerProductDTO dto =(CustomerProductDTO)cpDTOItr.next();
   		    	if (!csiPackageList.contains(new Integer(dto.getReferPackageID()))){
   		    		csiPackageList.add(new Integer(dto.getReferPackageID()));
   		    		Bundle2CampaignDTO bdDto=BusinessUtility.getBundle2CampaignDTOByPackageID(dto.getReferPackageID());
   		    		if (bdDto !=null && bdDto.getCampaignID()!=0){
   		    			csiCampaignArray.add(new Integer(bdDto.getCampaignID()));
   		    		}
   		    	}
   		    }
		    if (protocolMap.isEmpty()){
 				Collection feeList=FeeService.calculateImmediateFee(csiDto,csiPackageList, csiCampaignArray,commonObj);
 				Iterator   feeItr =feeList.iterator();
 				while (feeItr.hasNext()){
 					ImmediateCountFeeInfo immediateCountFeeInfo =(ImmediateCountFeeInfo)feeItr.next();
 					currentFeeList.addAll(immediateCountFeeInfo.getAcctItemList());
 				}
		    }else{
		    	Collection feeList=BusinessUtility.CaculateFeeForProtocol(1,csiPackageList,usedMonth,csiDto.getCustomerID());
		    	currentFeeList.addAll(feeList);
		    }
		}
		ImmediateCountFeeInfo immediateCountFeeInfo=FeeService.encapsulateFeeInfoAndAccountInfo(csiDto.getAccountID() ,csiDto.getType(),null,null);
		immediateCountFeeInfo.setAccountName(commonObj.getAccountName());
		immediateCountFeeInfo.setAccountid(csiDto.getAccountID());
		
		//合并费用
		Map mergeAcctItemMp =new LinkedHashMap();
		Iterator currentItr =currentFeeList.iterator();
		while (currentItr.hasNext()){
			AccountItemDTO itemDto =(AccountItemDTO)currentItr.next();
			if (mergeAcctItemMp.containsKey(itemDto.getAcctItemTypeID())){
				AccountItemDTO itemDto1 =(AccountItemDTO)mergeAcctItemMp.get(itemDto.getAcctItemTypeID());
				itemDto1.setValue(itemDto1.getValue()+itemDto.getValue());
			}else{
				mergeAcctItemMp.put(itemDto.getAcctItemTypeID(),itemDto);
			}
		}
		Collection mergeAcctItemList =new ArrayList();
		Iterator mergeAcctItemMpItr =mergeAcctItemMp.keySet().iterator();
		while (mergeAcctItemMpItr.hasNext()){
			String acctItemTypeId =(String)mergeAcctItemMpItr.next();
			AccountItemDTO dto= (AccountItemDTO)mergeAcctItemMp.get(acctItemTypeId);
			mergeAcctItemList.add(dto);
		}
		immediateCountFeeInfo.setAcctItemList(mergeAcctItemList);
		Collection resultFeeList =new ArrayList();
		resultFeeList.add(immediateCountFeeInfo);
    	return resultFeeList;
     }
	
	/**
	 * 该方法只在特定的客户、业务帐户、帐户下增加软件产品。如果需要买设备，使用[新增用户]方法(调用addServiceAccount)
	 * @param inEvent
	 */
	private void addCustomerProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		//合并套餐或者促销对应的产品包到event中
		if(inEvent.getCsiPackageArray()==null)
			inEvent.setCsiPackageArray(new ArrayList());
		inEvent.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(inEvent.getCsiCampaignArray()));
		ServiceContext serviceContext=initServiceContext(inEvent);
		//取得的初始信息
		int customerID=inEvent.getCustomerID();
		int accountID=inEvent.getAccountID();
		int saID=inEvent.getSaID();
		Collection csiPackageArray=inEvent.getCsiPackageArray();
		Collection csiCampaignArray=inEvent.getCsiCampaignArray();
		boolean doPost=inEvent.isDoPost();

		//进行数据、产品包/优惠检查
		Collection newPackageList=BusinessUtility.getCustProductPackageBySAID(saID);
		newPackageList.addAll(csiPackageArray);
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkAddCustomerProduct(inEvent);
		
		//创建受理单相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.createAddCustomerProductCsiInfo(inEvent);
		int csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();

		//创建客户产品
		//创建促销、产品包映射表,由BusinessRuleService保证packageList和campaignList逻辑正确，只需要进行映射即可
        Map packageCampaignMap=BusinessUtility.getPackageCampaignMap(csiPackageArray, csiCampaignArray);
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());
		Collection cpDTOList=BusinessUtility.getCustomerProductDTOListByPackageIDList(csiPackageArray);
	    /*********************add by yangchen 2008/07/23 start***************************************************/
		cpService.setCustomerBillingRuleMap(inEvent.getCustomerBillingRuleMap());
	    /*********************add by yangchen 2008/07/23 end***************************************************/
		cpService.setUsedMonth(inEvent.getUsedMonth());
		cpService.create(cpDTOList,csiCampaignArray,saID,customerID,accountID,packageCampaignMap, inEvent.getProductPropertyMap());
		csiService.updateCSIPayStatus(serviceContext,CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
		//创建客户促销实例
		CampaignService campaignService=new CampaignService(serviceContext);
		ArrayList serviceAccountList = new ArrayList();
		serviceAccountList.add(new Integer(saID));
		campaignService.createCustomerCampaign(cpDTOList, csiCampaignArray,serviceAccountList,inEvent.getCustomerID(),inEvent.getAccountID(),csiID);

		Collection col = (Collection) serviceContext.get(Service.CUSTOMER_PRODUCT_LIST);
		Iterator ite = col.iterator();
		String cpIDstr="";
		while (ite.hasNext()){
			CustomerProduct cp = (CustomerProduct) ite.next();
			cpIDstr=cpIDstr+cp.getPsID()+",";
		}
	    //费用支付处理
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		FeeService.payFeeOperationForOpen(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(), inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);

	    String description = "客户新增产品,受理单ID:"+csiID
	    +",业务帐户:"+saID 
	    +",添加的产品:"+BusinessUtility.getProductDescListByPSIDList(cpIDstr);
		//创建系统日志
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "新增产品",description,
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

		//设置业务对象之间关系,none

		//返回受理单ID
		this.response.setPayload(serviceContext.get(Service.CSI_ID));
	}

	/**
	 * 客户产品暂停
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void suspendCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//取得的初始信息
		//设置受理单类型：置入ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		//取得客户产品列表：Collection psidList
		Collection psidList=inEvent.getColPsid();
//		取得客户需要取消的产品列表
		 
		//是否提交
		boolean doPost=inEvent.isDoPost();
		//取得客户ID：置入ServiceContext,取得帐户ID：置入ServiceContext
		int customerID=0;
		int accountID=0;
		if(inEvent.getCsiDto()!=null){
			customerID=inEvent.getCsiDto().getCustomerID();
			accountID=inEvent.getCsiDto().getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

		//进行业务规则检查,部分方法还没有实现
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);


		//是否提交
		if(!doPost){
		    //创建费用/缴费记录
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerProductOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getColPsid(),0,inEvent.getSaID(),this.operatorID);
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//计算客户产品产生得信息费
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,batchNoDTO);
		
		//将受理原因记录到受理单创建原因：
		inEvent.getCsiDto().setCreateReason(inEvent.getCsiDto().getStatusReason());
		//创建受理单相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		//修改客户产品状态
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());
		ArrayList cplist=cpService.pauseCustomerProduct(psidList);
		//费用处理和产品费用计算
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		//返回受理单id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));

		StringBuffer strPsidList=new StringBuffer();
		for(int i=0;i<cplist.size();i++){
			CustomerProduct cp=(CustomerProduct) cplist.get(i);
			strPsidList.append(cp.getPsID());
			if(i<cplist.size()-1)strPsidList.append(",");
		}
		

		//创建系统日志记录
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "产品暂停","客户产品暂停，受理单ID:"
				+csiID+",客户ID："+customerID+",暂停产品:"
				+BusinessUtility.getProductDescListByPSIDList(strPsidList.toString()),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 客户产品恢复
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void resumeCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//取得的初始信息
		//设置受理单类型：置入ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		//取得客户产品列表：Collection psidList
		Collection psidList=inEvent.getColPsid();
		//是否提交
		boolean doPost=inEvent.isDoPost();
		//取得客户ID：置入ServiceContext,取得帐户ID：置入ServiceContext
		int customerID=0;
		int accountID=0;
		if(inEvent.getCsiDto()!=null){
			customerID=inEvent.getCsiDto().getCustomerID();
			accountID=inEvent.getCsiDto().getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

		//进行业务规则检查,部分方法还没有实现
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);

		//是否提交
		if(!doPost){
		    //创建费用/缴费记录
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerProductOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getColPsid(),0,inEvent.getSaID(),this.operatorID);
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//计算客户产品发生的信息费
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,batchNoDTO);

		//创建受理单相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		//修改客户产品状态
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());
		cpService.resumeCustomerProduct(psidList);

		//费用处理和产品费用计算
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());

		
		//返回受理单id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));

		//创建系统日志记录
		StringBuffer strPsidList=new StringBuffer();
		java.util.Iterator it=psidList.iterator();
		while(it.hasNext()){
			Integer psid=(Integer) it.next();
			strPsidList.append(psid).append(",");
		}
		
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "产品恢复","客户产品恢复，受理单ID:"+csiID
				+",客户ID ："+customerID
				+",恢复产品列表:"+BusinessUtility.getProductDescListByPSIDList(strPsidList.toString()),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);

	}

	/**
	 * action: 0: 产品升级1: 产品降级
	 * CustomerProductEJBEvent里的getColPsid（）封装中，第一个为客户产品的ID，第二个为目标产品的ID，第二个可以为空
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void alterCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{

		if(inEvent.getActionType()==EJBEvent.UPGRADE){
			alertCustomerProductAndDevice("U",inEvent);
		}
		else if(inEvent.getActionType()==EJBEvent.DOWNGRADE){
			alertCustomerProductAndDevice("D",inEvent);
		}
	}

	/**
	 * 取消客户产品 :   07-2-15加入了支持硬件产品取消的能力,
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void cancelCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);

		//设置受理单类型：置入ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		//取得客户需要取消的产品列表
		Collection cpIDList=inEvent.getColPsid();
		 
		//取得客户ID：置入ServiceContext,取得帐户ID：置入ServiceContext
		int customerID=0;
		int accountID=0;
		if(inEvent.getCsiDto()!=null){
			customerID=inEvent.getCsiDto().getCustomerID();
			accountID=inEvent.getCsiDto().getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

		//进行业务规则检查
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);

		//是否提交,accountid!=0由侯添加,用于区分是有效检查还是计费,在入口的时候,有效检查没有封装帐户信息,不能进入这里,
		if(accountID==0){
			return;
		}
		if(!inEvent.isDoPost()){
		    //创建费用/缴费记录
			Collection shouldPayFeeList = FeeService
					.customerProductOpImmediateFeeCalculator(inEvent
							.getCsiDto(), inEvent.getColPsid(),inEvent.getCsiFeeList(), 0, inEvent
							.getSaID(), this.operatorID);
			
			LogUtility.log(clazz,LogLevel.DEBUG,"设备退还的费用:"+inEvent.getCsiFeeList());
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//计算客户产品发生的信息费
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
		//创建受理单相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		//取消客户产品状态
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());

		cpService.cancelCustomerProduct(cpIDList,inEvent.isSendBackDevice());//新的需求,有硬件产品也可以取消,需要进行设备流转等,换个接口,
		//费用处理和产品费用计算
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		String cpIDstr = "";

		Iterator ite = cpIDList.iterator();
		while (ite.hasNext()) {
			Integer cpId = (Integer) ite.next();
			cpIDstr += cpId + ",";
		}
		// 返回受理单id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));

		//创建系统日志记录
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "产品取消","产品取消，受理单ID:"+csiID
				+",取消产品列表: "+BusinessUtility.getProductDescLogListByPSIDList(cpIDstr),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}

	/**
	 * 迁移客户产品
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void moveCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		//设置受理单类型：置入ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		//取得客户需要取消的产品列表
		Collection cpIDList=inEvent.getColPsid();
		String status=null;
		//取得客户ID：置入ServiceContext,取得帐户ID：置入ServiceContext
		int customerID=0;
		int accountID=0;
		if(inEvent.getCsiDto()!=null){
			customerID=inEvent.getCsiDto().getCustomerID();
			accountID=inEvent.getCsiDto().getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));

		//进行业务规则检查
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);

		//是否提交,accountid!=0由侯添加,用于区分是有效检查还是计费,在入口的时候,有效检查没有封装帐户信息,不能进入这里,
		if(accountID==0){
			return;
		}
		if(!inEvent.isDoPost()){
		    //创建费用/缴费记录
			Collection shouldPayFeeList = FeeService
					.customerProductOpImmediateFeeCalculator(inEvent
							.getCsiDto(), inEvent.getColPsid(),inEvent.getCsiFeeList(), 0, inEvent
							.getSaID(), this.operatorID);
			
			LogUtility.log(clazz,LogLevel.DEBUG,"设备退还的费用:"+inEvent.getCsiFeeList());
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//计算客户产品发生的信息费
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		Collection infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,batchNoDTO);
		
		//创建受理单相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		//取消客户产品状态
		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
				inEvent.getAccountID(),inEvent.getSaID());
		cpService.setTargetSaID(inEvent.getTargetSaID());
		cpService.moveCustomerProduct(cpIDList,inEvent.isSendBackDevice());
//		cpService.cancelCustomerProduct(cpIDList,inEvent.isSendBackDevice());//新的需求,有硬件产品也可以取消,需要进行设备流转等,换个接口,
		//费用处理和产品费用计算
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		String cpIDstr = "";

		Iterator ite = cpIDList.iterator();
		while (ite.hasNext()) {
			Integer cpId = (Integer) ite.next();
			cpIDstr += cpId + ",";
		}
		// 返回受理单id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));	
		//创建系统日志记录
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, "产品迁移","产品迁移，受理单ID:"+csiID+",从业务账户："+inEvent.getSaID()
				+"到业务账户："+inEvent.getTargetSaID()+",迁移产品列表: "+BusinessUtility.getProductDescLogListByPSIDList(cpIDstr),
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	/**
	 * 设备更换
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void swapCustomerDevice(CustomerProductEJBEvent inEvent) throws ServiceException{
		//设备更换和升降级做的差不多的事情，把他们整合到一个方法中。
		alertCustomerProductAndDevice("S",inEvent);
	}
	/**
	 * 设备更换升级
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void upgradeCustomerDevice(CustomerProductEJBEvent inEvent) throws ServiceException{
		//设备升级类设备更换和升降级做的事情，把他们整合到一个方法中。
		alertCustomerProductAndDevice("DU",inEvent);
	}
	/**
	 * 设备更换和升降级做的差不多的事情，把他们整合到一个方法中。
	 * @param actionType ：U表示升级，D表示降级，S表示更换,DU表示设备升级
	 * @param inEvent
	 * @throws ServiceException
	 */
	private void alertCustomerProductAndDevice(String actionType,CustomerProductEJBEvent inEvent)
																				throws ServiceException{
		ServiceContext serviceContext=initServiceContext(inEvent);
		if(!("U".equals(actionType)||"D".equals(actionType)||"S".equals(actionType)||"DU".equals(actionType))){
			LogUtility.log(clazz,LogLevel.WARN,"内部参数传输错误");
			throw new ServiceException("内部参数传输错误");
		}

		//设置受理单类型：置入ServiceContext
		serviceContext.put(Service.CSI_TYPE,inEvent.getCsiDto().getType());
		serviceContext.put(Service.CSI_DTO, inEvent.getCsiDto());
		//取得csiDto
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		//取得客户升降级产品：Collection psidList
		int psID=0;
		int productID=0;
		Collection psidList=inEvent.getColPsid();
		if(psidList==null || psidList.size()==0)
			throw new ServiceException("传入的产品参数有误！");
		Iterator itPSID=psidList.iterator();
		psID=((Integer)itPSID.next()).intValue();
		productID=inEvent.getProductID();
		 
		
		//是否提交
		boolean doPost=inEvent.isDoPost();
		//取得deviceid：升级/降级的可能是设备
		int deviceID=inEvent.getDeviceID();

		//取得客户ID：置入ServiceContext,取得帐户ID：置入ServiceContext
		int customerID=0;
		int accountID=0;
		if(csiDTO!=null){
			customerID=csiDTO.getCustomerID();
			accountID=csiDTO.getAccountID();
		}
		serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
		serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));
		//取得客户以前的设备ID和产品ID
		int oldDeviceID=0;
		int oldProductID=0;
		String destProductName = "";
		String oldProductName ="";
		try{
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(psID));
			oldDeviceID=cp.getDeviceID();
			serviceContext.put("OLDDEVICEID",new Integer(oldDeviceID));
			oldProductID=cp.getProductID();
			serviceContext.put("OLDPRODUCTID",new Integer(oldProductID));
			if ("U".equals(actionType)||"D".equals(actionType)){
			    ProductHome prodHome = HomeLocater.getProductHome();
			    Product pro = prodHome.findByPrimaryKey(new Integer(productID));
			    destProductName = pro.getProductName();
			    Product oldpro = prodHome.findByPrimaryKey(new Integer(oldProductID));
			    oldProductName =oldpro.getProductName();
			}
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"客户产品定位出错，客户产品ID："+psID);
			throw new ServiceException("客户产品定位出错");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.WARN,"客户产品查找出错，客户产品ID："+psID);
			throw new ServiceException("客户产品查找出错");
		}

		//进行业务规则检查
		BusinessRuleService brService=new BusinessRuleService(serviceContext);
		brService.checkOperationCustomerProduct(inEvent);


		//是否提交
		if(!doPost){
		    //创建费用/缴费记录
	    	Collection shouldPayFeeList=null;
			shouldPayFeeList=FeeService.customerProductOpImmediateFeeCalculator(inEvent.getCsiDto(),inEvent.getColPsid(),inEvent.getCsiFeeList(),inEvent.getProductID(),inEvent.getSaID(),this.operatorID);

			//记录人工决定的设备费用，这个由于已经在webaction中已经处理了，只要重新取得在放到shouldPayFeeList里就可以了
			//add by jason.zhou 200-7-17
			//if(inEvent.getCsiFeeList()!=null && inEvent.getCsiFeeList().size()>0)
			//	shouldPayFeeList.addAll(inEvent.getCsiFeeList());
			
			this.response.setPayload(shouldPayFeeList);
			return;
		}
		//立即计算信息费
		Collection infoFeeList=new ArrayList();
		//设备更换不用计算信息费
		BatchNoDTO  batchNoDTO=new BatchNoDTO();
		if(!CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DS.equals(inEvent.getCsiDto().getType())){
			infoFeeList=FeeService.calculateInfoFee(inEvent.getColPsid(),operatorID,"",batchNoDTO);
		}
		//创建受理单相关对象
		CSIService csiService=new CSIService(serviceContext);
		csiService.customerProductOperation(inEvent);

		CustomerProductService cpService=new CustomerProductService(serviceContext,inEvent.getCustomerID(),
			inEvent.getAccountID(),inEvent.getSaID());
		cpService.setOperatorID(operatorID);
		//进行客户产品升级/降级处理
		if("U".equals(actionType)||"D".equals(actionType)){
			LogUtility.log(clazz,LogLevel.DEBUG,"产品升降级处理");
			cpService.alterCustomerProduct(inEvent.getActionType(),psID,productID);
			//对软件产品升降级涉及到的优惠活动进行处理（暂时不考虑）
		}
		//进行设备交换
		else if("S".equals(actionType)||"DU".equals(actionType)){
			LogUtility.log(clazz,LogLevel.DEBUG,"设备更换，参数：psID=" + psID + " ,productID=" + productID + " ，deviceID="+deviceID);
			cpService.swapDevice(psID,productID,deviceID);
		}
		//费用处理和产品费用计算
		CommonFeeAndPayObject commonObj=new CommonFeeAndPayObject();
		commonObj.setSpCache(BusinessUtility.getSpProcessCache(batchNoDTO));
		FeeService.payFeeOperation(inEvent.getCsiDto(),inEvent.getCsiFeeList(),inEvent.getCsiPaymentList(),infoFeeList,inEvent.getCsiPrePaymentDeductionList(),commonObj,operatorID);
		csiService.updateCSIPayStatus(serviceContext,inEvent.getCsiDto().getPaymentStatus());
		//返回受理单id
		int csiID=0;
		if(serviceContext.get(Service.CSI_ID)!=null)
			csiID=((Integer)serviceContext.get(Service.CSI_ID)).intValue();
		this.response.setPayload(new Integer(csiID));

		//创建系统日志记录
		String log="";
		String logDes="";
		if("U".equals(actionType)){
			log="产品升级";
			logDes=log+",受理单ID:"+csiID+","+logDes + "原产品:("+oldProductID+")"+oldProductName+", 升级为:("+productID+")"+destProductName;
		}
		else if("D".equals(actionType)){
			log="产品降级";
			logDes=log+",受理单ID:"+csiID+","+logDes + "原产品:("+oldProductID+")"+oldProductName+", 降级为:("+productID+")"+destProductName;
		}
		else if("S".equals(actionType)){
			log="更换设备";
			logDes=log+",受理单ID:"+csiID+","+logDes+"老设备序列号为:"+inEvent.getOldSerialNo()+", 新设备序列号为:"+inEvent.getNewSeralNo();
			List cawList=(List) serviceContext.get(Service.CAWALLETSERIALNOSWAP);
			if(cawList!=null&&!cawList.isEmpty()){
				logDes+=",关联的CA钱包:";
				for(int i=0;i<cawList.size();i++){
					CAWalletDTO cawDto = (CAWalletDTO) cawList.get(i);
					logDes+=cawDto.getWalletId()+",";
				}
			}
		}
		else if("DU".equals(actionType)){
			log="设备升级";
			logDes=log+",受理单ID:"+csiID+","+logDes+"老设备序列号为:"+inEvent.getOldSerialNo()+", 新设备序列号为:"+inEvent.getNewSeralNo();
			List cawList=(List) serviceContext.get(Service.CAWALLETSERIALNOSWAP);
			if(cawList!=null&&!cawList.isEmpty()){
				logDes+=",关联的CA钱包:";
				for(int i=0;i<cawList.size();i++){
					CAWalletDTO cawDto = (CAWalletDTO) cawList.get(i);
					logDes+=cawDto.getWalletId()+",";
				}
			}
		}
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext),
    			PublicService.getCurrentOperatorID(serviceContext), customerID,
				SystemLogRecorder.LOGMODULE_CUSTSERV, log,logDes,
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	private void createCustomerBillingRule(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		CustomerBillingRuleService service=new CustomerBillingRuleService(serviceContext);
		service.createCustomerBillingRuleService(inEvent.getCustomerBillingRuleDTO());
	}
	
	private void updateCustomerBillingRule(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		CustomerBillingRuleService service=new CustomerBillingRuleService(serviceContext);
		service.updateCustomerBillingRuleService(inEvent.getCustomerBillingRuleDTO());
	}
	
	private void createCatvTerminal(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		CatvTerminalService service=new CatvTerminalService(serviceContext);
		service.createCatvTerminalService(inEvent.getCatvTerminalDTO());
	}
	
	private void updateCatvTerminal(CustomerProductEJBEvent inEvent)throws ServiceException {
		ServiceContext serviceContext=initServiceContext(inEvent);
		CatvTerminalService service=new CatvTerminalService(serviceContext);
		service.updateCatvTerminalService(inEvent.getCatvTerminalDTO());
	}
	
	 /* 初始化ServiceContext
	 * @param event
	 * @return
	 */
	private ServiceContext initServiceContext(CustomerProductEJBEvent event){
		String description="";
		String action="";

		ServiceContext serviceContext=new ServiceContext();
	    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    int customerID=event.getCustomerID();
	    int accountID=event.getAccountID();
	    serviceContext.put(Service.CUSTOMER_ID,new Integer(customerID));
	    serviceContext.put(Service.ACCOUNT_ID,new Integer(accountID));
	    serviceContext.put("OldSerialNo",event.getOldSerialNo());

	    if(event.getCsiDto()!=null)
	    	serviceContext.put(Service.CSI_TYPE,event.getCsiDto().getType());

	    switch (event.getActionType()) {
			//检查
			case EJBEvent.CHECK_PRODUCTPG_CAMPAINPG:
				description="";
				action="";
				break;
			//添加客户产品
			case EJBEvent.PURCHASE:
				description="添加产品";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//客户产品暂停
			case EJBEvent.SUSPEND:
				description="产品暂停";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//客户产品恢复
			case EJBEvent.RESUME:
				description="产品恢复";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//客户产品升级
			case EJBEvent.UPGRADE:
				description="产品升级";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//客户产品降级
			case EJBEvent.DOWNGRADE:
				description="产品降级";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//客户取消产品
			case EJBEvent.CANCEL:
				description="取消产品";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//客户迁移产品
			case EJBEvent.MOVE:
				description="迁移产品";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//设备更换
			case EJBEvent.DEVICESWAP:
				description="设备更换";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			//设备升级
			case EJBEvent.DEVICEUPGRADE:
				description="设备升级";
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				break;
			default:
				description="";
				action="";
	    }
	    serviceContext.put(Service.ACTION_DESCRTIPION,description);
	    serviceContext.put(Service.ACTION_DEFITION,action);

	    return serviceContext;
	}

	/**
	 * 计费
	 * @param event
	 * @return
	 * @throws ServiceException
	 */
	private Collection calculateImmediateFee(CustomerProductEJBEvent event)throws ServiceException{
		Collection protocolList =BusinessUtility.CaculateFeeForProtocol(1,event.getCsiPackageArray(),event.getUsedMonth(),event.getCustomerID());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForExitCust(event.getCsiDto(),event.getCustomerID(),event.getAccountID());

		//如果是协议价客户走协议价计费  add by david.Yang begin
		Map protocolMap =BusinessUtility.getProtocolDTOColByCustID(event.getCustomerID());
		if (protocolMap.isEmpty()){
			Collection currentFeeList=FeeService.calculateImmediateFee(event.getCsiDto(),event.getCsiPackageArray(), event.getCsiCampaignArray(),commonObj);
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

}
