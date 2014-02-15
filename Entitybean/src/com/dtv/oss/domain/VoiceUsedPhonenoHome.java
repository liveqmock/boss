package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.VoiceUsedPhonenoDTO;

public interface VoiceUsedPhonenoHome extends EJBLocalHome {
    public VoiceUsedPhoneno create(Integer seqno) throws CreateException;

    public VoiceUsedPhoneno create(VoiceUsedPhonenoDTO dto) throws CreateException;

    public VoiceUsedPhoneno findByPrimaryKey(Integer seqno) throws
            FinderException;
}
