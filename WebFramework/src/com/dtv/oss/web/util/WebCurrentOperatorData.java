package com.dtv.oss.web.util;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

public abstract class WebCurrentOperatorData implements java.io.Serializable{
  protected Object currentOperator = null;
  public WebCurrentOperatorData() {
  }

  public WebCurrentOperatorData(Object operator) {
    currentOperator = operator;
  }

  public abstract int GetCurrentOperatorID();

  public void SetCurrentOperator(Object operator) {
    currentOperator = operator;
  }

  public Object getCurrentOperator()
  {
    return currentOperator;
  }
}