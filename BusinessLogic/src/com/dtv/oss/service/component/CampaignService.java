package com.dtv.oss.service.component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.CPCampaignMap;
import com.dtv.oss.domain.CPCampaignMapHome;
import com.dtv.oss.domain.CsiCustProductInfoHome;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustServiceInteractionHome;
import com.dtv.oss.domain.CustomerCampaign;
import com.dtv.oss.domain.CustomerCampaignHome;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.domain.ServiceAccount;
import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.dto.BundlePrepaymentDTO;
import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CsiCustProductInfoDTO;
import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.TimestampUtility;

public class CampaignService {

	private static final Class clazz = CampaignService.class;

	private ServiceContext context;

	private int operatorID;

	public CampaignService(int operatorID) {
		this.operatorID = operatorID;
	}
	/**
	 * ���ڿ���/����ʧ�ܵ�ʱ������ײ�/�����Ŀͻ���ʵ��
	 * @param csiid
	 * @throws ServiceException
	 */
	public void  cancelCustCampaignForNew(int csiid)throws ServiceException{
    	Collection custcampaignList=BusinessUtility.getCustCampaignListByCsiID(csiid);
    	LogUtility.log(clazz,LogLevel.DEBUG,"cancelCustCampaignForNew");
    	if(!custcampaignList.isEmpty()){
    		Iterator custCampaignIter=custcampaignList.iterator();
    		while(custCampaignIter.hasNext()){
    			CustomerCampaignDTO dto=(CustomerCampaignDTO)custCampaignIter.next();
    			custCampaignCancel(dto);
    		}
    	}
    	LogUtility.log(clazz,LogLevel.DEBUG,"cancelCustCampaignForNew");
	}
	/**
	 * constructer method
	 * 
	 * @param context
	 */
	public CampaignService(ServiceContext serviceContext) {
		this.context = serviceContext;
		setOperatorID(PublicService.getCurrentOperatorID(serviceContext));
	}

