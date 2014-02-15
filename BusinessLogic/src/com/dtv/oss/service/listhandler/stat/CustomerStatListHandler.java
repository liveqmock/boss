package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatForMultiDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CustomerStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatForMultiDAO dao = null;
		private static final Class clazz = CustomerStatListHandler.class;
		final private String tableName = " T_Customer ";

		public CustomerStatListHandler(){
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
			StringBuffer sqlShow=new StringBuffer();
			StringBuffer sqlTable=new StringBuffer();
			StringBuffer sqlWhere=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			sqlShow.append("select cust.CustomerType subName, count(cust.CustomerType) amount,cust.Status secSubName");
			sqlTable.append(" from ");
			sqlWhere.append(" where 1=1");
			sqlGroup.append(" group by cust.CustomerType,cust.Status");
			
			
			
			
//			//SpareStr1:统计方式
//			//按组织机构统计
//			if("O".equals(dto.getSpareStr1())){
//				//默认"1"按照大的组织机构统计
//				String parentorgid = "1";
//				//选择了组织机构，要按照组织机构下的分支统计
//				if(!(dto.getSpareStr3()==null||"".equals(dto.getSpareStr3())))
//				{
//					parentorgid = dto.getSpareStr3();
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
				if(!(dto.getSpareStr4()==null||"".equals(dto.getSpareStr4())))
				{
					id = dto.getSpareStr4();
				}
				String sqlDistTableStr = getDistTableNew(id);
				sqlShow.append(distShowNew);
				sqlTable.append(sqlDistTableStr.substring(1,sqlDistTableStr.length()));
				sqlWhere.append(distWhereNew);
				sqlGroup.append(distGroupNew);
				
//}
//			else 
//				throw new ListHandlerException("参数错误：统计方式未知！");
			
		


			
//			//SpareStr2:客户状态
//			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
//				sqlWhere.append(" and cust.Status='" + dto2.getSpareStr2() +"'");	
			//SpareStr5:来源渠道
			if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5())))
			{
				sqlWhere.append(" and cust.OpenSourceType='" + dto2.getSpareStr5() + "'");
			}
			//SpareStr6:来源渠道ID
			if(!(dto2.getSpareStr6()==null || "".equals(dto2.getSpareStr6())))
			{
				sqlWhere.append(" and cust.OpenSourceTypeID=" + dto2.getSpareStr6());
			}
			
			//SpareTime1:创建时间1
			if(dto2.getSpareTime1()!=null)
			{
				sqlWhere.append(" and cust.createtime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto2.getSpareTime2()!=null)
			{
				sqlWhere.append(" and cust.createtime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
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
