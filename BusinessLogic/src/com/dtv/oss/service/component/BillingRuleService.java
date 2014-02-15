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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建付费规则开始");
		
		try{
			BillingRuleHome brHome=HomeLocater.getBillingRuleHome();
			BillingRule br=brHome.create(dto);
			
			 
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建付费规则", "创建付费规则,新付费规则ID为:" + br.getId().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建付费规则结束");
	}
	
	public void billingRuleModify(BillingRuleDTO dto)throws ServiceException{
		if(dto==null)
			throw new ServiceException("参数错误，付费规则信息不能为空！");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改付费规则信息开始");
				
		try{
			BillingRuleHome brHome=HomeLocater.getBillingRuleHome();
			BillingRule br=brHome.findByPrimaryKey(new Integer(dto.getId()));
			
			if(br.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"更新付费规则出错。");
	    		throw new ServiceException("更新付费规则信息出错！");
			}
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改付费规则", "修改付费规则信息,修改的付费规则ID为:" +dto.getId(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "更新付费规则结束");
	}
	
	public void billingRuleDelete(BillingRuleDTO dto) throws ServiceException{
		
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"付费规则删除");
		canDelete(dto.getId()); 
		try {
			BillingRuleHome brHome=HomeLocater.getBillingRuleHome();
			BillingRule br=brHome.findByPrimaryKey(new Integer(dto.getId()));
			br.remove();
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除修改付费规则", "删除付费规则信息,删除的付费规则ID为:" +dto.getId(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		 
		} catch (EJBException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费规则删除服务出错！");
			throw new ServiceException("付费规则删除服务出错");
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费规则删除服务出错！");
			throw new ServiceException("付费规则删除服务出错");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费规则删除定位出错！");
			throw new ServiceException("付费规则删除定位出错");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费规则删除出错！");
			throw new ServiceException("付费规则删除出错");
		}
	}
	/**
	 * 判断是否能够删除
	 * @param id
	 */
	private void canDelete(int id) throws ServiceException{
		try {
			
		  BillingRuleItemHome brItemHome=HomeLocater.getBillingRuleItemHome();
		  Collection item = brItemHome.findByBrId(id);
		  if(item!=null && item.size()>0)
		  	throw new ServiceException("该条记录不能删除");
		
		  } catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费规则删除服务出错！");
			throw new ServiceException("付费规则删除服务出错");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "付费规则删除定位出错！");
			throw new ServiceException("付费规则删除定位出错");
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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建费用资费开始");
		
		try{
			FeeSplitPlanHome feeHome=HomeLocater.getFeeSplitPlanHome();
			FeeSplitPlan fee=feeHome.create(dto);
			
			 
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建费用资费计划", "创建费用资费计划,新费用资费计划ID为:" + fee.getFeeSplitPlanID().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建费用资费结束");
	}
	public void feeSplitModify(FeeSplitPlanDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改费用资费开始");
		
		try{
			FeeSplitPlanHome feeHome=HomeLocater.getFeeSplitPlanHome();
			 
			FeeSplitPlan fee=feeHome.findByPrimaryKey(dto.getFeeSplitPlanID());
			fee.setPlanName(dto.getPlanName());
			fee.setTotalTimeCycleNo(dto.getTotalTimeCycleNo());
			fee.setDtLastmod(new Timestamp(System.currentTimeMillis())); 
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改费用资费计划", "修改费用资费计划,修改费用资费计划ID为:" + dto.getFeeSplitPlanID().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "查找费用资费出错");
    		throw new ServiceException("查找费用资费对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建费用资费结束");
	}
	public void feeSplitItemModify(FeeSplitPlanItemDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改费用计划条目开始");
		
		try{
			//zyg 2007.12.06 begin
			//处理费用摊销计划条目修改的问题
			canModifyFeeSplitItem(dto.getSeqNo().intValue(),dto.getFeeSplitPlanID(),dto.getTimeCycleNO());
			//checkFeeSplitItem(dto.getFeeSplitPlanID(),dto.getTimeCycleNO());
			//zyg 2007.12.06 end
			
			FeeSplitPlanItemHome feeItemHome=HomeLocater.getFeeSplitPlanItemHome();
			 
			FeeSplitPlanItem feeItem=feeItemHome.findByPrimaryKey(dto.getSeqNo());
			feeItem.setTimeCycleNO(dto.getTimeCycleNO());
			feeItem.setValue(dto.getValue());
			feeItem.setDtLastmod(new Timestamp(System.currentTimeMillis())); 
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改费用资费计划条目", "修改费用资费计划条目,修改费用资费计划条目seq为:" + dto.getSeqNo().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "查找费用资费计划条目出错");
    		throw new ServiceException("查找费用资费计划条目对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改费用计划条目结束");
	}
	public void feeSplitItemCreate(FeeSplitPlanItemDTO dto)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建费用计划条目开始");
		
		try{
			checkFeeSplitItem(dto.getFeeSplitPlanID(),dto.getTimeCycleNO());
			FeeSplitPlanItemHome feeItemHome=HomeLocater.getFeeSplitPlanItemHome();
			
			FeeSplitPlanItem feeItem=feeItemHome.create(dto);
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建费用资费计划条目", "创建费用资费计划条目,新费用资费计划条目seqno为:" + feeItem.getSeqNo().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建费用计划条目结束");
	}
	
	/**
	 * 检查费用摊消计划条目是否有重复数据，在同一摊消计划下,摊消数量不能重复出现。
	 * 
	 * @param key
	 * @param name
	 * @throws ServiceException
	 */
	private void checkFeeSplitItem(int feeSplitPlanID,int timeCycleNO) throws ServiceException {
			
			if(BusinessUtility.IsFeeSplitItem(feeSplitPlanID,timeCycleNO)){
				throw new ServiceException("在同一摊销计划下,摊销数量不能重复出现。");
			}

	}

  //zyg 2007.12.06 begin 
  //处理费用摊销计划条目修改的问题
	/**
	 * 检查能否修改费用摊消计划条目中的摊销数量 ，在同一摊消计划下,摊消数量不能重复出现。
	 * 
	 * @param seqno :摊消计划条目记录号
	 * @param feeSplitPlanID : 摊销计划ID
	 * @param timeCycleNO : 摊销数量
	 * @throws ServiceException
	 */
	private void canModifyFeeSplitItem(int seqno , int feeSplitPlanID, int timeCycleNO) throws ServiceException {
			
			if(BusinessUtility.IsFeeSplitItem(seqno,feeSplitPlanID,timeCycleNO)){
				throw new ServiceException("在同一摊销计划下,摊销数量不能重复出现。");
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
