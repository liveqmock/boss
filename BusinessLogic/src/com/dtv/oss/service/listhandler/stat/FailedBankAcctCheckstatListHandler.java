package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.TimestampUtility;

public class FailedBankAcctCheckstatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO daoNum = null;
		private CommonStatDAO daoCount = null;
		private static final Class clazz = FailedBankAcctCheckstatListHandler.class;
		final private String tableName = " T_Account ";

		public FailedBankAcctCheckstatListHandler(){
			daoNum=new CommonStatDAO();
			daoCount=new CommonStatDAO();
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
			executeSearch(daoNum, false, false,daoCount);

		}
		
		private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
			StringBuffer selectBuff=new StringBuffer();
			StringBuffer selectBuff2=new StringBuffer();
			String commonTable="";
			if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4()))){
				commonTable=" from T_Customer cust,t_address addr, T_ACCOUNT acct ," +
									distTable(dto2.getSpareStr4());
			}else {
				commonTable=" from T_Customer cust,t_address addr, T_ACCOUNT acct, " +
									ssqlTable_1;
			}
			
			String commonWhere=" where cust.AddressID = addr.ADDRESSID   and acct.CustomerID=cust.CustomerID " +
					" and acct.BankAccountStatus='" + CommonConstDefinition.ACCOUNTBANKACCOUNT_STATUS_UNSUCCESS + "' and acct.balance < 0 ";
			String commonGroupBy=" group by dist.id, dist.name  ";
			//SpareStr4:����
//			if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4())))
				commonWhere=commonWhere + ssqlWhere;
