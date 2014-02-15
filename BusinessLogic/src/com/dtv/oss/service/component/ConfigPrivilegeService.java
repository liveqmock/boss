package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.OpGroupToPrivilege;
import com.dtv.oss.domain.OpGroupToPrivilegeHome;
import com.dtv.oss.domain.OpGroupToPrivilegePK;
import com.dtv.oss.dto.OpGroupToPrivilegeDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigPrivilegeService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigPrivilegeService.class;
	
	public ConfigPrivilegeService(ServiceContext s){
		this.context=s;
	}
	
	public void deleteOpGroup2Privilege(Collection col)throws ServiceException{
		
		 if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		 try{
		    Iterator iter = col.iterator();
	        OpGroupToPrivilegeHome og2pHome = HomeLocater.getOpGroupToPrivilegeHome();
	        while (iter.hasNext()) {
	            OpGroupToPrivilegeDTO dto = (OpGroupToPrivilegeDTO) iter.next();
	            OpGroupToPrivilegePK pk = new OpGroupToPrivilegePK(dto.getOpGroupId(), dto.getPrivId());
	            OpGroupToPrivilege opGroupToPrivilege = og2pHome.findByPrimaryKey(pk);
	            
					opGroupToPrivilege.remove();
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG,"ɾ��", 
						"ɾ������Ա��Ȩ����Ϣ OPGROUPIDΪ:" + dto.getOpGroupId() +",PRIVIDΪ:" + dto.getPrivId(), 
						
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
	
	public void addOpGroup2Privilege(Collection col)throws ServiceException{
		 if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		
		 try{
		    Iterator iter = col.iterator();
	        OpGroupToPrivilegeHome og2pHome = HomeLocater.getOpGroupToPrivilegeHome();
	        while (iter.hasNext()) {
	            OpGroupToPrivilegeDTO dto = (OpGroupToPrivilegeDTO) iter.next();
	            og2pHome.create(dto);
	           
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG,"����", 
						"��������Ա��Ȩ����Ϣ OPGROUPIDΪ:" + dto.getOpGroupId() +",PRIVIDΪ:" + dto.getPrivId(), 
						
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
