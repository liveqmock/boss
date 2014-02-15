/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import com.dtv.oss.domain.CustomerBillingRule;
import com.dtv.oss.domain.CustomerBillingRuleHome;
import com.dtv.oss.domain.CustomerProduct;
import com.dtv.oss.domain.CustomerProductHome;
import com.dtv.oss.dto.CustomerBillingRuleDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class CustomerBillingRuleService extends AbstractService{
	private static final Class clazz = CustomerBillingRuleService.class;
	private ServiceContext context=null;
	public CustomerBillingRuleService(ServiceContext cxt){
		context=cxt;
	}
	
	/**
	 * �����ͻ����Ʒѹ���
	 * @param dto
	 * @throws ServiceException 
	 */
	public void createCustomerBillingRuleService(CustomerBillingRuleDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ͻ����Ʒѹ���,CustomerBillingRuleDTO==" + dto);
		//�������
		if(dto==null)
			throw new ServiceException("�����������");
		
		//������ݵ�������
		if(dto.getPsID()==0)
			throw new ServiceException("Ŀ���Ʒδ֪��");
		if(dto.getFeeType()==null || "".equals(dto.getFeeType()))
			throw new ServiceException("�������Ͳ���Ϊ�գ�");
		if(dto.getAcctItemTypeID()==null || "".equals(dto.getAcctItemTypeID()))
			throw new ServiceException("��Ŀ����ID����Ϊ�գ�");
		if(dto.getStartDate()==null || dto.getEndDate()==null)
			throw new ServiceException("��ֹʱ�䲻��Ϊ�գ�");
		
		//����Ƿ�����ص���ʱ������
		if(BusinessUtility.IsCustBillingRuleConflict(dto.getStartDate(),dto.getEndDate(),dto.getPsID(),0))
			throw new ServiceException("�ͻ����Ʒ����ڴ���ʱ���ϵ��ص���");
		
		//����
		dto.setBrRateType(CommonConstDefinition.BILLING_RULE_RATE_TYPE_V);
		//ȱʡΪ1���Ա㽫����չ
		dto.setEventClass(1);
		try{
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(dto.getPsID()));
			dto.setCustPackageID(cp.getReferPackageID());
			CustomerBillingRuleHome home=HomeLocater.getCustomerBillingRuleHome();
			CustomerBillingRule cbr=home.create(dto);
			
			StringBuffer logDes=new StringBuffer();
			logDes.append("���Ի�������ˮ�ţ�" + cbr.getId().intValue());
			logDes.append("���ͻ���Ʒ�ţ�" + cbr.getPsID());
			logDes.append("���������ͣ�" + BusinessUtility	.getCommonNameByKey("SET_F_BRFEETYPE",cbr.getFeeType()));
			logDes.append("����Ŀ����ID��" + cbr.getAcctItemTypeID());
			logDes.append("�����ʣ�" + cbr.getValue());
			logDes.append("����ʼʱ�䣺" + cbr.getStartDate().toString());
			logDes.append("������ʱ�䣺" + cbr.getEndDate().toString());
			
			//��־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), cp.getCustomerID(), 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "�½����Ի�����", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("�����ͻ����Ʒѹ���������������ԣ�");
		}		
	}
	/**
	 * �����ͻ����Ʒѹ���
	 * @param dto
	 * @throws ServiceException 
	 */
	public void createCustBillRuleServiceForOpen(CustomerBillingRuleDTO dto) throws ServiceException{
		//�������
		if(dto==null)
			throw new ServiceException("�����������");
		//����
		dto.setBrRateType(CommonConstDefinition.BILLING_RULE_RATE_TYPE_V);
		//ȱʡΪ1���Ա㽫����չ
		dto.setEventClass(1);
		try{
			CustomerBillingRuleHome home=HomeLocater.getCustomerBillingRuleHome();
			CustomerBillingRule cbr=home.create(dto);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("�����ͻ����Ʒѹ������!");
		}		
	}
		
	/**
	 * �޸Ŀͻ����Ʒѹ���
	 * @param dto
	 * @throws ServiceException 
	 */
	public void updateCustomerBillingRuleService(CustomerBillingRuleDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"��,CustomerBillingRuleDTO==" + dto);
		//�������
		if(dto==null)
			throw new ServiceException("�����������");
		
		//������ݵ�������
		if(dto.getId()==0)
			throw new ServiceException("û�и��Ի����ʱ�־���޷�����޸ģ�");
		if(dto.getStatus()==null ||"".equals(dto.getStatus()))
			throw new ServiceException("û��Ŀ��״̬���޷�����޸ģ�");
		if(!CommonConstDefinition.GENERALSTATUS_INVALID.equals(dto.getStatus())){
			if(dto.getPsID()==0)
				throw new ServiceException("Ŀ���Ʒδ֪��");
			if(dto.getFeeType()==null || "".equals(dto.getFeeType()))
				throw new ServiceException("�������Ͳ���Ϊ�գ�");
			if(dto.getAcctItemTypeID()==null || "".equals(dto.getAcctItemTypeID()))
				throw new ServiceException("��Ŀ����ID����Ϊ�գ�");
			if(dto.getStartDate()==null || dto.getEndDate()==null)
				throw new ServiceException("��ֹʱ�䲻��Ϊ�գ�");
			//����Ƿ�����ص���ʱ������
			if(BusinessUtility.IsCustBillingRuleConflict(dto.getStartDate(),dto.getEndDate(),dto.getPsID(),dto.getId()))
				throw new ServiceException("�ͻ����Ʒ����ڴ���ʱ���ϵ��ص���");
		}		
		
		//�޸�
		int result=1;
		StringBuffer logDes=new StringBuffer();
		try{
			CustomerBillingRuleHome home=HomeLocater.getCustomerBillingRuleHome();
			CustomerBillingRule cbr=home.findByPrimaryKey(new Integer(dto.getId()));
			CustomerProductHome cpHome=HomeLocater.getCustomerProductHome();
			CustomerProduct cp=cpHome.findByPrimaryKey(new Integer(cbr.getPsID()));
			
			logDes.append("���Ի�������ˮ�ţ�" + dto.getId());
			if(CommonConstDefinition.GENERALSTATUS_INVALID.equals(dto.getStatus()))
				logDes.append("��ȡ�����Ի�����");
			else{
				logDes.append(SystemLogRecorder.appendDescString(";��������:",
						BusinessUtility.getCommonNameByKey("SET_F_BRFEETYPE",cbr.getFeeType()), 
						BusinessUtility.getCommonNameByKey("SET_F_BRFEETYPE",dto.getFeeType())));
				logDes.append(SystemLogRecorder.appendDescString(";��Ŀ����ID:",cbr.getAcctItemTypeID(), dto.getAcctItemTypeID()));
				logDes.append(SystemLogRecorder.appendDescString(";����:","" + cbr.getValue(), "" + dto.getValue()));
				logDes.append(SystemLogRecorder.appendDescString(";��ʼʱ��:",cbr.getStartDate().toString(),dto.getStartDate().toString()));
				logDes.append(SystemLogRecorder.appendDescString(";����ʱ��:",cbr.getEndDate().toString(),dto.getEndDate().toString()));
				logDes.append(SystemLogRecorder.appendDescString(";״̬:",
						BusinessUtility.getCommonNameByKey("SET_G_GENERALSTATUS",cbr.getStatus()), 
						BusinessUtility.getCommonNameByKey("SET_G_GENERALSTATUS",dto.getStatus())));
			}
			
			result=cbr.ejbUpdate(dto);
			
			//��־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), cp.getCustomerID(), 
					SystemLogRecorder.LOGMODULE_CUSTSERV, "�޸ĸ��Ի�����", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("�޸Ŀͻ����Ʒѹ���������������ԣ�");
		}
		if(result==-1)
			throw new ServiceException("���Ի��������ݲ����������ݣ��޷����£������ԣ�");
	}
}
