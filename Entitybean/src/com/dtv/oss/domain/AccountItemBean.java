package com.dtv.oss.domain;

import javax.ejb.*;
import com.dtv.oss.dto.AccountItemDTO;
import java.sql.Timestamp;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class AccountItemBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer aiNO) throws CreateException {
    //setAiNO(aiNO);
    return null;
     
  }
  public java.lang.Integer ejbCreate(AccountItemDTO dto) throws CreateException {
    //20140222 fapiao
      setFapiaoSerialNo(dto.getFapiaoSerialNo());
      setFapiaoHaoma(dto.getFapiaoHaoma());
     //end 20140222
    setBatchNO(dto.getBatchNO());
    setCustID(dto.getCustID());
    setAcctID(dto.getAcctID());
    setServiceAccountID(dto.getServiceAccountID());
    setAcctItemTypeID(dto.getAcctItemTypeID());
    setPsID(dto.getPsID());
    setValue(dto.getValue());
    setForcedDepositFlag(dto.getForcedDepositFlag());
    setDate1(dto.getDate1());
    setDate2(dto.getDate2());
    setBillingCycleID(dto.getBillingCycleID());
    setStatus(dto.getStatus());
    setCreateTime(dto.getCreateTime());
    setOperatorID(dto.getOperatorID());
    setOrgID(dto.getOrgID());
    setReferType(dto.getReferType());
    setReferID(dto.getReferID());
    setInvoiceFlag(dto.getInvoiceFlag());
    setCreatingMethod(dto.getCreatingMethod());
    setSetOffAmount(dto.getSetOffAmount());
    setInvoiceNO(dto.getInvoiceNO());
    setSetOffFlag(dto.getSetOffFlag());
    setDtCreate(new Timestamp(System.currentTimeMillis()));
    setDtLastmod(new Timestamp(System.currentTimeMillis()));
    setProductID(dto.getProductID());
    setSourceRecordID(dto.getSourceRecordID());
    setFeeType(dto.getFeeType());
    setBrID(dto.getBrID());
    setAdjustmentFlag(dto.getAdjustmentFlag());
    setAdjustmentNo(dto.getAdjustmentNo());
    setComments(dto.getComments());
    setCcID(dto.getCcID());
    setFeeSplitPlanID(dto.getFeeSplitPlanID());
    setRfBillingCycleFlag(dto.getRfBillingCycleFlag());
    return null;
  }
  public void ejbPostCreate(java.lang.Integer aiNO) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(AccountItemDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setFeeType(String feeType);
  
  public abstract String getFeeType();
  public abstract void setFeeSplitPlanID(int feeSplitPlanID);
  public abstract int getFeeSplitPlanID();
  public abstract void setCcID(int ccID);
  public abstract int getCcID();
  public abstract void setRfBillingCycleFlag(String rfBillingCycleFlag);
  public abstract String getRfBillingCycleFlag();
    //20140222 fapiao
    public abstract void setFapiaoHaoma(String fapiaoHaoma);
    public abstract String getFapiaoHaoma();
    public abstract void setFapiaoSerialNo(String fapiaoSerialNo);
    public abstract String getFapiaoSerialNo();
    //end //20140222 fapiao
  public abstract void setSourceRecordID(int sourceRecordID);
  public abstract void setBrID(int brID);
  public abstract int getBrID(); 
  public abstract void setProductID(int productID);
  public abstract void setCreatingMethod(String creatingMethod);
  public abstract void setForcedDepositFlag(String forcedDepositFlag);
  public abstract void setInvoiceFlag(String invoiceFlag);
  public abstract void setSetOffAmount(double setOffAmount);
  public abstract void setInvoiceNO(int invoiceNO);
  public abstract void setSetOffFlag(String setOffFlag);
  public abstract void setAiNO(java.lang.Integer aiNO);
  public abstract void setBatchNO(int batchNO);
  public abstract void setCustID(int custID);
  public abstract void setAcctID(int acctID);
  public abstract void setServiceAccountID(int serviceAccountID);
  public abstract void setAcctItemTypeID(java.lang.String acctItemTypeID);
  public abstract void setPsID(int psID);
  public abstract void setValue(double value);
  public abstract void setDate1(java.sql.Timestamp date1);
  public abstract void setDate2(java.sql.Timestamp date2);
  public abstract void setBillingCycleID(int billingCycleID);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract void setCreateTime(java.sql.Timestamp createTime);
  public abstract void setOperatorID(int operatorID);
  public abstract void setOrgID(int orgID);
  public abstract void setReferType(java.lang.String referType);
  public abstract void setReferID(int referID);
  public abstract void setComments(String comments);
  public abstract String getComments();
  public abstract java.lang.Integer getAiNO();
  public abstract int getBatchNO();
  public abstract int getSourceRecordID();
  public abstract int getProductID();
  public abstract int getCustID();
  public abstract int getAcctID();
  public abstract int getServiceAccountID();
  public abstract java.lang.String getAcctItemTypeID();
  public abstract java.lang.String getCreatingMethod();
  public abstract int getPsID();
  public abstract double getValue();
  public abstract java.sql.Timestamp getDate1();
  public abstract java.sql.Timestamp getDate2();
  public abstract int getBillingCycleID();
  public abstract java.lang.String getStatus();
  public abstract java.sql.Timestamp getDtCreate();
  public abstract java.sql.Timestamp getDtLastmod();
  public abstract java.sql.Timestamp getCreateTime();
  public abstract int getOperatorID();
  public abstract int getOrgID();
  public abstract java.lang.String getReferType();
  public abstract int getReferID();
  public abstract void setAdjustmentFlag(String adjustmentFlag);
  public abstract void setAdjustmentNo(int adjustmentNo);
   
  public abstract String getAdjustmentFlag();
  public abstract int getAdjustmentNo();
  
   
   public abstract String getForcedDepositFlag();
   public abstract String getInvoiceFlag();
   public abstract double getSetOffAmount();
   public abstract int getInvoiceNO();
   public abstract String getSetOffFlag();
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
  public int ejbUpdate(AccountItemDTO dto){
        /**@todo Complete this method*/
        if(dto.getDtLastmod()==null){
          return -1;
        }

        if(!dto.getDtLastmod().equals(getDtLastmod())){

          return -1;
        } else{
          try{
                EntityBeanAutoUpdate.update(dto, this);
          } catch(Exception e){
                e.printStackTrace();
                return -1;
          }
          setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
          return 0;
        }
  }

}