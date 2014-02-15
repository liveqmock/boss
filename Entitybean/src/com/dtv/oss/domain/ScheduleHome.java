package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ScheduleDTO;

public interface ScheduleHome extends javax.ejb.EJBLocalHome {
	public Schedule create(Integer id) throws CreateException;

	public Schedule create(ScheduleDTO dto) throws CreateException;

	public Schedule findByPrimaryKey(Integer id) throws FinderException;
}