package com.dtv.oss.service.component;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.VOIPCondition;
import com.dtv.oss.domain.VOIPConditionHome;
import com.dtv.oss.domain.VOIPGateway;
import com.dtv.oss.domain.VOIPGatewayHome;
import com.dtv.oss.domain.VOIPGatewayPK;
import com.dtv.oss.domain.VOIPProduct;
import com.dtv.oss.domain.VOIPProductHome;
import com.dtv.oss.domain.VOIPProductPK;
import com.dtv.oss.dto.VOIPConditionDTO;
import com.dtv.oss.dto.VOIPGatewayDTO;
import com.dtv.oss.dto.VOIPProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class VOIPService extends AbstractService{
	public ServiceContext context=null;
	private static Class clazz=VOIPService.class;
	
	public VOIPService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOIPService(ServiceContext context) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public void createVOIPProduct(VOIPProductDTO dto) throws ServiceException {
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建语音产品!");
			return;
		}
		
		/*if(checkVOIPProductExist(dto))
			throw new ServiceException("该语音产品已经存在了，请不要重复设置！");*/
		
		try {
			VOIPProductHome home = HomeLocater.getVOIPProductHome();
			VOIPProduct vProduct=home.create(dto);
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "增加语音产品", 
					"增加语音产品,产品ID为：" + vProduct.getProductID() +",服务类型为:" + vProduct.getSssrvCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		} catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(DuplicateKeyException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错，语音产品已经存在！");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错，语音产品已经存在！");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
	}
	
	public void modifyVOIPProduct(VOIPProductDTO dto)throws ServiceException{
		if(dto==null || dto.getProductID()==0 || dto.getSssrvCode()==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改语音产品属性!");
			return;
		}
		/*if(!checkVOIPProductExist(dto))
			throw new ServiceException("该语音产品不存在，无法进行修改！");*/
		VOIPProductPK pk=new VOIPProductPK(dto.getProductID(),dto.getSssrvCode());
		VOIPProductHome home=null;
		VOIPProduct pProduct=null;
		try{
			home=HomeLocater.getVOIPProductHome();
			pProduct=home.findByPrimaryKey(pk);
			
			if(pProduct.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改语音产品属性出错!");
				throw new ServiceException("更新语音产品属性出错");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改语音产品", 
					"修改语音产品,产品ID为：" + pProduct.getProductID()+",产品服务类型为:" + pProduct.getSssrvCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
	}

	/*public boolean checkVOIPProductExist(VOIPProductDTO dto) {
		VOIPProductDTO pdto=BusinessUtility.getVOIPProductDTOByProductIDAndSCode(dto.getProductID(),dto.getSssrvCode());
		if(pdto==null)
			return false;
		else
			return true;
	}*/
	
	public void createVOIPCondition(VOIPConditionDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建语音条件!");
			return;
		}
		
		/*if(checkVOIPConditionExist(dto))
			throw new ServiceException("该语音条件已经存在了，请不要重复设置！");*/
		
		try {
			VOIPConditionHome home = HomeLocater.getVOIPConditionHome();
			VOIPCondition vCondition=home.create(dto);
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "增加语音条件", 
					"增加语音条件,条件ID为：" + vCondition.getConditionID() +",条件名称为:" + vCondition.getConditionName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		} catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(DuplicateKeyException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错，语音条件已经存在！");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错，语音条件已经存在！");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
	}
	
	public void modifyVOIPCondition(VOIPConditionDTO dto)throws ServiceException{
		if(dto==null || dto.getConditionID()==0 ){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改语音条件属性!");
			return;
		}
		/*if(!checkVOIPConditionExist(dto))
			throw new ServiceException("该条件不存在，无法进行修改！");*/
		
		VOIPConditionHome home=null;
		VOIPCondition vCondition=null;
		
		try{
			home=HomeLocater.getVOIPConditionHome();
			vCondition=home.findByPrimaryKey(new Integer(dto.getConditionID()));
			
			if(vCondition.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改语音条件出错!");
				throw new ServiceException("更新语音条件出错");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改语音条件", 
					"修改语音条件,条件ID为：" + vCondition.getConditionID()+",条件名称为:" + vCondition.getConditionName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
	}

	/*public boolean checkVOIPConditionExist(VOIPConditionDTO dto) {
		VOIPConditionDTO pdto=BusinessUtility.getVOIPConditionDTOByConditionID(dto.getConditionID());
		if(pdto == null)
			return false;
		else
			return true;
	}*/
	
	public void createVOIPGateway(VOIPGatewayDTO dto) throws ServiceException {
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建网关配置!");
			return;
		}
		
		/*if(checkVOIPGatewayExist(dto))
			throw new ServiceException("该网关配置已经存在了，请不要重复设置！");*/
		
		try {
			VOIPGatewayHome home = HomeLocater.getVOIPGatewayHome();
			VOIPGateway gateway=home.create(dto);
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "增加网关配置", 
					"增加网关配置,设备型号为：" + gateway.getDeviceModel() +",设备类型为:" + gateway.getDevsType(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		} catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(DuplicateKeyException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错，网关配置已经存在！");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错，网关配置已经存在！");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
	}
	
	public void modifyVOIPGateway(VOIPGatewayDTO dto,String prevDevsType)throws ServiceException{
		if(dto==null || dto.getDeviceModel()==null || dto.getDevsType()==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改网关配置属性!");
			return;
		}
		/*if(!checkVOIPGatewayExist(dto))
			throw new ServiceException("该网关配置不存在，无法进行修改！");*/
		VOIPGatewayPK pk=new VOIPGatewayPK(dto.getDeviceModel(),prevDevsType);
		VOIPGatewayHome home=null;
		VOIPGateway gateway=null;
		try{
			
			System.out.println("\n"+pk.deviceModel+"￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥"+pk.devsType);
			System.out.println(dto);
			
			
			
			home=HomeLocater.getVOIPGatewayHome();
			gateway=home.findByPrimaryKey(pk);
			String preDeviceModel=gateway.getDeviceModel();
			gateway.remove();
			
//			home=HomeLocater.getVOIPGatewayHome();			
			dto.setDtLastmod(new Timestamp(System.currentTimeMillis()));			
			home.create(dto);
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "修改网关配置", 
					"修改网关配置,设备型号为：" + preDeviceModel+",设备类型为:" + prevDevsType, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		} catch (RemoveException e) {
			LogUtility.log(clazz,LogLevel.WARN, "刪除出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("刪除出错");
		} catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		}
	}

	/*public boolean checkVOIPGatewayExist(VOIPGatewayDTO dto) {
		VOIPGatewayDTO gdto=BusinessUtility.getVOIPGatewayDTOByPK(dto.getDeviceModel(),dto.getDevsType());
		if(gdto ==null)
			return false;
		else
			return true;
	}*/

}
