package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class AccountInfoStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = AccountInfoStatListHandler.class;

		public AccountInfoStatListHandler(){
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
			
			StringBuffer sqlShow = new StringBuffer();
			StringBuffer sqlTable = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			StringBuffer sqlGroup = new StringBuffer();
			
			sqlShow.append("select acct.status subName,count(acct.accountid) amount");
			sqlTable.append(" from t_account acct");
			sqlGroup.append(" group by acct.status");
			
			String id = "0";
			// 没有选择区域,按照大的区域统计;选择了区域，要按照区域下的分支统计
			if (!(dto.getSpareStr4() == null || "".equals(dto.getSpareStr4()))) {
				id = dto.getSpareStr4();
			}
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(" where acct.customerid=cust.CUSTOMERID").append(distWhereNew);
			sqlGroup.append(distGroupNew);
			//SpareStr1:客户类型
			makeSQLByStringField("cust.CustomerType", dto2.getSpareStr1(), sqlWhere);
			//SpareStr2:帐户类型
			makeSQLByStringField("acct.AccountType", dto2.getSpareStr2(), sqlWhere);
			//SpareStr3:付费方式
			if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3()))){
				sqlWhere.append(" and acct.MOPID=" + dto2.getSpareStr3());
			}
			
			//SpareTime1:创建时间1
			if(dto2.getSpareTime1()!=null){
				sqlWhere.append(" and acct.createtime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto2.getSpareTime2()!=null){
				sqlWhere.append(" and acct.createtime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup),"D"));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
