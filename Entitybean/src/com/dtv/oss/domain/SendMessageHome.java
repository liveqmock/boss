package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.SendMessageDTO;

public interface SendMessageHome extends javax.ejb.EJBLocalHome {
	public SendMessage create(Integer messageId) throws CreateException;

	public SendMessage create(SendMessageDTO dto) throws CreateException;

	public SendMessage findByPrimaryKey(Integer messageId)
			throws FinderException;
}