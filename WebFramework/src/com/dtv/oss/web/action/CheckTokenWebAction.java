package com.dtv.oss.web.action;

public abstract class CheckTokenWebAction extends GeneralWebAction{

    protected boolean needCheckToken()
    {
        return true;
    }



}