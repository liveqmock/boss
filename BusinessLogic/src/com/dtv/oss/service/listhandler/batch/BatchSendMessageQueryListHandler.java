package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.GeHuaUploadCustBatchHeaderDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class BatchSendMessageQueryListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private GeHuaUploadCustBatchHeaderDAO dao = null;
	private static final Class clazz = BatchListHandler.class;
	final private String tableName = " GeHua_UploadCust_BatchHeader ";
	public BatchSendMessageQueryListHandler(int operatorID){
		this.dao=new GeHuaUploadCustBatchHeaderDAO();
		this.operatorID = operatorID;
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
		//����ͻ���Ϣ�Ĳ���Ա��������֯�ǵ�ǰ����Ա��������֯������������֯
		selectStatement.append(" where CreateOrgID in(select orgid from t_organization connect by prior orgid=parentorgid start with orgid = (select orgid from t_operator where operatorid = "+operatorID+"))");
		//��������
		if(dto.getSpareStr1()!=null)
			selectStatement.append(" and JobType='").append(dto.getSpareStr1()).append("' ");
		//SpareTime1:��¼������ʼʱ��
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and CREATETIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:��¼������ֹʱ��
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and CREATETIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
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
			selectStatement.append(" order by BATCHNO desc");
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
