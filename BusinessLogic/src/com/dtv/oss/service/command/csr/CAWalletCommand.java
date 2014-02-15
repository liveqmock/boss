/*
 * Created on 2007-9-26
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.command.csr;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAWalletChargeRecordDTO;
import com.dtv.oss.dto.CAWalletDTO;
import com.dtv.oss.dto.CsiProcessLogDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
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
import com.dtv.oss.service.component.CSIService;
import com.dtv.oss.service.component.ServiceAccountService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CAWalletEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.service.util.SystemLogRecorder;
import java.math.BigDecimal;
/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CAWalletCommand extends Command {

	private static final Class clazz = CAWalletCommand.class;

	private int operatorID = 0;
	
	private ServiceContext context; 

	private String machineName = "";

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CAWalletEJBEvent inEvent = (CAWalletEJBEvent) ev;
		this.operatorID = inEvent.getOperatorID();
		this.machineName = inEvent.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter " + clazz.getName()
				+ " execute() now.");
		try {
			switch (inEvent.getActionType()) {
			
			case EJBEvent.IPPV_CREATE:
				createCAWallet(inEvent);
			break;
			case EJBEvent.IPPV_CHARGE:
				chargeCAWallet(inEvent);
			break;
			default:
				throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		}catch(ServiceException ce) {
			ce.printStackTrace();
		LogUtility.log(clazz, LogLevel.ERROR, this, ce);
		throw new CommandException(ce.getMessage());
	} catch (Throwable unkown) {
		unkown.printStackTrace();
		LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
		throw new CommandException("未知错误。");
	}
		return response;
	}

	/**
	 * @param inEvent
	 */
	private void chargeCAWallet(CAWalletEJBEvent inEvent)throws ServiceException, FinderException, HomeFactoryException, CreateException {
		context = initServiceContext(inEvent);
		
		ServiceAccountService service = new ServiceAccountService(context);
		int seqno =0;
		String doPost = inEvent.getDoPost();
		if("false".equalsIgnoreCase(doPost)){
			int point = service.calculatePointForIPPVCharge(inEvent);
			response.setPayload(new Integer(point));
			return;
		}else if("true".equalsIgnoreCase(doPost)){
			seqno = service.chargeCAWallet(inEvent);
			createCsiInfo(inEvent,inEvent.getCawDto().getWalletId());
			
			Integer csiID = (Integer)context.get(Service.CSI_ID);
			String description = "钱包充值,钱包ID：" + inEvent.getCawDto().getWalletId() + ",受理单ID:" + csiID
								+ ",充值记录ID：" + seqno
								+ ",充值金额: " + inEvent.getCawcrDto().getValue()
								+ ",充值点数：" + inEvent.getCawcrDto().getPoint();
			service.updateCAWalletChargeRecord(seqno,csiID.intValue());
	
			createCaValletEvent(SystemEventRecorder.WALLET_CHARGE,inEvent);
	
			SystemLogRecorder.keyLog( description, "小钱包充值",
									 machineName, SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID, 
										inEvent.getCawDto().getCustomerId());
		}
	}

	/**
	 * @param inEvent
	 */
	private void createCAWallet(CAWalletEJBEvent inEvent)
			throws HomeFactoryException, ServiceException, CreateException,
			FinderException {
		LogUtility.log(clazz,LogLevel.DEBUG,"■■开始钱包创建■"); 
		context = initServiceContext(inEvent);
		int walletID = 0, seqno = 0;
		String doPost = inEvent.getDoPost();
		ServiceAccountService service = new ServiceAccountService(context);

		BusinessRuleService brService=new BusinessRuleService(context);
		brService.checkCreateCAWallet(inEvent);
		
		if ("false".equalsIgnoreCase(doPost)) {
			int point = service.calculatePointForIPPVCharge(inEvent);
			response.setPayload(new Integer(point));
			return;
		} else if ("true".equalsIgnoreCase(doPost)) {
			walletID = service.createCAWallet(inEvent);
			inEvent.getCawcrDto().setWalletId(walletID);
			seqno = service.chargeCAWallet(inEvent);
			createCsiInfo(inEvent, walletID);

			Integer csiID = (Integer) context.get(Service.CSI_ID);
			service.updateCAWalletChargeRecord(seqno, csiID.intValue());


			String description = "钱包创建,钱包ID：" + walletID + ",受理单ID:" + csiID
								+ ",充值金额: " + inEvent.getCawcrDto().getValue()
								+ ",充值点数：" + inEvent.getCawcrDto().getPoint();
			SystemLogRecorder.keyLog(description , "钱包创建",
					machineName, SystemLogRecorder.LOGMODULE_CUSTSERV,
					operatorID, inEvent.getCawDto().getCustomerId());

			createCaValletEvent(SystemEventRecorder.WALLET_CREATE,inEvent);
			if(inEvent.getCawcrDto().getPoint()>0){
				createCaValletEvent(SystemEventRecorder.WALLET_CHARGE,inEvent);
			}
		}

	}
	private void createCaValletEvent(int eventClass,CAWalletEJBEvent inEvent) throws HomeFactoryException, CreateException{
		CAWalletDTO caDto=inEvent.getCawDto();
		CAWalletChargeRecordDTO cawcrDto=inEvent.getCawcrDto();
		CustomerProductDTO cpDto=BusinessUtility.getCustomerProductDTOBySerialNo(caDto.getScSerialNo());
		int stbDeviceId=cpDto.getLinkToDevice1();
		if(stbDeviceId==cpDto.getDeviceID()){
			stbDeviceId=cpDto.getLinkToDevice2();
		}
		TerminalDeviceDTO stbDto=BusinessUtility.getDeviceByDeviceID(stbDeviceId);
		SystemEventRecorder.addEvent4CAWallet(
				eventClass,caDto.getCustomerId(),caDto.getServiceAccountId(),
				cpDto.getProductID(),cpDto.getPsID(),cpDto.getDeviceID(),
				caDto.getScSerialNo(),
				stbDeviceId,stbDto.getSerialNo(),
				caDto.getCaWalletCode(), cawcrDto.getPoint(),operatorID);
	}
	/**
	 * @param inEvent
	 */
	private void createCsiInfo(CAWalletEJBEvent inEvent,int walletID) throws ServiceException {
		
		
		CustServiceInteractionDTO csiDto = new CustServiceInteractionDTO();

		if(inEvent.getActionType() == EJBEvent.IPPV_CREATE){
			csiDto.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAM);
			context.put(Service.ACTION_DESCRTIPION,"钱包创建");
		}else if(inEvent.getActionType() == EJBEvent.IPPV_CHARGE){
			csiDto.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAC);
			context.put(Service.ACTION_DESCRTIPION,"钱包充值");
		}
			context.put(Service.ACTION_DEFITION,CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS);

		csiDto.setCustomerID(inEvent.getCawDto().getCustomerId());		
		csiDto.setWalletId(walletID);
		csiDto.setPoint(inEvent.getCawcrDto().getPoint());
		csiDto.setValue(inEvent.getCawcrDto().getValue());

		CSIService csiService = new CSIService(context);
		csiService.createCustServiceInteraction(csiDto);				
	}

	/**
	 * @param walletID
	 * @param i
	 */
	

	/**
	 * @param inEvent
	 * @return
	 */
	private ServiceContext initServiceContext(CAWalletEJBEvent inEvent) {
		
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS, inEvent.getRemoteHostAddress());
		if (inEvent.getCawcrDto() != null) {
			serviceContext.put(Service.CUSTOMER_ID, new Integer(inEvent.getCawcrDto().getCustomerId()));
		}
		
		return serviceContext;
	}

}
