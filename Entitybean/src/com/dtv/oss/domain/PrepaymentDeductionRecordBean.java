package com.dtv.oss.domain;

import javax.ejb.*;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class PrepaymentDeductionRecordBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer seqNo) throws CreateException {
    //setSeqNo(seqNo);
    return null;
  }
  public java.lang.Integer ejbCreate(PrepaymentDeductionRecordDTO dto) throws CreateException {
    /**@todo Complete this method*/
  	setCreatingMethod(dto.getCreatingMethod());
   setCreateTime(dto.getCreateTime());
   setOpId(dto.getOpId());
   setOrgId(dto.getOrgId());
   setCustId(dto.getCustId());
  setAcctId(dto.getAcctId());
  setAmount(dto.getAmount());
  setPrepaymentType(dto.getPrepaymentType());
  setReferRecordType(dto.getReferRecordType());
  setReferRecordId(dto.getReferRecordId());
  setInvoicedFlag(dto.getInvoicedFlag());
  setInvoiceNo(dto.getInvoiceNo());
  setStatus(dto.getStatus());
  setAdjustmentFlag(dto.getAdjustmentFlag());
  setAdjustmentNo(dto.getAdjustmentNo());
  setComments(dto.getComments());
  setReferSheetID(dto.getReferSheetID());
  setReferSheetType(dto.getReferSheetType());
  setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
  setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(PrepaymentDeductionRecordDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setCreatingMethod(String creatingMethod);
  public abstract void setSeqNo(java.lang.Integer seqNo);
  public abstract void setCreateTime(java.sql.Timestamp createTime);
  public abstract void setOpId(int opId);
  public abstract void setOrgId(int orgId);
  public abstract void setCustId(int custId);
  public abstract void setAcctId(int acctId);
  public abstract void setAmount(double amount);
  public abstract void setPrepaymentType(java.lang.String prepaymentType);
  public abstract void setReferRecordType(java.lang.String referRecordType);
  public abstract void setReferRecordId(int referRecordId);
  public abstract void setInvoicedFlag(java.lang.String invoicedFlag);
  public abstract void setInvoiceNo(int invoiceNo);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getSeqNo();
  public abstract java.sql.Timestamp getCreateTime();
  public abstract int getOpId();
  public abstract int getOrgId();
  public abstract int getCustId();
  public abstract int getAcctId();
  public abstract java.lang.String getCreatingMethod(); 
  public abstract double getAmount();
  public abstract java.lang.String getPrepaymentType();
  public abstract java.lang.String getReferRecordType();
  public abstract int getReferRecordId();
  public abstract java.lang.String getInvoicedFlag();
  public abstract int getInvoiceNo();
  public abstract java.lang.String getStatus();
  public abstract java.sql.Timestamp getDtCreate();
  public abstract java.sql.Timestamp getDtLastmod();
  public abstract void setAdjustmentFlag(String adjustmentFlag);
  public abstract void setAdjustmentNo(int adjustmentNo);
   
  public abstract String getAdjustmentFlag();
  public abstract int getAdjustmentNo();
  
  public abstract void setComments(String comments);
  public abstract void setReferSheetID(int referSheetID);
  public abstract void setReferSheetType(String referSheetType);
  public abstract String getComments();
  public abstract int getReferSheetID();
  public abstract String getReferSheetType();
  
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
  public int ejbUpdate(PrepaymentDeductionRecordDTO dto) {
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
