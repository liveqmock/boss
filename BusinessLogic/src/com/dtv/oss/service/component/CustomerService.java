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
 * 处理与客户有关的业务逻辑
 */
public class CustomerService extends AbstractService {
    private static final Class clazz = CustomerService.class;
    private ServiceContext context;
    
    /**
     * @param context：上下文对象
     */
    public CustomerService(ServiceContext context) {
        this.context = context;
        setOperatorID(PublicService.getCurrentOperatorID(context));
    }
    /**
     * 根据客户id封装客户信息
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
		    throw new ServiceException("查找账户信息时定位错误");
		}catch(FinderException e) {
		    LogUtility.log(clazz, LogLevel.ERROR,"create", e);
		    throw new ServiceException("查找账户信息时错误");		    
		}
    }
    /**
     * 创建客户
     * @param custDto
     * @param marketInfoList
     * @throws ServiceException
     */
    public void create(CustomerDTO custDto,Collection marketInfoList) throws ServiceException{
    	create(custDto,null,marketInfoList);
    }

  
   
    /**
     * 创建客户信息和与客户相关的市场信息
     * @param custDto
     * @param marketInfoList
     * @throws ServiceException
     */
    public void create(CustomerDTO custDto, AddressDTO addressDto,Collection marketInfoList)throws ServiceException {
    	
    	LogUtility.log(clazz,LogLevel.DEBUG,"进入创建客户对象程序");
    	LogUtility.log(clazz,LogLevel.DEBUG,"传入的参数：CustomerDTO["+custDto+"]、AddressDTO["+addressDto+"]、" +
    			"市场信息[" + marketInfoList + "]");
    	
    	int orgID=0;
    	int addressID=0;
    	try {
    		if(addressDto!=null){
    			//创建地址
    			LogUtility.log(clazz,LogLevel.DEBUG,"创建地址");
    			AddressHome addressHome=HomeLocater.getAddressHome();
    			Address address=addressHome.create(addressDto);
    			orgID=BusinessUtility.getParentHasCustomerOrgID(
    					BusinessUtility.getOpOrgIDByOperatorID(((Integer)this.context.get(Service.OPERATIOR_ID)).intValue()));
    			
    			//orgID=BusinessUtility.getOrgIDByDistrictID(address.getDistrictID());
    			addressID=address.getAddressID().intValue();
    			addressDto.setAddressID(addressID);
    			
    			//把客户的地址ID放入ServiceContext
    			context.put(Service.CUSTOMER_ADDRESS_ID,address.getAddressID());
    			
    			LogUtility.log(clazz,LogLevel.DEBUG,"创建新客户地址ID=" + addressID);
    		}
    		
    		//创建客户信息
    		LogUtility.log(clazz,LogLevel.DEBUG,"创建客户");
    		custDto.setOrgID(orgID);
    		custDto.setAddressID(addressID);
    		
            CustomerHome custHome = HomeLocater.getCustomerHome();
            Customer customer = custHome.create(custDto);
            
            custDto.setCustomerID(customer.getCustomerID().intValue());
            
            //回执信息
            context.put(Service.CUSTOMER,customer);
            context.put(Service.CUSTOMER_ID,customer.getCustomerID());
            LogUtility.log(clazz,LogLevel.DEBUG,"创建新客户ID=" + customer.getCustomerID());
            LogUtility.log(clazz,LogLevel.DEBUG,"创建新客户信息=" + custDto);
            
            //创建市场信息
            LogUtility.log(clazz,LogLevel.DEBUG,"创建市场信息");
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
                    LogUtility.log(clazz,LogLevel.DEBUG,"在创建一个市场信息时检查DTO，DTO内容为为："+dto);
                    marketHome.create(dto);
                }
            }
            
