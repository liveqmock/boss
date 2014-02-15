/*
 * Created on 2005-9-21
 *
 * �ͻ���Ʒҵ���������Ҫ�����ͻ���Ʒ��صĲ�����
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
 * ������ͻ���Ʒ��ص�ҵ�����
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
    //�ͻ����Ʒѹ��� add by yangchen 2008/07/23
    private Map customerBillingRuleMap; 
    
    public CustomerProductService() {};
	//��Ʒ�ɷ�ʱ�䳤�� ������Ϊ��λ
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
	 * ���캯��
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

    	LogUtility.log(clazz,LogLevel.DEBUG,"���봴���ͻ���Ʒ����");
    	LogUtility.log(clazz,LogLevel.DEBUG,"����Ĳ�������Ʒ["+productList+"]��ҵ���ʻ�["+saID+"]��" +
    			"�ͻ�id[" + customerID + "] �ʻ�ID[" + accountID +"]");

    	//��07-4-11���ӷ���ֵ,�����ײ�ת��.
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
        	LogUtility.log(clazz, LogLevel.DEBUG,"�����ͻ���Ʒʱcsicustproductinfolist,groupNo:"+groupNo+"sheafNo:"+sheafNo);
        }catch(Exception e) {
        	LogUtility.log(clazz, LogLevel.ERROR, e);
        	throw new ServiceException("����������ز�Ʒ����csiid-" + csiid);
        }

        //���ҳ���Ʒ�е�Ӳ���豸,��������ת
        Collection deviceIDList=new ArrayList();
        Iterator itCSIProductInfo=csiProductInfoList.iterator();
        while(itCSIProductInfo.hasNext()){
        	CsiCustProductInfo csiProduct = (CsiCustProductInfo)itCSIProductInfo.next();
    		LogUtility.log(clazz,LogLevel.DEBUG,"�����ͻ���Ʒ����ƷID= "+csiProduct.getCustProductID());
        	if(csiProduct.getReferDeviceID()>0 && productIDList.contains(new Integer(csiProduct.getProductID()))){
        		LogUtility.log(clazz,LogLevel.DEBUG,"�ҵ�һ��Ӳ���豸���豸ID= "+csiProduct.getReferDeviceID());
        		deviceMap.put(new Integer(csiProduct.getProductID()),new Integer(csiProduct.getReferDeviceID()));
        		deviceIDList.add(new Integer(csiProduct.getReferDeviceID()));
        		/*****************************add by yangchen 2008/06/20 start ***************************************/
        		TerminalDeviceDTO deviceForRelease=BusinessUtility.getDeviceByDeviceID(csiProduct.getReferDeviceID());
        		if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceForRelease.getDeviceClass())
        		   || "Y".equals(deviceForRelease.getPreAuthorization()) //add by david.Yang ���ڻ���һ����豸	
        		){
        			DeviceStockService deviceStockService=new DeviceStockService(context);
        			deviceStockService.loadDeviceList(csiProduct.getReferDeviceID()); //add by david.Yang ����豸��Ԥ��Ȩ��־
        			deviceStockService.devPreauthTrans(csiProduct.getReferDeviceID());
        		}
        		/*****************************add by yangchen 2008/06/20 end ***************************************/
        	}
        }
        //�����豸��ת
        try{
        	//��ת
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
        	LogUtility.log(clazz,LogLevel.WARN,"Ӳ���豸��תʧ��"+e);
        	throw new ServiceException("Ӳ���豸��תʧ��");
        }
        //ά���豸��Թ�ϵ.
        deviceMatch(deviceIDList);
     
    	//��ʼ�����ͻ���Ʒ
    	Iterator itCP=productList.iterator();
    	HashMap custAndCsiProdMap=new HashMap();
    	HashMap campaignAndCcidMap=new HashMap();
    	while(itCP.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itCP.next();
    		try{
    			//�����ͻ���Ʒ
    			CustomerProduct custProduct=createCustProductByCsiCustProductInfo(cpDTO,csiProductInfoList,status,custAndCsiProdMap);
            	cpList.add(custProduct);
    			//����������ҵ���ʻ�ʱ�����豸���豸��Դ
    			if(custProduct.getDeviceID()!=0)
    				custProduct.setDeviceProvideFlag(CommonConstDefinition.CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_B);
    			CsiCustProductInfo csiProductLocal = (CsiCustProductInfo)custAndCsiProdMap.get(custProduct.getPsID());
           	
                //������Ʒ������Ϣ
                LogUtility.log(clazz,LogLevel.DEBUG,"������Ʒ������Ϣ");
                
                //productPropertyMapΪȫ���ύ �Ĳ�Ʒ����map,��productIDΪKEY
                if (productPropertyMap != null && productPropertyMap.isEmpty() == false) {
                    LogUtility.log(clazz,LogLevel.DEBUG,"�ڴ���һ����Ʒ������ϢʱproductPropertyMap\n��"+productPropertyMap);
                    if(productPropertyMap.containsKey(Integer.toString(cpDTO.getProductID())))
                	{
                        //tempMapΪ��ǰ��Ʒ��Ӧ��propertyMap,��propertyCodeΪKEY
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
                				LogUtility.log(clazz,LogLevel.DEBUG,"��Ҫд���CpConfigedPropertyDTOΪ��"+dto);
                                cpPropertyHome.create(dto);
  
                			}
                		}
                	}
                }
    		}
    		catch(Exception e){
    			e.printStackTrace();
    			LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("�����ͻ���Ʒ��¼���ִ���");
    		}
    	}
    	

    	//�ѿͻ���Ʒ����ServiceTcontext��
    	
    	context.put(Service.CUSTOMER_PRODUCT_LIST,customerProductList);
    	//��linkToDevice1,linkToDevice2��ֵ
    	try{
    		productLinkToDevice();
    	}
    	catch(Exception e){
    		LogUtility.log(clazz,LogLevel.WARN,"�����Ʒ���ӵ�Ӳ����Ʒ����"+e);
    		throw new ServiceException("�����Ʒ���ӵ�Ӳ����Ʒ����");
    	}
    	//����ϵͳ�¼�
    	try{
    		createSystemEventRecord(customerID,accountID,saID,csiid);
    	}
    	catch(Exception e){
    		LogUtility.log(clazz,LogLevel.WARN,"����ϵͳ�¼�ʧ�ܣ��������£�"+e);
    		throw new ServiceException("����ϵͳ�¼�ʧ�ܣ��������£�"+e);
    	}
    	LogUtility.log(clazz,LogLevel.DEBUG,"���������ͻ���Ʒ����");
    	return cpList;
    }
    /**
     * �����ͻ���Ʒ,������������Ʒ��ʱ��ʹ��
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
     * �����������Ӳ�Ʒ���Ĵ���,add.by david.Yang
     * @param productList
     * @param saID
     * @param customerID
     * @param accountID
     * @param status
     * @throws ServiceException
     */
    public void create(Collection productList, int accountID, String status,int groupNo,int sheafNo) throws ServiceException {
    	LogUtility.log(clazz,LogLevel.DEBUG,"���봴���ͻ���Ʒ����");
    	
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
        	throw new ServiceException("����������ز�Ʒ����csiid-" + csiid);
        }
         //��ʼ�����ͻ���Ʒ
    	Iterator itCP=productList.iterator();
    	while(itCP.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itCP.next();
    		try{
    			//�����ͻ���Ʒ
    			CustomerProduct custProduct=createCustProductByCsiCustProductInfo(cpDTO,csiProductInfoList,status,custAndCsiProdMap);
    		} catch(Exception e){
        		e.printStackTrace();
        		LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("�����ͻ���Ʒ��¼���ִ���");
        	}
    	}	
    	
    	context.put(Service.CUSTOMER_PRODUCT_LIST,customerProductList);
    	//��linkToDevice1,linkToDevice2��ֵ
    	try{
    		productLinkToDevice();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		LogUtility.log(clazz,LogLevel.WARN,"�����Ʒ���ӵ�Ӳ����Ʒ����"+e);
    		throw new ServiceException("�����Ʒ���ӵ�Ӳ����Ʒ����");
    	}
    	//����ϵͳ�¼�
    	 try{
    	   	createSystemEventRecord(customerID,accountID,saID,csiid);
    	 }
    	 catch(Exception e){
    	  	LogUtility.log(clazz,LogLevel.WARN,"����ϵͳ�¼�ʧ�ܣ��������£�"+e);
    	   	throw new ServiceException("����ϵͳ�¼�ʧ�ܣ��������£�"+e);
    	 }
    	 LogUtility.log(clazz,LogLevel.DEBUG,"���������ͻ���Ʒ����");	
 
    }
    
    
    /**
     * �����ͻ���Ʒ(���ڿͻ�������������������������Ʒʱ����ֱ��ʹ�ã�������������Ϸ�װ�ķ���)
     * @param productList ����ʽΪCustomerProductDTO��List
     * @param campaignList  ��
     * @param saID ��ҵ���ʻ�ID
     * @param customerID �� �ͻ�ID
     * @param accountID ���ʻ�ID
     * @param packageCampaignMap  ����Ʒ����Ӧ���Ż�
     * @throws ServiceException
     */
    public ArrayList create(Collection productList, int saID,
    		int customerID,int accountID, Map productPropertyMap,String status) throws ServiceException {

    	LogUtility.log(clazz,LogLevel.DEBUG,"���봴���ͻ���Ʒ����");
    	LogUtility.log(clazz,LogLevel.DEBUG,"����Ĳ�������Ʒ["+productList+"]��ҵ���ʻ�["+saID+"]��" +
    			"�ͻ�id[" + customerID + "] �ʻ�ID[" + accountID +"]");

    	//��07-4-11���ӷ���ֵ,�����ײ�ת��.
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
        //�������ͺͰ�װ����
/*        String csiType = "";
        String installationType="";

        //ȷ���Ƿ����Ż�
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
        	throw new ServiceException("����������ز�Ʒ����csiid-" + csiid);
        }
        
        //���ҳ���Ʒ�е�Ӳ���豸,��������ת
        Collection deviceIDList=new ArrayList();
        Iterator itCSIProductInfo=csiProductInfoList.iterator();
        while(itCSIProductInfo.hasNext()){
        	CsiCustProductInfo csiProduct = (CsiCustProductInfo)itCSIProductInfo.next();
        	if(csiProduct.getReferDeviceID()>0 && productIDList.contains(new Integer(csiProduct.getProductID()))){
        		LogUtility.log(clazz,LogLevel.DEBUG,"�ҵ�һ��Ӳ���豸���豸ID= "+csiProduct.getReferDeviceID());
        		deviceMap.put(new Integer(csiProduct.getProductID()),new Integer(csiProduct.getReferDeviceID()));
        		deviceIDList.add(new Integer(csiProduct.getReferDeviceID()));
        		/*****************************add by yangchen 2008/06/20 start ***************************************/
        		TerminalDeviceDTO deviceForRelease=BusinessUtility.getDeviceByDeviceID(csiProduct.getReferDeviceID());
        	
        		if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceForRelease.getDeviceClass())
        				|| "Y".equals(deviceForRelease.getPreAuthorization()) //add by david.Yang ���ڻ���һ����豸		
        		){
        			DeviceStockService deviceStockService=new DeviceStockService(context);
        			devicelist=deviceStockService.loadDeviceList(csiProduct.getReferDeviceID());
        			deviceStockService.devPreauthTrans(csiProduct.getReferDeviceID());
        		}
        		/*****************************add by yangchen 2008/06/20 end ***************************************/
        	}
        }
        
    	//���豸��תǰ,ά���豸��Թ�ϵ 2007-12-21
        try{
        	Collection deviceIDListMatch=new ArrayList();
        	deviceIDListMatch.addAll(deviceIDList);
        	if(deviceIDListMatch.size()==1){   //����ʱ�����Ѿ����ڵĻ�����deviceID�ӽ���,����ά����Թ�ϵ
        		deviceIDListMatch.addAll(BusinessUtility.getDeviceListBySaID(saID));
        	}
        	deviceMatch(deviceIDListMatch);
        }catch(Exception e){
        	e.printStackTrace();
        	LogUtility.log(clazz,LogLevel.WARN,"ά���豸��Թ�ϵʧ��"+e.getMessage());
        	throw new ServiceException("ά���豸��Թ�ϵʧ��");
        }
        
        //�����豸��ת
        try{
        	//��ת
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
        	LogUtility.log(clazz,LogLevel.WARN,"Ӳ���豸��תʧ��"+e);
        	throw new ServiceException("Ӳ���豸��תʧ��");
        }
        
     
    	//��ʼ�����ͻ���Ʒ
    	Iterator itCP=productList.iterator();
    	HashMap custAndCsiProdMap=new HashMap();
    	HashMap campaignAndCcidMap=new HashMap();
    	while(itCP.hasNext()){
    		CustomerProductDTO cpDTO=(CustomerProductDTO)itCP.next();
    		try{
    			//�����ͻ���Ʒ
    			CustomerProduct custProduct=createCustProductByCsiCustProductInfo(cpDTO,csiProductInfoList,status,custAndCsiProdMap);
            	cpList.add(custProduct);
    			//����������ҵ���ʻ�ʱ�����豸���豸��Դ
    			if(custProduct.getDeviceID()!=0)
    				custProduct.setDeviceProvideFlag(CommonConstDefinition.CUSTOMERPRODUCT_DEVICEPROVIDEFLAG_B);
    			CsiCustProductInfo csiProductLocal = (CsiCustProductInfo)custAndCsiProdMap.get(custProduct.getPsID());
           	
                //������Ʒ������Ϣ
                LogUtility.log(clazz,LogLevel.DEBUG,"������Ʒ������Ϣ");
                
                //productPropertyMapΪȫ���ύ �Ĳ�Ʒ����map,��productIDΪKEY
                if (productPropertyMap != null && productPropertyMap.isEmpty() == false) {
                    LogUtility.log(clazz,LogLevel.DEBUG,"�ڴ���һ����Ʒ������ϢʱproductPropertyMap\n��"+productPropertyMap);
                    if(productPropertyMap.containsKey(Integer.toString(cpDTO.getProductID())))
                	{
                        //tempMapΪ��ǰ��Ʒ��Ӧ��propertyMap,��propertyCodeΪKEY
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
                				LogUtility.log(clazz,LogLevel.DEBUG,"��Ҫд���CpConfigedPropertyDTOΪ��"+dto);
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
						//��������ģ��û�ж��壬���������ֵ
						billRuleDTO.setReferType("M");
						billRuleDTO.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
						billRuleDTO.setComments("����ʱָ������");
						billRuleService.createCustBillRuleServiceForOpen(billRuleDTO);
					}
				}
			    /*********************add by yangchen 2008/07/23 end***************************************************/
    		}
    		catch(Exception e){
    			e.printStackTrace();
    			LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new ServiceException("�����ͻ���Ʒ��¼���ִ���");
    		}
    	}
    	

    	//�ѿͻ���Ʒ����ServiceTcontext��
    	
    	context.put(Service.CUSTOMER_PRODUCT_LIST,customerProductList);
    	//��linkToDevice1,linkToDevice2��ֵ
    	try{
    		productLinkToDevice();
    	}
    	catch(Exception e){
    		LogUtility.log(clazz,LogLevel.WARN,"�����Ʒ���ӵ�Ӳ����Ʒ����"+e);
    		throw new ServiceException("�����Ʒ���ӵ�Ӳ����Ʒ����");
    	}
    	//����ϵͳ�¼�
    	try{
    		createSystemEventRecord(customerID,accountID,saID,csiid);
    	}
    	catch(Exception e){
    		LogUtility.log(clazz,LogLevel.WARN,"����ϵͳ�¼�ʧ�ܣ��������£�"+e);
    		throw new ServiceException("����ϵͳ�¼�ʧ�ܣ��������£�"+e);
    	}
    	LogUtility.log(clazz,LogLevel.DEBUG,"���������ͻ���Ʒ����");
    	return cpList;
    }
    
    /**
     * ��ͣ�ͻ���Ʒ��
     * @param custProductIDList����װ��ʽΪproductID �� Integer����
     * @throws ServiceException
     */
    public ArrayList pauseCustomerProduct(Collection custProductIDList) throws ServiceException {
    	//���²�Ʒ״̬����¼�¼�����־
    	ArrayList list=updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND,
    			SystemEventRecorder.SE_CUSTPROD_PAUSE,"��Ʒ��ͣ","��Ʒ��ͣ���ͻ���ƷID��");
    	
    	Iterator itCP=custProductIDList.iterator();
		while(itCP.hasNext()){
			int cpID=((Integer)itCP.next()).intValue();
			//�޸Ŀͻ���Ʒ����״̬
			updateCPPropertyStatus(cpID,CommonConstDefinition.GENERALSTATUS_INVALIDATE);
		}
		return list;
    }

    /**
     * �ָ��ͻ���Ʒ
     * @param custProductIDList : ��װ��ʽΪproductID �� Integer����
     * @throws ServiceException
     */
    public void resumeCustomerProduct(Collection custProductIDList) throws ServiceException {
    	//���²�Ʒ״̬����¼�¼�����־
    	updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL,
    			SystemEventRecorder.SE_CUSTPROD_RESUME,"��Ʒ�ָ�","��Ʒ�ָ����ͻ���ƷID��");
    	
    	Iterator itCP=custProductIDList.iterator();
		while(itCP.hasNext()){
			int cpID=((Integer)itCP.next()).intValue();
			//�޸Ŀͻ���Ʒ����״̬
			updateCPPropertyStatus(cpID,CommonConstDefinition.GENERALSTATUS_VALIDATE);
		}
    }

    /**
     * ����ȡ���û���Ʒ����¼����¼���ȡ����Ʒ��ش������޸Ĳ�Ʒ״̬
     * @param custProductIDList
     * @throws ServiceException
     */
