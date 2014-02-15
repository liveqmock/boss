package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.FeeRecordAllDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * 查询费用记录ValueListHandler
 * author     ：Jason.Zhou 
 * date       : 2006-2-13
 * description:
 * @author 250713z
 *
 */
public class FeeRecordAllListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private FeeRecordAllDAO dao = null;
	private static final Class clazz = FeeRecordAllListHandler.class;
	final private String tableName = " T_ACCOUNTITEM item, t_customer cust";

	public FeeRecordAllListHandler(){
		dao=new FeeRecordAllDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO)dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			executeSearch(dao, false, false);
		else
			executeSearch(dao, true, true,true);
	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		StringBuffer begin = new StringBuffer();
		StringBuffer selectStatement = new StringBuffer(128);
		begin.append("select item.* from " + tableName);
		selectStatement.append(" where custid=cust.customerid ");

		//SpareStr1:费用序号
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1()))){
			makeSQLByIntField("item.AI_NO",Integer.valueOf(dto.getSpareStr1()).intValue(),selectStatement);
		}
		
		makeSQLByStringField("item.feetype",dto.getSpareStr18(),selectStatement);
		makeSQLByStringField("cust.customertype",dto.getSpareStr19(),selectStatement);
		
		//SpareStr2:客户证号
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByIntField("item.CUSTID",Integer.valueOf(dto.getSpareStr2()).intValue(),selectStatement);
		//SpareStr3:帐户号
		if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByIntField("item.ACCTID",Integer.valueOf(dto.getSpareStr3()).intValue(),selectStatement);
		//SpareStr5:区域
		if(!(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5()))){
			selectStatement.append(" and item.custid in (select customer.customerid from t_customer customer,t_address address ");
			selectStatement.append(" where customer.addressid=address.addressid and address.districtid in  ");
			selectStatement.append(" ( select id from t_DISTRICTSETTING where status='V' connect by prior id=belongto start with id=");
			selectStatement.append(dto.getSpareStr5() + " ) )");
		}
		//SpareStr6:业务帐户ID
		if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6())))
			makeSQLByIntField("item.SERVICEACCOUNTID",Integer.valueOf(dto.getSpareStr6()).intValue(),selectStatement);
		//SpareStr8:帐目类型
		if(!(dto.getSpareStr8()==null || "".equals(dto.getSpareStr8())))
			makeSQLByStringField("item.ACCTITEMTYPEID",dto.getSpareStr8(),selectStatement);
		//SpareStr9:计费周期
		if(!(dto.getSpareStr9()==null || "".equals(dto.getSpareStr9())))
			makeSQLByIntField("item.BILLINGCYCLEID",Integer.valueOf(dto.getSpareStr9()).intValue(),selectStatement);
		//SpareStr10:关联单据类型
		if(!(dto.getSpareStr10()==null || "".equals(dto.getSpareStr10())))
			makeSQLByStringField("item.REFERTYPE",dto.getSpareStr10(),selectStatement);
		//SpareStr11:关联单据号
		if(!(dto.getSpareStr11()==null || "".equals(dto.getSpareStr11())))
			makeSQLByIntField("item.REFERID",Integer.valueOf(dto.getSpareStr11()).intValue(),selectStatement);
		//SpareStr12:状态
		if(!(dto.getSpareStr12()==null || "".equals(dto.getSpareStr12())))
			makeSQLByStringField("item.STATUS",dto.getSpareStr12(),selectStatement);
		//SpareStr13:强制预存标志
		if(!(dto.getSpareStr13()==null || "".equals(dto.getSpareStr13())))
			makeSQLByStringField("item.FORCEDDEPOSITFLAG",dto.getSpareStr13(),selectStatement);
		//SpareStr14:出帐标志
		if(!(dto.getSpareStr14()==null || "".equals(dto.getSpareStr14())))
			makeSQLByStringField("item.INVOICEDFLAG",dto.getSpareStr14(),selectStatement);
//		//SpareStr15:销帐标志
//		if(!(dto.getSpareStr15()==null || "".equals(dto.getSpareStr15())))
//			makeSQLByStringField("SETOFFFLAG",dto.getSpareStr15(),selectStatement);
		//SpareStr16:创建来源
		if(!(dto.getSpareStr16()==null || "".equals(dto.getSpareStr16())))
			makeSQLByStringField("item.CREATINGMETHOD",dto.getSpareStr16(),selectStatement);
		//SpareStr17:帐单号
		if (!(dto.getSpareStr17()==null || "".equals(dto.getSpareStr17())))
			makeSQLByStringField("item.INVOICENO",dto.getSpareStr17(),selectStatement);
		
		//SpareTime1:创建起始时间
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and item.CREATETIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:创建截止时间
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and item.CREATETIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		if(dto.getSpareStr20() != null)
			selectStatement.append(" and item.custid = cust.customerid and cust.addressid in (select addressid from t_address where detailaddress like '%" + dto.getSpareStr20() +"%')");
		//设置构造取得当前查询总纪录数的sql
		appendOrderByString(selectStatement);
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		setExtraQuerySQL("select sum(item.VALUE) from " + tableName + selectStatement.toString());
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by AI_NO desc");
		else {
			selectStatement.append(" order by " + dto.getOrderField() + orderByAscend);
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
