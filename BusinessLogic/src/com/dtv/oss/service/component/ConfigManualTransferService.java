package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.ManualTransferSetting;
import com.dtv.oss.domain.ManualTransferSettingHome;
import com.dtv.oss.domain.OpGroup;
import com.dtv.oss.domain.OpGroupHome;
import com.dtv.oss.dto.ManualTransferSettingDTO;
import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigManualTransferService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigManualTransferService.class;
	
	public ConfigManualTransferService(ServiceContext s){
		this.context=s;
	}
	
	public void manualTransferModify(ManualTransferSettingDTO dto)throws ServiceException{
		if(dto==null)
			throw new ServiceException("参数错误，流转设置不能为空！");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改流转设置开始");
				
		try{
			ManualTransferSettingHome manualTranHome = HomeLocater.getManualTransferSettingHome();
		 
			ManualTransferSetting manuTrans=manualTranHome.findByPrimaryKey(new Integer(dto.getSeqNo()));
			
			if(manuTrans.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"流转设置修改出错。");
	    		throw new ServiceException("流转设置修改出错！");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改流转设置", 
					"修改流转设置信息,修改的seqno为:" +dto.getSeqNo(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "更新流转设置结束");
	}
	
	public void manualTransferCreate(ManualTransferSettingDTO  dto)throws ServiceException{
		 if (dto == null) {
		 	LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空!");
			return;
        }
		
		 try{
		   
			 ManualTransferSettingHome manualTranHome = HomeLocater.getManualTransferSettingHome();
			 
			 ManualTransferSetting  opGroup = manualTranHome.create(dto);
	           
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "创建流转设置",
						"新增流转设置seqno为:" + opGroup.getSeqNo(), 
						
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
