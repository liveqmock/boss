/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CAWalletEJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.*;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.domain.*;
import com.dtv.oss.log.*;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CAWalletChargeRecordDTO;
import com.dtv.oss.dto.CAWalletDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.dto.PhoneNoUseLogDTO;
import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.dto.ServiceAccountDTO;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Leon Liu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiceAccountService extends AbstractService {
	private static final Class clazz = ServiceAccountService.class;

	private ServiceContext context;

	private int customerid = 0;

	private int accountid = 0;
	//产品缴费时间长度 ，以月为单位
	private int usedMonth=0;

	/**
	 * @return the usedMonth
	 */
	public int getUsedMonth() {
		return usedMonth;
	}

	/**
	 * @param usedMonth the usedMonth to set
	 */
	public void setUsedMonth(int usedMonth) {
		this.usedMonth = usedMonth;
	}

	private Collection productDependency=null;
	
    //客户化计费规则 add by yangchen 2008/07/23
    private Map customerBillingRuleMap; 
	/**
	 * @param accountid The accountid to set.
	 */
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	/**
	 * @param customerid The customerid to set.
	 */
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}


	/**
	 * @return the customerBillingRuleMap
	 */
	public Map getCustomerBillingRuleMap() {
		return customerBillingRuleMap;
	}

	/**
	 * @param customerBillingRuleMap the customerBillingRuleMap to set
	 */
	public void setCustomerBillingRuleMap(Map customerBillingRuleMap) {
		this.customerBillingRuleMap = customerBillingRuleMap;
	}

	/**
	 * @param context: 上下文对象
	 */
	public ServiceAccountService(ServiceContext context) {
		this.context = context;
		if (context.get(Service.CUSTOMER_ID) != null) {
			this.customerid = ((Integer) this.context.get(Service.CUSTOMER_ID))
					.intValue();
		}
		if (context.get(Service.ACCOUNT_ID) != null) {
			this.accountid = ((Integer) this.context.get(Service.ACCOUNT_ID))
					.intValue();
		} else if (context.get(Service.ACCOUNT) != null) {
			Account acc = (Account) context.get(Service.ACCOUNT);
			this.accountid = acc.getAccountID().intValue();

		}
		setOperatorID(PublicService.getCurrentOperatorID(context));
	}
	public ServiceAccountService(ServiceContext context,Collection productDependency) {
		this.context = context;
		if (context.get(Service.CUSTOMER_ID) != null) {
			this.customerid = ((Integer) this.context.get(Service.CUSTOMER_ID))
					.intValue();
		}
		if (context.get(Service.ACCOUNT_ID) != null) {
			this.accountid = ((Integer) this.context.get(Service.ACCOUNT_ID))
					.intValue();
		} else if (context.get(Service.ACCOUNT) != null) {
			Account acc = (Account) context.get(Service.ACCOUNT);
			this.accountid = acc.getAccountID().intValue();

		}
		setOperatorID(PublicService.getCurrentOperatorID(context));
		
		this.productDependency=productDependency;
	}
	/**
	 * 预约增机的预约单创建
	 * 
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void createSubScriberForBooking(ServiceAccountEJBEvent inEvent) throws ServiceException {
        //创建受理单信息
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		CSIService csiService=new CSIService(context);
		csiService.createCustServiceInteraction(csiDTO);
		
		//处理电话号码资源锁定 记录电话号码使用日志 add by chaoqiu 20070420
		int operatorID=PublicService.getCurrentOperatorID(context);
	    if (csiDTO.getServiceCodeList() != null) {
			String[] phoneNo = csiDTO.getServiceCodeList().split(",");
			for(int i=0;i<phoneNo.length;i++)
			{
				System.out.println("phoneNo[i]======="+phoneNo[i]);
				if(!"".equals(phoneNo[i]))
					lockPhoneNo(phoneNo[i],csiDTO.getCustomerID(),new Integer(operatorID),0);
			}
			
		}
	    //处理电话号码资源锁定 end
	    
        CustServiceInteraction csiEJB = (CustServiceInteraction)context.get(Service.CSI);
    	
		//创建受理单涉及的客户产品相关信息
        csiService.createCsiCustProductInfo( inEvent.getCsiPackageArray(), null, inEvent.getCsiCampaignArray(), csiDTO);
        try {
           //创建工单,调用工单service创建工单信息
    	   CustomerHome customerHome =HomeLocater.getCustomerHome();
           Customer customer=customerHome.findByPrimaryKey(new Integer(inEvent.getCustomerID()));
           CustomerDTO custoemrDto=new CustomerDTO();
           // 此时不能对JobCard的CustId设置值，否则会在还没有做预约增机的情况下，在安装反馈信息中查到该工单
           //8-22应张玉国要求,设置客户信息到工单,解决在客户树上无法查询工单的问题.
		   custoemrDto.setCustomerID(customer.getCustomerID().intValue());
		  
           //客户地址信息
    	   AddressDTO custAddrDto =new AddressDTO();
    	   AddressHome addressHome =HomeLocater.getAddressHome();
    	   Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
    	   custAddrDto.setAddressID(address.getAddressID().intValue());
    	   custAddrDto.setDistrictID(address.getDistrictID());
		   //创建工单,调用工单service创建工单信息
		   InstallationJobCardService installationJobCardService=new InstallationJobCardService(context);
	       int jobcardID=installationJobCardService.createJobCardForCustomerOperation(csiDTO,custoemrDto,custAddrDto,inEvent.getNextOrgID());
		   //设置受理单关联的工单id
		   csiEJB.setReferjobcardID(jobcardID);
        }catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中找不到相关信息");
			
		}
	}
	/**
	 * 预约增机的预约单修改
	 * @param inEvent
	 * @throws ServiceException
	 * @throws HomeFactoryException 
	 * @throws FinderException 
	 */
	public void updateSubscriberForBooking(ServiceAccountEJBEvent inEvent) throws ServiceException {
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		CSIService csiService=new CSIService(context);
		csiService.updateCustServiceInteractionInfo(csiDTO,inEvent.getActionType());
		//修改客户产品
		csiService.delectOldCsiCustProductInfo(csiDTO);
    	
		csiService.createCsiCustProductInfo( inEvent.getCsiPackageArray(), null, inEvent.getCsiCampaignArray(), csiDTO);
	    //更新工单

 		//创建工单,调用工单service修改工单信息
        InstallationJobCardService installationJobCardService=new InstallationJobCardService(context);
        installationJobCardService.updateJobCardForCustomerOperation(csiDTO);

	}
	/**
	 * 创建业务帐户对象(用于普通客户的开户、新增业务帐户)，创建后的状态都是初始状态的情况
	 * add by zhouxushun, 2005-10-21
	 * @param packageList：产品包列表
	 * @param campaignList：促销列表
	 */
	public void create(Collection packageList, Collection campaignList,
			String phoneNo, int itemID, Map productPropertyMap,boolean isBookingOpen,ArrayList serviceCodeList)throws ServiceException{
		create(packageList,campaignList,phoneNo,itemID,productPropertyMap,CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT,isBookingOpen,serviceCodeList);
	}
	/**
	 * 用于象集团客户子客户开户，子客户新增业务帐户立即生效的情况
	 * @param packageList
	 * @param campaignList
	 * @param phoneNo
	 * @param itemID
	 * @param productPropertyMap
	 * @throws ServiceException
	 */
	public void createForGroup(Collection packageList, Collection campaignList,
			String phoneNo, int itemID, Map productPropertyMap)throws ServiceException{
		create(packageList,campaignList,phoneNo,itemID,productPropertyMap,CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL,false,null);
	}
