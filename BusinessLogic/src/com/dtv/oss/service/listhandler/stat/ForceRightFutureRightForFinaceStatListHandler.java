package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatForMultiDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * 帐务统计-押金和期权统计
 * 通过UNION来分别统计押金和期权
 * 押金Y，期权Q
 * 2007-1-16 JASON
 * @author 250713z
 *
 */
public class ForceRightFutureRightForFinaceStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatForMultiDAO dao = null;
		private static final Class clazz = ForceRightFutureRightForFinaceStatListHandler.class;

		public ForceRightFutureRightForFinaceStatListHandler(){
			dao=new CommonStatForMultiDAO();
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
			
			//押金
			StringBuffer sqlShow1=new StringBuffer();
			StringBuffer sqlTable1=new StringBuffer();
			StringBuffer sqlWhere1=new StringBuffer();
			StringBuffer sqlGroup1=new StringBuffer();
			//期权
			StringBuffer sqlShow2=new StringBuffer();
			StringBuffer sqlTable2=new StringBuffer();
			StringBuffer sqlWhere2=new StringBuffer();
			StringBuffer sqlGroup2=new StringBuffer();
			
			sqlShow1.append("select 'Y' subName,sum(ai.Value) amount,ai.Status secSubName");
			sqlShow2.append("select 'Q' subName,sum(futureright.Value) amount,futureright.Status secSubName");
			sqlTable1.append(" from T_AccountItem ai");
			sqlTable2.append(" from T_FutureRight futureright");
			sqlWhere1.append(" where ai.FeeType='" + CommonConstDefinition.BRFEETYPE_FORCEDDEPOSIT +"'");
			sqlGroup1.append(" group by ai.Status");
			sqlGroup2.append(" group by futureright.Status");
			
			String id = "0";
			// 没有选择区域,按照大的区域统计;选择了区域，要按照区域下的分支统计
			if (!(dto2.getSpareStr2() == null || "".equals(dto2.getSpareStr2()))) {
				id = dto2.getSpareStr2();
			}
			sqlShow1.append(distShowNew);
			sqlTable1.append(getDistTableNew(id));
			sqlWhere1.append(" and ai.CustID=cust.CUSTOMERID").append(distWhereNew);
			sqlGroup1.append(",dist.id,dist.name");
			
			sqlShow2.append(distShowNew);
			sqlTable2.append(getDistTableNew(id));
			sqlWhere2.append(" where futureright.CustomerID=cust.CUSTOMERID").append(distWhereNew);
			sqlGroup2.append(",dist.id,dist.name");
		
			//SpareStr1:客户类型
			makeSQLByStringField("cust.CustomerType", dto2.getSpareStr1(), sqlWhere1);
			makeSQLByStringField("cust.CustomerType", dto2.getSpareStr1(), sqlWhere2);
			
			
			//SpareTime1:创建时间1
			if(dto2.getSpareTime1()!=null){
				sqlWhere1.append(" and ai.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhere2.append(" and futureright.CreateDate>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto2.getSpareTime2()!=null){
				sqlWhere1.append(" and ai.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhere2.append(" and futureright.CreateDate<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}

			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlShow1.append(sqlTable1).append(sqlWhere1).append(sqlGroup1).append(" union all ")
					.append(sqlShow2).append(sqlTable2).append(sqlWhere2).append(sqlGroup2),"D"));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
