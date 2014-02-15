/*
 * Created on 2006-2-14
 * @author Stone Liang
 *
 * 应收费统计
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
 * 帐单统计
 * date       : 2007-1-17
 * description:
 * SpareStr1:客户类型
 * SpareStr2:所在区域
 * SpareStr3:帐户类型
 * SpareStr4:帐务周期
 * SpareStr5:付费类型
 * SpareTime1:创建起始时间
 * SpareTime2:创建结束时间
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
		// 构造查询字符串
		constructSelectQueryString(dto);

		// 执行数据查询
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
		// 没有选择区域,按照大的区域统计;选择了区域，要按照区域下的分支统计
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

		// SpareTime1:创建时间1
		if (dto.getSpareTime1() != null)
		{
			sqlWhere.append(" and invoice.CreateDate>=to_timestamp('").append(dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
		}
		// SpareTime2:创建时间2
		if (dto.getSpareTime2() != null)
		{
			sqlWhere.append(" and invoice.CreateDate<=to_timestamp('").append(dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		
		StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		// 设置当前数据查询sql
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
