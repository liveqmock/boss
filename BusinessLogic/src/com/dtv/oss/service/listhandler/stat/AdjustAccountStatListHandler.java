package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatForBatchDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;


public class AdjustAccountStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatForBatchDAO dao = null;
		private static final Class clazz = CustomerCampaignStatListHandler.class;
		final private String tableName = " T_AccountAdjustment ";

		public AdjustAccountStatListHandler(){
			this.dao = new CommonStatForBatchDAO();
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
		//调帐统计 本统计 统计金额 1-->支付调帐及预存调帐;2-->费用调帐;3-->抵扣调帐
		private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException {
			

		StringBuffer sqlShow = new StringBuffer();
		StringBuffer sqlShow1 = new StringBuffer();
		StringBuffer sqlShow2 = new StringBuffer();
		StringBuffer sqlShow3 = new StringBuffer();
		StringBuffer sqlTable = new StringBuffer();
		StringBuffer sqlTable1 = new StringBuffer();
		StringBuffer sqlTable2 = new StringBuffer();
		StringBuffer sqlTable3 = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		StringBuffer sqlWhere1 = new StringBuffer();
		StringBuffer sqlWhere2 = new StringBuffer();
		StringBuffer sqlWhere3 = new StringBuffer();
		StringBuffer sqlGroup = new StringBuffer();
		
		sqlShow.append("select accadj.ReferRecordType subname,count(distinct accadj.SeqNo) batchnumber");
		sqlShow1.append(",sum(payre.Amount) amount");
		sqlShow2.append(",sum(accit.Value) amount");
		sqlShow3.append(",sum(dedre.Amount) amount");
		
		sqlTable.append(" from T_AccountAdjustment accadj");
		sqlTable1.append(",T_PaymentRecord payre");
		sqlTable2.append(",T_AccountItem accit");
		sqlTable3.append(",T_PrePaymentDeductionRecord dedre");
		sqlWhere.append(" where 1=1");
		sqlWhere1.append(" and accadj.seqno = payre.AdjustmentNo and payre.AdjustmentFlag = 'Y'" +
				" and accadj.ReferRecordType in ('"+CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_C+"','"+
				CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_P+"')");
		sqlWhere2.append(" and accadj.seqno = accit.AdjustmentNo and accit.AdjustmentFlag = 'Y' and accadj.ReferRecordType='"+CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_F+"'");
		sqlWhere3.append(" and accadj.seqno = dedre.AdjustmentNo and dedre.AdjustmentFlag = 'Y' and accadj.ReferRecordType='"+CommonConstDefinition.ADJUSTMENT_REFERRE_CORD_TYPE_D+"'");
		// 本统计只统计状态为有效的调帐记录
		sqlWhere.append(" and accadj.Status = '"+ CommonConstDefinition.ADJUST_STATUS_VALIDATION + "'");
		sqlGroup.append(" group by accadj.ReferRecordType");
		
		// SpareStr1:统计方式
		// 按组织机构统计 该组织结构是受理的组织机构，应从操作员所在的组织入手
		String id = "0";
		if ("O".equals(dto.getSpareStr1())) {
			
			// 选择了组织机构，要按照组织机构下的分支统计
			if (!(dto.getSpareStr2() == null || "".equals(dto.getSpareStr2())))
				id = dto.getSpareStr2();
			// 如果该操作机构不含下属机构，那么就显示其调帐记录关联的操作员
			if (!BusinessUtility.hasSon(id, "org")) {
				sqlShow.append(",ope.OperatorID id,ope.OperatorName name");
				sqlTable.append(",T_Operator ope");
				sqlWhere.append(" and ope.OrgID in (select orgid from t_organization connect by prior orgid=parentorgid start with orgid="+id+")")
								.append(" and ope.OperatorID = accadj.CreateOpID");
				sqlGroup.append(",ope.OperatorID,ope.OperatorName");
				
			} else {
				sqlShow.append(orgShowNewByCsi);
				sqlTable.append(getOrgTableNewByCsi(id));
				sqlWhere.append(" and accadj.CreateOrgID=org.sonid");
				sqlGroup.append(",org.id,org.name");
				
			}

		}
		// 按区域统计
		else if ("D".equals(dto.getSpareStr1())) {
			// 选择了区域，要按照区域下的分支统计
			if (!(dto.getSpareStr3() == null || "".equals(dto.getSpareStr3()))) {
				id = dto.getSpareStr3();
			}
			//如果该区域不含下属区域，那么就显示其调帐记录关联的操作员
			if (!BusinessUtility.hasSon(id, "dist")) {
				sqlShow.append(",ope.OperatorID id,ope.OperatorName name");
				sqlTable.append(",T_ADDRESS addr,T_Operator ope,t_customer cust");
				sqlWhere.append(" and accadj.CreateOpID=ope.OperatorID and cust.addressid=addr.addressid and addr.districtid="+ id )
				.append(" and accadj.CustID = cust.CUSTOMERID");
				sqlGroup.append(",ope.OperatorID,ope.OperatorName");
				
			} else {
				sqlShow.append(distShowNew);
				sqlTable.append(getDistTableNew(id));
				sqlWhere.append(distWhereNew).append(" and accadj.CustID = cust.CUSTOMERID");
				sqlGroup.append(",dist.id,dist.name ");
			}
			
		}

		// SpareStr4:客户类型
		if (!(dto.getSpareStr4() == null || "".equals(dto.getSpareStr4())))
		{
			if(sqlTable.indexOf(",t_customer cust")<1){
				sqlTable.append(",t_customer cust");
				sqlWhere.append(" and accadj.custid=cust.customerid");
			}
			sqlWhere.append(" and cust.CustomerType = '" + dto.getSpareStr4()
					+ "'");
		}
		// SpareStr5:调帐方式
		if (!(dto.getSpareStr5() == null || "".equals(dto.getSpareStr5())))
		{
			sqlWhere.append(" and accadj.AdjustmentType = '"
					+ dto.getSpareStr5() + "'");
		}
		// SpareStr6:帐户类型
		if (!(dto.getSpareStr6() == null || "".equals(dto.getSpareStr6()))) {
			sqlTable.append(",T_Account acc");
			sqlWhere.append(" and accadj.AcctID=acc.AccountID");
			sqlWhere
					.append(" and acc.AccountType='" + dto.getSpareStr6() + "'");
			
		}
		// SpareStr7:调帐原因
		if (!(dto.getSpareStr7() == null || "".equals(dto.getSpareStr7())))
		{
			sqlWhere
					.append(" and accadj.Reason = '" + dto.getSpareStr7() + "'");
		}

		// SpareTime1:创建时间1
		if (dto.getSpareTime1() != null)
		{
			sqlWhere.append(" and accadj.CreateTime>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
		}
		// SpareTime2:创建时间2
		if (dto.getSpareTime2() != null)
		{
			sqlWhere.append(" and accadj.CreateTime<=to_timestamp('").append(
					dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		
	StringBuffer sqlStr = new StringBuffer().append(sqlShow).append(sqlShow1).append(sqlTable).append(sqlTable1).append(sqlWhere).append(sqlWhere1).append(sqlGroup)
	.append(" union all ").append(sqlShow).append(sqlShow2).append(sqlTable).append(sqlTable2).append(sqlWhere).append(sqlWhere2).append(sqlGroup)
	.append(" union all ").append(sqlShow).append(sqlShow3).append(sqlTable).append(sqlTable3).append(sqlWhere).append(sqlWhere3).append(sqlGroup);
	
	//System.out.println("____sql="+sqlStr);
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
