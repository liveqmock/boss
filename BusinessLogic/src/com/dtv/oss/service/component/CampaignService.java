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
	 * 用于开户/增机失败的时候更新套餐/促销的客户化实例
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
	 * 取消客户促销产品
	 * @param custCampaignDTO
	 */
	public CustomerCampaign custCampaignCancel(CustomerCampaignDTO dto)throws ServiceException{
		try{
			//取消客户促销
			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
			CustomerCampaign custCamp=customerCampaignHome.findByPrimaryKey(new Integer(dto.getCcID()));
			custCamp.setStatus(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_CANCEL);
			Timestamp curDate = TimestampUtility.getCurrentDateWithoutTime();
			custCamp.setDateTo(curDate);
			custCamp.setNbrDate(curDate);
			custCamp.setPrePaidTo(curDate);
			custCamp.setDtLastmod(new Timestamp(System.currentTimeMillis()));

			//更新CPCampaignMap记录:
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
			throw new ServiceException("检查客户产品的依赖性关系时定位错误。");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e2);
			throw new ServiceException("检查客户产品的依赖性关系时查找错误。");
		}
	}
	/**
	 * 取消客户或者帐户关联的客户促销
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
	 * 客户套餐取消,设置头记录状态为终止,明细记录状态为取消,dateTo,NbrDate,PrePaidTo为当前日期.
	 * @param ccid
	 * @return
	 * @throws ServiceException
	 */
	public CustomerCampaign custBundleCancel(int ccid)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"custBundleCancel>>>>>"+ccid);
		try{
			//取消客户促销
			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
			CustomerCampaign custCamp=customerCampaignHome.findByPrimaryKey(new Integer(ccid));
			custCamp.setStatus(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_TRANSFE);
			custCamp.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			Timestamp curDate = TimestampUtility.getCurrentDateWithoutTime();
			custCamp.setDateTo(curDate);
			custCamp.setNbrDate(curDate);
			custCamp.setPrePaidTo(curDate);
			//更新CPCampaignMap记录:
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
			throw new ServiceException("取消客户套餐时定位错误。");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"checkProductDependency",e2);
			throw new ServiceException("取消客户套餐时查找错误。");
		}
	}
	/**
	 * 更新客户产品促销信息
	 * 
	 * @param cpCampaignMapDTO
	 * @throws ServiceException
	 * @param CustomerCampaignDTO
	 * @throws ServiceException 
	 */

	public void CustCampaignModify(CustomerCampaignDTO dto) throws ServiceException{
		try{
			//更新客户产品促销
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
			
			//更新CPCampaignMap记录:
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
	 * 延迟客户产品促销
	 * @param dto
	 * @throws ServiceException
	 */
	public  CustomerCampaign custBundleDelay(int customerCampaignID,Timestamp endDateTo,String comments)throws ServiceException{
		try{
			//延迟客户产品促销
			CustomerCampaignHome customerCampaignHome=HomeLocater.getCustomerCampaignHome();
			CustomerCampaign oldCustomerCampaign=customerCampaignHome.findByPrimaryKey(new Integer(customerCampaignID));
			oldCustomerCampaign.setDateTo(TimestampUtility.getTimeEnd(endDateTo,"D",0));
			Timestamp nbrDate =  TimestampUtility.getDateEnd(endDateTo,"D",1);
			oldCustomerCampaign.setNbrDate(nbrDate);
			oldCustomerCampaign.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			oldCustomerCampaign.setComments(comments);
			//延迟客户产品CPCMAP
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
			throw new ServiceException("更新客户产品促销定位错误。");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
			throw new ServiceException("更新客户产品促销查找错误。");
		}
	}
	/**
	 * 套餐预付费
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
				LogUtility.log(clazz, LogLevel.ERROR,"没有对应的paymentMethod记录!!");
				throw new ServiceException("套餐续费配置错误.");
			}
			// 当前客户套餐对象
			CustomerCampaignHome customerCampaignHome = HomeLocater
					.getCustomerCampaignHome();
			CustomerCampaign customerCampaign = customerCampaignHome
					.findByPrimaryKey(new Integer(customerCampaignID));
			//根据付费方法里定义的时间长度更新现在nbrDate,nbrDate=nbrDate+本次续费时间;
			customerCampaign.setNbrDate(TimestampUtility.getDateEnd(
					customerCampaign.getNbrDate(), paymentMethodDto
							.getTimeUnitLengthType(), paymentMethodDto
							.getTimeUnitLengthNumber()));
			customerCampaign.setPrePaidTo(TimestampUtility.getDateEnd(
					customerCampaign.getPrePaidTo(), paymentMethodDto
							.getTimeUnitLengthType(), paymentMethodDto
							.getTimeUnitLengthNumber()));			
			//如果客户套餐协议期结束日期<NbrDate，那么客户套餐协议期结束日期＝NbrDate
			Timestamp newDateTo=TimestampUtility.getTimeEnd(customerCampaign.getNbrDate(),"D",-1);
			
			if (customerCampaign.getDateTo().before(newDateTo)) {
				customerCampaign.setDateTo(newDateTo);
			}
			customerCampaign.setDtLastmod(TimestampUtility
					.getCurrentTimestamp());
			return customerCampaign;
		}catch(HomeFactoryException e1) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e1);
			throw new ServiceException("更新客户产品促销定位错误。");
		}catch(FinderException e2) {
			LogUtility.log(clazz,LogLevel.ERROR,"MantainCpCampaignMap",e2);
			throw new ServiceException("更新客户产品促销查找错误。");
		}
	}

	/**
	 * 套餐转换 需要完成的工作: 
	 * 1,取消一些产品, 
	 * 2,增加一些产品,
	 * 3,重新分配业务帐户,???!!!这里最麻烦,怎么匹配?如果取消完产品后,没有有效客户产品,取消该业务帐户,
	 * 然后把剩余的客户产品和新添加的客户产品放到一起,重新分组, 
	 * 4,更新旧的客户产品引用包,等相关信息.
	 * 5,创建新的customercampaign对象,
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
		LogUtility.log(clazz, LogLevel.DEBUG,"开始套餐转换>>>>>>>>>>" );
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

		//1,先取出旧套餐相关业务帐户下所有客户产品,移除掉要取消的客户产品.添加到有效的产品集合.
		//2,如果某业务帐户下产品全部取消,直接调用业务帐户取消方法,
		Map saMap = BusinessUtility.getServiceAccountMapByCustCampaignID(ccDto
				.getCcID(), true);
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"原来套餐下相关客户产品集合,原套餐相关所有业务帐户及产品:" , saMap);

		for (Iterator sait = saMap.keySet().iterator(); sait.hasNext();) {
			Integer said=(Integer) sait.next();
			//当前业务帐户下有效的客户产品.
			ArrayList curSaCpList =(ArrayList) saMap.get(said);
			//当前业务帐户下需要取消的客户产品.
			ArrayList curCancelPsidList=new ArrayList();
			ArrayList curValidCpList=new ArrayList();
			for (Iterator cpit = curSaCpList.iterator(); cpit.hasNext();) {
				CustomerProductDTO cpDto = (CustomerProductDTO) cpit.next();
				Integer psid=new Integer(cpDto.getPsID());
				//如果当前产品包含在取消产品集合中,
				if (opCancelCpList != null
						&& opCancelCpList.contains(psid)) {
					curCancelPsidList.add(psid);
				}else{
					curValidCpList.add(cpDto);
				}
			}
			//取消当前业务帐户下产品全部要取消,则关闭业务帐户.
			if(curValidCpList.isEmpty()){
				LogUtility.log(clazz, LogLevel.DEBUG,
						"业务帐户被取消:" + said.intValue());
				//这里暂定为不退设备，实际中根据需要来设置
				saService.close(said.intValue(),isReturnDevice);
				cancelSaList.add(said);
				opCancelCpList.removeAll(curCancelPsidList);
			}
			allValidCpList.addAll(curValidCpList);
		}
//		LogUtility.log(clazz, LogLevel.DEBUG,
//				"原来套餐下相关客户产品集合,移除了要取消的产品后,list.size()" + allValidCpList.size());
		//3,取消客户产品,不做设备流转,
		CustomerProductService cpService = new CustomerProductService(context);
		if(opCancelCpList!=null&&!opCancelCpList.isEmpty()){
			cpService.cancelCustomerProduct(opCancelCpList, isReturnDevice);
		}

		//4,把本次新增的产品加入全部有效的产品集合.
		if (addProductMap != null && !addProductMap.isEmpty()) {
			// 把本次操作新增加的产品加入有效产品集合.
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
//					"原来套餐下相关客户产品集合,加入了新增产品后的,list.size()"
//							+ allValidCpList.size());
		}
		//5,把原业务帐户下剩余的有效客户产品和新增加的产品,进行分组.
		Map splitValidCpMap = saService.splitCustomerProductWithService(allValidCpList);
		LogUtility.log(clazz, LogLevel.DEBUG,"有效产品分组后结果>>>>>>>>>>",splitValidCpMap );

		//6,检查分组结果,如果业务帐户产品为新增加产品,则创建业务帐户,
		//7,检查业务帐户下产品,如果产品是新增加的,创建客户产品,并入业务帐户,如果产品是旧的,更新产品引用包
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
			// 如果当前业务帐户产品的业务帐户为零,说明该业务帐户 不存在.
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
				// 如果当前产品业务帐户为零,说明是新添加产品.
				if (curCp.getServiceAccountID() == 0) {
					newProductList.add(curCp);
				} else if(campaignAgmtProductMap.keySet().contains(productID)){
					// 如果当前产品已经存在,且包含在新套餐协议中,则更新原来的引用包.
					cpService.updateCustomerProduct(curCp.getPsID(), accountID,
							serviceAccountID,
							newPackageID != null ? newPackageID.intValue() : 0);
				}
			}
			// 如果当前业务帐户下有新的产品,则创建客户产品,并设置到当前业务帐户下.
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
		//取消旧的客户套餐
		custBundleCancel(ccDto.getCcID());
		//建立新的套餐.
		ArrayList campaignList=new ArrayList();
		campaignList.add(new Integer(newCampaignID));
		ArrayList createCamapignList= createCustomerCampaign(allValidCpList,campaignList,saList,ccDto.getCustomerID(),ccDto.getAccountID(),csi.getId().intValue());
		LogUtility.log(clazz, LogLevel.DEBUG,"结束套餐转换>>>>>>>>>>" );
		return (createCamapignList!=null&&!createCamapignList.isEmpty())?(CustomerCampaign) createCamapignList.get(0):null;
	}

	/**
	 * 手工授予客户促销,如果促销配置了对应的客户产品,则从serviceAccount中取对应的customerproductList/selproduct
	 * 这个方法里假定了促销是有对应客户产品的,
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
			//取得当前业务帐户下所有产品,封装PSID的集合,和选择产品的集合,
			cpList=BusinessUtility.getCustProdDTOListBySaAndPsID(dto.getServiceAccountID(),0,"");
			Iterator it=cpList.iterator();
			while(it.hasNext()){
				CustomerProductDTO cpDto=(CustomerProductDTO) it.next();
				customerProductMap.put(new Integer(cpDto.getProductID()), new Integer(cpDto.getPsID()));
				selProductList.add(new Integer(cpDto.getProductID()));
			}
		}
		//调用创建促销方法,修改创建好促销的状态为正常,
		CustomerCampaign cc = createCustomerCampaign(dto, customerProductMap, selProductList, null);
		cc.setStatus(CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL);
		
		//修改cpcampaignmap记录的状态,
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
			throw new ServiceException("授予客户促销业务对象定位错误。");
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "grantNewCampaign", e);
			throw new ServiceException("授予客户促销业务对象定位错误。");
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
			throw new ServiceException("创建受理单相关客户产品记录失败.");
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
	 * 适应现有开户流程,可处理多业务帐户,多促销(一个套销,和其它相关的促销,如果要扩展为多个套餐同时开户,...需要再修改),不处理可选产品包,
	 * @param cpDTOList customerPorductDto的集合,前台选择的产品,
	 * @param campaignList 前台选择的套餐或者促销,
	 * @param serviceAccountList 创建的业务帐户,需要从这个里面捞出psid
	 * @param customerID 客户id
	 * @param accountID 套餐计费的帐户
	 * @param csiID 受理单的id
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
					"createCustomerCampaign.serviceAccountList:>>>>>没有需要创建的套餐列表");
			return null;
		}
		Map customerProductMap=new HashMap();
		ArrayList selProductList=new ArrayList();
		CustomerProductHome cpHome;
/**
 * 取serviceaccountid,目前customercampaign.serviceaccountid字段是int型,故只能放一个,允门面的,这个字段目前没什么用处.
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
		//取得套餐和对应的bundlePrePaymentDto集合,
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
 * 创建套餐/促销的客户化实例,
 * @param cpDto customerCampaign对象,封装campaignid/customerid/accountid/csiid/dateFrom
 * @param customerProductMap key:customerproduct对应的productID,value:PSID,
 * @param selProductList 前台传入的,选择的产品列表,
 * @param bundlePrePaymentList 当前套餐的对应的预付费计划列表,封装的是bundlePrePaymentDTO
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
				throw new ServiceException("促销ID不能为空！");
			if (cpDto.getCustomerID() == 0)
				throw new ServiceException("客户号不能为空！");
			if (cpDto.getAccountID() == 0)
				throw new ServiceException("帐号不能为空！");
			//促销套餐的受理单相关处理未完整,先不做强制要求
//			if (cpDto.getCsiID() == 0)
//				throw new ServiceException("受理单ID不能为空！");
			String ccStatus=CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NEW;
			String cpStatus=CommonConstDefinition.GENERALSTATUS_VALIDATE;
			int csiID=cpDto.getCsiID();
			if(csiID!=0){
				CustServiceInteractionHome csiHome = HomeLocater.getCustServiceInteractionHome();
				CustServiceInteraction csi=csiHome.findByPrimaryKey(new Integer(csiID));
				String csiType=csi.getType();
				//当受理单是客户产品新增时,设置客户促销为有效
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
			//根据传入的预付费列表,计算套餐结束日期,
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
							throw new ServiceException("当前客户产品中不包含套餐必须产品!");
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
			throw new ServiceException("创建新的客户产品促销记录定位错误。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "createCustomerCampaign", e);
			throw new ServiceException("创建新的客户产品促销记录查找错误。");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建新的客户产品促销记录出现错误");
		}
	}

  /**
    * 修改客户优惠状态  add by david.Yang
    * @param isUpdateTime:是否更新优惠时间，如果为true，则优惠的开始时间为当前时间，截止时间为当前时间 + 优惠的时间长度
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
					//取得现在到初始化时的时间长度,进行时间平移
					int length=TimestampUtility.getDaysBetweenTwoDay(curDate,custCampaign.getDateFrom())-1;
		    		LogUtility.log(clazz,LogLevel.DEBUG,"优惠平移时间:"+length);
					if(length<0)
			    		LogUtility.log(clazz,LogLevel.ERROR,"优惠平移时间错误.");

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
	        LogUtility.log(clazz,LogLevel.WARN,"updateCustCampaign定位出错！");
	        throw new ServiceException(e1);
	   } catch(FinderException e2){
	    	LogUtility.log(clazz,LogLevel.WARN,"没有找到相应的客户优惠记录！");
	    	throw new ServiceException(e2);
	   } catch(Exception e3){
		    LogUtility.log(clazz,LogLevel.WARN,"CampaignService的updateCustCampaign异常！");
	    	throw new ServiceException(e3);
	   }
		
		
		
	}
	public void close(){
		
	}

}
