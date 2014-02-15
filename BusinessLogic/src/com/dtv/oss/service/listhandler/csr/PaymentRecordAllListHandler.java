package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.PaymentRecordDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * 支付费用记录ValueListHandler
 * author     ：Jason.Zhou 
 * date       : 2006-2-13
 * description:
 * @author 250713z
 *
 */
public class PaymentRecordAllListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private PaymentRecordDAO dao = null;
	private static final Class clazz = PaymentRecordAllListHandler.class;
	final private String tableName = " T_PaymentRecord ";

	public PaymentRecordAllListHandler(){
		dao=new PaymentRecordDAO();
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
		begin.append("select * from " + tableName);
		selectStatement.append(" where 1=1 ");

		//SpareStr1:支付序号
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1()))){
			makeSQLByIntField("SEQNO",Integer.valueOf(dto.getSpareStr1()).intValue(),selectStatement);
		}
		
		//SpareStr2:客户证号
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByIntField("CUSTID",Integer.valueOf(dto.getSpareStr2()).intValue(),selectStatement);
		//SpareStr3:帐户号
		if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByIntField("ACCTID",Integer.valueOf(dto.getSpareStr3()).intValue(),selectStatement);
		//SpareStr4:组织
		if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4()))){
			selectStatement.append(" and custid in (select customer.customerid from t_customer customer ");
			selectStatement.append(" where customer.orgid in (select orgid from t_organization ");
			selectStatement.append(" where status='V' and hascustomerflag='Y' connect ");
			selectStatement.append(" by prior orgid=parentorgid start with orgid=" + dto.getSpareStr4() + " ) )");
		}
		//SpareStr5:区域
		if(!(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5()))){
			selectStatement.append(" and custid in (select customer.customerid from t_customer customer,t_address address ");
			selectStatement.append(" where customer.addressid=address.addressid and address.districtid in  ");
			selectStatement.append(" ( select id from t_DISTRICTSETTING where status='V' connect by prior id=belongto start with id=");
			selectStatement.append(dto.getSpareStr5() + " ) )");
		}
		//SpareStr6:付费方式
		if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6())))
			makeSQLByIntField("MOPID",Integer.valueOf(dto.getSpareStr6()).intValue(),selectStatement);
		//SpareStr7:支付类别
		if(!(dto.getSpareStr7()==null || "".equals(dto.getSpareStr7())))
			makeSQLByStringField("PAYTYPE",dto.getSpareStr7(),selectStatement);
		//SpareStr8:代用券类型
		if(!(dto.getSpareStr8()==null || "".equals(dto.getSpareStr8())))
			makeSQLByStringField("TICKETTYPE",dto.getSpareStr8(),selectStatement);
		//SpareStr9:券号
		if(!(dto.getSpareStr9()==null || "".equals(dto.getSpareStr9())))
			makeSQLByStringField("TICKETNO",dto.getSpareStr9(),selectStatement);
		//SpareStr10:关联单据类型
		if(!(dto.getSpareStr10()==null || "".equals(dto.getSpareStr10())))
			makeSQLByStringField("REFERTYPE",dto.getSpareStr10(),selectStatement);
		//SpareStr11:关联单据号
		if(!(dto.getSpareStr11()==null || "".equals(dto.getSpareStr11())))
			makeSQLByIntField("REFERID",Integer.valueOf(dto.getSpareStr11()).intValue(),selectStatement);
		//SpareStr12:来源
		if(!(dto.getSpareStr12()==null || "".equals(dto.getSpareStr12())))
			makeSQLByStringField("SOURCETYPE",dto.getSpareStr12(),selectStatement);
		//SpareStr13:来源单据号
		if(!(dto.getSpareStr13()==null || "".equals(dto.getSpareStr13())))
			makeSQLByIntField("SOURCERECORDID",Integer.valueOf(dto.getSpareStr13()).intValue(),selectStatement);
		//SpareStr14:状态
		if(!(dto.getSpareStr14()==null || "".equals(dto.getSpareStr14())))
			makeSQLByStringField("STATUS",dto.getSpareStr14(),selectStatement);
		//SpareStr15:出帐标志
		if(!(dto.getSpareStr15()==null || "".equals(dto.getSpareStr15())))
			makeSQLByStringField("INVOICEDFLAG",dto.getSpareStr15(),selectStatement);
        //	SpareStr16:帐单号
		if(!(dto.getSpareStr16()==null || "".equals(dto.getSpareStr16())))
			makeSQLByStringField("INVOICENO",dto.getSpareStr16(),selectStatement);
		// SpareStr20:客户地址
		if(!(dto.getSpareStr20() == null || "".equals(dto.getSpareStr20())))
			selectStatement.append(" and custid in (select customerid from t_customer where addressid in(select addressid from t_address where detailaddress like '%" + dto.getSpareStr20()+"%'))");
/*	yangchen 2007/2/11 remove	
		//SpareTime1:支付时间1
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and PAYMENTTIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:支付时间2
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and PAYMENTTIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
*/	
/* yangcheng  2007/2/11 add start */
		//BeginTime:创建时间(起始)
		if(dto.getBeginTime()!=null)
			selectStatement.append(" and CreateTime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//EndTime:创建时间(结束)
		if(dto.getEndTime()!=null)
			selectStatement.append(" and CreateTime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
        //	SpareStr17:创建来源
		if(!(dto.getSpareStr17()==null || "".equals(dto.getSpareStr17())))
			makeSQLByStringField("CreatingMethod",dto.getSpareStr17(),selectStatement);
        //	SpareStr18:预存款类型
		if(!(dto.getSpareStr18()==null || "".equals(dto.getSpareStr18())))
			makeSQLByStringField("PrePaymentType",dto.getSpareStr18(),selectStatement);
		//SpareStr19:受款人operatorid
		if(!(dto.getSpareStr19()==null || "".equals(dto.getSpareStr19())))
			makeSQLByStringField("OpID",dto.getSpareStr19(),selectStatement);
/* yangcheng  2007/2/11 add end */
		//设置构造取得当前查询总纪录数的sql
		appendOrderByString(selectStatement);
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		setExtraQuerySQL("select sum(amount) from " + tableName + selectStatement.toString());
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by SEQNO desc");
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
