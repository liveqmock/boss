package com.dtv.oss.service.command.network;

import com.dtv.oss.dto.CAConditionDTO;
import com.dtv.oss.dto.CAEventCmdMapDTO;
import com.dtv.oss.dto.CAHostDTO;
import com.dtv.oss.dto.CAProductDTO;
import com.dtv.oss.dto.CAProductDefDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.CaService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.network.CaEJBEvent;
import com.dtv.oss.service.util.SystemLogRecorder;

/**
 * Ca��������
 * 
 * @author 260327h
 * 
 */
public class CaCommand extends Command {

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;
	private static Class clazz=CaService.class;

	private ServiceContext context;

	/**
	 * 
	 */
	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		CaEJBEvent inEvent = (CaEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "Ca��������");

		try {
			switch (inEvent.getActionType()) {

			case CaEJBEvent.CA_HOST_CREATE: // ����
				this.createCaHost((CAHostDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_HOST_DELETE: // ɾ��
				break;
			case CaEJBEvent.CA_HOST_MODIFY: // �޸�
				this.modifyCaHost((CAHostDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_PRODUCT_CREATE:
				this.createCaProduct((CAProductDefDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_PRODUCT_MODIFY:
				this.modifyCaProduct((CAProductDefDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_PRODUCT_DELETE:
				this.deleteCaProduct((CAProductDefDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_PRODUCTSMS_CREATE:
				this.createCaSMSProduct((CAProductDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_PRODUCTSMS_MODIFY:
				this.modifyCaSMSProduct((CAProductDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_PRODUCTSMS_DELETE:
				this.deleteCaSMSProduct((CAProductDTO) inEvent.getDto());
				break;	
		 
			case CaEJBEvent.CA_CONDITION_CREATE:
				this.createCaCondition((CAConditionDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_CONDITION_MODIFY:
				this.modifyCaCondition((CAConditionDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_EVENTCMDMAP_CREATE:
				this.createCaEventCmdMap((CAEventCmdMapDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_EVENTCMDMAP_MODIFY:
				this.modifyCaEventCmdMap((CAEventCmdMapDTO) inEvent.getDto());
				break;
			 
			default:
				break;
			}
		} catch (ServiceException ce) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(this.getClass(), LogLevel.FATAL, this, unkown);
			throw new CommandException("δ֪����");
		}
		return response;
	}


	private void modifyCaEventCmdMap(CAEventCmdMapDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaEventCmdMap(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "�޸�",
				"CA�¼�����ӳ�������޸�,�¼�����ӳ��ID��" + dto.getMapID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
		//SystemLogRecorder.createSystemLog(String machineName, int operatorID, int customerID,
		//String moduleName, String operation, String logDesc, String logClass, String logType)
	}

	private void createCaEventCmdMap(CAEventCmdMapDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.createCaEventCmdMap(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "����",
				"CA�¼�����ӳ������,�¼�����ӳ��ID��" + dto.getMapID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	private void modifyCaCondition(CAConditionDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaCondition(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "�޸�",
				"CA��ϵ�����޸�,��ϵID��" + dto.getCondID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	private void createCaCondition(CAConditionDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.createCaCondition(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "����",
				"CA��ϵ����,��ϵID��" + dto.getCondID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	private void modifyCaHost(CAHostDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaHost(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "�޸�",
				"CA���������޸�,����ID��" + dto.getHostID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void createCaHost(CAHostDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
	//	checkHostExist(dto.getHostID());
		service.createCaHost(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "����CA����",
				"CA��������,�޸�����ID��" + dto.getHostID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	 
	private void modifyCaProduct(CAProductDefDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "�޸�CA��Ʒ����",
				"CA��Ʒ�����޸�,����ID,opi_id,CA��ƷID�ֱ�Ϊ: " + dto.getHostID()+", "
				+dto.getOpiID()+", "+dto.getCaProductID(),SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	private void deleteCaProduct(CAProductDefDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.deleteCaProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "ɾ��CA��Ʒ����",
				"CA��Ʒ����ɾ��,����ID,opi_id,CA��ƷID�ֱ�Ϊ: " + dto.getHostID()+", "
				+dto.getOpiID()+", "+dto.getCaProductID(),SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void createCaProduct(CAProductDefDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.createCaProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "����CA��Ʒ����",
				"CA��Ʒ��������,����ID,opi_id,CA��ƷID�ֱ�Ϊ: " + dto.getHostID()+", "
				+dto.getOpiID()+", "+dto.getCaProductID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	private void modifyCaSMSProduct(CAProductDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaSMSProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "�޸�SMS-CA��Ʒӳ��",
				"SMS-CA��Ʒӳ���޸�,��ƷID,����ID�ֱ�Ϊ: " + dto.getProductId()+", "
				+dto.getConditionId(),SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	private void deleteCaSMSProduct(CAProductDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.deleteCaSMSProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "ɾ��SMS-CA��Ʒӳ��",
				"SMS-CA��Ʒӳ��ɾ��,��ƷID,����ID�ֱ�Ϊ: " + dto.getProductId()+", "
				+dto.getConditionId(),SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void createCaSMSProduct(CAProductDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.createCaSMSProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "����SMS-CA��Ʒӳ��",
				"SMS-CA��Ʒӳ������,��ƷID,����ID�ֱ�Ϊ: " + dto.getProductId()+", "
				+dto.getConditionId(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	 
		 

	 

}
