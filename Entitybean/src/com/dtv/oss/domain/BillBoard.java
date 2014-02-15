package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.BillBoardDTO;

public interface BillBoard extends EJBLocalObject {
    public void setName(String name);

    public String getName();

    public void setDescription(String description);

    public String getDescription();

    public void setDateFrom(Timestamp dateFrom);

    public Timestamp getDateFrom();

    public void setDateTo(Timestamp dateTo);

    public Timestamp getDateTo();

    

    public void setStatus(String status);

    public String getStatus();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();


    public  void setGrade(String grade);

    public  String getGrade();

    public  void setPublishPerson(String publishPerson);

    public  String getPublishPerson();

    public  void setPublishDate(Timestamp publishDate);

    public  Timestamp getPublishDate();

    public  void setPublishReason(String publishReason);

    public  String getPublishReason();



    public Integer getSeqNo();
    public int ejbUpdate(BillBoardDTO dto);
}
