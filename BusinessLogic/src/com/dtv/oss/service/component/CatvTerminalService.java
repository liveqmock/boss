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
	 * ����ģ���ն�
	 * @param dto
	 * @throws ServiceException 
	 */
	public void createCatvTerminalService(CatvTerminalDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"����ģ���ն�,CatvTerminalDTO==" + dto);
		//�������
		if(dto==null)
			throw new ServiceException("�����������");
		
		try{
			CatvTerminalHome home=HomeLocater.getCatvTerminalHome();
			//�½�
			dto.setCreateDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//��ͨ
			if(dto.getStatus().equals("N")) dto.setActiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//����
			if(dto.getStatus().equals("D")) dto.setCancelDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//�ض�
			if(dto.getStatus().equals("S")) dto.setPauseDate(new java.sql.Timestamp(System.currentTimeMillis()));
			
			//�õ�ID,����function�õ�����ID
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
			
			//����ID
			dto.setId(retValue);
			CatvTerminal cbr=home.create(dto);
			
			StringBuffer logDes=new StringBuffer();
			logDes.append("ģ���ն���ˮ�ţ�" + cbr.getId());
			logDes.append("����������" + BusinessUtility.getDistrictNameById(cbr.getDistrictID()));
			logDes.append("���˿�����" + cbr.getPortNo());
			logDes.append("���ʱࣺ" + cbr.getPostcode());
			logDes.append("���ն�״̬��" + BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALSTATUS",cbr.getStatus()));
			logDes.append("����ϸ��ַ��" + cbr.getDetailedAddress());
			logDes.append("���������ͣ�" + BusinessUtility.getCommomSettingValue("SET_D_CABLETYPE",cbr.getCableType()));
			logDes.append("����ڵ㣺" + cbr.getFiberNodeID());
			logDes.append("��˫������ǣ�" + BusinessUtility.getCommomSettingValue("SET_G_YESNOFLAG",cbr.getBiDirectionFlag()));
			logDes.append("���ն���Դ��" + BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALRECORDSOURCE",cbr.getRecordSource()));
			logDes.append("���ն����ͣ�" + BusinessUtility.getCommomSettingValue("SET_A_CATVTERMTYPE",cbr.getCatvTermType()));
			logDes.append("����ע��Ϣ��" + cbr.getComments());
			
			//��־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "�½�ģ���ն�", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("����ģ���ն˳������������ԣ�");
		}		
	}
	
	/**
	 * �޸�ģ���ն�
	 * @param dto
	 * @throws ServiceException 
	 */
	public void updateCatvTerminalService(CatvTerminalDTO dto) throws ServiceException{
		LogUtility.log(clazz,LogLevel.DEBUG,"�޸�ģ���ն�,CatvTerminalDTO==" + dto);
		//�������
		if(dto==null)
			throw new ServiceException("�����������");
		
		//������ݵ�������
		if(dto.getId()== null)
			throw new ServiceException("û���ն˱�־���޷�����޸ģ�");

		/**
			if(dto.getDistrictID()==0)
				throw new ServiceException("����������Ϊ�գ�");
			if(dto.getPortNo()==0)
				throw new ServiceException("�˿�������Ϊ�գ�");
			if(dto.getPostcode()==null || "".equals(dto.getPostcode()))
				throw new ServiceException("�ʱ಻��Ϊ�գ�");
			if(dto.getStatus()==null || dto.getStatus()==null)
				throw new ServiceException("�ն�״̬����Ϊ�գ�");
			if(dto.getDetailedAddress()==null || dto.getDetailedAddress()==null)
				throw new ServiceException("��ϸ��ַ����Ϊ�գ�");
			if(dto.getCableType()==null || dto.getCableType()==null)
				throw new ServiceException("�������Ͳ���Ϊ�գ�");
			if(dto.getFiberNodeID()==null || dto.getFiberNodeID()==null)
				throw new ServiceException("��ڵ㲻��Ϊ�գ�");
			if(dto.getBiDirectionFlag()==null || dto.getBiDirectionFlag()==null)
				throw new ServiceException("˫������ǲ���Ϊ�գ�");
		*/
		
		//�޸�
		int result=1;
		StringBuffer logDes=new StringBuffer();
		try{
			CatvTerminalHome home=HomeLocater.getCatvTerminalHome();
			CatvTerminal cbr=home.findByPrimaryKey(dto.getId());
			
			logDes.append("ģ���ն���ˮ�ţ�" + dto.getId());
			logDes.append(SystemLogRecorder.appendDescString(";�˿�����", String.valueOf(cbr.getPortNo()),String.valueOf(dto.getPortNo())));
			logDes.append(SystemLogRecorder.appendDescString(";��������", BusinessUtility.getDistrictNameById(cbr.getDistrictID()),BusinessUtility.getDistrictNameById(dto.getDistrictID())));
			logDes.append(SystemLogRecorder.appendDescString(";�ʱࣺ", cbr.getPostcode(),dto.getPostcode()));
			logDes.append(SystemLogRecorder.appendDescString(";�ն�״̬��", BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALSTATUS",cbr.getStatus()),BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALSTATUS",dto.getStatus())));
			logDes.append(SystemLogRecorder.appendDescString(";��ϸ��ַ��", cbr.getDetailedAddress(),dto.getDetailedAddress()));
			logDes.append(SystemLogRecorder.appendDescString(";�������ͣ�", BusinessUtility.getCommomSettingValue("SET_D_CABLETYPE",cbr.getCableType()),BusinessUtility.getCommomSettingValue("SET_D_CABLETYPE",dto.getCableType())));
			logDes.append(SystemLogRecorder.appendDescString(";��ڵ㣺", String.valueOf(cbr.getFiberNodeID()),String.valueOf(dto.getFiberNodeID())));
			logDes.append(SystemLogRecorder.appendDescString(";˫������ǣ�", BusinessUtility.getCommomSettingValue("SET_G_YESNOFLAG",cbr.getBiDirectionFlag()),BusinessUtility.getCommomSettingValue("SET_G_YESNOFLAG",dto.getBiDirectionFlag())));
			logDes.append(SystemLogRecorder.appendDescString(";�ն���Դ��", BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALRECORDSOURCE",cbr.getRecordSource()),BusinessUtility.getCommomSettingValue("SET_A_CATVTERMINALRECORDSOURCE",dto.getRecordSource())));
			logDes.append(SystemLogRecorder.appendDescString(";�ն����ͣ�", BusinessUtility.getCommomSettingValue("SET_A_CATVTERMTYPE",cbr.getCatvTermType()),BusinessUtility.getCommomSettingValue("SET_A_CATVTERMTYPE",dto.getCatvTermType())));
			logDes.append(SystemLogRecorder.appendDescString(";��ע��Ϣ��", cbr.getComments(),dto.getComments()));
			
			//�½�
			if(dto.getStatus().equals("I")) dto.setCreateDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//��ͨ
			if(dto.getStatus().equals("N")) dto.setActiveDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//����
			if(dto.getStatus().equals("D")) dto.setCancelDate(new java.sql.Timestamp(System.currentTimeMillis()));
			//�ض�
			if(dto.getStatus().equals("S")) dto.setPauseDate(new java.sql.Timestamp(System.currentTimeMillis()));
			result=cbr.ejbUpdate(dto);
			
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CATV, "�޸�ģ���ն�", logDes.toString(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
    		throw new ServiceException("�޸�ģ���ն˳������������ԣ�");
		}
		if(result==-1)
			throw new ServiceException("ģ���ն˲����������ݣ��޷����£������ԣ�");
	}

	private void createCatvTermProcess(CatvTermProcessDTO ctpDto) throws ServiceException {
		if(ctpDto == null)
			throw new ServiceException("�ն˴����¼����Ϊ��");
		try{
			CatvTermProcessHome ctHome = HomeLocater.getCatvTermProcessHome();
			CatvTermProcess ctp = ctHome.create(ctpDto);
		}catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,e);
			throw new ServiceException("�����ն˴����¼����");
		}		
	}


	/**
	 * @param jcDto
	 * @param jcpDto
	 */
	public void updateCatvTerminalStatusAndSAStatus(JobCardDTO jcDto, CustServiceInteractionDTO csiDto) throws ServiceException {
		if(csiDto == null ||csiDto.getId()<=0)
			throw new ServiceException("csiDTO û�����úá�");
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
		}//�����ģ����ӿ�������
		else if(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CAO.equals(csiDto.getType())){
			//���¿ͻ�״̬Ϊ����
			CustomerService cs = new CustomerService(context);
			cs.updateCustomer(csiDto.getId(), EJBEvent.REGISTER_INSTALLATION_INFO);                                  
    		 //�޸�ҵ���˻������Ʒ��״̬
			Collection serviceAccountIDList=BusinessUtility.getServiceAccountIDListByCsiID(csiDto.getId());
			ServiceAccountService  serviceAccountService=new ServiceAccountService(context);
			serviceAccountService.updateSAStatus(serviceAccountIDList,EJBEvent.REGISTER_INSTALLATION_INFO);
			//���ģ����ӿ������������������˵�
			if((CommonConstDefinition.JOBCARD_TYPE_CATV).equals(jcDto.getJobType()) && (CommonConstDefinition.CATV_JOBCARD_SUBTYPE_Z.equals(jcDto.getSubType())))
			{
			//�޸��ն˵Ķ˿���
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
			throw new ServiceException("�����ն˶˿�������");
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
			throw new ServiceException("�����ն˶˿�������");
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
		throw new ServiceException("�����ն˶˿�������");
	}
		// TODO Auto-generated method stub
		
	}
}
