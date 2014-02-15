/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Account;
import com.dtv.oss.domain.AccountHome;
import com.dtv.oss.domain.Address;
import com.dtv.oss.domain.AddressHome;
import com.dtv.oss.domain.CallBackInfo;
import com.dtv.oss.domain.CallBackInfoHome;
import com.dtv.oss.domain.CsiCustProductInfo;
import com.dtv.oss.domain.CsiCustProductInfoHome;
import com.dtv.oss.domain.CsiProcessLogHome;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustServiceInteractionHome;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.domain.CustomerCampaignHome;
import com.dtv.oss.domain.CustomerHome;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.domain.GroupBargain;
import com.dtv.oss.domain.GroupBargainDetail;
import com.dtv.oss.domain.GroupBargainDetailHome;
import com.dtv.oss.domain.GroupBargainHome;
import com.dtv.oss.domain.NewCustAccountInfo;
import com.dtv.oss.domain.NewCustAccountInfoHome;
import com.dtv.oss.domain.NewCustomerInfo;
import com.dtv.oss.domain.NewCustomerInfoHome;
import com.dtv.oss.domain.NewCustomerMarketInfo;
import com.dtv.oss.domain.NewCustomerMarketInfoHome;
import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.domain.Product;
import com.dtv.oss.domain.ProductHome;
import com.dtv.oss.domain.ResourcePhoneNo;
import com.dtv.oss.domain.ResourcePhoneNoHome;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CallBackInfoDTO;
import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.CsiCustProductInfoDTO;
import com.dtv.oss.dto.CsiProcessLogDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.GroupBargainDetailDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.dto.NewCustomerMarketInfoDTO;
import com.dtv.oss.dto.PhoneNoUseLogDTO;
import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.dto.custom.CommonBusinessParamDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.BookEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CallBackInfoEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.ejbevent.csr.OpenAccountGeneralEJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.ejbevent.groupcustomer.GroupCustomerEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.util.TimestampUtility;
/**
 * @author Leon Liu
 * 
 * 处理与受理单相关的对象的创建、修改、删除；处理预约逻辑等等
 */
public class CSIService extends AbstractService {
    private final static Class clazz = CSIService.class;
	private ServiceContext serviceContext=null;
	public CSIService(ServiceContext serviceContext){
		this.serviceContext=serviceContext;
	}
	/**
	 * 新增产品时的创建受理单信息
	 * 
	 * @throws ServiceException
	 */
	public void createAddCustomerProductCsiInfo(CustomerProductEJBEvent event)throws ServiceException{
			// 取得客户购买的产品包信息
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();	
			}
			// 取得客户选择的优惠信息
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	// 取得新客户信息
	    	int  customerID = event.getCustomerID();    	
	    	// 取得账户信息
	    	int accountID=event.getAccountID();

	    	// 取得受理单信息
	    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
	    	csiDTO.setCustomerID(customerID);
	    	csiDTO.setAccountID(accountID);
	    	// setContactPersonInfo(csiDTO,customer.getName(),customer.getTelephone(),customer.getTelephoneMobile());
	    	
