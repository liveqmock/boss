/*
 * Created on 2005-10-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.command.csr;

import java.util.*;

import com.dtv.oss.domain.CatvTerminal;
import com.dtv.oss.domain.CatvTerminalHome;
import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.custom.CommonFeeAndPayObject;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.command.*;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.*;
import com.dtv.oss.service.ejbevent.csr.OpenAccountCheckEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.component.*;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CsrCheckCommand extends Command {
	private static final Class clazz = CsrCheckCommand.class;
	private int operatorID = 0;
	private String machineName = "";	
	CommandResponseImp response = null;
  
	public CommandResponse execute(EJBEvent ev) throws CommandException {
	    response = new CommandResponseImp(null);
	    OpenAccountCheckEJBEvent inEvent = (OpenAccountCheckEJBEvent)ev;
	    operatorID = inEvent.getOperatorID();
	    machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");
	    try {
	    	switch (inEvent.getActionType()) {
	    		//��鿪��ʱ���û������û��˻����Ź�����Ϣ
	    		case OpenAccountCheckEJBEvent.CHECK_CUSTOMERINFO:
	    			checkAccountAndCustomerInfo(inEvent);
	    			break;
	    		//�����ѡ��Ŀͻ���Ʒ���Żݵ���Ϣ
	    		case OpenAccountCheckEJBEvent.CHECK_PRODUCTPG_CAMPAINPG:
	    			checkAccountAndCustomerInfo(inEvent);
	    			break;
	    		//���ͻ���Բ�Ʒ��Ӧ��Ӳ���豸��Ϣ
	    		case OpenAccountCheckEJBEvent.CHECK_HARDPRODUCTINFO:
	    			checkAccountAndCustomerInfo(inEvent);
	    			break;
	    		//��������Ϣ�Ƿ�׼ȷ
	    		case OpenAccountCheckEJBEvent.CHECK_FREEINFO:
	    			checkAccountAndCustomerInfo(inEvent);
	    			break;
	    		//����ģ����ӿ�������
	    		case OpenAccountCheckEJBEvent.CACULATE_OPEN_CATV_FEE:
	    			checkAccountAndCustomerInfo(inEvent);	
	    			break;
	    		//���ģ����ӿ���
	    		case OpenAccountCheckEJBEvent.CHECK_OPEN_ACCOUNT_CATV:
	    			checkAccountAndCustomerInfo(inEvent);	
	    			break;
	    		default:
	    			throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	    	}
	    } catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    } catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}
	
	/**
	 * ���ԤԼ��������ԤԼ��ԤԼ�������ŵ꿪��ʱ��Ϣ
	 * @param event
	 * @throws ServiceException
	 */
	private void checkAccountAndCustomerInfo(OpenAccountCheckEJBEvent event) throws ServiceException{
		//�ϲ��ײͻ��ߴ�����Ӧ�Ĳ�Ʒ����event��
		if(event.getCsiPackageArray()==null)
			event.setCsiPackageArray(new ArrayList());
		event.getCsiPackageArray().addAll(BusinessUtility.getBundle2CampaignPackageCol(event.getCsiCampaignArray()));
		
		BusinessRuleService businessRuleService=new BusinessRuleService(initServiceContext(event));
		businessRuleService.checkOpenInfo(event);
		
		if(event.getActionType()==EJBEvent.CHECK_OPEN_ACCOUNT_CATV){
			if(event.getCatvID()!=null && !"".equals(event.getCatvID())){
				if(!BusinessUtility.IsExistCatvID(event.getCatvID())){
					String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
					if(catvName==null || "".equals(catvName))
						catvName="�ն˱��";
					throw new ServiceException(catvName +"������");
				}
				//���  �Ѿ��������ͻ� ���ն� �����������½���ģ����ӿ�����
				if(BusinessUtility.IsCustUseCatvID(event.getCatvID())){
					String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
					if(catvName==null || "".equals(catvName))
						catvName="�ն˱��";
					throw new ServiceException(catvName +"�Ѿ���ʹ��");
				}
			}

			
			return;
		}
		
		if(event.getActionType()==EJBEvent.CACULATE_OPEN_CATV_FEE){
			if(event.getCatvID()!=null && !"".equals(event.getCatvID())){
				if(!BusinessUtility.IsExistCatvID(event.getCatvID())){
					String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
					if(catvName==null || "".equals(catvName))
						catvName="�ն˱��";
					throw new ServiceException(catvName +"������");
				}
				//���  �Ѿ��������ͻ� ���ն� �����������½���ģ����ӿ�����
				if(BusinessUtility.IsCustUseCatvID(event.getCatvID())){
					String catvName=BusinessUtility.getFieldNameByFieldInterID("CUSTOMER_CATVID");
					if(catvName==null || "".equals(catvName))
						catvName="�ն˱��";
					throw new ServiceException(catvName +"�Ѿ���ʹ��");
				}
			}
			this.response.setPayload(calculateImmediateFee(event));
			return;
		}
		
		 //���ͻ���½ID
	    if(event.getNewCustInfo().getLoginID()!=null&&(!event.getNewCustInfo().getLoginID().equals(""))){
	       	if(BusinessUtility.isExitMultiLoginID(0,event.getNewCustInfo().getLoginID())){
	       		throw new ServiceException("������ͻ���½ID����ID���Ѿ����ڣ�");
	    	}
	    }
			
		if (event.getActionType()==EJBEvent.CHECK_CUSTOMERINFO && 
				event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CO)){
			this.response.setPayload(calculateImmediateFee(event));
			return;
		}
		
		if(event.getActionType()==EJBEvent.CHECK_PRODUCTPG_CAMPAINPG && 
				event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK)){
			this.response.setPayload(calculateImmediateFee(event));
		}
		
		//�ж��Ƿ���Ҫ�����豸����,���� �ŵ꿪����ԤԼ����
		if(event.getActionType()==EJBEvent.CHECK_PRODUCTPG_CAMPAINPG && 
				(event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS)||
						event.getCsiDto().getType().equals(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB))){
			
			Collection  deviceClassList =PublicService.getDependencyDeviceClassBygetCsiPackageArray(event.getCsiDto(),event.getDetailNo(),
					event.getCsiPackageArray());
			
			//2008-04-21 ��ò�Ʒ�����б� ��ֻҪ��Ʒ���������ã��ͻ���ʾ��ѡ����ӦӲ���豸��ҳ��
			Collection  productPropertyList =PublicService.getProductPropertyArray(event.getCsiPackageArray());
			
            //�����Ӳ���豸�б������豸�б�û�з��ط����б� add by david.Yang   //2008-04-21 ��ò�Ʒ�����б� ��ֻҪ��Ʒ���������ã��ͻ���ʾ��ѡ����ӦӲ���豸��ҳ��
			if ((deviceClassList !=null && !deviceClassList.isEmpty()) || (productPropertyList !=null && !productPropertyList.isEmpty()))
			{
			    this.response.setPayload(deviceClassList);
			    this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_DEVICE);
			}
			//ֱ�ӷ��ط����б�
			else 
			{
				this.response.setPayload(calculateImmediateFee(event));
				 this.response.setFlowHandler(CommonConstDefinition.COMMAND_ID_IMMEDIATEFEE);
			}
		}
		//���������Ӳ���豸�Ļ��ͱ���������
		if(event.getActionType()==EJBEvent.CHECK_HARDPRODUCTINFO||
		   event.getActionType()==EJBEvent.CHECK_FREEINFO	){
			this.response.setPayload(calculateImmediateFee(event));
		}
	}
	private Collection calculateImmediateFee(OpenAccountCheckEJBEvent event)throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"������������csi="+event.getCsiDto());
		LogUtility.log(clazz,LogLevel.DEBUG,"������������NewCustInfo="+event.getNewCustInfo());
		LogUtility.log(clazz,LogLevel.DEBUG,"������������NewCustAcctInfo="+event.getNewCustAcctInfo());
		CommonFeeAndPayObject commonObj=PublicService.encapsulateBaseParamForOpen(event.getCsiDto(),event.getNewCustInfo(),event.getNewCustAcctInfo());
		//�����Ƿ����Ź���Ϣ�����Ź�id���Ź�ȯ�������ײ�id
		if(event.getDetailNo()!=null && !"".equals(event.getDetailNo())){
			commonObj.setGroupBargainID(BusinessUtility.getGroupBargainDetailByNO(event.getDetailNo()).getGroupBargainID());
		}
		//Ѻ��
		commonObj.setDeposit(event.getForcedDeposit());
		
		//�����ģ����ӿ�������Ҫ�����ն���Ϣ,add by jason 2007-07-27
		if(event.getActionType()==EJBEvent.CACULATE_OPEN_CATV_FEE){
			LogUtility.log(clazz,LogLevel.DEBUG,"������������ģ����ӿ���������ã���ʼ��װ�ն���Ϣ");
			
			if(event.getCsiDto()!=null)
				commonObj.setInstallationType(event.getCsiDto().getInstallationType());
			
			commonObj.setDestPortNum(event.getAddPortNum());
			//��������ն�
			try{
				if(BusinessUtility.IsExistCatvID(event.getCatvID())){
					CatvTerminalHome catvTerminalHome=HomeLocater.getCatvTerminalHome();
					CatvTerminal catvTerminal=catvTerminalHome.findByPrimaryKey(event.getCatvID());
					commonObj.setCatvTermType(catvTerminal.getCatvTermType());
					commonObj.setCableType(catvTerminal.getCableType());
					commonObj.setBiDirectionFlag(catvTerminal.getBiDirectionFlag());
					commonObj.setOrgPortNum(catvTerminal.getPortNo());
					
					//����������˿�
					if(event.getAddPortNum()>0)
						commonObj.setInstallOnOrgPort(CommonConstDefinition.CATV_OPEN_TYPE_A);
					else
						commonObj.setInstallOnOrgPort(CommonConstDefinition.CATV_OPEN_TYPE_O);
				}
				else{
					CatvTerminalDTO catvTerminalDTO=event.getCatvTerminalDTO();
					if(catvTerminalDTO!=null){
						commonObj.setCatvTermType(catvTerminalDTO.getCatvTermType());
						commonObj.setCableType(catvTerminalDTO.getCableType());
						commonObj.setBiDirectionFlag(catvTerminalDTO.getBiDirectionFlag());
						commonObj.setOrgPortNum(0);
						commonObj.setInstallOnOrgPort(CommonConstDefinition.CATV_OPEN_TYPE_NEW);
					}
				}
			}
			catch(Exception e){
				LogUtility.log(clazz,LogLevel.WARN,"������������ģ����ӿ����쳣="+e);
				throw new ServiceException("ģ����ӿ����ն���Ϣ��ȫ���޷���ɼƷ�");
			}
			LogUtility.log(clazz,LogLevel.DEBUG,"������������ģ����ӿ���������ã�������װ�ն���Ϣ��" + commonObj.toString());		
		}
		
		Collection currentFeeList=FeeService.calculateImmediateFeeForOpen(event.getCsiDto(),event.getCsiPackageArray(), event.getCsiCampaignArray(),commonObj);
		return currentFeeList;
	}
	
	private ServiceContext initServiceContext(OpenAccountCheckEJBEvent event){
		ServiceContext context=new ServiceContext();
		context.put(Service.OPERATIOR_ID, new Integer(event.getOperatorID()));
		context.put(Service.REMOTE_HOST_ADDRESS, event.getRemoteHostAddress());
		return context;
	}
}
