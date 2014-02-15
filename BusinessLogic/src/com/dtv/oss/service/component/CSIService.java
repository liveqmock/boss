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
 * ������������صĶ���Ĵ������޸ġ�ɾ��������ԤԼ�߼��ȵ�
 */
public class CSIService extends AbstractService {
    private final static Class clazz = CSIService.class;
	private ServiceContext serviceContext=null;
	public CSIService(ServiceContext serviceContext){
		this.serviceContext=serviceContext;
	}
	/**
	 * ������Ʒʱ�Ĵ���������Ϣ
	 * 
	 * @throws ServiceException
	 */
	public void createAddCustomerProductCsiInfo(CustomerProductEJBEvent event)throws ServiceException{
			// ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();	
			}
			// ȡ�ÿͻ�ѡ����Ż���Ϣ
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	// ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();    	
	    	// ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();

	    	// ȡ��������Ϣ
	    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
	    	csiDTO.setCustomerID(customerID);
	    	csiDTO.setAccountID(accountID);
	    	// setContactPersonInfo(csiDTO,customer.getName(),customer.getTelephone(),customer.getTelephoneMobile());
	    	
	    	// ����������Ϣ
			createCustServiceInteraction(csiDTO);
			// ���������漰�Ŀͻ���Ʒ�����Ϣ
			if(event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP)){
				CommonBusinessParamDTO paramDto =new CommonBusinessParamDTO();
				paramDto.setServiceaccountID(event.getSaID());
				createCsiCustProductInfo( csiPackageArray, null, csiCampaignArray, csiDTO,paramDto);
			}else
				createCsiCustProductInfo( csiPackageArray, null, csiCampaignArray, csiDTO);

	}
	/**
	 * ����ҵ���˻���������Ϣ
	 * 
	 * @throws ServiceException
	 */
	public void createServiceAccountCsiInfo(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			// ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			// ȡ�ÿͻ�ѡ����Ż���Ϣ
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	// ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	
	    	// ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	// �ͻ���ַ��Ϣ
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());

	    	// ȡ��������Ϣ
	    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
	    	csiDTO.setCustomerID(customerID);
	    	csiDTO.setAccountID(accountID);
	    	
	    	// ����������Ϣ
			createCustServiceInteraction(csiDTO);
			// ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
			CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
			// ���������漰�Ŀͻ���Ʒ�����Ϣ
			createCsiCustProductInfo( csiPackageArray, null, csiCampaignArray, csiDTO);
	    	// �Ƿ񴴽�����
			if(isCreateJobcard(csiDTO)){
				CustomerDTO custoemrDto=new CustomerDTO();
				custoemrDto.setCustomerID(customer.getCustomerID().intValue());
				custoemrDto.setTelephone(customer.getTelephone());
				custoemrDto.setTelephoneMobile(customer.getTelephoneMobile());
				custoemrDto.setAddressID(customer.getAddressID());
				custoemrDto.setName(customer.getName());
				// ��������,���ù���service����������Ϣ
				InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
			    int jobcardID=installationJobCardService.createJobCardForCustomerOperation(csiDTO,custoemrDto,custAddrDto,event.getNextOrgID());
				// �������������Ĺ���id
				csiEJB.setReferjobcardID(jobcardID);
			}
			
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ�����Ҳ��������Ϣ");
			
		}
	}
	/**
	 * �ظ������ŵ�����ʱ������������,�ؼ���csicustomerproductinfo�Ĵ���,Ӱ�쵽customerproduct����,
	 * @param event
	 * @throws ServiceException
	 */
	public void createServiceAccountCsiInfoWithBatchBuy(ServiceAccountEJBEvent event)throws ServiceException{
		try {
			// ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
			Collection csiPackageArray = event.getCsiPackageArray();
			if(csiPackageArray==null){
				csiPackageArray=new ArrayList();
			}
			// ȡ�ÿͻ�ѡ����Ż���Ϣ
			Collection csiCampaignArray = event.getCsiCampaignArray();
	    	// ȡ���¿ͻ���Ϣ
	    	int  customerID = event.getCustomerID();
	    	CustomerHome customerHome =HomeLocater.getCustomerHome();
	    	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
	    	
	    	// ȡ���˻���Ϣ
	    	int accountID=event.getAccountID();
	    	// �ͻ���ַ��Ϣ
	    	AddressDTO custAddrDto =new AddressDTO();
	    	AddressHome addressHome =HomeLocater.getAddressHome();
	    	Address address=addressHome.findByPrimaryKey(new Integer(customer.getAddressID()));
	    	custAddrDto.setAddressID(address.getAddressID().intValue());
	    	custAddrDto.setDistrictID(address.getDistrictID());

	    	// ȡ��������Ϣ
	    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
	    	csiDTO.setCustomerID(customerID);
	    	csiDTO.setAccountID(accountID);
	    	
	    	// ����������Ϣ
			createCustServiceInteraction(csiDTO);
			// ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
			CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
			
			ArrayList buyProductList = event.getBuyProductList();
			batchCreateCsiCustProductInfo(buyProductList,csiDTO);
	    	// �Ƿ񴴽�����
			if(isCreateJobcard(csiDTO)){
				CustomerDTO custoemrDto=new CustomerDTO();
				custoemrDto.setCustomerID(customer.getCustomerID().intValue());
				custoemrDto.setTelephone(customer.getTelephone());
				custoemrDto.setTelephoneMobile(customer.getTelephoneMobile());
				custoemrDto.setAddressID(customer.getAddressID());
				custoemrDto.setName(customer.getName());
				// ��������,���ù���service����������Ϣ
				InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
			    int jobcardID=installationJobCardService.createJobCardForCustomerOperation(csiDTO,custoemrDto,custAddrDto,event.getNextOrgID());
				// �������������Ĺ���id
				csiEJB.setReferjobcardID(jobcardID);
			}
			
			
		}catch(HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ���ж�λ����");
		}catch(FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����ҵ���˻��ļ�����Ҳ��������Ϣ");
			
		}
	}
	private void batchCreateCsiCustProductInfo(ArrayList buyProductList,CustServiceInteractionDTO csiDTO) throws ServiceException{
		LogUtility.log(clazz, LogLevel.DEBUG, "����csiCustProductInfo",buyProductList);
		
		
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

			LogUtility.log(clazz, LogLevel.DEBUG, buyIndex+"�鵱ǰ��Ʒ��:",buyPackageList);
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
		LogUtility.log(clazz, LogLevel.DEBUG, "����csiCustProductInfo���.",allCsiCustProductList);
	}
	/**
	 * �ظ���������
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
		// ��Ų�Ʒ����Ӧ���Żݺ��ײ�
		Collection referCsiCampaignCol = new ArrayList();
		// ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
		Iterator currentPackageIterator = csiPackageArray.iterator();
		while (currentPackageIterator.hasNext()) {
			int packageid = ((Integer) currentPackageIterator.next())
					.intValue();
			allCsiCustProductDTOList.addAll(createCsiCustProductInfo(packageid,
					detailNo, null, csiCampaignArray, csiDTO,
					haveDeviceProductCol, businessParamDTO,
					referCsiCampaignCol, buyGroupNo, sheafNo));
		}

		// ���������ײ͵��Ż�Ҳ��¼��CsiProductInfo��
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
	    				//��2007-9-17����
	    				custProductDto.setGroupNo(buyGroupNo);
	    				custProductDto.setSheafNo(sheafNo);
						allCsiCustProductDTOList.add(custProductDto);
					}
				}
			}
		} catch (HomeFactoryException e) {
			LogUtility
					.log(clazz, LogLevel.ERROR, "createCsiCustProductInfo", e);
			throw new ServiceException("�¿��������Ʒʱ��λ����");
		} catch (CreateException e) {
			LogUtility
					.log(clazz, LogLevel.ERROR, "createCsiCustProductInfo", e);
			throw new ServiceException("�¿��������Ʒʱ��������");
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
            // �ж��Ź�ȯ���Ƿ�Ϊ��
            if(detailNo==null ||"".equals(detailNo)){
            	if(csiCampaignArray!=null && !csiCampaignArray.isEmpty()){
            		// ȡ�ò�Ʒ��������������Ż�
            		Collection campaignIDCol=BusinessUtility.getCampaignIDListByPackageID(packageId);
            		// ����ѡ����Ż��б��ҵ���Ӧ��Ʒ�����Ż�id
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
            	// ���Ź�ȯ��ʱ��ͨ���Ź�ȯȡ���Żݵ�id
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
				//��2007-9-17����
				custProductDto.setGroupNo(buyGroupNo);
				custProductDto.setSheafNo(sheafNo);
	            
	            // ֻ�����ŵ꿪����ԤԼ�����ǣ��ڼ���Ʒ���豸�Ĺ��������в����õ������Ϣ
	            if(haveDeviceProductCol!=null && !haveDeviceProductCol.isEmpty()){
		            // ȡ�ò�Ʒ���������ж��Ƿ���Ӳ����Ʒ
		            Product product=productHome.findByPrimaryKey(new Integer(productId));
		            if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
			            // ��ȡ�õ����豸��Ӳ����Ʒѭ���ҵ���Ӧ�Ĳ�Ʒ
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
					// ���嶯��
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_OPEN);
				// ������Ʒ
				}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType())||
						 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDTO.getType())){
					// ���嶯��
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT);
				}
				
				// �����Ź����Żݵ������Ϣ
				if(detailNo!=null && !"".equals(detailNo)){
	                custProductDto.setReferGroupBargainID(csiDTO.getGroupCampaignID());                
				}else{
					custProductDto.setReferGroupBargainID(0);   
				}	
				custProductDto.setReferCampaignID(currentCampaign);
				if(contractNO!=null && !"".equals(contractNO)){
					custProductDto.setReferContractNo(contractNO);
				}
				// ���������漰�Ŀͻ���Ʒ�����Ϣ
				CsiCustProductInfo custProduct = custHome.create(custProductDto);
				// ����id
				custProductDto.setId(custProduct.getId().intValue());

	            csiCustProductDTOList.add(custProductDto);
			}
            return csiCustProductDTOList;
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ��������");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ���Ҵ���");
    	}		
	}
    /**
	 * ԤԼ
	 * 
	 * @param event��ԤԼ�������������
	 */
    public void book(BookEJBEvent event) throws ServiceException {
    	// ȡ���¿ͻ���Ϣ
    	NewCustomerInfoDTO newCustInfo=event.getNewCustInfo();
    	// ȡ���¿ͻ����˻���Ϣ
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAcctInfo();
    	// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// ȡ�ÿͻ���ַ��Ϣ
    	AddressDTO custAddrDto =event.getCustAddressDTO();
		// ȡ���˻���ַ��Ϣ
    	AddressDTO acctAddrDto =event.getAcctAddressDTO();
    	// ȡ���Ƿ����
    	boolean isAgent=event.isAgent();
    	serviceContext.put(Service.IS_AGENT,new Boolean(isAgent));

		// ȡ�ÿͻ�ѡ����Ż���Ϣ
		Collection csiCampaignArray = event.getCsiCampaignArray();
    	
    	// ȡ���Ź�ȯ��
    	String detailNO=event.getDetailNo();
    	// ��Ʒ���б�
    	Collection	csiPackageArray =event.getCsiPackageArray();
    	
    	// ȡ���¿ͻ��г���Ϣ
    	Collection  newCustMarketInfoList=event.getNewCustMarketInfoList();
    	
    	if(detailNO!=null &&!"".equals(detailNO)){
    		// ȡ���Ź�����ϸ��Ϣ
    		GroupBargainDetailDTO detailDTO=BusinessUtility.getGroupBargainDetailByNO(detailNO);
    		// ע��������Ķ�����ϸ��ID�������ܵ�
    		csiDTO.setGroupCampaignID(detailDTO.getId());
			newCustInfo.setGroupBargainID(detailDTO.getId());	
    	}
    	// �����ͻ�/�ͻ��˻���ַ��Ϣ
    	createAddressInfo(custAddrDto,acctAddrDto);
    	
		newCustInfo.setFromAddressID(custAddrDto.getAddressID());
		newCustAcctInfo.setAddressID(acctAddrDto.getAddressID());
		// �����ʻ���ַ���Ա�־
		//newCustAcctInfo.setAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
		
		// ����������Ϣ
		createCustServiceInteraction(csiDTO);
		
		//����绰������Դ���� ��¼�绰����ʹ����־
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
	    //����绰������Դ���� end
		
		// �����¿ͻ��˻�/�ͻ�����������ID
		newCustAcctInfo.setCsiID(csiDTO.getId());
		newCustInfo.setCsiID(csiDTO.getId());
		// �����¿ͻ���Ϣ
		createNewCustomerInfo( newCustInfo);
		
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		// �����¿ͻ��г���Ϣ
		createNewCustomerMarketInfo(newCustMarketInfoList,newCustInfo.getId(),csiEJB.getId().intValue());
		// �����¿ͻ��˻���Ϣ
		createNewCustAccountInfo(newCustAcctInfo,true);
		
		// ���������漰�Ŀͻ���Ʒ�����Ϣ
		createCsiCustProductInfo( csiPackageArray, detailNO, csiCampaignArray, csiDTO);
		if(!isAgent){
	    	// �Ƿ񴴽�����
			if(isCreateJobcard(csiDTO)){
				// ��������,���ù���service����������Ϣ
				InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
			    int jobcardID=installationJobCardService.createJobCardForBooking(csiDTO,0,0,newCustInfo, custAddrDto,event.getNextOrgID());
			    
				// �������������Ĺ���id
				csiEJB.setReferjobcardID(jobcardID);
			}
		}
    }
    /**
	 * �ŵ꿪��
	 * 
	 * @param event���������������
	 */
    public void openAccount(BookEJBEvent event) throws ServiceException{
    	// ȡ���¿ͻ���Ϣ
    	NewCustomerInfoDTO newCustInfo=event.getNewCustInfo();
    	// ȡ���¿ͻ����˻���Ϣ
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAcctInfo();
    	// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// ȡ�ÿͻ���ַ��Ϣ
    	AddressDTO custAddrDto =event.getCustAddressDTO();
		// ȡ���˻���ַ��Ϣ
    	AddressDTO acctAddrDto =event.getAcctAddressDTO();
    	// ȡ���Ƿ����
    	boolean isAgent=event.isAgent();
    	serviceContext.put(Service.IS_AGENT,new Boolean(isAgent));

		// ȡ�ÿͻ�ѡ����Ż���Ϣ
		Collection csiCampaignArray = event.getCsiCampaignArray();
    	
    	// ȡ���Ź�ȯ��
    	String detailNO=event.getDetailNo();
    	// ��Ʒ���б�
    	Collection	csiPackageArray =event.getCsiPackageArray();
    	
    	// ȡ���¿ͻ��г���Ϣ
    	Collection  newCustMarketInfoList=event.getNewCustMarketInfoList();
    	
    	if(detailNO!=null &&!"".equals(detailNO)){
    		// ȡ���Ź�����ϸ��Ϣ,
    		GroupBargainDetailDTO detailDTO=BusinessUtility.getGroupBargainDetailByNO(detailNO);
    		// ע��������Ķ�����ϸ��ID�������ܵ�
    		csiDTO.setGroupCampaignID(detailDTO.getId());
			newCustInfo.setGroupBargainID(detailDTO.getId());	
    	}
    	// �����ͻ�/�ͻ��˻���ַ��Ϣ
    	createAddressInfo(custAddrDto,acctAddrDto);
    	// ���õ�ַid
		newCustInfo.setFromAddressID(custAddrDto.getAddressID());
		newCustAcctInfo.setAddressID(acctAddrDto.getAddressID());
		// ����������Ϣ
		createCustServiceInteraction(csiDTO);
		
		// ���ÿͻ�service�����Ŀͻ���Ϣ
		CustomerService customerService=new CustomerService(serviceContext);
		customerService.create(newCustInfo,custAddrDto,newCustMarketInfoList);
		Customer customer=(Customer)serviceContext.get(Service.CUSTOMER);
		
		// ���ÿͻ��˻�sevice�������˻���Ϣ
		AccountService accountService=new AccountService(serviceContext);
		accountService.create(newCustAcctInfo,acctAddrDto);
		Account account =(Account)serviceContext.get(Service.ACCOUNT);
		
		// �����¿ͻ���Ϣ�˻���Ϣ������id
		newCustAcctInfo.setCsiID(csiDTO.getId());
		// �����ʻ���ַ���Ա�־
		newCustAcctInfo.setAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
		newCustInfo.setCsiID(csiDTO.getId());
		// �����¿ͻ���Ϣ
		newCustInfo.setCustID(customer.getCustomerID().intValue());
		createNewCustomerInfo( newCustInfo);
		
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		// �����¿ͻ��г���Ϣ
		createNewCustomerMarketInfo(newCustMarketInfoList,newCustInfo.getId(),csiEJB.getId().intValue());
		// �����¿ͻ��˻���Ϣ
		newCustAcctInfo.setAccountID(account.getAccountID().intValue());
		createNewCustAccountInfo(newCustAcctInfo,true);
        // ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
		// ��������Ϣ�������˻�id
		csiEJB.setAccountID(account.getAccountID().intValue());
		csiDTO.setAccountID(account.getAccountID().intValue());
		// �ڿͻ��˻���Ϣ�����ÿͻ�id
		account.setCustomerID(customer.getCustomerID().intValue());
		// ��������Ϣ�����ÿͻ�id
		csiEJB.setCustomerID(customer.getCustomerID().intValue());
		csiDTO.setCustomerID(customer.getCustomerID().intValue());
		// �Ƿ񴴽�����
		if(isCreateJobcard(csiDTO) && CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiEJB.getType())){
			LogUtility.log(clazz,LogLevel.DEBUG,"����������Ҫ����������������");
			// ��������,���ù���service����������Ϣ
			InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
		    int jobcardID=installationJobCardService.createJobCardForBooking(csiDTO,customer.getCustomerID().intValue(),0,newCustInfo, custAddrDto,event.getNextOrgID());
			// �������������Ĺ���id
			csiEJB.setReferjobcardID(jobcardID);	
		}
		// �����ԤԼ�������Ļ��޸Ķ�Ӧ��ԤԼ����Ӧ������ReferSheetId�����ԤԼ����Ϣ
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiEJB.getType())){
			try{
			   CustServiceInteractionHome custServiceInteractionHome=HomeLocater.getCustServiceInteractionHome();
    		   CustServiceInteraction custServiceInteraction=custServiceInteractionHome.findByPrimaryKey(new Integer(csiEJB.getReferBookingSheetID()));
    	       // ԤԼ����ʱ�ҵ���Ӧ��ԤԼ��������ԤԼ����ԤԼ������֮��Ĺ���
    		   custServiceInteraction.setReferBookingSheetID(csiEJB.getId().intValue());
    		   // ����ԤԼ���Ŀͻ�ID
    		   custServiceInteraction.setCustomerID(customer.getCustomerID().intValue());
               // ���ö�Ӧ��ԤԼ�Ĺ�������
    		   csiDTO.setReferJobCardID(custServiceInteraction.getReferjobcardID());
    		   csiEJB.setReferjobcardID(custServiceInteraction.getReferjobcardID());
			}catch(HomeFactoryException e) {
			    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
			    throw new ServiceException("��λԤԼ������ʱ����");	            		
	    	}catch(FinderException e) {
			    LogUtility.log(clazz,LogLevel.ERROR,"updateNewCustomerInfo",e);
			    throw new ServiceException("�����Ų��Ҵ���");
			}
	    	LogUtility.log(clazz,LogLevel.DEBUG,"���������ҵ���ԤԼ����ԤԼ�������Ĺ�����������"+csiEJB.getReferjobcardID());
			// ��������,���ù���service����������Ϣ
			InstallationJobCardService installationJobCardService=new InstallationJobCardService(csiEJB.getReferjobcardID(),serviceContext);
			installationJobCardService.setsetReferSheetId(csiEJB.getId().intValue());
			installationJobCardService.setReferCustomerId(customer.getCustomerID().intValue());
		}
		
		//ģ����ӿ������漰���Ĺ���,add by jason 2007-7-27 
		if(isCreateJobcard(csiDTO) && CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiEJB.getType())){
			LogUtility.log(clazz,LogLevel.DEBUG,"��������ģ����ӿ�����Ҫ����������������");
			// ��������,���ù���service����������Ϣ
			InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
		    int jobcardID=installationJobCardService.createJobCardForCATVOpenAccount(csiDTO,customer.getCustomerID().intValue(),
		    		0,newCustInfo, custAddrDto,event.getNextOrgID(),event.getCatvID(),event.getAddPortNum());
			// �������������Ĺ���id
			csiEJB.setReferjobcardID(jobcardID);	
		}
		
		// ���������漰�Ŀͻ���Ʒ�����Ϣ
		LogUtility.log(clazz,LogLevel.DEBUG,"���������漰�Ŀͻ���Ʒ�����Ϣ");
		createCsiCustProductInfo( csiPackageArray, detailNO, csiCampaignArray, csiDTO);
		// �����Ź�ȯ�������Ϣ
		if(detailNO!=null &&!"".equals(detailNO)){
        	PublicService.dealOpenAccountRealationGroupBargain(csiDTO,true,CommonConstDefinition.GROUPBARGAINDETAILSTATUS_LOCK);
		}
    }
    /**
	 * ������ԤԼ���£�ԤԼ��������
	 * 
	 * @param csiid��ԤԼ����������ID
	 */
    public void updateBookObject(BookEJBEvent event) throws ServiceException {
    	// ȡ���¿ͻ���Ϣ
    	NewCustomerInfoDTO newCustInfo=event.getNewCustInfo();
    	// ȡ���¿ͻ����˻���Ϣ
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAcctInfo();
    	// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// ȡ�ÿͻ���ַ��Ϣ
    	AddressDTO custAddrDto =event.getCustAddressDTO();
		// ȡ���˻���ַ��Ϣ
    	AddressDTO acctAddrDto =event.getAcctAddressDTO();
    	// ȡ���Ƿ����
    	boolean isAgent=event.isAgent();
    	serviceContext.put(Service.IS_AGENT,new Boolean(isAgent));
		// ȡ�ÿͻ�ѡ����Ż���Ϣ
		Collection csiCampaignArray = event.getCsiCampaignArray();
    	// ȡ���Ź�ȯ��
    	String detailNO=event.getDetailNo();
    	// ��Ʒ���б�
    	Collection	csiPackageArray =event.getCsiPackageArray();
    	// ȡ���¿ͻ��г���Ϣ
    	Collection  newCustMarketInfoList=event.getNewCustMarketInfoList();
    	if(detailNO!=null &&!"".equals(detailNO)){
    		// ȡ���Ź�����ϸ��Ϣ,�����Ϣ��PublicService.getPackageArrayByBargainDetailNoִ�������õ�
    		GroupBargainDetailDTO detailDTO=BusinessUtility.getGroupBargainDetailByNO(detailNO);
    		// ע��������Ķ�����ϸ��ID�������ܵ�
    		csiDTO.setGroupCampaignID(detailDTO.getId());
			newCustInfo.setGroupBargainID(detailDTO.getId());	
    	}
    	// �����¿ͻ��˻���Ϣ
    	updateNewCustAccountInfo(newCustAcctInfo,true);
    	// �����¿ͻ���Ϣ
    	updateNewCustomerInfo(newCustInfo);
    	// ���¿ͻ���ַ��Ϣ
    	custAddrDto.setAddressID(newCustInfo.getFromAddressID());
    	updateAddressInfo(custAddrDto);
    	// ���¿ͻ��˻���Ϣ
    	acctAddrDto.setAddressID(newCustAcctInfo.getAddressID());
    	updateAddressInfo(acctAddrDto);
    	// ����������Ϣ
    	updateCustServiceInteractionInfo( csiDTO, event.getActionType());
    	// �����¿ͻ��г���Ϣ
    	updateNewCustomerMarketInfo( newCustMarketInfoList,newCustInfo.getId(),csiDTO.getId());
    	// �����¿ͻ���Ʒ��Ϣ
    	delectOldCsiCustProductInfo(csiDTO);
    	// ���������漰�Ŀͻ���Ʒ�����Ϣ
		createCsiCustProductInfo( csiPackageArray, detailNO, csiCampaignArray, csiDTO);
		if(event.getActionType()==EJBEvent.CHECK_AGENT_BOOKING){
			if(isCreateJobcard(csiDTO)){
				// ��������,���ù���service����������Ϣ
				InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
			    int jobcardID=installationJobCardService.createJobCardForBooking(csiDTO,0,0, newCustInfo, custAddrDto,event.getNextOrgID());
			    // ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
				CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
				// �������������Ĺ���id
				csiEJB.setReferjobcardID(jobcardID);
			}
		}
    }
    /**
	 * ȷ��ԤԼ������Ŀǰ��Ҫ�Ǵ�����ԤԼ������Ҫȷ��
	 * 
	 * @param
	 */
    public void confirmBook(BookEJBEvent event) throws  ServiceException{
    	updateBookObject(event) ;
    }
    /**
	 * ȡ��ԤԼ��
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void cancelBookSheet(BookEJBEvent event) throws  ServiceException{
    	updateCustServiceInteractionInfo(event.getCsiDto(),event.getActionType());
    }
    /**
	 * ���������ط���Ϣ
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void callbackOpenAccount(CallBackInfoEJBEvent event) throws  ServiceException{ 
    	// �ط���Ϣ
    	Collection callBackInfoList=event.geCallBackInfoList();
    	// �������ݶ���
    	CustServiceInteractionDTO csiDto=event.getCsiDto();
    	if(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS.equals(csiDto) ||CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL.equals(csiDto)){
			throw new ServiceException("��״̬�µ��������������ط���Ϣ");
		}
		if(CommonConstDefinition.CCPCALLBACKFLAG_YES.equals(csiDto.getCallBackFlag())){
			throw new ServiceException("�Ѿ��طõ��������������ط���Ϣ");
		}
    	// ����������Ϣ
    	updateCustServiceInteractionInfo(csiDto,event.getActionType());
    	// �����ط���Ϣ
    	createCallbackInfo(callBackInfoList,csiDto);
    }
    
    /**
	 * �ͻ�Ǩ��
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void moveCustomer( CustomerEJBEvent event)throws  ServiceException{
    	// �ͻ���ַ
    	AddressDTO  custAddressDTO=event.getCustAddressDTO();
    	// �ͻ���Ϣ
    	CustomerDTO customerDTO =event.getCustomerDTO();
       	// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDto=event.getCsiDto();
    	// ����������Ϣ
    	createCustServiceInteraction(csiDto);
    	
    	// �����Ͽͻ��ı�����Ϣ
    	CustomerService customerService=new CustomerService(serviceContext);
    	customerService.createCustAndAcctBackupInfo(customerDTO);
    	try{
    		 AddressHome addressHome=HomeLocater.getAddressHome();
    		 custAddressDTO.setAddressID(customerDTO.getAddressID());
    		 Address addr =addressHome.findByPrimaryKey(new Integer(customerDTO.getAddressID()));
    		 if(addr.ejbUpdate(custAddressDTO)==-1){
				throw new ServiceException("���µ�ַ��Ϣʱ��������");
			 }
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"moveCustomer",e);
			throw new ServiceException("��λ��ַ��Ϣʱ����");	            		
	    }catch(FinderException e) {
			LogUtility.log(clazz,LogLevel.ERROR,"moveCustomer",e);
			throw new ServiceException("��ַ��Ϣ���Ҵ���");
		}
        // ���¿ͻ���Ϣ
    	customerService.moveCustomer(customerDTO,custAddressDTO);
    }
    
    /**
	 * �ͻ�ԭ�ع�������ع���
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void transferCustomer( CustomerEJBEvent event)throws  ServiceException{
    	// �ͻ���ַ
    	AddressDTO  custAddressDTO=event.getCustAddressDTO();	
    	// �ͻ��г���Ϣ
    	Collection custMarketInfoList=event.getCustMarketInfoList();
    	// �ͻ���Ϣ
    	CustomerDTO customerDTO =event.getCustomerDTO();
    	// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDto=event.getCsiDto();
    	// ����������Ϣ
    	createCustServiceInteraction(csiDto);
    	// ���¿ͻ���Ϣ
    	CustomerService customerService=new CustomerService(serviceContext);
    	// �����Ͽͻ��ı�����Ϣ
    	customerService.createCustAndAcctBackupInfo(customerDTO);
    	if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_TD.equals(csiDto.getType())){
    		customerService.transferCustomer(event.getActionType(),customerDTO,custMarketInfoList,custAddressDTO);		
    	}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_TS.equals(csiDto.getType())){
    		customerService.transferCustomer(event.getActionType(),customerDTO,custMarketInfoList,null);	
    	}
    }
    
    /**
	 * ���ҵ���˻��Ĳ�������ͣ/�ָ�/����/�û��رգ�
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void serviceAccountOperation( ServiceAccountEJBEvent event)throws  ServiceException{
    	// ҵ���˻�id
    	int serviceAccountID=event.getServiceAccountID();
    	// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();

    	Collection psidList=null;
    	
    	// ����������Ϣ
    	// setContactPersonInfo(csiDTO,customerDTO.getName(),customerDTO.getTelephone(),customerDTO.getTelephoneMobile());
    	createCustServiceInteraction(csiDTO);
    	// ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		// ����
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UT.equals(csiEJB.getType())){
	    	// ȡ��ҵ���˻��������е������Ʒ
	    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
	    // �û��ر�
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UC.equals(csiEJB.getType())){
	    	// ȡ��ҵ���˻��������е������Ʒ
	    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    	// �û�Ԥ�˻�
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_SP.equals(csiEJB.getType())){
	    	// ȡ��ҵ���˻��������е������Ʒ
	    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
	    // �û�ʵ�˻�
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_SP.equals(csiEJB.getType())){
	    	// ȡ��ҵ���˻��������е������Ʒ
	    	psidList=BusinessUtility.getAllProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
	    	// ��ͣ
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UP.equals(csiEJB.getType())){
	    	// ȡ��ҵ���˻��������е������Ʒ
	    	psidList=BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    	// �ָ�
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UR.equals(csiEJB.getType())){
	  		createCsiCustProductInfo(csiEJB,event.getColPsid());
	  		return;
	  	// add by chaoqiu 2007-08-02 begin
	  	//ģ�����ҵ�� ��ͣ 
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAS.equals(csiEJB.getType())){
	    	// ȡ��ҵ���˻��������е������Ʒ
	    	psidList=BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL);
    	// ģ�����ҵ�� ����
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAR.equals(csiEJB.getType())){
			psidList=BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,null);
	  	// add by chaoqiu 2007-08-02 end	
		}
		// �û�����/�ر�/��ͣ/  ģ�����ҵ�� ��ͣ /ģ�����ҵ�� ����
		createCsiCustProductInfo(csiEJB,psidList);
    	
    }
    public void serviceAccountOperationForTransfer(CustServiceInteractionDTO csiDTO ,int serviceAccountID)throws  ServiceException{
    	if(csiDTO==null||serviceAccountID==0){
    		throw new ServiceException("Ŀ��ͻ���������ʧ��,��Ϣ��ȫ");
    	}
    	// ����������Ϣ
    	createCustServiceInteraction(csiDTO);
    	// �������������Ĳ�Ʒ��Ϣ.
    	createCSIProduct(csiDTO.getId(),serviceAccountID);

    }
    /**
	 * ��Բ�Ʒ�Ĳ�������ͣ/�ָ�/ȡ��/��Ʒ����/��Ʒ����/�����豸��
	 * 
	 * @param event
	 * @throws ServiceException
	 */
    public void customerProductOperation(CustomerProductEJBEvent event) throws  ServiceException{
    	// �ͻ���Ʒid�б�
    	Collection  psidList=event.getColPsid();
    	// ��/������Ŀ���Ʒ
		int productID=event.getProductID();// =event.get
		// ��/������Ŀ���Ʒ����Ӧ���豸
		int deviceID=event.getDeviceID();
		// ����
		// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// ����������Ϣ
    	/*2007/3/1 � add  ��������֧��״̬  start*/
    	csiDTO.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_WAITFORPAY);
    	/*2007/3/1 � add  ��������֧��״̬  end*/
    	createCustServiceInteraction(csiDTO);
    	// ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		// ��Ʒ����/��Ʒ����/�����豸
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PU.equals(csiEJB.getType())||
			CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PD.equals(csiEJB.getType())||
			CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DS.equals(csiEJB.getType())){
			CsiCustProductInfoDTO custProductDto=new CsiCustProductInfoDTO();
			custProductDto.setDestProductID(productID);
			custProductDto.setReferDestDeviceID(deviceID);
			createCsiCustProductInfo(csiEJB,(Integer)psidList.iterator().next(),custProductDto);
		// ��ͣ/�ָ�/Ǩ��/ȡ���ͻ���Ʒ
		}else{
			createCsiCustProductInfo(csiEJB,psidList);
		}
    }
    
    /**
     * �������������ĸ���״̬,���������������������,���Բ���Ҫ����������־��¼
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
     * �������������ĸ���״̬,���������������������,���Բ���Ҫ����������־��¼
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
		    throw new ServiceException("������������״̬ʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("������������״̬ʱ���Ҵ���");
		}
    }
    
    /**
	 * Ǩ�ƣ�����ʱ�����Ͽͻ�������Ϣ
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
    	// ȡ���Ͽͻ������Ϣ
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
    		// ���õ�ַ��Ϣ
        	newCustomerInfoDTO.setFromAddressID(address.getAddressID().intValue());
    	}else{
    		newCustomerInfoDTO.setToAddressID(customerDTO.getAddressID());
    		newCustomerInfoDTO.setFromAddressID(customerDTO.getAddressID());
    	}
    	// �����Ͽͻ��ı�����Ϣ
    	createNewCustomerInfo(newCustomerInfoDTO);
    	// ȡ�ÿͻ����г���Ϣ
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
    	// �����Ͽͻ��ı����г���Ϣ
    	createNewCustomerMarketInfo(currentMarketDtoList,newCustomerInfoDTO.getId());
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�����ͻ�������Ϣ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�����ͻ�������Ϣʱ���Ҵ���");
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�����ͻ�������Ϣ��������");
    	}
    }
    */
    /**
	 * ����ʱ�����Ͽͻ��˻�������Ϣ
	 * 
	 * @param csiDTO
	 * @param oldCustomerID
	 * @param newAddressID
	 * @throws ServiceException
	 */
    public void  createAccountBackupInfo(int customerID,int csiID,boolean updateAddress)throws  ServiceException{
    	try{  	
        	// �����Ͽͻ����˻�������Ϣ
        	AccountHome accountHome=HomeLocater.getAccountHome();
        	// ȡ���Ͽͻ��������˻���Ϣ
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
		    throw new ServiceException("�����ͻ��˻�������Ϣ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�����ͻ�������Ϣʱ���Ҵ���");
    	}
    }
    /**
	 * ����ʱ�����Ͽͻ��˻�������Ϣ
	 * 
	 * @param csiDTO
	 * @param oldCustomerID
	 * @param newAddressID
	 * @throws ServiceException
	 */
    private void  createAccountBackupInfo(Account oldAccount,int csiID,boolean updateAddress)throws  ServiceException{
    	try{  	
        	// �����Ͽͻ����˻�������Ϣ
        	NewCustAccountInfoDTO newCustAccountInfoDTO=new NewCustAccountInfoDTO();
    		// ȡ���˻���ַ��Ϣ
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
			// �����Ͽͻ��ⱸ����Ϣ
			createNewCustAccountInfo(newCustAccountInfoDTO,false);
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�����ͻ��˻�������Ϣ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�����ͻ��˻�������Ϣʱ��������");
    	}
    }
    /**
	 * ��������ʱ�Ļط���Ϣ
	 * 
	 * @param callBackInfoList
	 * @param csiDto
	 * @throws ServiceException
	 */
    private void  createCallbackInfo(Collection callBackInfoList,CustServiceInteractionDTO csiDto) throws  ServiceException{
    	try{
    		CallBackInfoHome callBackInfoHome=HomeLocater.getCallBackInfoHome();
    		// ɾ���ϵĿ����ط���Ϣ
    		int csiId=csiDto.getId();
    		Collection haveExitCallBackInfo=callBackInfoHome.findByReferTypeAndReferSourceId(CommonConstDefinition.CALLBACKINFOTYPE_OPEN,csiId);
    		if(haveExitCallBackInfo!=null && !haveExitCallBackInfo.isEmpty()){
    			Iterator haveExitIterator=haveExitCallBackInfo.iterator();
    			while(haveExitIterator.hasNext()){
    				CallBackInfo callBackInfo=(CallBackInfo)haveExitIterator.next();
    				callBackInfo.remove();
    			}
    		}
    		// �����µĿ����ط���Ϣ
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
		    throw new ServiceException("���������ط���Ϣ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("���������ط���Ϣʱ��������");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("���������ط���Ϣʱ���Ҵ���");
    	}catch(RemoveException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("ɾ���ɵĿ����ط���Ϣʱ����");
		}
    }
    
    /**
	 * ���ݵ�ַIDȡ����ϸ��ַ��Ϣ
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
		    throw new ServiceException("ȡ�õ�ַ��Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("ȡ�õ�ַ��Ϣʱ���Ҵ���");
    	}
    }
    /**
	 * ���ݿͻ���idȡ�ÿͻ�������
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
		    throw new ServiceException("ȡ�õÿͻ���Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("ȡ�õÿͻ���Ϣʱ���Ҵ���");
    	}
    }
    
    /**
	 * ����������Ϣ�Ͳ������͸���������Ϣ������������־
	 * 
	 * @param csiDTO
	 *            ����dto(�ڶ�����һ��ֻ��Ҫ��������id,�������������������Ϣ����ӹ���������ȡʱҲ����)
	 * @param actionType
	 *            ͳһ�����ejbevent��
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
	    		// ��װԤԼ
	    		case EJBEvent.CONTACT_USER_FOR_INSTALLATION:
	    			// ״̬���ڴ���
	    			custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_PROCESS);
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_INSTALL;
	    			actionDesc = "ԤԼ�ɹ�";
	    			break;
    			// ������װ
    			case EJBEvent.CLOSE_INSTALLATION_INFO:
    				// �����ɹ�
	    			custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL);    			
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_FAIL;
    				actionDesc = "����ʧ��";
	    			if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(custServiceInteraction.getType())){
		    			CustServiceInteraction bookCsi =custServiceInteractionHome.findByPrimaryKey(new Integer(custServiceInteraction.getReferBookingSheetID()));
		    			bookCsi.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL);
                        // ����ԤԼ����������־
		        		operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
		    		    recordCsiProcessLog(bookCsi.getId().intValue(), action, operatorID.intValue(), actionDesc,csiDTO.getStatusReason());
	    			}
    				break;
    			case EJBEvent.CATV_REGISTER_JOBCARD:
    				//¼��ģ�����ʩ����
				custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
				actionDesc = "����ɹ�";				
				break;
    			// ¼�밲װ��Ϣ
    			case EJBEvent.REGISTER_INSTALLATION_INFO:
	    			custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
    				actionDesc = "����ɹ�";
    				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(custServiceInteraction.getType())){
    				   CustServiceInteraction bookCsi =custServiceInteractionHome.findByPrimaryKey(new Integer(custServiceInteraction.getReferBookingSheetID()));
 	    			   bookCsi.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
	    			   // ����ԤԼ����������־
		        	   operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
		    		   recordCsiProcessLog(bookCsi.getId().intValue(), action, operatorID.intValue(), actionDesc,csiDTO.getStatusReason());
    				}
    				break;
                // �޸�ԤԼ����Ϣ
    			case EJBEvent.ALTERBOOKING:
    				
    				//����ҵ�� load�ϵ绰����:
    				serviceCodeList = custServiceInteraction.getServiceCodeList();
    				if(serviceCodeList == null)serviceCodeList = "";
    				//�µ绰����:
    				phoneNo = csiDTO.getServiceCodeList();
    				if(phoneNo == null) phoneNo="";
    				
    				//��������
    				updateResult = custServiceInteraction.ejbUpdate(csiDTO);
    				if(updateResult==-1){
    					throw new ServiceException("����ԤԼ����ʱ��������");
    				}
    				//����ҵ�� ����绰������Դ begin
    				//int itemID = BusinessUtility.getDTOByPhoneNo(phoneNo).getItemID();
    				if(!serviceCodeList.equals(phoneNo))
    				{
	    				//�ͷ�ԭ�绰������Դ:
    					operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    				if(!"".equals(serviceCodeList))
	    					releasePhoneNo(serviceCodeList,custServiceInteraction.getCustomerID(),operatorID,0);
	    				
	    				//�����µ绰������Դ:
	    				if(!"".equals(phoneNo))
	    					ServiceAccountService.lockPhoneNo(phoneNo,custServiceInteraction.getCustomerID(),
	    							operatorID,0);
    				}
    				//����ҵ�� ����绰������Դ end
    			    action=CommonConstDefinition.CSIPROCESSLOG_ACTION_UPDATE;
				    actionDesc = "�޸�ԤԼ��";
    				break;
                // ȷ�ϴ�����ԤԼ
    			case EJBEvent.CHECK_AGENT_BOOKING:
    				
    				//����ҵ�� load�ϵ绰����:
    				serviceCodeList = custServiceInteraction.getServiceCodeList();
    				if(serviceCodeList == null)serviceCodeList = "";
    				//�µ绰����:
    				phoneNo = csiDTO.getServiceCodeList();
    				if(phoneNo == null) phoneNo="";
    				
    				updateResult = custServiceInteraction.ejbUpdate(csiDTO);
	    			if(updateResult==-1){
	    				throw new ServiceException("���´�����ԤԼ����ʱ��������");	
	    				
	    			}
	    			
	    			//����ҵ�� ����绰������Դ begin
    				//int itemID = BusinessUtility.getDTOByPhoneNo(phoneNo).getItemID();
    				if(!serviceCodeList.equals(phoneNo))
    				{
	    				//�ͷ�ԭ�绰������Դ:
    					operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    				if(!"".equals(serviceCodeList))
	    					releasePhoneNo(serviceCodeList,custServiceInteraction.getCustomerID(),operatorID,0);
	    				
	    				//�����µ绰������Դ:
	    				if(!"".equals(phoneNo))
	    					ServiceAccountService.lockPhoneNo(phoneNo,custServiceInteraction.getCustomerID(),
	    							operatorID,0);
    				}
    				//����ҵ�� ����绰������Դ end
	    			
	    			custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT);
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_APPLY;
				    actionDesc = "ȷ�ϴ�����ԤԼ��";
    				break;    			
    			// ȡ��ԤԼ��
    			case EJBEvent.CANCELBOOKING:
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_BOOKINGCANCEL;
    				if(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT.equals(custServiceInteraction.getStatus())){
	    				if(custServiceInteraction.getReferjobcardID()!=0){
	    					// ȡ����ԤԼ�������Ĺ���
		    				InstallationJobCardService jobCardService=new InstallationJobCardService(custServiceInteraction.getReferjobcardID(),serviceContext);
		    				JobCardProcessDTO jobCardProcessDto =new JobCardProcessDTO();
		    				jobCardProcessDto.setWorkResult(CommonConstDefinition.JOBCARD_STATUS_SUCCESS);
		    				//jobCardProcessDto.setOutOfDateReason();
		    				jobCardService.cancel(jobCardProcessDto);
	    				}
    				}
    				custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL);
    				custServiceInteraction.setStatusReason(csiDTO.getStatusReason());
    				//����ҵ�� �ͷŵ绰������Դ:
    				operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    			if(custServiceInteraction.getServiceCodeList()!=null && !"".equals(custServiceInteraction.getServiceCodeList()))
	    				releasePhoneNo(custServiceInteraction.getServiceCodeList(),custServiceInteraction.getCustomerID(),operatorID,0);
    				
    				actionDesc = "ȡ��ԤԼ��";	
    				break;
        		// ��װԤԼȡ�� ȡ����װ���� ͬʱȡ��ԤԼ��
    			case EJBEvent.CANCEL_INSTALLATION_JOB_CARD:
    			case EJBEvent.CATV_CANCEL_JOBCARD:
    				custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL);    				
    					
    				custServiceInteraction.setStatusReason(csiDTO.getStatusReason());
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_INSTALLCANCEL;
    				actionDesc = "��װԤԼȡ��";
    				if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(custServiceInteraction.getType())
    						||CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(custServiceInteraction.getType())){
						custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_INSTALLCANCEL);
						
					}
    				//����ҵ�� �ͷŵ绰������Դ:
    				operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    			if(custServiceInteraction.getServiceCodeList()!=null && !"".equals(custServiceInteraction.getServiceCodeList()))
	    				releasePhoneNo(custServiceInteraction.getServiceCodeList(),custServiceInteraction.getCustomerID(),operatorID,0);
    				
    					
    				break;
    			// �����ط�
    			case EJBEvent.CALLFOROPENACCOUNT:
	    			custServiceInteraction.setCallbackFlag(CommonConstDefinition.CCPCALLBACKFLAG_YES);
    				custServiceInteraction.setCallBackDate(TimestampUtility.getCurrentDate());
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_CALLBACK;
				    actionDesc = "�����ط�";
    				break; 
    			// �����ط��ݴ�
    			case EJBEvent.SETCALLFLAG4OPENACCOUNT:
	    			custServiceInteraction.setCallbackFlag(CommonConstDefinition.CPCALLBACKFLAG_T);
    			    custServiceInteraction.setCallBackDate(TimestampUtility.getCurrentDate());
	    			action=CommonConstDefinition.CSIPROCESSLOG_ACTION_SETCALLBACKFLAG;
				    actionDesc = "�����ط��ݴ�";
    				break;
    			// ��װ���ɹ��˷�
    			case EJBEvent.RETURNFEE4FAILINSTALLATION:
    				if(!CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL.equals(custServiceInteraction.getStatus())){
    					throw new ServiceException("ֻ�д����ɹ������������˷�");	
    				}
    				/*�������ڵ�֧���ǿ����,���ŵ��԰�װʧ�ܵ�ʱ�������δ���ѵ�
	    			if(!CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED.equals(custServiceInteraction.getPaymentStatus())){
						throw new ServiceException("ֻ���Ѹ��ѵ����������˷�");	
					}
					*/
				    // ����й����Ĺ���,�����Ӧ������״̬
				    if(custServiceInteraction.getReferjobcardID()!=0){
				    	// ֻ���޷���������Ĺ��������˷�
				    	JobCardService jobCardService=new InstallationJobCardService(custServiceInteraction.getReferjobcardID(),serviceContext);
				    	jobCardService.canReturnFeeForInstallFail();
				    }
				    custServiceInteraction.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_RETURNMONEY);
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_RETURNMONEY;
				    actionDesc = "��װ���ɹ��˷�";
    				break;
			    // ԤԼ����ȷ��
    			case EJBEvent.CUSTOMER_BOOKINGUSER_ADD_SUBSCRIBER:
    				action=CommonConstDefinition.CSIPRPCESSLOG_ACTION_PROCESS;
    				actionDesc ="ԤԼ����ȷ��";
    				csiDTO.setPaymentStatus(custServiceInteraction.getPaymentStatus());
    				custServiceInteraction.setBillCollectionMethod(csiDTO.getBillCollectionMethod());
    				String oldCreatReason = custServiceInteraction.getCreateReason()==null?"":custServiceInteraction.getCreateReason();
    				String nowCreatReason = csiDTO.getCreateReason()==null?"":csiDTO.getCreateReason();
    				if(!oldCreatReason.equals(nowCreatReason))
    				{
    					
    					custServiceInteraction.setComments("����ʱ����ԭ����ԤԼʱ��:"+
    							BusinessUtility.getCsiReasonDesByCon(custServiceInteraction.getType(), "N", oldCreatReason)+"��Ϊ:"+
    							BusinessUtility.getCsiReasonDesByCon(custServiceInteraction.getType(), "N", nowCreatReason));
    				}
    				//custServiceInteraction.setCreateReason(csiDTO.getCreateReason());
    				InstallationJobCardService jobCardService1=new InstallationJobCardService(custServiceInteraction.getReferjobcardID(),serviceContext);
    				jobCardService1.setReferCustomerId(custServiceInteraction.getCustomerID());
    				break;
				// ԤԼ������ԤԼȡ��
    			case EJBEvent.CUSTOMER_BOOKINGUSER_CANCEL_SUBSCRIBER:
    				action=CommonConstDefinition.CSIPROCESSLOG_ACTION_BOOKINGCANCEL;
    				if(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT.equals(custServiceInteraction.getStatus())){
	    				// ȡ����ԤԼ�������Ĺ���
	    				InstallationJobCardService jobCardService=new InstallationJobCardService(custServiceInteraction.getReferjobcardID(),serviceContext);
	    				jobCardService.cancel(null);
    				}
    				custServiceInteraction.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL);
    				
    				//����ҵ�� �ͷŵ绰������Դ:
    				operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    			if(custServiceInteraction.getServiceCodeList()!=null && !"".equals(custServiceInteraction.getServiceCodeList()))
	    				releasePhoneNo(custServiceInteraction.getServiceCodeList(),custServiceInteraction.getCustomerID(),operatorID,0);
	    			
    				actionDesc = "ԤԼ������ԤԼȡ��";	
    				break;
    			// ԤԼ������ԤԼ�޸�
    			case EJBEvent.CUSTOMER_BOOKINGUSER_UPDATE_SUBSCRIBER:
    				
    				//����ҵ�� load�ϵ绰����:
    				serviceCodeList = custServiceInteraction.getServiceCodeList();
    				if(serviceCodeList == null)serviceCodeList = "";
    				//�µ绰����:
    				phoneNo = csiDTO.getServiceCodeList();
    				if(phoneNo == null) phoneNo="";
    
    				action =CommonConstDefinition.CSIPROCESSLOG_ACTION_UPDATE;
    				actionDesc ="ԤԼ�����޸�";
    			
    				if (custServiceInteraction.ejbUpdate(csiDTO) ==-1)
    					   throw new ServiceException("�޸���������");
    				csiDTO.setReferJobCardID(custServiceInteraction.getReferjobcardID());
    				
    				//����ҵ�� ����绰������Դ begin
    				//int itemID = BusinessUtility.getDTOByPhoneNo(phoneNo).getItemID();
    				if(!serviceCodeList.equals(phoneNo))
    				{
	    				//�ͷ�ԭ�绰������Դ:
    					operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
	    				if(!"".equals(serviceCodeList))
	    					releasePhoneNo(serviceCodeList,custServiceInteraction.getCustomerID(),operatorID,0);
	    				
	    				//�����µ绰������Դ:
	    				if(!"".equals(phoneNo))
	    					ServiceAccountService.lockPhoneNo(phoneNo,custServiceInteraction.getCustomerID(),
	    							operatorID,0);
    				}
    				//����ҵ�� ����绰������Դ end
    				break;
    				
    			default:
    				break;
    		}
			custServiceInteraction.setDtLastmod(TimestampUtility.getCurrentDate());
    		serviceContext.put(Service.CSI, custServiceInteraction);
    		//��1-08����
		    serviceContext.put(Service.CSI_ID, custServiceInteraction.getId());

    		// ��������������־
    		operatorID=(Integer)serviceContext.get(Service.OPERATIOR_ID);
		    recordCsiProcessLog(csiDTO.getId(), action, operatorID.intValue(), actionDesc,csiDTO.getStatusReason());
    	
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("����������Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("����������Ϣʱ���Ҵ���");
    	}
    }
	/**
	 * �÷�����Ҫ������ͨ�ͻ�������������Ʒ֮���ҵ�� ���������漰�Ŀͻ���Ʒ�����Ϣ
	 * @param csiPackageArray
	 * @param detailNo
	 * @param csiCampaignArray
	 * @param csiDTO
	 * @return
	 * @throws ServiceException
	 */
	
	/**
	 * �÷�����Ҫ���ڼ��ſͻ��ӿͻ��������ӿͻ��¿��û���������Ʒҵ�� ���������漰�Ŀͻ���Ʒ�����Ϣ
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
		// ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
		Iterator currentPackageIterator=csiPackageArray.iterator();
		while(currentPackageIterator.hasNext()){
			int packageid = ((Integer) currentPackageIterator.next()).intValue();	
			allCsiCustProductDTOList.addAll(createCsiCustProductInfo(packageid,detailNo,contractNo,csiCampaignArray,csiDTO,haveDeviceProductCol));	
		}
		return allCsiCustProductDTOList;
	}
	/**
	 * �÷�����Ҫ������Բ�Ʒ���е�ĳ�ֲ��� ��Կͻ���Ʒid���б��������漰�Ŀͻ���Ʒ�����Ϣ
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
	 * �÷�����Ҫ������Բ�Ʒ���е�ĳ�ֲ��� ��Ե����ͻ���Ʒid���������漰�Ŀͻ���Ʒ�����Ϣ
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
			
			// ����������Ʒ�����Ϣ
			// ȡ���ͻ���Ʒ
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CP.equals(csi.getType())){
				custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_CANCEL);
			// ��Ʒ����
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PU.equals(csi.getType())){
				custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_ASCEND);
			// ��Ʒ����
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PD.equals(csi.getType())){
				custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_DESCEND);
			// �豸����
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_DS.equals(csi.getType())){
				custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_S);
			}
			custProductDto.setReferAccountID(cp.getAccountID());
			custProductDto.setReferServiceAccountID(cp.getServiceAccountID());
			custProductDto.setCsiID(csi.getId().intValue());
			custProductDto.setCustProductID(psID.intValue());
			custProductDto.setProductID(cp.getProductID());
			custProductDto.setReferDeviceID(cp.getDeviceID());
			// �������ݴ���
			// custProductDto.setReferContractNo();
			// custProductDto.setReferCampaignID();
//			custProductDto.setDestProductID(cp.getProductID());
			custProductDto.setReferGroupBargainID(customer.getGroupBargainID());
			custProductDto.setReferPackageID(cp.getReferPackageID());
			custProductDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
			csiCustProductInfoHome.create(custProductDto);
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�Բ�Ʒ����ʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�Բ�Ʒ����ʱ��������");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�Բ�Ʒ����ʱ���Ҵ���");
    	}
	}
	/**
	 * ����������Ϣ�����ͻ�����ͻ���Ʒ�ı�����Ϣ
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
		    throw new ServiceException("�Բ�Ʒ����ʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�Բ�Ʒ����ʱ��������");
		}
	}
	/**
	 * �÷�����Ҫ���ڿ�����������Ʒ֮���ҵ�� ��Ե�����Ʒ��id���������漰�Ŀͻ���Ʒ�����Ϣ
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
            // �ж��Ź�ȯ���Ƿ�Ϊ��
            if(detailNo==null ||"".equals(detailNo)){
            	if(csiCampaignArray!=null && !csiCampaignArray.isEmpty()){
            		// ȡ�ò�Ʒ��������������Ż�
            		Collection campaignIDCol=BusinessUtility.getCampaignIDListByPackageID(packageId);
            		// ����ѡ����Ż��б��ҵ���Ӧ��Ʒ�����Ż�id
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
            	// ���Ź�ȯ��ʱ��ͨ���Ź�ȯȡ���Żݵ�id
            	GroupBargainDetailHome groupBargainDetailHome=HomeLocater.getGroupBargainDetailHome();
            	GroupBargainDetail groupBargainDetail=groupBargainDetailHome.findObjectByDetailNo(detailNo);
            	GroupBargainHome groupBargainHome=HomeLocater.getGroupBargainHome();
            	GroupBargain groupBargain=groupBargainHome.findByPrimaryKey(new Integer(groupBargainDetail.getGroupBargainID()));
            	currentCampaign=groupBargain.getCampaignId();
            }
            //----------2006/11/23-------yangchen---------start------
            //���µĴ�������Ƴ���ͨ���������ô��룬
            // ȡ���ڼ���豸�Ͳ�Ʒ���������õĲ�Ʒ���豸ӳ��ɹ�CsiCustProductDTO
            // ����BusinessRuleService.checkSelectTerminalDevice�����õ�
            //Collection haveDeviceProductCol=(Collection)serviceContext.get(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST);
            //----------2006/11/23-------yangchen---------end------
            while(iteratorProductID.hasNext()){
				int productId=((Integer) iteratorProductID.next()).intValue();
				CsiCustProductInfoDTO custProductDto = new CsiCustProductInfoDTO();
	            custProductDto.setCsiID(csiDTO.getId());
	            custProductDto.setProductID(productId);
	            custProductDto.setReferPackageID(packageId);
	            custProductDto.setStatus(CommonConstDefinition.GENERALSTATUS_VALIDATE);
	            // ֻ�����ŵ꿪����ԤԼ�����ǣ��ڼ���Ʒ���豸�Ĺ��������в����õ������Ϣ
	            if(haveDeviceProductCol!=null && !haveDeviceProductCol.isEmpty()){
		            // ȡ�ò�Ʒ���������ж��Ƿ���Ӳ����Ʒ
		            Product product=productHome.findByPrimaryKey(new Integer(productId));
		            if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
			            // ��ȡ�õ����豸��Ӳ����Ʒѭ���ҵ���Ӧ�Ĳ�Ʒ
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
					// ���嶯��
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_OPEN);
				// ������Ʒ
				}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType())){
					// ���嶯��
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT);
				}
				
				
				// �����Ź����Żݵ������Ϣ
				if(detailNo!=null && !"".equals(detailNo)){
	                custProductDto.setReferGroupBargainID(csiDTO.getGroupCampaignID());                
	                custProductDto.setReferCampaignID(currentCampaign);
				}else{
					custProductDto.setReferGroupBargainID(0);   
					custProductDto.setReferCampaignID(currentCampaign);
				}
/*
				// ȡ���¿ͻ���Ϣ,�����Ϣ������ǰ����ø÷�������ڷ�����������
				NewCustomerInfoDTO newCustInfo=(NewCustomerInfoDTO)serviceContext.get(com.dtv.oss.service.Service.NEW_CUSTOMER_INFO_DTO);
				// ������ҵ�û�����ʱʹ�õĺ�ͬ��
				if(newCustInfo!=null){
					custProductDto.setReferContractNo(newCustInfo.getContractNo());
				}
*/
				if(contractNO!=null && !"".equals(contractNO)){
					custProductDto.setReferContractNo(contractNO);
				}
				// ���������漰�Ŀͻ���Ʒ�����Ϣ
				CsiCustProductInfo custProduct = custHome.create(custProductDto);
				// ����id
				custProductDto.setId(custProduct.getId().intValue());
	            csiCustProductDTOList.add(custProductDto);
			}
            return csiCustProductDTOList;
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ��������");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ���Ҵ���");
    	}		
	}
	/**
	 * ���� CSI ��־��¼
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
		    throw new ServiceException("��¼������־��Ϣʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"recordCsiProcessLog",e);
		    throw new ServiceException("��¼������־��Ϣʱ����");
		}
	}
	private void recordCsiProcessLog(int csiid, String action, int operatorid, String description,String workResultReason) throws  ServiceException{
		recordCsiProcessLog(csiid,action,operatorid,0,description,workResultReason);
	}
	/**
	 * �����ͻ�/�ͻ��˻���ַ��Ϣ
	 * 
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public void createAddressInfo(AddressDTO custAddrDto,AddressDTO acctAddrDto)throws ServiceException {
		try{
			AddressHome addrHome = HomeLocater.getAddressHome();
		    if(acctAddrDto!=null){
		    	LogUtility.log(CSIService.class,LogLevel.DEBUG,"�����ͻ���ַ",custAddrDto);
		    	Address custAddr = addrHome.create(custAddrDto);
		    	custAddrDto.setAddressID(custAddr.getAddressID().intValue());
		    }
		    if(custAddrDto!=null){
		    	LogUtility.log(CSIService.class,LogLevel.DEBUG,"�����˻���ַ",acctAddrDto);
		    	Address acctAddr= addrHome.create(acctAddrDto);
		    	acctAddrDto.setAddressID(acctAddr.getAddressID().intValue());
		    }
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("������ַ��Ϣʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("������ַ��Ϣ����");
		}
	}
	/**
	 * �������е�������Ϣ
	 * 
	 * @param serviceContext
	 * @throws ServiceException
	 */
	public void createCustServiceInteraction(CustServiceInteractionDTO csiDTO,int invoiceNo)throws ServiceException {
		try{
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"��createCustServiceInteraction����������Ϣ��ʼ");
			// ����������Ϣ
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
			// ��������ʱԤԼ/������ԤԼ��
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK.equals(csiDTO.getType())){
				Boolean isAgent=(Boolean)serviceContext.get(Service.IS_AGENT);
			    if (isAgent.booleanValue()) // ����Ǵ����̣�ԤԼ��״̬Ϊ���½���
			    	csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_NEW);
			    else
			    	csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT);
			// ��������ԤԼ������
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(csiDTO.getType())){
				csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_PROCESS);
			// ���������ŵ꿪��/ԤԼ����/ԤԼ�����ͻ���Ʒ/�ŵ�����/ģ����ӿ���
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(csiDTO.getType())||
				//	CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDTO.getType())||  ע�͵� by david.Yang
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAS.equals(csiDTO.getType())||
					CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAR.equals(csiDTO.getType())
			){
				csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT);		
				//ģ�������������
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAA.equals(csiDTO.getType())){
				csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_PROCESS);
				//������ԭ�ع���/�û�����/���ſͻ�����/���ſͻ��ӿͻ������Լ�������������
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAM.equals(csiDTO.getType())
								||CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAC.equals(csiDTO.getType())
								||CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDTO.getType()) //��������add by david.Yang
			        ){
				csiDTO.setPaymentStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED);
				csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
			}else{
				 csiDTO.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
			}
			// �趨�طñ�־
			csiDTO.setCallBackFlag(CommonConstDefinition.YESNOFLAG_NO);
			CustServiceInteractionHome csiHome = HomeLocater.getCustServiceInteractionHome();
		    CustServiceInteraction csi = csiHome.create(csiDTO);
		    // ����������id
		    csiDTO.setId(csi.getId().intValue());
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createCustServiceInteraction",csiDTO);
		    		    
		    // ������ԤԼ���������������͵����������Ƕ�Ӧ�ļ�ֵ��һ����Ŀ����Ϊ��ԤԼ��������������
		    serviceContext.put(Service.CSI, csi);
		    serviceContext.put(Service.CSI_ID, csi.getId());
		    // ��������������־
		    String actionDesc = (String)serviceContext.get(Service.ACTION_DESCRTIPION);
		    String action =(String)serviceContext.get(Service.ACTION_DEFITION);
		    //�ڴ���������ʱ����־�е�״̬�仯ԭ���¼Ϊ�����Ĵ���ԭ��
		    recordCsiProcessLog(csi.getId().intValue(), action, operatorID.intValue(), invoiceNo,actionDesc,csiDTO.getCreateReason());
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("����������ʱ��λϢ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustServiceInteraction",e);
		    throw new ServiceException("����������Ϣ����");
		}
	}
	public void createCustServiceInteraction(CustServiceInteractionDTO csiDTO)throws ServiceException{
		createCustServiceInteraction(csiDTO,0);
	}
	
	/**
	 * �����¿ͻ���Ϣ
	 * 
	 * @param newCustInfoDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void createNewCustomerInfo(NewCustomerInfoDTO newCustInfoDto)throws ServiceException {
		try{
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"��createNewCustomerInfo�����¿ͻ���Ϣ��ʼ");
			NewCustomerInfoHome nciHome = HomeLocater.getNewCustomerInfoHome();
			LogUtility.log(CSIService.class,LogLevel.DEBUG,"createNewCustomerInfo",newCustInfoDto);
		    NewCustomerInfo nci = nciHome.create(newCustInfoDto);
		    serviceContext.put(Service.NEW_CUSTOMER_INFO_EJB,nci);
		    // ���ÿͻ�ID
		    newCustInfoDto.setId(nci.getId().intValue());
		    LogUtility.log(CSIService.class,LogLevel.DEBUG,"��createNewCustomerInfo�����¿ͻ���Ϣ����");
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustomerInfo",e);
		    throw new ServiceException("�������������ÿͻ���Ϣʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustomerInfo",e);
		    throw new ServiceException("�������������ÿͻ���Ϣ����");
		}
	}
	/**
	 * �����¿ͻ��г���Ϣ
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
		    throw new ServiceException("���������������г���Ϣʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustomerMarketInfo",e);
		    throw new ServiceException("���������������г���Ϣ����");
		}
	}
	
	/**
	 * �����¿ͻ��˻���Ϣ
	 * 
	 * @param newCustAcctInfoDto
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void createNewCustAccountInfo(NewCustAccountInfoDTO newCustAcctInfoDto ,boolean isPutIntoContext)throws ServiceException {
		try{
			NewCustAccountInfoHome ncaiHome = HomeLocater.getNewCustAccountInfoHome();
			NewCustAccountInfo newCustAccountInfo=ncaiHome.create(newCustAcctInfoDto);
			// ����id
			newCustAcctInfoDto.setId(newCustAccountInfo.getId().intValue());
			if(isPutIntoContext){
				serviceContext.put(Service.NEW_CUST_ACCOUNT_INFO_EJB,newCustAccountInfo);
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustAccountInfo",e);
		    throw new ServiceException("���������������˻���Ϣʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createNewCustAccountInfo",e);
		    throw new ServiceException("���������������˻���Ϣ����");
		}
	}
	
	/**
	 * �����¿ͻ���Ϣ
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
			// ����һ�µ�ַ��Ϣid,���ڵ�ַ��Ϣ����ʱʹ��
			newCustInfoDto.setFromAddressID(newCustomerInfo.getFromAddressID());
			int updateResult=newCustomerInfo.ejbUpdate(newCustInfoDto);
			if(updateResult==-1){
				throw new ServiceException("�����¿ͻ���Ϣʱ��������");	
				
			}
			serviceContext.put(Service.NEW_CUSTOMER_INFO_EJB,newCustomerInfo);
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"updateNewCustomerInfo",e);
		    throw new ServiceException("�����¿ͻ���Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"updateNewCustomerInfo",e);
		    throw new ServiceException("�����¿ͻ���Ϣ����");
		}
	}
	/**
	 * �����¿ͻ��г���Ϣ
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
		    throw new ServiceException("�����¿ͻ��г���Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("�����¿ͻ��г���Ϣ����");
		}catch(RemoveException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("ɾ���¿ͻ��г���Ϣ����");
		}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("�����¿ͻ��г���Ϣ����");
		}
	}
	
	/**
	 * �����¿ͻ��˻���Ϣ
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
			// ���õ�ַ��Ϣid�����ڵ�ַ����ʱʹ��
			newCustAcctInfoDto.setAddressID(newCustAccountInfo.getAddressID());
			int updateResult=newCustAccountInfo.ejbUpdate(newCustAcctInfoDto);
			if(updateResult==-1)
				throw new ServiceException("�����˻���Ϣʱ��������");
			if(isPutIntoContext){
				serviceContext.put(Service.NEW_CUST_ACCOUNT_INFO_EJB,newCustAccountInfo);
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("�����˻���Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("�����˻���Ϣ����");
		}
	}
	/**
	 * ���µ�ַ��Ϣ
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
				throw new ServiceException("���µ�ַ��Ϣʱ��������");	
				
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("���µ�ַ��Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("���µ�ַ��Ϣ���Ҵ���");
		}
	}
	
	/**
	 * ɾ��������Ӧ���ϲ�Ʒ��Ϣ
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
		    throw new ServiceException("���µ�ַ��Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("���µ�ַ��Ϣ����");
		}
    	catch(RemoveException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("���µ�ַ��Ϣ����");
		}
		
	}
	/**
	 * �жϸ������Ƿ���Ҫ��������
	 * 
	 * @param csiDTO
	 * @return
	 */
	private boolean isCreateJobcard(CustServiceInteractionDTO csiDTO){
		LogUtility.log(clazz,LogLevel.DEBUG,"���������� InstallationType��"+csiDTO.getInstallationType());
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
			throw new  ServiceException("�ù���û�ж�Ӧ������");
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
			throw new  ServiceException("�ù���û�ж�Ӧ������");
		}
		return ischange;
	}
	
	/**
	 * ����saID���������Ĳ�Ʒ
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
			throw new ServiceException("����������Ʒ��Ϣ����");
		}
	}
	/**
	 * �������ſͻ�����,
	 * 
	 * @param event
	 * @throws ServiceException
	 */
	public void createGroupCustServiceInteractionInfo(GroupCustomerEJBEvent event) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"���ſͻ�����");
    	// ȡ���¿ͻ���Ϣ
    	NewCustomerInfoDTO newCustInfo=event.getNewCustomerInfoDTO();
    	newCustInfo.setCustStyle(CommonConstDefinition.CUSTOMERSTYLE_GROUP);
    	// ȡ���¿ͻ����˻���Ϣ
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAccountInfoDTO();
    	
    	// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	
    	// ȡ�ÿͻ���ַ��Ϣ
    	AddressDTO custAddrDto =event.getAddressDTO();
    	
		// ȡ���˻���ַ��Ϣ
    	AddressDTO acctAddrDto =event.getAccountAddressDTO();
    	
    	// ��Ʒ���б�
