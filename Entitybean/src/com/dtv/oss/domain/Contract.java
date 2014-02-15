package com.dtv.oss.domain;


import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.ContractDTO;

public interface Contract extends EJBLocalObject {
    public void setName(String name);

    public String getName();

    public void setDescription(String description);

    public String getDescription();

    public void setDatefrom(Timestamp datefrom);

    public Timestamp getDatefrom();

    public void setNormaldate(Timestamp normaldate);

    public Timestamp getNormaldate();

    public void setDateto(Timestamp dateto);

    public Timestamp getDateto();

    public void setRentFeeTotal(double rentFeeTotal);

    public double getRentFeeTotal();

    public void setOneOffFeeTotal(double oneOffFeeTotal);

    public double getOneOffFeeTotal();

    public void setPrepayAmount(double prepayAmount);

    public double getPrepayAmount();

    public void setPrepayMopID(int prepayMopID);

    public int getPrepayMopID();

    public void setUsedDate(Timestamp usedDate);

    public Timestamp getUsedDate();

    public void setUsedCustomerID(int usedCustomerID);

    public int getUsedCustomerID();

    public void setSheetNo(String sheetNo);

    public String getSheetNo();

    public void setUserCount(int userCount);

    public int getUserCount();

    public void setUsedCount(int usedCount);

    public int getUsedCount();

    public void setSourceOrg(int sourceOrg);

    public int getSourceOrg();

    

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    


    public String getContractNo();
    public int ejbUpdate(ContractDTO dto);
}
