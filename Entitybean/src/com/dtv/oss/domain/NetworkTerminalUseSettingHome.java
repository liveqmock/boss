package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.dtv.oss.dto.NetworkTerminalUseSettingDTO;

public interface NetworkTerminalUseSettingHome extends EJBLocalHome {
    public NetworkTerminalUseSetting create(int serviceId,
                                            String terminalType) throws
            CreateException;

    

	public NetworkTerminalUseSetting create(NetworkTerminalUseSettingDTO dto)
			throws CreateException;

	 
    public NetworkTerminalUseSetting findByPrimaryKey(
           
            NetworkTerminalUseSettingPK pk) throws FinderException;
}