// Collection csiPackageArray =event.getCsiPackageArray();
    	
    	// �����ͻ�/�ͻ��˻���ַ��Ϣ
    	createAddressInfo(custAddrDto,acctAddrDto);
    	// ���õ�ַid
		newCustInfo.setFromAddressID(custAddrDto.getAddressID());
		
		newCustAcctInfo.setAddressID(acctAddrDto.getAddressID());
		// ����������Ϣ
		createCustServiceInteraction(csiDTO);
		
		// ���ÿͻ�service�����Ŀͻ���Ϣ
		CustomerService customerService=new CustomerService(serviceContext);
		customerService.create(newCustInfo,custAddrDto,null);
		Customer customer=(Customer)serviceContext.get(Service.CUSTOMER);
		
		// ���ÿͻ��˻�sevice�������˻���Ϣ
		AccountService accountService=new AccountService(serviceContext);
		accountService.create(newCustAcctInfo,acctAddrDto);
		Account account =(Account)serviceContext.get(Service.ACCOUNT);
		
		// �����¿ͻ���Ϣ�˻���Ϣ������id
		newCustAcctInfo.setCsiID(csiDTO.getId());
		// �����ʻ���ַ���Ա�־
		newCustAcctInfo.setAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
		newCustInfo.setCsiID(csiDTO.getId());
		// �����¿ͻ���Ϣ
		newCustInfo.setCustID(customer.getCustomerID().intValue());
		createNewCustomerInfo( newCustInfo);
		
		// �����¿ͻ��˻���Ϣ
		newCustAcctInfo.setAccountID(account.getAccountID().intValue());
		createNewCustAccountInfo(newCustAcctInfo,true);

		// ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
 
		// ��������Ϣ�������˻�id
		csiEJB.setAccountID(account.getAccountID().intValue());
		csiDTO.setAccountID(account.getAccountID().intValue());
		
		// �ڿͻ��˻���Ϣ�����ÿͻ�id
		account.setCustomerID(customer.getCustomerID().intValue());
		// ��������Ϣ�����ÿͻ�id
		csiEJB.setCustomerID(customer.getCustomerID().intValue());
		csiDTO.setCustomerID(customer.getCustomerID().intValue());
		
    	// ȡ�ú�ͬ��Ϣ
    	ContractDTO contractDTO=event.getContractDTO();
    	ContractDTO dto=BusinessUtility.getContractDTOByContractNo(event.getContractDTO().getContractNo());
    	
    	//���º�ͬ����ʹ��������������ͬʹ����־
		ContractService contractService=new ContractService(serviceContext);
		String descpription="���ſͻ���ͬ�ţ�"+event.getContractDTO().getContractNo()+" ���ſͻ�ID��"+customer.getCustomerID()+" ��ͬ��;�����ſͻ�����";
		contractService.processContractWithGroupCustomerOpen(contractDTO.getContractNo(),customer.getCustomerID().intValue(),0,descpription);
		//������ͬ��Ϣ
		createGroupCustomerCampaign(dto,customer.getCustomerID().intValue(),account.getAccountID().intValue(),0);
		
		LogUtility.log(clazz,LogLevel.DEBUG,"���������漰�Ŀͻ���Ʒ�����Ϣ���");
		
	}
	
	public void createGroupSubCustomer(GroupCustomerEJBEvent event,ServiceContext serviceContext)
			throws ServiceException {
		//����豸��ϵ�Ƿ���ȷ
		ContractDTO contractDTO = BusinessUtility
		.getContractDTOByContractNo(event.getContractNO());
		BusinessRuleService businessRuleService = new BusinessRuleService(
				serviceContext);
		Collection initCsiproductCol = businessRuleService
				.checkOpenChildDevAndGetCsiProdList(event.getCsiDto(),contractDTO, event.getDeviceTable(),
						true);
		//��������
		createCustServiceInteraction(event.getCsiDto());
		//�����ͻ���Ʒ������Ϣ
		createCsiCustProductInfo(event.getContractToPackageIDCol(),null ,event.getContractNO(),null,event.getCsiDto(),initCsiproductCol);
		// ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
		CustServiceInteraction csiEJB = (CustServiceInteraction) serviceContext
				.get(Service.CSI);
		// ȡ���ӿͻ���ַ��Ϣ
		AddressDTO custAddrDto = event.getAddressDTO();
		// ȡ���ӿͻ���Ϣ
		NewCustomerInfoDTO subCustDTO = event.getNewCustomerInfoDTO();
		//�����ӿͻ��ͻ���Ϣ
		CustomerService customerService = new CustomerService(serviceContext);
		customerService.create(subCustDTO, custAddrDto, null);
		Customer customer = (Customer) serviceContext.get(Service.CUSTOMER);
		subCustDTO.setCustID(customer.getCustomerID().intValue());
		// ȡ�ü��ſͻ���Ϣ�����ſͻ�ID
		subCustDTO.setGroupCustID(event.getCustomerID());
		subCustDTO.setCsiID(csiEJB.getId().intValue());
		//�����ӿͻ�������Ϣ
		createNewCustomerInfo(subCustDTO);
		

		//����ֻ���ÿͻ�id���˻�id�ڴ�����ʱ����ҳ��ѡ�������
		csiEJB.setCustomerID(customer.getCustomerID().intValue());
		//����ҵ���ʻ�
		ServiceAccountService serviceAccountService = new ServiceAccountService(
				serviceContext);
		serviceAccountService.createForGroup(event.getContractToPackageIDCol(), null, event.getPhoneNo(),
				event.getItemID(), event.getProductPropertyMap());

		String serviceAccountID = (String)serviceContext.get(Service.SERVICE_ACCOUNT_ID);

		//���º�ͬ����ʹ��������������ͬʹ����־
		ContractService contractService=new ContractService(serviceContext);
		String descpription="���ſͻ���ͬ�ţ�"+contractDTO.getContractNo()+" ���ſͻ�ID��"+event.getCustomerID()+" ��ͬ��;���ӿͻ����� �ӿͻ�ID��"+customer.getCustomerID()+";ҵ���ʻ�ID"+serviceAccountID;
		contractService.processContractWithGroupCustomerOpen(event.getContractNO(),event.getCustomerID(),1,descpription);
	}
	/**
	 * �������ſͻ��Ĵ���������Ϣ,��Ҫ�Ǻ�ͬ,����һ��������¼,��¼��ͬ��,�ͻ���,�˲��������ڿͻ�����֮��.
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
					"\n�����ͻ���غ�ͬ��Ϣ��customerCampaign,\ncustomerID:"
							+ customerID + "\ncontractNo:"
							+ dto.getContractNo());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("�������ſͻ���ͬʧ��.");
		}		
	}

	/**
	 * �Լ��ſͻ��¿��û����д���
	 * fiona
	 * @param event
	 * @throws ServiceException
	 */
	public void openChildCustServiceAccount(GroupCustomerEJBEvent event,Collection initCsiproductCol) throws ServiceException{
		//update by chaoqiu 2007-04-29 begin
		//�豸�б��Ƿ��м�¼  
		//if(event.getDeviceTable()==null||event.getDeviceTable().isEmpty())
		//	throw new ServiceException("û���豸��¼��");
		//update by chaoqiu 2007-04-29 end
		
		//serviceContext.put(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST,initCsiproductCol.iterator().next());
		serviceContext.put(com.dtv.oss.service.Service.CUSTOMER_ID, new Integer(event.getCsiDto().getCustomerID()));
		serviceContext.put(com.dtv.oss.service.Service.ACCOUNT_ID, new Integer(event.getCsiDto().getAccountID()));
		//��������
		createCustServiceInteraction(event.getCsiDto());
		//�����ͻ���Ʒ������Ϣ
		Collection packagecol=BusinessUtility.getPackageIDByContractID(event.getContractNO());
		createCsiCustProductInfo(packagecol,null ,event.getContractNO(),null,event.getCsiDto(),initCsiproductCol);
		//����ҵ���ʻ�/�ͻ���Ʒ
		ServiceAccountService serviceAccountService=new ServiceAccountService(serviceContext);
		serviceAccountService.createForGroup (packagecol,null,event.getPhoneNo(),event.getItemID(),event.getProductPropertyMap());
		//���º�ͬ����ʹ��������������ͬʹ����־
		ContractService contractService=new ContractService(serviceContext);
		String serviceAccountID = (String)serviceContext.get(Service.SERVICE_ACCOUNT_ID);
		String descpription="���ſͻ�ID��"+event.getCustomerID()+" �ӿͻ�ID��"+event.getCsiDto().getCustomerID()+" ��ͬ��;���ӿͻ��¿��û�  �ӿͻ�ҵ���ʻ�ID��"+serviceAccountID;
		contractService.processContractWithGroupCustomerOpen(event.getContractNO(),event.getCustomerID(),1,descpription);
	}
	
	/**
	 * ��������idȡ�ñ���������豸id�б�  
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
	 * �ŵ꿪��
	 * 
	 * @param event���������������
	 */
    public void openAccountDirectly(BookEJBEvent event) throws ServiceException{
    	// ȡ���¿ͻ���Ϣ
    	NewCustomerInfoDTO newCustInfo=event.getNewCustInfo();
    	// ȡ���¿ͻ����˻���Ϣ
    	NewCustAccountInfoDTO newCustAcctInfo=event.getNewCustAcctInfo();
    	// ȡ��������Ϣ
    	CustServiceInteractionDTO csiDTO=event.getCsiDto();
    	// ȡ�ÿͻ���ַ��Ϣ
    	AddressDTO custAddrDto =event.getCustAddressDTO();
		// ȡ���˻���ַ��Ϣ
    	AddressDTO acctAddrDto =event.getAcctAddressDTO();
    	// ȡ���Ƿ����
    	boolean isAgent=event.isAgent();
    	serviceContext.put(Service.IS_AGENT,new Boolean(isAgent));
    	
    	//ȡ���¿ͻ��г���Ϣ
    	Collection  newCustMarketInfoList=event.getNewCustMarketInfoList();

		// �����ͻ�/�ͻ��˻���ַ��Ϣ
    	createAddressInfo(custAddrDto,acctAddrDto);
    	// ���õ�ַid
		newCustInfo.setFromAddressID(custAddrDto.getAddressID());
		newCustAcctInfo.setAddressID(acctAddrDto.getAddressID());
		
		// ����������Ϣ
		createCustServiceInteraction(csiDTO);
		
		// ���ÿͻ�service�����Ŀͻ���Ϣ
		CustomerService customerService=new CustomerService(serviceContext);
		customerService.create(newCustInfo,custAddrDto,newCustMarketInfoList);
		Customer customer=(Customer)serviceContext.get(Service.CUSTOMER);
		customer.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
		// ���ÿͻ��˻�sevice�������˻���Ϣ
		AccountService accountService=new AccountService(serviceContext);
		accountService.create(newCustAcctInfo,acctAddrDto);
		Account account =(Account)serviceContext.get(Service.ACCOUNT);
		
		// �����¿ͻ���Ϣ�˻���Ϣ������id
		newCustAcctInfo.setCsiID(csiDTO.getId());
		// �����ʻ���ַ���Ա�־
		newCustAcctInfo.setAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
		newCustInfo.setCsiID(csiDTO.getId());
		// �����¿ͻ���Ϣ
		newCustInfo.setCustID(customer.getCustomerID().intValue());
		createNewCustomerInfo( newCustInfo);
		
				
		// �����¿ͻ��˻���Ϣ
		newCustAcctInfo.setAccountID(account.getAccountID().intValue());
		createNewCustAccountInfo(newCustAcctInfo,true);

		// ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		
		// �����¿ͻ��г���Ϣ
		createNewCustomerMarketInfo(newCustMarketInfoList,newCustInfo.getId(),csiEJB.getId().intValue());
		
		
 
		// ��������Ϣ�������˻�id
		csiEJB.setAccountID(account.getAccountID().intValue());
		csiEJB.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
		csiDTO.setAccountID(account.getAccountID().intValue());
		
		// �ڿͻ��˻���Ϣ�����ÿͻ�id
		account.setCustomerID(customer.getCustomerID().intValue());
		// ��������Ϣ�����ÿͻ�id
		csiEJB.setCustomerID(customer.getCustomerID().intValue());
		csiDTO.setCustomerID(customer.getCustomerID().intValue());
		
		// ����Ѻ����Ϣ
		// �Ƿ񴴽�����
		if(isCreateJobcard(csiDTO)){
			LogUtility.log(clazz,LogLevel.DEBUG,"����������Ҫ����������������");
			// ��������,���ù���service����������Ϣ
			InstallationJobCardService installationJobCardService=new InstallationJobCardService(serviceContext);
		    int jobcardID=installationJobCardService.createJobCardForBooking(csiDTO,customer.getCustomerID().intValue(),0,newCustInfo, custAddrDto,event.getNextOrgID());
			// �������������Ĺ���id
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
			LogUtility.log(clazz,LogLevel.ERROR,"���ͻ�ԤԼ��Ʒ����״̬�����쳣��"+e1);
			throw new ServiceException("���ͻ�ԤԼ��Ʒ����״̬�����쳣");
		}catch(FinderException e){
			LogUtility.log(clazz,LogLevel.ERROR,"���ͻ�ԤԼ��Ʒ����״̬��λ������"+e);
			throw new ServiceException("���ͻ�ԤԼ��Ʒ����״̬��λ������"+e);
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
		    throw new ServiceException("���¿ͻ�ԤԼ��Ʒ��Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"updateCsiCustProductInfo",e);
		    throw new ServiceException("���¿ͻ�ԤԼ��Ʒ��Ϣʱ���Ҵ���");
    	}		
	}
	public Collection createCsiCustProductInfo(Collection csiPackageArray,String detailNo,Collection csiCampaignArray,CustServiceInteractionDTO csiDTO,CommonBusinessParamDTO businessParamDTO)throws  ServiceException{
		Collection allCsiCustProductDTOList=new ArrayList();
		
        //----------2006/11/23-------yangchen---------start------
        //���µĴ������׷��
        // ȡ���ڼ���豸�Ͳ�Ʒ���������õĲ�Ʒ���豸ӳ��ɹ�CsiCustProductDTO
        // ����BusinessRuleService.checkSelectTerminalDevice�����õ�
        Collection haveDeviceProductCol=(Collection)serviceContext.get(com.dtv.oss.service.Service.PRODUCT_MATCH_DEVICE_PRODUCT_LIST);
        //----------2006/11/23-------yangchen---------end------
		
        //��Ų�Ʒ����Ӧ���Żݺ��ײ�
        Collection referCsiCampaignCol =new ArrayList();
		// ȡ�ÿͻ�����Ĳ�Ʒ����Ϣ
		Iterator currentPackageIterator=csiPackageArray.iterator();
		while(currentPackageIterator.hasNext()){
			int packageid = ((Integer) currentPackageIterator.next()).intValue();	
			allCsiCustProductDTOList.addAll(createCsiCustProductInfo(packageid,detailNo,null,csiCampaignArray,csiDTO,haveDeviceProductCol,businessParamDTO,referCsiCampaignCol));	
		}
		
		// ���������ײ͵��Ż�Ҳ��¼��CsiProductInfo��
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
		    throw new ServiceException("�¿��������Ʒʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ��������");
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
            // �ж��Ź�ȯ���Ƿ�Ϊ��
            if(detailNo==null ||"".equals(detailNo)){
            	if(csiCampaignArray!=null && !csiCampaignArray.isEmpty()){
            		// ȡ�ò�Ʒ��������������Ż�
            		Collection campaignIDCol=BusinessUtility.getCampaignIDListByPackageID(packageId);
            		// ����ѡ����Ż��б��ҵ���Ӧ��Ʒ�����Ż�id
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
            	// ���Ź�ȯ��ʱ��ͨ���Ź�ȯȡ���Żݵ�id
            	GroupBargainDetailHome groupBargainDetailHome=HomeLocater.getGroupBargainDetailHome();
            	GroupBargainDetail groupBargainDetail=groupBargainDetailHome.findObjectByDetailNo(detailNo);
            	GroupBargainHome groupBargainHome=HomeLocater.getGroupBargainHome();
            	GroupBargain groupBargain=groupBargainHome.findByPrimaryKey(new Integer(groupBargainDetail.getGroupBargainID()));
            	currentCampaign=groupBargain.getCampaignId();
            }
            //----------2006/11/23-------yangchen---------start------
            //���µĴ�������Ƴ���ͨ���������ô��룬
            // ȡ���ڼ���豸�Ͳ�Ʒ���������õĲ�Ʒ���豸ӳ��ɹ�CsiCustProductDTO
            // ����BusinessRuleService.checkSelectTerminalDevice�����õ�
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
	            // ֻ�����ŵ꿪����ԤԼ�����ǣ��ڼ���Ʒ���豸�Ĺ��������в����õ������Ϣ
	            if(haveDeviceProductCol!=null && !haveDeviceProductCol.isEmpty()){
		            // ȡ�ò�Ʒ���������ж��Ƿ���Ӳ����Ʒ
		            Product product=productHome.findByPrimaryKey(new Integer(productId));
		            if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(product.getProductClass())){
			            // ��ȡ�õ����豸��Ӳ����Ʒѭ���ҵ���Ӧ�Ĳ�Ʒ
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
					// ���嶯��
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_OPEN);
				// ������Ʒ
				}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType())||
						 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDTO.getType())){
					// ���嶯��
					custProductDto.setAction(CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT);
				}
				
				// �����Ź����Żݵ������Ϣ
				if(detailNo!=null && !"".equals(detailNo)){
	                custProductDto.setReferGroupBargainID(csiDTO.getGroupCampaignID());                
				}else{
					custProductDto.setReferGroupBargainID(0);   
				}	
				custProductDto.setReferCampaignID(currentCampaign);
