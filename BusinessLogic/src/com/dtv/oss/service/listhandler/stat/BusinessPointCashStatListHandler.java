package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatForBatchDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class BusinessPointCashStatListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;

	private CommonStatForBatchDAO dao = null;

	private static final Class clazz = PayStatListHandler.class;

	final private String tableName = " T_PaymentRecord payre ";

	public BusinessPointCashStatListHandler() {
		dao = new CommonStatForBatchDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// �����ѯ�ַ���
		constructSelectQueryString(dto);

		// ִ�����ݲ�ѯ
		executeSearch(dao, false, false);

	}

	/**
	 * Ӫҵ���շ�ͳ�� date : 2007-1-19 description: SpareStr4:�ͻ����� SpareStr2:��������
	 * SpareStr3:���ڻ��� SpareTime1:֧����ʼʱ�� SpareTime2:֧������ʱ��
	 * 
	 * @author
	 * 
	 */
	private void constructSelectQueryString(CommonQueryConditionDTO dto)
			throws ListHandlerException {

		StringBuffer sqlShow = new StringBuffer();
		StringBuffer sqlTable = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		StringBuffer sqlGroup = new StringBuffer();

		sqlShow
				.append("select payre.PayType subName, sum(payre.Amount) amount,count(payre.SeqNo) batchnumber");
		sqlShow.append(",ope.operatorid id,ope.operatorname name");

		sqlTable.append(" from " + tableName);
		sqlTable.append(",t_operator ope");

		sqlWhere.append(" where 1 = 1");
		sqlWhere.append(" and payre.OpID=ope.operatorid");

		sqlGroup.append(" group by payre.PayType");
		sqlGroup.append(",ope.operatorid,ope.operatorname");

		// ��ͳ��ֻͳ��״̬Ϊ���쳣�ķ���
		sqlWhere.append(" and payre.Status not in ('"
				+ CommonConstDefinition.AISTATUS_INVALIDATE + "','"
				+ CommonConstDefinition.AISTATUS_LOCK + "','"
				+ CommonConstDefinition.AISTATUS_POTENTIAL + "')");

		// ѡ������֯������ֻͳ����ֱ�Ӷ�Ӧ���շѼ�¼
		if (!(dto.getSpareStr3() == null || "".equals(dto.getSpareStr3()))) {
			sqlWhere.append(" and payre.orgid=" + dto.getSpareStr3());
		}

		if (!(dto.getSpareStr4() == null || "".equals(dto.getSpareStr4()))) {
			if (sqlTable.indexOf(",t_customer cust") < 1) {
				sqlTable.append(",t_customer cust");
				sqlWhere.append(" and payre.CustID = cust.CUSTOMERID ");

			}
			sqlWhere.append(" and cust.CustomerType='" + dto.getSpareStr4()
					+ "' ");
		}
		// �ʻ�����
		if (!(dto.getSpareStr5() == null || "".equals(dto.getSpareStr5()))) {
			sqlTable.append(",T_Account account");
			sqlWhere
					.append(" and payre.AcctID = account.AccountID and account.AccountType='"
							+ dto.getSpareStr5() + "'");
		}
		// �շ�����
		if (!(dto.getSpareStr6() == null || "".equals(dto.getSpareStr6()))) {
			sqlWhere.append(" and payre.PayType ='" + dto.getSpareStr6() + "'");
		}
		//֧����ʽ
		if(!(dto.getSpareStr7()==null || "".equals(dto.getSpareStr7()))){
			sqlWhere.append(" and payre.MOPID ="+dto.getSpareStr7());
		}

		// SpareTime1:֧��ʱ��1
		if (dto.getSpareTime1() != null) {
			sqlWhere.append(" and payre.PaymentTime>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// SpareTime2:֧��ʱ��2
		if (dto.getSpareTime2() != null) {
			sqlWhere.append(" and payre.PaymentTime<=to_timestamp('").append(
					dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);

		// ���õ�ǰ���ݲ�ѯsql
		StringBuffer finalBuffer = new StringBuffer();
		finalBuffer.append(sqlShow).append(sqlTable).append(sqlWhere).append(
				sqlGroup);
		setRecordDataQueryBuffer(finalBuffer);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
