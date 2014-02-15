package com.dtv.oss.service.command.logistics;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.DeviceTransition;
import com.dtv.oss.domain.DeviceTransitionDetail;
import com.dtv.oss.domain.DeviceTransitionDetailHome;
import com.dtv.oss.domain.DeviceTransitionHome;
import com.dtv.oss.domain.LogisticsSetting;
import com.dtv.oss.domain.LogisticsSettingHome;
import com.dtv.oss.domain.TerminalDevice;
import com.dtv.oss.domain.TerminalDeviceHome;
import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.dto.VodstbDeviceImportHeadDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DevicePutIntoDepotEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemEventRecorder;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

public class DevicePutIntoDepotCommand extends Command {
	private static final Class clazz = DevicePutIntoDepotCommand.class;

	CommandResponseImp response = null;

	private int operatorID = 0;

	private String machineName = "";

	private CommonQueryConditionDTO devConDTO;

	ServiceContext context = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {

		response = new CommandResponseImp(null);
		DevicePutIntoDepotEJBEvent inEvent=(DevicePutIntoDepotEJBEvent)ev;
		this.operatorID = inEvent.getOperatorID();
	    this.machineName = inEvent.getRemoteHostAddress();
	    LogUtility.log(clazz,LogLevel.DEBUG,"Enter " + clazz.getName() + " execute() now.");

		try {
			switch (inEvent.getActionType()) {
    		  case DevicePutIntoDepotEJBEvent.DEVICE_PUT_INTO_DEPOT:
    		 	   DevicePutIntoDepot(ev);
                   break;
    		  case DevicePutIntoDepotEJBEvent.VOD_DEVICE_PUT_INTO_DEPOT:
    			   VodDevicePutIntoDepot(inEvent);
    			   break;
    	      default:
    	    	  throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
			}
		} 
		catch (HomeFactoryException he) {
			throw new CommandException(ErrorCode.HOMEFAC_CANNT_LOOKUP);
		} 
		catch (CreateException ce) {
			throw new CommandException(ErrorCode.ENTITYBEAN_CANNT_CREATE);
		} 
		catch(ServiceException se){
			throw new CommandException(se.getMessage());
		}
		catch (FinderException fe) {
			throw new CommandException(ErrorCode.ENTITYBEAN_HOMEINTERFACE_CANNTFIND);
		}

		return response;
	}
	
	/*
	 * 双向设备记录,先插入到vod_stbdevice_import_head,vod_stbdevice_import_detail后,
	 * 调用Sp:   实现设备的真正入库,由Sp中实现STB与CM的绑定
	 * */
	private void VodDevicePutIntoDepot(DevicePutIntoDepotEJBEvent event) throws CommandException{
		try{
		  if (!event.isDoPost()){
		     VodstbDeviceImportHeadDTO importHeadDto =event.getVodstbDeviceHead();
		     importHeadDto.setOperatorID(event.getOperatorID());
		     Collection detailCols =event.getVodDevicePutInfoDepotList();
		     if (detailCols.isEmpty()){
				throw new CommandException("xls文件无导入内容!");
		     } 
		     int  batchID = BusinessUtility.getSequenceKey("vod_stbdevice_import_SEQNO");
		     importHeadDto.setBatchID(batchID);
		     //创建vod设备头信息
		     BusinessUtility.importVodDeviceHead(importHeadDto);
		     //创建vod设备明细信息
		     BusinessUtility.importVodDeviceDetail(batchID, detailCols);
		     response.setPayload(importHeadDto);
			
		  }else{
			   LogUtility.log(clazz, LogLevel.DEBUG, "inEvent.getVodstbDeviceHead().getBatchID()--->"+event.getVodstbDeviceHead().getBatchID());
			   BusinessUtility.callProcedureForVodDevicePut(event.getVodstbDeviceHead().getBatchID());
		  }
		} catch(ServiceException e){
			   throw new CommandException(e);
		}
	}

	/**
	 * 单向设备入库
	 * 
	 * @param event
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 * @throws ServiceException 
	 */

