/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import java.util.*;

import javax.ejb.FinderException;
import javax.ejb.CreateException;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.csr.CustomerCommand;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.*;
import com.dtv.oss.service.util.ImmediateFee.BillingObject;
import com.dtv.oss.domain.*;
import com.dtv.oss.dto.*;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.util.*;
/**
 * @author Leon Liu
 *
 * 提供公共服务的组件
 * 
 */
public class PublicService {
	
	private static final Class clazz = PublicService.class;
	
	public static PublicService getInstance() {
		return new PublicService();
	}
	
	public int locateOrgIdByDistrictID(int districtid, String role) {
		boolean run = true;
		int resultOrgID = 0;
		RoleOrganizationDTO dto;
		
		int orgid = BusinessUtility.getGovernedOrgIDByDistrictID(districtid);
		while(run) {
			if (orgid == 0) break;
			Collection roleOrgList = BusinessUtility.getRoleOrgListByOrgId(orgid, role);
			if (roleOrgList == null ||roleOrgList.isEmpty()){
				orgid = BusinessUtility.getParentOrgIDByOrgID(orgid);
				continue;
			}
			if (roleOrgList.size() == 1) {
				dto = (RoleOrganizationDTO)roleOrgList.iterator().next();
				resultOrgID = dto.getServiceOrgId();
				break;
			}
			Iterator iterator = roleOrgList.iterator();
			while(iterator.hasNext()) {
				dto = (RoleOrganizationDTO)iterator.next();
				if ("Y".equals(dto.getIsFirst())) {
					resultOrgID = dto.getServiceOrgId();
					break;
				}
			}
			if (resultOrgID != 0) break;		
		}
		
		return resultOrgID;
	}
	
	public static int getCurrentOperatorID(ServiceContext serviceContext){
		return ((Integer)serviceContext.get(Service.OPERATIOR_ID)).intValue();
	}
	
	public static  String getRemoteHostAddress(ServiceContext serviceContext){
		return (String)serviceContext.get(Service.REMOTE_HOST_ADDRESS);
	}
	/**
	 * 处理开户，预约开户，预约时团购券的相关信息
	 * @param gbdetail
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void dealOpenAccountRealationGroupBargain(CustServiceInteractionDTO csiDTO,boolean isLock,String status)throws  ServiceException{
		try {
			
			GroupBargainDetailHome gbDetailHome = HomeLocater.getGroupBargainDetailHome();
			GroupBargainDetail gbdetail=gbDetailHome.findByPrimaryKey(new Integer(csiDTO.getGroupCampaignID()));
			//更新团购明细信息
			if(isLock){
				gbdetail.setUsedDate(TimestampUtility.getCurrentDateWithoutTime());
				gbdetail.setUserID(csiDTO.getCustomerID());
				//更新团购头信息
				GroupBargainHome groupBargainHome =HomeLocater.getGroupBargainHome();
				GroupBargain groupBargain=groupBargainHome.findByPrimaryKey(new Integer(gbdetail.getGroupBargainID()));
				groupBargain.setUsedSheets(groupBargain.getUsedSheets()+1);
				groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				if ((groupBargain.getUsedSheets()+ groupBargain.getAbortsheets())== groupBargain.getTotalSheets())
					groupBargain.setStatus("F");
			}
			//预约开户或者是们店开户是状态设置为 回收CommonConstDefinition.GROUPBARGAINDETAILSTATUS_RETURN
			gbdetail.setStatus(status);
			gbdetail.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		}catch(HomeFactoryException e) {
		    LogUtility.log(PublicService.class,LogLevel.ERROR,"dealOpenAccountRealationGroupBargain",e);
		    throw new ServiceException("处理团购相关信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(PublicService.class,LogLevel.ERROR,"dealOpenAccountRealationGroupBargain",e);
		    throw new ServiceException("处理团购相关信息时查找错误");
    	}
	}
	/**
	 * 处理预约开户/门店开户受理不成功团购信息
	 * @param gbdetail
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void cancelOpenAccountRealationGroupBargain(CustServiceInteractionDTO csiDTO)throws  ServiceException{
		try {
			
			GroupBargainDetailHome gbDetailHome = HomeLocater.getGroupBargainDetailHome();
			GroupBargainDetail gbdetail=gbDetailHome.findByPrimaryKey(new Integer(csiDTO.getGroupCampaignID()));
			//预约开户或者是们店开户是状态设置为 回收CommonConstDefinition.GROUPBARGAINDETAILSTATUS_RETURN
			gbdetail.setStatus(CommonConstDefinition.GROUPBARGAINDETAILSTATUS_SOLD);
			gbdetail.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			gbdetail.setUserID(0);
			gbdetail.setUsedDate(null);
			//更新团购明细信息
			GroupBargainHome groupBargainHome =HomeLocater.getGroupBargainHome();
			GroupBargain groupBargain=groupBargainHome.findByPrimaryKey(new Integer(gbdetail.getGroupBargainID()));
			if(CommonConstDefinition.GROUPBARGAIN_STATUS_FINISH.equals(groupBargain.getStatus())){
				groupBargain.setUsedSheets(groupBargain.getUsedSheets()-1);
				groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				groupBargain.setStatus(CommonConstDefinition.GROUPBARGAIN_STATUS_SALED);
			}else  if(CommonConstDefinition.GROUPBARGAIN_STATUS_SALED.equals(groupBargain.getStatus())){
				groupBargain.setUsedSheets(groupBargain.getUsedSheets()-1);
				groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(PublicService.class,LogLevel.ERROR,"dealOpenAccountRealationGroupBargain",e);
		    throw new ServiceException("处理团购相关信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(PublicService.class,LogLevel.ERROR,"dealOpenAccountRealationGroupBargain",e);
		    throw new ServiceException("处理团购相关信息时查找错误");
    	}
	}
	
	/**
	 * 通过CSIPackageArray或者团购券detailNo获得所依赖的硬件色后备类型
	 * add by zhouxushun , 2005-11-17
	 * @param detailNo : 团购券detailNo
	 * @param csiPackageArray : 产品包列表，封装格式为Integer对象
	 * @return  ：Collection封装为 DeviceClassDTO  对象的列表
	 * @throws ServiceException
	 */
	public static Collection getDependencyDeviceClassBygetCsiPackageArray(CustServiceInteractionDTO csiDTO,String detailNo,Collection csiPackageArray)throws ServiceException{
		
		LogUtility.log(clazz,LogLevel.DEBUG, "进入返回设备类型方法的调用");

		
		if(detailNo==null && csiPackageArray==null){
			LogUtility.log(clazz,LogLevel.WARN, "参数错误");
			throw new ServiceException("参数错误");
		}
		Collection deviceClassList=new ArrayList();
		
		Collection productList=null;
		
		//由于团购的时候已经把产品包设置进去了,所以这里和正常开户是一样的,不需要区分处理
		productList=BusinessUtility.getCustomerProductDTOListByPackageIDList(csiPackageArray);
		String condStr=BusinessUtility.getSystemsettingValueByName("SET_D_DEVICEPURPOSE");
/*	
		//这里用来表示是否启用设备用途
		boolean isDeviceMtchStart=false;
		if(CommonConstDefinition.YESNOFLAG_YES.equals(condStr)&& csiDTO.getCreateReason()!=null 
			&& !"".equals(csiDTO.getCreateReason()))
			isDeviceMtchStart=true;
*/		
		Iterator itProductDto=productList.iterator();
		while(itProductDto.hasNext()){
			CustomerProductDTO cpDto=(CustomerProductDTO)itProductDto.next();
			//DeviceClassDTO deviceClassDto=BusinessUtility.getDeviceClassDTOByProductID(cpDto.getProductID());
			Collection deviceModelList=BusinessUtility.getDeviceModelDTOByProductID(cpDto.getProductID());
			if(deviceModelList!=null && deviceModelList.size()>0){
				deviceClassList.addAll(deviceModelList);
			}
		}
		LogUtility.log(clazz,LogLevel.DEBUG, "返回的设备类型列表为:" + deviceClassList.toString());
		
		return deviceClassList;
	}
	
