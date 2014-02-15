package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.PackageToMarketSegment;
import com.dtv.oss.domain.PackageToMarketSegmentHome;
import com.dtv.oss.dto.PackageToMarketSegmentDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class ConfigPackageSegmentService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigPackageSegmentService.class;
	
	public ConfigPackageSegmentService(ServiceContext s){
		this.context=s;
	}
	
	public void configPackageSegment(ConfigProductEJBEvent inEvent)throws ServiceException{
		
		 PackageToMarketSegmentHome pmHome = null;
		 
		 try{ 
		 	
		 if(inEvent.getSegmentIDstr()==null || inEvent.getSegmentIDstr()==""){
		 	
			 pmHome = HomeLocater.getPackageToMarketSegmentHome();
			 
			 Collection pmCol=pmHome.findByPackageID(inEvent.getPackageID());
			 
			 Iterator pmIte = pmCol.iterator();
			   
			  while(pmIte.hasNext()){
			  	PackageToMarketSegment pm = (PackageToMarketSegment) pmIte.next();
			  	pm.remove();
			  }
			    return ;
			 
		  }
		 String[] segmentStr=inEvent.getSegmentIDstr().split(",");
		 
	   
	    	
	    	pmHome = HomeLocater.getPackageToMarketSegmentHome();
		
		   Collection pmCol=pmHome.findByPackageID(inEvent.getPackageID());
		  
		  
		  Iterator pmIte = pmCol.iterator();
		   
		  while(pmIte.hasNext()){
		  	PackageToMarketSegment pm = (PackageToMarketSegment) pmIte.next();
		  	pm.remove();
		  }
	   
	    	
	    for(int i = 0; i<segmentStr.length; i++){
	    	
	    	PackageToMarketSegmentDTO pmDto = new PackageToMarketSegmentDTO();
	    	pmDto.setPackageId(inEvent.getPackageID());
	    	pmDto.setMarketSegmentId(Integer.valueOf(segmentStr[i]).intValue());
	    	 
	    	PackageToMarketSegmentHome pmHome1 = null;
	    	pmHome1 = HomeLocater.getPackageToMarketSegmentHome();
	    	pmHome1.create(pmDto);
	    	
	    	
	    }
		 
			 
			 
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
		catch(RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN, "删除出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("删除出错");
		}
		
	}
	
	 
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