/**
 * 批量创建业务帐户 ,适用于门店增机
 * @param packageList
 * @param campaignList
 * @param phoneNo
 * @param itemID
 * @param productPropertyMap
 * @param groupNo
 * @param sheafNo
 * @return
 * @throws ServiceException
 */
	public String batchCreate(Collection packageList, Collection campaignList,
			String phoneNo, int itemID, Map productPropertyMap,
			int groupNo,
			int sheafNo) throws ServiceException {
		return batchCreate(packageList, campaignList, phoneNo, itemID, productPropertyMap,
				CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT,
				false, null, groupNo, sheafNo);
	}
	/**
	 * 批量创建业务帐户 ,适用于预约
	 * @param packageList
	 * @param campaignList
	 * @param phoneNo
	 * @param itemID
	 * @param productPropertyMap
	 * @param isBookingOpen
	 * @param serviceCodeList
	 * @param groupNo
	 * @param sheafNo
	 * @return
	 * @throws ServiceException
	 */
	public String batchCreate(Collection packageList, Collection campaignList,
			String phoneNo, int itemID, Map productPropertyMap,
			boolean isBookingOpen, ArrayList serviceCodeList, int groupNo,
			int sheafNo) throws ServiceException {
		return batchCreate(packageList, campaignList, phoneNo, itemID, productPropertyMap,
				CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT,
				isBookingOpen, serviceCodeList, groupNo, sheafNo);
	}
	/**
	 * 批量创建业务帐户 ,自定义初始状态.
	 * @param packageList
	 * @param campaignList
	 * @param phoneNo
	 * @param itemID
	 * @param productPropertyMap
	 * @param status
	 * @param isBookingOpen
	 * @param serviceCodeList
	 * @param groupNo
	 * @param sheafNo
	 * @return
	 * @throws ServiceException
	 */
	private String batchCreate(Collection packageList, Collection campaignList,
			String phoneNo, int itemID, Map productPropertyMap,String status,
			boolean isBookingOpen,ArrayList serviceCodeList,int groupNo,int sheafNo)
			throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();

		LogUtility.log(clazz, LogLevel.DEBUG, "处理产品，分组，为创建业务帐户做准备！"); 
		String strSAList="";
		
		//扩展产品包列表,product都以CustomerProductDTO来封装了
		Collection cpDTOList = BusinessUtility.getCustomerProductDTOListByPackageIDList(packageList);

		//分组的Map，Map的KEY为能创建业务帐户的产品，Value为其业务帐户下的产品List
		Map arrayMap = splitCustomerProductWithService(cpDTOList);
		
		//用来保存创建的业务帐户 ID
		ArrayList serviceAccountList=new ArrayList();
		
		//开始创建业务帐户
		CustServiceInteraction csi = (CustServiceInteraction) context.get(Service.CSI);
		Iterator saIt = arrayMap.keySet().iterator();
		boolean hasPhoneBusibess = false;
		while (saIt.hasNext()) {
			CustomerProductDTO saCPDTO = (CustomerProductDTO) saIt.next();
			Collection productList = (ArrayList) arrayMap.get(saCPDTO);
			productList.add(saCPDTO);
			ArrayList saCPDTOList = new ArrayList();
			saCPDTOList.add(saCPDTO);
			int serviceID = BusinessUtility
					.getServiceIDFromCustomerPoductDTOList(saCPDTOList);
			ServiceAccountDTO saDTO = new ServiceAccountDTO();
			saDTO.setCreateTime(new java.sql.Timestamp(System
					.currentTimeMillis()));
			saDTO.setCustomerID(customerid);
			saDTO.setServiceID(serviceID);
			//目前给语音项目使用,以后要去掉的
			saDTO.setDescription(csi.getComments());
			saDTO.setStatus(status);
			saDTO.setSubscriberID(0);
			saDTO.setUserID(0);
			//saDTO.setDescription("业务帐户");
			//语音业务
			if (serviceID == 3) {
				hasPhoneBusibess = true;
				if (phoneNo == "" || phoneNo == null)
					throw new ServiceException("语音业务没有选择电话号码，不能创建业务帐户！");
				else {
					boolean phoneUseFlag = false;
					LogUtility.log(clazz, LogLevel.DEBUG, "<><>phoneNo::"+phoneNo+" itemID::"+itemID); 
					//预约开户 对电话号码检查有所不同
					if(isBookingOpen)
					{
						phoneUseFlag = checkPhoneNoForBookOpen(phoneNo,itemID,serviceCodeList);
					}
					//其它情况 保持原来的检查逻辑
					else
						phoneUseFlag = checkPhoneNo(phoneNo, itemID);
					if (phoneUseFlag) {
						saDTO.setServiceCode(phoneNo);
					} else
						throw new ServiceException("语音业务电话号码不正确，不能创建业务帐户！");

				}
			}
			try {
				//创建业务帐户
				ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
				ServiceAccount sa = saHome.create(saDTO);
				serviceAccountList.add(sa.getServiceAccountID());
				
				if(!"".equals(strSAList))strSAList=strSAList+",";
				strSAList=strSAList + sa.getServiceAccountID();
				
				//创建业务帐户创建事件：161
				SystemEventRecorder.AddEvent4Customer(
						SystemEventRecorder.SE_SERVICEACCOUNT_OPEN, customerid,
						accountid, sa.getServiceAccountID().intValue(), csi
								.getId().intValue(), PublicService.getCurrentOperatorID(context),
						SystemEventRecorder.SE_STATUS_CREATE);

				LogUtility.log(clazz, LogLevel.DEBUG, "创建一个新的业务帐户，ID为："
						+ sa.getServiceAccountID());
				
				//电话号码状态变更、使用记录
				if (serviceID == 3)
				{
					// 预约开户 记录电话号码变更使用
					if(isBookingOpen)
						usePhoneNoForBookOpen(phoneNo, itemID,sa.getServiceAccountID().intValue(),serviceCodeList);
					//其它情况 保持原来的逻辑
					else
						usePhoneNo(phoneNo, itemID,sa.getServiceAccountID().intValue());
				}
				
				//创建客户产品
				CustomerProductService cpService = new CustomerProductService(
						context, customerid, accountid, sa
								.getServiceAccountID().intValue());
				cpService.setUsedMonth(this.usedMonth);
				//注意一下，由于业务帐户的状态和客户产品的状态在开户时的值是一样，为了简便就直接使用业务帐户的状态，
				//正常应该通过判断设置对应客户产品的状态
				cpService.createCustomerProductWithBatchBuy(productList,  sa
						.getServiceAccountID().intValue(), customerid,
						accountid,  productPropertyMap,status,groupNo,sheafNo);
			} catch (ServiceException e) {
				throw new ServiceException(e.getMessage());
			} catch (Exception e) {
				LogUtility.log(clazz, LogLevel.ERROR, e);
				throw new ServiceException("创建业务对象错误");
			}
		}
		
		//预约开户/预约增机 预约时有ip电话服务 现在开户时取消掉ip电话服务 需要将预约时锁定的电话号码释放
		if(isBookingOpen)
		{
			if(serviceCodeList != null && serviceCodeList.size()==1 && "".equals(serviceCodeList.get(0)))
			{
				serviceCodeList = new ArrayList();
			}
			if(serviceCodeList != null && serviceCodeList.size()>0 && !hasPhoneBusibess)
			{
				for(int i=0;i<serviceCodeList.size();i++)
				{
					String tempPhoneNo = (String)serviceCodeList.get(i);
					if(tempPhoneNo != null && !tempPhoneNo.trim().equals(""))
					releasePhoneNo(tempPhoneNo,0);
				}
			}
		}
		//电话号码释放结束
		
		CampaignService campaignService=new CampaignService(context);
		campaignService.createCustomerCampaign(cpDTOList, campaignList,serviceAccountList,customerid,accountid,csi.getId().intValue());
		
		//回置业务帐户ID,modify by jason 2007-3-8
		context.put(Service.SERVICE_ACCOUNT_ID, strSAList);
		return strSAList;
		//end
	}
	/**
	 * 客户产品分组的一个方法,原来在serviceaccountservice中,独立出的,
	 * 该方法调用前,请一定初始化productDependency
	 * 优化了下检查依赖的方法,在serviceaccountservice初始化的时候,先初始化一个产品依赖定义的缓存,以后判断依赖关系从缓存中取数据,避免游标溢出.
	 * @param cpDTOList CustomerProductDTO的集合,
	 * @return Map,key=可以创建业务帐户的产品,value=arraylist,依赖于key产品的产品集合,该集合不包括key, 
	 * @throws ServiceException
	 */
	public Map splitCustomerProductWithService(Collection cpDTOList)
	throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "需要分组的产品：", cpDTOList);
		Collection saProductList = BusinessUtility
				.getServicesListFromCustomerPoductDTOList(cpDTOList, false);
		
		LogUtility.log(clazz, LogLevel.DEBUG, "能创建业务帐户的产品为：", saProductList);
		// 分组的Map，Map的KEY为能创建业务帐户的产品，Value为其业务帐户下的产品List
		ArrayList opCPDTOList = (ArrayList) ((ArrayList) cpDTOList).clone();

		Map arrayMap = new LinkedHashMap();
		Iterator sacpit = saProductList.iterator();
		while (sacpit.hasNext()) {
			CustomerProductDTO saDto = (CustomerProductDTO) sacpit.next();
			opCPDTOList.remove(saDto);
			ArrayList dependList = getDependProductList(saDto.getProductID(),opCPDTOList);
			if(dependList==null){
				dependList=new ArrayList();
			}
			arrayMap.put(saDto, dependList);
			opCPDTOList.removeAll(dependList);
		}
		
		// 检查是否分组完毕
		if (opCPDTOList.containsAll(saProductList)) {
			opCPDTOList.removeAll(saProductList);
		}
		
		if (opCPDTOList.size() > 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "产品分组失败,分组后剩下的产品:",
					opCPDTOList);
			throw new ServiceException("产品购买不完整！");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "产品分组完毕！");
		LogUtility.log(clazz, LogLevel.DEBUG, "产品分组：" , arrayMap);
		return arrayMap;
	}

	private ArrayList getDependProductList(int productID, ArrayList sourceCol) throws ServiceException {
//		LogUtility.log(clazz, LogLevel.DEBUG, "getDependProductList!!!!!!!!!productID", productID+"");
//		LogUtility.log(clazz, LogLevel.DEBUG, "getDependProductList!!!!!!!!!sourceCol", sourceCol);
//		LogUtility.log(clazz, LogLevel.DEBUG, "getDependProductList!!!!!!!!!productDependency", productDependency);

		if (productID == 0 || sourceCol == null || sourceCol.isEmpty())
			return null;
		
		ArrayList opList = (ArrayList) sourceCol.clone();
		ArrayList result = new ArrayList();
		for (Iterator it = opList.iterator(); it.hasNext();) {
			CustomerProductDTO cpDto = (CustomerProductDTO) it.next();
			if (prodcutsIsDependency(cpDto.getProductID(), productID)) {
				result.add(cpDto);
			}
		}
		opList.removeAll(result);
		if(result!=null&&!result.isEmpty()&&opList!=null&&!opList.isEmpty()){
			for(int i=0;i<result.size();i++){
				CustomerProductDTO cpDto = (CustomerProductDTO) result.get(i);
				result.addAll(getDependProductList(cpDto.getProductID(),opList));
			}
		}
		return result;
	}
	/**
	 * 判断是否存在授权依赖和购买依赖关系.
	 * @param p1
	 * @param p2
	 * @return
	 * @throws ServiceException 
	 */
	private boolean prodcutsIsDependency(int p1,int p2) throws ServiceException{
//		LogUtility.log(clazz, LogLevel.DEBUG, "getDependProductList!!!!!!!!!p1:"+p1);
//		LogUtility.log(clazz, LogLevel.DEBUG, "getDependProductList!!!!!!!!!p2:"+p2);
		if(productDependency==null){
			LogUtility.log(clazz, LogLevel.ERROR, "检查依赖,准备参数不足.");
			throw new ServiceException("检查依赖,准备参数不足.");
		}
		for(Iterator it=productDependency.iterator();it.hasNext();){
			ProductDependencyDTO dto=(ProductDependencyDTO) it.next();
			if(dto.getProductId()==p1
					&&(CommonConstDefinition.PRODUCTDEPENDENCYTYPE_D.equals(dto.getType())
							||CommonConstDefinition.PRODUCTDEPENDENCYTYPE_P.equals(dto.getType()))){
				Collection dependProduct=PublicService.stringSplitToTargetObject(dto.getReferProductIDList(),",","String");
				LogUtility.log(clazz, LogLevel.DEBUG, "getDependProductList!!!!!!!!!dependProduct", dependProduct);
				if(dependProduct!=null&&dependProduct.contains(String.valueOf(p2))){
					return true;
				}
			}
		}
		return false;
	}
		
	/**
	 * 创建业务帐户对象
	 * add by zhouxushun, 2005-10-21
	 * @param packageList：产品包列表
	 * @param campaignList：促销列表
	 */
	private void create(Collection packageList, Collection campaignList,
			String phoneNo, int itemID, Map productPropertyMap,String status,boolean isBookingOpen,ArrayList serviceCodeList)
			throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();

		LogUtility.log(clazz, LogLevel.DEBUG, "处理产品，分组，为创建业务帐户做准备！");
		String strSAList="";
		
		//扩展产品包列表,product都以CustomerProductDTO来封装了
		Collection cpDTOList = BusinessUtility.getCustomerProductDTOListByPackageIDList(packageList);
		LogUtility.log(clazz, LogLevel.DEBUG, "拆分后的产品为：" + cpDTOList);
		//创建需要创建业务帐户的产品列表
		Collection cpForSAList = (ArrayList) ((ArrayList) cpDTOList).clone();
		Collection saProductList = BusinessUtility.getServicesListFromCustomerPoductDTOList(cpForSAList);

		LogUtility.log(clazz, LogLevel.DEBUG, "能创建业务帐户的产品为：" + saProductList);
		//分组的Map，Map的KEY为能创建业务帐户的产品，Value为其业务帐户下的产品List
		ArrayList opCPDTOList = (ArrayList) ((ArrayList) cpDTOList).clone();
		
		Map arrayMap = new HashMap();
		Iterator it = saProductList.iterator();
		while (it.hasNext()) {
			CustomerProductDTO dto = (CustomerProductDTO) it.next();
			Collection list = new ArrayList();
			arrayMap.put(dto, list);
		}

		//循环所有产品List，产品分组后放到arrayMap里面
		Iterator itBig = cpDTOList.iterator();
		while (itBig.hasNext()) {
			CustomerProductDTO product = (CustomerProductDTO) itBig.next();
			Iterator itSmall = saProductList.iterator();
			while (itSmall.hasNext()) {
				CustomerProductDTO saProduct = (CustomerProductDTO) itSmall.next();
				boolean isDependency = BusinessUtility.prodcutsIsDependency(product.getProductID(), saProduct.getProductID());
				if (isDependency) {
					fillArrayMap(product, saProduct, arrayMap);
					opCPDTOList.remove(product);
				}
			}
		}

		//检查是否分组完毕
		if (opCPDTOList.containsAll(saProductList)) {
			opCPDTOList.removeAll(saProductList);
		}

		LogUtility.log(clazz, LogLevel.DEBUG, "分组后的剩下的产品：" + opCPDTOList);

		if (opCPDTOList.size() > 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "产品分组失败，不能创建业务帐户！");
			throw new ServiceException("产品购买不完整，不能创建业务帐户！");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "产品分组完毕，开始创建业务帐户！");
		LogUtility.log(clazz, LogLevel.DEBUG, "产品分组：" + arrayMap.keySet());

		//匹配优惠
		ArrayList serviceAccountList=new ArrayList();
		
		
		//开始创建业务帐户
		CustServiceInteraction csi = (CustServiceInteraction) context.get(Service.CSI);
		Iterator saIt = arrayMap.keySet().iterator();
		boolean hasPhoneBusibess = false;
		while (saIt.hasNext()) {
			CustomerProductDTO saCPDTO = (CustomerProductDTO) saIt.next();
			ArrayList productList = (ArrayList) arrayMap.get(saCPDTO);
			productList.add(0,saCPDTO);//把创建业务帐户的产品放前面,
//			productList.add(saCPDTO);
			ArrayList saCPDTOList = new ArrayList();
			saCPDTOList.add(saCPDTO);
			int serviceID = BusinessUtility
					.getServiceIDFromCustomerPoductDTOList(saCPDTOList);
			ServiceAccountDTO saDTO = new ServiceAccountDTO();
			saDTO.setCreateTime(new java.sql.Timestamp(System
					.currentTimeMillis()));
			saDTO.setCustomerID(customerid);
			saDTO.setServiceID(serviceID);
			//目前给语音项目使用,以后要去掉的
			saDTO.setDescription(csi.getComments());
			//----------yangchen-----------2006/11/23-----------start---------------
/*
			//创建时业务帐户状态为初始， modify 2005-12-15
			saDTO.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT);
			//11-21侯添加,当受理单类型为集团客户添加子客户时,业务帐户状态为正常
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS.equals(csi.getType())){
				saDTO.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
			}
*/			
			saDTO.setStatus(status);
			//----------yangchen-----------2006/11/23-----------end---------------
			saDTO.setSubscriberID(0);
			saDTO.setUserID(0);
			//saDTO.setDescription("业务帐户");
			//语音业务
			if (serviceID == 3) {
				hasPhoneBusibess = true;
				if (phoneNo == "" || phoneNo == null)
					throw new ServiceException("语音业务没有选择电话号码，不能创建业务帐户！");
				else {
					boolean phoneUseFlag = false;
					//预约开户 对电话号码检查有所不同
					if(isBookingOpen)
					{
						phoneUseFlag = checkPhoneNoForBookOpen(phoneNo,itemID,serviceCodeList);
					}
					//其它情况 保持原来的检查逻辑
					else
						phoneUseFlag = checkPhoneNo(phoneNo, itemID);
					if (phoneUseFlag) {
						saDTO.setServiceCode(phoneNo);
					} else
						throw new ServiceException("语音业务电话号码不正确，不能创建业务帐户！");

				}
			}
			try {
				//创建业务帐户
				ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
				ServiceAccount sa = saHome.create(saDTO);
				serviceAccountList.add(sa.getServiceAccountID());
				
				if(!"".equals(strSAList))strSAList=strSAList+",";
				strSAList=strSAList + sa.getServiceAccountID();
				
				//创建业务帐户创建事件：161
				SystemEventRecorder.AddEvent4Customer(
						SystemEventRecorder.SE_SERVICEACCOUNT_OPEN, customerid,
						accountid, sa.getServiceAccountID().intValue(), csi
								.getId().intValue(), PublicService.getCurrentOperatorID(context),
						SystemEventRecorder.SE_STATUS_CREATE);

				LogUtility.log(clazz, LogLevel.DEBUG, "创建一个新的业务帐户，帐户ID为："
						+ sa.getServiceAccountID());
				
				//电话号码状态变更、使用记录
				if (serviceID == 3)
				{
					// 预约开户 记录电话号码变更使用
					if(isBookingOpen)
						usePhoneNoForBookOpen(phoneNo, itemID,sa.getServiceAccountID().intValue(),serviceCodeList);
					//其它情况 保持原来的逻辑
					else
						usePhoneNo(phoneNo, itemID,sa.getServiceAccountID().intValue());
				}
				
				//创建客户产品
				CustomerProductService cpService = new CustomerProductService(
						context, customerid, accountid, sa
								.getServiceAccountID().intValue());
			    /*********************add by yangchen 2008/07/23 start***************************************************/
				cpService.setCustomerBillingRuleMap(getCustomerBillingRuleMap());
			    /*********************add by yangchen 2008/07/23 end***************************************************/
				
				//注意一下，由于业务帐户的状态和客户产品的状态在开户时的值是一样，为了简便就直接使用业务帐户的状态，
				//正常应该通过判断设置对应客户产品的状态
				cpService.create(productList,  sa
						.getServiceAccountID().intValue(), customerid,
						accountid,  productPropertyMap,status);
			} catch (Exception e) {
				LogUtility.log(clazz, LogLevel.ERROR, e);
				throw new ServiceException("创建业务对象错误");
			}
		}
		
		//预约开户/预约增机 预约时有ip电话服务 现在开户时取消掉ip电话服务 需要将预约时锁定的电话号码释放
		if(isBookingOpen)
		{
			if(serviceCodeList != null && serviceCodeList.size()==1 && "".equals(serviceCodeList.get(0)))
			{
				serviceCodeList = new ArrayList();
			}
			if(serviceCodeList != null && serviceCodeList.size()>0 && !hasPhoneBusibess)
			{
				for(int i=0;i<serviceCodeList.size();i++)
				{
					String tempPhoneNo = (String)serviceCodeList.get(i);
					if(tempPhoneNo != null && !tempPhoneNo.trim().equals(""))
					releasePhoneNo(tempPhoneNo,0);
				}
			}
		}
		//电话号码释放结束
		
		CampaignService campaignService=new CampaignService(context);
		campaignService.createCustomerCampaign(cpDTOList, campaignList,serviceAccountList,customerid,accountid,csi.getId().intValue());
		
		//回置业务帐户ID,modify by jason 2007-3-8
		context.put(Service.SERVICE_ACCOUNT_ID, strSAList);
		//end
	}
