package com.dtv.oss.domain;

import javax.ejb.*;

import com.dtv.oss.dto.GuestLedgerDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class GuestLedgerBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer seqNo) throws CreateException {
    //setSeqNo(seqNo);
    return null;
  }
  public java.lang.Integer ejbCreate(GuestLedgerDTO dto) throws CreateException {
      setCustId(dto.getCustId());
      setAcctId(dto.getAcctId());
      setReferType(dto.getReferType());
      setReferId(dto.getReferId());
      setReferAmount(dto.getReferAmount());
      setReason(dto.getReason());
      setComments(dto.getComments());
      setStatus(dto.getStatus());
      setReferSheetID(dto.getReferSheetID());
      setConfirmAmount(dto.getConfirmAmount());
      setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(GuestLedgerDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setSeqNo(java.lang.Integer seqNo);
  public abstract void setReferSheetID(int referSheetID);
  public abstract int getReferSheetID();
  public abstract void setCustId(int custId);
  public abstract void setAcctId(int acctId);
  public abstract void setReferType(java.lang.String referType);
  public abstract void setReferId(int referId);
  public abstract void setReferAmount(double referAmount);
  public abstract void setReason(java.lang.String reason);
  public abstract void setComments(java.lang.String comments);
  public abstract void setStatus(java.lang.String status);
  public abstract void setConfirmAmount(double confirmAmount);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getSeqNo();
  public abstract int getCustId();
  public abstract int getAcctId();
  public abstract java.lang.String getReferType();
  public abstract int getReferId();
  public abstract double getReferAmount();
  public abstract java.lang.String getReason();
  public abstract java.lang.String getComments();
  public abstract java.lang.String getStatus();
  public abstract double getConfirmAmount();
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
  public int ejbUpdate(GuestLedgerDTO dto) {
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