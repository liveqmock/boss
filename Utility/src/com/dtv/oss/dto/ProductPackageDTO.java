package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class ProductPackageDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private Timestamp dateFrom;
	private Timestamp dateTo;
	private String status;
	private int packageID;
	private String packageName;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
	private int packagePriority;
	private String packageClass;
	private int minSelProductNum;
	private int maxSelProductNum;
	private String hasOptionProductFlag;
	private int grade;
    private String captureFlag;
    private String csiTypeList;
    private String custTypeList;
	

	public ProductPackageDTO()
	{
	}

	/**
	 * @return Returns the captureFlag.
	 */
	public String getCaptureFlag() {
		return captureFlag;
	}
	/**
	 * @param captureFlag The captureFlag to set.
	 */
	public void setCaptureFlag(String captureFlag) {
		this.captureFlag = captureFlag;
		put("captureFlag");
	}
	/**
	 * @return Returns the csiTypeList.
	 */
	public String getCsiTypeList() {
		return csiTypeList;
	}
	/**
	 * @param csiTypeList The csiTypeList to set.
	 */
	public void setCsiTypeList(String csiTypeList) {
		this.csiTypeList = csiTypeList;
		put("csiTypeList");
	}
	/**
	 * @return Returns the packageClassify.
	 */
	public String getPackageClass() {
		
		return packageClass;
	}
	/**
	 * @param packageClassify The packageClassify to set.
	 */
	public void setPackageClass(String packageClass) {
		this.packageClass = packageClass;
		put("packageClass");
	}
	/**
	 * @return Returns the packagePriority.
	 */
	public int getPackagePriority() {
		return packagePriority;
	}
	/**
	 * @param packagePriority The packagePriority to set.
	 */
	public void setPackagePriority(int packagePriority) {
		this.packagePriority = packagePriority;
		put("packagePriority");
	}
	public void setDescription(String description)
	{
		this.description = description;
		put("description");
	}

	public String getDescription()
	{
		return description;
	}

	public void setDateFrom(Timestamp  dateFrom)
	{
		this.dateFrom = dateFrom;
		put("dateFrom");
	}

	public Timestamp getDateFrom()
	{
		return dateFrom;
	}

	public void setDateTo(Timestamp  dateTo)
	{
		this.dateTo = dateTo;
		put("dateTo");
	}

	public Timestamp getDateTo()
	{
		return dateTo;
	}

	public void setStatus(String status)
	{
		this.status = status;
		put("status");
	}

	public String getStatus()
	{
		return status;
	}

	public void setPackageID(int packageID)
	{
		this.packageID = packageID;
		//put("packageID");
	}

	public int getPackageID()
	{
		return packageID;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
		put("packageName");
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setDtCreate(Timestamp dtCreate)
	{
		this.dtCreate = dtCreate;
	}

	public Timestamp getDtCreate()
	{
		return dtCreate;
	}

	public void setDtLastmod(Timestamp dtLastmod)
	{
		this.dtLastmod = dtLastmod;
	}

	public Timestamp getDtLastmod()
	{
		return dtLastmod;
	}



	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				ProductPackageDTO that = (ProductPackageDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getDateFrom() == null) && (that.getDateFrom() == null)) ||
						(this.getDateFrom() != null && this.getDateFrom().equals(that.getDateFrom()))) &&
					(((this.getDateTo() == null) && (that.getDateTo() == null)) ||
						(this.getDateTo() != null && this.getDateTo().equals(that.getDateTo()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getPackageID() == that.getPackageID()  &&
					this.getPackagePriority() == that.getPackagePriority()  &&
					this.getMinSelProductNum() == that.getMinSelProductNum()  &&
					this.getMaxSelProductNum() == that.getMaxSelProductNum()  &&
					(((this.getHasOptionProductFlag() == null) && (that.getHasOptionProductFlag() == null)) ||
							(this.getHasOptionProductFlag() != null && this.getHasOptionProductFlag().equals(that.getHasOptionProductFlag()))) &&
					(((this.getPackageName() == null) && (that.getPackageName() == null)) ||
						(this.getPackageName() != null && this.getPackageName().equals(that.getPackageName()))) &&
				  (((this.getPackageClass() == null) && (that.getPackageClass() == null)) ||
					(this.getPackageClass() != null && this.getPackageClass().equals(that.getPackageClass()))) &&	
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())));
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
		buf.append(description);
		buf.append(",").append(dateFrom);
		buf.append(",").append(dateTo);
		buf.append(",").append(status);
		buf.append(",").append(packageID);
		buf.append(",").append(packageName);
		buf.append(",").append(packagePriority);
		buf.append(",").append(packageClass);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		buf.append(",").append(minSelProductNum);
		buf.append(",").append(maxSelProductNum);
		buf.append(",").append(hasOptionProductFlag);
		buf.append(",").append(captureFlag);
		buf.append(",").append(csiTypeList);
		buf.append(",").append("custTypeList");
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

	/**
	 * @return Returns the hasOptionProductFlag.
	 */
	public String getHasOptionProductFlag() {
		return hasOptionProductFlag;
	}
	/**
	 * @param hasOptionProductFlag The hasOptionProductFlag to set.
	 */
	public void setHasOptionProductFlag(String hasOptionProductFlag) {
		this.hasOptionProductFlag = hasOptionProductFlag;
		put("hasOptionProductFlag");
	}
	/**
	 * @return Returns the maxSelProdtuctNum.
	 */
	public int getMaxSelProductNum() {
		return maxSelProductNum;
	}
	/**
	 * @param maxSelProdtuctNum The maxSelProdtuctNum to set.
	 */
	public void setMaxSelProductNum(int maxSelProductNum) {
		this.maxSelProductNum = maxSelProductNum;
		put("maxSelProductNum");
	}
	/**
	 * @return Returns the minSelProductNum.
	 */
	public int getMinSelProductNum() {
		return minSelProductNum;
	}
	/**
	 * @param minSelProductNum The minSelProductNum to set.
	 */
	public void setMinSelProductNum(int minSelProductNum) {
		this.minSelProductNum = minSelProductNum;
		put("minSelProductNum");
	}

	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}



	/**
	 * @param grade the grade to set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
		put("grade");
	}

	public String getCustTypeList() {
		return custTypeList;
	}

	public void setCustTypeList(String custTypeList) {
		this.custTypeList = custTypeList;
		put("custTypeList");
	}
	
	
}

