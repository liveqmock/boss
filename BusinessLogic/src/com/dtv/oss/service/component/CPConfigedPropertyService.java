package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.CpConfigedProperty;
import com.dtv.oss.domain.CpConfigedPropertyHome;
import com.dtv.oss.domain.CpConfigedPropertyPK;
import com.dtv.oss.domain.ProductProperty;
import com.dtv.oss.domain.ProductPropertyHome;
import com.dtv.oss.domain.ProductPropertyPK;
import com.dtv.oss.dto.CpConfigedPropertyDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class CPConfigedPropertyService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=CPConfigedPropertyService.class;
	
	public CPConfigedPropertyService(ServiceContext s){
		this.context=s;
	}
	
	public void propertyValueAdd(CpConfigedPropertyDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ�����Ʒ����!");
			return;
		}
//		CpConfigedPropertyPK
		
		CpConfigedPropertyHome pHome=null;
		CpConfigedProperty p=null;
		try{
			pHome= HomeLocater.getCpConfigedPropertyHome();
			p=(CpConfigedProperty) pHome.create(dto);
			
			CustomerProductDTO custproductDto=BusinessUtility.getCustomerProductDTOByPsID(dto.getPsID());
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), custproductDto.getCustomerID(), 
	    			custproductDto.getAccountID(),
	    			custproductDto.getServiceAccountID(),
	    			custproductDto.getPsID(),
					SystemLogRecorder.LOGMODULE_CONFIG, "����", 
					"���Ӳ�Ʒ����ֵ,��ƷPsIDΪ��" + p.getPsID() +",����������Ϊ:" + p.getPropertyCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(DuplicateKeyException e){
			LogUtility.log(clazz,LogLevel.WARN, "������������ֵ�Ѿ����ڣ�");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("������������ֵ�Ѿ����ڣ�");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
		
	}
	
	public void propertyValueModify(CpConfigedPropertyDTO dto,int productID)throws ServiceException{
		if(dto==null || dto.getPsID()==0 || dto.getPropertyCode()==null||productID==0){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸Ĳ�Ʒ����ֵ!");
			return;
		}
		
		CpConfigedPropertyPK pPK=new CpConfigedPropertyPK(new Integer(dto.getPsID()),dto.getPropertyCode());
		
		CpConfigedPropertyHome pHome=null;
		CpConfigedProperty p=null;
		try{
			pHome= HomeLocater.getCpConfigedPropertyHome();
			p=pHome.findByPrimaryKey(pPK);

			String oldValue=p.getPropertyValue();
			ProductPropertyDTO ppDTO=BusinessUtility.getProductPropertyDTOByProductIDAndCode(productID,
					dto.getPropertyCode());
			
			CustomerProductDTO custproductDto=BusinessUtility.getCustomerProductDTOByPsID(dto.getPsID());
			
			dto.setDtLastmod(p.getDtLastmod());
			p.setDtLastmod(dto.getDtLastmod());
			p.setPropertyValue(dto.getPropertyValue());

			//������־
			String productName=BusinessUtility.getProductDescListByPSIDList(p.getPsID()+"");
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 
	    			((Integer)context.get(Service.CUSTOMER_ID)).intValue(), 
	    			custproductDto.getAccountID(),
	    			custproductDto.getServiceAccountID(),
	    			custproductDto.getPsID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "��Ʒ�����޸�", 
					"��Ʒ�����޸�,��Ʒ:" + productName + 
					SystemLogRecorder.appendDescString(",����:"+ppDTO.getPropertyName(),oldValue,dto.getPropertyValue()), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��λ����");
		}
	}
	
	public void propertyValueDelete(CpConfigedPropertyDTO dto) throws ServiceException{
		if(dto==null || dto.getPsID()==0 || dto.getPropertyCode()==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸Ĳ�Ʒ����ֵ!");
			return;
		}

		try{
			CpConfigedPropertyPK pPK=new CpConfigedPropertyPK(new Integer(dto.getPsID()),dto.getPropertyCode());
			CpConfigedProperty p=null;
			CpConfigedPropertyHome pHome=HomeLocater.getCpConfigedPropertyHome();

			p=pHome.findByPrimaryKey(pPK);
			p.remove();
			CustomerProductDTO custproductDto=BusinessUtility.getCustomerProductDTOByPsID(dto.getPsID());
				
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), custproductDto.getCustomerID(), 
	    			custproductDto.getAccountID(),
	    			custproductDto.getServiceAccountID(),
	    			custproductDto.getPsID(),
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��", 
					"ɾ����Ʒ����,��ƷpsIDΪ:" + dto.getPsID() +",���Ա���Ϊ:" + dto.getPropertyCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "�������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("�������");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��λ����");
		}
		catch(RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN, "ɾ������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("ɾ������");
		}
	}
	

}
