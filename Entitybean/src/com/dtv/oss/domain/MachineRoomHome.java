package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.MachineRoomDTO;

public interface MachineRoomHome extends EJBLocalHome {
    public MachineRoom create(Integer id) throws CreateException;

    public MachineRoom create(MachineRoomDTO dto) throws CreateException;

    public MachineRoom findByPrimaryKey(Integer id) throws FinderException;
}
