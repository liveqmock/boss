package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.MethodOfPaymentDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * ���ܲ�ѯ
 * @author 260327h
 *
 */
public class MethodOfPaymentListHandler extends ValueListHandler {
	private MethodOfPaymentDAO dao = null;

	private final String tableName = "t_methodofpayment t ";

	private MethodOfPaymentDTO dto = null;

	public MethodOfPaymentListHandler() {
		this.dao = new MethodOfPaymentDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof MethodOfPaymentDTO)
			this.dto = (MethodOfPaymentDTO) dto1;
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

	private void constructSelectQueryString(MethodOfPaymentDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
	  	selectStatement.append(" where 1=1 ");
	  	makeSQLByIntField("t.mopid",dto.getMopID(),selectStatement);
		
		selectStatement.append(" order by t.mopID desc");
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
