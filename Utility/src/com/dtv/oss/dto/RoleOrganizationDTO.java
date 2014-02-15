package com.dtv.oss.dto;

import java.sql.Timestamp;

public class RoleOrganizationDTO
    implements ReflectionSupport, java.io.Serializable {
  private int id;
  private Timestamp dtCreate;
  private Timestamp dtLastmod;
  private int districtId;
  private int serviceOrgId;
  private String orgRole;
   
  private String isFirst;
  private String troubleType;
  private String troubleSubType;
  private int saProductId;
  private String diagnosisResult;
  private String orgSubRole;
  
  
  
  public String getOrgSubRole() {
	return orgSubRole;
}

public void setOrgSubRole(String orgSubRole) {
	this.orgSubRole = orgSubRole;
	 put("orgSubRole");
}

public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;

  }
  public String getDiagnosisResult() {
		return diagnosisResult;
	}

	public void setDiagnosisResult(String diagnosisResult) {
		this.diagnosisResult = diagnosisResult;
		 put("diagnosisResult");
	}
  public int getDistrictId() {
	return districtId;
}

public void setDistrictId(int districtId) {
	this.districtId = districtId;
	 put("districtId");
}

public int getSaProductId() {
	return saProductId;
}

public void setSaProductId(int saProductId) {
	this.saProductId = saProductId;
	 put("saProductId");
}

public String getTroubleSubType() {
	return troubleSubType;
}

public void setTroubleSubType(String troubleSubType) {
	this.troubleSubType = troubleSubType;
	put("troubleSubType");
}

public String getTroubleType() {
	return troubleType;
}

public void setTroubleType(String troubleType) {
	this.troubleType = troubleType;
	put("troubleType");
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

  
  public int getServiceOrgId() {
    return serviceOrgId;
  }

  public void setServiceOrgId(int serviceOrgId) {
    this.serviceOrgId = serviceOrgId;
     put("serviceOrgId");
  }

  public String getOrgRole() {
    return orgRole;
  }

  public void setOrgRole(String orgRole) {
    this.orgRole = orgRole;
    put("orgRole");
  }

  public String getIsFirst() {
    return isFirst;
  }

  public void setIsFirst(String isFirst) {
    this.isFirst = isFirst;
      put("isFirst");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        RoleOrganizationDTO that = (RoleOrganizationDTO) obj;
        return  this.getId() ==  that.getId() &&
            this.getDistrictId() ==  that.getDistrictId() &&
        ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
           
            this.getSaProductId() == that.getSaProductId() &&
            this.getServiceOrgId() == that.getServiceOrgId() &&
            ( ( (this.getOrgRole() == null) && (that.getOrgRole() == null)) ||
             (this.getOrgRole() != null &&
              this.getOrgRole().equals(that.getOrgRole()))) &&
              ( ( (this.getOrgSubRole() == null) && (that.getOrgSubRole() == null)) ||
                      (this.getOrgSubRole() != null &&
                       this.getOrgSubRole().equals(that.getOrgSubRole()))) &&
              ( ( (this.getTroubleSubType() == null) && (that.getTroubleSubType() == null)) ||
                      (this.getTroubleSubType() != null &&
                       this.getTroubleSubType().equals(that.getTroubleSubType()))) &&
                       ( ( (this.getTroubleType() == null) && (that.getTroubleType() == null)) ||
                               (this.getTroubleType() != null &&
                                this.getTroubleType().equals(that.getTroubleType()))) &&         
            ( ( (this.getIsFirst() == null) && (that.getIsFirst() == null)) ||
             (this.getIsFirst() != null &&
              this.getIsFirst().equals(that.getIsFirst())));
      }
    }
    return false;
  }

  public int hashCode() {
    return
        toString().hashCode();
  }



  public String toString() {
       StringBuffer buf = new StringBuffer(256);
       buf.append(id);
       buf.append(",").append(districtId);
       buf.append(",").append(orgSubRole);
       buf.append(",").append(serviceOrgId);
       buf.append(",").append(orgRole);
       buf.append(",").append(isFirst);
       buf.append(",").append(troubleType);
       buf.append(",").append(troubleSubType);
       buf.append(",").append(saProductId);
       buf.append(",").append(diagnosisResult);
       buf.append(",").append(dtCreate);
       buf.append(",").append(dtLastmod);

       return buf.toString();
     }

     private java.util.Map map = new java.util.HashMap();

     public void put(String field) {
       map.put(field, Boolean.TRUE);
     }

     public java.util.Map getMap() {
       return this.map;
     }

   }