	/**
	 * 退还押金的操作
	 * @param forcedDepositDTO
	 * @param csiDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void widthdrawForcedDeposit(Collection  forcedDepositCol,CustServiceInteractionDTO csiDto,ServiceContext serviceContext)throws ServiceException{
		if(forcedDepositCol!=null && !forcedDepositCol.isEmpty()){
			Iterator currentIter=forcedDepositCol.iterator();
			while(currentIter.hasNext()){
				ForcedDepositDTO  forcedDepositDTO=(ForcedDepositDTO)currentIter.next();
				widthdrawForcedDeposit(forcedDepositDTO,csiDto,serviceContext);
			}
		}
	}
	
	
	
	/**
	 * 退还押金的操作
	 * @param forcedDepositDTO
	 * @param csiDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void widthdrawForcedDeposit(ForcedDepositDTO  forcedDepositDTO,CustServiceInteractionDTO csiDto,ServiceContext serviceContext)throws ServiceException{
		try {
			//取得受理单信息
			forcedDepositDTO.setCustomerID(csiDto.getCustomerID());
			forcedDepositDTO.setWithdrawCsiID(csiDto.getId());
			//取得已有的押金信息
			ForcedDepositHome forcedDepositHome=HomeLocater.getForcedDepositHome();
			ForcedDeposit forcedDeposit=forcedDepositHome.findByPrimaryKey(new Integer(forcedDepositDTO.getSeqNo()));
			
			//设置相关操作员信息
			Integer operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
			OperatorHome homeOpe = HomeLocater.getOperatorHome();
			Operator operator = homeOpe.findByPrimaryKey(operatorID);
			forcedDeposit.setProcessOperatorID(operatorID.intValue());
			forcedDeposit.setProcessOrgID(operator.getOrgID());
			forcedDeposit.setProcessTime(TimestampUtility.getCurrentTimestamp());
			//设置押金和状态信息
			forcedDeposit.setSeizureMoney(forcedDeposit.getMoney()-forcedDepositDTO.getWithdrawMoney());
			forcedDeposit.setWithdrawMoney(forcedDepositDTO.getWithdrawMoney());
			if(forcedDeposit.getSeizureMoney()==0){
				forcedDeposit.setStatus(CommonConstDefinition.FDSTATUS_BACK);
			}else if(forcedDeposit.getSeizureMoney()==forcedDeposit.getMoney()){
				forcedDeposit.setStatus(CommonConstDefinition.FDSTATUS_CONFISCATE);
			}else if(forcedDeposit.getSeizureMoney()>0){
				forcedDeposit.setStatus(CommonConstDefinition.FDSTATUS_PART_CONFISCATE);
			}
			//创建回收的押金信息
			ForcedDeposit forcedDepositCreate=forcedDepositHome.create(forcedDepositDTO);
			forcedDepositDTO.setSeqNo(forcedDeposit.getSeqNo().intValue());
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建退还押金纪录时定位错误");
		}catch(CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建退还押金纪录时找不到相关信息");
			
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,e);
		    throw new ServiceException("创建退还押金纪录时查找操作员错误");	            		
    		
    	}
	}
	/**
	 * 收取押金的操作
	 * @param forcedDepositDTO
	 * @param csiDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void createForcedDeposit(ForcedDepositDTO  forcedDepositDTO,CustServiceInteractionDTO csiDto,ServiceContext serviceContext)throws ServiceException{
		try {
			//取得受理单信息
			forcedDepositDTO.setCustomerID(csiDto.getCustomerID());
			forcedDepositDTO.setCsiID(csiDto.getId());
			//设置相关操作员信息
			Integer operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
			OperatorHome homeOpe = HomeLocater.getOperatorHome();
			Operator operator = homeOpe.findByPrimaryKey(operatorID);
			forcedDepositDTO.setCreateOperator(operatorID.intValue());
		    forcedDepositDTO.setCreateorgID(operator.getOrgID());
		    forcedDepositDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
		    forcedDepositDTO.setStatus(CommonConstDefinition.FDSTATUS_CREATE);
			ForcedDepositHome forcedDepositHome=HomeLocater.getForcedDepositHome();
			ForcedDeposit forcedDeposit=forcedDepositHome.create(forcedDepositDTO);
			forcedDepositDTO.setSeqNo(forcedDeposit.getSeqNo().intValue());
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建收取押金纪录时定位错误");
		}catch(CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建收取押金纪录时找不到相关信息");
			
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,e);
		    throw new ServiceException("创建收取押金纪录时查找操作员错误");	            		
    		
    	}
	}
//	/**
//	 * 取消客户促销产品
//	 * @param custCampaignDTO
//	 */
//	public static void closeCusCampaign(CustomerCampaignDTO custCampaignDTO)throws ServiceException{
//		try{
//			//修改客户促销
//			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
//			CustomerCampaign cusCamp=customerCampaignHome.findByPrimaryKey(new Integer(custCampaignDTO.getCcID()));
//			cusCamp.ejbUpdate(custCampaignDTO);
//			//更新客户产品促销
//			CPCampaignMapDTO cpcCampDto=new CPCampaignMapDTO();
//			cpcCampDto.setStatus(CommonConstDefinition.CPCM_STATUS_CANCEL);
//			cpcCampDto.setTimeEnd(TimestampUtility.getCurrentDate());
//			CPCampaignMapHome cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
//			Collection col = cPCampaignMapHome.findByCustomerCampaign(new Integer(custCampaignDTO.getCcID()));
//			if(!(col==null||col.isEmpty())){
//				Iterator iter=col.iterator();
//				while(iter.hasNext()){
//					CPCampaignMap cPCampaignMap=(CPCampaignMap)iter.next();
//					cpcCampDto.setDtLastmod(cPCampaignMap.getDtLastmod());
//					cPCampaignMap.ejbUpdate(cpcCampDto);
//				}
//			}
//			
//		}catch(HomeFactoryException e1) {
//			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e1);
//			throw new ServiceException("检查客户产品的依赖性关系时定位错误。");
//		}catch(FinderException e2) {
//			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e2);
//			throw new ServiceException("检查客户产品的依赖性关系时查找错误。");
//		}
//	}
//	/**
//	 * 更新客户产品促销信息
//	 * @param cpCampaignMapDTO
//	 * @throws ServiceException 
//	 */
//	public static void MantainCpCampaignMap(CPCampaignMapDTO cpCampaignMapDTO) throws ServiceException{
//		try{
//			//更新客户产品促销
//			CPCampaignMapHome cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
//			Collection col = cPCampaignMapHome.findByCustomerCampaign(new Integer(cpCampaignMapDTO.getCcId()));
//			if(!(col==null||col.isEmpty())){
//				Iterator iter=col.iterator();
//				while(iter.hasNext()){
//					CPCampaignMap cPCampaignMap=(CPCampaignMap)iter.next();
//					cpCampaignMapDTO.setDtLastmod(cPCampaignMap.getDtLastmod());
//					cPCampaignMap.ejbUpdate(cpCampaignMapDTO);
//				}
//			}			
//		}catch(HomeFactoryException e1) {
//			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e1);
//			throw new ServiceException("更新客户产品促销定位错误。");
//		}catch(FinderException e2) {
//			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
//			throw new ServiceException("更新客户产品促销查找错误。");
//		}
//	}
//	/**
//	 * 延迟客户产品促销
//	 * @param dto
//	 * @throws ServiceException
//	 */
//	public static CustomerCampaign delayCusCampaign(CustomerCampaignDTO dto) throws ServiceException{
//		try{
//			//延迟客户产品促销
//			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
//			CustomerCampaign cusCamp=customerCampaignHome.findByPrimaryKey(new Integer(dto.getCcID()));
//			cusCamp.ejbUpdate(dto);	
//			//延迟客户产品CPCMAP
//			CPCampaignMapDTO cpcCampDto=new CPCampaignMapDTO();
//			cpcCampDto.setTimeEnd(dto.getDateTo());
//			CPCampaignMapHome cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
//			Collection col = cPCampaignMapHome.findByCustomerCampaign(new Integer(dto.getCcID()));
//			if(!(col==null||col.isEmpty())){
//				Iterator iter=col.iterator();
//				while(iter.hasNext()){
//					CPCampaignMap cPCampaignMap=(CPCampaignMap)iter.next();
//					cpcCampDto.setDtLastmod(cPCampaignMap.getDtLastmod());
//					cPCampaignMap.ejbUpdate(cpcCampDto);
//				}
//			}
//			return cusCamp;
//		}catch(HomeFactoryException e1) {
//			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e1);
//			throw new ServiceException("更新客户产品促销定位错误。");
//		}catch(FinderException e2) {
//			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
//			throw new ServiceException("更新客户产品促销查找错误。");
//		}
//	}
//	/**
//	 * 创建新的客户产品促销记录
//	 * @param dto
//	 * @throws ServiceException 
//	 * @throws  
//	 */
//	public static CustomerCampaign grantNewCampaign(CustomerCampaignDTO dto) throws ServiceException{
//		try{
//			dto.setStatus(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL);
//			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
//			CustomerCampaign customerCampaign =customerCampaignHome.create(dto);
//			//更新cpcmap
//			HashMap custProdMap=BusinessUtility.getProductAndCutProdMapBySAID(dto.getServiceAccountID());
//			if(!custProdMap.isEmpty()){
//				CPCampaignMapHome cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
//				Collection col2=BusinessUtility.getOldProductCondByCampID(dto.getCampaignID());
//				CampaignDTO campaignDTO=BusinessUtility.getCampaignDTOByID(dto.getCampaignID());
//				CPCampaignMapDTO cpcCampDto=new CPCampaignMapDTO();
//				cpcCampDto.setAllowAlter(campaignDTO.getAllowAlter());
//				cpcCampDto.setAllowClose(campaignDTO.getAllowClose());
//				cpcCampDto.setAllowPause(campaignDTO.getAllowPause());
//				cpcCampDto.setAllowTransfer(campaignDTO.getAllowTransfer());
//				cpcCampDto.setAllowTransition(campaignDTO.getAllowTransition());
//				cpcCampDto.setCcId(customerCampaign.getCcID().intValue());
//				cpcCampDto.setTimeStart(customerCampaign.getDateFrom());
//				cpcCampDto.setTimeEnd(customerCampaign.getDateTo());
//				cpcCampDto.setCampaignID(customerCampaign.getCampaignID());
//				cpcCampDto.setStatus(CommonConstDefinition.CPCM_STATUS_VALID);
//				Iterator iter=col2.iterator();
//				while(iter.hasNext()){
//					
//					CampaignOldProductCondDTO campaignOldProductCondDTO=(CampaignOldProductCondDTO)iter.next();
//					CustomerProductDTO custProdDTO=(CustomerProductDTO)custProdMap.get(new Integer(campaignOldProductCondDTO.getOldProductId()));
//					cpcCampDto.setCustProductID(custProdDTO.getPsID());
//					cPCampaignMapHome.create(cpcCampDto);
//				}
//			}
//			return customerCampaign;
//		}catch(HomeFactoryException e1) {
//			LogUtility.log(clazz,LogLevel.ERROR,"grantNewCampaign",e1);
//			throw new ServiceException("创建新的客户产品促销记录定位错误。");
//		}catch(CreateException e) {
//			LogUtility.log(clazz, LogLevel.ERROR,e);
//			throw new ServiceException("创建新的客户产品促销记录出现错误");		
//		}
//	}
//	
	public static AccountItemDTO getCloneAccountItemDTO(AccountItemDTO currentFee){
		AccountItemDTO dto =new AccountItemDTO();
		dto.setAcctID(currentFee.getAcctID());
		dto.setAcctItemTypeID(currentFee.getAcctItemTypeID());
		dto.setAiNO(currentFee.getAiNO());
		dto.setBatchNO(currentFee.getBatchNO());
		dto.setBillingCycleID(currentFee.getBillingCycleID());
		dto.setCreateTime(currentFee.getCreateTime());
		dto.setCustID(currentFee.getCustID());
		dto.setDate1(currentFee.getDate1());
		dto.setDate2(currentFee.getDate2());
		dto.setDtCreate(currentFee.getDtCreate());
		dto.setDtLastmod(currentFee.getDtLastmod());
		dto.setForcedDepositFlag(currentFee.getForcedDepositFlag());
		dto.setInvoiceFlag(currentFee.getInvoiceFlag());
		dto.setInvoiceNO(currentFee.getInvoiceNO());
		dto.setOrgID(currentFee.getOrgID());
		dto.setOperatorID(currentFee.getOperatorID());
		dto.setPsID(currentFee.getPsID());
		dto.setReferID(currentFee.getReferID());
		dto.setReferType(currentFee.getReferType());
		dto.setServiceAccountID(currentFee.getServiceAccountID());
		dto.setSetOffAmount(currentFee.getSetOffAmount());
		dto.setSetOffFlag(currentFee.getSetOffFlag());
		dto.setStatus(currentFee.getStatus());
		dto.setFeeType(currentFee.getFeeType());
		dto.setValue(currentFee.getValue());
		dto.setCreatingMethod(currentFee.getCreatingMethod());
		return dto;
	}
	
