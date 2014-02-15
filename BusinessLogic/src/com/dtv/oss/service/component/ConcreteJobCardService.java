/**
 * 
 */
package com.dtv.oss.service.component;

import javax.ejb.CreateException;

import com.dtv.oss.domain.Address;
import com.dtv.oss.domain.AddressHome;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.TimestampUtility;

/**
 * @author 240910y
 *
 */
public class ConcreteJobCardService extends JobCardService {
    private static final Class clazz = ConcreteJobCardService.class;
    
    public ConcreteJobCardService(ServiceContext context) {
        super(context);
    }
    
    public ConcreteJobCardService(int jobCardNo, ServiceContext context) {
        super(jobCardNo, context);
    }
    
  //实际完成创建工单的方法
    public void createJobCard(JobCardDTO jcDto,AddressDTO addressDTO,AddressDTO oldAddressDTO) throws ServiceException {
    	createAddress(addressDTO);
    	if(oldAddressDTO!=null){
    		createAddress(oldAddressDTO);
    		jcDto.setOldAddressId(oldAddressDTO.getAddressID());
    	}
    	jcDto.setCreateOpID(operatorID);
    	jcDto.setCreateOrgID(BusinessUtility.getOpOrgIDByOperatorID(operatorID));
    	jcDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
    	jcDto.setCreateMethod(CommonConstDefinition.JOBCARDCREATEMETHOD_M);
    	jcDto.setPayStatus(CommonConstDefinition.JOBCARDPAYSTATUS_W);
    	jcDto.setAddressId(addressDTO.getAddressID());
    	createJobCard(jcDto,jcDto.getProcessOrgId());
    } 
          
	/**
	 * 创建客户/客户账户地址信息
	 * 
	 * @param serviceContext
	 * @throws ServiceException
	 */
	private void createAddress(AddressDTO addrDto)throws ServiceException {
		try{
			AddressHome addrHome = HomeLocater.getAddressHome();
	    	LogUtility.log(CSIService.class,LogLevel.DEBUG,"创建工单地址",addrDto);
	    	Address custAddr = addrHome.create(addrDto);
	    	addrDto.setAddressID(custAddr.getAddressID().intValue());
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createAddress",e);
		    throw new ServiceException("创建工单地址定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"createAddress",e);
		    throw new ServiceException("创建工单地址信息错误");
		}
	}
	public void udatePayStatus()throws ServiceException {
		load();
    	jc.setPayStatus(CommonConstDefinition.JOBCARDPAYSTATUS_D); 
    	jc.setPayDate(TimestampUtility.getCurrentTimestamp());
	}
	
	/* (non-Javadoc)
	 * @see com.dtv.oss.service.component.JobCardService#getOriginalSheet()
	 */
	public Object getOriginalSheet() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.component.JobCardService#relateWithOriginalSheet(java.lang.Object)
	 */
	public void relateWithOriginalSheet(Object cps) throws ServiceException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.component.JobCardService#setJobCardType(com.dtv.oss.dto.JobCardDTO)
	 */
	public void setJobCardType(JobCardDTO jcDto) throws ServiceException {
		// TODO Auto-generated method stub
	}

}
