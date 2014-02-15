package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.VODInterfaceProductDTO;

public interface VodProduct extends EJBLocalObject {
    public void setNewsaflag(String newsaflag);

    public String getNewsaflag();

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();

    public void setAcctItemTypeID(String acctItemTypeID);

    public String getAcctItemTypeID();


    


    public Integer getSmsProductID();

    public void setVodProductIDlist(String vodProductIDlist);

    public String getVodProductIDlist();

    public void setVodServiceIDlist(String vodServiceIDlist);

    public String getVodServiceIDlist();

    public void setBillingCodeList(String billingCodeList);

    public String getBillingCodeList();
    public int ejbUpdate(VODInterfaceProductDTO dto);
}
