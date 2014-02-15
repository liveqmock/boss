package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * 押金和期权统计
 * 通过UNION来分别统计押金和期权
 * 押金Y，期权Q
 * 2007-1-16 JASON
 * @author 250713z
 *
 */
public class ForceRightFutureRightStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = ForceRightFutureRightStatListHandler.class;

		public ForceRightFutureRightStatListHandler(){
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
			
			//押金
			StringBuffer sqlShow1=new StringBuffer();
			StringBuffer sqlTable1=new StringBuffer();
			StringBuffer sqlWhere1=new StringBuffer();
			//期权
			StringBuffer sqlShow2=new StringBuffer();
			StringBuffer sqlTable2=new StringBuffer();
			StringBuffer sqlWhere2=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			
			sqlShow1.append("select 'Y' subName,sum(ai.Value) amount");
			sqlShow2.append("select 'Q' subName,sum(futureright.Value) amount");
			sqlTable1.append(" from T_AccountItem ai");
			sqlTable2.append(" from T_FutureRight futureright");
			sqlWhere1.append(" where ai.FeeType='" + CommonConstDefinition.BRFEETYPE_FORCEDDEPOSIT +
					"' and ai.Status='" + CommonConstDefinition.AISTATUS_VALIDATE +"'");
			sqlWhere2.append(" where futureright.Status not in('" + CommonConstDefinition.FUTURERIGHT_RESULT_X +"','" +
					CommonConstDefinition.FUTURERIGHT_RESULT_C +"')");
			String id = "0";
			// 没有选择区域,按照大的区域统计;选择了区域，要按照区域下的分支统计
			if (!(dto2.getSpareStr4() == null || "".equals(dto2.getSpareStr4()))) {
				id = dto2.getSpareStr4();
			}
			sqlShow1.append(distShowNew);
			sqlTable1.append(getDistTableNew(id));
			sqlWhere1.append(" and ai.CustID=cust.CUSTOMERID").append(distWhereNew);
			sqlGroup.append(" group by dist.id,dist.name");
			
			sqlShow2.append(distShowNew);
			sqlTable2.append(getDistTableNew(id));
			sqlWhere2.append(" and futureright.CustomerID=cust.CUSTOMERID").append(distWhereNew);
			
	
			
			//SpareStr1:客户类型
			makeSQLByStringField("cust.CustomerType", dto2.getSpareStr1(), sqlWhere1);
			makeSQLByStringField("cust.CustomerType", dto2.getSpareStr1(), sqlWhere2);
			
			//SpareTime1:创建时间1
			if(dto2.getSpareTime1()!=null){
				sqlWhere1.append(" and cust.createtime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhere2.append(" and cust.createtime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto2.getSpareTime2()!=null){
				sqlWhere1.append(" and cust.createtime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhere2.append(" and cust.createtime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlShow1.append(sqlTable1).append(sqlWhere1).append(sqlGroup).append(" union all ")
					.append(sqlShow2).append(sqlTable2).append(sqlWhere2).append(sqlGroup),"D"));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
