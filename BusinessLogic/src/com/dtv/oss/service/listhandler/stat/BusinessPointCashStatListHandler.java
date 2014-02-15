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
		// 构造查询字符串
		constructSelectQueryString(dto);

		// 执行数据查询
		executeSearch(dao, false, false);

	}

	/**
	 * 营业点收费统计 date : 2007-1-19 description: SpareStr4:客户类型 SpareStr2:所在区域
	 * SpareStr3:所在机构 SpareTime1:支付起始时间 SpareTime2:支付结束时间
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

		// 本统计只统计状态为非异常的费用
		sqlWhere.append(" and payre.Status not in ('"
				+ CommonConstDefinition.AISTATUS_INVALIDATE + "','"
				+ CommonConstDefinition.AISTATUS_LOCK + "','"
				+ CommonConstDefinition.AISTATUS_POTENTIAL + "')");

		// 选择了组织机构，只统计它直接对应的收费记录
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
		// 帐户类型
		if (!(dto.getSpareStr5() == null || "".equals(dto.getSpareStr5()))) {
			sqlTable.append(",T_Account account");
			sqlWhere
					.append(" and payre.AcctID = account.AccountID and account.AccountType='"
							+ dto.getSpareStr5() + "'");
		}
		// 收费类型
		if (!(dto.getSpareStr6() == null || "".equals(dto.getSpareStr6()))) {
			sqlWhere.append(" and payre.PayType ='" + dto.getSpareStr6() + "'");
		}
		//支付方式
		if(!(dto.getSpareStr7()==null || "".equals(dto.getSpareStr7()))){
			sqlWhere.append(" and payre.MOPID ="+dto.getSpareStr7());
		}

		// SpareTime1:支付时间1
		if (dto.getSpareTime1() != null) {
			sqlWhere.append(" and payre.PaymentTime>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// SpareTime2:支付时间2
		if (dto.getSpareTime2() != null) {
			sqlWhere.append(" and payre.PaymentTime<=to_timestamp('").append(
					dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);

		// 设置当前数据查询sql
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