	public static PaymentRecordDTO getClonePaymentDTO(PaymentRecordDTO currentPay){
		PaymentRecordDTO dto = new PaymentRecordDTO();
		dto.setAcctID(currentPay.getAcctID());
		dto.setAmount(currentPay.getAmount());
		dto.setCreateTime(currentPay.getCreateTime());
		dto.setCustID(currentPay.getCustID());
		dto.setDtCreate(currentPay.getDtCreate());
		dto.setDtLastmod(currentPay.getDtLastmod());
		dto.setInvoicedFlag(currentPay.getInvoicedFlag());
		dto.setInvoiceNo(currentPay.getInvoiceNo());
		dto.setMopID(currentPay.getMopID());
		dto.setOpID(currentPay.getOpID());
		dto.setOrgID(currentPay.getOrgID());
		dto.setPaidTo(currentPay.getPaidTo());
		dto.setPaymentTime(currentPay.getPaymentTime());
		dto.setPayType(currentPay.getPayType());
		dto.setPrepaymentType(currentPay.getPrepaymentType());
		dto.setSeqNo(currentPay.getSeqNo());
		dto.setSourceRecordID(currentPay.getSourceRecordID());
		dto.setSourceType(currentPay.getSourceType());
		dto.setStatus(currentPay.getStatus());
		dto.setTicketNo(currentPay.getTicketNo());
		dto.setTicketType(currentPay.getTicketType());
		dto.setReferID(currentPay.getReferID());
		dto.setReferType(currentPay.getReferType());
		return dto;
	}
	
