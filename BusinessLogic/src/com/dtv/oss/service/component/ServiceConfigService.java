package com.dtv.oss.service.component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Service;
import com.dtv.oss.domain.ServiceDependency;
import com.dtv.oss.domain.ServiceDependencyHome;
import com.dtv.oss.domain.ServiceDependencyPK;
import com.dtv.oss.domain.ServiceHome;
import com.dtv.oss.domain.ServiceResource;
import com.dtv.oss.domain.ServiceResourceHome;
import com.dtv.oss.dto.ServiceDTO;
import com.dtv.oss.dto.ServiceDependencyDTO;
import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.dto.ServiceResourceDetailDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class ServiceConfigService extends AbstractService {

	private ServiceContext context=null;
	private static final Class clazz = ServiceConfigService.class;

	public ServiceConfigService(ServiceContext inContext){
		this.context=inContext;
	}
	
	public void createService(ServiceDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"创建业务定义");
		try{
			ServiceHome cpHome =HomeLocater.getServiceHome();
			Service service=cpHome.create(dto);
			Integer id=service.getServiceID();
			context.put("SERVICEID",id);
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "创建业务定义定位出错！");                    
            throw new ServiceException("创建业务定义定位出错！");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "业务定义创建出错！");                    
            throw new ServiceException("业务定义创建出错！");
		} 

	} 
	
	public void delService(String serviceIds) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"删除业务定义");
		try{
			ServiceHome cpHome =HomeLocater.getServiceHome();
			ServiceDependencyHome serviceDependencyHome =HomeLocater.getServiceDependencyHome();
			String[] arrService =serviceIds.split(","); 
			for (int i=0; i<arrService.length; i++){
				Service service =cpHome.findByPrimaryKey(new Integer(Integer.parseInt(arrService[i])));				
				service.remove();
				Collection cols =serviceDependencyHome.findByServiceId(Integer.parseInt(arrService[i]));
			    Iterator it =cols.iterator();
			    while (it.hasNext()){
			    	ServiceDependency serviceDependency =(ServiceDependency)it.next();
			    	serviceDependency.remove();
			    }
			}
			
		}catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "删除业务定义定位出错！");                    
            throw new ServiceException("删除业务定义定位出错！");
		}
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "业务定义删除出错！");                    
            throw new ServiceException("业务定义删除出错！");
		} 
		catch(FinderException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "业务定义查找出错！");                    
            throw new ServiceException("业务定义查找出错！");
		} 
		
	}
	
	public void updateService(ServiceDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"更新业务定义");
	    try{
	    	ServiceHome cpHome =HomeLocater.getServiceHome();
	    	Service service =cpHome.findByPrimaryKey(new Integer(dto.getServiceID()));
	    	if (service.ejbUpdate(dto)==-1){ 
			   throw new RuntimeException("ServiceDTO update fail, ID:"+dto.getServiceID()+". Please make sure that dt_lastmod of ServiceDTO is set.");
		    }
	    }catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("业务定义定位出错，业务定义ID："+dto.getServiceID());
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("业务定义查找出错，业务定义ID："+dto.getServiceID());
        }
	}
	
	public void createServiceDependency(ServiceDependencyDTO dto) throws ServiceException {
		LogUtility.log(clazz,LogLevel.DEBUG,"业务的自依赖关系");
		try{
			ServiceDependencyHome cpHome =HomeLocater.getServiceDependencyHome();
			Collection cols =cpHome.findByReferServiceId(dto.getReferServiceId());
			if (cols !=null && cols.isEmpty()==false){
				Iterator it =cols.iterator();
				while (it.hasNext()){
					ServiceDependency serviceDependeny =(ServiceDependency)it.next();
					if (serviceDependeny.getServiceId() ==dto.getServiceId()){
						throw new ServiceException("业务的自依赖关系已存在，不能重复定义");
					}
				}
			}
			cpHome.create(dto);
		} catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "业务的自依赖关系定位出错！");                    
            throw new ServiceException("业务的自依赖关系定位出错！");
		} catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("业务的自依赖关系查找出错，业务依赖ID："+dto.getReferServiceId());
        } catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "业务的自依赖关系创建出错！");                    
            throw new ServiceException("业务的自依赖关系创建出错！");
		} 
	}
	
	public void delServiceDependency(String serviceId ,String referServiceIds) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"删除业务的自依赖关系");
		try{
			ServiceDependencyHome cpHome =HomeLocater.getServiceDependencyHome();
			String[] arrReferServiceIds =referServiceIds.split(","); 
			for (int i=0; i<arrReferServiceIds.length; i++){
				ServiceDependencyPK pk =new ServiceDependencyPK(Integer.parseInt(serviceId),Integer.parseInt(arrReferServiceIds[i]));
				ServiceDependency serviceDependency =cpHome.findByPrimaryKey(pk);
				serviceDependency.remove();
			}	
		} catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "业务的自依赖关系定位出错！");                    
            throw new ServiceException("业务的自依赖关系定位出错！");
		} catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "业务的自依赖关系删除出错！");                    
            throw new ServiceException("业务的自依赖关系删除出错！");
		} catch(FinderException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "业务的自依赖关系查找出错！");                    
            throw new ServiceException("业务的自依赖关系查找出错！");
		} 
	}
	
	public void createServiceResource(ServiceResourceDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"创建业务资源定义");
		ServiceResourceHome cpHome =null;
		try{
			cpHome =HomeLocater.getServiceResourceHome();
			if (checkServiceResource(dto.getResourceName())){
				LogUtility.log(clazz,LogLevel.WARN, "业务资源已存在，不能重复定义！");
	    		throw new ServiceException("创建出错，业务资源已存在，不能重复定义！");
			}
			
			ServiceResource sr=cpHome.create(dto);
			 
		} catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "创建业务资源定义定位出错！");                    
            throw new ServiceException("创建业务资源定义定位出错！");
		} catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "业务资源创建出错！");                    
            throw new ServiceException("业务资源创建出错！");
		}
	}
	
	public void updateServiceResource(ServiceResourceDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"更新业务定义");
	    try{
	    	ServiceResourceHome cpHome =HomeLocater.getServiceResourceHome();
	    	ServiceResource serviceResource =cpHome.findByPrimaryKey(dto.getResourceName());
	    	if (serviceResource.ejbUpdate(dto)==-1){ 
			   throw new RuntimeException("ServiceResourceDTO update fail, Name:"+dto.getResourceName()+". Please make sure that dt_lastmod of ServiceResourceDTO is set.");
		    }
	    } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("业务资源定义定位出错，业务定义ID："+dto.getResourceName());
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("业务资源定义查找出错，业务定义ID："+dto.getResourceName());
        }
	}
	
	public void delServiceResource(String serviceNames) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"删除业务资源定义");
		try{
			ServiceResourceHome cpHome =HomeLocater.getServiceResourceHome();
			String[] arrserviceName =serviceNames.split(",");
			String detailResourceNames ="''";
			for (int i=0; i<arrserviceName.length; i++){
				ServiceResource serviceResource = cpHome.findByPrimaryKey(arrserviceName[i]);				
			    serviceResource.remove();
			    detailResourceNames =detailResourceNames +",'"+arrserviceName[i]+"'";
			}
			Connection con = null;
			Statement stmt = null;
			String sql ="delete from t_serviceresourcedetail where resourcename in ("+detailResourceNames+")";
			try{
				con = HomeFactory.getFactory().getDataSource().getConnection();
				stmt =con.createStatement();
				stmt.execute(sql);	
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException ee) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException ee) {
					}
				}
			}
    	} catch(HomeFactoryException e1){
	    	LogUtility.log(clazz,LogLevel.ERROR, "删除业务资源定义定位出错！");                    
            throw new ServiceException("删除业务资源定义定位出错！");
	    } catch(RemoveException e2){
		    LogUtility.log(clazz,LogLevel.ERROR, "业务资源定义删除出错！");                    
            throw new ServiceException("业务资源定义删除出错！");
	    } catch(FinderException e3){
		    LogUtility.log(clazz,LogLevel.ERROR, "业务资源定义查找出错！");                    
            throw new ServiceException("业务资源定义查找出错！");
	    } 
	}
	
	public void createServiceResourceDetail(ServiceResourceDetailDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"业务资源明细");
