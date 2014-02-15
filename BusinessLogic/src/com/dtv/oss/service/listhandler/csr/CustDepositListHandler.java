package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.AccountItemDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CustDepositListHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private AccountItemDAO dao = null;
	private static final Class clazz = CustDepositListHandler.class;
	final private String tableName = " T_AccountItem ";

	public CustDepositListHandler(){
		this.dao=new AccountItemDAO();
	}
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO)dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		StringBuffer begin = new StringBuffer();
		StringBuffer selectStatement = new StringBuffer(128);
		begin.append("select * from " + tableName);
		selectStatement.append(" where value > 0 ");

		//SpareStr1:�˻���
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByIntField("AcctID",Integer.valueOf(dto.getSpareStr1()).intValue(),selectStatement);
		//SpareStr2:������
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByIntField("ReferID",Integer.valueOf(dto.getSpareStr2()).intValue(),selectStatement);
		//SpareStr3:���ü�¼��ˮ��
		if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByIntField("AI_NO",Integer.valueOf(dto.getSpareStr3()).intValue(),selectStatement);
		//SpareStr4:�ͻ�֤��
		if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4())))
			makeSQLByIntField("CustID",Integer.valueOf(dto.getSpareStr4()).intValue(),selectStatement);
		//SpareStr5:��Ŀ����
		makeSQLByStringField("AcctItemTypeID",dto.getSpareStr5(),selectStatement);
		//SpareStr6:״̬(��Ч)
	//	makeSQLByStringField("Status",dto.getSpareStr6(),selectStatement);
		//SpareTime1:������ʼʱ��
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and CreateTime>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:������ֹʱ��
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and CreateTime<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		appendOrderByString(selectStatement);
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by AI_NO desc");
		else {
			selectStatement.append(" order by " + dto.getOrderField()
					+ orderByAscend);
		}
		orderByAscend = null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
