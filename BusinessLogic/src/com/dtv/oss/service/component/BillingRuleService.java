package com.dtv.oss.service.component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.BillingRule;
import com.dtv.oss.domain.BillingRuleHome;
import com.dtv.oss.domain.BillingRuleItemHome;
import com.dtv.oss.domain.CommonSettingDataHome;
import com.dtv.oss.domain.FeeSplitPlan;
import com.dtv.oss.domain.FeeSplitPlanHome;
import com.dtv.oss.domain.FeeSplitPlanItem;
import com.dtv.oss.domain.FeeSplitPlanItemHome;
import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.dto.FeeSplitPlanDTO;
import com.dtv.oss.dto.FeeSplitPlanItemDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class BillingRuleService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=BillingRuleService.class;
	
	public BillingRuleService(ServiceContext s){
		this.context=s;
	}
	
	public void billingRuleCreate(BillingRuleDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�������ѹ���ʼ");
		
		try{
			BillingRuleHome brHome=HomeLocater.getBillingRuleHome();
			BillingRule br=brHome.create(dto);
			
			 
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�������ѹ���", "�������ѹ���,�¸��ѹ���IDΪ:" + br.getId().intValue(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�������ѹ������");
	}
	
	public void billingRuleModify(BillingRuleDTO dto)throws ServiceException{
		if(dto==null)
			throw new ServiceException("�������󣬸��ѹ�����Ϣ����Ϊ�գ�");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸ĸ��ѹ�����Ϣ��ʼ");
				
		try{
			BillingRuleHome brHome=HomeLocater.getBillingRuleHome();
			BillingRule br=brHome.findByPrimaryKey(new Integer(dto.getId()));
			
			if(br.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"���¸��ѹ������");
	    		throw new ServiceException("���¸��ѹ�����Ϣ����");
			}
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸ĸ��ѹ���", "�޸ĸ��ѹ�����Ϣ,�޸ĵĸ��ѹ���IDΪ:" +dto.getId(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���¸��ѹ������");
	}
	
	public void billingRuleDelete(BillingRuleDTO dto) throws ServiceException{
		
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"���ѹ���ɾ��");
		canDelete(dto.getId()); 
		try {
			BillingRuleHome brHome=HomeLocater.getBillingRuleHome();
			BillingRule br=brHome.findByPrimaryKey(new Integer(dto.getId()));
			br.remove();
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ���޸ĸ��ѹ���", "ɾ�����ѹ�����Ϣ,ɾ���ĸ��ѹ���IDΪ:" +dto.getId(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		 
		} catch (EJBException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѹ���ɾ���������");
			throw new ServiceException("���ѹ���ɾ���������");
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѹ���ɾ���������");
			throw new ServiceException("���ѹ���ɾ���������");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѹ���ɾ����λ����");
			throw new ServiceException("���ѹ���ɾ����λ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѹ���ɾ������");
			throw new ServiceException("���ѹ���ɾ������");
		}
	}
	/**
	 * �ж��Ƿ��ܹ�ɾ��
	 * @param id
	 */
	private void canDelete(int id) throws ServiceException{
		try {
			
		  BillingRuleItemHome brItemHome=HomeLocater.getBillingRuleItemHome();
		  Collection item = brItemHome.findByBrId(id);
		  if(item!=null && item.size()>0)
		  	throw new ServiceException("������¼����ɾ��");
		
		  } catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѹ���ɾ���������");
			throw new ServiceException("���ѹ���ɾ���������");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ѹ���ɾ����λ����");
			throw new ServiceException("���ѹ���ɾ����λ����");
		}  
	}
	
	public void callRecaculate() throws ServiceException, HomeFactoryException {
        
       Connection con = null;
       CallableStatement stmt = null;
       try {
         con = HomeFactory.getFactory().getDataSource().getConnection();

         stmt = con.prepareCall("{call SP_F_CreateBillingRuleItem(?,?)}");
  
        
         stmt.registerOutParameter(1, Types.INTEGER);
		 stmt.registerOutParameter(2, Types.VARCHAR);
		 int result=stmt.executeUpdate();
		 int resInt = stmt.getInt(1);
			String resStr = stmt.getString(2);

			if (resInt < 0) {
				throw new ServiceException(resStr);
			}

         
  
      } catch (SQLException e) {
      	
        e.printStackTrace();
        
       throw new ServiceException(e);
       
      } finally {
     if (stmt != null) {
        try {
       stmt.close();
    } catch (SQLException ee) {
    }
    }
    if (con != null) {
     try {
     con.close();
     } catch (SQLException ee) {
   }
  }
 }
}
	public void feeSplitCreate(FeeSplitPlanDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���������ʷѿ�ʼ");
		
		try{
			FeeSplitPlanHome feeHome=HomeLocater.getFeeSplitPlanHome();
			FeeSplitPlan fee=feeHome.create(dto);
			
			 
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "���������ʷѼƻ�", "���������ʷѼƻ�,�·����ʷѼƻ�IDΪ:" + fee.getFeeSplitPlanID().intValue(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���������ʷѽ���");
	}
	public void feeSplitModify(FeeSplitPlanDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸ķ����ʷѿ�ʼ");
		
		try{
			FeeSplitPlanHome feeHome=HomeLocater.getFeeSplitPlanHome();
			 
			FeeSplitPlan fee=feeHome.findByPrimaryKey(dto.getFeeSplitPlanID());
			fee.setPlanName(dto.getPlanName());
			fee.setTotalTimeCycleNo(dto.getTotalTimeCycleNo());
			fee.setDtLastmod(new Timestamp(System.currentTimeMillis())); 
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸ķ����ʷѼƻ�", "�޸ķ����ʷѼƻ�,�޸ķ����ʷѼƻ�IDΪ:" + dto.getFeeSplitPlanID().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���ҷ����ʷѳ���");
    		throw new ServiceException("���ҷ����ʷѶ������");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���������ʷѽ���");
	}
	public void feeSplitItemModify(FeeSplitPlanItemDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸ķ��üƻ���Ŀ��ʼ");
		
		try{
			//zyg 2007.12.06 begin
			//�������̯���ƻ���Ŀ�޸ĵ�����
			canModifyFeeSplitItem(dto.getSeqNo().intValue(),dto.getFeeSplitPlanID(),dto.getTimeCycleNO());
			//checkFeeSplitItem(dto.getFeeSplitPlanID(),dto.getTimeCycleNO());
			//zyg 2007.12.06 end
			
			FeeSplitPlanItemHome feeItemHome=HomeLocater.getFeeSplitPlanItemHome();
			 
			FeeSplitPlanItem feeItem=feeItemHome.findByPrimaryKey(dto.getSeqNo());
			feeItem.setTimeCycleNO(dto.getTimeCycleNO());
			feeItem.setValue(dto.getValue());
			feeItem.setDtLastmod(new Timestamp(System.currentTimeMillis())); 
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸ķ����ʷѼƻ���Ŀ", "�޸ķ����ʷѼƻ���Ŀ,�޸ķ����ʷѼƻ���ĿseqΪ:" + dto.getSeqNo().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "���ҷ����ʷѼƻ���Ŀ����");
    		throw new ServiceException("���ҷ����ʷѼƻ���Ŀ�������");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸ķ��üƻ���Ŀ����");
	}
	public void feeSplitItemCreate(FeeSplitPlanItemDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�������üƻ���Ŀ��ʼ");
		
		try{
			checkFeeSplitItem(dto.getFeeSplitPlanID(),dto.getTimeCycleNO());
			FeeSplitPlanItemHome feeItemHome=HomeLocater.getFeeSplitPlanItemHome();
			
			FeeSplitPlanItem feeItem=feeItemHome.create(dto);
			
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "���������ʷѼƻ���Ŀ", "���������ʷѼƻ���Ŀ,�·����ʷѼƻ���ĿseqnoΪ:" + feeItem.getSeqNo().intValue(), 
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�������üƻ���Ŀ����");
	}
	
	/**
	 * ������̯���ƻ���Ŀ�Ƿ����ظ����ݣ���ͬһ̯���ƻ���,̯�����������ظ����֡�
	 * 
	 * @param key
	 * @param name
	 * @throws ServiceException
	 */
	private void checkFeeSplitItem(int feeSplitPlanID,int timeCycleNO) throws ServiceException {
			
			if(BusinessUtility.IsFeeSplitItem(feeSplitPlanID,timeCycleNO)){
				throw new ServiceException("��ͬһ̯���ƻ���,̯�����������ظ����֡�");
			}

	}

  //zyg 2007.12.06 begin 
  //�������̯���ƻ���Ŀ�޸ĵ�����
	/**
	 * ����ܷ��޸ķ���̯���ƻ���Ŀ�е�̯������ ����ͬһ̯���ƻ���,̯�����������ظ����֡�
	 * 
	 * @param seqno :̯���ƻ���Ŀ��¼��
	 * @param feeSplitPlanID : ̯���ƻ�ID
	 * @param timeCycleNO : ̯������
	 * @throws ServiceException
	 */
	private void canModifyFeeSplitItem(int seqno , int feeSplitPlanID, int timeCycleNO) throws ServiceException {
			
			if(BusinessUtility.IsFeeSplitItem(seqno,feeSplitPlanID,timeCycleNO)){
				throw new ServiceException("��ͬһ̯���ƻ���,̯�����������ظ����֡�");
			}

	}
  //zyg 2007.12.06 end
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
