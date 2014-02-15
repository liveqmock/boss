package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.MethodOfPayment;
import com.dtv.oss.domain.MethodOfPaymentHome;
import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class MethodOfPaymentService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=MethodOfPaymentService.class;
	
	public MethodOfPaymentService(ServiceContext s){
		this.context=s;
	}
	
	public void methodOfPaymentCreate(MethodOfPaymentDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建付费方式开始");
		
		try{
			MethodOfPaymentHome methodHome=HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment m=methodHome.create(dto);
			
			 
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "新增", "创建付费方式,新付费方式ID=" + m.getMopID().intValue(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建付费方式结束");
	}
	
	public void methodOfPaymentModify(MethodOfPaymentDTO dto)throws ServiceException{
		if(dto==null || dto.getMopID()==0)
			throw new ServiceException("参数错误，付费方式信息不能为空！");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改付费方式信息开始");
				
		try{
			MethodOfPaymentHome methodHome=HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment method=methodHome.findByPrimaryKey(new Integer(dto.getMopID()));
			
			if(method.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"更新付费方式出错。");
	    		throw new ServiceException("更新付费方式信息出错！");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改", "修改付费方式信息,修改的付费方式ID=" +dto.getMopID(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "更新付费方式结束");
	}
	
	public void methodOfPaymentDelete(MethodOfPaymentDTO dto) throws ServiceException{
		
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"付费方式删除");
		try {
			MethodOfPaymentHome methodHome=HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment method=methodHome.findByPrimaryKey(new Integer(dto.getMopID()));
			method.remove();
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除", "删除付费方式信息,删除的付费方式ID=" +dto.getMopID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		} catch (EJBException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费方式删除服务出错！");
			throw new ServiceException("付费方式删除服务出错");
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费方式删除服务出错！");
			throw new ServiceException("付费方式删除服务出错");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费方式删除定位出错！");
			throw new ServiceException("付费方式删除定位出错");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费方式删除出错！");
			throw new ServiceException("付费方式删除出错");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
