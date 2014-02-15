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
			//�����ѯ�ַ���
			constructSelectQueryString(this.dto);
			
			//ִ�����ݲ�ѯ
			executeSearch(this.dao, false, false);

		}
		//����ͳ�� ��ͳ�� ͳ�ƽ�� 1-->֧�����ʼ�Ԥ�����;2-->���õ���;3-->�ֿ۵���
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
		// ��ͳ��ֻͳ��״̬Ϊ��Ч�ĵ��ʼ�¼
		sqlWhere.append(" and accadj.Status = '"+ CommonConstDefinition.ADJUST_STATUS_VALIDATION + "'");
		sqlGroup.append(" group by accadj.ReferRecordType");
		
		// SpareStr1:ͳ�Ʒ�ʽ
		// ����֯����ͳ�� ����֯�ṹ���������֯������Ӧ�Ӳ���Ա���ڵ���֯����
		String id = "0";
		if ("O".equals(dto.getSpareStr1())) {
			
			// ѡ������֯������Ҫ������֯�����µķ�֧ͳ��
			if (!(dto.getSpareStr2() == null || "".equals(dto.getSpareStr2())))
				id = dto.getSpareStr2();
			// ����ò�����������������������ô����ʾ����ʼ�¼�����Ĳ���Ա
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
		// ������ͳ��
		else if ("D".equals(dto.getSpareStr1())) {
			// ѡ��������Ҫ���������µķ�֧ͳ��
			if (!(dto.getSpareStr3() == null || "".equals(dto.getSpareStr3()))) {
				id = dto.getSpareStr3();
			}
			//��������򲻺�����������ô����ʾ����ʼ�¼�����Ĳ���Ա
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

		// SpareStr4:�ͻ�����
		if (!(dto.getSpareStr4() == null || "".equals(dto.getSpareStr4())))
		{
			if(sqlTable.indexOf(",t_customer cust")<1){
				sqlTable.append(",t_customer cust");
				sqlWhere.append(" and accadj.custid=cust.customerid");
			}
			sqlWhere.append(" and cust.CustomerType = '" + dto.getSpareStr4()
					+ "'");
		}
		// SpareStr5:���ʷ�ʽ
		if (!(dto.getSpareStr5() == null || "".equals(dto.getSpareStr5())))
		{
			sqlWhere.append(" and accadj.AdjustmentType = '"
					+ dto.getSpareStr5() + "'");
		}
		// SpareStr6:�ʻ�����
		if (!(dto.getSpareStr6() == null || "".equals(dto.getSpareStr6()))) {
			sqlTable.append(",T_Account acc");
			sqlWhere.append(" and accadj.AcctID=acc.AccountID");
			sqlWhere
					.append(" and acc.AccountType='" + dto.getSpareStr6() + "'");
			
		}
		// SpareStr7:����ԭ��
		if (!(dto.getSpareStr7() == null || "".equals(dto.getSpareStr7())))
		{
			sqlWhere
					.append(" and accadj.Reason = '" + dto.getSpareStr7() + "'");
		}

		// SpareTime1:����ʱ��1
		if (dto.getSpareTime1() != null)
		{
			sqlWhere.append(" and accadj.CreateTime>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
		}
		// SpareTime2:����ʱ��2
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
		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,dto.getSpareStr1()));
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
