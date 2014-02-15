/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Address;
import com.dtv.oss.domain.AddressHome;
import com.dtv.oss.domain.CustMarketInfo;
import com.dtv.oss.domain.CustMarketInfoHome;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustServiceInteractionHome;
import com.dtv.oss.domain.Customer;
import com.dtv.oss.domain.CustomerHome;
import com.dtv.oss.domain.NewCustomerInfoHome;
import com.dtv.oss.domain.NewCustomerMarketInfoHome;
import com.dtv.oss.domain.OldCustomerInfoHome;
import com.dtv.oss.domain.OldCustomerMarketInfoHome;
import com.dtv.oss.domain.ServiceAccount;
import com.dtv.oss.domain.ServiceAccountHome;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustMarketInfoDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.dto.NewCustomerMarketInfoDTO;
import com.dtv.oss.dto.OldCustomerInfoDTO;
import com.dtv.oss.dto.OldCustomerMarketInfoDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

/**
 * @author Leon Liu
 *
 * ������ͻ��йص�ҵ���߼�
 */
public class CustomerService extends AbstractService {
    private static final Class clazz = CustomerService.class;
    private ServiceContext context;
    
    /**
     * @param context�������Ķ���
     */
    public CustomerService(ServiceContext context) {
        this.context = context;
        setOperatorID(PublicService.getCurrentOperatorID(context));
    }
    /**
     * ���ݿͻ�id��װ�ͻ���Ϣ
     * @param customerID
     * @return
     * @throws ServiceException
     */
    public static CustomerDTO encapsulateCustomerDto(int customerID)throws ServiceException {
    	try {
    		CustomerDTO custDto = new CustomerDTO();
    		CustomerHome customerHome = HomeLocater.getCustomerHome();
    		Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
    		custDto.setCustomerID(customerID);
    		custDto.setCustomerType(customer.getCustomerType());
			return custDto;
    	}catch(HomeFactoryException e) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create",e);
		    throw new ServiceException("�����˻���Ϣʱ��λ����");
		}catch(FinderException e) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create", e);
		    throw new ServiceException("�����˻���Ϣʱ����");		    
		}
    }
    /**
     * �����ͻ�
     * @param custDto
     * @param marketInfoList
     * @throws ServiceException
     */
    public void create(CustomerDTO custDto,Collection marketInfoList) throws ServiceException{
    	create(custDto,null,marketInfoList);
    }

  
   
    /**
     * �����ͻ���Ϣ����ͻ���ص��г���Ϣ
     * @param custDto
     * @param marketInfoList
     * @throws ServiceException
     */
    public void create(CustomerDTO custDto, AddressDTO addressDto,Collection marketInfoList)throws ServiceException {
    	
    	LogUtility.log(clazz,LogLevel.DEBUG,"���봴���ͻ��������");
    	LogUtility.log(clazz,LogLevel.DEBUG,"����Ĳ�����CustomerDTO["+custDto+"]��AddressDTO["+addressDto+"]��" +
    			"�г���Ϣ[" + marketInfoList + "]");
    	
    	int orgID=0;
    	int addressID=0;
    	try {
    		if(addressDto!=null){
    			//������ַ
    			LogUtility.log(clazz,LogLevel.DEBUG,"������ַ");
    			AddressHome addressHome=HomeLocater.getAddressHome();
    			Address address=addressHome.create(addressDto);
    			orgID=BusinessUtility.getParentHasCustomerOrgID(
    					BusinessUtility.getOpOrgIDByOperatorID(((Integer)this.context.get(Service.OPERATIOR_ID)).intValue()));
    			
    			//orgID=BusinessUtility.getOrgIDByDistrictID(address.getDistrictID());
    			addressID=address.getAddressID().intValue();
    			addressDto.setAddressID(addressID);
    			
    			//�ѿͻ��ĵ�ַID����ServiceContext
    			context.put(Service.CUSTOMER_ADDRESS_ID,address.getAddressID());
    			
    			LogUtility.log(clazz,LogLevel.DEBUG,"�����¿ͻ���ַID=" + addressID);
    		}
    		
    		//�����ͻ���Ϣ
    		LogUtility.log(clazz,LogLevel.DEBUG,"�����ͻ�");
    		custDto.setOrgID(orgID);
    		custDto.setAddressID(addressID);
    		
            CustomerHome custHome = HomeLocater.getCustomerHome();
            Customer customer = custHome.create(custDto);
            
            custDto.setCustomerID(customer.getCustomerID().intValue());
            
            //��ִ��Ϣ
            context.put(Service.CUSTOMER,customer);
            context.put(Service.CUSTOMER_ID,customer.getCustomerID());
            LogUtility.log(clazz,LogLevel.DEBUG,"�����¿ͻ�ID=" + customer.getCustomerID());
            LogUtility.log(clazz,LogLevel.DEBUG,"�����¿ͻ���Ϣ=" + custDto);
            
            //�����г���Ϣ
            LogUtility.log(clazz,LogLevel.DEBUG,"�����г���Ϣ");
            if (marketInfoList != null && marketInfoList.isEmpty() == false) {
                Iterator ite4marketinfo = marketInfoList.iterator();
                CustMarketInfoHome marketHome = HomeLocater.getCustMarketInfoHome(); 
                while(ite4marketinfo.hasNext()) {
                    NewCustomerMarketInfoDTO newDto = (NewCustomerMarketInfoDTO)ite4marketinfo.next();
                    CustMarketInfoDTO dto = new CustMarketInfoDTO();
                    dto.setCustomerId(customer.getCustomerID().intValue());
                    dto.setInfoSettingId(newDto.getInfoSettingId());
                    dto.setInfoValueId(newDto.getInfoValueId());
                    dto.setMemo(newDto.getMemo());
                    LogUtility.log(clazz,LogLevel.DEBUG,"�ڴ���һ���г���Ϣʱ���DTO��DTO����ΪΪ��"+dto);
                    marketHome.create(dto);
                }
            }
            
            //ȡ���������ض���
            CustServiceInteraction csi = (CustServiceInteraction)context.get(Service.CSI);
            int csiID=0;
            if(csi!=null){
            	csi.setCustomerID(customer.getCustomerID().intValue());
            	csiID=csi.getId().intValue();
            }
            //����ϵͳ�¼�
            LogUtility.log(clazz,LogLevel.DEBUG,"����ϵͳ�¼�");
            SystemEventRecorder.AddEvent4Customer(SystemEventRecorder.SE_CUST_OPEN, customer.getCustomerID().intValue(),
					0, 0, csiID, PublicService.getCurrentOperatorID(context), SystemEventRecorder.SE_STATUS_CREATE);
        }
    	catch(HomeFactoryException e) {
            LogUtility.log(clazz,LogLevel.ERROR, e);
            throw new ServiceException("�޷��ҵ�entity customer ��home �ӿ�");
        }catch(CreateException e) {
            LogUtility.log(clazz,LogLevel.ERROR, e);
            throw new ServiceException("�޷������ͻ�����");
        }
        LogUtility.log(clazz,LogLevel.DEBUG,"���������ͻ��������");
    }

    /**
     * �����ͻ���Ϣ����ͻ���ص��г���Ϣ
     * @param custDto
     * @param marketInfoList
     * @PARAM isCreateBak:�Ƿ�ͬʱ�����û�������Ϣ
     * @throws ServiceException
     */
    public void create(CustomerDTO custDto, AddressDTO addressDto,Collection marketInfoList,boolean isCreateBak)throws ServiceException{
    	create(custDto,addressDto,marketInfoList);
    	if(isCreateBak){
    		createCustAndAcctBackupInfo(custDto);
    	}
    }
    
    public void createCustAndAcctBackupInfo(CustomerDTO custDto)throws ServiceException{
    	OldCustomerInfoDTO oldDTO=getOldCustomerInfoDTOFromCustomer(custDto.getCustomerID());
    	try{
		   OldCustomerInfoHome oldCustInfohome=HomeLocater.getOldCustomerInfoHome();
		   oldCustInfohome.create(oldDTO);
		   NewCustomerInfoDTO newDto=getNewCustomerInfoDTOFromCustomer(custDto);
		   NewCustomerInfoHome newCustInfohome=HomeLocater.getNewCustomerInfoHome();
		   newCustInfohome.create(newDto);
    	 } catch(HomeFactoryException e){
	    	 LogUtility.log(clazz,LogLevel.ERROR, e);
	         throw new ServiceException("���ҿͻ�������Ϣ�ӿڳ���");
    	 } catch(CreateException e){
	     	 LogUtility.log(clazz,LogLevel.ERROR, e);
	         throw new ServiceException("�����ͻ�������Ϣ����");
	     }
     }
    
    /**
     * �����ͻ�
     * @param nciDto
     * @param marketInfoList
     * @throws ServiceException
     */
    public void create(NewCustomerInfoDTO nciDto,Collection marketInfoList) throws ServiceException{
    	create(nciDto,null,marketInfoList);
    }
    
    /**
     * �����ͻ�
     * @param nciDto
     * @param marketInfoList
     * @throws ServiceException
     */
    public void create(NewCustomerInfoDTO nciDto, AddressDTO addressDto,Collection marketInfoList) throws ServiceException {
    	//�������ͺͰ�װ����
    	String csiType="";
    	String installationType="";
    	CustServiceInteraction csiEJB=(CustServiceInteraction)context.get(Service.CSI);
    	if(csiEJB==null){        
    		csiType ="" + context.get(Service.CSI_TYPE).toString();
    		installationType="" + context.get(Service.CSI_INSTALLATION_TYPE).toString();
    	}
    	else
    	{
    		csiType=csiEJB.getType();
    		installationType=csiEJB.getInstallationType();
    	}
        
        CustomerDTO custDto = new CustomerDTO();
        custDto.setCustomerSerialNo(nciDto.getCustomerSerialNo());
        custDto.setCustomerStyle(nciDto.getCustStyle());
        custDto.setAgentName(nciDto.getAgentName());
        custDto.setCustomerType(nciDto.getType());
        custDto.setGender(nciDto.getGender());
        custDto.setName(nciDto.getName());
        custDto.setBirthday(nciDto.getBirthday());
        custDto.setCardType(nciDto.getCardType());
        custDto.setCardID(nciDto.getCardID());
        custDto.setTelephone(nciDto.getTelephone());
        custDto.setLoginID(nciDto.getLoginID());
        custDto.setLoginPwd(nciDto.getLoginPWD());
        
        custDto.setTelephoneMobile(nciDto.getMobileNumber());
        custDto.setFax(nciDto.getFaxNumber());
        custDto.setEmail(nciDto.getEmail());
        custDto.setOpenSourceType(nciDto.getOpenSourceType());
        custDto.setOpenSourceTypeID(nciDto.getOpenSourceID());
        custDto.setGroupBargainID(nciDto.getGroupBargainID());
        custDto.setAddressID(nciDto.getFromAddressID());
        custDto.setCatvID(nciDto.getCatvID());
        custDto.setSocialSecCardID(nciDto.getSocialSecCardID());
        custDto.setComments(nciDto.getComments());
        
        // add by Chen jiang 2006/10/08
        custDto.setNationality(nciDto.getNationality());
        //add by chen jiang 2006/12/21 
        custDto.setContractNo(nciDto.getContractNo());
        //ֻ���ŵ꿪�����������԰�װ���û���״̬����Ǳ�ڣ�����Ϊ����״̬----->���п�����״̬��ΪǱ��modify 2005-12-15
		//if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiType)&&
		//	CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL.equals(installationType))
		custDto.setStatus(CommonConstDefinition.CUSTOMER_STATUS_POTENTIAL);
		//else
		//	custDto.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
		//��06-11-17�޸ļ��ſͻ���ʼ״̬Ϊ����
		if(CommonConstDefinition.CUSTOMERSTYLE_GROUP.equals(nciDto.getCustStyle()))
			custDto.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
		//����Ǽ��ſͻ��ӿͻ�����
    	if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS.equals(csiType)){
    		custDto.setGroupCustID(nciDto.getGroupCustID());
    	}
    	
        custDto.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        
        create(custDto,addressDto,marketInfoList);  
    }

    /**
     * ����actionType���¿ͻ���Ϣ
     * ationType=EJBEvent.REGISTER_INSTALLATION_INFO��
     * @param csiID
     * @param actionType
     * @throws ServiceException
     */
    public void updateCustomer(int csiID,int actionType) throws ServiceException{
    	if(csiID==0||actionType==0)
    		throw new ServiceException("���¿ͻ����ϲ�������!");
    	
    	try{
    		CustServiceInteractionHome csiHome=HomeLocater.getCustServiceInteractionHome();
    		CustServiceInteraction csi=csiHome.findByPrimaryKey(new Integer(csiID));
    		int customerID=csi.getCustomerID();
    		CustomerHome customerHome=HomeLocater.getCustomerHome();
    		Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
    		
    		//registerInstallationInfo(¼�밲װ��Ϣ),����ɹ�,�޸Ŀͻ�״̬Ϊ����
    		if(actionType==EJBEvent.REGISTER_INSTALLATION_INFO){
    			customer.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
    		} 
    	}
    	catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ϣ����ԭ�򣺿ͻ���λ����");
    		throw new ServiceException("���Ŀͻ���Ϣ����ԭ�򣺿ͻ���λ����");
		}
		catch(FinderException e1){
			LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ϣ����ԭ�򣺿ͻ����ҳ���");
    		throw new ServiceException("���Ŀͻ���Ϣ����ԭ�򣺿ͻ����ҳ���");
		}
    }
    /**
     * ���¿ͻ���Ϣ,����˱��ݴ���,�Ծɿͻ���Ϣ����,���¿ͻ���Ϣ����.
     * �����,Ŀǰ�˷���ֻ�����ڼ��ſͻ�ת���˿ͻ�
     * @param customerDto �ͻ�DTO
     * @param addressDto ��ַDTO
     * @param custMarketInfoList �г���Ϣ
     * @param isCreateBak �Ƿ񴴽�����
     * @throws ServiceException
     */
    public void updateCustomer(CustomerDTO customerDto, AddressDTO addressDto,
			Collection custMarketInfoList, boolean isCreateBak)
			throws ServiceException {
		if (isCreateBak) {
			//������/�ɿͻ���Ϣ
			if (addressDto !=null) 
                customerDto.setAddressID(addressDto.getAddressID());
			createCustAndAcctBackupInfo(customerDto);
             //	���¿ͻ���Ϣ
			updateCustomer(customerDto, addressDto, custMarketInfoList);
		} else {
			updateCustomer(customerDto, addressDto, custMarketInfoList);
		}
	}
    /**
     *  �޸Ŀͻ���Ϣ�����addressDtoΪ�գ���ֻ�޸�Customer��Ϣ��
     *  ���addressDto��Ϊ�գ�customerDtoΪ�ջ�customerDto.getCustomerID=0�����׳��쳣��
     * @param customerDto
     * @param addressDto
     * @param custMarketInfoList:�ͻ��г���Ϣ
     * @throws ServiceException 
     */    		
    public void updateCustomer(CustomerDTO customerDto,AddressDTO addressDto,Collection custMarketInfoList)
    		throws ServiceException{
    	
    	LogUtility.log(clazz,LogLevel.DEBUG,"������¿ͻ���Ϣ��");
    	
    	if(customerDto==null||customerDto.getCustomerID()==0){
    		throw new ServiceException("�������󣺲�����Ϊ�գ�");
    	}

    	try{
    		LogUtility.log(clazz,LogLevel.DEBUG,"���¿ͻ���Ϣ�������DTOΪ��" +customerDto);
    		CustomerHome custHome=HomeLocater.getCustomerHome();
    		Customer customer=custHome.findByPrimaryKey(new Integer(customerDto.getCustomerID()));
    		
    		//�޸Ŀͻ���Ϣ	
    		if(BusinessUtility.isExitMultiLoginID(customerDto.getCustomerID(),customerDto.getLoginID())){
    			throw new ServiceException("������ͻ���½ID����ID���Ѿ����ڣ�");
    		}
    		if(customer.ejbUpdate(customerDto)==-1){
    			LogUtility.log(clazz,LogLevel.WARN,"���¿ͻ���Ϣ����ԭ��ejbUpdate(customerDto)==-1");
	    		throw new ServiceException("���¿ͻ���Ϣ����");
    		}
    		
    		//�޸ĵ�ַ��Ϣ
    		if(addressDto!=null){
    			LogUtility.log(clazz,LogLevel.DEBUG,"���¿ͻ���ַ��Ϣ�������DTOΪ��" +addressDto);
                
    			addressDto.setAddressID(customerDto.getAddressID());
    			AddressHome addHome=HomeLocater.getAddressHome();
    			Address add=addHome.findByPrimaryKey(new Integer(addressDto.getAddressID()));;
    			if(add.ejbUpdate(addressDto)==-1){
    				LogUtility.log(clazz,LogLevel.WARN,"���¿ͻ���ַ��Ϣ����ԭ��ejbUpdate(addressDto)==-1");
    	    		throw new ServiceException("���¿ͻ���ַ��Ϣ����");
    			}
        		context.put(Service.CUSTOMER_ADDRESS_EJB, add);
    		}
    		    		
    		//�޸Ŀͻ��г���Ϣ
    		int csiID =((Integer) context.get(Service.CSI_ID)).intValue();
    		if(custMarketInfoList!=null && custMarketInfoList.isEmpty()==false){
    			LogUtility.log(clazz,LogLevel.DEBUG,"���¿ͻ��г���Ϣ�������listΪ��" +custMarketInfoList);
    			updateNewCustomerMarketInfo(custMarketInfoList,customerDto.getCustomerID(),csiID);
    		}
    		
    		//��12-06����,���ڼ���ת���˵�ʱ��,�����ʻ�,
    		context.put(Service.CUSTOMER, customer);

    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ϣ����ԭ�򣺿ͻ���λ����");
    		throw new ServiceException("���Ŀͻ���Ϣ����ԭ�򣺿ͻ���λ����");
    		
    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.WARN,"���Ŀͻ���Ϣ����ԭ�򣺿ͻ����ҳ���");
    		throw new ServiceException("���Ŀͻ���Ϣ����ԭ�򣺿ͻ����ҳ���");
    	}    	
    	LogUtility.log(clazz,LogLevel.DEBUG,"�������¿ͻ���Ϣ��");
    }
    
    
    
   /**
    * ԭ�ع�������ع���
    * @param actionType: ��������:ԭ�ع�������ع���������EJBEvent
    * @param customerDTO : �¿ͻ���Ϣ
    * @param custMarketInfoList : �¿ͻ����г���Ϣ
    * @param custAddrDTO : �¿ͻ��ĵ�ַ��Ϣ
 * @throws ServiceException 
    */
    public void transferCustomer(int actionType,CustomerDTO customerDTO,
    		Collection custMarketInfoList,AddressDTO custAddrDTO) throws ServiceException{
    	try{ 
    		
    	
    	if(actionType!=EJBEvent.TRANSFERTODIFFERENTPLACE && actionType!=EJBEvent.TRANSFERTOSAMEPLACE){
    		LogUtility.log(clazz,LogLevel.WARN, "������������δ֪��Ŀǰֻ֧��ԭ�ع�������ع�����");
    		throw new ServiceException("Ŀǰֻ֧��ԭ�ع�������ع�����");
    	}
    	if(customerDTO==null || customerDTO.getCustomerID()==0){
    		LogUtility.log(clazz,LogLevel.WARN, "����������������");
    		throw new ServiceException("����������������");
    	}

		CustomerDTO oldCustomerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerDTO.getCustomerID());
		AddressDTO oldaddrDto=BusinessUtility.getAddressDTOByAddressID(oldCustomerDTO.getAddressID());
		//�ж��Ƿ�Ϊ��ع�������ع���������ַ
		if(actionType==EJBEvent.TRANSFERTODIFFERENTPLACE){
			
			updateCustomer(customerDTO,custAddrDTO,custMarketInfoList);	
		}else{
			updateCustomer(customerDTO,null,custMarketInfoList);	
		}
        SystemEventRecorder.AddEvent4Customer(SystemEventRecorder.SE_CUST_TRANSFER, customerDTO.getCustomerID(),
				0, 0, ((Integer)context.get(Service.CSI_ID)).intValue(), getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);
		
		//ϵͳ��־��¼
		String log="";
		StringBuffer logDes=new StringBuffer();
		if (actionType == EJBEvent.TRANSFERTODIFFERENTPLACE) {
			log = "�ͻ���ع���";
			logDes.append(log);
			logDes.append(",����ID:").append(context.get(Service.CSI_ID)).append(
					",");
			logDes.append(SystemLogRecorder.appendDescString(";�ͻ�����:",
					oldCustomerDTO.getName(), customerDTO.getName()));

			logDes.append(SystemLogRecorder.appendDescString(";�ͻ�����:",
					BusinessUtility.getDistrictNameById(oldaddrDto
							.getDistrictID()), BusinessUtility
							.getDistrictNameById(custAddrDTO.getDistrictID())));
			logDes.append(SystemLogRecorder.appendDescString(";�ʱ�:", oldaddrDto
					.getPostcode(), custAddrDTO.getPostcode()));
			logDes.append(SystemLogRecorder.appendDescString(";��ϸ��ַ:",
					oldaddrDto.getDetailAddress(), custAddrDTO
							.getDetailAddress()));
		} else {
			log = "�ͻ�ԭַ����";
			logDes.append(log);
			logDes.append(",����ID:").append(context.get(Service.CSI_ID)).append(
					",");
			logDes.append(SystemLogRecorder.appendDescString(";�ͻ�����:",
					oldCustomerDTO.getName(), customerDTO.getName()));
			logDes.append(SystemLogRecorder.appendDescString(";֤������:",
					BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERCARDTYPE",oldCustomerDTO.getCardType()),
					BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERCARDTYPE", customerDTO.getCardType())));
			logDes.append(SystemLogRecorder.appendDescString(";֤������:",
					oldCustomerDTO.getCardID(), customerDTO.getCardID()));
		}
		
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
    			PublicService.getCurrentOperatorID(context), customerDTO.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, log, logDes.toString(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
    	}catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("�ͻ�������λ����");
    	}catch(CreateException e2){
    		LogUtility.log(clazz,LogLevel.WARN,e2);
    		throw new ServiceException("�����ͻ�����ϵͳ�¼�����");
    	}
    }
    
    /**
     * Ǩ��
     * @param customerDTO
     * @param custAddrDTO
     * @throws ServiceException
     */
    public void  moveCustomer(CustomerDTO customerDTO,AddressDTO custAddrDTO) throws ServiceException{
    	
    	if(customerDTO==null||customerDTO.getCustomerID()==0){
    		LogUtility.log(clazz,LogLevel.WARN, "Ǩ�Ʋ�����������");
    		throw new ServiceException("Ǩ�Ʋ�����������");
    	}
    	//ȡ���Ͽͻ���Ϣ
    	CustomerDTO oldCustomerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerDTO.getCustomerID());
    	AddressDTO oldaddrDto=BusinessUtility.getAddressDTOByAddressID(oldCustomerDTO.getAddressID());

    	//�ò����еĿͻ���Ϣ�����Ͽͻ���¼
    	updateCustomer(customerDTO,null,null);
    		
    	//ϵͳ��־��¼
    	String log = "�ͻ�Ǩ��";
		StringBuffer logDes = new StringBuffer();
		logDes.append(log).append(",");
		logDes.append("����ID:").append(context.get(Service.CSI_ID)).append(
					",");
		logDes.append("�ͻ�����:").append(oldCustomerDTO.getName());
		logDes.append(SystemLogRecorder.appendDescString(";�ͻ�����:",
					BusinessUtility.getDistrictNameById(oldaddrDto
							.getDistrictID()), BusinessUtility
							.getDistrictNameById(custAddrDTO.getDistrictID())));
		logDes.append(SystemLogRecorder.appendDescString(";�ʱ�:", oldaddrDto
					.getPostcode(), custAddrDTO.getPostcode())
					+ SystemLogRecorder
							.appendDescString(";��ϸ��ַ:", oldaddrDto
									.getDetailAddress(), custAddrDTO
									.getDetailAddress()));

		SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context),
					customerDTO.getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, log, logDes.toString(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
    	
    }
   
    /**
     * �ͻ�����
     * @param customerID���ͻ�ID
     * @throws ServiceException
     */
    public void closeCustomer(int customerID)throws ServiceException{
    	
    	//�ͻ����� ��ʱ��,����������ͻ�Ӳ����Ʒ��ȡ��,��Ҫ����־��¼�м�¼�豸�����к�
    	//ȡ���豸�����к�
        String logDeviceSerialNoDesc="";
        Collection deviceIDList = BusinessUtility.getDeviceIDListByCustInfo(customerID);
        Iterator itDeviceIDList = deviceIDList.iterator();
        int i = 0;
    	while(itDeviceIDList.hasNext()){
    		Integer deviceID=(Integer)itDeviceIDList.next();
    		LogUtility.log(clazz,LogLevel.DEBUG,"�ͻ������漰���豸��:"+deviceID);
    		if(deviceID.intValue()>0){
    			TerminalDeviceDTO DeviceDTO = BusinessUtility.getDeviceByDeviceID(deviceID.intValue());
    			String SerialNo = DeviceDTO.getSerialNo();
    			LogUtility.log(clazz,LogLevel.DEBUG,"�ͻ������漰���豸���к�:"+SerialNo);
    			logDeviceSerialNoDesc = logDeviceSerialNoDesc +"("+(++i)+")"+ SerialNo + ",";
    		}
    	}
    	if(!"".equals(logDeviceSerialNoDesc))
    		logDeviceSerialNoDesc=";�漰���豸���к�:" + logDeviceSerialNoDesc;
    	
    	//ά���豸��Թ�ϵ 2007-12-19
    	CustomerProductService customerProductService = new CustomerProductService(this.context);
    	customerProductService.unchainDeviceMatch(deviceIDList);
    	closeCustOnly(customerID,false);
    	
    	try{        	
        	//����ϵͳ�¼�
        	LogUtility.log(clazz,LogLevel.DEBUG,"�ͻ���������ϵͳ�¼�");
            int csiID=0;
            if(this.context.get(Service.CSI_ID)!=null){
            	csiID=((Integer)this.context.get(Service.CSI_ID)).intValue();
            }
            SystemEventRecorder.AddEvent4Customer(SystemEventRecorder.SE_CUST_CANCEL, customerID,
					0, 0, csiID, getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);
            
        	//����ϵͳ��־
        	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(this.context), 
        			PublicService.getCurrentOperatorID(this.context), customerID, 
    				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ͻ�����"," �ͻ�����,����ID:"+context.get(Service.CSI_ID)+";�ͻ�ID ��"+customerID+logDeviceSerialNoDesc, 
    				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
        	
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"�ͻ�������λ����:"+e);
    		throw new ServiceException("�ͻ�������λ����");
    	}
    	catch(CreateException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"����ϵͳ��־����:"+e2);
    		throw new ServiceException("����ϵͳ��־����");
    	}
    	
    }
    
    /**
     * �ͻ��˻�
     * @param customerID
     * @param deviceList���˻����豸�б�,��װ��ʽΪInteger����
     * @throws ServiceException
     */
    public void withdrawCustomer(int customerID,Collection deviceList)throws ServiceException{
    	
    	//�ͻ��˻� ��ʱ��,����������ͻ�Ӳ����Ʒ��ȡ��,��Ҫ����־��¼�м�¼�豸�����к�
    	//ȡ���豸�����к�
        String logDeviceSerialNoDesc="";
        Collection deviceIDList = BusinessUtility.getDeviceIDListByCustInfo(customerID);
        Iterator itDeviceIDList = deviceIDList.iterator();
        int i = 0;
        String SerialNo = "";
        Integer deviceID;
        TerminalDeviceDTO DeviceDTO;
    	while(itDeviceIDList.hasNext()){
    		deviceID=(Integer)itDeviceIDList.next();
    		LogUtility.log(clazz,LogLevel.DEBUG,"�ͻ��˻��漰���豸��:"+deviceID);
    		if(deviceID.intValue()>0){
    			DeviceDTO = (TerminalDeviceDTO)BusinessUtility.getDeviceByDeviceID(deviceID.intValue());
    			SerialNo = DeviceDTO.getSerialNo();
    			LogUtility.log(clazz,LogLevel.DEBUG,"�ͻ��˻��漰���豸���к�:"+SerialNo);
    			logDeviceSerialNoDesc = logDeviceSerialNoDesc +"("+(++i)+")"+ SerialNo + ",";
    		}
    	}
    	if(!"".equals(logDeviceSerialNoDesc))
    		logDeviceSerialNoDesc=";�漰���豸���к�:" + logDeviceSerialNoDesc;
    	
    	//ά���豸��Թ�ϵ 2007-12-19
    	CustomerProductService customerProductService = new CustomerProductService(this.context);
    	customerProductService.unchainDeviceMatch(deviceIDList);
    	closeCustOnly(customerID,true);
    	
    	try{        	
        	//����ϵͳ�¼�
        	LogUtility.log(clazz,LogLevel.DEBUG,"�ͻ��˻�����ϵͳ�¼�");
            int csiID=0;
            if(this.context.get(Service.CSI_ID)!=null){
            	csiID=((Integer)this.context.get(Service.CSI_ID)).intValue();
            }
            SystemEventRecorder.AddEvent4Customer(SystemEventRecorder.SE_CUST_WITHDRWAN, customerID,
					0, 0, csiID, getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);
            
        	//����ϵͳ��־
        	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(this.context), 
        			PublicService.getCurrentOperatorID(this.context), customerID, 
    				SystemLogRecorder.LOGMODULE_CUSTSERV, "�ͻ��˻�"," �ͻ��˻�,����ID:"+context.get(Service.CSI_ID)+";�ͻ�ID ��"+customerID+logDeviceSerialNoDesc, 
    				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
        	
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"�ͻ��˻���λ����:"+e);
    		throw new ServiceException("�ͻ��˻���λ����");
    	}
    	catch(CreateException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"����ϵͳ��־����:"+e2);
    		throw new ServiceException("����ϵͳ��־����");
    	}
    }
    
    public void returnFeeForCustomer(CustServiceInteractionDTO custServiceInteractionDTO)throws ServiceException{
    	try{
	    	if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(custServiceInteractionDTO.getType())||
	    	   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(custServiceInteractionDTO.getType())||
	    	   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(custServiceInteractionDTO.getType())){
	    		closeCustOnly(custServiceInteractionDTO.getCustomerID(),true);
	    		
	        		LogUtility.log(clazz,LogLevel.DEBUG,"��װ���ɹ��˷�ϵͳ�¼�");
	        		int csiID =0;
	        		if (this.context.get(Service.CSI_ID)!=null){
	        			csiID =((Integer)this.context.get(Service.CSI_ID)).intValue();
	        		}
	        		//zyg 2007.10.23 begin
	        		//�յ��¼��ţ�û������
	        		/*****************************************
	        		//�¼��Ų�֪����������
	        		SystemEventRecorder.AddEvent4Customer(0, custServiceInteractionDTO.getCustomerID(),
	    					0, 0, csiID, getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);
	            /*****************************************/   
	            //zyg 2007.10.23 end
	    	}
	    	//zyg 2007.10.23 begin 
	    	//����Ϊ ֻ���ŵ����� �� ԤԼ���� ��������·�������� ģ��ҵ����ع�����װ���ɹ��˷� �ߵ�����·�����Ӷ������ȡ��ҵ���ʻ�
	    	/*********************************************/
	    	//else{
	    	else if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO.equals(custServiceInteractionDTO.getType())||
	    	   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU.equals(custServiceInteractionDTO.getType()))
	    	{
	    	/*********************************************/
	    	//zyg 2007.10.23 end
	    		Collection col=BusinessUtility.getServiceAccountIDListByCsiID(custServiceInteractionDTO.getId());
	    		ServiceAccountService serviceAccountService=new ServiceAccountService(this.context);
	    		Iterator it = col.iterator();
	            while (it.hasNext())
	            {
	            	Integer integer=(Integer) it.next();
	            	serviceAccountService.close(integer.intValue(),true);
	            }
	    	}
    	}
    	//zyg 2007.10.23 begin
    	//exception never thrown
    	/***********************************************
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"��װ���ɹ��˷Ѷ�λ����:"+e);
    		throw new ServiceException("��װ���ɹ��˷Ѷ�λ����");
    	}
    	catch(CreateException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"����ϵͳ��־����:"+e2);
    		throw new ServiceException("����ϵͳ��־����");
    	}
    	/***********************************************/
    	/***********************************************/
    	catch(Exception e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"��װ���ɹ��˷ѹ����г���",e);
		    throw new ServiceException("��װ���ɹ��˷ѹ����г���");
		}
		/***********************************************/
		//zyg 2007.10.23 end
		
    }
    
    
    
    
    /**
     * �ͻ�����/�˻�/��װ���ɹ��˷�/ԤԼ����������װ����
     * @param customerID
     * @throws ServiceException
     */
    public void closeCustOnly(int customerID,boolean returnDevice)throws ServiceException{
    	if(customerID==0)
    		throw new ServiceException("��������!");

    	context.put(Service.CUSTOMER_ID,new Integer(customerID));
    	try{
    		//�õ�ҵ���ʻ��б�
        	ServiceAccountHome saHome=HomeLocater.getServiceAccountHome();
        	ServiceAccount sa=null; 	
        	Collection saList=BusinessUtility.getCurrentServiceAccountByCustomerID(customerID,null);
        	//����saList�б�
        	ServiceAccountService saService=new ServiceAccountService(this.context);
        	Iterator itSA=saList.iterator();
        	while(itSA.hasNext()){
        		Integer saID=(Integer)itSA.next();
        		sa=saHome.findByPrimaryKey(saID);
        		//�õ��ͻ�����ȡ��״̬��ҵ���ʻ�
        		if(!(CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL.equals(sa.getStatus()))){
        			saService.close(sa.getServiceAccountID().intValue(),returnDevice);
        			/*
        			if(returnDevice)
        				saService.close(sa.getServiceAccountID().intValue());
        			else
        				saService.closeOnly(sa.getServiceAccountID().intValue());
        			*/
        		}
        	}
        	
        	AccountService accountService=new AccountService(this.context);
        	accountService.close(customerID);
        	
        	//����ͻ���¼
        	CustomerHome customerHome = HomeLocater.getCustomerHome();
        	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
        	customer.setStatus(CommonConstDefinition.CUSTOMER_STATUS_CANCEL);
        	customer.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        	
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"ҵ���ʻ���λ����:"+e);
    		throw new ServiceException("ҵ���ʻ���λ����");
    	}
    	catch(FinderException e1){
    		LogUtility.log(clazz,LogLevel.WARN,"ҵ���ʻ����ҳ���:"+e1);
    		throw new ServiceException("ҵ���ʻ����ҳ���");
    	}
    }
    
    
    /**
     * ͨ��Customer�õ��û�����Ϣ����Ҫ���ڹ�����Ǩ��ʱ�޸��û���Ϣ�����ֱ��뱣���ϵĿͻ���Ϣ��T_NEWCUSTOMERINFO
     * @param customer
     * @return
     */
    private OldCustomerInfoDTO getOldCustomerInfoDTOFromCustomer(int customerID) throws ServiceException{
    	OldCustomerInfoDTO oldCustInfoDTO =new OldCustomerInfoDTO();
		int csiid=((Integer)context.get(Service.CSI_ID)).intValue();
		Customer customer =null;
		try{
			CustomerHome customerHome=HomeLocater.getCustomerHome();
			customer =customerHome.findByPrimaryKey(new Integer(customerID));
		}
		catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"�ͻ���λ����:"+e);
    		throw new ServiceException("�ͻ���λ����");
    	}
    	catch(FinderException e1){
    		LogUtility.log(clazz,LogLevel.WARN,"�ͻ����ҳ���:"+e1);
    		throw new ServiceException("�ͻ����ҳ���");
    	}
    	
		oldCustInfoDTO.setCsiID(csiid);
		oldCustInfoDTO.setCustomerSerialNo(customer.getCustomerSerialNo());
		oldCustInfoDTO.setCardID(customer.getCardID());
		oldCustInfoDTO.setCardType(customer.getCardType());
		oldCustInfoDTO.setCatvID(customer.getCatvID());
		oldCustInfoDTO.setContractNo(customer.getContractNo());
		oldCustInfoDTO.setCustID(customer.getCustomerID().intValue());
		oldCustInfoDTO.setEmail(customer.getEmail());
		oldCustInfoDTO.setFaxNumber(customer.getFax());
		oldCustInfoDTO.setAddressID(customer.getAddressID());
		oldCustInfoDTO.setGender(customer.getGender());
		oldCustInfoDTO.setGroupBargainID(customer.getGroupBargainID());
		oldCustInfoDTO.setMobileNumber(customer.getTelephoneMobile());
		oldCustInfoDTO.setName(customer.getName());
		oldCustInfoDTO.setNationality(customer.getNationality());
		oldCustInfoDTO.setOpenSourceID(customer.getOpenSourceTypeID());
		oldCustInfoDTO.setOpenSourceType(customer.getOpenSourceType());
		oldCustInfoDTO.setSocialSeccardID(customer.getSocialSecCardID());
		oldCustInfoDTO.setTelephone(customer.getTelephone());
		oldCustInfoDTO.setDtCreate(TimestampUtility.getCurrentTimestamp());
		oldCustInfoDTO.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		//oldCustInfoDTO.setTimeRangeEnd(customer.gett);
		//oldCustInfoDTO.setTimeRangeStart();
		oldCustInfoDTO.setType(customer.getCustomerType());
		oldCustInfoDTO.setComments(customer.getComments());
		oldCustInfoDTO.setBirthday(customer.getBirthday());
		oldCustInfoDTO.setLoginID(customer.getLoginID());
		oldCustInfoDTO.setLoginPwd(customer.getLoginPwd());
		return oldCustInfoDTO;
    }
    
    private NewCustomerInfoDTO getNewCustomerInfoDTOFromCustomer(CustomerDTO customerDto) 
       throws ServiceException{
    	NewCustomerInfoDTO newCustInfoDTO =new NewCustomerInfoDTO();
		int csiid=((Integer)context.get(Service.CSI_ID)).intValue();
		Address bakAddress =null;
		if (customerDto.getAddressID() !=0)
		    bakAddress =PublicService.createAddressBakup(customerDto.getAddressID());
		int fromAddressID =0;
		if (bakAddress ==null){
			try{
				CustomerHome  custHome =HomeLocater.getCustomerHome();
				Customer      cust =custHome.findByPrimaryKey(new Integer(customerDto.getCustomerID()));
			    fromAddressID =cust.getAddressID();
			}
			catch(HomeFactoryException e){
	    		LogUtility.log(clazz,LogLevel.WARN,"�ͻ���λ����:"+e);
	    		throw new ServiceException("�ͻ���λ����");
	    	}
	    	catch(FinderException e1){
	    		LogUtility.log(clazz,LogLevel.WARN,"�ͻ����ҳ���:"+e1);
	    		throw new ServiceException("�ͻ����ҳ���");
	    	}
		}else{
			fromAddressID =bakAddress.getAddressID().intValue();
		}
			
		newCustInfoDTO.setCsiID(csiid);
		newCustInfoDTO.setCustomerSerialNo(customerDto.getCustomerSerialNo());
		newCustInfoDTO.setCardID(customerDto.getCardID());
		newCustInfoDTO.setCardType(customerDto.getCardType());
		newCustInfoDTO.setCatvID(customerDto.getCatvID());
		newCustInfoDTO.setContractNo(customerDto.getContractNo());
		newCustInfoDTO.setCustID(customerDto.getCustomerID());
		newCustInfoDTO.setEmail(customerDto.getEmail());
		newCustInfoDTO.setFaxNumber(customerDto.getFax());
		newCustInfoDTO.setFromAddressID(fromAddressID);
		newCustInfoDTO.setGender(customerDto.getGender());
		newCustInfoDTO.setGroupBargainID(customerDto.getGroupBargainID());
		newCustInfoDTO.setMobileNumber(customerDto.getTelephoneMobile());
		newCustInfoDTO.setName(customerDto.getName());
		newCustInfoDTO.setNationality(customerDto.getNationality());
		newCustInfoDTO.setOpenSourceID(customerDto.getOpenSourceTypeID());
		newCustInfoDTO.setOpenSourceType(customerDto.getOpenSourceType());
		newCustInfoDTO.setSocialSecCardID(customerDto.getSocialSecCardID());
		newCustInfoDTO.setTelephone(customerDto.getTelephone());
		newCustInfoDTO.setDtCreate(TimestampUtility.getCurrentTimestamp());
		newCustInfoDTO.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		//oldCustInfoDTO.setTimeRangeEnd(customer.gett);
		//oldCustInfoDTO.setTimeRangeStart();
		newCustInfoDTO.setType(customerDto.getCustomerType());
		newCustInfoDTO.setComments(customerDto.getComments());
		newCustInfoDTO.setBirthday(customerDto.getBirthday());
		newCustInfoDTO.setLoginID(customerDto.getLoginID());
		newCustInfoDTO.setLoginPWD(customerDto.getLoginPwd());
		if (bakAddress !=null)
		   customerDto.setAddressID(bakAddress.getAddressID().intValue());
		return newCustInfoDTO;
    }
    
    
    /**
     * ͨ�����context�õ�csiid
     * @return
     */
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
	 * �����¿ͻ��г���Ϣ
	 * @param newCustMarketInfoList
	 * @param customerID
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void updateNewCustomerMarketInfo(Collection  custMarketInfoList,int customerID,int csiID)throws ServiceException {
		if(custMarketInfoList==null || custMarketInfoList.isEmpty())
			return;
		
		try{
			//����ɵ���Ϣ
			CustMarketInfoHome custMarketInfoHome = HomeLocater.getCustMarketInfoHome();
			OldCustomerMarketInfoHome oldCustomerMarketInfoHome =HomeLocater.getOldCustomerMarketInfoHome();
			NewCustomerMarketInfoHome newCustomerMarketInfoHome =HomeLocater.getNewCustomerMarketInfoHome();
			Collection oldCustomerMarketInfoList=custMarketInfoHome.findByCustomerId(customerID);
			if(oldCustomerMarketInfoList!=null && !oldCustomerMarketInfoList.isEmpty()){
				Iterator oldNewCustomerMarketInfoIter=oldCustomerMarketInfoList.iterator();
				while(oldNewCustomerMarketInfoIter.hasNext()){
					CustMarketInfo custMarketInfo=(CustMarketInfo)oldNewCustomerMarketInfoIter.next();
					OldCustomerMarketInfoDTO  oldCustMarketInfoDto = getOldCustomerMarketInfoFromCustMarketInfo(custMarketInfo,csiID);
					oldCustomerMarketInfoHome.create(oldCustMarketInfoDto);
					custMarketInfo.remove();
				}
			}
			
			//�����µ���Ϣ
			Iterator itemarketinfo = custMarketInfoList.iterator();
			while(itemarketinfo.hasNext()) {
				CustMarketInfoDTO marketDto = (CustMarketInfoDTO)itemarketinfo.next();
				marketDto.setCustomerId(customerID);
				NewCustomerMarketInfoDTO newCustMarketInfoDto =getNewCustomerMarketInfoFromCustMarketInfo(marketDto,csiID);
				newCustomerMarketInfoHome.create(newCustMarketInfoDto);
				custMarketInfoHome.create(marketDto);
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
		}
		catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("�����¿ͻ��г���Ϣ����");
		}
	}
	
	private OldCustomerMarketInfoDTO getOldCustomerMarketInfoFromCustMarketInfo(CustMarketInfo custMarketInfo,int csiID){
		OldCustomerMarketInfoDTO oldCustMarketInfoDto =new OldCustomerMarketInfoDTO();
		oldCustMarketInfoDto.setCsiID(csiID);
		oldCustMarketInfoDto.setCustomerid(custMarketInfo.getCustomerId());
		oldCustMarketInfoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		oldCustMarketInfoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		oldCustMarketInfoDto.setInfoSettingID(custMarketInfo.getInfoSettingId());
		oldCustMarketInfoDto.setInfoValueID(custMarketInfo.getInfoValueId());
		oldCustMarketInfoDto.setMemo(custMarketInfo.getMemo());
		return oldCustMarketInfoDto;
	}
	
	private NewCustomerMarketInfoDTO getNewCustomerMarketInfoFromCustMarketInfo(CustMarketInfoDTO custMarketInfoDto,int csiID){
		NewCustomerMarketInfoDTO newCustMarketInfoDto =new NewCustomerMarketInfoDTO();
		newCustMarketInfoDto.setCsiID(csiID);
		newCustMarketInfoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		newCustMarketInfoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		newCustMarketInfoDto.setInfoSettingId(custMarketInfoDto.getInfoSettingId());
		newCustMarketInfoDto.setInfoValueId(custMarketInfoDto.getInfoValueId());
		newCustMarketInfoDto.setMemo(custMarketInfoDto.getMemo());
		newCustMarketInfoDto.setNewCustomerId(custMarketInfoDto.getCustomerId());
		return newCustMarketInfoDto;
	}
	
	
    public void setObjectRelation(ServiceContext sc) {
        Address custAddr = (Address)context.get(Service.CUSTOMER_ADDRESS_EJB);
        Customer customer = (Customer)context.get(Service.CUSTOMER);
        customer.setAddressID(custAddr.getAddressID().intValue());
    }
    
    /**
	 * ����ָ���Ŀͻ�id���г�������Ϣid�޸Ķ�Ӧ����Ϣ
	 * @param customerId
	 * @param infoSettingID
	 * @param value
	 * @param isAddToOld
	 * @throws ServiceException
	 */
	public void updateSpecifyMarketInfoByCustId(int customerId,int infoSettingID,String value,boolean isAddToOld)throws ServiceException{
		try{
			//����ָ������Ϣ
			CustMarketInfoHome custMarketInfoHome = HomeLocater.getCustMarketInfoHome();
			Collection customerMarketInfoList=custMarketInfoHome.findByCustomerId(customerId);
			if(customerMarketInfoList!=null && !customerMarketInfoList.isEmpty()){
				Iterator customerMarketInfoIter=customerMarketInfoList.iterator();
				while(customerMarketInfoIter.hasNext()){
					CustMarketInfo custMarketInfo=(CustMarketInfo)customerMarketInfoIter.next();
					if(custMarketInfo.getInfoSettingId()==infoSettingID){
						if(custMarketInfo.getInfoValueId()!=0){
							if(isAddToOld)
								custMarketInfo.setInfoValueId(custMarketInfo.getInfoValueId()+Integer.valueOf(value).intValue());
							else
								custMarketInfo.setInfoValueId(Integer.valueOf(value).intValue());
						}else if(custMarketInfo.getMemo()!=null && !"".equals(custMarketInfo.getMemo())){
							if(isAddToOld)
								custMarketInfo.setMemo(custMarketInfo.getMemo()+"  "+value);
							else
								custMarketInfo.setMemo(value);
						}
					}
				}
			}
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("�����¿ͻ��г���Ϣʱ��λ����");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("�����¿ͻ��г���Ϣ����");
    	}
	}
    
}
