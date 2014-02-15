package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.SetOffDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class SetOffListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private SetOffDAO dao = null;
	private static final Class clazz = SetOffListHandler.class;
	final private String tableName = " T_FinanceSetoffMap ";

	public SetOffListHandler(){
		dao=new SetOffDAO();
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
		executeSearch(dao, false, false);

	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		
		StringBuffer selectStatement = new StringBuffer();
		
		//SpareStr4:实收关联类型
		//支付
		if(CommonConstDefinition.SETOFFREFERTYPE_P.equalsIgnoreCase(dto2.getSpareStr4()))
			selectStatement.append(contructSQL("P",dto2));
		//抵扣
		else if(CommonConstDefinition.SETOFFREFERTYPE_D.equalsIgnoreCase(dto2.getSpareStr4()))
			selectStatement.append(contructSQL("D",dto2));
		else if(CommonConstDefinition.SETOFFREFERTYPE_F.equalsIgnoreCase(dto2.getSpareStr4()))
			selectStatement.append(contructSQL("F",dto2));
		else{
			LogUtility.log(clazz, LogLevel.DEBUG,"the dto params is not proper...");
			throw new ListHandlerException("the dto params is not proper...");
		}
			
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(selectStatement);
	}
	
	private String contructSQL(String actionType,CommonQueryConditionDTO dto2){
		StringBuffer sql=new StringBuffer();		
		//构件P
		if("P".equalsIgnoreCase(actionType)){
			sql.append("select acctItemType.ACCTITEMTYPEID as ID,acctItemType.ACCTITEMTYPENAME,TO_CHAR(pay.MOPID) as payType,sum(setoff.value) as amount "
					+ " from T_AcctItemType acctItemType,T_FinanceSetoffMap setoff,T_AccountItem fee,T_PaymentRecord pay ");
			
			//组织
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2()))){
				sql.append(",t_customer cust, " +
						orgTable(dto2.getSpareStr2())+
						"where 1=1 and setoff.custid = cust.customerid " +
						"and cust.orgid = org.orgid and cust.orgid = " + dto2.getSpareStr2()+
						ssqlWhere_z);
			}
			//区域
			else if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3()))){
				sql.append(",t_customer cust, t_address addr, " +
						distTable(dto2.getSpareStr3()));
				sql.append(" where 1=1 and setoff.custid = cust.customerid and cust.ADDRESSID = addr.ADDRESSID " +
						ssqlWhere);
			}
			else
				sql.append(" where 1=1 ");
			
			sql.append(" and setoff.MINUSREFERID=fee.AI_NO and acctItemType.ACCTITEMTYPEID=fee.ACCTITEMTYPEID and " +
					" setoff.PLUSREFERTYPE='P' and setoff.PLUSREFERID=pay.SEQNO ");
			
			addSQLDate(sql,dto2);
			sql.append(" group by acctItemType.acctitemtypeid, acctItemType.ACCTITEMTYPENAME,pay.mopid ");
		}
		//D
		else if("D".equalsIgnoreCase(actionType)){
			sql.append("select acctItemType.ACCTITEMTYPEID as ID,acctItemType.ACCTITEMTYPENAME,pay.PrePaymentType as payType, sum(setoff.value) as amount "
					+ " from T_AcctItemType acctItemType,T_FinanceSetoffMap setoff,T_AccountItem fee,T_PrePaymentDeductionRecord pay ");
			
			//组织
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2()))){
				sql.append(",t_customer cust, "+orgTable(dto2.getSpareStr2())+" where 1=1 and setoff.custid = cust.customerid " +
						"and cust.orgid = org.orgid and cust.orgid = " + dto2.getSpareStr2()+ssqlWhere_z);
			}
			//区域
			else if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3()))){
				sql.append(",t_customer cust, t_address addr, "+distTable(dto2.getSpareStr3())+" where 1=1 ");
				sql.append(" and setoff.custid = cust.customerid and cust.ADDRESSID = addr.ADDRESSID " +
						ssqlWhere);
			}
			else
				sql.append(" where 1=1 ");
			
			sql.append(" and setoff.MINUSREFERID=fee.AI_NO and acctItemType.ACCTITEMTYPEID=fee.ACCTITEMTYPEID and " +
					" setoff.PLUSREFERTYPE='D' and setoff.PLUSREFERID=pay.SEQNO ");
			
			addSQLDate(sql,dto2);
			sql.append(" group by acctItemType.acctitemtypeid, acctItemType.ACCTITEMTYPENAME,pay.PrePaymentType ");
		}
		else if("F".equalsIgnoreCase(actionType)){
			sql.append("select acctItemType.ACCTITEMTYPEID as ID,acctItemType.ACCTITEMTYPENAME,pay.AcctItemTypeID as payType,sum(setoff.value) as amount "
					+ " from T_AcctItemType acctItemType,T_FinanceSetoffMap setoff,T_AccountItem fee,T_AccountItem pay ");
			
			//组织
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2()))){
				sql.append(", t_customer cust, "+orgTable(dto2.getSpareStr2())+" where 1=1 and setoff.custid = cust.customerid " +
						"and cust.orgid = org.orgid and cust.orgid = " + dto2.getSpareStr2()+ssqlWhere_z);
			}
			//区域
			else if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3()))){
				sql.append(", t_customer cust, t_address addr, "+distTable(dto2.getSpareStr3())+" where 1=1 ");
				sql.append(" and setoff.custid = cust.customerid and cust.ADDRESSID = addr.ADDRESSID " +
						ssqlWhere);
			}
			else
				sql.append(" where 1=1 ");
			
			sql.append(" and setoff.MINUSREFERID=fee.AI_NO and acctItemType.ACCTITEMTYPEID=fee.ACCTITEMTYPEID and " +
					" setoff.PLUSREFERTYPE='F' and setoff.PLUSREFERID=pay.AI_NO ");
			
			addSQLDate(sql,dto2);
			sql.append(" group by acctItemType.acctitemtypeid, acctItemType.ACCTITEMTYPENAME,pay.AcctItemTypeID ");
		}
		
		return sql.toString();
	}
	
	private void addSQLDate(StringBuffer str,CommonQueryConditionDTO dto2){
		//SpareTime1:支付时间1
		if(dto.getSpareTime1()!=null)
			str.append(" and setoff.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:支付时间2
		if(dto.getSpareTime2()!=null)
			str.append(" and setoff.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
