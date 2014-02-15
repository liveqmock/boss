package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.BankDeductionDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class BankDeductionDetailListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private BankDeductionDetailDAO dao = null;
	private static final Class clazz = BankDeductionDetailListHandler.class;
	final private String tableName = " T_BankDeductionHeader header,T_BankDeductionDetail detail,T_Customer cust,T_Address addr ";
	
	public BankDeductionDetailListHandler(){
		this.dao=new BankDeductionDetailDAO();
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
		
		begin.append("select detail.* from " + tableName);
		selectStatement.append(" where detail.BatchNo=header.BatchNo and detail.CUSTOMERID=cust.CUSTOMERID and cust.AddressID=addr.Addressid ");
		
		
		//SpareStr1��������
		if(dto.getSpareStr1()!=null && dto.getSpareStr1().trim().length()>0)
			makeSQLByIntField("header.BatchNo", Integer.parseInt(dto.getSpareStr1()), selectStatement);
		//SpareStr2:����״̬
		makeSQLByStringField("detail.status", dto.getSpareStr2(), selectStatement);
		//SpareStr3:֧����ʽ
		if(dto.getSpareStr3()!=null && dto.getSpareStr3().trim().length()>0)
			makeSQLByIntField("header.MopID", Integer.parseInt(dto.getSpareStr3()), selectStatement);
		//SpareStr4:��������
		if(dto.getSpareStr4()!=null && dto.getSpareStr4().trim().length()>0)
			makeSQLByIntField("header.billingcycle", Integer.parseInt(dto.getSpareStr4()), selectStatement);
		//SpareTime1:��¼������ʼʱ��
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and detail.Dt_create>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:��¼������ֹʱ��
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and detail.Dt_create<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		//SpareStr5:�ͻ�֤��
		if(dto.getSpareStr5()!=null && dto.getSpareStr5().trim().length()>0)
			makeSQLByIntField("detail.CUSTOMERID", Integer.parseInt(dto.getSpareStr5()), selectStatement);
		//SpareStr6:�ͻ�����
		makeSQLByStringField("cust.CustomerType", dto.getSpareStr6(), selectStatement);
		//SpareStr7:�ͻ���������
		if(dto.getSpareStr7()!=null && dto.getSpareStr7().trim().length()>0){
			selectStatement.append(" and addr.DistrictID in (  select ID from T_DISTRICTSETTING WHERE STATUS='V' " +
					"connect by prior ID=BELONGTO start with ID in (" + dto.getSpareStr7() + ") )");
		}
		//SpareStr8:��ˮ��
		if(dto.getSpareStr8()!=null && dto.getSpareStr8().trim().length()>0)
			makeSQLByIntField("detail.SeqNo", Integer.parseInt(dto.getSpareStr8()), selectStatement);
		
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
			selectStatement.append(" order by SeqNo desc");
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
