package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * �����ʺŴ���ͳ��
 * 2007-1-12 Jason
 * @author 250713z
 *
 */
public class AcctBankMatchStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = AcctBankMatchStatListHandler.class;

		public AcctBankMatchStatListHandler(){
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
			
			sqlShow.append("select acct.BankAccountStatus subName,count(acct.accountid) amount");
			sqlTable.append(" from t_account acct");
			sqlWhere.append(" where acct.BankAccountStatus is not null and acct.status <> '" + 
					CommonConstDefinition.ACCOUNT_STATUS_CLOSE +"' and acct.MOPID in (select mop.MOPID from T_MethodOfPayment mop where mop.Status='" + 
					CommonConstDefinition.GENERALSTATUS_VALIDATE +"' and mop.PayType='" + CommonConstDefinition.MOPPAYTYPE_PAYBYBANK +"')");
			sqlGroup.append(" group by acct.BankAccountStatus");
			
			String id = "0";
			// û��ѡ������,���մ������ͳ��;ѡ��������Ҫ���������µķ�֧ͳ��
			if (!(dto2.getSpareStr4() == null || "".equals(dto2.getSpareStr4()))) {
				id = dto2.getSpareStr4();
			}
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(" and acct.customerid=cust.CUSTOMERID").append(distWhereNew);
			sqlGroup.append(distGroupNew);
			
			//SpareStr1:�ͻ�����
			makeSQLByStringField("cust.CustomerType", dto2.getSpareStr1(), sqlWhere);
			//SpareStr2:�ʻ�����
			makeSQLByStringField("acct.AccountType", dto2.getSpareStr2(), sqlWhere);
			//SpareStr3:���ѷ�ʽ
			if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3()))){
				sqlWhere.append(" and acct.MOPID=" + dto2.getSpareStr3());
			}
			
			//SpareStr5:�ʻ�״̬
			makeSQLByStringField("acct.Status", dto2.getSpareStr5(), sqlWhere);
			
			//SpareTime1:����ʱ��1
			if(dto2.getSpareTime1()!=null){
				sqlWhere.append(" and acct.createtime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:����ʱ��2
			if(dto2.getSpareTime2()!=null){
				sqlWhere.append(" and acct.createtime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderAndShowAllForStat(sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup),"D",id));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
