/*
 * author :david.Yang 
 */
package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.SimpleCustServiceInteractionDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CsrBusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

public class SimpleCustServiceInteractionListHandler extends ValueListHandler {
    private SimpleCustServiceInteractionDAO dao = null;
    private String tableName = "";

    private CommonQueryConditionDTO dto = null;
    private String orgIDs =null;
    private StringBuffer begin = new StringBuffer("select a.* ");
    private StringBuffer selectStatement = new StringBuffer();

    public SimpleCustServiceInteractionListHandler() {
        this.dao = new SimpleCustServiceInteractionDAO();
    }

    public void setCriteria(Object dto1) throws ListHandlerException {
        LogUtility.log(SimpleCustServiceInteractionListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
        if (dto1 instanceof CommonQueryConditionDTO) 
      	    this.dto = (CommonQueryConditionDTO)dto1;
        else {
      	    LogUtility.log(SimpleCustServiceInteractionListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
        //以下的判断条件适用于p4的情况，在p5中不太适用所以注释掉---yangchen 2006/09/28----
        //if (!isParentCompany()) orgIDs = getOrgIDs();
        
        constructSelectQueryString(dto);
        //执行数据查询
        executeSearch(dao,true,true);
    }
    
    private void constructSelectQueryString(CommonQueryConditionDTO dto){
    	constructSelectQueryHead(dto);
    	constructSelectQueryBody(dto);
//为保持公用性，以下内容注释掉，把共同内容提取出来---yangchen 2006/09/28----    
        appendOrderByString(selectStatement);
        //设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
    }
   
    private void constructSelectQueryHead(CommonQueryConditionDTO dto){
    	//由于目前的客户是按区域管理的，所以所有的查询都涉及到客户/客户地址/分区信息，判断条件也就没有必要，所以注释掉---yangchen 2006/09/28----
    	//if (dto.getStreet() ==0){
    	//	tableName =" t_custserviceinteraction a ,t_address c ";
    	//} else{
    		tableName =" t_custserviceinteraction a";
    	//}
    	begin.append(" from "+tableName);
    }
    
    
    private void constructSelectQueryBody(CommonQueryConditionDTO dto) {
    	
    	selectStatement.append(" where 1=1 ");
    	
    	//受理单编号
    	if(dto.getNo()!=null &&!"".equals(dto.getNo())){
			if(dto.getNo().indexOf(";")!=-1){
				makeSQLByIntFieldIn("a.Id", dto.getNo(), selectStatement);
			}else{
				makeSQLByStringField("a.Id", dto.getNo(), selectStatement);
			}
	    }
    	
        //受理单类型
    	makeSQLByStringFieldIn("a.Type", dto.getType(), selectStatement);
    	
        //受理单状态
   		makeSQLByStringFieldIn("a.status", dto.getStatus(), selectStatement);
    	
        //操作员信息
    	 if (HelperCommonUtil.StringHaveContent(dto.getOperator())){
    		selectStatement.append(" and a.id in (select log.csiid from t_operator sub_oper,t_csiprocesslog log where log.operatorid =sub_oper.operatorid ");
			selectStatement.append(" and sub_oper.operatorname like '%").append(dto.getOperator()).append("%')");
    	 }
         //回访标志
    	 makeSQLByStringField("a.CALLBACKFLAG", dto.getSpareStr1(), selectStatement);
   
    	 //付费标志
    	 makeSQLByStringField("a.PAYMENTSTATUS", dto.getSpareStr2(), selectStatement);
    
         //用户证号  侯12-04修改,集团客户查询时,包括了子客户
    	 if(dto.getSpareStr5()!=null&&!dto.getSpareStr5().equals("")){
    		 appendStringWithGroupCustomerID("a.CUSTOMERID",dto.getSpareStr5(),selectStatement);
    	 }
    	 //区分customerstyle 2007/06/26 由于性能问题 杨晨注释掉
    	 /*if(dto.getSpareStr12()!=null&&!dto.getSpareStr12().equals("")){
    		 appendStringWithGroupCustomerStyle("a.CUSTOMERID",dto.getSpareStr12(),selectStatement);
    	 }*/
    	 
    	 //取消原因
    	 makeSQLByStringField("a.STATUSREASON", dto.getSpareStr9(), selectStatement);
         //受理原因
    	 makeSQLByStringField("a.CREATEREASON", dto.getSpareStr14(), selectStatement);
    	 
         //创建时间
         if (dto.getBeginTime() != null)
             selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

         if (dto.getEndTime() != null)
             selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
    	 
         //预约上门时间
         if (dto.getSpareBeginTime() != null)
             selectStatement.append(" and a.prefereddate>=to_timestamp('").append(dto.getSpareBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

         if (dto.getSpareEndTime() != null)
             selectStatement.append(" and a.prefereddate<=to_timestamp('").append(dto.getSpareEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
         
         //检查是否为代理商查询
         if (CsrBusinessUtility.isAgent(operatorID)) {
             selectStatement.append(" and a.id in (select distinct csiid from t_csiprocesslog where operatorId ="+operatorID+") ");
         }
         //工单编号查询
         if (HelperCommonUtil.StringHaveContent(dto.getSpareStr11())){
        	 selectStatement.append(" and a.referjobcardid =(select jobcardid from t_jobcard  where jobcardid ="+dto.getSpareStr11()+") ");   	 
         }
         //特殊条件标志条件用来给安装不成功退费用
         if (HelperCommonUtil.StringHaveContent(dto.getSpareStr13())){
        	 if("returnfee".equalsIgnoreCase(dto.getSpareStr13())){
        		 selectStatement.append("and ( a.PaymentStatus='D' And a.Status ='F') ");  
        		 if(dto.getNo()!=null &&!"".equals(dto.getNo()))
        			 selectStatement.append(" or (a.InstallationType ='S' and a.PaymentStatus='W' And a.Status ='F' And a.Id="+dto.getNo()+")");  
        		 else
        			 selectStatement.append(" or (a.InstallationType ='S' and a.PaymentStatus='W' And a.Status ='F' )");  
        	 }
         }
         
        //以下内容是对客户/地址的查询条件的组合
 		selectStatement.append(" and (");
 		selectStatement.append(" a.CUSTOMERID in (select CUSTOMERID from T_CUSTOMER customer ,T_ADDRESS addr");
 		selectStatement.append(" where customer.ADDRESSID= addr.ADDRESSID");
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr7())) {
 			selectStatement.append(" and addr.DETAILADDRESS like '%" + dto.getSpareStr7() + "%' ");
        }
	   	 //区分customerstyle 2007/06/26 杨晨追加
	   	 if(dto.getSpareStr12()!=null&&CommonConstDefinition.CUSTOMERSTYLE_GROUP.equals(dto.getSpareStr12())){
	   		selectStatement.append(" and Customer.Customertype='"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"'");
	   	 }else{
	   		selectStatement.append(" and (Customer.Customertype<>'"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"' Or Customer.Customertype Is Null)");
	   		 
	   	 }
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr6())) {
            selectStatement.append(" and customer.NAME='");
            selectStatement.append(dto.getSpareStr6());
            selectStatement.append("'");
 		}
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr8())) {
            selectStatement.append(" and (customer.TELEPHONE like '%");
            selectStatement.append(dto.getSpareStr8());
            selectStatement.append("%' or customer.TELEPHONEMOBILE like '%" + dto.getSpareStr8() + "%') ");
        }
 		//客户类型条件
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr10())) {
            selectStatement.append(" and customer.CUSTOMERTYPE='");
            selectStatement.append(dto.getSpareStr10());
            selectStatement.append("'");
        }
 		//对区域条件的整理
 		//所属组织
        try {
       	 //根据选择的区域条件来组织区域检索条件
       	 if (dto.getStreet() !=0){
       		appendStringWithDistrictID("addr.districtid",
           			 dto.getStreet(), selectStatement);
           	 
         }
       	
       	if (HelperCommonUtil.StringHaveContent(dto.getSpareStr26())&&(dto.getSpareStr26().equalsIgnoreCase("agentBk"))){
       	 selectStatement.append(" and customer.opensourcetype='")
       	 .append(CommonConstDefinition.OPENSOURCETYPE_PROXY).append("'");
       	 
        }
        
       	//根据输入的组织机构来建立组织管理区域的检索条件，输入dto.getFiliale为0的时候以当前错作员的组织为基准
			appendStringWithOrgGovernedDistrict("addr.districtid",dto.getFiliale(),selectStatement);
		} catch (ListHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectStatement.append(")");
		selectStatement.append("  OR ");
		selectStatement.append(" a.id in (select CSIID from T_NEWCUSTOMERINFO cust ,T_ADDRESS addr ");
		selectStatement.append(" where cust.CSIID=a.id AND a.TYPE='BK' ");
        selectStatement.append(" and addr.ADDRESSID=cust.FROMADDRESSID ");
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr7())) {
            selectStatement.append(" and addr.DETAILADDRESS like '%" + dto.getSpareStr7() + "%' ");
        }
	   	 //区分customerstyle 2007/06/26 杨晨追加
		if(dto.getSpareStr12()!=null&&CommonConstDefinition.CUSTOMERSTYLE_GROUP.equals(dto.getSpareStr12())){
	   		selectStatement.append(" and cust.custstyle='"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"'");
	   	 }else{
	   		selectStatement.append(" and (cust.custstyle<>'"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"' Or cust.custstyle Is Null)");
	   		 
	   	 }
        if (HelperCommonUtil.StringHaveContent(dto.getSpareStr6())) {
            selectStatement.append(" and cust.NAME='");
            selectStatement.append(dto.getSpareStr6());
            selectStatement.append("'");
        }
        if (HelperCommonUtil.StringHaveContent(dto.getSpareStr8())) {
            selectStatement.append(" and (cust.TELEPHONE like '%");
            selectStatement.append(dto.getSpareStr8());
            selectStatement.append("%' or cust.MOBILENUMBER like '%" + dto.getSpareStr8() + "%')");
        }
        //客户类型条件
        if (HelperCommonUtil.StringHaveContent(dto.getSpareStr10())) {
            selectStatement.append(" and cust.CUSTOMERTYPE='");
            selectStatement.append(dto.getSpareStr10());
            selectStatement.append("'");
        }
        
    	if (HelperCommonUtil.StringHaveContent(dto.getSpareStr26())&&(dto.getSpareStr26().equalsIgnoreCase("agentBk"))){
          	 selectStatement.append(" and cust.opensourcetype='")
          	 .append(CommonConstDefinition.OPENSOURCETYPE_PROXY).append("'");
          	 
        }
        
        //对区域条件的整理
 		//所属组织
        try {
       	 //根据选择的区域条件来组织区域检索条件
       	 if (dto.getStreet() !=0){
       		appendStringWithDistrictID("addr.districtid",
           			 dto.getStreet(), selectStatement);
           	 
            }
       	//根据输入的组织机构来建立组织管理区域的检索条件，输入dto.getFiliale为0的时候以当前错作员的组织为基准
			appendStringWithOrgGovernedDistrict("addr.districtid",dto.getFiliale(),selectStatement);
		} catch (ListHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        selectStatement.append("))");
        
    }

    private void appendOrderByString(StringBuffer selectStatement) {		
		//如果按customerid检索时的排序条件,
		if(dto.getSpareStr5()!=null&&!dto.getSpareStr5().equals("")){
			selectStatement.append(" order by a.customerid, a.id desc");
			return;
		}
		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		if ((orderByString == null) ||
				orderByString.trim().equals(""))
			selectStatement.append(" order by a.id desc");
		else {
			selectStatement.append(" order by a." + orderByString + orderByAscend);
		}
		
	}
}