/**
 * 创建一个业务帐户,不创建产品,
 * @param saCpDto newsaflag的产品,
 * @param phoneNo 语音业务的电话号码,
 * @param itemID 号码资源ID
 * @param status 业务帐户状态.
 * @return
 * @throws ServiceException
 */
	public ServiceAccount createServiceAccountOnly(CustomerProductDTO saCpDto,int customerID,
			String phoneNo, int itemID, String status) throws ServiceException {
		CustServiceInteraction csi = (CustServiceInteraction) context
				.get(Service.CSI);
		ArrayList saCPDTOList = new ArrayList();
		saCPDTOList.add(saCpDto);
		int serviceID = BusinessUtility
				.getServiceIDFromCustomerPoductDTOList(saCPDTOList);
		ServiceAccountDTO saDTO = new ServiceAccountDTO();
		saDTO.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		saDTO.setCustomerID(customerID);
		saDTO.setServiceID(serviceID);
		saDTO.setStatus(status);
		saDTO.setSubscriberID(0);
		saDTO.setUserID(0);
		saDTO.setDescription("业务帐户");
		// 语音业务
		if (serviceID == 3) {
			if (phoneNo == "" || phoneNo == null)
				throw new ServiceException("语音业务没有选择电话号码，不能创建业务帐户！");
			else {
				if (checkPhoneNo(phoneNo, itemID)) {
					saDTO.setServiceCode(phoneNo);
				} else
					throw new ServiceException("语音业务电话号码不正确，不能创建业务帐户！");

			}
		}
		try {
			// 创建业务帐户
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.create(saDTO);

			context.put(Service.SERVICE_ACCOUNT_EJB, sa);
			
			// 创建业务帐户创建事件：161
			SystemEventRecorder.AddEvent4Customer(
					SystemEventRecorder.SE_SERVICEACCOUNT_OPEN, customerid,
					accountid, sa.getServiceAccountID().intValue(), csi.getId()
							.intValue(), PublicService
							.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE);

			LogUtility.log(clazz, LogLevel.DEBUG, "创建一个新的业务帐户，帐户ID为："
					+ sa.getServiceAccountID());

			// 电话号码状态变更、使用记录
			if (serviceID == 3) {
				usePhoneNo(phoneNo, itemID, sa.getServiceAccountID().intValue());
			}
			return sa;
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务对象错误");
		}
	}
	/**
	 * 业务帐户暂停
	 * @param said：业务帐户ID
	 * @throws ServiceException
	 * @throws CreateException
	 */
	public void suspend(int said) throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();

		//得到CSIID
		CustServiceInteraction csiEJB = (CustServiceInteraction)context.get(Service.CSI);
		int csiID = csiEJB.getId().intValue();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			if (!sa.getStatus().equals(
					CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL)) {
				LogUtility.log(clazz, LogLevel.ERROR, "业务帐户不是正常状态，不能暂停！");
				throw new ServiceException("业务帐户不是正常状态，不能暂停！");
			}
			//修改业务帐户状态----主动暂停
			sa
					.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_REQUESTSUSPEND);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
			//增加业务帐户描述
			if(csiEJB.getCreateReason()!=null){
				sa.setDescription(BusinessUtility.getCsiActionReasonDesc(csiEJB.getType(),csiEJB.getCreateReason()));
			}

			//得到相同业务帐户下的所有的产品
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());

			//产品依赖的硬件设备
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String linkToDevice1SerialNo = "";
			String linkToDevice2SerialNo = "";

			//调用组件CustomerProductService的pauseCustomerProduct方法和创建系统事件
			CustomerProductService cpService = new CustomerProductService(
					context, 0, 0, said);
			Iterator itCP = cpList.iterator();
			Collection cpIDList = new ArrayList();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
                if (cp.getDeviceID() !=0) continue;
				
				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
				//只有不为取消状态的产品才能暂停
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
					cpIDList.add(cp.getPsID());
			}
			cpService.pauseCustomerProduct(cpIDList);

			//得到设备序列号
			if (linkToDevice1 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice1)).getSerialNo();
			}
			if (linkToDevice2 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice2)).getSerialNo();
			}

			//创建业务帐户的系统事件
			SystemEventRecorder.addEvent4ServiceAccount(
					SystemEventRecorder.SE_SERVICEACCOUNT_PAUSE, customerid,
					accountid, said, csiID, 
					linkToDevice1,linkToDevice1SerialNo, 
					linkToDevice2,linkToDevice2SerialNo, 
					PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
			
			//创建系统日志记录
			String logDesc="";
			if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
			}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",";
			}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice2SerialNo+",";
			}
			
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), customerid,
					SystemLogRecorder.LOGMODULE_CUSTSERV, "业务帐户停机", "业务帐户停机，受理单ID:"+csiID
					+",业务帐户ID："
							+ said+","+logDesc, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			throw new ServiceException("业务帐户查找出错！");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建业务帐户系统事件出错！");
			throw new ServiceException("创建业务帐户系统事件出错！");
		}
	}

	/**
	 * 设备转租
	 * @param said：业务帐户ID,dopost:是否录入信息
	 * @throws ServiceException
	 * @throws CreateException
	 */
	public void rent(int said,boolean doPost) throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();
		
		//得到CSIID
		CustServiceInteraction csiEJB = (CustServiceInteraction)context.get(Service.CSI);
		int csiID = csiEJB.getId().intValue();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			if (!sa.getStatus().equals(
					CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL)) {
				LogUtility.log(clazz, LogLevel.ERROR, "业务帐户不是正常状态，不能设备转租！");
				throw new ServiceException("业务帐户不是正常状态，不能设备转租！");
			}
			
			//增加业务帐户描述
			if(csiEJB.getCreateReason()!=null){
				sa.setDescription(BusinessUtility.getCsiActionReasonDesc(csiEJB.getType(),csiEJB.getCreateReason()));
			}

			//得到相同业务帐户下的所有的产品
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());

			//产品依赖的硬件设备
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String SerialNo = null;
			String linkToDevice1SerialNo=null;
            String linkToDevice2SerialNo=null;
			//修改设备信息和创建系统事件
			
			//创建系统日志记录
			StringBuffer logDesc=new StringBuffer();
			Iterator itCP = cpList.iterator();
			TerminalDeviceHome tdhome=HomeLocater.getTerminalDeviceHome();
			TerminalDevice terDev=null;
			
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
                if (cp.getDeviceID() ==0) continue;
				
				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
				//只有不为取消状态的产品才能转租
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
				{
					if(doPost){										
		                	terDev=tdhome.findByPrimaryKey(new Integer(cp.getDeviceID()));
		                	SerialNo=terDev.getSerialNo();
		                	if(SerialNo!=null&&!SerialNo.equals("")){
		        				logDesc.append(terDev.getDeviceClass()+":"+SerialNo+",产品创建时间"+cp.getCreateTime()+";");
		        			}
		                	cp.setCreateTime(TimestampUtility.getCurrentTimestamp());
		                	cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
		                	terDev.setPurposeStrList(",R,");
					} 
				}
			}
			
			TerminalDeviceHome terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			//得到设备序列号
			if (linkToDevice1 != 0) {
				linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(new Integer(linkToDevice1)).getSerialNo();			
			}
			if (linkToDevice2 != 0) {				
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(new Integer(linkToDevice2)).getSerialNo();				
			}

			//创建业务帐户的系统事件
			SystemEventRecorder.addEvent4ServiceAccount(
					SystemEventRecorder.SE_SERVICEACCOUNT_RENT, customerid,
					accountid, said, csiID, 
					linkToDevice1,linkToDevice1SerialNo, 
					linkToDevice2,linkToDevice2SerialNo, 
					PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
					
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), customerid,
					SystemLogRecorder.LOGMODULE_CUSTSERV, "设备转租", "设备转租，受理单ID:"+csiID
					+",业务帐户ID："
							+ said+","+logDesc.toString(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			e.printStackTrace();
			throw new ServiceException("业务帐户查找出错！");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建业务帐户系统事件出错！");
			throw new ServiceException("创建业务帐户系统事件出错！");
		}
	}
	/**
	 *  恢复暂停状态的业务帐户
	 * @param said：业务帐户ID
	 * @throws ServiceException
	 */
	public void resume(ServiceAccountEJBEvent event) throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();

		//得到CSIID
		CustServiceInteraction csiEJB = (CustServiceInteraction)context.get(Service.CSI);
		int csiID = csiEJB.getId().intValue();
		int said =event.getServiceAccountID();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			if (!(sa.getStatus().equals(
					CommonConstDefinition.SERVICEACCOUNT_STATUS_REQUESTSUSPEND) || sa
					.getStatus()
					.equals(
							CommonConstDefinition.SERVICEACCOUNT_STATUS_SYSTEMSUSPEND))) {
				LogUtility.log(clazz, LogLevel.ERROR, "业务帐户不是暂停状态，不能恢复！");
				throw new ServiceException("业务帐户不是暂停状态，不能恢复！");
			}
			//修改业务帐户状态----正常
			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
			//增加业务帐户描述
			//if(csiEJB.getCreateReason()!=null){ 2008-06-27
				sa.setDescription(BusinessUtility.getCsiActionReasonDesc(csiEJB.getType(),csiEJB.getCreateReason()));
			//}
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
			//得到相同业务帐户下的所有的产品
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());

			context.put(Service.CUSTOMER_PRODUCT_LIST, cpList);
			//产品依赖的硬件设备
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String linkToDevice1SerialNo = "";
			String linkToDevice2SerialNo = "";

			//调用组件CustomerProductService的pauseCustomerProduct方法和创建系统事件
			CustomerProductService cpService = new CustomerProductService(
					context, 0, 0, said);
			Iterator itCP = cpList.iterator();
			Collection cpIDList = new ArrayList();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				if (!event.getColPsid().contains(cp.getPsID()))
						continue;
				//过滤硬件产品
				if (cp.getDeviceID() >0) continue;

				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
				//只有不为取消状态的产品才能恢复
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
					cpIDList.add(cp.getPsID());
			}
			cpService.resumeCustomerProduct(cpIDList);

			//得到设备序列号
			if (linkToDevice1 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice1)).getSerialNo();
			}
			if (linkToDevice2 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice2)).getSerialNo();
			}

			//创建业务帐户的系统事件
			SystemEventRecorder.addEvent4ServiceAccount(
					SystemEventRecorder.SE_SERVICEACCOUNT_RESUME, customerid,
					accountid, said, csiID, 
					linkToDevice1,linkToDevice1SerialNo, 
					linkToDevice2,linkToDevice2SerialNo, 
					PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND);

			//创建系统日志记录
			String logDesc="";
			if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
			}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",";
			}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice2SerialNo+",";
			}
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), customerid,
					SystemLogRecorder.LOGMODULE_CUSTSERV, "业务帐户复机", "业务帐户复机,受理单ID:"+csiID
					+",业务帐户:"
							+ said + ","+logDesc, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			throw new ServiceException("业务帐户查找出错！");
		}

		catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建业务帐户系统事件出错！");
			throw new ServiceException("创建业务帐户系统事件出错！");
		}

	}

	/**
	 * 用户销户/业务帐户销户：不进行设备流转
	 * @param said
	 * @throws ServiceException
	 */
