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
 * �ṩ������������
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
	 * ��������ԤԼ������ԤԼʱ�Ź�ȯ�������Ϣ
	 * @param gbdetail
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void dealOpenAccountRealationGroupBargain(CustServiceInteractionDTO csiDTO,boolean isLock,String status)throws  ServiceException{
		try {
			
			GroupBargainDetailHome gbDetailHome = HomeLocater.getGroupBargainDetailHome();
			GroupBargainDetail gbdetail=gbDetailHome.findByPrimaryKey(new Integer(csiDTO.getGroupCampaignID()));
			//�����Ź���ϸ��Ϣ
			if(isLock){
				gbdetail.setUsedDate(TimestampUtility.getCurrentDateWithoutTime());
				gbdetail.setUserID(csiDTO.getCustomerID());
				//�����Ź�ͷ��Ϣ
				GroupBargainHome groupBargainHome =HomeLocater.getGroupBargainHome();
				GroupBargain groupBargain=groupBargainHome.findByPrimaryKey(new Integer(gbdetail.getGroupBargainID()));
				groupBargain.setUsedSheets(groupBargain.getUsedSheets()+1);
				groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				if ((groupBargain.getUsedSheets()+ groupBargain.getAbortsheets())== groupBargain.getTotalSheets())
					groupBargain.setStatus("F");
			}
			//ԤԼ�����������ǵ꿪����״̬����Ϊ ����CommonConstDefinition.GROUPBARGAINDETAILSTATUS_RETURN
			gbdetail.setStatus(status);
			gbdetail.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		}catch(HomeFactoryException e) {
		    LogUtility.log(PublicService.class,LogLevel.ERROR,"dealOpenAccountRealationGroupBargain",e);
		    throw new ServiceException("�����Ź������Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(PublicService.class,LogLevel.ERROR,"dealOpenAccountRealationGroupBargain",e);
		    throw new ServiceException("�����Ź������Ϣʱ���Ҵ���");
    	}
	}
	/**
	 * ����ԤԼ����/�ŵ꿪�������ɹ��Ź���Ϣ
	 * @param gbdetail
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void cancelOpenAccountRealationGroupBargain(CustServiceInteractionDTO csiDTO)throws  ServiceException{
		try {
			
			GroupBargainDetailHome gbDetailHome = HomeLocater.getGroupBargainDetailHome();
			GroupBargainDetail gbdetail=gbDetailHome.findByPrimaryKey(new Integer(csiDTO.getGroupCampaignID()));
			//ԤԼ�����������ǵ꿪����״̬����Ϊ ����CommonConstDefinition.GROUPBARGAINDETAILSTATUS_RETURN
			gbdetail.setStatus(CommonConstDefinition.GROUPBARGAINDETAILSTATUS_SOLD);
			gbdetail.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			gbdetail.setUserID(0);
			gbdetail.setUsedDate(null);
			//�����Ź���ϸ��Ϣ
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
		    throw new ServiceException("�����Ź������Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(PublicService.class,LogLevel.ERROR,"dealOpenAccountRealationGroupBargain",e);
		    throw new ServiceException("�����Ź������Ϣʱ���Ҵ���");
    	}
	}
	
	/**
	 * ͨ��CSIPackageArray�����Ź�ȯdetailNo�����������Ӳ��ɫ������
	 * add by zhouxushun , 2005-11-17
	 * @param detailNo : �Ź�ȯdetailNo
	 * @param csiPackageArray : ��Ʒ���б���װ��ʽΪInteger����
	 * @return  ��Collection��װΪ DeviceClassDTO  ������б�
	 * @throws ServiceException
	 */
	public static Collection getDependencyDeviceClassBygetCsiPackageArray(CustServiceInteractionDTO csiDTO,String detailNo,Collection csiPackageArray)throws ServiceException{
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���뷵���豸���ͷ����ĵ���");

		
		if(detailNo==null && csiPackageArray==null){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			throw new ServiceException("��������");
		}
		Collection deviceClassList=new ArrayList();
		
		Collection productList=null;
		
		//�����Ź���ʱ���Ѿ��Ѳ�Ʒ�����ý�ȥ��,�������������������һ����,����Ҫ���ִ���
		productList=BusinessUtility.getCustomerProductDTOListByPackageIDList(csiPackageArray);
		String condStr=BusinessUtility.getSystemsettingValueByName("SET_D_DEVICEPURPOSE");
/*	
		//����������ʾ�Ƿ������豸��;
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
		LogUtility.log(clazz,LogLevel.DEBUG, "���ص��豸�����б�Ϊ:" + deviceClassList.toString());
		
		return deviceClassList;
	}
	
	/**
	 * �˻�Ѻ��Ĳ���
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
	 * �˻�Ѻ��Ĳ���
	 * @param forcedDepositDTO
	 * @param csiDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void widthdrawForcedDeposit(ForcedDepositDTO  forcedDepositDTO,CustServiceInteractionDTO csiDto,ServiceContext serviceContext)throws ServiceException{
		try {
			//ȡ��������Ϣ
			forcedDepositDTO.setCustomerID(csiDto.getCustomerID());
			forcedDepositDTO.setWithdrawCsiID(csiDto.getId());
			//ȡ�����е�Ѻ����Ϣ
			ForcedDepositHome forcedDepositHome=HomeLocater.getForcedDepositHome();
			ForcedDeposit forcedDeposit=forcedDepositHome.findByPrimaryKey(new Integer(forcedDepositDTO.getSeqNo()));
			
			//������ز���Ա��Ϣ
			Integer operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
			OperatorHome homeOpe = HomeLocater.getOperatorHome();
			Operator operator = homeOpe.findByPrimaryKey(operatorID);
			forcedDeposit.setProcessOperatorID(operatorID.intValue());
			forcedDeposit.setProcessOrgID(operator.getOrgID());
			forcedDeposit.setProcessTime(TimestampUtility.getCurrentTimestamp());
			//����Ѻ���״̬��Ϣ
			forcedDeposit.setSeizureMoney(forcedDeposit.getMoney()-forcedDepositDTO.getWithdrawMoney());
			forcedDeposit.setWithdrawMoney(forcedDepositDTO.getWithdrawMoney());
			if(forcedDeposit.getSeizureMoney()==0){
				forcedDeposit.setStatus(CommonConstDefinition.FDSTATUS_BACK);
			}else if(forcedDeposit.getSeizureMoney()==forcedDeposit.getMoney()){
				forcedDeposit.setStatus(CommonConstDefinition.FDSTATUS_CONFISCATE);
			}else if(forcedDeposit.getSeizureMoney()>0){
				forcedDeposit.setStatus(CommonConstDefinition.FDSTATUS_PART_CONFISCATE);
			}
			//�������յ�Ѻ����Ϣ
			ForcedDeposit forcedDepositCreate=forcedDepositHome.create(forcedDepositDTO);
			forcedDepositDTO.setSeqNo(forcedDeposit.getSeqNo().intValue());
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����˻�Ѻ���¼ʱ��λ����");
		}catch(CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����˻�Ѻ���¼ʱ�Ҳ��������Ϣ");
			
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,e);
		    throw new ServiceException("�����˻�Ѻ���¼ʱ���Ҳ���Ա����");	            		
    		
    	}
	}
	/**
	 * ��ȡѺ��Ĳ���
	 * @param forcedDepositDTO
	 * @param csiDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public static void createForcedDeposit(ForcedDepositDTO  forcedDepositDTO,CustServiceInteractionDTO csiDto,ServiceContext serviceContext)throws ServiceException{
		try {
			//ȡ��������Ϣ
			forcedDepositDTO.setCustomerID(csiDto.getCustomerID());
			forcedDepositDTO.setCsiID(csiDto.getId());
			//������ز���Ա��Ϣ
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
			throw new ServiceException("������ȡѺ���¼ʱ��λ����");
		}catch(CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("������ȡѺ���¼ʱ�Ҳ��������Ϣ");
			
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,e);
		    throw new ServiceException("������ȡѺ���¼ʱ���Ҳ���Ա����");	            		
    		
    	}
	}
//	/**
//	 * ȡ���ͻ�������Ʒ
//	 * @param custCampaignDTO
//	 */
//	public static void closeCusCampaign(CustomerCampaignDTO custCampaignDTO)throws ServiceException{
//		try{
//			//�޸Ŀͻ�����
//			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
//			CustomerCampaign cusCamp=customerCampaignHome.findByPrimaryKey(new Integer(custCampaignDTO.getCcID()));
//			cusCamp.ejbUpdate(custCampaignDTO);
//			//���¿ͻ���Ʒ����
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
//			throw new ServiceException("���ͻ���Ʒ�������Թ�ϵʱ��λ����");
//		}catch(FinderException e2) {
//			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e2);
//			throw new ServiceException("���ͻ���Ʒ�������Թ�ϵʱ���Ҵ���");
//		}
//	}
//	/**
//	 * ���¿ͻ���Ʒ������Ϣ
//	 * @param cpCampaignMapDTO
//	 * @throws ServiceException 
//	 */
//	public static void MantainCpCampaignMap(CPCampaignMapDTO cpCampaignMapDTO) throws ServiceException{
//		try{
//			//���¿ͻ���Ʒ����
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
//			throw new ServiceException("���¿ͻ���Ʒ������λ����");
//		}catch(FinderException e2) {
//			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
//			throw new ServiceException("���¿ͻ���Ʒ�������Ҵ���");
//		}
//	}
//	/**
//	 * �ӳٿͻ���Ʒ����
//	 * @param dto
//	 * @throws ServiceException
//	 */
//	public static CustomerCampaign delayCusCampaign(CustomerCampaignDTO dto) throws ServiceException{
//		try{
//			//�ӳٿͻ���Ʒ����
//			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
//			CustomerCampaign cusCamp=customerCampaignHome.findByPrimaryKey(new Integer(dto.getCcID()));
//			cusCamp.ejbUpdate(dto);	
//			//�ӳٿͻ���ƷCPCMAP
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
//			throw new ServiceException("���¿ͻ���Ʒ������λ����");
//		}catch(FinderException e2) {
//			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
//			throw new ServiceException("���¿ͻ���Ʒ�������Ҵ���");
//		}
//	}
//	/**
//	 * �����µĿͻ���Ʒ������¼
//	 * @param dto
//	 * @throws ServiceException 
//	 * @throws  
//	 */
//	public static CustomerCampaign grantNewCampaign(CustomerCampaignDTO dto) throws ServiceException{
//		try{
//			dto.setStatus(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL);
//			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
//			CustomerCampaign customerCampaign =customerCampaignHome.create(dto);
//			//����cpcmap
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
//			throw new ServiceException("�����µĿͻ���Ʒ������¼��λ����");
//		}catch(CreateException e) {
//			LogUtility.log(clazz, LogLevel.ERROR,e);
//			throw new ServiceException("�����µĿͻ���Ʒ������¼���ִ���");		
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
			throw new ServiceException("�����ͻ���ַ��λ����");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
			throw new ServiceException("�ͻ���ַ���Ҵ���");
		}catch(CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR,e);
			throw new ServiceException("�����ͻ���ַ��¼���ִ���");		
		}
	}
	
	/**
	 * ���ݷָ���Ű��ַ����ֳ���ӦĿ�������б�ĿǰĿ�����ֻʵ�֡�Integer/String��
	 * @param str����Ҫ�ָ���ַ���
	 * @param splitSymbol���ָ�����硱����������ָ��Ϊ�գ����str����targetObj����ת���������ظ����͵ĵ�һ�������б�
	 * @param targetObj��Integer/String�������ִ�Сд�������Ϊ���ߣ��򷵻�һ��str�Ķ����б�
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
				LogUtility.log(clazz, LogLevel.WARN, "stringSplitToTargetObject()����:"+e);
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
				LogUtility.log(clazz, LogLevel.WARN, "stringSplitToTargetObject()����:"+e);
				resultList=null;
			}	
		}
		return resultList;
	}
 	/**
 	 * ������װ����һ���Է��õ����ڹ��ýӿڵĲ�������(����ԤԼ/ԤԼ����/�ŵ꿪��ʱ)
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
	 * ������װ����һ���Է��õ����ڹ��ýӿڵĲ�������(��ϵͳ���Ѿ����ڵĿͻ�)
	 * @param csiDto   CustServiceInteractionDTO
	 * @param customerID  �ͻ�id
	 * @param accountID    �˻�id
	 * @return
	 */
	public static CommonFeeAndPayObject encapsulateBaseParamForExitCust(CustServiceInteractionDTO csiDto,int customerID,int accountID){
		CustomerDTO customerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerID);
		AccountDTO accountDTO=BusinessUtility.getAcctDTOByAcctID(accountID);
		CommonFeeAndPayObject paramObj=encapsulateBaseParam(csiDto,customerDTO,accountDTO);
		return paramObj;
	}
	/**
	 * ������װ����һ���Է��õ����ڹ��ýӿڵĲ�������(��ϵͳ���Ѿ����ڵĿͻ�)
	 * @param csiDto   CustServiceInteractionDTO
	 * @param customerID  �ͻ�id
	 * @param accountDTO    �˻���Ϣ
	 * @return
	 */
	public static CommonFeeAndPayObject encapsulateBaseParamForOpenAcct(CustServiceInteractionDTO csiDto,int customerID,AccountDTO accountDTO){
		CustomerDTO customerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerID);
		CommonFeeAndPayObject paramObj=encapsulateBaseParam(csiDto,customerDTO,accountDTO);
		return paramObj;
	}
	/**
	 * ������װ����һ���Է��õ����ڹ��ýӿڵĲ�������
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
	 * ��װ�Ʒ���������(Ŀǰ���ڿ���/����/������Ʒ�������)
	 * @param paramObj   �ƷѺ�֧����������
	 * @param packageCol  ��Ʒ������
	 * @param campaignCol  ��������
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
		//����һ����ʱ�Ŀɲ�����camapign���ϵĿ�¡����
		Collection conCampaignCol=null;
		if(campaignCol!=null)  conCampaignCol=new ArrayList(campaignCol);
		Collection conPackageCol=null;
		if(packageCol!=null )  conPackageCol=new ArrayList(packageCol);
		//������źͲ�Ʒ���й����Ĵ���
		Collection havepcCol=new ArrayList();
		//����һ����ʱ�Ŀɲ�����pakcage���ϵĿ�¡����
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
				//������Ժ���Ҫ׷�ӶԿ�ѡ���Ĵ���,Ŀǰû��׷��
				Collection productIDCol=BusinessUtility.getProductIDsByPackageID(conPackageID.intValue());
				package2ProductMap.put(conPackageID, productIDCol);
				billObj.setPackage2ProductMap(package2ProductMap);
				Collection p2cCol=getPackage2CampaignByPackageID(conPackageID.intValue(),campaignCol);
				billObj.setCampaignCol(p2cCol);
				//��p2cCol�еĶ���Ͳ���havepcCol��ȥ
				mergCollection(p2cCol,havepcCol);
				billingObjectCol.add(billObj);
			}	
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"��havepcCol��"+havepcCol);
		LogUtility.log(clazz,LogLevel.DEBUG,"��conCampaignCol��"+conCampaignCol);
		if(conCampaignCol!=null && !conCampaignCol.isEmpty()){
			conCampaignCol.removeAll(havepcCol);
			LogUtility.log(clazz,LogLevel.DEBUG,"��conCampaignCol remove havepcCol��"+conCampaignCol);
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
		
		
		LogUtility.log(PublicService.class, LogLevel.DEBUG,"����fillBillingObject����"+billingObjectCol);
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
		LogUtility.log(PublicService.class, LogLevel.DEBUG,"����encapsulateBillingObjectForCust����"+billingObjectCol);
		return billingObjectCol;
	}
	/**
	 * ��sourceCol�еĵ���targerCol��û�еĶ���ϲ���targerCol��,targerCol����Ϊnull
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
	 * ���ݲ�Ʒ��id��װ��Ʒ����Ӧ�����еĴ����������Ĵ���
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
		LogUtility.log(clazz,LogLevel.DEBUG,"��package2CampaignCol��"+package2CampaignCol);
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
		LogUtility.log(clazz,LogLevel.DEBUG,"��package2CampaignCol��"+package2CampaignCol);
		return package2CampaignCol;
	}
	/**
	 * ���ݲ�������һ���Է��üƷѶ���(saID��psIDColֻ�ܶ�ѡһ)
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
			//����ģ��ҵ��ͣ��/�����Ʒ�����Ҫ�Ĳ��� begin
			if(paramObj.getServiceAccountID() == 0)
				billObj.setServiceAccountID(saID);
			int serviceID = BusinessUtility.getServiceIDBySAID(billObj.getServiceAccountID());
			LogUtility.log(PublicService.class, LogLevel.DEBUG,"____serviceID����"+serviceID);
			if(serviceID == 6)
			{
				//String catvID = BusinessUtility.getCustomerDTOByCustomerID(new Integer(paramObj.getCustomerID()).intValue()).getCatvID();
				/* �����Ǿ仰������Ӧ��ע�͵����������Ŀ�ж�û�ж�T_catvTerminal���ű���й�ά������Ȼ���������Ҳ���Ҳ�������
				 * �����ģ��ָ�ʱҪ�շѣ�����ñ��в���һ����¼��catvidΪ0��CATVTermTypeΪ�Ʒ�������ֵ������ģ�������ҵ���˻��ָ�ʱ�Ʒ�
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
			//����ģ��ҵ��ͣ��/��������Ҫ�Ĳ��� end
			
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
		LogUtility.log(PublicService.class, LogLevel.DEBUG,"����fillBillingObject����"+billObj);
	   return billObj;
	}
	
	
	
	/**
	 * ͨ��CSIPackageArray��ò�Ʒ�����б�
	 * add by hujinpeng , 2008-04-21
	 * @param csiPackageArray : ��Ʒ���б���װ��ʽΪInteger����
	 * @return  ��Collection��װΪ ProductPropertyDTO  ������б�
	 * @throws ServiceException
	 */
	public static Collection getProductPropertyArray(Collection csiPackageArray)throws ServiceException{
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���뷵�ز�Ʒ�����б�");

		
		Collection productPropertyList=new ArrayList();
		
		Collection productList=null;
		
		//�����Ź���ʱ���Ѿ��Ѳ�Ʒ�����ý�ȥ��,�������������������һ����,����Ҫ���ִ���
		productList=BusinessUtility.getCustomerProductDTOListByPackageIDList(csiPackageArray);

		Iterator itProductDto=productList.iterator();
		while(itProductDto.hasNext()){
			CustomerProductDTO cpDto=(CustomerProductDTO)itProductDto.next();			
			Collection List =BusinessUtility.getProductPropertyListByProductID(cpDto.getProductID());
			if(List!=null && List.size()>0){
				productPropertyList.addAll(List);
			}
		}
		LogUtility.log(clazz,LogLevel.DEBUG, "���صĲ�Ʒ�����б�Ϊ:" + productPropertyList.toString());
		
		return productPropertyList;
	}
	

}
