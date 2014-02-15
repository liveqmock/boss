package com.dtv.oss.dto.custom;
import java.sql.Timestamp;
import java.util.Map;

/* DTO created by awk, script coded by YJ */

/**
 * CommonQueryConditionDTO 通用的查询条件dto， 它继承了父类QueryConditionDTO的全部功能， 并增加了其它可能用到的字段，一般的查询都应尽量使用该dto。
 * 如果该dto满足不了要求，可以直接继承其父类QueryConditionDTO生成相应的类。
 */

public class CommonQueryConditionDTO extends QueryConditionDTO {
	private Timestamp spareBeginTime;
	private Timestamp spareEndTime;
	private String beginStr;
	private String endStr;
	private String spareStr1;
	private String spareStr2;
	private String spareStr3;
	private String spareStr4;
	private String spareStr5;
	private String spareStr6;
	private String spareStr7;
	private String spareStr8;
	private String spareStr9;
	private String spareStr10;
	private String spareStr11;
	private String spareStr12;
	private String spareStr13;
	private String spareStr14;
	private String spareStr15;
	private String spareStr16;
	private String spareStr17;
	private String spareStr18;
	private String spareStr19;
	private String spareStr20;
	private String spareStr21;
	private String spareStr22;
	private String spareStr23;
	private String spareStr24;
	private String spareStr25;
	private String spareStr26;
	private String spareStr27;
	private String spareStr28;
	private String spareStr29;
	private String spareStr30;
	private String spareStr31;
	private String spareStr32;
	private String spareStr33;
	private Timestamp spareTime1;
	private Timestamp spareTime2;
	private Timestamp spareTime3;
	private Timestamp spareTime4;
	private Timestamp spareTime5;
	private Timestamp spareTime6;
	private boolean spareBoolean;
	private Map  reportMap;
	
	public boolean isSpareBoolean() {
		return spareBoolean;
	}

	public void setSpareBoolean(boolean spareBoolean) {
		this.spareBoolean = spareBoolean;
	}

	public CommonQueryConditionDTO() {
	}

	public CommonQueryConditionDTO(
		Timestamp spareBeginTime,
		Timestamp spareEndTime,
		String beginStr,
		String endStr,
		String spareStr1,
		String spareStr2,
		String spareStr3,
		String spareStr4,
		String spareStr5,
		String spareStr6,
		String spareStr7,
		String spareStr8,
		 
		Timestamp spareTime1,
		Timestamp spareTime2,
		Timestamp spareTime3,
		Timestamp spareTime4,
		Timestamp spareTime5,
		Timestamp spareTime6) {
		this.spareBeginTime = spareBeginTime;
		this.spareEndTime = spareEndTime;
		this.beginStr = beginStr;
		this.endStr = endStr;
		this.spareStr1 = spareStr1;
		this.spareStr2 = spareStr2;
		this.spareStr3 = spareStr3;
		this.spareStr4 = spareStr4;
		this.spareStr5 = spareStr5;
		this.spareStr6 = spareStr6;
		this.spareStr7 = spareStr7;
		this.spareStr8 = spareStr8;
		 
		this.spareTime1 = spareTime1;
		this.spareTime2 = spareTime2;
		this.spareTime3 = spareTime3;
		this.spareTime4 = spareTime4;
		this.spareTime5 = spareTime5;
		this.spareTime6 = spareTime6;
	}

	public void setSpareBeginTime(Timestamp spareBeginTime) {
		this.spareBeginTime = spareBeginTime;
	}

	public Timestamp getSpareBeginTime() {
		return spareBeginTime;
	}

	public void setSpareEndTime(Timestamp spareEndTime) {
		this.spareEndTime = spareEndTime;
	}

	public Timestamp getSpareEndTime() {
		return spareEndTime;
	}

	public void setBeginStr(String beginStr) {
		this.beginStr = beginStr;
	}

	public String getBeginStr() {
		return beginStr;
	}

	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

	public String getEndStr() {
		return endStr;
	}

	public void setSpareStr1(String spareStr1) {
		this.spareStr1 = spareStr1;
	}