/*
	public void closeOnly(int said) throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();

		//得到CSIID
		int csiID = getCSIID();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));

			//修改业务帐户状态----取消
			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL);

			//得到相同业务帐户下的所有的产品
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			context.put(Service.CUSTOMER_PRODUCT_LIST, cpList);
			
			//调用组件CustomerProductService的pauseCustomerProduct方法和创建系统事件
			CustomerProductService cpService = new CustomerProductService(
					context, 0, 0, said);
			Iterator itCP = cpList.iterator();
			Collection cpIDList = new ArrayList();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
					cpIDList.add(cp.getPsID());
			}
			cpService.cancelcustProductOnly(cpIDList);

			//创建业务帐户的系统事件
			SystemEventRecorder.AddEvent4Customer(
					SystemEventRecorder.SE_SERVICEACCOUNT_CANCEL, customerid,
					accountid, said, csiID, PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE);

			//电话号码状态变更、使用记录
			if (sa.getServiceID() == 3)
				releasePhoneNo(sa.getServiceCode(),said);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			throw new ServiceException("业务帐户查找出错！");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建业务帐户系统事件出错！");
			throw new ServiceException("创建业务帐户系统事件出错！");
		}
	}
*/
	/**
	 * 用户退户：包括设备的流转
	 * @param said： 业务帐户ID
	 * @throws ServiceException
	 */
	public void close(int said,boolean returnDevice) throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();

		//得到CSIID
		int csiID = getCSIID();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));

			//修改业务帐户状态----取消
			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
			//得到相同业务帐户下的所有的产品
//			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList= BusinessUtility.getPsIDListByServiceAccount(said, null," STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
//			Collection cpList = cpHome.findByServiceAccountID(sa
//					.getServiceAccountID().intValue());

			//调用组件CustomerProductService的pauseCustomerProduct方法和创建系统事件
			CustomerProductService cpService = new CustomerProductService(
					context, sa.getCustomerID(), 0, said);

			cpService.cancelCustomerProduct(cpList,returnDevice);

			//创建业务帐户的系统事件
			SystemEventRecorder.AddEvent4Customer(
					SystemEventRecorder.SE_SERVICEACCOUNT_CANCEL, customerid,
					accountid, said, csiID, PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE);

	    	//电话号码状态变更、使用记录
			if (sa.getServiceID() == 3)
				releasePhoneNo(sa.getServiceCode(),said);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			throw new ServiceException("业务帐户查找出错！");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建业务帐户系统事件出错！");
			throw new ServiceException("创建业务帐户系统事件出错！");
		}
	}
	/**
	 * 用户预退户：包括设备的流转
	 * @param said： 业务帐户ID
	 * @throws ServiceException
	 */
	public void beforehandClose(int said,boolean returnDevice) throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();

		//得到CSIID
		int csiID = getCSIID();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			//修改业务帐户状态----预退
			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_BEFOREHANDCLOSE);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
	    	//电话号码状态变更、使用记录
			if (sa.getServiceID() == 3)
				releasePhoneNo(sa.getServiceCode(),said);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			throw new ServiceException("业务帐户查找出错！");
		} 
	}
	
	public void batchResendEMM(int eventClass) throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();
		Collection softCol =BusinessUtility.getCustomerSoft(customerid);
		Collection execCol =new ArrayList();
		Iterator   softItr =softCol.iterator();
		while (softItr.hasNext()){
			Collection tempCol =(Collection)softItr.next();
			execCol.add(tempCol);
			System.out.println("@@@@execCol.size()**************>"+execCol.size());
			if (execCol.size() ==100){
			   BusinessUtility.insertSystemEventRecord(execCol,eventClass,SystemEventRecorder.SE_STATUS_CREATE,getOperatorID());
			   execCol.clear();
			}
		}
		
		if (!execCol.isEmpty()){
			System.out.println("execCol.size()----->"+execCol.size());
		    BusinessUtility.insertSystemEventRecord(execCol,eventClass,SystemEventRecorder.SE_STATUS_CREATE,getOperatorID());
		}
	}
	
	/**
	 * 产品重授权
	 * @param said
	 * @throws ServiceException
	 */
	public String resendEMM(int said ,int eventClass) throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();
		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			//得到相同业务帐户下的所有的产品
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			//产品依赖的硬件设备
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String linkToDevice1SerialNo = "";
			String linkToDevice2SerialNo = "";

			Iterator itCP = cpList.iterator();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();

				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
			}
			//得到设备序列号
			if (linkToDevice1 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice1)).getSerialNo();
			}
			if (linkToDevice2 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice2)).getSerialNo();
			}

			//获得业务账户下智能卡的产品号和客户产品号
			int psid = 0;
			int productid = 0;
			try {
				Map rs = BusinessUtility.getDeviceInfoBySAID(said, "SC");
				for(Iterator it=rs.entrySet().iterator();it.hasNext();) {
					Entry en=(Entry) it.next();
					psid =((Integer)en.getKey()).intValue();
					productid = ((Integer)en.getValue()).intValue();
				}
			} catch (Exception sqlex) {
				LogUtility.log(clazz, LogLevel.WARN, sqlex);
				throw new ServiceException("查找智能卡的产品号和客户产品号失败！");
			}
			//创建业务帐户的系统事件
			SystemEventRecorder.addEvent4ServiceAccount(
					eventClass, customerid, accountid,
					said, productid, psid, linkToDevice1,
					linkToDevice1SerialNo, linkToDevice2,
					linkToDevice2SerialNo, getOperatorID(), null,
					SystemEventRecorder.SE_STATUS_CREATE);

			//创建系统日志记录
			String logDesc="产品重授权,业务帐户ID:"	 + said + ",";
			if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc=logDesc+",涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
			}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
				logDesc=logDesc+",涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",";
			}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc=logDesc+",涉及的设备序列号:"+"(1)"+linkToDevice2SerialNo+",";
			}
			return logDesc;
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			throw new ServiceException("业务帐户查找出错！");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建业务帐户系统事件出错！");
			throw new ServiceException("创建业务帐户系统事件出错！");
		}
	}

	/**
	 * 业务帐户过户
	 * @param saID ：被过户的用户的业务帐户ID
	 * @param customerID　：　目标客户
	 * @param accountID　：　目标帐户
	 * @throws ServiceException
	 */
	public void transfer(int saID, int customerID, int accountID)
			throws ServiceException {

		LogUtility.log(clazz, LogLevel.DEBUG, "业务帐户过户开始！");
		//检查customerid、accountid是否存在
		checkServiceContext();

		if (saID == 0 || customerID == 0 || accountID == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}

		Collection deviceIDList = new ArrayList();
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(saID));

			if (!sa.getStatus().equals(
					CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL)) {
				LogUtility.log(clazz, LogLevel.ERROR, "业务帐户不是正常状态，不能过户！");
				throw new ServiceException("业务帐户不是正常状态，不能过户！");
			}
			LogUtility.log(clazz, LogLevel.DEBUG, "源业务帐户:"+sa);

			sa.setCustomerID(customerID);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
