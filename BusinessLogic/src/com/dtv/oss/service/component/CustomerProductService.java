/*
 * Created on 2005-9-21
 *
 * 客户产品业务组件，主要完成与客户产品相关的操作。
 */
package com.dtv.oss.service.component;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import java.util.*;

import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.dto.*;
import com.dtv.oss.dto.custom.CustCustomerBillingRuleDTO;
import com.dtv.oss.domain.*;
import com.dtv.oss.service.util.*;
import com.dtv.oss.log.*;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.factory.*;
import com.dtv.oss.util.TimestampUtility;

/**
 * @author Leon Liu
 *
 * 处理与客户产品相关的业务组件
 */
public class CustomerProductService extends AbstractService {
    private static final Class clazz = CustomerProductService.class;

    private ServiceContext context;
    private int customerID;
    private int accountId;
    private int saID;
    private int targetSaID;
    private List devicelist=new ArrayList();

	private Collection customerProductList=null;
    private HashMap deviceMap=null;
    //客户化计费规则 add by yangchen 2008/07/23
    private Map customerBillingRuleMap; 
    
    public CustomerProductService() {};
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
    
    public CustomerProductService(ServiceContext context){
    	super();
        this.context = context;
        customerProductList=new ArrayList();
        deviceMap=new HashMap();
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
	
	 public int getTargetSaID() {
			return targetSaID;
		}

		public void setTargetSaID(int targetSaID) {
			this.targetSaID = targetSaID;
		}
	/**
	 * 构造函数
     * @param context
     * @param customerID
     * @param accountId
     * @param saID
     */
    public CustomerProductService(ServiceContext context, int customerID,int accountid, int said) {
        super();
        this.context = context;
        this.customerID = customerID;
        accountId = accountid;
        saID = said;
        
        customerProductList=new ArrayList();
        deviceMap=new HashMap();
        setOperatorID(PublicService.getCurrentOperatorID(context));
    }


    public ArrayList createCustomerProductWithBatchBuy(Collection productList, int saID,
    		int customerID,int accountID, Map productPropertyMap,String status,int groupNo,int sheafNo) throws ServiceException {

    	LogUtility.log(clazz,LogLevel.DEBUG,"进入创建客户产品程序");
    	LogUtility.log(clazz,LogLevel.DEBUG,"传入的参数：产品["+productList+"]、业务帐户["+saID+"]、" +
    			"客户id[" + customerID + "] 帐户ID[" + accountID +"]");

    	//侯07-4-11增加返回值,用于套餐转换.
    	ArrayList cpList=new ArrayList();
    	
    	this.deviceMap.clear();
    	this.customerProductList.clear();

    	Iterator itProduct=productList.iterator();
    	Collection productIDList=new ArrayList();
    	while(itProduct.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itProduct.next();
    		productIDList.add(new Integer(cpDTO.getProductID()));
    	}
        Collection csiProductInfoList = null;
        CustServiceInteraction csi = (CustServiceInteraction)context.get(Service.CSI);


        int csiid = csi.getId().intValue();
        try {
        	CsiCustProductInfoHome csiCustProductHome = HomeLocater.getCsiCustProductInfoHome();
        	csiProductInfoList = csiCustProductHome.findByCSIIDAndBuyGroup(csiid,groupNo,sheafNo);
        	LogUtility.log(clazz, LogLevel.DEBUG,"创建客户产品时csicustproductinfolist,groupNo:"+groupNo+"sheafNo:"+sheafNo);
        }catch(Exception e) {
        	LogUtility.log(clazz, LogLevel.ERROR, e);
        	throw new ServiceException("查找受理单相关产品错误，csiid-" + csiid);
        }

        //查找出产品中的硬件设备,并进行流转
        Collection deviceIDList=new ArrayList();
        Iterator itCSIProductInfo=csiProductInfoList.iterator();
        while(itCSIProductInfo.hasNext()){
        	CsiCustProductInfo csiProduct = (CsiCustProductInfo)itCSIProductInfo.next();
    		LogUtility.log(clazz,LogLevel.DEBUG,"受理单客户产品，产品ID= "+csiProduct.getCustProductID());
        	if(csiProduct.getReferDeviceID()>0 && productIDList.contains(new Integer(csiProduct.getProductID()))){
        		LogUtility.log(clazz,LogLevel.DEBUG,"找到一个硬件设备，设备ID= "+csiProduct.getReferDeviceID());
        		deviceMap.put(new Integer(csiProduct.getProductID()),new Integer(csiProduct.getReferDeviceID()));
        		deviceIDList.add(new Integer(csiProduct.getReferDeviceID()));
        		/*****************************add by yangchen 2008/06/20 start ***************************************/
        		TerminalDeviceDTO deviceForRelease=BusinessUtility.getDeviceByDeviceID(csiProduct.getReferDeviceID());
        		if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceForRelease.getDeviceClass())
        		   || "Y".equals(deviceForRelease.getPreAuthorization()) //add by david.Yang 用于机卡一体的设备	
        		){
        			DeviceStockService deviceStockService=new DeviceStockService(context);
        			deviceStockService.loadDeviceList(csiProduct.getReferDeviceID()); //add by david.Yang 清楚设备的预授权标志
        			deviceStockService.devPreauthTrans(csiProduct.getReferDeviceID());
        		}
        		/*****************************add by yangchen 2008/06/20 end ***************************************/
        	}
        }
        //进行设备流转
        try{
        	//流转
        	String csi_Type=(String)context.get(Service.CSI_TYPE);
        	boolean useFlag =false;
        	if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csi_Type)
        		|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csi_Type)){
        		useFlag =true;
        	}
        	
        	deviceTransitionForCPop(deviceIDList,customerID,
        			((Integer)context.get(Service.OPERATIOR_ID)).intValue(),0,
        			CommonConstDefinition.DEVICE_TRANSACTION_ACTION_P,useFlag);
        }catch(ServiceException e){
        	throw new ServiceException(e.getMessage());
        }catch(Exception e){
        	LogUtility.log(clazz,LogLevel.WARN,"硬件设备流转失败"+e);
        	throw new ServiceException("硬件设备流转失败");
        }
        //维护设备配对关系.
        deviceMatch(deviceIDList);
     
    	//开始创建客户产品
    	Iterator itCP=productList.iterator();
    	HashMap custAndCsiProdMap=new HashMap();
    	HashMap campaignAndCcidMap=new HashMap();
    	while(itCP.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itCP.next();
    		try{
    			//创建客户产品
    			CustomerProduct custProduct=createCustProductByCsiCustProductInfo(cpDTO,csiProductInfoList,status,custAndCsiProdMap);
            	cpList.add(custProduct);
    			//开户、新增业务帐户时设置设备的设备来源
    			if(custProduct.getDeviceID()!=0)
    				custProduct.setDeviceProvideFlag(CommonConstDefinition.CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_B);
    			CsiCustProductInfo csiProductLocal = (CsiCustProductInfo)custAndCsiProdMap.get(custProduct.getPsID());
           	
                //创建产品属性信息
                LogUtility.log(clazz,LogLevel.DEBUG,"创建产品属性信息");
                
                //productPropertyMap为全部提交 的产品属性map,以productID为KEY
                if (productPropertyMap != null && productPropertyMap.isEmpty() == false) {
                    LogUtility.log(clazz,LogLevel.DEBUG,"在创建一个产品属性信息时productPropertyMap\n："+productPropertyMap);
                    if(productPropertyMap.containsKey(Integer.toString(cpDTO.getProductID())))
                	{
                        //tempMap为当前产品对应的propertyMap,以propertyCode为KEY
                		Map tempMap = (Map)productPropertyMap.get(Integer.toString(cpDTO.getProductID()));
                        if (tempMap != null && tempMap.isEmpty() == false) {
                        	
                			Set keySet = tempMap.keySet();
                			Iterator itPropertyCode = keySet.iterator();
                			CpConfigedPropertyHome cpPropertyHome = HomeLocater.getCpConfigedPropertyHome();
                			
                			while(itPropertyCode.hasNext())
                			{
                				CpConfigedPropertyDTO dto = new CpConfigedPropertyDTO();
                				String propertyCode = (String) itPropertyCode.next();
                				
                				dto.setPropertyCode(propertyCode);
                				dto.setPropertyValue((String)tempMap.get(propertyCode));
                				dto.setPsID(custProduct.getPsID().intValue());
                				dto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
                				dto.setStatusTime(TimestampUtility.getCurrentTimestamp());
                				dto.setDtCreate(TimestampUtility.getCurrentTimestamp());
                				dto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
                				LogUtility.log(clazz,LogLevel.DEBUG,"将要写入的CpConfigedPropertyDTO为："+dto);
                                cpPropertyHome.create(dto);
  
                			}
                		}
                	}
                }
    		}
    		catch(Exception e){
    			e.printStackTrace();
    			LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("创建客户产品记录出现错误");
    		}
    	}
    	

    	//把客户产品放入ServiceTcontext里
    	
    	context.put(Service.CUSTOMER_PRODUCT_LIST,customerProductList);
    	//给linkToDevice1,linkToDevice2赋值
    	try{
    		productLinkToDevice();
    	}
    	catch(Exception e){
    		LogUtility.log(clazz,LogLevel.WARN,"软件产品连接到硬件产品出错，"+e);
    		throw new ServiceException("软件产品连接到硬件产品出错");
    	}
    	//创建系统事件
    	try{
    		createSystemEventRecord(customerID,accountID,saID,csiid);
    	}
    	catch(Exception e){
    		LogUtility.log(clazz,LogLevel.WARN,"创建系统事件失败，错误如下："+e);
    		throw new ServiceException("创建系统事件失败，错误如下："+e);
    	}
    	LogUtility.log(clazz,LogLevel.DEBUG,"结束创建客户产品程序");
    	return cpList;
    }
    /**
     * 创建客户产品,仅用于新增产品的时候使用
     * @param productList
     * @param campaignList
     * @param saID
     * @param customerID
     * @param accountID
     * @param packageCampaignMap
     * @param productPropertyMap
     * @throws ServiceException
     */
    public void create(Collection productList, Collection campaignList,int saID,
    		int customerID,int accountID,Map packageCampaignMap, Map productPropertyMap) throws ServiceException {
    	create(productList,saID,customerID,accountID,productPropertyMap,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    	
    }
    
    /**
     * 用于批量增加产品包的创建,add.by david.Yang
     * @param productList
     * @param saID
     * @param customerID
     * @param accountID
     * @param status
     * @throws ServiceException
     */
    public void create(Collection productList, int accountID, String status,int groupNo,int sheafNo) throws ServiceException {
    	LogUtility.log(clazz,LogLevel.DEBUG,"进入创建客户产品程序");
    	
    	this.deviceMap.clear();
    	this.customerProductList.clear();

    	Iterator itProduct=productList.iterator();
    	Collection productIDList=new ArrayList();
    	while(itProduct.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itProduct.next();
    		productIDList.add(new Integer(cpDTO.getProductID()));
    	}

        Collection csiProductInfoList = null;
        CustServiceInteraction csi = (CustServiceInteraction)context.get(Service.CSI);

        HashMap custAndCsiProdMap=new HashMap();
        int csiid = csi.getId().intValue();
        try {
        	CsiCustProductInfoHome csiCustProductHome = HomeLocater.getCsiCustProductInfoHome();
        	csiProductInfoList = csiCustProductHome.findByCSIIDAndBuyGroup(csiid,groupNo,sheafNo);
        }catch(Exception e) {
        	LogUtility.log(clazz, LogLevel.ERROR, e);
        	throw new ServiceException("查找受理单相关产品错误，csiid-" + csiid);
        }
         //开始创建客户产品
    	Iterator itCP=productList.iterator();
    	while(itCP.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itCP.next();
    		try{
    			//创建客户产品
    			CustomerProduct custProduct=createCustProductByCsiCustProductInfo(cpDTO,csiProductInfoList,status,custAndCsiProdMap);
    		} catch(Exception e){
        		e.printStackTrace();
        		LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("创建客户产品记录出现错误");
        	}
    	}	
    	
    	context.put(Service.CUSTOMER_PRODUCT_LIST,customerProductList);
    	//给linkToDevice1,linkToDevice2赋值
    	try{
    		productLinkToDevice();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		LogUtility.log(clazz,LogLevel.WARN,"软件产品连接到硬件产品出错，"+e);
    		throw new ServiceException("软件产品连接到硬件产品出错");
    	}
    	//创建系统事件
    	 try{
    	   	createSystemEventRecord(customerID,accountID,saID,csiid);
    	 }
    	 catch(Exception e){
    	  	LogUtility.log(clazz,LogLevel.WARN,"创建系统事件失败，错误如下："+e);
    	   	throw new ServiceException("创建系统事件失败，错误如下："+e);
    	 }
    	 LogUtility.log(clazz,LogLevel.DEBUG,"结束创建客户产品程序");	
 
    }
    
    
    /**
     * 创建客户产品(用于客户开户、新增订户，在新增产品时不能直接使用，必须调用在其上封装的方法)
     * @param productList ：格式为CustomerProductDTO的List
     * @param campaignList  ：
     * @param saID ：业务帐户ID
     * @param customerID ： 客户ID
     * @param accountID ：帐户ID
     * @param packageCampaignMap  ：产品包对应的优惠
     * @throws ServiceException
     */
    public ArrayList create(Collection productList, int saID,
    		int customerID,int accountID, Map productPropertyMap,String status) throws ServiceException {

    	LogUtility.log(clazz,LogLevel.DEBUG,"进入创建客户产品程序");
    	LogUtility.log(clazz,LogLevel.DEBUG,"传入的参数：产品["+productList+"]、业务帐户["+saID+"]、" +
    			"客户id[" + customerID + "] 帐户ID[" + accountID +"]");

    	//侯07-4-11增加返回值,用于套餐转换.
    	ArrayList cpList=new ArrayList();
    	
    	this.deviceMap.clear();
    	this.customerProductList.clear();

    	Iterator itProduct=productList.iterator();
    	Collection productIDList=new ArrayList();
    	while(itProduct.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itProduct.next();
    		productIDList.add(new Integer(cpDTO.getProductID()));
    	}
/*
        boolean existCampaign = true;
        HashSet campaignSet = new HashSet();
        HashMap campaignMap = new HashMap();
*/
        Collection csiProductInfoList = null;
		//----------yangchen-----------2006/11/23-----------start---------------
        //受理单类型和安装类型
/*        String csiType = "";
        String installationType="";

        //确定是否有优惠
        if (campaignList == null || campaignList.isEmpty())
        	existCampaign = false;
*/
        CustServiceInteraction csi = (CustServiceInteraction)context.get(Service.CSI);


        int csiid = csi.getId().intValue();
/*        installationType=csi.getInstallationType();
        if(context.get(Service.CSI_TYPE)==null)
        	csiType=csi.getType();
        else
        	csiType = (String)context.get(Service.CSI_TYPE);*/
		//----------yangchen-----------2006/11/23-----------end---------------
        try {
        	CsiCustProductInfoHome csiCustProductHome = HomeLocater.getCsiCustProductInfoHome();
        	csiProductInfoList = csiCustProductHome.findByCSIID(csiid);
        }catch(Exception e) {
        	LogUtility.log(clazz, LogLevel.ERROR, e);
        	throw new ServiceException("查找受理单相关产品错误，csiid-" + csiid);
        }
        
        //查找出产品中的硬件设备,并进行流转
        Collection deviceIDList=new ArrayList();
        Iterator itCSIProductInfo=csiProductInfoList.iterator();
        while(itCSIProductInfo.hasNext()){
        	CsiCustProductInfo csiProduct = (CsiCustProductInfo)itCSIProductInfo.next();
        	if(csiProduct.getReferDeviceID()>0 && productIDList.contains(new Integer(csiProduct.getProductID()))){
        		LogUtility.log(clazz,LogLevel.DEBUG,"找到一个硬件设备，设备ID= "+csiProduct.getReferDeviceID());
        		deviceMap.put(new Integer(csiProduct.getProductID()),new Integer(csiProduct.getReferDeviceID()));
        		deviceIDList.add(new Integer(csiProduct.getReferDeviceID()));
        		/*****************************add by yangchen 2008/06/20 start ***************************************/
        		TerminalDeviceDTO deviceForRelease=BusinessUtility.getDeviceByDeviceID(csiProduct.getReferDeviceID());
        	
        		if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceForRelease.getDeviceClass())
        				|| "Y".equals(deviceForRelease.getPreAuthorization()) //add by david.Yang 用于机卡一体的设备		
        		){
        			DeviceStockService deviceStockService=new DeviceStockService(context);
        			devicelist=deviceStockService.loadDeviceList(csiProduct.getReferDeviceID());
        			deviceStockService.devPreauthTrans(csiProduct.getReferDeviceID());
        		}
        		/*****************************add by yangchen 2008/06/20 end ***************************************/
        	}
        }
        
    	//在设备流转前,维护设备配对关系 2007-12-21
        try{
        	Collection deviceIDListMatch=new ArrayList();
        	deviceIDListMatch.addAll(deviceIDList);
        	if(deviceIDListMatch.size()==1){   //补卡时，把已经存在的机顶盒deviceID加进来,用于维护配对关系
        		deviceIDListMatch.addAll(BusinessUtility.getDeviceListBySaID(saID));
        	}
        	deviceMatch(deviceIDListMatch);
        }catch(Exception e){
        	e.printStackTrace();
        	LogUtility.log(clazz,LogLevel.WARN,"维护设备配对关系失败"+e.getMessage());
        	throw new ServiceException("维护设备配对关系失败");
        }
        
        //进行设备流转
        try{
        	//流转
        	String csi_Type=(String)context.get(Service.CSI_TYPE);
        	boolean useFlag =false;
        	if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csi_Type)
        		|| CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csi_Type)){
        		useFlag =true;
        	}
        	deviceTransitionForCPop(deviceIDList,customerID,
        			((Integer)context.get(Service.OPERATIOR_ID)).intValue(),0,
        			CommonConstDefinition.DEVICE_TRANSACTION_ACTION_P,useFlag);
        }
        catch(Exception e){
        	e.printStackTrace();
        	LogUtility.log(clazz,LogLevel.WARN,"硬件设备流转失败"+e);
        	throw new ServiceException("硬件设备流转失败");
        }
        
     
    	//开始创建客户产品
    	Iterator itCP=productList.iterator();
    	HashMap custAndCsiProdMap=new HashMap();
    	HashMap campaignAndCcidMap=new HashMap();
    	while(itCP.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itCP.next();
    		try{
    			//创建客户产品
    			CustomerProduct custProduct=createCustProductByCsiCustProductInfo(cpDTO,csiProductInfoList,status,custAndCsiProdMap);
            	cpList.add(custProduct);
    			//开户、新增业务帐户时设置设备的设备来源
    			if(custProduct.getDeviceID()!=0)
    				custProduct.setDeviceProvideFlag(CommonConstDefinition.CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_B);
    			CsiCustProductInfo csiProductLocal = (CsiCustProductInfo)custAndCsiProdMap.get(custProduct.getPsID());
           	
                //创建产品属性信息
                LogUtility.log(clazz,LogLevel.DEBUG,"创建产品属性信息");
                
                //productPropertyMap为全部提交 的产品属性map,以productID为KEY
                if (productPropertyMap != null && productPropertyMap.isEmpty() == false) {
                    LogUtility.log(clazz,LogLevel.DEBUG,"在创建一个产品属性信息时productPropertyMap\n："+productPropertyMap);
                    if(productPropertyMap.containsKey(Integer.toString(cpDTO.getProductID())))
                	{
                        //tempMap为当前产品对应的propertyMap,以propertyCode为KEY
                		Map tempMap = (Map)productPropertyMap.get(Integer.toString(cpDTO.getProductID()));
                        if (tempMap != null && tempMap.isEmpty() == false) {
                        	
                			Set keySet = tempMap.keySet();
                			Iterator itPropertyCode = keySet.iterator();
                			CpConfigedPropertyHome cpPropertyHome = HomeLocater.getCpConfigedPropertyHome();
                			
                			while(itPropertyCode.hasNext())
                			{
                				CpConfigedPropertyDTO dto = new CpConfigedPropertyDTO();
                				String propertyCode = (String) itPropertyCode.next();
                				
                				dto.setPropertyCode(propertyCode);
                				dto.setPropertyValue((String)tempMap.get(propertyCode));
                				dto.setPsID(custProduct.getPsID().intValue());
                				dto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
                				dto.setStatusTime(TimestampUtility.getCurrentTimestamp());
                				dto.setDtCreate(TimestampUtility.getCurrentTimestamp());
                				dto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
                				LogUtility.log(clazz,LogLevel.DEBUG,"将要写入的CpConfigedPropertyDTO为："+dto);
                                cpPropertyHome.create(dto);
  
                			}
                		}
                	} 
                }
                /*********************add by yangchen 2008/07/23 start***************************************************/
				if(customerBillingRuleMap!=null && !customerBillingRuleMap.isEmpty()){
					CustCustomerBillingRuleDTO billRuleDTO=(CustCustomerBillingRuleDTO)customerBillingRuleMap.get(new Integer(custProduct.getProductID()));
					if(billRuleDTO!=null){
						CustomerBillingRuleService billRuleService=new CustomerBillingRuleService(context);
						billRuleDTO.setCustPackageID(custProduct.getReferPackageID());
						billRuleDTO.setPsID(custProduct.getPsID().intValue());
						billRuleDTO.setReferID(csi.getId().intValue());
						//由于数据模型没有定义，先设置这个值
						billRuleDTO.setReferType("M");
						billRuleDTO.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
						billRuleDTO.setComments("购买时指定生成");
						billRuleService.createCustBillRuleServiceForOpen(billRuleDTO);
					}
				}
			    /*********************add by yangchen 2008/07/23 end***************************************************/
    		}
    		catch(Exception e){
    			e.printStackTrace();
    			LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("创建客户产品记录出现错误");
    		}
    	}
    	

    	//把客户产品放入ServiceTcontext里
    	
    	context.put(Service.CUSTOMER_PRODUCT_LIST,customerProductList);
    	//给linkToDevice1,linkToDevice2赋值
    	try{
    		productLinkToDevice();
    	}
    	catch(Exception e){
    		LogUtility.log(clazz,LogLevel.WARN,"软件产品连接到硬件产品出错，"+e);
    		throw new ServiceException("软件产品连接到硬件产品出错");
    	}
    	//创建系统事件
    	try{
    		createSystemEventRecord(customerID,accountID,saID,csiid);
    	}
    	catch(Exception e){
    		LogUtility.log(clazz,LogLevel.WARN,"创建系统事件失败，错误如下："+e);
    		throw new ServiceException("创建系统事件失败，错误如下："+e);
    	}
    	LogUtility.log(clazz,LogLevel.DEBUG,"结束创建客户产品程序");
    	return cpList;
    }
    
    /**
     * 暂停客户产品，
     * @param custProductIDList：封装格式为productID 的 Integer对象
     * @throws ServiceException
     */
    public ArrayList pauseCustomerProduct(Collection custProductIDList) throws ServiceException {
    	//更新产品状态、记录事件、日志
    	ArrayList list=updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,
    			SystemEventRecorder.SE_CUSTPROD_PAUSE,"产品暂停","产品暂停，客户产品ID：");
    	
    	Iterator itCP=custProductIDList.iterator();
		while(itCP.hasNext()){
			int cpID=((Integer)itCP.next()).intValue();
			//修改客户产品属性状态
			updateCPPropertyStatus(cpID,CommonConstDefinition.GENERALSTATUS_INVALIDATE);
		}
		return list;
    }

    /**
     * 恢复客户产品
     * @param custProductIDList : 封装格式为productID 的 Integer对象
     * @throws ServiceException
     */
    public void resumeCustomerProduct(Collection custProductIDList) throws ServiceException {
    	//更新产品状态、记录事件、日志
    	updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,
    			SystemEventRecorder.SE_CUSTPROD_RESUME,"产品恢复","产品恢复，客户产品ID：");
    	
    	Iterator itCP=custProductIDList.iterator();
		while(itCP.hasNext()){
			int cpID=((Integer)itCP.next()).intValue();
			//修改客户产品属性状态
			updateCPPropertyStatus(cpID,CommonConstDefinition.GENERALSTATUS_VALIDATE);
		}
    }

    /**
     * 仅仅取消用户产品：记录相关事件，取消产品相关促销，修改产品状态
     * @param custProductIDList
     * @throws ServiceException
     */
