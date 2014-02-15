package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * �ͻ��ʻ�Ԥ��ͳ��
 * ͨ��UNION���ֱ�ͳ���ֽ���������
 * �ֽ����C��������Ҵ���T
 * 2007-1-12 JASON
 * @author 250713z
 *
 */
public class AccountDepositStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = AccountDepositStatListHandler.class;

		public AccountDepositStatListHandler(){
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
			
			//�ֽ�
			StringBuffer sqlShow1=new StringBuffer();
			//�������
			StringBuffer sqlShow2=new StringBuffer();
			//�ʻ���
			StringBuffer sqlShow3=new StringBuffer();
			StringBuffer sqlTable=new StringBuffer();
			StringBuffer sqlWhere=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			
			sqlShow1.append("select 'C' subName,sum(acct.CashDeposit) amount");
			sqlShow2.append("select 'T' subName,sum(acct.TokenDeposit) amount");
			sqlShow3.append("select 'A' subName,count(acct.accountid) amount");
			sqlTable.append(" from t_account acct");
			sqlWhere.append(" where 1=1");
			
			// ������˻�״̬Ϊ��ȡ�������� from frank bug 522
			sqlWhere.append(" and acct.status !='C'");
			String id = "0";
			// û��ѡ������,���մ������ͳ��;ѡ��������Ҫ���������µķ�֧ͳ��
			if (!(dto2.getSpareStr4() == null || "".equals(dto2.getSpareStr4()))) {
				id = dto2.getSpareStr4();
			}
			sqlShow1.append(distShowNew);
			sqlShow2.append(distShowNew);
			sqlShow3.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(" and acct.customerid=cust.CUSTOMERID").append(distWhereNew);
			sqlGroup.append(" group by dist.id,dist.name");
			
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
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlShow1.append(sqlTable).append(sqlWhere).append(sqlGroup).append(" union all ")
					.append(sqlShow2).append(sqlTable).append(sqlWhere).append(sqlGroup).append(" union all ")
					.append(sqlShow3).append(sqlTable).append(sqlWhere).append(sqlGroup),"D"));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