	/*
	 * public void createCustomerCampaign(Map customerProductMap,
	 * CustomerCampaignDTO custCampaignDto,ArrayList selProductList) {
	 * customerProductMap.containsKey(""); selProductList.contains("");
	 *  }
	 */
	/**
	 * ȡ���ͻ�������Ʒ
	 * @param custCampaignDTO
	 */
	public CustomerCampaign custCampaignCancel(CustomerCampaignDTO dto)throws ServiceException{
		try{
			//ȡ���ͻ�����
			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
			CustomerCampaign custCamp=customerCampaignHome.findByPrimaryKey(new Integer(dto.getCcID()));
			custCamp.setStatus(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_CANCEL);
			Timestamp curDate = TimestampUtility.getCurrentDateWithoutTime();
			custCamp.setDateTo(curDate);
			custCamp.setNbrDate(curDate);
			custCamp.setPrePaidTo(curDate);
			custCamp.setDtLastmod(new Timestamp(System.currentTimeMillis()));

			//����CPCampaignMap��¼:
			CPCampaignMapHome cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
			Collection col = cPCampaignMapHome.findByCustomerCampaign(new Integer(dto.getCcID()));
			if(!(col==null||col.isEmpty())){
				Iterator iter=col.iterator();
				while(iter.hasNext()){
					CPCampaignMap cPCampaignMap=(CPCampaignMap)iter.next();
					cPCampaignMap.setStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
					cPCampaignMap.setDtLastmod(new Timestamp(System.currentTimeMillis()));
					//cPCampaignMap.ejbUpdate(cpCampaignMapDTO);
				}
			}			
			return custCamp;
		}catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e1);
			throw new ServiceException("���ͻ���Ʒ�������Թ�ϵʱ��λ����");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e2);
			throw new ServiceException("���ͻ���Ʒ�������Թ�ϵʱ���Ҵ���");
		}
	}
	/**
	 * ȡ���ͻ������ʻ������Ŀͻ�����
	 * @param customerID
	 * @param accountID
	 * @throws ServiceException
	 */
	public void cancelCustCampaignForCust(int customerID,int accountID,int serviceaccountID) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"cancelCustCampaignForCust start");
		Collection custCampaignIDCol=BusinessUtility.getCustCpListByCustInfo(customerID, accountID, serviceaccountID,0);
		if(!custCampaignIDCol.isEmpty()){
			Iterator cpIter=custCampaignIDCol.iterator();
			while(cpIter.hasNext()){
				CustomerCampaignDTO cpDTO=(CustomerCampaignDTO)cpIter.next();
				custBundleCancel(cpDTO.getCcID());
			}
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"cancelCustCampaignForCust end");
	}
	
	/**
	 * �ͻ��ײ�ȡ��,����ͷ��¼״̬Ϊ��ֹ,��ϸ��¼״̬Ϊȡ��,dateTo,NbrDate,PrePaidToΪ��ǰ����.
	 * @param ccid
	 * @return
	 * @throws ServiceException
	 */
	public CustomerCampaign custBundleCancel(int ccid)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"custBundleCancel>>>>>"+ccid);
		try{
			//ȡ���ͻ�����
			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
			CustomerCampaign custCamp=customerCampaignHome.findByPrimaryKey(new Integer(ccid));
			custCamp.setStatus(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_TRANSFE);
			custCamp.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			Timestamp curDate = TimestampUtility.getCurrentDateWithoutTime();
			custCamp.setDateTo(curDate);
			custCamp.setNbrDate(curDate);
			custCamp.setPrePaidTo(curDate);
			//����CPCampaignMap��¼:
			CPCampaignMapHome cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
			Collection col = cPCampaignMapHome.findByCustomerCampaign(new Integer(ccid));
			if(!(col==null||col.isEmpty())){
				Iterator iter=col.iterator();
				while(iter.hasNext()){
					CPCampaignMap cPCampaignMap=(CPCampaignMap)iter.next();
					cPCampaignMap.setStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
					cPCampaignMap.setDtLastmod(new Timestamp(System.currentTimeMillis()));
					//cPCampaignMap.ejbUpdate(cpCampaignMapDTO);
				}
			}			
			return custCamp;
		}catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e1);
			throw new ServiceException("ȡ���ͻ��ײ�ʱ��λ����");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e2);
			throw new ServiceException("ȡ���ͻ��ײ�ʱ���Ҵ���");
		}
	}
	/**
	 * ���¿ͻ���Ʒ������Ϣ
	 * 
	 * @param cpCampaignMapDTO
	 * @throws ServiceException
	 * @param CustomerCampaignDTO
	 * @throws ServiceException 
	 */

	public void CustCampaignModify(CustomerCampaignDTO dto) throws ServiceException{
		try{
			//���¿ͻ���Ʒ����
			CustomerCampaignHome home=HomeLocater.getCustomerCampaignHome();
			CustomerCampaign custCampaign=home.findByPrimaryKey(new Integer(dto.getCcID()));
			custCampaign.setAutoExtendFlag(dto.getAutoExtendFlag());
			custCampaign.setAllowPause(dto.getAllowPause());
			custCampaign.setAllowTransition(dto.getAllowTransition());
			custCampaign.setAllowTransfer(dto.getAllowTransfer());
			custCampaign.setAllowClose(dto.getAllowClose());
			custCampaign.setAllowAlter(dto.getAllowAlter());
			custCampaign.setComments(dto.getComments());
			custCampaign.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			
			//����CPCampaignMap��¼:
			CPCampaignMapHome cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
			Collection col = cPCampaignMapHome.findByCustomerCampaign(new Integer(dto.getCcID()));
			if(!(col==null||col.isEmpty())){
				Iterator iter=col.iterator();
				while(iter.hasNext()){
					CPCampaignMap cPCampaignMap=(CPCampaignMap)iter.next();
					cPCampaignMap.setDtLastmod(new Timestamp(System.currentTimeMillis()));
					//cPCampaignMap.ejbUpdate(cpCampaignMapDTO);
				}
			}
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "MantainCpCampaignMap", e1);
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.ERROR, "MantainCpCampaignMap", e2);
		}
	}

	/**
	 * �ӳٿͻ���Ʒ����
	 * @param dto
	 * @throws ServiceException
	 */
	public  CustomerCampaign custBundleDelay(int customerCampaignID,Timestamp endDateTo,String comments)throws ServiceException{
		try{
			//�ӳٿͻ���Ʒ����
			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
			CustomerCampaign oldCustomerCampaign=customerCampaignHome.findByPrimaryKey(new Integer(customerCampaignID));
			oldCustomerCampaign.setDateTo(TimestampUtility.getTimeEnd(endDateTo,"D",0));
			Timestamp nbrDate =  TimestampUtility.getDateEnd(endDateTo,"D",1);
			oldCustomerCampaign.setNbrDate(nbrDate);
			oldCustomerCampaign.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			oldCustomerCampaign.setComments(comments);
			//�ӳٿͻ���ƷCPCMAP
			CPCampaignMapHome cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
			Collection cpCampaignMapList = cPCampaignMapHome.findByCustomerCampaign(new Integer(customerCampaignID));
			if(cpCampaignMapList!=null&&!cpCampaignMapList.isEmpty()){
				Iterator iter=cpCampaignMapList.iterator();
				while(iter.hasNext()){
					CPCampaignMap cPCampaignMap=(CPCampaignMap)iter.next();
					cPCampaignMap.setDtLastmod(cPCampaignMap.getDtLastmod());
				}
			}
			return oldCustomerCampaign;
		}catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e1);
			throw new ServiceException("���¿ͻ���Ʒ������λ����");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
			throw new ServiceException("���¿ͻ���Ʒ�������Ҵ���");
		}
	}
	/**
	 * �ײ�Ԥ����
	 * @param customerCampaignID
	 * @param rfBillingCycleFlag
	 * @return
	 * @throws ServiceException
	 */
	public CustomerCampaign custBundlePrePayment(int customerCampaignID,
			BundlePaymentMethodDTO paymentMethodDto) throws ServiceException {
		try {
			LogUtility.log(clazz, LogLevel.DEBUG,
					"CampaignService.custBundlePrePayment.paymentMethodDto:>>>>>"+paymentMethodDto);
			if(paymentMethodDto==null){
				LogUtility.log(clazz, LogLevel.ERROR,"û�ж�Ӧ��paymentMethod��¼!!");
				throw new ServiceException("�ײ��������ô���.");
			}
			// ��ǰ�ͻ��ײͶ���
			CustomerCampaignHome customerCampaignHome = HomeLocater
					.getCustomerCampaignHome();
			CustomerCampaign customerCampaign = customerCampaignHome
					.findByPrimaryKey(new Integer(customerCampaignID));
			//���ݸ��ѷ����ﶨ���ʱ�䳤�ȸ�������nbrDate,nbrDate=nbrDate+��������ʱ��;
			customerCampaign.setNbrDate(TimestampUtility.getDateEnd(
					customerCampaign.getNbrDate(), paymentMethodDto
							.getTimeUnitLengthType(), paymentMethodDto
							.getTimeUnitLengthNumber()));
			customerCampaign.setPrePaidTo(TimestampUtility.getDateEnd(
					customerCampaign.getPrePaidTo(), paymentMethodDto
							.getTimeUnitLengthType(), paymentMethodDto
							.getTimeUnitLengthNumber()));			
			//����ͻ��ײ�Э���ڽ�������<NbrDate����ô�ͻ��ײ�Э���ڽ������ڣ�NbrDate
			Timestamp newDateTo=TimestampUtility.getTimeEnd(customerCampaign.getNbrDate(),"D",-1);
			
			if (customerCampaign.getDateTo().before(newDateTo)) {
				customerCampaign.setDateTo(newDateTo);
			}
			customerCampaign.setDtLastmod(TimestampUtility
					.getCurrentTimestamp());
			return customerCampaign;
		}catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e1);
			throw new ServiceException("���¿ͻ���Ʒ������λ����");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
			throw new ServiceException("���¿ͻ���Ʒ�������Ҵ���");
		}
	}

	/**
	 * �ײ�ת�� ��Ҫ��ɵĹ���: 
	 * 1,ȡ��һЩ��Ʒ, 
	 * 2,����һЩ��Ʒ,
	 * 3,���·���ҵ���ʻ�,???!!!�������鷳,��ôƥ��?���ȡ�����Ʒ��,û����Ч�ͻ���Ʒ,ȡ����ҵ���ʻ�,
	 * Ȼ���ʣ��Ŀͻ���Ʒ������ӵĿͻ���Ʒ�ŵ�һ��,���·���, 
	 * 4,���¾ɵĿͻ���Ʒ���ð�,�������Ϣ.
	 * 5,�����µ�customercampaign����,
	 * 
	 * @param oldCustomerCampaignID
	 * @param newCampaignID
	 * @param cancelPsidList
	 * @param addProductMap
	 * @return
	 * @throws ServiceException
	 */
	public CustomerCampaign custBundleTransfer(CustomerCampaignDTO ccDto,
			int newCampaignID, ArrayList cancelCpList, Map addProductMap,
			Map productPropertyMap, String phoneNo, int itemID,boolean isReturnDevice)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG,"��ʼ�ײ�ת��>>>>>>>>>>" );
		CustServiceInteraction csi = (CustServiceInteraction) context
		.get(Service.CSI);
		ServiceAccountService saService = new ServiceAccountService(context,BusinessUtility.getAllProductDepentDefineList());
		saService.setCustomerid(ccDto.getCustomerID());
		saService.setAccountid(ccDto.getAccountID());
		ArrayList allValidCpList = new ArrayList();
		ArrayList opCancelCpList = new ArrayList();
		ArrayList newSaList=new ArrayList();
		ArrayList cancelSaList=new ArrayList();

		if(cancelCpList!=null){
			opCancelCpList=(ArrayList) cancelCpList.clone();
		}

		//1,��ȡ�����ײ����ҵ���ʻ������пͻ���Ʒ,�Ƴ���Ҫȡ���Ŀͻ���Ʒ.��ӵ���Ч�Ĳ�Ʒ����.
		//2,���ĳҵ���ʻ��²�Ʒȫ��ȡ��,ֱ�ӵ���ҵ���ʻ�ȡ������,
		Map saMap = BusinessUtility.getServiceAccountMapByCustCampaignID(ccDto
				.getCcID(), true);
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"ԭ���ײ�����ؿͻ���Ʒ����,ԭ�ײ��������ҵ���ʻ�����Ʒ:" , saMap);

		for (Iterator sait = saMap.keySet().iterator(); sait.hasNext();) {
			Integer said=(Integer) sait.next();
			//��ǰҵ���ʻ�����Ч�Ŀͻ���Ʒ.
			ArrayList curSaCpList =(ArrayList) saMap.get(said);
			//��ǰҵ���ʻ�����Ҫȡ���Ŀͻ���Ʒ.
			ArrayList curCancelPsidList=new ArrayList();
			ArrayList curValidCpList=new ArrayList();
			for (Iterator cpit = curSaCpList.iterator(); cpit.hasNext();) {
				CustomerProductDTO cpDto = (CustomerProductDTO) cpit.next();
				Integer psid=new Integer(cpDto.getPsID());
				//�����ǰ��Ʒ������ȡ����Ʒ������,
				if (opCancelCpList != null
						&& opCancelCpList.contains(psid)) {
					curCancelPsidList.add(psid);
				}else{
					curValidCpList.add(cpDto);
				}
			}
			//ȡ����ǰҵ���ʻ��²�Ʒȫ��Ҫȡ��,��ر�ҵ���ʻ�.
			if(curValidCpList.isEmpty()){
				LogUtility.log(clazz, LogLevel.DEBUG,
						"ҵ���ʻ���ȡ��:" + said.intValue());
				//�����ݶ�Ϊ�����豸��ʵ���и�����Ҫ������
				saService.close(said.intValue(),isReturnDevice);
				cancelSaList.add(said);
				opCancelCpList.removeAll(curCancelPsidList);
			}
			allValidCpList.addAll(curValidCpList);
		}
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"ԭ���ײ�����ؿͻ���Ʒ����,�Ƴ���Ҫȡ���Ĳ�Ʒ��,list.size()" + allValidCpList.size());
		//3,ȡ���ͻ���Ʒ,�����豸��ת,
		CustomerProductService cpService = new CustomerProductService(context);
		if(opCancelCpList!=null&&!opCancelCpList.isEmpty()){
			cpService.cancelCustomerProduct(opCancelCpList, isReturnDevice);
		}

		//4,�ѱ��������Ĳ�Ʒ����ȫ����Ч�Ĳ�Ʒ����.
		if (addProductMap != null && !addProductMap.isEmpty()) {
			// �ѱ��β��������ӵĲ�Ʒ������Ч��Ʒ����.
			for (Iterator addpit = addProductMap.keySet().iterator(); addpit
					.hasNext();) {
				Integer productID = (Integer) addpit.next();
				Integer referPackageID = (Integer) addProductMap.get(productID);
				CustomerProductDTO cpDto = new CustomerProductDTO();
				cpDto.setProductID(productID.intValue());
				cpDto.setReferPackageID(referPackageID.intValue());
				allValidCpList.add(cpDto);
			}
//			LogUtility.log(clazz, LogLevel.DEBUG,
//					"ԭ���ײ�����ؿͻ���Ʒ����,������������Ʒ���,list.size()"
//							+ allValidCpList.size());
		}
		//5,��ԭҵ���ʻ���ʣ�����Ч�ͻ���Ʒ�������ӵĲ�Ʒ,���з���.
		Map splitValidCpMap = saService.splitCustomerProductWithService(allValidCpList);
		LogUtility.log(clazz, LogLevel.DEBUG,"��Ч��Ʒ�������>>>>>>>>>>",splitValidCpMap );

		//6,��������,���ҵ���ʻ���ƷΪ�����Ӳ�Ʒ,�򴴽�ҵ���ʻ�,
		//7,���ҵ���ʻ��²�Ʒ,�����Ʒ�������ӵ�,�����ͻ���Ʒ,����ҵ���ʻ�,�����Ʒ�Ǿɵ�,���²�Ʒ���ð�
		Map campaignAgmtProductMap =  BusinessUtility.getCampaignAgmtProductList(newCampaignID);
		ArrayList saList=new ArrayList();
		for (Iterator sait = splitValidCpMap.keySet().iterator(); sait
				.hasNext();) {
			CustomerProductDTO curSaCp = (CustomerProductDTO) sait.next();
			ArrayList cpList = (ArrayList) splitValidCpMap.get(curSaCp);
			cpList.add(0,curSaCp);
			int serviceAccountID = curSaCp.getServiceAccountID();
			int customerID = ccDto.getCustomerID();
			int accountID = ccDto.getAccountID();
			// �����ǰҵ���ʻ���Ʒ��ҵ���ʻ�Ϊ��,˵����ҵ���ʻ� ������.
			if (serviceAccountID == 0) {
				ServiceAccount newSa = saService.createServiceAccountOnly(
						curSaCp,customerID, phoneNo, itemID,
						CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
				serviceAccountID = newSa.getServiceAccountID().intValue();
				newSaList.add(newSa.getServiceAccountID());
			}
			ArrayList newProductList = new ArrayList();
			for (Iterator cpit = cpList.iterator(); cpit.hasNext();) {
				CustomerProductDTO curCp = (CustomerProductDTO) cpit.next();
				Integer productID=new Integer(curCp.getProductID());
				Integer newPackageID = (Integer) campaignAgmtProductMap.get(productID);
				// �����ǰ��Ʒҵ���ʻ�Ϊ��,˵��������Ӳ�Ʒ.
				if (curCp.getServiceAccountID() == 0) {
					newProductList.add(curCp);
				} else if(campaignAgmtProductMap.keySet().contains(productID)){
					// �����ǰ��Ʒ�Ѿ�����,�Ұ��������ײ�Э����,�����ԭ�������ð�.
					cpService.updateCustomerProduct(curCp.getPsID(), accountID,
							serviceAccountID,
							newPackageID != null ? newPackageID.intValue() : 0);
				}
			}
			// �����ǰҵ���ʻ������µĲ�Ʒ,�򴴽��ͻ���Ʒ,�����õ���ǰҵ���ʻ���.
			if (newProductList != null && !newProductList.isEmpty()) {
				cpService = new CustomerProductService(context,customerID,accountID,serviceAccountID);
				cpService.create(newProductList, serviceAccountID, customerID,
						accountID, productPropertyMap,
						CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
			}
			saList.add(new Integer(serviceAccountID));
		}
		context.put(Service.NEWSERVICEACCOUNTIDLIST, newSaList);
		context.put(Service.CANCELSERVICEACCOUNTIDLIST, cancelSaList);
		//ȡ���ɵĿͻ��ײ�
		custBundleCancel(ccDto.getCcID());
		//�����µ��ײ�.
		ArrayList campaignList=new ArrayList();
		campaignList.add(new Integer(newCampaignID));
		ArrayList createCamapignList= createCustomerCampaign(allValidCpList,campaignList,saList,ccDto.getCustomerID(),ccDto.getAccountID(),csi.getId().intValue());
		LogUtility.log(clazz, LogLevel.DEBUG,"�����ײ�ת��>>>>>>>>>>" );
		return (createCamapignList!=null&&!createCamapignList.isEmpty())?(CustomerCampaign) createCamapignList.get(0):null;
	}

	/**
	 * �ֹ�����ͻ�����,������������˶�Ӧ�Ŀͻ���Ʒ,���serviceAccount��ȡ��Ӧ��customerproductList/selproduct
	 * ���������ٶ��˴������ж�Ӧ�ͻ���Ʒ��,
	 * @param dto
	 * @throws ServiceException
	 * @throws
	 */
	public CustomerCampaign grantNewCampaign(CustomerCampaignDTO dto)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "grantNewCampaign", dto);
		int said=dto.getServiceAccountID();
		Collection cpList=null;
		Map customerProductMap=new HashMap();
		ArrayList selProductList=new ArrayList();
		if(said!=0){
			//ȡ�õ�ǰҵ���ʻ������в�Ʒ,��װPSID�ļ���,��ѡ���Ʒ�ļ���,
			cpList=BusinessUtility.getCustProdDTOListBySaAndPsID(dto.getServiceAccountID(),0,"");
			Iterator it=cpList.iterator();
			while(it.hasNext()){
				CustomerProductDTO cpDto=(CustomerProductDTO) it.next();
				customerProductMap.put(new Integer(cpDto.getProductID()), new Integer(cpDto.getPsID()));
				selProductList.add(new Integer(cpDto.getProductID()));
			}
		}
		//���ô�����������,�޸Ĵ����ô�����״̬Ϊ����,
		CustomerCampaign cc = createCustomerCampaign(dto, customerProductMap, selProductList, null);
		cc.setStatus(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL);
		
		//�޸�cpcampaignmap��¼��״̬,
		CPCampaignMapHome cpCampaignMapHome;
		try {
			cpCampaignMapHome = HomeLocater.getCPCampaignMapHome();
			Collection cpMapList = cpCampaignMapHome.findByCustomerCampaign(cc.getCcID());
			Iterator cpMapIt = cpMapList.iterator();
			while (cpMapIt.hasNext()) {
				CPCampaignMap cpMap = (CPCampaignMap) cpMapIt.next();
				cpMap.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			}
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "grantNewCampaign", e);
			throw new ServiceException("����ͻ�����ҵ�����λ����");
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "grantNewCampaign", e);
			throw new ServiceException("����ͻ�����ҵ�����λ����");
		}
		return cc;
	}
	public void createCsiCustProductInfoWithCampaign(int csiID,CustomerCampaign dto,String action) throws ServiceException{
		CsiCustProductInfoDTO cpInfoDto=new CsiCustProductInfoDTO();
		cpInfoDto.setAction(action);
		cpInfoDto.setCsiID(csiID);
		cpInfoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		cpInfoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		cpInfoDto.setReferAccountID(dto.getAccountID());
		cpInfoDto.setReferCampaignID(dto.getCampaignID());
		cpInfoDto.setReferCCID(dto.getCcID().intValue());
		cpInfoDto.setReferServiceAccountID(dto.getServiceAccountID());
		cpInfoDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
		try {
			CsiCustProductInfoHome csiCustProductInfoHome = HomeLocater
					.getCsiCustProductInfoHome();
			csiCustProductInfoHome.create(cpInfoDto);
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR, "createCsiCustProductInfoWithCampaign", e);
			throw new ServiceException("����������ؿͻ���Ʒ��¼ʧ��.");
		}
	}
	public ServiceContext getContext() {
		return context;
	}

	public void setContext(ServiceContext context) {
		this.context = context;
	}

	public int getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
	/**
	 * ��Ӧ���п�������,�ɴ����ҵ���ʻ�,�����(һ������,��������صĴ���,���Ҫ��չΪ����ײ�ͬʱ����,...��Ҫ���޸�),�������ѡ��Ʒ��,
	 * @param cpDTOList customerPorductDto�ļ���,ǰ̨ѡ��Ĳ�Ʒ,
	 * @param campaignList ǰ̨ѡ����ײͻ��ߴ���,
	 * @param serviceAccountList ������ҵ���ʻ�,��Ҫ����������̳�psid
	 * @param customerID �ͻ�id
	 * @param accountID �ײͼƷѵ��ʻ�
	 * @param csiID ������id
	 * @throws ServiceException
	 */
	public ArrayList createCustomerCampaign(Collection cpDTOList, Collection campaignList,Collection serviceAccountList,
			int customerID,int accountID,int csiID) throws ServiceException{
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"createCustomerCampaign.cpDTOList:>>>>>"+cpDTOList.size()
//						, cpDTOList);
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"createCustomerCampaign.campaignList:>>>>>" + campaignList);
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"createCustomerCampaign.serviceAccountList:>>>>>" + serviceAccountList);
		ArrayList createCampaignList=new ArrayList();
		if(campaignList==null||campaignList.isEmpty()){
			LogUtility.log(clazz, LogLevel.DEBUG,
					"createCustomerCampaign.serviceAccountList:>>>>>û����Ҫ�������ײ��б�");
			return null;
		}
		Map customerProductMap=new HashMap();
		ArrayList selProductList=new ArrayList();
		CustomerProductHome cpHome;
