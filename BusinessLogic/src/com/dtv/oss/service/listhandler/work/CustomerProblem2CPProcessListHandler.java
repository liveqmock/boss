/*
 * Created on 2005-11-8
 * 
 * @author whq
 * 
 * T_CustomerProblem��T_CustProblemProcess�����ϲ�ѯ
 * ����9-28�޸�,�����ѯ����T_CustProblemProcessʲô��,��������ɺ�ע�͵���,Ӱ�����CustomerProblem2CPProcessDAO,
 * Ӱ�쵽��JSP,cp_query.jsp
 * 
 */
package com.dtv.oss.service.listhandler.work;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.CustomerProblem2CPProcessDAO;
import com.dtv.oss.service.dao.work.CustomerProblemDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonUtility;
import com.dtv.oss.service.util.CommonConstDefinition;

public class CustomerProblem2CPProcessListHandler extends ValueListHandler {
	private CustomerProblem2CPProcessDAO dao = null;

	final private String tableName = "T_CustomerProblem cp,T_CustProblemProcess cpp";

	// final private String tableName = "T_CustomerProblem cp
	// ,t_custproblemprocess cpp";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CommonQueryConditionDTO dto = null;

	public CustomerProblem2CPProcessListHandler() {
		this.dao = new CustomerProblem2CPProcessDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(CustomerProblem2CPProcessListHandler.class,
				LogLevel.DEBUG, "in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(CustomerProblem2CPProcessListHandler.class,
					LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// if (fillDTOWithPrivilege(dto) == false)
		// return;
		// �����ѯ�ַ���
		constructSelectQueryString(dto);
		// ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	/**
	 * ��ѯ����˵�������Ӧ��CommonQueryConditionDTO�ֶΣ���ʽ���£� ���.����˵������ �ֶΣ�:
	 * ��Ӧ��CommonQueryConditionDTO�ֶ� 1.���޵����(T_customerProblem Id): No
	 * 2.��������(T_Address DistrictID): district 3.����ʱ��(T_customerProblem
	 * creatDate): BeginTime����EndTime 4.״̬(T_CustomerProblem status): status
	 * 5.�������(T_CustomerProblem ProblemCategory): type 6.�շ����(T_CustomerProblem
	 * FeeClass): spareStr1 7.�û�֤��(T_Customer CATVID): spareStr2
	 * 8.���о���һ��������customerproblem ��customerproblemprocess��״̬��action�Ķ�Ӧ��
	 * 9.�طñ�־(T_Customer callbackflag) spareStr3
	 * 
	 * @throws ListHandlerException
	 * 
	 */

	private void constructSelectQueryString(CommonQueryConditionDTO dto)
			throws ListHandlerException {
		boolean none = false;
		StringBuffer begin = new StringBuffer();
		begin.append("select cp.*,"
				+ CommonUtility.getSelectExpression4CustProblemProcess("cpp.")
				+ " from " + tableName);

		// begin.append("select cp.*, "
		// + CommonUtility.getSelectExpression4CustProblemProcess("cpp.")
		// + " from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where cp.id=cpp.custproblemid ");
		// �������ű������
		//appendString("cp.id = cpp.CustProblemID", selectStatement);

		/*
		 * Search Condition:
		 * 
		 */
		// 1.���޵����
		makeSQLByStringField("cp.Id", dto.getNo(), selectStatement);

		// 2.����ʱ��
		if (dto.getBeginTime() != null)
			selectStatement.append(" and cp.createdate>=to_timestamp('")
					.append(dto.getBeginTime().toString()).append(
							"', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getEndTime() != null)
			selectStatement.append(" and cp.createdate<=to_timestamp('")
					.append(dto.getEndTime().toString()).append(
							"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		// 3.״̬
		makeSQLByStringFieldIn("cp.status", dto.getStatus(), selectStatement);
		// 4.�������
		makeSQLByStringFieldIn("cp.ProblemCategory", dto.getType(),
				selectStatement);
		// 5.�շ����
		makeSQLByStringField("cp.FeeClass", dto.getSpareStr1(), selectStatement);
		// 6.�û�֤��

		makeSQLByStringField("cp.custId", dto.getSpareStr2(), selectStatement);

		//		 
		// //7.�طñ�־
		makeSQLByStringField("cp.callbackflag", dto.getSpareStr3(),
				selectStatement);
		// 8.��װ��ַ
		if (dto.getSpareStr4() != null) {
			selectStatement
					.append(" and cp.addressid in (select addressid from t_address where detailaddress like '%"
							+ dto.getSpareStr4() + "%')");
		}
		// 9.�������

		if (dto.getSpareStr5() != null) {
			selectStatement
					.append(" and cp.CUSTSERVICEACCOUNTID in (select SERVICEACCOUNTID from t_serviceaccount where servicecode = '"
							+ dto.getSpareStr5() + "')");
		}
		// 10.�豸�ͺ�

		if (dto.getSpareStr6() != null) {
			selectStatement
					.append(" and cp.CUSTSERVICEACCOUNTID in (select SERVICEACCOUNTID from t_customerproduct where deviceid in ( select deviceid from t_terminaldevice where devicemodel='"
							+ dto.getSpareStr6() + "'))");
		}
		// 11.�豸����
		
		if (dto.getSpareStr8() != null&&dto.getSpareStr6() == null) {
			selectStatement
					.append(" and cp.CUSTSERVICEACCOUNTID in (select SERVICEACCOUNTID from t_customerproduct where deviceid in ( select deviceid from t_terminaldevice where deviceclass='"
							+ dto.getSpareStr8() + "'))");
		}
		if (dto.getSpareStr10() !=null && ("Y").equals(dto.getSpareStr10()))
			selectStatement.append(" and cp.ismanualtransfer='"+dto.getSpareStr10()+"'");
		else if(("N").equals(dto.getSpareStr10()))
				selectStatement.append(" and cp.ismanualtransfer is null");
		/**
		 * if (HelperCommonUtil.StringHaveContent(dto.getSpareStr2()) ) {
		 * selectStatement.append(" and cp.Id in ( select cp.id from
		 * T_CustomerProblem cp where cp.custid in" + " (select customerid from
		 * t_customer c where c.addressid in " + "(select addressid from
		 * t_address a where a.districtid= '")
		 * .append(dto.getSpareStr2()).append("')))"); }
		 */
		String conditionForOnlyOneRecord = "cpp.id in(select max(id) from t_custproblemprocess cpp1 where cpp1.custproblemid = cp.id)";//"cpp.id in (select max(cpp.id) from T_CustomerProblem cp,T_CustProblemProcess cpp "
											
				//+ selectStatement.toString() + " group by cp.id)";
		appendString(conditionForOnlyOneRecord, selectStatement);

		//����֯�������ѯ�����Ƶ������,����һ������,
		//appendString("Cp.Addressid = Addr.Addressid", selectStatement);
		if (dto.getDistrict() != 0 && (dto.getSpareStr7() != null && !dto.getSpareStr7().equals(""))) {
			appendString("cp.addressid in (select addressid from t_address addr where 1=1",selectStatement);		
		// 1.��������
		//if (dto.getDistrict() != 0) {
			appendStringWithDistrictID("addr.districtid", dto.getDistrict(),
					selectStatement);
		//}// 2.������֯
			int orgID = 0;
			orgID = Integer.parseInt(dto.getSpareStr7());
			appendStringWithOrgGovernedDistrict("addr.districtid", orgID,
					selectStatement);			
			selectStatement.append(")");
		}else if(dto.getDistrict() != 0){
			appendString("cp.addressid in (select addressid from t_address addr where 1=1",selectStatement);
			appendStringWithDistrictID("addr.districtid", dto.getDistrict(),
						selectStatement);
			selectStatement.append(")");
		}else if(dto.getSpareStr7() != null && !dto.getSpareStr7().equals("")){
			appendString("cp.addressid in (select addressid from t_address addr where 1=1",selectStatement);
			int orgID = 0;
			orgID = Integer.parseInt(dto.getSpareStr7());
			appendStringWithOrgGovernedDistrict("addr.districtid", orgID,
						selectStatement);
			selectStatement.append(")");
		}
		// 2.������֯
		/*int orgID = 0;
		if (dto.getSpareStr7() != null && !dto.getSpareStr7().equals("")) {
			orgID = Integer.parseInt(dto.getSpareStr7());
		}
		appendStringWithOrgGovernedDistrict("addr.districtid", orgID,
				selectStatement);
		*/
		appendPrivilege(selectStatement,dto.getSpareStr9());
		LogUtility.log(CustomerProblem2CPProcessListHandler.class,
				LogLevel.DEBUG, selectStatement.toString());
		appendOrderByString(selectStatement);
		// appendOrderByString(end);
		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	/**
	 * Use fillDTOWithPrivilege() method to filter by operator's privilege
	 * 
	 * @return
	 * 
	 * private boolean fillDTOWithPrivilege(CommonQueryConditionDTO dto) throws
	 * ListHandlerException {
	 * 
	 * switch (getBelongTo()) { case OPERATOR_BELONG_TO_GENERAL_COMPANY: return
	 * true; case OPERATOR_BELONG_TO_SUBCOMPANY:
	 * dto.setFiliale(getOperator().getOrgID()); return true; case
	 * OPERATOR_BELONG_TO_STREET_STATION:
	 * dto.setStreet(getOperator().getOrgID()); return true; default: return
	 * false; } }
	 */

	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");

		if ((dto.getOrderField() == null)
				|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by cp.ID desc");
		else {
			selectStatement.append(" order by cp." + dto.getOrderField()
					+ orderByAscend);
		}

		orderByAscend = null;

	}

	private void appendPrivilege(StringBuffer selectStatement,String querytype)throws ListHandlerException {
		String orginfo = BusinessUtility.getOrgInfoByOrgID(getOperator().getOrgID());
		String orgtype = orginfo.substring(0,1);
		int orgID = getOperator().getOrgID();
		int parentorgid = BusinessUtility.getParentHasCustomerOrgID(orgID);
		//if(CommonConstDefinition.ORGANIZATIONORGTYPE_DEPARTMENT.equals(orgtype)||CommonConstDefinition.ORGANIZATIONORGTYPE_PARTNER.equals(orgtype))
			orgID = parentorgid;
			
	  	  	//�ֹ�˾ֻ�ܿ����и÷ֹ�˾�µĿͻ��ģ��Լ��÷ֹ�˾���������ı��޵�			  	
		  	String tvalue = BusinessUtility.getSystemsettingValueByName("SET_W_PRECISEWORK");
			if(tvalue != null && ("Y").equals(tvalue) && (dto.getSpareStr9() != null&&("queryPart").equals(dto.getSpareStr9()))){
					selectStatement.append(" and cp.processorgid="+getOperator().getOrgID());					
			}else if(dto.getSpareStr9() != null && ("queryAll").equals(dto.getSpareStr9())&&(tvalue != null && ("Y").equals(tvalue))){
					selectStatement.append(" and ( cp.processorgid="+getOperator().getOrgID());
					selectStatement.append(" or cp.addressid in (select addressid from t_address addr where 1=1");		
					selectStatement.append(" and addr.districtid in (select id from t_districtsetting connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
					selectStatement.append("))))");				
			}else {
				selectStatement.append(" and cp.addressid in (select addressid from t_address addr where 1=1");
				selectStatement.append(" and addr.districtid in (select id from t_districtsetting connect by prior id=belongto start with id in(select districtid from t_orggoverneddistrict where orgid=" + orgID);
				selectStatement.append(")))");
			}			
		  /*case OPERATOR_BELONG_TO_STREET_STATION:
			dto.setStreet(getOperator().getOrgID());
			break;*/  		 
	  	 
	}
	
	public static void main(String[] args) {
		// CustomerProblem2CPProcessListHandler lh = new
		// CustomerProblem2CPProcessListHandler();
		// CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
		// lh.constructSelectQueryString(dto);
	}
}