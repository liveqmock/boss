package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.CpConfigedProperty;
import com.dtv.oss.domain.CpConfigedPropertyHome;
import com.dtv.oss.domain.CpConfigedPropertyPK;
import com.dtv.oss.domain.ProductProperty;
import com.dtv.oss.domain.ProductPropertyHome;
import com.dtv.oss.domain.ProductPropertyPK;
import com.dtv.oss.dto.CpConfigedPropertyDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class CPConfigedPropertyService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=CPConfigedPropertyService.class;
	
	public CPConfigedPropertyService(ServiceContext s){
		this.context=s;
	}
	
	public void propertyValueAdd(CpConfigedPropertyDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建产品属性!");
			return;
		}
//		CpConfigedPropertyPK
		
		CpConfigedPropertyHome pHome=null;
		CpConfigedProperty p=null;
		try{
			pHome= HomeLocater.getCpConfigedPropertyHome();
			p=(CpConfigedProperty) pHome.create(dto);
			
			CustomerProductDTO custproductDto=BusinessUtility.getCustomerProductDTOByPsID(dto.getPsID());
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), custproductDto.getCustomerID(), 
	    			custproductDto.getAccountID(),
	    			custproductDto.getServiceAccountID(),
	    			custproductDto.getPsID(),
					SystemLogRecorder.LOGMODULE_CONFIG, "增加", 
					"增加产品属性值,产品PsID为：" + p.getPsID() +",属性名编码为:" + p.getPropertyCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(DuplicateKeyException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错，属性值已经存在！");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错，属性值已经存在！");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
		
	}
	
	public void propertyValueModify(CpConfigedPropertyDTO dto,int productID)throws ServiceException{
		if(dto==null || dto.getPsID()==0 || dto.getPropertyCode()==null||productID==0){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改产品属性值!");
			return;
		}
		
		CpConfigedPropertyPK pPK=new CpConfigedPropertyPK(new Integer(dto.getPsID()),dto.getPropertyCode());
		
		CpConfigedPropertyHome pHome=null;
		CpConfigedProperty p=null;
		try{
			pHome= HomeLocater.getCpConfigedPropertyHome();
			p=pHome.findByPrimaryKey(pPK);

			String oldValue=p.getPropertyValue();
			ProductPropertyDTO ppDTO=BusinessUtility.getProductPropertyDTOByProductIDAndCode(productID,
					dto.getPropertyCode());
			
			CustomerProductDTO custproductDto=BusinessUtility.getCustomerProductDTOByPsID(dto.getPsID());
			
			dto.setDtLastmod(p.getDtLastmod());
			p.setDtLastmod(dto.getDtLastmod());
			p.setPropertyValue(dto.getPropertyValue());

			//创建日志
			String productName=BusinessUtility.getProductDescListByPSIDList(p.getPsID()+"");
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 
	    			((Integer)context.get(Service.CUSTOMER_ID)).intValue(), 
	    			custproductDto.getAccountID(),
	    			custproductDto.getServiceAccountID(),
	    			custproductDto.getPsID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "产品属性修改", 
					"产品属性修改,产品:" + productName + 
					SystemLogRecorder.appendDescString(",属性:"+ppDTO.getPropertyName(),oldValue,dto.getPropertyValue()), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("定位出错");
		}
	}
	
	public void propertyValueDelete(CpConfigedPropertyDTO dto) throws ServiceException{
		if(dto==null || dto.getPsID()==0 || dto.getPropertyCode()==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改产品属性值!");
			return;
		}

		try{
			CpConfigedPropertyPK pPK=new CpConfigedPropertyPK(new Integer(dto.getPsID()),dto.getPropertyCode());
			CpConfigedProperty p=null;
			CpConfigedPropertyHome pHome=HomeLocater.getCpConfigedPropertyHome();

			p=pHome.findByPrimaryKey(pPK);
			p.remove();
			CustomerProductDTO custproductDto=BusinessUtility.getCustomerProductDTOByPsID(dto.getPsID());
				
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), custproductDto.getCustomerID(), 
	    			custproductDto.getAccountID(),
	    			custproductDto.getServiceAccountID(),
	    			custproductDto.getPsID(),
					SystemLogRecorder.LOGMODULE_CONFIG, "删除", 
					"删除产品属性,产品psID为:" + dto.getPsID() +",属性编码为:" + dto.getPropertyCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "服务出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("服务出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("定位出错");
		}
		catch(RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN, "删除出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("删除出错");
		}
	}
	

}
