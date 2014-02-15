package com.dtv.oss.service.component;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Bundle2Campaign;
import com.dtv.oss.domain.Bundle2CampaignHome;
import com.dtv.oss.domain.ProductPackage;
import com.dtv.oss.domain.ProductPackageHome;
import com.dtv.oss.dto.Bundle2CampaignDTO;
import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ProductPackageService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ProductPackageService.class;
	
	public ProductPackageService(ServiceContext s){
		this.context=s;
	}
	
	public void productPackageCreate(ConfigProductEJBEvent event)throws ServiceException{
		ProductPackageDTO dto=event.getProductPackageDTO();
		String campaignId = event.getCampaignIdStr();
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建产品包!");
			return;
		}
		
	 
		ProductPackageHome pHome=null;
		
		ProductPackage p=null;
		
		try{
			if(campaignId!=null && campaignId!=""){
				
				 Bundle2CampaignHome bundleHome=HomeLocater.getBundle2CampaignHome();
				
				Collection bund=bundleHome.findByCampaignID(Integer.valueOf(campaignId).intValue());
				if(bund!=null && bund.size()>0)
					throw new ServiceException("该套餐活动已经被使用过,请重新选择其他套餐活动");
				
			     pHome=HomeLocater.getProductPackageHome();
			     p=pHome.create(dto);
			
				Bundle2CampaignDTO bandleDto= new Bundle2CampaignDTO();	
				bandleDto.setPackageID(p.getPackageID().intValue());
				bandleDto.setCampaignID(Integer.valueOf(campaignId).intValue());
				
				bundleHome.create(bandleDto);
			} else{
				     pHome=HomeLocater.getProductPackageHome();
				     p=pHome.create(dto);
			}
				
			 
			context.put(Service.PRODUCT_PACKAGE_ID,p.getPackageID());
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建产品包", 
					"创建产品包,packageID为：" + p.getPackageID() +",包名为:" + p.getPackageName(), 
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
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "查找出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("查找出错");
		}
	}
	
	 

	public void productPackageModify(ConfigProductEJBEvent event)throws ServiceException{
		
		ProductPackageDTO dto=event.getProductPackageDTO();
		String campaignId = event.getCampaignIdStr();
		if(dto==null || dto.getPackageID()==0){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改产品包!");
			return;
		}
		
		 
		
	 
		ProductPackageHome pHome=null;
		ProductPackage p=null;
		try{
			
			if(campaignId!=null && campaignId!=""){
				 Bundle2CampaignHome bundleHome=HomeLocater.getBundle2CampaignHome();
				pHome=HomeLocater.getProductPackageHome();
		 		
				Bundle2CampaignDTO bundleDto =	BusinessUtility.getBundle2CampaignDTOByPackageID(dto.getPackageID());
				if(bundleDto!=null){
				 if(campaignId.equalsIgnoreCase(String.valueOf(bundleDto.getCampaignID())))
				  {
					p=pHome.findByPrimaryKey(new Integer(dto.getPackageID()));
					if(p.ejbUpdate(dto)==-1){
						LogUtility.log(clazz,LogLevel.WARN,"修改产品包出错!");
						throw new ServiceException("更新产品包出错");
					}
					
				}
				else{
					Collection bund=bundleHome.findByCampaignID(Integer.valueOf(campaignId).intValue());
					
					if(bund!=null && bund.size()>0)
						throw new ServiceException("该套餐活动已经被使用过,请重新选择其他套餐活动");
					p=pHome.findByPrimaryKey(new Integer(dto.getPackageID()));
					if(p.ejbUpdate(dto)==-1){
						LogUtility.log(clazz,LogLevel.WARN,"修改产品包出错!");
						throw new ServiceException("更新产品包出错");
					}
					Bundle2Campaign bundle=bundleHome.findByPrimaryKey(new Integer(dto.getPackageID()));
					bundle.setCampaignID(Integer.valueOf(campaignId).intValue());
					bundle.setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
					bundle.setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
				}
				
			}	else{
				  
				Collection bundle=bundleHome.findByCampaignID(Integer.valueOf(campaignId).intValue());
				 
				if(bundle!=null && bundle.size()>0)
					throw new ServiceException("该套餐活动已经被使用过,请重新选择其他套餐活动");
				Bundle2CampaignDTO bundle2Dto= new Bundle2CampaignDTO();
				bundle2Dto.setPackageID(dto.getPackageID());
				bundle2Dto.setCampaignID(Integer.valueOf(campaignId).intValue());
				 Bundle2CampaignHome bundle2Home=HomeLocater.getBundle2CampaignHome();
				 bundle2Home.create(bundle2Dto);
				 p=pHome.findByPrimaryKey(new Integer(dto.getPackageID()));
					if(p.ejbUpdate(dto)==-1){
						LogUtility.log(clazz,LogLevel.WARN,"修改产品包出错!");
						throw new ServiceException("更新产品包出错");
					}
			   }
				
				 
			} else {
			         pHome=HomeLocater.getProductPackageHome();
			
			        p=pHome.findByPrimaryKey(new Integer(dto.getPackageID()));
			        Bundle2CampaignDTO bundleDto =	BusinessUtility.getBundle2CampaignDTOByPackageID(dto.getPackageID());
			        if(bundleDto!=null){
			        	 Bundle2CampaignHome bundle2CamHome=HomeLocater.getBundle2CampaignHome();
			        	 Bundle2Campaign romObj = bundle2CamHome.findByPrimaryKey(new Integer(dto.getPackageID()));
			        	 romObj.remove();
			        }     
			        
			        
			  if(p.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"修改产品包出错!");
				throw new ServiceException("更新产品包出错");
			}
			}
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改产品包", 
					"修改产品包,产品包ID为：" + p.getPackageID(), 
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
		} catch (CreateException e) {
			LogUtility.log(clazz,LogLevel.WARN, "创建出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建出错");
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
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
