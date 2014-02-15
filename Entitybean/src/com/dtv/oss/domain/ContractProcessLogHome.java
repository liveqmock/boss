package com.dtv.oss.domain;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ContractProcessLogDTO;

public interface ContractProcessLogHome extends EJBLocalHome {
    public ContractProcessLog create(Integer seqNo) throws CreateException;

    public ContractProcessLog create(ContractProcessLogDTO dto) throws CreateException;

    public ContractProcessLog findByPrimaryKey(Integer seqNo) throws
            FinderException;
}
