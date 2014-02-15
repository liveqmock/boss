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
			//�����ѯ�ַ���
			constructSelectQueryString(dto);
			
			//ִ�����ݲ�ѯ
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
			
			
			
			
//			//SpareStr1:ͳ�Ʒ�ʽ
//			//����֯����ͳ��
//			if("O".equals(dto.getSpareStr1())){
//				//Ĭ��"1"���մ����֯����ͳ��
//				String parentorgid = "1";
//				//ѡ������֯������Ҫ������֯�����µķ�֧ͳ��
//				if(!(dto.getSpareStr3()==null||"".equals(dto.getSpareStr3())))
//				{
//					parentorgid = dto.getSpareStr3();
//				}
//				sqlShow.append(orgShow);
//				sqlTable.append(orgTable);
//				sqlWhere.append(orgWhere).append(parentorgid);
//				sqlGroup.append(orgGroup);
//			}
//			//������ͳ��
//			else if("D".equals(dto.getSpareStr1())){
				//Ĭ��"1"���մ������ͳ��,ѡ�����Ϻ���Ҳ���մ������ͳ��
				String id = "0";
				//ѡ��������Ҫ���������µķ�֧ͳ��
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
//				throw new ListHandlerException("��������ͳ�Ʒ�ʽδ֪��");
			
		


			
//			//SpareStr2:�ͻ�״̬
//			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
//				sqlWhere.append(" and cust.Status='" + dto2.getSpareStr2() +"'");	
			//SpareStr5:��Դ����
			if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5())))
			{
				sqlWhere.append(" and cust.OpenSourceType='" + dto2.getSpareStr5() + "'");
			}
			//SpareStr6:��Դ����ID
			if(!(dto2.getSpareStr6()==null || "".equals(dto2.getSpareStr6())))
			{
				sqlWhere.append(" and cust.OpenSourceTypeID=" + dto2.getSpareStr6());
			}
			
			//SpareTime1:����ʱ��1
			if(dto2.getSpareTime1()!=null)
			{
				sqlWhere.append(" and cust.createtime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:����ʱ��2
			if(dto2.getSpareTime2()!=null)
			{
				sqlWhere.append(" and cust.createtime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
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
