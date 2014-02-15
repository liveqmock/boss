package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CryptoUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigOperatorService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ConfigOperatorService.class;
	
	public ConfigOperatorService(ServiceContext s){
		this.context=s;
	}
	
	public void operatorCreate(OperatorDTO dto)throws ServiceException{
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ�գ����ܴ�������Ա!");
			return;
		}
		
	  
		OperatorHome opHome=null;
		Operator oper=null;
		try{
			opHome=HomeLocater.getOperatorHome();
			String OriginalPwd = dto.getLoginPwd();
			//add by chenjiang 2007/02/05
			String loginID=dto.getLoginID();
			int count=BusinessUtility.getLoginIDCount(loginID);
			if(count>0){
				throw new ServiceException("�õ�¼�ʺ��Ѿ�����!");	
			}
			String enPwd = CryptoUtility.enpwd(OriginalPwd);
			dto.setLoginPwd(enPwd);
			oper=opHome.create(dto);
			 
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "��������Ա",
					"��������Ա,�´����Ĳ���ԱIDΪ:"+oper.getOperatorID(), 
					 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		 
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "��������");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("��������");
		}
		
	}
	
	public void operatorModify(OperatorDTO dto)throws ServiceException{
		if(dto==null && dto.getDtLastmod()==null){
			LogUtility.log(clazz,LogLevel.WARN,"����Ĳ���Ϊ��,����û����������޸�ʱ��,�����޸Ĳ���Ա!");
			return;
		}
		dto.setLoginPwd(CryptoUtility.enpwd(dto.getLoginPwd()));
		OperatorHome opHome=null;
		Operator oper=null;
		try{
//			add by chenjiang 2007/02/05
			String loginID=dto.getLoginID();
			
			opHome=HomeLocater.getOperatorHome();
			oper=opHome.findByPrimaryKey(new Integer(dto.getOperatorID()));
			String curLoginID=oper.getLoginID();
			if (!curLoginID.equals(loginID)) {
				int count = BusinessUtility.getLoginIDCount(loginID);
				if (count > 0) {
					throw new ServiceException("�õ�¼�ʺ��Ѿ�����!");
				}
			}
			if(oper.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�޸Ĳ���Ա����!");
				throw new ServiceException("���²���Ա����");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ĳ���Ա", 
					"�޸Ĳ���Ա,����ԱIDΪ��" + dto.getOperatorID(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���ҳ���");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("���ҳ���");
		}
	}
	
	 
	 
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
