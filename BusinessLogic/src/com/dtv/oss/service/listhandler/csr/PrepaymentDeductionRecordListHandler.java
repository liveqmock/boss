package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.PrepaymentDeductionRecordDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * Ԥ��ֿۼ�¼��ѯValueListHandler
 * author     ��Jason.Zhou 
 * date       : 2006-2-13
 * description:
 * @author 250713z
 *
 */
public class PrepaymentDeductionRecordListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private PrepaymentDeductionRecordDAO dao = null;
	private static final Class clazz = PrepaymentDeductionRecordListHandler.class;
	final private String tableName = " T_PrePaymentDeductionRecord ";

	public PrepaymentDeductionRecordListHandler(){
		dao=new PrepaymentDeductionRecordDAO();
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
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			executeSearch(dao, false, false);
		else
			executeSearch(dao, true, true,true);
	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		StringBuffer begin = new StringBuffer();
		StringBuffer selectStatement = new StringBuffer(128);
		begin.append("select * from " + tableName);
		selectStatement.append(" where 1=1 ");

		//SpareStr1:���
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1()))){
			makeSQLByIntField("SeqNo",Integer.valueOf(dto.getSpareStr1()).intValue(),selectStatement);
		}
		
		//SpareStr2:�ͻ�֤��
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByIntField("CustID",Integer.valueOf(dto.getSpareStr2()).intValue(),selectStatement);
		//SpareStr3:�ʻ���
		if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByIntField("ACCTID",Integer.valueOf(dto.getSpareStr3()).intValue(),selectStatement);
		//SpareStr4:��֯
		if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4()))){
			selectStatement.append(" and custid in (select customer.customerid from t_customer customer ");
			selectStatement.append(" where customer.orgid in (select orgid from t_organization ");
			selectStatement.append(" where status='V' and hascustomerflag='Y' connect ");
			selectStatement.append(" by prior orgid=parentorgid start with orgid=" + dto.getSpareStr4() + " ) )");
		}
		//SpareStr5:����
		if(!(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5()))){
			selectStatement.append(" and custid in (select customer.customerid from t_customer customer,t_address address ");
			selectStatement.append(" where customer.addressid=address.addressid and address.districtid in  ");
			selectStatement.append(" ( select id from t_DISTRICTSETTING where status='V' connect by prior id=belongto start with id=");
			selectStatement.append(dto.getSpareStr5() + " ) )");
		}
		//SpareStr6:�ۿ�����
		if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6())))
			makeSQLByStringField("PrePaymentType",dto.getSpareStr6(),selectStatement);
		//SpareStr7:��Դ��¼����
		if(!(dto.getSpareStr7()==null || "".equals(dto.getSpareStr7())))
			makeSQLByStringField("ReferRecordType",dto.getSpareStr7(),selectStatement);
		//SpareStr8:��Դ���ݺ�
		if(!(dto.getSpareStr8()==null || "".equals(dto.getSpareStr8())))
			makeSQLByIntField("ReferRecordID",Integer.valueOf(dto.getSpareStr8()).intValue(),selectStatement);
		//SpareStr9:���ʱ�־
		if(!(dto.getSpareStr9()==null || "".equals(dto.getSpareStr9())))
			makeSQLByStringField("InvoicedFlag",dto.getSpareStr9(),selectStatement);
		//SpareStr10:״̬
		if(!(dto.getSpareStr10()==null || "".equals(dto.getSpareStr10())))
			makeSQLByStringField("Status",dto.getSpareStr10(),selectStatement);
		//SpareStr11:������Դ
		if(!(dto.getSpareStr11()==null || "".equals(dto.getSpareStr11())))
			makeSQLByStringField("CreatingMethod",dto.getSpareStr11(),selectStatement);
		//SpareStr12:�ʵ���
		if(!(dto.getSpareStr12()==null || "".equals(dto.getSpareStr12())))
			makeSQLByStringField("INVOICENO",dto.getSpareStr12(),selectStatement);
/*		yangchen 2007/2/11 move
		//SpareTime1:�ֿ�ʱ��1
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and CREATETIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:�ֿ�ʱ��2
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and CREATETIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
*/
/* yangchen  2007/2/11 add start */
		//BeginTime:����ʱ��(��ʼ)
		if(dto.getBeginTime()!=null)
			selectStatement.append(" and CreateTime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//EndTime:����ʱ��(����)
		if(dto.getEndTime()!=null)
			selectStatement.append(" and CreateTime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareStr13:������������
		if(!(dto.getSpareStr13()==null || "".equals(dto.getSpareStr13())))
			makeSQLByStringField("ReferSheetType",dto.getSpareStr13(),selectStatement);
		//SpareStr14:�������ݺ�
		if(!(dto.getSpareStr14()==null || "".equals(dto.getSpareStr14())))
			makeSQLByStringField("ReferSheetID",dto.getSpareStr14(),selectStatement);
		
/* yangchen  2007/2/11 add end */		
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		appendOrderByString(selectStatement);
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		setExtraQuerySQL("select sum(amount) from " + tableName + selectStatement.toString());
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by SeqNo desc");
		else {
			selectStatement.append(" order by " + dto.getOrderField() + orderByAscend);
		}
		orderByAscend = null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
