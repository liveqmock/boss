package com.dtv.oss.domain;

import javax.ejb.EJBLocalObject;

import com.dtv.oss.dto.FaPiaoDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public interface FaPiao extends javax.ejb.EJBLocalObject {
    public void setType(String type);

    public String getType();

    public void setModuleName(String moduleName);

    public String getModuleName();

    public void setSerailNo(String serailNo);

    public String getSerailNo();

    public void setSysSerialNo(String sysSerialNo);

    public String getSysSerialNo();

    public void setVolumnSeqno(int volumnSeqno);

    public int getVolumnSeqno();

    public void setCreatorOpID(int creatorOpID);

    public int getCreatorOpID();

    public void setCreatorOrgID(int creatorOrgID);

    public int getCreatorOrgID();

    public void setRecipientOpID(int recipientOpID);

    public int getRecipientOpID();

    public void setRecipientOrgID(int recipientOrgID);

    public int getRecipientOrgID();

    public void setDrawOpID(int drawOpID);

    public int getDrawOpID();

    public void setDrawOrgID(int drawOrgID);

    public int getDrawOrgID();


    public void setTitle(String title);

    public String getTitle();

    public void setAccountID(int accountID);

    public int getAccountID();

    public void setSumAmount(int sumAmount);

    public int getSumAmount();


    public void setIsAmtFixed(String isAmtFixed);

    public String getIsAmtFixed();

    public void setAuthBy(String authBy);

    public String getAuthBy();

    public void setAddressType(String addressType);

    public String getAddressType();

    public void setAddressID(int addressID);

    public int getAddressID();


    public void setStatus(String status);

    public String getStatus();

    public void setStatusTime(Timestamp statusTime);

    public Timestamp getStatusTime();

    

    public void setDescription(String description);

    public String getDescription();

    public void setCreateTime(Timestamp createTime);

    public Timestamp getCreateTime();

    public void setDiscardReason(String discardReason);

    public String getDiscardReason();

    public void setPrintTimes(int printTimes);

    public int getPrintTimes();

    

    public void setLastPrintDate(Timestamp lastPrintDate);

    public Timestamp getLastPrintDate();

    public void setPrintCode(String printCode);

    public String getPrintCode();

    public void setMachineCode(String machineCode);

    public String getMachineCode();

    public void setPrintNumber(String printNumber);

    public String getPrintNumber();

    public void setTaxControlCode(String taxControlCode);

    public String getTaxControlCode();

    public void setMajorTaxAgentCode(String majorTaxAgentCode);

    public String getMajorTaxAgentCode();

    public void setIsZongBaoRen(String isZongBaoRen);

    public String getIsZongBaoRen();

    public void setIsFenBaoRen(String isFenBaoRen);

    public String getIsFenBaoRen();

    public void setPayerName(String payerName);

    public String getPayerName();

    public void setPayerType(String payerType);

    public String getPayerType();

    public void setPayerID(String payerID);

    public String getPayerID();

    public void setPayeeName(String payeeName);

    public String getPayeeName();

    public void setPayeeType(String payeeType);

    public String getPayeeType();

    public void setPayeeID(String payeeID);

    public String getPayeeID();

    public void setDtCreate(Timestamp dtCreate);

    public Timestamp getDtCreate();

    public void setDtLastmod(Timestamp dtLastmod);

    public Timestamp getDtLastmod();
    
    public void setRecipientTime(Timestamp recipientTime);

    public Timestamp getRecipientTime();
    
    public void setDrawTime(Timestamp drawTime);

    public Timestamp getDrawTime();
    
    public void setIsPatchPrint(String isPatchPrint);

    public String getIsPatchPrint();

    public int getInvoiceCycleID();

	public void setInvoiceCycleID(int invoiceCycleID);

	public int getSourceID();

	public void setSourceID(int sourceID);

	public String getSourceType();

	public void setSourceType(String sourceType);
	
    public Integer getSeqNo();
    
    public int ejbUpdate(FaPiaoDTO dto);
}

