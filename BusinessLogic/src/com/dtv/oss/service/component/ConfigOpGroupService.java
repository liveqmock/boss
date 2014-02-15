package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.OpGroup;
import com.dtv.oss.domain.OpGroupHome;
import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigOpGroupService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigOpGroupService.class;
	
	public ConfigOpGroupService(ServiceContext s){
		this.context=s;
	}
	
	public void OpGroupModify(OpGroupDTO dto)throws ServiceException{
		if(dto==null)
			throw new ServiceException("参数错误，操作员组不能为空！");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改操作员组信息开始");
				
		try{
			OpGroupHome og2pHome = HomeLocater.getOpGroupHome();
		 
			OpGroup opGroup=og2pHome.findByPrimaryKey(new Integer(dto.getOpGroupID()));
			
			if(opGroup.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"更新操作员组出错。");
	    		throw new ServiceException("更新操作员组信息出错！");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改操作员组", 
					"修改操作员组信息,修改的操作员组ID为:" +dto.getOpGroupID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "查找对象出错");
    		throw new ServiceException("查找对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "更新操作员组结束");
	}
	
	public void addOpGroup(OpGroupDTO  dto)throws ServiceException{
		 if (dto == null) {
		 	LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空!");
			return;
        }
		
		 try{
		   
		 	OpGroupHome og2pHome = HomeLocater.getOpGroupHome();
		 	OpGroup  opGroup = og2pHome.create(dto);
	           
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "创建操作员组",
						"新增操作员组 OPGROUPID为:" + opGroup.getOpGroupID(), 
						
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	         
	        
	    }catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
	}
	 
	 
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
