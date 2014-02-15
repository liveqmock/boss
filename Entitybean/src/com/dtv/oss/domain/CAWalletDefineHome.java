package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.dtv.oss.dto.CAWalletDefineDTO;

public interface CAWalletDefineHome extends EJBLocalHome {
    public CAWalletDefine create(String caWalletCode) throws CreateException;

    public CAWalletDefine create(CAWalletDefineDTO dto) throws CreateException;

    public CAWalletDefine findByPrimaryKey(String caWalletCode) throws
            FinderException;
}
