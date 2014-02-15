package com.dtv.oss.dto; 
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class PaymentInterfaceDTO implements ReflectionSupport, java.io.Serializable
{
	private int id;
	private String name;
	private String description;
	private int mopId;
	private String status;
	private String libraryName;
	private String outputDeductionFileFn;
	private String inputPaymentFileFn;
	private String outputCheckingAcctFileFn;
	private String inputAcctCheckResultFn;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;


	public PaymentInterfaceDTO()
	{
	}

	public PaymentInterfaceDTO(int id, String name, String description, int mopId, String status, String libraryName, String outputDeductionFileFn, String inputPaymentFileFn, String outputCheckingAcctFileFn, String inputAcctCheckResultFn, Timestamp dtCreate, Timestamp dtLastmod)
	{
		setId(id);
		setName(name);
		setDescription(description);
		setMopId(mopId);
		setStatus(status);
		setLibraryName(libraryName);
		setOutputDeductionFileFn(outputDeductionFileFn);
		setInputPaymentFileFn(inputPaymentFileFn);
		setOutputCheckingAcctFileFn(outputCheckingAcctFileFn);
		setInputAcctCheckResultFn(inputAcctCheckResultFn);
		setDtCreate(dtCreate);
		setDtLastmod(dtLastmod);
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

	public void setName(String name)
	{
		this.name = name;
		put("name");
	}

	public String getName()
	{
		return name;
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

	public void setMopId(int mopId)
	{
		this.mopId = mopId;
		put("mopId");
	}

	public int getMopId()
	{
		return mopId;
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

	public void setLibraryName(String libraryName)
	{
		this.libraryName = libraryName;
		put("libraryName");
	}

	public String getLibraryName()
	{
		return libraryName;
	}

	public void setOutputDeductionFileFn(String outputDeductionFileFn)
	{
		this.outputDeductionFileFn = outputDeductionFileFn;
		put("outputDeductionFileFn");
	}

	public String getOutputDeductionFileFn()
	{
		return outputDeductionFileFn;
	}

	public void setInputPaymentFileFn(String inputPaymentFileFn)
	{
		this.inputPaymentFileFn = inputPaymentFileFn;
		put("inputPaymentFileFn");
	}

	public String getInputPaymentFileFn()
	{
		return inputPaymentFileFn;
	}

	public void setOutputCheckingAcctFileFn(String outputCheckingAcctFileFn)
	{
		this.outputCheckingAcctFileFn = outputCheckingAcctFileFn;
		put("outputCheckingAcctFileFn");
	}

	public String getOutputCheckingAcctFileFn()
	{
		return outputCheckingAcctFileFn;
	}

	public void setInputAcctCheckResultFn(String inputAcctCheckResultFn)
	{
		this.inputAcctCheckResultFn = inputAcctCheckResultFn;
		put("inputAcctCheckResultFn");
	}

	public String getInputAcctCheckResultFn()
	{
		return inputAcctCheckResultFn;
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
				PaymentInterfaceDTO that = (PaymentInterfaceDTO) obj;
				return 
					this.getId() == that.getId()  && 
					(((this.getName() == null) && (that.getName() == null)) ||
						(this.getName() != null && this.getName().equals(that.getName()))) && 
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) && 
					this.getMopId() == that.getMopId()  && 
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) && 
					(((this.getLibraryName() == null) && (that.getLibraryName() == null)) ||
						(this.getLibraryName() != null && this.getLibraryName().equals(that.getLibraryName()))) && 
					(((this.getOutputDeductionFileFn() == null) && (that.getOutputDeductionFileFn() == null)) ||
						(this.getOutputDeductionFileFn() != null && this.getOutputDeductionFileFn().equals(that.getOutputDeductionFileFn()))) && 
					(((this.getInputPaymentFileFn() == null) && (that.getInputPaymentFileFn() == null)) ||
						(this.getInputPaymentFileFn() != null && this.getInputPaymentFileFn().equals(that.getInputPaymentFileFn()))) && 
					(((this.getOutputCheckingAcctFileFn() == null) && (that.getOutputCheckingAcctFileFn() == null)) ||
						(this.getOutputCheckingAcctFileFn() != null && this.getOutputCheckingAcctFileFn().equals(that.getOutputCheckingAcctFileFn()))) && 
					(((this.getInputAcctCheckResultFn() == null) && (that.getInputAcctCheckResultFn() == null)) ||
						(this.getInputAcctCheckResultFn() != null && this.getInputAcctCheckResultFn().equals(that.getInputAcctCheckResultFn()))) && 
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
		buf.append(id);
		buf.append(",").append(name);
		buf.append(",").append(description);
		buf.append(",").append(mopId);
		buf.append(",").append(status);
		buf.append(",").append(libraryName);
		buf.append(",").append(outputDeductionFileFn);
		buf.append(",").append(inputPaymentFileFn);
		buf.append(",").append(outputCheckingAcctFileFn);
		buf.append(",").append(inputAcctCheckResultFn);
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

