package com.dtv.oss.service.listhandler.system;

import java.sql.Timestamp;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.dto.VOIPRecordDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.system.BillBoardDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class BillBoardListHandler extends ValueListHandler {
	private BillBoardDAO dao = null;
	private final String tableName=" t_billboard a ";
	private BillBoardDTO dto=null;
	
	public BillBoardListHandler(){
		dao = new BillBoardDAO();
	}
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof BillBoardDTO)
			this.dto = (BillBoardDTO) dto1;
		else {
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
	    constructSelectQueryString(dto);
	    //执行数据查询
	    executeSearch(dao,true,true);
		
		
	}
	private void constructSelectQueryString(BillBoardDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * ");
		begin.append(" from " + tableName);
			
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		selectStatement.append(" and a.status='"+CommonConstDefinition.GENERALSTATUS_VALIDATE);
		selectStatement.append("'");
		Timestamp currentTime=new Timestamp(System.currentTimeMillis());
		selectStatement.append(" and to_timestamp('").append(currentTime).append("','yyyy-MM-dd-HH24:MI:SSxff') between a.datefrom and a.dateto ");
		selectStatement.append(" order by a.dt_create desc");
		
//		设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}
