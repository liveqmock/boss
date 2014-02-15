/*
 * Created on 2006-11-30
 *
 */
package com.dtv.oss.dto;

import java.sql.Timestamp;

/**
 * @author whq
 *
 * 适用于条件非常多的查询
 */
public class LongCommonQueryConditionDTO extends JobCardDTO {
	private String extraStr1;
	private String extraStr2;
	private String extraStr3;
	private String extraStr4;
	private String extraStr5;
	private Timestamp extraTime1;
	private Timestamp extraTime2;
	
	/**
	 * 
	 */
	public LongCommonQueryConditionDTO() {
	}

	/**
	 * @param spareBeginTime
	 * @param spareEndTime
	 * @param beginStr
	 * @param endStr
	 * @param spareStr1
	 * @param spareStr2
	 * @param spareStr3
	 * @param spareStr4
	 * @param spareStr5
	 * @param spareStr6
	 * @param spareStr7
	 * @param spareStr8
	 * @param spareTime1
	 * @param spareTime2
	 * @param spareTime3
	 * @param spareTime4
	 * @param spareTime5
	 * @param spareTime6
	 */
	public LongCommonQueryConditionDTO(Timestamp spareBeginTime,
			Timestamp spareEndTime, String beginStr, String endStr,
			String spareStr1, String spareStr2, String spareStr3,
			String spareStr4, String spareStr5, String spareStr6,
			String spareStr7, String spareStr8, Timestamp spareTime1,
			Timestamp spareTime2, Timestamp spareTime3, Timestamp spareTime4,
			Timestamp spareTime5, Timestamp spareTime6, String extraStr1, 
			String extraStr2, String extraStr3, String extraStr4, 
			String extraStr5, Timestamp extraTime1, Timestamp extraTime2) {
		
		this.extraStr1 = extraStr1;
		this.extraStr2 = extraStr2;
		this.extraStr3 = extraStr3;
		this.extraStr4 = extraStr4;
		this.extraStr5 = extraStr5;
		this.extraTime1 = extraTime1;
		this.extraTime2 = extraTime2;
	}

	public static void main(String[] args) {
	}
	public String getExtraStr1() {
		return extraStr1;
	}
	public void setExtraStr1(String extraStr1) {
		this.extraStr1 = extraStr1;
	}
	public String getExtraStr2() {
		return extraStr2;
	}
	public void setExtraStr2(String extraStr2) {
		this.extraStr2 = extraStr2;
	}
	public String getExtraStr3() {
		return extraStr3;
	}
	public void setExtraStr3(String extraStr3) {
		this.extraStr3 = extraStr3;
	}
	public String getExtraStr4() {
		return extraStr4;
	}
	public void setExtraStr4(String extraStr4) {
		this.extraStr4 = extraStr4;
	}
	public String getExtraStr5() {
		return extraStr5;
	}
	public void setExtraStr5(String extraStr5) {
		this.extraStr5 = extraStr5;
	}
	public Timestamp getExtraTime1() {
		return extraTime1;
	}
	public void setExtraTime1(Timestamp extraTime1) {
		this.extraTime1 = extraTime1;
	}
	public Timestamp getExtraTime2() {
		return extraTime2;
	}
	public void setExtraTime2(Timestamp extraTime2) {
		this.extraTime2 = extraTime2;
	}
}
