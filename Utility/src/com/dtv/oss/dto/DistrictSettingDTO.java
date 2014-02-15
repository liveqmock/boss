package com.dtv.oss.dto;

/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/
import java.sql.Timestamp;

public class DistrictSettingDTO implements  ReflectionSupport, java.io.Serializable{
  private int id;
  private String districtCode;
  private String name;
  private String type;
  private int belongTo;
   private String status;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;

  public DistrictSettingDTO(){
  }

  public DistrictSettingDTO(int id, String districtCode, String name, String type, int belongTo, Timestamp dtCreate, Timestamp dtLastmod){
	this.id = id;
	this.districtCode = districtCode;
	this.name = name;
	this.type = type;
	this.belongTo = belongTo;
	this.dtCreate = dtCreate;
	this.dtLastmod = dtLastmod;
  }

  public void setId(int id){
	this.id = id;
  }

  public int getId(){
	return id;
  }

  public void setDistrictCode(String districtCode){
	this.districtCode = districtCode;
	put("districtCode");
  }

  public String getStatus(){
	return status;
  }
  public void setStatus(String status){
         this.status = status;
         put("status");
   }

   public String getDistrictCode(){
         return districtCode;
   }

  public void setName(String name){
	this.name = name;
	put("name");
  }

  public String getName(){
	return name;
  }

  public void setType(String type){
	this.type = type;
	put("type");
  }

  public String getType(){
	return type;
  }

  public void setBelongTo(int belongTo){
	this.belongTo = belongTo;
	put("belongTo");
  }

  public int getBelongTo(){
	return belongTo;
  }

  public void setDtCreate(Timestamp dtCreate){
	this.dtCreate = dtCreate;
  }

  public Timestamp getDtCreate(){
	return dtCreate;
  }

  public void setDtLastmod(Timestamp dtLastmod){
	this.dtLastmod = dtLastmod;
  }

  public Timestamp getDtLastmod(){
	return dtLastmod;
  }

  public boolean equals(Object obj){
	if(obj != null){
	  if(this.getClass().equals(obj.getClass())){
		DistrictSettingDTO that = (DistrictSettingDTO)obj;
		return
			this.getId() == that.getId() &&
			(((this.getDistrictCode() == null) && (that.getDistrictCode() == null)) ||
			 (this.getDistrictCode() != null && this.getDistrictCode().equals(that.getDistrictCode()))) &&
                        (((this.getStatus() == null) && (that.getStatus() == null)) ||
			 (this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
			(((this.getName() == null) && (that.getName() == null)) ||
			 (this.getName() != null && this.getName().equals(that.getName()))) &&
			(((this.getType() == null) && (that.getType() == null)) ||
			 (this.getType() != null && this.getType().equals(that.getType()))) &&
			this.getBelongTo() == that.getBelongTo() &&
			(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
			 (this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
			(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
			 (this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())));
	  }
	}
	return false;
  }

  public int hashCode(){
	return toString().hashCode();
  }

  public String toString(){
	StringBuffer buf = new StringBuffer(256);
	buf.append(id);
	buf.append(",").append(districtCode);
	buf.append(",").append(name);
	buf.append(",").append(type);
        buf.append(",").append(status);
	buf.append(",").append(belongTo);
	buf.append(",").append(dtCreate);
	buf.append(",").append(dtLastmod);
	return buf.toString();
  }

  private java.util.Map map = new java.util.HashMap();

  public void put(String field) { map.put(field, Boolean.TRUE); }

  public java.util.Map getMap() { return this.map; }

}