/*		try{
			ServiceResourceDetailHome detailHome =HomeLocater.getServiceResourceDetailHome();
			detailHome.create(dto);
			ServiceResourceHome cpHome =HomeLocater.getServiceResourceHome();
			ServiceResource cp =cpHome.findByPrimaryKey(dto.getResourceName());
			cp.setTotalNumber(cp.getTotalNumber()+1);
		} catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "业务资源明细定位出错！");                    
            throw new ServiceException("业务资源明细定位出错！");
		} catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "业务资源明细创建出错！");                    
            throw new ServiceException("业务资源明细创建出错！");
		} catch(FinderException e3){
		    LogUtility.log(clazz,LogLevel.ERROR, "业务资源定义查找出错！");                    
            throw new ServiceException("业务资源定义查找出错！");
	    }
*/	     
	}
	
	public void delServiceResourceDetail(String ids) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"删除业务资源明细");
/*		try{
			ServiceResourceDetailHome cpHome =HomeLocater.getServiceResourceDetailHome();
			ServiceResourceHome resourceHome =HomeLocater.getServiceResourceHome();
			ServiceResourceDetail cp =null;
			ServiceResource resource =null;
			String[] arrId =ids.split(",");
			for (int i=0; i<arrId.length; i++){
				cp =cpHome.findByPrimaryKey(new Integer(Integer.parseInt(arrId[i])));
			    resource =resourceHome.findByPrimaryKey(cp.getResourceName());
			    resource.setTotalNumber(resource.getTotalNumber()-1);
			    cp.remove();
			}
		} catch(HomeFactoryException e1){
	    	LogUtility.log(clazz,LogLevel.ERROR, "删除业务资源定义定位出错！");                    
            throw new ServiceException("删除业务资源定义定位出错！");
	    } catch(RemoveException e2){
		    LogUtility.log(clazz,LogLevel.ERROR, "业务资源定义删除出错！");                    
            throw new ServiceException("业务资源定义删除出错！");
	    } catch(FinderException e3){
		    LogUtility.log(clazz,LogLevel.ERROR, "业务资源定义查找出错！");                    
            throw new ServiceException("业务资源定义查找出错！");
	    }
*/	     
	}
	
	private boolean checkServiceResource(String resourceName) throws ServiceException{
		boolean flag =false;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs =null;
		String sql ="select * from t_serviceresource t where t.resourcename ='"+resourceName+"'";
		try{
			con = HomeFactory.getFactory().getDataSource().getConnection();
			stmt =con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()){
				flag =true;
			}
		} catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("业务资源定义定位出错，业务资源："+resourceName);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException ee) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ee) {
				}
			}
		}
		return flag;
	}
	
}
