package com.dtv.oss.domain;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;

public interface VoiceFriendPhoneNoHome extends EJBLocalHome {
    public VoiceFriendPhoneNo create(Integer seqNo) throws CreateException;

    public VoiceFriendPhoneNo  create(VoiceFriendPhoneNoDTO dto) throws CreateException;

    public VoiceFriendPhoneNo findByPrimaryKey(Integer seqNo) throws
            FinderException;
    public Collection findByServiceAccountIDAndPhoneNO(int serviceAccountID, java.lang.String phoneNO)throws
    FinderException;
}
