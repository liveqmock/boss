package com.dtv.oss.dto;

import java.sql.Timestamp;

public class ProductDependencyDTO
    implements  ReflectionSupport, java.io.Serializable {
 
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int seqNo;
  private int productId;
  private String referProductIDList;
  private String referAllFlag;

  private int referProductNum;
  private String type;
  

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

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
     put("productId");
  }

  /**
   * @return Returns the referAllFlag.
   */
  public String getReferAllFlag() {
  	return referAllFlag;
  }
  /**
   * @param referAllFlag The referAllFlag to set.
   */
  public void setReferAllFlag(String referAllFlag) {
  	this.referAllFlag = referAllFlag;
  	  put("referAllFlag");
  }
  /**
   * @return Returns the referProductIDList.
   */
  public String getReferProductIDList() {
  	return referProductIDList;
  }
  /**
   * @param referProductIDList The referProductIDList to set.
   */
  public void setReferProductIDList(String referProductIDList) {
  	this.referProductIDList = referProductIDList;
  	 put("referProductIDList");
  }
  /**
   * @return Returns the referProductNum.
   */
  public int getReferProductNum() {
  	return referProductNum;
  }
  /**
   * @param referProductNum The referProductNum to set.
   */
  public void setReferProductNum(int referProductNum) {
  	this.referProductNum = referProductNum;
  	 put("referProductNum");
  } 
   
  /**
   * @return Returns the type.
   */
  public String getType() {
  	return type;
  }
  /**
   * @param type The type to set.
   */
  public void setType(String type) {
  	this.type = type;
  	 put("type");
  } 

   

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        ProductDependencyDTO that = (ProductDependencyDTO) obj;
        return ( ( (this.getReferAllFlag() == null) && (that.getReferAllFlag() == null)) ||
                (this.getReferAllFlag() != null && this.getReferAllFlag().equals(that.getReferAllFlag()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
              ( ( (this.getType() == null) && (that.getType() == null)) ||
                    (this.getType() != null &&
                     this.getType().equals(that.getType()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
              this.getReferProductNum() == that.getReferProductNum()  &&
             this.getSeqNo() == that.getSeqNo()  &&
            this.getProductId() == that.getProductId() &&
            ( ( (this.getReferProductIDList() == null) && (that.getReferProductIDList() == null)) ||
                    (this.getReferProductIDList() != null &&
                     this.getReferProductIDList().equals(that.getReferProductIDList())))  
           ;
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
                  buf.append(referProductIDList);
                  buf.append(",").append(productId);
                  buf.append(",").append(type);
                  buf.append(",").append(referProductIDList);
                  buf.append(",").append(referAllFlag);
                  buf.append(",").append(referProductNum);
                  buf.append(",").append(dtCreate);
                  buf.append(",").append(dtLastmod);


                  return buf.toString();
          }

          private java.util.Map map = new java.util.HashMap();

          public void put(String field) { map.put(field, Boolean.TRUE); }

          public java.util.Map getMap() { return this.map; }


 }

