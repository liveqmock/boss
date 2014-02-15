package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Brcondition;
import com.dtv.oss.domain.BrconditionHome;
import com.dtv.oss.dto.BrconditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class BrConditionService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=BrConditionService.class;
	
	public BrConditionService(ServiceContext s){
		this.context=s;
	}
	
	public void brConditionCreate(BrconditionDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建支付条件开始");
		
		try{
			BrconditionHome brCondHome=HomeLocater.getBrconditionHome();
			Brcondition p=brCondHome.create(dto);
			
			context.put(Service.BRCONDITION_ID,p.getBrcntID());
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建支付条件", "创建支付条件信息,新创建的支付条件ID为:" + p.getBrcntID().intValue(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建支付条件结束");
	}
	
	public void brConditionModify(BrconditionDTO dto)throws ServiceException{
		if(dto==null || dto.getBrcntID()==0)
			throw new ServiceException("参数错误，支付条件信息不能为空！");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改支付条件信息开始");
				
		try{
			BrconditionHome brCondHome=HomeLocater.getBrconditionHome();
			Brcondition brc=brCondHome.findByPrimaryKey(new Integer(dto.getBrcntID()));
			
			if(brc.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"更新支付条件信信息出错。");
	    		throw new ServiceException("更新支付条件信息出错！");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改支付条件", "修改支付条件信息,修改的支付条件为:" +dto.getBrcntID(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "更新支付条件结束");
	}
	
	public void brConditionDelete(BrconditionDTO dto) throws ServiceException{
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"计费条件删除");
		try {
			BrconditionHome brCondHome=HomeLocater.getBrconditionHome();
			Brcondition br=brCondHome.findByPrimaryKey(new Integer(dto.getBrcntID()));
			br.remove();
//			创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    	        PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除支付条件", "删除支付条件信息,删除的支付条件为:" +dto.getBrcntID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		} catch (EJBException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "计费条件删除服务出错！");
			throw new ServiceException("计费条件删除服务出错");
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "计费条件删除服务出错！");
			throw new ServiceException("计费条件删除服务出错");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "计费条件删除定位出错！");
			throw new ServiceException("计费条件删除定位出错");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "计费条件删除出错！");
			throw new ServiceException("计费条件删除出错");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