//			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT);

			//得到相同业务帐户下的所有的产品
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			LogUtility.log(clazz, LogLevel.DEBUG, "源业务帐户下产品:"+cpList);

			
			//产品依赖的硬件设备
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String linkToDevice1SerialNo = "";
			String linkToDevice2SerialNo = "";
			//修改客户产品字段
			Iterator itCP = cpList.iterator();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				//取得对应的业务帐户下面的能发授权的设备
				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();

				//只有不为取消状态的产品才能恢复
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus()))) {
					cp.setCustomerID(customerID);
					cp.setAccountID(accountID);
//					cp.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_INIT);
//					cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
					cp.setDtLastmod(TimestampUtility.getCurrentDate());
					
					//如果是设备，进行相应的流转
					if (cp.getDeviceID() > 0)
						deviceIDList.add(new Integer(cp.getDeviceID()));
				}
			}
			//设备流转
			LogUtility.log(clazz, LogLevel.DEBUG, "业务帐户下需要流转的设备:"+deviceIDList);

			deviceTransitionForTranfer(deviceIDList, customerID);

			//修改业务帐户的优惠
			CustomerCampaignHome ccHome = HomeLocater.getCustomerCampaignHome();
			Collection ccList = ccHome.findBySAID(saID);
			int csiID = ((Integer)context.get(Service.CSI_ID)).intValue();
			if (ccList != null && ccList.size() > 0) {
				Iterator itCC = ccList.iterator();

				//CustServiceInteraction csiEJB = (CustServiceInteraction) context
				//		.get(Service.CSI);
				
				//if (csiEJB != null)
				//	csiID = csiEJB.getId().intValue();

				while (itCC.hasNext()) {
					CustomerCampaign cc = (CustomerCampaign) itCC.next();
					if (CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_NORMAL.equals(cc
							.getStatus())) {
						cc.setAccountID(accountID);
						cc.setCustomerID(customerID);
					//	cc.setCsiID(csiID);
						cc.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					}
				}
			}
			//得到设备序列号
			if (linkToDevice1 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice1)).getSerialNo();
			}
			if (linkToDevice2 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice2)).getSerialNo();
			}
			//创建业务帐户过户的系统事件
			SystemEventRecorder.addEvent4ServiceAccount(
					SystemEventRecorder.SE_SERVICEACCOUNT_TRANSFER, customerID,
					accountID, saID, csiID, 
					linkToDevice1,linkToDevice1SerialNo, 
					linkToDevice2,linkToDevice2SerialNo, 
					PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE,sa.getStatus());
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			throw new ServiceException("业务帐户查找出错！");
		}catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建业务帐过户系统事件出错！");
			throw new ServiceException("创建业务帐过户系统事件出错！");
		}
	}

	/**
	 * 机卡配对、解配对、消除密码、发送消息（modified by wesley）
	 * @param saID :　业务帐户ＩＤ
	 * @param content : 发送消息内容
	 * @param type : 操作类型: M--机卡配对、D--机卡解配对、C--消除密码、S--发送消息
	 * @throws ServiceException
	 */
	public void matchSCtoSTB(int saID, String content, String type)
			throws ServiceException {
		//检查customerid、accountid是否存在
		checkServiceContext();
		if (saID == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户参数错误！");
			throw new ServiceException("业务帐户参数错误！");
		}
		if (!(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_D)
				|| type.equals(CommonConstDefinition.MATCH_SC_TO_STB_M)
				|| type.equals(CommonConstDefinition.MATCH_SE_CA_CLEARPWD) || type
				.equals(CommonConstDefinition.MATCH_SE_CA_SENDMAIL))) {
			LogUtility.log(clazz, LogLevel.ERROR, "未知操作类型！");
			throw new ServiceException("未知操作类型！");
		}
		String log = "";
		String logDesc="";
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(saID));
			//得到相同业务帐户下的所有的产品
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			//产品依赖的硬件设备
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String linkToDevice1SerialNo = "";
			String linkToDevice2SerialNo = "";
			
			Iterator itCP = cpList.iterator();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				if (cp.getLinkToDevice1() != 0){
					linkToDevice1 = cp.getLinkToDevice1();
					if(linkToDevice1!=0)
					linkToDevice1SerialNo = BusinessUtility.getDeviceByDeviceID(linkToDevice1).getSerialNo();
				}
				if (cp.getLinkToDevice2() != 0){
					linkToDevice2 = cp.getLinkToDevice2();
					if(linkToDevice2!=0)
					linkToDevice2SerialNo = BusinessUtility.getDeviceByDeviceID(linkToDevice2).getSerialNo();
				}
			}
			//得到设备序列号
			
			//设备配对/解配对 2008-1-28
			Collection deviceIDList=new ArrayList();
			deviceIDList.addAll(BusinessUtility.getDeviceListBySaID(saID));
			LogUtility.log(clazz, LogLevel.INFO, "■■matchSCtoSTB■■deviceIDList■■"+deviceIDList);
			CustomerProductService customerProductService = new CustomerProductService(context);
			if(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_M)){
				if(deviceIDList.size() >= 2) //必须有两个或两个以上设备，才能配对
					customerProductService.deviceMatch(deviceIDList);
				else
					throw new ServiceException("配对设备不存在！");	
			}else if(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_D)){
				if(deviceIDList.size() >= 2) //必须有两个或两个以上设备，才能解配对
					customerProductService.unchainDeviceMatch(deviceIDList);
				else
					throw new ServiceException("配对设备不存在！");
			}
			
			/**
			if (linkToDevice1 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				if(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_M)){
					TerminalDevice terminalDevice=terminalDeviceHome.findByPrimaryKey(
							new Integer(linkToDevice1));
					terminalDevice.setMatchFlag("Y");
				}else if(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_D)){
					TerminalDevice terminalDevice=terminalDeviceHome.findByPrimaryKey(
							new Integer(linkToDevice1));
					terminalDevice.setMatchFlag("N");
				}
					linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice1)).getSerialNo();
			}
			if (linkToDevice2 != 0) {
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				if(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_M)){
					TerminalDevice terminalDevice=terminalDeviceHome.findByPrimaryKey(
							new Integer(linkToDevice2));
					terminalDevice.setMatchFlag("Y");
				}else if(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_D)){
					TerminalDevice terminalDevice=terminalDeviceHome.findByPrimaryKey(
							new Integer(linkToDevice2));
					terminalDevice.setMatchFlag("N");
				}
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(
						new Integer(linkToDevice2)).getSerialNo();
			}
			**/
			
			//获得业务账户下智能卡的产品号和客户产品号
			int psid = 0;
			int productid = 0;
			try {
				Map rs = BusinessUtility.getDeviceInfoBySAID(saID, "SC");
				for(Iterator it=rs.entrySet().iterator();it.hasNext();) {
					Entry en=(Entry) it.next();
					psid =((Integer)en.getKey()).intValue();
					productid = ((Integer)en.getValue()).intValue();
				}
			} catch (Exception sqlex) {
				LogUtility.log(clazz, LogLevel.WARN, sqlex);
				throw new ServiceException("查找智能卡的产品号和客户产品号失败！");
			}
			logDesc="业务帐户ID："+ saID+",";
			if (CommonConstDefinition.MATCH_SC_TO_STB_M.equals(type)) {
				//创建机卡配对系统事件
				log = "机卡配对";
				SystemEventRecorder.addEvent4ServiceAccount(
						SystemEventRecorder.SE_CA_MATCH, customerid, accountid,
						saID, productid, psid, linkToDevice1,
						linkToDevice1SerialNo, linkToDevice2,
						linkToDevice2SerialNo, getOperatorID(), content,
						SystemEventRecorder.SE_STATUS_CREATE);
			} else if (CommonConstDefinition.MATCH_SC_TO_STB_D.equals(type)) {
				//创建机卡解配对系统事件
				log = "机卡解配对";
				SystemEventRecorder.addEvent4ServiceAccount(
						SystemEventRecorder.SE_CA_UNMATCH, customerid,
						accountid, saID, productid, psid, linkToDevice1,
						linkToDevice1SerialNo, linkToDevice2,
						linkToDevice2SerialNo, getOperatorID(), content,
						SystemEventRecorder.SE_STATUS_CREATE);
			} else if (CommonConstDefinition.MATCH_SE_CA_CLEARPWD.equals(type)) {
				//创建消除密码系统事件
				log = "消除密码";
				SystemEventRecorder.addEvent4ServiceAccount(
						SystemEventRecorder.SE_CA_CLEARPWD, customerid,
						accountid, saID, productid, psid, linkToDevice1,
						linkToDevice1SerialNo, linkToDevice2,
						linkToDevice2SerialNo, getOperatorID(), content,
						SystemEventRecorder.SE_STATUS_CREATE);
			} else if (CommonConstDefinition.MATCH_SE_CA_SENDMAIL.equals(type)) {
				//创建发送消息系统事件
				log = "发送消息";
				SystemEventRecorder.addEvent4ServiceAccount(
						SystemEventRecorder.SE_CA_SENDMAIL, customerid,
						accountid, saID, productid, psid, linkToDevice1,
						linkToDevice1SerialNo, linkToDevice2,
						linkToDevice2SerialNo, getOperatorID(), content,
						SystemEventRecorder.SE_STATUS_CREATE);
			}
			if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
			}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice1SerialNo+",";
			}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="涉及的设备序列号:"+"(1)"+linkToDevice2SerialNo+",";
			}
			if(content!=null&&!content.equals("")){
				logDesc+=",发送的消息:"+content;
			}
			//创建系统日志记录
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), customerid,
					SystemLogRecorder.LOGMODULE_CUSTSERV, log, log+","+logDesc, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户定位出错！");
			throw new ServiceException("业务帐户定位出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "业务帐户查找出错！");
			throw new ServiceException("业务帐户查找出错！");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建业务帐户系统事件出错！");
			throw new ServiceException("创建业务帐户系统事件出错！");
		}
	}

	/**
	 * 修改业务帐户信息
	 * @param dto: 业务帐户信息
	 * @throws ServiceException
	 */
	public void update(ServiceAccountDTO dto) throws ServiceException {
		if (dto == null || dto.getServiceAccountID() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "业务帐户为空，无法更新!");
			throw new ServiceException("业务帐户为空，无法更新帐户信息");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(dto
					.getServiceAccountID()));
			if (sa.ejbUpdate(dto) == -1) {
				LogUtility.log(clazz, LogLevel.WARN,
						"更新业务帐户出错，原因：sa.ejbUpdate(dto)==-1");
				throw new ServiceException("更新业务帐户出错！");
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "业务帐户定位错误!");
			throw new ServiceException("业务帐户定位错误，无法更新帐户信息");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.WARN, "业务帐户查找出错，无法更新!");
			throw new ServiceException("业务帐户查找出错，无法更新帐户信息");
		}
	}

	/**
	 * 修改业务帐户的状态（包括修改该业务帐户下产品的状态）
	 * @param customerID
	 * @param actionType
	 * @throws ServiceException
	 */
	public void updateSAStatus(Collection saIDList, int actionType)
			throws ServiceException {
		if (saIDList == null || saIDList.size() == 0 || actionType == 0)
			throw new ServiceException("修改客户业务帐户状态参数错误!");

		if (!(EJBEvent.MOVETODIFFERENTPLACE == actionType
				|| EJBEvent.TRANSFERTODIFFERENTPLACE == actionType || EJBEvent.REGISTER_INSTALLATION_INFO == actionType))
			return;

		ServiceAccountHome saHome = null;
		ServiceAccount sa = null;
		CustomerProductHome cpHome = null;
		CustomerProduct cp = null;

		//Collection saList=BusinessUtility.getCurrentServiceAccountByCustomerID(customerID,null);
		Iterator itSAList = saIDList.iterator();
		try {
			saHome = HomeLocater.getServiceAccountHome();
			cpHome = HomeLocater.getCustomerProductHome();

			while (itSAList.hasNext()) {
				Integer saID = (Integer) itSAList.next();
				Collection cpList = null;

				sa = saHome.findByPrimaryKey(saID);
				//如果是迁移、异地过户 ，则把正常的业务帐户状态修改为初始,并把该业务帐户下的正常产品修改为初始
				if (EJBEvent.MOVETODIFFERENTPLACE == actionType
						|| EJBEvent.TRANSFERTODIFFERENTPLACE == actionType) {
					if (CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL
							.equals(sa.getStatus())) {
						sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT);
						sa.setDtLastmod(TimestampUtility.getCurrentTimestamp());

						cpList = cpHome.findByServiceAccountID(sa
								.getServiceAccountID().intValue());
						//修改产品状态
						Iterator itCPList = cpList.iterator();
						while (itCPList.hasNext()) {
							cp = (CustomerProduct) itCPList.next();

							if (CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL.equals(cp.getStatus())){
								cp.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_INIT);
								cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
							}
							cp.setActivatetime(null);
						}
					}
				}
				//如果是录入安装信息，则把初始的改为正常
				else if (EJBEvent.REGISTER_INSTALLATION_INFO == actionType) {
					if (CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT
							.equals(sa.getStatus())) {
						sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
						sa.setDtLastmod(TimestampUtility.getCurrentTimestamp());
												
						cpList = cpHome.findByServiceAccountID(sa
								.getServiceAccountID().intValue());
						//修改产品状态
						Iterator itCPList = cpList.iterator();
						while (itCPList.hasNext()) {
							cp = (CustomerProduct) itCPList.next();
							if (CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_INIT.equals(cp.getStatus())){
								cp.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
								cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
								cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
							}
						}

					}
				}
			}
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.WARN, "修改客户业务帐户状态时,无法定位业务帐户!");
			throw new ServiceException("修改客户业务帐户状态时,无法定位业务帐户！");
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.WARN, "修改客户业务帐户状态时,无法查找到业务帐户!");
			throw new ServiceException("修改客户业务帐户状态时,无法查找到业务帐户！");
		}
	}

	/**
	 *
	 * @param sc
	 */
	public void setObjectRelation(ServiceContext sc) {
		int customerid = 0;
		Customer customer = (Customer) context.get(Service.CUSTOMER);
		if (customer == null) {
			Integer custID = (Integer) context.get(Service.CUSTOMER_ID);
			customerid = custID.intValue();
		} else
			customerid = customer.getCustomerID().intValue();
		ServiceAccount sa = (ServiceAccount) context
				.get(Service.SERVICE_ACCOUNT_EJB);
		sa.setCustomerID(customerid);

	}

	/**
	 * 检查customerid、accountid是否存在
	 * @throws ServiceException
	 */
	private void checkServiceContext() throws ServiceException {
		if (customerid == 0) {
			Customer customer = (Customer) context.get(Service.CUSTOMER);
			if (customer == null) {
				Integer custID = (Integer) context.get(Service.CUSTOMER_ID);
				if (custID == null)
					throw new ServiceException("在上下文中没有客户对象");
				customerid = custID.intValue();
			} else
				customerid = customer.getCustomerID().intValue();
		}
		/*if (accountid == 0) {
		 Account account = (Account) context.get(Service.ACCOUNT);
		 if (account == null) {
		 Integer acctID = (Integer) context.get(Service.ACCOUNT_ID);
		 if (acctID == null)
		 throw new ServiceException("在上下文中没有帐户对象");
		 accountid = acctID.intValue();
		 } else
		 accountid = account.getAccountID().intValue();
		 }*/
	}

	/**
	 * 通过检查context得到csiid
	 * @return
	 */
	private int getCSIID() {
		int csiid = 0;
		if (context.get(Service.CSI_ID) != null) {
			csiid = ((Integer) context.get(Service.CSI_ID)).intValue();
		} else if (context.get(Service.CSI) != null) {
			CustServiceInteraction csiEJB = (CustServiceInteraction) context
					.get(Service.CSI);
			csiid = csiEJB.getId().intValue();
		}
		return csiid;
	}

	/**
	 *属于内部帮助方法
	 * @param dtoInTotalList：
	 * @param dtoInSAList
	 * @param map
	 * @throws ServiceException
	 */
	private void fillArrayMap(CustomerProductDTO dtoInTotalList,
			CustomerProductDTO dtoInSAList, Map map) throws ServiceException {
		Collection list = (ArrayList) map.get(dtoInSAList);
		if (list == null)
			throw new ServiceException("找不到能创建业务帐户的产品");
		list.add(dtoInTotalList);
		map.put(dtoInSAList, list);
	}

	/**
	 * 设备流转
	 * @param deviceID
	 * @param customerID
	 * @param opID
	 * @throws ServiceException
	 * @throws HomeFactoryException
	 * @throws FinderException
	 * @throws CreateException
	 */
	private void deviceTransitionForTranfer(Collection deviceIDList,
			int toCustomerID) throws ServiceException {

		String strFromType = "";
		int intFromID = 0;

		try {
			LogUtility.log(clazz, LogLevel.DEBUG, "进入设备流转");

			//创建设备流转记录
			LogUtility.log(clazz, LogLevel.DEBUG, "创建设备流转记录");
			DeviceTransitionDTO deviceTransitionDto = new DeviceTransitionDTO();
			deviceTransitionDto.setCreateTime(new Timestamp(System
					.currentTimeMillis()));

			/***************************************************
			 * 设计文档并没有说明此处的动作，暂时这样，待以后修改
			 ******************************************************/
			deviceTransitionDto
					.setAction(CommonConstDefinition.SERVICEACCOUNTTRANSFER);

			deviceTransitionDto.setDeviceNumber(deviceIDList.size());

			deviceTransitionDto
					.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
			deviceTransitionDto.setToID(toCustomerID);
			deviceTransitionDto
					.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			deviceTransitionDto.setOperatorID(getOperatorID());
			DeviceTransitionHome deviceTransitionHome = HomeLocater
					.getDeviceTransitionHome();
			DeviceTransition deviceTransition = deviceTransitionHome
					.create(deviceTransitionDto);

			Iterator itDeviceID = deviceIDList.iterator();
			while (itDeviceID.hasNext()) {
				int deviceID = ((Integer) itDeviceID.next()).intValue();
				TerminalDeviceHome terminalDeviceHome = HomeLocater
						.getTerminalDeviceHome();
				TerminalDevice terminalDevice = terminalDeviceHome
						.findByPrimaryKey(new Integer(deviceID));
				//设备的状态不是已售
				if (!terminalDevice.getStatus().equals(
						CommonConstDefinition.DEVICE_STATUS_SOLD)){
					LogUtility.log(clazz, LogLevel.ERROR, "该产品状态不是处于已售状态，设备ID：" + deviceID);
					throw new ServiceException("设备状态异常,不能完成过户!");
				}
				
				String fromType = terminalDevice.getAddressType();
				int fromID = terminalDevice.getAddressID();
				if (!"".equals(fromType))
					strFromType = fromType;
				if (fromID != 0)
					intFromID = fromID;

				//设置新的地址类型和ID
				terminalDevice.setAddressType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
				terminalDevice.setAddressID(toCustomerID);

				//创建设备流转明细
				LogUtility.log(clazz, LogLevel.DEBUG, "创建设备流转明细");
				DeviceTransitionDetailDTO deviceTransitonDto = new DeviceTransitionDetailDTO();
				deviceTransitonDto.setBatchID(deviceTransition.getBatchID()
						.intValue());
				deviceTransitonDto.setDeviceID(deviceID);
				deviceTransitonDto
						.setFromDeviceStatus(CommonConstDefinition.DEVICE_STATUS_SOLD);
				deviceTransitonDto.setFromID(fromID);
				deviceTransitonDto.setFromType(fromType);
				deviceTransitonDto.setSerialNo(terminalDevice.getSerialNo());
				deviceTransitonDto
						.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
				deviceTransitonDto
						.setToDeviceStatus(CommonConstDefinition.DEVICE_STATUS_SOLD);
				deviceTransitonDto.setToID(toCustomerID);
				deviceTransitonDto
						.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
				DeviceTransitionDetailHome deviceTransitionDetailHome = HomeLocater
						.getDeviceTransitionDetailHome();
				deviceTransitionDetailHome.create(deviceTransitonDto);
			}
			deviceTransition.setFromType(strFromType);
			deviceTransition.setFromID(intFromID);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "设备定位出错！");
			throw new ServiceException("设备定位出错！");
		} catch (FinderException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "设备查找出错！");
			throw new ServiceException("设备查找出错！");
		} catch (CreateException e2) {
			LogUtility.log(clazz, LogLevel.ERROR, "设备流转创建出错！");
			throw new ServiceException("设备流转创建出错！");
		}

		LogUtility.log(clazz, LogLevel.DEBUG, "结束设备流转");
	}

	public void updatePhoneNo(int serviceAccountID,int itemID, String oldPhoneNo,
			String newPhoneNo) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "号码更新开始");
