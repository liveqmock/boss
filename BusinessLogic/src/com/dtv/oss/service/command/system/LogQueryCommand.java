/*
 * Created on 2005-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.command.system;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.system.LogQueryEJBEvent;
import com.dtv.oss.service.ejbevent.work.WorkQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.system.LogListHandler;
import com.dtv.oss.service.listhandler.system.SystemEventListHandler;
import com.dtv.oss.service.listhandler.work.JobCardListHandler;

/**
 * @author chen jiang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogQueryCommand
extends QueryCommand {

public ValueListHandler getActualListHandler(QueryEJBEvent event) {
ValueListHandler handler = null;
switch (event.getType()) {
case LogQueryEJBEvent.QUERY_SYSTEM_EVENT :
	handler = new SystemEventListHandler();
	break;
case LogQueryEJBEvent.QUERY_SYSTEM_LOG :
	handler = new LogListHandler();
	break;
default :
	break;
}
  return handler;
}
}