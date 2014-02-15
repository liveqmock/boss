package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CAWalletDTO;

public interface CAWalletHome extends EJBLocalHome {
    public CAWallet create(Integer walletID) throws CreateException;

    public CAWallet create(CAWalletDTO dto) throws CreateException;

    public CAWallet findByPrimaryKey(Integer walletId) throws
            FinderException;
}