/*    
    public void cancelcustProductOnly(Collection custProductIDList)throws ServiceException{
    	//更新产品状态、记录事件、日志
    	updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,
    			SystemEventRecorder.SE_CUSTPROD_CANCEL,"产品取消","产品取消，客户产品ID：");
    	
    	Iterator itCP=custProductIDList.iterator();
		while(itCP.hasNext()){
			int cpID=((Integer)itCP.next()).intValue();
			//对产品级别的操作不影响到客户促销,所以直接注释掉----杨晨 2007/3/21 
			//修改优惠状态
			//updateCampaign(cpID,CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_INVALID,false);
			
			//修改客户产品属性状态
			updateCPPropertyStatus(cpID,CommonConstDefinition.GENERALSTATUS_INVALIDATE);
		}
    }
*/
    /**
     * 2007-2-27增加的重载方法,用于区分在取消产品时硬件设备是否需要进行流转.
     * @param custProductIDList
     * @throws ServiceException
     */
/*
    public void cancelCustomerProduct(Collection custProductIDList) throws ServiceException {
    	cancelCustomerProduct(custProductIDList,true);
    }
*/
    /**
     * 取消用户产品：记录相关事件，进行设备流转，取消产品相关促销，修改产品状态
     * @param custProductIDList：custProductIDList为封装CPID的Integer对象
     * @throws ServiceException
     */
    public void moveCustomerProduct(Collection custProductIDList,boolean returnDevice) throws ServiceException {
    	//更新产品状态、记录事件、日志   
    	ArrayList  custproductList=updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_MOVE,
    			SystemEventRecorder.SE_CUSTPROD_CANCEL,"产品取消","产品取消，客户产品ID：");
    	           updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_MOVE,
    			SystemEventRecorder.SE_CUSTPROD_PURCHASE,"产品购买","产品购买，客户产品ID：");
    	context.put(Service.CUSTOMER_PRODUCT_LIST,custproductList);
    }
    /**
     * 取消用户产品：记录相关事件，进行设备流转，取消产品相关促销，修改产品状态
     * @param custProductIDList：custProductIDList为封装CPID的Integer对象
     * @throws ServiceException
     */
    public void cancelCustomerProduct(Collection custProductIDList,boolean returnDevice) throws ServiceException {
    	//更新产品状态、记录事件、日志
    	ArrayList  custproductList=updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,
    			SystemEventRecorder.SE_CUSTPROD_CANCEL,"产品取消","产品取消，客户产品ID：");

    	context.put(Service.CUSTOMER_PRODUCT_LIST,custproductList);
    	//硬件设备ID
    	Collection deviceIDList=new ArrayList();

    	//取消相关产品促销
    	try{
    		//Iterator itCP=custProductIDList.iterator();
    		Iterator itCP=custproductList.iterator();
    		CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
    		CustomerProduct cp=null;
    		int serviceAccountID=0;
        	while(itCP.hasNext()){
        		//int cpID=((Integer)itCP.next()).intValue();
        		//cp=cpHome.findByPrimaryKey(new Integer(cpID));
        		cp=(CustomerProduct)itCP.next();
        		if(cp.getDeviceID()>0){
            		if(cp.getServiceAccountID()!=0)
            			serviceAccountID=cp.getServiceAccountID();
        			deviceIDList.add(new Integer(cp.getDeviceID()));
        		}
        		//修改客户产品属性状态
    			updateCPPropertyStatus(cp.getPsID().intValue(),CommonConstDefinition.GENERALSTATUS_INVALIDATE);
        	}
        	//侯2007-2-27添加内容,用于取消一组客户产品时,同时修改被取消硬件产品的linktodevice1/linktodevice2
        	if (serviceAccountID != 0) {
				Collection allCp = cpHome
						.findByServiceAccountID(serviceAccountID);
				Iterator allCpit = allCp.iterator();
				while (allCpit.hasNext()) {
					cp = (CustomerProduct) allCpit.next();
					Integer link1 = new Integer(cp.getLinkToDevice1());
					if (deviceIDList.contains(link1))
						cp.setLinkToDevice1(0);
					Integer link2 = new Integer(cp.getLinkToDevice2());
					if (deviceIDList.contains(link2))
						cp.setLinkToDevice2(0);
					if (deviceIDList.contains(link1)||deviceIDList.contains(link2)){
						cp.setDtLastmod(TimestampUtility.getCurrentDate());
					}
				}
			}
        	//侯2007-2-27修改内容,进行设备流转
        	if(returnDevice){
        		transferDevice(deviceIDList,0);
        	}else{
        		//不流转的时候修改设备的状态
        		//Iterator itDeviceID=deviceIDList.iterator();
            	//while(itDeviceID.hasNext()){
            	//	int deviceID=((Integer)itDeviceID.next()).intValue();
            	//	TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
                //	TerminalDevice terminalDevice=terminalDeviceHome.findByPrimaryKey(new Integer(deviceID));
                //	terminalDevice.setStatus(CommonConstDefinition.DEVICE_STATUS_OFFLINE);
                //	terminalDevice.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        		try{
        			if(deviceIDList!=null && deviceIDList.size()>0)
        				deviceTransitionForCPop(deviceIDList,this.customerID,getOperatorID(),
        						1,CommonConstDefinition.DEVICE_TRANSACTION_ACTION_J,true,CommonConstDefinition.DEVICE_STATUS_OFFLINE);
        		}
        		catch(Exception e){
        			LogUtility.log(clazz,LogLevel.WARN,"创建设备流转出错！");
            		throw new ServiceException("创建设备流转出错！");
        		}
            }
    	}
    	catch(HomeFactoryException e1){
    		LogUtility.log(clazz,LogLevel.WARN,"客户产品定位出错！");
    		throw new ServiceException("客户产品定位出错！");
    	}
    	catch(FinderException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"客户产品查找出错！");
    		throw new ServiceException("客户产品查找出错，请确定产品是否有效！");
    	}

    	
    }

    /**
     * 根据actionType更改客户产品信息
     * 支持的actionType为EJBEvent.PAYBYBILL、EJBEvent.REGISTER_INSTALLATION_INFO
     * 当actionType为EJBEvent.PAYBYBILL时，csiID不会使用
     * 当actionType为EJBEvent.REGISTER_INSTALLATION_INFO，csiID为受理单ID
     * @param csiID
     * @param actionType
     */
    public void updateCustomerProduct(int csiID, int actionType)
			throws ServiceException {

		if (csiID == 0 || actionType == 0)
			throw new ServiceException("更新客户产品参数错误!");

		// 用于修改业务帐户状态用
		HashSet saSet = new HashSet();
		// 客户产品列表
		Collection productList = null;
		// 客户产品
		CustomerProductHome cpHome = null;
		// 业务帐户
		ServiceAccountHome saHome = null;
		ServiceAccount sa = null;
		CustomerProduct cp = null;

		try {
			// 当actionType为EJBEvent.REGISTER_INSTALLATION_INFO，csiID为受理单ID，
			// (录入安装信息),如果成功,修改客户产品状态为正常,同时修改该产品下的业务帐户的状态为正常
			if (actionType == EJBEvent.REGISTER_INSTALLATION_INFO) {

				CsiCustProductInfoHome csiCustProductHome = HomeLocater
						.getCsiCustProductInfoHome();
				cpHome = HomeLocater.getCustomerProductHome();
				saHome = HomeLocater.getServiceAccountHome();

				productList = csiCustProductHome.findByCSIID(csiID);
				Iterator itcsiProductInfo = productList.iterator();
				saSet.clear();

				while (itcsiProductInfo.hasNext()) {
					CsiCustProductInfo csiCP = (CsiCustProductInfo) itcsiProductInfo
							.next();
					if (csiCP.getCustProductID() == 0)
						continue;
					cp = cpHome.findByPrimaryKey(new Integer(csiCP
							.getCustProductID()));
					cp
							.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
					cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
					cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());

					// 修改业务帐户为正常
					if (saSet.add(new Integer(cp.getServiceAccountID()))) {
						sa = saHome.findByPrimaryKey(new Integer(cp
								.getServiceAccountID()));
						sa
								.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
						sa.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					}
				}
			}

//			// 当actionType为EJBEvent.PAYBYBILL时，修改客户该帐户下的为系统暂停的产品的状态，改为正常，并修改相关业务帐户为正常
//			if (actionType == EJBEvent.PAYBYBILL) {
//
//				if (this.accountId == 0)
//					throw new ServiceException("参数错误：用户帐户不存在！");
//
//				cpHome = HomeLocater.getCustomerProductHome();
//				saHome = HomeLocater.getServiceAccountHome();
//
//				productList = cpHome.findByAccountID(this.accountId);
//
//				saSet.clear();
//
//				Iterator itCPList = productList.iterator();
//				while (itCPList.hasNext()) {
//					cp = (CustomerProduct) itCPList.next();
//
//					// 如果是系统暂停，则恢复正常
//					if (CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND
//							.equals(cp.getStatus())) {
//						cp
//								.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
//						cp.setActivatetime(TimestampUtility
//								.getCurrentTimestamp());
//					}
//					// 修改业务帐户为正常,侯2008-1-2,只有业务帐户 为系统暂停,才恢复正常.
//					if (saSet.add(new Integer(cp.getServiceAccountID()))) {
//						sa = saHome.findByPrimaryKey(new Integer(cp
//								.getServiceAccountID()));
//						LogUtility.log(clazz, LogLevel.DEBUG, "更新业务帐户状态！"
//								+ sa.getServiceAccountID());
//						LogUtility.log(clazz, LogLevel.DEBUG, "业务帐户状态1"
//								+ sa.getStatus());
//						if (CommonConstDefinition.SERVICEACCOUNT_STATUS_SYSTEMSUSPEND
//								.equals(sa.getStatus())) {
//							sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
//							sa.setDtLastmod(TimestampUtility
//									.getCurrentTimestamp());
//							LogUtility.log(clazz, LogLevel.DEBUG, "业务帐户状态2"
//									+ sa.getStatus());
//						}
//					}
//				}
//			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "更改客户产品信息出错，原因：客户产品定位出错！");
			throw new ServiceException("更改客户产品信息出错，原因：客户产品定位出错！");
		} catch (FinderException e1) {
			LogUtility.log(clazz, LogLevel.WARN, "更改客户产品信息出错，原因：客户产品查找出错！");
			throw new ServiceException("更改客户产品信息出错，原因：客户产品查找出错！");
		}
	}

    /**
     * 帐户支付时,更新客户产品和业务帐户 状态 ,及生成相关事件.此方法只适用在帐单状态变更时使用.
	 * 根据actionTtype对psIDList进行相关操作
	 * 侯08-1-7增加对业务帐户 相关操作.
	 * @param psIDList
	 * @param actionType
	 * @throws ServiceException
	 */
    public static void updateCustomerProduct(Collection psIDList,int actionType,int operatorID) throws ServiceException{
//    	if(psIDList==null || psIDList.isEmpty() || actionType==0)
    	//07-2-05侯修改,这里的操作不关actionType的事,所以不做必要检查.
        if(psIDList==null || psIDList.isEmpty())
    		return;
        
        ServiceAccountHome saHome = null;
        ServiceAccount sa = null;
    	CustomerProductHome cpHome=null;
    	CustomerProduct cp=null;
    	HashSet saSet = new HashSet();
    	HashMap saSCDeviceIDMap=new HashMap();
    	HashMap saSCSerialNoMap=new HashMap();
    	HashMap saSTBDeviceIDMap=new HashMap();
    	HashMap saSTBSerialNoMap=new HashMap();
   		try{
    		cpHome=HomeLocater.getCustomerProductHome();
    		saHome = HomeLocater.getServiceAccountHome();
    		
    		Iterator itPSID=psIDList.iterator();
    		while(itPSID.hasNext()){
    			 Integer psID=(Integer)itPSID.next();
    			 cp=cpHome.findByPrimaryKey(psID);
    			 Integer said=new Integer(cp.getServiceAccountID());
    			 saSet.add(said);
    			 if (cp.getStatus().equals(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND)){
    				 int linkToDevice1=cp.getLinkToDevice1();
    					int linkToDevice2=cp.getLinkToDevice2();

    					//得到客户设备
    					String device1SerialNo="";
    					String device2SerialNo="";
    					if(linkToDevice1>0){
    						device1SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice1));
    					}
    					if(linkToDevice2>0){
    						device2SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice2));
    					}
    					saSTBDeviceIDMap.put(said, new Integer(linkToDevice1));
    					saSCDeviceIDMap.put(said, new Integer(linkToDevice2));
    			    	saSTBSerialNoMap.put(said, device1SerialNo);
    			    	saSCSerialNoMap.put(said, device2SerialNo);
    					try{
    					//无法得到CSIID，直接设为0
    				    SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTPROD_RESUME,cp.getCustomerID(),
    					    		cp.getAccountID(),cp.getServiceAccountID(),cp.getProductID(),0,
    														 psID.intValue(),linkToDevice2,device2SerialNo,
    														 linkToDevice1,device1SerialNo,0,operatorID,
    														 cp.getStatus(),SystemEventRecorder.SE_STATUS_CREATE);
    					}catch(CreateException e3){
    						LogUtility.log(clazz,LogLevel.WARN,"更新客户产品，原因：创建系统事件出错！"+e3);
    						throw new ServiceException("更新客户产品，原因：创建系统事件出错！");
    					}
 
    			 }
    			 cp.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    			 cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
    			 cp.setDtLastmod(TimestampUtility.getCurrentDate());
    		}
    		
			LogUtility.log(clazz, LogLevel.DEBUG, "更新业务帐户::" + saSet);
    		//修改业务帐户 状态,并添加 业务帐户 恢复事件.
    		for(Iterator it=saSet.iterator();it.hasNext();){
    			Integer saId=(Integer) it.next();
    			sa = saHome.findByPrimaryKey(saId);
				LogUtility.log(clazz, LogLevel.DEBUG, "更新业务帐户状态！"
						+ sa.getServiceAccountID());
				LogUtility.log(clazz, LogLevel.DEBUG, "业务帐户状态1"
						+ sa.getStatus());
				if (CommonConstDefinition.SERVICEACCOUNT_STATUS_SYSTEMSUSPEND
						.equals(sa.getStatus())) {
					sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
					sa.setDtLastmod(TimestampUtility
							.getCurrentTimestamp());
					LogUtility.log(clazz, LogLevel.DEBUG, "业务帐户状态2"
							+ sa.getStatus());
					try{
						SystemEventRecorder.addEvent4ServiceAccount(
								SystemEventRecorder.SE_SERVICEACCOUNT_RESUME, sa.getCustomerID(),
								0, saId.intValue(), 0, 
								saSTBDeviceIDMap.get(saId)!=null?((Integer)saSTBDeviceIDMap.get(saId)).intValue():0,
								saSTBSerialNoMap.get(saId)!=null?((String)saSTBSerialNoMap.get(saId)):null, 
								saSCDeviceIDMap.get(saId)!=null?((Integer)saSCDeviceIDMap.get(saId)).intValue():0,
								saSCSerialNoMap.get(saId)!=null?((String)saSCSerialNoMap.get(saId)):null, 
								operatorID,
								SystemEventRecorder.SE_STATUS_CREATE,CommonConstDefinition.SERVICEACCOUNT_STATUS_SYSTEMSUSPEND);
					}catch(CreateException e3){
						LogUtility.log(clazz,LogLevel.WARN,"更新业务帐户：创建系统事件出错！"+e3);
						throw new ServiceException("更新业务帐户：创建系统事件出错！");
					}
				}
    		}
    	}
    	catch(HomeFactoryException e1){
    		  LogUtility.log(clazz,LogLevel.WARN,"更改客户产品信息出错，原因：客户产品定位出错！");
        	  throw new ServiceException("更改客户产品信息出错，原因：客户产品定位出错！");
    	}
    	catch(FinderException e2){
    	      LogUtility.log(clazz,LogLevel.WARN,"更改客户产品信息出错，原因：客户产品查找出错！");
        	  throw new ServiceException("更改客户产品信息出错，原因：客户产品查找出错！");
    	}
    }

    /**
     * 根据DTO修改客户产品信息
     * !!!这个方法不能,EJB对象和DTO对象的activeTime属性名不统一,autoUpdate会出错.
     * @param dto
     */
    public void updateCustomerProduct(CustomerProductDTO dto)throws ServiceException{
    	if(dto==null||dto.getPsID()==0)
    		throw new ServiceException("参数错误：参数为空！");

    	try{
    		CustomerProductHome custProductHome=HomeLocater.getCustomerProductHome();
    		CustomerProduct cp=custProductHome.findByPrimaryKey(new Integer(dto.getPsID()));
    		//修改客户产品信息
    		if(cp.ejbUpdate(dto)==-1){
    			LogUtility.log(clazz,LogLevel.WARN,"更新客户产品信息出错，原因：cp.ejbUpdate(dto)==-1");
	    		throw new ServiceException("更新客户产品信息出错！");
    		}

    		//记录系统日志
    		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
	    			PublicService.getCurrentOperatorID(context), dto.getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "修改客户产品", "修改客户产品，产品ID：" +dto.getPsID(),
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"更改客户信息出错，原因：客户定位出错！");
    		throw new ServiceException("更改客户信息出错，原因：客户定位出错！");

    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.WARN,"更改客户信息出错，原因：客户查找出错！");
    		throw new ServiceException("更改客户信息出错，原因：客户查找出错");
    	}
    }
    /**更新客户产品,
     * 
     * @param psid
     * @param accountID
     * @param serviceAccountID
     * @param referPackageID
     * @throws ServiceException
     */
    public void updateCustomerProduct(int psid,int accountID,int serviceAccountID,int referPackageID)throws ServiceException{
    	if(psid==0||accountID==0||serviceAccountID==0)
    		throw new ServiceException("参数错误：参数为空！");

    	try{
    		CustomerProductHome custProductHome=HomeLocater.getCustomerProductHome();
    		CustomerProduct cp=custProductHome.findByPrimaryKey(new Integer(psid));
    		//修改客户产品信息
    		
    		cp.setAccountID(accountID);
    		cp.setServiceAccountID(serviceAccountID);
    		cp.setReferPackageID(referPackageID);
    		cp.setDtLastmod(TimestampUtility.getCurrentDate());
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"更改客户产品信息出错，原因：客户产品定位出错！");
    		throw new ServiceException("更改客户产品信息出错，原因：客户产品定位出错！");

    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.WARN,"更改客户产品信息出错，原因：客户产品查找出错！");
    		throw new ServiceException("更改客户产品信息出错，原因：客户产品查找出错");
    	}
    }
	/**
	 * 软件产品升降级
	 * @param actionType：升/降级 :EJBEvent.UPGRADE/EJBEvent.DOWNGRADE
	 * @param psID ：客户产品ID
	 * @param productID ：目标产品ID
	 * @throws ServiceException
	 */
	public void alterCustomerProduct(int actionType,int psID,int productID)throws ServiceException{
		//参数检查
		if(actionType==0||psID==0||productID==0)
		{
			LogUtility.log(clazz, LogLevel.WARN, "alterCustomerProduct(int actionType,int psID,int productID)参数错误！");
			throw new ServiceException("参数错误！");
		}
		if(actionType!=EJBEvent.UPGRADE && actionType!=EJBEvent.DOWNGRADE){
			LogUtility.log(clazz, LogLevel.WARN, "客户产品操作动作未知");
			throw new ServiceException("客户产品操作动作未知！");
		}
		int saID=0;
		int oldProductID=0;
		try{
			//找到需要升级/降级的客户产品
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(psID));
			int linkToDevice1=cp.getLinkToDevice1();
			int linkToDevice2=cp.getLinkToDevice2();

			saID=cp.getServiceAccountID();
			oldProductID=cp.getProductID();
			cp.setProductID(productID);
			cp.setDtLastmod(TimestampUtility.getCurrentDate());
			
			//得到客户设备
			String device1SerialNo="";
			String device2SerialNo="";
			if(linkToDevice1>0){
				device1SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice1));
			}
			if(linkToDevice2>0){
				device2SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice2));
			}

		 