/*    
    public void cancelcustProductOnly(Collection custProductIDList)throws ServiceException{
    	//���²�Ʒ״̬����¼�¼�����־
    	updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,
    			SystemEventRecorder.SE_CUSTPROD_CANCEL,"��Ʒȡ��","��Ʒȡ�����ͻ���ƷID��");
    	
    	Iterator itCP=custProductIDList.iterator();
		while(itCP.hasNext()){
			int cpID=((Integer)itCP.next()).intValue();
			//�Բ�Ʒ����Ĳ�����Ӱ�쵽�ͻ�����,����ֱ��ע�͵�----� 2007/3/21 
			//�޸��Ż�״̬
			//updateCampaign(cpID,CommonConstDefinition.CUSTOMERCAMPAIGNSTATUS_INVALID,false);
			
			//�޸Ŀͻ���Ʒ����״̬
			updateCPPropertyStatus(cpID,CommonConstDefinition.GENERALSTATUS_INVALIDATE);
		}
    }
*/
    /**
     * 2007-2-27���ӵ����ط���,����������ȡ����ƷʱӲ���豸�Ƿ���Ҫ������ת.
     * @param custProductIDList
     * @throws ServiceException
     */
/*
    public void cancelCustomerProduct(Collection custProductIDList) throws ServiceException {
    	cancelCustomerProduct(custProductIDList,true);
    }
*/
    /**
     * ȡ���û���Ʒ����¼����¼��������豸��ת��ȡ����Ʒ��ش������޸Ĳ�Ʒ״̬
     * @param custProductIDList��custProductIDListΪ��װCPID��Integer����
     * @throws ServiceException
     */
    public void moveCustomerProduct(Collection custProductIDList,boolean returnDevice) throws ServiceException {
    	//���²�Ʒ״̬����¼�¼�����־   
    	ArrayList  custproductList=updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_MOVE,
    			SystemEventRecorder.SE_CUSTPROD_CANCEL,"��Ʒȡ��","��Ʒȡ�����ͻ���ƷID��");
    	           updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_MOVE,
    			SystemEventRecorder.SE_CUSTPROD_PURCHASE,"��Ʒ����","��Ʒ���򣬿ͻ���ƷID��");
    	context.put(Service.CUSTOMER_PRODUCT_LIST,custproductList);
    }
    /**
     * ȡ���û���Ʒ����¼����¼��������豸��ת��ȡ����Ʒ��ش������޸Ĳ�Ʒ״̬
     * @param custProductIDList��custProductIDListΪ��װCPID��Integer����
     * @throws ServiceException
     */
    public void cancelCustomerProduct(Collection custProductIDList,boolean returnDevice) throws ServiceException {
    	//���²�Ʒ״̬����¼�¼�����־
    	ArrayList  custproductList=updateCustomerProductStatus(custProductIDList,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL,
    			SystemEventRecorder.SE_CUSTPROD_CANCEL,"��Ʒȡ��","��Ʒȡ�����ͻ���ƷID��");

    	context.put(Service.CUSTOMER_PRODUCT_LIST,custproductList);
    	//Ӳ���豸ID
    	Collection deviceIDList=new ArrayList();

    	//ȡ����ز�Ʒ����
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
        		//�޸Ŀͻ���Ʒ����״̬
    			updateCPPropertyStatus(cp.getPsID().intValue(),CommonConstDefinition.GENERALSTATUS_INVALIDATE);
        	}
        	//��2007-2-27�������,����ȡ��һ��ͻ���Ʒʱ,ͬʱ�޸ı�ȡ��Ӳ����Ʒ��linktodevice1/linktodevice2
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
        	//��2007-2-27�޸�����,�����豸��ת
        	if(returnDevice){
        		transferDevice(deviceIDList,0);
        	}else{
        		//����ת��ʱ���޸��豸��״̬
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
        			LogUtility.log(clazz,LogLevel.WARN,"�����豸��ת����");
            		throw new ServiceException("�����豸��ת����");
        		}
            }
    	}
    	catch(HomeFactoryException e1){
    		LogUtility.log(clazz,LogLevel.WARN,"�ͻ���Ʒ��λ����");
    		throw new ServiceException("�ͻ���Ʒ��λ����");
    	}
    	catch(FinderException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"�ͻ���Ʒ���ҳ���");
    		throw new ServiceException("�ͻ���Ʒ���ҳ�����ȷ����Ʒ�Ƿ���Ч��");
    	}

    	
    }

    /**
     * ����actionType���Ŀͻ���Ʒ��Ϣ
     * ֧�ֵ�actionTypeΪEJBEvent.PAYBYBILL��EJBEvent.REGISTER_INSTALLATION_INFO
     * ��actionTypeΪEJBEvent.PAYBYBILLʱ��csiID����ʹ��
     * ��actionTypeΪEJBEvent.REGISTER_INSTALLATION_INFO��csiIDΪ����ID
     * @param csiID
     * @param actionType
     */
    public void updateCustomerProduct(int csiID, int actionType)
			throws ServiceException {

		if (csiID == 0 || actionType == 0)
			throw new ServiceException("���¿ͻ���Ʒ��������!");

		// �����޸�ҵ���ʻ�״̬��
		HashSet saSet = new HashSet();
		// �ͻ���Ʒ�б�
		Collection productList = null;
		// �ͻ���Ʒ
		CustomerProductHome cpHome = null;
		// ҵ���ʻ�
		ServiceAccountHome saHome = null;
		ServiceAccount sa = null;
		CustomerProduct cp = null;

		try {
			// ��actionTypeΪEJBEvent.REGISTER_INSTALLATION_INFO��csiIDΪ����ID��
			// (¼�밲װ��Ϣ),����ɹ�,�޸Ŀͻ���Ʒ״̬Ϊ����,ͬʱ�޸ĸò�Ʒ�µ�ҵ���ʻ���״̬Ϊ����
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

					// �޸�ҵ���ʻ�Ϊ����
					if (saSet.add(new Integer(cp.getServiceAccountID()))) {
						sa = saHome.findByPrimaryKey(new Integer(cp
								.getServiceAccountID()));
						sa
								.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
						sa.setDtLastmod(TimestampUtility.getCurrentTimestamp());
					}
				}
			}

