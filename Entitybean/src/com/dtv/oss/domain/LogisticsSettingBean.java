package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.LogisticsSettingDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

abstract public class LogisticsSettingBean implements EntityBean {
  EntityContext entityContext;
  public java.lang.Integer ejbCreate(java.lang.Integer seqNo) throws CreateException {
    //setSeqNo(seqNo);
    return null;
  }
  public java.lang.Integer ejbCreate(LogisticsSettingDTO dto) throws CreateException {
  	setInAndOut(dto.getInAndOut());
  	setOutOrgnization(dto.getOutOrgnization());
  	setMatchAndPreauth(dto.getMatchAndPreauth());
  	setPreauthProductid1(dto.getPreauthProductid1());
  	setPreauthProductid2(dto.getPreauthProductid2());
  	setPreauthProductid3(dto.getPreauthProductid3());
  	setPreauthProductid4(dto.getPreauthProductid4());
  	setPreauthProductid5(dto.getPreauthProductid5());
  	setStatus(dto.getStatus());
	setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
	setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
    return null;
  }
  public void ejbPostCreate(java.lang.Integer seqNo) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbPostCreate(LogisticsSettingDTO dto) throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() throws RemoveException {
    /**@todo Complete this method*/
  }
  public abstract void setSeqNo(java.lang.Integer seqNo);
  public abstract void setInAndOut(java.lang.String inAndOut);
  public abstract void setOutOrgnization(int outOrgnization);
  public abstract void setMatchAndPreauth(java.lang.String matchAndPreauth);
  public abstract void setPreauthProductid1(int preauthProductid1);
  public abstract void setPreauthProductid2(int preauthProductid2);
  public abstract void setPreauthProductid3(int preauthProductid3);
  public abstract void setPreauthProductid4(int preauthProductid4);
  public abstract void setPreauthProductid5(int preauthProductid5);
  public abstract void setStatus(java.lang.String status);
  public abstract void setDtCreate(java.sql.Timestamp dtCreate);
  public abstract void setDtLastmod(java.sql.Timestamp dtLastmod);
  public abstract java.lang.Integer getSeqNo();
  public abstract java.lang.String getInAndOut();
  public abstract int getOutOrgnization();
  public abstract java.lang.String getMatchAndPreauth();
  public abstract int getPreauthProductid1();
  public abstract int getPreauthProductid2();
  public abstract int getPreauthProductid3();
  public abstract int getPreauthProductid4();
  public abstract int getPreauthProductid5();
  public abstract java.lang.String getStatus();
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
  public int ejbUpdate(LogisticsSettingDTO dto) {
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