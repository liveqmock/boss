package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.AdjustAccountDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * 调帐记录ListHndler
 * author     ：Jason.Zhou 
 * date       : 2006-2-9
 * description:
 * @author 250713z
 *
 */
public class AdjustAccountListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private AdjustAccountDAO dao = null;
	private static final Class clazz = AdjustAccountListHandler.class;
	final private String tableName = " T_ACCOUNTADJUSTMENT ";

	public AdjustAccountListHandler(){
		this.dao=new AdjustAccountDAO();
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
		StringBuffer selectStatement = new StringBuffer();
        // SpareStr1:序号
		if(!(dto2.getSpareStr1()==null || "".equals(dto2.getSpareStr1()))){
			contructUnionSQL(selectStatement,dto2);
			//设置构造取得当前查询总纪录数的sql
			setRecordCountQueryTable(" ( " + selectStatement.toString() + " )");
			setRecordCountSuffixBuffer(new StringBuffer(" "));
			
			//设置当前数据查询sql
			setRecordDataQueryBuffer(selectStatement);
			return;
		}
		
		if(!(dto2.getSpareStr8()==null || "".equals(dto2.getSpareStr8())))
			contructPDFSQL(selectStatement,dto2);
		else 
			contructUnionSQL(selectStatement,dto2);

		StringBuffer orderSelectStatement =new StringBuffer();
		appendOrderByString(orderSelectStatement,selectStatement);	

		setRecordCountQueryTable(" ( " + orderSelectStatement.toString() + " ) ");
		setRecordCountSuffixBuffer(new StringBuffer(" "));
		setExtraQuerySQL("select sum(amount) from ( "  + orderSelectStatement.toString() + " )");
		
		//设置当前数据查询sql
		setRecordDataQueryBuffer(orderSelectStatement);
	}
	
	private void contructPDFSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2) {
		//支付关联表为支付表ADJUST_REFERCODETYPE_D
		if(CommonConstDefinition.ADJUST_REFERCODETYPE_P.equals(dto2.getSpareStr8())||
				CommonConstDefinition.ADJUST_REFERCODETYPE_C.equals(dto2.getSpareStr8())){
			contructPSQL(selectStatement,dto2);
		}
		//支付关联表为预存抵扣表
		else if(CommonConstDefinition.ADJUST_REFERCODETYPE_D.equals(dto2.getSpareStr8())){
			contructDSQL(selectStatement,dto2);
		}
		//支付关联表为费用表
		else if(CommonConstDefinition.ADJUST_REFERCODETYPE_F.equals(dto2.getSpareStr8())){
			contructFSQL(selectStatement,dto2);
		}
	}
	
	private void contructUnionSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2){
		contructPSQL(selectStatement,dto2);
		selectStatement.append(" union ");
		
		contructDSQL(selectStatement,dto2);
		selectStatement.append(" union ");
		
		contructFSQL(selectStatement,dto2);
	}
	
	private void contructPSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2){
		selectStatement.append(" select acc.*,p.AMOUNT as amount,p.CREATETIME as originalTime " +
				" from T_AccountAdjustment acc,T_PaymentRecord p where acc.ReferRecordType in ('P','C') " +
				" and acc.REFERRECORDID=p.SEQNO ");
		appendCondition(selectStatement,dto2);
	}
	
	private void contructDSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2){
		selectStatement.append(" select acc.*,d.AMOUNT as amount,d.CREATETIME as originalTime " +
				" from T_AccountAdjustment acc,T_PrePaymentDeductionRecord d where acc.ReferRecordType='D' " +
				" and acc.REFERRECORDID=d.SEQNO ");
		appendCondition(selectStatement,dto2);
	}
	
	private void contructFSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2){
		selectStatement.append(" select acc.*,f.Value as amount,f.CREATETIME as originalTime " +
				" from T_AccountAdjustment acc,T_AccountItem f where acc.ReferRecordType='F' " +
				" and acc.REFERRECORDID=f.AI_NO ");
		appendCondition(selectStatement,dto2);
	}
	
    //	select * from （ selectStatement ） order by的方式来排序
	private void appendOrderByString(StringBuffer orderSelectStatement, StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		
		orderSelectStatement.append("select * from (");
		orderSelectStatement.append(selectStatement);
		orderSelectStatement.append(")");

		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals("")){
			orderSelectStatement.append(" order by SEQNO desc");
		}
		else {
			orderSelectStatement.append(" order by " + dto.getOrderField() + orderByAscend);
		}
		
		orderByAscend = null;
	}
	
	private void appendCondition(StringBuffer strBuff,CommonQueryConditionDTO dto2){
		
		//SpareStr1:调帐序号,如果调帐序号不为空，则只查找一条记录
		if(!(dto2.getSpareStr1()==null || "".equals(dto2.getSpareStr1()))){
			makeSQLByIntField("acc.SEQNO",Integer.valueOf(dto2.getSpareStr1()).intValue(),strBuff);
			return;
		}
			
		//SpareStr2:客户证号
		if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
			makeSQLByIntField("acc.CUSTID",Integer.valueOf(dto2.getSpareStr2()).intValue(),strBuff);
		//SpareStr3:帐户号
		if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3())))
			makeSQLByIntField("acc.ACCTID",Integer.valueOf(dto2.getSpareStr3()).intValue(),strBuff);
		//SpareStr4:调帐原因
		makeSQLByStringField("acc.REASON",dto2.getSpareStr4(),strBuff);
		//SpareStr5:调帐类型
		makeSQLByStringField("acc.ADJUSTMENTTYPE",dto2.getSpareStr5(),strBuff);
		
		//SpareStr6:所属组织
		if(!(dto2.getSpareStr6()==null || "".equals(dto2.getSpareStr6()))){
			strBuff.append(" and acc.custid in (select customer.customerid from t_customer customer ");
			strBuff.append(" where customer.orgid in (select orgid from t_organization ");
			strBuff.append(" where status='V' and hascustomerflag='Y' connect ");
			strBuff.append(" by prior orgid=parentorgid start with orgid=" + dto2.getSpareStr6() + " ) )");
		}
		//SpareStr7:区域
		if(!(dto2.getSpareStr7()==null || "".equals(dto2.getSpareStr7()))){
			strBuff.append(" and acc.custid in (select customer.customerid from t_customer customer,t_address address ");
			strBuff.append(" where customer.addressid=address.addressid and address.districtid in  ");
			strBuff.append(" ( select id from t_DISTRICTSETTING where status='V' connect by prior id=belongto start with id=");
			strBuff.append(dto2.getSpareStr7() + " ) )");
		}

		//SpareStr8:调帐对象
		makeSQLByStringField("acc.REFERRECORDTYPE",dto2.getSpareStr8(),strBuff);
		//SpareStr9:调帐对象ID
		if(!(dto2.getSpareStr9()==null || "".equals(dto2.getSpareStr9())))
			makeSQLByIntField("acc.REFERRECORDID",Integer.valueOf(dto2.getSpareStr9()).intValue(),strBuff);
		//SpareStr10:状态
		makeSQLByStringField("acc.STATUS",dto2.getSpareStr10(),strBuff);
		//SpareStr11:预存调整标记
		makeSQLByStringField("acc.ADJUSTMENTPREPAYMENTFLAG",dto2.getSpareStr11(),strBuff);
		//SpareTime1:创建起始时间
		if(dto2.getSpareTime1()!=null)
			strBuff.append(" and acc.CREATETIME>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:创建截止时间
		if(dto2.getSpareTime2()!=null)
			strBuff.append(" and acc.CREATETIME<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareStr12:操作员ID
		if(dto2.getSpareStr12()!=null)
			makeSQLByIntField("acc.CREATEOPID",Integer.parseInt(dto2.getSpareStr12()),strBuff);
		//SpareStr13:调账方向
		if(dto2.getSpareStr13()!=null)
			makeSQLByStringField("acc.ADJUSTMENTDIRECTION",dto2.getSpareStr13(),strBuff);
		if(dto2.getSpareStr14()!=null)
			strBuff.append(" and acc.custid in (select customerid from t_customer where addressid in (select addressid from t_address where detailaddress like '%"+dto2.getSpareStr14()+"%'))");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
