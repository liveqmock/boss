package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.OpToGroup;
import com.dtv.oss.domain.OpToGroupHome;
import com.dtv.oss.domain.OpToGroupPK;
import com.dtv.oss.dto.OpToGroupDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigOpToGroupService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigOrgDistrictService.class;
	
	public ConfigOpToGroupService(ServiceContext s){
		this.context=s;
	}
	
	public void deleteOp2Group(Collection col)throws ServiceException{
		
		 if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		 try{
		  
		    Iterator iter = col.iterator();
		    OpToGroupHome opToGroupHome = HomeLocater.getOpToGroupHome();
	        while (iter.hasNext()) {
	        	
	        	OpToGroupDTO dto = (OpToGroupDTO) iter.next();
	        	OpToGroupPK pk = new OpToGroupPK(dto.getOperatorId(), dto.getOpGroupId());
	        	OpToGroup opToGroup = opToGroupHome.findByPrimaryKey(pk);
	        	opToGroup.remove();
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG,"ɾ������Ա��", 
						"ɾ������Ա����Ϣ operatorIDΪ:" + dto.getOperatorId() +",groupidΪ:" + dto.getOpGroupId(), 
						
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
	
	public void addOp2Group(Collection col)throws ServiceException{
		 if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		
		 try{
		    Iterator iter = col.iterator();
		    OpToGroupHome opToGroupHome = HomeLocater.getOpToGroupHome();
	        while (iter.hasNext()) {
	        	 OpToGroupDTO dto = (OpToGroupDTO) iter.next();
	        	 opToGroupHome.create(dto);
	           
				   
				 
	            SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
		    			PublicService.getCurrentOperatorID(context), 0, 
						SystemLogRecorder.LOGMODULE_CONFIG,"��������Ա��", 
						"��������Ա����ϢoperatorIDΪ:" + dto.getOperatorId()+",groupidΪ:" + dto.getOpGroupId(), 
						
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
