package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.ArrayList;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.AccountItem;
import com.dtv.oss.domain.AccountItemHome;
import com.dtv.oss.domain.CsiProcessLogHome;
import com.dtv.oss.domain.CustServiceInteraction;
import com.dtv.oss.domain.CustServiceInteractionHome;
import com.dtv.oss.domain.Operator;
import com.dtv.oss.domain.OperatorHome;
import com.dtv.oss.dto.CsiProcessLogDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.PayFeeUtil;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.service.util.BusinessUtility;

public class CustDepositService extends AbstractService {

	private ServiceContext context;
	private static final Class clazz=CustDepositService.class;

	public CustDepositService(ServiceContext inContext){
		this.context=inContext;
	}

	/**
	 * �ͻ�Ѻ���˻�
	 * @param acctItemList �����б�
	 * @param paymentList  ֧���б�
	 * @param dto          ����DTO
	 * @param aino         ԭ��Ѻ����ü�¼��
	 * @throws ServiceException
	 */
	public void returnDeposit(AccountItemDTO acctItemDto,PaymentRecordDTO paymentDto
							  ,CustServiceInteractionDTO csiDto,int aino) throws ServiceException{
		if(acctItemDto==null)
			throw new ServiceException("acctItemDto����Ϊ�գ����ܴ�����Ӧ�ķ��ü�¼��");
		if(paymentDto==null)
			throw new ServiceException("paymentDto����Ϊ�գ����ܴ�����Ӧ��֧����¼��");
		if(csiDto==null)
			throw new ServiceException("csiDto����Ϊ�գ����ܴ����ͻ�Ѻ���˻�������¼��");
		if(aino<=0)
			throw new ServiceException("aino�������󣬲��ܸ���ԭ��Ѻ����ü�¼״̬��");
		LogUtility.log(clazz,LogLevel.DEBUG,"����ͻ�Ѻ���˻���");
		try
		{
			double returnVaule=acctItemDto.getValue();
			double confiscateValue=0;
			LogUtility.log(clazz,LogLevel.DEBUG,"returnDeposit",csiDto);
			//�����ͻ�Ѻ���˻�������¼
			CustServiceInteractionHome csiHome=HomeLocater.getCustServiceInteractionHome();
			 
			CustServiceInteraction csi=csiHome.create(csiDto);
			//��������Status��CreateTime��ֵ
			csi.setCreateTime(TimestampUtility.getCurrentTimestamp());
			csi.setDtCreate(TimestampUtility.getCurrentTimestamp());
			csi.setPaymentStatus("R");
			csi.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			csi.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_FR);
			csi.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
			LogUtility.log(clazz,LogLevel.DEBUG,"Ѻ���˻�����ID= "+ csi.getId().intValue());
			//add by chen jiang ,help to tell the action;
			String actionDesc ="";
			String action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			AccountItemHome acctItemHome1 = HomeLocater.getAccountItemHome();
			AccountItem acctItem1 = acctItemHome1.findByPrimaryKey(new Integer(aino));
			if(acctItem1.getValue()>Math.abs(acctItemDto.getValue())){
				actionDesc ="�����˿�";
				confiscateValue=acctItem1.getValue()-Math.abs(acctItemDto.getValue());
			}else{ 
				actionDesc = "ȫ���˿�";
			}
			Integer operatorID=(Integer) context.get(Service.OPERATIOR_ID);
			recordCsiProcessLog(csi.getId().intValue(), action, operatorID.intValue(), actionDesc);
			
			//���÷��ü�¼DTO
			acctItemDto.setAcctItemTypeID(acctItem1.getAcctItemTypeID());
			acctItemDto.setFeeType(acctItem1.getFeeType());
			acctItemDto.setForcedDepositFlag(acctItem1.getForcedDepositFlag());
			acctItemDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			acctItemDto.setCreatingMethod(CommonConstDefinition.FTCREATINGMETHOD_R);
			acctItemDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			acctItemDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			acctItemDto.setInvoiceFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
			acctItemDto.setOperatorID(PublicService.getCurrentOperatorID(this.context));
			acctItemDto.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(acctItemDto.getOperatorID()));
			acctItemDto.setReferID(csi.getId().intValue());
			acctItemDto.setReferType(CommonConstDefinition.FTREFERTYPE_M);
			acctItemDto.setSetOffFlag(CommonConstDefinition.SETOFFFLAG_W);
			acctItemDto.setStatus(CommonConstDefinition.AISTATUS_RETURN);
			//����֧����¼DTO
			paymentDto.setCreateTime(TimestampUtility.getCurrentTimestamp());
			paymentDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
			paymentDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			paymentDto.setInvoicedFlag(CommonConstDefinition.FTINVOICEDFLAG_NO);
			paymentDto.setOpID(PublicService.getCurrentOperatorID(this.context));
			paymentDto.setOrgID(BusinessUtility.getOpOrgIDByOperatorID(paymentDto.getOpID()));
			paymentDto.setPaymentTime(TimestampUtility.getCurrentDateWithoutTime());
			paymentDto.setReferType(CommonConstDefinition.FTREFERTYPE_M);
			paymentDto.setSourceType(CommonConstDefinition.PAYMENTRECORDSOURCETYPE_ACCEPT);
			paymentDto.setStatus(CommonConstDefinition.AISTATUS_VALIDATE);
			paymentDto.setPaidTo(csi.getId().intValue());
			paymentDto.setReferID(csi.getId().intValue());
			paymentDto.setSourceRecordID(csi.getId().intValue());

