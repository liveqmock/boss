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
import com.dtv.oss.service.dao.statistics.CommonStatForMultiDAO;
import com.dtv.oss.service.dao.statistics.InvoiceStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;


/**
 * �ʵ�ͳ��
 * date       : 2007-1-17
 * description:
 * SpareStr1:�ͻ�����
 * SpareStr2:��������
 * SpareStr3:�ʻ�����
 * SpareStr4:��������
 * SpareStr5:��������
 * SpareTime1:������ʼʱ��
 * SpareTime2:��������ʱ��
 * @author 
 *
 */

public class BillStatListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;

	private InvoiceStatDAO dao = null;

	private static final Class clazz = CustomerStatListHandler.class;

	final private String tableName = " T_Invoice ";

	public BillStatListHandler() {
		dao = new InvoiceStatDAO();
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
		
		sqlShow.append("select invoice.Status subName, count(invoice.Invoiceno) amount,sum(invoice.amount) secSubName");
		sqlTable.append(" from T_Invoice invoice");
		sqlWhere.append(" where invoice.CustID = cust.CUSTOMERID");
		sqlGroup.append(" group by invoice.Status");
		

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
			sqlWhere.append(" and cust.CustomerType='"+dto.getSpareStr1()+"'");
		}
	
		if (!(dto.getSpareStr3() == null || "".equals(dto.getSpareStr3())))
		{
			sqlTable.append(",T_Account acc");
			sqlWhere.append(" and invoice.AcctID =acc.AccountID");
			sqlWhere.append(" and acc.AccountType='" + dto.getSpareStr3()+ "'");
			
		}
		if (!(dto.getSpareStr4() == null || "".equals(dto.getSpareStr4())))
		{
			sqlWhere.append(" and invoice.InvoiceCycleID='" + dto.getSpareStr4()+ "'");
		}
		
		if (!(dto.getSpareStr5() == null || "".equals(dto.getSpareStr5()))) {
			if (sqlTable.indexOf(",T_Account acc") == -1) {
				sqlTable.append(",T_Account acc");
				sqlWhere.append(" and invoice.AcctID =acc.AccountID");
			}
			sqlWhere.append(" and acc.MOPID='" + dto.getSpareStr5() + "'");
			
		}

		// SpareTime1:����ʱ��1
		if (dto.getSpareTime1() != null)
		{
			sqlWhere.append(" and invoice.CreateDate>=to_timestamp('").append(dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
		}
		// SpareTime2:����ʱ��2
		if (dto.getSpareTime2() != null)
		{
			sqlWhere.append(" and invoice.CreateDate<=to_timestamp('").append(dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		
		StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		// ���õ�ǰ���ݲ�ѯsql
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("select statdata.subname,nvl(statdata.amount,0) amount,nvl(statdata.secSubName,0) secSubName,setting.id,setting.name" +
				" from (select * from t_districtsetting where belongto ="+id+")setting,("
				+sqlStr+") statdata where statdata.id (+)= setting.id order by setting.districtcode desc,setting.id desc");
		setRecordDataQueryBuffer(queryStr);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
