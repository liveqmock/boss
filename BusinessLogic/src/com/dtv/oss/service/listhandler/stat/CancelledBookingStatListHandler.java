package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class CancelledBookingStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = CancelledBookingStatListHandler.class;
		final private String tableName = " T_CustServiceInteraction ";

		public CancelledBookingStatListHandler(){
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
			StringBuffer selectBuff=new StringBuffer(); 
			StringBuffer sqlShow=new StringBuffer();
			StringBuffer sqlTable=new StringBuffer();
			StringBuffer sqlWhere=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			sqlShow.append("select csi.StatusReason subName,count(csi.StatusReason) amount ");
			sqlTable.append(" from T_CustServiceInteraction csi");
			sqlWhere.append(" where csi.Status='" + CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL +"' and csi.StatusReason is not null");
			sqlWhere.append(" and csi.type='"+CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK+"'");
			sqlGroup.append(" Group by csi.StatusReason");

			//if(sqlTable.indexOf("T_NewCustomerInfo cust")<1){
			//	sqlTable.append(" ,T_NewCustomerInfo cust ");
			//	sqlWhere.append(" and cust.CSIID=csi.id ");
			//}
			
			//SpareStr1:ͳ�Ʒ�ʽ
			String id = "0";
			//����֯����ͳ�� �Ӳ���Ա��������
			if("O".equals(dto2.getSpareStr1())){
				//SpareStr3:��֯
				if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3())))
					id=dto2.getSpareStr3();
				
				sqlShow.append(orgShowNewByCsi);
				sqlTable.append(getOrgTableNewByCsi(id));
				sqlWhere.append(orgWhereNewByCsi);
				sqlGroup.append(orgGroupNewByCsi);	
				
			}
			//������ͳ��
			else if("D".equals(dto2.getSpareStr1())){
				if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4())))
					id=dto2.getSpareStr4();

				sqlShow.append(distShowNew);
				sqlTable.append(getDistTableNew(id).replaceAll(",t_customer", ",T_NewCustomerInfo"));
				sqlWhere.append(distWhereNew.replaceAll("cust.ADDRESSID", "cust.FromAddressID"))
				.append(" and csi.id=cust.CSIID");
				sqlGroup.append(distGroupNew);
				
			}
			else 
				throw new ListHandlerException("��������ͳ�Ʒ�ʽδ֪��");
			
				
			//SpareStr2:��Դ����
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2()))){
				if(sqlTable.indexOf("T_NewCustomerInfo cust")<1){
					sqlTable.append(",T_NewCustomerInfo cust");
					sqlWhere.append(" and cust.CSIID=csi.id");
				}
				sqlWhere.append(" and cust.OpenSourceType='" + dto2.getSpareStr2() + "'");
			}						
			//SpareTime1:����ʱ��1
			if(dto2.getSpareTime1()!=null){
				sqlWhere.append(" and csi.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:����ʱ��2
			if(dto2.getSpareTime2()!=null){
				sqlWhere.append(" and csi.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}	
			//SpareTime3:ԤԼ����ʱ��1
			if(dto2.getSpareTime3()!=null){
				sqlWhere.append(" and csi.PreferedDate>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime4:ԤԼ����ʱ��2
			if(dto2.getSpareTime4()!=null){
				sqlWhere.append(" and csi.PreferedDate<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
			setRecordCountQueryTable(tableName);
			
			selectBuff.append(sqlShow).append(sqlTable).append(sqlWhere).append(sqlGroup);
			
			//���õ�ǰ���ݲ�ѯsql
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(selectBuff,dto2.getSpareStr1()));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
