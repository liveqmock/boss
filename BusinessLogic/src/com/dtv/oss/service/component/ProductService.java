package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Product;
import com.dtv.oss.domain.ProductHome;
import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ProductService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ProductService.class;
	
	public ProductService(ServiceContext s){
		this.context=s;
	}
	
	public void productBaseInfoCreate(ProductDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "������Ʒ��Ϣ��ʼ");
		
		try{
			ProductHome pHome=HomeLocater.getProductHome();
			Product p=pHome.create(dto);
			
			context.put(Service.PRODUCT_ID,p.getProductID());
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "������Ʒ��Ϣ", "������Ʒ��Ϣ,�´����Ĳ�ƷID=" + p.getProductID().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "�����������");
    		throw new ServiceException("�����������");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "������Ʒ��Ϣ����");
	}
	
	public void productBaseInfoModify(ProductDTO dto)throws ServiceException{
		if(dto==null || dto.getProductID()==0)
			throw new ServiceException("�������󣬲�Ʒ��Ϣ����Ϊ�գ�");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸Ĳ�Ʒ��Ϣ��ʼ");
				
		try{
			ProductHome pHome=HomeLocater.getProductHome();
			Product p=pHome.findByPrimaryKey(new Integer(dto.getProductID()));
			
			if(p.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"���²�Ʒ��Ϣ����");
	    		throw new ServiceException("û�������ϴθ���ʱ�䣡");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ĳ�Ʒ��Ϣ", "�޸Ĳ�Ʒ��Ϣ,�޸ĵĲ�ƷID=" +dto.getProductID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "�����������");
    		throw new ServiceException("�����������");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸Ĳ�Ʒ��Ϣ����");
	}
	
	public void productBaseInfoDelete(ProductDTO productDTO) throws ServiceException {
		if(productDTO==null||productDTO.getProductID()==0)
			throw new ServiceException("�������󣬲�Ʒ��Ϣ����Ϊ�գ�");
		
		LogUtility.log(clazz,LogLevel.DEBUG,"ɾ����Ʒ��Ϣ��ʼ");
		
		try{
			ProductHome home=HomeLocater.getProductHome();
			Product product=home.findByPrimaryKey(new Integer(productDTO.getProductID()));
			product.remove();
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
					PublicService.getCurrentOperatorID(context),0,
					SystemLogRecorder.LOGMODULE_CONFIG,"ɾ����Ʒ��Ϣ","ɾ����Ʒ��Ϣ��ɾ���Ĳ�ƷIDΪ��"+productDTO.getProductID(),
					SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���Ҳ�Ʒ�������");
    		throw new ServiceException("���Ҳ�Ʒ�������");
		}catch (RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN,"ɾ����Ʒ�������");
			throw new ServiceException("ɾ����Ʒ�������");
		}
	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
