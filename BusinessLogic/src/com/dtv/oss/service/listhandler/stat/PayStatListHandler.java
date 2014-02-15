package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatForBatchDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;


public class PayStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatForBatchDAO dao = null;
		private static final Class clazz = PayStatListHandler.class;
		final private String tableName = " T_PaymentRecord ";

		public PayStatListHandler(){
			dao=new CommonStatForBatchDAO();
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
		
		
		/**
		 * 支付统计/预存统计
		 * date       : 2007-1-15
		 * description:
		 * SpareStr1:客户类型
		 * SpareStr2:所在区域
		 * SpareStr3:帐户类型
		 * SpareStr4:付费类型
		 * SpareTime1:创建起始时间
		 * SpareTime2:创建结束时间
		 * @author 
		 *
		 */
		private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException {

			StringBuffer sqlShow = new StringBuffer();
			StringBuffer sqlTable = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			StringBuffer sqlGroup = new StringBuffer();
			
	
			sqlShow.append("select payre.MOPID subName, sum(payre.Amount) amount,count(payre.SeqNo) batchnumber");
			sqlTable.append(" from T_PaymentRecord payre");
			sqlWhere.append(" where payre.CustID = cust.CUSTOMERID");
			sqlGroup.append(" group by payre.MOPID");
			//预存统计 包括（预存和预存退费）
			if("prepay".equals(dto.getSpareStr5()))
			{
				sqlWhere.append(" and (payre.PayType='"+CommonConstDefinition.PAYMENTRECORD_TYPE_PRESAVE+"' or " +
						"payre.PayType='"+CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_RR+"')");
			}
			//支付统计 包括（支付费用 支付和受理单退费）
			else
			{
			//本统计包括由于调帐和其它帐务处理人工产生的支付记录
			sqlWhere.append(" and payre.PayType in ('"+CommonConstDefinition.PAYMENTRECORD_TYPE_ACCEPT_CASE+"','"+
					CommonConstDefinition.PAYMENTRECORD_TYPE_BILLING+
					"','"+CommonConstDefinition.PAYMENTRECORD_TYPE_RETURN_FEE+
					"')");
			}
			//本统计只统计状态为非异常的费用
			sqlWhere.append(" and payre.Status not in ('"+CommonConstDefinition.AISTATUS_INVALIDATE+"','"+
					CommonConstDefinition.AISTATUS_LOCK+"','"+CommonConstDefinition.AISTATUS_POTENTIAL+"')");
			
			String id = "0";
			// 没有选择区域,按照大的区域统计;选择了区域，要按照区域下的分支统计
			if (!(dto.getSpareStr2() == null || "".equals(dto.getSpareStr2()))) {
				id = dto.getSpareStr2();
			}
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(distWhereNew);
			sqlGroup.append(distGroupNew);
			
			if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			{
				sqlWhere.append(" and cust.CustomerType = '"+dto.getSpareStr1()+"'");
			}
		
			if (!(dto.getSpareStr3() == null || "".equals(dto.getSpareStr3())))
			{
				sqlTable.append(",T_Account acc");
				sqlWhere.append(" and payre.AcctID = acc.AccountID");
				sqlWhere.append(" and acc.AccountType='" + dto.getSpareStr3()+ "'");
				
			}
			if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4())))
			{
				if(sqlTable.indexOf(",T_Account acc") == -1)
				{
					sqlTable.append(",T_Account acc");
					sqlWhere.append(" and payre.AcctID = acc.AccountID");
				}
				sqlWhere.append(" and acc.MOPID = '"+dto.getSpareStr4()+"'");
				
			}

			// SpareTime1:创建时间1
			if (dto.getSpareTime1() != null)
			{
				sqlWhere.append(" and payre.createtime>=to_timestamp('").append(
						dto.getSpareTime1().toString()).append(
						"', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			// SpareTime2:创建时间2
			if (dto.getSpareTime2() != null)
			{
				sqlWhere.append(" and payre.createtime<=to_timestamp('").append(
						dto.getSpareTime2().toString()).append(
						"', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			
			StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
			//设置构造取得当前查询总纪录数的sql
			setRecordCountQueryTable(tableName);
			// 设置当前数据查询sql
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,"D"));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
