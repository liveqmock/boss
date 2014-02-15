package com.dtv.oss.domain;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.dto.ActivityToMarketSegmentDTO;

public interface ActivityToMarketSegmentHome extends javax.ejb.EJBLocalHome {
  public ActivityToMarketSegment create(int activityId, int marketSegmentId) throws CreateException;
  public ActivityToMarketSegment create(ActivityToMarketSegmentDTO dto) throws CreateException;
  public ActivityToMarketSegment findByPrimaryKey(ActivityToMarketSegmentPK pk) throws FinderException;
}