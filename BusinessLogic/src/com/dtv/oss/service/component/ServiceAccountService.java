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

	private Collection productDependency=null;
	
    //�ͻ����Ʒѹ��� add by yangchen 2008/07/23
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
	 * @param context: �����Ķ���
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
	 * ԤԼ������ԤԼ������
	 * 
	 * @param inEvent
	 * @throws ServiceException
	 */
	public void createSubScriberForBooking(ServiceAccountEJBEvent inEvent) throws ServiceException {
        //����������Ϣ
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		CSIService csiService=new CSIService(context);
		csiService.createCustServiceInteraction(csiDTO);
		
		//����绰������Դ���� ��¼�绰����ʹ����־ add by chaoqiu 20070420
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
	    //����绰������Դ���� end
	    
        CustServiceInteraction csiEJB = (CustServiceInteraction)context.get(Service.CSI);
    	
		//���������漰�Ŀͻ���Ʒ�����Ϣ
        csiService.createCsiCustProductInfo( inEvent.getCsiPackageArray(), null, inEvent.getCsiCampaignArray(), csiDTO);
        try {
           //��������,���ù���service����������Ϣ
    	   CustomerHome customerHome =HomeLocater.getCustomerHome();
           Customer customer=customerHome.findByPrimaryKey(new Integer(inEvent.getCustomerID()));
           CustomerDTO custoemrDto=new CustomerDTO();
           // ��ʱ���ܶ�JobCard��CustId����ֵ��������ڻ�û����ԤԼ����������£��ڰ�װ������Ϣ�в鵽�ù���
           //8-22Ӧ�����Ҫ��,���ÿͻ���Ϣ������,����ڿͻ������޷���ѯ����������.
		   custoemrDto.setCustomerID(customer.getCustomerID().intValue());
		  
           //�ͻ���ַ��Ϣ
    	   AddressDTO custAddrDto =new AddressDTO();
    	   AddressHome addressHome =HomeLocater.getAddressHome();
    	   Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
    	   custAddrDto.setAddressID(address.getAddressID().intValue());
    	   custAddrDto.setDistrictID(address.getDistrictID());
		   //��������,���ù���service����������Ϣ
		   InstallationJobCardService installationJobCardService=new InstallationJobCardService(context);
	       int jobcardID=installationJobCardService.createJobCardForCustomerOperation(csiDTO,custoemrDto,custAddrDto,inEvent.getNextOrgID());
		   //�������������Ĺ���id
		   csiEJB.setReferjobcardID(jobcardID);
        }catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ�����Ҳ��������Ϣ");
			
		}
	}
	/**
	 * ԤԼ������ԤԼ���޸�
	 * @param inEvent
	 * @throws ServiceException
	 * @throws HomeFactoryException 
	 * @throws FinderException 
	 */
	public void updateSubscriberForBooking(ServiceAccountEJBEvent inEvent) throws ServiceException {
		CustServiceInteractionDTO csiDTO=inEvent.getCsiDto();
		CSIService csiService=new CSIService(context);
		csiService.updateCustServiceInteractionInfo(csiDTO,inEvent.getActionType());
		//�޸Ŀͻ���Ʒ
		csiService.delectOldCsiCustProductInfo(csiDTO);
    	
		csiService.createCsiCustProductInfo( inEvent.getCsiPackageArray(), null, inEvent.getCsiCampaignArray(), csiDTO);
	    //���¹���

 		//��������,���ù���service�޸Ĺ�����Ϣ
        InstallationJobCardService installationJobCardService=new InstallationJobCardService(context);
        installationJobCardService.updateJobCardForCustomerOperation(csiDTO);

	}
	/**
	 * ����ҵ���ʻ�����(������ͨ�ͻ��Ŀ���������ҵ���ʻ�)���������״̬���ǳ�ʼ״̬�����
	 * add by zhouxushun, 2005-10-21
	 * @param packageList����Ʒ���б�
	 * @param campaignList�������б�
	 */
	public void create(Collection packageList, Collection campaignList,
			String phoneNo, int itemID, Map productPropertyMap,boolean isBookingOpen,ArrayList serviceCodeList)throws ServiceException{
		create(packageList,campaignList,phoneNo,itemID,productPropertyMap,CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT,isBookingOpen,serviceCodeList);
	}
	/**
	 * �������ſͻ��ӿͻ��������ӿͻ�����ҵ���ʻ�������Ч�����
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
 * ��������ҵ���ʻ� ,�������ŵ�����
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
	 * ��������ҵ���ʻ� ,������ԤԼ
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
	 * ��������ҵ���ʻ� ,�Զ����ʼ״̬.
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
		//���customerid��accountid�Ƿ����
		checkServiceContext();

		LogUtility.log(clazz, LogLevel.DEBUG, "�����Ʒ�����飬Ϊ����ҵ���ʻ���׼����"); 
		String strSAList="";
		
		//��չ��Ʒ���б�,product����CustomerProductDTO����װ��
		Collection cpDTOList = BusinessUtility.getCustomerProductDTOListByPackageIDList(packageList);

		//�����Map��Map��KEYΪ�ܴ���ҵ���ʻ��Ĳ�Ʒ��ValueΪ��ҵ���ʻ��µĲ�ƷList
		Map arrayMap = splitCustomerProductWithService(cpDTOList);
		
		//�������洴����ҵ���ʻ� ID
		ArrayList serviceAccountList=new ArrayList();
		
		//��ʼ����ҵ���ʻ�
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
			//Ŀǰ��������Ŀʹ��,�Ժ�Ҫȥ����
			saDTO.setDescription(csi.getComments());
			saDTO.setStatus(status);
			saDTO.setSubscriberID(0);
			saDTO.setUserID(0);
			//saDTO.setDescription("ҵ���ʻ�");
			//����ҵ��
			if (serviceID == 3) {
				hasPhoneBusibess = true;
				if (phoneNo == "" || phoneNo == null)
					throw new ServiceException("����ҵ��û��ѡ��绰���룬���ܴ���ҵ���ʻ���");
				else {
					boolean phoneUseFlag = false;
					LogUtility.log(clazz, LogLevel.DEBUG, "<><>phoneNo::"+phoneNo+" itemID::"+itemID); 
					//ԤԼ���� �Ե绰������������ͬ
					if(isBookingOpen)
					{
						phoneUseFlag = checkPhoneNoForBookOpen(phoneNo,itemID,serviceCodeList);
					}
					//������� ����ԭ���ļ���߼�
					else
						phoneUseFlag = checkPhoneNo(phoneNo, itemID);
					if (phoneUseFlag) {
						saDTO.setServiceCode(phoneNo);
					} else
						throw new ServiceException("����ҵ��绰���벻��ȷ�����ܴ���ҵ���ʻ���");

				}
			}
			try {
				//����ҵ���ʻ�
				ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
				ServiceAccount sa = saHome.create(saDTO);
				serviceAccountList.add(sa.getServiceAccountID());
				
				if(!"".equals(strSAList))strSAList=strSAList+",";
				strSAList=strSAList + sa.getServiceAccountID();
				
				//����ҵ���ʻ������¼���161
				SystemEventRecorder.AddEvent4Customer(
						SystemEventRecorder.SE_SERVICEACCOUNT_OPEN, customerid,
						accountid, sa.getServiceAccountID().intValue(), csi
								.getId().intValue(), PublicService.getCurrentOperatorID(context),
						SystemEventRecorder.SE_STATUS_CREATE);

				LogUtility.log(clazz, LogLevel.DEBUG, "����һ���µ�ҵ���ʻ���IDΪ��"
						+ sa.getServiceAccountID());
				
				//�绰����״̬�����ʹ�ü�¼
				if (serviceID == 3)
				{
					// ԤԼ���� ��¼�绰������ʹ��
					if(isBookingOpen)
						usePhoneNoForBookOpen(phoneNo, itemID,sa.getServiceAccountID().intValue(),serviceCodeList);
					//������� ����ԭ�����߼�
					else
						usePhoneNo(phoneNo, itemID,sa.getServiceAccountID().intValue());
				}
				
				//�����ͻ���Ʒ
				CustomerProductService cpService = new CustomerProductService(
						context, customerid, accountid, sa
								.getServiceAccountID().intValue());
				cpService.setUsedMonth(this.usedMonth);
				//ע��һ�£�����ҵ���ʻ���״̬�Ϳͻ���Ʒ��״̬�ڿ���ʱ��ֵ��һ����Ϊ�˼���ֱ��ʹ��ҵ���ʻ���״̬��
				//����Ӧ��ͨ���ж����ö�Ӧ�ͻ���Ʒ��״̬
				cpService.createCustomerProductWithBatchBuy(productList,  sa
						.getServiceAccountID().intValue(), customerid,
						accountid,  productPropertyMap,status,groupNo,sheafNo);
			} catch (ServiceException e) {
				throw new ServiceException(e.getMessage());
			} catch (Exception e) {
				LogUtility.log(clazz, LogLevel.ERROR, e);
				throw new ServiceException("����ҵ��������");
			}
		}
		
		//ԤԼ����/ԤԼ���� ԤԼʱ��ip�绰���� ���ڿ���ʱȡ����ip�绰���� ��Ҫ��ԤԼʱ�����ĵ绰�����ͷ�
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
		//�绰�����ͷŽ���
		
		CampaignService campaignService=new CampaignService(context);
		campaignService.createCustomerCampaign(cpDTOList, campaignList,serviceAccountList,customerid,accountid,csi.getId().intValue());
		
		//����ҵ���ʻ�ID,modify by jason 2007-3-8
		context.put(Service.SERVICE_ACCOUNT_ID, strSAList);
		return strSAList;
		//end
	}
	/**
	 * �ͻ���Ʒ�����һ������,ԭ����serviceaccountservice��,��������,
	 * �÷�������ǰ,��һ����ʼ��productDependency
	 * �Ż����¼�������ķ���,��serviceaccountservice��ʼ����ʱ��,�ȳ�ʼ��һ����Ʒ��������Ļ���,�Ժ��ж�������ϵ�ӻ�����ȡ����,�����α����.
	 * @param cpDTOList CustomerProductDTO�ļ���,
	 * @return Map,key=���Դ���ҵ���ʻ��Ĳ�Ʒ,value=arraylist,������key��Ʒ�Ĳ�Ʒ����,�ü��ϲ�����key, 
	 * @throws ServiceException
	 */
	public Map splitCustomerProductWithService(Collection cpDTOList)
	throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "��Ҫ����Ĳ�Ʒ��", cpDTOList);
		Collection saProductList = BusinessUtility
				.getServicesListFromCustomerPoductDTOList(cpDTOList, false);
		
		LogUtility.log(clazz, LogLevel.DEBUG, "�ܴ���ҵ���ʻ��Ĳ�ƷΪ��", saProductList);
		// �����Map��Map��KEYΪ�ܴ���ҵ���ʻ��Ĳ�Ʒ��ValueΪ��ҵ���ʻ��µĲ�ƷList
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
		
		// ����Ƿ�������
		if (opCPDTOList.containsAll(saProductList)) {
			opCPDTOList.removeAll(saProductList);
		}
		
		if (opCPDTOList.size() > 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "��Ʒ����ʧ��,�����ʣ�µĲ�Ʒ:",
					opCPDTOList);
			throw new ServiceException("��Ʒ����������");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "��Ʒ������ϣ�");
		LogUtility.log(clazz, LogLevel.DEBUG, "��Ʒ���飺" , arrayMap);
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
	 * �ж��Ƿ������Ȩ�����͹���������ϵ.
	 * @param p1
	 * @param p2
	 * @return
	 * @throws ServiceException 
	 */
	private boolean prodcutsIsDependency(int p1,int p2) throws ServiceException{
//		LogUtility.log(clazz, LogLevel.DEBUG, "getDependProductList!!!!!!!!!p1:"+p1);
//		LogUtility.log(clazz, LogLevel.DEBUG, "getDependProductList!!!!!!!!!p2:"+p2);
		if(productDependency==null){
			LogUtility.log(clazz, LogLevel.ERROR, "�������,׼����������.");
			throw new ServiceException("�������,׼����������.");
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
	 * ����ҵ���ʻ�����
	 * add by zhouxushun, 2005-10-21
	 * @param packageList����Ʒ���б�
	 * @param campaignList�������б�
	 */
	private void create(Collection packageList, Collection campaignList,
			String phoneNo, int itemID, Map productPropertyMap,String status,boolean isBookingOpen,ArrayList serviceCodeList)
			throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();

		LogUtility.log(clazz, LogLevel.DEBUG, "�����Ʒ�����飬Ϊ����ҵ���ʻ���׼����");
		String strSAList="";
		
		//��չ��Ʒ���б�,product����CustomerProductDTO����װ��
		Collection cpDTOList = BusinessUtility.getCustomerProductDTOListByPackageIDList(packageList);
		LogUtility.log(clazz, LogLevel.DEBUG, "��ֺ�Ĳ�ƷΪ��" + cpDTOList);
		//������Ҫ����ҵ���ʻ��Ĳ�Ʒ�б�
		Collection cpForSAList = (ArrayList) ((ArrayList) cpDTOList).clone();
		Collection saProductList = BusinessUtility.getServicesListFromCustomerPoductDTOList(cpForSAList);

		LogUtility.log(clazz, LogLevel.DEBUG, "�ܴ���ҵ���ʻ��Ĳ�ƷΪ��" + saProductList);
		//�����Map��Map��KEYΪ�ܴ���ҵ���ʻ��Ĳ�Ʒ��ValueΪ��ҵ���ʻ��µĲ�ƷList
		ArrayList opCPDTOList = (ArrayList) ((ArrayList) cpDTOList).clone();
		
		Map arrayMap = new HashMap();
		Iterator it = saProductList.iterator();
		while (it.hasNext()) {
			CustomerProductDTO dto = (CustomerProductDTO) it.next();
			Collection list = new ArrayList();
			arrayMap.put(dto, list);
		}

		//ѭ�����в�ƷList����Ʒ�����ŵ�arrayMap����
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

		//����Ƿ�������
		if (opCPDTOList.containsAll(saProductList)) {
			opCPDTOList.removeAll(saProductList);
		}

		LogUtility.log(clazz, LogLevel.DEBUG, "������ʣ�µĲ�Ʒ��" + opCPDTOList);

		if (opCPDTOList.size() > 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "��Ʒ����ʧ�ܣ����ܴ���ҵ���ʻ���");
			throw new ServiceException("��Ʒ�������������ܴ���ҵ���ʻ���");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "��Ʒ������ϣ���ʼ����ҵ���ʻ���");
		LogUtility.log(clazz, LogLevel.DEBUG, "��Ʒ���飺" + arrayMap.keySet());

		//ƥ���Ż�
		ArrayList serviceAccountList=new ArrayList();
		
		
		//��ʼ����ҵ���ʻ�
		CustServiceInteraction csi = (CustServiceInteraction) context.get(Service.CSI);
		Iterator saIt = arrayMap.keySet().iterator();
		boolean hasPhoneBusibess = false;
		while (saIt.hasNext()) {
			CustomerProductDTO saCPDTO = (CustomerProductDTO) saIt.next();
			ArrayList productList = (ArrayList) arrayMap.get(saCPDTO);
			productList.add(0,saCPDTO);//�Ѵ���ҵ���ʻ��Ĳ�Ʒ��ǰ��,
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
			//Ŀǰ��������Ŀʹ��,�Ժ�Ҫȥ����
			saDTO.setDescription(csi.getComments());
			//----------yangchen-----------2006/11/23-----------start---------------
/*
			//����ʱҵ���ʻ�״̬Ϊ��ʼ�� modify 2005-12-15
			saDTO.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT);
			//11-21�����,����������Ϊ���ſͻ�����ӿͻ�ʱ,ҵ���ʻ�״̬Ϊ����
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS.equals(csi.getType())){
				saDTO.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
			}
*/			
			saDTO.setStatus(status);
			//----------yangchen-----------2006/11/23-----------end---------------
			saDTO.setSubscriberID(0);
			saDTO.setUserID(0);
			//saDTO.setDescription("ҵ���ʻ�");
			//����ҵ��
			if (serviceID == 3) {
				hasPhoneBusibess = true;
				if (phoneNo == "" || phoneNo == null)
					throw new ServiceException("����ҵ��û��ѡ��绰���룬���ܴ���ҵ���ʻ���");
				else {
					boolean phoneUseFlag = false;
					//ԤԼ���� �Ե绰������������ͬ
					if(isBookingOpen)
					{
						phoneUseFlag = checkPhoneNoForBookOpen(phoneNo,itemID,serviceCodeList);
					}
					//������� ����ԭ���ļ���߼�
					else
						phoneUseFlag = checkPhoneNo(phoneNo, itemID);
					if (phoneUseFlag) {
						saDTO.setServiceCode(phoneNo);
					} else
						throw new ServiceException("����ҵ��绰���벻��ȷ�����ܴ���ҵ���ʻ���");

				}
			}
			try {
				//����ҵ���ʻ�
				ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
				ServiceAccount sa = saHome.create(saDTO);
				serviceAccountList.add(sa.getServiceAccountID());
				
				if(!"".equals(strSAList))strSAList=strSAList+",";
				strSAList=strSAList + sa.getServiceAccountID();
				
				//����ҵ���ʻ������¼���161
				SystemEventRecorder.AddEvent4Customer(
						SystemEventRecorder.SE_SERVICEACCOUNT_OPEN, customerid,
						accountid, sa.getServiceAccountID().intValue(), csi
								.getId().intValue(), PublicService.getCurrentOperatorID(context),
						SystemEventRecorder.SE_STATUS_CREATE);

				LogUtility.log(clazz, LogLevel.DEBUG, "����һ���µ�ҵ���ʻ����ʻ�IDΪ��"
						+ sa.getServiceAccountID());
				
				//�绰����״̬�����ʹ�ü�¼
				if (serviceID == 3)
				{
					// ԤԼ���� ��¼�绰������ʹ��
					if(isBookingOpen)
						usePhoneNoForBookOpen(phoneNo, itemID,sa.getServiceAccountID().intValue(),serviceCodeList);
					//������� ����ԭ�����߼�
					else
						usePhoneNo(phoneNo, itemID,sa.getServiceAccountID().intValue());
				}
				
				//�����ͻ���Ʒ
				CustomerProductService cpService = new CustomerProductService(
						context, customerid, accountid, sa
								.getServiceAccountID().intValue());
			    /*********************add by yangchen 2008/07/23 start***************************************************/
				cpService.setCustomerBillingRuleMap(getCustomerBillingRuleMap());
			    /*********************add by yangchen 2008/07/23 end***************************************************/
				
				//ע��һ�£�����ҵ���ʻ���״̬�Ϳͻ���Ʒ��״̬�ڿ���ʱ��ֵ��һ����Ϊ�˼���ֱ��ʹ��ҵ���ʻ���״̬��
				//����Ӧ��ͨ���ж����ö�Ӧ�ͻ���Ʒ��״̬
				cpService.create(productList,  sa
						.getServiceAccountID().intValue(), customerid,
						accountid,  productPropertyMap,status);
			} catch (Exception e) {
				LogUtility.log(clazz, LogLevel.ERROR, e);
				throw new ServiceException("����ҵ��������");
			}
		}
		
		//ԤԼ����/ԤԼ���� ԤԼʱ��ip�绰���� ���ڿ���ʱȡ����ip�绰���� ��Ҫ��ԤԼʱ�����ĵ绰�����ͷ�
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
		//�绰�����ͷŽ���
		
		CampaignService campaignService=new CampaignService(context);
		campaignService.createCustomerCampaign(cpDTOList, campaignList,serviceAccountList,customerid,accountid,csi.getId().intValue());
		
		//����ҵ���ʻ�ID,modify by jason 2007-3-8
		context.put(Service.SERVICE_ACCOUNT_ID, strSAList);
		//end
	}
