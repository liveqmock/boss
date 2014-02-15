package com.dtv.oss.domain;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.dtv.oss.dto.FaPiaoDTO;
import com.dtv.oss.util.EntityBeanAutoUpdate;

public abstract class FaPiaoBean implements EntityBean {
	EntityContext entityContext;
	
	public Integer ejbCreate(Integer seqNo) throws CreateException {

        //setSeqNo(seqNo);
        return null;
    }

    public Integer ejbCreate(FaPiaoDTO dto) throws CreateException {
    	
    	setType(dto.getType());
        setModuleName(dto.getModuleName());
        setSerailNo(dto.getSerailNo());
        setSysSerialNo(dto.getSysSerialNo());
        setVolumnSeqno(dto.getVolumnSeqno());

        setCreatorOpID(dto.getCreatorOpID());
        setCreatorOrgID(dto.getCreatorOrgID());
        setRecipientOpID(dto.getRecipientOpID());
        setRecipientOrgID(dto.getRecipientOrgID());
        setDrawOpID(dto.getDrawOpID());

        setDrawOrgID(dto.getDrawOrgID());
        setTitle(dto.getTitle());
        setAccountID(dto.getAccountID());
        setSumAmount(dto.getSumAmount());
        setIsAmtFixed(dto.getIsAmtFixed());

        setAuthBy(dto.getAuthBy());
        setAddressType(dto.getAddressType());
        setAddressID(dto.getAddressID());
        setStatus(dto.getStatus());
        setStatusTime(dto.getStatusTime());

        setDescription(dto.getDescription());
        setCreateTime(dto.getCreateTime());
        setDiscardReason(dto.getDiscardReason());
        setPrintTimes(dto.getPrintTimes());
        setLastPrintDate(dto.getLastPrintDate());

        setPrintCode(dto.getPrintCode());
        setMachineCode(dto.getMachineCode());
        setPrintNumber(dto.getPrintNumber());
        setTaxControlCode(dto.getTaxControlCode());
        setMajorTaxAgentCode(dto.getMajorTaxAgentCode());

        setIsZongBaoRen(dto.getIsZongBaoRen());
        setIsFenBaoRen(dto.getIsFenBaoRen());
        setPayerName(dto.getPayerName());
        setPayerType(dto.getPayerType());
        setPayerID(dto.getPayerID());

        setPayeeName(dto.getPayeeName());
        setPayeeType(dto.getPayeeType());
        setPayeeID(dto.getPayeeID());
        setDtCreate(dto.getDtCreate());
        setDtLastmod(dto.getDtLastmod());
        
        setRecipientTime(dto.getRecipientTime());
        setDrawTime(dto.getDrawTime());
        setIsPatchPrint(dto.getIsPatchPrint());
        setSourceType(dto.getSourceType());
        setSourceID(dto.getSourceID());
        setInvoiceCycleID(dto.getInvoiceCycleID());
        
        return null;
    }

    public void ejbPostCreate(Integer seqNo) throws CreateException {
    }

    public void ejbPostCreate(FaPiaoDTO dto) throws CreateException {
    }

    public void ejbRemove() throws RemoveException {
    }

    public abstract void setSeqNo(Integer seqNo);

    public abstract Integer getSeqNo();

    public abstract void setType(String type);

    public abstract String getType();

    public abstract void setModuleName(String moduleName);

    public abstract String getModuleName();

    public abstract void setSerailNo(String serailNo);

    public abstract String getSerailNo();

    public abstract void setSysSerialNo(String sysSerialNo);

    public abstract String getSysSerialNo();

    public abstract void setVolumnSeqno(int volumnSeqno);

    public abstract int getVolumnSeqno();

    public abstract void setCreatorOpID(int creatorOpID);

    public abstract int getCreatorOpID();

    public abstract void setCreatorOrgID(int creatorOrgID);

    public abstract int getCreatorOrgID();

    public abstract void setRecipientOpID(int recipientOpID);

    public abstract int getRecipientOpID();

    public abstract void setRecipientOrgID(int recipientOrgID);

    public abstract int getRecipientOrgID();