	private void DevicePutIntoDepot(EJBEvent ev) throws CommandException,
			HomeFactoryException, CreateException, FinderException, ServiceException {
		DevicePutIntoDepotEJBEvent inEvent = (DevicePutIntoDepotEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = inEvent.getRemoteHostAddress();
		if (inEvent.getDevConDTO() != null) {
			devConDTO = inEvent.getDevConDTO();
		}
		List terminalDeviceList = new ArrayList();
		// 假定传入的参数不为空

		String serials = devConDTO.getSpareStr7().trim();
		String[] arrTerminals = serials.split("\n");
		//编号中设置了哪些字段,应该和编号对应
		String[] arrFields = devConDTO.getSpareStr8().trim().split("\\|");

		int deviceNumber = arrTerminals.length;
		DeviceTransition devTrans;
		TerminalDevice terDev = null;
		;
		String idList = "";

		// 添加一条记录到表T_DeviceTransition，一次设备录入一条记录
		devTrans = createDeviceTransition(operatorID, devConDTO, deviceNumber);
		// 流转
		int batchID = devTrans.getBatchID().intValue();
		for (int i = 0; i < arrTerminals.length; i++) {
			/*
			 * 添加一条记录到表T_TerminalDevice，一次多个设备，一个设备一条记录
			 * 一个设备的编号,可能会包含多个信息(序列号|MAC地址|内部MAC地址|OK地址)
			 */
			//取得一个设备的全部编号
			String[] arrNOs = arrTerminals[i].trim().split("\\|");
			//arrFields中不包含序列号

			if(arrFields!=null&&!arrFields[0].equalsIgnoreCase("")&&arrNOs.length!=arrFields.length+1)
				throw new CommandException("输入的编号数目和要必须填写的不对应,请检查输入!");

			terDev = createTerminalDevice(devConDTO, arrFields,arrNOs, batchID);
			LogUtility.log(this.getClass(), LogLevel.DEBUG,"设置好的DTO:"+terDev.toString());
			
			int deviceID = terDev.getDeviceID().intValue();
			idList += deviceID + "/";
			// 添加一条记录到表T_DeviceTransitionDetail,记录设备流转明细，一次多个设备，一个设备一条记录
			createDeviceTransitionDetail(devConDTO, arrNOs[0], batchID, deviceID);
					
			terminalDeviceList.add(terDev);
		}
		
		Iterator itDevice=terminalDeviceList.iterator();
		while(itDevice.hasNext()){
			TerminalDevice device=(TerminalDevice) itDevice.next();
			
			//创建设备入库事件(620事件) 只对入库设备中的智能卡和机顶盒记录事件 add by chaoqiu 2007-05-21 begin
			if("SC".equals(device.getDeviceClass()))
				SystemEventRecorder.addEvent4DevicePut(SystemEventRecorder.DEVICE_PUT, 
						device.getDeviceID().intValue(), device.getSerialNo(),0,"",operatorID, "C");
			else if("STB".equals(devConDTO.getSpareStr10()))
				SystemEventRecorder.addEvent4DevicePut(SystemEventRecorder.DEVICE_PUT, 
						0,"",device.getDeviceID().intValue(), device.getSerialNo(),operatorID, "C");
			//创建设备入库事件(620事件) add by chaoqiu 2007-05-21 end
		}
		
		if(idList.length()>400)
			idList=idList.substring(0, 400)+"...(批量入库,总设备数:"+deviceNumber+",在此记录部分ID.)";
		try {
			// record system log
			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
					SystemLogRecorder.LOGMODULE_LOGISTICS, "设备入库",
					"设备入库,入库设备ID:" + idList, SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			throw new CommandException("系统日志写入错误!");
		}

		// 以下操作是设备出库的操作 add by chenjiang
		//modify by jason 2007-3-11,上个版本号1.9
		TerminalDevice currentTerDev;
		if(CommonConstDefinition.YESNOFLAG_YES.equals(devConDTO.getSpareStr12())){
			System.out.println("******** 立即出库需要出库***********，目标组织ID："	+ devConDTO.getSpareStr13());
			if(devConDTO.getSpareStr13()==null || "".equals(devConDTO.getSpareStr13()))
				throw new ServiceException("立即出库的目标组织不能为空！");
			
			int outToOrg=Integer.parseInt(devConDTO.getSpareStr13());
			devConDTO.setFiliale(outToOrg);
			devTrans = createOutDeviceTransition(operatorID, devConDTO,deviceNumber);
			
			int outBatchID = devTrans.getBatchID().intValue();
			for (int i = 0; i < terminalDeviceList.size(); i++) {
				currentTerDev = (TerminalDevice) terminalDeviceList.get(i);
				currentTerDev.setAddressType("T");
				//currentTerDev.setAddressID(orgid);
				currentTerDev.setAddressID(outToOrg);
				currentTerDev.setStatus("W");
				currentTerDev.setDepotID(0);
				currentTerDev.setDtLastmod(TimestampUtility.getCurrentTimestamp());
				int deviceID = currentTerDev.getDeviceID().intValue();
				createOutDeviceTransitionDetail(devConDTO, currentTerDev.getSerialNo(), outBatchID, deviceID, outToOrg);
				//createOutDeviceTransitionDetail(devConDTO, currentTerDev.getSerialNo(), outBatchID, deviceID, outBatchID);

			}
			try {
				SystemLogRecorder.createSystemLog(machineName, operatorID,0, 
						SystemLogRecorder.LOGMODULE_LOGISTICS, "设备出库","设备出库,出库设备ID:" + idList,
						SystemLogRecorder.LOGCLASS_NORMAL,SystemLogRecorder.LOGTYPE_APP);
					
			} catch (ServiceException e) {
				throw new CommandException("系统日志写入错误!");
			}
		}
	}

	/**
	 * 添加一条记录到表T_DeviceTransition
	 * 
	 * @param operatorID
	 *            操作员ID
	 * @param devConDTO
	 *            前台传入的数据
	 * @param deviceNumber
	 *            本次入库设备数量
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */

	private DeviceTransition createDeviceTransition(int operatorID,
			CommonQueryConditionDTO devConDTO, int deviceNumber)
			throws CommandException, HomeFactoryException, CreateException,
			FinderException {
		DeviceTransition devTrans;
		DeviceTransitionHome dtHome;
		dtHome = HomeLocater.getDeviceTransitionHome();
		DeviceTransitionDTO devTraDTO = new DeviceTransitionDTO();
		devTraDTO.setAction("N"); // "新设备入库"
		devTraDTO.setBatchNo(devConDTO.getSpareStr5()); // 批号
		devTraDTO.setCreateTime(TimestampUtility.getCurrentDate()); // 操作发生日期
		devTraDTO.setDescription(devConDTO.getSpareStr6()); // 备注
		devTraDTO.setDeviceNumber(deviceNumber); // 设备数量
		devTraDTO.setDtCreate(TimestampUtility.getCurrentDate()); // 记录新增日期
		devTraDTO.setDtLastmod(TimestampUtility.getCurrentDate()); // 记录修改日期
		devTraDTO.setFromType("T"); // "制造商"
		devTraDTO.setFromID(Integer.parseInt(devConDTO.getSpareStr2())); // 制造商ID
		devTraDTO.setToType("D"); // "仓库"
		devTraDTO.setToID(Integer.parseInt(devConDTO.getSpareStr4())); // 仓库ID
		devTraDTO.setOperatorID(operatorID); // 操作员ID
		devTraDTO.setStatus("V"); // 有效
		devTraDTO.setStatusReason(devConDTO.getSpareStr11());//入库原因
		devTrans = dtHome.create(devTraDTO);
		return devTrans;
	}

	/**
	 * 添加一条记录到表T_DeviceTransition
	 * 
	 * @param operatorID
	 *            操作员ID
	 * @param devConDTO
	 *            前台传入的数据
	 * @param deviceNumber
	 *            本次出库设备数量
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */

	private DeviceTransition createOutDeviceTransition(int operatorID,
			CommonQueryConditionDTO devConDTO, int deviceNumber)
			throws CommandException, HomeFactoryException, CreateException,
			FinderException {
		DeviceTransition devTrans;
		DeviceTransitionHome dtHome;
		dtHome = HomeLocater.getDeviceTransitionHome();
		DeviceTransitionDTO devTraDTO = new DeviceTransitionDTO();
		devTraDTO.setAction("O"); // 新设备出库
		devTraDTO.setBatchNo(devConDTO.getSpareStr5()); // 批号
		devTraDTO.setCreateTime(TimestampUtility.getCurrentDate()); // 操作发生日期
		devTraDTO.setDescription(devConDTO.getSpareStr6()); // 备注
		devTraDTO.setDeviceNumber(deviceNumber); // 设备数量
		devTraDTO.setDtCreate(TimestampUtility.getCurrentDate()); // 记录新增日期
		devTraDTO.setDtLastmod(TimestampUtility.getCurrentDate()); // 记录修改日期
		devTraDTO.setFromType("D"); // 总公司仓库
		devTraDTO.setFromID(Integer.parseInt(devConDTO.getSpareStr4())); // 总公司仓库ID
		devTraDTO.setToType("T"); // 总公司
		devTraDTO.setToID(devConDTO.getFiliale()); // 出库的ID
		devTraDTO.setOperatorID(operatorID); // 操作员ID
		devTraDTO.setStatus("V"); // 有效
		devTrans = dtHome.create(devTraDTO);
		return devTrans;
	}

	/**
	 * 添加一条记录到表T_TerminalDevice
	 * 
	 * @param devConDTO
	 *            前台传入的数据
	 * @param serialNo
	 *            序列号
	 * @param batchID
	 *            批次ID，T_DeviceTransition.BatchID
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */
	private TerminalDevice createTerminalDevice(
			CommonQueryConditionDTO devConDTO, String[] arrFields,String[] arrNOs, int batchID)
			throws CommandException, HomeFactoryException, CreateException,
			FinderException {
		TerminalDevice terDev = null;
		TerminalDeviceHome terDevHome;
		terDevHome = HomeLocater.getTerminalDeviceHome();

		//序列号在第一个位置,前台如此封装,不用判断,真接取
		String serialNO = arrNOs[0];

		Collection terCol = terDevHome.findBySerialNo(serialNO);
		if (!terCol.isEmpty()) {
			throw new CommandException("设备编号["+serialNO+"]重复,该编号已录入。");
		}
		TerminalDeviceDTO terDevDTO = new TerminalDeviceDTO();



		/*
		 * 输入的序列号/MAC地址等顺序由配置读来,不固定,此处用了...,
		 */
		try {
			for (int j = 0; j < arrFields.length; j++) {
				if(arrFields[j].trim().equalsIgnoreCase(""))continue;
				boolean i=false;
				LogUtility.log(this.getClass(), LogLevel.DEBUG, "当前填装的字段:"
						+ arrFields[j]);
				Method[] methods = terDevDTO.getClass().getMethods();
				for (int k = 0; k < methods.length; k++) {
					Method curMethod = methods[k];
					if (curMethod.getName().equalsIgnoreCase(
							"set" + arrFields[j])) {
						i=true;
						Object[] para = new String[1];
						para[0] = arrNOs[j+1];

						LogUtility.log(this.getClass(), LogLevel.DEBUG,
								"方法名:" + curMethod.getName() + "   设置的值"
										+ arrNOs[j+1]);

						curMethod.invoke(terDevDTO, para);
					}
				}
				if(!i){
					throw new CommandException("找不到"+arrFields[j]+"的相关属性,请检查输入.");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtility.log(this.getClass(), LogLevel.DEBUG,"数据填装错误,请检查数据..");
			e.printStackTrace();
			throw new CommandException("数据填装错误,请检查数据.");
		}
		
		String purposeStrList = devConDTO.getSpareStr14();
		if(purposeStrList != null && !purposeStrList.trim().equals(""))
		{
			purposeStrList = ","+devConDTO.getSpareStr14()+",";
		}
		
		
		terDevDTO.setDeviceClass(devConDTO.getSpareStr10()); // 设备类别
		terDevDTO.setDeviceModel(devConDTO.getSpareStr1());// 设备型号
		terDevDTO.setSerialNo(serialNO);// 序列号
		terDevDTO.setStatus("S"); // 库存
		terDevDTO.setAddressType("D");// "仓库"
		terDevDTO.setAddressID(Integer.parseInt(devConDTO.getSpareStr4()));// 仓库ID
		terDevDTO.setBatchID(batchID);// 批次ID,T_DeviceTransition.BatchID
		terDevDTO
				.setGuaranteeLength(Integer.parseInt(devConDTO.getSpareStr3()));// 保质期
		terDevDTO.setDepotID(Integer.parseInt(devConDTO.getSpareStr4())); // 仓库ID
		terDevDTO.setPalletID(0);// 货架ID
		terDevDTO.setUseFlag("N");// "新设备"
		terDevDTO.setMatchFlag("N"); // 尚未配对
		terDevDTO.setPreAuthorization("N");
		terDevDTO.setCaSetFlag("N");
		terDevDTO.setCaSetDate(null);
		terDevDTO.setDtCreate(TimestampUtility.getCurrentDate()); // 记录新增日期
		terDevDTO.setDtLastmod(TimestampUtility.getCurrentDate()); // 记录修改日期
		terDevDTO.setPurposeStrList(purposeStrList); // 记录设备用途

		terDev = terDevHome.create(terDevDTO);

		return terDev;
	}

	/**
	 * 添加一条记录到表T_DeviceTransitionDetail
	 * 
	 * @param devConDTO
	 *            前台传入的数据
	 * @param serialNo
	 *            序列号
	 * @param batchID
	 *            批次ID，T_DeviceTransition.BatchID
	 * @param deviceID
	 *            设备ID,T_TerminalDevice.DeviceID
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */
	private void createDeviceTransitionDetail(
			CommonQueryConditionDTO devConDTO, String serialNo, int batchID,
			int deviceID) throws CommandException, HomeFactoryException,
			CreateException, FinderException {
		DeviceTransitionDetail devDet;
		DeviceTransitionDetailHome devTranDetailHome;
		devTranDetailHome = HomeLocater.getDeviceTransitionDetailHome();
		DeviceTransitionDetailDTO devDetDTO = new DeviceTransitionDetailDTO();
		devDetDTO.setBatchID(batchID);// 批次ID,T_DeviceTransition.BatchID
		devDetDTO.setDeviceID(deviceID);// 设备ID,T_TerminalDevice.DeviceID
		devDetDTO.setSerialNo(serialNo);// 序列号
		devDetDTO.setStatus("V"); // 有效
		devDetDTO.setFromType("T"); // "制造商"
		devDetDTO.setFromID(Integer.parseInt(devConDTO.getSpareStr2())); // 制造商ID
		devDetDTO.setToType("D"); // "仓库"
		devDetDTO.setToDeviceStatus("S");
		devDetDTO.setToID(Integer.parseInt(devConDTO.getSpareStr4())); // 仓库ID
		devDetDTO.setDtCreate(TimestampUtility.getCurrentDate()); // 记录新增日期
		devDetDTO.setDtLastmod(TimestampUtility.getCurrentDate()); // 记录修改日期

		devDet = devTranDetailHome.create(devDetDTO);
	}

	/**
	 * 添加一条记录到表T_DeviceTransitionDetail
	 * 
	 * @param devConDTO
	 *            前台传入的数据
	 * @param serialNo
	 *            序列号
	 * @param batchID
	 *            批次ID，T_DeviceTransition.BatchID
	 * @param deviceID
	 *            设备ID,T_TerminalDevice.DeviceID
	 * @throws CommandException
	 * @throws HomeFactoryException
	 * @throws CreateException
	 * @throws FinderException
	 */
	private void createOutDeviceTransitionDetail(
			CommonQueryConditionDTO devConDTO, String serialNo, int batchID,
			int deviceID, int orgid) throws CommandException,
			HomeFactoryException, CreateException, FinderException {
		DeviceTransitionDetail devDet;
		DeviceTransitionDetailHome devTranDetailHome;
		devTranDetailHome = HomeLocater.getDeviceTransitionDetailHome();
		DeviceTransitionDetailDTO devDetDTO = new DeviceTransitionDetailDTO();
		devDetDTO.setBatchID(batchID);// 批次ID,T_DeviceTransition.BatchID
		devDetDTO.setDeviceID(deviceID);// 设备ID,T_TerminalDevice.DeviceID
		devDetDTO.setSerialNo(serialNo);// 序列号
		devDetDTO.setStatus("V"); // 有效
		devDetDTO.setFromType("D"); // 总公司仓库
		devDetDTO.setFromID(Integer.parseInt(devConDTO.getSpareStr4())); // 总公司仓库
		devDetDTO.setFromDeviceStatus("S");
		devDetDTO.setToType("T"); // 总公司
		devDetDTO.setToID(orgid); // 总公司
		devDetDTO.setToDeviceStatus("W");
		devDetDTO.setDtCreate(TimestampUtility.getCurrentDate()); // 记录新增日期
		devDetDTO.setDtLastmod(TimestampUtility.getCurrentDate()); //记录修改日期

		devDet = devTranDetailHome.create(devDetDTO);
	}

}
