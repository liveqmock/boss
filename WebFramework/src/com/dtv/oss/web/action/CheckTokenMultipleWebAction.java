package com.dtv.oss.web.action;

public abstract class CheckTokenMultipleWebAction extends MultipleWebAction{

  protected boolean needCheckToken()
  {
      return true;
  }

}