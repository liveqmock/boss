package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.ProductProperty;
import com.dtv.oss.domain.ProductPropertyHome;
import com.dtv.oss.domain.ProductPropertyPK;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ProductPropertyService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ProductPropertyService.class;
	
	public ProductPropertyService(ServiceContext s){
		this.context=s;
	}
	
	public void productPropertyCreate(ProductPropertyDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ�����Ʒ����!");
			return;
		}
		
		if(checkProductPopertyExist(dto))
			throw new ServiceException("�������Ѿ������ˣ��벻Ҫ�ظ����ã�");
		
		ProductPropertyHome pHome=null;
		ProductProperty p=null;
		try{
			pHome=HomeLocater.getProductPropertyHome();
			p=pHome.create(dto);
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "����", 
					"���Ӳ�Ʒ����,��ƷIDΪ��" + p.getProductId() +",������Ϊ:" + p.getPropertyName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(DuplicateKeyException e){
			LogUtility.log(clazz,LogLevel.WARN, "�����������������Ѿ����ڣ�");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("�����������������Ѿ����ڣ�");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
		
	}
	
	public void productPropertyModify(ProductPropertyDTO dto)throws ServiceException{
		if(dto==null || dto.getProductId()==0 || dto.getPropertyName()==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸Ĳ�Ʒ����!");
			return;
		}
		
		if(!checkProductPopertyExist(dto))
			throw new ServiceException("�����Բ����ڣ��޷������޸ģ�");
		
		ProductPropertyPK pPK=new ProductPropertyPK(dto.getProductId(),dto.getPropertyName());

		ProductPropertyHome pHome=null;
		ProductProperty p=null;
		try{
			pHome=HomeLocater.getProductPropertyHome();
			p=pHome.findByPrimaryKey(pPK);
			if(p.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸Ĳ�Ʒ���Գ���!");
				throw new ServiceException("���²�Ʒ���Գ���");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸�", 
					"�޸Ĳ�Ʒ����,��ƷIDΪ��" + p.getProductId() +",������Ϊ:" + p.getPropertyName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
	}
	
	public void productPropertyDelete(ProductPropertyDTO dto,String propertyNamesList) throws ServiceException{
		if(dto.getProductId()==0 || propertyNamesList==null || "".equals(propertyNamesList)){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���������������ɾ����Ʒ����!");
			return;
		}

		String propertyNames[]=propertyNamesList.split(",");
		try{
			ProductPropertyPK pPK=null;
			ProductProperty p=null;
			ProductPropertyHome pHome=HomeLocater.getProductPropertyHome();
			for(int i=0;i<propertyNames.length;i++){
				pPK=new ProductPropertyPK(dto.getProductId(),propertyNames[i]);
				p=pHome.findByPrimaryKey(pPK);
				p.remove();
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��", 
					"ɾ����Ʒ����,��ƷIDΪ:" + dto.getProductId() +",������Ϊ:" + propertyNamesList, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "ɾ������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("ɾ������");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "ɾ������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("ɾ������");
		}
		catch(RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN, "ɾ������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("ɾ������");
		}
	}
	
	private boolean checkProductPopertyExist(ProductPropertyDTO dto){
		ProductPropertyDTO pdto=BusinessUtility.getProductPropertyDTOByProductIDAndName(dto.getProductId(),dto.getPropertyName());
		
		if(pdto==null)
			return false;
		else 
			return true;
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
