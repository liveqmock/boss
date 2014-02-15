package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.VOIPCmdProDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VOIPCmdProDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class QueryVOIPCmdListHandler extends ValueListHandler {
	private VOIPCmdProDTO dto=null;
	private final String tableName=" t_ssif_cmd_his a, t_ssif_command b ";
	private VOIPCmdProDAO dao=null;
	
	public QueryVOIPCmdListHandler() {
		this.dao=new VOIPCmdProDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
			"in setCriteria begin setCriteria...");
		if (dto1 instanceof VOIPCmdProDTO)
			this.dto = (VOIPCmdProDTO) dto1;
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

	private void constructSelectQueryString(VOIPCmdProDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select a.seqno seqno, a.ifid ifid, a.ifname ifname, a.queueid queueid, a.eventid eventid, a.cmdid cmdid, ");
		begin.append(" a.phoneno phoneno, a.cardno cardno, a.cmdcode cmdcode, a.tranid tranid, ");
		begin.append(" a.senddata senddata, a.senttime sendtime, a.rcvdata revdata, a.rcvtime revtime, a.processresult processresult, ");
		begin.append(" a.dt_create dt_create, a.dt_lastmod dt_lastmod, b.commandname commandname");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//设置构造取得当前查询总纪录数的sql
	  	selectStatement.append(" where a.cmdid=b.commandid ");
	  	this.makeSQLByIntField("queueid",dto.getQueueID(),selectStatement);
		appendOrderByString(selectStatement);

	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by seqno");
	}
}
