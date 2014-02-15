package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.BankDeductionHeaderDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class BankDeductionHeaderListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private BankDeductionHeaderDAO dao = null;
	private static final Class clazz = BankDeductionHeaderListHandler.class;
	final private String tableName = " T_BankDeductionHeader ";
	
	public BankDeductionHeaderListHandler(){
		this.dao=new BankDeductionHeaderDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
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
		selectStatement.append(" where 1=1 ");
		
		//SpareStr1��������
		if(dto.getSpareStr1()!=null && dto.getSpareStr1().trim().length()>0)
			makeSQLByIntField("BatchNo", Integer.parseInt(dto.getSpareStr1()), selectStatement);
		//SpareStr2:����״̬
		makeSQLByStringField("ProcessStatus", dto.getSpareStr2(), selectStatement);
		//SpareStr3:֧����ʽ
		if(dto.getSpareStr3()!=null && dto.getSpareStr3().trim().length()>0)
			makeSQLByIntField("MopID", Integer.parseInt(dto.getSpareStr3()), selectStatement);
		//SpareStr4:��������
		if(dto.getSpareStr4()!=null && dto.getSpareStr4().trim().length()>0)
			makeSQLByIntField("billingcycle", Integer.parseInt(dto.getSpareStr4()), selectStatement);
		//SpareTime1:��¼������ʼʱ��
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and Dt_create>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:��¼������ֹʱ��
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and Dt_create<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
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
			selectStatement.append(" order by BatchNo desc");
		else 
			selectStatement.append(" order by " + dto.getOrderField()+ orderByAscend);
		
		orderByAscend = null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