	public static PrepaymentDeductionRecordDTO getClonePrepaymentDeductionRecordDTO(PrepaymentDeductionRecordDTO currentPrepay){
		PrepaymentDeductionRecordDTO dto = new PrepaymentDeductionRecordDTO();
		dto.setAcctId(currentPrepay.getAcctId());
		dto.setAmount(currentPrepay.getAmount());
		dto.setCreateTime(currentPrepay.getCreateTime());
		dto.setCreatingMethod(currentPrepay.getCreatingMethod());
		dto.setCustId(currentPrepay.getCustId());
		dto.setDtCreate(currentPrepay.getDtCreate());
		dto.setDtLastmod(currentPrepay.getDtLastmod());
		dto.setInvoicedFlag(currentPrepay.getInvoicedFlag());
		dto.setInvoiceNo(currentPrepay.getInvoiceNo());
		dto.setOpId(currentPrepay.getOpId());
		dto.setOrgId(currentPrepay.getOrgId());
		dto.setPrepaymentType(currentPrepay.getPrepaymentType());
		dto.setReferRecordId(currentPrepay.getReferRecordId());
		dto.setReferRecordType(currentPrepay.getReferRecordType());
		dto.setSeqNo(currentPrepay.getSeqNo());
		dto.setStatus(currentPrepay.getStatus());
		return dto;

	}
	
