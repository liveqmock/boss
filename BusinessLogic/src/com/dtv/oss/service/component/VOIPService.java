package com.dtv.oss.service.component;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.VOIPCondition;
import com.dtv.oss.domain.VOIPConditionHome;
import com.dtv.oss.domain.VOIPGateway;
import com.dtv.oss.domain.VOIPGatewayHome;
import com.dtv.oss.domain.VOIPGatewayPK;
import com.dtv.oss.domain.VOIPProduct;
import com.dtv.oss.domain.VOIPProductHome;
import com.dtv.oss.domain.VOIPProductPK;
import com.dtv.oss.dto.VOIPConditionDTO;
import com.dtv.oss.dto.VOIPGatewayDTO;
import com.dtv.oss.dto.VOIPProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class VOIPService extends AbstractService{
	public ServiceContext context=null;
	private static Class clazz=VOIPService.class;
	
	public VOIPService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VOIPService(ServiceContext context) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public void createVOIPProduct(VOIPProductDTO dto) throws ServiceException {
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ���������Ʒ!");
			return;
		}
		
		/*if(checkVOIPProductExist(dto))
			throw new ServiceException("��������Ʒ�Ѿ������ˣ��벻Ҫ�ظ����ã�");*/
		
		try {
			VOIPProductHome home = HomeLocater.getVOIPProductHome();
			VOIPProduct vProduct=home.create(dto);
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "����������Ʒ", 
					"����������Ʒ,��ƷIDΪ��" + vProduct.getProductID() +",��������Ϊ:" + vProduct.getSssrvCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		} catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(DuplicateKeyException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������������Ʒ�Ѿ����ڣ�");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������������Ʒ�Ѿ����ڣ�");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
	}
	
	public void modifyVOIPProduct(VOIPProductDTO dto)throws ServiceException{
		if(dto==null || dto.getProductID()==0 || dto.getSssrvCode()==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�������Ʒ����!");
			return;
		}
		/*if(!checkVOIPProductExist(dto))
			throw new ServiceException("��������Ʒ�����ڣ��޷������޸ģ�");*/
		VOIPProductPK pk=new VOIPProductPK(dto.getProductID(),dto.getSssrvCode());
		VOIPProductHome home=null;
		VOIPProduct pProduct=null;
		try{
			home=HomeLocater.getVOIPProductHome();
			pProduct=home.findByPrimaryKey(pk);
			
			if(pProduct.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸�������Ʒ���Գ���!");
				throw new ServiceException("����������Ʒ���Գ���");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸�������Ʒ", 
					"�޸�������Ʒ,��ƷIDΪ��" + pProduct.getProductID()+",��Ʒ��������Ϊ:" + pProduct.getSssrvCode(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
	}

	/*public boolean checkVOIPProductExist(VOIPProductDTO dto) {
		VOIPProductDTO pdto=BusinessUtility.getVOIPProductDTOByProductIDAndSCode(dto.getProductID(),dto.getSssrvCode());
		if(pdto==null)
			return false;
		else
			return true;
	}*/
	
	public void createVOIPCondition(VOIPConditionDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ�����������!");
			return;
		}
		
		/*if(checkVOIPConditionExist(dto))
			throw new ServiceException("�����������Ѿ������ˣ��벻Ҫ�ظ����ã�");*/
		
		try {
			VOIPConditionHome home = HomeLocater.getVOIPConditionHome();
			VOIPCondition vCondition=home.create(dto);
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "������������", 
					"������������,����IDΪ��" + vCondition.getConditionID() +",��������Ϊ:" + vCondition.getConditionName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		} catch(HomeFactoryException e){
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
	
	public void modifyVOIPCondition(VOIPConditionDTO dto)throws ServiceException{
		if(dto==null || dto.getConditionID()==0 ){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�������������!");
			return;
		}
		/*if(!checkVOIPConditionExist(dto))
			throw new ServiceException("�����������ڣ��޷������޸ģ�");*/
		
		VOIPConditionHome home=null;
		VOIPCondition vCondition=null;
		
		try{
			home=HomeLocater.getVOIPConditionHome();
			vCondition=home.findByPrimaryKey(new Integer(dto.getConditionID()));
			
			if(vCondition.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸�������������!");
				throw new ServiceException("����������������");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸���������", 
					"�޸���������,����IDΪ��" + vCondition.getConditionID()+",��������Ϊ:" + vCondition.getConditionName(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
	}

	/*public boolean checkVOIPConditionExist(VOIPConditionDTO dto) {
		VOIPConditionDTO pdto=BusinessUtility.getVOIPConditionDTOByConditionID(dto.getConditionID());
		if(pdto == null)
			return false;
		else
			return true;
	}*/
	
	public void createVOIPGateway(VOIPGatewayDTO dto) throws ServiceException {
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ�����������!");
			return;
		}
		
		/*if(checkVOIPGatewayExist(dto))
			throw new ServiceException("�����������Ѿ������ˣ��벻Ҫ�ظ����ã�");*/
		
		try {
			VOIPGatewayHome home = HomeLocater.getVOIPGatewayHome();
			VOIPGateway gateway=home.create(dto);
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "������������", 
					"������������,�豸�ͺ�Ϊ��" + gateway.getDeviceModel() +",�豸����Ϊ:" + gateway.getDevsType(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		} catch(HomeFactoryException e){
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
	
	public void modifyVOIPGateway(VOIPGatewayDTO dto,String prevDevsType)throws ServiceException{
		if(dto==null || dto.getDeviceModel()==null || dto.getDevsType()==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ������޸�������������!");
			return;
		}
		/*if(!checkVOIPGatewayExist(dto))
			throw new ServiceException("���������ò����ڣ��޷������޸ģ�");*/
		VOIPGatewayPK pk=new VOIPGatewayPK(dto.getDeviceModel(),prevDevsType);
		VOIPGatewayHome home=null;
		VOIPGateway gateway=null;
		try{
			
			System.out.println("\n"+pk.deviceModel+"������������������������������������"+pk.devsType);
			System.out.println(dto);
			
			
			
			home=HomeLocater.getVOIPGatewayHome();
			gateway=home.findByPrimaryKey(pk);
			String preDeviceModel=gateway.getDeviceModel();
			gateway.remove();
			
//			home=HomeLocater.getVOIPGatewayHome();			
			dto.setDtLastmod(new Timestamp(System.currentTimeMillis()));			
			home.create(dto);
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_NET, "�޸���������", 
					"�޸���������,�豸�ͺ�Ϊ��" + preDeviceModel+",�豸����Ϊ:" + prevDevsType, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
			
		}catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		} catch (RemoveException e) {
			LogUtility.log(clazz,LogLevel.WARN, "�h������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("�h������");
		} catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
	}

	/*public boolean checkVOIPGatewayExist(VOIPGatewayDTO dto) {
		VOIPGatewayDTO gdto=BusinessUtility.getVOIPGatewayDTOByPK(dto.getDeviceModel(),dto.getDevsType());
		if(gdto ==null)
			return false;
		else
			return true;
	}*/

}
