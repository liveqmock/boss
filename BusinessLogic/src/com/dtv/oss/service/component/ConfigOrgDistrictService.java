package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.OrgGovernedDistrict;
import com.dtv.oss.domain.OrgGovernedDistrictHome;
import com.dtv.oss.domain.OrgGovernedDistrictPK;
import com.dtv.oss.dto.OrgGovernedDistrictDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigOrgDistrictService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigOrgDistrictService.class;
	
	public ConfigOrgDistrictService(ServiceContext s){
		this.context=s;
	}
	
	public void deleteOrg2District(Collection col)throws ServiceException{
		
		 if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		 try{
		  
		    Iterator iter = col.iterator();
	        OrgGovernedDistrictHome orgDistrictHome = HomeLocater.getOrgGovernedDistrictHome();
	        while (iter.hasNext()) {
	        	 OrgGovernedDistrictDTO dto = (OrgGovernedDistrictDTO) iter.next();
	        	 
	        	 OrgGovernedDistrictPK pk = new OrgGovernedDistrictPK(dto.getOrgId(), dto.getDistrictId());
	        	 OrgGovernedDistrict orgDistrict = orgDistrictHome.findByPrimaryKey(pk);
	        	 orgDistrict.remove();
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG, "ɾ����������", 
						"ɾ������������Ϣ orgIDΪ:" + dto.getOrgId() +",DISTRICTIDΪ:" + dto.getDistrictId(), 
						
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	        }
	        
	    }
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (EJBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 catch (RemoveException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void addOrg2District(Collection col)throws ServiceException{
		 if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		
		 try{
		    Iterator iter = col.iterator();
		    OrgGovernedDistrictHome orgDistrictHome = HomeLocater.getOrgGovernedDistrictHome();
	        while (iter.hasNext()) {
	        	 OrgGovernedDistrictDTO dto = (OrgGovernedDistrictDTO) iter.next();
	        	 orgDistrictHome.create(dto);
	           
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG,"��������������Ϣ", 
						"��������������ϢorgIDΪ:" + dto.getOrgId()+",DISTRICTIDΪ:" + dto.getDistrictId(), 
						
						SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	        }
	        
	    }
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (EJBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
	}
	 
	 
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