	public String getSpareStr1() {
		return spareStr1;
	}

	public void setSpareStr2(String spareStr2) {
		this.spareStr2 = spareStr2;
	}

	public String getSpareStr2() {
		return spareStr2;
	}

	public void setSpareStr3(String spareStr3) {
		this.spareStr3 = spareStr3;
	}

	public String getSpareStr3() {
		return spareStr3;
	}

	public void setSpareStr4(String spareStr4) {
		this.spareStr4 = spareStr4;
	}

	public String getSpareStr4() {
		return spareStr4;
	}

	public void setSpareStr5(String spareStr5) {
		this.spareStr5 = spareStr5;
	}

	public String getSpareStr5() {
		return spareStr5;
	}

	public void setSpareStr6(String spareStr6) {
		this.spareStr6 = spareStr6;
	}

	public String getSpareStr6() {
		return spareStr6;
	}

	public void setSpareStr7(String spareStr7) {
		this.spareStr7 = spareStr7;
	}

	public String getSpareStr7() {
		return spareStr7;
	}

	public void setSpareStr8(String spareStr8) {
		this.spareStr8 = spareStr8;
	}

	public String getSpareStr8() {
		return spareStr8;
	}

	public void setSpareTime1(Timestamp spareTime1) {
		this.spareTime1 = spareTime1;
	}

	public Timestamp getSpareTime1() {
		return spareTime1;
	}

	public void setSpareTime2(Timestamp spareTime2) {
		this.spareTime2 = spareTime2;
	}

	public Timestamp getSpareTime2() {
		return spareTime2;
	}

	public void setSpareTime3(Timestamp spareTime3) {
		this.spareTime3 = spareTime3;
	}

	public Timestamp getSpareTime3() {
		return spareTime3;
	}

	public void setSpareTime4(Timestamp spareTime4) {
		this.spareTime4 = spareTime4;
	}

	public Timestamp getSpareTime4() {
		return spareTime4;
	}

	public void setSpareTime5(Timestamp spareTime5) {
		this.spareTime5 = spareTime5;
	}

	public Timestamp getSpareTime5() {
		return spareTime5;
	}

	public void setSpareTime6(Timestamp spareTime6) {
		this.spareTime6 = spareTime6;
	}

