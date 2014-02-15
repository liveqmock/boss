package com.dtv.oss.dto;

import java.sql.Timestamp;

public class BillBoardDTO implements ReflectionSupport, java.io.Serializable {
    private int seqNo;
    private String name;
    private String description;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private String status;
    private String publishPerson;
    private Timestamp publishDate;
    private String publishReason;
    private String grade;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
	/**
	 * @return Returns the grade.
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade The grade to set.
	 */
	public void setGrade(String grade) {
		this.grade = grade;
		 put("grade");
	}
	/**
	 * @return Returns the publishDate.
	 */
	public Timestamp getPublishDate() {
		return publishDate;
	}
	/**
	 * @param publishDate The publishDate to set.
	 */
	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
		 put("publishDate");
	}
	/**
	 * @return Returns the publishPerson.
	 */
	public String getPublishPerson() {
		return publishPerson;
	}
	/**
	 * @param publishPerson The publishPerson to set.
	 */
	public void setPublishPerson(String publishPerson) {
		this.publishPerson = publishPerson;
		 put("publishPerson");
	}
	/**
	 * @return Returns the publishReason.
	 */
	public String getPublishReason() {
		return publishReason;
	}
	/**
	 * @param publishReason The publishReason to set.
	 */
	public void setPublishReason(String publishReason) {
		this.publishReason = publishReason;
		 put("publishReason");
	}
   
    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        put("name");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        put("description");
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
        put("dateFrom");
    }

    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        this.dateTo = dateTo;
        put("dateTo");
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

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BillBoardDTO))
            return false;
        BillBoardDTO that = (BillBoardDTO) obj;
        if  ( that.seqNo != this.seqNo  )
              
            return false;
        if (!(that.name == null ? this.name == null :
              that.name.equals(this.name)))
            return false;
        if (!(that.description == null ? this.description == null :
              that.description.equals(this.description)))
            return false;
        if (!(that.dateFrom == null ? this.dateFrom == null :
              that.dateFrom.equals(this.dateFrom)))
            return false;
        if (!(that.dateTo == null ? this.dateTo == null :
              that.dateTo.equals(this.dateTo)))
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.seqNo;
        result = 37 * result + this.name.hashCode();
        result = 37 * result + this.description.hashCode();
        result = 37 * result + this.dateFrom.hashCode();
        result = 37 * result + this.getPublishPerson().hashCode();
        result = 37 * result + this.getGrade().hashCode();
        result = 37 * result + this.getPublishDate().hashCode();
        result = 37 * result + this.getPublishReason().hashCode();
        result = 37 * result + this.dateTo.hashCode();
        result = 37 * result + this.status.hashCode();
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(256);
        returnStringBuffer.append("[");
        returnStringBuffer.append("seqNo:").append(seqNo);
        returnStringBuffer.append("name:").append(name);
        returnStringBuffer.append("description:").append(description);
        returnStringBuffer.append("dateFrom:").append(dateFrom);
        returnStringBuffer.append("dateTo:").append(dateTo);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("publishperson:").append(publishPerson);
        returnStringBuffer.append("publishReason:").append(publishReason);
        returnStringBuffer.append("publishDate:").append(publishDate);
        returnStringBuffer.append("grade:").append(grade);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }
    private java.util.Map map = new java.util.HashMap();

    public void put(String field) {
      map.put(field, Boolean.TRUE);
    }

    public java.util.Map getMap() {
      return this.map;
    }

}