//		LogUtility.log(clazz, LogLevel.DEBUG, "itemID:"+itemID);
//		LogUtility.log(clazz, LogLevel.DEBUG, "serviceAccountID:"+serviceAccountID);
//		LogUtility.log(clazz, LogLevel.DEBUG, "oldPhoneNo:"+oldPhoneNo);
//		LogUtility.log(clazz, LogLevel.DEBUG, "newPhoneNo:"+newPhoneNo);
//		ResourcePhoneNoDTO newPhoneNoDto = BusinessUtility
//				.getDTOByPhoneNo(newPhoneNo);
//		ResourcePhoneNoDTO oldPhoneNoDto = BusinessUtility
//				.getDTOByPhoneNo(oldPhoneNo);
//		LogUtility.log(clazz, LogLevel.DEBUG, "newPhoneNoDto:"+newPhoneNoDto);
//		LogUtility.log(clazz, LogLevel.DEBUG, "oldPhoneNoDto:"+oldPhoneNoDto);

		checkPhoneNo(newPhoneNo, itemID);
		usePhoneNo(newPhoneNo, itemID,serviceAccountID);
		releasePhoneNo(oldPhoneNo,serviceAccountID);

		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(
					serviceAccountID));
			sa.setServiceCode(newPhoneNo);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "服务出错！");
			throw new ServiceException("服务出错！");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "服务帐号查找出错！");
			throw new ServiceException("服务帐号查找出错！");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "号码更新结束");
	}

    /**
     * 检查phoneNo是否在电话表中存在，状态是否为可用
     * @throws ServiceException
     */
    private boolean checkPhoneNo(String phoneNo,int itemID) throws ServiceException{
    	
		ResourcePhoneNoHome phoneHome;
		ResourcePhoneNo phone;
		try {
			phoneHome = HomeLocater.getResourcePhoneNoHome();
			phone = phoneHome.findByPrimaryKey(new Integer(itemID));
			if(!(phoneNo.equals(phone.getPhoneNo()) && phone.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_AVAILABLE)))
				throw new ServiceException("电话号码 "+phoneNo+" 不正确！");
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源定位错误!");
        	throw new ServiceException("电话号码资源定位错误，无法更新电话号码资源");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源查找出错，无法更新!");
        	throw new ServiceException("电话号码资源查找出错，无法更新电话号码资源");
        }
    	return true;
    }
    
    /**
     * 检查phoneNo是否在电话表中存在，状态是否为可用 预约开户专用
     * @throws ServiceException
     */
    private boolean checkPhoneNoForBookOpen(String phoneNo,int itemID,ArrayList serviceCodeList) throws ServiceException{
    	
    	
    	if(serviceCodeList==null)serviceCodeList=new ArrayList();
		ResourcePhoneNoHome phoneHome;
		ResourcePhoneNo phone;
		try {
			phoneHome = HomeLocater.getResourcePhoneNoHome();
			phone = phoneHome.findByPrimaryKey(new Integer(itemID));
			if (serviceCodeList.contains(phoneNo)) {
				if (!(phoneNo.equals(phone.getPhoneNo()) && phone.getStatus()
						.equals(CommonConstDefinition.PHONENO_STATUS_LOCKED)))
					throw new ServiceException("电话号码不正确！");
			} else {
				if (!(phoneNo.equals(phone.getPhoneNo()) && phone.getStatus()
						.equals(CommonConstDefinition.PHONENO_STATUS_AVAILABLE)))
					throw new ServiceException("电话号码不正确！");
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "电话号码资源定位错误!");
			throw new ServiceException("电话号码资源定位错误，无法更新电话号码资源");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.WARN, "电话号码资源查找出错，无法更新!");
			throw new ServiceException("电话号码资源查找出错，无法更新电话号码资源");
		}
		return true;
	}
    /**
	 * 锁定电话号码资源，建立使用日志
	 * 
	 * @throws ServiceException
	 */
    public static boolean lockPhoneNo(String phoneNo,int customerid,Integer operatorID,int saID) throws ServiceException{
    	
    	ResourcePhoneNoDTO dto = BusinessUtility.getDTOByPhoneNo(phoneNo);
		ResourcePhoneNoHome phoneHome;
		ResourcePhoneNo phone;
		try {
			phoneHome = HomeLocater.getResourcePhoneNoHome();
			phone = phoneHome.findByPrimaryKey(new Integer(dto.getItemID()));

			if(phoneNo.equals(phone.getPhoneNo()) && phone.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_AVAILABLE)){
				phone.setStatus(CommonConstDefinition.PHONENO_STATUS_LOCKED);
				phone.setStatusTime(TimestampUtility.getCurrentDate());
				
				PhoneNoUseLogDTO phoneUseDto = new PhoneNoUseLogDTO();
				phoneUseDto.setAction(CommonConstDefinition.PHONENOUSELOG_ACTION_USED);
				phoneUseDto.setAreaCode(phone.getAreaCode());
				phoneUseDto.setCountryCode(phone.getCountryCode());
				phoneUseDto.setCreateTime(TimestampUtility.getCurrentDate());
				phoneUseDto.setCustID(customerid);
				phoneUseDto.setDtCreate(TimestampUtility.getCurrentDate());
				phoneUseDto.setDtLastmod(TimestampUtility.getCurrentDate());
				phoneUseDto.setOpID(operatorID.intValue());
				
				OperatorHome homeOpe = HomeLocater.getOperatorHome();
				Operator operator = homeOpe.findByPrimaryKey(operatorID);
				phoneUseDto.setOrgID(operator.getOrgID());
				phoneUseDto.setPhoneNo(phoneNo);
				phoneUseDto.setResourceItemID(dto.getItemID());
				phoneUseDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
											
				phoneUseDto.setUserID(saID);
				phoneUseDto.setDescription("电话号码被锁定");
				phoneUseDto.setNetworkID("");
				phoneUseDto.setPsID(0);
				
				BusinessUtility.addPhoneUseLog(phoneUseDto);

			}
			else
				throw new ServiceException("电话号码不正确！");
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源定位错误!");
        	throw new ServiceException("电话号码资源定位错误!");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源查找出错，无法更新!");
        	throw new ServiceException("电话号码资源查找出错，无法更新电话号码资源!");
        }
    	return true;
    }
    /**
     * 占用电话号码资源，建立使用日志 预约开户专用
     * @throws ServiceException
     */
    private boolean usePhoneNoForBookOpen(String phoneNo,int itemID,int saID,ArrayList serviceCodeList) throws ServiceException{

    	if(serviceCodeList == null) serviceCodeList = new ArrayList();
		ResourcePhoneNoHome phoneHome;
		ResourcePhoneNo phone;
		try {
			phoneHome = HomeLocater.getResourcePhoneNoHome();
			phone = phoneHome.findByPrimaryKey(new Integer(itemID));
			//预约开户且比较的结果是号码相同 则原状态是PHONENO_STATUS_LOCKED转为PHONENO_STATUS_USED
			//预约开户且比较的结果是号码不同 则原状态是PHONENO_STATUS_AVAILABLE转为PHONENO_STATUS_USED 
			//			同时要将原来的号码由PHONENO_STATUS_LOCKED转为PHONENO_STATUS_AVAILABLE
			
			if(!phoneNo.equals(phone.getPhoneNo()))
				throw new ServiceException("电话号码不正确！");


			//号码没有变更
			if(serviceCodeList.contains(phoneNo))
			{
				if(!phone.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_LOCKED))
					throw new ServiceException("电话号码不正确！");
			}
			//原来有电话号码 现在改了 /或原来没有ip电话业务 现在有
			//需要将原来的号码解锁,如果有多个号码 这里的处理要求前台传递的老号码和新号码按照对应的序列存放
			else
			{
				System.out.println("__________________serviceCodeList="+serviceCodeList);
				System.out.println("__________________phoneNo="+phoneNo);
				if(!phone.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_AVAILABLE))
					throw new ServiceException("电话号码不正确！");
				if(serviceCodeList.size()>0 && !"".equals((String)serviceCodeList.get(0)))
				{
					String oldPhoneNo = (String)serviceCodeList.get(0);
					ResourcePhoneNoDTO oldDto = BusinessUtility.getDTOByPhoneNo(oldPhoneNo);
					if(!oldDto.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_LOCKED))
						throw new ServiceException("电话号码状态不正确！");
					releasePhoneNo(oldPhoneNo,0);
				}
			}
			phone.setStatus(CommonConstDefinition.PHONENO_STATUS_USED);
			phone.setStatusTime(TimestampUtility.getCurrentDate());
			PhoneNoUseLogDTO phoneUseDto = new PhoneNoUseLogDTO();
			phoneUseDto.setAction(CommonConstDefinition.PHONENOUSELOG_ACTION_USED);
			phoneUseDto.setAreaCode(phone.getAreaCode());
			phoneUseDto.setCountryCode(phone.getCountryCode());
			phoneUseDto.setCreateTime(TimestampUtility.getCurrentDate());
			phoneUseDto.setCustID(customerid);
			phoneUseDto.setDtCreate(TimestampUtility.getCurrentDate());
			phoneUseDto.setDtLastmod(TimestampUtility.getCurrentDate());
			phoneUseDto.setOpID(PublicService.getCurrentOperatorID(context));
			
			OperatorHome homeOpe = HomeLocater.getOperatorHome();
			Operator operator = homeOpe.findByPrimaryKey(new Integer(PublicService.getCurrentOperatorID(context)));
			phoneUseDto.setOrgID(operator.getOrgID());
			phoneUseDto.setPhoneNo(phoneNo);
			phoneUseDto.setResourceItemID(itemID);
			phoneUseDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
										
			phoneUseDto.setUserID(saID);
			phoneUseDto.setDescription("电话号码被使用");
			phoneUseDto.setNetworkID("");
			phoneUseDto.setPsID(0);
			
			BusinessUtility.addPhoneUseLog(phoneUseDto);
				
			if(serviceCodeList.size()>0)serviceCodeList.remove(0);
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源定位错误!");
        	throw new ServiceException("电话号码资源定位错误，无法更新电话号码资源");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源查找出错，无法更新!");
        	throw new ServiceException("电话号码资源查找出错，无法更新电话号码资源");
        }
    	return true;
    }
    /**
     * 占用电话号码资源，建立使用日志
     * @throws ServiceException
     */
    private boolean usePhoneNo(String phoneNo,int itemID,int saID) throws ServiceException{

		ResourcePhoneNoHome phoneHome;
		ResourcePhoneNo phone;
		try {
			phoneHome = HomeLocater.getResourcePhoneNoHome();
			phone = phoneHome.findByPrimaryKey(new Integer(itemID));
		
			if(phoneNo.equals(phone.getPhoneNo()) && phone.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_AVAILABLE)){
				phone.setStatus(CommonConstDefinition.PHONENO_STATUS_USED);
				phone.setStatusTime(TimestampUtility.getCurrentDate());
				
				PhoneNoUseLogDTO phoneUseDto = new PhoneNoUseLogDTO();
				phoneUseDto.setAction(CommonConstDefinition.PHONENOUSELOG_ACTION_USED);
				phoneUseDto.setAreaCode(phone.getAreaCode());
				phoneUseDto.setCountryCode(phone.getCountryCode());
				phoneUseDto.setCreateTime(TimestampUtility.getCurrentDate());
				phoneUseDto.setCustID(customerid);
				phoneUseDto.setDtCreate(TimestampUtility.getCurrentDate());
				phoneUseDto.setDtLastmod(TimestampUtility.getCurrentDate());
				phoneUseDto.setOpID(PublicService.getCurrentOperatorID(context));
				
				OperatorHome homeOpe = HomeLocater.getOperatorHome();
				Operator operator = homeOpe.findByPrimaryKey(new Integer(PublicService.getCurrentOperatorID(context)));
				phoneUseDto.setOrgID(operator.getOrgID());
				phoneUseDto.setPhoneNo(phoneNo);
				phoneUseDto.setResourceItemID(itemID);
				phoneUseDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
											
				phoneUseDto.setUserID(saID);
				phoneUseDto.setDescription("电话号码被使用");
				phoneUseDto.setNetworkID("");
				phoneUseDto.setPsID(0);
				
				BusinessUtility.addPhoneUseLog(phoneUseDto);

			}
			else
				throw new ServiceException("电话号码不正确！");
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源定位错误!");
        	throw new ServiceException("电话号码资源定位错误，无法更新电话号码资源");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源查找出错，无法更新!");
        	throw new ServiceException("电话号码资源查找出错，无法更新电话号码资源");
        }
    	return true;
    }
    /**
     *回收电话号码资源，建立使用日志
     * @throws ServiceException
     */
    private boolean releasePhoneNo(String phoneNo, int saID) throws ServiceException{

    	ResourcePhoneNoDTO dto = BusinessUtility.getDTOByPhoneNo(phoneNo);
    	ResourcePhoneNoHome phoneHome;
		ResourcePhoneNo phone;
		try {
			phoneHome = HomeLocater.getResourcePhoneNoHome();
			phone = phoneHome.findByPrimaryKey(new Integer(dto.getItemID()));

			if(CommonConstDefinition.PHONENO_STATUS_USED.equals(phone.getStatus())||
			   CommonConstDefinition.PHONENO_STATUS_LOCKED.equals(phone.getStatus())){
				phone.setStatus(CommonConstDefinition.PHONENO_STATUS_AVAILABLE);
				phone.setStatusTime(TimestampUtility.getCurrentDate());
				phone.setDtLastmod(TimestampUtility.getCurrentDate());
				
				PhoneNoUseLogDTO phoneUseDto = new PhoneNoUseLogDTO();
				phoneUseDto.setAction(CommonConstDefinition.PHONENOUSELOG_ACTION_REUSE);
				phoneUseDto.setAreaCode(phone.getAreaCode());
				phoneUseDto.setCountryCode(phone.getCountryCode());
				phoneUseDto.setCreateTime(TimestampUtility.getCurrentDate());
				phoneUseDto.setCustID(customerid);
				phoneUseDto.setDtCreate(TimestampUtility.getCurrentDate());
				phoneUseDto.setDtLastmod(TimestampUtility.getCurrentDate());
				phoneUseDto.setOpID(PublicService.getCurrentOperatorID(context));
				phoneUseDto.setPhoneNo(phone.getPhoneNo());
				
				OperatorHome homeOpe = HomeLocater.getOperatorHome();
				Operator operator = homeOpe.findByPrimaryKey(new Integer(PublicService.getCurrentOperatorID(context)));
				phoneUseDto.setOrgID(operator.getOrgID());
				
				phoneUseDto.setResourceItemID(dto.getItemID());
				phoneUseDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
				phoneUseDto.setUserID(saID);
				phoneUseDto.setDescription("释放电话号码");
				phoneUseDto.setNetworkID("");
				phoneUseDto.setPsID(0);
				
				BusinessUtility.addPhoneUseLog(phoneUseDto);

			}
			else
				throw new ServiceException("电话号码不正确！");
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源定位错误!");
        	throw new ServiceException("电话号码资源定位错误，无法更新电话号码资源");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"电话号码资源查找出错，无法更新!");
        	throw new ServiceException("电话号码资源查找出错，无法更新电话号码资源");
        }
    	return true;
    }   
    
    /**
     * 产品分组函数，根据传的产品ID列表（Integer对象），返回以能创建业务帐户的产品ID为KEY，具有依赖关系的产品的ID（Integer）对象的
     * Collection为value的Map对象，注意：value不包括key
     * @param productIDList
     * @return
     * @throws ServiceException 
     */
    public Map productSplitToSA(Collection productIDList) throws ServiceException{
    	LogUtility.log(clazz, LogLevel.DEBUG, "productSplitToSA（）产品按业务帐户分组！");
    	LogUtility.log(clazz, LogLevel.DEBUG, "传入的参数（productIDList）：" +productIDList);
    	if(productDependency==null){
        	productDependency=BusinessUtility.getAllProductDepentDefineList();
    	}
    	
    	if(productIDList==null || productIDList.isEmpty())
    		return null;
    	
    	Map saProductIDsMap=new HashMap();
    	
		Collection productIDForSAIDList = (ArrayList) ((ArrayList) productIDList).clone();
		//能创建业务帐户的product
		Collection saProductIDList = BusinessUtility.getSAProductIDListFromPoductIDList(productIDForSAIDList);
		LogUtility.log(clazz, LogLevel.DEBUG, "能创建业务帐户的产品为：" + saProductIDList);
		if(saProductIDList==null || saProductIDList.isEmpty())
			return null;
		
		//由来操作的产品ID列表
		ArrayList opProductIDList = (ArrayList) ((ArrayList) productIDList).clone();
		opProductIDList.removeAll(saProductIDList);
		
		Iterator itSA = saProductIDList.iterator();
		while (itSA.hasNext()) {
			Integer productIDOfSA = (Integer) itSA.next();
			Collection productIDToOneSAList=new ArrayList();
			
			Iterator itProductIDToOneSA=productIDList.iterator();
			while(itProductIDToOneSA.hasNext()){
				Integer productIDToOneSA = (Integer) itProductIDToOneSA.next();
				if(!saProductIDList.contains(productIDToOneSA)){
					boolean isDependency = prodcutsIsDependency(productIDToOneSA.intValue(), productIDOfSA.intValue());
					if(isDependency){
						opProductIDList.remove(productIDToOneSA);
						productIDToOneSAList.add(productIDToOneSA);
					}
				}
			}
			saProductIDsMap.put(productIDOfSA, productIDToOneSAList);
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "分组后的剩下的产品：" + opProductIDList);
		if (opProductIDList.size() > 0) {
			ArrayList li=new ArrayList();
			li.add(opProductIDList);
			String err="购买不完整，产品:"+BusinessUtility.getProductDesByProductID(li)+",没有有效的依赖.";
			LogUtility.log(clazz, LogLevel.ERROR, err);
			throw new ServiceException(err);
		}
		return saProductIDsMap;
    }
    
    public int calculatePointForIPPVCharge(CAWalletEJBEvent inEvent)throws ServiceException{
		
		if(inEvent.getCawDto() == null || inEvent.getCawcrDto() == null)
			throw new ServiceException("充值信息不完整，无法计算点数！");
		LogUtility.log(clazz, LogLevel.DEBUG, "业务帐户ID：" + inEvent.getCawDto().getServiceAccountId()+" 进行小钱包充值计算点数");
		
		int point = BusinessUtility.calculatePointForIPPV(inEvent.getCawDto().getCaWalletCode(),inEvent.getCawcrDto().getValue());
    	
		return point;
    }

	/**
	 * @param inEvent
	 */
	public int createCAWallet(CAWalletEJBEvent inEvent) throws ServiceException, CreateException {
		if(inEvent.getCawDto() == null || inEvent.getCawcrDto() == null)
			throw new ServiceException("充值信息不完整，无法创建小钱包！");
		int walletID = 0;
		CAWalletDTO cawDto = inEvent.getCawDto();
		CAWalletChargeRecordDTO cawcrDto = inEvent.getCawcrDto();
		
		CAWalletHome cawHome;
		CAWalletChargeRecordHome cawcrHome;
		
		try{
			cawHome = HomeLocater.getCAWalletHome();
			cawDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			CAWallet wallet = cawHome.create(cawDto);
			walletID = wallet.getWalletId().intValue();
			
			/*cawcrDto.setWalletId(walletID);
			cawcrDto.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
			cawcrDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			cawcrHome = HomeLocater.getCAWalletChargeRecordHome();
			cawcrHome.create(cawcrDto);*/
			
			}catch(HomeFactoryException e){
				LogUtility.log(clazz,LogLevel.WARN,"小钱包定位错误!");
				throw new ServiceException("小钱包定位错误，无法更新小钱包");
			}	
			return walletID;
	}

	/**
	 * @param inEvent
	 */
	public int chargeCAWallet(CAWalletEJBEvent inEvent) throws ServiceException, CreateException, FinderException {
		if(inEvent.getCawcrDto() == null)
			throw new ServiceException("充值信息不完整，无法充值小钱包！");
		int seqno = 0;
		CAWalletChargeRecordDTO cawcrDto = inEvent.getCawcrDto();
		CAWalletChargeRecordHome cawcrHome;
		CAWalletHome cawHome;
		
		try{
			cawcrHome = HomeLocater.getCAWalletChargeRecordHome();
			cawcrDto.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
			cawcrDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			CAWalletChargeRecord cawcRecord = cawcrHome.create(cawcrDto);
			seqno = cawcRecord.getSeqNo().intValue();
			
			cawHome = HomeLocater.getCAWalletHome();
			CAWallet caWallet = cawHome.findByPrimaryKey(new Integer(cawcrDto.getWalletId()));
			int preTotalPoint = caWallet.getTotalPoint();
			caWallet.setTotalPoint(preTotalPoint + cawcrDto.getPoint());
			caWallet.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"小钱包定位错误!");
			throw new ServiceException("小钱包定位错误，无法更新小钱包");
		}catch(FinderException e1){
			LogUtility.log(clazz,LogLevel.WARN,"小钱包定位错误！");
			throw new ServiceException("小钱包查找错误！");
		}
		return seqno;
	}

	/**
	 * @param walletID
	 * @param csiid
	 */
	public void updateCAWalletChargeRecord(int seqno, int csiid) throws ServiceException {
		try{
			
			CAWalletChargeRecordHome home = HomeLocater.getCAWalletChargeRecordHome();
			CAWalletChargeRecord record = home.findByPrimaryKey(new Integer(seqno));
			record.setCsiId(csiid);
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"小钱包记录定位错误!");
			throw new ServiceException("小钱包记录定位错误，无法更新小钱包");
		}catch(FinderException e1){
			LogUtility.log(clazz,LogLevel.WARN,"小钱包记录定位错误！");
			throw new ServiceException("小钱包记录查找错误！");
		}
		
	}
}
