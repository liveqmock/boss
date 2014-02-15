package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CustomerDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

public class CustomerListHandler extends ValueListHandler {
	private CustomerDAO dao = null;
	private String tableName = "t_customer a left join t_address addr on a.addressid = addr.addressid ";
									
	private CommonQueryConditionDTO dto = null;

	public CustomerListHandler() {
		this.dao = new CustomerDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(CustomerListHandler.class, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(CustomerListHandler.class, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto)throws ListHandlerException {
		
		boolean customerCancelFlag=dto.isSpareBoolean();
		//if(dto.getStatus()!=null&&dto.getStatus().equals(CommonConstDefinition.CUSTOMER_STATUS_CANCEL))
		//customerCancelFlag=true;
		
		//所属组织
		int orgID = 0;
		if (dto.getSpareStr16() != null && !dto.getSpareStr16().equals("")){
			orgID = Integer.parseInt(dto.getSpareStr16());
				//appendStringWithOrgGovernedDistrict("addr.districtid", orgID,selectStatement);
				this.tableName = tableName +
						" left join (select id from t_districtsetting "+
						" connect by prior id=belongto start with id in "+
						" (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+orgID+")) "+
						" district on district.id=addr.districtid ";
		}
		
		//所属区域
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr15()))
				this.tableName = tableName +
								" , (select addressID from  T_ADDRESS " +
								"where districtid in(select Id from T_DISTRICTSETTING " +
								"connect by prior ID=BELONGTO  start with ID="+ dto.getSpareStr15() +" )) viewAddress";
		else //根据操作员所在组织的管理区域做限制条件
				this.tableName = tableName +
								" , (select addressID from  T_ADDRESS where districtid in " +
								" (select Id from T_DISTRICTSETTING " +
								" connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid= "+ BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg()) +"))) viewAddress ";
		
		//业务类型
		if (dto.getSpareStr28() != null && !dto.getSpareStr28().equals("")){
				this.tableName = tableName +
						" ,t_serviceaccount sa ";
		}
		
		//宽带用户名
		if (dto.getSpareStr29() != null && !dto.getSpareStr29().equals("")){
				this.tableName = tableName +
						" ,t_customerproduct cp,t_cpconfigedproperty pro ";
		}
		
		StringBuffer begin = new StringBuffer();
		begin.append("select distinct(a.CustomerID),"+
									"a.GroupCustID,"+
									"a.CustomerStyle,"+
									"a.CustomerType,"+
									"a.Gender,"+
									"a.Title,"+
									"a.Name,"+
									"a.Birthday,"+
									"a.Nationality,"+
									"a.CardType,"+
									"a.CardID,"+
									"a.SocialSecCardID,"+
									"a.LoginID,"+
									"a.LoginPWD,"+
									"a.Telephone,"+
									"a.TelephoneMobile,"+
									"a.Fax,"+
									"a.Email,"+
									"a.OrgID,"+
									"a.OpenSourceType,"+
									"a.OpenSourceTypeID,"+
									"a.AgentName,"+
									"a.ContractNo,"+
									"a.GroupBargainID,"+
									"a.MarketSegmentID,"+
									"a.AddressID,"+
									"a.catvid,"+
									"a.CurAccumulatePoint,"+
									"a.TotalAccumulatePoint,"+
									"a.createtime,"+
									"a.Status,"+
									"a.CustomerSerialNo,"+
									"a.Comments,"+
									"a.dt_create,"+
									"a.dt_lastmod, "+CommonUtility.getSelectExpression4Address("addr.") + " from "+ tableName);
		
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		
		//if (HelperCommonUtil.StringHaveContent(dto.getSpareStr15()))
			selectStatement.append(" and viewAddress.addressID=addr.addressID  ");
		if (dto.getSpareStr16() != null && !dto.getSpareStr16().equals(""))
			selectStatement.append(" and district.id=addr.districtid  ");
		
		appendString("a.addressid = addr.addressid", selectStatement);
		
		makeSQLByIntField("a.CustomerID", dto.getCustomerID(), selectStatement);
		//客户证集合
		makeSQLByIntFieldIn("a.CustomerID",dto.getSpareStr31(),selectStatement);
		makeSQLByStringField("a.Name", dto.getSpareStr1(), selectStatement,"like");
		//客户条形码条件
		makeSQLByStringField("a.CustomerSerialNo ", dto.getSpareStr27(),selectStatement);
		// 集团客户是"G",个人客户是"S" modify by david.Yang
		makeSQLByStringField("a.CustomerStyle", dto.getSpareStr2(),selectStatement);
		makeSQLByStringField("a.CustomerType", dto.getType(), selectStatement);
		makeSQLByStringField("a.opensourcetype", dto.getSpareStr13(),selectStatement);
		makeSQLByStringField("addr.detailaddress", dto.getSpareStr3(),selectStatement, "like");
		
		if(dto.getStatus()!=null&&!dto.getStatus().equals("")){
			//明确指定的status,直接拼上去,
			makeSQLByStringField("a.status", dto.getStatus(), selectStatement);
		}else if(customerCancelFlag){
			//session中是取消的客户,拼取消的条件,
			appendString("a.status = '"+ CommonConstDefinition.CUSTOMER_STATUS_CANCEL + "'",selectStatement);
		}else{
			//session中是非取消的客户,拼非取消的条件.
			appendString("a.status <> '"+ CommonConstDefinition.CUSTOMER_STATUS_CANCEL + "'",selectStatement);
		}
		//makeSQLByStringField("a.contractno",dto.getSpareStr26(),selectStatement);
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr26()))
			appendString(" a.contractno like '"+ dto.getSpareStr26() + "%'", selectStatement);
		
		
		// 对子客户有效的集团客户ID modify by david.Yang
		// 1.集团客户子客户查询为<0;2.集团客户查询为0;3.针对单个集团客户查它的groupCustID;
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr4())) {
			if (HelperCommonUtil.String2Int(dto.getSpareStr4()) > 0)
				makeSQLByIntField("a.groupCustID", HelperCommonUtil.String2Int(dto.getSpareStr4()), selectStatement);
			else if (HelperCommonUtil.String2Int(dto.getSpareStr4()) == 0)
				appendString("(a.groupCustID =0 or a.groupCustID is null)",selectStatement);
			else
				appendString("a.groupCustID !=0", selectStatement);
		}

		// 区分customerstyle
		if (dto.getSpareStr18() != null && !dto.getSpareStr18().equals("")) {
			if (CommonConstDefinition.CUSTOMERSTYLE_GROUP.equals(dto.getSpareStr18()))
				appendString("a.CUSTOMERSTYLE = 'G'", selectStatement);
			else
				appendString("(a.CUSTOMERSTYLE <> 'G' or a.customerstyle is null)", selectStatement);
		}
		
		if(HelperCommonUtil.StringHaveContent(dto.getSpareStr24())&&HelperCommonUtil.StringHaveContent(dto.getSpareStr25())){
			selectStatement.append(" and a.customerid in (select customerid from t_account where billaddressflag='")
			.append(dto.getSpareStr24()).append("' and invalidaddressreason='").append(dto.getSpareStr25()).append("')");
		}else if(HelperCommonUtil.StringHaveContent(dto.getSpareStr24())){
			selectStatement.append(" and a.customerid in (select customerid from t_account where billaddressflag='")
			.append(dto.getSpareStr24()).append("')");
		}else if(HelperCommonUtil.StringHaveContent(dto.getSpareStr25())){
			selectStatement.append(" and a.customerid in (select customerid from t_account where invalidaddressreason='")
			.append(dto.getSpareStr25()).append("')");
		}
		// 证件号
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr5()))
			makeSQLByStringField("a.CARDID", dto.getSpareStr5(),selectStatement, "like");

		// 社保卡号
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr6()))
			makeSQLByStringField("a.SOCIALSECCARDID", dto.getSpareStr6(),selectStatement, "like");

		// 固定电话
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr7()))
			makeSQLByStringField("a.TELEPHONE", dto.getSpareStr7(),selectStatement, "like");

		// 移动电话
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr8()))
			makeSQLByStringField("a.TELEPHONEMOBILE", dto.getSpareStr8(),selectStatement, "like");

		// 付费方式
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr9()))
			appendString("a.CustomerID in (select customerid from T_ACCOUNT where MOPID="+ dto.getSpareStr9() + ")", selectStatement);

		// 银行账号
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr10()))
			appendString("a.CustomerID in (select customerid from T_ACCOUNT where BANKACCOUNT like '"+ dto.getSpareStr10() + "%')", selectStatement);
		// 终端编号
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr21()))
			appendString(" a.CATVID like '"+ dto.getSpareStr21() + "%'", selectStatement);
		//终端编号 精确查询
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr33()))
			appendString(" a.CATVID = '"+ dto.getSpareStr33() + "'", selectStatement);


		// Mac地址 SpareStr11
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr11()) || HelperCommonUtil.StringHaveContent(dto.getSpareStr20())|| HelperCommonUtil.StringHaveContent(dto.getSpareStr32())){
			appendString(" a.CustomerID in (select sub_cp.customerid from T_CUSTOMERPRODUCT sub_cp, t_terminaldevice sub_td where sub_cp.deviceid=sub_td.deviceid and sub_cp.status <>'C' ",selectStatement);
			if(HelperCommonUtil.StringHaveContent(dto.getSpareStr11()))
				appendString(" ( sub_td.MACADDRESS like '" + dto.getSpareStr11() + "%' or " +" sub_td.INTERMACADDRESS like '" + dto.getSpareStr11() + "%' ) ", selectStatement);
			if(HelperCommonUtil.StringHaveContent(dto.getSpareStr20()))
				appendString(" Upper(sub_td.serialno) like '%" + dto.getSpareStr20() +"' ",selectStatement);
			if(HelperCommonUtil.StringHaveContent(dto.getSpareStr32()))
				appendString(" Upper(sub_td.serialno) = '" + dto.getSpareStr32() +"' ",selectStatement);

			selectStatement.append(" ) ");
		}
		
		// 服务号
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr14()))
			appendString("a.customerID in (select customerID from T_SERVICEACCOUNT where servicecode ="+ dto.getSpareStr14() + ")", selectStatement);
		// 所属区域
		//if (HelperCommonUtil.StringHaveContent(dto.getSpareStr15()))
		//	appendString("addr.addressID in (select addressID from  T_ADDRESS where districtid in(select Id from T_DISTRICTSETTING connect by prior ID=BELONGTO  start with ID="
		//					+ dto.getSpareStr15() + "))", selectStatement);

		// 合同号，针对集团开户 modify by david.Yang
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr17()))
			appendString("a.CustomerID in (select t.customerID from t_customerCampaign t where t.contractNo ='"
							+ dto.getSpareStr17() + "' union select distinct b.groupCustID from t_customer b,t_customerCampaign c "
							+ " where b.customerID =c.customerID and c.contractNo ='" + dto.getSpareStr17() + "')", selectStatement);
				
		// 市场信息 add by david.Yang
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr19()))
		   appendString("a.CustomerID in("+dto.getSpareStr19()+")", selectStatement);
		
		// cretetime
		if (dto.getBeginTime() != null)
			selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getEndTime() != null)
			selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				
		//销户时间
		
			if (dto.getSpareStr22() != null)
				selectStatement.append(" and a.dt_lastmod>=to_timestamp('").append(dto.getSpareStr22()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

			if (dto.getSpareStr23() != null)
				selectStatement.append(" and a.dt_lastmod<=to_timestamp('").append(dto.getSpareStr23()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if (dto.getSpareStr28() != null && !dto.getSpareStr28().equals(""))
			selectStatement.append(" and sa.customerid=a.customerid and sa.status<>'C' and sa.serviceid="+dto.getSpareStr28()+" " );
			
		if (dto.getSpareStr29() != null && !dto.getSpareStr29().equals(""))
			selectStatement.append(" and cp.customerid=a.customerid and cp.psid=pro.psid and pro.propertycode=30002 and cp.status<>'C' and pro.propertyvalue='"+dto.getSpareStr29().trim()+"' ");

		appendOrderByString(selectStatement);
		// appendOrderByString(end);
		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));

	}

	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		if ((orderByString == null) || orderByString.trim().equals(""))
			selectStatement.append(" order by a.CustomerID desc");
		else
		{
			if(orderByString!=null && "DETAILADDRESS".equals(orderByString))
				selectStatement.append(" order by addr." + orderByString+ orderByAscend);
			else if(orderByString!=null && "DISTRICTID".equals(orderByString))
				selectStatement.append(" order by addr." + orderByString+ orderByAscend);
			else
				selectStatement.append(" order by a." + orderByString+ orderByAscend);
		}
	}
}