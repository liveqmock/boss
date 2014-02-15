/*
 * Created on 2005-10-20
 * 
 * @author chenjiang
 */
package com.dtv.oss.service.command.work;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.work.WorkQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.work.CustomerProblemListHandler;
import com.dtv.oss.service.listhandler.work.*;

public class WorkQueryCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch (event.getType()) {
			case WorkQueryEJBEvent.QUERY_TYPE_JOBCARD :
				handler = new JobCardListHandler();
				break;
			case WorkQueryEJBEvent.QUERY_TYPE_JOBCARD_WITHPRIVILEGE:
				handler = new JobCardListHandler(true);
				break;
			case WorkQueryEJBEvent.QUERY_TYPE_JOBCARD_PROCESS_LOG :
				handler = new JobCardProcessLogListHandler();
				break;
			case WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM :
				handler = new CustomerProblemListHandler();
				break;
			case WorkQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM2CUSTOMER_PROBLEM_PROCESS :
				handler = new CustomerProblem2CPProcessListHandler();
				break;
			case WorkQueryEJBEvent.QUERY_TYPE_CP_PROCESS_LOG :
				handler = new CustProblemProcessLogListHandler();
				break;
			//查找客户的排程
			case WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_CUSTOMER:
				handler=new ScheduleListHandler();
				break;
			//查找所有的排程
			case WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_ALL:
				handler=new ScheduleAllListHandler();
				break;
			//查找排程明细
			case WorkQueryEJBEvent.QUERY_TYPE_SCHEDULE_DETAIL:
				handler=new ScheduleDetailListHandler();
				break;
			//歌华模拟工单打印的查询
			case WorkQueryEJBEvent.QUERY_TYPE_JOBCARD_FOR_PRINT:
				handler=new QueryJobCardForPrintListHandler();
				break;
				
			 default :
				break;
		}
		return handler;
	}

}
