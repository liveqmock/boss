package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CryptoUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigOperatorService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigOperatorService.class;
	
	public ConfigOperatorService(ServiceContext s){
		this.context=s;
	}
	
	public void operatorCreate(OperatorDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建操作员!");
			return;
		}
		
	  
		OperatorHome opHome=null;
		Operator oper=null;
		try{
			opHome=HomeLocater.getOperatorHome();
			String OriginalPwd = dto.getLoginPwd();
			//add by chenjiang 2007/02/05
			String loginID=dto.getLoginID();
			int count=BusinessUtility.getLoginIDCount(loginID);
			if(count>0){
				throw new ServiceException("该登录帐号已经存在!");	
			}
			String enPwd = CryptoUtility.enpwd(OriginalPwd);
			dto.setLoginPwd(enPwd);
			oper=opHome.create(dto);
			 
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建操作员",
					"创建操作员,新创建的操作员ID为:"+oper.getOperatorID(), 
					 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		 
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
		
	}
	
	public void operatorModify(OperatorDTO dto)throws ServiceException{
		if(dto==null && dto.getDtLastmod()==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空,或则没有设置最后修改时间,不能修改操作员!");
			return;
		}
		dto.setLoginPwd(CryptoUtility.enpwd(dto.getLoginPwd()));
		OperatorHome opHome=null;
		Operator oper=null;
		try{
//			add by chenjiang 2007/02/05
			String loginID=dto.getLoginID();
			
			opHome=HomeLocater.getOperatorHome();
			oper=opHome.findByPrimaryKey(new Integer(dto.getOperatorID()));
			String curLoginID=oper.getLoginID();
			if (!curLoginID.equals(loginID)) {
				int count = BusinessUtility.getLoginIDCount(loginID);
				if (count > 0) {
					throw new ServiceException("该登录帐号已经存在!");
				}
			}
			if(oper.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改操作员出错!");
				throw new ServiceException("更新操作员出错");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改操作员", 
					"修改操作员,操作员ID为：" + dto.getOperatorID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "查找出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("查找出错");
		}
	}
	
	 
	 
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
