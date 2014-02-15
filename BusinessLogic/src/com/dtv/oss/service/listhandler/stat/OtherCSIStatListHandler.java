package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class OtherCSIStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = OtherCSIStatListHandler.class;
		final private String tableName = " T_CustServiceInteraction csi";

		public OtherCSIStatListHandler(){
			dao=new CommonStatDAO();
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
		
			sqlShow.append("select csi.Type as subName, count(csi.Type) as amount ");
			sqlTable.append(" from T_CustServiceInteraction csi");
			sqlWhere.append(" where csi.Type NOT in ( '" +
					 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS + "','" +
					 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB + "','" +
					 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_GO + "' ) " );
			sqlGroup.append(" Group by csi.Type ");
			
			//SpareStr1:ͳ�Ʒ�ʽ
			//����֯����ͳ��
			String id = "0";
			if("O".equals(dto2.getSpareStr1())){
				//ѡ������֯������Ҫ������֯�����µķ�֧ͳ��
				if(!(dto2.getSpareStr3()==null||"".equals(dto2.getSpareStr3())))
				{
					id = dto2.getSpareStr3();
				}
				sqlShow.append(orgShowNewByCsi);
				sqlTable.append(getOrgTableNewByCsi(id));
				sqlWhere.append(orgWhereNewByCsi);
				sqlGroup.append(orgGroupNewByCsi);
			}
			//������ͳ��
			else if("D".equals(dto2.getSpareStr1())){
				//ѡ��������Ҫ���������µķ�֧ͳ��
				if(!(dto2.getSpareStr4()==null||"".equals(dto2.getSpareStr4())))
				{
					id = dto2.getSpareStr4();
				}
				sqlShow.append(distShowNew);
				sqlTable.append(getDistTableNew(id));
				sqlWhere.append(distWhereNew).append(" and csi.CustomerID=cust.customerid");
				sqlGroup.append(distGroupNew);
			}
				
			//SpareStr2:�û�����
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
			{
				if(sqlTable.indexOf(",t_customer cust")<1){
					sqlTable.append(",t_customer cust");
					sqlWhere.append(" and csi.CustomerID=cust.customerid");
				}
				sqlWhere.append(" and cust.CustomerType='" + dto2.getSpareStr2() + "' ");
			}
						
			//SpareTime1:����ʱ��1
			if(dto2.getSpareTime1()!=null)
			{
				sqlWhere.append(" and csi.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:����ʱ��2
			if(dto2.getSpareTime2()!=null)
			{
				sqlWhere.append(" and csi.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			
			StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
			//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
			setRecordCountQueryTable(tableName);
			//���õ�ǰ���ݲ�ѯsql
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,dto2.getSpareStr1()));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