//			// ��actionTypeΪEJBEvent.PAYBYBILLʱ���޸Ŀͻ����ʻ��µ�Ϊϵͳ��ͣ�Ĳ�Ʒ��״̬����Ϊ���������޸����ҵ���ʻ�Ϊ����
//			if (actionType == EJBEvent.PAYBYBILL) {
//
//				if (this.accountId == 0)
//					throw new ServiceException("���������û��ʻ������ڣ�");
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
//					// �����ϵͳ��ͣ����ָ�����
//					if (CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_SYSTEMSUSPEND
//							.equals(cp.getStatus())) {
//						cp
//								.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
//						cp.setActivatetime(TimestampUtility
//								.getCurrentTimestamp());
//					}
//					// �޸�ҵ���ʻ�Ϊ����,��2008-1-2,ֻ��ҵ���ʻ� Ϊϵͳ��ͣ,�Żָ�����.
//					if (saSet.add(new Integer(cp.getServiceAccountID()))) {
//						sa = saHome.findByPrimaryKey(new Integer(cp
//								.getServiceAccountID()));
//						LogUtility.log(clazz, LogLevel.DEBUG, "����ҵ���ʻ�״̬��"
//								+ sa.getServiceAccountID());
//						LogUtility.log(clazz, LogLevel.DEBUG, "ҵ���ʻ�״̬1"
//								+ sa.getStatus());
//						if (CommonConstDefinition.SERVICEACCOUNT_STATUS_SYSTEMSUSPEND
//								.equals(sa.getStatus())) {
//							sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
//							sa.setDtLastmod(TimestampUtility
//									.getCurrentTimestamp());
//							LogUtility.log(clazz, LogLevel.DEBUG, "ҵ���ʻ�״̬2"
//									+ sa.getStatus());
//						}
//					}
//				}
//			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ��λ����");
			throw new ServiceException("���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ��λ����");
		} catch (FinderException e1) {
			LogUtility.log(clazz, LogLevel.WARN, "���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ���ҳ���");
			throw new ServiceException("���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ���ҳ���");
		}
	}

    /**
     * �ʻ�֧��ʱ,���¿ͻ���Ʒ��ҵ���ʻ� ״̬ ,����������¼�.�˷���ֻ�������ʵ�״̬���ʱʹ��.
	 * ����actionTtype��psIDList������ز���
	 * ��08-1-7���Ӷ�ҵ���ʻ� ��ز���.
	 * @param psIDList
	 * @param actionType
	 * @throws ServiceException
	 */
    public static void updateCustomerProduct(Collection psIDList,int actionType,int operatorID) throws ServiceException{
//    	if(psIDList==null || psIDList.isEmpty() || actionType==0)
    	//07-2-05���޸�,����Ĳ�������actionType����,���Բ�����Ҫ���.
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

    					//�õ��ͻ��豸
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
    					//�޷��õ�CSIID��ֱ����Ϊ0
    				    SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTPROD_RESUME,cp.getCustomerID(),
    					    		cp.getAccountID(),cp.getServiceAccountID(),cp.getProductID(),0,
    														 psID.intValue(),linkToDevice2,device2SerialNo,
    														 linkToDevice1,device1SerialNo,0,operatorID,
    														 cp.getStatus(),SystemEventRecorder.SE_STATUS_CREATE);
    					}catch(CreateException e3){
    						LogUtility.log(clazz,LogLevel.WARN,"���¿ͻ���Ʒ��ԭ�򣺴���ϵͳ�¼�����"+e3);
    						throw new ServiceException("���¿ͻ���Ʒ��ԭ�򣺴���ϵͳ�¼�����");
    					}
 
    			 }
    			 cp.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    			 cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
    			 cp.setDtLastmod(TimestampUtility.getCurrentDate());
    		}
    		
			LogUtility.log(clazz, LogLevel.DEBUG, "����ҵ���ʻ�::" + saSet);
    		//�޸�ҵ���ʻ� ״̬,����� ҵ���ʻ� �ָ��¼�.
    		for(Iterator it=saSet.iterator();it.hasNext();){
    			Integer saId=(Integer) it.next();
    			sa = saHome.findByPrimaryKey(saId);
				LogUtility.log(clazz, LogLevel.DEBUG, "����ҵ���ʻ�״̬��"
						+ sa.getServiceAccountID());
				LogUtility.log(clazz, LogLevel.DEBUG, "ҵ���ʻ�״̬1"
						+ sa.getStatus());
				if (CommonConstDefinition.SERVICEACCOUNT_STATUS_SYSTEMSUSPEND
						.equals(sa.getStatus())) {
					sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
					sa.setDtLastmod(TimestampUtility
							.getCurrentTimestamp());
					LogUtility.log(clazz, LogLevel.DEBUG, "ҵ���ʻ�״̬2"
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
						LogUtility.log(clazz,LogLevel.WARN,"����ҵ���ʻ�������ϵͳ�¼�����"+e3);
						throw new ServiceException("����ҵ���ʻ�������ϵͳ�¼�����");
					}
				}
    		}
    	}
    	catch(HomeFactoryException e1){
    		  LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ��λ����");
        	  throw new ServiceException("���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ��λ����");
    	}
    	catch(FinderException e2){
    	      LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ���ҳ���");
        	  throw new ServiceException("���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ���ҳ���");
    	}
    }

    /**
     * ����DTO�޸Ŀͻ���Ʒ��Ϣ
     * !!!�����������,EJB�����DTO�����activeTime��������ͳһ,autoUpdate�����.
     * @param dto
     */
    public void updateCustomerProduct(CustomerProductDTO dto)throws ServiceException{
    	if(dto==null||dto.getPsID()==0)
    		throw new ServiceException("�������󣺲���Ϊ�գ�");

    	try{
    		CustomerProductHome custProductHome=HomeLocater.getCustomerProductHome();
    		CustomerProduct cp=custProductHome.findByPrimaryKey(new Integer(dto.getPsID()));
    		//�޸Ŀͻ���Ʒ��Ϣ
    		if(cp.ejbUpdate(dto)==-1){
    			LogUtility.log(clazz,LogLevel.WARN,"���¿ͻ���Ʒ��Ϣ����ԭ��cp.ejbUpdate(dto)==-1");
	    		throw new ServiceException("���¿ͻ���Ʒ��Ϣ����");
    		}

    		//��¼ϵͳ��־
    		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
	    			PublicService.getCurrentOperatorID(context), dto.getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "�޸Ŀͻ���Ʒ", "�޸Ŀͻ���Ʒ����ƷID��" +dto.getPsID(),
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ϣ����ԭ�򣺿ͻ���λ����");
    		throw new ServiceException("���Ŀͻ���Ϣ����ԭ�򣺿ͻ���λ����");

    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ϣ����ԭ�򣺿ͻ����ҳ���");
    		throw new ServiceException("���Ŀͻ���Ϣ����ԭ�򣺿ͻ����ҳ���");
    	}
    }
    /**���¿ͻ���Ʒ,
     * 
     * @param psid
     * @param accountID
     * @param serviceAccountID
     * @param referPackageID
     * @throws ServiceException
     */
    public void updateCustomerProduct(int psid,int accountID,int serviceAccountID,int referPackageID)throws ServiceException{
    	if(psid==0||accountID==0||serviceAccountID==0)
    		throw new ServiceException("�������󣺲���Ϊ�գ�");

    	try{
    		CustomerProductHome custProductHome=HomeLocater.getCustomerProductHome();
    		CustomerProduct cp=custProductHome.findByPrimaryKey(new Integer(psid));
    		//�޸Ŀͻ���Ʒ��Ϣ
    		
    		cp.setAccountID(accountID);
    		cp.setServiceAccountID(serviceAccountID);
    		cp.setReferPackageID(referPackageID);
    		cp.setDtLastmod(TimestampUtility.getCurrentDate());
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ��λ����");
    		throw new ServiceException("���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ��λ����");

    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ���ҳ���");
    		throw new ServiceException("���Ŀͻ���Ʒ��Ϣ����ԭ�򣺿ͻ���Ʒ���ҳ���");
    	}
    }
	/**
	 * �����Ʒ������
	 * @param actionType����/���� :EJBEvent.UPGRADE/EJBEvent.DOWNGRADE
	 * @param psID ���ͻ���ƷID
	 * @param productID ��Ŀ���ƷID
	 * @throws ServiceException
	 */
	public void alterCustomerProduct(int actionType,int psID,int productID)throws ServiceException{
		//�������
		if(actionType==0||psID==0||productID==0)
		{
			LogUtility.log(clazz, LogLevel.WARN, "alterCustomerProduct(int actionType,int psID,int productID)��������");
			throw new ServiceException("��������");
		}
		if(actionType!=EJBEvent.UPGRADE && actionType!=EJBEvent.DOWNGRADE){
			LogUtility.log(clazz, LogLevel.WARN, "�ͻ���Ʒ��������δ֪");
			throw new ServiceException("�ͻ���Ʒ��������δ֪��");
		}
		int saID=0;
		int oldProductID=0;
		try{
			//�ҵ���Ҫ����/�����Ŀͻ���Ʒ
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(psID));
			int linkToDevice1=cp.getLinkToDevice1();
			int linkToDevice2=cp.getLinkToDevice2();

			saID=cp.getServiceAccountID();
			oldProductID=cp.getProductID();
			cp.setProductID(productID);
			cp.setDtLastmod(TimestampUtility.getCurrentDate());
			
			//�õ��ͻ��豸
			String device1SerialNo="";
			String device2SerialNo="";
			if(linkToDevice1>0){
				device1SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice1));
			}
			if(linkToDevice2>0){
				device2SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice2));
			}

		 
