package com.dtv.oss.service.listhandler.network;

 
import com.dtv.oss.dto.CAProductDefDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.CaProductDefDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * CA��Ʒ�����ѯ
 * @author 260327h
 *
 */
public class QueryCaProductDefListHandler extends ValueListHandler {
	private CaProductDefDAO dao = null;

	private final String tableName = "t_caproductdef t ";

	private CAProductDefDTO dto = null;

	public QueryCaProductDefListHandler() {
		this.dao = new CaProductDefDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CAProductDefDTO)
			this.dto = (CAProductDefDTO) dto1;
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

	private void constructSelectQueryString(CAProductDefDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
	  	selectStatement.append(" where 1=1 ");
	  	this.makeSQLByIntField("hostid",dto.getHostID(),selectStatement);
		this.makeSQLByIntField("opi_id",dto.getOpiID(),selectStatement);
		this.makeSQLByStringField("caproductid",dto.getCaProductID(),selectStatement);
	  	selectStatement.append(" order by hostid,opi_id,caproductid desc");
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