	    	// 创建受理单信息
			createCustServiceInteraction(csiDTO);
			// 创建受理单涉及的客户产品相关信息
			if(event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP)){
				CommonBusinessParamDTO paramDto =new CommonBusinessParamDTO();
				paramDto.setServiceaccountID(event.getSaID());
				createCsiCustProductInfo( csiPackageArray, null, csiCampaignArray, csiDTO,paramDto);
			}else
				createCsiCustProductInfo( csiPackageArray, null, csiCampaignArray, csiDTO);

	}
	/**
	 * 创建业务账户关联的信息
	 * 
	 * @throws ServiceException
	 */
	public void createServiceAccountCsiInfo(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			// 取得客户购买的产品包信息
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			// 取得客户选择的优惠信息
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	// 取得新客户信息
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	
	    	// 取得账户信息
	    	int accountID=event.getAccountID();
	    	// 客户地址信息
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());

	    	// 取得受理单信息
	    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
	    	csiDTO.setCustomerID(customerID);
	    	csiDTO.setAccountID(accountID);
	    	
	    	// 创建受理单信息
			createCustServiceInteraction(csiDTO);
			// 在新客户信息账户信息之后需要在受理单中设置客户和账户id
			CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
			// 创建受理单涉及的客户产品相关信息
			createCsiCustProductInfo( csiPackageArray, null, csiCampaignArray, csiDTO);
	    	// 是否创建工单
			if(isCreateJobcard(csiDTO)){
				CustomerDTO custoemrDto=new CustomerDTO();
				custoemrDto.setCustomerID(customer.getCustomerID().intValue());
				custoemrDto.setTelephone(customer.getTelephone());
				custoemrDto.setTelephoneMobile(customer.getTelephoneMobile());
				custoemrDto.setAddressID(customer.getAddressID());
				custoemrDto.setName(customer.getName());
				// 创建工单,调用工单service创建工单信息
				InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
			    int jobcardID=installationJobCardService.createJobCardForCustomerOperation(csiDTO,custoemrDto,custAddrDto,event.getNextOrgID());
				// 设置受理单关联的工单id
				csiEJB.setReferjobcardID(jobcardID);
			}
			
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中找不到相关信息");
			
		}
	}
	/**
	 * 重复购买门店增机时创建受理单方法,关键是csicustomerproductinfo的创建,影响到customerproduct创建,
	 * @param event
	 * @throws ServiceException
	 */
	public void createServiceAccountCsiInfoWithBatchBuy(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			// 取得客户购买的产品包信息
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			// 取得客户选择的优惠信息
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	// 取得新客户信息
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	
	    	// 取得账户信息
	    	int accountID=event.getAccountID();
	    	// 客户地址信息
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());

	    	// 取得受理单信息
	    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
	    	csiDTO.setCustomerID(customerID);
	    	csiDTO.setAccountID(accountID);
	    	
	    	// 创建受理单信息
			createCustServiceInteraction(csiDTO);
			// 在新客户信息账户信息之后需要在受理单中设置客户和账户id
			CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
			
			ArrayList buyProductList = event.getBuyProductList();
			batchCreateCsiCustProductInfo(buyProductList,csiDTO);
	    	// 是否创建工单
			if(isCreateJobcard(csiDTO)){
				CustomerDTO custoemrDto=new CustomerDTO();
				custoemrDto.setCustomerID(customer.getCustomerID().intValue());
				custoemrDto.setTelephone(customer.getTelephone());
				custoemrDto.setTelephoneMobile(customer.getTelephoneMobile());
				custoemrDto.setAddressID(customer.getAddressID());
				custoemrDto.setName(customer.getName());
				// 创建工单,调用工单service创建工单信息
				InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
			    int jobcardID=installationJobCardService.createJobCardForCustomerOperation(csiDTO,custoemrDto,custAddrDto,event.getNextOrgID());
				// 设置受理单关联的工单id
				csiEJB.setReferjobcardID(jobcardID);
			}
			
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中定位错误");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建业务账户的检查中找不到相关信息");
			
		}
	}
	private void batchCreateCsiCustProductInfo(ArrayList buyProductList,CustServiceInteractionDTO csiDTO) throws ServiceException{
		LogUtility.log(clazz, LogLevel.DEBUG, "创建csiCustProductInfo",buyProductList);
		
		
		Collection allCsiCustProductList=new ArrayList();
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

			LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"组当前产品包:",buyPackageList);
			for (int i = 1; i <= buyNum.intValue(); i++) {
				Integer buySheaf = new Integer(i);
				Map curDeviceMap = null;
				if(buyDeviceMap!=null)
				curDeviceMap = (Map) buyDeviceMap.get(buySheaf);
				allCsiCustProductList.addAll(createCsiCustProductInfo(
						buyPackageList, null, buyCampaignList, csiDTO, null,
						buyIndex.intValue(), buySheaf.intValue()));
			}
		}		
		LogUtility.log(clazz, LogLevel.DEBUG, "创建csiCustProductInfo完成.",allCsiCustProductList);
	}
	/**
	 * 重复购买适用
	 * @param csiPackageArray
	 * @param detailNo
	 * @param csiCampaignArray
	 * @param csiDTO
	 * @param businessParamDTO
	 * @param buyGroupNo
	 * @param sheafNo
	 * @return
	 * @throws ServiceException
	 */
	public Collection createCsiCustProductInfo(Collection csiPackageArray,
			String detailNo, Collection csiCampaignArray,
			CustServiceInteractionDTO csiDTO,
			CommonBusinessParamDTO businessParamDTO, int buyGroupNo, int sheafNo)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "....................buyGroupNo:"+buyGroupNo+"sheafNo:"+sheafNo);
		Collection allCsiCustProductDTOList = new ArrayList();

		Collection haveDeviceProductCol = (Collection) serviceContext
		.get(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST);
		// 存放产品包对应的优惠和套餐
		Collection referCsiCampaignCol = new ArrayList();
		// 取得客户购买的产品包信息
		Iterator currentPackageIterator = csiPackageArray.iterator();
		while (currentPackageIterator.hasNext()) {
			int packageid = ((Integer) currentPackageIterator.next())
					.intValue();
			allCsiCustProductDTOList.addAll(createCsiCustProductInfo(packageid,
					detailNo, null, csiCampaignArray, csiDTO,
					haveDeviceProductCol, businessParamDTO,
					referCsiCampaignCol, buyGroupNo, sheafNo));
		}

		// 将促销对套餐的优惠也记录到CsiProductInfo中
		try {
			CsiCustProductInfoHome custHome = HomeLocater
					.getCsiCustProductInfoHome();
			if (csiCampaignArray != null && !csiCampaignArray.isEmpty()) {
				Iterator csiCampaignIter = csiCampaignArray.iterator();
				while (csiCampaignIter.hasNext()) {
					Integer csiCampaignID = (Integer) csiCampaignIter.next();
					if (!referCsiCampaignCol.contains(csiCampaignID)) {
						CsiCustProductInfoDTO custProductDto = new CsiCustProductInfoDTO();
						custProductDto.setCsiID(csiDTO.getId());
						custProductDto
								.setReferServiceAccountID((businessParamDTO == null) ? 0
										: businessParamDTO
												.getServiceaccountID());
						custProductDto
								.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
						custProductDto.setAction(getActionByCsitype(csiDTO
								.getType()));
						custProductDto.setReferCampaignID(csiCampaignID
								.intValue());
						CsiCustProductInfo custProduct = custHome
								.create(custProductDto);
						custProductDto.setId(custProduct.getId().intValue());
	    				//侯2007-9-17增加
	    				custProductDto.setGroupNo(buyGroupNo);
	    				custProductDto.setSheafNo(sheafNo);
						allCsiCustProductDTOList.add(custProductDto);
					}
				}
			}
		} catch (HomeFactoryException e) {
			LogUtility
					.log(clazz, LogLevel.ERROR, "createCsiCustProductInfo", e);
			throw new ServiceException("新开户购买产品时定位错误");
		} catch (CreateException e) {
			LogUtility
					.log(clazz, LogLevel.ERROR, "createCsiCustProductInfo", e);
			throw new ServiceException("新开户购买产品时创建错误");
		}

		return allCsiCustProductDTOList;
	}
	private Collection createCsiCustProductInfo(int packageId, String detailNo,
			String contractNO, Collection csiCampaignArray,
			CustServiceInteractionDTO csiDTO, Collection haveDeviceProductCol,
			CommonBusinessParamDTO businessParamDTO,
			Collection referCsiCampaignCol, int buyGroupNo, int sheafNo)
	 throws  ServiceException{
		try{
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createCsiCustProductInfo",new Integer(packageId));
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createCsiCustProductInfo",csiCampaignArray);
			Collection csiCustProductDTOList=new ArrayList();
            ProductHome productHome =HomeLocater.getProductHome();
            CsiCustProductInfoHome custHome = HomeLocater.getCsiCustProductInfoHome();
            int currentCampaign=0;
            // 判断团购券号是否为空
            if(detailNo==null ||"".equals(detailNo)){
            	if(csiCampaignArray!=null && !csiCampaignArray.isEmpty()){
            		// 取得产品包参与过的所有优惠
            		Collection campaignIDCol=BusinessUtility.getCampaignIDListByPackageID(packageId);
            		// 根据选择的优惠列表找到对应产品包的优惠id
            		Iterator currentCampaignIter=csiCampaignArray.iterator();  		
            		while(currentCampaignIter.hasNext()){
            			Integer currentCampaignID=(Integer)currentCampaignIter.next();	
            			if(campaignIDCol.contains(currentCampaignID)){
            				currentCampaign=currentCampaignID.intValue();
            				if (!referCsiCampaignCol.contains(currentCampaignID))
            					 referCsiCampaignCol.add(currentCampaignID);
            				break;
            			}
            		}
            	}
            }else{
            	// 在团购券的时候通过团购券取得优惠的id
            	GroupBargainDetailHome groupBargainDetailHome=HomeLocater.getGroupBargainDetailHome();
            	GroupBargainDetail groupBargainDetail=groupBargainDetailHome.findObjectByDetailNo(detailNo);
            	GroupBargainHome groupBargainHome=HomeLocater.getGroupBargainHome();
            	GroupBargain groupBargain=groupBargainHome.findByPrimaryKey(new Integer(groupBargainDetail.getGroupBargainID()));
            	currentCampaign=groupBargain.getCampaignId();
            }
            Collection colProductID = new ArrayList();
            
            System.out.println("businessParamDTO================"+businessParamDTO);          
            int serviceAccountID =0;
            if (businessParamDTO !=null){
               serviceAccountID =businessParamDTO.getServiceaccountID();
               if (businessParamDTO.getOptioProductSelectMap() !=null && !businessParamDTO.getOptioProductSelectMap().isEmpty())
            	   colProductID =(Collection)businessParamDTO.getOptioProductSelectMap().get(new Integer(packageId));
               if(colProductID==null || colProductID.isEmpty())
            	   colProductID=BusinessUtility.getProductIDsByPackageID(packageId);
            }
            else{        	
                colProductID = BusinessUtility.getProductIDsByPackageID(packageId);
            }
                        
            Iterator iteratorProductID = colProductID.iterator();
            while(iteratorProductID.hasNext()){
				int productId=((Integer) iteratorProductID.next()).intValue();
				CsiCustProductInfoDTO custProductDto = new CsiCustProductInfoDTO();
	            custProductDto.setCsiID(csiDTO.getId());
	            custProductDto.setAction(csiDTO.getType());
	            custProductDto.setProductID(productId);
	            custProductDto.setReferServiceAccountID(serviceAccountID);
	            custProductDto.setReferPackageID(packageId);
	            custProductDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
				//侯2007-9-17增加
				custProductDto.setGroupNo(buyGroupNo);
				custProductDto.setSheafNo(sheafNo);
	            
	            // 只有在门店开户，预约开户是，在检查产品和设备的关联过程中才设置的这个信息
	            if(haveDeviceProductCol!=null && !haveDeviceProductCol.isEmpty()){
		            // 取得产品的类型来判断是否是硬件产品
		            Product product=productHome.findByPrimaryKey(new Integer(productId));
		            if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
			            // 对取得的有设备的硬件产品循环找到对应的产品
			            Iterator haveDeviceProductIterator=haveDeviceProductCol.iterator();
			            while(haveDeviceProductIterator.hasNext()){
			            	CsiCustProductInfoDTO deviceProductDto=(CsiCustProductInfoDTO)haveDeviceProductIterator.next();
			            	if(productId==deviceProductDto.getProductID()
			            			&&buyGroupNo==deviceProductDto.getGroupNo()
			            			&&sheafNo==deviceProductDto.getSheafNo()){
			            		custProductDto.setReferDeviceID(deviceProductDto.getReferDeviceID());
			            		haveDeviceProductCol.remove(deviceProductDto);
			            		break;
			            	}
			            }
		            }else{
		            	custProductDto.setReferDeviceID(0);
		            }
	            }

				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GO.equals(csiDTO.getType()) ||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType()) ||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType())){
					// 定义动作
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_OPEN);
				// 新增产品
				}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType())||
						 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDTO.getType())){
					// 定义动作
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT);
				}
				
				// 设置团购和优惠的相关信息
				if(detailNo!=null && !"".equals(detailNo)){
	                custProductDto.setReferGroupBargainID(csiDTO.getGroupCampaignID());                
				}else{
					custProductDto.setReferGroupBargainID(0);   
				}	
				custProductDto.setReferCampaignID(currentCampaign);
				if(contractNO!=null && !"".equals(contractNO)){
					custProductDto.setReferContractNo(contractNO);
				}
				// 创建受理单涉及的客户产品相关信息
				CsiCustProductInfo custProduct = custHome.create(custProductDto);
				// 回置id
				custProductDto.setId(custProduct.getId().intValue());

	            csiCustProductDTOList.add(custProductDto);
			}
            return csiCustProductDTOList;
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时创建错误");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时查找错误");
    	}		
	}
    /**
	 * 预约
	 * 
	 * @param event：预约开户对象的容器
	 */
    public void book(BookEJBEvent event) throws ServiceException {
    	// 取得新客户信息
    	NewCustomerInfoDTO newCustInfo=event.getNewCustInfo();
    	// 取得新客户新账户信息
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAcctInfo();
    	// 取得受理单信息
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// 取得客户地址信息
    	AddressDTO custAddrDto =event.getCustAddressDTO();
		// 取得账户地址信息
    	AddressDTO acctAddrDto =event.getAcctAddressDTO();
    	// 取得是否代理
    	boolean isAgent=event.isAgent();
    	serviceContext.put(Service.IS_AGENT,new Boolean(isAgent));

		// 取得客户选择的优惠信息
		Collection csiCampaignArray = event.getCsiCampaignArray();
    	
    	// 取得团购券号
    	String detailNO=event.getDetailNo();
    	// 产品包列表
    	Collection	csiPackageArray =event.getCsiPackageArray();
    	
    	// 取得新客户市场信息
    	Collection  newCustMarketInfoList=event.getNewCustMarketInfoList();
    	
    	if(detailNO!=null &&!"".equals(detailNO)){
    		// 取得团购的明细信息
    		GroupBargainDetailDTO detailDTO=BusinessUtility.getGroupBargainDetailByNO(detailNO);
    		// 注意这里填的都是明细的ID，不是总的
    		csiDTO.setGroupCampaignID(detailDTO.getId());
			newCustInfo.setGroupBargainID(detailDTO.getId());	
    	}
    	// 创建客户/客户账户地址信息
    	createAddressInfo(custAddrDto,acctAddrDto);
    	
		newCustInfo.setFromAddressID(custAddrDto.getAddressID());
		newCustAcctInfo.setAddressID(acctAddrDto.getAddressID());
		// 设置帐户地址可以标志
		//newCustAcctInfo.setAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
		
		// 创建受理单信息
		createCustServiceInteraction(csiDTO);
		
		//处理电话号码资源锁定 记录电话号码使用日志
		Integer operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    if (csiDTO.getServiceCodeList() != null) {
			String[] phoneNo = csiDTO.getServiceCodeList().split(",");
			for(int i=0;i<phoneNo.length;i++)
			{
				System.out.println("phoneNo[i]======="+phoneNo[i]);
				if(!"".equals(phoneNo[i]))
					ServiceAccountService.lockPhoneNo(phoneNo[i],csiDTO.getCustomerID(),operatorID,0);
			}
			
		}
	    //处理电话号码资源锁定 end
		
		// 设置新客户账户/客户关联的受理单ID
		newCustAcctInfo.setCsiID(csiDTO.getId());
		newCustInfo.setCsiID(csiDTO.getId());
		// 创建新客户信息
		createNewCustomerInfo( newCustInfo);
		
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		// 创建新客户市场信息
		createNewCustomerMarketInfo(newCustMarketInfoList,newCustInfo.getId(),csiEJB.getId().intValue());
		// 创建新客户账户信息
		createNewCustAccountInfo(newCustAcctInfo,true);
		
		// 创建受理单涉及的客户产品相关信息
		createCsiCustProductInfo( csiPackageArray, detailNO, csiCampaignArray, csiDTO);
		if(!isAgent){
	    	// 是否创建工单
			if(isCreateJobcard(csiDTO)){
				// 创建工单,调用工单service创建工单信息
				InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
			    int jobcardID=installationJobCardService.createJobCardForBooking(csiDTO,0,0,newCustInfo, custAddrDto,event.getNextOrgID());
			    
				// 设置受理单关联的工单id
				csiEJB.setReferjobcardID(jobcardID);
			}
		}
    }
    /**
	 * 门店开户
	 * 
	 * @param event：开户对象的容器
	 */
    public void openAccount(BookEJBEvent event) throws ServiceException{
    	// 取得新客户信息
    	NewCustomerInfoDTO newCustInfo=event.getNewCustInfo();
    	// 取得新客户新账户信息
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAcctInfo();
    	// 取得受理单信息
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// 取得客户地址信息
    	AddressDTO custAddrDto =event.getCustAddressDTO();
		// 取得账户地址信息
    	AddressDTO acctAddrDto =event.getAcctAddressDTO();
    	// 取得是否代理
    	boolean isAgent=event.isAgent();
    	serviceContext.put(Service.IS_AGENT,new Boolean(isAgent));

		// 取得客户选择的优惠信息
		Collection csiCampaignArray = event.getCsiCampaignArray();
    	
    	// 取得团购券号
    	String detailNO=event.getDetailNo();
    	// 产品包列表
    	Collection	csiPackageArray =event.getCsiPackageArray();
    	
    	// 取得新客户市场信息
    	Collection  newCustMarketInfoList=event.getNewCustMarketInfoList();
    	
    	if(detailNO!=null &&!"".equals(detailNO)){
    		// 取得团购的明细信息,
    		GroupBargainDetailDTO detailDTO=BusinessUtility.getGroupBargainDetailByNO(detailNO);
    		// 注意这里填的都是明细的ID，不是总的
    		csiDTO.setGroupCampaignID(detailDTO.getId());
			newCustInfo.setGroupBargainID(detailDTO.getId());	
    	}
    	// 创建客户/客户账户地址信息
    	createAddressInfo(custAddrDto,acctAddrDto);
    	// 设置地址id
		newCustInfo.setFromAddressID(custAddrDto.getAddressID());
		newCustAcctInfo.setAddressID(acctAddrDto.getAddressID());
		// 创建受理单信息
		createCustServiceInteraction(csiDTO);
		
		// 调用客户service创建的客户信息
		CustomerService customerService=new CustomerService(serviceContext);
		customerService.create(newCustInfo,custAddrDto,newCustMarketInfoList);
		Customer customer=(Customer)serviceContext.get(Service.CUSTOMER);
		
		// 调用客户账户sevice创建的账户信息
		AccountService accountService=new AccountService(serviceContext);
		accountService.create(newCustAcctInfo,acctAddrDto);
		Account account =(Account)serviceContext.get(Service.ACCOUNT);
		
		// 设置新客户信息账户信息的受理单id
		newCustAcctInfo.setCsiID(csiDTO.getId());
		// 设置帐户地址可以标志
		newCustAcctInfo.setAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
		newCustInfo.setCsiID(csiDTO.getId());
		// 创建新客户信息
		newCustInfo.setCustID(customer.getCustomerID().intValue());
		createNewCustomerInfo( newCustInfo);
		
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		// 创建新客户市场信息
		createNewCustomerMarketInfo(newCustMarketInfoList,newCustInfo.getId(),csiEJB.getId().intValue());
		// 创建新客户账户信息
		newCustAcctInfo.setAccountID(account.getAccountID().intValue());
		createNewCustAccountInfo(newCustAcctInfo,true);
        // 在新客户信息账户信息之后需要在受理单中设置客户和账户id
		// 在受理单信息中设置账户id
		csiEJB.setAccountID(account.getAccountID().intValue());
		csiDTO.setAccountID(account.getAccountID().intValue());
		// 在客户账户信息中设置客户id
		account.setCustomerID(customer.getCustomerID().intValue());
		// 在受理单信息中设置客户id
		csiEJB.setCustomerID(customer.getCustomerID().intValue());
		csiDTO.setCustomerID(customer.getCustomerID().intValue());
		// 是否创建工单
		if(isCreateJobcard(csiDTO) && CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiEJB.getType())){
			LogUtility.log(clazz,LogLevel.DEBUG,"■■■■需要创建工单■■■■");
			// 创建工单,调用工单service创建工单信息
			InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
		    int jobcardID=installationJobCardService.createJobCardForBooking(csiDTO,customer.getCustomerID().intValue(),0,newCustInfo, custAddrDto,event.getNextOrgID());
			// 设置受理单关联的工单id
			csiEJB.setReferjobcardID(jobcardID);	
		}
		// 如果是预约开户单的话修改对应的预约单对应工单的ReferSheetId和相关预约单信息
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiEJB.getType())){
			try{
			   CustServiceInteractionHome custServiceInteractionHome=HomeLocater.getCustServiceInteractionHome();
    		   CustServiceInteraction custServiceInteraction=custServiceInteractionHome.findByPrimaryKey(new Integer(csiEJB.getReferBookingSheetID()));
    	       // 预约开户时找到对应的预约单，设置预约单和预约开户单之间的关联
    		   custServiceInteraction.setReferBookingSheetID(csiEJB.getId().intValue());
    		   // 设置预约单的客户ID
    		   custServiceInteraction.setCustomerID(customer.getCustomerID().intValue());
               // 设置对应的预约的关联工单
    		   csiDTO.setReferJobCardID(custServiceInteraction.getReferjobcardID());
    		   csiEJB.setReferjobcardID(custServiceInteraction.getReferjobcardID());
			}catch(HomeFactoryException e) {
			    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
			    throw new ServiceException("定位预约受理单信时错误");	            		
	    	}catch(FinderException e) {
			    LogUtility.log(clazz,LogLevel.ERROR,"updateNewCustomerInfo",e);
			    throw new ServiceException("受理单信查找错误");
			}
	    	LogUtility.log(clazz,LogLevel.DEBUG,"■■■■找到该预约单的预约开户单的工单■■■■"+csiEJB.getReferjobcardID());
			// 创建工单,调用工单service创建工单信息
			InstallationJobCardService installationJobCardService=new InstallationJobCardService(csiEJB.getReferjobcardID(),serviceContext);
			installationJobCardService.setsetReferSheetId(csiEJB.getId().intValue());
			installationJobCardService.setReferCustomerId(customer.getCustomerID().intValue());
		}
		
		//模拟电视开户所涉及到的工单,add by jason 2007-7-27 
		if(isCreateJobcard(csiDTO) && CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiEJB.getType())){
			LogUtility.log(clazz,LogLevel.DEBUG,"■■■■模拟电视开户需要创建工单■■■■");
			// 创建工单,调用工单service创建工单信息
			InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
		    int jobcardID=installationJobCardService.createJobCardForCATVOpenAccount(csiDTO,customer.getCustomerID().intValue(),
		    		0,newCustInfo, custAddrDto,event.getNextOrgID(),event.getCatvID(),event.getAddPortNum());
			// 设置受理单关联的工单id
			csiEJB.setReferjobcardID(jobcardID);	
		}
		
		// 创建受理单涉及的客户产品相关信息
		LogUtility.log(clazz,LogLevel.DEBUG,"创建受理单涉及的客户产品相关信息");
		createCsiCustProductInfo( csiPackageArray, detailNO, csiCampaignArray, csiDTO);
		// 设置团购券的相关信息
		if(detailNO!=null &&!"".equals(detailNO)){
        	PublicService.dealOpenAccountRealationGroupBargain(csiDTO,true,CommonConstDefinition.GROUPBARGAINDETAILSTATUS_LOCK);
		}
    }
    /**
	 * 代理商预约更新，预约受理单更新
	 * 
	 * @param csiid：预约开户受理单的ID
	 */
    public void updateBookObject(BookEJBEvent event) throws ServiceException {
    	// 取得新客户信息
    	NewCustomerInfoDTO newCustInfo=event.getNewCustInfo();
    	// 取得新客户新账户信息
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAcctInfo();
    	// 取得受理单信息
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// 取得客户地址信息
    	AddressDTO custAddrDto =event.getCustAddressDTO();
		// 取得账户地址信息
    	AddressDTO acctAddrDto =event.getAcctAddressDTO();
    	// 取得是否代理
    	boolean isAgent=event.isAgent();
    	serviceContext.put(Service.IS_AGENT,new Boolean(isAgent));
		// 取得客户选择的优惠信息
		Collection csiCampaignArray = event.getCsiCampaignArray();
    	// 取得团购券号
    	String detailNO=event.getDetailNo();
    	// 产品包列表
    	Collection	csiPackageArray =event.getCsiPackageArray();
    	// 取得新客户市场信息
    	Collection  newCustMarketInfoList=event.getNewCustMarketInfoList();
    	if(detailNO!=null &&!"".equals(detailNO)){
    		// 取得团购的明细信息,这个信息是PublicService.getPackageArrayByBargainDetailNo执行是设置的
    		GroupBargainDetailDTO detailDTO=BusinessUtility.getGroupBargainDetailByNO(detailNO);
    		// 注意这里填的都是明细的ID，不是总的
    		csiDTO.setGroupCampaignID(detailDTO.getId());
			newCustInfo.setGroupBargainID(detailDTO.getId());	
    	}
    	// 更新新客户账户信息
    	updateNewCustAccountInfo(newCustAcctInfo,true);
    	// 更新新客户信息
    	updateNewCustomerInfo(newCustInfo);
    	// 更新客户地址信息
    	custAddrDto.setAddressID(newCustInfo.getFromAddressID());
    	updateAddressInfo(custAddrDto);
    	// 更新客户账户信息
    	acctAddrDto.setAddressID(newCustAcctInfo.getAddressID());
    	updateAddressInfo(acctAddrDto);
    	// 更新受理单信息
    	updateCustServiceInteractionInfo( csiDTO, event.getActionType());
    	// 更新新客户市场信息
    	updateNewCustomerMarketInfo( newCustMarketInfoList,newCustInfo.getId(),csiDTO.getId());
    	// 更新新客户产品信息
    	delectOldCsiCustProductInfo(csiDTO);
    	// 创建受理单涉及的客户产品相关信息
		createCsiCustProductInfo( csiPackageArray, detailNO, csiCampaignArray, csiDTO);
		if(event.getActionType()==EJBEvent.CHECK_AGENT_BOOKING){
			if(isCreateJobcard(csiDTO)){
				// 创建工单,调用工单service创建工单信息
				InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
			    int jobcardID=installationJobCardService.createJobCardForBooking(csiDTO,0,0, newCustInfo, custAddrDto,event.getNextOrgID());
			    // 在新客户信息账户信息之后需要在受理单中设置客户和账户id
				CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
				// 设置受理单关联的工单id
				csiEJB.setReferjobcardID(jobcardID);
			}
		}
    }
    /**
	 * 确认预约受理单，目前主要是代理商预约受理单需要确认
	 * 
	 * @param
	 */
    public void confirmBook(BookEJBEvent event) throws  ServiceException{
    	updateBookObject(event) ;
    }
    /**
	 * 取消预约单
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void cancelBookSheet(BookEJBEvent event) throws  ServiceException{
    	updateCustServiceInteractionInfo(event.getCsiDto(),event.getActionType());
    }
    /**
	 * 创建开户回访信息
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void callbackOpenAccount(CallBackInfoEJBEvent event) throws  ServiceException{ 
    	// 回访信息
    	Collection callBackInfoList=event.geCallBackInfoList();
    	// 受理单数据对象
    	CustServiceInteractionDTO csiDto=event.getCsiDto();
    	if(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS.equals(csiDto) ||CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL.equals(csiDto)){
			throw new ServiceException("该状态下的受理单不允许创建回访信息");
		}
		if(CommonConstDefinition.CCPCALLBACKFLAG_YES.equals(csiDto.getCallBackFlag())){
			throw new ServiceException("已经回访的受理单不允许创建回访信息");
		}
    	// 更新受理单信息
    	updateCustServiceInteractionInfo(csiDto,event.getActionType());
    	// 创建回访信息
    	createCallbackInfo(callBackInfoList,csiDto);
    }
    
    /**
	 * 客户迁移
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void moveCustomer( CustomerEJBEvent event)throws  ServiceException{
    	// 客户地址
    	AddressDTO  custAddressDTO=event.getCustAddressDTO();
    	// 客户信息
    	CustomerDTO customerDTO =event.getCustomerDTO();
       	// 取得受理单信息
    	CustServiceInteractionDTO csiDto=event.getCsiDto();
    	// 创建受理单信息
    	createCustServiceInteraction(csiDto);
    	
    	// 创建老客户的备份信息
    	CustomerService customerService=new CustomerService(serviceContext);
    	customerService.createCustAndAcctBackupInfo(customerDTO);
    	try{
    		 AddressHome addressHome=HomeLocater.getAddressHome();
    		 custAddressDTO.setAddressID(customerDTO.getAddressID());
    		 Address addr =addressHome.findByPrimaryKey(new Integer(customerDTO.getAddressID()));
    		 if(addr.ejbUpdate(custAddressDTO)==-1){
				throw new ServiceException("更新地址信息时发生错误！");
			 }
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"moveCustomer",e);
			throw new ServiceException("定位地址信息时错误");	            		
	    }catch(FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"moveCustomer",e);
			throw new ServiceException("地址信息查找错误");
		}
        // 更新客户信息
    	customerService.moveCustomer(customerDTO,custAddressDTO);
    }
    
    /**
	 * 客户原地过户，异地过户
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void transferCustomer( CustomerEJBEvent event)throws  ServiceException{
    	// 客户地址
    	AddressDTO  custAddressDTO=event.getCustAddressDTO();	
    	// 客户市场信息
    	Collection custMarketInfoList=event.getCustMarketInfoList();
    	// 客户信息
    	CustomerDTO customerDTO =event.getCustomerDTO();
    	// 取得受理单信息
    	CustServiceInteractionDTO csiDto=event.getCsiDto();
    	// 创建受理单信息
    	createCustServiceInteraction(csiDto);
    	// 更新客户信息
    	CustomerService customerService=new CustomerService(serviceContext);
    	// 创建老客户的备份信息
    	customerService.createCustAndAcctBackupInfo(customerDTO);
    	if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_TD.equals(csiDto.getType())){
    		customerService.transferCustomer(event.getActionType(),customerDTO,custMarketInfoList,custAddressDTO);		
    	}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_TS.equals(csiDto.getType())){
    		customerService.transferCustomer(event.getActionType(),customerDTO,custMarketInfoList,null);	
    	}
    }
    
    /**
	 * 针对业务账户的操作（暂停/恢复/过户/用户关闭）
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void serviceAccountOperation( ServiceAccountEJBEvent event)throws  ServiceException{
    	// 业务账户id
    	int serviceAccountID=event.getServiceAccountID();
    	// 取得受理单信息
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();

    	Collection psidList=null;
    	
    	// 创建受理单信息
    	// setContactPersonInfo(csiDTO,customerDTO.getName(),customerDTO.getTelephone(),customerDTO.getTelephoneMobile());
    	createCustServiceInteraction(csiDTO);
    	// 在新客户信息账户信息之后需要在受理单中设置客户和账户id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		// 过户
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT.equals(csiEJB.getType())){
	    	// 取得业务账户下面所有的软件产品
	    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
	    // 用户关闭
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UC.equals(csiEJB.getType())){
	    	// 取得业务账户下面所有的软件产品
	    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    	// 用户预退户
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_SP.equals(csiEJB.getType())){
	    	// 取得业务账户下面所有的软件产品
	    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
	    // 用户实退户
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_SP.equals(csiEJB.getType())){
	    	// 取得业务账户下面所有的软件产品
	    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
	    	// 暂停
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UP.equals(csiEJB.getType())){
	    	// 取得业务账户下面所有的软件产品
	    	psidList=BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    	// 恢复
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UR.equals(csiEJB.getType())){
	  		createCsiCustProductInfo(csiEJB,event.getColPsid());
	  		return;
	  	// add by chaoqiu 2007-08-02 begin
	  	//模拟电视业务 暂停 
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAS.equals(csiEJB.getType())){
	    	// 取得业务账户下面所有的软件产品
	    	psidList=BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    	// 模拟电视业务 复机
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAR.equals(csiEJB.getType())){
			psidList=BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,null);
	  	// add by chaoqiu 2007-08-02 end	
		}
		// 用户过户/关闭/暂停/  模拟电视业务 暂停 /模拟电视业务 复机
		createCsiCustProductInfo(csiEJB,psidList);
    	
    }
    public void serviceAccountOperationForTransfer(CustServiceInteractionDTO csiDTO ,int serviceAccountID)throws  ServiceException{
    	if(csiDTO==null||serviceAccountID==0){
    		throw new ServiceException("目标客户受理单创建失败,信息不全");
    	}
    	// 创建受理单信息
    	createCustServiceInteraction(csiDTO);
    	// 创建受理单关联的产品信息.
    	createCSIProduct(csiDTO.getId(),serviceAccountID);

    }
    /**
	 * 针对产品的操作（暂停/恢复/取消/产品升级/产品降级/更换设备）
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void customerProductOperation(CustomerProductEJBEvent event) throws  ServiceException{
    	// 客户产品id列表
    	Collection  psidList=event.getColPsid();
    	// 升/降级的目标产品
		int productID=event.getProductID();// =event.get
		// 升/降级的目标产品所对应当设备
		int deviceID=event.getDeviceID();
		// 动作
		// 取得受理单信息
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// 创建受理单信息
    	/*2007/3/1 杨晨 add  用来设置支付状态  start*/
    	csiDTO.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY);
    	/*2007/3/1 杨晨 add  用来设置支付状态  end*/
    	createCustServiceInteraction(csiDTO);
    	// 在新客户信息账户信息之后需要在受理单中设置客户和账户id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		// 产品升级/产品降级/更换设备
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PU.equals(csiEJB.getType())||
			CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PD.equals(csiEJB.getType())||
			CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DS.equals(csiEJB.getType())){
			CsiCustProductInfoDTO custProductDto=new CsiCustProductInfoDTO();
			custProductDto.setDestProductID(productID);
			custProductDto.setReferDestDeviceID(deviceID);
			createCsiCustProductInfo(csiEJB,(Integer)psidList.iterator().next(),custProductDto);
		// 暂停/恢复/迁移/取消客户产品
		}else{
			createCsiCustProductInfo(csiEJB,psidList);
		}
    }
    
    /**
     * 用来更新受理单的付费状态,在这里是属于受理处理过程,所以不需要创建受理单日志记录
     * @param serviceContext
     * @param status
     * @throws ServiceException
     */
    public void updateCSIPayStatus(ServiceContext serviceContext,String status)throws  ServiceException{
    	CustServiceInteraction csi=(CustServiceInteraction)serviceContext.get(Service.CSI);
    	csi.setPaymentStatus(status);
    	csi.setDtLastmod(TimestampUtility.getCurrentTimestamp());
    }
    /**
     * 用来更新受理单的付费状态,在这里是属于受理处理过程,所以不需要创建受理单日志记录
     * @param csiID
     * @param status
     * @throws ServiceException
     */
    public void updateCSIPayStatus(int csiID,String status)throws  ServiceException{
    	try{
    		CustServiceInteractionHome csiHome = HomeLocater.getCustServiceInteractionHome();
    		CustServiceInteraction csi=csiHome.findByPrimaryKey(new Integer(csiID));
    		csi.setPaymentStatus(status);
    		csi.setDtLastmod(TimestampUtility.getCurrentTimestamp());
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("更新受理单付费状态时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("更新受理单付费状态时查找错误");
		}
    }
    
    /**
	 * 迁移，过户时处理老客户备份信息
	 * 
	 * @param csiDTO
	 * @param oldCustomerID
	 * @param newAddressID
	 * @throws ServiceException
	 */
    /*
    private void  createCustAndAcctBackupInfo( int customerID,int csiID,boolean updateAddress)throws  ServiceException{
=======
    public void  createCustAndAcctBackupInfo( int customerID,int csiID,boolean updateAddress)throws  ServiceException{
>>>>>>> 1.223
    	try{
    	// 取得老客户相关信息
    	CustomerDTO customerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerID);
    	NewCustomerInfoDTO newCustomerInfoDTO=new NewCustomerInfoDTO();
    	newCustomerInfoDTO.setCsiID(csiID);  
    	newCustomerInfoDTO.setBirthday(customerDTO.getBirthday());
    	newCustomerInfoDTO.setCustID(customerDTO.getCustomerID());                  
    	newCustomerInfoDTO.setCatvID(customerDTO.getCatvID());                  
    	newCustomerInfoDTO.setDigitalCatvID(customerDTO.getCatvID());    
    	newCustomerInfoDTO.setType(customerDTO.getCustomerType());                      
    	newCustomerInfoDTO.setNationality(customerDTO.getNationality());        
    	newCustomerInfoDTO.setGender(customerDTO.getGender());                  
    	newCustomerInfoDTO.setName(customerDTO.getName());                      
    	newCustomerInfoDTO.setCardType(customerDTO.getCardType());              
    	newCustomerInfoDTO.setCardID(customerDTO.getCardID());                             
    	newCustomerInfoDTO.setOpenSourceType(customerDTO.getOpenSourceType());  
    	newCustomerInfoDTO.setOpenSourceID(customerDTO.getOpenSourceTypeID());      
    	newCustomerInfoDTO.setTelephone(customerDTO.getTelephone());            
    	newCustomerInfoDTO.setMobileNumber(customerDTO.getTelephoneMobile());      
    	newCustomerInfoDTO.setFaxNumber(customerDTO.getFax());            
    	newCustomerInfoDTO.setEmail(customerDTO.getEmail());                    
    	newCustomerInfoDTO.setGroupBargainID(customerDTO.getGroupBargainID());  
    	newCustomerInfoDTO.setContractNo(null);          
    	newCustomerInfoDTO.setTimeRangeStart(null);  
    	newCustomerInfoDTO.setTimeRangeEnd(null);      
    	newCustomerInfoDTO.setSocialSecCardID(customerDTO.getSocialSecCardID());
    	if(updateAddress){
    		newCustomerInfoDTO.setToAddressID(customerDTO.getAddressID());
    		AddressDTO addrDto=BusinessUtility.getAddressDTOByAddressID(customerDTO.getAddressID());
			AddressHome addressHome=HomeLocater.getAddressHome();
			Address address=addressHome.create(addrDto);
    		// 设置地址信息
        	newCustomerInfoDTO.setFromAddressID(address.getAddressID().intValue());
    	}else{
    		newCustomerInfoDTO.setToAddressID(customerDTO.getAddressID());
    		newCustomerInfoDTO.setFromAddressID(customerDTO.getAddressID());
    	}
    	// 创建老客户的备份信息
    	createNewCustomerInfo(newCustomerInfoDTO);
    	// 取得客户的市场信息
    	CustMarketInfoHome customerMarketInfoHome=HomeLocater.getCustMarketInfoHome();
    	Collection customerMarketInfoList=customerMarketInfoHome.findByCustomerId(customerDTO.getCustomerID());
    	Collection currentMarketDtoList=new ArrayList();
    	if(customerMarketInfoList!=null && !customerMarketInfoList.isEmpty()){
    		Iterator currentMarketIter=customerMarketInfoList.iterator();
    		while(currentMarketIter.hasNext()){
    			CustMarketInfo  custMarketInfo=(CustMarketInfo)currentMarketIter.next();
    			NewCustomerMarketInfoDTO newCustomerMarketInfoDTO=new NewCustomerMarketInfoDTO();
    			newCustomerMarketInfoDTO.setNewCustomerId(newCustomerInfoDTO.getId());
				newCustomerMarketInfoDTO.setInfoSettingId(custMarketInfo.getInfoSettingId());
				newCustomerMarketInfoDTO.setInfoValueId(custMarketInfo.getInfoValueId());
				currentMarketDtoList.add(newCustomerMarketInfoDTO);
    		}
    	}
    	// 创建老客户的备份市场信息
    	createNewCustomerMarketInfo(currentMarketDtoList,newCustomerInfoDTO.getId());
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建客户备份信息定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建客户备份信息时查找错误");
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建客户备份信息创建错误");
    	}
    }
    */
    /**
	 * 过户时处理老客户账户备份信息
	 * 
	 * @param csiDTO
	 * @param oldCustomerID
	 * @param newAddressID
	 * @throws ServiceException
	 */
    public void  createAccountBackupInfo(int customerID,int csiID,boolean updateAddress)throws  ServiceException{
    	try{  	
        	// 创建老客户的账户备份信息
        	AccountHome accountHome=HomeLocater.getAccountHome();
        	// 取得老客户的所有账户信息
        	Collection oldAccountList=accountHome.findByCustomerId(customerID);
        	if(oldAccountList!=null && !oldAccountList.isEmpty()){
        		Iterator oldAcountIter=oldAccountList.iterator();
        		while(oldAcountIter.hasNext()){
        			Account oldAccount=(Account)oldAcountIter.next();
        			createAccountBackupInfo(oldAccount,csiID,updateAddress);
        		}
        	}	
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建客户账户备份信息定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建客户备份信息时查找错误");
    	}
    }
    /**
	 * 过户时处理老客户账户备份信息
	 * 
	 * @param csiDTO
	 * @param oldCustomerID
	 * @param newAddressID
	 * @throws ServiceException
	 */
    private void  createAccountBackupInfo(Account oldAccount,int csiID,boolean updateAddress)throws  ServiceException{
    	try{  	
        	// 创建老客户的账户备份信息
        	NewCustAccountInfoDTO newCustAccountInfoDTO=new NewCustAccountInfoDTO();
    		// 取得账户地址信息
    		if(updateAddress){
    			AddressDTO acctAddrDto=BusinessUtility.getAddressDTOByAddressID(oldAccount.getAddressID());
    			AddressHome addressHome=HomeLocater.getAddressHome();
    			Address acctAddress=addressHome.create(acctAddrDto);
				newCustAccountInfoDTO.setAddressID(acctAddress.getAddressID().intValue());
    	    }else{
    	    	newCustAccountInfoDTO.setAddressID(oldAccount.getAddressID());
    	    }
			newCustAccountInfoDTO.setCsiID(csiID);
			newCustAccountInfoDTO.setAccountID(oldAccount.getAccountID().intValue());
			newCustAccountInfoDTO.setAddressFlag(oldAccount.getBillAddressFlag());
			newCustAccountInfoDTO.setDescription(null);
			newCustAccountInfoDTO.setMopID(oldAccount.getMopID());
			newCustAccountInfoDTO.setBankAccountName(oldAccount.getAccountName());
			newCustAccountInfoDTO.setBankAccount(oldAccount.getBankAccount());
			newCustAccountInfoDTO.setAddressFlag(oldAccount.getBillAddressFlag());
			newCustAccountInfoDTO.setAccountType(oldAccount.getAccountType());
			// 创建老客户测备份信息
			createNewCustAccountInfo(newCustAccountInfoDTO,false);
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建客户账户备份信息定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建客户账户备份信息时创建错误");
    	}
    }
    /**
	 * 创建开户时的回访信息
	 * 
	 * @param callBackInfoList
	 * @param csiDto
	 * @throws ServiceException
	 */
    private void  createCallbackInfo(Collection callBackInfoList,CustServiceInteractionDTO csiDto) throws  ServiceException{
    	try{
    		CallBackInfoHome callBackInfoHome=HomeLocater.getCallBackInfoHome();
    		// 删除老的开户回访信息
    		int csiId=csiDto.getId();
    		Collection haveExitCallBackInfo=callBackInfoHome.findByReferTypeAndReferSourceId(CommonConstDefinition.CALLBACKINFOTYPE_OPEN,csiId);
    		if(haveExitCallBackInfo!=null && !haveExitCallBackInfo.isEmpty()){
    			Iterator haveExitIterator=haveExitCallBackInfo.iterator();
    			while(haveExitIterator.hasNext()){
    				CallBackInfo callBackInfo=(CallBackInfo)haveExitIterator.next();
    				callBackInfo.remove();
    			}
    		}
    		// 创建新的开户回访信息
	    	if(callBackInfoList!=null && !callBackInfoList.isEmpty()){
	    		Iterator callbackInfoIter=callBackInfoList.iterator() ;
	    		while(callbackInfoIter.hasNext()){
	    			CallBackInfoDTO callbackInfoDTO=(CallBackInfoDTO)callbackInfoIter.next();
	    			callbackInfoDTO.setReferSourceId(csiDto.getId());
	    			callbackInfoDTO.setReferSourceType(CommonConstDefinition.CALLBACKINFOTYPE_OPEN);
	    			CallBackInfo callBackInfo=callBackInfoHome.create(callbackInfoDTO);
	    			callbackInfoDTO.setSeqNo(callBackInfo.getSeqNo().intValue());
	    		}
	    	}
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建开户回访信息定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建开户回访信息时创建错误");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("创建开户回访信息时查找错误");
    	}catch(RemoveException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("删除旧的开户回访信息时错误");
		}
    }
    
    /**
	 * 根据地址ID取得详细地址信息
	 * 
	 * @param addressID
	 * @return
	 * @throws ServiceException
	 */
    private String getAddressName(int addressID) throws  ServiceException{
    	try{
    		String addressName="";
    		AddressHome addressHome=HomeLocater.getAddressHome();
    		Address address=addressHome.findByPrimaryKey(new Integer(addressID));
    		addressName=address.getDetailAddress();
    		return addressName;
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("取得地址信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("取得地址信息时查找错误");
    	}
    }
    /**
	 * 根据客户的id取得客户的姓名
	 * 
	 * @param addressID
	 * @return
	 * @throws ServiceException
	 */
    private String getCustomerName(int customerID) throws  ServiceException{
    	try{
    		String customerName="";
    		CustomerHome customerHome=HomeLocater.getCustomerHome();
    		Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
    		customerName=customer.getName();
    		return customerName;
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("取得得客户信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("取得得客户信息时查找错误");
    	}
    }
    
    /**
	 * 根据受理单信息和操作类型更新受理单信息并创建受理单日志
	 * 
	 * @param csiDTO
	 *            受理单dto(在对象中一般只需要传入受理单id,如果关于受理单的其他信息必须从工单操作获取时也传入)
	 * @param actionType
	 *            统一定义的ejbevent中
	 */
    public void updateCustServiceInteractionInfo(CustServiceInteractionDTO csiDTO,int actionType) throws  ServiceException{
    	try{
    		String actionDesc = "";
		    String action ="";
    		int updateResult=0;
    		CustServiceInteractionHome custServiceInteractionHome=HomeLocater.getCustServiceInteractionHome();
    		CustServiceInteraction custServiceInteraction=custServiceInteractionHome.findByPrimaryKey(new Integer(csiDTO.getId()));
    		Integer operatorID;
    		String serviceCodeList;
    		String phoneNo;
    		switch(actionType){
	    		// 安装预约
	    		case EJBEvent.CONTACT_USER_FOR_INSTALLATION:
	    			// 状态正在处理
	    			custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_PROCESS);
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_INSTALL;
	    			actionDesc = "预约成功";
	    			break;
    			// 结束安装
    			case EJBEvent.CLOSE_INSTALLATION_INFO:
    				// 受理不成功
	    			custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL);    			
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_FAIL;
    				actionDesc = "受理失败";
	    			if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(custServiceInteraction.getType())){
		    			CustServiceInteraction bookCsi =custServiceInteractionHome.findByPrimaryKey(new Integer(custServiceInteraction.getReferBookingSheetID()));
		    			bookCsi.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL);
                        // 创建预约受理单处理日志
		        		operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
		    		    recordCsiProcessLog(bookCsi.getId().intValue(), action, operatorID.intValue(), actionDesc,csiDTO.getStatusReason());
	    			}
    				break;
    			case EJBEvent.CATV_REGISTER_JOBCARD:
    				//录入模拟电视施工单
				custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				actionDesc = "受理成功";				
				break;
    			// 录入安装信息
    			case EJBEvent.REGISTER_INSTALLATION_INFO:
	    			custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    				actionDesc = "受理成功";
    				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(custServiceInteraction.getType())){
    				   CustServiceInteraction bookCsi =custServiceInteractionHome.findByPrimaryKey(new Integer(custServiceInteraction.getReferBookingSheetID()));
 	    			   bookCsi.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
	    			   // 创建预约受理单处理日志
		        	   operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
		    		   recordCsiProcessLog(bookCsi.getId().intValue(), action, operatorID.intValue(), actionDesc,csiDTO.getStatusReason());
    				}
    				break;
                // 修改预约单信息
    			case EJBEvent.ALTERBOOKING:
    				
    				//语音业务 load老电话号码:
    				serviceCodeList = custServiceInteraction.getServiceCodeList();
    				if(serviceCodeList == null)serviceCodeList = "";
    				//新电话号码:
    				phoneNo = csiDTO.getServiceCodeList();
    				if(phoneNo == null) phoneNo="";
    				
    				//更新受理单
    				updateResult = custServiceInteraction.ejbUpdate(csiDTO);
    				if(updateResult==-1){
    					throw new ServiceException("更新预约受理单时发生错误！");
    				}
    				//语音业务 处理电话号码资源 begin
    				//int itemID = BusinessUtility.getDTOByPhoneNo(phoneNo).getItemID();
    				if(!serviceCodeList.equals(phoneNo))
    				{
	    				//释放原电话号码资源:
    					operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    				if(!"".equals(serviceCodeList))
	    					releasePhoneNo(serviceCodeList,custServiceInteraction.getCustomerID(),operatorID,0);
	    				
	    				//锁定新电话号码资源:
	    				if(!"".equals(phoneNo))
	    					ServiceAccountService.lockPhoneNo(phoneNo,custServiceInteraction.getCustomerID(),
	    							operatorID,0);
    				}
    				//语音业务 处理电话号码资源 end
    			    action=CommonConstDefinition.CSIPROCESSLOG_ACTION_UPDATE;
				    actionDesc = "修改预约单";
    				break;
                // 确认代理商预约
    			case EJBEvent.CHECK_AGENT_BOOKING:
    				
    				//语音业务 load老电话号码:
    				serviceCodeList = custServiceInteraction.getServiceCodeList();
    				if(serviceCodeList == null)serviceCodeList = "";
    				//新电话号码:
    				phoneNo = csiDTO.getServiceCodeList();
    				if(phoneNo == null) phoneNo="";
    				
    				updateResult = custServiceInteraction.ejbUpdate(csiDTO);
	    			if(updateResult==-1){
	    				throw new ServiceException("更新代理商预约受理单时发生错误");	
	    				
	    			}
	    			
	    			//语音业务 处理电话号码资源 begin
    				//int itemID = BusinessUtility.getDTOByPhoneNo(phoneNo).getItemID();
    				if(!serviceCodeList.equals(phoneNo))
    				{
	    				//释放原电话号码资源:
    					operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    				if(!"".equals(serviceCodeList))
	    					releasePhoneNo(serviceCodeList,custServiceInteraction.getCustomerID(),operatorID,0);
	    				
	    				//锁定新电话号码资源:
	    				if(!"".equals(phoneNo))
	    					ServiceAccountService.lockPhoneNo(phoneNo,custServiceInteraction.getCustomerID(),
	    							operatorID,0);
    				}
    				//语音业务 处理电话号码资源 end
	    			
	    			custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT);
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_APPLY;
				    actionDesc = "确认代理商预约单";
    				break;    			
    			// 取消预约单
    			case EJBEvent.CANCELBOOKING:
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_BOOKINGCANCEL;
    				if(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT.equals(custServiceInteraction.getStatus())){
	    				if(custServiceInteraction.getReferjobcardID()!=0){
	    					// 取消该预约单关联的工单
		    				InstallationJobCardService jobCardService=new InstallationJobCardService(custServiceInteraction.getReferjobcardID(),serviceContext);
		    				JobCardProcessDTO jobCardProcessDto =new JobCardProcessDTO();
		    				jobCardProcessDto.setWorkResult(CommonConstDefinition.JOBCARD_STATUS_SUCCESS);
		    				//jobCardProcessDto.setOutOfDateReason();
		    				jobCardService.cancel(jobCardProcessDto);
	    				}
    				}
    				custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL);
    				custServiceInteraction.setStatusReason(csiDTO.getStatusReason());
    				//语音业务 释放电话号码资源:
    				operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    			if(custServiceInteraction.getServiceCodeList()!=null && !"".equals(custServiceInteraction.getServiceCodeList()))
	    				releasePhoneNo(custServiceInteraction.getServiceCodeList(),custServiceInteraction.getCustomerID(),operatorID,0);
    				
    				actionDesc = "取消预约单";	
    				break;
        		// 安装预约取消 取消安装工单 同时取消预约单
    			case EJBEvent.CANCEL_INSTALLATION_JOB_CARD:
    			case EJBEvent.CATV_CANCEL_JOBCARD:
    				custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL);    				
    					
    				custServiceInteraction.setStatusReason(csiDTO.getStatusReason());
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_INSTALLCANCEL;
    				actionDesc = "安装预约取消";
    				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(custServiceInteraction.getType())
    						||CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(custServiceInteraction.getType())){
						custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_INSTALLCANCEL);
						
					}
    				//语音业务 释放电话号码资源:
    				operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    			if(custServiceInteraction.getServiceCodeList()!=null && !"".equals(custServiceInteraction.getServiceCodeList()))
	    				releasePhoneNo(custServiceInteraction.getServiceCodeList(),custServiceInteraction.getCustomerID(),operatorID,0);
    				
    					
    				break;
    			// 开户回访
    			case EJBEvent.CALLFOROPENACCOUNT:
	    			custServiceInteraction.setCallbackFlag(CommonConstDefinition.CCPCALLBACKFLAG_YES);
    				custServiceInteraction.setCallBackDate(TimestampUtility.getCurrentDate());
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_CALLBACK;
				    actionDesc = "开户回访";
    				break; 
    			// 开户回访暂存
    			case EJBEvent.SETCALLFLAG4OPENACCOUNT:
	    			custServiceInteraction.setCallbackFlag(CommonConstDefinition.CPCALLBACKFLAG_T);
    			    custServiceInteraction.setCallBackDate(TimestampUtility.getCurrentDate());
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SETCALLBACKFLAG;
				    actionDesc = "开户回访暂存";
    				break;
    			// 安装不成功退费
    			case EJBEvent.RETURNFEE4FAILINSTALLATION:
    				if(!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL.equals(custServiceInteraction.getStatus())){
    					throw new ServiceException("只有处理不成功的受理单才能退费");	
    				}
    				/*由于现在的支付是可配的,在门店自安装失败的时候可能是未付费的
	    			if(!CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED.equals(custServiceInteraction.getPaymentStatus())){
						throw new ServiceException("只有已付费的受理单才能退费");	
					}
					*/
				    // 如果有关联的工单,处理对应工单的状态
				    if(custServiceInteraction.getReferjobcardID()!=0){
				    	// 只有无法继续处理的工单才能退费
				    	JobCardService jobCardService=new InstallationJobCardService(custServiceInteraction.getReferjobcardID(),serviceContext);
				    	jobCardService.canReturnFeeForInstallFail();
				    }
				    custServiceInteraction.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_RETURNMONEY);
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_RETURNMONEY;
				    actionDesc = "安装不成功退费";
    				break;
			    // 预约增机确认
    			case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER:
    				action=CommonConstDefinition.CSIPRPCESSLOG_ACTION_PROCESS;
    				actionDesc ="预约增机确认";
    				csiDTO.setPaymentStatus(custServiceInteraction.getPaymentStatus());
    				custServiceInteraction.setBillCollectionMethod(csiDTO.getBillCollectionMethod());
    				String oldCreatReason = custServiceInteraction.getCreateReason()==null?"":custServiceInteraction.getCreateReason();
    				String nowCreatReason = csiDTO.getCreateReason()==null?"":csiDTO.getCreateReason();
    				if(!oldCreatReason.equals(nowCreatReason))
    				{
    					
    					custServiceInteraction.setComments("增机时受理原因由预约时的:"+
    							BusinessUtility.getCsiReasonDesByCon(custServiceInteraction.getType(), "N", oldCreatReason)+"改为:"+
    							BusinessUtility.getCsiReasonDesByCon(custServiceInteraction.getType(), "N", nowCreatReason));
    				}
    				//custServiceInteraction.setCreateReason(csiDTO.getCreateReason());
    				InstallationJobCardService jobCardService1=new InstallationJobCardService(custServiceInteraction.getReferjobcardID(),serviceContext);
    				jobCardService1.setReferCustomerId(custServiceInteraction.getCustomerID());
    				break;
				// 预约增机的预约取消
    			case EJBEvent.CUSTOMER_BOOKINGUSER_CANCEL_SUBSCRIBER:
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_BOOKINGCANCEL;
    				if(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT.equals(custServiceInteraction.getStatus())){
	    				// 取消该预约单关联的工单
	    				InstallationJobCardService jobCardService=new InstallationJobCardService(custServiceInteraction.getReferjobcardID(),serviceContext);
	    				jobCardService.cancel(null);
    				}
    				custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL);
    				
    				//语音业务 释放电话号码资源:
    				operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    			if(custServiceInteraction.getServiceCodeList()!=null && !"".equals(custServiceInteraction.getServiceCodeList()))
	    				releasePhoneNo(custServiceInteraction.getServiceCodeList(),custServiceInteraction.getCustomerID(),operatorID,0);
	    			
    				actionDesc = "预约增机的预约取消";	
    				break;
    			// 预约增机的预约修改
    			case EJBEvent.CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER:
    				
    				//语音业务 load老电话号码:
    				serviceCodeList = custServiceInteraction.getServiceCodeList();
    				if(serviceCodeList == null)serviceCodeList = "";
    				//新电话号码:
    				phoneNo = csiDTO.getServiceCodeList();
    				if(phoneNo == null) phoneNo="";
    
    				action =CommonConstDefinition.CSIPROCESSLOG_ACTION_UPDATE;
    				actionDesc ="预约增机修改";
    			
    				if (custServiceInteraction.ejbUpdate(csiDTO) ==-1)
    					   throw new ServiceException("修改受理单出错！");
    				csiDTO.setReferJobCardID(custServiceInteraction.getReferjobcardID());
    				
    				//语音业务 处理电话号码资源 begin
    				//int itemID = BusinessUtility.getDTOByPhoneNo(phoneNo).getItemID();
    				if(!serviceCodeList.equals(phoneNo))
    				{
	    				//释放原电话号码资源:
    					operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    				if(!"".equals(serviceCodeList))
	    					releasePhoneNo(serviceCodeList,custServiceInteraction.getCustomerID(),operatorID,0);
	    				
	    				//锁定新电话号码资源:
	    				if(!"".equals(phoneNo))
	    					ServiceAccountService.lockPhoneNo(phoneNo,custServiceInteraction.getCustomerID(),
	    							operatorID,0);
    				}
    				//语音业务 处理电话号码资源 end
    				break;
    				
    			default:
    				break;
    		}
			custServiceInteraction.setDtLastmod(TimestampUtility.getCurrentDate());
    		serviceContext.put(Service.CSI, custServiceInteraction);
    		//侯1-08增加
		    serviceContext.put(Service.CSI_ID, custServiceInteraction.getId());

    		// 创建受理单处理日志
    		operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
		    recordCsiProcessLog(csiDTO.getId(), action, operatorID.intValue(), actionDesc,csiDTO.getStatusReason());
    	
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("更新受理单信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("更新受理单信息时查找错误");
    	}
    }
	/**
	 * 该方法主要用于普通客户开户，新增产品之类的业务 创建受理单涉及的客户产品相关信息
	 * @param csiPackageArray
	 * @param detailNo
	 * @param csiCampaignArray
	 * @param csiDTO
	 * @return
	 * @throws ServiceException
	 */
	
	/**
	 * 该方法主要用于集团客户子客户开户，子客户新开用户，新增产品业务， 创建受理单涉及的客户产品相关信息
	 * @param csiPackageArray
	 * @param detailNo
	 * @param contractNo
	 * @param csiCampaignArray
	 * @param csiDTO
	 * @param haveDeviceProductCol
	 * @return
	 * @throws ServiceException
	 */
	public Collection createCsiCustProductInfo(Collection csiPackageArray,String detailNo,String contractNo,Collection csiCampaignArray,CustServiceInteractionDTO csiDTO,Collection haveDeviceProductCol)throws  ServiceException{
		Collection allCsiCustProductDTOList=new ArrayList();
		// 取得客户购买的产品包信息
		Iterator currentPackageIterator=csiPackageArray.iterator();
		while(currentPackageIterator.hasNext()){
			int packageid = ((Integer) currentPackageIterator.next()).intValue();	
			allCsiCustProductDTOList.addAll(createCsiCustProductInfo(packageid,detailNo,contractNo,csiCampaignArray,csiDTO,haveDeviceProductCol));	
		}
		return allCsiCustProductDTOList;
	}
	/**
	 * 该方法主要用于针对产品进行的某种操作 针对客户产品id的列表创建受理单涉及的客户产品相关信息
	 * 
	 * @param csi
	 * @param colPsid
	 * @param action
	 * @return
	 * @throws ServiceException
	 */
	public void createCsiCustProductInfo(CustServiceInteraction csi,Collection colPsid) throws  ServiceException{
		if(colPsid!=null &&!colPsid.isEmpty()){
			Iterator psIdIterator = colPsid.iterator();
			while (psIdIterator.hasNext()) {
				Integer psid = (Integer)psIdIterator.next();
				CsiCustProductInfoDTO custProductDto =new CsiCustProductInfoDTO();
				createCsiCustProductInfo(csi,psid,custProductDto);
			}
		}
	}
	
	/**
	 * 该方法主要用于针对产品进行的某种操作 针对单个客户产品id创建受理单涉及的客户产品相关信息
	 * 
	 * @param csi
	 * @param psID
	 * @param custProductDto
	 * @param action
	 * @throws ServiceException
	 */
	private void createCsiCustProductInfo(CustServiceInteraction csi,Integer psID,CsiCustProductInfoDTO custProductDto) throws  ServiceException{
		try{
			CustomerProductHome customerProductHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp = customerProductHome.findByPrimaryKey(psID);
			
			CsiCustProductInfoHome csiCustProductInfoHome=HomeLocater.getCsiCustProductInfoHome();
			
			CustomerHome customerHome=HomeLocater.getCustomerHome();
			Customer customer=customerHome.findByPrimaryKey(new Integer(csi.getCustomerID()));
			// CPCampaignMapHome
			// cPCampaignMapHome=HomeLocater.getCPCampaignMapHome();
			// cPCampaignMapHome.findByCustProductID();
			
			// 创建受理单产品相关信息
			// 取消客户产品
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CP.equals(csi.getType())){
				custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_CANCEL);
			// 产品升级
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PU.equals(csi.getType())){
				custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_ASCEND);
			// 产品降级
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PD.equals(csi.getType())){
				custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_DESCEND);
			// 设备更换
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DS.equals(csi.getType())){
				custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_S);
			}
			custProductDto.setReferAccountID(cp.getAccountID());
			custProductDto.setReferServiceAccountID(cp.getServiceAccountID());
			custProductDto.setCsiID(csi.getId().intValue());
			custProductDto.setCustProductID(psID.intValue());
			custProductDto.setProductID(cp.getProductID());
			custProductDto.setReferDeviceID(cp.getDeviceID());
			// 以下内容待定
			// custProductDto.setReferContractNo();
			// custProductDto.setReferCampaignID();
