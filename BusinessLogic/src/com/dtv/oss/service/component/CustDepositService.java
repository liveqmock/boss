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
	 * 客户押金退还
	 * @param acctItemList 费用列表
	 * @param paymentList  支付列表
	 * @param dto          受理单DTO
	 * @param aino         原来押金费用记录号
	 * @throws ServiceException
	 */
	public void returnDeposit(AccountItemDTO acctItemDto,PaymentRecordDTO paymentDto
							  ,CustServiceInteractionDTO csiDto,int aino) throws ServiceException{
		if(acctItemDto==null)
			throw new ServiceException("acctItemDto参数为空，不能创建对应的费用记录！");
		if(paymentDto==null)
			throw new ServiceException("paymentDto参数为空，不能创建对应的支付记录！");
		if(csiDto==null)
			throw new ServiceException("csiDto参数为空，不能创建客户押金退还受理单记录！");
		if(aino<=0)
			throw new ServiceException("aino参数错误，不能更改原有押金费用记录状态！");
		LogUtility.log(clazz,LogLevel.DEBUG,"进入客户押金退还：");
		try
		{
			double returnVaule=acctItemDto.getValue();
			double confiscateValue=0;
			LogUtility.log(clazz,LogLevel.DEBUG,"returnDeposit",csiDto);
			//创建客户押金退还受理单记录
			CustServiceInteractionHome csiHome=HomeLocater.getCustServiceInteractionHome();
			 
			CustServiceInteraction csi=csiHome.create(csiDto);
			//设置受理单Status、CreateTime等值
			csi.setCreateTime(TimestampUtility.getCurrentTimestamp());
			csi.setDtCreate(TimestampUtility.getCurrentTimestamp());
			csi.setPaymentStatus("R");
			csi.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			csi.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_FR);
			csi.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
			LogUtility.log(clazz,LogLevel.DEBUG,"押金退还受理单ID= "+ csi.getId().intValue());
			//add by chen jiang ,help to tell the action;
			String actionDesc ="";
			String action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			AccountItemHome acctItemHome1 = HomeLocater.getAccountItemHome();
			AccountItem acctItem1 = acctItemHome1.findByPrimaryKey(new Integer(aino));
			if(acctItem1.getValue()>Math.abs(acctItemDto.getValue())){
				actionDesc ="部分退款";
				confiscateValue=acctItem1.getValue()-Math.abs(acctItemDto.getValue());
			}else{ 
				actionDesc = "全额退款";
			}
			Integer operatorID=(Integer) context.get(Service.OPERATIOR_ID);
			recordCsiProcessLog(csi.getId().intValue(), action, operatorID.intValue(), actionDesc);
			
			//设置费用记录DTO
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
			//设置支付记录DTO
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
			//产生对应的费用记录/支付记录/销账记录
			ImmediatePayFeeService payfee = new ImmediatePayFeeService(acctItemList,paymentList,null,null);
			// 持久化费用、支付、预存抵扣
			FeeService.createFeeAndPaymentAndPreDuciton(payfee.getFeeList(),payfee.getPaymentList(),payfee.getPrePaymentList(),null);
			payfee.setType(CommonConstDefinition.SETOFFREFERTYPE_C);
			payfee.setId(csi.getId().intValue());
			PayFeeUtil.updatePrepaymentList(payfee.getPaymentList());
			payfee.payFeeDB(payfee.payFee());
			
			//更新原来的押金记录的状态 为 退还
			AccountItemHome acctItemHome=HomeLocater.getAccountItemHome();
			AccountItem acctItem=acctItemHome.findByPrimaryKey(new Integer(aino));
			acctItem.setStatus(CommonConstDefinition.AISTATUS_RETURN);
			//插入相关的日志记录
			String confiscateValueDesc=(confiscateValue>0)?(",没收金额:"+confiscateValue):"";
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
	    			PublicService.getCurrentOperatorID(context),csiDto.getCustomerID() ,
					SystemLogRecorder.LOGMODULE_CUSTSERV, "押金退还", "客户押金退还--"+actionDesc
					+",受理单ID:"+ csi.getId().intValue()
					+",退还押金金额:"+returnVaule
					+confiscateValueDesc,
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e1)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "客户押金退还时定位出错！");
			throw new ServiceException("客户押金退还时定位出错！");
		}
		catch(FinderException e2)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询押金费用记录信息出错，原因：押金费用记录查找出错！");
			throw new ServiceException("查询押金费用记录信息出错，原因：押金费用记录查找出错");
		}
		catch(CreateException e2)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "客户押金退还出错！");
			throw new ServiceException("客户押金退还出错！");
		}
		catch (ServiceException e)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "客户押金退还时创建日志出错！");
			throw new ServiceException("客户押金退还时创建日志出错！");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束客户押金退还：");
	}
	/**
	 *  创建 CSI 日志记录
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
		    throw new ServiceException("纪录受理单日志信息时定位错误");	            		
    	}catch(CreateException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,"recordCsiProcessLog",e);
		    throw new ServiceException("纪录受理单日志信息时错误");
		}catch(FinderException e) {
		    LogUtility.log(clazz,LogLevel.ERROR,e);
		    throw new ServiceException("纪录受理单日志信息时查找操作员错误");	            		
    		
    	}
	}
	/**
	 * 客户押金没收
	 * @param dto          受理单DTO
	 * @param aino         原来押金费用记录号
	 * @throws ServiceException
	 */
	public void confiscateDeposit(CustServiceInteractionDTO dto,int aino)throws ServiceException{
		if(dto==null)
			throw new ServiceException("dto参数为空，不能创建客户押金没收受理单记录！");
		if(aino<=0)
			throw new ServiceException("aino参数错误，不能更改原有押金费用记录状态！");

		LogUtility.log(clazz,LogLevel.DEBUG,"进入客户押金没收：");
		try
		{
			LogUtility.log(clazz,LogLevel.DEBUG,"confiscateDeposit",dto);
			//创建客户押金没收受理单记录
			CustServiceInteractionHome csiHome=HomeLocater.getCustServiceInteractionHome();
			CustServiceInteraction csi=csiHome.create(dto);
			//设置Status、CreateTime
			csi.setCreateTime(TimestampUtility.getCurrentTimestamp());
			csi.setDtCreate(TimestampUtility.getCurrentTimestamp());
			csi.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			csi.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_FS);
			csi.setStatus(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS);
			csi.setPaymentStatus("D");
			LogUtility.log(clazz,LogLevel.DEBUG,"押金没收受理单ID= "+ csi.getId().intValue());
//			add by chen jiang ,help to tell the action;
			String actionDesc ="押金没收";
			String action = CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS;
			 
			Integer operatorID=(Integer) context.get(Service.OPERATIOR_ID);
			recordCsiProcessLog(csi.getId().intValue(), action, operatorID.intValue(), actionDesc);
			
			//更新原来的押金记录的状态 为 没收
			AccountItemHome acctItemHome=HomeLocater.getAccountItemHome();
			AccountItem acctItem=acctItemHome.findByPrimaryKey(new Integer(aino));
			acctItem.setStatus(CommonConstDefinition.AISTATUS_DELETE);
			//插入相关的日志记录
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context),
					PublicService.getCurrentOperatorID(context), dto.getCustomerID(),
					SystemLogRecorder.LOGMODULE_CUSTSERV, "押金没收", "押金没收,受理单ID:"+ csi.getId().intValue()
					+",没收押金金额:"+acctItem.getValue(),
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e1)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "客户押金没收时定位出错！");
			throw new ServiceException("客户押金没收时定位出错！");
		}
		catch(FinderException e2)
		{
			LogUtility.log(clazz,LogLevel.WARN,"查询押金费用记录信息出错，原因：押金费用记录查找出错！");
			throw new ServiceException("查询押金费用记录信息出错，原因：押金费用记录查找出错");
		}
		catch(CreateException e3)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "客户押金没收出错！");
			throw new ServiceException("客户押金没收出错！");
		}
		catch (ServiceException e4)
		{
			LogUtility.log(clazz,LogLevel.ERROR, "客户押金没收时创建日志出错！");
			throw new ServiceException("客户押金没收时创建日志出错！");
		}
		LogUtility.log(clazz,LogLevel.DEBUG,"结束客户押金没收：");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
