package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class CsiProcessLogDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private int csiID;
	private String action;
	private String description;
	private Timestamp actionTime;
	private int operatorID;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
    private int invoiceNo;
	private int orgID;
    private String workResult;
    private String workResultReason;

	public CsiProcessLogDTO()
	{
	}

	public CsiProcessLogDTO(int id, int csiID, String action, String description, Timestamp actionTime, int operatorID, Timestamp dtCreate, Timestamp dtLastmod,int invoiceNo,int orgID)
	{
		setId(id);
		setCsiID(csiID);
		setAction(action);
		setDescription(description);
		setActionTime(actionTime);
		setOperatorID(operatorID);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
              setOrgID(orgID);
              setInvoiceNo(invoiceNo);

	}
        public void setOrgID(int orgID)
               {
                       this.orgID = orgID;
                       put("orgID");
               }

               public int getOrgID()
               {
                       return orgID;
               }

        public void setInvoiceNo(int invoiceNo)
                {
                        this.invoiceNo = invoiceNo;
                        put("invoiceNo");
                }

                public int getInvoiceNo()
                {
                        return invoiceNo;
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

	public void setCsiID(int csiID)
	{
		this.csiID = csiID;
		put("csiID");
	}

	public int getCsiID()
	{
		return csiID;
	}

	public void setAction(String action)
	{
		this.action = action;
		put("action");
	}

	public String getAction()
	{
		return action;
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

	public void setActionTime(Timestamp actionTime)
	{
		this.actionTime = actionTime;
		put("actionTime");
	}

	public Timestamp getActionTime()
	{
		return actionTime;
	}

	public void setOperatorID(int operatorID)
	{
		this.operatorID = operatorID;
		put("operatorID");
	}

	public int getOperatorID()
	{
		return operatorID;
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

        public void setWorkResult(String workResult)
        {
                 this.workResult = workResult;
                 put("workResult");
          }

         public String getWorkResult()
         {
                return workResult;
          }

          public void setWorkResultReason(String workResultReason)
                 {
                          this.workResultReason = workResultReason;
                          put("workResultReason");
                   }

          public String getWorkResultReason()
        {
             return workResultReason;
          }

	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				CsiProcessLogDTO that = (CsiProcessLogDTO) obj;
				return
					this.getId() == that.getId()  &&
                                        this.getInvoiceNo() == that.getInvoiceNo()  &&
                                        this.getOrgID()== that.getOrgID()  &&
					this.getCsiID() == that.getCsiID()  &&
					(((this.getAction() == null) && (that.getAction() == null)) ||
						(this.getAction() != null && this.getAction().equals(that.getAction()))) &&
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getActionTime() == null) && (that.getActionTime() == null)) ||
						(this.getActionTime() != null && this.getActionTime().equals(that.getActionTime()))) &&
					this.getOperatorID() == that.getOperatorID()  &&
					(((this.getDtCreate() == null) && (that.getDtCreate() == null)) ||
						(this.getDtCreate() != null && this.getDtCreate().equals(that.getDtCreate()))) &&
					(((this.getDtLastmod() == null) && (that.getDtLastmod() == null)) ||
						(this.getDtLastmod() != null && this.getDtLastmod().equals(that.getDtLastmod())))&&
                                           (((this.getWorkResult() == null) && (that.getWorkResult() == null)) ||
                                                (this.getWorkResult() != null && this.getWorkResult().equals(that.getWorkResult()))) &&
                                        (((this.getWorkResultReason() == null) && (that.getWorkResultReason() == null)) ||
                                                (this.getWorkResultReason() != null && this.getWorkResultReason().equals(that.getWorkResultReason())));

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
		buf.append(",").append(csiID);
		buf.append(",").append(action);
		buf.append(",").append(description);
		buf.append(",").append(actionTime);
		buf.append(",").append(operatorID);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
                buf.append(",").append(invoiceNo);
                buf.append(",").append(dtLastmod);
                buf.append(",").append(workResult);
		buf.append(",").append(workResultReason);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

