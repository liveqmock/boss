package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.BossConfigurationDTO;

public interface BossConfigurationHome extends javax.ejb.EJBLocalHome {
	public BossConfiguration create(String softwareName) throws CreateException;

	public BossConfiguration create(BossConfigurationDTO dto)
			throws CreateException;

	public BossConfiguration findByPrimaryKey(String softwareName)
			throws FinderException;
}