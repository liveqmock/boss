package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CustServiceInteractionDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonUtility;
import com.dtv.oss.service.util.CsrBusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author david.Yang
 * Created on 2005-10-19
 */

public class CustServiceInteractionListHandler extends ValueListHandler {
	 private CustServiceInteractionDAO dao = null;
	 private final String tableName = "t_custserviceinteraction a, t_address addr,"
	 	                            + "t_address addr2, t_newCustomerinfo nci,t_newcustAccountInfo ncai";
	 private final String tableName2 = "t_custserviceinteraction a, t_address addr, T_Customer nci";
	 
     private CommonQueryConditionDTO dto = null;

	 public CustServiceInteractionListHandler() {
		 this.dao = new CustServiceInteractionDAO();
	 }
	 
	/**
      * Use setCriteria() method to check whether or not the QUERY is the same
      * @return
      */
     public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(CustServiceInteractionListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
         if (dto1 instanceof CommonQueryConditionDTO) 
         	this.dto = (CommonQueryConditionDTO)dto1;
         else {
         	LogUtility.log(CustServiceInteractionListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
          }
         // added 
         //   if (fillDTOWithPrivilege(dto) == false) return;
         //构造查询字符串
         constructSelectQueryString(dto);
         //执行数据查询
         executeSearch(dao,true,true);
     }

     /**
      * Use fillDTOWithPrivilege() method to filter by operator's privilege
      * @return
     
     private boolean fillDTOWithPrivilege(CommonQueryConditionDTO dto)
	  	throws ListHandlerException {
     	
     	switch(getBelongTo()) {
	      	case OPERATOR_BELONG_TO_GENERAL_COMPANY:
	      		return true;
	      	case OPERATOR_BELONG_TO_SUBCOMPANY:
	      		dto.setFiliale(getOperator().getOrgID());
	      		return true;
	      	case OPERATOR_BELONG_TO_STREET_STATION:
	      		dto.setStreet(getOperator().getOrgID());
	      		return true;
	      	default:
	      		return false;
     	}
     }
      */
     private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException{
     	StringBuffer begin = new StringBuffer();
    	begin.append("select a.*, ");
    	begin.append(CommonUtility.getSelectExpression4Address("addr."));
		begin.append(",");
		begin.append(CommonUtility.getSelectExpression4Address2("addr2."));
		begin.append(",");
		begin.append(CommonUtility.getSelectExpression4NewCustomerInfo("nci."));
		begin.append(",");
		begin.append(CommonUtility.getSelectExpression4NewCustAccountInfo("ncai."));
		begin.append(" from " + tableName);
		
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	
    	//预约单号
		if(dto.getNo()!=null &&!"".equals(dto.getNo())){
			if(dto.getNo().indexOf(";")!=-1){
				makeSQLByIntFieldIn("a.Id", dto.getNo(), selectStatement);
			}else{
				makeSQLByIntField("a.Id", HelperCommonUtil.String2Int(dto.getNo()), selectStatement);
			}
	    }
		
        //所在区
		if (dto.getStreet() !=0){
		    String distinctIds =CsrBusinessUtility.getSubDistrinctID(dto.getStreet());
		    selectStatement.append(" and addr.DISTRICTID in ( "+distinctIds+" ) " );
		}
		/**-------20070122 杨晨   start-------------*/
		/**
		 * 默认条件,取得当前操作员所属组织,再取得该组织可管理区域,则该操作员只能查询该管理区域内的客户
		 */
		// 所属组织
		appendStringWithOrgGovernedDistrict("addr.districtid", 0,selectStatement);
		/**-------20070122 杨晨   end-------------*/
		
        //用户证号
        //	   makeSQLByIntField("a.CustomerId",HelperCommonUtil.String2Int(dto.getSpareStr1()),selectStatement);
		//  不能用makeSQLByIntField,因为预约查询传进来的就是0，modify by david.Yang
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())){
			selectStatement.append(" and a.CustomerId="+dto.getSpareStr1());
		}
		
		//付费状态
		makeSQLByStringField("a.PaymentStatus",dto.getSpareStr5(),selectStatement);
		
		//回访标志
		makeSQLByStringField("a.CallBackFlag",dto.getSpareStr6(),selectStatement);
		
		//联系人姓名
		makeSQLByStringField("nci.Name", dto.getSpareStr11(), selectStatement, "like");
		