			Collection acctItemList = new ArrayList();
			Collection paymentList = new ArrayList();
			acctItemList.add(acctItemDto);
			paymentList.add(paymentDto);
			//������Ӧ�ķ��ü�¼/֧����¼/���˼�¼
			ImmediatePayFeeService payfee = new ImmediatePayFeeService(acctItemList,paymentList,null,null);
			// �־û����á�֧����Ԥ��ֿ�
			FeeService.createFeeAndPaymentAndPreDuciton(payfee.getFeeList(),payfee.getPaymentList(),payfee.getPrePaymentList(),null);
			payfee.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
			payfee.setId(csi.getId().intValue());
			PayFeeUtil.updatePrepaymentList(payfee.getPaymentList());
			payfee.payFeeDB(payfee.payFee());
			
			//����ԭ����Ѻ���¼��״̬ Ϊ �˻�
			AccountItemHome acctItemHome=HomeLocater.getAccountItemHome();
			AccountItem acctItem=acctItemHome.findByPrimaryKey(new Integer(aino));
			acctItem.setStatus(CommonConstDefinition.AISTATUS_RETURN);
			//������ص���־��¼
			String confiscateValueDesc=(confiscateValue>0)?(",û�ս��:"+confiscateValue):"";
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
	    			PublicService.getCurrentOperatorID(context),csiDto.getCustomerID() ,
					SystemLogRecorder.LOGMODULE_CUSTSERV, "Ѻ���˻�", "�ͻ�Ѻ���˻�--"+actionDesc
					+",����ID:"+ csi.getId().intValue()
					+",�˻�Ѻ����:"+returnVaule
					+confiscateValueDesc,
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e1)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "�ͻ�Ѻ���˻�ʱ��λ����");
			throw new ServiceException("�ͻ�Ѻ���˻�ʱ��λ����");
		}
		catch(FinderException e2)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯѺ����ü�¼��Ϣ����ԭ��Ѻ����ü�¼���ҳ���");
			throw new ServiceException("��ѯѺ����ü�¼��Ϣ����ԭ��Ѻ����ü�¼���ҳ���");
		}
		catch(CreateException e2)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "�ͻ�Ѻ���˻�����");
			throw new ServiceException("�ͻ�Ѻ���˻�����");
		}
		catch (ServiceException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "�ͻ�Ѻ���˻�ʱ������־����");
			throw new ServiceException("�ͻ�Ѻ���˻�ʱ������־����");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ͻ�Ѻ���˻���");
	}
	/**
	 *  ���� CSI ��־��¼
	 * @param csiid
	 * @param action
	 * @param operatorid
	 * @param description
	 * @throws HomeFactoryException
	 * @throws CreateException
	 */
	private void recordCsiProcessLog(int csiid, String action, int operatorid, String description) throws  ServiceException{
		try{
			OperatorHome homeOpe = HomeLocater.getOperatorHome();
			Operator operator = homeOpe.findByPrimaryKey(new Integer(operatorid));
			CsiProcessLogDTO cplDto = new CsiProcessLogDTO();
			cplDto.setCsiID(csiid);
			cplDto.setAction(action);
			cplDto.setActionTime(TimestampUtility.getCurrentTimestamp());
			cplDto.setOperatorID(operatorid);
			cplDto.setOrgID(operator.getOrgID());
			cplDto.setDescription(description);
			CsiProcessLogHome home = HomeLocater.getCsiProcessLogHome();
			LogUtility.log(CustDepositService.class,LogLevel.DEBUG,"recordCsiProcessLog",cplDto);
			home.create(cplDto);
		}catch(HomeFactoryException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"recordCsiProcessLog",e);
		    throw new ServiceException("��¼������־��Ϣʱ��λ����");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"recordCsiProcessLog",e);
		    throw new ServiceException("��¼������־��Ϣʱ����");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,e);
		    throw new ServiceException("��¼������־��Ϣʱ���Ҳ���Ա����");	            		
    		
    	}
	}
	/**
	 * �ͻ�Ѻ��û��
	 * @param dto          ����DTO
	 * @param aino         ԭ��Ѻ����ü�¼��
	 * @throws ServiceException
	 */
	public void confiscateDeposit(CustServiceInteractionDTO dto,int aino)throws ServiceException{
		if(dto==null)
			throw new ServiceException("dto����Ϊ�գ����ܴ����ͻ�Ѻ��û��������¼��");
		if(aino<=0)
			throw new ServiceException("aino�������󣬲��ܸ���ԭ��Ѻ����ü�¼״̬��");

		LogUtility.log(clazz,LogLevel.DEBUG,"����ͻ�Ѻ��û�գ�");
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"confiscateDeposit",dto);
			//�����ͻ�Ѻ��û��������¼
			CustServiceInteractionHome csiHome=HomeLocater.getCustServiceInteractionHome();
			CustServiceInteraction csi=csiHome.create(dto);
			//����Status��CreateTime
			csi.setCreateTime(TimestampUtility.getCurrentTimestamp());
			csi.setDtCreate(TimestampUtility.getCurrentTimestamp());
			csi.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			csi.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_FS);
			csi.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
			csi.setPaymentStatus("D");
			LogUtility.log(clazz,LogLevel.DEBUG,"Ѻ��û������ID= "+ csi.getId().intValue());
