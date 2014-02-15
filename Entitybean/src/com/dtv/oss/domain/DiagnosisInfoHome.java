package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.DiagnosisInfoDTO;

public interface DiagnosisInfoHome extends javax.ejb.EJBLocalHome {
	public DiagnosisInfo create(Integer id) throws CreateException;

	public DiagnosisInfo create(DiagnosisInfoDTO dto) throws CreateException;

	public DiagnosisInfo findByPrimaryKey(Integer id) throws FinderException;
}