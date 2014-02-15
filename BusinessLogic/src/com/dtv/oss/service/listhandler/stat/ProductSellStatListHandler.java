package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;


public class ProductSellStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = CustomerCampaignStatListHandler.class;
		final private String tableName = " T_CSICustProductInfo ";

		public ProductSellStatListHandler(){
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
		
		sqlShow.append("select csipro.ProductID subname,count(csipro.ProductID) amount");
		sqlTable.append(" from T_CSICustProductInfo csipro,T_CustServiceInteraction csi");
		sqlWhere.append(" where csipro.CSIID = csi.ID and csi.Status='"+
				CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS+"' and csi.Type in('"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP+"','"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_PN+"','"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GS+"','"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB+"','"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS+"','"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_UO+"','"+
				CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BU+"')"
				);
		sqlGroup.append(" group by csipro.ProductID");
		
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
			if(!(dto.getSpareStr2()==null||"".equals(dto.getSpareStr2())))
			{
				id = dto.getSpareStr2();
			}
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(distWhereNew).append(" and csi.CustomerID = cust.CUSTOMERID");
			sqlGroup.append(distGroupNew);
		}

		// SpareStr3:客户类型
		if (!(dto.getSpareStr3() == null || "".equals(dto.getSpareStr3())))
		{
			if(sqlTable.indexOf(",t_customer cust")<1){
				sqlTable.append(",t_customer cust");
				sqlWhere.append(" and csi.CustomerID = cust.CUSTOMERID");
			}
			sqlWhere.append(" and cust.CustomerType = '" + dto.getSpareStr3()+ "'");
		}
		// SpareStr5:业务类型
		if (!(dto.getSpareStr5() == null || "".equals(dto.getSpareStr5())))
		{
			sqlTable.append(",T_ProductToService protoser");
			sqlWhere.append(" and csipro.ProductID=protoser.ProductID");
			sqlWhere.append(" and protoser.ServiceID=" + dto.getSpareStr5());
		}
		//产品类型
		if (!(dto.getSpareStr6() == null || "".equals(dto.getSpareStr6()))) {
			sqlTable.append(",T_Product pro");
			sqlWhere.append(" and csipro.ProductID=pro.ProductID");
			sqlWhere.append(" and pro.ProductClass='" + dto.getSpareStr6() + "'");
		}
		
		//产品状态
		if (!(dto.getSpareStr7() == null || "".equals(dto.getSpareStr7()))) {
			if(sqlTable.indexOf(",T_Product pro") == -1)
			sqlTable.append(",T_Product pro");
			sqlWhere.append(" and csipro.ProductID = pro.ProductID");
			sqlWhere.append(" and pro.Status='" + dto.getSpareStr7() + "'");
		}

		// SpareTime1:销售时间1
		if (dto.getSpareTime1() != null)
		{
			sqlTable.append(",T_CSIProcessLog csilog");
			sqlWhere.append(" and csi.ID=csilog.CSIID and csilog.Action='"+CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS+"'");
			sqlWhere.append(" and csilog.ActionTime>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// SpareTime2:销售时间2
		if (dto.getSpareTime2() != null)
		{
			if(sqlTable.indexOf(",T_CSIProcessLog csilog")==-1)
			{
				sqlTable.append(",T_CSIProcessLog csilog");
				sqlWhere.append(" and csi.ID=csilog.CSIID and csilog.Action='"+CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS+"'");
			}
			sqlWhere.append(" and csilog.ActionTime<=to_timestamp('").append(
					dto.getSpareTime2().toString()).append(
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