//			//��������id
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
			LogUtility.log(clazz,LogLevel.WARN,"��Ʒ����������ԭ�򣺲�Ʒ��λ����"+e);
			throw new ServiceException("��Ʒ����������ԭ�򣺿ͻ���λ����");
		}
		catch(FinderException e2){
			LogUtility.log(clazz,LogLevel.WARN,"��Ʒ����������ԭ�򣺲�Ʒ���ҳ���"+e2);
			throw new ServiceException("��Ʒ����������ԭ�򣺲�Ʒ���ҳ���");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.WARN,"��Ʒ����������ԭ�򣺴���ϵͳ�¼�����"+e3);
			throw new ServiceException("��Ʒ����������ԭ�򣺴���ϵͳ�¼�����");
		}
	}
    /**
     * ��Ʒ������
     * ҵ�����
     * 1��������/�������豸��״̬��Ϊ[���]��ʹ�õ��豸��״̬��Ϊ[ʹ��]
     * 2��һ��ֻ�ܽ���һ��Ӳ���豸������
     * @param actionType����/���� :EJBEvent.UPGRADE/EJBEvent.DOWNGRADE
     * @param psID ���ͻ���ƷID
     * @param productID ��Ŀ���ƷID
     * @param deviceID �� Ӳ���豸ID
     * @throws ServiceException
     */
    public void alterCustomerProduct(int actionType,int psID,int productID,int deviceID)throws ServiceException{
    	//�������
    	if(actionType==0||psID==0||productID==0||deviceID==0)
    	{
    		LogUtility.log(clazz, LogLevel.WARN, "alterCustomerProduct(int actionType,int psID,int productID,int deviceID)��������");
			throw new ServiceException("��������");
    	}
    	if(actionType!=EJBEvent.UPGRADE && actionType!=EJBEvent.DOWNGRADE &&actionType!=EJBEvent.DEVICESWAP){
    		LogUtility.log(clazz, LogLevel.WARN, "�ͻ���Ʒ��������δ֪");
			throw new ServiceException("�ͻ���Ʒ��������δ֪��");
    	}
        
    	// �ֱ�Դ�CM��STB,SC���豸����
    	// CMֻ�ü����matchFlag ,matchdevieceID,status�����
    	// STB,SC��Ҫ�����T_DEVICEMATCHTOPRODUCT�Ĺ�ϵ
    	// add by david.Yang
    	TerminalDeviceDTO deviceInfo =BusinessUtility.getDeviceByDeviceID(deviceID);
    	if (deviceInfo.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
    		if(!BusinessUtility.checkCmInfoByDeviceID(deviceID)){
     		   LogUtility.log(clazz, LogLevel.WARN, "��Ʒ���豸��ƥ���û�д˴����豸��");
 			   throw new ServiceException("��Ʒ���豸��ƥ���û�д˴����豸��");
     	    }
    	}else{
    	    //����Ʒ���豸֮���ƥ���ϵ,���ﱣ֤�����productIDΪӲ������
    	    if(!BusinessUtility.productIDMatchDeviceID(productID,deviceID)){
    		   LogUtility.log(clazz, LogLevel.WARN, "��Ʒ���豸��ƥ���û�д˴����豸��");
			   throw new ServiceException("��Ʒ���豸��ƥ���û�д˴����豸��");
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
    		//����Ǹ����豸����Ҫ�ҳ�Ŀ���豸��״̬
    		if(actionType==EJBEvent.DEVICESWAP && context.get(Service.CSI_DTO)!=null){
    			CustServiceInteractionDTO csiDTO=(CustServiceInteractionDTO)context.get(Service.CSI_DTO);
    			//�õ�Ŀ��״̬
    			toStatus=BusinessUtility.getDeviceSwapStatusByReason(csiDTO.getCreateReason());
    		}
    		
    		//�ҵ���Ҫ����/�����Ŀͻ���Ʒ
    		CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
    		CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(psID));
    		saID=cp.getServiceAccountID();
    		
    		Collection oldDeviceIDList=new ArrayList();
    		Collection deviceIDList=new ArrayList();
    		
    		TerminalDeviceHome tdHome1=HomeLocater.getTerminalDeviceHome();
  		    TerminalDevice td1=tdHome1.findByPrimaryKey(new Integer(deviceID));
  		    String newSerialNo = td1.getSerialNo();
  		    
            // ά���豸��Թ�ϵ 2007-12-19
  		    deviceMatch(saID,newSerialNo);
  		    
  		    oldProductID=cp.getProductID();
  		    oldDeviceID=cp.getDeviceID();
  		    
  		    // ����CM���ж� modify by david.Yang
  		    if (!deviceInfo.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
    		    cp.setProductID(productID);
    		    cp.setDeviceID(deviceID);
    		    cp.setDtLastmod(TimestampUtility.getCurrentDate());
  		    }
    		//ȡ�����豸����Ϣ,�����������豸��״̬,���������豸��״̬����Ϊ���豸��ԭʼ״̬
    		TerminalDeviceDTO oldDeviceInfo =BusinessUtility.getDeviceByDeviceID(oldDeviceID);
    		oldDeviceIDList.add(new Integer(oldDeviceID));
    		deviceIDList.add(new Integer(deviceID));	
    		//�����豸��ת��¼,���豸���
    		deviceTransitionForCPop(oldDeviceIDList,cp.getCustomerID(),((Integer)context.get(Service.OPERATIOR_ID)).intValue(),
    				1,CommonConstDefinition.DEVICE_TRANSACTION_ACTION_G,false,toStatus);

    		
    		/*****************************add by yangchen 2008/06/20 start ***************************************/
    		TerminalDeviceDTO deviceForRelease=BusinessUtility.getDeviceByDeviceID(deviceID);
    		
    		if(CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceForRelease.getDeviceClass())
    			|| "Y".equals(deviceForRelease.getPreAuthorization()) //add by david.Yang ���ڻ���һ����豸
    		){
    			DeviceStockService deviceStockService=new DeviceStockService(context);
    			deviceStockService.loadDeviceList(deviceID);
    			deviceStockService.devPreauthTrans(deviceID);
    		}
    		/*****************************add by yangchen 2008/06/20 end***************************************/
    		//�����豸��ת��¼,���豸����
    		deviceTransitionForCPop(deviceIDList,cp.getCustomerID(),((Integer)context.get(Service.OPERATIOR_ID)).intValue(),
    	    		0,CommonConstDefinition.DEVICE_TRANSACTION_ACTION_G,false);

    		//��������ҵ���ʻ������пͻ���Ʒ�������ֶ�
    		this.customerProductList=cpHome.findByServiceAccountID(saID);
    		this.deviceMap.put(new Integer(productID),new Integer(deviceID));
    		
    		//����Ǹ���CM,���������linkToDevice1,linkToDevice2��ֵ add by david.Yang
    		System.out.println("td1.getDeviceClass()------->"+td1.getDeviceClass());
    		if (!td1.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
    		    productLinkToDevice();
    		}

    	     // ȡ��oldDeviceID
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
    		// ȡ�ø�������豸���кŸ�deviceID
    		 
   		     
    		//�豸������ʱ��,����������豸��״̬�͸���ǰ���豸��״̬һ��
  		     td1.setStatus(oldDeviceInfo.getStatus());
   		     //�����洢�豸������ʱ������л���ʱ���ܿ����豸�ź����к�(���ǵ�ǰҪ�������豸),
  		    
  		   /*****************************add by david.Yang 2009/07/29 start ***************************************/
  		     if (td1.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_CM)){
  		    	 deviceClass =CommonConstDefinition.DEVICECALSS_CM;
  		    	 TerminalDeviceHome tdHome=HomeLocater.getTerminalDeviceHome();
     		     TerminalDevice td=tdHome.findByPrimaryKey(oldDeviceId);
                  //  ��������CM������,��״̬�����ñ��е�ӳ��һ��
     		     TerminalDevice cm=tdHome.findByPrimaryKey(new Integer(td.getMatchDeviceID()));
     		     cm.setStatus(td.getStatus());
     		     cm.setMatchDeviceID(0);
     		     cm.setMatchFlag("N");
     		     //ԭSTB �ﶨ��CM
     		     td.setMACAddress(td1.getSerialNo());
     		     td.setMatchDeviceID(td1.getDeviceID().intValue());
     		     //��CM �ﶨԭSTB
     		     td1.setMatchDeviceID(td.getDeviceID().intValue());
     		     td1.setMatchFlag("Y");
     		     //STB ״̬��Ϊԭ��������״̬
     		     td.setStatus(oldDeviceInfo.getStatus());   
  		     }else{
  		    	 if (td1.getDeviceClass().equals(CommonConstDefinition.DEVICECALSS_STB)){   		
  		    	   	TerminalDeviceHome tdHome=HomeLocater.getTerminalDeviceHome();
  	     		    TerminalDevice td=tdHome.findByPrimaryKey(oldDeviceId);
  	     		    //˫������и���
                    if (BusinessUtility.getVodModel().contains(td.getDeviceModel()) 
                    		&& BusinessUtility.getVodModel().contains(td1.getDeviceModel())){
  	     		        if (!"Y".equals(td1.getMatchFlag())){  //  ����˫������е�����STB,Ҫ����ԭCM�İﶨ��ϵ,�����µ�CM�ﶨ��ϵ
  	     		           //��STB �ﶨ�ϵ�CM
  	     		           td1.setMACAddress(td.getMACAddress());
   	     		           td1.setMatchDeviceID(td.getMatchDeviceID());
  	     		           td1.setMatchFlag("Y");
  	     		           //CM �ﶨ�µ�STB 
  	     		           TerminalDevice cm =tdHome.findByPrimaryKey(new Integer(td.getMatchDeviceID()));
  	     		           cm.setMatchDeviceID(td1.getDeviceID().intValue());
  	     		           //��������STB,���CM��ϵ�ﶨ
                           td.setMACAddress(null);
                           td.setMatchFlag("N");
                           td.setMatchDeviceID(0);
                           
  	     		        }else{       //��������,ԭSTB,CM�İﶨ��ϵ��� .��ʱ�������Թ�ϵ
  	     		           TerminalDevice cm =tdHome.findByPrimaryKey(new Integer(td.getMatchDeviceID()));
  	     		        //   cm.setMatchFlag("N");
  	     		        //   cm.setMatchDeviceID(0);
  	     		           cm.setStatus(td.getStatus());
  	     		        //   td.setMACAddress(null);
                        //   td.setMatchFlag("N");
                        //   td.setMatchDeviceID(0);
  	     		        }
                    }else if (BusinessUtility.getVodModel().contains(td.getDeviceModel()) 
                             && !BusinessUtility.getVodModel().contains(td1.getDeviceModel())){  //˫������л����������
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
   		         //���豸�¼����޸�ԭ�����ϵ��豸�¼������豸�¼��Ĳ�Ʒ��oldProductID�ĳ�productID
   		    	 //SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
				 //	this.accountId, saID,oldProductID, csiID, psID,deviceID,newSerialNo,otherDeviceID,otherSerialNo,
				//	oldDeviceId.intValue(),oldSerialNo,otherDeviceID,otherSerialNo,
				//	getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		         SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
   		    		  this.accountId, saID,oldProductID, csiID, psID,oldDeviceId.intValue(),oldSerialNo,otherDeviceID,otherSerialNo,
   		    		  oldDeviceId.intValue(),oldSerialNo,otherDeviceID,otherSerialNo,
   		    		  getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		         //���豸�¼�
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
   		        //���豸�¼����޸�ԭ�����ϵ��豸�¼������豸�¼��Ĳ�Ʒ��oldProductID�ĳ�productID
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
                 // ���豸�¼�
  		         SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE, customerID,
    		    		  this.accountId, saID,oldProductID, csiID, psID,oldcmDevice.getDeviceID().intValue(),oldcmDevice.getSerialNo(),otherDeviceID,otherSerialNo,
    		    		  oldcmDevice.getDeviceID().intValue(),oldcmDevice.getSerialNo(),otherDeviceID,otherSerialNo,
    		    		  getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
  		       
    		     // ���豸�¼�
    		     SystemEventRecorder.AddEvent4SwapProduct(SystemEventRecorder.SE_CUSTDEV_EXCHANGE_NEW, customerID,
    		          	this.accountId, saID,productID, csiID, psID,td1.getDeviceID().intValue(),td1.getSerialNo(),otherDeviceID,otherSerialNo,
    		            td1.getDeviceID().intValue(),td1.getSerialNo(),otherDeviceID,otherSerialNo,
 					    getOperatorID(),SystemEventRecorder.SE_STATUS_CREATE);	
   		      }else{
   		    	 //���豸�¼����޸�ԭ�����ϵ��豸�¼������豸�¼��Ĳ�Ʒ��oldProductID�ĳ�productID
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
    		LogUtility.log(clazz,LogLevel.WARN,"��Ʒ����������ԭ�򣺲�Ʒ��λ����"+e);
    		throw new ServiceException("��Ʒ����������ԭ�򣺿ͻ���λ����");
    	}
    	catch(FinderException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"��Ʒ����������ԭ�򣺲�Ʒ���ҳ���"+e2);
    		throw new ServiceException("��Ʒ����������ԭ�򣺲�Ʒ���ҳ���");
    	}
    	catch(CreateException e3){
    		LogUtility.log(clazz,LogLevel.WARN,"��Ʒ����������ԭ�򣺴���ϵͳ�¼�����"+e3);
    		throw new ServiceException("��Ʒ����������ԭ�򣺴���ϵͳ�¼�����");
    	}
    }

    /**
     * ���� ���ܿ� ��ʱ�� ��ֻ�ý������ܿ���ص�Ǯ����¼
     * ��Ϊ��Ч �� �ȴ��˹����¿������¿�ͨ��ת��������� �� 
     * @param oldDeviceID
     * @param serialNo
     * @throws ServiceException
     */
    public void updateCAValletDependDevice(int oldDeviceID) throws ServiceException{
    	//ȡ��ǰ�豸��ص���ЧǮ��
    	try {
	    	List cawList=BusinessUtility.getCAWalletListByDeviceID(oldDeviceID);
	    	if(cawList==null||cawList.isEmpty()){
	    		return;
	    	}
	    	context.put(Service.CAWALLETSERIALNOSWAP, cawList);
	    	LogUtility.log(clazz,LogLevel.DEBUG,"��ʼ����CAWallet�����кţ�"+cawList);
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
    		LogUtility.log(clazz,LogLevel.WARN,"�����豸������CAǮ������"+e);
			throw new ServiceException("�����豸������CAǮ������");
		}
    }
    /**
     * �����豸
     * @param psID
     * @param productID
     * @param deviceID
     * @throws ServiceException
     */
    public void swapDevice(int psID,int productID,int deviceID)throws ServiceException{
    	//������Ϊ���Ͳ�Ʒ��������ҵ���߼�û�кܴ��������ʱ�ŵ�һ��ȥ
    	alterCustomerProduct(EJBEvent.DEVICESWAP,psID,productID,deviceID);
    }

    /**
     * �豸��ת
     * @param deviceList
     * @param actionType
     * @throws ServiceException
     */
    public void transferDevice(Collection deviceList,int actionType) throws ServiceException{

    	//��ʱû�п���actionType

    	if(deviceList==null||deviceList.isEmpty()){
    		LogUtility.log(clazz,LogLevel.WARN,"�豸�б�Ϊ��");
        	return ;
    	}
    	try{
    		deviceTransitionForCPop(deviceList,this.customerID,getOperatorID(),
        		1,CommonConstDefinition.DEVICE_TRANSACTION_ACTION_H,true);
    	}
        catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�豸��ת����ԭ�򣺲�Ʒ��λ����"+e);
        	throw new ServiceException("�豸��ת����ԭ�򣺿ͻ���λ����");
        }
        catch(FinderException e2){
        	LogUtility.log(clazz,LogLevel.WARN,"�豸��ת����ԭ�򣺲�Ʒ���ҳ���"+e2);
        	throw new ServiceException("�豸��ת����ԭ�򣺲�Ʒ���ҳ���");
        }
        catch(CreateException e3){
        	LogUtility.log(clazz,LogLevel.WARN,"�豸��ת����ԭ�򣺴���ϵͳ�¼�����"+e3);
        	throw new ServiceException("�豸��ת����ԭ�򣺴���ϵͳ�¼�����");
        }
    }

    /**
     * ͨ��CSIID�õ�CsiCustProductInfo����Ϣ������CsiCustProductInfo����CustomerProduct�����޸�CsiCustProductInfo
     * ��CustomerProductID�����ظ�CustomerProduct��
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
				//������ȷ�ϣ�����ֻ����״̬ΪN����������жϲ���Ҫ��
				if(this.usedMonth!=0){
					if (cpDto.getDeviceID() ==0){
					   cpDto.setStartTime(TimestampUtility.getCurrentDateWithoutTime());
					   cpDto.setEndTime(TimestampUtility.getTimeEndWithoutTime(cpDto.getStartTime(), "M",this.usedMonth));
					}
				}

				//ֻ���ŵ꿪�����������԰�װ����Ʒ��״̬���ǳ�ʼ״̬������Ϊ����״̬--->���в�Ʒ״̬Ϊ��ʼ, modify 2005-12-15
				//if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csitype)&&
				//		CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL.equals(installationType))
				//cpDto.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_INIT);
				//----------yangchen-----------2006/11/23-----------start---------------
/*				
				//ֻ�пͻ�������Ʒ��ʱ��ţ���Ʒ״̬��������������ΪǱ�� modify 2006-1-5
				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csitype))
				{
					cpDto.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
					cpDto.setActivateTime(new Timestamp(System.currentTimeMillis()));//6-8�����
				}//11-21�����,�����ſͻ����ӿͻ�ʱ,��Ʒ״̬Ϊ����,����ʱ��Ϊ��ǰ
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
					LogUtility.log(clazz,LogLevel.DEBUG,"����һ���ͻ���Ʒ��"+cp.getPsID());
					this.customerProductList.add(cp);
					//����CSI��Ʒ�б�Ŀͻ���ƷID
					csiProductLocal.setCustProductID(cp.getPsID().intValue());
					csiProductLocal.setReferAccountID(accountId);
					csiProductLocal.setReferServiceAccountID(saID);
					returnHashMap.put(cp.getPsID(), csiProductLocal);	
					return cp;
				}
				catch (HomeFactoryException e) {
					LogUtility.log(clazz, LogLevel.ERROR, e);
					throw new ServiceException("����home �ӿڴ���");
				}
				catch (CreateException e) {
					LogUtility.log(clazz, LogLevel.ERROR, e);
					throw new ServiceException("�����ͻ���Ʒ���� ����");
				}
        	}
    	}
        return null;
    }

    /**
     * �豸��ת
     * @param deviceIDList : �豸ID���б�
     * @param customerID
     * @param opID
     * @param actionType : 0Ϊ���豸������⣬1Ϊ�۳����豸��⣬
     * @param action : �豸�豸��ת�Ķ�������
     * @param useflag : ��actionType=1ʱ��useflag �Ƿ�Ϊ���ֻ���
     *                  ��actionTypeʱ useflag Ϊ true ��ʾ�豸������false ��ʾ�豸�۳���
     * @throws ServiceException
     * @throws HomeFactoryException
     * @throws FinderException
     * @throws CreateException
     */
    public void deviceTransitionForCPop(Collection deviceIDList,int customerID,int opID,int actionType,String tranAction,boolean useFlag,String toStatus)
    	throws ServiceException, HomeFactoryException, FinderException, CreateException{
    	
    	if(actionType!=0 && actionType!=1){
    		LogUtility.log(clazz,LogLevel.WARN,"�豸��ת����δ֪");
    		throw new ServiceException("�豸��ת����δ֪");
    	}

    	if(deviceIDList==null || deviceIDList.size()==0){
    		LogUtility.log(clazz,LogLevel.DEBUG,"û���豸��Ҫ��ת");
    		return;
    	}

    	LogUtility.log(clazz,LogLevel.DEBUG,"�����豸��ת");

    	//����Ա���ڵ���֯ID
    	int orgID=BusinessUtility.getOpOrgIDByOperatorID(((Integer)this.context.get(Service.OPERATIOR_ID)).intValue());
    	String strFromType="";
    	int intFromID=0;

    	//�����豸��ת��¼
    	LogUtility.log(clazz,LogLevel.DEBUG,"�����豸��ת��¼");
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

    		LogUtility.log(clazz,LogLevel.DEBUG,"��ǰ�������豸��Ϊ��" + deviceID);

        	TerminalDevice terminalDevice=terminalDeviceHome.findByPrimaryKey(new Integer(deviceID));
        	String fromType=terminalDevice.getAddressType();
        	int fromID=terminalDevice.getAddressID();
        	
        	//�豸������״̬���Ǵ���
        	if(actionType==0){
        		if(!(CommonConstDefinition.DEVICE_STATUS_WAITFORSELL.equals(terminalDevice.getStatus()))
        			&& !(CommonConstDefinition.DEVICE_STATUS_LOCK.equals(terminalDevice.getStatus()))){
        			throw new ServiceException("���豸:"+terminalDevice.getSerialNo()+" ״̬���Ǵ��ڴ���/����״̬");
        		}
        	}
        	//�豸�����״̬��Ϊ����/ ����
        	if(actionType==1){
        		if(!(CommonConstDefinition.DEVICE_STATUS_SOLD.equals(terminalDevice.getStatus()))
        			&& !(CommonConstDefinition.DEVICE_STATUS_LOCK.equals(terminalDevice.getStatus()))){
        			throw new ServiceException("�ͻ��豸:"+terminalDevice.getSerialNo()+" ��״̬���Ǵ�������/����״̬");
        		}
        	}
        	
            // �����豸��ת��ϸ
        	LogUtility.log(clazz,LogLevel.DEBUG,"�����豸��ת��ϸ");
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
        			terminalDevice.setStatus(CommonConstDefinition.DEVICE_STATUS_WAITFORSELL);//9-07david.Yang��,�˻�����װ���ɹ��˷��豸״̬ӦΪ����
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

    	//�����豸��ת
    	deviceTransition.setFromType(strFromType);
    	deviceTransition.setFromID(intFromID);

    	LogUtility.log(clazz,LogLevel.DEBUG,"�����豸��ת");
    	
    }
    /**
     * �豸��ת
     * @param deviceIDList : �豸ID���б�
     * @param customerID
     * @param opID
     * @param actionType : 0Ϊ���豸������⣬1Ϊ�۳����豸��⣬
     * @param action : �豸�豸��ת�Ķ�������
     * @param useflag : ��actionType=1ʱ��useflag �Ƿ�Ϊ���ֻ���
     *                  ��actionTypeʱ useflag Ϊ true ��ʾ�豸������false ��ʾ�豸�۳���
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
     * ���ͻ���Ʒ��linkToDevice1,linkToDevice2��ֵ
     * @throws FinderException
     * @throws HomeFactoryException
     * @throws HomeFactoryException
     * @throws FinderException
     * @throws ServiceException
     *
     */
    private void productLinkToDevice() throws HomeFactoryException, FinderException, ServiceException{

    	//�����ƷΪ�գ����˳�
    	if(this.customerProductList==null||this.customerProductList.isEmpty()){
    		return;
    	}
    	
    	boolean isChangeLinkedDevice=false;
    	LogUtility.log(clazz,LogLevel.DEBUG,"�����Ʒ���ӵ�Ӳ����Ʒ");
    	int linkToDevice1=0;
    	int linkToDevice2=0;
    	Set productIDSet=null;
    	//���deviceMapΪ�գ����Ƚ����ڸ�ҵ���ʻ��²��ң�������Ҳ��������˳�
    	if(this.deviceMap==null || this.deviceMap.isEmpty()){

    		Map deviceMap=BusinessUtility.getSADeviceIDMapBySAID(this.saID);

    		if(deviceMap==null || deviceMap.size()==0)
    			return;

    		productIDSet=deviceMap.keySet();

    		//ȡ��linkToDevice1��linkToDevice2
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
    		//cloneһ���豸Map
    		HashMap deviceMapCopy=(HashMap)this.deviceMap.clone();
    		productIDSet=deviceMapCopy.keySet();
    		boolean changeDevice=false;

    		//ȡ��linkToDevice1��linkToDevice2
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
    	LogUtility.log(clazz,LogLevel.DEBUG,"�����Ʒ���ӵ�Ӳ����ƷlinkToDevice1  "+linkToDevice1+"   linkToDevice2  "+linkToDevice2);

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

    		//��������ϵ������linkToDevice1��linkToDevice�������ڲ��ò��ж�������ϵ��ͳͳ��device����������linkToDevice1��linkToDevice2
    		/*
    		Iterator itProduct=productIDSet.iterator();
    		while(itProduct.hasNext()){

    			Integer productID=(Integer)itProduct.next();
    			if(BusinessUtility.prodcutsIsDependency(cp.getProductID(),productID.intValue())){

    				Integer deviceID=(Integer)deviceMapCopy.get(productID);
    				String deviceClass=BusinessUtility.getDeviceClassByDeviceID(deviceID);

    				//�������ΪSTB��CM����뵽linkToDevice1,�����SC���ŵ�linkToDevice2
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

    		//ȡ��linkToDevice1��linkToDevice2
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
     * �����ͻ���Ʒϵͳ�¼�
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
    	//�����newsaflag='Y'��ϵͳ�¼�������Ҫ����ҵ���ʻ��Ĳ�Ʒ�Ĺ����¼�
    	//update by Stone, ����Ӳ���������Ʒ��ϵͳ�¼���ϵͳ�¼��ֱ���610��310
    	
    	Iterator itSA=this.customerProductList.iterator();
    	
    	while(itSA.hasNext()){
    		CustomerProduct cp=(CustomerProduct)itSA.next();
    		ProductDTO pDTO = BusinessUtility.getProductDTOByProductID(cp.getProductID());

    		if("Y".equals(pDTO.getNewsaFlag()))
    		{
				if(cpAddEventRecordSet.add(cp.getPsID())){
	
					LogUtility.log(clazz,LogLevel.DEBUG,"�����newsaflag='Y'��ϵͳ�¼�");
	
					Device1SerialNo="";
					Device2SerialNo="";
	
					linkToDevice1=cp.getLinkToDevice1();
					linkToDevice2=cp.getLinkToDevice2();
					if(linkToDevice1>0)
						Device1SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice1));
					if(linkToDevice2>0)
						Device2SerialNo=BusinessUtility.getDeviceSerialNoByDeviceID(new Integer(linkToDevice2));
					
					//Ӳ����Ʒ
					if(cp.getDeviceID()>0)
						SystemEventRecorder.AddEvent4Product(SystemEventRecorder.SE_CUSTDEV_PURCHASE, customerID,
							accountID, saID,cp.getProductID(), csiid, cp.getPsID().intValue(),
							linkToDevice2,Device2SerialNo,linkToDevice1,Device1SerialNo,
							PublicService.getCurrentOperatorID(context),SystemEventRecorder.SE_STATUS_CREATE);
					else //�����Ʒ
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
 
    	//�����ʣ�µ�Ӳ����ϵͳ�¼���610
    	Iterator itRestDevice=this.customerProductList.iterator();
    	while(itRestDevice.hasNext()){
    		CustomerProduct cp=(CustomerProduct)itRestDevice.next();
    		if(cp.getDeviceID()>0){
    			if(cpAddEventRecordSet.add(cp.getPsID())){

    				LogUtility.log(clazz,LogLevel.DEBUG,"�����ʣ�µ�Ӳ��ϵͳ�¼�");

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
    	//�����ʣ�µĲ�Ʒ��ϵͳ�¼���310
    	Iterator itRest=this.customerProductList.iterator();
    	while(itRest.hasNext()){
    		CustomerProduct cp=(CustomerProduct)itRest.next();
    		if(cpAddEventRecordSet.add(cp.getPsID())){

    			LogUtility.log(clazz,LogLevel.DEBUG,"�����ʣ�µĲ�Ʒ�¼�");

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
     * �޸Ŀͻ���Ʒ״̬����¼��Ʒ�¼�����־�����ڲ�Ʒ��ͣ���ָ���ȡ��
     * @throws ServiceException
     * 1.5�������˷���ֵ,����������־
     */
    private ArrayList updateCustomerProductStatus(Collection custProductIDList,String status,int event,
    		String log,String logDes) throws ServiceException{

    	//�����������У�
    	custProductIDList=BusinessUtility.sortPsidWithCancelCp(custProductIDList);
    	LogUtility.log(clazz, LogLevel.DEBUG, "�������Ҫ�����Ĳ�Ʒ�б�Ϊ��"+custProductIDList);
    	ArrayList list=new ArrayList();
    	//������
    	String errorMsg = "";
    	if(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL.equals(status))
    		errorMsg = "û�д�ȡ���Ĳ�Ʒ��";
    	else if(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND.equals(status))
    		errorMsg = "û�д���ͣ�������Ʒ��";
    	else if(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL.equals(status))
    		errorMsg = "û�д��ָ��Ĳ�Ʒ��";
    	
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
    			//�ͻ���Ʒ״̬�޸�
    			if(!CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_MOVE.equals(status))
    			cp.setStatus(status);
    			cp.setDtLastmod(TimestampUtility.getCurrentDate());
    			//���ø���ʱ��ʱ��
    			switch(event){
    				//��ͣ
    				case SystemEventRecorder.SE_CUSTPROD_PAUSE:
    					cp.setPauseTime(TimestampUtility.getCurrentTimestamp());
    					break;
    				//�ָ�
    				case SystemEventRecorder.SE_CUSTPROD_RESUME:
    					cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
    					break;
    				//ȡ��
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
            	//�Ѷ�Ӳ�����豸���������Ʒ��ȡ����¼�¼����������ֿ���Ӳ����612�������314
            	
            	//Ĭ���������Ʒ��
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
    			//����ϵͳ�¼���¼
            	SystemEventRecorder.AddEvent4Product(eventClass,cp.getCustomerID(),
            			cp.getAccountID(),cp.getServiceAccountID(),cp.getProductID(),csiID,cpID.intValue(),linkToDevice2,
            			linkToDevice2SerialNo,linkToDevice1,linkToDevice1SerialNo,0,
    					getOperatorID(),oldStatus,SystemEventRecorder.SE_STATUS_CREATE);
            	list.add(cp);
    		}
    		return list;
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.ERROR, "�ͻ���Ʒ��λ����");
            throw new ServiceException("ҵ�ͻ���Ʒ��λ����");
    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.ERROR, "�ͻ���Ʒ���ҳ���");
            throw new ServiceException("�ͻ���Ʒ���ҳ���");
    	}
    	catch (CreateException e) {
    		LogUtility.log(clazz,LogLevel.ERROR, "�����ͻ���Ʒϵͳ�¼�����");
            throw new ServiceException("�����ͻ���Ʒϵͳ�¼�����");
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
     * �޸Ŀͻ���Ʒ����״̬
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
    		LogUtility.log(clazz,LogLevel.WARN,"���ҿͻ���Ʒ���Գ���");
    	}
    	catch(FinderException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"û��"+ psID + "�Ŀͻ���Ʒ���ԣ�");
    	}
    }
    public CustomerProduct findCp(Integer psid) throws ServiceException{
		try {
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			CustomerProduct cp = cpHome.findByPrimaryKey(psid);
			return cp;
    	}catch(HomeFactoryException e1){
    		LogUtility.log(clazz,LogLevel.ERROR,"�޸Ŀͻ��豸��Դ�����쳣"+e1);
    		throw new ServiceException("�޸Ŀͻ��豸��Դ�����쳣");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"�޸Ŀͻ��豸��Դ��λ�豸��"+e);
    		throw new ServiceException("�޸Ŀͻ��豸��Դ��λ�豸��!"+e);
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
    		LogUtility.log(clazz,LogLevel.ERROR,"���¿ͻ���Ʒ�����ʻ������쳣:"+e1);
    		throw new ServiceException("���¿ͻ���Ʒ�����ʻ������쳣");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"���¿ͻ���Ʒ�����ʻ������쳣��λ��Ʒ��"+e);
    		throw new ServiceException("���¿ͻ���Ʒ�����ʻ������쳣��λ��Ʒ��!"+e);
		}    	
    }
    
    
    /**
     * ����������ʱ,ά���豸��Թ�ϵ
     * @param deviceIDList
     */
    public void deviceMatch(Collection deviceIDList) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "����deviceMatch(Collection deviceIDList)��" + deviceIDList);
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
			//ϵͳ�Ƿ�����豸���,��:���ء�
			if(!BusinessUtility.isSystemManagerDeviceMatch()) return ;
			//��ǰ�豸�Ƿ����(ֻ���STB,SC)
			if(terminalDeviceSTB != null) {
				if("Y".equals(terminalDeviceSTB.getMatchFlag())) return ;
			}
			if(terminalDeviceSC != null) {
				if("Y".equals(terminalDeviceSC.getMatchFlag())) return ;
			}
			//�Ƿ�ֻ����STB
			if(terminalDeviceSC==null) return ;
			//�޸�(STB,SC ��Թ�ϵ)
			if (terminalDeviceSTB != null && terminalDeviceSC != null) {
				//��¼�������/����Թ���
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
			LogUtility.log(clazz, LogLevel.ERROR, "����������ʱ��ά���豸��Թ�ϵ����" + e);
			throw new ServiceException("����������ʱ��ά���豸��Թ�ϵ����!" + e);
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����������ʱ��ά���豸��Թ�ϵ����λ�豸����" + e);
			throw new ServiceException("����������ʱ��ά���豸��Թ�ϵ����λ�豸����!" + e);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
	}
    
    
    /**
     * �豸����ʱ,ά���豸��Թ�ϵ
     * @param saID,newSerialNo
     */
    public void deviceMatch(int saID,String newSerialNo) throws ServiceException {
    	LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ deviceMatch ����saID��" + saID + "����newSerialNo��" + newSerialNo);
    	
    	Map deviceMap = BusinessUtility.getSADeviceIDMapBySAID(saID);
    	TerminalDeviceDTO terminalDeviceDTO = BusinessUtility.getDeviceBySerialNo(newSerialNo);
    	String newDeviceClass = terminalDeviceDTO.getDeviceClass();
    	int newDeviceID = terminalDeviceDTO.getDeviceID();
    	LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����newDeviceID��" + newDeviceID);
    	int linkToDeviceSTB=0;
    	int linkToDeviceSC=0;
    	TerminalDevice newTerminaDevice = null;
		TerminalDevice terminalDeviceSTB = null;
		TerminalDevice terminalDeviceSC = null;
		
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����deviceMap��" + deviceMap);
		if(deviceMap==null || deviceMap.size()==0)
			return;
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����terminalDeviceDTO��" + terminalDeviceDTO);
		if(terminalDeviceDTO==null)
			return;
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����newDeviceClass��" + newDeviceClass);
		if(!CommonConstDefinition.DEVICECALSS_STB.equals(newDeviceClass) && !CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(newDeviceClass))
			return;
		
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����deviceMap��" + deviceMap);
		Set productIDSet=deviceMap.keySet();
		LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����productIDSet��" + productIDSet);
		//ȡ��linkToDeviceSTB��linkToDeviceSC
		Iterator itProductID=productIDSet.iterator();
		try {
			TerminalDeviceHome terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			while (itProductID.hasNext()) {
				Integer productID = (Integer) itProductID.next();
				Integer deviceID = (Integer) deviceMap.get(productID);
				String deviceClass = BusinessUtility.getDeviceClassByDeviceID(deviceID);
				if (CommonConstDefinition.DEVICECALSS_STB.equals(deviceClass)) {
					linkToDeviceSTB = deviceID.intValue();
					LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����linkToDeviceSTB��" + linkToDeviceSTB);
				} else if (CommonConstDefinition.DEVICECALSS_SMARTCARD.equals(deviceClass)) {
					linkToDeviceSC = deviceID.intValue();
					LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����linkToDeviceSC��" + linkToDeviceSC);
				}
			}
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����ϵͳ�Ƿ�����豸��ԡ�" + !BusinessUtility.isSystemManagerDeviceMatch());
			//ϵͳ�Ƿ�����豸���,��:���ء�
			if(!BusinessUtility.isSystemManagerDeviceMatch()) return ;
			//��������豸����Թ�ϵ(�������������ľ��豸��ʹ�õľ��豸),�����ɵ��豸(ʹ���е�)�����豸����Թ�ϵ
			newTerminaDevice  = terminalDeviceHome.findByPrimaryKey(new Integer(newDeviceID));
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����newTerminaDevice��" + newTerminaDevice);
			terminalDeviceSTB  = terminalDeviceHome.findByPrimaryKey(new Integer(linkToDeviceSTB));
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����terminalDeviceSTB��" + terminalDeviceSTB);
			terminalDeviceSC  = terminalDeviceHome.findByPrimaryKey(new Integer(linkToDeviceSC));
			LogUtility.log(clazz, LogLevel.DEBUG, "�豸����ʱ,ά���豸��Թ�ϵ  ����terminalDeviceSC��" + terminalDeviceSC);
			
			if (newTerminaDevice != null && terminalDeviceSTB != null
					&& terminalDeviceSC != null) {
				if (newDeviceClass.equals(terminalDeviceSTB.getDeviceClass())) { // ȷ�����豸
					//��¼��������Թ���
			    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
						devicePrematchRecord(terminalDeviceSTB,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_DM);
			    	//��¼������Թ���
			    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
						devicePrematchRecord(newTerminaDevice,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_AM);
			    	
					terminalDeviceSTB.setMatchFlag("N");
					terminalDeviceSTB.setMatchDeviceID(0);
					terminalDeviceSTB.setDtLastmod(new Timestamp(System
							.currentTimeMillis()));

					// terminalDeviceSC �� newTerminaDevice ���
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
				if (newDeviceClass.equals(terminalDeviceSC.getDeviceClass())) { // ȷ�����豸
					//��¼��������Թ���
			    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
						devicePrematchRecord(terminalDeviceSTB,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_DM);
			    	//��¼������Թ���
			    	if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
						devicePrematchRecord(terminalDeviceSTB,newTerminaDevice,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_AM);

					terminalDeviceSC.setMatchFlag("N");
					terminalDeviceSC.setMatchDeviceID(0);
					terminalDeviceSC.setDtLastmod(new Timestamp(System.currentTimeMillis()));
										
					// terminalDeviceSTB �� newTerminaDevice ���
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
			LogUtility.log(clazz, LogLevel.ERROR, "�豸����ʱ��ά���豸��Թ�ϵ����" + e);
			throw new ServiceException("�豸����ʱ��ά���豸��Թ�ϵ����!" + e);
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "�豸����ʱ��ά���豸��Թ�ϵ����λ�豸����" + e);
			throw new ServiceException("�豸����ʱ��ά���豸��Թ�ϵ����λ�豸����!" + e);
		} catch (Exception e){
			e.printStackTrace();
		}
    }
    
    
    /**
     * �˻�������ʱ,ά���豸��Թ�ϵ
     * @param deviceIDList
     */
    public void unchainDeviceMatch(Collection deviceIDList) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "����unchainDeviceMatch(Collection deviceIDList)��" + deviceIDList);
		TerminalDevice currentTerminaDevice = null;
		int deviceID = 0;
		TerminalDevice terminalDeviceSTB = null;
		TerminalDevice terminalDeviceSC = null;
		//ϵͳ�Ƿ�����豸���,��:���ء�
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
					//����豸��Թ�ϵ(STB,SC)
					currentTerminaDevice.setMatchFlag("N");
					currentTerminaDevice.setMatchDeviceID(0);
					currentTerminaDevice.setDtLastmod(new Timestamp(System.currentTimeMillis()));
				}
			}
			//��¼�������/����Թ���
			if(terminalDeviceSTB!=null && terminalDeviceSC!=null)
				devicePrematchRecord(terminalDeviceSTB,terminalDeviceSC,CommonConstDefinition.SET_D_DEVICEPREAUTH_OPERATIONTYPE_DM);
			
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "�˻�������ʱ��ά���豸��Թ�ϵ����" + e);
			throw new ServiceException("�˻�������ʱ��ά���豸��Թ�ϵ����!" + e);
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "�˻�������ʱ��ά���豸��Թ�ϵ����λ�豸����" + e);
			throw new ServiceException("�˻�������ʱ��ά���豸��Թ�ϵ����λ�豸����!" + e);
		}
	}
    
    /**
     * ��¼�������/����Թ���
     * @param terminalDeviceSTB,terminalDeviceSC,OperationType
     */
    public void devicePrematchRecord(TerminalDevice terminalDeviceSTB,TerminalDevice terminalDeviceSC,String OperationType) throws ServiceException {
    	LogUtility.log(clazz, LogLevel.DEBUG, "��¼�������/����Թ��̡�terminalDeviceSTB����" + terminalDeviceSTB.toString() + "����terminalDeviceSC��" + terminalDeviceSC.toString() + "����OperationType��" + OperationType);
    	//��¼�������/����Թ���
		try{
			if (terminalDeviceSTB != null && terminalDeviceSC != null) {
				LogUtility.log(clazz, LogLevel.DEBUG, "��¼�������/����Թ��̡�terminalDeviceSTB����"+terminalDeviceSTB.getDeviceID() );
				LogUtility.log(clazz, LogLevel.DEBUG, "��¼�������/����Թ��̡�terminalDeviceSC����"+terminalDeviceSC.getDeviceID() );
				DevicePrematchRecordDTO dto = new DevicePrematchRecordDTO();
				int operatorID = ((Integer)context.get(Service.OPERATIOR_ID)).intValue();
				LogUtility.log(clazz, LogLevel.DEBUG, "��¼�������/����Թ��̡�operatorID����"+operatorID );
				int orgID = BusinessUtility.FindOrgIDByOperatorID(operatorID);
				LogUtility.log(clazz, LogLevel.DEBUG, "��¼�������/����Թ��̡�orgID����"+orgID );
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
				LogUtility.log(clazz, LogLevel.DEBUG, "��¼�������/����Թ��̡�dto����"+dto );
				DevicePrematchRecordHome devicePrematchRecordHome = HomeLocater.getDevicePrematchRecordHome();
				LogUtility.log(clazz, LogLevel.DEBUG, "��¼�������/����Թ��̡�devicePrematchRecordHome����"+devicePrematchRecordHome );
				devicePrematchRecordHome.create(dto);
			}
    	}catch(CreateException e) {
     		LogUtility.log(clazz, LogLevel.ERROR, "���һ������/����Թ��̳���" + e);
 			throw new ServiceException("���һ������/����Թ��̳���" + e);
     	}
    	catch (HomeFactoryException e) {
    		LogUtility.log(clazz, LogLevel.ERROR, "������¼�������/����Թ��̳���" + e);
    		throw new ServiceException("������¼�������/����Թ��̳���" + e);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * Э������Ѳ�Ʒ����ʱ���޸�
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
							throw new ServiceException(dto.getPackageName()+"֮ǰ�ǰ��²�Ʒ�������Ƚ��������Э�����ѣ�");
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
    		LogUtility.log(clazz,LogLevel.ERROR,"Э������Ѳ�Ʒ�����쳣:"+e1);
    		throw new ServiceException("Э������Ѳ�Ʒ�����쳣");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"Э������Ѷ�λ��Ʒ��"+e);
    		throw new ServiceException("Э������Ѷ�λ��Ʒ��"+e);
		}	
    } 	
    
    /*
     * �ֹ�ά��Э�����ʱ��
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
    		LogUtility.log(clazz,LogLevel.ERROR,"�ֹ�ά��Э�����ʱ��:"+e1);
    		throw new ServiceException("�ֹ�ά��Э�����ʱ��");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"�ֹ�ά��Э�����ʱ��"+e);
    		throw new ServiceException("�ֹ�ά��Э�����ʱ��"+e);
		}	
    }
}

