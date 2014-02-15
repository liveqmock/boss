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
		LogUtility.log(clazz,LogLevel.DEBUG,"����ҵ����");
		try{
			ServiceHome cpHome =HomeLocater.getServiceHome();
			Service service=cpHome.create(dto);
			Integer id=service.getServiceID();
			context.put("SERVICEID",id);
		}
		catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "����ҵ���嶨λ����");                    
            throw new ServiceException("����ҵ���嶨λ����");
		}
		catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ���崴������");                    
            throw new ServiceException("ҵ���崴������");
		} 

	} 
	
	public void delService(String serviceIds) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ��ҵ����");
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
			LogUtility.log(clazz,LogLevel.ERROR, "ɾ��ҵ���嶨λ����");                    
            throw new ServiceException("ɾ��ҵ���嶨λ����");
		}
		catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ����ɾ������");                    
            throw new ServiceException("ҵ����ɾ������");
		} 
		catch(FinderException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ������ҳ���");                    
            throw new ServiceException("ҵ������ҳ���");
		} 
		
	}
	
	public void updateService(ServiceDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"����ҵ����");
	    try{
	    	ServiceHome cpHome =HomeLocater.getServiceHome();
	    	Service service =cpHome.findByPrimaryKey(new Integer(dto.getServiceID()));
	    	if (service.ejbUpdate(dto)==-1){ 
			   throw new RuntimeException("ServiceDTO update fail, ID:"+dto.getServiceID()+". Please make sure that dt_lastmod of ServiceDTO is set.");
		    }
	    }catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("ҵ���嶨λ����ҵ����ID��"+dto.getServiceID());
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("ҵ������ҳ���ҵ����ID��"+dto.getServiceID());
        }
	}
	
	public void createServiceDependency(ServiceDependencyDTO dto) throws ServiceException {
		LogUtility.log(clazz,LogLevel.DEBUG,"ҵ�����������ϵ");
		try{
			ServiceDependencyHome cpHome =HomeLocater.getServiceDependencyHome();
			Collection cols =cpHome.findByReferServiceId(dto.getReferServiceId());
			if (cols !=null && cols.isEmpty()==false){
				Iterator it =cols.iterator();
				while (it.hasNext()){
					ServiceDependency serviceDependeny =(ServiceDependency)it.next();
					if (serviceDependeny.getServiceId() ==dto.getServiceId()){
						throw new ServiceException("ҵ�����������ϵ�Ѵ��ڣ������ظ�����");
					}
				}
			}
			cpHome.create(dto);
		} catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ�����������ϵ��λ����");                    
            throw new ServiceException("ҵ�����������ϵ��λ����");
		} catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("ҵ�����������ϵ���ҳ���ҵ������ID��"+dto.getReferServiceId());
        } catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ�����������ϵ��������");                    
            throw new ServiceException("ҵ�����������ϵ��������");
		} 
	}
	
	public void delServiceDependency(String serviceId ,String referServiceIds) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ��ҵ�����������ϵ");
		try{
			ServiceDependencyHome cpHome =HomeLocater.getServiceDependencyHome();
			String[] arrReferServiceIds =referServiceIds.split(","); 
			for (int i=0; i<arrReferServiceIds.length; i++){
				ServiceDependencyPK pk =new ServiceDependencyPK(Integer.parseInt(serviceId),Integer.parseInt(arrReferServiceIds[i]));
				ServiceDependency serviceDependency =cpHome.findByPrimaryKey(pk);
				serviceDependency.remove();
			}	
		} catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ�����������ϵ��λ����");                    
            throw new ServiceException("ҵ�����������ϵ��λ����");
		} catch(RemoveException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ�����������ϵɾ������");                    
            throw new ServiceException("ҵ�����������ϵɾ������");
		} catch(FinderException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ�����������ϵ���ҳ���");                    
            throw new ServiceException("ҵ�����������ϵ���ҳ���");
		} 
	}
	
	public void createServiceResource(ServiceResourceDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"����ҵ����Դ����");
		ServiceResourceHome cpHome =null;
		try{
			cpHome =HomeLocater.getServiceResourceHome();
			if (checkServiceResource(dto.getResourceName())){
				LogUtility.log(clazz,LogLevel.WARN, "ҵ����Դ�Ѵ��ڣ������ظ����壡");
	    		throw new ServiceException("��������ҵ����Դ�Ѵ��ڣ������ظ����壡");
			}
			
			ServiceResource sr=cpHome.create(dto);
			 
		} catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "����ҵ����Դ���嶨λ����");                    
            throw new ServiceException("����ҵ����Դ���嶨λ����");
		} catch(CreateException e2){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ����Դ��������");                    
            throw new ServiceException("ҵ����Դ��������");
		}
	}
	
	public void updateServiceResource(ServiceResourceDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"����ҵ����");
	    try{
	    	ServiceResourceHome cpHome =HomeLocater.getServiceResourceHome();
	    	ServiceResource serviceResource =cpHome.findByPrimaryKey(dto.getResourceName());
	    	if (serviceResource.ejbUpdate(dto)==-1){ 
			   throw new RuntimeException("ServiceResourceDTO update fail, Name:"+dto.getResourceName()+". Please make sure that dt_lastmod of ServiceResourceDTO is set.");
		    }
	    } catch (HomeFactoryException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("ҵ����Դ���嶨λ����ҵ����ID��"+dto.getResourceName());
        } catch (FinderException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new ServiceException("ҵ����Դ������ҳ���ҵ����ID��"+dto.getResourceName());
        }
	}
	
	public void delServiceResource(String serviceNames) throws  ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ��ҵ����Դ����");
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
	    	LogUtility.log(clazz,LogLevel.ERROR, "ɾ��ҵ����Դ���嶨λ����");                    
            throw new ServiceException("ɾ��ҵ����Դ���嶨λ����");
	    } catch(RemoveException e2){
		    LogUtility.log(clazz,LogLevel.ERROR, "ҵ����Դ����ɾ������");                    
            throw new ServiceException("ҵ����Դ����ɾ������");
	    } catch(FinderException e3){
		    LogUtility.log(clazz,LogLevel.ERROR, "ҵ����Դ������ҳ���");                    
            throw new ServiceException("ҵ����Դ������ҳ���");
	    } 
	}
	
	public void createServiceResourceDetail(ServiceResourceDetailDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ҵ����Դ��ϸ");
/*		try{
			ServiceResourceDetailHome detailHome =HomeLocater.getServiceResourceDetailHome();
			detailHome.create(dto);
			ServiceResourceHome cpHome =HomeLocater.getServiceResourceHome();
			ServiceResource cp =cpHome.findByPrimaryKey(dto.getResourceName());
			cp.setTotalNumber(cp.getTotalNumber()+1);
		} catch(HomeFactoryException e1){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ����Դ��ϸ��λ����");                    
            throw new ServiceException("ҵ����Դ��ϸ��λ����");
		} catch(CreateException e3){
			LogUtility.log(clazz,LogLevel.ERROR, "ҵ����Դ��ϸ��������");                    
            throw new ServiceException("ҵ����Դ��ϸ��������");
		} catch(FinderException e3){
		    LogUtility.log(clazz,LogLevel.ERROR, "ҵ����Դ������ҳ���");                    
            throw new ServiceException("ҵ����Դ������ҳ���");
	    }
*/	     
	}
	
	public void delServiceResourceDetail(String ids) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ��ҵ����Դ��ϸ");
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
	    	LogUtility.log(clazz,LogLevel.ERROR, "ɾ��ҵ����Դ���嶨λ����");                    
            throw new ServiceException("ɾ��ҵ����Դ���嶨λ����");
	    } catch(RemoveException e2){
		    LogUtility.log(clazz,LogLevel.ERROR, "ҵ����Դ����ɾ������");                    
            throw new ServiceException("ҵ����Դ����ɾ������");
	    } catch(FinderException e3){
		    LogUtility.log(clazz,LogLevel.ERROR, "ҵ����Դ������ҳ���");                    
            throw new ServiceException("ҵ����Դ������ҳ���");
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
            throw new ServiceException("ҵ����Դ���嶨λ����ҵ����Դ��"+resourceName);
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
