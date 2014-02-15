package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.VOIPEventCmdDTO;
import com.dtv.oss.dto.wrap.VOIPEventWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VOIPEventCmdDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class QueryVOIPEventCmdListHandler extends ValueListHandler {
	private VOIPEventCmdDTO dto=null;
	private final String tableName="t_ssif_queue a,t_softswitch_interface b,t_ssif_command c";
	private VOIPEventCmdDAO dao=null;
	
	public QueryVOIPEventCmdListHandler() {
		this.dao=new VOIPEventCmdDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
			"in setCriteria begin setCriteria...");
		if (dto1 instanceof VOIPEventCmdDTO)
			this.dto = (VOIPEventCmdDTO) dto1;
		else {
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
		
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(VOIPEventCmdDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select a.queueid queueid, a.ifid ifid, a.cmdid cmdid, a.eventid eventid, a.createtime createtime, a.donetime donetime, a.status status, b.ifname ifname, c.commandname commandname ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//设置构造取得当前查询总纪录数的sql
	  	selectStatement.append(" where a.ifid=b.ifid and a.cmdid=c.commandid ");
	  	this.makeSQLByIntField("eventid",dto.getEventID(),selectStatement);
		appendOrderByString(selectStatement);

	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
		
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by queueid");
		
	}

}
