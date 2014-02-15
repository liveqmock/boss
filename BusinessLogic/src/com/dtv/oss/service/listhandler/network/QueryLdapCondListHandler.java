package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.LdapConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.LdapCondDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * CA主机查询
 * @author 260327h
 *
 */
public class QueryLdapCondListHandler extends ValueListHandler {
	private LdapCondDAO dao = null;

	private final String tableName = "t_ldapcondition t ";

	private LdapConditionDTO dto = null;

	public QueryLdapCondListHandler() {
		this.dao = new LdapCondDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof LdapConditionDTO)
			this.dto = (LdapConditionDTO) dto1;
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

	private void constructSelectQueryString(LdapConditionDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//设置构造取得当前查询总纪录数的sql
	  	selectStatement.append(" where 1=1 ");
	  	this.makeSQLByIntField("condID",dto.getCondId(),selectStatement);
	  	this.makeSQLByStringField("condName",dto.getCondName(),selectStatement,"like");
	  	this.makeSQLByIntField("hostID",dto.getHostId(),selectStatement);
		appendOrderByString(selectStatement);
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by condid desc");
	}
	
}