//			custProductDto.setDestProductID(cp.getProductID());
			custProductDto.setReferGroupBargainID(customer.getGroupBargainID());
			custProductDto.setReferPackageID(cp.getReferPackageID());
			custProductDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			csiCustProductInfoHome.create(custProductDto);
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("对产品操作时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("对产品操作时创建错误");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("对产品操作时查找错误");
    	}
	}
	/**
	 * 根据受理单信息创建客户下面客户产品的备份信息
	 * @param csiDTO
	 * @throws ServiceException
	 */
	public  void createCsiCustProductInfo(CustServiceInteractionDTO csiDTO) throws  ServiceException{
		try{
			Collection custprodCol=BusinessUtility.getCustProdDTOListBySaAndPsID(0,0,"customerid="+csiDTO.getCustomerID());
			if(custprodCol!=null && !custprodCol.isEmpty()){
				Iterator cusprodIter=custprodCol.iterator();
				CustomerProductDTO cusProductDTO=null;
				CsiCustProductInfoHome csiCustProductInfoHome=HomeLocater.getCsiCustProductInfoHome();
				while(cusprodIter.hasNext()){
					cusProductDTO=(CustomerProductDTO)cusprodIter.next();
					CsiCustProductInfoDTO dto=new CsiCustProductInfoDTO();
					dto.setCsiID(csiDTO.getId());
					dto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_CANCEL);
					dto.setCustProductID(cusProductDTO.getPsID());
					dto.setDtCreate(TimestampUtility.getCurrentTimestamp());
					dto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					dto.setProductID(cusProductDTO.getProductID());
					dto.setReferAccountID(cusProductDTO.getAccountID());
					dto.setReferDeviceID(cusProductDTO.getDeviceID());
					dto.setReferPackageID(cusProductDTO.getReferPackageID());
					dto.setReferServiceAccountID(cusProductDTO.getServiceAccountID());
					dto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
					csiCustProductInfoHome.create(dto);
				}
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("对产品操作时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("对产品操作时创建错误");
		}
	}
	/**
	 * 该方法主要用于开户，新增产品之类的业务 针对单个产品包id创建受理单涉及的客户产品相关信息
	 * 
	 * @param packageId
	 * @param detailNo
	 * @param csiCampaignArray
	 * @param csiDTO
	 * @return
	 * @throws ServiceException
	 */
	private Collection createCsiCustProductInfo(int packageId,String detailNo,String contractNO,Collection csiCampaignArray,CustServiceInteractionDTO csiDTO,Collection haveDeviceProductCol)throws  ServiceException{
		try{
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createCsiCustProductInfo",new Integer(packageId));
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createCsiCustProductInfo",csiCampaignArray);
			Collection csiCustProductDTOList=new ArrayList();
			Collection colProductID = BusinessUtility.getProductIDsByPackageID(packageId);
            Iterator iteratorProductID = colProductID.iterator();
            ProductHome productHome =HomeLocater.getProductHome();
            CsiCustProductInfoHome custHome = HomeLocater.getCsiCustProductInfoHome();
            int currentCampaign=0;
            // 判断团购券号是否为空
            if(detailNo==null ||"".equals(detailNo)){
            	if(csiCampaignArray!=null && !csiCampaignArray.isEmpty()){
            		// 取得产品包参与过的所有优惠
            		Collection campaignIDCol=BusinessUtility.getCampaignIDListByPackageID(packageId);
            		// 根据选择的优惠列表找到对应产品包的优惠id
            		Iterator currentCampaignIter=csiCampaignArray.iterator();  		
            		while(currentCampaignIter.hasNext()){
            			Integer currentCampaignID=(Integer)currentCampaignIter.next();	
            			if(campaignIDCol.contains(currentCampaignID)){
            				currentCampaign=currentCampaignID.intValue();
            				break;
            			}
            		}
            	}
            }else{
            	// 在团购券的时候通过团购券取得优惠的id
            	GroupBargainDetailHome groupBargainDetailHome=HomeLocater.getGroupBargainDetailHome();
            	GroupBargainDetail groupBargainDetail=groupBargainDetailHome.findObjectByDetailNo(detailNo);
            	GroupBargainHome groupBargainHome=HomeLocater.getGroupBargainHome();
            	GroupBargain groupBargain=groupBargainHome.findByPrimaryKey(new Integer(groupBargainDetail.getGroupBargainID()));
            	currentCampaign=groupBargain.getCampaignId();
            }
            //----------2006/11/23-------yangchen---------start------
            //以下的处理由杨晨移除，通过参数引用传入，
            // 取得在检查设备和产品关联是设置的产品和设备映射成功CsiCustProductDTO
            // 是在BusinessRuleService.checkSelectTerminalDevice中设置的
            //Collection haveDeviceProductCol=(Collection)serviceContext.get(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST);
            //----------2006/11/23-------yangchen---------end------
            while(iteratorProductID.hasNext()){
				int productId=((Integer) iteratorProductID.next()).intValue();
				CsiCustProductInfoDTO custProductDto = new CsiCustProductInfoDTO();
	            custProductDto.setCsiID(csiDTO.getId());
	            custProductDto.setProductID(productId);
	            custProductDto.setReferPackageID(packageId);
	            custProductDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
	            // 只有在门店开户，预约开户是，在检查产品和设备的关联过程中才设置的这个信息
	            if(haveDeviceProductCol!=null && !haveDeviceProductCol.isEmpty()){
		            // 取得产品的类型来判断是否是硬件产品
		            Product product=productHome.findByPrimaryKey(new Integer(productId));
		            if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
			            // 对取得的有设备的硬件产品循环找到对应的产品
			            Iterator haveDeviceProductIterator=haveDeviceProductCol.iterator();
			            while(haveDeviceProductIterator.hasNext()){
			            	CsiCustProductInfoDTO deviceProductDto=(CsiCustProductInfoDTO)haveDeviceProductIterator.next();
			            	if(productId==deviceProductDto.getProductID()){
			            		custProductDto.setReferDeviceID(deviceProductDto.getReferDeviceID());
			            		haveDeviceProductCol.remove(deviceProductDto);
			            		break;
			            	}
			            }
		            }else{
		            	custProductDto.setReferDeviceID(0);
		            }
	            }
				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GO.equals(csiDTO.getType())){
					// 定义动作
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_OPEN);
				// 新增产品
				}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType())){
					// 定义动作
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT);
				}
				
				
				// 设置团购和优惠的相关信息
				if(detailNo!=null && !"".equals(detailNo)){
	                custProductDto.setReferGroupBargainID(csiDTO.getGroupCampaignID());                
	                custProductDto.setReferCampaignID(currentCampaign);
				}else{
					custProductDto.setReferGroupBargainID(0);   
					custProductDto.setReferCampaignID(currentCampaign);
				}
