package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;


public class CustomerCampaignStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private int queryType = 0;
		private static final Class clazz = CustomerCampaignStatListHandler.class;
		final private String tableName = " T_CUSTOMERCAMPAIGN ";

		public CustomerCampaignStatListHandler(int queryType){
			this.dao = new CommonStatDAO();
			this.queryType = queryType;
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
			String CampaignType = null;
			//客户套餐统计
			if(queryType==StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERMEAL)
			{
				//if(dto.getSpareStr6()==null||"".equals(dto.getSpareStr6()))
				//	throw new ListHandlerException("参数错误：分类方式未知！");
				CampaignType = CommonConstDefinition.CAMPAIGNCAMPAIGNTYPE_OPEN;
			}
			//客户促销活动统计
			else if(queryType==StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERCAMPAIGN)
				CampaignType = CommonConstDefinition.CAMPAIGNCAMPAIGNTYPE_NORMAL;
			else
				throw new ListHandlerException("参数错误：统计对象未知！");
			
			StringBuffer sqlShow=new StringBuffer();
			StringBuffer sqlTable=new StringBuffer();
			StringBuffer sqlWhere=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			sqlShow.append("select cucamp.campaignid subname,count(cucamp.campaignid) amount");
			sqlTable.append(" from T_CUSTOMERCAMPAIGN cucamp,T_CAMPAIGN camp");
			sqlWhere.append(" where cucamp.CampaignID = camp.CampaignID");			
			sqlWhere.append(" and camp.CampaignType = '"+CampaignType+"'");
			sqlWhere.append(" and cucamp.CUSTOMERID = cust.CUSTOMERID");
			sqlGroup.append(" group by cucamp.campaignid");
			
			//SpareStr1:统计方式
			//按组织机构统计
			String id = "0";
			if("O".equals(dto.getSpareStr1())){
				//选择了组织机构，要按照组织机构下的分支统计
				if(!(dto.getSpareStr2()==null||"".equals(dto.getSpareStr2())))
				{
					id = dto.getSpareStr2();
				}
				sqlShow.append(orgShowNew);
				sqlTable.append(getOrgTableNew(id));
				sqlWhere.append(orgWhereNew);
				sqlGroup.append(orgGroupNew);
			}
			//按区域统计
			else if("D".equals(dto.getSpareStr1())){
				//选择了区域，要按照区域下的分支统计
				if(!(dto.getSpareStr3()==null||"".equals(dto.getSpareStr3())))
				{
					id = dto.getSpareStr3();
				}
				sqlShow.append(distShowNew);
				sqlTable.append(getDistTableNew(id));
				sqlWhere.append(distWhereNew);
				sqlGroup.append(distGroupNew);
			}
			else 
				throw new ListHandlerException("参数错误：统计方式未知！");
			//SpareStr6:客户类型
			if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4())))
			{
				sqlWhere.append(" and cust.CustomerType = '"+dto.getSpareStr4()+"'");
			}
			//SpareStr5:套餐状态
			if(!(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5())))
			{
				sqlWhere.append(" and cucamp.status = '"+dto.getSpareStr5()+"'");
			}
			//SpareTime1:创建时间1
			if(dto.getSpareTime1()!=null)
			{
				sqlWhere.append(" and cucamp.Dt_Create>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto.getSpareTime2()!=null)
			{
				sqlWhere.append(" and cucamp.Dt_Create<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
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
