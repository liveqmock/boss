package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.MethodOfPayment;
import com.dtv.oss.domain.MethodOfPaymentHome;
import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class MethodOfPaymentService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=MethodOfPaymentService.class;
	
	public MethodOfPaymentService(ServiceContext s){
		this.context=s;
	}
	
	public void methodOfPaymentCreate(MethodOfPaymentDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�������ѷ�ʽ��ʼ");
		
		try{
			MethodOfPaymentHome methodHome=HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment m=methodHome.create(dto);
			
			 
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "����", "�������ѷ�ʽ,�¸��ѷ�ʽID=" + m.getMopID().intValue(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�������ѷ�ʽ����");
	}
	
	public void methodOfPaymentModify(MethodOfPaymentDTO dto)throws ServiceException{
		if(dto==null || dto.getMopID()==0)
			throw new ServiceException("�������󣬸��ѷ�ʽ��Ϣ����Ϊ�գ�");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸ĸ��ѷ�ʽ��Ϣ��ʼ");
				
		try{
			MethodOfPaymentHome methodHome=HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment method=methodHome.findByPrimaryKey(new Integer(dto.getMopID()));
			
			if(method.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"���¸��ѷ�ʽ����");
	    		throw new ServiceException("���¸��ѷ�ʽ��Ϣ����");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸�", "�޸ĸ��ѷ�ʽ��Ϣ,�޸ĵĸ��ѷ�ʽID=" +dto.getMopID(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���¸��ѷ�ʽ����");
	}
	
	public void methodOfPaymentDelete(MethodOfPaymentDTO dto) throws ServiceException{
		
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"���ѷ�ʽɾ��");
		try {
			MethodOfPaymentHome methodHome=HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment method=methodHome.findByPrimaryKey(new Integer(dto.getMopID()));
			method.remove();
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ��", "ɾ�����ѷ�ʽ��Ϣ,ɾ���ĸ��ѷ�ʽID=" +dto.getMopID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		} catch (EJBException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѷ�ʽɾ���������");
			throw new ServiceException("���ѷ�ʽɾ���������");
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѷ�ʽɾ���������");
			throw new ServiceException("���ѷ�ʽɾ���������");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѷ�ʽɾ����λ����");
			throw new ServiceException("���ѷ�ʽɾ����λ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѷ�ʽɾ������");
			throw new ServiceException("���ѷ�ʽɾ������");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