	public static Address createAddressBakup(int addressID) throws ServiceException{
		try{
		   AddressHome addressHome =HomeLocater.getAddressHome();
		   Address     address =addressHome.findByPrimaryKey(new Integer(addressID));
		   AddressDTO addressDto =new AddressDTO();
		   addressDto.setAddressID(address.getAddressID().intValue());
		   addressDto.setDetailAddress(address.getDetailAddress());
		   addressDto.setDistrictID(address.getDistrictID());
		   addressDto.setPostcode(address.getPostcode());
		   Address  createAddress =addressHome.create(addressDto);
		   createAddress.setDtLastmod(address.getDtLastmod());
		   return createAddress;
		}catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"createAddressBakup",e1);
			throw new ServiceException("创建客户地址定位错误。");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
			throw new ServiceException("客户地址查找错误。");
		}catch(CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("创建客户地址记录出现错误");		
		}
	}
	
	/**
	 * 根据分割符号把字符串分成相应目标对象的列表，目前目标对象只实现”Integer/String“
	 * @param str：需要分割的字符串
	 * @param splitSymbol：分割符，如”，“，如果分割符为空，则把str根据targetObj类型转换，并返回该类型的的一个对象列表
	 * @param targetObj：Integer/String，不区分大小写，如果不为二者，则返回一个str的对象列表
	 * @return
	 */
	public static Collection stringSplitToTargetObject(String str,String splitSymbol,String targetObj){
		if(str==null || "".equals(str))return null;
		Collection resultList=new ArrayList();
		if(splitSymbol==null || "".equals(splitSymbol)){
			try{
				if("Integer".equalsIgnoreCase(targetObj))
					resultList.add(new Integer(str));
				else if("String".equalsIgnoreCase(targetObj))
					resultList.add(str);
				else
					resultList.add(str);
			}
			catch(Exception e){
				LogUtility.log(clazz, LogLevel.WARN, "stringSplitToTargetObject()出错:"+e);
				resultList=null;
			}
		}
		else{
			try{
				String objs[]=str.split(splitSymbol);
				for(int i=0;i<objs.length;i++){
					if("Integer".equalsIgnoreCase(targetObj))
						resultList.add(new Integer(objs[i]));
					else if("String".equalsIgnoreCase(targetObj))
						resultList.add(objs[i]);
					else
						resultList.add(objs[i]);
				}
			}
			catch(Exception e){
				LogUtility.log(clazz, LogLevel.WARN, "stringSplitToTargetObject()出错:"+e);
				resultList=null;
			}	
		}
		return resultList;
	}
 	/**
 	 * 用来封装计算一次性费用的用于公用接口的参数对象(用于预约/预约开户/门店开户时)
 	 * @param csiDto    CustServiceInteractionDTO
 	 * @param customerDTO  NewCustomerInfoDTO
 	 * @param accountDTO  NewCustAccountInfoDTO
 	 * @return
 	 */
 	public static CommonFeeAndPayObject encapsulateBaseParamForOpen(CustServiceInteractionDTO csiDto,NewCustomerInfoDTO csiCustDTO,NewCustAccountInfoDTO cisAcctDTO){
 		CustomerDTO customerDTO=new CustomerDTO();
 		customerDTO.setCustomerType(csiCustDTO.getType());
 		AccountDTO accountDTO=new AccountDTO();
 		accountDTO.setAccountType(cisAcctDTO.getAccountType());
 		accountDTO.setAccountName(cisAcctDTO.getAccountName());
 		CommonFeeAndPayObject paramObj=encapsulateBaseParam(csiDto,customerDTO,accountDTO);
 		return paramObj;
 	}
	/**
	 * 用来封装计算一次性费用的用于公用接口的参数对象(对系统中已经促在的客户)
	 * @param csiDto   CustServiceInteractionDTO
	 * @param customerID  客户id
	 * @param accountID    账户id
	 * @return
	 */
	public static CommonFeeAndPayObject encapsulateBaseParamForExitCust(CustServiceInteractionDTO csiDto,int customerID,int accountID){
		CustomerDTO customerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerID);
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(accountID);
		CommonFeeAndPayObject paramObj=encapsulateBaseParam(csiDto,customerDTO,accountDTO);
		return paramObj;
	}
	/**
	 * 用来封装计算一次性费用的用于公用接口的参数对象(对系统中已经促在的客户)
	 * @param csiDto   CustServiceInteractionDTO
	 * @param customerID  客户id
	 * @param accountDTO    账户信息
	 * @return
	 */
	public static CommonFeeAndPayObject encapsulateBaseParamForOpenAcct(CustServiceInteractionDTO csiDto,int customerID,AccountDTO accountDTO){
		CustomerDTO customerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerID);
		CommonFeeAndPayObject paramObj=encapsulateBaseParam(csiDto,customerDTO,accountDTO);
		return paramObj;
	}
	/**
	 * 用来封装计算一次性费用的用于公用接口的参数对象
	 * @param csiDto
	 * @param customerDTO
	 * @param accountDTO
	 * @return
	 */
	public static CommonFeeAndPayObject encapsulateBaseParam(CustServiceInteractionDTO csiDto,CustomerDTO customerDTO,AccountDTO accountDTO){
		CommonFeeAndPayObject paramObj=new CommonFeeAndPayObject();
		paramObj.setCustomerID(customerDTO.getCustomerID());
		paramObj.setCustType(customerDTO.getCustomerType());
		paramObj.setAccountID(accountDTO.getAccountID());
		paramObj.setAccountName(accountDTO.getAccountName());
		paramObj.setAcctType(accountDTO.getAccountType());
		paramObj.setGroupBargainID(csiDto.getGroupCampaignID());
		paramObj.setContractNo(customerDTO.getContractNo());
		return paramObj;
	}
	/**
	 * 封装计费条件对象(目前用于开户/增机/新增产品的情况下)
	 * @param paramObj   计费和支付参数对象
	 * @param packageCol  产品包集合
	 * @param campaignCol  促销集合
	 * @return
	 * @throws ServiceException
	 */
	public static Collection encapsulateBillingObject(CommonFeeAndPayObject paramObj,Collection packageCol,Collection campaignCol)throws ServiceException{
		Collection billingObjectCol=new ArrayList();
		if((packageCol==null || packageCol.isEmpty())&&
			(campaignCol==null || campaignCol.isEmpty())){
			BillingObject billObj=new BillingObject();
			billObj.setAccountID(paramObj.getAccountID());
			billObj.setAcctType(paramObj.getAcctType());
			billObj.setCustomerID(paramObj.getCustomerID());
			billObj.setCustType(paramObj.getCustType());
			billObj.setContractNo(paramObj.getContractNo());
			billObj.setServiceAccountID(paramObj.getServiceAccountID());
			billObj.setGroupBargainID(paramObj.getGroupBargainID());
			billObj.setDestProductID(paramObj.getDestProductID());
			billObj.setSHasPackage("N");
			
			billObj.setCATVTermType(paramObj.getCatvTermType());
			billObj.setCableType(paramObj.getCableType());
			billObj.setBiDirectionFlag(paramObj.getBiDirectionFlag());
			billObj.setOrgPortNum(paramObj.getOrgPortNum());
			billObj.setDestPortNum(paramObj.getDestPortNum());
			
			billObj.setUserType(paramObj.getUserType());
			billObj.setInstallationType(paramObj.getInstallationType());
			billObj.setInstallOnOrgPort(paramObj.getInstallOnOrgPort());
			
			billingObjectCol.add(billObj);
		}
		if(paramObj.getGroupBargainID()>0){
			BillingObject billObj=new BillingObject();
			billObj.setAccountID(paramObj.getAccountID());
			billObj.setAcctType(paramObj.getAcctType());
			billObj.setCustomerID(paramObj.getCustomerID());
			billObj.setCustType(paramObj.getCustType());
			billObj.setContractNo(paramObj.getContractNo());
			billObj.setServiceAccountID(paramObj.getServiceAccountID());
			billObj.setGroupBargainID(paramObj.getGroupBargainID());
			billObj.setDestProductID(paramObj.getDestProductID());
			billObj.setBOType("G");
			
			billObj.setCATVTermType(paramObj.getCatvTermType());
			billObj.setCableType(paramObj.getCableType());
			billObj.setBiDirectionFlag(paramObj.getBiDirectionFlag());
			billObj.setOrgPortNum(paramObj.getOrgPortNum());
			billObj.setDestPortNum(paramObj.getDestPortNum());
			
			billObj.setUserType(paramObj.getUserType());
			billObj.setInstallationType(paramObj.getInstallationType());
			billObj.setInstallOnOrgPort(paramObj.getInstallOnOrgPort());
			
			billingObjectCol.add(billObj);
		}
		//建立一个临时的可操作的camapign集合的克隆对象
		Collection conCampaignCol=null;
		if(campaignCol!=null)  conCampaignCol=new ArrayList(campaignCol);
		Collection conPackageCol=null;
		if(packageCol!=null )  conPackageCol=new ArrayList(packageCol);
		//用来存放和产品包有关联的促销
		Collection havepcCol=new ArrayList();
		//建立一个临时的可操作的pakcage集合的克隆对象
		if(conPackageCol!=null && !conPackageCol.isEmpty()){
			Iterator conPackageIter=conPackageCol.iterator();
			while(conPackageIter.hasNext()){
				Integer conPackageID=(Integer)conPackageIter.next();
				BillingObject billObj=new BillingObject();
				billObj.setAccountID(paramObj.getAccountID());
				billObj.setAcctType(paramObj.getAcctType());
				billObj.setCustomerID(paramObj.getCustomerID());
				billObj.setCustType(paramObj.getCustType());
				billObj.setContractNo(paramObj.getContractNo());
				billObj.setServiceAccountID(paramObj.getServiceAccountID());
				billObj.setGroupBargainID(paramObj.getGroupBargainID());
				billObj.setDestProductID(paramObj.getDestProductID());
				
				billObj.setCATVTermType(paramObj.getCatvTermType());
				billObj.setCableType(paramObj.getCableType());
				billObj.setBiDirectionFlag(paramObj.getBiDirectionFlag());
				billObj.setOrgPortNum(paramObj.getOrgPortNum());
				billObj.setDestPortNum(paramObj.getDestPortNum());
				
				billObj.setUserType(paramObj.getUserType());
				billObj.setInstallationType(paramObj.getInstallationType());
				billObj.setInstallOnOrgPort(paramObj.getInstallOnOrgPort());
				
				HashMap package2ProductMap=new HashMap();
				//这里才以后需要追加对可选包的处理,目前没有追加
				Collection productIDCol=BusinessUtility.getProductIDsByPackageID(conPackageID.intValue());
				package2ProductMap.put(conPackageID, productIDCol);
				billObj.setPackage2ProductMap(package2ProductMap);
				Collection p2cCol=getPackage2CampaignByPackageID(conPackageID.intValue(),campaignCol);
				billObj.setCampaignCol(p2cCol);
				//把p2cCol中的对象和并到havepcCol中去
				mergCollection(p2cCol,havepcCol);
				billingObjectCol.add(billObj);
			}	
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"■havepcCol■"+havepcCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"■conCampaignCol■"+conCampaignCol);
		if(conCampaignCol!=null && !conCampaignCol.isEmpty()){
			conCampaignCol.removeAll(havepcCol);
			LogUtility.log(clazz,LogLevel.DEBUG,"■conCampaignCol remove havepcCol■"+conCampaignCol);
			if(!conCampaignCol.isEmpty()){
				BillingObject billObj=new BillingObject();
				billObj.setAccountID(paramObj.getAccountID());
				billObj.setAcctType(paramObj.getAcctType());
				billObj.setCustomerID(paramObj.getCustomerID());
				billObj.setCustType(paramObj.getCustType());
				billObj.setContractNo(paramObj.getContractNo());
				billObj.setServiceAccountID(paramObj.getServiceAccountID());
				billObj.setGroupBargainID(paramObj.getGroupBargainID());
				billObj.setDestProductID(paramObj.getDestProductID());
				billObj.setCampaignCol(conCampaignCol);
				billObj.setSHasPackage("N");
				
				billObj.setCATVTermType(paramObj.getCatvTermType());
				billObj.setCableType(paramObj.getCableType());
				billObj.setBiDirectionFlag(paramObj.getBiDirectionFlag());
				billObj.setOrgPortNum(paramObj.getOrgPortNum());
				billObj.setDestPortNum(paramObj.getDestPortNum());
				
				billObj.setUserType(paramObj.getUserType());
				billObj.setInstallationType(paramObj.getInstallationType());
				billObj.setInstallOnOrgPort(paramObj.getInstallOnOrgPort());
				
				billingObjectCol.add(billObj);
			}
		}
		
		
		LogUtility.log(PublicService.class, LogLevel.DEBUG,"■■fillBillingObject■■"+billingObjectCol);
		return billingObjectCol;
	}
	public static Collection encapsulateBillingObjectForCust(CommonFeeAndPayObject paramObj,Collection serviceAccountLit,Collection  psIDList)throws ServiceException{
		
		Collection billingObjectCol=new ArrayList();
		if((serviceAccountLit==null || serviceAccountLit.isEmpty())&&
		   (psIDList==null || psIDList.isEmpty())){
			BillingObject billObj=new BillingObject();
			billObj.setAccountID(paramObj.getAccountID());
			billObj.setAcctType(paramObj.getAcctType());
			billObj.setCustomerID(paramObj.getCustomerID());
			billObj.setCustType(paramObj.getCustType());
			billObj.setContractNo(paramObj.getContractNo());
			billObj.setServiceAccountID(paramObj.getServiceAccountID());
			billObj.setGroupBargainID(paramObj.getGroupBargainID());
			billObj.setDestProductID(paramObj.getDestProductID());
			billObj.setSHasPackage("N");
			billingObjectCol.add(billObj);
		}
		if(serviceAccountLit!=null && !serviceAccountLit.isEmpty()){
			Iterator saIDIter=serviceAccountLit.iterator();
			while(saIDIter.hasNext()){
				Integer saID=(Integer)saIDIter.next();
				billingObjectCol.add(fillBillingObject(paramObj,saID.intValue(),null));
			}
		}
		if(psIDList!=null && !psIDList.isEmpty()){
			billingObjectCol.add(fillBillingObject(paramObj,0,psIDList));
		}
		LogUtility.log(PublicService.class, LogLevel.DEBUG,"■■encapsulateBillingObjectForCust■■"+billingObjectCol);
		return billingObjectCol;
	}
	/**
	 * 把sourceCol中的但是targerCol中没有的对象合并到targerCol中,targerCol不能为null
	 * @param sourceCol
	 * @param targerCol
	 */
	private static void mergCollection(Collection sourceCol,Collection targerCol){
		if(targerCol.isEmpty()){		
			targerCol.addAll(sourceCol);
		}else{
			if( sourceCol !=null && !sourceCol.isEmpty()){
				Iterator sourceIter=sourceCol.iterator();
				while(sourceIter.hasNext()){
					Integer campaingID=(Integer)sourceIter.next();
					if(!targerCol.contains(campaingID))
						targerCol.add(campaingID);
				}	
			}
		}
	}
	/**
	 * 根据产品包id封装产品包对应的所有的促销及关联的促销
	 * @param packageID
	 * @param campaignCol
	 * @return
	 */
	private static Collection getPackage2CampaignByPackageID(int packageID,Collection campaignCol) throws ServiceException{
		Collection  package2CampaignCol=new ArrayList();
		Collection c2pCol=BusinessUtility.getCampaignIDListByPackageID(packageID);
		if(!c2pCol.isEmpty()){
			Iterator c2pIter=c2pCol.iterator();
			while(c2pIter.hasNext()){
				Integer c2pCampaignID=(Integer)c2pIter.next();
				if(campaignCol.contains(c2pCampaignID)&&!package2CampaignCol.contains(c2pCampaignID))
					package2CampaignCol.add(c2pCampaignID);
			}
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"■package2CampaignCol■"+package2CampaignCol);
		if(!package2CampaignCol.isEmpty()){
			Iterator p2cIter=package2CampaignCol.iterator();
			while(p2cIter.hasNext()){
				Integer campaignID=(Integer)p2cIter.next();
				Collection c2cCol=BusinessUtility.getCampaignAgmtCampaignListByCampaignID(campaignID.intValue());
				if(!c2cCol.isEmpty()){
					Iterator c2cIter=c2cCol.iterator();
					while(c2cIter.hasNext()){
						Integer c2cCampaignID=(Integer)c2cIter.next();
						if(campaignCol.contains(c2cCampaignID) && !package2CampaignCol.contains(c2cCampaignID))
							package2CampaignCol.add(c2cCampaignID);
					}
				}
			}
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"■package2CampaignCol■"+package2CampaignCol);
		return package2CampaignCol;
	}
	/**
	 * 根据参速生成一次性费用计费对象(saID和psIDCol只能二选一)
	 * @param paramObj
	 * @param saID
	 * @param psIDCol
	 * @return
	 * @throws ServiceException
	 */
	private static BillingObject fillBillingObject(CommonFeeAndPayObject paramObj,int saID,Collection psIDCol) throws ServiceException{
		BillingObject billObj=null;
		Collection custProdCol=new ArrayList();
		if(psIDCol!=null && !psIDCol.isEmpty()){
			String psidStr=psIDCol.toString().replace('[', '(').replace(']', ')');
			custProdCol.addAll(BusinessUtility.getCustProdDTOListBySaAndPsID(0,0," PSID in"+psidStr));
		}
		
		if(saID!=0)
			custProdCol.addAll(BusinessUtility.getCustProdDTOListBySaAndPsID(saID, 0,null));
		if(!custProdCol.isEmpty()){
			HashMap package2ProductMap=new HashMap();
			billObj=new BillingObject();
			billObj.setAccountID(paramObj.getAccountID());
			billObj.setAcctType(paramObj.getAcctType());
			billObj.setCustomerID(paramObj.getCustomerID());
			billObj.setCustType(paramObj.getCustType());
			billObj.setContractNo(paramObj.getContractNo());
			billObj.setServiceAccountID(paramObj.getServiceAccountID());
			billObj.setGroupBargainID(paramObj.getGroupBargainID());
			billObj.setDestProductID(paramObj.getDestProductID());
			//增加模拟业务停机/复机计费所需要的参数 begin
			if(paramObj.getServiceAccountID() == 0)
				billObj.setServiceAccountID(saID);
			int serviceID = BusinessUtility.getServiceIDBySAID(billObj.getServiceAccountID());
			LogUtility.log(PublicService.class, LogLevel.DEBUG,"____serviceID■■"+serviceID);
			if(serviceID == 6)
			{
				//String catvID = BusinessUtility.getCustomerDTOByCustomerID(new Integer(paramObj.getCustomerID()).intValue()).getCatvID();
				/* 上面那句话，本不应该注释掉，因外地项目中都没有对T_catvTerminal这张表进行过维护，自然在这个表中也就找不到数据
				 * 而外地模拟恢复时要收费，现向该表中插入一条记录，catvid为0，CATVTermType为计费条件的值，便于模拟电视做业务账户恢复时计费
				 * modify by david.Yang 2010.5.10
				 */
				String catvID ="0";
				CatvTerminalDTO catvTerminalDto =  BusinessUtility.getCatvTerminalDTOByID(catvID);
				System.out.println("###########catvTerminalDto###"+catvTerminalDto);
				if(catvTerminalDto == null) catvTerminalDto = new CatvTerminalDTO();
				billObj.setCATVTermType(catvTerminalDto.getCatvTermType());
				billObj.setCableType(catvTerminalDto.getCableType());
				billObj.setBiDirectionFlag(catvTerminalDto.getBiDirectionFlag());
				billObj.setOrgPortNum(catvTerminalDto.getPortNo());
				billObj.setDestPortNum(0);
			}
			//增加模拟业务停机/复机所需要的参数 end
			
			Iterator custProdIter=custProdCol.iterator();
			while(custProdIter.hasNext()){
				CustomerProductDTO dto =(CustomerProductDTO)custProdIter.next();
				Collection prodCol=(Collection)package2ProductMap.get(new Integer(dto.getReferPackageID()));
				if(prodCol==null){
					prodCol=new ArrayList();
					prodCol.add(new Integer(dto.getProductID()));
					package2ProductMap.put(new Integer(dto.getReferPackageID()), prodCol);
				}else
					prodCol.add(new Integer(dto.getProductID()));
			}
			billObj.setPackage2ProductMap(package2ProductMap);
		}
		LogUtility.log(PublicService.class, LogLevel.DEBUG,"■■fillBillingObject■■"+billObj);
	   return billObj;
	}
	
	
	
	/**
	 * 通过CSIPackageArray获得产品属性列表
	 * add by hujinpeng , 2008-04-21
	 * @param csiPackageArray : 产品包列表，封装格式为Integer对象
	 * @return  ：Collection封装为 ProductPropertyDTO  对象的列表
	 * @throws ServiceException
	 */
	public static Collection getProductPropertyArray(Collection csiPackageArray)throws ServiceException{
		
		LogUtility.log(clazz,LogLevel.DEBUG, "进入返回产品属性列表");

		
		Collection productPropertyList=new ArrayList();
		
		Collection productList=null;
		
		//由于团购的时候已经把产品包设置进去了,所以这里和正常开户是一样的,不需要区分处理
		productList=BusinessUtility.getCustomerProductDTOListByPackageIDList(csiPackageArray);

		Iterator itProductDto=productList.iterator();
		while(itProductDto.hasNext()){
			CustomerProductDTO cpDto=(CustomerProductDTO)itProductDto.next();			
			Collection List =BusinessUtility.getProductPropertyListByProductID(cpDto.getProductID());
			if(List!=null && List.size()>0){
				productPropertyList.addAll(List);
			}
		}
		LogUtility.log(clazz,LogLevel.DEBUG, "返回的产品属性列表为:" + productPropertyList.toString());
		
		return productPropertyList;
	}
	

}
