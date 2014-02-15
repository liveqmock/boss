/*
 * Created on 2005-12-7
 *
 * @author whq
 * 
 * 按事件进行计费的接口
 */
package com.dtv.oss.service.util.ImmediateFee;

import java.sql.SQLException;
import java.util.Collection;
import com.dtv.oss.service.ServiceException;

import com.dtv.oss.dto.*;

public interface ImmediateFeeCalculator4Event {
    /*
     * old interface, deprecated
     */
//    public abstract ImmediateFeeList GetImmediateFee(int eventClass,
//            String eventReason, String custType, String acctType,
//            Collection productInfoList) throws SQLException;
//    public abstract ImmediateFeeList getImmediateFee(int eventClass, 
//				String eventReason, CustomerDTO customerDto, Collection accountList,
//				Collection serviceAccountList, Collection productInfoList) throws SQLException;
    public abstract AccountItemDTO getImmediateFee4CP(int eventClass, 
			 String eventReason,
			 CustomerDTO customerDto) throws SQLException;
    public abstract ImmediateFeeList getImmediateFee(int eventClass, 
				String eventReason, Collection billingObjectCol) throws SQLException;

    public abstract ImmediateFeeList getCampaignFee(Collection billingObjectCol) throws SQLException;

	public abstract void calcuGroupBargain(CustServiceInteractionDTO csiDto, Collection billingObjectCol,ImmediateFeeList immediateFeeList) throws SQLException,ServiceException;
			 
}