/**
 * ����һ��ҵ���ʻ�,��������Ʒ,
 * @param saCpDto newsaflag�Ĳ�Ʒ,
 * @param phoneNo ����ҵ��ĵ绰����,
 * @param itemID ������ԴID
 * @param status ҵ���ʻ�״̬.
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
		saDTO.setDescription("ҵ���ʻ�");
		// ����ҵ��
		if (serviceID == 3) {
			if (phoneNo == "" || phoneNo == null)
				throw new ServiceException("����ҵ��û��ѡ��绰���룬���ܴ���ҵ���ʻ���");
			else {
				if (checkPhoneNo(phoneNo, itemID)) {
					saDTO.setServiceCode(phoneNo);
				} else
					throw new ServiceException("����ҵ��绰���벻��ȷ�����ܴ���ҵ���ʻ���");

			}
		}
		try {
			// ����ҵ���ʻ�
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.create(saDTO);

			context.put(Service.SERVICE_ACCOUNT_EJB, sa);
			
			// ����ҵ���ʻ������¼���161
			SystemEventRecorder.AddEvent4Customer(
					SystemEventRecorder.SE_SERVICEACCOUNT_OPEN, customerid,
					accountid, sa.getServiceAccountID().intValue(), csi.getId()
							.intValue(), PublicService
							.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE);

			LogUtility.log(clazz, LogLevel.DEBUG, "����һ���µ�ҵ���ʻ����ʻ�IDΪ��"
					+ sa.getServiceAccountID());

			// �绰����״̬�����ʹ�ü�¼
			if (serviceID == 3) {
				usePhoneNo(phoneNo, itemID, sa.getServiceAccountID().intValue());
			}
			return sa;
		} catch (Exception e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ��������");
		}
	}
	/**
	 * ҵ���ʻ���ͣ
	 * @param said��ҵ���ʻ�ID
	 * @throws ServiceException
	 * @throws CreateException
	 */
	public void suspend(int said) throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();

		//�õ�CSIID
		CustServiceInteraction csiEJB = (CustServiceInteraction)context.get(Service.CSI);
		int csiID = csiEJB.getId().intValue();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			if (!sa.getStatus().equals(
					CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL)) {
				LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������״̬��������ͣ��");
				throw new ServiceException("ҵ���ʻ���������״̬��������ͣ��");
			}
			//�޸�ҵ���ʻ�״̬----������ͣ
			sa
					.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_REQUESTSUSPEND);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
			//����ҵ���ʻ�����
			if(csiEJB.getCreateReason()!=null){
				sa.setDescription(BusinessUtility.getCsiActionReasonDesc(csiEJB.getType(),csiEJB.getCreateReason()));
			}

			//�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());

			//��Ʒ������Ӳ���豸
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String linkToDevice1SerialNo = "";
			String linkToDevice2SerialNo = "";

			//�������CustomerProductService��pauseCustomerProduct�����ʹ���ϵͳ�¼�
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
				//ֻ�в�Ϊȡ��״̬�Ĳ�Ʒ������ͣ
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
					cpIDList.add(cp.getPsID());
			}
			cpService.pauseCustomerProduct(cpIDList);

			//�õ��豸���к�
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

			//����ҵ���ʻ���ϵͳ�¼�
			SystemEventRecorder.addEvent4ServiceAccount(
					SystemEventRecorder.SE_SERVICEACCOUNT_PAUSE, customerid,
					accountid, said, csiID, 
					linkToDevice1,linkToDevice1SerialNo, 
					linkToDevice2,linkToDevice2SerialNo, 
					PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
			
			//����ϵͳ��־��¼
			String logDesc="";
			if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
			}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",";
			}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice2SerialNo+",";
			}
			
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), customerid,
					SystemLogRecorder.LOGMODULE_CUSTSERV, "ҵ���ʻ�ͣ��", "ҵ���ʻ�ͣ��������ID:"+csiID
					+",ҵ���ʻ�ID��"
							+ said+","+logDesc, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			throw new ServiceException("ҵ���ʻ����ҳ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����ҵ���ʻ�ϵͳ�¼�����");
			throw new ServiceException("����ҵ���ʻ�ϵͳ�¼�����");
		}
	}

	/**
	 * �豸ת��
	 * @param said��ҵ���ʻ�ID,dopost:�Ƿ�¼����Ϣ
	 * @throws ServiceException
	 * @throws CreateException
	 */
	public void rent(int said,boolean doPost) throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();
		
		//�õ�CSIID
		CustServiceInteraction csiEJB = (CustServiceInteraction)context.get(Service.CSI);
		int csiID = csiEJB.getId().intValue();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			if (!sa.getStatus().equals(
					CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL)) {
				LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������״̬�������豸ת�⣡");
				throw new ServiceException("ҵ���ʻ���������״̬�������豸ת�⣡");
			}
			
			//����ҵ���ʻ�����
			if(csiEJB.getCreateReason()!=null){
				sa.setDescription(BusinessUtility.getCsiActionReasonDesc(csiEJB.getType(),csiEJB.getCreateReason()));
			}

			//�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());

			//��Ʒ������Ӳ���豸
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String SerialNo = null;
			String linkToDevice1SerialNo=null;
            String linkToDevice2SerialNo=null;
			//�޸��豸��Ϣ�ʹ���ϵͳ�¼�
			
			//����ϵͳ��־��¼
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
				//ֻ�в�Ϊȡ��״̬�Ĳ�Ʒ����ת��
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
				{
					if(doPost){										
		                	terDev=tdhome.findByPrimaryKey(new Integer(cp.getDeviceID()));
		                	SerialNo=terDev.getSerialNo();
		                	if(SerialNo!=null&&!SerialNo.equals("")){
		        				logDesc.append(terDev.getDeviceClass()+":"+SerialNo+",��Ʒ����ʱ��"+cp.getCreateTime()+";");
		        			}
		                	cp.setCreateTime(TimestampUtility.getCurrentTimestamp());
		                	cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
		                	terDev.setPurposeStrList(",R,");
					} 
				}
			}
			
			TerminalDeviceHome terminalDeviceHome = HomeLocater.getTerminalDeviceHome();
			//�õ��豸���к�
			if (linkToDevice1 != 0) {
				linkToDevice1SerialNo = terminalDeviceHome.findByPrimaryKey(new Integer(linkToDevice1)).getSerialNo();			
			}
			if (linkToDevice2 != 0) {				
				linkToDevice2SerialNo = terminalDeviceHome.findByPrimaryKey(new Integer(linkToDevice2)).getSerialNo();				
			}

			//����ҵ���ʻ���ϵͳ�¼�
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
					SystemLogRecorder.LOGMODULE_CUSTSERV, "�豸ת��", "�豸ת�⣬����ID:"+csiID
					+",ҵ���ʻ�ID��"
							+ said+","+logDesc.toString(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			e.printStackTrace();
			throw new ServiceException("ҵ���ʻ����ҳ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����ҵ���ʻ�ϵͳ�¼�����");
			throw new ServiceException("����ҵ���ʻ�ϵͳ�¼�����");
		}
	}
	/**
	 *  �ָ���ͣ״̬��ҵ���ʻ�
	 * @param said��ҵ���ʻ�ID
	 * @throws ServiceException
	 */
	public void resume(ServiceAccountEJBEvent event) throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();

		//�õ�CSIID
		CustServiceInteraction csiEJB = (CustServiceInteraction)context.get(Service.CSI);
		int csiID = csiEJB.getId().intValue();
		int said =event.getServiceAccountID();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			if (!(sa.getStatus().equals(
					CommonConstDefinition.SERVICEACCOUNT_STATUS_REQUESTSUSPEND) || sa
					.getStatus()
					.equals(
							CommonConstDefinition.SERVICEACCOUNT_STATUS_SYSTEMSUSPEND))) {
				LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ�������ͣ״̬�����ָܻ���");
				throw new ServiceException("ҵ���ʻ�������ͣ״̬�����ָܻ���");
			}
			//�޸�ҵ���ʻ�״̬----����
			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
			//����ҵ���ʻ�����
			//if(csiEJB.getCreateReason()!=null){ 2008-06-27
				sa.setDescription(BusinessUtility.getCsiActionReasonDesc(csiEJB.getType(),csiEJB.getCreateReason()));
			//}
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
			//�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());

			context.put(Service.CUSTOMER_PRODUCT_LIST, cpList);
			//��Ʒ������Ӳ���豸
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String linkToDevice1SerialNo = "";
			String linkToDevice2SerialNo = "";

			//�������CustomerProductService��pauseCustomerProduct�����ʹ���ϵͳ�¼�
			CustomerProductService cpService = new CustomerProductService(
					context, 0, 0, said);
			Iterator itCP = cpList.iterator();
			Collection cpIDList = new ArrayList();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				if (!event.getColPsid().contains(cp.getPsID()))
						continue;
				//����Ӳ����Ʒ
				if (cp.getDeviceID() >0) continue;

				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();
				//ֻ�в�Ϊȡ��״̬�Ĳ�Ʒ���ָܻ�
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus())))
					cpIDList.add(cp.getPsID());
			}
			cpService.resumeCustomerProduct(cpIDList);

			//�õ��豸���к�
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

			//����ҵ���ʻ���ϵͳ�¼�
			SystemEventRecorder.addEvent4ServiceAccount(
					SystemEventRecorder.SE_SERVICEACCOUNT_RESUME, customerid,
					accountid, said, csiID, 
					linkToDevice1,linkToDevice1SerialNo, 
					linkToDevice2,linkToDevice2SerialNo, 
					PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_REQUESTSUSPEND);

			//����ϵͳ��־��¼
			String logDesc="";
			if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
			}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",";
			}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice2SerialNo+",";
			}
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), customerid,
					SystemLogRecorder.LOGMODULE_CUSTSERV, "ҵ���ʻ�����", "ҵ���ʻ�����,����ID:"+csiID
					+",ҵ���ʻ�:"
							+ said + ","+logDesc, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			throw new ServiceException("ҵ���ʻ����ҳ���");
		}

		catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����ҵ���ʻ�ϵͳ�¼�����");
			throw new ServiceException("����ҵ���ʻ�ϵͳ�¼�����");
		}

	}

	/**
	 * �û�����/ҵ���ʻ��������������豸��ת
	 * @param said
	 * @throws ServiceException
	 */
