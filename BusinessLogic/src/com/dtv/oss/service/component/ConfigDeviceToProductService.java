package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.DeviceMatchToProductHome;
import com.dtv.oss.domain.DeviceMatchToProductPK;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.domain.DeviceMatchToProduct;
import com.dtv.oss.dto.DeviceMatchToProductDTO;

public class ConfigDeviceToProductService {
	ServiceContext context=null;
	private static Class clazz=ConfigDeviceToProductService.class;

	public ConfigDeviceToProductService(ServiceContext context) {
		this.context=context;
	}

	public void addDeviceModel(Collection col) throws ServiceException{
		if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		try{
		    Iterator itr = col.iterator();
		    DeviceMatchToProductHome home=HomeLocater.getDeviceMatchToProductHome();
		    while(itr.hasNext()){
		    	DeviceMatchToProduct dmtp=home.create((DeviceMatchToProductDTO) itr.next());
		    	
		    	SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
		    	PublicService.getCurrentOperatorID(context),0,
		    	SystemLogRecorder.LOGMODULE_CONFIG,"������Ʒ��Ӧ���豸��Ϣ","��Ʒ��Ϊ��"+dmtp.getProductId()+" �������豸�ͺ�Ϊ��"+dmtp.getDeviceModel(),
		    	SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
		    }
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		} catch (CreateException e) {
			LogUtility.log(clazz,LogLevel.WARN,"������Ʒ��Ӧ�豸��Ϣ����");
			throw new ServiceException("������Ʒ��Ӧ�豸��Ϣ����");
		}
	}

	public void deleteDeviceModel(Collection col) throws ServiceException{
		if (col == null || col.size() == 0) {
		 	LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��!");
			return;
        }
		try{
			Iterator itr=col.iterator();
			DeviceMatchToProductHome home=HomeLocater.getDeviceMatchToProductHome();
			while(itr.hasNext()){
				DeviceMatchToProductDTO dto=(DeviceMatchToProductDTO)itr.next();
				DeviceMatchToProductPK pk=new DeviceMatchToProductPK(dto.getProductId(),dto.getDeviceModel());
				DeviceMatchToProduct dmtp=home.findByPrimaryKey(pk);
				dmtp.remove();
				
				SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
						PublicService.getCurrentOperatorID(context),0,
						SystemLogRecorder.LOGMODULE_CONFIG,"ɾ����Ʒ��Ӧ���豸��Ϣ","ɾ����Ʒ��Ϊ��"+dto.getProductId()+" ��Ӧ���豸�ͺţ�"+dto.getDeviceModel(),
						SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
			}
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz,LogLevel.WARN,"���ҳ���");
			throw new ServiceException("���ҳ���");
		} catch (RemoveException e1) {
			LogUtility.log(clazz,LogLevel.WARN,"ɾ����Ʒ��Ӧ���豸��Ϣ����");
			throw new ServiceException("ɾ����Ʒ��Ӧ���豸��Ϣ����");
		}
	}
}