            //取得受理单本地对象
            CustServiceInteraction csi = (CustServiceInteraction)context.get(Service.CSI);
            int csiID=0;
            if(csi!=null){
            	csi.setCustomerID(customer.getCustomerID().intValue());
            	csiID=csi.getId().intValue();
            }
            //创建系统事件
            LogUtility.log(clazz,LogLevel.DEBUG,"创建系统事件");
            SystemEventRecorder.AddEvent4Customer(SystemEventRecorder.SE_CUST_OPEN, customer.getCustomerID().intValue(),
					0, 0, csiID, PublicService.getCurrentOperatorID(context), SystemEventRecorder.SE_STATUS_CREATE);
        }
    	catch(HomeFactoryException e) {
            LogUtility.log(clazz,LogLevel.ERROR, e);
            throw new ServiceException("无法找到entity customer 的home 接口");
        }catch(CreateException e) {
            LogUtility.log(clazz,LogLevel.ERROR, e);
            throw new ServiceException("无法创建客户对象");
        }
        LogUtility.log(clazz,LogLevel.DEBUG,"结束创建客户对象程序");
    }

    /**
     * 创建客户信息和与客户相关的市场信息
     * @param custDto
     * @param marketInfoList
     * @PARAM isCreateBak:是否同时创建用户备份信息
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
	         throw new ServiceException("查找客户备份信息接口出错");
    	 } catch(CreateException e){
	     	 LogUtility.log(clazz,LogLevel.ERROR, e);
	         throw new ServiceException("创建客户备份信息出错");
	     }
     }
    
    /**
     * 创建客户
     * @param nciDto
     * @param marketInfoList
     * @throws ServiceException
     */
    public void create(NewCustomerInfoDTO nciDto,Collection marketInfoList) throws ServiceException{
    	create(nciDto,null,marketInfoList);
    }
    
    /**
     * 创建客户
     * @param nciDto
     * @param marketInfoList
     * @throws ServiceException
     */
    public void create(NewCustomerInfoDTO nciDto, AddressDTO addressDto,Collection marketInfoList) throws ServiceException {
    	//受理单类型和安装类型
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
        //只有门店开户并且属于自安装，用户的状态才是潜在，其他为正常状态----->所有开户的状态都为潜在modify 2005-12-15
		//if (CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(csiType)&&
		//	CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL.equals(installationType))
		custDto.setStatus(CommonConstDefinition.CUSTOMER_STATUS_POTENTIAL);
		//else
		//	custDto.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
		//侯06-11-17修改集团客户初始状态为正常
		if(CommonConstDefinition.CUSTOMERSTYLE_GROUP.equals(nciDto.getCustStyle()))
			custDto.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
		//如果是集团客户子客户开户
    	if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS.equals(csiType)){
    		custDto.setGroupCustID(nciDto.getGroupCustID());
    	}
    	
        custDto.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        
        create(custDto,addressDto,marketInfoList);  
    }

    /**
     * 根据actionType更新客户信息
     * ationType=EJBEvent.REGISTER_INSTALLATION_INFO、
     * @param csiID
     * @param actionType
     * @throws ServiceException
     */
    public void updateCustomer(int csiID,int actionType) throws ServiceException{
    	if(csiID==0||actionType==0)
    		throw new ServiceException("更新客户资料参数错误!");
    	
    	try{
    		CustServiceInteractionHome csiHome=HomeLocater.getCustServiceInteractionHome();
    		CustServiceInteraction csi=csiHome.findByPrimaryKey(new Integer(csiID));
    		int customerID=csi.getCustomerID();
    		CustomerHome customerHome=HomeLocater.getCustomerHome();
    		Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
    		
    		//registerInstallationInfo(录入安装信息),如果成功,修改客户状态为正常
    		if(actionType==EJBEvent.REGISTER_INSTALLATION_INFO){
    			customer.setStatus(CommonConstDefinition.CUSTOMER_STATUS_NORMAL);
    		} 
    	}
    	catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN,"更改客户信息出错，原因：客户定位出错！");
    		throw new ServiceException("更改客户信息出错，原因：客户定位出错！");
		}
		catch(FinderException e1){
			LogUtility.log(clazz,LogLevel.WARN,"更改客户信息出错，原因：客户查找出错！");
    		throw new ServiceException("更改客户信息出错，原因：客户查找出错！");
		}
    }
    /**
     * 更新客户信息,添加了备份处理,对旧客户信息备份,对新客户信息备份.
     * 侯添加,目前此方法只适用于集团客户转个人客户
     * @param customerDto 客户DTO
     * @param addressDto 地址DTO
     * @param custMarketInfoList 市场信息
     * @param isCreateBak 是否创建备份
     * @throws ServiceException
     */
    public void updateCustomer(CustomerDTO customerDto, AddressDTO addressDto,
			Collection custMarketInfoList, boolean isCreateBak)
			throws ServiceException {
		if (isCreateBak) {
			//备份新/旧客户信息
			if (addressDto !=null) 
                customerDto.setAddressID(addressDto.getAddressID());
			createCustAndAcctBackupInfo(customerDto);
             //	更新客户信息
			updateCustomer(customerDto, addressDto, custMarketInfoList);
		} else {
			updateCustomer(customerDto, addressDto, custMarketInfoList);
		}
	}
    /**
     *  修改客户信息：如果addressDto为空，则只修改Customer信息，
     *  如果addressDto不为空，customerDto为空或customerDto.getCustomerID=0，则抛出异常。
     * @param customerDto
     * @param addressDto
     * @param custMarketInfoList:客户市场信息
     * @throws ServiceException 
     */    		
    public void updateCustomer(CustomerDTO customerDto,AddressDTO addressDto,Collection custMarketInfoList)
    		throws ServiceException{
    	
    	LogUtility.log(clazz,LogLevel.DEBUG,"进入更新客户信息：");
    	
    	if(customerDto==null||customerDto.getCustomerID()==0){
    		throw new ServiceException("参数错误：参数都为空！");
    	}

    	try{
    		LogUtility.log(clazz,LogLevel.DEBUG,"更新客户信息，传入的DTO为：" +customerDto);
    		CustomerHome custHome=HomeLocater.getCustomerHome();
    		Customer customer=custHome.findByPrimaryKey(new Integer(customerDto.getCustomerID()));
    		
    		//修改客户信息	
    		if(BusinessUtility.isExitMultiLoginID(customerDto.getCustomerID(),customerDto.getLoginID())){
    			throw new ServiceException("请更换客户登陆ID，该ID号已经存在！");
    		}
    		if(customer.ejbUpdate(customerDto)==-1){
    			LogUtility.log(clazz,LogLevel.WARN,"更新客户信息出错，原因：ejbUpdate(customerDto)==-1");
	    		throw new ServiceException("更新客户信息出错！");
    		}
    		
    		//修改地址信息
    		if(addressDto!=null){
    			LogUtility.log(clazz,LogLevel.DEBUG,"更新客户地址信息，传入的DTO为：" +addressDto);
                
    			addressDto.setAddressID(customerDto.getAddressID());
    			AddressHome addHome=HomeLocater.getAddressHome();
    			Address add=addHome.findByPrimaryKey(new Integer(addressDto.getAddressID()));;
    			if(add.ejbUpdate(addressDto)==-1){
    				LogUtility.log(clazz,LogLevel.WARN,"更新客户地址信息出错，原因：ejbUpdate(addressDto)==-1");
    	    		throw new ServiceException("更新客户地址信息出错！");
    			}
        		context.put(Service.CUSTOMER_ADDRESS_EJB, add);
    		}
    		    		
    		//修改客户市场信息
    		int csiID =((Integer) context.get(Service.CSI_ID)).intValue();
    		if(custMarketInfoList!=null && custMarketInfoList.isEmpty()==false){
    			LogUtility.log(clazz,LogLevel.DEBUG,"更新客户市场信息，传入的list为：" +custMarketInfoList);
    			updateNewCustomerMarketInfo(custMarketInfoList,customerDto.getCustomerID(),csiID);
    		}
    		
    		//侯12-06增加,用于集团转个人的时候,创建帐户,
    		context.put(Service.CUSTOMER, customer);

    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"更改客户信息出错，原因：客户定位出错！");
    		throw new ServiceException("更改客户信息出错，原因：客户定位出错！");
    		
    	}
    	catch(FinderException e){
    		LogUtility.log(clazz,LogLevel.WARN,"更改客户信息出错，原因：客户查找出错！");
    		throw new ServiceException("更改客户信息出错，原因：客户查找出错");
    	}    	
    	LogUtility.log(clazz,LogLevel.DEBUG,"结束更新客户信息！");
    }
    
    
    
   /**
    * 原地过户、异地过户
    * @param actionType: 操作类型:原地过户、异地过户，来自EJBEvent
    * @param customerDTO : 新客户信息
    * @param custMarketInfoList : 新客户的市场信息
    * @param custAddrDTO : 新客户的地址信息
 * @throws ServiceException 
    */
    public void transferCustomer(int actionType,CustomerDTO customerDTO,
    		Collection custMarketInfoList,AddressDTO custAddrDTO) throws ServiceException{
    	try{ 
    		
    	
    	if(actionType!=EJBEvent.TRANSFERTODIFFERENTPLACE && actionType!=EJBEvent.TRANSFERTOSAMEPLACE){
    		LogUtility.log(clazz,LogLevel.WARN, "过户动作类型未知，目前只支持原地过户和异地过户。");
    		throw new ServiceException("目前只支持原地过户和异地过户。");
    	}
    	if(customerDTO==null || customerDTO.getCustomerID()==0){
    		LogUtility.log(clazz,LogLevel.WARN, "过户参数不完整。");
    		throw new ServiceException("过户参数不完整。");
    	}

		CustomerDTO oldCustomerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerDTO.getCustomerID());
		AddressDTO oldaddrDto=BusinessUtility.getAddressDTOByAddressID(oldCustomerDTO.getAddressID());
		//判断是否为异地过户，异地过户创建地址
		if(actionType==EJBEvent.TRANSFERTODIFFERENTPLACE){
			
			updateCustomer(customerDTO,custAddrDTO,custMarketInfoList);	
		}else{
			updateCustomer(customerDTO,null,custMarketInfoList);	
		}
        SystemEventRecorder.AddEvent4Customer(SystemEventRecorder.SE_CUST_TRANSFER, customerDTO.getCustomerID(),
				0, 0, ((Integer)context.get(Service.CSI_ID)).intValue(), getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);
		
		//系统日志记录
		String log="";
		StringBuffer logDes=new StringBuffer();
		if (actionType == EJBEvent.TRANSFERTODIFFERENTPLACE) {
			log = "客户异地过户";
			logDes.append(log);
			logDes.append(",受理单ID:").append(context.get(Service.CSI_ID)).append(
					",");
			logDes.append(SystemLogRecorder.appendDescString(";客户姓名:",
					oldCustomerDTO.getName(), customerDTO.getName()));

			logDes.append(SystemLogRecorder.appendDescString(";客户区域:",
					BusinessUtility.getDistrictNameById(oldaddrDto
							.getDistrictID()), BusinessUtility
							.getDistrictNameById(custAddrDTO.getDistrictID())));
			logDes.append(SystemLogRecorder.appendDescString(";邮编:", oldaddrDto
					.getPostcode(), custAddrDTO.getPostcode()));
			logDes.append(SystemLogRecorder.appendDescString(";详细地址:",
					oldaddrDto.getDetailAddress(), custAddrDTO
							.getDetailAddress()));
		} else {
			log = "客户原址过户";
			logDes.append(log);
			logDes.append(",受理单ID:").append(context.get(Service.CSI_ID)).append(
					",");
			logDes.append(SystemLogRecorder.appendDescString(";客户姓名:",
					oldCustomerDTO.getName(), customerDTO.getName()));
			logDes.append(SystemLogRecorder.appendDescString(";证件类型:",
					BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERCARDTYPE",oldCustomerDTO.getCardType()),
					BusinessUtility.getCommonNameByKey("SET_C_CUSTOMERCARDTYPE", customerDTO.getCardType())));
			logDes.append(SystemLogRecorder.appendDescString(";证件号码:",
					oldCustomerDTO.getCardID(), customerDTO.getCardID()));
		}
		
		SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
    			PublicService.getCurrentOperatorID(context), customerDTO.getCustomerID(), 
				SystemLogRecorder.LOGMODULE_CUSTSERV, log, logDes.toString(), 
				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
    	}catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("客户过户定位出错！");
    	}catch(CreateException e2){
    		LogUtility.log(clazz,LogLevel.WARN,e2);
    		throw new ServiceException("创建客户过户系统事件出错！");
    	}
    }
    
    /**
     * 迁移
     * @param customerDTO
     * @param custAddrDTO
     * @throws ServiceException
     */
    public void  moveCustomer(CustomerDTO customerDTO,AddressDTO custAddrDTO) throws ServiceException{
    	
    	if(customerDTO==null||customerDTO.getCustomerID()==0){
    		LogUtility.log(clazz,LogLevel.WARN, "迁移参数不完整。");
    		throw new ServiceException("迁移参数不完整。");
    	}
    	//取得老客户信息
    	CustomerDTO oldCustomerDTO=BusinessUtility.getCustomerDTOByCustomerID(customerDTO.getCustomerID());
    	AddressDTO oldaddrDto=BusinessUtility.getAddressDTOByAddressID(oldCustomerDTO.getAddressID());

    	//用参数中的客户信息更新老客户记录
    	updateCustomer(customerDTO,null,null);
    		
    	//系统日志记录
    	String log = "客户迁移";
		StringBuffer logDes = new StringBuffer();
		logDes.append(log).append(",");
		logDes.append("受理单ID:").append(context.get(Service.CSI_ID)).append(
					",");
		logDes.append("客户姓名:").append(oldCustomerDTO.getName());
		logDes.append(SystemLogRecorder.appendDescString(";客户区域:",
					BusinessUtility.getDistrictNameById(oldaddrDto
							.getDistrictID()), BusinessUtility
							.getDistrictNameById(custAddrDTO.getDistrictID())));
		logDes.append(SystemLogRecorder.appendDescString(";邮编:", oldaddrDto
					.getPostcode(), custAddrDTO.getPostcode())
					+ SystemLogRecorder
							.appendDescString(";详细地址:", oldaddrDto
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
     * 客户销户
     * @param customerID：客户ID
     * @throws ServiceException
     */
    public void closeCustomer(int customerID)throws ServiceException{
    	
    	//客户销户 的时候,如果关联到客户硬件产品的取消,需要在日志记录中记录设备的序列号
    	//取得设备的序列号
        String logDeviceSerialNoDesc="";
        Collection deviceIDList = BusinessUtility.getDeviceIDListByCustInfo(customerID);
        Iterator itDeviceIDList = deviceIDList.iterator();
        int i = 0;
    	while(itDeviceIDList.hasNext()){
    		Integer deviceID=(Integer)itDeviceIDList.next();
    		LogUtility.log(clazz,LogLevel.DEBUG,"客户销户涉及的设备号:"+deviceID);
    		if(deviceID.intValue()>0){
    			TerminalDeviceDTO DeviceDTO = BusinessUtility.getDeviceByDeviceID(deviceID.intValue());
    			String SerialNo = DeviceDTO.getSerialNo();
    			LogUtility.log(clazz,LogLevel.DEBUG,"客户销户涉及的设备序列号:"+SerialNo);
    			logDeviceSerialNoDesc = logDeviceSerialNoDesc +"("+(++i)+")"+ SerialNo + ",";
    		}
    	}
    	if(!"".equals(logDeviceSerialNoDesc))
    		logDeviceSerialNoDesc=";涉及的设备序列号:" + logDeviceSerialNoDesc;
    	
    	//维护设备配对关系 2007-12-19
    	CustomerProductService customerProductService = new CustomerProductService(this.context);
    	customerProductService.unchainDeviceMatch(deviceIDList);
    	closeCustOnly(customerID,false);
    	
    	try{        	
        	//创建系统事件
        	LogUtility.log(clazz,LogLevel.DEBUG,"客户销户创建系统事件");
            int csiID=0;
            if(this.context.get(Service.CSI_ID)!=null){
            	csiID=((Integer)this.context.get(Service.CSI_ID)).intValue();
            }
            SystemEventRecorder.AddEvent4Customer(SystemEventRecorder.SE_CUST_CANCEL, customerID,
					0, 0, csiID, getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);
            
        	//创建系统日志
        	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(this.context), 
        			PublicService.getCurrentOperatorID(this.context), customerID, 
    				SystemLogRecorder.LOGMODULE_CUSTSERV, "客户销户"," 客户销户,受理单ID:"+context.get(Service.CSI_ID)+";客户ID ："+customerID+logDeviceSerialNoDesc, 
    				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
        	
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"客户销户定位出错:"+e);
    		throw new ServiceException("客户销户定位出错！");
    	}
    	catch(CreateException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"创建系统日志出错:"+e2);
    		throw new ServiceException("创建系统日志出错！");
    	}
    	
    }
    
    /**
     * 客户退户
     * @param customerID
     * @param deviceList：退还的设备列表,封装格式为Integer对象
     * @throws ServiceException
     */
    public void withdrawCustomer(int customerID,Collection deviceList)throws ServiceException{
    	
    	//客户退户 的时候,如果关联到客户硬件产品的取消,需要在日志记录中记录设备的序列号
    	//取得设备的序列号
        String logDeviceSerialNoDesc="";
        Collection deviceIDList = BusinessUtility.getDeviceIDListByCustInfo(customerID);
        Iterator itDeviceIDList = deviceIDList.iterator();
        int i = 0;
        String SerialNo = "";
        Integer deviceID;
        TerminalDeviceDTO DeviceDTO;
    	while(itDeviceIDList.hasNext()){
    		deviceID=(Integer)itDeviceIDList.next();
    		LogUtility.log(clazz,LogLevel.DEBUG,"客户退户涉及的设备号:"+deviceID);
    		if(deviceID.intValue()>0){
    			DeviceDTO = (TerminalDeviceDTO)BusinessUtility.getDeviceByDeviceID(deviceID.intValue());
    			SerialNo = DeviceDTO.getSerialNo();
    			LogUtility.log(clazz,LogLevel.DEBUG,"客户退户涉及的设备序列号:"+SerialNo);
    			logDeviceSerialNoDesc = logDeviceSerialNoDesc +"("+(++i)+")"+ SerialNo + ",";
    		}
    	}
    	if(!"".equals(logDeviceSerialNoDesc))
    		logDeviceSerialNoDesc=";涉及的设备序列号:" + logDeviceSerialNoDesc;
    	
    	//维护设备配对关系 2007-12-19
    	CustomerProductService customerProductService = new CustomerProductService(this.context);
    	customerProductService.unchainDeviceMatch(deviceIDList);
    	closeCustOnly(customerID,true);
    	
    	try{        	
        	//创建系统事件
        	LogUtility.log(clazz,LogLevel.DEBUG,"客户退户创建系统事件");
            int csiID=0;
            if(this.context.get(Service.CSI_ID)!=null){
            	csiID=((Integer)this.context.get(Service.CSI_ID)).intValue();
            }
            SystemEventRecorder.AddEvent4Customer(SystemEventRecorder.SE_CUST_WITHDRWAN, customerID,
					0, 0, csiID, getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);
            
        	//创建系统日志
        	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(this.context), 
        			PublicService.getCurrentOperatorID(this.context), customerID, 
    				SystemLogRecorder.LOGMODULE_CUSTSERV, "客户退户"," 客户退户,受理单ID:"+context.get(Service.CSI_ID)+";客户ID ："+customerID+logDeviceSerialNoDesc, 
    				SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
        	
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"客户退户定位出错:"+e);
    		throw new ServiceException("客户退户定位出错！");
    	}
    	catch(CreateException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"创建系统日志出错:"+e2);
    		throw new ServiceException("创建系统日志出错！");
    	}
    }
    
    public void returnFeeForCustomer(CustServiceInteractionDTO custServiceInteractionDTO)throws ServiceException{
    	try{
	    	if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS.equals(custServiceInteractionDTO.getType())||
	    	   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB.equals(custServiceInteractionDTO.getType())||
	    	   CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(custServiceInteractionDTO.getType())){
	    		closeCustOnly(custServiceInteractionDTO.getCustomerID(),true);
	    		
	        		LogUtility.log(clazz,LogLevel.DEBUG,"安装不成功退费系统事件");
	        		int csiID =0;
	        		if (this.context.get(Service.CSI_ID)!=null){
	        			csiID =((Integer)this.context.get(Service.CSI_ID)).intValue();
	        		}
	        		//zyg 2007.10.23 begin
	        		//空的事件号，没有意义
	        		/*****************************************
	        		//事件号不知道，待商议
	        		SystemEventRecorder.AddEvent4Customer(0, custServiceInteractionDTO.getCustomerID(),
	    					0, 0, csiID, getOperatorID(), SystemEventRecorder.SE_STATUS_CREATE);
	            /*****************************************/   
	            //zyg 2007.10.23 end
	    	}
	    	//zyg 2007.10.23 begin 
	    	//更改为 只有门店增机 和 预约增机 才走如下路径，避免 模拟业务相关工单安装不成功退费 走到如下路径，从而错误地取消业务帐户
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
    		LogUtility.log(clazz,LogLevel.WARN,"安装不成功退费定位出错:"+e);
    		throw new ServiceException("安装不成功退费定位出错！");
    	}
    	catch(CreateException e2){
    		LogUtility.log(clazz,LogLevel.WARN,"创建系统日志出错:"+e2);
    		throw new ServiceException("创建系统日志出错！");
    	}
    	/***********************************************/
    	/***********************************************/
    	catch(Exception e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"安装不成功退费过程中出错：",e);
		    throw new ServiceException("安装不成功退费过程中出错！");
		}
		/***********************************************/
		//zyg 2007.10.23 end
		
    }
    
    
    
    
    /**
     * 客户销户/退户/安装不成功退费/预约开户结束安装工单
     * @param customerID
     * @throws ServiceException
     */
    public void closeCustOnly(int customerID,boolean returnDevice)throws ServiceException{
    	if(customerID==0)
    		throw new ServiceException("参数错误!");

    	context.put(Service.CUSTOMER_ID,new Integer(customerID));
    	try{
    		//得到业务帐户列表
        	ServiceAccountHome saHome=HomeLocater.getServiceAccountHome();
        	ServiceAccount sa=null; 	
        	Collection saList=BusinessUtility.getCurrentServiceAccountByCustomerID(customerID,null);
        	//处理saList列表
        	ServiceAccountService saService=new ServiceAccountService(this.context);
        	Iterator itSA=saList.iterator();
        	while(itSA.hasNext()){
        		Integer saID=(Integer)itSA.next();
        		sa=saHome.findByPrimaryKey(saID);
        		//得到客户不是取消状态的业务帐户
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
        	
        	//处理客户记录
        	CustomerHome customerHome = HomeLocater.getCustomerHome();
        	Customer customer=customerHome.findByPrimaryKey(new Integer(customerID));
        	customer.setStatus(CommonConstDefinition.CUSTOMER_STATUS_CANCEL);
        	customer.setDtLastmod(TimestampUtility.getCurrentTimestamp());
        	
    	}
    	catch(HomeFactoryException e){
    		LogUtility.log(clazz,LogLevel.WARN,"业务帐户定位出错:"+e);
    		throw new ServiceException("业务帐户定位出错！");
    	}
    	catch(FinderException e1){
    		LogUtility.log(clazz,LogLevel.WARN,"业务帐户查找出错:"+e1);
    		throw new ServiceException("业务帐户查找出错！");
    	}
    }
    
    
    /**
     * 通过Customer得到用户的信息，主要用于过户、迁移时修改用户信息，而又必须保存老的客户信息于T_NEWCUSTOMERINFO
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
    		LogUtility.log(clazz,LogLevel.WARN,"客户定位出错:"+e);
    		throw new ServiceException("客户定位出错！");
    	}
    	catch(FinderException e1){
    		LogUtility.log(clazz,LogLevel.WARN,"客户查找出错:"+e1);
    		throw new ServiceException("客户查找出错！");
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
	    		LogUtility.log(clazz,LogLevel.WARN,"客户定位出错:"+e);
	    		throw new ServiceException("客户定位出错！");
	    	}
	    	catch(FinderException e1){
	    		LogUtility.log(clazz,LogLevel.WARN,"客户查找出错:"+e1);
	    		throw new ServiceException("客户查找出错！");
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
     * 通过检查context得到csiid
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
	 * 更新新客户市场信息
	 * @param newCustMarketInfoList
	 * @param customerID
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void updateNewCustomerMarketInfo(Collection  custMarketInfoList,int customerID,int csiID)throws ServiceException {
		if(custMarketInfoList==null || custMarketInfoList.isEmpty())
			return;
		
		try{
			//清除旧的信息
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
			
			//插入新的信息
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
		    throw new ServiceException("更新新客户市场信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("查找新客户市场信息错误");
		}catch(RemoveException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("删除新客户市场信息错误");
		}
		catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("创建新客户市场信息错误");
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
	 * 根据指定的客户id和市场配置信息id修改对应的信息
	 * @param customerId
	 * @param infoSettingID
	 * @param value
	 * @param isAddToOld
	 * @throws ServiceException
	 */
	public void updateSpecifyMarketInfoByCustId(int customerId,int infoSettingID,String value,boolean isAddToOld)throws ServiceException{
		try{
			//更新指定的信息
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
		    throw new ServiceException("更新新客户市场信息时定位错误");	            		
    	}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createCustomerBackupInfo",e);
		    throw new ServiceException("查找新客户市场信息错误");
    	}
	}
    
}
