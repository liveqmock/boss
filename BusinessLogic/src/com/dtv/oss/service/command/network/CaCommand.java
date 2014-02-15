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
 * Ca主机管理
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "Ca主机管理");

		try {
			switch (inEvent.getActionType()) {

			case CaEJBEvent.CA_HOST_CREATE: // 创建
				this.createCaHost((CAHostDTO) inEvent.getDto());
				break;
			case CaEJBEvent.CA_HOST_DELETE: // 删除
				break;
			case CaEJBEvent.CA_HOST_MODIFY: // 修改
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
			throw new CommandException("未知错误。");
		}
		return response;
	}


	private void modifyCaEventCmdMap(CAEventCmdMapDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaEventCmdMap(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "修改",
				"CA事件命令映射设置修改,事件命令映射ID：" + dto.getMapID(),
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
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "增加",
				"CA事件命令映射新增,事件命令映射ID：" + dto.getMapID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	private void modifyCaCondition(CAConditionDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaCondition(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "修改",
				"CA关系设置修改,关系ID：" + dto.getCondID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	private void createCaCondition(CAConditionDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.createCaCondition(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "增加",
				"CA关系增加,关系ID：" + dto.getCondID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	private void modifyCaHost(CAHostDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaHost(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "修改",
				"CA主机设置修改,主机ID：" + dto.getHostID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void createCaHost(CAHostDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
	//	checkHostExist(dto.getHostID());
		service.createCaHost(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "增加CA主机",
				"CA主机增加,修改主机ID：" + dto.getHostID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	 
	private void modifyCaProduct(CAProductDefDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "修改CA产品定义",
				"CA产品定义修改,主机ID,opi_id,CA产品ID分别为: " + dto.getHostID()+", "
				+dto.getOpiID()+", "+dto.getCaProductID(),SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	private void deleteCaProduct(CAProductDefDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.deleteCaProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "删除CA产品定义",
				"CA产品定义删除,主机ID,opi_id,CA产品ID分别为: " + dto.getHostID()+", "
				+dto.getOpiID()+", "+dto.getCaProductID(),SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void createCaProduct(CAProductDefDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.createCaProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "增加CA产品定义",
				"CA产品定义增加,主机ID,opi_id,CA产品ID分别为: " + dto.getHostID()+", "
				+dto.getOpiID()+", "+dto.getCaProductID(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	private void modifyCaSMSProduct(CAProductDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.updateCaSMSProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "修改SMS-CA产品映射",
				"SMS-CA产品映射修改,产品ID,条件ID分别为: " + dto.getProductId()+", "
				+dto.getConditionId(),SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	private void deleteCaSMSProduct(CAProductDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.deleteCaSMSProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "删除SMS-CA产品映射",
				"SMS-CA产品映射删除,产品ID,条件ID分别为: " + dto.getProductId()+", "
				+dto.getConditionId(),SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	private void createCaSMSProduct(CAProductDTO dto) throws ServiceException {
		this.context = new ServiceContext();
		CaService service = new CaService(this.context);
		service.createCaSMSProduct(dto);
		SystemLogRecorder.createSystemLog(machineName, new Integer(operatorID)
				.intValue(), 0, SystemLogRecorder.LOGMODULE_NET, "增加SMS-CA产品映射",
				"SMS-CA产品映射增加,产品ID,条件ID分别为: " + dto.getProductId()+", "
				+dto.getConditionId(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}
	 
		 

	 

}
