package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.BatchDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class BatchListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private BatchDAO dao = null;
	private static final Class clazz = BatchListHandler.class;
	final private String tableName = " T_Query ";
	
	public BatchListHandler(){
		this.dao=new BatchDAO();
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
		
		if("all".equals(dto.getSpareStr6()))//��ѯ����
		{
			
		}
		else if(CommonConstDefinition.YESNOFLAG_YES.equals(dto.getSpareStr6()))//ģ���ѯ
		{
			makeSQLByStringField("Templateflag",CommonConstDefinition.YESNOFLAG_YES,selectStatement);
		}
		else //һ��������ѯ
		{
			selectStatement.append(" and (Templateflag != '"+CommonConstDefinition.YESNOFLAG_YES+"' or Templateflag is null) ");
		}

		//SpareStr1:��ѯ��������
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			selectStatement.append(" and QueryName like '%" + dto.getSpareStr1() +"%'");
		//SpareStr2:���������
		makeSQLByStringField("QueryType",dto.getSpareStr2(),selectStatement);
		//SpareStr3:ִ�з�ʽ
		makeSQLByStringField("ScheduleType",dto.getSpareStr3(),selectStatement);
		//SpareStr4:״̬
		if(dto.getSpareStr4()!=null && (!"".equals(dto.getSpareStr4()))
				&& dto.getSpareStr5()!=null && (!"".equals(dto.getSpareStr5())))
			selectStatement.append(" and ( Status='" + dto.getSpareStr4() +"' or Status='" + dto.getSpareStr5() +"') ");
		else
			makeSQLByStringField("Status",dto.getSpareStr4(),selectStatement);
		
		//SpareTime1:��¼������ʼʱ��
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and CreateTime>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:��¼������ֹʱ��
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and CreateTime<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime3:Ԥ��ִ����ʼʱ��
		if(dto.getSpareTime3()!=null)
			selectStatement.append(" and ScheduleTime>=to_timestamp('").append(dto.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime4:Ԥ��ִ�н�ֹʱ��
		if(dto.getSpareTime4()!=null)
			selectStatement.append(" and ScheduleTime<=to_timestamp('").append(dto.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime5:ʵ��ִ����ʼʱ��
		if(dto.getSpareTime5()!=null)
			selectStatement.append(" and PerformTime>=to_timestamp('").append(dto.getSpareTime5().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime6:ʵ��ִ�н�ֹʱ��
		if(dto.getSpareTime6()!=null)
			selectStatement.append(" and PerformTime<=to_timestamp('").append(dto.getSpareTime6().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareStr8:��¼ID
		if(!(dto.getSpareStr8()==null || "".equals(dto.getSpareStr8())))
			selectStatement.append(" and QueryID =" + dto.getSpareStr8() + " ");
			
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
			selectStatement.append(" order by QueryID desc");
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
