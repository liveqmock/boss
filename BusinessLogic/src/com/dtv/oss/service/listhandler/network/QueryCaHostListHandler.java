package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.CAHostDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.CaHostDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * CA������ѯ
 * @author 260327h
 *
 */
public class QueryCaHostListHandler extends ValueListHandler {
	private CaHostDAO dao = null;

	private final String tableName = "t_cahost t ";

	private CAHostDTO dto = null;

	public QueryCaHostListHandler() {
		this.dao = new CaHostDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CAHostDTO)
			this.dto = (CAHostDTO) dto1;
		else {
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
		
	}

	private void constructSelectQueryString(CAHostDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
	  	selectStatement.append(" where 1=1 ");
	  	this.makeSQLByIntField("hostID",dto.getHostID(),selectStatement);
		appendOrderByString(selectStatement);
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by hostid desc");
	}
	
}
