/*
 * Created on 2005-11-8
 * 
 * @author whq
 * 
 * T_CustomerProblem和T_CustProblemProcess的联合查询
 * 侯于9-28修改,这个查询不关T_CustProblemProcess什么事,相关内容由侯注释掉了,影响的类CustomerProblem2CPProcessDAO,
 * 影响到的JSP,cp_query.jsp
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
		// 构造查询字符串
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	/**
	 * 查询条件说明及其对应的CommonQueryConditionDTO字段，格式如下： 序号.条件说明（表 字段）:
	 * 对应的CommonQueryConditionDTO字段 1.报修单编号(T_customerProblem Id): No
	 * 2.所在区域(T_Address DistrictID): district 3.创建时间(T_customerProblem
	 * creatDate): BeginTime——EndTime 4.状态(T_CustomerProblem status): status
	 * 5.报修类别(T_CustomerProblem ProblemCategory): type 6.收费类别(T_CustomerProblem
	 * FeeClass): spareStr1 7.用户证号(T_Customer CATVID): spareStr2
	 * 8.还有就是一条件就是customerproblem 跟customerproblemprocess的状态与action的对应。
	 * 9.回访标志(T_Customer callbackflag) spareStr3
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
		// 连接三张表的条件
		//appendString("cp.id = cpp.CustProblemID", selectStatement);

		/*
		 * Search Condition:
		 * 
		 */
		// 1.报修单编号
		makeSQLByStringField("cp.Id", dto.getNo(), selectStatement);

		// 2.创建时间
		if (dto.getBeginTime() != null)
			selectStatement.append(" and cp.createdate>=to_timestamp('")
					.append(dto.getBeginTime().toString()).append(
							"', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getEndTime() != null)
			selectStatement.append(" and cp.createdate<=to_timestamp('")
					.append(dto.getEndTime().toString()).append(
							"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		// 3.状态
		makeSQLByStringFieldIn("cp.status", dto.getStatus(), selectStatement);
		// 4.报修类别
		makeSQLByStringFieldIn("cp.ProblemCategory", dto.getType(),
				selectStatement);
		// 5.收费类别
		makeSQLByStringField("cp.FeeClass", dto.getSpareStr1(), selectStatement);
		// 6.用户证号

		makeSQLByStringField("cp.custId", dto.getSpareStr2(), selectStatement);

		//		 
		// //7.回访标志
		makeSQLByStringField("cp.callbackflag", dto.getSpareStr3(),
				selectStatement);
		// 8.安装地址
		if (dto.getSpareStr4() != null) {
			selectStatement
					.append(" and cp.addressid in (select addressid from t_address where detailaddress like '%"
							+ dto.getSpareStr4() + "%')");
		}
		// 9.服务号码

		if (dto.getSpareStr5() != null) {
			selectStatement
					.append(" and cp.CUSTSERVICEACCOUNTID in (select SERVICEACCOUNTID from t_serviceaccount where servicecode = '"
							+ dto.getSpareStr5() + "')");
		}
		// 10.设备型号

		if (dto.getSpareStr6() != null) {
			selectStatement
					.append(" and cp.CUSTSERVICEACCOUNTID in (select SERVICEACCOUNTID from t_customerproduct where deviceid in ( select deviceid from t_terminaldevice where devicemodel='"
							+ dto.getSpareStr6() + "'))");
		}
		// 11.设备类型
		
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

		//把组织和区域查询条件移到最后来,简化上一句条件,
		//appendString("Cp.Addressid = Addr.Addressid", selectStatement);
		if (dto.getDistrict() != 0 && (dto.getSpareStr7() != null && !dto.getSpareStr7().equals(""))) {
			appendString("cp.addressid in (select addressid from t_address addr where 1=1",selectStatement);		
		// 1.所在区域
		//if (dto.getDistrict() != 0) {
			appendStringWithDistrictID("addr.districtid", dto.getDistrict(),
					selectStatement);
		//}// 2.所属组织
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
		// 2.所属组织
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
		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
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
			
	  	  	//分公司只能看所有该分公司下的客户的，以及该分公司做过操作的报修单			  	
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