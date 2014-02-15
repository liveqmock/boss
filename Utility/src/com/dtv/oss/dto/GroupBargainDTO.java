package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class GroupBargainDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int agentId;
	private String isCampaign;
	private String bargainNo;
	private String description;
	private Timestamp dataFrom;
	private Timestamp createTime;
	private Timestamp normalTimeTo;
	private Timestamp dateTo;
	private int totalSheets;
	private int usedSheets;
	private int abortSheets;
	private double prepayImmediateFee;
	private double prepayDepositFee;
	private String status;
	private int mopId;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
    private int campaignId;
    private int orgId;

	public GroupBargainDTO()
	{
	}
	 
	/**
	 * @return Returns the prepayDepositFee.
	 */
	public double getPrepayDepositFee() {
		return prepayDepositFee;
	}
	/**
	 * @param prepayDepositFee The prepayDepositFee to set.
	 */
	public void setPrepayDepositFee(double prepayDepositFee) {
		this.prepayDepositFee = prepayDepositFee;
		 put("prepayDepositFee");
	}
	/**
	 * @return Returns the prepayImmediateFee.
	 */
	public double getPrepayImmediateFee() {
		return prepayImmediateFee;
	}
	/**
	 * @param prepayImmediateFee The prepayImmediateFee to set.
	 */
	public void setPrepayImmediateFee(double prepayImmediateFee) {
		this.prepayImmediateFee = prepayImmediateFee;
		 put("prepayImmediateFee");
	}
	


	 
	public void setAgentId(int agentId) {
		this.agentId = agentId;
		put("agentId");

	}
	public int getAgentId()
	{
		return agentId;
	}
	
	public void setMopId(int mopId) {
		this.mopId = mopId;
		put("mopId");

	}
	public int getMopId()
	{
		return mopId;
	}
	public void setId(int id)
	{
		this.id = id;
		//put("id");
	}

	public int getId()
	{
		return id;
	}

	public void setBargainNo(String bargainNo)
	{
		this.bargainNo = bargainNo;
		put("bargainNo");
	}

	public String getBargainNo()
	{
		return bargainNo;
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
	public void setIsCampaign(String isCampaign)
	{
		this.isCampaign = isCampaign;
		put("isCampaign");
	}

	public String getIsCampaign()
	{
		return isCampaign;
	}

	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
		put("createTime");
	}

	public Timestamp getCreateTime()
	{
		return createTime;
	}

	public void setDataFrom(Timestamp dataFrom)
	{
		this.dataFrom = dataFrom;
		put("dataFrom");
	}

	public Timestamp getDataFrom()
	{
		return dataFrom;
	}

	public void setNormalTimeTo(Timestamp normalTimeTo)
	{
		this.normalTimeTo = normalTimeTo;
		put("normalTimeTo");
	}

	public Timestamp getNormalTimeTo()
	{
		return normalTimeTo;
	}

	public void setDateTo(Timestamp dateTo)
	{
		this.dateTo = dateTo;
		put("dateTo");
	}

	public Timestamp getDateTo()
	{
		return dateTo;
	}

	public void setTotalSheets(int totalSheets)
	{
		this.totalSheets = totalSheets;
		put("totalSheets");
	}

	public int getTotalSheets()
	{
		return totalSheets;
	}

	public void setUsedSheets(int usedSheets)
	{
		this.usedSheets = usedSheets;
		put("usedSheets");
	}

	public int getUsedSheets()
	{
		return usedSheets;
	}

	public void setAbortSheets(int abortSheets)
	{
		this.abortSheets = abortSheets;
		put("abortSheets");
	}

	public int getAbortSheets()
	{
		return abortSheets;
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

   
   

  public int getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(int campaignId) {
    this.campaignId = campaignId;
     put("campaignId");
  }

  public int getOrgId() {
    return orgId;
  }

  public void setOrgId(int orgId) {
    this.orgId = orgId;
      put("orgId");
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        com.dtv.oss.dto.GroupBargainDTO that = (com.dtv.oss.dto.
            GroupBargainDTO) obj;
        return this.getId() ==  that.getId() &&
            ( ( (this.getDescription() == null) && (that.getDescription() == null)) ||
             (this.getDescription() != null &&
              this.getDescription().equals(that.getDescription()))) &&
            
            ( ( (this.getStatus() == null) && (that.getStatus() == null)) ||
             (this.getStatus() != null &&
              this.getStatus().equals(that.getStatus()))) &&
            ( ( (this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
             (this.getDtCreate() != null &&
              this.getDtCreate().equals(that.getDtCreate()))) &&
            ( ( (this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
             (this.getDtLastmod() != null &&
              this.getDtLastmod().equals(that.getDtLastmod()))) &&
            this.getCampaignId() == that.getCampaignId() &&
            ( ( (this.getBargainNo() == null) && (that.getBargainNo
                                                  () == null)) ||
             (this.getBargainNo() != null &&
              this.getBargainNo().equals(that.getBargainNo()))) &&
            ( ( (this.getCreateTime() == null) && (that.getCreateTime() == null)) ||
             (this.getCreateTime() != null &&
              this.getCreateTime().equals(that.getCreateTime()))) &&
            ( ( (this.getDataFrom() == null) && (that.getDataFrom() == null)) ||
             (this.getDataFrom() != null &&
              this.getDataFrom().equals(that.getDataFrom()))) &&
            ( ( (this.getNormalTimeTo() == null) && (that.getNormalTimeTo() == null)) ||
             (this.getNormalTimeTo() != null &&
              this.getNormalTimeTo().equals(that.getNormalTimeTo()))) &&
            ( ( (this.getDateTo() == null) && (that.getDateTo() == null)) ||
             (this.getDateTo() != null &&
              this.getDateTo().equals(that.getDateTo()))) &&
            this.getTotalSheets() == that.getTotalSheets() &&
            this.getUsedSheets() == that.getUsedSheets() &&
            this.getPrepayDepositFee() == that.getPrepayDepositFee() &&
            this.getPrepayImmediateFee() == that.getPrepayImmediateFee() &&
            this.getOrgId() == that.getOrgId() &&
            this.getMopId() == that.getMopId() &&
            this.getAgentId() == that.getAgentId() &&
            ( ( (this.getIsCampaign() == null) && (that.getIsCampaign() == null))
             ||
             (this.getIsCampaign() != null &&
              this.getIsCampaign().equals(that.getIsCampaign())));
      }
    }
    return false;
  }

  public String toString()
  {
       StringBuffer buf = new StringBuffer(256);
         buf.append(status);
         buf.append(",").append(description);
         buf.append(",").append(totalSheets);
         buf.append(",").append(createTime);
         buf.append(",").append(prepayDepositFee);
         buf.append(",").append(prepayImmediateFee);
         buf.append(",").append(orgId);
         buf.append(",").append(agentId);
         buf.append(",").append(usedSheets);
         buf.append(",").append(dateTo);
         buf.append(",").append(normalTimeTo);
         buf.append(",").append(dataFrom);
         buf.append(",").append(mopId);
         buf.append(",").append(dtCreate);
         buf.append(",").append(dtLastmod);
         buf.append(",").append(bargainNo);
         buf.append(",").append(campaignId);
         buf.append(",").append(isCampaign);
        
         return buf.toString();
      }
  private java.util.Map map = new java.util.HashMap();


  public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }


}

