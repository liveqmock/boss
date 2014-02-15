package com.dtv.oss.domain;

import java.io.Serializable;

public class CustProblemFeeSettingPK
    implements Serializable {

  public String problemLevel;
  public String problemCategory;
  public int eventClass;

  public CustProblemFeeSettingPK() {
  }

  public CustProblemFeeSettingPK(String problemLevel, String problemCategory,
                                 int eventClass) {
    this.problemLevel = problemLevel;
    this.problemCategory = problemCategory;
    this.eventClass = eventClass;
  }

  public boolean equals(Object obj) {
    if (obj != null) {
      if (this.getClass().equals(obj.getClass())) {
        CustProblemFeeSettingPK that = (CustProblemFeeSettingPK) obj;
        return ( ( (this.problemLevel == null) && (that.problemLevel == null)) ||
                (this.problemLevel != null &&
                 this.problemLevel.equals(that.problemLevel))) &&
            ( ( (this.problemCategory == null) && (that.problemCategory == null)) ||
             (this.problemCategory != null &&
              this.problemCategory.equals(that.problemCategory))) &&
            this.eventClass == that.eventClass;
      }
    }
    return false;
  }

  public int hashCode() {
    return (problemLevel + problemCategory + eventClass).hashCode();
  }
}
