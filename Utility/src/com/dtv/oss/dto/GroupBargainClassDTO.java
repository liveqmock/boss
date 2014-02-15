package com.dtv.oss.dto;

 
import java.sql.Timestamp;

public class GroupBargainClassDTO  implements ReflectionSupport, java.io.Serializable{
  private String name;
  private String keepBilling;
  private String description;
  private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int ID;
  private int newProductPackage1;
  private int newProductPackage2;
  private int newProductPackage3;
  private int newProductPackage4;
  private int newProductPackage5;
  private String timeLengthUnitType;
  private int timeLengthUnitNumber;
  private String allowPause;
  private String allTransition;
  private String allowTransfer;
  private String allowCancel;
  private int createOpID;
  private Timestamp createTime;
  private int updateOpID;
  private Timestamp updateTime;
  public GroupBargainClassDTO()
   {
   }
 public GroupBargainClassDTO(String keepBilling,int id, String name, String description, String status, int newProductPackage1, int newProductPackage2, int newProductPackage3,int newProductPackage4,int newProductPackage5,String timeLengthUnitType, Timestamp dtCreate, Timestamp dtLastmod,
                  int timeLengthUnitNumber, String allowPause, String allTransition,String allowTransfer, String allowCancel ,int createOpID,Timestamp createTime, int updateOpID, Timestamp updateTime)
        {
                setID(ID);
                setName(name);
                setKeepBilling(keepBilling);
                setDescription(description);
                setStatus(status);
                setNewProductPackage1(newProductPackage1);
                setNewProductPackage2(newProductPackage2);
                setNewProductPackage3(newProductPackage3);
                setNewProductPackage4(newProductPackage4);
                setNewProductPackage5(newProductPackage5);
                setTimeLengthUnitType(timeLengthUnitType);
                setTimeLengthUnitNumber(timeLengthUnitNumber);
                setAllowPause(allowPause);
                setAllTransition(allTransition);
                setAllowTransfer(allowTransfer);
                setAllowCancel(allowCancel);
                setCreateOpID(createOpID);
                setStatus(status);
                setCreateTime(createTime);
                setUpdateOpID(updateOpID);
                setUpdateTime(updateTime);
                setDtCreate(dtCreate);
                setDtLastmod(dtLastmod);
        }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
      put("name");
  }
  public String getKeepBilling() {
    return keepBilling;
  }

  public void setKeepBilling(String keepBilling) {
    this.keepBilling = keepBilling;
      put("keepBilling");
  }
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
     put("description");
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
     put("status");
  }

  public Timestamp getDtCreate() {
    return dtCreate;
  }

  public void setDtCreate(Timestamp dtCreate) {
    this.dtCreate = dtCreate;

  }

  public Timestamp getDtLastmod() {
    return dtLastmod;
  }

  public void setDtLastmod(Timestamp dtLastmod) {
    this.dtLastmod = dtLastmod;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public int getNewProductPackage1() {
    return newProductPackage1;
  }

  public void setNewProductPackage1(int newProductPackage1) {
    this.newProductPackage1 = newProductPackage1;
     put("newProductPackage1");
  }

  public int getNewProductPackage2() {
    return newProductPackage2;
  }

  public void setNewProductPackage2(int newProductPackage2) {
    this.newProductPackage2 = newProductPackage2;
     put("newProductPackage2");
  }

  public int getNewProductPackage3() {
    return newProductPackage3;
  }

  public void setNewProductPackage3(int newProductPackage3) {
    this.newProductPackage3 = newProductPackage3;
     put("newProductPackage3");
  }

  public int getNewProductPackage4() {
    return newProductPackage4;
  }

  public void setNewProductPackage4(int newProductPackage4) {
    this.newProductPackage4 = newProductPackage4;
     put("newProductPackage4");
  }

  public int getNewProductPackage5() {
    return newProductPackage5;
  }

  public void setNewProductPackage5(int newProductPackage5) {
    this.newProductPackage5 = newProductPackage5;
     put("newProductPackage5");
  }

  public String getTimeLengthUnitType() {
    return timeLengthUnitType;
  }

  public void setTimeLengthUnitType(String timeLengthUnitType) {
    this.timeLengthUnitType = timeLengthUnitType;
     put("timeLengthUnitType");
  }

  public int getTimeLengthUnitNumber() {
    return timeLengthUnitNumber;
  }

  public void setTimeLengthUnitNumber(int timeLengthUnitNumber) {
    this.timeLengthUnitNumber = timeLengthUnitNumber;
     put("timeLengthUnitNumber");
  }

  public String getAllowPause() {
    return allowPause;
  }

  public void setAllowPause(String allowPause) {
    this.allowPause = allowPause;
     put("allowPause");
  }

  public String getAllTransition() {
    return allTransition;
  }

  public void setAllTransition(String allTransition) {
    this.allTransition = allTransition;
     put("allTransition");
  }

  public String getAllowTransfer() {
    return allowTransfer;
  }

  public void setAllowTransfer(String allowTransfer) {
    this.allowTransfer = allowTransfer;
     put("allowTransfer");
  }

  public String getAllowCancel() {
    return allowCancel;
  }

  public void setAllowCancel(String allowCancel) {
    this.allowCancel = allowCancel;
     put("allowCancel");
  }

  public int getCreateOpID() {
    return createOpID;
  }

  public void setCreateOpID(int createOpID) {
    this.createOpID = createOpID;
     put("createOpID");
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
     put("createTime");
  }

  public int getUpdateOpID() {
    return updateOpID;
  }

  public void setUpdateOpID(int updateOpID) {
    this.updateOpID = updateOpID;
     put("updateOpID");
  }

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
     put("updateTime");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        GroupBargainClassDTO that = (GroupBargainClassDTO) obj;
        return ( ( (this.getName() == null) && (that.getName() == null)) ||
                (this.getName() != null && this.getName().equals(that.getName()))) &&
            ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
             ( ( (this.getKeepBilling() == null) && (that.getKeepBilling() == null)) ||
                 (this.getKeepBilling() != null && this.getKeepBilling().equals(that.getKeepBilling()))) &&  
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
                 (this.getStatus() != null &&
                 this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getID() == that.getID() &&
            this.getNewProductPackage1() == that.getNewProductPackage1
            () && this.getNewProductPackage2() == that.getNewProductPackage2() &&
            this.getNewProductPackage3() == that.getNewProductPackage3() &&
            this.getNewProductPackage4() == that.getNewProductPackage4() &&
            this.getNewProductPackage5() == that.getNewProductPackage5() &&
            ( ( (this.getTimeLengthUnitType() == null) &&
               (that.getTimeLengthUnitType() == null)) ||
             (this.getTimeLengthUnitType() != null &&
              this.getTimeLengthUnitType().equals(that.getTimeLengthUnitType()))) &&
             this.getTimeLengthUnitNumber() == that.getTimeLengthUnitNumber() &&
            ( ( (this.getAllowPause() == null) && (that.getAllowPause() == null)) ||
             (this.getAllowPause() != null &&
              this.getAllowPause().equals(that.getAllowPause()))) &&
            ( ( (this.getAllTransition() == null) && (that.getAllTransition() == null)) ||
             (this.getAllTransition() != null &&
              this.getAllTransition().equals(that.getAllTransition()))) && ( ( (this
            .getAllowTransfer() == null) && (that.getAllowTransfer() == null)) ||
            (this.getAllowTransfer() != null &&
             this.getAllowTransfer().equals(that.getAllowTransfer()))) &&
            ( ( (this.getAllowCancel() == null) && (that.getAllowCancel() == null)) ||
             (this.getAllowCancel() != null &&
              this.getAllowCancel().equals(that.getAllowCancel()))) &&
            this.getCreateOpID() == that.getCreateOpID() &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            this.getUpdateOpID() == that.getUpdateOpID() &&
            ( ( (this.getUpdateTime() == null) && (that.getUpdateTime() == null)) ||
             (this.getUpdateTime() != null &&
              this.getUpdateTime().equals(that.getUpdateTime())));
      }
    }
    return false;
  }

     public int hashCode()
         {
                 return toString().hashCode();
         }

         public String toString()
         {
                 StringBuffer buf = new StringBuffer(256);
                 buf.append(ID);
                 buf.append(",").append(name);
                 buf.append(",").append(keepBilling);
                 buf.append(",").append(description);
                 buf.append(",").append(newProductPackage1);
                 buf.append(",").append(newProductPackage2);
                 buf.append(",").append(newProductPackage3);
                 buf.append(",").append(newProductPackage4);
                 buf.append(",").append(newProductPackage5);
                 buf.append(",").append(timeLengthUnitType);
                 buf.append(",").append(timeLengthUnitNumber);
                 buf.append(",").append(allowPause);
                 buf.append(",").append(status);
                 buf.append(",").append(dtCreate);
                 buf.append(",").append(dtLastmod);
                 buf.append(",").append(createTime);
                 buf.append(",").append(allTransition);
                 buf.append(",").append(allowTransfer);
                 buf.append(",").append(allowCancel);
                 buf.append(",").append(createOpID);
                 buf.append(",").append(createTime);
                 buf.append(",").append(updateOpID);
                 buf.append(",").append(updateTime);
                 return buf.toString();
         }

  private java.util.Map map = new java.util.HashMap();


  public void put(String field) { map.put(field, Boolean.TRUE); }

  public java.util.Map getMap() { return this.map; }

}
