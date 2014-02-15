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
			//�����ѯ�ַ���
			constructSelectQueryString(this.dto);
			
			//ִ�����ݲ�ѯ
			executeSearch(this.dao, false, false);

		}
		private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException {
			String CampaignType = null;
			//�ͻ��ײ�ͳ��
			if(queryType==StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERMEAL)
			{
				//if(dto.getSpareStr6()==null||"".equals(dto.getSpareStr6()))
				//	throw new ListHandlerException("�������󣺷��෽ʽδ֪��");
				CampaignType = CommonConstDefinition.CAMPAIGNCAMPAIGNTYPE_OPEN;
			}
			//�ͻ������ͳ��
			else if(queryType==StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERCAMPAIGN)
				CampaignType = CommonConstDefinition.CAMPAIGNCAMPAIGNTYPE_NORMAL;
			else
				throw new ListHandlerException("��������ͳ�ƶ���δ֪��");
			
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
			
			//SpareStr1:ͳ�Ʒ�ʽ
			//����֯����ͳ��
			String id = "0";
			if("O".equals(dto.getSpareStr1())){
				//ѡ������֯������Ҫ������֯�����µķ�֧ͳ��
				if(!(dto.getSpareStr2()==null||"".equals(dto.getSpareStr2())))
				{
					id = dto.getSpareStr2();
				}
				sqlShow.append(orgShowNew);
				sqlTable.append(getOrgTableNew(id));
				sqlWhere.append(orgWhereNew);
				sqlGroup.append(orgGroupNew);
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
				sqlWhere.append(distWhereNew);
				sqlGroup.append(distGroupNew);
			}
			else 
				throw new ListHandlerException("��������ͳ�Ʒ�ʽδ֪��");
			//SpareStr6:�ͻ�����
			if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4())))
			{
				sqlWhere.append(" and cust.CustomerType = '"+dto.getSpareStr4()+"'");
			}
			//SpareStr5:�ײ�״̬
			if(!(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5())))
			{
				sqlWhere.append(" and cucamp.status = '"+dto.getSpareStr5()+"'");
			}
			//SpareTime1:����ʱ��1
			if(dto.getSpareTime1()!=null)
			{
				sqlWhere.append(" and cucamp.Dt_Create>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:����ʱ��2
			if(dto.getSpareTime2()!=null)
			{
				sqlWhere.append(" and cucamp.Dt_Create<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
			
			//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
			setRecordCountQueryTable(tableName);
			//���õ�ǰ���ݲ�ѯsql
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,"D"));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
