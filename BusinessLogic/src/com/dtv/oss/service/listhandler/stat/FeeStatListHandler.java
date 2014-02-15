/*
 * Created on 2006-2-14
 * @author Stone Liang
 *
 * Ӧ�շ�ͳ��
 *
 */
package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;


/**
 * date       : 2007-1-15
 * description:
 * SpareStr1:�ͻ�����
 * SpareStr2:��������
 * SpareStr3:�ʻ�����
 * SpareStr4:��������
 * SpareTime1:������ʼʱ��
 * SpareTime2:��������ʱ��
 * @author 
 *
 */

public class FeeStatListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;

	private CommonStatDAO dao = null;

	private static final Class clazz = CustomerStatListHandler.class;

	final private String tableName = " T_AccountItem ";

	public FeeStatListHandler() {
		dao = new CommonStatDAO();
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

	private void constructSelectQueryString(CommonQueryConditionDTO dto)
			throws ListHandlerException {
		StringBuffer sqlShow = new StringBuffer();
		StringBuffer sqlTable = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		StringBuffer sqlGroup = new StringBuffer();
		
		
		//�������û��ѡ��������ͣ���ô���Ƿ������͡����ѡ��������ͣ���ô������������Ŀ���ͣ�һ����Ŀ��
		if (!(dto.getSpareStr4() == null || "".equals(dto.getSpareStr4())))
		{
			sqlShow.append("select accitem.AcctItemTypeID subName, sum(accitem.Value) amount");
			sqlTable.append(" from T_AccountItem accitem,T_ACCTITEMTYPE acctype");
			sqlWhere.append(" where accitem.CustID = cust.CUSTOMERID and accitem.FeeType = '"+dto.getSpareStr4()+"'");
			sqlWhere.append(" and accitem.AcctItemTypeID = acctype.AcctItemTypeID");
			//sqlWhere.append(" and acctype.ShowLevel='1'");//???
			sqlWhere.append(" and acctype.SummaryAIFlag='"+CommonConstDefinition.YESNOFLAG_YES+"'");
			sqlWhere.append(" and acctype.Status='"+CommonConstDefinition.GENERALSTATUS_VALIDATE+"'");
			sqlGroup.append(" group by accitem.AcctItemTypeID");
			
		}
		else {
			sqlShow.append("select accitem.FeeType subName, sum(accitem.Value) amount");
			sqlTable.append(" from T_AccountItem accitem");
			sqlWhere.append(" where accitem.CustID = cust.CUSTOMERID");
			sqlGroup.append(" group by accitem.FeeType");
			
		}
		
		//��ͳ��ֻͳ��״̬Ϊ���쳣�ķ���
		sqlWhere.append(" and accitem.Status not in ('"+CommonConstDefinition.AISTATUS_INVALIDATE+"','"+
				CommonConstDefinition.AISTATUS_LOCK+"','"+CommonConstDefinition.AISTATUS_POTENTIAL+"')");
		
		String id = "0";
		// û��ѡ������,���մ������ͳ��;ѡ��������Ҫ���������µķ�֧ͳ��
		if (!(dto.getSpareStr2() == null || "".equals(dto.getSpareStr2()))) {
			id = dto.getSpareStr2();
		}
		sqlShow.append(distShowNew);
		sqlTable.append(getDistTableNew(id));
		sqlWhere.append(distWhereNew);
		sqlGroup.append(distGroupNew);
		
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
		{
			sqlWhere.append(" and cust.CustomerType = '"+dto.getSpareStr1()+"'");
		}
	
		if (!(dto.getSpareStr3() == null || "".equals(dto.getSpareStr3())))
		{
			sqlTable.append(",T_Account acc");
			sqlWhere.append(" and accitem.AcctID =acc.AccountID");
			sqlWhere.append(" and acc.AccountType='" + dto.getSpareStr3()+ "'");
			
		}

		// SpareTime1:����ʱ��1
		if (dto.getSpareTime1() != null)
		{
			sqlWhere.append(" and accitem.createtime>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
		}
		// SpareTime2:����ʱ��2
		if (dto.getSpareTime2() != null)
		{
			sqlWhere.append(" and accitem.createtime<=to_timestamp('").append(
					dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
		}
		
		StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,"D"));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
