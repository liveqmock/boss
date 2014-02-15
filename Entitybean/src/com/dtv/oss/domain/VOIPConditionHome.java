package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.VOIPConditionDTO;

public interface VOIPConditionHome extends javax.ejb.EJBLocalHome{
	public VOIPCondition create(Integer conditionID)throws CreateException;
	
	public VOIPCondition create(VOIPConditionDTO dto)throws CreateException;
	
	public VOIPCondition findByPrimaryKey(Integer conditionID)throws FinderException;
}