/*
	public void closeOnly(int said) throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();

		//�õ�CSIID
		int csiID = getCSIID();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));

			//�޸�ҵ���ʻ�״̬----ȡ��
			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL);

			//�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			context.put(Service.CUSTOMER_PRODUCT_LIST, cpList);
			
			//�������CustomerProductService��pauseCustomerProduct�����ʹ���ϵͳ�¼�
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

			//����ҵ���ʻ���ϵͳ�¼�
			SystemEventRecorder.AddEvent4Customer(
					SystemEventRecorder.SE_SERVICEACCOUNT_CANCEL, customerid,
					accountid, said, csiID, PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE);

			//�绰����״̬�����ʹ�ü�¼
			if (sa.getServiceID() == 3)
				releasePhoneNo(sa.getServiceCode(),said);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			throw new ServiceException("ҵ���ʻ����ҳ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����ҵ���ʻ�ϵͳ�¼�����");
			throw new ServiceException("����ҵ���ʻ�ϵͳ�¼�����");
		}
	}
*/
	/**
	 * �û��˻��������豸����ת
	 * @param said�� ҵ���ʻ�ID
	 * @throws ServiceException
	 */
	public void close(int said,boolean returnDevice) throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();

		//�õ�CSIID
		int csiID = getCSIID();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));

			//�޸�ҵ���ʻ�״̬----ȡ��
			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
			//�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