/*
				// 取得新客户信息,这个信息必须在前面调用该方法的入口方法里面设置
				NewCustomerInfoDTO newCustInfo=(NewCustomerInfoDTO)serviceContext.get(com.dtv.oss.service.Service.NEW_CUSTOMER_INFO_DTO);
				// 设置企业用户开户时使用的合同号
				if(newCustInfo!=null){
					custProductDto.setReferContractNo(newCustInfo.getContractNo());
				}
*/
				if(contractNO!=null && !"".equals(contractNO)){
					custProductDto.setReferContractNo(contractNO);
				}
				// 创建受理单涉及的客户产品相关信息
				CsiCustProductInfo custProduct = custHome.create(custProductDto);
				// 回置id
				custProductDto.setId(custProduct.getId().intValue());
	            csiCustProductDTOList.add(custProductDto);
			}
            return csiCustProductDTOList;
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时创建错误");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时查找错误");
    	}		
	}
	/**
	 * 创建 CSI 日志记录
	 * 
	 * @param csiid
	 * @param action
	 * @param operatorid
	 * @param description
	 * @throws HomeFactoryException
	 * @throws CreateException
	 */
	private void recordCsiProcessLog(int csiid, String action, int operatorid, int invoiceNo,String description,String workResultReason) throws  ServiceException{
		try{
			CsiProcessLogDTO cplDto = new CsiProcessLogDTO();
			cplDto.setCsiID(csiid);
			cplDto.setAction(action);
			cplDto.setActionTime(TimestampUtility.getCurrentTimestamp());
			cplDto.setOperatorID(operatorid);
			cplDto.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorid));
			cplDto.setInvoiceNo(invoiceNo);
			cplDto.setDescription(description);
			cplDto.setWorkResultReason(workResultReason);
			
			
			
			CsiProcessLogHome home = HomeLocater.getCsiProcessLogHome();
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"recordCsiProcessLog",cplDto);
			home.create(cplDto);
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"recordCsiProcessLog",e);
		    throw new ServiceException("纪录受理单日志信息时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"recordCsiProcessLog",e);
		    throw new ServiceException("纪录受理单日志信息时错误");
		}
	}
	private void recordCsiProcessLog(int csiid, String action, int operatorid, String description,String workResultReason) throws  ServiceException{
		recordCsiProcessLog(csiid,action,operatorid,0,description,workResultReason);
	}
	/**
	 * 创建客户/客户账户地址信息
	 * 
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public void createAddressInfo(AddressDTO custAddrDto,AddressDTO acctAddrDto)throws ServiceException {
		try{
			AddressHome addrHome = HomeLocater.getAddressHome();
		    if(acctAddrDto!=null){
		    	LogUtility.log(CSIService.class,LogLevel.DEBUG,"创建客户地址",custAddrDto);
		    	Address custAddr = addrHome.create(custAddrDto);
		    	custAddrDto.setAddressID(custAddr.getAddressID().intValue());
		    }
		    if(custAddrDto!=null){
		    	LogUtility.log(CSIService.class,LogLevel.DEBUG,"创建账户地址",acctAddrDto);
		    	Address acctAddr= addrHome.create(acctAddrDto);
		    	acctAddrDto.setAddressID(acctAddr.getAddressID().intValue());
		    }
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("创建地址信息时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("创建地址信息错误");
		}
	}
	/**
	 * 创建所有的受理单信息
	 * 
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public void createCustServiceInteraction(CustServiceInteractionDTO csiDTO,int invoiceNo)throws ServiceException {
		try{
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"在createCustServiceInteraction创建受理单信息开始");
			// 设置受理单信息
			csiDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());
			Integer operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
			csiDTO.setCreateOperatorId(operatorID.intValue());
			csiDTO.setCreateORGId(BusinessUtility.getOpOrgIDByOperatorID(operatorID.intValue()));
			csiDTO.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY);
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_MB.equals(csiDTO.getType())){
				csiDTO.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
			}
			//csiDTO.setCreateOperatorId(operatorID.intValue());
			//csiDTO.setCreateORGId(BusinessUtility.getOpOrgIDByOperatorID(operatorID.intValue()));
			// 受理单类型时预约/代理商预约单
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiDTO.getType())){
				Boolean isAgent=(Boolean)serviceContext.get(Service.IS_AGENT);
			    if (isAgent.booleanValue()) // 如果是代理商，预约单状态为‘新建’
			    	csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_NEW);
			    else
			    	csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT);
			// 受理单类型预约开户单
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDTO.getType())){
				csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_PROCESS);
			// 受理单是是门店开户/预约增机/预约新增客户产品/门店增机/模拟电视开户
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType())||
				//	CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDTO.getType())||  注释掉 by david.Yang
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAS.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAR.equals(csiDTO.getType())
			){
				csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT);		
				//模拟电视增端受理单
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAA.equals(csiDTO.getType())){
				csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_PROCESS);
				//受理单是原地过户/用户过户/集团客户开户/集团客户子客户开户以及其他受理类型
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAM.equals(csiDTO.getType())
								||CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAC.equals(csiDTO.getType())
								||CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDTO.getType()) //丽江需求，add by david.Yang
			        ){
				csiDTO.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
				csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
			}else{
				 csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
			}
			// 设定回访标志
			csiDTO.setCallBackFlag(CommonConstDefinition.YESNOFLAG_NO);
			CustServiceInteractionHome csiHome = HomeLocater.getCustServiceInteractionHome();
		    CustServiceInteraction csi = csiHome.create(csiDTO);
		    // 回置受理单的id
		    csiDTO.setId(csi.getId().intValue());
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createCustServiceInteraction",csiDTO);
		    		    
		    // 区分是预约受理单还是其他类型的受理单，他们对应的键值不一样，目的是为了预约开户是有所区分
		    serviceContext.put(Service.CSI, csi);
		    serviceContext.put(Service.CSI_ID, csi.getId());
		    // 创建受理单处理日志
		    String actionDesc = (String)serviceContext.get(Service.ACTION_DESCRTIPION);
		    String action =(String)serviceContext.get(Service.ACTION_DEFITION);
		    //在创建受理单的时候，日志中的状态变化原因记录为受理单的创建原因
		    recordCsiProcessLog(csi.getId().intValue(), action, operatorID.intValue(), invoiceNo,actionDesc,csiDTO.getCreateReason());
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("创建受理单信时定位息错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("创建受理单信息错误");
		}
	}
	public void createCustServiceInteraction(CustServiceInteractionDTO csiDTO)throws ServiceException{
		createCustServiceInteraction(csiDTO,0);
	}
	
	/**
	 * 创建新客户信息
	 * 
	 * @param newCustInfoDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void createNewCustomerInfo(NewCustomerInfoDTO newCustInfoDto)throws ServiceException {
		try{
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"在createNewCustomerInfo创建新客户信息开始");
			NewCustomerInfoHome nciHome = HomeLocater.getNewCustomerInfoHome();
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createNewCustomerInfo",newCustInfoDto);
		    NewCustomerInfo nci = nciHome.create(newCustInfoDto);
		    serviceContext.put(Service.NEW_CUSTOMER_INFO_EJB,nci);
		    // 回置客户ID
		    newCustInfoDto.setId(nci.getId().intValue());
		    LogUtility.log(CSIService.class,LogLevel.DEBUG,"在createNewCustomerInfo创建新客户信息结束");
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustomerInfo",e);
		    throw new ServiceException("开户处理中设置客户信息时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustomerInfo",e);
		    throw new ServiceException("开户处理中设置客户信息错误");
		}
	}
	/**
	 * 创建新客户市场信息
	 * 
	 * @param newCustMarketInfoList
	 * @param customerID
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void createNewCustomerMarketInfo(Collection  newCustMarketInfoList,int customerID,int csiID)throws ServiceException {
		try{
			NewCustomerMarketInfoHome newCustomerMarketInfoHome = HomeLocater.getNewCustomerMarketInfoHome();
			Iterator itemarketinfo = newCustMarketInfoList.iterator();
			while(itemarketinfo.hasNext()) {
				NewCustomerMarketInfoDTO marketDto = (NewCustomerMarketInfoDTO)itemarketinfo.next();
				marketDto.setNewCustomerId(customerID);
				marketDto.setCsiID(csiID);
				newCustomerMarketInfoHome.create(marketDto);
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustomerMarketInfo",e);
		    throw new ServiceException("开户处理中设置市场信息时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustomerMarketInfo",e);
		    throw new ServiceException("开户处理中设置市场信息错误");
		}
	}
	
	/**
	 * 创建新客户账户信息
	 * 
	 * @param newCustAcctInfoDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void createNewCustAccountInfo(NewCustAccountInfoDTO newCustAcctInfoDto ,boolean isPutIntoContext)throws ServiceException {
		try{
			NewCustAccountInfoHome ncaiHome = HomeLocater.getNewCustAccountInfoHome();
			NewCustAccountInfo newCustAccountInfo=ncaiHome.create(newCustAcctInfoDto);
			// 回置id
			newCustAcctInfoDto.setId(newCustAccountInfo.getId().intValue());
			if(isPutIntoContext){
				serviceContext.put(Service.NEW_CUST_ACCOUNT_INFO_EJB,newCustAccountInfo);
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustAccountInfo",e);
		    throw new ServiceException("开户处理中设置账户信息时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustAccountInfo",e);
		    throw new ServiceException("开户处理中设置账户信息错误");
		}
	}
	
	/**
	 * 更新新客户信息
	 * 
	 * @param newCustInfoDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void updateNewCustomerInfo(NewCustomerInfoDTO newCustInfoDto)throws ServiceException {
		try{
			NewCustomerInfoHome nciHome = HomeLocater.getNewCustomerInfoHome();
			NewCustomerInfo  newCustomerInfo=nciHome.findByPrimaryKey(new Integer(newCustInfoDto.getId()));
			newCustInfoDto.setDtLastmod(newCustomerInfo.getDtLastmod());
			// 设置一下地址信息id,便于地址信息更新时使用
			newCustInfoDto.setFromAddressID(newCustomerInfo.getFromAddressID());
			int updateResult=newCustomerInfo.ejbUpdate(newCustInfoDto);
			if(updateResult==-1){
				throw new ServiceException("更新新客户信息时发生错误");	
				
			}
			serviceContext.put(Service.NEW_CUSTOMER_INFO_EJB,newCustomerInfo);
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"updateNewCustomerInfo",e);
		    throw new ServiceException("更新新客户信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"updateNewCustomerInfo",e);
		    throw new ServiceException("更新新客户信息错误");
		}
	}
	/**
	 * 更新新客户市场信息
	 * 
	 * @param newCustMarketInfoList
	 * @param customerID
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void updateNewCustomerMarketInfo(Collection  newCustMarketInfoList,int customerID,int csiID)throws ServiceException {
		try{
			NewCustomerMarketInfoHome newCustomerMarketInfoHome = HomeLocater.getNewCustomerMarketInfoHome();
			Collection newCustomerMarketInfoList=newCustomerMarketInfoHome.findByNewCustomerId(customerID);
			if(newCustomerMarketInfoList!=null && !newCustomerMarketInfoList.isEmpty()){
				Iterator oldNewCustomerMarketInfoIter=newCustomerMarketInfoList.iterator();
				while(oldNewCustomerMarketInfoIter.hasNext()){
					NewCustomerMarketInfo newCustomerMarketInfo=(NewCustomerMarketInfo)oldNewCustomerMarketInfoIter.next();
					newCustomerMarketInfo.remove();
				}
			}
			Iterator itemarketinfo = newCustMarketInfoList.iterator();
			while(itemarketinfo.hasNext()) {
				NewCustomerMarketInfoDTO newcustMarketDto = (NewCustomerMarketInfoDTO)itemarketinfo.next();
				newcustMarketDto.setNewCustomerId(customerID);
				newcustMarketDto.setCsiID(csiID);
				newCustomerMarketInfoHome.create(newcustMarketDto);
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("更新新客户市场信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("查找新客户市场信息错误");
		}catch(RemoveException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("删除新客户市场信息错误");
		}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("创建新客户市场信息错误");
		}
	}
	
	/**
	 * 更新新客户账户信息
	 * 
	 * @param newCustAcctInfoDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void updateNewCustAccountInfo(NewCustAccountInfoDTO newCustAcctInfoDto ,boolean isPutIntoContext)throws ServiceException {
		try{
			NewCustAccountInfoHome ncaiHome = HomeLocater.getNewCustAccountInfoHome();
			NewCustAccountInfo  newCustAccountInfo=ncaiHome.findByPrimaryKey(new Integer(newCustAcctInfoDto.getId()));
			newCustAcctInfoDto.setDtLastmod(newCustAccountInfo.getDtLastmod());
			// 设置地址信息id，便于地址更新时使用
			newCustAcctInfoDto.setAddressID(newCustAccountInfo.getAddressID());
			int updateResult=newCustAccountInfo.ejbUpdate(newCustAcctInfoDto);
			if(updateResult==-1)
				throw new ServiceException("更新账户信息时发生错误");
			if(isPutIntoContext){
				serviceContext.put(Service.NEW_CUST_ACCOUNT_INFO_EJB,newCustAccountInfo);
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("更新账户信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("更新账户信息错误");
		}
	}
	/**
	 * 更新地址信息
	 * 
	 * @param addressDto
	 * @throws ServiceException
	 */
	private void updateAddressInfo(AddressDTO addressDto)throws ServiceException {
		try{
			AddressHome addressHome = HomeLocater.getAddressHome();
			Address  address=addressHome.findByPrimaryKey(new Integer(addressDto.getAddressID()));
			addressDto.setDtLastmod(address.getDtLastmod());
			int updateResult=address.ejbUpdate(addressDto);
			if(updateResult==-1){
				throw new ServiceException("更新地址信息时发生错误");	
				
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("更新地址信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("更新地址信息查找错误");
		}
	}
	
	/**
	 * 删除受理单对应的老产品信息
	 * 
	 * @param csiDTO
	 * @throws ServiceException
	 */
	public void delectOldCsiCustProductInfo(CustServiceInteractionDTO csiDTO)throws ServiceException {
		try{
			CsiCustProductInfoHome csiCustProductInfoHome = HomeLocater.getCsiCustProductInfoHome();
			Collection  csiCustProductInfoCol=csiCustProductInfoHome.findByCSIID(csiDTO.getId());
			if(csiCustProductInfoCol!=null && !csiCustProductInfoCol.isEmpty()){
				Iterator csiCustProductInfoIter=csiCustProductInfoCol.iterator();
				while(csiCustProductInfoIter.hasNext()){
					CsiCustProductInfo csiCustProductInfo=(CsiCustProductInfo)csiCustProductInfoIter.next();
					csiCustProductInfo.remove();
				}
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("更新地址信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("更新地址信息错误");
		}
    	catch(RemoveException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("更新地址信息错误");
		}
		
	}
	/**
	 * 判断该受理单是否需要创建工单
	 * 
	 * @param csiDTO
	 * @return
	 */
	private boolean isCreateJobcard(CustServiceInteractionDTO csiDTO){
		LogUtility.log(clazz,LogLevel.DEBUG,"■创建工单 InstallationType■"+csiDTO.getInstallationType());
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL.equals(csiDTO.getInstallationType())){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @param csiID
	 * @return
	 * @throws ServiceException
	 */
	public boolean isChangeCustomerAndProductStatus(int csiID) throws ServiceException{
		boolean ischange=false;
		CustServiceInteractionDTO csiDTO=BusinessUtility.getCSIDTOByID(csiID);
		if(csiDTO!=null){
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiDTO.getType())||
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDTO.getType())||
				  CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiDTO.getType())){
				ischange=true;
			}
		}else{
			throw new  ServiceException("该工单没有对应的受理单");
		}
		return ischange;
	}
	
	public boolean isChangeTerminalDeviceStatus(int csiID) throws ServiceException{
		boolean ischange =false;
		CustServiceInteractionDTO csiDTO=BusinessUtility.getCSIDTOByID(csiID);
		if(csiDTO!=null){
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDTO.getType())){
					ischange=true;
				}
		}else{
			throw new  ServiceException("该工单没有对应的受理单");
		}
		return ischange;
	}
	
	/**
	 * 根据saID创建受理单的产品
	 * 
	 * @param csiID
	 * @param saID
	 * @throws ServiceException
	 */
	public void createCSIProduct(int csiID,int saID) throws ServiceException{
		
		if(csiID==0 || saID==0)
			return;
		
		try{
			CustomerProductHome pHome=HomeLocater.getCustomerProductHome();
			Collection cpList=pHome.findByServiceAccountID(saID);

			if(cpList==null || cpList.isEmpty())
				return;
			
			CsiCustProductInfoHome csiCPHome=HomeLocater.getCsiCustProductInfoHome();
			
			Iterator itcp=cpList.iterator();
			while(itcp.hasNext()){
				CustomerProduct cp=(CustomerProduct)itcp.next();
				
				if (cp.getStatus().equals(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL)) continue;
				CsiCustProductInfoDTO custProductDto = new CsiCustProductInfoDTO();
				
				custProductDto.setAction(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT);
				custProductDto.setCsiID(csiID);
				custProductDto.setProductID(cp.getProductID());
				custProductDto.setReferPackageID(cp.getReferPackageID());
				custProductDto.setReferAccountID(cp.getAccountID());
				custProductDto.setCustProductID(cp.getPsID().intValue());
				custProductDto.setReferServiceAccountID(saID);
				custProductDto.setReferCampaignID(0);
				custProductDto.setReferDeviceID(cp.getDeviceID());
				custProductDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
				
				csiCPHome.create(custProductDto);
			}
		} 
		catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("创建受理单产品信息出错！");
		}
	}
	/**
	 * 创建集团客户开户,
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void createGroupCustServiceInteractionInfo(GroupCustomerEJBEvent event) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"集团客户开户");
    	// 取得新客户信息
    	NewCustomerInfoDTO newCustInfo=event.getNewCustomerInfoDTO();
    	newCustInfo.setCustStyle(CommonConstDefinition.CUSTOMERSTYLE_GROUP);
    	// 取得新客户新账户信息
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAccountInfoDTO();
    	
    	// 取得受理单信息
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	
    	// 取得客户地址信息
    	AddressDTO custAddrDto =event.getAddressDTO();
    	
		// 取得账户地址信息
    	AddressDTO acctAddrDto =event.getAccountAddressDTO();
    	
    	// 产品包列表
// Collection csiPackageArray =event.getCsiPackageArray();
    	
    	// 创建客户/客户账户地址信息
    	createAddressInfo(custAddrDto,acctAddrDto);
    	// 设置地址id
		newCustInfo.setFromAddressID(custAddrDto.getAddressID());
		
		newCustAcctInfo.setAddressID(acctAddrDto.getAddressID());
		// 创建受理单信息
		createCustServiceInteraction(csiDTO);
		
		// 调用客户service创建的客户信息
		CustomerService customerService=new CustomerService(serviceContext);
		customerService.create(newCustInfo,custAddrDto,null);
		Customer customer=(Customer)serviceContext.get(Service.CUSTOMER);
		
		// 调用客户账户sevice创建的账户信息
		AccountService accountService=new AccountService(serviceContext);
		accountService.create(newCustAcctInfo,acctAddrDto);
		Account account =(Account)serviceContext.get(Service.ACCOUNT);
		
		// 设置新客户信息账户信息的受理单id
		newCustAcctInfo.setCsiID(csiDTO.getId());
		// 设置帐户地址可以标志
		newCustAcctInfo.setAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
		newCustInfo.setCsiID(csiDTO.getId());
		// 创建新客户信息
		newCustInfo.setCustID(customer.getCustomerID().intValue());
		createNewCustomerInfo( newCustInfo);
		
		// 创建新客户账户信息
		newCustAcctInfo.setAccountID(account.getAccountID().intValue());
		createNewCustAccountInfo(newCustAcctInfo,true);

		// 在新客户信息账户信息之后需要在受理单中设置客户和账户id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
 
		// 在受理单信息中设置账户id
		csiEJB.setAccountID(account.getAccountID().intValue());
		csiDTO.setAccountID(account.getAccountID().intValue());
		
		// 在客户账户信息中设置客户id
		account.setCustomerID(customer.getCustomerID().intValue());
		// 在受理单信息中设置客户id
		csiEJB.setCustomerID(customer.getCustomerID().intValue());
		csiDTO.setCustomerID(customer.getCustomerID().intValue());
		
    	// 取得合同信息
    	ContractDTO contractDTO=event.getContractDTO();
    	ContractDTO dto=BusinessUtility.getContractDTOByContractNo(event.getContractDTO().getContractNo());
    	
    	//更新合同的已使用人数并创建合同使用日志
		ContractService contractService=new ContractService(serviceContext);
		String descpription="集团客户合同号："+event.getContractDTO().getContractNo()+" 集团客户ID："+customer.getCustomerID()+" 合同用途：集团客户开户";
		contractService.processContractWithGroupCustomerOpen(contractDTO.getContractNo(),customer.getCustomerID().intValue(),0,descpription);
		//创建合同信息
		createGroupCustomerCampaign(dto,customer.getCustomerID().intValue(),account.getAccountID().intValue(),0);
		
		LogUtility.log(clazz,LogLevel.DEBUG,"创建受理单涉及的客户产品相关信息完成");
		
	}
	
	public void createGroupSubCustomer(GroupCustomerEJBEvent event,ServiceContext serviceContext)
			throws ServiceException {
		//检查设备关系是否正确
		ContractDTO contractDTO = BusinessUtility
		.getContractDTOByContractNo(event.getContractNO());
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		Collection initCsiproductCol = businessRuleService
				.checkOpenChildDevAndGetCsiProdList(event.getCsiDto(),contractDTO, event.getDeviceTable(),
						true);
		//创建受理单
		createCustServiceInteraction(event.getCsiDto());
		//创建客户产品备份信息
		createCsiCustProductInfo(event.getContractToPackageIDCol(),null ,event.getContractNO(),null,event.getCsiDto(),initCsiproductCol);
		// 在新客户信息账户信息之后需要在受理单中设置客户和账户id
		CustServiceInteraction csiEJB = (CustServiceInteraction) serviceContext
				.get(Service.CSI);
		// 取得子客户地址信息
		AddressDTO custAddrDto = event.getAddressDTO();
		// 取得子客户信息
		NewCustomerInfoDTO subCustDTO = event.getNewCustomerInfoDTO();
		//创建子客户客户信息
		CustomerService customerService = new CustomerService(serviceContext);
		customerService.create(subCustDTO, custAddrDto, null);
		Customer customer = (Customer) serviceContext.get(Service.CUSTOMER);
		subCustDTO.setCustID(customer.getCustomerID().intValue());
		// 取得集团客户信息，集团客户ID
		subCustDTO.setGroupCustID(event.getCustomerID());
		subCustDTO.setCsiID(csiEJB.getId().intValue());
		//创建子客户备份信息
		createNewCustomerInfo(subCustDTO);
		

		//这里只回置客户id，账户id在创建的时候由页面选择完成了
		csiEJB.setCustomerID(customer.getCustomerID().intValue());
		//创建业务帐户
		ServiceAccountService serviceAccountService = new ServiceAccountService(
				serviceContext);
		serviceAccountService.createForGroup(event.getContractToPackageIDCol(), null, event.getPhoneNo(),
				event.getItemID(), event.getProductPropertyMap());

		String serviceAccountID = (String)serviceContext.get(Service.SERVICE_ACCOUNT_ID);

		//更新合同的已使用人数并创建合同使用日志
		ContractService contractService=new ContractService(serviceContext);
		String descpription="集团客户合同号："+contractDTO.getContractNo()+" 集团客户ID："+event.getCustomerID()+" 合同用途：子客户开户 子客户ID："+customer.getCustomerID()+";业务帐户ID"+serviceAccountID;
		contractService.processContractWithGroupCustomerOpen(event.getContractNO(),event.getCustomerID(),1,descpription);
	}
	/**
	 * 建立集团客户的促销促销信息,主要是合同,创建一条促销记录,记录合同号,客户号,此操作必须在客户创建之后.
	 * 
	 * @throws ServiceException
	 * 
	 */
	public void createGroupCustomerCampaign(ContractDTO dto,int customerID,int accountID,int serviceAccountID) throws ServiceException{
		try {
			CustomerCampaignHome cchome = HomeLocater.getCustomerCampaignHome();
			CustomerCampaignDTO ccDTO = new CustomerCampaignDTO();
			ccDTO.setCustomerID(customerID);
			ccDTO.setContactNo(dto.getContractNo());
			ccDTO.setDateFrom(dto.getDatefrom());
			ccDTO.setDateTo(dto.getDateto());
			ccDTO.setAccountID(accountID);
			ccDTO.setServiceAccountID(serviceAccountID);
			cchome.create(ccDTO);
			LogUtility.log(clazz, LogLevel.DEBUG,
					"\n创建客户相关合同信息到customerCampaign,\ncustomerID:"
							+ customerID + "\ncontractNo:"
							+ dto.getContractNo());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("创建集团客户合同失败.");
		}		
	}

	/**
	 * 对集团客户新开用户进行处理
	 * fiona
	 * @param event
	 * @throws ServiceException
	 */
	public void openChildCustServiceAccount(GroupCustomerEJBEvent event,Collection initCsiproductCol) throws ServiceException{
		//update by chaoqiu 2007-04-29 begin
		//设备列表是否有记录  
		//if(event.getDeviceTable()==null||event.getDeviceTable().isEmpty())
		//	throw new ServiceException("没有设备记录！");
		//update by chaoqiu 2007-04-29 end
		
		//serviceContext.put(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST,initCsiproductCol.iterator().next());
		serviceContext.put(com.dtv.oss.service.Service.CUSTOMER_ID, new Integer(event.getCsiDto().getCustomerID()));
		serviceContext.put(com.dtv.oss.service.Service.ACCOUNT_ID, new Integer(event.getCsiDto().getAccountID()));
		//创建受理单
		createCustServiceInteraction(event.getCsiDto());
		//创建客户产品备份信息
		Collection packagecol=BusinessUtility.getPackageIDByContractID(event.getContractNO());
		createCsiCustProductInfo(packagecol,null ,event.getContractNO(),null,event.getCsiDto(),initCsiproductCol);
		//创建业务帐户/客户产品
		ServiceAccountService serviceAccountService=new ServiceAccountService(serviceContext);
		serviceAccountService.createForGroup (packagecol,null,event.getPhoneNo(),event.getItemID(),event.getProductPropertyMap());
		//更新合同的已使用人数并创建合同使用日志
		ContractService contractService=new ContractService(serviceContext);
		String serviceAccountID = (String)serviceContext.get(Service.SERVICE_ACCOUNT_ID);
		String descpription="集团客户ID："+event.getCustomerID()+" 子客户ID："+event.getCsiDto().getCustomerID()+" 合同用途：子客户新开用户  子客户业务帐户ID："+serviceAccountID;
		contractService.processContractWithGroupCustomerOpen(event.getContractNO(),event.getCustomerID(),1,descpription);
	}
	
	/**
	 * 根据受理单id取得本次受理的设备id列表  
	 * @param csiId
	 * @return
	 * @throws ServiceException
	 */
	public Collection getDeviceIDList(int csiId)throws ServiceException{
	  	Collection  deviceIDList =new ArrayList();
  		Collection saCol=BusinessUtility.getReferServiceIDByCsiID(csiId);
  		if(saCol!=null && !saCol.isEmpty()){
  			Iterator saIter=saCol.iterator();
  			while(saIter.hasNext()){
  				Integer saID=(Integer)saIter.next();
  				deviceIDList.addAll(BusinessUtility.getDeviceListBySaID(saID.intValue()));
  			}
  		}
        return deviceIDList;	
	}
	/**
	 * 门店开户
	 * 
	 * @param event：开户对象的容器
	 */
    public void openAccountDirectly(BookEJBEvent event) throws ServiceException{
    	// 取得新客户信息
    	NewCustomerInfoDTO newCustInfo=event.getNewCustInfo();
    	// 取得新客户新账户信息
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAcctInfo();
    	// 取得受理单信息
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// 取得客户地址信息
    	AddressDTO custAddrDto =event.getCustAddressDTO();
		// 取得账户地址信息
    	AddressDTO acctAddrDto =event.getAcctAddressDTO();
    	// 取得是否代理
    	boolean isAgent=event.isAgent();
    	serviceContext.put(Service.IS_AGENT,new Boolean(isAgent));
    	
    	//取得新客户市场信息
    	Collection  newCustMarketInfoList=event.getNewCustMarketInfoList();

		// 创建客户/客户账户地址信息
    	createAddressInfo(custAddrDto,acctAddrDto);
    	// 设置地址id
		newCustInfo.setFromAddressID(custAddrDto.getAddressID());
		newCustAcctInfo.setAddressID(acctAddrDto.getAddressID());
		
		// 创建受理单信息
		createCustServiceInteraction(csiDTO);
		
		// 调用客户service创建的客户信息
		CustomerService customerService=new CustomerService(serviceContext);
		customerService.create(newCustInfo,custAddrDto,newCustMarketInfoList);
		Customer customer=(Customer)serviceContext.get(Service.CUSTOMER);
		customer.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
		// 调用客户账户sevice创建的账户信息
		AccountService accountService=new AccountService(serviceContext);
		accountService.create(newCustAcctInfo,acctAddrDto);
		Account account =(Account)serviceContext.get(Service.ACCOUNT);
		
		// 设置新客户信息账户信息的受理单id
		newCustAcctInfo.setCsiID(csiDTO.getId());
		// 设置帐户地址可以标志
		newCustAcctInfo.setAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
		newCustInfo.setCsiID(csiDTO.getId());
		// 创建新客户信息
		newCustInfo.setCustID(customer.getCustomerID().intValue());
		createNewCustomerInfo( newCustInfo);
		
				
		// 创建新客户账户信息
		newCustAcctInfo.setAccountID(account.getAccountID().intValue());
		createNewCustAccountInfo(newCustAcctInfo,true);

		// 在新客户信息账户信息之后需要在受理单中设置客户和账户id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		
		// 创建新客户市场信息
		createNewCustomerMarketInfo(newCustMarketInfoList,newCustInfo.getId(),csiEJB.getId().intValue());
		
		
 
		// 在受理单信息中设置账户id
		csiEJB.setAccountID(account.getAccountID().intValue());
		csiEJB.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
		csiDTO.setAccountID(account.getAccountID().intValue());
		
		// 在客户账户信息中设置客户id
		account.setCustomerID(customer.getCustomerID().intValue());
		// 在受理单信息中设置客户id
		csiEJB.setCustomerID(customer.getCustomerID().intValue());
		csiDTO.setCustomerID(customer.getCustomerID().intValue());
		
		// 创建押金信息
		// 是否创建工单
		if(isCreateJobcard(csiDTO)){
			LogUtility.log(clazz,LogLevel.DEBUG,"■■■■需要创建工单■■■■");
			// 创建工单,调用工单service创建工单信息
			InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
		    int jobcardID=installationJobCardService.createJobCardForBooking(csiDTO,customer.getCustomerID().intValue(),0,newCustInfo, custAddrDto,event.getNextOrgID());
			// 设置受理单关联的工单id
			csiEJB.setReferjobcardID(jobcardID);	
		}
    }
    public boolean canCancelBookingAddCustProduct(CustomerProductEJBEvent inEvent)throws ServiceException {
		try{
			CustServiceInteractionHome csiHome = HomeLocater.getCustServiceInteractionHome();
			CustServiceInteraction csi = csiHome.findByPrimaryKey(new Integer(inEvent.getCsiDto().getId()));
			String status = csi.getStatus();
			if(status.equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT)){
				return true;
			}
		}catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR,"检查客户预约产品受理单状态环境异常："+e1);
			throw new ServiceException("检查客户预约产品受理单状态环境异常");
		}catch(FinderException e){
			LogUtility.log(clazz,LogLevel.ERROR,"检查客户预约产品受理单状态定位受理单错："+e);
			throw new ServiceException("检查客户预约产品受理单状态定位受理单错："+e);
		}
		return false;
	}
	public void cancelBookingAddCustomerProduct(CustomerProductEJBEvent inEvent) throws ServiceException {
		updateCustServiceInteractionInfo(inEvent.getCsiDto(),inEvent.getActionType());
		updateCsiCustProductInfo(inEvent);
	}
	
	private void updateCsiCustProductInfo(CustomerProductEJBEvent inEvent) throws ServiceException {
		try{
			CsiCustProductInfoHome csiCustProductInfoHome=HomeLocater.getCsiCustProductInfoHome();			
			Collection ccpidCol=BusinessUtility.getCsiCustProductInfoIdByCSIID(inEvent.getCsiDto().getId());
			for(Iterator itr=ccpidCol.iterator();itr.hasNext();){
				int id=((Integer)itr.next()).intValue();
				CsiCustProductInfo csiCustProductInfo=csiCustProductInfoHome.findByPrimaryKey(new Integer(id));
				csiCustProductInfo.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_CANCEL);
			}			
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"updateCsiCustProductInfo",e);
		    throw new ServiceException("更新客户预约产品信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"updateCsiCustProductInfo",e);
		    throw new ServiceException("更新客户预约产品信息时查找错误");
    	}		
	}
	public Collection createCsiCustProductInfo(Collection csiPackageArray,String detailNo,Collection csiCampaignArray,CustServiceInteractionDTO csiDTO,CommonBusinessParamDTO businessParamDTO)throws  ServiceException{
		Collection allCsiCustProductDTOList=new ArrayList();
		
        //----------2006/11/23-------yangchen---------start------
        //以下的处理由杨晨追加
        // 取得在检查设备和产品关联是设置的产品和设备映射成功CsiCustProductDTO
        // 是在BusinessRuleService.checkSelectTerminalDevice中设置的
        Collection haveDeviceProductCol=(Collection)serviceContext.get(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST);
        //----------2006/11/23-------yangchen---------end------
		
        //存放产品包对应的优惠和套餐
        Collection referCsiCampaignCol =new ArrayList();
		// 取得客户购买的产品包信息
		Iterator currentPackageIterator=csiPackageArray.iterator();
		while(currentPackageIterator.hasNext()){
			int packageid = ((Integer) currentPackageIterator.next()).intValue();	
			allCsiCustProductDTOList.addAll(createCsiCustProductInfo(packageid,detailNo,null,csiCampaignArray,csiDTO,haveDeviceProductCol,businessParamDTO,referCsiCampaignCol));	
		}
		
		// 将促销对套餐的优惠也记录到CsiProductInfo中
		try{
		   CsiCustProductInfoHome custHome = HomeLocater.getCsiCustProductInfoHome();
		   if (csiCampaignArray !=null && !csiCampaignArray.isEmpty()){
			  Iterator csiCampaignIter =csiCampaignArray.iterator();
			  while (csiCampaignIter.hasNext()){
				 Integer csiCampaignID=(Integer)csiCampaignIter.next();	
				 if (!referCsiCampaignCol.contains(csiCampaignID)){
					CsiCustProductInfoDTO custProductDto = new CsiCustProductInfoDTO();
		            custProductDto.setCsiID(csiDTO.getId());
		            custProductDto.setReferServiceAccountID((businessParamDTO ==null) ? 0 :businessParamDTO.getServiceaccountID());
		            custProductDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
		            custProductDto.setAction(getActionByCsitype(csiDTO.getType()));
		            custProductDto.setReferCampaignID(csiCampaignID.intValue());
		            CsiCustProductInfo custProduct = custHome.create(custProductDto);
		            custProductDto.setId(custProduct.getId().intValue());
		            allCsiCustProductDTOList.add(custProductDto);
				}
			}
		  }
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时创建错误");
		}
		
		
		return allCsiCustProductDTOList;
	}
	
	public Collection createCsiCustProductInfo(Collection csiPackageArray,String detailNo,Collection csiCampaignArray,CustServiceInteractionDTO csiDTO)throws  ServiceException{
		Collection allCsiCustProductDTOList=createCsiCustProductInfo(csiPackageArray,detailNo,csiCampaignArray,csiDTO,null);
		return allCsiCustProductDTOList;
	}
	private Collection createCsiCustProductInfo(int packageId,String detailNo,String contractNO,Collection csiCampaignArray,CustServiceInteractionDTO csiDTO,
			                                    Collection haveDeviceProductCol,CommonBusinessParamDTO businessParamDTO,Collection referCsiCampaignCol)
	 throws  ServiceException{
		try{
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createCsiCustProductInfo",new Integer(packageId));
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createCsiCustProductInfo",csiCampaignArray);
			Collection csiCustProductDTOList=new ArrayList();
            ProductHome productHome =HomeLocater.getProductHome();
            CsiCustProductInfoHome custHome = HomeLocater.getCsiCustProductInfoHome();
            int currentCampaign=0;
            // 判断团购券号是否为空
            if(detailNo==null ||"".equals(detailNo)){
            	if(csiCampaignArray!=null && !csiCampaignArray.isEmpty()){
            		// 取得产品包参与过的所有优惠
            		Collection campaignIDCol=BusinessUtility.getCampaignIDListByPackageID(packageId);
            		// 根据选择的优惠列表找到对应产品包的优惠id
            		Iterator currentCampaignIter=csiCampaignArray.iterator();  		
            		while(currentCampaignIter.hasNext()){
            			Integer currentCampaignID=(Integer)currentCampaignIter.next();	
            			if(campaignIDCol.contains(currentCampaignID)){
            				currentCampaign=currentCampaignID.intValue();
            				if (!referCsiCampaignCol.contains(currentCampaignID))
            					 referCsiCampaignCol.add(currentCampaignID);
            				break;
            			}
            		}
            	}
            }else{
            	// 在团购券的时候通过团购券取得优惠的id
            	GroupBargainDetailHome groupBargainDetailHome=HomeLocater.getGroupBargainDetailHome();
            	GroupBargainDetail groupBargainDetail=groupBargainDetailHome.findObjectByDetailNo(detailNo);
            	GroupBargainHome groupBargainHome=HomeLocater.getGroupBargainHome();
            	GroupBargain groupBargain=groupBargainHome.findByPrimaryKey(new Integer(groupBargainDetail.getGroupBargainID()));
            	currentCampaign=groupBargain.getCampaignId();
            }
            //----------2006/11/23-------yangchen---------start------
            //以下的处理由杨晨移除，通过参数引用传入，
            // 取得在检查设备和产品关联是设置的产品和设备映射成功CsiCustProductDTO
            // 是在BusinessRuleService.checkSelectTerminalDevice中设置的
            //Collection haveDeviceProductCol=(Collection)serviceContext.get(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST);
            //----------2006/11/23-------yangchen---------end------
            Collection colProductID = new ArrayList();
            
            System.out.println("businessParamDTO================"+businessParamDTO);          
            int serviceAccountID =0;
            if (businessParamDTO !=null){
               serviceAccountID =businessParamDTO.getServiceaccountID();
               if (businessParamDTO.getOptioProductSelectMap() !=null && !businessParamDTO.getOptioProductSelectMap().isEmpty())
            	   colProductID =(Collection)businessParamDTO.getOptioProductSelectMap().get(new Integer(packageId));
               if(colProductID==null || colProductID.isEmpty())
            	   colProductID=BusinessUtility.getProductIDsByPackageID(packageId);
            }
            else{        	
                colProductID = BusinessUtility.getProductIDsByPackageID(packageId);
            }
                        
            Iterator iteratorProductID = colProductID.iterator();
            while(iteratorProductID.hasNext()){
				int productId=((Integer) iteratorProductID.next()).intValue();
				CsiCustProductInfoDTO custProductDto = new CsiCustProductInfoDTO();
	            custProductDto.setCsiID(csiDTO.getId());
	            custProductDto.setAction(csiDTO.getType());
	            custProductDto.setProductID(productId);
	            custProductDto.setReferServiceAccountID(serviceAccountID);
	            custProductDto.setReferPackageID(packageId);
	            custProductDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
	            // 只有在门店开户，预约开户是，在检查产品和设备的关联过程中才设置的这个信息
	            if(haveDeviceProductCol!=null && !haveDeviceProductCol.isEmpty()){
		            // 取得产品的类型来判断是否是硬件产品
		            Product product=productHome.findByPrimaryKey(new Integer(productId));
		            if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
			            // 对取得的有设备的硬件产品循环找到对应的产品
			            Iterator haveDeviceProductIterator=haveDeviceProductCol.iterator();
			            while(haveDeviceProductIterator.hasNext()){
			            	CsiCustProductInfoDTO deviceProductDto=(CsiCustProductInfoDTO)haveDeviceProductIterator.next();
			            	if(productId==deviceProductDto.getProductID()){
			            		custProductDto.setReferDeviceID(deviceProductDto.getReferDeviceID());
			            		haveDeviceProductCol.remove(deviceProductDto);
			            		break;
			            	}
			            }
		            }else{
		            	custProductDto.setReferDeviceID(0);
		            }
	            }

				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GO.equals(csiDTO.getType()) ||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType()) ||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType())){
					// 定义动作
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_OPEN);
				// 新增产品
				}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType())||
						 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDTO.getType())){
					// 定义动作
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT);
				}
				
				// 设置团购和优惠的相关信息
				if(detailNo!=null && !"".equals(detailNo)){
	                custProductDto.setReferGroupBargainID(csiDTO.getGroupCampaignID());                
				}else{
					custProductDto.setReferGroupBargainID(0);   
				}	
				custProductDto.setReferCampaignID(currentCampaign);