/* 
				// ȡ���¿ͻ���Ϣ,�����Ϣ������ǰ����ø÷�������ڷ�����������
				NewCustomerInfoDTO newCustInfo=(NewCustomerInfoDTO)serviceContext.get(com.dtv.oss.service.Service.NEW_CUSTOMER_INFO_DTO);
				// ������ҵ�û�����ʱʹ�õĺ�ͬ��
				if(newCustInfo!=null){
					custProductDto.setReferContractNo(newCustInfo.getContractNo());
				}
*/
				if(contractNO!=null && !"".equals(contractNO)){
					custProductDto.setReferContractNo(contractNO);
				}
				// ���������漰�Ŀͻ���Ʒ�����Ϣ
				CsiCustProductInfo custProduct = custHome.create(custProductDto);
				// ����id
				custProductDto.setId(custProduct.getId().intValue());
	            csiCustProductDTOList.add(custProductDto);
			}
            return csiCustProductDTOList;
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ��������");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCsiCustProductInfo",e);
		    throw new ServiceException("�¿��������Ʒʱ���Ҵ���");
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
				// ���嶯��
			    action =CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_OPEN;
		 // ������Ʒ
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiType)||
				 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiType)){
				// ���嶯��
				action =CommonConstDefinition.CSICUSTPRODUCTINFO_ACTION_NEWPRODUCT;
		}
		return action;
	}
	/**
     *���յ绰������Դ������ʹ����־
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
	 * ��װ����ԤԼȡ��ʱ�˷�
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
					LogUtility.log(clazz,LogLevel.DEBUG,"��װԤԼȡ���˷�ϵͳ�¼�");
					int csiID =0;
					if (csiDto.getId()!= 0){
						csiID = csiDto.getId();
					}
					//�¼��Ų�֪����������
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
				//��ȡ���������õ�ServiceContext������
				//��װ����ȡ���˷ѣ����ð�װ���ɹ��˷ѣ�
				FeeService.returnFeeForFailureInInstallation(csiDto,operatorID);
			}
		}
    	
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"��װԤԼȡ���˷Ѷ�λ����:"+e);
			throw new ServiceException("��װԤԼȡ���˷Ѷ�λ����");
		}
		catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.WARN,"����ϵͳ��־����:"+e2);
			throw new ServiceException("����ϵͳ��־����");
		}		
	}   
	
	public void batchServiceAccountOperation( ServiceAccountEJBEvent event)throws  ServiceException{
	    // ȡ��������Ϣ
	    CustServiceInteractionDTO csiDTO=event.getCsiDto();

     	Collection psidList=new ArrayList();
	    	
	   	// ����������Ϣ
	  	createCustServiceInteraction(csiDTO);
	   	// ���¿ͻ���Ϣ�˻���Ϣ֮����Ҫ�����������ÿͻ����˻�id
		CustServiceInteraction csiEJB = (CustServiceInteraction)serviceContext.get(Service.CSI);
		String[] serviceAcctStr =event.getServiceAcctIdStr();
	    for (int i=0;i<serviceAcctStr.length; i++){
	         int serviceAccountID =Integer.parseInt(serviceAcctStr[i]);
	         // ��ͣ
	         if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UP.equals(csiEJB.getType())){
	    	    // ȡ��ҵ���˻��������е������Ʒ
	    	    psidList.addAll(BusinessUtility.getSoftwareProductIDListByServiceAccount(serviceAccountID,CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_NORMAL));
		     // �ָ�
		     }else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UR.equals(csiEJB.getType())){
	  		    createCsiCustProductInfo(csiEJB,event.getColPsid());
	  		    return;
	         }
		}
	        
	     //  �û�/��ͣ/����
		 createCsiCustProductInfo(csiEJB,psidList);
     }
	
	
}
