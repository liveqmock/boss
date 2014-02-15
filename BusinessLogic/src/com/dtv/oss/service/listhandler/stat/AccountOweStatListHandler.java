package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * 帐户欠费统计
 * 客户数 CC、帐户数 AC、未付帐单数 UI、欠费金额 OA
 * 2007-1-12 Jason
 * @author 250713z
 *
 */
public class AccountOweStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = AccountOweStatListHandler.class;

		public AccountOweStatListHandler(){
			dao=new CommonStatDAO();
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
			
			StringBuffer sqlShow1=new StringBuffer();
			StringBuffer sqlShow2=new StringBuffer();
			StringBuffer sqlShow3=new StringBuffer();
			StringBuffer sqlShow4=new StringBuffer();
			StringBuffer sqlTable=new StringBuffer();
			StringBuffer sqlWhere=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			sqlShow1.append("select 'CC' subName,count(distinct cust.CUSTOMERID) amount");
			sqlShow2.append("select 'AC' subName,count(distinct acct.AccountID) amount");
			sqlShow3.append("select 'UI' subName,count(invoice.InvoiceNo) amount");
			sqlShow4.append("select 'OA' subName,sum(invoice.Amount) amount");
			
			sqlTable.append(" from t_account acct,T_Invoice invoice");
			sqlWhere.append(" where acct.accountid=invoice.AcctID and invoice.Status in ('" + 
					CommonConstDefinition.INVOICE_STATUS_WAIT + "','"+ CommonConstDefinition.INVOICE_STATUS_OWE +"')");
			//sqlWhere.append(" and acct.status !='C'");
			String id = "0";
			// 没有选择区域,按照大的区域统计;选择了区域，要按照区域下的分支统计
			if (!(dto2.getSpareStr4() == null || "".equals(dto2.getSpareStr4()))) {
				id = dto2.getSpareStr4();
			}
			sqlShow1.append(distShowNew);
			sqlShow2.append(distShowNew);
			sqlShow3.append(distShowNew);
			sqlShow4.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(" and acct.customerid=cust.CUSTOMERID").append(distWhereNew);
			sqlGroup.append(" group by dist.id,dist.name");
			
			//SpareStr1:客户类型
			makeSQLByStringField("cust.CustomerType", dto2.getSpareStr1(), sqlWhere);
			//SpareStr2:帐户类型
			makeSQLByStringField("acct.AccountType", dto2.getSpareStr2(), sqlWhere);
			//SpareStr3:付费方式
			if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3()))){
				sqlWhere.append(" and acct.MOPID=" + dto2.getSpareStr3());
			}
			
			//SpareStr5:帐户状态
			makeSQLByStringField("acct.Status", dto2.getSpareStr5(), sqlWhere);
			//SpareStr6:串配状态
			makeSQLByStringField("acct.BankAccountStatus", dto2.getSpareStr6(), sqlWhere);
			
			//SpareTime1:创建时间1
			if(dto2.getSpareTime1()!=null){
				sqlWhere.append(" and acct.createtime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto2.getSpareTime2()!=null){
				sqlWhere.append(" and acct.createtime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			
			//SpareTime3:欠费时间1
			if(dto2.getSpareTime3()!=null){
				sqlWhere.append(" and invoice.CreateDate>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime4:欠费时间2
			if(dto2.getSpareTime4()!=null){
				sqlWhere.append(" and invoice.CreateDate<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			
			sqlShow1.append(sqlTable).append(sqlWhere).append(sqlGroup);
			sqlShow2.append(sqlTable).append(sqlWhere).append(sqlGroup);
			sqlShow3.append(sqlTable).append(sqlWhere).append(sqlGroup);
			sqlShow4.append(sqlTable).append(sqlWhere).append(sqlGroup);
			
			
			sqlShow1.append(" union all ").append(sqlShow2).append(" union all ").append(sqlShow3).append(" union all ").append(sqlShow4);
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderAndShowAllForStat(sqlShow1,"D",id));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
