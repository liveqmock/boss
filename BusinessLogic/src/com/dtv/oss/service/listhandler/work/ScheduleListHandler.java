package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.ScheduleDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * ���ҿͻ�ҵ���ʻ��²�Ʒ���ų�
 * author     ��zhouxushun 
 * date       : 2005-12-30
 * description:
 * @author 250713z
 *
 */
public class ScheduleListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private ScheduleDAO dao = null;
	private static final Class clazz = ScheduleListHandler.class;
	final private String tableName = " T_BATCHJOB batch,T_BATCHJOBITEM item ";
	
	public ScheduleListHandler(){
		dao=new ScheduleDAO();
	}
	
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,"the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);

	}

	/**
	 * 
	 * @param dto2
	 */
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		

		StringBuffer begin = new StringBuffer();
		
		begin.append(" select distinct batch.*,"+CommonUtility.getSelectExpress4BatchJobItemInfo("item.")
				   +" from " + tableName);
	
		
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 and batch.BATCHID=item.BATCHID  ");
		selectStatement.append(" and ( batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PC +"' or ");
		selectStatement.append("  batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PR +"' or ");
		selectStatement.append("  batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PS +"' ) ");
		
		//ֻ��ѯ�����ͻ��������ų� .
		makeSQLByStringField("refertype",CommonConstDefinition.BATCH_JOB_REFER_TYPE_C,selectStatement);
		//�ų�������ID
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByIntField("batch.BatchID", Integer.valueOf(dto.getSpareStr1()).intValue(), selectStatement);
				
		//�ų�����״̬
		makeSQLByStringField("batch.Status",dto.getSpareStr7(),selectStatement);
		//�¼�ԭ��
		makeSQLByStringField("batch.ReasonCode",dto.getSpareStr4(),selectStatement);
		//�¼�����
		makeSQLByStringField("batch.JobType",dto.getSpareStr3(),selectStatement);
		//�ͻ�ID
		makeSQLByIntField("CUSTOMERID",Integer.parseInt(dto.getSpareStr5()),selectStatement);

		//�ų�ִ��ʱ��
		if (dto.getSpareTime1()!=null)
			selectStatement.append(" and batch.ScheduleTime>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if (dto.getSpareTime2() != null)
			selectStatement.append(" and batch.ScheduleTime<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		//�ų̴���ʱ��
		if (dto.getSpareTime3()!=null)
			selectStatement.append(" and batch.CreateTime>=to_timestamp('").append(dto.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if (dto.getSpareTime4() != null)
			selectStatement.append(" and batch.CreateTime<=to_timestamp('").append(dto.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		appendOrderByString(selectStatement);
	    setRecordCountQueryTable(tableName);
	    setRecordCountSuffixBuffer(selectStatement);
	    
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		 
		selectStatement.append(" order by batch.batchid desc,item.itemno desc");

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
