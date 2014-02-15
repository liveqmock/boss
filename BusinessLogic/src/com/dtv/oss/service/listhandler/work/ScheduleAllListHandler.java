package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.ScheduleAllDAO;
import com.dtv.oss.service.dao.work.ScheduleDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;

public class ScheduleAllListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private ScheduleAllDAO dao = null;
	private static final Class clazz = ScheduleAllListHandler.class;
	final private String tableName = " T_BATCHJOB batch ";
	
	public ScheduleAllListHandler(){
		this.dao=new ScheduleAllDAO();
	}
	
	/**
	 * 
	 */
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
		begin.append("select * from " + tableName);
	    StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		
		selectStatement.append(" and ( batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PC +"' or ");
		selectStatement.append("  batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PR +"' or ");
		selectStatement.append("  batch.JOBTYPE='"+CommonConstDefinition.BATCH_JOB_TYPE_PS +"' ) ");
		
		//�ų�������ID
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			makeSQLByIntField("batch.BatchID", Integer.valueOf(dto.getSpareStr1()).intValue(), selectStatement);
		//�ų�����״̬
		makeSQLByStringField("batch.Status",dto.getSpareStr7(),selectStatement);
		//�¼�ԭ��
		makeSQLByStringField("batch.ReasonCode",dto.getSpareStr4(),selectStatement);
		//�¼�����
		makeSQLByStringField("batch.JobType",dto.getSpareStr3(),selectStatement);
		//
		makeSQLByStringField("batch.REFERTYPE",dto.getSpareStr8(),selectStatement);
		
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
		
		//�û�֤��:dto.setSpareStr5
		if(!(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5()))){
			selectStatement.append(" and batch.batchID in (select distinct batchID  from t_batchJobItem t where t.CUSTOMERID = "+ dto.getSpareStr5()+") ");
		}
		//�ͻ�����:dto.setSpareStr6
		if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6()))){
			selectStatement.append(" and batch.batchID in (select distinct batchID  from t_batchJobItem t,t_customer p where t.customerID =p.customerID and p.name like '%"+ dto.getSpareStr6()+"%') ");
		}
		if(dto.getSpareStr9() != null)
			selectStatement.append(" and batch.batchID in (select distinct batchID from t_batchJobItem t,t_customer p where t.customerID = p.customerID and p.addressID in(select addressid from t_address where detailaddress like '%" + dto.getSpareStr9() + "%'))");
		if(dto.getDistrict()!= 0){
			selectStatement.append(" and batch.batchID in (select distinct batchID from t_batchJobItem t,t_customer p where t.customerID = p.customerID and p.addressID in(select addressid from t_address where districtid in (select Id from T_DISTRICTSETTING "
					+ "connect by prior ID=BELONGTO  start with ID=" + dto.getDistrict() +")))");
		}
		appendOrderByString(selectStatement);
	    setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
		
	}


	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by batch.batchID desc");
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
