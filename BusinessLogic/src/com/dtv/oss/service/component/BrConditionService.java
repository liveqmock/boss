package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Brcondition;
import com.dtv.oss.domain.BrconditionHome;
import com.dtv.oss.dto.BrconditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class BrConditionService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=BrConditionService.class;
	
	public BrConditionService(ServiceContext s){
		this.context=s;
	}
	
	public void brConditionCreate(BrconditionDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "����֧��������ʼ");
		
		try{
			BrconditionHome brCondHome=HomeLocater.getBrconditionHome();
			Brcondition p=brCondHome.create(dto);
			
			context.put(Service.BRCONDITION_ID,p.getBrcntID());
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "����֧������", "����֧��������Ϣ,�´�����֧������IDΪ:" + p.getBrcntID().intValue(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "����֧����������");
	}
	
	public void brConditionModify(BrconditionDTO dto)throws ServiceException{
		if(dto==null || dto.getBrcntID()==0)
			throw new ServiceException("��������֧��������Ϣ����Ϊ�գ�");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸�֧��������Ϣ��ʼ");
				
		try{
			BrconditionHome brCondHome=HomeLocater.getBrconditionHome();
			Brcondition brc=brCondHome.findByPrimaryKey(new Integer(dto.getBrcntID()));
			
			if(brc.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"����֧����������Ϣ����");
	    		throw new ServiceException("����֧��������Ϣ����");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸�֧������", "�޸�֧��������Ϣ,�޸ĵ�֧������Ϊ:" +dto.getBrcntID(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "����֧����������");
	}
	
	public void brConditionDelete(BrconditionDTO dto) throws ServiceException{
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"�Ʒ�����ɾ��");
		try {
			BrconditionHome brCondHome=HomeLocater.getBrconditionHome();
			Brcondition br=brCondHome.findByPrimaryKey(new Integer(dto.getBrcntID()));
			br.remove();
//			������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    	        PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��֧������", "ɾ��֧��������Ϣ,ɾ����֧������Ϊ:" +dto.getBrcntID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		} catch (EJBException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�Ʒ�����ɾ���������");
			throw new ServiceException("�Ʒ�����ɾ���������");
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�Ʒ�����ɾ���������");
			throw new ServiceException("�Ʒ�����ɾ���������");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�Ʒ�����ɾ����λ����");
			throw new ServiceException("�Ʒ�����ɾ����λ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�Ʒ�����ɾ������");
			throw new ServiceException("�Ʒ�����ɾ������");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
