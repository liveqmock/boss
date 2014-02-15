package com.dtv.oss.domain;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.CatvJobCardConfigDTO;

public interface CatvJobCardConfigHome extends EJBLocalHome {
    public CatvJobCardConfig create(String csiType, String jobCardType,
                                    String jobCardSubType) throws
            CreateException;

    public CatvJobCardConfig create(CatvJobCardConfigDTO dto) throws CreateException;

    public CatvJobCardConfig findByPrimaryKey(CatvJobCardConfigPK pk) throws
            FinderException;
}
