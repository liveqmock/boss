package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.VoiceUserPhonenoDTO;

public interface VoiceUserPhonenoHome extends EJBLocalHome {
    public VoiceUserPhoneno create(Integer seqno) throws CreateException;

    public VoiceUserPhoneno create(VoiceUserPhonenoDTO dto) throws CreateException;

    public VoiceUserPhoneno findByPrimaryKey(Integer seqno) throws
            FinderException;
}
