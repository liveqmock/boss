 
package com.dtv.oss.service.command.market;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.UserPointsExchangeGoods;
import com.dtv.oss.domain.UserPointsExchangeGoodsHome;
import com.dtv.oss.domain.UserPointsExchangerCd;
import com.dtv.oss.domain.UserPointsExchangerCdHome;
import com.dtv.oss.dto.UserPointsExchangerCdDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.component.BusinessRuleService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.UserPointEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

/**
 * �����û����ֶһ��ô���
 *  
 * date       : 2006-1-5
 * description:
 * @author Chen jiang
 *
 */
public class UserPointsCommand extends Command {
	private static final Class clazz = UserPointsCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
	protected UserPointsExchangerCd recd;
  
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    UserPointEJBEvent inEvent = (UserPointEJBEvent)ev;
	    this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    
	    LogUtility.log(clazz,LogLevel.DEBUG,"�����customerDtoΪ�� " + inEvent.getCustDto().toString());
	    LogUtility.log(clazz,LogLevel.DEBUG,"�����ruleDtoΪ��������������" + inEvent.getUserPointsExchRuleDto().toString());
	    try {
	    	switch (inEvent.getActionType()) {
	    		case EJBEvent.USERPOINTS_EXCHANGE:
	    			exchangeGoods(inEvent);
	    			break;
	    		 
    	    	 
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}
	
	 
	
	/**
	 * ���ֶһ���Ʒ
	 * @param event
	 * @throws CommandException
	 */
	private void exchangeGoods(UserPointEJBEvent event) throws ServiceException{
	    
		ServiceContext serviceContext=initServiceContext(event);
	    int goodsID=event.getUserPointsExchRuleDto().getExchangeGoodsTypeId();
	    int customerID=event.getCustDto().getCustomerID();
	    int currentPoints=event.getCustDto().getCurrentAccumulatePoint();
	    int totalPoints=event.getCustDto().getTotalAccumulatePoint();
	    int activityID=event.getUserPointsExchRuleDto().getActivityId();
	    //�ò�����ServiceContext
		if(event.getCustDto()!=null){
			serviceContext.put(Service.CUSTOMER_ID, new Integer(customerID));
			serviceContext.put(Service.CURRENT_POINTS,new Integer(currentPoints));
			serviceContext.put(Service.TOTAL_POINTS,new Integer(totalPoints));
			serviceContext.put(Service.CUSTOMER_TYPE,event.getCustDto().getCustomerType());
			serviceContext.put(Service.GOOD_ID,new Integer(goodsID));
			serviceContext.put(Service.ACTIVITY_ID,new Integer(activityID));
		}
	    
	    //���в�����ҵ���߼����
	    BusinessRuleService businessRuleService=new BusinessRuleService(serviceContext);
	    UserPointsExchangerCdDTO  recordDto= businessRuleService.processExchange();
	    LogUtility.log(clazz,LogLevel.DEBUG,"�����UserPointsExchangerCdDTOΪ��������������" + recordDto);
	    if(recordDto!=null)
	    	create(recordDto);
	    //����ϵͳ��־
		UserPointsExchangeGoodsHome goodsHome;
		UserPointsExchangeGoods goods=null;
		try {
			goodsHome = HomeLocater.getUserPointsExchangeGoodsHome();
			goods = goodsHome.findByPrimaryKey(new Integer(recordDto.getExchangeGoodsTypeId()));
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(serviceContext), 
	    	PublicService.getCurrentOperatorID(serviceContext),event.getCustDto().getCustomerID(), 
			SystemLogRecorder.LOGMODULE_CUSTSERV, "���ֶһ�", 
			"���ֶһ����һ������ID��"+event.getUserPointsExchRuleDto().getExchangeGoodsTypeId()
			+",�һ���Ʒ������:"+goods.getName()
			+",�һ���Ʒ������:"+recordDto.getExchangeGoodsAmount()
			+",ʹ�õ��Ļ���:"+recordDto.getExchangePoints(), 
			SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
	}
	
	 
	
	 
	 
	/**
	 * �������ּ�¼
	 * @param UserPointsExchangerCdDTO recordDto
	 * @throws ServiceException
	 */
	private void create(UserPointsExchangerCdDTO recordDto) throws ServiceException {
         
        try {
        	UserPointsExchangerCdHome home = HomeLocater.getUserPointsExchangerCdHome();
      
        	recd = home.create(recordDto);
             
        } catch (HomeFactoryException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("�������ֶһ�����");
        } catch (CreateException e) {
            LogUtility.log(this.getClass(), LogLevel.ERROR, e);
            throw new ServiceException("�������ֶһ�����");
        }
    }


	private ServiceContext initServiceContext(UserPointEJBEvent event){
		
		ServiceContext serviceContext=new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
	    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
	    return serviceContext;
	}
}
