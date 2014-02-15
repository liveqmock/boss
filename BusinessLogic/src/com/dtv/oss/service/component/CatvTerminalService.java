/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import java.util.Collection;

import com.dtv.oss.domain.CatvTermProcess;
import com.dtv.oss.domain.CatvTermProcessHome;
import com.dtv.oss.domain.CatvTerminal;
import com.dtv.oss.domain.CatvTerminalHome;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustServiceInteractionHome;
import com.dtv.oss.domain.JobCard;
import com.dtv.oss.domain.JobCardHome;
import com.dtv.oss.dto.CatvTermProcessDTO;
import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.dto.JobCardProcessDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.ServiceAccountEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;


public class CatvTerminalService extends AbstractService{
	private static final Class clazz = CatvTerminalService.class;
	private ServiceContext context=null;
	public CatvTerminalService(ServiceContext cxt){
		context=cxt;
	}
	
	
	/**
	 * 创建模拟终端
	 * @param dto
	 * @throws ServiceException 
	 */
	public void createCatvTerminalService(CatvTerminalDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"创建模拟终端,CatvTerminalDTO==" + dto);
		//检查数据
		if(dto==null)
			throw new ServiceException("参数传输错误！");
		
		try{
			CatvTerminalHome home=HomeLocater.getCatvTerminalHome();
			//新建
			dto.setCreateDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//开通
			if(dto.getStatus().equals("N")) dto.setActiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//销号
			if(dto.getStatus().equals("D")) dto.setCancelDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//关断
			if(dto.getStatus().equals("S")) dto.setPauseDate(new java.sql.Timestamp(System.currentTimeMillis()));
			
			//得到ID,根据function得到主键ID
			String retValue = BusinessUtility.getCatvID(dto.getDistrictID());
			
			/**
			CallableStatement cs;
			Connection con = DBUtil.getConnection();
			cs = con.prepareCall("{? = call sp_F_GetCATVID(?)}");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setInt(2, dto.getDistrictID());
			cs.execute();
			String retValue = cs.getString(1);
			**/
			
			//设置ID
			dto.setId(retValue);
			CatvTerminal cbr=home.create(dto);
			
			StringBuffer logDes=new StringBuffer();
			logDes.append("模拟终端流水号：" + cbr.getId());
			logDes.append("，所在区域：" + BusinessUtility.getDistrictNameById(cbr.getDistrictID()));
			logDes.append("，端口数：" + cbr.getPortNo());
			logDes.append("，邮编：" + cbr.getPostcode());
			logDes.append("，终端状态：" + BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALSTATUS",cbr.getStatus()));
			logDes.append("，详细地址：" + cbr.getDetailedAddress());
			logDes.append("，线缆类型：" + BusinessUtility.getCommomSettingValue("SET_D_CABLETYPE",cbr.getCableType()));
			logDes.append("，光节点：" + cbr.getFiberNodeID());
			logDes.append("，双向网标记：" + BusinessUtility.getCommomSettingValue("SET_G_YESNOFLAG",cbr.getBiDirectionFlag()));
			logDes.append("，终端来源：" + BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALRECORDSOURCE",cbr.getRecordSource()));
			logDes.append("，终端类型：" + BusinessUtility.getCommomSettingValue("SET_A_CATVTERMTYPE",cbr.getCatvTermType()));
			logDes.append("，备注信息：" + cbr.getComments());
			
			//日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "新建模拟终端", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("创建模拟终端出错，请重新再试！");
		}		
	}
	
	/**
	 * 修改模拟终端
	 * @param dto
	 * @throws ServiceException 
	 */
	public void updateCatvTerminalService(CatvTerminalDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"修改模拟终端,CatvTerminalDTO==" + dto);
		//检查数据
		if(dto==null)
			throw new ServiceException("参数传输错误！");
		
		//检查数据的完整性
		if(dto.getId()== null)
			throw new ServiceException("没有终端标志，无法完成修改！");

		/**
			if(dto.getDistrictID()==0)
				throw new ServiceException("所在区域不能为空！");
			if(dto.getPortNo()==0)
				throw new ServiceException("端口数不能为空！");
			if(dto.getPostcode()==null || "".equals(dto.getPostcode()))
				throw new ServiceException("邮编不能为空！");
			if(dto.getStatus()==null || dto.getStatus()==null)
				throw new ServiceException("终端状态不能为空！");
			if(dto.getDetailedAddress()==null || dto.getDetailedAddress()==null)
				throw new ServiceException("详细地址不能为空！");
			if(dto.getCableType()==null || dto.getCableType()==null)
				throw new ServiceException("线缆类型不能为空！");
			if(dto.getFiberNodeID()==null || dto.getFiberNodeID()==null)
				throw new ServiceException("光节点不能为空！");
			if(dto.getBiDirectionFlag()==null || dto.getBiDirectionFlag()==null)
				throw new ServiceException("双向网标记不能为空！");
		*/
		
		//修改
		int result=1;
		StringBuffer logDes=new StringBuffer();
		try{
			CatvTerminalHome home=HomeLocater.getCatvTerminalHome();
			CatvTerminal cbr=home.findByPrimaryKey(dto.getId());
			
			logDes.append("模拟终端流水号：" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";端口数：", String.valueOf(cbr.getPortNo()),String.valueOf(dto.getPortNo())));
			logDes.append(SystemLogRecorder.appendDescString(";所在区域：", BusinessUtility.getDistrictNameById(cbr.getDistrictID()),BusinessUtility.getDistrictNameById(dto.getDistrictID())));
			logDes.append(SystemLogRecorder.appendDescString(";邮编：", cbr.getPostcode(),dto.getPostcode()));
			logDes.append(SystemLogRecorder.appendDescString(";终端状态：", BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALSTATUS",cbr.getStatus()),BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALSTATUS",dto.getStatus())));
			logDes.append(SystemLogRecorder.appendDescString(";详细地址：", cbr.getDetailedAddress(),dto.getDetailedAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";线缆类型：", BusinessUtility.getCommomSettingValue("SET_D_CABLETYPE",cbr.getCableType()),BusinessUtility.getCommomSettingValue("SET_D_CABLETYPE",dto.getCableType())));
			logDes.append(SystemLogRecorder.appendDescString(";光节点：", String.valueOf(cbr.getFiberNodeID()),String.valueOf(dto.getFiberNodeID())));
			logDes.append(SystemLogRecorder.appendDescString(";双向网标记：", BusinessUtility.getCommomSettingValue("SET_G_YESNOFLAG",cbr.getBiDirectionFlag()),BusinessUtility.getCommomSettingValue("SET_G_YESNOFLAG",dto.getBiDirectionFlag())));
			logDes.append(SystemLogRecorder.appendDescString(";终端来源：", BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALRECORDSOURCE",cbr.getRecordSource()),BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALRECORDSOURCE",dto.getRecordSource())));
			logDes.append(SystemLogRecorder.appendDescString(";终端类型：", BusinessUtility.getCommomSettingValue("SET_A_CATVTERMTYPE",cbr.getCatvTermType()),BusinessUtility.getCommomSettingValue("SET_A_CATVTERMTYPE",dto.getCatvTermType())));
			logDes.append(SystemLogRecorder.appendDescString(";备注信息：", cbr.getComments(),dto.getComments()));
			
			//新建
			if(dto.getStatus().equals("I")) dto.setCreateDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//开通
			if(dto.getStatus().equals("N")) dto.setActiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//销号
			if(dto.getStatus().equals("D")) dto.setCancelDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//关断
			if(dto.getStatus().equals("S")) dto.setPauseDate(new java.sql.Timestamp(System.currentTimeMillis()));
			result=cbr.ejbUpdate(dto);
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "修改模拟终端", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("修改模拟终端出错，请重新再试！");
		}
		if(result==-1)
			throw new ServiceException("模拟终端不是最新数据，无法更新，请重试！");
	}

	private void createCatvTermProcess(CatvTermProcessDTO ctpDto) throws ServiceException {
		if(ctpDto == null)
			throw new ServiceException("终端处理记录不能为空");
		try{
			CatvTermProcessHome ctHome = HomeLocater.getCatvTermProcessHome();
			CatvTermProcess ctp = ctHome.create(ctpDto);
		}catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
			throw new ServiceException("创建终端处理记录出错！");
		}		
	}


	/**
	 * @param jcDto
	 * @param jcpDto
	 */
	public void updateCatvTerminalStatusAndSAStatus(JobCardDTO jcDto, CustServiceInteractionDTO csiDto) throws ServiceException {
		if(csiDto == null ||csiDto.getId()<=0)
			throw new ServiceException("csiDTO 没有设置好。");
		int said = BusinessUtility.getCatvSAIDByCustomerID(csiDto.getCustomerID());System.out.println("&&&&&&&&&&&&&said:"+said);
		if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAA.equalsIgnoreCase(csiDto.getType())){
			//int oldPortNumber = BusinessUtility.getOldPortNumber(jcDto.getJobCardId());System.out.println("&&&&&&&&&&&&&oldportno:"+oldPortNumber);
			updateTerminalProtNumber(jcDto.getAddPortNumber(),jcDto.getCatvID());			
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAR.equals(csiDto.getType())){
			context.put(Service.CUSTOMER_ID,new Integer(csiDto.getCustomerID()));
			CustServiceInteraction csi=getCsiEJB(csiDto.getId());
			context.put(Service.CSI,csi);
			ServiceAccountService sas = new ServiceAccountService(context);			
			ServiceAccountEJBEvent event = new ServiceAccountEJBEvent();
			event.setServiceAccountID(said);
			Collection colpsid = BusinessUtility.getPsIDListByCond(said,0,null);
			event.setColPsid(colpsid);
			sas.resume(event);
		}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAS.equals(csiDto.getType())){
			context.put(Service.CUSTOMER_ID,new Integer(csiDto.getCustomerID()));	
			ServiceAccountService sas = new ServiceAccountService(context);
			sas.suspend(said);
		}//如果是模拟电视开户受理单
		else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiDto.getType())){
			//更新客户状态为正常
			CustomerService cs = new CustomerService(context);
			cs.updateCustomer(csiDto.getId(), EJBEvent.REGISTER_INSTALLATION_INFO);                                  
    		 //修改业务账户及其产品的状态
			Collection serviceAccountIDList=BusinessUtility.getServiceAccountIDListByCsiID(csiDto.getId());
			ServiceAccountService  serviceAccountService=new ServiceAccountService(context);
			serviceAccountService.updateSAStatus(serviceAccountIDList,EJBEvent.REGISTER_INSTALLATION_INFO);
			//如果模拟电视开户受理单关联的是增端单
			if((CommonConstDefinition.JOBCARD_TYPE_CATV).equals(jcDto.getJobType()) && (CommonConstDefinition.CATV_JOBCARD_SUBTYPE_Z.equals(jcDto.getSubType())))
			{
			//修改终端的端口数
			updateTerminalProtNumber(jcDto.getAddPortNumber(),jcDto.getCatvID()); 
			}
		}
		updateTerminalStatusByCsiType(csiDto.getType(),jcDto.getCatvID());
	}


	/**
	 * @param i
	 */
	private CustServiceInteraction getCsiEJB(int csiid)throws ServiceException {
		CustServiceInteraction csi = null;
		try{
			CustServiceInteractionHome home = HomeLocater.getCustServiceInteractionHome();
			csi = home.findByPrimaryKey(new Integer(csiid));
			
		}catch(Exception e){
			e.printStackTrace();
			LogUtility.log(clazz,LogLevel.WARN,e);
			throw new ServiceException("更新终端端口数出错！");
		}
		return csi;
	}


	/**
	 * @param string
	 */
	public void updateTerminalStatusByCsiType(String csiType,String catvId) throws ServiceException {
		if(catvId == null || "".equals(catvId))
			return;
		try{
			CatvTerminalHome home = HomeLocater.getCatvTerminalHome();
			CatvTerminal ct = home.findByPrimaryKey(catvId);
			if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAR.equals(csiType)
					||CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiType)){
				ct.setStatus(CommonConstDefinition.CATVTERMINAL_STATUS_NORMAL);
				ct.setActiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
			}else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAS.equals(csiType)){
				ct.setStatus(CommonConstDefinition.CATVTERMINAL_STATUS_SUSPEND);
				ct.setPauseDate(new java.sql.Timestamp(System.currentTimeMillis()));
			}
		}catch(Exception e){
			e.printStackTrace();
			LogUtility.log(clazz,LogLevel.WARN,e);
			throw new ServiceException("更新终端端口数出错！");
		}		
		
	}


	/**
	 * @param i
	 */
	private void updateTerminalProtNumber(int addPortNumber,String catvId) throws ServiceException {
		if(catvId == null || "".equals(catvId))
			return;
		try{
			CatvTerminalHome home = HomeLocater.getCatvTerminalHome();
			CatvTerminal ct = home.findByPrimaryKey(catvId);
			int oldPortNumber = ct.getPortNo();
			ct.setPortNo(oldPortNumber + addPortNumber);
		}catch(Exception e){
		e.printStackTrace();
		LogUtility.log(clazz,LogLevel.WARN,e);
		throw new ServiceException("更新终端端口数出错！");
	}
		// TODO Auto-generated method stub
		
	}
}