/* 
				// 取得新客户信息,这个信息必须在前面调用该方法的入口方法里面设置
				NewCustomerInfoDTO newCustInfo=(NewCustomerInfoDTO)serviceContext.get(com.dtv.oss.service.Service.NEW_CUSTOMER_INFO_DTO);
				// 设置企业用户开户时使用的合同号
				if(newCustInfo!=null){
					custProductDto.setReferContractNo(newCustInfo.getContractNo());
				}
*/
				if(contractNO!=null && !"".equals(contractNO)){
					custProductDto.setReferContractNo(contractNO);
				}
				// 创建受理单涉及的客户产品相关信息
				CsiCustProductInfo custProduct = custHome.create(custProductDto);
				// 回置id
				custProductDto.setId(custProduct.getId().intValue());
	            csiCustProductDTOList.add(custProductDto);
			}
            return csiCustProductDTOList;
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时创建错误");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("新开户购买产品时查找错误");
    	}		
	}
	public void modifyAddCustomerProductCsiInfo(CustomerProductEJBEvent inEvent) throws ServiceException{
		updateCustServiceInteractionInfo(inEvent.getCsiDto(),inEvent.getActionType());
		delectOldCsiCustProductInfo(inEvent.getCsiDto());
		CommonBusinessParamDTO paramDto =new CommonBusinessParamDTO();
		paramDto.setServiceaccountID(inEvent.getSaID());
		createCsiCustProductInfo(inEvent.getCsiPackageArray(),null,inEvent.getCsiCampaignArray(),inEvent.getCsiDto(),paramDto);		
	}
	
	private String getActionByCsitype(String csiType){
		String action ="";
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiType)||
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiType)||
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiType)||
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GO.equals(csiType)){
				// 定义动作
			    action =CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_OPEN;
		 // 新增产品
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiType)||
				 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiType)){
				// 定义动作
				action =CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT;
		}
		return action;
	}
	/**
     *回收电话号码资源，建立使用日志
     * @throws ServiceException
     */
    private boolean releasePhoneNo(String phoneNo,int customerid,Integer operatorID, int saID) throws ServiceException{

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
				phoneUseDto.setOpID(operatorID.intValue());
				phoneUseDto.setPhoneNo(phone.getPhoneNo());
				
				OperatorHome homeOpe = HomeLocater.getOperatorHome();
				Operator operator = homeOpe.findByPrimaryKey(operatorID);
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
	 * 安装工单预约取消时退费
	 */
	public void returnFeeForCancel(CustServiceInteractionDTO csiDto,int operatorID) throws ServiceException {
		try{
			ServiceContext context = new ServiceContext();
			context.put(Service.OPERATIOR_ID,new Integer(operatorID));
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiDto.getType())||
			   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDto.getType())//||
			   //CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiDto.getType())//||
			  // CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDto.getType())|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDto.getType())
					){					    		
					LogUtility.log(clazz,LogLevel.DEBUG,"安装预约取消退费系统事件");
					int csiID =0;
					if (csiDto.getId()!= 0){
						csiID = csiDto.getId();
					}
					//事件号不知道，待商议
					SystemEventRecorder.AddEvent4Customer(0, csiDto.getCustomerID(),
							0, 0, csiID, getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);	        	
			}else{
				context.put(Service.CUSTOMER_ID,new Integer(csiDto.getCustomerID()));
				Collection col=BusinessUtility.getServiceAccountIDListByCsiID(csiDto.getId());
				ServiceAccountService serviceAccountService = new ServiceAccountService(context);
				Iterator it = col.iterator();
				while (it.hasNext()){
					Integer integer=(Integer) it.next();
					serviceAccountService.close(integer.intValue(),true);
				}
				CampaignService campaignService=new CampaignService(serviceContext);
				campaignService.cancelCustCampaignForNew(csiDto.getId());
				//获取参数并设置到ServiceContext对象中
				//安装工单取消退费（重用安装不成功退费）
				FeeService.returnFeeForFailureInInstallation(csiDto,operatorID);
			}
		}
    	
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"安装预约取消退费定位出错:"+e);
			throw new ServiceException("安装预约取消退费定位出错！");
		}
		catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.WARN,"创建系统日志出错:"+e2);
			throw new ServiceException("创建系统日志出错！");
		}		
	}   
	
	public void batchServiceAccountOperation( ServiceAccountEJBEvent event)throws  ServiceException{
	    // 取得受理单信息
	    CustServiceInteractionDTO csiDTO=event.getCsiDto();

     	Collection psidList=new ArrayList();
	    	
	   	// 创建受理单信息
	  	createCustServiceInteraction(csiDTO);
	   	// 在新客户信息账户信息之后需要在受理单中设置客户和账户id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		String[] serviceAcctStr =event.getServiceAcctIdStr();
	    for (int i=0;i<serviceAcctStr.length; i++){
	         int serviceAccountID =Integer.parseInt(serviceAcctStr[i]);
	         // 暂停
	         if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UP.equals(csiEJB.getType())){
	    	    // 取得业务账户下面所有的软件产品
	    	    psidList.addAll(BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL));
		     // 恢复
		     }else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UR.equals(csiEJB.getType())){
	  		    createCsiCustProductInfo(csiEJB,event.getColPsid());
	  		    return;
	         }
		}
	        
	     //  用户/暂停/复机
		 createCsiCustProductInfo(csiEJB,psidList);
     }
	
	
}
