package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.BatchResultDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.work.JobCardListHandler;

public class BatchResultListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private BatchResultDAO dao = null;
	private static final Class clazz = BatchResultListHandler.class;
	final private String tableName = " T_QueryResultItem ";
	
	public BatchResultListHandler(){
		this.dao=new BatchResultDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
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

		//SpareStr8:��¼ID
		makeSQLByIntField("QueryID",Integer.valueOf(dto.getSpareStr8()).intValue(),selectStatement);
		//SpareStr9:״̬
		makeSQLByStringField("Status",dto.getSpareStr9(),selectStatement);
		//SpareStr10:�û�֤��
		if(!(dto.getSpareStr10()==null || "".equals(dto.getSpareStr10())))
			selectStatement.append(" and CustomerID like '%"+ dto.getSpareStr10() + "%' ");
		//SpareStr11:�˻���
		if(!(dto.getSpareStr11()==null || "".equals(dto.getSpareStr11())))
			selectStatement.append(" and AccountID like '%"+ dto.getSpareStr11() + "%' ");
		//SpareStr12:ҵ���˻���
		if(!(dto.getSpareStr12()==null || "".equals(dto.getSpareStr12())))
			selectStatement.append(" and UserID like '%"+ dto.getSpareStr12() + "%' ");
		//SpareStr13:��Ӫ�̲�Ʒ
		if(!(dto.getSpareStr13()==null || "".equals(dto.getSpareStr13())))
			selectStatement.append(" and ProductID in ("+ dto.getSpareStr13() + ") ");
		//SpareStr14:��Ӫ�̲�Ʒ��
		if(!(dto.getSpareStr14()==null || "".equals(dto.getSpareStr14())))
			selectStatement.append(" and PackageID in ("+ dto.getSpareStr14() + ")  ");
		//SpareStr15:�����
		if(!(dto.getSpareStr15()==null || "".equals(dto.getSpareStr15())))
			selectStatement.append(" and CCID in ("+ dto.getSpareStr15() + ") ");

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
			selectStatement.append(" order by ItemNO desc");
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
