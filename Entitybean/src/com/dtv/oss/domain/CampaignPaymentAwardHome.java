package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CampaignPaymentAwardDTO;

public interface CampaignPaymentAwardHome extends EJBLocalHome {
    public CampaignPaymentAward create(Integer seqNo) throws
            CreateException;

    public CampaignPaymentAward create(CampaignPaymentAwardDTO dto) throws CreateException;

    public CampaignPaymentAward findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
