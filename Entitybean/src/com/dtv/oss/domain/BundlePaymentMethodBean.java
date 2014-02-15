package com.dtv.oss.domain;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
 
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import java.sql.Timestamp;

import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class BundlePaymentMethodBean implements EntityBean {
    EntityContext entityContext;
    public BundlePaymentMethodPK ejbCreate(int bundleID,
                                           String rfBillingCycleFlag) throws
            CreateException {

       
        return null;
    }

    public BundlePaymentMethodPK ejbCreate(BundlePaymentMethodDTO dto) throws CreateException {
    	 setBundleID(dto.getBundleID());
         setRfBillingCycleFlag(dto.getRfBillingCycleFlag());
         setBundlePaymentName(dto.getBundlePaymentName());
         setTimeUnitLengthNumber(dto.getTimeUnitLengthNumber());
         setTimeUnitLengthType(dto.getTimeUnitLengthType());
         setDtCreate(new java.sql.Timestamp(System.currentTimeMillis()));
  		setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));   
        return null;
    }

    public void ejbPostCreate(int bundleID, String rfBillingCycleFlag) throws
            CreateException {
    }

    public void ejbPostCreate(BundlePaymentMethodDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setBundleID(int bundleID);

    public abstract int getBundleID();

    public abstract void setRfBillingCycleFlag(String rfBillingCycleFlag);

    public abstract String getRfBillingCycleFlag();

    public abstract void setBundlePaymentName(String bundlePaymentName);

    public abstract String getBundlePaymentName();

    public abstract void setTimeUnitLengthNumber(int timeUnitLengthNumber);

    public abstract int getTimeUnitLengthNumber();

    public abstract void setTimeUnitLengthType(String timeUnitLengthType);

    public abstract String getTimeUnitLengthType();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();

    public void ejbLoad() {
    }

    public void ejbStore() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void unsetEntityContext() {
        this.entityContext = null;
    }

    public void setEntityContext(EntityContext entityContext) {
        this.entityContext = entityContext;
    }
    public int ejbUpdate(BundlePaymentMethodDTO dto){
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
