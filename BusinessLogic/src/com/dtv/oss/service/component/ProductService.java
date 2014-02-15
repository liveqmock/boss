package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Product;
import com.dtv.oss.domain.ProductHome;
import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ProductService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ProductService.class;
	
	public ProductService(ServiceContext s){
		this.context=s;
	}
	
	public void productBaseInfoCreate(ProductDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建产品信息开始");
		
		try{
			ProductHome pHome=HomeLocater.getProductHome();
			Product p=pHome.create(dto);
			
			context.put(Service.PRODUCT_ID,p.getProductID());
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建产品信息", "创建产品信息,新创建的产品ID=" + p.getProductID().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建产品信息结束");
	}
	
	public void productBaseInfoModify(ProductDTO dto)throws ServiceException{
		if(dto==null || dto.getProductID()==0)
			throw new ServiceException("参数错误，产品信息不能为空！");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改产品信息开始");
				
		try{
			ProductHome pHome=HomeLocater.getProductHome();
			Product p=pHome.findByPrimaryKey(new Integer(dto.getProductID()));
			
			if(p.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"更新产品信息出错。");
	    		throw new ServiceException("没有设置上次更新时间！");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改产品信息", "修改产品信息,修改的产品ID=" +dto.getProductID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改产品信息结束");
	}
	
	public void productBaseInfoDelete(ProductDTO productDTO) throws ServiceException {
		if(productDTO==null||productDTO.getProductID()==0)
			throw new ServiceException("参数错误，产品信息不能为空！");
		
		LogUtility.log(clazz,LogLevel.DEBUG,"删除产品信息开始");
		
		try{
			ProductHome home=HomeLocater.getProductHome();
			Product product=home.findByPrimaryKey(new Integer(productDTO.getProductID()));
			product.remove();
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
					PublicService.getCurrentOperatorID(context),0,
					SystemLogRecorder.LOGMODULE_CONFIG,"删除产品信息","删除产品信息，删除的产品ID为："+productDTO.getProductID(),
					SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "查找产品对象出错");
    		throw new ServiceException("查找产品对象出错");
		}catch (RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN,"删除产品对象出错");
			throw new ServiceException("删除产品对象出错");
		}
	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
