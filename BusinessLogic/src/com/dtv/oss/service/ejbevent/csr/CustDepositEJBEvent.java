package com.dtv.oss.service.ejbevent.csr;

import java.util.ArrayList;
import java.util.Collection;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.PaymentRecordDTO;

public class CustDepositEJBEvent extends CsrAbstractEJbevent
{
	//ԭ��Ѻ����ü�¼��
	private int aino = 0;
	//�����б�
	private Collection acctItemList = null;
	//֧���б�
	private Collection paymentList = new ArrayList();
	//����DTO�����ڴ������ü�¼��
	private AccountItemDTO acctItemDTO = null;
	//֧��DTO�����ڴ���֧����¼��
	private PaymentRecordDTO paymentDTO = null;
	//����DTO�����ڴ���������¼��
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
