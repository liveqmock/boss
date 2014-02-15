package com.dtv.oss.dto;

 
import java.sql.Timestamp;

public class OtherPaymentListDTO implements ReflectionSupport, java.io.Serializable {
  private int id;
  private double value;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int referId;
  private int mopID;
  private String paymentType;
  private Timestamp createDate;
  private int createOperatorID;
  private int createOrgID;
  private int createDepartmentID;
  private String comments;
  
  private String subPaymentType;
  public OtherPaymentListDTO(){
}
  public OtherPaymentListDTO(int createOperatorID,String comments,int createDepartmentID,int createOrgID,Timestamp createDate,int mopID, int id,double value,Timestamp dtCreate,Timestamp dtLastmod ,int referId,String paymentType,String subPaymentType){
    setId(id);
    setValue(value);
    setMopID(mopID);
    setDtCreate(dtCreate);
    setDtLastmod(dtLastmod);
    setReferId(referId);
    setPaymentType(paymentType);
    setSubPaymentType( subPaymentType);
    setCreateDepartmentID(createDepartmentID);
	setCreateOrgID(createOrgID);
	setCreateOperatorID(createOperatorID);
	setCreateDate(createDate);
	setComments(comments);
}
  public void setCreateDepartmentID(int createDepartmentID)
	{
		this.createDepartmentID = createDepartmentID;
		put("createDepartmentID");
	}

	public int getCreateDepartmentID()
	{
		return createDepartmentID;
	}
	public void setCreateOrgID(int createOrgID)
	{
		this.createOrgID = createOrgID;
		put("createOrgID");
	}

	public int getCreateOrgID()
	{
		return createOrgID;
	}
	public void setCreateOperatorID(int createOperatorID)
	{
		this.createOperatorID = createOperatorID;
		put("createOperatorID");
	}

	public int getCreateOperatorID()
	{
		return createOperatorID;
	}
	public String getComments()
	{
		return comments;
	}
	
	public void setComments(String comments)
	{
		this.comments = comments;
		put("comments");
	}
	public void setCreateDate(Timestamp createDate)
	{
		this.createDate = createDate;
	}

	public Timestamp getCreateDate()
	{
		return createDate;
	}
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMopID(int mopID)
	{
		this.mopID = mopID;
		//put("mopID");
	}

	public int getMopID()
	{
		return mopID;
	}
	
  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
      put("value");
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

  public int getReferId() {
    return referId;
  }

  public void setReferId(int referId) {
    this.referId = referId;
     put("referId");
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
    put("paymentType");
  }

  public String getSubPaymentType() {
    return subPaymentType;
  }

  public void setSubPaymentType(String subPaymentType) {
    this.subPaymentType = subPaymentType;
    put("subPaymentType");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        OtherPaymentListDTO that = (OtherPaymentListDTO) obj;
        return
        this.getCreateDepartmentID() == that.getCreateDepartmentID()  &&
	    this.getCreateOperatorID() == that.getCreateOperatorID()  &&
	    this.getCreateOrgID() == that.getCreateOrgID()  &&
            this.getId() == that.getId() &&
            this.getValue() == that.getValue() &&
            (((this.getComments() == null) && (that.getComments() == null)) ||
				(this.getComments() != null && this.getComments().equals(that.getComments()))) &&	
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getReferId() == that.getReferId() &&
            ( ( (this.getPaymentType() == null) && (that.getPaymentType() == null)) ||
             (this.getPaymentType() != null &&
             this.getMopID() == that.getMopID()  &&
              this.getPaymentType().equals(that.getPaymentType()))) &&
              (((this.getCreateDate() == null) && (that.getCreateDate() == null)) ||
			(this.getCreateDate() != null && this.getCreateDate().equals(that.getCreateDate()))) &&		
            ( ( (this.getSubPaymentType() == null) && (that.getSubPaymentType() == null)) ||
             (this.getSubPaymentType() != null &&
              this.getSubPaymentType().equals(that.getSubPaymentType())));
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
                buf.append(id);
                buf.append(",").append(createDate);
        		buf.append(",").append(createOperatorID);
        		buf.append(",").append(comments);
        		buf.append(",").append(createOrgID);
        		buf.append(",").append(createDepartmentID);
                buf.append(",").append(value);
                buf.append(",").append(dtCreate);
                buf.append(",").append(dtLastmod);
                buf.append(",").append(paymentType);
                buf.append(",").append(referId);
                buf.append(",").append(mopID);
                buf.append(",").append(subPaymentType);



                return buf.toString();
        }

        private java.util.Map map = new java.util.HashMap();

        public void put(String field) { map.put(field, Boolean.TRUE); }

        public java.util.Map getMap() { return this.map; }

}

