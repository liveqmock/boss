package com.dtv.oss.dto;
import java.sql.Timestamp;
/* DTO created by awk, script coded by YJ */

/**@todo Complete package & import here*/

public class ProductDTO implements ReflectionSupport, java.io.Serializable
{
	private String description;
	private Timestamp dateFrom;
	private Timestamp dateTo;
	private String status;
	private int productID;
	private String productName;
	 
	private String productClass;
	private Timestamp dtCreate;
	private Timestamp dtLastmod;
    private String newsaFlag;

    
    
	public ProductDTO()
	{
	}
      

	 
        public void setNewsaFlag(String newsaFlag)
               {
                       this.newsaFlag = newsaFlag;
                        put("newsaFlag");
               }

               public String getNewsaFlag()
               {
                       return newsaFlag;
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

	public void setProductID(int productID)
	{
		this.productID = productID;
		//put("productID");
	}

	public int getProductID()
	{
		return productID;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
		put("productName");
	}

	public String getProductName()
	{
		return productName;
	}

	 

	public void setProductClass(String productClass)
	{
		this.productClass = productClass;
		put("productClass");
	}

	public String getProductClass()
	{
		return productClass;
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
				ProductDTO that = (ProductDTO) obj;
				return
					(((this.getDescription() == null) && (that.getDescription() == null)) ||
						(this.getDescription() != null && this.getDescription().equals(that.getDescription()))) &&
					(((this.getDateFrom() == null) && (that.getDateFrom() == null)) ||
						(this.getDateFrom() != null && this.getDateFrom().equals(that.getDateFrom()))) &&
					(((this.getDateTo() == null) && (that.getDateTo() == null)) ||
						(this.getDateTo() != null && this.getDateTo().equals(that.getDateTo()))) &&
					(((this.getStatus() == null) && (that.getStatus() == null)) ||
						(this.getStatus() != null && this.getStatus().equals(that.getStatus()))) &&
					this.getProductID() == that.getProductID()  &&
				 
					(((this.getProductName() == null) && (that.getProductName() == null)) ||
						(this.getProductName() != null && this.getProductName().equals(that.getProductName()))) &&
					 
					(((this.getProductClass() == null) && (that.getProductClass() == null)) ||
						(this.getProductClass() != null && this.getProductClass().equals(that.getProductClass()))) &&
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
		buf.append(",").append(productID);
		buf.append(",").append(productName);
		 
		buf.append(",").append(productClass);
		 
		buf.append(",").append(dtCreate);
		buf.append(",").append(dtLastmod);
        buf.append(",").append(newsaFlag);

		return buf.toString();
	}

	private java.util.Map map = new java.util.HashMap();

	public void put(String field) { map.put(field, Boolean.TRUE); }

	public java.util.Map getMap() { return this.map; }

}