//			else
//				commonWhere=commonWhere + " and  dist.id  in( select ID  from t_districtsetting " +
//						" CONNECT BY PRIOR  id =BelongTo start with id = 1 ) ";;
			//SpareStr1:�ʻ�״̬
			if(!(dto2.getSpareStr1()==null || "".equals(dto2.getSpareStr1())))
				commonWhere= commonWhere + " and acct.Status='" + dto2.getSpareStr1() +"' ";
			//SpareStr2:�ͻ�����
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
				commonWhere= commonWhere + " and cust.CustomerType='" + dto2.getSpareStr2() +"' ";
			
			//ͳ��һ�����ϵ�
			selectBuff.append(" select dist.id as id, dist.name as name ,'Y' as subName ,count(acct.AccountID) as amount ");
			selectBuff2.append(" select dist.id as id, dist.name as name ,'Y' as subName ,sum(acct.Balance) as amount ");
			
			selectBuff.append(commonTable);
			selectBuff2.append(commonTable);
			
			selectBuff.append(commonWhere);
			selectBuff2.append(commonWhere);
			
			selectBuff.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-12)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-12)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
			selectBuff.append(commonGroupBy);
			selectBuff2.append(commonGroupBy);
			
			selectBuff.append(" UNION ALL ");
			selectBuff2.append(" UNION ALL ");
			
			//ͳ��7-12����
			selectBuff.append(" select dist.id as id, dist.name as name ,'Q' as subName ,count(acct.AccountID) as amount ");
			selectBuff2.append(" select dist.id as id, dist.name as name ,'Q' as subName ,sum(acct.Balance) as amount ");
			
			selectBuff.append(commonTable);
			selectBuff2.append(commonTable);
			
			selectBuff.append(commonWhere);
			selectBuff2.append(commonWhere);
			
			selectBuff.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-12)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-12)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-7)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-7)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
			selectBuff.append(commonGroupBy);
			selectBuff2.append(commonGroupBy);
			
			selectBuff.append(" UNION ALL ");
			selectBuff2.append(" UNION ALL ");
			
			//ͳ��4-6����
			selectBuff.append(" select dist.id as id, dist.name as name ,'L' as subName ,count(acct.AccountID) as amount ");
			selectBuff2.append(" select dist.id as id, dist.name as name ,'L' as subName ,sum(acct.Balance) as amount ");
			
			selectBuff.append(commonTable);
			selectBuff2.append(commonTable);
			
			selectBuff.append(commonWhere);
			selectBuff2.append(commonWhere);
			
			selectBuff.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-7)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-7)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-4)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-4)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
			selectBuff.append(commonGroupBy);
			selectBuff2.append(commonGroupBy);
			
			selectBuff.append(" UNION ALL ");
			selectBuff2.append(" UNION ALL ");
			
			//ͳ��3���µ�
			selectBuff.append(" select dist.id as id, dist.name as name ,'S' as subName ,count(acct.AccountID) as amount ");
			selectBuff2.append(" select dist.id as id, dist.name as name ,'S' as subName ,sum(acct.Balance) as amount ");
			
			selectBuff.append(commonTable);
			selectBuff2.append(commonTable);
			
			selectBuff.append(commonWhere);
			selectBuff2.append(commonWhere);
			
			selectBuff.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-4)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-4)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-3)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-3)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
			selectBuff.append(commonGroupBy);
			selectBuff2.append(commonGroupBy);
			
			selectBuff.append(" UNION ALL ");
			selectBuff2.append(" UNION ALL ");
			
			//ͳ��2���µ�
			selectBuff.append(" select dist.id as id, dist.name as name ,'T' as subName ,count(acct.AccountID) as amount ");
			selectBuff2.append(" select dist.id as id, dist.name as name ,'T' as subName ,sum(acct.Balance) as amount ");
			
			selectBuff.append(commonTable);
			selectBuff2.append(commonTable);
			
			selectBuff.append(commonWhere);
			selectBuff2.append(commonWhere);
			
			selectBuff.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-3)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-3)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-2)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-2)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
			selectBuff.append(commonGroupBy);
			selectBuff2.append(commonGroupBy);
			
			selectBuff.append(" UNION ALL ");
			selectBuff2.append(" UNION ALL ");
			
			//ͳ��һ���µ�
			selectBuff.append(" select dist.id as id, dist.name as name ,'O' as subName ,count(acct.AccountID) as amount ");
			selectBuff2.append(" select dist.id as id, dist.name as name ,'O' as subName ,sum(acct.Balance) as amount ");
			
			selectBuff.append(commonTable);
			selectBuff2.append(commonTable);
			
			selectBuff.append(commonWhere);
			selectBuff2.append(commonWhere);
			
			selectBuff.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-2)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime>to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-2)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-1)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),-1)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
			selectBuff.append(commonGroupBy);
			selectBuff2.append(commonGroupBy);
			
			selectBuff.append(" UNION ALL ");
			selectBuff2.append(" UNION ALL ");
			
			//ͳ�Ƶ�ǰ��
			selectBuff.append(" select dist.id as id, dist.name as name ,'C' as subName ,count(acct.AccountID) as amount ");
			selectBuff2.append(" select dist.id as id, dist.name as name ,'C' as subName ,sum(acct.Balance) as amount ");
			
			selectBuff.append(commonTable);
			selectBuff2.append(commonTable);
			
			selectBuff.append(commonWhere);
			selectBuff2.append(commonWhere);
			
			selectBuff.append(" and acct.createtime>=to_timestamp('").append(TimestampUtility.getMonthFirstDay(TimestampUtility.getCurrentTimestamp())).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime>=to_timestamp('").append(TimestampUtility.getMonthFirstDay(TimestampUtility.getCurrentTimestamp())).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),"D",1)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			selectBuff2.append(" and acct.createtime<=to_timestamp('").append(TimestampUtility.getTimeEnd(TimestampUtility.getCurrentTimestamp(),"D",1)).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
			selectBuff.append(commonGroupBy);
			selectBuff2.append(commonGroupBy);
			
			//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
			setRecordCountQueryTable(tableName);
			//���õ�ǰ���ݲ�ѯsql			
			setExtraQuerySQL(selectBuff2.toString());
			setRecordDataQueryBuffer(selectBuff);
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
