package com.dtv.oss.ajax;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lili on 2014/9/20.
 */
public class AjaxTableResult {
    private int TotalRecordCount =0;
    private String Result ="OK";
    private List Records =new ArrayList();

    public int getTotalRecordCount() {
        return TotalRecordCount;
    }

    public void setTotalRecordCount(int totalRecordCount) {
        TotalRecordCount = totalRecordCount;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public List getRecords() {
        return Records;
    }

    public void setRecords(List records) {
        Records = records;
    }
}