//			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList= BusinessUtility.getPsIDListByServiceAccount(said, null," STATUS<>'"+CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL+"'");
//			Collection cpList = cpHome.findByServiceAccountID(sa
//					.getServiceAccountID().intValue());

			//�������CustomerProductService��pauseCustomerProduct�����ʹ���ϵͳ�¼�
			CustomerProductService cpService = new CustomerProductService(
					context, sa.getCustomerID(), 0, said);

			cpService.cancelCustomerProduct(cpList,returnDevice);

			//����ҵ���ʻ���ϵͳ�¼�
			SystemEventRecorder.AddEvent4Customer(
					SystemEventRecorder.SE_SERVICEACCOUNT_CANCEL, customerid,
					accountid, said, csiID, PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE);

	    	//�绰����״̬�����ʹ�ü�¼
			if (sa.getServiceID() == 3)
				releasePhoneNo(sa.getServiceCode(),said);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			throw new ServiceException("ҵ���ʻ����ҳ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����ҵ���ʻ�ϵͳ�¼�����");
			throw new ServiceException("����ҵ���ʻ�ϵͳ�¼�����");
		}
	}
	/**
	 * �û�Ԥ�˻��������豸����ת
	 * @param said�� ҵ���ʻ�ID
	 * @throws ServiceException
	 */
	public void beforehandClose(int said,boolean returnDevice) throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();

		//�õ�CSIID
		int csiID = getCSIID();

		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			//�޸�ҵ���ʻ�״̬----Ԥ��
			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_BEFOREHANDCLOSE);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
	    	//�绰����״̬�����ʹ�ü�¼
			if (sa.getServiceID() == 3)
				releasePhoneNo(sa.getServiceCode(),said);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			throw new ServiceException("ҵ���ʻ����ҳ���");
		} 
	}
	
	public void batchResendEMM(int eventClass) throws ServiceException {
		//���customerid��accountid�Ƿ����
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
	 * ��Ʒ����Ȩ
	 * @param said
	 * @throws ServiceException
	 */
	public String resendEMM(int said ,int eventClass) throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();
		if (said == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(said));
			//�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			//��Ʒ������Ӳ���豸
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
			//�õ��豸���к�
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

			//���ҵ���˻������ܿ��Ĳ�Ʒ�źͿͻ���Ʒ��
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
				throw new ServiceException("�������ܿ��Ĳ�Ʒ�źͿͻ���Ʒ��ʧ�ܣ�");
			}
			//����ҵ���ʻ���ϵͳ�¼�
			SystemEventRecorder.addEvent4ServiceAccount(
					eventClass, customerid, accountid,
					said, productid, psid, linkToDevice1,
					linkToDevice1SerialNo, linkToDevice2,
					linkToDevice2SerialNo, getOperatorID(), null,
					SystemEventRecorder.SE_STATUS_CREATE);

			//����ϵͳ��־��¼
			String logDesc="��Ʒ����Ȩ,ҵ���ʻ�ID:"	 + said + ",";
			if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc=logDesc+",�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
			}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
				logDesc=logDesc+",�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",";
			}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc=logDesc+",�漰���豸���к�:"+"(1)"+linkToDevice2SerialNo+",";
			}
			return logDesc;
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			throw new ServiceException("ҵ���ʻ����ҳ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����ҵ���ʻ�ϵͳ�¼�����");
			throw new ServiceException("����ҵ���ʻ�ϵͳ�¼�����");
		}
	}

	/**
	 * ҵ���ʻ�����
	 * @param saID �����������û���ҵ���ʻ�ID
	 * @param customerID������Ŀ��ͻ�
	 * @param accountID������Ŀ���ʻ�
	 * @throws ServiceException
	 */
	public void transfer(int saID, int customerID, int accountID)
			throws ServiceException {

		LogUtility.log(clazz, LogLevel.DEBUG, "ҵ���ʻ�������ʼ��");
		//���customerid��accountid�Ƿ����
		checkServiceContext();

		if (saID == 0 || customerID == 0 || accountID == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}

		Collection deviceIDList = new ArrayList();
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(saID));

			if (!sa.getStatus().equals(
					CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL)) {
				LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������״̬�����ܹ�����");
				throw new ServiceException("ҵ���ʻ���������״̬�����ܹ�����");
			}
			LogUtility.log(clazz, LogLevel.DEBUG, "Դҵ���ʻ�:"+sa);

			sa.setCustomerID(customerID);
			sa.setDtLastmod(TimestampUtility.getCurrentDate());