/**
 * ȡserviceaccountid,Ŀǰcustomercampaign.serviceaccountid�ֶ���int��,��ֻ�ܷ�һ��,�������,����ֶ�Ŀǰûʲô�ô�.
 * 
 */		
		Integer saID=new Integer(0);
		try {
			cpHome = HomeLocater.getCustomerProductHome();
		for (Iterator saIt = serviceAccountList.iterator(); saIt.hasNext();){
			saID=(Integer) saIt.next();
			Collection saCPList = cpHome.findByServiceAccountID(saID.intValue());
			Iterator cpit=saCPList.iterator();
			while(cpit.hasNext()){
				CustomerProduct cp=(CustomerProduct) cpit.next();
				if(!CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL.equals(cp.getStatus())){
					LogUtility.log(clazz, LogLevel.DEBUG,"\nsaID:"+saID+">>>cp.getProductID():" + cp.getProductID()+">>>cp.getPsid():" + cp.getPsID());
					customerProductMap.put(new Integer(cp.getProductID()), cp.getPsID());
				}
			}
		}
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			e.printStackTrace();
		}
		for(Iterator productIt=cpDTOList.iterator();productIt.hasNext();){
			CustomerProductDTO cpDto=(CustomerProductDTO) productIt.next();
			selProductList.add(new Integer(cpDto.getProductID()));
		}
		//ȡ���ײͺͶ�Ӧ��bundlePrePaymentDto����,
		Map bundlePrePaymentMap=BusinessUtility.getBundlePrePaymentMapWithCampaignList(campaignList);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"createCustomerCampaign.bundlePrePaymentMap:>>>>>" + bundlePrePaymentMap);
		for(Iterator campaignIt=campaignList.iterator();campaignIt.hasNext();){
			Integer campaignID=(Integer) campaignIt.next();
			CustomerCampaignDTO ccDto=new CustomerCampaignDTO();
			ccDto.setCustomerID(customerID);
			ccDto.setAccountID(accountID);
			ccDto.setCampaignID(campaignID.intValue());
			ccDto.setCsiID(csiID);
			if(serviceAccountList!=null&&serviceAccountList.size()==1){
				ccDto.setServiceAccountID(saID.intValue());
			}
			ArrayList bundlePrePaymentList=(ArrayList)bundlePrePaymentMap.get(campaignID);
			createCampaignList.add(createCustomerCampaign(ccDto,customerProductMap,selProductList,bundlePrePaymentList));
		}
		return createCampaignList;
	}
