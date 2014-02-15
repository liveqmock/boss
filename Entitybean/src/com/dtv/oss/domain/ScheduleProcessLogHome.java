package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ScheduleProcessLogDTO;

public interface ScheduleProcessLogHome extends javax.ejb.EJBLocalHome {
	public ScheduleProcessLog create(Integer seqNo) throws CreateException;

	public ScheduleProcessLog create(ScheduleProcessLogDTO dto)
			throws CreateException;

	public ScheduleProcessLog findByPrimaryKey(Integer seqNo)
			throws FinderException;
}