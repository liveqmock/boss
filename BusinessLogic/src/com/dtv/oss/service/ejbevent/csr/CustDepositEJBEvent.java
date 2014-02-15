package com.dtv.oss.service.ejbevent.csr;

import java.util.ArrayList;
import java.util.Collection;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.PaymentRecordDTO;

public class CustDepositEJBEvent extends CsrAbstractEJbevent
{
	//原有押金费用记录号
	private int aino = 0;
	//费用列表
	private Collection acctItemList = null;
	//支付列表
	private Collection paymentList = new ArrayList();
	//费用DTO，用于创建费用记录用
	private AccountItemDTO acctItemDTO = null;
	//支付DTO，用于创建支付记录用
	private PaymentRecordDTO paymentDTO = null;
	//受理单DTO，用于创建受理单记录用
	private CustServiceInteractionDTO csiDTO = null;

	public int getAiNO()
	{
		return aino;
	}
	public void setAiNO(int aino)
	{
		this.aino = aino;
	}
	public Collection getAcctItemList()
	{
		return acctItemList;
	}
	public void setAcctItemList(Collection acctItemList)
	{
		this.acctItemList = acctItemList;
	}
	public Collection getPaymentList()
	{
		return paymentList;
	}
	public void setPaymentList(Collection paymentList)
	{
		this.paymentList = paymentList;
	}
	public CustServiceInteractionDTO getCsiDTO()
	{
		return csiDTO;
	}
	public void setCsiDTO(CustServiceInteractionDTO csiDTO)
	{
		this.csiDTO = csiDTO;
	}
	public AccountItemDTO getAcctItemDTO()
	{
		return acctItemDTO;
	}
	public void setAcctItemDTO(AccountItemDTO acctItemDTO)
	{
		this.acctItemDTO = acctItemDTO;
	}
	public PaymentRecordDTO getPaymentDTO()
	{
		return paymentDTO;
	}
	public void setPaymentDTO(PaymentRecordDTO paymentDTO)
	{
		this.paymentDTO = paymentDTO;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
