/*
 * Created on 2005-10-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.command.csr;

import java.util.*;

import com.dtv.oss.domain.CatvTerminal;
import com.dtv.oss.domain.CatvTerminalHome;
import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.command.*;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.*;
import com.dtv.oss.service.ejbevent.csr.OpenAccountCheckEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.component.*;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CsrCheckCommand extends Command {
	private static final Class clazz = CsrCheckCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
  
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    OpenAccountCheckEJBEvent inEvent = (OpenAccountCheckEJBEvent)ev;
	    operatorID = inEvent.getOperatorID();
	    machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    try {
	    	switch (inEvent.getActionType()) {
	    		//检查开户时新用户，新用户账户，团购等信息
	    		case OpenAccountCheckEJBEvent.CHECK_CUSTOMERINFO:
	    			checkAccountAndCustomerInfo(inEvent);
	    			break;
	    		//检查所选择的客户产品和优惠的信息
	    		case OpenAccountCheckEJBEvent.CHECK_PRODUCTPG_CAMPAINPG:
	    			checkAccountAndCustomerInfo(inEvent);
	    			break;
	    		//检查客户针对产品对应的硬件设备信息
	    		case OpenAccountCheckEJBEvent.CHECK_HARDPRODUCTINFO:
	    			checkAccountAndCustomerInfo(inEvent);
	    			break;
	    		//检查费用信息是否准确
	    		case OpenAccountCheckEJBEvent.CHECK_FREEINFO:
	    			checkAccountAndCustomerInfo(inEvent);
	    			break;
	    		//计算模拟电视开户费用
	    		case OpenAccountCheckEJBEvent.CACULATE_OPEN_CATV_FEE:
	    			checkAccountAndCustomerInfo(inEvent);	
	    			break;
	    		//检查模拟电视开户
	    		case OpenAccountCheckEJBEvent.CHECK_OPEN_ACCOUNT_CATV:
	    			checkAccountAndCustomerInfo(inEvent);	
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    } catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}
	
	/**
	 * 检查预约，代理商预约，预约开户，门店开户时信息
	 * @param event
	 * @throws ServiceException
	 */
	private void checkAccountAndCustomerInfo(OpenAccountCheckEJBEvent event) throws ServiceException{
		//合并套餐或者促销对应的产品包到event中
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		BusinessRuleService businessRuleService=new BusinessRuleService(initServiceContext(event));
		businessRuleService.checkOpenInfo(event);
		
		if(event.getActionType()==EJBEvent.CHECK_OPEN_ACCOUNT_CATV){
			if(event.getCatvID()!=null && !"".equals(event.getCatvID())){
				if(!BusinessUtility.IsExistCatvID(event.getCatvID())){
					String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
					if(catvName==null || "".equals(catvName))
						catvName="终端编号";
					throw new ServiceException(catvName +"不存在");
				}
				//检查  已经关联到客户 的终端 不能用于重新进行模拟电视开户。
				if(BusinessUtility.IsCustUseCatvID(event.getCatvID())){
					String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
					if(catvName==null || "".equals(catvName))
						catvName="终端编号";
					throw new ServiceException(catvName +"已经被使用");
				}
			}

			
			return;
		}
		
		if(event.getActionType()==EJBEvent.CACULATE_OPEN_CATV_FEE){
			if(event.getCatvID()!=null && !"".equals(event.getCatvID())){
				if(!BusinessUtility.IsExistCatvID(event.getCatvID())){
					String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
					if(catvName==null || "".equals(catvName))
						catvName="终端编号";
					throw new ServiceException(catvName +"不存在");
				}
				//检查  已经关联到客户 的终端 不能用于重新进行模拟电视开户。
				if(BusinessUtility.IsCustUseCatvID(event.getCatvID())){
					String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
					if(catvName==null || "".equals(catvName))
						catvName="终端编号";
					throw new ServiceException(catvName +"已经被使用");
				}
			}
			this.response.setPayload(calculateImmediateFee(event));
			return;
		}
		
		 //检查客户登陆ID
	    if(event.getNewCustInfo().getLoginID()!=null&&(!event.getNewCustInfo().getLoginID().equals(""))){
	       	if(BusinessUtility.isExitMultiLoginID(0,event.getNewCustInfo().getLoginID())){
	       		throw new ServiceException("请更换客户登陆ID，该ID号已经存在！");
	    	}
	    }
			
		if (event.getActionType()==EJBEvent.CHECK_CUSTOMERINFO && 
				event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CO)){
			this.response.setPayload(calculateImmediateFee(event));
			return;
		}
		
		if(event.getActionType()==EJBEvent.CHECK_PRODUCTPG_CAMPAINPG && 
				event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK)){
			this.response.setPayload(calculateImmediateFee(event));
		}
		
		//判断是否需要返回设备类型,用于 门店开户、预约开户
		if(event.getActionType()==EJBEvent.CHECK_PRODUCTPG_CAMPAINPG && 
				(event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS)||
						event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB))){
			
			Collection  deviceClassList =PublicService.getDependencyDeviceClassBygetCsiPackageArray(event.getCsiDto(),event.getDetailNo(),
					event.getCsiPackageArray());
			
			//2008-04-21 获得产品属性列表 ，只要产品属性有设置，就会显示“选择相应硬件设备”页面
			Collection  productPropertyList =PublicService.getProductPropertyArray(event.getCsiPackageArray());
			
            //如果有硬件设备列表，返回设备列表。没有返回费用列表 add by david.Yang   //2008-04-21 获得产品属性列表 ，只要产品属性有设置，就会显示“选择相应硬件设备”页面
			if ((deviceClassList !=null && !deviceClassList.isEmpty()) || (productPropertyList !=null && !productPropertyList.isEmpty()))
			{
			    this.response.setPayload(deviceClassList);
			    this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_DEVICE);
			}
			//直接返回费用列表
			else 
			{
				this.response.setPayload(calculateImmediateFee(event));
				 this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
			}
		}
		//如果输入了硬件设备的话就必须计算费用
		if(event.getActionType()==EJBEvent.CHECK_HARDPRODUCTINFO||
		   event.getActionType()==EJBEvent.CHECK_FREEINFO	){
			this.response.setPayload(calculateImmediateFee(event));
		}
	}
	private Collection calculateImmediateFee(OpenAccountCheckEJBEvent event)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"■■■■■■csi="+event.getCsiDto());
		LogUtility.log(clazz,LogLevel.DEBUG,"■■■■■■NewCustInfo="+event.getNewCustInfo());
		LogUtility.log(clazz,LogLevel.DEBUG,"■■■■■■NewCustAcctInfo="+event.getNewCustAcctInfo());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForOpen(event.getCsiDto(),event.getNewCustInfo(),event.getNewCustAcctInfo());
		//根据是否有团购信息设置团购id和团购券关联的套餐id
		if(event.getDetailNo()!=null && !"".equals(event.getDetailNo())){
			commonObj.setGroupBargainID(BusinessUtility.getGroupBargainDetailByNO(event.getDetailNo()).getGroupBargainID());
		}
		//押金
		commonObj.setDeposit(event.getForcedDeposit());
		
		//如果是模拟电视开户，还要设置终端信息,add by jason 2007-07-27
		if(event.getActionType()==EJBEvent.CACULATE_OPEN_CATV_FEE){
			LogUtility.log(clazz,LogLevel.DEBUG,"■■■■■■模拟电视开户计算费用，开始封装终端信息");
			
			if(event.getCsiDto()!=null)
				commonObj.setInstallationType(event.getCsiDto().getInstallationType());
			
			commonObj.setDestPortNum(event.getAddPortNum());
			//如果存在终端
			try{
				if(BusinessUtility.IsExistCatvID(event.getCatvID())){
					CatvTerminalHome catvTerminalHome=HomeLocater.getCatvTerminalHome();
					CatvTerminal catvTerminal=catvTerminalHome.findByPrimaryKey(event.getCatvID());
					commonObj.setCatvTermType(catvTerminal.getCatvTermType());
					commonObj.setCableType(catvTerminal.getCableType());
					commonObj.setBiDirectionFlag(catvTerminal.getBiDirectionFlag());
					commonObj.setOrgPortNum(catvTerminal.getPortNo());
					
					//如果有新增端口
					if(event.getAddPortNum()>0)
						commonObj.setInstallOnOrgPort(CommonConstDefinition.CATV_OPEN_TYPE_A);
					else
						commonObj.setInstallOnOrgPort(CommonConstDefinition.CATV_OPEN_TYPE_O);
				}
				else{
					CatvTerminalDTO catvTerminalDTO=event.getCatvTerminalDTO();
					if(catvTerminalDTO!=null){
						commonObj.setCatvTermType(catvTerminalDTO.getCatvTermType());
						commonObj.setCableType(catvTerminalDTO.getCableType());
						commonObj.setBiDirectionFlag(catvTerminalDTO.getBiDirectionFlag());
						commonObj.setOrgPortNum(0);
						commonObj.setInstallOnOrgPort(CommonConstDefinition.CATV_OPEN_TYPE_NEW);
					}
				}
			}
			catch(Exception e){
				LogUtility.log(clazz,LogLevel.WARN,"■■■■■■模拟电视开户异常="+e);
				throw new ServiceException("模拟电视开户终端信息不全，无法完成计费");
			}
			LogUtility.log(clazz,LogLevel.DEBUG,"■■■■■■模拟电视开户计算费用，结束封装终端信息：" + commonObj.toString());		
		}
		
		Collection currentFeeList=FeeService.calculateImmediateFeeForOpen(event.getCsiDto(),event.getCsiPackageArray(), event.getCsiCampaignArray(),commonObj);
		return currentFeeList;
	}
	
	private ServiceContext initServiceContext(OpenAccountCheckEJBEvent event){
		ServiceContext context=new ServiceContext();
		context.put(Service.OPERATIOR_ID, new Integer(event.getOperatorID()));
		context.put(Service.REMOTE_HOST_ADDRESS, event.getRemoteHostAddress());
		return context;
	}
}