    public abstract void setDrawOpID(int drawOpID);

    public abstract int getDrawOpID();

    public abstract void setDrawOrgID(int drawOrgID);

    public abstract int getDrawOrgID();

    public abstract void setTitle(String title);

    public abstract String getTitle();

    public abstract void setAccountID(int accountID);

    public abstract int getAccountID();

    public abstract void setSumAmount(int sumAmount);

    public abstract int getSumAmount();

    public abstract void setIsAmtFixed(String isAmtFixed);

    public abstract String getIsAmtFixed();

    public abstract void setAuthBy(String authBy);

    public abstract String getAuthBy();

    public abstract void setAddressType(String addressType);

    public abstract String getAddressType();

    public abstract void setAddressID(int addressID);

    public abstract int getAddressID();

    public abstract void setStatus(String status);

    public abstract String getStatus();

    public abstract void setStatusTime(Timestamp statusTime);

    public abstract Timestamp getStatusTime();

    public abstract void setDescription(String description);

    public abstract String getDescription();

    public abstract void setCreateTime(Timestamp createTime);

    public abstract Timestamp getCreateTime();

    public abstract void setDiscardReason(String discardReason);

    public abstract String getDiscardReason();

    public abstract void setPrintTimes(int printTimes);

    public abstract int getPrintTimes();

    public abstract void setLastPrintDate(Timestamp lastPrintDate);

    public abstract Timestamp getLastPrintDate();

    public abstract void setPrintCode(String printCode);

    public abstract String getPrintCode();

    public abstract void setMachineCode(String machineCode);

    public abstract String getMachineCode();

    public abstract void setPrintNumber(String printNumber);

    public abstract String getPrintNumber();

    public abstract void setTaxControlCode(String taxControlCode);

    public abstract String getTaxControlCode();

    public abstract void setMajorTaxAgentCode(String majorTaxAgentCode);

    public abstract String getMajorTaxAgentCode();

    public abstract void setIsZongBaoRen(String isZongBaoRen);

    public abstract String getIsZongBaoRen();

    public abstract void setIsFenBaoRen(String isFenBaoRen);

    public abstract String getIsFenBaoRen();

    public abstract void setPayerName(String payerName);

    public abstract String getPayerName();

    public abstract void setPayerType(String payerType);

    public abstract String getPayerType();

    public abstract void setPayerID(String payerID);

    public abstract String getPayerID();

    public abstract void setPayeeName(String payeeName);

    public abstract String getPayeeName();

    public abstract void setPayeeType(String payeeType);

    public abstract String getPayeeType();

    public abstract void setPayeeID(String payeeID);

    public abstract String getPayeeID();

    public abstract void setDtCreate(Timestamp dtCreate);

    public abstract Timestamp getDtCreate();

    public abstract void setDtLastmod(Timestamp dtLastmod);

    public abstract Timestamp getDtLastmod();
    
    public abstract void setRecipientTime(Timestamp recipientTime);
    public abstract Timestamp getRecipientTime();
    
    public abstract void setDrawTime(Timestamp drawTime);
    public abstract Timestamp getDrawTime();
    
    public abstract void setIsPatchPrint(String isPatchPrint);
    public abstract String getIsPatchPrint();

    public abstract int getInvoiceCycleID();
	public abstract void setInvoiceCycleID(int invoiceCycleID);

	public abstract int getSourceID();
	public abstract void setSourceID(int sourceID);

	public abstract String getSourceType();
	public abstract void setSourceType(String sourceType);
	
    public void ejbLoad() {
    }

    public void ejbStore() {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }
    
    public void unsetEntityContext() {
		this.entityContext = null;
	}

	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	public int ejbUpdate(FaPiaoDTO dto) {
		/** @todo Complete this method */
		if (dto.getDtLastmod() == null) {
			return -1;
		}

		if (!dto.getDtLastmod().equals(getDtLastmod())) {

			return -1;
		} else {
			try {
				EntityBeanAutoUpdate.update(dto, this);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			setDtLastmod(new java.sql.Timestamp(System.currentTimeMillis()));
			return 0;
		}
	}

}
