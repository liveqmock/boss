/*
 * Created on 2005-10-12
 *
 * @author Chen jiang
 */
package com.dtv.oss.service.ejbevent.work;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class WorkQueryEJBEvent extends QueryEJBEvent{
  public static final int QUERY_TYPE_JOBCARD = 1;
  public static final int QUERY_TYPE_CUSTOMER_PROBLEM = 2;
  public static final int QUERY_TYPE_JOBCARD_PROCESS_LOG = 3;
  public static final int QUERY_TYPE_CP_TRANSITION_INFO = 4;
  public static final int QUERY_TYPE_CP_PROCESS_LOG = 5;
  // add by liyanchun
  public static final int QUERY_TYPE_JOBCARD_WITHPRIVILEGE = 14;

  //add by Mac
  public static final int QUERY_TYPE_CUSTOMER_PROBLEM2JOBCARD = 6;
  
  public static final int QUERY_TYPE_JOBCARD_PRINT = 8;
  
  public static final int QUERY_TYPE_CUSTOMER_PROBLEM2CUSTOMER_PROBLEM_PROCESS = 10;
  
  public static final int QUERY_TYPE_JOBCARD_WITH_CUSTOMER_PROBLEM = 7;
  public static final int QUERY_DIAGNOSTICATE_PARAMETER 	= 9; //疑难报修单诊断参数

  
  //add by zhouxushun for schedule , 2005-12-28
  public static final int QUERY_TYPE_SCHEDULE_ALL=11;
  public static final int QUERY_TYPE_SCHEDULE_DETAIL=12;
  public static final int QUERY_TYPE_SCHEDULE_CUSTOMER=13;
  public static final int QUERY_TYPE_JOBCARD_FOR_PRINT = 15;
  //END 

  
  
  public WorkQueryEJBEvent() {
	  super();
  }
  public WorkQueryEJBEvent(Object dto, int from, int to, int querytype) {
	  super(dto, from, to, querytype);
  }  
}