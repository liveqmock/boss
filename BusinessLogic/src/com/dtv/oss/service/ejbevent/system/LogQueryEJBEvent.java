package com.dtv.oss.service.ejbevent.system;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

/**
 * <p>Title: BOSS_P5 for SCND </p>
 * <p>Description: ÎïÁ÷Ä£¿é</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SHDV</p>
 * @author chenjiang
 * @version 1.0
 * @since 2005-12-14
 */

public class LogQueryEJBEvent
    extends QueryEJBEvent {
	
	public static final int QUERY_SYSTEM_EVENT = 1;
	public static final int QUERY_SYSTEM_LOG = 2;

  /**
   * constructor
   */
  public LogQueryEJBEvent(Object dto, int from, int to, int querytype) {
    super(dto,from,to,querytype);
  }



}