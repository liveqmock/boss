package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.DeviceMatchToProductHome;
import com.dtv.oss.domain.DeviceMatchToProductPK;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.domain.DeviceMatchToProduct;
import com.dtv.oss.dto.DeviceMatchToProductDTO;

public class ConfigDeviceToProductService {
	ServiceContext context=null;
	private static Class clazz=ConfigDeviceToProductService.class;

	public ConfigDeviceToProductService(ServiceContext context) {
		this.context=context;
	}

	public void addDeviceModel(Collection col) throws ServiceException{
		if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空!");
			return;
        }
		try{
		    Iterator itr = col.iterator();
		    DeviceMatchToProductHome home=HomeLocater.getDeviceMatchToProductHome();
		    while(itr.hasNext()){
		    	DeviceMatchToProduct dmtp=home.create((DeviceMatchToProductDTO) itr.next());
		    	
		    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
		    	PublicService.getCurrentOperatorID(context),0,
		    	SystemLogRecorder.LOGMODULE_CONFIG,"新增产品对应的设备信息","产品号为："+dmtp.getProductId()+" 新增的设备型号为："+dmtp.getDeviceModel(),
		    	SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		    }
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		} catch (CreateException e) {
			LogUtility.log(clazz,LogLevel.WARN,"创建产品对应设备信息出错");
			throw new ServiceException("创建产品对应设备信息出错");
		}
	}

	public void deleteDeviceModel(Collection col) throws ServiceException{
		if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空!");
			return;
        }
		try{
			Iterator itr=col.iterator();
			DeviceMatchToProductHome home=HomeLocater.getDeviceMatchToProductHome();
			while(itr.hasNext()){
				DeviceMatchToProductDTO dto=(DeviceMatchToProductDTO)itr.next();
				DeviceMatchToProductPK pk=new DeviceMatchToProductPK(dto.getProductId(),dto.getDeviceModel());
				DeviceMatchToProduct dmtp=home.findByPrimaryKey(pk);
				dmtp.remove();
				
				SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
						PublicService.getCurrentOperatorID(context),0,
						SystemLogRecorder.LOGMODULE_CONFIG,"删除产品对应的设备信息","删除产品号为："+dto.getProductId()+" 对应的设备型号："+dto.getDeviceModel(),
						SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
			}
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.WARN,"查找出错");
			throw new ServiceException("查找出错");
		} catch (RemoveException e1) {
			LogUtility.log(clazz,LogLevel.WARN,"删除产品对应的设备信息出错");
			throw new ServiceException("删除产品对应的设备信息出错");
		}
	}
}