/**
 * �����ײ�/�����Ŀͻ���ʵ��,
 * @param cpDto customerCampaign����,��װcampaignid/customerid/accountid/csiid/dateFrom
 * @param customerProductMap key:customerproduct��Ӧ��productID,value:PSID,
 * @param selProductList ǰ̨�����,ѡ��Ĳ�Ʒ�б�,
 * @param bundlePrePaymentList ��ǰ�ײ͵Ķ�Ӧ��Ԥ���Ѽƻ��б�,��װ����bundlePrePaymentDTO
 * @throws ServiceException
 */
//	public CustomerCampaign createCustomerCampaign(CustomerCampaignDTO cpDto,Map customerProductMap,
//			 ArrayList selProductList,ArrayList bundlePrePaymentList)
//			throws ServiceException {
//		return createCustomerCampaign(cpDto,customerProductMap,selProductList,bundlePrePaymentList,null);
//	}
	public CustomerCampaign createCustomerCampaign(CustomerCampaignDTO cpDto,Map customerProductMap,
			 ArrayList selProductList,ArrayList bundlePrePaymentList)
			throws ServiceException {
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"createCustomerCampaign.CustomerCampaignDTO:>>>>>" + cpDto);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"createCustomerCampaign.customerProductMap:>>>>>"
						+ customerProductMap);
		LogUtility.log(clazz, LogLevel.DEBUG,
				"createCustomerCampaign.selProductList:>>>>>" + selProductList);
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"createCustomerCampaign.bundlePrePaymentList:>>>>>" + bundlePrePaymentList);
		try {
			if (cpDto.getCampaignID() == 0)
				throw new ServiceException("����ID����Ϊ�գ�");
			if (cpDto.getCustomerID() == 0)
				throw new ServiceException("�ͻ��Ų���Ϊ�գ�");
			if (cpDto.getAccountID() == 0)
				throw new ServiceException("�ʺŲ���Ϊ�գ�");
			//�����ײ͵�������ش���δ����,�Ȳ���ǿ��Ҫ��
//			if (cpDto.getCsiID() == 0)
//				throw new ServiceException("����ID����Ϊ�գ�");
			String ccStatus=CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NEW;
			String cpStatus=CommonConstDefinition.GENERALSTATUS_VALIDATE;
			int csiID=cpDto.getCsiID();
			if(csiID!=0){
				CustServiceInteractionHome csiHome = HomeLocater.getCustServiceInteractionHome();
				CustServiceInteraction csi=csiHome.findByPrimaryKey(new Integer(csiID));
				String csiType=csi.getType();
				//�������ǿͻ���Ʒ����ʱ,���ÿͻ�����Ϊ��Ч
				if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN
						.equals(csiType)
						|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDS
								.equals(csiType)) {
					ccStatus = CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL;
//					cpStatus = CommonConstDefinition.GENERALSTATUS_VALIDATE;
				}
			}
			CampaignDTO campaignDto = BusinessUtility.getCampaignDTOByID(cpDto
					.getCampaignID());
			cpDto.setStatus(ccStatus);
			java.sql.Timestamp currentDate = TimestampUtility.getCurrentDate();
			cpDto.setDtCreate(currentDate);
			cpDto.setDtLastmod(currentDate);
			cpDto.setAutoExtendFlag(campaignDto.getAutoExtendFlag());
			cpDto.setCreateTime(currentDate);
			cpDto.setCreatreOpID(getOperatorID());
			cpDto.setCreateOrgID(BusinessUtility
					.getOpOrgIDByOperatorID(getOperatorID()));
			cpDto.setAllowPause(campaignDto.getAllowPause());
			cpDto.setAllowTransition(campaignDto.getAllowTransition());
			cpDto.setAllowTransfer(campaignDto.getAllowTransfer());
			cpDto.setAllowClose(campaignDto.getAllowClose());
			cpDto.setAllowAlter(campaignDto.getAllowAlter());
			
			Timestamp dateFrom = TimestampUtility.getCurrentDateWithoutTime();
			if(cpDto.getDateFrom()!=null){
				dateFrom=cpDto.getDateFrom();
			}
			Timestamp dateTo = TimestampUtility.getTimeEnd(dateFrom,
					campaignDto.getTimeLengthUnitType(),
					campaignDto.getTimeLengthUnitNumber());
			Timestamp nbrDate = dateFrom;
			//���ݴ����Ԥ�����б�,�����ײͽ�������,
			if (bundlePrePaymentList != null){
				for (Iterator bundlePrePaymentIt = bundlePrePaymentList
						.iterator(); bundlePrePaymentIt.hasNext();) {
					BundlePrepaymentDTO bundlePrePaymentDto = (BundlePrepaymentDTO) bundlePrePaymentIt
							.next();
					nbrDate = TimestampUtility.getDateEnd(nbrDate,
							bundlePrePaymentDto.getTimeUnitLengthType(),
							bundlePrePaymentDto.getTimeUnitLengthNumber());
				}
			}
			LogUtility.log(clazz, LogLevel.DEBUG,
					"createCustomerCampaign.dateTo:>>>>>"
							+ dateTo);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"createCustomerCampaign.nbrDate:>>>>>"
							+ nbrDate);
			cpDto.setNbrDate(nbrDate);
			cpDto.setPrePaidTo(nbrDate);
			cpDto.setDateFrom(dateFrom);
			cpDto.setDateTo(dateTo);

			CustomerCampaignHome ccHome = HomeLocater.getCustomerCampaignHome();
			CustomerCampaign cc = ccHome.create(cpDto);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"createCustomerCampaign.customerCampaign:>>>>>"
							+ cpDto);
			Map campaignAgmtProductMap =  BusinessUtility
					.getCampaignAgmtProductList(cpDto.getCampaignID());
			if (selProductList != null) {
				for (Iterator selProductIt = selProductList.iterator(); selProductIt
						.hasNext();) {
					Integer productID = (Integer) selProductIt.next();
					if (campaignAgmtProductMap.keySet().contains(productID)) {
						Integer psID = (Integer) customerProductMap
								.get(productID);
						if (psID == null || psID.intValue() == 0) {
							throw new ServiceException("��ǰ�ͻ���Ʒ�в������ײͱ����Ʒ!");
						}
						CPCampaignMapDTO cpcDto = new CPCampaignMapDTO();
						cpcDto.setStatus(cpStatus);
						cpcDto.setCustProductID(psID.intValue());
						cpcDto.setCcId(cc.getCcID().intValue());
						cpcDto.setDtCreate(currentDate);
						cpcDto.setDtLastmod(currentDate);
						CPCampaignMapHome cpcmHome = HomeLocater
								.getCPCampaignMapHome();
						cpcmHome.create(cpcDto);
						LogUtility.log(clazz, LogLevel.DEBUG,
								"createCustomerCampaign.cpCampaignMap:>>>>>"
										+ cpcDto);
					}
				}
			}
			return cc;
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "createCustomerCampaign", e1);
			throw new ServiceException("�����µĿͻ���Ʒ������¼��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "createCustomerCampaign", e);
			throw new ServiceException("�����µĿͻ���Ʒ������¼���Ҵ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����µĿͻ���Ʒ������¼���ִ���");
		}
	}

  /**
    * �޸Ŀͻ��Ż�״̬  add by david.Yang
    * @param isUpdateTime:�Ƿ�����Ż�ʱ�䣬���Ϊtrue�����ŻݵĿ�ʼʱ��Ϊ��ǰʱ�䣬��ֹʱ��Ϊ��ǰʱ�� + �Żݵ�ʱ�䳤��
    * @param psID
    */
   public void updateCustCampaign(int csiID,String status,boolean isUpdateTime)
      throws ServiceException{
	    LogUtility.log(clazz, LogLevel.DEBUG,"updateCustCampaign.csiID:>>>>>" + csiID);
		try{
		   CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
		   CPCampaignMapHome cpCampaignMapHome = HomeLocater.getCPCampaignMapHome();
		   Collection custCampaignCols =customerCampaignHome.findByCsiID(csiID);
		   if (custCampaignCols!=null && !custCampaignCols.isEmpty()){
   			  Iterator  custCampaignIter =custCampaignCols.iterator();
   			  while (custCampaignIter.hasNext()){
   				 CustomerCampaign  custCampaign =(CustomerCampaign)custCampaignIter.next();
   			     custCampaign.setStatus(status);
                 if (isUpdateTime){
					Timestamp curDate=TimestampUtility.getCurrentDateWithoutTime();
					//ȡ�����ڵ���ʼ��ʱ��ʱ�䳤��,����ʱ��ƽ��
					int length=TimestampUtility.getDaysBetweenTwoDay(curDate,custCampaign.getDateFrom())-1;
		    		LogUtility.log(clazz,LogLevel.DEBUG,"�Ż�ƽ��ʱ��:"+length);
					if(length<0)
			    		LogUtility.log(clazz,LogLevel.ERROR,"�Ż�ƽ��ʱ�����.");

					custCampaign.setDateFrom(curDate);
					custCampaign.setDateTo(TimestampUtility.getTimeEnd(custCampaign.getDateTo(),
							"D",length+1));
					custCampaign.setNbrDate(TimestampUtility.getDateEnd(custCampaign.getNbrDate(),
							"D",length));
					custCampaign.setPrePaidTo(TimestampUtility.getDateEnd(custCampaign.getPrePaidTo(),
							"D",length));
   			    }
   			    custCampaign.setDtLastmod(TimestampUtility.getCurrentDate());
   			    Collection cpCampaignMapCols =cpCampaignMapHome.findByCustomerCampaign(custCampaign.getCcID());
   			    if (cpCampaignMapCols !=null && !cpCampaignMapCols.isEmpty()){
   			    	Iterator cpCampaignMapIter =cpCampaignMapCols.iterator();
   			    	while (cpCampaignMapIter.hasNext()){
   			    		CPCampaignMap cpCampaignMap =(CPCampaignMap)cpCampaignMapIter.next();
   			    		cpCampaignMap.setStatus(status);
   			    		cpCampaignMap.setDtLastmod(TimestampUtility.getCurrentDate());
   			    	}
   			    }
   			 }
		  }
	   } catch(HomeFactoryException e1){
	        LogUtility.log(clazz,LogLevel.WARN,"updateCustCampaign��λ����");
	        throw new ServiceException(e1);
	   } catch(FinderException e2){
	    	LogUtility.log(clazz,LogLevel.WARN,"û���ҵ���Ӧ�Ŀͻ��Żݼ�¼��");
	    	throw new ServiceException(e2);
	   } catch(Exception e3){
		    LogUtility.log(clazz,LogLevel.WARN,"CampaignService��updateCustCampaign�쳣��");
	    	throw new ServiceException(e3);
	   }
		
		
		
	}
	public void close(){
		
	}

}
