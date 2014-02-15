package com.dtv.oss.service.component;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.ProductDependency;
import com.dtv.oss.domain.ProductDependencyHome;
import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ProductDependencyService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ProductDependencyService.class;
	
	public ProductDependencyService(ServiceContext s){
		this.context=s;
	}
	
	public void productDependencyCreate(ProductDependencyDTO dto)throws ServiceException{
		
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ�����Ʒ��ϵ!");
			return;
		}
		
		checkProductExist(dto);
		
		LogUtility.log(clazz,LogLevel.DEBUG, "������Ʒ��ϵ��ʼ");
		
		ProductDependency p=null;
		
		try{
			ProductDependencyHome pHome=HomeLocater.getProductDependencyHome();
			p=pHome.create(dto);
			
			//context.put(Service.PRODUCT_ID,p.getSeqNo());
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "������Ʒ��ϵ", 
					"������Ʒ��ϵ,��ƷIDΪ��" + p.getProductId() +",�����Ĳ�Ʒ��ϵIDΪ:" + p.getSeqNo().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��λ����");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "�����������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("�����������");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "������Ʒ��ϵ��Ϣ����");
		
	}
	
	public void productDependencyModify(ProductDependencyDTO dto)throws ServiceException{
		if(dto==null || dto.getSeqNo()==0){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸Ĳ�Ʒ��ϵ!");
			return;
		}
		
		checkProductExist(dto);
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸Ĳ�Ʒ��ϵ��ʼ ");
		ProductDependency p=null;
		
		try{
			ProductDependencyHome pHome=HomeLocater.getProductDependencyHome();
			p=pHome.findByPrimaryKey(new Integer(dto.getSeqNo()));
			if(p==null)
				throw new ServiceException("û�иù�ϵ�������Ϣ,���ܽ����޸�");
			
			if(p.ejbUpdate(dto)==-1)
				throw new ServiceException("�޸Ĳ�Ʒ��ϵʧ�ܣ�");
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ĳ�Ʒ��ϵ", 
					"�޸Ĳ�Ʒ��ϵ,��ƷIDΪ��" + p.getProductId() +",�޸ĵĲ�Ʒ��ϵIDΪ:" + p.getSeqNo().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "�����������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("�����������");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸Ĳ�Ʒ��ϵ����");
	}
	
	public void productDependencyDelete(String seqNosList)throws ServiceException{
		if(seqNosList==null || "".equals(seqNosList)){
			LogUtility.log(clazz,LogLevel.DEBUG, "ɾ�������ϢΪ�գ����ܽ��в�����");
			return;
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "ɾ����Ʒ��ϵ��ʼ ");
		
		String seNos[]=seqNosList.split(",");
		
		if(seNos==null || seNos.length==0){
			LogUtility.log(clazz,LogLevel.DEBUG, "û�в�Ʒ��ϵ��ţ����ܽ��в�����");
			return;
		}
		try{
			ProductDependency p=null;
			ProductDependencyHome pHome=HomeLocater.getProductDependencyHome();
			for(int i=0;i<seNos.length;i++){
				p=pHome.findByPrimaryKey(Integer.valueOf(seNos[i]));
				p.remove();
			}
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ����Ʒ��ϵ", 
					"ɾ����Ʒ��ϵ,��Ʒ��ϵ����Ϊ��" + seqNosList, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���ҳ���");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("���ҳ���");
		}
		catch(RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN, "ɾ������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("ɾ������");
		}		
		LogUtility.log(clazz,LogLevel.DEBUG, "ɾ����Ʒ��ϵ����");
	}
	
	
	private void checkProductExist(ProductDependencyDTO dto) throws ServiceException{
 
		
		String productIdStr=dto.getReferProductIDList();
		String[] productIdArr = productIdStr.split(",");
		List checkList = new ArrayList();
		for (int i=0 ; i<productIdArr.length;i++){
			if (productIdArr[i]!=null)
				checkList.add(productIdArr[i]);
		}
		if(checkList.contains(String.valueOf(dto.getProductId())))
			throw new ServiceException("��Ʒ"+ dto.getProductId() + "�ظ�");
				
	 }
	 
	 
	 
}
