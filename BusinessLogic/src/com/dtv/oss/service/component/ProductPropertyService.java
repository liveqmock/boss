package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.ProductProperty;
import com.dtv.oss.domain.ProductPropertyHome;
import com.dtv.oss.domain.ProductPropertyPK;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ProductPropertyService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ProductPropertyService.class;
	
	public ProductPropertyService(ServiceContext s){
		this.context=s;
	}
	
	public void productPropertyCreate(ProductPropertyDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建产品属性!");
			return;
		}
		
		if(checkProductPopertyExist(dto))
			throw new ServiceException("该属性已经存在了，请不要重复设置！");
		
		ProductPropertyHome pHome=null;
		ProductProperty p=null;
		try{
			pHome=HomeLocater.getProductPropertyHome();
			p=pHome.create(dto);
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "增加", 
					"增加产品属性,产品ID为：" + p.getProductId() +",属性名为:" + p.getPropertyName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(DuplicateKeyException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错，属性名称已经存在！");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错，属性名称已经存在！");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
		
	}
	
	public void productPropertyModify(ProductPropertyDTO dto)throws ServiceException{
		if(dto==null || dto.getProductId()==0 || dto.getPropertyName()==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改产品属性!");
			return;
		}
		
		if(!checkProductPopertyExist(dto))
			throw new ServiceException("该属性不存在，无法进行修改！");
		
		ProductPropertyPK pPK=new ProductPropertyPK(dto.getProductId(),dto.getPropertyName());

		ProductPropertyHome pHome=null;
		ProductProperty p=null;
		try{
			pHome=HomeLocater.getProductPropertyHome();
			p=pHome.findByPrimaryKey(pPK);
			if(p.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改产品属性出错!");
				throw new ServiceException("更新产品属性出错");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改", 
					"修改产品属性,产品ID为：" + p.getProductId() +",属性名为:" + p.getPropertyName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
	}
	
	public void productPropertyDelete(ProductPropertyDTO dto,String propertyNamesList) throws ServiceException{
		if(dto.getProductId()==0 || propertyNamesList==null || "".equals(propertyNamesList)){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数不完整，不能删除产品属性!");
			return;
		}

		String propertyNames[]=propertyNamesList.split(",");
		try{
			ProductPropertyPK pPK=null;
			ProductProperty p=null;
			ProductPropertyHome pHome=HomeLocater.getProductPropertyHome();
			for(int i=0;i<propertyNames.length;i++){
				pPK=new ProductPropertyPK(dto.getProductId(),propertyNames[i]);
				p=pHome.findByPrimaryKey(pPK);
				p.remove();
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除", 
					"删除产品属性,产品ID为:" + dto.getProductId() +",属性名为:" + propertyNamesList, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "删除出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("删除出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "删除出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("删除出错");
		}
		catch(RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN, "删除出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("删除出错");
		}
	}
	
	private boolean checkProductPopertyExist(ProductPropertyDTO dto){
		ProductPropertyDTO pdto=BusinessUtility.getProductPropertyDTOByProductIDAndName(dto.getProductId(),dto.getPropertyName());
		
		if(pdto==null)
			return false;
		else 
			return true;
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
