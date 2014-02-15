package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CustomerSAStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = CustomerSAStatListHandler.class;
		final private String tableName = " T_ServiceAccount ";

		public CustomerSAStatListHandler(){
			this.dao=new CommonStatDAO();
		}
		
		public void setCriteria(Object dto) throws ListHandlerException {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
			if (dto instanceof CommonQueryConditionDTO)
				this.dto = (CommonQueryConditionDTO)dto;
			else {
				LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
				throw new ListHandlerException("the dto type is not proper...");
			}
			//构造查询字符串
			constructSelectQueryString(this.dto);
			
			//执行数据查询
			executeSearch(this.dao, false, false);

		}
		
		private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException {
			StringBuffer sqlShow=new StringBuffer();
			StringBuffer sqlTable=new StringBuffer();
			StringBuffer sqlWhere=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			
			sqlShow.append("select seracc.Status subName,count(seracc.Status) amount");
			sqlTable.append(" from T_ServiceAccount seracc");
			sqlWhere.append(" where 1=1");
			sqlWhere.append(" and seracc.CUSTOMERID = cust.CUSTOMERID");
			sqlGroup.append(" group by seracc.Status");
			
			

//			//SpareStr1:统计方式
//			//按组织机构统计
//			if("O".equals(dto.getSpareStr1())){
//				//默认"1"按照大的组织机构统计
//				String parentorgid = "1";
//				//选择了组织机构，要按照组织机构下的分支统计
//				if(!(dto.getSpareStr2()==null||"".equals(dto.getSpareStr2())))
//				{
//					parentorgid = dto.getSpareStr2();
//				}
//				sqlShow.append(orgShow);
//				sqlTable.append(orgTable);
//				sqlWhere.append(orgWhere).append(parentorgid);
//				sqlGroup.append(orgGroup);
//			}
//			//按区域统计
//			else if("D".equals(dto.getSpareStr1())){
				//默认"1"按照大的区域统计,选择了上海市也按照大的区域统计
				String id = "0";
				//选择了区域，要按照区域下的分支统计
				if(!(dto.getSpareStr3()==null||"".equals(dto.getSpareStr3())))
				{
					id = dto.getSpareStr3();
				}
				sqlShow.append(distShowNew);
				sqlTable.append(getDistTableNew(id));
				sqlWhere.append(distWhereNew);
				sqlGroup.append(distGroupNew);
				
//			}
//			else 
//				throw new ListHandlerException("参数错误：统计方式未知！");
			
			//SpareStr4:客户类型
			if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4())))
			{
				sqlWhere.append(" and cust.CustomerType = '"+dto.getSpareStr4()+"'");
			}
			//SpareStr5:订户类型
			if(!(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5())))
			{
				sqlWhere.append(" and seracc.User_Type = '"+dto.getSpareStr5()+"'");
			}
//			//SpareStr6:订户状态
//			if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6())))
//				sqlWhere.append(" and seracc.Status = '"+dto.getSpareStr6()+"'");
			//SpareTime1:创建时间1
			if(dto.getSpareTime1()!=null)
			{
				sqlWhere.append(" and seracc.CreateTime>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto.getSpareTime2()!=null)
			{
				sqlWhere.append(" and seracc.CreateTime<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
			//设置构造取得当前查询总纪录数的sql
			setRecordCountQueryTable(tableName);
			//设置当前数据查询sql
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,"D"));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
