package com.dtv.oss.web.util;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import com.dtv.oss.dto.OperatorDTO;

public class LogonWebCurrentOperator extends WebCurrentOperatorData{
  public LogonWebCurrentOperator(OperatorDTO operator) {
    currentOperator = operator;
  }

  public int GetCurrentOperatorID()
  {
    OperatorDTO dtoOper = (OperatorDTO)currentOperator;
    if (dtoOper==null) return 0;
    return dtoOper.getOperatorID();
  }

}