		//联系电话
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr12()))
			selectStatement.append(" and (nci.TELEPHONE like '%").append(dto.getSpareStr12()).append("%' or nci.MOBILENUMBER like '%").append(dto.getSpareStr12()).append("%')");
		
		//来源渠道 和 来源渠道ID
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr13())) {
            selectStatement.append(" and nci.OPENSOURCETYPE='").append(dto.getSpareStr13());
            selectStatement.append("'");
            if (HelperCommonUtil.StringHaveContent(dto.getSpareStr14())) {
                selectStatement.append(" and nci.OPENSOURCEID=");
                selectStatement.append(dto.getSpareStr14());
            }
        }
		
        //工单编号
		makeSQLByStringField("a.ReferJobCardID", dto.getSpareStr15(), selectStatement);
		
		//详细地址
		makeSQLByStringField("addr.DETAILADDRESS", dto.getSpareStr16(),  selectStatement, "like");
		
        //创建时间
		if (dto.getBeginTime() != null)
			selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getEndTime() != null)
			selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		
		//预约上门日期
		if (dto.getSpareTime1() != null)
			selectStatement.append(" and a.PREFEREDDATE>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getSpareTime2() != null)
			selectStatement.append(" and a.PREFEREDDATE<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		appendString(" a.id=nci.csiid ",selectStatement);
		makeSQLByStringFieldIn(" a.type",dto.getType(),selectStatement);
		makeSQLByStringFieldIn(" a.status", dto.getStatus(), selectStatement);
		appendString(" a.id=ncai.csiid", selectStatement);
		appendString(" nci.FromAddressID=addr.addressid", selectStatement);
		appendString(" ncai.addressid=addr2.addressid", selectStatement);
		
		//appendOrderByString(selectStatement);
		
		
		//构造 预约增机和门店增机 的回访查询sql 与前面的结构保持一致 begin
		
		StringBuffer begin2 = new StringBuffer();
    	begin2.append("select a.*, ");
    	begin2.append(CommonUtility.getSelectExpression4Address("addr."));
		begin2.append(",");
		//拼凑出空的addr2
		begin2.append(CommonUtility.getSelectNullAddress2());
		begin2.append(",");
		//为取客户信息所拼凑的sql
		begin2.append(CommonUtility.getSelectExpression4Customer("nci."));
		begin2.append(",");
		//拼凑出空的客户帐户信息 （预约增机门店增机应该显示付费帐户信息，若需要再说，先拼空的）
		begin2.append(CommonUtility.getSelectNullCustAccountInfo());
		begin2.append(" from " + tableName2);
		
    	StringBuffer selectStatement2 = new StringBuffer();
    	selectStatement2.append(" where 1=1 ");
    	
    	//预约单号
		if(dto.getNo()!=null &&!"".equals(dto.getNo())){
			if(dto.getNo().indexOf(";")!=-1){
				makeSQLByIntFieldIn("a.Id", dto.getNo(), selectStatement2);
			}else{
				makeSQLByIntField("a.Id", HelperCommonUtil.String2Int(dto.getNo()), selectStatement2);
			}
	    }
		
        //所在区
		if (dto.getStreet() !=0){
		    String distinctIds =CsrBusinessUtility.getSubDistrinctID(dto.getStreet());
		    selectStatement2.append(" and addr.DISTRICTID in ( "+distinctIds+" ) " );
		}
		/**-------20070122 杨晨   start-------------*/
		/**
		 * 默认条件,取得当前操作员所属组织,再取得该组织可管理区域,则该操作员只能查询该管理区域内的客户
		 */
		// 所属组织
		appendStringWithOrgGovernedDistrict("addr.districtid", 0,selectStatement2);
		/**-------20070122 杨晨   end-------------*/
		
        //用户证号
        //	   makeSQLByIntField("a.CustomerId",HelperCommonUtil.String2Int(dto.getSpareStr1()),selectStatement2);
		//  不能用makeSQLByIntField,因为预约查询传进来的就是0，modify by david.Yang
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())){
			selectStatement2.append(" and a.CustomerId="+dto.getSpareStr1());
		}
		
		//付费状态
		makeSQLByStringField("a.PaymentStatus",dto.getSpareStr5(),selectStatement2);
		
		//回访标志
		makeSQLByStringField("a.CallBackFlag",dto.getSpareStr6(),selectStatement2);
		
		//联系人姓名
		makeSQLByStringField("nci.Name", dto.getSpareStr11(), selectStatement2, "like");
		
		//联系电话
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr12()))
			selectStatement2.append(" and (nci.TELEPHONE like '%").append(dto.getSpareStr12()).append("%' or nci.TelephoneMobile like '%").append(dto.getSpareStr12()).append("%')");
		
		//来源渠道 和 来源渠道ID
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr13())) {
            selectStatement2.append(" and nci.OPENSOURCETYPE='").append(dto.getSpareStr13());
            selectStatement2.append("'");
        }
		
        //工单编号
		makeSQLByStringField("a.ReferJobCardID", dto.getSpareStr15(), selectStatement2);
		
		//详细地址
		makeSQLByStringField("addr.DETAILADDRESS", dto.getSpareStr16(),  selectStatement2, "like");
		
        //创建时间
		if (dto.getBeginTime() != null)
			selectStatement2.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getEndTime() != null)
			selectStatement2.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		
		//预约上门日期
		if (dto.getSpareTime1() != null)
			selectStatement2.append(" and a.PREFEREDDATE>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getSpareTime2() != null)
			selectStatement2.append(" and a.PREFEREDDATE<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		appendString(" a.CustomerID=nci.CustomerID ",selectStatement2);
		makeSQLByStringFieldIn(" a.type",dto.getSpareStr7(),selectStatement2);
		//这种情况下不构造预约增机和门店增机的结果 使这部分(union的后半部分)结果为空
		if(dto.getSpareStr7() == null || "".equals(dto.getSpareStr7().trim()))
		{
			selectStatement2.append(" and a.id = -1");
		}
		makeSQLByStringFieldIn(" a.status", dto.getStatus(), selectStatement2);
		
		appendString(" nci.addressid=addr.addressid", selectStatement2);
		

		//构造 预约增机和门店增机 的回访查询sql 与前面的结构保持一致 end
		
		//设置构造取得当前查询总纪录数的sql
		//setRecordCountQueryTable(tableName);
		//setRecordCountSuffixBuffer(selectStatement);
		
		//设置构造取得当前查询总纪录数的sql
		StringBuffer querySql = new StringBuffer("select * from (").append(begin).append(selectStatement).append(" union ").append(begin2).append(selectStatement2).append(")");
		appendOrderByString(querySql);
		setRecordCountSuffixCondBuffer(querySql);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(querySql);
	}
     
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		
		if ((orderByString == null) ||
				orderByString.trim().equals(""))
			selectStatement.append(" order by DT_CREATE desc");
		else {
			selectStatement.append(" order by " + orderByString + orderByAscend);
		}
		
	}

}