//			sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT);

			//�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			LogUtility.log(clazz, LogLevel.DEBUG, "Դҵ���ʻ��²�Ʒ:"+cpList);

			
			//��Ʒ������Ӳ���豸
			int linkToDevice1 = 0;
			int linkToDevice2 = 0;
			String linkToDevice1SerialNo = "";
			String linkToDevice2SerialNo = "";
			//�޸Ŀͻ���Ʒ�ֶ�
			Iterator itCP = cpList.iterator();
			while (itCP.hasNext()) {
				CustomerProduct cp = (CustomerProduct) itCP.next();
				//ȡ�ö�Ӧ��ҵ���ʻ�������ܷ���Ȩ���豸
				if (cp.getLinkToDevice1() != 0)
					linkToDevice1 = cp.getLinkToDevice1();
				if (cp.getLinkToDevice2() != 0)
					linkToDevice2 = cp.getLinkToDevice2();

				//ֻ�в�Ϊȡ��״̬�Ĳ�Ʒ���ָܻ�
				if (!(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL
						.equals(cp.getStatus()))) {
					cp.setCustomerID(customerID);
					cp.setAccountID(accountID);
//					cp.setStatus(CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_INIT);
//					cp.setActivatetime(TimestampUtility.getCurrentTimestamp());
					cp.setDtLastmod(TimestampUtility.getCurrentDate());
					
					//������豸��������Ӧ����ת
					if (cp.getDeviceID() > 0)
						deviceIDList.add(new Integer(cp.getDeviceID()));
				}
			}
			//�豸��ת
			LogUtility.log(clazz, LogLevel.DEBUG, "ҵ���ʻ�����Ҫ��ת���豸:"+deviceIDList);

			deviceTransitionForTranfer(deviceIDList, customerID);

			//�޸�ҵ���ʻ����Ż�
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
			//�õ��豸���к�
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
			//����ҵ���ʻ�������ϵͳ�¼�
			SystemEventRecorder.addEvent4ServiceAccount(
					SystemEventRecorder.SE_SERVICEACCOUNT_TRANSFER, customerID,
					accountID, saID, csiID, 
					linkToDevice1,linkToDevice1SerialNo, 
					linkToDevice2,linkToDevice2SerialNo, 
					PublicService.getCurrentOperatorID(context),
					SystemEventRecorder.SE_STATUS_CREATE,sa.getStatus());
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			throw new ServiceException("ҵ���ʻ����ҳ���");
		}catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����ҵ���ʹ���ϵͳ�¼�����");
			throw new ServiceException("����ҵ���ʹ���ϵͳ�¼�����");
		}
	}

	/**
	 * ������ԡ�����ԡ��������롢������Ϣ��modified by wesley��
	 * @param saID :��ҵ���ʻ��ɣ�
	 * @param content : ������Ϣ����
	 * @param type : ��������: M--������ԡ�D--��������ԡ�C--�������롢S--������Ϣ
	 * @throws ServiceException
	 */
	public void matchSCtoSTB(int saID, String content, String type)
			throws ServiceException {
		//���customerid��accountid�Ƿ����
		checkServiceContext();
		if (saID == 0) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���������");
			throw new ServiceException("ҵ���ʻ���������");
		}
		if (!(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_D)
				|| type.equals(CommonConstDefinition.MATCH_SC_TO_STB_M)
				|| type.equals(CommonConstDefinition.MATCH_SE_CA_CLEARPWD) || type
				.equals(CommonConstDefinition.MATCH_SE_CA_SENDMAIL))) {
			LogUtility.log(clazz, LogLevel.ERROR, "δ֪�������ͣ�");
			throw new ServiceException("δ֪�������ͣ�");
		}
		String log = "";
		String logDesc="";
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(saID));
			//�õ���ͬҵ���ʻ��µ����еĲ�Ʒ
			CustomerProductHome cpHome = HomeLocater.getCustomerProductHome();
			Collection cpList = cpHome.findByServiceAccountID(sa
					.getServiceAccountID().intValue());
			//��Ʒ������Ӳ���豸
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
			//�õ��豸���к�
			
			//�豸���/����� 2008-1-28
			Collection deviceIDList=new ArrayList();
			deviceIDList.addAll(BusinessUtility.getDeviceListBySaID(saID));
			LogUtility.log(clazz, LogLevel.INFO, "����matchSCtoSTB����deviceIDList����"+deviceIDList);
			CustomerProductService customerProductService = new CustomerProductService(context);
			if(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_M)){
				if(deviceIDList.size() >= 2) //���������������������豸���������
					customerProductService.deviceMatch(deviceIDList);
				else
					throw new ServiceException("����豸�����ڣ�");	
			}else if(type.equals(CommonConstDefinition.MATCH_SC_TO_STB_D)){
				if(deviceIDList.size() >= 2) //���������������������豸�����ܽ����
					customerProductService.unchainDeviceMatch(deviceIDList);
				else
					throw new ServiceException("����豸�����ڣ�");
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
			
			//���ҵ���˻������ܿ��Ĳ�Ʒ�źͿͻ���Ʒ��
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
				throw new ServiceException("�������ܿ��Ĳ�Ʒ�źͿͻ���Ʒ��ʧ�ܣ�");
			}
			logDesc="ҵ���ʻ�ID��"+ saID+",";
			if (CommonConstDefinition.MATCH_SC_TO_STB_M.equals(type)) {
				//�����������ϵͳ�¼�
				log = "�������";
				SystemEventRecorder.addEvent4ServiceAccount(
						SystemEventRecorder.SE_CA_MATCH, customerid, accountid,
						saID, productid, psid, linkToDevice1,
						linkToDevice1SerialNo, linkToDevice2,
						linkToDevice2SerialNo, getOperatorID(), content,
						SystemEventRecorder.SE_STATUS_CREATE);
			} else if (CommonConstDefinition.MATCH_SC_TO_STB_D.equals(type)) {
				//�������������ϵͳ�¼�
				log = "���������";
				SystemEventRecorder.addEvent4ServiceAccount(
						SystemEventRecorder.SE_CA_UNMATCH, customerid,
						accountid, saID, productid, psid, linkToDevice1,
						linkToDevice1SerialNo, linkToDevice2,
						linkToDevice2SerialNo, getOperatorID(), content,
						SystemEventRecorder.SE_STATUS_CREATE);
			} else if (CommonConstDefinition.MATCH_SE_CA_CLEARPWD.equals(type)) {
				//������������ϵͳ�¼�
				log = "��������";
				SystemEventRecorder.addEvent4ServiceAccount(
						SystemEventRecorder.SE_CA_CLEARPWD, customerid,
						accountid, saID, productid, psid, linkToDevice1,
						linkToDevice1SerialNo, linkToDevice2,
						linkToDevice2SerialNo, getOperatorID(), content,
						SystemEventRecorder.SE_STATUS_CREATE);
			} else if (CommonConstDefinition.MATCH_SE_CA_SENDMAIL.equals(type)) {
				//����������Ϣϵͳ�¼�
				log = "������Ϣ";
				SystemEventRecorder.addEvent4ServiceAccount(
						SystemEventRecorder.SE_CA_SENDMAIL, customerid,
						accountid, saID, productid, psid, linkToDevice1,
						linkToDevice1SerialNo, linkToDevice2,
						linkToDevice2SerialNo, getOperatorID(), content,
						SystemEventRecorder.SE_STATUS_CREATE);
			}
			if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")&&linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",(2)"+linkToDevice2SerialNo+",";
			}else if(linkToDevice1SerialNo!=null&&!linkToDevice1SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice1SerialNo+",";
			}else if(linkToDevice2SerialNo!=null&&!linkToDevice2SerialNo.equals("")){
				logDesc="�漰���豸���к�:"+"(1)"+linkToDevice2SerialNo+",";
			}
			if(content!=null&&!content.equals("")){
				logDesc+=",���͵���Ϣ:"+content;
			}
			//����ϵͳ��־��¼
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), customerid,
					SystemLogRecorder.LOGMODULE_CUSTSERV, log, log+","+logDesc, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ���λ����");
			throw new ServiceException("ҵ���ʻ���λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "ҵ���ʻ����ҳ���");
			throw new ServiceException("ҵ���ʻ����ҳ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "����ҵ���ʻ�ϵͳ�¼�����");
			throw new ServiceException("����ҵ���ʻ�ϵͳ�¼�����");
		}
	}

	/**
	 * �޸�ҵ���ʻ���Ϣ
	 * @param dto: ҵ���ʻ���Ϣ
	 * @throws ServiceException
	 */
	public void update(ServiceAccountDTO dto) throws ServiceException {
		if (dto == null || dto.getServiceAccountID() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "ҵ���ʻ�Ϊ�գ��޷�����!");
			throw new ServiceException("ҵ���ʻ�Ϊ�գ��޷������ʻ���Ϣ");
		}
		try {
			ServiceAccountHome saHome = HomeLocater.getServiceAccountHome();
			ServiceAccount sa = saHome.findByPrimaryKey(new Integer(dto
					.getServiceAccountID()));
			if (sa.ejbUpdate(dto) == -1) {
				LogUtility.log(clazz, LogLevel.WARN,
						"����ҵ���ʻ�����ԭ��sa.ejbUpdate(dto)==-1");
				throw new ServiceException("����ҵ���ʻ�����");
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "ҵ���ʻ���λ����!");
			throw new ServiceException("ҵ���ʻ���λ�����޷������ʻ���Ϣ");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.WARN, "ҵ���ʻ����ҳ����޷�����!");
			throw new ServiceException("ҵ���ʻ����ҳ����޷������ʻ���Ϣ");
		}
	}

	/**
	 * �޸�ҵ���ʻ���״̬�������޸ĸ�ҵ���ʻ��²�Ʒ��״̬��
	 * @param customerID
	 * @param actionType
	 * @throws ServiceException
	 */
	public void updateSAStatus(Collection saIDList, int actionType)
			throws ServiceException {
		if (saIDList == null || saIDList.size() == 0 || actionType == 0)
			throw new ServiceException("�޸Ŀͻ�ҵ���ʻ�״̬��������!");

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
				//�����Ǩ�ơ���ع��� �����������ҵ���ʻ�״̬�޸�Ϊ��ʼ,���Ѹ�ҵ���ʻ��µ�������Ʒ�޸�Ϊ��ʼ
				if (EJBEvent.MOVETODIFFERENTPLACE == actionType
						|| EJBEvent.TRANSFERTODIFFERENTPLACE == actionType) {
					if (CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL
							.equals(sa.getStatus())) {
						sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT);
						sa.setDtLastmod(TimestampUtility.getCurrentTimestamp());

						cpList = cpHome.findByServiceAccountID(sa
								.getServiceAccountID().intValue());
						//�޸Ĳ�Ʒ״̬
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
				//�����¼�밲װ��Ϣ����ѳ�ʼ�ĸ�Ϊ����
				else if (EJBEvent.REGISTER_INSTALLATION_INFO == actionType) {
					if (CommonConstDefinition.SERVICEACCOUNT_STATUS_INIT
							.equals(sa.getStatus())) {
						sa.setStatus(CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL);
						sa.setDtLastmod(TimestampUtility.getCurrentTimestamp());
												
						cpList = cpHome.findByServiceAccountID(sa
								.getServiceAccountID().intValue());
						//�޸Ĳ�Ʒ״̬
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
			LogUtility.log(clazz, LogLevel.WARN, "�޸Ŀͻ�ҵ���ʻ�״̬ʱ,�޷���λҵ���ʻ�!");
			throw new ServiceException("�޸Ŀͻ�ҵ���ʻ�״̬ʱ,�޷���λҵ���ʻ���");
		} catch (FinderException e2) {
			LogUtility.log(clazz, LogLevel.WARN, "�޸Ŀͻ�ҵ���ʻ�״̬ʱ,�޷����ҵ�ҵ���ʻ�!");
			throw new ServiceException("�޸Ŀͻ�ҵ���ʻ�״̬ʱ,�޷����ҵ�ҵ���ʻ���");
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
	 * ���customerid��accountid�Ƿ����
	 * @throws ServiceException
	 */
	private void checkServiceContext() throws ServiceException {
		if (customerid == 0) {
			Customer customer = (Customer) context.get(Service.CUSTOMER);
			if (customer == null) {
				Integer custID = (Integer) context.get(Service.CUSTOMER_ID);
				if (custID == null)
					throw new ServiceException("����������û�пͻ�����");
				customerid = custID.intValue();
			} else
				customerid = customer.getCustomerID().intValue();
		}
		/*if (accountid == 0) {
		 Account account = (Account) context.get(Service.ACCOUNT);
		 if (account == null) {
		 Integer acctID = (Integer) context.get(Service.ACCOUNT_ID);
		 if (acctID == null)
		 throw new ServiceException("����������û���ʻ�����");
		 accountid = acctID.intValue();
		 } else
		 accountid = account.getAccountID().intValue();
		 }*/
	}

	/**
	 * ͨ�����context�õ�csiid
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
	 *�����ڲ���������
	 * @param dtoInTotalList��
	 * @param dtoInSAList
	 * @param map
	 * @throws ServiceException
	 */
	private void fillArrayMap(CustomerProductDTO dtoInTotalList,
			CustomerProductDTO dtoInSAList, Map map) throws ServiceException {
		Collection list = (ArrayList) map.get(dtoInSAList);
		if (list == null)
			throw new ServiceException("�Ҳ����ܴ���ҵ���ʻ��Ĳ�Ʒ");
		list.add(dtoInTotalList);
		map.put(dtoInSAList, list);
	}

	/**
	 * �豸��ת
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
			LogUtility.log(clazz, LogLevel.DEBUG, "�����豸��ת");

			//�����豸��ת��¼
			LogUtility.log(clazz, LogLevel.DEBUG, "�����豸��ת��¼");
			DeviceTransitionDTO deviceTransitionDto = new DeviceTransitionDTO();
			deviceTransitionDto.setCreateTime(new Timestamp(System
					.currentTimeMillis()));

			/***************************************************
			 * ����ĵ���û��˵���˴��Ķ�������ʱ���������Ժ��޸�
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
				//�豸��״̬��������
				if (!terminalDevice.getStatus().equals(
						CommonConstDefinition.DEVICE_STATUS_SOLD)){
					LogUtility.log(clazz, LogLevel.ERROR, "�ò�Ʒ״̬���Ǵ�������״̬���豸ID��" + deviceID);
					throw new ServiceException("�豸״̬�쳣,������ɹ���!");
				}
				
				String fromType = terminalDevice.getAddressType();
				int fromID = terminalDevice.getAddressID();
				if (!"".equals(fromType))
					strFromType = fromType;
				if (fromID != 0)
					intFromID = fromID;

				//�����µĵ�ַ���ͺ�ID
				terminalDevice.setAddressType(CommonConstDefinition.DEVICE_ADDRESSTYPE_WITHUSER);
				terminalDevice.setAddressID(toCustomerID);

				//�����豸��ת��ϸ
				LogUtility.log(clazz, LogLevel.DEBUG, "�����豸��ת��ϸ");
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
			LogUtility.log(clazz, LogLevel.ERROR, "�豸��λ����");
			throw new ServiceException("�豸��λ����");
		} catch (FinderException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�豸���ҳ���");
			throw new ServiceException("�豸���ҳ���");
		} catch (CreateException e2) {
			LogUtility.log(clazz, LogLevel.ERROR, "�豸��ת��������");
			throw new ServiceException("�豸��ת��������");
		}

		LogUtility.log(clazz, LogLevel.DEBUG, "�����豸��ת");
	}

	public void updatePhoneNo(int serviceAccountID,int itemID, String oldPhoneNo,
			String newPhoneNo) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "������¿�ʼ");
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
			LogUtility.log(clazz, LogLevel.ERROR, "�������");
			throw new ServiceException("�������");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "�����ʺŲ��ҳ���");
			throw new ServiceException("�����ʺŲ��ҳ���");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "������½���");
	}

    /**
     * ���phoneNo�Ƿ��ڵ绰���д��ڣ�״̬�Ƿ�Ϊ����
     * @throws ServiceException
     */
    private boolean checkPhoneNo(String phoneNo,int itemID) throws ServiceException{
    	
		ResourcePhoneNoHome phoneHome;
		ResourcePhoneNo phone;
		try {
			phoneHome = HomeLocater.getResourcePhoneNoHome();
			phone = phoneHome.findByPrimaryKey(new Integer(itemID));
			if(!(phoneNo.equals(phone.getPhoneNo()) && phone.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_AVAILABLE)))
				throw new ServiceException("�绰���� "+phoneNo+" ����ȷ��");
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ��λ����!");
        	throw new ServiceException("�绰������Դ��λ�����޷����µ绰������Դ");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ���ҳ����޷�����!");
        	throw new ServiceException("�绰������Դ���ҳ����޷����µ绰������Դ");
        }
    	return true;
    }
    
    /**
     * ���phoneNo�Ƿ��ڵ绰���д��ڣ�״̬�Ƿ�Ϊ���� ԤԼ����ר��
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
					throw new ServiceException("�绰���벻��ȷ��");
			} else {
				if (!(phoneNo.equals(phone.getPhoneNo()) && phone.getStatus()
						.equals(CommonConstDefinition.PHONENO_STATUS_AVAILABLE)))
					throw new ServiceException("�绰���벻��ȷ��");
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "�绰������Դ��λ����!");
			throw new ServiceException("�绰������Դ��λ�����޷����µ绰������Դ");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.WARN, "�绰������Դ���ҳ����޷�����!");
			throw new ServiceException("�绰������Դ���ҳ����޷����µ绰������Դ");
		}
		return true;
	}
    /**
	 * �����绰������Դ������ʹ����־
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
				phoneUseDto.setDescription("�绰���뱻����");
				phoneUseDto.setNetworkID("");
				phoneUseDto.setPsID(0);
				
				BusinessUtility.addPhoneUseLog(phoneUseDto);

			}
			else
				throw new ServiceException("�绰���벻��ȷ��");
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ��λ����!");
        	throw new ServiceException("�绰������Դ��λ����!");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ���ҳ����޷�����!");
        	throw new ServiceException("�绰������Դ���ҳ����޷����µ绰������Դ!");
        }
    	return true;
    }
    /**
     * ռ�õ绰������Դ������ʹ����־ ԤԼ����ר��
     * @throws ServiceException
     */
    private boolean usePhoneNoForBookOpen(String phoneNo,int itemID,int saID,ArrayList serviceCodeList) throws ServiceException{

    	if(serviceCodeList == null) serviceCodeList = new ArrayList();
		ResourcePhoneNoHome phoneHome;
		ResourcePhoneNo phone;
		try {
			phoneHome = HomeLocater.getResourcePhoneNoHome();
			phone = phoneHome.findByPrimaryKey(new Integer(itemID));
			//ԤԼ�����ұȽϵĽ���Ǻ�����ͬ ��ԭ״̬��PHONENO_STATUS_LOCKEDתΪPHONENO_STATUS_USED
			//ԤԼ�����ұȽϵĽ���Ǻ��벻ͬ ��ԭ״̬��PHONENO_STATUS_AVAILABLEתΪPHONENO_STATUS_USED 
			//			ͬʱҪ��ԭ���ĺ�����PHONENO_STATUS_LOCKEDתΪPHONENO_STATUS_AVAILABLE
			
			if(!phoneNo.equals(phone.getPhoneNo()))
				throw new ServiceException("�绰���벻��ȷ��");


			//����û�б��
			if(serviceCodeList.contains(phoneNo))
			{
				if(!phone.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_LOCKED))
					throw new ServiceException("�绰���벻��ȷ��");
			}
			//ԭ���е绰���� ���ڸ��� /��ԭ��û��ip�绰ҵ�� ������
			//��Ҫ��ԭ���ĺ������,����ж������ ����Ĵ���Ҫ��ǰ̨���ݵ��Ϻ�����º��밴�ն�Ӧ�����д��
			else
			{
				System.out.println("__________________serviceCodeList="+serviceCodeList);
				System.out.println("__________________phoneNo="+phoneNo);
				if(!phone.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_AVAILABLE))
					throw new ServiceException("�绰���벻��ȷ��");
				if(serviceCodeList.size()>0 && !"".equals((String)serviceCodeList.get(0)))
				{
					String oldPhoneNo = (String)serviceCodeList.get(0);
					ResourcePhoneNoDTO oldDto = BusinessUtility.getDTOByPhoneNo(oldPhoneNo);
					if(!oldDto.getStatus().equals(CommonConstDefinition.PHONENO_STATUS_LOCKED))
						throw new ServiceException("�绰����״̬����ȷ��");
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
			phoneUseDto.setDescription("�绰���뱻ʹ��");
			phoneUseDto.setNetworkID("");
			phoneUseDto.setPsID(0);
			
			BusinessUtility.addPhoneUseLog(phoneUseDto);
				
			if(serviceCodeList.size()>0)serviceCodeList.remove(0);
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ��λ����!");
        	throw new ServiceException("�绰������Դ��λ�����޷����µ绰������Դ");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ���ҳ����޷�����!");
        	throw new ServiceException("�绰������Դ���ҳ����޷����µ绰������Դ");
        }
    	return true;
    }
    /**
     * ռ�õ绰������Դ������ʹ����־
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
				phoneUseDto.setDescription("�绰���뱻ʹ��");
				phoneUseDto.setNetworkID("");
				phoneUseDto.setPsID(0);
				
				BusinessUtility.addPhoneUseLog(phoneUseDto);

			}
			else
				throw new ServiceException("�绰���벻��ȷ��");
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ��λ����!");
        	throw new ServiceException("�绰������Դ��λ�����޷����µ绰������Դ");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ���ҳ����޷�����!");
        	throw new ServiceException("�绰������Դ���ҳ����޷����µ绰������Դ");
        }
    	return true;
    }
    /**
     *���յ绰������Դ������ʹ����־
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
				phoneUseDto.setDescription("�ͷŵ绰����");
				phoneUseDto.setNetworkID("");
				phoneUseDto.setPsID(0);
				
				BusinessUtility.addPhoneUseLog(phoneUseDto);

			}
			else
				throw new ServiceException("�绰���벻��ȷ��");
		}catch(HomeFactoryException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ��λ����!");
        	throw new ServiceException("�绰������Դ��λ�����޷����µ绰������Դ");
        }
        catch(FinderException e){
        	LogUtility.log(clazz,LogLevel.WARN,"�绰������Դ���ҳ����޷�����!");
        	throw new ServiceException("�绰������Դ���ҳ����޷����µ绰������Դ");
        }
    	return true;
    }   
    
    /**
     * ��Ʒ���麯�������ݴ��Ĳ�ƷID�б�Integer���󣩣��������ܴ���ҵ���ʻ��Ĳ�ƷIDΪKEY������������ϵ�Ĳ�Ʒ��ID��Integer�������
     * CollectionΪvalue��Map����ע�⣺value������key
     * @param productIDList
     * @return
     * @throws ServiceException 
     */
    public Map productSplitToSA(Collection productIDList) throws ServiceException{
    	LogUtility.log(clazz, LogLevel.DEBUG, "productSplitToSA������Ʒ��ҵ���ʻ����飡");
    	LogUtility.log(clazz, LogLevel.DEBUG, "����Ĳ�����productIDList����" +productIDList);
    	if(productDependency==null){
        	productDependency=BusinessUtility.getAllProductDepentDefineList();
    	}
    	
    	if(productIDList==null || productIDList.isEmpty())
    		return null;
    	
    	Map saProductIDsMap=new HashMap();
    	
		Collection productIDForSAIDList = (ArrayList) ((ArrayList) productIDList).clone();
		//�ܴ���ҵ���ʻ���product
		Collection saProductIDList = BusinessUtility.getSAProductIDListFromPoductIDList(productIDForSAIDList);
		LogUtility.log(clazz, LogLevel.DEBUG, "�ܴ���ҵ���ʻ��Ĳ�ƷΪ��" + saProductIDList);
		if(saProductIDList==null || saProductIDList.isEmpty())
			return null;
		
		//���������Ĳ�ƷID�б�
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
		LogUtility.log(clazz, LogLevel.DEBUG, "������ʣ�µĲ�Ʒ��" + opProductIDList);
		if (opProductIDList.size() > 0) {
			ArrayList li=new ArrayList();
			li.add(opProductIDList);
			String err="������������Ʒ:"+BusinessUtility.getProductDesByProductID(li)+",û����Ч������.";
			LogUtility.log(clazz, LogLevel.ERROR, err);
			throw new ServiceException(err);
		}
		return saProductIDsMap;
    }
    
    public int calculatePointForIPPVCharge(CAWalletEJBEvent inEvent)throws ServiceException{
		
		if(inEvent.getCawDto() == null || inEvent.getCawcrDto() == null)
			throw new ServiceException("��ֵ��Ϣ���������޷����������");
		LogUtility.log(clazz, LogLevel.DEBUG, "ҵ���ʻ�ID��" + inEvent.getCawDto().getServiceAccountId()+" ����СǮ����ֵ�������");
		
		int point = BusinessUtility.calculatePointForIPPV(inEvent.getCawDto().getCaWalletCode(),inEvent.getCawcrDto().getValue());
    	
		return point;
    }

	/**
	 * @param inEvent
	 */
	public int createCAWallet(CAWalletEJBEvent inEvent) throws ServiceException, CreateException {
		if(inEvent.getCawDto() == null || inEvent.getCawcrDto() == null)
			throw new ServiceException("��ֵ��Ϣ���������޷�����СǮ����");
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
				LogUtility.log(clazz,LogLevel.WARN,"СǮ����λ����!");
				throw new ServiceException("СǮ����λ�����޷�����СǮ��");
			}	
			return walletID;
	}

	/**
	 * @param inEvent
	 */
	public int chargeCAWallet(CAWalletEJBEvent inEvent) throws ServiceException, CreateException, FinderException {
		if(inEvent.getCawcrDto() == null)
			throw new ServiceException("��ֵ��Ϣ���������޷���ֵСǮ����");
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
			LogUtility.log(clazz,LogLevel.WARN,"СǮ����λ����!");
			throw new ServiceException("СǮ����λ�����޷�����СǮ��");
		}catch(FinderException e1){
			LogUtility.log(clazz,LogLevel.WARN,"СǮ����λ����");
			throw new ServiceException("СǮ�����Ҵ���");
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
			LogUtility.log(clazz,LogLevel.WARN,"СǮ����¼��λ����!");
			throw new ServiceException("СǮ����¼��λ�����޷�����СǮ��");
		}catch(FinderException e1){
			LogUtility.log(clazz,LogLevel.WARN,"СǮ����¼��λ����");
			throw new ServiceException("СǮ����¼���Ҵ���");
		}
		
	}
}
