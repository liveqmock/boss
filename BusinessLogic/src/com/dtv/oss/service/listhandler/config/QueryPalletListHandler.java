package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.PalletDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * ���ܲ�ѯ
 * @author 260327h
 *
 */
public class QueryPalletListHandler extends ValueListHandler {
	private PalletDAO dao = null;

	private final String tableName = "t_pallet t ";

	private PalletDTO dto = null;

	public QueryPalletListHandler() {
		this.dao = new PalletDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof PalletDTO)
			this.dto = (PalletDTO) dto1;
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

	private void constructSelectQueryString(PalletDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
	  	selectStatement.append(" where 1=1 ");
	  	//���û���ID
    	makeSQLByIntField("t.palletID",dto.getPalletID(),selectStatement);
    	//���û�������
    	makeSQLByStringField("t.palletName",dto.getPalletName(),selectStatement,"like");
    	//���û���״̬
    	makeSQLByStringField("t.status",dto.getStatus(),selectStatement);
    	//���û��������ֿ�
        makeSQLByIntField("t.depotid",dto.getDepotID(),selectStatement);
		
		selectStatement.append(" order by t.palletID asc");
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
