package com.dtv.oss.domain;

import javax.ejb.*;

import com.dtv.oss.dto.PaymentRecordDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class PaymentRecordBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer seqNo) throws CreateException {
    //setSeqNo(seqNo);
    return null;
  }
  public java.lang.Integer ejbCreate(PaymentRecordDTO dto) throws CreateException {
    /**@todo Complete this method*/
  	setReferType(dto.getReferType());
  	setReferID(dto.getReferID());
  	setCreateTime(dto.getCreateTime());
    setPaymentTime(dto.getPaymentTime());
    setOperatorId(dto.getOpID());
    setOrgId(dto.getOrgID());
    
    setCustId(dto.getCustID());
    setAcctId(dto.getAcctID());
    setAmount(dto.getAmount());
    setInvoiceNo(dto.getInvoiceNo());
    setPrepaymentType(dto.getPrepaymentType());
    setTicketNo(dto.getTicketNo());
    setTicketType(dto.getTicketType());
    setPayType(dto.getPayType());
    setInvoicedFlag(dto.getInvoicedFlag());
    setPaidTo(dto.getPaidTo());
    setMopId(dto.getMopID());
    setSourceType(dto.getSourceType());
    setSourceRecordId(dto.getSourceRecordID());
    setStatus(dto.getStatus());
    setAdjustmentFlag(dto.getAdjustmentFlag());
    setAdjustmentNo(dto.getAdjustmentNo());
    setCreatingMethod(dto.getCreatingMethod());
    setComments(dto.getComments());
    setFaPiaoNo(dto.getFaPiaoNo());
    setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
	setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(PaymentRecordDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setReferType(String referType);
  public abstract void setReferID(int referID);
  public abstract void setSeqNo(java.lang.Integer seqNo);
  public abstract void setCreateTime(java.sql.Timestamp createTime);
  public abstract void setPaymentTime(java.sql.Timestamp paymentTime);
  public abstract void setOperatorId(int operatorId);
  //
  public abstract void setInvoiceNo(int invoiceNo);
  public abstract void setPrepaymentType(String prepaymentType);
  public abstract void setTicketNo(String ticketNo);
  public abstract void setTicketType(String ticketType);
  //
  public abstract void setOrgId(int orgId);
  public abstract void setCustId(int custId);
  public abstract void setAcctId(int acctId);
  public abstract void setAmount(double amount);
  public abstract void setPayType(java.lang.String payType);
  public abstract void setInvoicedFlag(java.lang.String invoicedFlag);
  public abstract void setPaidTo(int paidTo);
  public abstract void setMopId(int mopId);
  public abstract void setSourceType(java.lang.String sourceType);
  public abstract void setSourceRecordId(int sourceRecordId);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getSeqNo();
  public abstract java.sql.Timestamp getCreateTime();
  public abstract java.sql.Timestamp getPaymentTime();
  public abstract int getOperatorId();
  public abstract int getOrgId();
  public abstract int getCustId();
  public abstract int getAcctId();
  public abstract double getAmount();
  public abstract int getInvoiceNo( );
  public abstract String getPrepaymentType();
  public abstract String getInvoicedFlag();
  public abstract String getTicketNo();
  public abstract String getTicketType();
  public abstract java.lang.String getPayType();
  
  public abstract int getPaidTo();
  public abstract int getMopId();
  public abstract java.lang.String getSourceType();
  public abstract int getSourceRecordId();
  public abstract java.lang.String getStatus();
  public abstract String getReferType();
  public abstract int getReferID();
  public abstract void setAdjustmentFlag(String adjustmentFlag);
  public abstract void setAdjustmentNo(int adjustmentNo);
  public abstract void setCreatingMethod(String creatingMethod);
  public abstract String getAdjustmentFlag();
  public abstract int getAdjustmentNo();
  public abstract String getCreatingMethod();
  public abstract void setComments(String comments);
  public abstract String getComments();
  public abstract String getFaPiaoNo();
  public abstract void setFaPiaoNo(String faPiaoNo);
  public abstract java.sql.Timestamp getDtCreate();
  public abstract java.sql.Timestamp getDtLastmod();
  public void ejbLoad() {
    /**@todo Complete this method*/
  }
  public void ejbStore() {
    /**@todo Complete this method*/
  }
  public void ejbActivate() {
    /**@todo Complete this method*/
  }
  public void ejbPassivate() {
    /**@todo Complete this method*/
  }
  public void unsetEntityContext() {
    this.entityContext = null;
  }
  public void setEntityContext(EntityContext entityContext) {
    this.entityContext = entityContext;
  }
  public int ejbUpdate(PaymentRecordDTO dto) {
	/** @todo Complete this method */
	if (dto.getDtLastmod() == null) {
		return -1;
	}

	if (!dto.getDtLastmod().equals(getDtLastmod())) {

		return -1;
	} else {
		try {
			EntityBeanAutoUpdate.update(dto, this);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
		return 0;
	}
}

}