	public Timestamp getSpareTime6() {
		return spareTime6;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (this.getClass().equals(obj.getClass())) {
				CommonQueryConditionDTO that = (CommonQueryConditionDTO) obj;
				return (((this.getSpareBeginTime() == null) && (that.getSpareBeginTime() == null)) || (this.getSpareBeginTime() != null && this.getSpareBeginTime().equals(that.getSpareBeginTime())))
					&& (((this.getSpareEndTime() == null) && (that.getSpareEndTime() == null)) || (this.getSpareEndTime() != null && this.getSpareEndTime().equals(that.getSpareEndTime())))
					&& (((this.getBeginStr() == null) && (that.getBeginStr() == null)) || (this.getBeginStr() != null && this.getBeginStr().equals(that.getBeginStr())))
					&& (((this.getEndStr() == null) && (that.getEndStr() == null)) || (this.getEndStr() != null && this.getEndStr().equals(that.getEndStr())))
					&& (((this.getSpareStr1() == null) && (that.getSpareStr1() == null)) || (this.getSpareStr1() != null && this.getSpareStr1().equals(that.getSpareStr1())))
					&& (((this.getSpareStr2() == null) && (that.getSpareStr2() == null)) || (this.getSpareStr2() != null && this.getSpareStr2().equals(that.getSpareStr2())))
					&& (((this.getSpareStr3() == null) && (that.getSpareStr3() == null)) || (this.getSpareStr3() != null && this.getSpareStr3().equals(that.getSpareStr3())))
					&& (((this.getSpareStr4() == null) && (that.getSpareStr4() == null)) || (this.getSpareStr4() != null && this.getSpareStr4().equals(that.getSpareStr4())))
					&& (((this.getSpareStr5() == null) && (that.getSpareStr5() == null)) || (this.getSpareStr5() != null && this.getSpareStr5().equals(that.getSpareStr5())))
					&& (((this.getSpareStr6() == null) && (that.getSpareStr6() == null)) || (this.getSpareStr6() != null && this.getSpareStr6().equals(that.getSpareStr6())))
					&& (((this.getSpareStr7() == null) && (that.getSpareStr7() == null)) || (this.getSpareStr7() != null && this.getSpareStr7().equals(that.getSpareStr7())))
					&& (((this.getSpareStr8() == null) && (that.getSpareStr8() == null)) || (this.getSpareStr8() != null && this.getSpareStr8().equals(that.getSpareStr8())))
					&& (((this.getSpareStr9() == null) && (that.getSpareStr9() == null)) || (this.getSpareStr9() != null && this.getSpareStr9().equals(that.getSpareStr9())))
					&& (((this.getSpareStr10() == null) && (that.getSpareStr10() == null)) || (this.getSpareStr10() != null && this.getSpareStr10().equals(that.getSpareStr10())))
					&& (((this.getSpareStr11() == null) && (that.getSpareStr11() == null)) || (this.getSpareStr11() != null && this.getSpareStr11().equals(that.getSpareStr11())))
					&& (((this.getSpareStr12() == null) && (that.getSpareStr12() == null)) || (this.getSpareStr12() != null && this.getSpareStr12().equals(that.getSpareStr12())))
                    && (((this.getSpareStr13() == null) && (that.getSpareStr13() == null)) || (this.getSpareStr13() != null && this.getSpareStr13().equals(that.getSpareStr13())))
                    && (((this.getSpareStr14() == null) && (that.getSpareStr14() == null)) || (this.getSpareStr14() != null && this.getSpareStr14().equals(that.getSpareStr14())))
                    && (((this.getSpareStr15() == null) && (that.getSpareStr15() == null)) || (this.getSpareStr15() != null && this.getSpareStr15().equals(that.getSpareStr15())))
                    && (((this.getSpareStr16() == null) && (that.getSpareStr16() == null)) || (this.getSpareStr16() != null && this.getSpareStr16().equals(that.getSpareStr16())))
					&& (((this.getSpareTime1() == null) && (that.getSpareTime1() == null)) || (this.getSpareTime1() != null && this.getSpareTime1().equals(that.getSpareTime1())))
					&& (((this.getSpareTime2() == null) && (that.getSpareTime2() == null)) || (this.getSpareTime2() != null && this.getSpareTime2().equals(that.getSpareTime2())))
					&& (((this.getSpareTime3() == null) && (that.getSpareTime3() == null)) || (this.getSpareTime3() != null && this.getSpareTime3().equals(that.getSpareTime3())))
					&& (((this.getSpareTime4() == null) && (that.getSpareTime4() == null)) || (this.getSpareTime4() != null && this.getSpareTime4().equals(that.getSpareTime4())))
					&& (((this.getSpareTime5() == null) && (that.getSpareTime5() == null)) || (this.getSpareTime5() != null && this.getSpareTime5().equals(that.getSpareTime5())))
					&& (((this.getSpareTime6() == null) && (that.getSpareTime6() == null)) || (this.getSpareTime6() != null && this.getSpareTime6().equals(that.getSpareTime6())));
			}
		}
		return false;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(256);
		buf.append(spareBeginTime);
		buf.append(",").append(spareEndTime);
		buf.append(",").append(beginStr);
		buf.append(",").append(endStr);
		buf.append(",").append(spareStr1);
		buf.append(",").append(spareStr2);
		buf.append(",").append(spareStr3);
		buf.append(",").append(spareStr4);
		buf.append(",").append(spareStr5);
		buf.append(",").append(spareStr6);
		buf.append(",").append(spareStr7);
		buf.append(",").append(spareStr8);
		buf.append(",").append(spareStr9);
		buf.append(",").append(spareStr10);
		buf.append(",").append(spareStr11);
		buf.append(",").append(spareStr12);
		buf.append(",").append(spareStr13);
		buf.append(",").append(spareStr14);
		buf.append(",").append(spareStr15);
		buf.append(",").append(spareStr16);
		buf.append(",").append(spareStr17);
		buf.append(",").append(spareStr18);
		buf.append(",").append(spareStr19);
		buf.append(",").append(spareStr20);
		buf.append(",").append(spareTime1);
		buf.append(",").append(spareTime2);
		buf.append(",").append(spareTime3);
		buf.append(",").append(spareTime4);
		buf.append(",").append(spareTime5);
		buf.append(",").append(spareTime6);
		return buf.toString();
	}

	public String toXml() {
		StringBuffer buf = new StringBuffer(256);
		buf.append("<sparebegintime>").append(spareBeginTime).append("</sparebegintime>");
		buf.append("<spareendtime>").append(spareEndTime).append("</spareendtime>");
		buf.append("<beginstr>").append(beginStr).append("</beginstr>");
		buf.append("<endstr>").append(endStr).append("</endstr>");
		buf.append("<sparestr1>").append(spareStr1).append("</sparestr1>");
		buf.append("<sparestr2>").append(spareStr2).append("</sparestr2>");
		buf.append("<sparestr3>").append(spareStr3).append("</sparestr3>");
		buf.append("<sparestr4>").append(spareStr4).append("</sparestr4>");
		buf.append("<sparestr5>").append(spareStr5).append("</sparestr5>");
		buf.append("<sparestr6>").append(spareStr6).append("</sparestr6>");
		buf.append("<sparestr7>").append(spareStr7).append("</sparestr7>");
		buf.append("<sparestr8>").append(spareStr8).append("</sparestr8>");
		buf.append("<sparestr9>").append(spareStr9).append("</sparestr9>");
		buf.append("<sparestr10>").append(spareStr10).append("</sparestr10>");
		buf.append("<sparestr11>").append(spareStr11).append("</sparestr11>");
		buf.append("<sparestr12>").append(spareStr12).append("</sparestr13>");
		buf.append("<sparestr13>").append(spareStr12).append("</sparestr12>");
		buf.append("<sparetime1>").append(spareTime1).append("</sparetime1>");
		buf.append("<sparetime2>").append(spareTime2).append("</sparetime2>");
		buf.append("<sparetime3>").append(spareTime3).append("</sparetime3>");
		buf.append("<sparetime4>").append(spareTime4).append("</sparetime4>");
		buf.append("<sparetime5>").append(spareTime5).append("</sparetime5>");
		buf.append("<sparetime6>").append(spareTime6).append("</sparetime6>");
		return buf.toString();
	}

	/**
	 * @return Returns the spareStr10.
	 */
	public String getSpareStr10() {
		return spareStr10;
	}
	/**
	 * @param spareStr10 The spareStr10 to set.
	 */
	public void setSpareStr10(String spareStr10) {
		this.spareStr10 = spareStr10;
	}
	/**
	 * @return Returns the spareStr11.
	 */
	public String getSpareStr11() {
		return spareStr11;
	}
	/**
	 * @param spareStr11 The spareStr11 to set.
	 */
	public void setSpareStr11(String spareStr11) {
		this.spareStr11 = spareStr11;
	}
	/**
	 * @return Returns the spareStr12.
	 */
	public String getSpareStr12() {
		return spareStr12;
	}
	/**
	 * @param spareStr12 The spareStr12 to set.
	 */
	public void setSpareStr12(String spareStr12) {
		this.spareStr12 = spareStr12;
	}
	/**
	 * @return Returns the spareStr13.
	 */
	public String getSpareStr13() {
		return spareStr13;
	}
	
	/**
	 * @param spareStr13 The spareStr13 to set.
	 */
	public void setSpareStr13(String spareStr13) {
		this.spareStr13 = spareStr13;
	}
	/**
	 * @param spareStr14 The spareStr14 to set.
	 */
	public void setSpareStr14(String spareStr14) {
		this.spareStr14 = spareStr14;
	}
	/**
	 * @return Returns the spareStr14.
	 */
	public String getSpareStr14() {
		return spareStr14;
	}
	/**
	 * @param spareStr15 The spareStr15 to set.
	 */
	public void setSpareStr15(String spareStr15) {
		this.spareStr15 = spareStr15;
	}
	/**
	 * @return Returns the spareStr15.
	 */
	public String getSpareStr15() {
		return spareStr15;
	}
	/**
	 * @param spareStr15 The spareStr15 to set.
	 */
	public void setSpareStr16(String spareStr16) {
		this.spareStr16 = spareStr16;
	}
	/**
	 * @return Returns the spareStr15.
	 */
	public String getSpareStr16() {
		return spareStr16;
	}
	/**
	 * @return Returns the spareStr9.
	 */
	public String getSpareStr9() {
		return spareStr9;
	}
	/**
	 * @param spareStr9 The spareStr9 to set.
	 */
	public void setSpareStr9(String spareStr9) {
		this.spareStr9 = spareStr9;
	}

	public String getSpareStr17() {
		return spareStr17;
	}

	public void setSpareStr17(String spareStr17) {
		this.spareStr17 = spareStr17;
	}

	/**
	 * @return the spareStr18
	 */
	public String getSpareStr18() {
		return spareStr18;
	}

	/**
	 * @param spareStr18 the spareStr18 to set
	 */
	public void setSpareStr18(String spareStr18) {
		this.spareStr18 = spareStr18;
	}

	public String getSpareStr19() {
		return spareStr19;
	}

	public void setSpareStr19(String spareStr19) {
		this.spareStr19 = spareStr19;
	}
	
	public String getSpareStr20() {
		return spareStr20;
	}

	public void setSpareStr20(String spareStr20) {
		this.spareStr20 = spareStr20;
	}
	/**
	 * @return Returns the spareStr11.
	 */
	public String getSpareStr21() {
		return spareStr21;
	}
	/**
	 * @param spareStr11 The spareStr11 to set.
	 */
	public void setSpareStr21(String spareStr21) {
		this.spareStr21 = spareStr21;
	}

	public String getSpareStr22() {
		return spareStr22;
	}

	public void setSpareStr22(String spareStr22) {
		this.spareStr22 = spareStr22;
	}

	public String getSpareStr23() {
		return spareStr23;
	}

	public void setSpareStr23(String spareStr23) {
		this.spareStr23 = spareStr23;
	}

	public String getSpareStr24() {
		return spareStr24;
	}

	public void setSpareStr24(String spareStr24) {
		this.spareStr24 = spareStr24;
	}

	public String getSpareStr25() {
		return spareStr25;
	}

	public void setSpareStr25(String spareStr25) {
		this.spareStr25 = spareStr25;
	}

	public String getSpareStr26() {
		return spareStr26;
	}

	public void setSpareStr26(String spareStr26) {
		this.spareStr26 = spareStr26;
	}

	/**
	 * @return the spareStr27
	 */
	public String getSpareStr27() {
		return spareStr27;
	}

	/**
	 * @param spareStr27 the spareStr27 to set
	 */
	public void setSpareStr27(String spareStr27) {
		this.spareStr27 = spareStr27;
	}

	public String getSpareStr28() {
		return spareStr28;
	}

	public void setSpareStr28(String spareStr28) {
		this.spareStr28 = spareStr28;
	}

	public String getSpareStr29() {
		return spareStr29;
	}

	public void setSpareStr29(String spareStr29) {
		this.spareStr29 = spareStr29;
	}

	public String getSpareStr30() {
		return spareStr30;
	}

	public void setSpareStr30(String spareStr30) {
		this.spareStr30 = spareStr30;
	}

	public String getSpareStr31() {
		return spareStr31;
	}

	public void setSpareStr31(String spareStr31) {
		this.spareStr31 = spareStr31;
	}

	public Map getReportMap() {
		return reportMap;
	}

	public void setReportMap(Map reportMap) {
		this.reportMap = reportMap;
	}

	public String getSpareStr32() {
		return spareStr32;
	}

	public void setSpareStr32(String spareStr32) {
		this.spareStr32 = spareStr32;
	}

	public String getSpareStr33() {
		return spareStr33;
	}

	public void setSpareStr33(String spareStr33) {
		this.spareStr33 = spareStr33;
	}
	
}