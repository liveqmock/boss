package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;


public class CallBackStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = CustomerCampaignStatListHandler.class;
		final private String tableName = " T_CustServiceInteraction ";

		public CallBackStatListHandler(){
			this.dao = new CommonStatDAO();
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
		//产品销售统计
		private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException {
			
		StringBuffer sqlShow = new StringBuffer();
		StringBuffer sqlTable = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		StringBuffer sqlGroup = new StringBuffer();
		
		sqlShow.append("select csi.Status subname,count(csi.Status) amount");
		sqlTable.append(" from T_CustServiceInteraction csi");
		sqlWhere.append(" where (csi.Type='"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB+"' or csi.Type ='"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS+"')"
				);
		sqlGroup.append(" group by csi.Status");
		
		//SpareStr1:统计方式
		//按组织机构统计
		String id = "0";
		if("O".equals(dto.getSpareStr1())){
			//选择了组织机构，要按照组织机构下的分支统计
			if(!(dto.getSpareStr4()==null||"".equals(dto.getSpareStr4())))
			{
				id = dto.getSpareStr4();
			}
			sqlShow.append(orgShowNewByCsi);
			sqlTable.append(getOrgTableNewByCsi(id));
			sqlWhere.append(orgWhereNewByCsi);
			sqlGroup.append(orgGroupNewByCsi);
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
			sqlWhere.append(distWhereNew).append(" and csi.CustomerID = cust.CUSTOMERID");
			sqlGroup.append(distGroupNew);
		}

		// SpareStr2:回访标志
		if (!(dto.getSpareStr2() == null || "".equals(dto.getSpareStr2())))
		{
			if(CommonConstDefinition.CPCALLBACKFLAG_NO.equals(dto.getSpareStr2()))
				sqlWhere.append(" and (csi.CallBackFlag = '" + dto.getSpareStr2()+ "' or csi.CallBackFlag is null)");
			else
				sqlWhere.append(" and csi.CallBackFlag = '" + dto.getSpareStr2()+ "'");
		}
		// SpareStr5:安装方式
		if (!(dto.getSpareStr5() == null || "".equals(dto.getSpareStr5())))
		{
			sqlWhere.append(" and csi.InstallationType='" + dto.getSpareStr5()+"'");
		}
		
		// SpareTime1:创建时间1
		if (dto.getSpareTime1() != null)
		{
			sqlWhere.append(" and csi.CreateTime>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// SpareTime2:创建时间2
		if (dto.getSpareTime2() != null)
		{
			sqlWhere.append(" and csi.CreateTime<=to_timestamp('").append(
					dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//SpareTime1:回访时间1
		if (dto.getSpareTime3() != null)
		{
			sqlWhere.append(" and csi.CallBackDate>=to_timestamp('").append(
					dto.getSpareTime3().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// SpareTime2:回访时间2
		if (dto.getSpareTime4() != null)
		{
			sqlWhere.append(" and csi.CallBackDate<=to_timestamp('").append(
					dto.getSpareTime4().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
		

		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,dto.getSpareStr1()));
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
