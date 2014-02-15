package com.dtv.oss.dto.custom;
import java.sql.Timestamp;

/* DTO created by liudi, script coded by liudi */

/**@todo Complete package & import here*/

/**
 * BatchNoDto用来取得批号返回参数
 * 其中
 */
public class BatchNoDTO implements java.io.Serializable
{
	private int batch_no;
	private int batch_campaign;

	public BatchNoDTO()
	{
		batch_no = 0;
		batch_campaign = 0;
	}

	public BatchNoDTO(int batch)
	{
		
		this.batch_no= batch;
		this.batch_campaign = batch;
	}

	public int getBatchNo()
	{
		return batch_no;
	}
	public void setBatchNo(int batch)
	{
		this.batch_no = batch;
	}
	
	public int getBatchNo2()
	{
		return batch_campaign;
	}
	public void setBatchNo2(int batch)
	{
		this.batch_campaign = batch;
	}
	
	public boolean equals(Object obj)
	{
		if (obj != null)
		{
			if (this.getClass().equals(obj.getClass()))
			{
				BatchNoDTO that = (BatchNoDTO) obj;
				return this.getBatchNo()==that.getBatchNo();
					
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
		return  "BatchNo = " + batch_no;
	}
}