//			add by chen jiang ,help to tell the action;
			String actionDesc ="Ѻ��û��";
			String action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			 
			Integer operatorID=(Integer) context.get(Service.OPERATIOR_ID);
			recordCsiProcessLog(csi.getId().intValue(), action, operatorID.intValue(), actionDesc);
			
			//����ԭ����Ѻ���¼��״̬ Ϊ û��
			AccountItemHome acctItemHome=HomeLocater.getAccountItemHome();
			AccountItem acctItem=acctItemHome.findByPrimaryKey(new Integer(aino));
			acctItem.setStatus(CommonConstDefinition.AISTATUS_DELETE);
			//������ص���־��¼
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
					PublicService.getCurrentOperatorID(context), dto.getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "Ѻ��û��", "Ѻ��û��,����ID:"+ csi.getId().intValue()
					+",û��Ѻ����:"+acctItem.getValue(),
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e1)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "�ͻ�Ѻ��û��ʱ��λ����");
			throw new ServiceException("�ͻ�Ѻ��û��ʱ��λ����");
		}
		catch(FinderException e2)
		{
			LogUtility.log(clazz,LogLevel.WARN,"��ѯѺ����ü�¼��Ϣ����ԭ��Ѻ����ü�¼���ҳ���");
			throw new ServiceException("��ѯѺ����ü�¼��Ϣ����ԭ��Ѻ����ü�¼���ҳ���");
		}
		catch(CreateException e3)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "�ͻ�Ѻ��û�ճ���");
			throw new ServiceException("�ͻ�Ѻ��û�ճ���");
		}
		catch (ServiceException e4)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "�ͻ�Ѻ��û��ʱ������־����");
			throw new ServiceException("�ͻ�Ѻ��û��ʱ������־����");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"�����ͻ�Ѻ��û�գ�");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