//			//返回受理单id
			int csiID=0;
			if(context.get(Service.CSI_ID)!=null)
				csiID=((Integer)context.get(Service.CSI_ID)).intValue();
			if(EJBEvent.UPGRADE==actionType)
			    SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTPROD_UPGRADE,customerID,
												 accountId,saID,productID,csiID,
												 psID,linkToDevice2,device2SerialNo,
												 linkToDevice1,device1SerialNo,oldProductID,getOperatorID(),
												 cp.getStatus(),SystemEventRecorder.SE_STATUS_CREATE);
			if(EJBEvent.DOWNGRADE==actionType)
				SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTPROD_DOWNGRADE
					                             ,customerID,accountId,saID,productID,csiID
												 ,psID,linkToDevice2,device2SerialNo
												 ,linkToDevice1,device1SerialNo,oldProductID,getOperatorID()
												 ,cp.getStatus(),SystemEventRecorder.SE_STATUS_CREATE);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"产品升降级出错，原因：产品定位出错："+e);
			throw new ServiceException("产品升降级出错，原因：客户定位出错！");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.WARN,"产品升降级出错，原因：产品查找出错："+e2);
			throw new ServiceException("产品升降级出错，原因：产品查找出错！");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.WARN,"产品升降级出错，原因：创建系统事件出错！"+e3);
			throw new ServiceException("产品升降级出错，原因：创建系统事件出错！");
		}
	}
    /**
     * 产品升降级
     * 业务规则：
     * 1、被升级/降级的设备的状态置为[库存]；使用的设备的状态置为[使用]
     * 2、一次只能进行一个硬件设备升降级
     * @param actionType：升/降级 :EJBEvent.UPGRADE/EJBEvent.DOWNGRADE
     * @param psID ：客户产品ID
     * @param productID ：目标产品ID
     * @param deviceID ： 硬件设备ID
     * @throws ServiceException
     */
    public void alterCustomerProduct(int actionType,int psID,int productID,int deviceID)throws ServiceException{
    	//参数检查
    	if(actionType==0||psID==0||productID==0||deviceID==0)
    	{
    		LogUtility.log(clazz, LogLevel.WARN, "alterCustomerProduct(int actionType,int psID,int productID,int deviceID)参数错误！");
			throw new ServiceException("参数错误！");
    	}
    	if(actionType!=EJBEvent.UPGRADE && actionType!=EJBEvent.DOWNGRADE &&actionType!=EJBEvent.DEVICESWAP){
    		LogUtility.log(clazz, LogLevel.WARN, "客户产品操作动作未知");
			throw new ServiceException("客户产品操作动作未知！");
    	}
        
    	// 分别对待CM与STB,SC的设备更换
    	// CM只用检查其matchFlag ,matchdevieceID,status的情况
    	// STB,SC则要检查在T_DEVICEMATCHTOPRODUCT的关系
    	// add by david.Yang
    	TerminalDeviceDTO deviceInfo =BusinessUtility.getDeviceByDeviceID(deviceID);
    	if (deviceInfo.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
    		if(!BusinessUtility.checkCmInfoByDeviceID(deviceID)){
     		   LogUtility.log(clazz, LogLevel.WARN, "产品与设备不匹配或没有此待售设备！");
 			   throw new ServiceException("产品与设备不匹配或没有此待售设备！");
     	    }
    	}else{
    	    //检查产品与设备之间的匹配关系,这里保证传入的productID为硬件类型
    	    if(!BusinessUtility.productIDMatchDeviceID(productID,deviceID)){
    		   LogUtility.log(clazz, LogLevel.WARN, "产品与设备不匹配或没有此待售设备！");
			   throw new ServiceException("产品与设备不匹配或没有此待售设备！");
    	    }
    	}

    	int saID=0;
    	int csiID=0;
    	if(this.context.get(Service.CSI_ID)!=null)
    		csiID=((Integer)this.context.get(Service.CSI_ID)).intValue();

    	int oldProductID=0;
    	int oldDeviceID=0;
    	this.deviceMap.clear();
    	this.customerProductList.clear();

    	try{
    		String toStatus=null;
    		//如果是更换设备，需要找出目标设备的状态
    		if(actionType==EJBEvent.DEVICESWAP && context.get(Service.CSI_DTO)!=null){
    			CustServiceInteractionDTO csiDTO=(CustServiceInteractionDTO)context.get(Service.CSI_DTO);
    			//得到目标状态
    			toStatus=BusinessUtility.getDeviceSwapStatusByReason(csiDTO.getCreateReason());
    		}
    		
    		//找到需要升级/降级的客户产品
    		CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
    		CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(psID));
    		saID=cp.getServiceAccountID();
    		
    		Collection oldDeviceIDList=new ArrayList();
    		Collection deviceIDList=new ArrayList();
    		
    		TerminalDeviceHome tdHome1=HomeLocater.getTerminalDeviceHome();
  		    TerminalDevice td1=tdHome1.findByPrimaryKey(new Integer(deviceID));
  		    String newSerialNo = td1.getSerialNo();
  		    
            // 维护设备配对关系 2007-12-19
  		    deviceMatch(saID,newSerialNo);
  		    
  		    oldProductID=cp.getProductID();
  		    oldDeviceID=cp.getDeviceID();
  		    
  		    // 加上CM的判断 modify by david.Yang
  		    if (!deviceInfo.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
    		    cp.setProductID(productID);
    		    cp.setDeviceID(deviceID);
    		    cp.setDtLastmod(TimestampUtility.getCurrentDate());
  		    }
    		//取得老设备的信息,用来更新新设备的状态,用来把新设备的状态更新为老设备的原始状态
    		TerminalDeviceDTO oldDeviceInfo =BusinessUtility.getDeviceByDeviceID(oldDeviceID);
    		oldDeviceIDList.add(new Integer(oldDeviceID));
    		deviceIDList.add(new Integer(deviceID));	
    		//创建设备流转记录,老设备入库
    		deviceTransitionForCPop(oldDeviceIDList,cp.getCustomerID(),((Integer)context.get(Service.OPERATIOR_ID)).intValue(),
    				1,CommonConstDefinition.DEVICE_TRANSACTION_ACTION_G,false,toStatus);

    		
    		/*****************************add by yangchen 2008/06/20 start ***************************************/
    		TerminalDeviceDTO deviceForRelease=BusinessUtility.getDeviceByDeviceID(deviceID);
    		
    		if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceForRelease.getDeviceClass())
    			|| "Y".equals(deviceForRelease.getPreAuthorization()) //add by david.Yang 用于机卡一体的设备
    		){
    			DeviceStockService deviceStockService=new DeviceStockService(context);
    			deviceStockService.loadDeviceList(deviceID);
    			deviceStockService.devPreauthTrans(deviceID);
    		}
    		/*****************************add by yangchen 2008/06/20 end***************************************/
    		//创建设备流转记录,新设备出库
    		deviceTransitionForCPop(deviceIDList,cp.getCustomerID(),((Integer)context.get(Service.OPERATIOR_ID)).intValue(),
    	    		0,CommonConstDefinition.DEVICE_TRANSACTION_ACTION_G,false);

    		//重新设置业务帐户下所有客户产品的连接字段
    		this.customerProductList=cpHome.findByServiceAccountID(saID);
    		this.deviceMap.put(new Integer(productID),new Integer(deviceID));
    		
    		//如果是更换CM,则无需更改linkToDevice1,linkToDevice2的值 add by david.Yang
    		System.out.println("td1.getDeviceClass()------->"+td1.getDeviceClass());
    		if (!td1.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
    		    productLinkToDevice();
    		}

    	     // 取得oldDeviceID
    		Integer oldDeviceId =null;
    		String oldSerialNo = null;
    		String deviceClass = null;
    		if(context.get("OLDDEVICEID")!=null){
    		   oldDeviceId =(Integer) context.get("OLDDEVICEID");
    		   TerminalDeviceHome tdHome=HomeLocater.getTerminalDeviceHome();
    		   TerminalDevice td=tdHome.findByPrimaryKey(oldDeviceId);
    		   oldSerialNo = td.getSerialNo();
    		   deviceClass = td.getDeviceClass();
    		}
    		// 取得更换后的设备序列号跟deviceID
    		 
   		     
    		//设备更换的时候,更换后的新设备的状态和更换前的设备的状态一致
  		     td1.setStatus(oldDeviceInfo.getStatus());
   		     //用来存储设备更换的时候机顶盒或者时智能卡的设备号和序列号(不是当前要更换的设备),
  		    
  		   /*****************************add by david.Yang 2009/07/29 start ***************************************/
  		     if (td1.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
  		    	 deviceClass =CommonConstDefinition.DEVICECALSS_CM;
  		    	 TerminalDeviceHome tdHome=HomeLocater.getTerminalDeviceHome();
     		     TerminalDevice td=tdHome.findByPrimaryKey(oldDeviceId);
                  //  换下来的CM解除配对,其状态与配置表中的映射一致
     		     TerminalDevice cm=tdHome.findByPrimaryKey(new Integer(td.getMatchDeviceID()));
     		     cm.setStatus(td.getStatus());
     		     cm.setMatchDeviceID(0);
     		     cm.setMatchFlag("N");
     		     //原STB 帮定新CM
     		     td.setMACAddress(td1.getSerialNo());
     		     td.setMatchDeviceID(td1.getDeviceID().intValue());
     		     //新CM 帮定原STB
     		     td1.setMatchDeviceID(td.getDeviceID().intValue());
     		     td1.setMatchFlag("Y");
     		     //STB 状态仍为原来的销售状态
     		     td.setStatus(oldDeviceInfo.getStatus());   
  		     }else{
  		    	 if (td1.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_STB)){   		
  		    	   	TerminalDeviceHome tdHome=HomeLocater.getTerminalDeviceHome();
  	     		    TerminalDevice td=tdHome.findByPrimaryKey(oldDeviceId);
  	     		    //双向机顶盒更换
                    if (BusinessUtility.getVodModel().contains(td.getDeviceModel()) 
                    		&& BusinessUtility.getVodModel().contains(td1.getDeviceModel())){
  	     		        if (!"Y".equals(td1.getMatchFlag())){  //  更换双向机顶盒的主板STB,要求解除原CM的帮定关系,建立新的CM帮定关系
  	     		           //新STB 帮定老的CM
  	     		           td1.setMACAddress(td.getMACAddress());
   	     		           td1.setMatchDeviceID(td.getMatchDeviceID());
  	     		           td1.setMatchFlag("Y");
  	     		           //CM 帮定新的STB 
  	     		           TerminalDevice cm =tdHome.findByPrimaryKey(new Integer(td.getMatchDeviceID()));
  	     		           cm.setMatchDeviceID(td1.getDeviceID().intValue());
  	     		           //换下来的STB,解除CM关系帮定
                           td.setMACAddress(null);
                           td.setMatchFlag("N");
                           td.setMatchDeviceID(0);
                           
  	     		        }else{       //整机更换,原STB,CM的帮定关系解除 .暂时不解除配对关系
  	     		           TerminalDevice cm =tdHome.findByPrimaryKey(new Integer(td.getMatchDeviceID()));
  	     		        //   cm.setMatchFlag("N");
  	     		        //   cm.setMatchDeviceID(0);
  	     		           cm.setStatus(td.getStatus());
  	     		        //   td.setMACAddress(null);
                        //   td.setMatchFlag("N");
                        //   td.setMatchDeviceID(0);
  	     		        }
                    }else if (BusinessUtility.getVodModel().contains(td.getDeviceModel()) 
                             && !BusinessUtility.getVodModel().contains(td1.getDeviceModel())){  //双向机顶盒换单向机顶盒
                    	   TerminalDevice cm =tdHome.findByPrimaryKey(new Integer(td.getMatchDeviceID()));
	     		           cm.setMatchFlag("N");
	     		           cm.setMatchDeviceID(0);
	     		           cm.setStatus(td.getStatus());
	     		           td.setMACAddress(null);
                           td.setMatchFlag("N");
                           td.setMatchDeviceID(0);
                    }
  		    	 }
  		     }  
  		   /*****************************add by david.Yang 2009/07/29 end***************************************/
  
   		     int otherDeviceID=0;
   		     String otherSerialNo="";
     		 updateCAValletDependDevice(oldDeviceId.intValue());
   		     if("SC".equalsIgnoreCase(deviceClass)){
   		    	 TerminalDeviceDTO deviceDTO=BusinessUtility.getDeviceInfoBySaIDandClass(cp.getServiceAccountID(),"STB");
   		         if(deviceDTO!=null){
   		        	otherDeviceID=deviceDTO.getDeviceID();
   		        	otherSerialNo=deviceDTO.getSerialNo();
   		         }
   		         //旧设备事件，修改原来的老的设备事件，新设备事件的产品由oldProductID改成productID
   		    	 //SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
				 //	this.accountId, saID,oldProductID, csiID, psID,deviceID,newSerialNo,otherDeviceID,otherSerialNo,
				//	oldDeviceId.intValue(),oldSerialNo,otherDeviceID,otherSerialNo,
				//	getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		         SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
   		    		  this.accountId, saID,oldProductID, csiID, psID,oldDeviceId.intValue(),oldSerialNo,otherDeviceID,otherSerialNo,
   		    		  oldDeviceId.intValue(),oldSerialNo,otherDeviceID,otherSerialNo,
   		    		  getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		         //新设备事件
   		    	 SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE_NEW, customerID,
   		          	this.accountId, saID,productID, csiID, psID,deviceID,newSerialNo,otherDeviceID,otherSerialNo,
					oldDeviceId.intValue(),oldSerialNo,otherDeviceID,otherSerialNo,
					getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		          
   		     }else if("STB".equalsIgnoreCase(deviceClass)){
   		    	 TerminalDeviceDTO deviceDTO=BusinessUtility.getDeviceInfoBySaIDandClass(cp.getServiceAccountID(),"SC");
   		         if(deviceDTO!=null){
   		        	otherDeviceID=deviceDTO.getDeviceID();
   		        	otherSerialNo=deviceDTO.getSerialNo();
   		         }
   		        //旧设备事件，修改原来的老的设备事件，新设备事件的产品由oldProductID改成productID
   		    	//SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
   				//		this.accountId, saID,oldProductID, csiID, psID,otherDeviceID,otherSerialNo,deviceID,newSerialNo,
   				//		otherDeviceID,otherSerialNo,oldDeviceId.intValue(),oldSerialNo,
   				//		getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		    	SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
   						this.accountId, saID,oldProductID, csiID, psID,otherDeviceID,otherSerialNo,oldDeviceId.intValue(),oldSerialNo,
   						otherDeviceID,otherSerialNo,oldDeviceId.intValue(),oldSerialNo,
   						getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   	   		     SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE_NEW, customerID,
   	   		          	this.accountId, saID,productID, csiID, psID,otherDeviceID,otherSerialNo,deviceID,newSerialNo,
   	   		          	otherDeviceID,otherSerialNo,oldDeviceId.intValue(),oldSerialNo,
   						getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);
   		     }else if("CM".equalsIgnoreCase(deviceClass)){
   		    	 TerminalDeviceDTO deviceDTO=BusinessUtility.getDeviceInfoBySaIDandClass(cp.getServiceAccountID(),"STB");
  		         if(deviceDTO!=null){
  		        	otherDeviceID=deviceDTO.getDeviceID();
  		        	otherSerialNo=deviceDTO.getSerialNo();
  		         }
  		         TerminalDeviceHome tdHome=HomeLocater.getTerminalDeviceHome();
   		         TerminalDevice oldcmDevice=tdHome.findByPrimaryKey(new Integer(deviceDTO.getMatchDeviceID()));
                 // 旧设备事件
  		         SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
    		    		  this.accountId, saID,oldProductID, csiID, psID,oldcmDevice.getDeviceID().intValue(),oldcmDevice.getSerialNo(),otherDeviceID,otherSerialNo,
    		    		  oldcmDevice.getDeviceID().intValue(),oldcmDevice.getSerialNo(),otherDeviceID,otherSerialNo,
    		    		  getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
  		       
    		     // 新设备事件
    		     SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE_NEW, customerID,
    		          	this.accountId, saID,productID, csiID, psID,td1.getDeviceID().intValue(),td1.getSerialNo(),otherDeviceID,otherSerialNo,
    		            td1.getDeviceID().intValue(),td1.getSerialNo(),otherDeviceID,otherSerialNo,
 					    getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		      }else{
   		    	 //旧设备事件，修改原来的老的设备事件，新设备事件的产品由oldProductID改成productID
   		         //SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
				//	this.accountId, saID,oldProductID, csiID, psID,0,null,deviceID,newSerialNo,
				//	0,null,oldDeviceId.intValue(),oldSerialNo,
				//	getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		       SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
					this.accountId, saID,oldProductID, csiID, psID,0,null,oldDeviceId.intValue(),oldSerialNo,
					0,null,oldDeviceId.intValue(),oldSerialNo,
					getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		       SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE_NEW, customerID,
   		          	this.accountId, saID,productID, csiID, psID,0,null,deviceID,newSerialNo,
					0,null,oldDeviceId.intValue(),oldSerialNo,
					getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);
   		          
   		     }
   		     
   		     
   		      
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"产品升降级出错，原因：产品定位出错："+e);
    		throw new ServiceException("产品升降级出错，原因：客户定位出错！");
    	}
    	catch(FinderException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"产品升降级出错，原因：产品查找出错："+e2);
    		throw new ServiceException("产品升降级出错，原因：产品查找出错！");
    	}
    	catch(CreateException e3){
    		LogUtility.log(clazz,LogLevel.WARN,"产品升降级出错，原因：创建系统事件出错！"+e3);
    		throw new ServiceException("产品升降级出错，原因：创建系统事件出错！");
    	}
    }

    /**
     * 更换 智能卡 的时候 ，只用将老智能卡相关的钱包记录
     * 置为无效 。 等待人工在新卡上重新开通和转移信用余额 。 
     * @param oldDeviceID
     * @param serialNo
     * @throws ServiceException
     */
    public void updateCAValletDependDevice(int oldDeviceID) throws ServiceException{
    	//取当前设备相关的有效钱包
    	try {
	    	List cawList=BusinessUtility.getCAWalletListByDeviceID(oldDeviceID);
	    	if(cawList==null||cawList.isEmpty()){
	    		return;
	    	}
	    	context.put(Service.CAWALLETSERIALNOSWAP, cawList);
	    	LogUtility.log(clazz,LogLevel.DEBUG,"开始更新CAWallet中序列号！"+cawList);
			for (int i = 0; i < cawList.size(); i++) {
				CAWalletDTO cawDto = (CAWalletDTO) cawList.get(i);
				CAWalletHome cawHome = HomeLocater.getCAWalletHome();
				CAWallet caw = cawHome.findByPrimaryKey(new Integer(cawDto
						.getWalletId()));
				caw.setStatus(CommonConstDefinition.GENERALSTATUS_INVALIDATE);
				caw.setDtLastmod(com.dtv.oss.util.TimestampUtility
						.getCurrentTimestamp());
			}
		} catch (Exception e) {
    		LogUtility.log(clazz,LogLevel.WARN,"更新设备关联的CA钱包出错"+e);
			throw new ServiceException("更新设备关联的CA钱包出错");
		}
    }
    /**
     * 更换设备
     * @param psID
     * @param productID
     * @param deviceID
     * @throws ServiceException
     */
    public void swapDevice(int psID,int productID,int deviceID)throws ServiceException{
    	//个人认为它和产品升降级在业务逻辑没有很大的区别，暂时放到一块去
    	alterCustomerProduct(EJBEvent.DEVICESWAP,psID,productID,deviceID);
    }

    /**
     * 设备流转
     * @param deviceList
     * @param actionType
     * @throws ServiceException
     */
    public void transferDevice(Collection deviceList,int actionType) throws ServiceException{

    	//暂时没有考虑actionType

    	if(deviceList==null||deviceList.isEmpty()){
    		LogUtility.log(clazz,LogLevel.WARN,"设备列表为空");
        	return ;
    	}
    	try{
    		deviceTransitionForCPop(deviceList,this.customerID,getOperatorID(),
        		1,CommonConstDefinition.DEVICE_TRANSACTION_ACTION_H,true);
    	}
        catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"设备流转出错，原因：产品定位出错："+e);
        	throw new ServiceException("设备流转出错，原因：客户定位出错！");
        }
        catch(FinderException e2){
        	LogUtility.log(clazz,LogLevel.WARN,"设备流转出错，原因：产品查找出错："+e2);
        	throw new ServiceException("设备流转出错，原因：产品查找出错！");
        }
        catch(CreateException e3){
        	LogUtility.log(clazz,LogLevel.WARN,"设备流转出错，原因：创建系统事件出错！"+e3);
        	throw new ServiceException("设备流转出错，原因：创建系统事件出错！");
        }
    }

    /**
     * 通过CSIID得到CsiCustProductInfo的信息，根据CsiCustProductInfo创建CustomerProduct，并修改CsiCustProductInfo
     * 的CustomerProductID，返回该CustomerProduct！
     * @param productid
     * @param packageid
     * @param csiProductInfoList
     * @param csitype
     * @return
     * @throws ServiceException
     */

    private CustomerProduct createCustProductByCsiCustProductInfo(CustomerProductDTO dto,
    		Collection csiProductInfoList,String status,HashMap returnHashMap) throws ServiceException {

    	Iterator iterator = csiProductInfoList.iterator();
    	while(iterator.hasNext()) {
        	CsiCustProductInfo csiProductLocal = (CsiCustProductInfo)iterator.next();

        	if (csiProductLocal.getProductID() == dto.getProductID()) {
        		CustomerProductDTO cpDto = new CustomerProductDTO();
        		cpDto.setAccountID(accountId);
        		cpDto.setCustomerID(customerID);
        		cpDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
        		cpDto.setProductID(dto.getProductID());
				cpDto.setServiceAccountID(saID);
				cpDto.setReferPackageID(dto.getReferPackageID());
				cpDto.setDeviceID(csiProductLocal.getReferDeviceID());
				//杨勇请确认，正常只有在状态为N的情况下面判断才需要的
				if(this.usedMonth!=0){
					if (cpDto.getDeviceID() ==0){
					   cpDto.setStartTime(TimestampUtility.getCurrentDateWithoutTime());
					   cpDto.setEndTime(TimestampUtility.getTimeEndWithoutTime(cpDto.getStartTime(), "M",this.usedMonth));
					}
				}

				//只有门店开户并且属于自安装，产品的状态才是初始状态，其他为正常状态--->所有产品状态为初始, modify 2005-12-15
				//if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csitype)&&
				//		CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL.equals(installationType))
				//cpDto.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_INIT);
				//----------yangchen-----------2006/11/23-----------start---------------
/*				
				//只有客户新增产品的时候才，产品状态才是正常，其他为潜在 modify 2006-1-5
				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csitype))
				{
					cpDto.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
					cpDto.setActivateTime(new Timestamp(System.currentTimeMillis()));//6-8侯添加
				}//11-21侯添加,当集团客户开子客户时,产品状态为正常,激活时间为当前
				else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS.equals(csitype)){
					cpDto.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
					cpDto.setActivateTime(new Timestamp(System.currentTimeMillis()));
				}
				else
					cpDto.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_INIT);
*/				
				if(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL.equals(status)){
					cpDto.setActivateTime(TimestampUtility.getCurrentTimestamp());
				}
				cpDto.setStatus(status);
				
				
				//----------yangchen-----------2006/11/23-----------start---------------
				//else
				//	cpDto.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);

				try {
					CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
					CustomerProduct cp = cpHome.create(cpDto);
					LogUtility.log(clazz,LogLevel.DEBUG,"创建一个客户产品："+cp.getPsID());
					this.customerProductList.add(cp);
					//更新CSI产品列表的客户产品ID
					csiProductLocal.setCustProductID(cp.getPsID().intValue());
					csiProductLocal.setReferAccountID(accountId);
					csiProductLocal.setReferServiceAccountID(saID);
					returnHashMap.put(cp.getPsID(), csiProductLocal);	
					return cp;
				}
				catch (HomeFactoryException e) {
					LogUtility.log(clazz, LogLevel.ERROR, e);
					throw new ServiceException("查找home 接口错误");
				}
				catch (CreateException e) {
					LogUtility.log(clazz, LogLevel.ERROR, e);
					throw new ServiceException("创建客户产品对象 错误");
				}
        	}
    	}
        return null;
    }

    /**
     * 设备流转
     * @param deviceIDList : 设备ID的列表
     * @param customerID
     * @param opID
     * @param actionType : 0为新设备购买出库，1为售出的设备入库，
     * @param action : 设备设备流转的动作类型
     * @param useflag : 当actionType=1时，useflag 是否为二手货。
     *                  当actionType时 useflag 为 true 表示设备锁定，false 表示设备售出！
     * @throws ServiceException
     * @throws HomeFactoryException
     * @throws FinderException
     * @throws CreateException
     */
    public void deviceTransitionForCPop(Collection deviceIDList,int customerID,int opID,int actionType,String tranAction,boolean useFlag,String toStatus)
    	throws ServiceException, HomeFactoryException, FinderException, CreateException{
    	
    	if(actionType!=0 && actionType!=1){
    		LogUtility.log(clazz,LogLevel.WARN,"设备流转类型未知");
    		throw new ServiceException("设备流转类型未知");
    	}

    	if(deviceIDList==null || deviceIDList.size()==0){
    		LogUtility.log(clazz,LogLevel.DEBUG,"没有设备需要流转");
    		return;
    	}

    	LogUtility.log(clazz,LogLevel.DEBUG,"进入设备流转");

    	//操作员所在的组织ID
    	int orgID=BusinessUtility.getOpOrgIDByOperatorID(((Integer)this.context.get(Service.OPERATIOR_ID)).intValue());
    	String strFromType="";
    	int intFromID=0;

    	//创建设备流转记录
    	LogUtility.log(clazz,LogLevel.DEBUG,"创建设备流转记录");
    	DeviceTransitionDTO deviceTransitionDto=new DeviceTransitionDTO();
    	deviceTransitionDto.setCreateTime(new Timestamp(System.currentTimeMillis()));
    	deviceTransitionDto.setAction(tranAction);
    	deviceTransitionDto.setDeviceNumber(deviceIDList.size());
    	if(actionType==0){
    		deviceTransitionDto.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
    		deviceTransitionDto.setToID(customerID);
    	}
    	else if(actionType==1){
    		if(CommonConstDefinition.DEVICE_STATUS_LOST.equals(toStatus) || CommonConstDefinition.DEVICE_STATUS_OFFLINE.equals(toStatus)){
    			deviceTransitionDto.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
        		deviceTransitionDto.setToID(customerID);
    		}
    		else{
    			deviceTransitionDto.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_INORGANIZATION);
        		deviceTransitionDto.setToID(orgID);
    		}
    		
    	}
    	deviceTransitionDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
    	deviceTransitionDto.setOperatorID(opID);
    	DeviceTransitionHome deviceTransitionHome=HomeLocater.getDeviceTransitionHome();
    	DeviceTransition deviceTransition=deviceTransitionHome.create(deviceTransitionDto);

    	Iterator itDeviceID=deviceIDList.iterator();
		TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
    	while(itDeviceID.hasNext()){
    		int deviceID=((Integer)itDeviceID.next()).intValue();

    		LogUtility.log(clazz,LogLevel.DEBUG,"当前操作的设备号为：" + deviceID);

        	TerminalDevice terminalDevice=terminalDeviceHome.findByPrimaryKey(new Integer(deviceID));
        	String fromType=terminalDevice.getAddressType();
        	int fromID=terminalDevice.getAddressID();
        	
        	//设备出库且状态不是待售
        	if(actionType==0){
        		if(!(CommonConstDefinition.DEVICE_STATUS_WAITFORSELL.equals(terminalDevice.getStatus()))
        			&& !(CommonConstDefinition.DEVICE_STATUS_LOCK.equals(terminalDevice.getStatus()))){
        			throw new ServiceException("该设备:"+terminalDevice.getSerialNo()+" 状态不是处于待售/锁定状态");
        		}
        	}
        	//设备入库且状态不为已售/ 锁定
        	if(actionType==1){
        		if(!(CommonConstDefinition.DEVICE_STATUS_SOLD.equals(terminalDevice.getStatus()))
        			&& !(CommonConstDefinition.DEVICE_STATUS_LOCK.equals(terminalDevice.getStatus()))){
        			throw new ServiceException("客户设备:"+terminalDevice.getSerialNo()+" 的状态不是处于已售/锁定状态");
        		}
        	}
        	
            // 创建设备流转明细
        	LogUtility.log(clazz,LogLevel.DEBUG,"创建设备流转明细");
        	DeviceTransitionDetailDTO deviceTransitonDetailDto=new DeviceTransitionDetailDTO();
        	deviceTransitonDetailDto.setBatchID(deviceTransition.getBatchID().intValue());
        	deviceTransitonDetailDto.setDeviceID(deviceID);
        	deviceTransitonDetailDto.setFromID(fromID);
        	deviceTransitonDetailDto.setFromType(fromType);
        	deviceTransitonDetailDto.setSerialNo(terminalDevice.getSerialNo());
        	deviceTransitonDetailDto.setFromDeviceStatus(terminalDevice.getStatus());
        	if(actionType==0){
        		if(toStatus!=null && toStatus.length()>0){
        			deviceTransitonDetailDto.setToDeviceStatus(toStatus);
        		}
        		else{
        			if (useFlag ==false){
        				deviceTransitonDetailDto.setToDeviceStatus(CommonConstDefinition.DEVICE_STATUS_SOLD);
        			} 
        			else{
        				deviceTransitonDetailDto.setToDeviceStatus(CommonConstDefinition.DEVICE_STATUS_LOCK);
        			}
        		}
        		deviceTransitonDetailDto.setToID(customerID);
        		deviceTransitonDetailDto.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
        	}
        	else if(actionType==1){
        		if(toStatus!=null && toStatus.length()>0){
        			deviceTransitonDetailDto.setToDeviceStatus(toStatus);
        		}
        		else{
        			if (useFlag==false){
        				deviceTransitonDetailDto.setToDeviceStatus(CommonConstDefinition.DEVICE_STATUS_WAIT4REPAIR);
        			} 
        			else{
        				deviceTransitonDetailDto.setToDeviceStatus(CommonConstDefinition.DEVICE_STATUS_WAITFORSELL);
        			}
        		}
        		if(CommonConstDefinition.DEVICE_STATUS_LOST.equals(toStatus) || CommonConstDefinition.DEVICE_STATUS_OFFLINE.equals(toStatus)){
        			deviceTransitonDetailDto.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
        			deviceTransitonDetailDto.setToID(customerID);
        		}
        		else{
        			deviceTransitonDetailDto.setToID(orgID);
        			deviceTransitonDetailDto.setToType(CommonConstDefinition.DEVICE_ADDRESSTYPE_INORGANIZATION);
        		}
        	}
        	
        	deviceTransitonDetailDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
        	DeviceTransitionDetailHome deviceTransitionDetailHome=HomeLocater.getDeviceTransitionDetailHome();
        	deviceTransitionDetailHome.create(deviceTransitonDetailDto);

        	if(!"".equals(fromType))
        		strFromType=fromType;
        	if(fromID!=0)
        		intFromID=fromID;
        	if(actionType==0 &&useFlag==false){
        		if(toStatus!=null && toStatus.length()>0)
        			terminalDevice.setStatus(toStatus);
        		else
        			terminalDevice.setStatus(CommonConstDefinition.DEVICE_STATUS_SOLD);
        		terminalDevice.setAddressType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
            	terminalDevice.setAddressID(customerID);
            	terminalDevice.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        	} else if (actionType ==0 &&useFlag==true){
        		if(toStatus!=null && toStatus.length()>0)
        			terminalDevice.setStatus(toStatus);
        		else
        			terminalDevice.setStatus(CommonConstDefinition.DEVICE_STATUS_LOCK);
        		terminalDevice.setAddressType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
            	terminalDevice.setAddressID(customerID);
            	terminalDevice.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        	} else if(actionType==1&&useFlag==false){
        		if(toStatus!=null && toStatus.length()>0)
        			terminalDevice.setStatus(toStatus);
        		else
        			terminalDevice.setStatus(CommonConstDefinition.DEVICE_STATUS_WAIT4REPAIR);
        		
        		if(CommonConstDefinition.DEVICE_STATUS_LOST.equals(toStatus) || CommonConstDefinition.DEVICE_STATUS_OFFLINE.equals(toStatus)){
            		terminalDevice.setAddressType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
                	terminalDevice.setAddressID(customerID);
        		}
        		else{
        			terminalDevice.setAddressType(CommonConstDefinition.DEVICE_ADDRESSTYPE_INORGANIZATION);
        			terminalDevice.setAddressID(orgID);
        		}
        		terminalDevice.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        	} else if(actionType==1&&useFlag){
        		if(toStatus!=null && toStatus.length()>0)
        			terminalDevice.setStatus(toStatus);
        		else
        			terminalDevice.setStatus(CommonConstDefinition.DEVICE_STATUS_WAITFORSELL);//9-07david.Yang改,退户、安装不成功退费设备状态应为待售
        		if(CommonConstDefinition.DEVICE_STATUS_LOST.equals(toStatus) || CommonConstDefinition.DEVICE_STATUS_OFFLINE.equals(toStatus)){
            		terminalDevice.setAddressType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
                	terminalDevice.setAddressID(customerID);
        		}
        		else{
        			terminalDevice.setAddressType(CommonConstDefinition.DEVICE_ADDRESSTYPE_INORGANIZATION);
        			terminalDevice.setAddressID(orgID);
        		}
            	terminalDevice.setUseFlag(CommonConstDefinition.DEVICE_USEFLAG_USED);
            	terminalDevice.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
        	}

    	}

    	//回置设备流转
    	deviceTransition.setFromType(strFromType);
    	deviceTransition.setFromID(intFromID);

    	LogUtility.log(clazz,LogLevel.DEBUG,"结束设备流转");
    	
    }
    /**
     * 设备流转
     * @param deviceIDList : 设备ID的列表
     * @param customerID
     * @param opID
     * @param actionType : 0为新设备购买出库，1为售出的设备入库，
     * @param action : 设备设备流转的动作类型
     * @param useflag : 当actionType=1时，useflag 是否为二手货。
     *                  当actionType时 useflag 为 true 表示设备锁定，false 表示设备售出！
     * @throws ServiceException
     * @throws HomeFactoryException
     * @throws FinderException
     * @throws CreateException
     */
    public void deviceTransitionForCPop(Collection deviceIDList,int customerID,int opID,int actionType,String tranAction,boolean useFlag)
    	throws ServiceException, HomeFactoryException, FinderException, CreateException{

    	deviceTransitionForCPop(deviceIDList,customerID,opID,actionType,tranAction,useFlag,null);
    }

    /**
     * 给客户产品的linkToDevice1,linkToDevice2赋值
     * @throws FinderException
     * @throws HomeFactoryException
     * @throws HomeFactoryException
     * @throws FinderException
     * @throws ServiceException
     *
     */
    private void productLinkToDevice() throws HomeFactoryException, FinderException, ServiceException{

    	//如果产品为空，则退出
    	if(this.customerProductList==null||this.customerProductList.isEmpty()){
    		return;
    	}
    	
    	boolean isChangeLinkedDevice=false;
    	LogUtility.log(clazz,LogLevel.DEBUG,"软件产品连接到硬件产品");
    	int linkToDevice1=0;
    	int linkToDevice2=0;
    	Set productIDSet=null;
    	//如果deviceMap为空，则先进行在该业务帐户下查找，如果还找不到，则退出
    	if(this.deviceMap==null || this.deviceMap.isEmpty()){

    		Map deviceMap=BusinessUtility.getSADeviceIDMapBySAID(this.saID);

    		if(deviceMap==null || deviceMap.size()==0)
    			return;

    		productIDSet=deviceMap.keySet();

    		//取得linkToDevice1、linkToDevice2
    		Iterator itProductID=productIDSet.iterator();
        	while(itProductID.hasNext()){
        		Integer productID=(Integer)itProductID.next();
        		Integer deviceID=(Integer)deviceMap.get(productID);
    			String deviceClass=BusinessUtility.getDeviceClassByDeviceID(deviceID);
    			if(CommonConstDefinition.DEVICECALSS_CM.equals(deviceClass)||
    					CommonConstDefinition.DEVICECALSS_STB.equals(deviceClass)||
    					CommonConstDefinition.DEVICECALSS_IPP.equals(deviceClass)){
    				linkToDevice1=deviceID.intValue();
    			}
    			else if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceClass)){
    				linkToDevice2=deviceID.intValue();
    			}
        	}
    	}
    	else
    	{
    		//clone一个设备Map
    		HashMap deviceMapCopy=(HashMap)this.deviceMap.clone();
    		productIDSet=deviceMapCopy.keySet();
    		boolean changeDevice=false;

    		//取得linkToDevice1、linkToDevice2
        	Iterator itProductID=productIDSet.iterator();
        	while(itProductID.hasNext()){
        		Integer productID=(Integer)itProductID.next();
        		Integer deviceID=(Integer)deviceMapCopy.get(productID);
    			String deviceClass=BusinessUtility.getDeviceClassByDeviceID(deviceID);
    			if(CommonConstDefinition.DEVICECALSS_CM.equals(deviceClass)||
    					CommonConstDefinition.DEVICECALSS_STB.equals(deviceClass)||
    					CommonConstDefinition.DEVICECALSS_IPP.equals(deviceClass)){
    				linkToDevice1=deviceID.intValue();
    			}
    			else if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceClass)){
    				linkToDevice2=deviceID.intValue();
    			}
        	}
        	if(linkToDevice1!=0||linkToDevice2!=0){
        		isChangeLinkedDevice=true;
        	}
        	
        	if(linkToDevice1==0){
        		linkToDevice1=getLinkTodevice(1);       		
        	}
        	if(linkToDevice2==0){
        		linkToDevice2=getLinkTodevice(2);        		
        	}
        	if(linkToDevice1!=0||linkToDevice2!=0){
        		changeDevice=true;
        	}
        	if(changeDevice){
        		CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
        		Collection saHasProdCol=cpHome.findByServiceAccountID(this.saID);
        		Iterator saHasProdIter=saHasProdCol.iterator();
            	while(saHasProdIter.hasNext()){
            		CustomerProduct cp=(CustomerProduct)saHasProdIter.next();
            		if (linkToDevice1 !=0)
            		     cp.setLinkToDevice1(linkToDevice1);
            		if (linkToDevice2 !=0)
            		     cp.setLinkToDevice2(linkToDevice2);
            		if(isChangeLinkedDevice){
                		cp.setDtLastmod(TimestampUtility.getCurrentDate());
            		}
            	}
        	}
    	}
    	LogUtility.log(clazz,LogLevel.DEBUG,"软件产品连接到硬件产品linkToDevice1  "+linkToDevice1+"   linkToDevice2  "+linkToDevice2);

    	Iterator itCP=customerProductList.iterator();
    	while(itCP.hasNext()){
    		CustomerProduct cp=(CustomerProduct)itCP.next();

    		if (linkToDevice1 !=0)
    		     cp.setLinkToDevice1(linkToDevice1);
    		if (linkToDevice2 !=0)
    		     cp.setLinkToDevice2(linkToDevice2);
    		if(isChangeLinkedDevice){
        		cp.setDtLastmod(TimestampUtility.getCurrentDate());
    		}

    		//有依赖关系才设置linkToDevice1、linkToDevice，而现在采用不判断依赖关系，统统按device类型来设置linkToDevice1、linkToDevice2
    		/*
    		Iterator itProduct=productIDSet.iterator();
    		while(itProduct.hasNext()){

    			Integer productID=(Integer)itProduct.next();
    			if(BusinessUtility.prodcutsIsDependency(cp.getProductID(),productID.intValue())){

    				Integer deviceID=(Integer)deviceMapCopy.get(productID);
    				String deviceClass=BusinessUtility.getDeviceClassByDeviceID(deviceID);

    				//如果类型为STB或CM则放入到linkToDevice1,如果是SC则存放到linkToDevice2
    				if(CommonConstDefinition.DEVICECALSS_CM.equals(deviceClass)||
    						CommonConstDefinition.DEVICECALSS_STB.equals(deviceClass)||
    						CommonConstDefinition.DEVICECALSS_IPP.equals(deviceClass)){
    					cp.setLinkToDevice1(deviceID.intValue());
    				}
    				else if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceClass)){
    					cp.setLinkToDevice2(deviceID.intValue());
    				}
    			}
    		}
    		*/
    	}
    }
    private int getLinkTodevice(int device1Ordevice2) throws HomeFactoryException, FinderException{
    	int linkToDevice=0;
    	Map deviceMap=BusinessUtility.getSADeviceIDMapBySAID(this.saID);
    	Set productIDSet=null;
		if(deviceMap==null || deviceMap.size()==0){
			
		}else{
			productIDSet=deviceMap.keySet();

    		//取得linkToDevice1、linkToDevice2
    		Iterator productIDit=productIDSet.iterator();
        	while(productIDit.hasNext()){
        		Integer productID=(Integer)productIDit.next();
        		Integer deviceID=(Integer)deviceMap.get(productID);
    			String deviceClass=BusinessUtility.getDeviceClassByDeviceID(deviceID);
    			if(device1Ordevice2==1){
    				if(CommonConstDefinition.DEVICECALSS_CM.equals(deviceClass)||
        					CommonConstDefinition.DEVICECALSS_STB.equals(deviceClass)||
        					CommonConstDefinition.DEVICECALSS_IPP.equals(deviceClass)){
        				linkToDevice=deviceID.intValue();
        			}
    			}	
    			else if(device1Ordevice2==2){
    				if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceClass)){
        				linkToDevice=deviceID.intValue();
    			} 
    				
    			}
        	}
		}
    	return linkToDevice;
    	
    }

    /**
     * 创建客户产品系统事件
     * @param customerID
     * @param accountID
     * @param saID
     * @param csiid
     * @throws HomeFactoryException
     * @throws FinderException
     * @throws CreateException
     */
    private void createSystemEventRecord(int customerID,int accountID,int saID,int csiid)
    throws HomeFactoryException, FinderException, CreateException,ServiceException{
    	HashSet cpAddEventRecordSet=new HashSet();
    	int linkToDevice1=0;
    	int linkToDevice2=0;
    	String Device1SerialNo="";
    	String Device2SerialNo="";
        String reAuthorization=BusinessUtility.getSystemsettingValueByName("SET_V_REAUTHORIZATION");
    	//先添加newsaflag='Y'的系统事件，既需要创建业务帐户的产品的购买事件
    	//update by Stone, 区分硬件和软件产品的系统事件，系统事件分别是610和310
    	
    	Iterator itSA=this.customerProductList.iterator();
    	
    	while(itSA.hasNext()){
    		CustomerProduct cp=(CustomerProduct)itSA.next();
    		ProductDTO pDTO = BusinessUtility.getProductDTOByProductID(cp.getProductID());

    		if("Y".equals(pDTO.getNewsaFlag()))
    		{
				if(cpAddEventRecordSet.add(cp.getPsID())){
	
					LogUtility.log(clazz,LogLevel.DEBUG,"先添加newsaflag='Y'的系统事件");
	
					Device1SerialNo="";
					Device2SerialNo="";
	
					linkToDevice1=cp.getLinkToDevice1();
					linkToDevice2=cp.getLinkToDevice2();
					if(linkToDevice1>0)
						Device1SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice1));
					if(linkToDevice2>0)
						Device2SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice2));
					
					//硬件产品
					if(cp.getDeviceID()>0)
						SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTDEV_PURCHASE, customerID,
							accountID, saID,cp.getProductID(), csiid, cp.getPsID().intValue(),
							linkToDevice2,Device2SerialNo,linkToDevice1,Device1SerialNo,
							PublicService.getCurrentOperatorID(context),SystemEventRecorder.SE_STATUS_CREATE);
					else //软件产品
					{if(devicelist.contains(new Integer(cp.getProductID()))&&"Y".equalsIgnoreCase(reAuthorization))
						continue;
						SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTPROD_PURCHASE, customerID,
								accountID, saID,cp.getProductID(), csiid, cp.getPsID().intValue(),
								linkToDevice2,Device2SerialNo,linkToDevice1,Device1SerialNo,
								PublicService.getCurrentOperatorID(context),SystemEventRecorder.SE_STATUS_CREATE);
					
					}
				}
    		}
    	}
 
    	//再添加剩下的硬件，系统事件是610
    	Iterator itRestDevice=this.customerProductList.iterator();
    	while(itRestDevice.hasNext()){
    		CustomerProduct cp=(CustomerProduct)itRestDevice.next();
    		if(cp.getDeviceID()>0){
    			if(cpAddEventRecordSet.add(cp.getPsID())){

    				LogUtility.log(clazz,LogLevel.DEBUG,"再添加剩下的硬件系统事件");

    				Device1SerialNo="";
					Device2SerialNo="";

    				linkToDevice1=cp.getLinkToDevice1();
    				linkToDevice2=cp.getLinkToDevice2();
    				if(linkToDevice1>0)
    					Device1SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice1));
    				if(linkToDevice2>0)
    					Device2SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice2));

    				SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTDEV_PURCHASE, customerID,
    						accountID, saID,cp.getProductID(), csiid, cp.getPsID().intValue(),
    						linkToDevice2,Device2SerialNo,linkToDevice1,Device1SerialNo,
    						PublicService.getCurrentOperatorID(context),SystemEventRecorder.SE_STATUS_CREATE);
    			}

    		}
    	}
    	//再添加剩下的产品，系统事件是310
    	Iterator itRest=this.customerProductList.iterator();
    	while(itRest.hasNext()){
    		CustomerProduct cp=(CustomerProduct)itRest.next();
    		if(cpAddEventRecordSet.add(cp.getPsID())){

    			LogUtility.log(clazz,LogLevel.DEBUG,"再添加剩下的产品事件");

    			Device1SerialNo="";
				Device2SerialNo="";
    			linkToDevice1=cp.getLinkToDevice1();
    			linkToDevice2=cp.getLinkToDevice2();    			
    			if(linkToDevice1>0)
    				Device1SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice1));
    			if(linkToDevice2>0)
    				Device2SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice2));   			
    			if(devicelist.contains(new Integer(cp.getProductID()))&&"Y".equalsIgnoreCase(reAuthorization))
    				continue;
    			SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTPROD_PURCHASE, customerID,
    					accountID, saID,cp.getProductID(), csiid, cp.getPsID().intValue(),
    					linkToDevice2,Device2SerialNo,linkToDevice1,Device1SerialNo,
    					PublicService.getCurrentOperatorID(context),SystemEventRecorder.SE_STATUS_CREATE);
    			
    		}
    	}
    }


    /**
     * 修改客户产品状态，记录产品事件和日志，用于产品暂停、恢复、取消
     * @throws ServiceException
     * 1.5侯增加了返回值,用于生成日志
     */
    private ArrayList updateCustomerProductStatus(Collection custProductIDList,String status,int event,
    		String log,String logDes) throws ServiceException{

    	//进行逆序排列，
    	custProductIDList=BusinessUtility.sortPsidWithCancelCp(custProductIDList);
    	LogUtility.log(clazz, LogLevel.DEBUG, "传入的需要操作的产品列表为："+custProductIDList);
    	ArrayList list=new ArrayList();
    	//检查参数
    	String errorMsg = "";
    	if(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL.equals(status))
    		errorMsg = "没有待取消的产品！";
    	else if(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND.equals(status))
    		errorMsg = "没有待暂停的软件产品！";
    	else if(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL.equals(status))
    		errorMsg = "没有待恢复的产品！";
    	
    	if(custProductIDList==null||custProductIDList.isEmpty()){
    		LogUtility.log(clazz, LogLevel.ERROR, errorMsg);
        	throw new ServiceException(errorMsg);
        }
    	
    	int csiID=getCSIID();

    	try{
    		Iterator itCPID=custProductIDList.iterator();
    		CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
    		CustomerProduct cp=null;

        	TerminalDeviceHome terminalDeviceHome=HomeLocater.getTerminalDeviceHome();
    		while(itCPID.hasNext()){
    			Integer cpID=(Integer)itCPID.next();
    			cp=cpHome.findByPrimaryKey(cpID);
    			//客户产品状态修改
    			if(!CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_MOVE.equals(status))
    			cp.setStatus(status);
    			cp.setDtLastmod(TimestampUtility.getCurrentDate());
    			//设置更改时的时间
    			switch(event){
    				//暂停
    				case SystemEventRecorder.SE_CUSTPROD_PAUSE:
    					cp.setPauseTime(TimestampUtility.getCurrentTimestamp());
    					break;
    				//恢复
    				case SystemEventRecorder.SE_CUSTPROD_RESUME:
    					cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
    					break;
    				//取消
    				case SystemEventRecorder.SE_CUSTPROD_CANCEL:
    					cp.setCancelTime(TimestampUtility.getCurrentTimestamp());
    					break;
    				default:
    					break;
    			}
    			if(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_MOVE.equals(status)&&SystemEventRecorder.SE_CUSTPROD_PURCHASE==event){
            		CustomerProductDTO dto=BusinessUtility.getCustomerProductDTOBySaID(targetSaID);
            		cp.setServiceAccountID(targetSaID);
            		cp.setLinkToDevice1(dto.getLinkToDevice1());
            		cp.setLinkToDevice2(dto.getLinkToDevice2());
            		
            	}
        		int linkToDevice1=cp.getLinkToDevice1();
            	int linkToDevice2=cp.getLinkToDevice2();
            	String linkToDevice1SerialNo="";
            	String linkToDevice2SerialNo="";
            	String oldStatus="";
        		if(linkToDevice1!=0){
            		linkToDevice1SerialNo=terminalDeviceHome.findByPrimaryKey(new Integer(linkToDevice1)).getSerialNo();
            	}
            	if(linkToDevice2!=0){
            		linkToDevice2SerialNo=terminalDeviceHome.findByPrimaryKey(new Integer(linkToDevice2)).getSerialNo();
            	}
            	//add by chaoqiu on 2007-04-19 begin
            	//把对硬件（设备）和软件产品的取消记录事件的类型区分开：硬件：612；软件：314
            	
            	//默认是软件产品：
            	int eventClass = event;
            	//System.out.println("__________productID="+cp.getProductID());
            	ProductDTO pDto = BusinessUtility.getProductDTOByProductID(cp.getProductID());
            	String productClass = "";
            	if(pDto != null)
            	{
            		productClass = pDto.getProductClass();
            	}
            	//System.out.println("__________productClass="+productClass);
            	if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(productClass))
            	{
            		eventClass = SystemEventRecorder.SE_CUSTDEV_RETURN;
            	}
            	
            	//System.out.println("__________eventClass="+eventClass);
            	//add by chaoqiu on 2007-04-19 end
    			//创建系统事件记录
            	SystemEventRecorder.AddEvent4Product(eventClass,cp.getCustomerID(),
            			cp.getAccountID(),cp.getServiceAccountID(),cp.getProductID(),csiID,cpID.intValue(),linkToDevice2,
            			linkToDevice2SerialNo,linkToDevice1,linkToDevice1SerialNo,0,
    					getOperatorID(),oldStatus,SystemEventRecorder.SE_STATUS_CREATE);
            	list.add(cp);
    		}
    		return list;
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.ERROR, "客户产品定位出错！");
            throw new ServiceException("业客户产品定位出错！");
    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.ERROR, "客户产品查找出错！");
            throw new ServiceException("客户产品查找出错！");
    	}
    	catch (CreateException e) {
    		LogUtility.log(clazz,LogLevel.ERROR, "创建客户产品系统事件出错！");
            throw new ServiceException("创建客户产品系统事件出错！");
		}
    }
    
    public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public ServiceContext getContext() {
		return context;
	}

	public void setContext(ServiceContext context) {
		this.context = context;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getSaID() {
		return saID;
	}

	public void setSaID(int saID) {
		this.saID = saID;
	}

	private int getCSIID(){
		int csiid=0;
    	if(context.get(Service.CSI_ID)!=null){
    		csiid=((Integer)context.get(Service.CSI_ID)).intValue();
    	}
    	else if(context.get(Service.CSI)!=null)
    	{
    		CustServiceInteraction csiEJB=(CustServiceInteraction)context.get(Service.CSI);
    		csiid=csiEJB.getId().intValue();
    	}
    	return csiid;
	}
	
    /**
     * 修改客户产品属性状态
     * @param psID
     */
    private void updateCPPropertyStatus(int psID,String status){
    	try{
    		CpConfigedPropertyHome cpConfigedPropertyHome = HomeLocater.getCpConfigedPropertyHome();
    		CpConfigedProperty cpConfigedProperty=null;
    		Collection cpConfigedPropertyList = cpConfigedPropertyHome.findByPsID(psID);

    		if(cpConfigedPropertyList!=null && cpConfigedPropertyList.size()>0){
    			Iterator itcpConfigedProperty=cpConfigedPropertyList.iterator();
    			while(itcpConfigedProperty.hasNext()){
    				cpConfigedProperty=(CpConfigedProperty)itcpConfigedProperty.next();
    				cpConfigedProperty.setStatus(status);
    			}
    		}
    	}
    	catch(HomeFactoryException e1){
    		LogUtility.log(clazz,LogLevel.WARN,"查找客户产品属性出错");
    	}
    	catch(FinderException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"没有"+ psID + "的客户产品属性！");
    	}
    }
    public CustomerProduct findCp(Integer psid) throws ServiceException{
		try {
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			CustomerProduct cp = cpHome.findByPrimaryKey(psid);
			return cp;
    	}catch(HomeFactoryException e1){
    		LogUtility.log(clazz,LogLevel.ERROR,"修改客户设备来源环境异常"+e1);
    		throw new ServiceException("修改客户设备来源环境异常");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"修改客户设备来源定位设备错！"+e);
    		throw new ServiceException("修改客户设备来源定位设备错!"+e);
		}
    }
    
    public CustomerProduct cpDeviceProvideModify(CustomerProductDTO dto) throws ServiceException{
    	CustomerProduct cp;
    	try{
    	   cp =findCp(new Integer(dto.getPsID()));
    	   context.put(Service.PROCESS_RESULT,cp.getDeviceProvideFlag());
    	   cp.setDeviceProvideFlag(dto.getDeviceProvideFlag());
    	   cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
    	  
    	}catch(ServiceException e){
    		throw new ServiceException(e);
    	}
    	return cp;
    }
    
    public CustomerProduct cpAccountModify(CustomerProductDTO dto) throws ServiceException{
    	CustomerProduct cp;
    	try{
    	   cp =findCp(new Integer(dto.getPsID()));
    	   context.put(Service.PROCESS_RESULT,String.valueOf(cp.getAccountID()));
    	   cp.setAccountID(dto.getAccountID());
    	   cp.setDtLastmod(TimestampUtility.getCurrentTimestamp());
    	   
    	}catch(ServiceException e){
    		throw new ServiceException(e);
    	}
    	return cp;
    }
    
    public void updateCustomerProductAccount(int customerID,int accountID) throws ServiceException{
		try {
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			java.util.Collection pscol=cpHome.findByCustomerID(customerID);
			java.util.Iterator psit=pscol.iterator();
			while (psit.hasNext()){
				CustomerProduct cp=(CustomerProduct) psit.next();
				cp.setAccountID(accountID);
				cp.setDtLastmod(TimestampUtility.getCurrentDate());
			}
    	}catch(HomeFactoryException e1){
    		LogUtility.log(clazz,LogLevel.ERROR,"更新客户产品付费帐户环境异常:"+e1);
    		throw new ServiceException("更新客户产品付费帐户环境异常");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"更新客户产品付费帐户环境异常定位产品错！"+e);
    		throw new ServiceException("更新客户产品付费帐户环境异常定位产品错!"+e);
		}    	
    }
    
    
    /**
     * 开户、增机时,维护设备配对关系
     * @param deviceIDList
     */
    public void deviceMatch(Collection deviceIDList) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "■■deviceMatch(Collection deviceIDList)■" + deviceIDList);
		TerminalDevice currentTerminaDevice = null;
		TerminalDevice terminalDeviceSTB = null;
		TerminalDevice terminalDeviceSC = null;
		try {
			TerminalDeviceHome terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			Iterator itDeviceID = deviceIDList.iterator();
			while (itDeviceID.hasNext()) {
				currentTerminaDevice  = terminalDeviceHome.findByPrimaryKey((Integer)itDeviceID.next());
				if (currentTerminaDevice != null) {
					if (CommonConstDefinition.DEVICECALSS_STB.equals(currentTerminaDevice.getDeviceClass()))
						terminalDeviceSTB = currentTerminaDevice;
					if (CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(currentTerminaDevice.getDeviceClass()))
						terminalDeviceSC = currentTerminaDevice;
				}
			}
			//系统是否管理设备配对,否:返回。
			if(!BusinessUtility.isSystemManagerDeviceMatch()) return ;
			//当前设备是否配对(只检查STB,SC)
			if(terminalDeviceSTB != null) {
				if("Y".equals(terminalDeviceSTB.getMatchFlag())) return ;
			}
			if(terminalDeviceSC != null) {
				if("Y".equals(terminalDeviceSC.getMatchFlag())) return ;
			}
			//是否只包含STB
			if(terminalDeviceSC==null) return ;
			//修改(STB,SC 配对关系)
			if (terminalDeviceSTB != null && terminalDeviceSC != null) {
				//记录机卡配对/解配对过程
		    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
					devicePrematchRecord(terminalDeviceSTB,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_AM);
				
				terminalDeviceSTB.setMatchFlag("Y");
				terminalDeviceSTB.setMatchDeviceID(terminalDeviceSC.getDeviceID().intValue());
				terminalDeviceSTB.setDtLastmod(new Timestamp(System.currentTimeMillis()));
				terminalDeviceSC.setMatchFlag("Y");
				terminalDeviceSC.setMatchDeviceID(terminalDeviceSTB.getDeviceID().intValue());
				terminalDeviceSC.setDtLastmod(new Timestamp(System.currentTimeMillis()));
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "开户、增机时，维护设备配对关系出错！" + e);
			throw new ServiceException("开户、增机时，维护设备配对关系出错!" + e);
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "开户、增机时，维护设备配对关系，定位设备出错！" + e);
			throw new ServiceException("开户、增机时，维护设备配对关系，定位设备出错!" + e);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
	}
    
    
    /**
     * 设备更换时,维护设备配对关系
     * @param saID,newSerialNo
     */
    public void deviceMatch(int saID,String newSerialNo) throws ServiceException {
    	LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系 deviceMatch ■■saID■" + saID + "■■newSerialNo■" + newSerialNo);
    	
    	Map deviceMap = BusinessUtility.getSADeviceIDMapBySAID(saID);
    	TerminalDeviceDTO terminalDeviceDTO = BusinessUtility.getDeviceBySerialNo(newSerialNo);
    	String newDeviceClass = terminalDeviceDTO.getDeviceClass();
    	int newDeviceID = terminalDeviceDTO.getDeviceID();
    	LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■newDeviceID■" + newDeviceID);
    	int linkToDeviceSTB=0;
    	int linkToDeviceSC=0;
    	TerminalDevice newTerminaDevice = null;
		TerminalDevice terminalDeviceSTB = null;
		TerminalDevice terminalDeviceSC = null;
		
		LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■deviceMap■" + deviceMap);
		if(deviceMap==null || deviceMap.size()==0)
			return;
		LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■terminalDeviceDTO■" + terminalDeviceDTO);
		if(terminalDeviceDTO==null)
			return;
		LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■newDeviceClass■" + newDeviceClass);
		if(!CommonConstDefinition.DEVICECALSS_STB.equals(newDeviceClass) && !CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(newDeviceClass))
			return;
		
		LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■deviceMap■" + deviceMap);
		Set productIDSet=deviceMap.keySet();
		LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■productIDSet■" + productIDSet);
		//取得linkToDeviceSTB、linkToDeviceSC
		Iterator itProductID=productIDSet.iterator();
		try {
			TerminalDeviceHome terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			while (itProductID.hasNext()) {
				Integer productID = (Integer) itProductID.next();
				Integer deviceID = (Integer) deviceMap.get(productID);
				String deviceClass = BusinessUtility.getDeviceClassByDeviceID(deviceID);
				if (CommonConstDefinition.DEVICECALSS_STB.equals(deviceClass)) {
					linkToDeviceSTB = deviceID.intValue();
					LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■linkToDeviceSTB■" + linkToDeviceSTB);
				} else if (CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceClass)) {
					linkToDeviceSC = deviceID.intValue();
					LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■linkToDeviceSC■" + linkToDeviceSC);
				}
			}
			LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■系统是否管理设备配对■" + !BusinessUtility.isSystemManagerDeviceMatch());
			//系统是否管理设备配对,否:返回。
			if(!BusinessUtility.isSystemManagerDeviceMatch()) return ;
			//清除掉旧设备的配对关系(包括更换下来的旧设备和使用的旧设备),创建旧的设备(使用中的)和新设备的配对关系
			newTerminaDevice  = terminalDeviceHome.findByPrimaryKey(new Integer(newDeviceID));
			LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■newTerminaDevice■" + newTerminaDevice);
			terminalDeviceSTB  = terminalDeviceHome.findByPrimaryKey(new Integer(linkToDeviceSTB));
			LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■terminalDeviceSTB■" + terminalDeviceSTB);
			terminalDeviceSC  = terminalDeviceHome.findByPrimaryKey(new Integer(linkToDeviceSC));
			LogUtility.log(clazz, LogLevel.DEBUG, "设备更换时,维护设备配对关系  ■■terminalDeviceSC■" + terminalDeviceSC);
			
			if (newTerminaDevice != null && terminalDeviceSTB != null
					&& terminalDeviceSC != null) {
				if (newDeviceClass.equals(terminalDeviceSTB.getDeviceClass())) { // 确定旧设备
					//记录机卡解配对过程
			    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
						devicePrematchRecord(terminalDeviceSTB,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_DM);
			    	//记录机卡配对过程
			    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
						devicePrematchRecord(newTerminaDevice,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_AM);
			    	
					terminalDeviceSTB.setMatchFlag("N");
					terminalDeviceSTB.setMatchDeviceID(0);
					terminalDeviceSTB.setDtLastmod(new Timestamp(System
							.currentTimeMillis()));

					// terminalDeviceSC 和 newTerminaDevice 配对
					newTerminaDevice.setMatchFlag("Y");
					newTerminaDevice.setMatchDeviceID(terminalDeviceSC
							.getDeviceID().intValue());
					newTerminaDevice.setDtLastmod(new Timestamp(System
							.currentTimeMillis()));
					terminalDeviceSC.setMatchFlag("Y");
					terminalDeviceSC.setMatchDeviceID(newTerminaDevice
							.getDeviceID().intValue());
					terminalDeviceSC.setDtLastmod(new Timestamp(System
							.currentTimeMillis()));
				}
				if (newDeviceClass.equals(terminalDeviceSC.getDeviceClass())) { // 确定旧设备
					//记录机卡解配对过程
			    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
						devicePrematchRecord(terminalDeviceSTB,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_DM);
			    	//记录机卡配对过程
			    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
						devicePrematchRecord(terminalDeviceSTB,newTerminaDevice,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_AM);

					terminalDeviceSC.setMatchFlag("N");
					terminalDeviceSC.setMatchDeviceID(0);
					terminalDeviceSC.setDtLastmod(new Timestamp(System.currentTimeMillis()));
										
					// terminalDeviceSTB 和 newTerminaDevice 配对
					newTerminaDevice.setMatchFlag("Y");
					newTerminaDevice.setMatchDeviceID(terminalDeviceSTB
							.getDeviceID().intValue());
					newTerminaDevice.setDtLastmod(new Timestamp(System
							.currentTimeMillis()));
					terminalDeviceSTB.setMatchFlag("Y");
					terminalDeviceSTB.setMatchDeviceID(newTerminaDevice
							.getDeviceID().intValue());
					terminalDeviceSTB.setDtLastmod(new Timestamp(System
							.currentTimeMillis()));
				}
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "设备更换时，维护设备配对关系出错！" + e);
			throw new ServiceException("设备更换时，维护设备配对关系出错!" + e);
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "设备更换时，维护设备配对关系，定位设备出错！" + e);
			throw new ServiceException("设备更换时，维护设备配对关系，定位设备出错!" + e);
		} catch (Exception e){
			e.printStackTrace();
		}
    }
    
    
    /**
     * 退户、销户时,维护设备配对关系
     * @param deviceIDList
     */
    public void unchainDeviceMatch(Collection deviceIDList) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "■■unchainDeviceMatch(Collection deviceIDList)■" + deviceIDList);
		TerminalDevice currentTerminaDevice = null;
		int deviceID = 0;
		TerminalDevice terminalDeviceSTB = null;
		TerminalDevice terminalDeviceSC = null;
		//系统是否管理设备配对,否:返回。
		if(!BusinessUtility.isSystemManagerDeviceMatch()) return ;
		try {
			TerminalDeviceHome terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			Iterator itDeviceID = deviceIDList.iterator();
			while (itDeviceID.hasNext()) {
				deviceID = ((Integer)itDeviceID.next()).intValue();
				if(deviceID != 0){
					currentTerminaDevice  = terminalDeviceHome.findByPrimaryKey(new Integer(deviceID));
				}else{
					continue;
				}
				if(currentTerminaDevice==null){
					continue;
				}
				if (CommonConstDefinition.DEVICECALSS_STB.equals(currentTerminaDevice.getDeviceClass()))
					terminalDeviceSTB = currentTerminaDevice;
				if (CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(currentTerminaDevice.getDeviceClass()))
					terminalDeviceSC = currentTerminaDevice;
				if (CommonConstDefinition.DEVICECALSS_STB.equals(currentTerminaDevice.getDeviceClass()) || CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(currentTerminaDevice.getDeviceClass())){
					//解除设备配对关系(STB,SC)
					currentTerminaDevice.setMatchFlag("N");
					currentTerminaDevice.setMatchDeviceID(0);
					currentTerminaDevice.setDtLastmod(new Timestamp(System.currentTimeMillis()));
				}
			}
			//记录机卡配对/解配对过程
			if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
				devicePrematchRecord(terminalDeviceSTB,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_DM);
			
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "退户、销户时，维护设备配对关系出错！" + e);
			throw new ServiceException("退户、销户时，维护设备配对关系出错!" + e);
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "退户、销户时，维护设备配对关系，定位设备出错！" + e);
			throw new ServiceException("退户、销户时，维护设备配对关系，定位设备出错!" + e);
		}
	}
    
    /**
     * 记录机卡配对/解配对过程
     * @param terminalDeviceSTB,terminalDeviceSC,OperationType
     */
    public void devicePrematchRecord(TerminalDevice terminalDeviceSTB,TerminalDevice terminalDeviceSC,String OperationType) throws ServiceException {
    	LogUtility.log(clazz, LogLevel.DEBUG, "记录机卡配对/解配对过程■terminalDeviceSTB■■" + terminalDeviceSTB.toString() + "■■terminalDeviceSC■" + terminalDeviceSC.toString() + "■■OperationType■" + OperationType);
    	//记录机卡配对/解配对过程
		try{
			if (terminalDeviceSTB != null && terminalDeviceSC != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "记录机卡配对/解配对过程■terminalDeviceSTB■■"+terminalDeviceSTB.getDeviceID() );
				LogUtility.log(clazz, LogLevel.DEBUG, "记录机卡配对/解配对过程■terminalDeviceSC■■"+terminalDeviceSC.getDeviceID() );
				DevicePrematchRecordDTO dto = new DevicePrematchRecordDTO();
				int operatorID = ((Integer)context.get(Service.OPERATIOR_ID)).intValue();
				LogUtility.log(clazz, LogLevel.DEBUG, "记录机卡配对/解配对过程■operatorID■■"+operatorID );
				int orgID = BusinessUtility.FindOrgIDByOperatorID(operatorID);
				LogUtility.log(clazz, LogLevel.DEBUG, "记录机卡配对/解配对过程■orgID■■"+orgID );
				dto.setOperationtype(OperationType);
				dto.setCreateTime(new Timestamp(System.currentTimeMillis()));
				dto.setOpId(operatorID);
				dto.setOrgId(orgID);
				dto.setStbDeviceID(terminalDeviceSTB.getDeviceID().intValue());
				dto.setStbSerialNO(terminalDeviceSTB.getSerialNo());
				dto.setStbDeviceModel(terminalDeviceSTB.getDeviceModel());
				dto.setScDeviceID(terminalDeviceSC.getDeviceID().intValue());
				dto.setScSerialNo(terminalDeviceSC.getSerialNo());
				dto.setScdeviceModel(terminalDeviceSC.getDeviceModel());
				dto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
				dto.setDtCreate(new Timestamp(System.currentTimeMillis()));
				dto.setDtLastmod(new Timestamp(System.currentTimeMillis()));
				LogUtility.log(clazz, LogLevel.DEBUG, "记录机卡配对/解配对过程■dto■■"+dto );
				DevicePrematchRecordHome devicePrematchRecordHome = HomeLocater.getDevicePrematchRecordHome();
				LogUtility.log(clazz, LogLevel.DEBUG, "记录机卡配对/解配对过程■devicePrematchRecordHome■■"+devicePrematchRecordHome );
				devicePrematchRecordHome.create(dto);
			}
    	}catch(CreateException e) {
     		LogUtility.log(clazz, LogLevel.ERROR, "查找机卡配对/解配对过程出错！" + e);
 			throw new ServiceException("查找机卡配对/解配对过程出错！" + e);
     	}
    	catch (HomeFactoryException e) {
    		LogUtility.log(clazz, LogLevel.ERROR, "创建记录机卡配对/解配对过程出错！" + e);
    		throw new ServiceException("创建记录机卡配对/解配对过程出错！" + e);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 协议价续费产品结束时间修改
     * @param psidList
     * @param saID
     */
    public void changCustProdEndTimeByExtend(Collection packagIDList,int saID,int usedMonth,int csiId)throws ServiceException {
    	if(packagIDList==null ||packagIDList.isEmpty()) return ;
    	try {
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			java.util.Collection pscol=cpHome.findByServiceAccountIDAndStatus(saID,"N");
			if(pscol!=null &&!pscol.isEmpty()){
				java.util.Iterator psit=pscol.iterator();
				while (psit.hasNext()){
					CustomerProduct cp=(CustomerProduct) psit.next();
					if(packagIDList.contains(new Integer(cp.getReferPackageID()))){
						if (cp.getEndTime() ==null){
							ProductPackageDTO dto =BusinessUtility.getProductPackageDTOByID(cp.getReferPackageID());
							throw new ServiceException(dto.getPackageName()+"之前是包月产品包，请先结清后再做协议续费！");
						}
						cp.setEndTime(TimestampUtility.getDateEnd(cp.getEndTime(),"M",usedMonth));
						cp.setDtLastmod(TimestampUtility.getCurrentDate());
						
						CsiCustProductInfoDTO csiCustInfoDto =new CsiCustProductInfoDTO();
						csiCustInfoDto.setCsiID(csiId);
						csiCustInfoDto.setAction(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BDP);
						csiCustInfoDto.setCustProductID(cp.getPsID().intValue());
						csiCustInfoDto.setProductID(cp.getProductID());
						csiCustInfoDto.setReferPackageID(cp.getReferPackageID());
						csiCustInfoDto.setReferServiceAccountID(saID);
						BusinessUtility.insertCsiCustProductInfo(csiCustInfoDto);
					}
				}
			}
    	}catch(HomeFactoryException e1){
    		LogUtility.log(clazz,LogLevel.ERROR,"协议价续费产品处理异常:"+e1);
    		throw new ServiceException("协议价续费产品处理异常");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"协议价续费定位产品错"+e);
    		throw new ServiceException("协议价续费定位产品错"+e);
		}	
    } 	
    
    /*
     * 手工维护协议结束时间
     * */
    public void changCustProdEndTime(Collection colPsId) throws ServiceException {
    	if(colPsId==null ||colPsId.isEmpty()) return ;
    	try {
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Iterator psIdItr =colPsId.iterator();
			while (psIdItr.hasNext()){
				CustomerProductDTO cpDto =(CustomerProductDTO)psIdItr.next();
				CustomerProduct cp =cpHome.findByPrimaryKey(new Integer(cpDto.getPsID()));
				cp.setEndTime(cpDto.getEndTime());
			}
    	}catch(HomeFactoryException e1){
    		LogUtility.log(clazz,LogLevel.ERROR,"手工维护协议结束时间:"+e1);
    		throw new ServiceException("手工维护协议结束时间");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"手工维护协议结束时间"+e);
    		throw new ServiceException("手工维护协议结束时间"+e);
		}	
    }
}

