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
			//�����ѯ�ַ���
			constructSelectQueryString(this.dto);
			
			//ִ�����ݲ�ѯ
			executeSearch(this.dao, false, false);

		}
		//��Ʒ����ͳ��
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
		
		//SpareStr1:ͳ�Ʒ�ʽ
		//����֯����ͳ��
		String id = "0";
		if("O".equals(dto.getSpareStr1())){
			//ѡ������֯������Ҫ������֯�����µķ�֧ͳ��
			if(!(dto.getSpareStr4()==null||"".equals(dto.getSpareStr4())))
			{
				id = dto.getSpareStr4();
			}
			sqlShow.append(orgShowNewByCsi);
			sqlTable.append(getOrgTableNewByCsi(id));
			sqlWhere.append(orgWhereNewByCsi);
			sqlGroup.append(orgGroupNewByCsi);
		}
		//������ͳ��
		else if("D".equals(dto.getSpareStr1())){
			//ѡ��������Ҫ���������µķ�֧ͳ��
			if(!(dto.getSpareStr3()==null||"".equals(dto.getSpareStr3())))
			{
				id = dto.getSpareStr3();
			}
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(distWhereNew).append(" and csi.CustomerID = cust.CUSTOMERID");
			sqlGroup.append(distGroupNew);
		}

		// SpareStr2:�طñ�־
		if (!(dto.getSpareStr2() == null || "".equals(dto.getSpareStr2())))
		{
			if(CommonConstDefinition.CPCALLBACKFLAG_NO.equals(dto.getSpareStr2()))
				sqlWhere.append(" and (csi.CallBackFlag = '" + dto.getSpareStr2()+ "' or csi.CallBackFlag is null)");
			else
				sqlWhere.append(" and csi.CallBackFlag = '" + dto.getSpareStr2()+ "'");
		}
		// SpareStr5:��װ��ʽ
		if (!(dto.getSpareStr5() == null || "".equals(dto.getSpareStr5())))
		{
			sqlWhere.append(" and csi.InstallationType='" + dto.getSpareStr5()+"'");
		}
		
		// SpareTime1:����ʱ��1
		if (dto.getSpareTime1() != null)
		{
			sqlWhere.append(" and csi.CreateTime>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// SpareTime2:����ʱ��2
		if (dto.getSpareTime2() != null)
		{
			sqlWhere.append(" and csi.CreateTime<=to_timestamp('").append(
					dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//SpareTime1:�ط�ʱ��1
		if (dto.getSpareTime3() != null)
		{
			sqlWhere.append(" and csi.CallBackDate>=to_timestamp('").append(
					dto.getSpareTime3().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// SpareTime2:�ط�ʱ��2
		if (dto.getSpareTime4() != null)
		{
			sqlWhere.append(" and csi.CallBackDate<=to_timestamp('").append(
					dto.getSpareTime4().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
		

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
