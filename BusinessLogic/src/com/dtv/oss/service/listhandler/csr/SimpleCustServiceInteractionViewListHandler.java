/*
 * author :david.Yang 
 */
package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.SimpleCustServiceInteractionViewDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CsrBusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

public class SimpleCustServiceInteractionViewListHandler extends ValueListHandler {
    private SimpleCustServiceInteractionViewDAO dao = null;
    private String tableName = "";

    private CommonQueryConditionDTO dto = null;
    private String orgIDs =null;
    private StringBuffer begin = new StringBuffer("select a.id id,a.type type,a.customerid customerid,a.createtime createtime,a.status status,a.agentname agentname,a.agenttelephone agenttelephone,a.agentcardtype agentcardtype,a.agentcardid agentcardid ");
    private StringBuffer selectStatement = new StringBuffer();

    public SimpleCustServiceInteractionViewListHandler() {
        this.dao = new SimpleCustServiceInteractionViewDAO();
    }

    public void setCriteria(Object dto1) throws ListHandlerException {
        LogUtility.log(SimpleCustServiceInteractionViewListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
        if (dto1 instanceof CommonQueryConditionDTO) 
      	    this.dto = (CommonQueryConditionDTO)dto1;
        else {
      	    LogUtility.log(SimpleCustServiceInteractionViewListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
        //以下的判断条件适用于p4的情况，在p5中不太适用所以注释掉---yangchen 2006/09/28----
        //if (!isParentCompany()) orgIDs = getOrgIDs();
        
        constructSelectQueryString(dto);
        //执行数据查询
        executeSearch(dao,true,true);
    }
    
    private void constructSelectQueryString(CommonQueryConditionDTO dto)throws ListHandlerException {
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
   
    private void constructSelectQueryHead(CommonQueryConditionDTO dto)throws ListHandlerException {
    	//由于目前的客户是按区域管理的，所以所有的查询都涉及到客户/客户地址/分区信息，判断条件也就没有必要，所以注释掉---yangchen 2006/09/28----
    	//if (dto.getStreet() ==0){
    	//	tableName =" t_custserviceinteraction a ,t_address c ";
    	//} else{
    	//根据输入的组织机构来建立组织管理区域的检索条件，输入dto.getFiliale为0的时候以当前错作员的组织为基准
		//tableName =" custserviceteractionView a,T_ADDRESS addr," +
		//			"(select org.orgid from t_organization org connect by prior org.orgid = org.parentorgid start with org.orgid = "+BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+ ") organization ";
    	//tableName =" custserviceteractionView a,T_ADDRESS addr," +
    	//			"(select id from t_districtsetting connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+ ")) districtset ";
		//根据输入的组织机构来查询该组织及下属组织创建的受理单,如果输入为零,则取当前操作员组织及其下属组织创建的受理单
		
    	tableName =" custserviceteractionView a,T_ADDRESS addr ";
    	
    	//组织机构
    	if(dto.getFiliale()!=0){
			tableName =tableName +
						", (select org.orgid from t_organization org connect by prior org.orgid = org.parentorgid start with org.orgid = "+dto.getFiliale()+") organization ";
			//tableName =" custserviceteractionView a,T_ADDRESS addr," +
			//			"(select id from t_districtsetting connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+dto.getFiliale()+ ")) districtset ";
		}
    	
    	//所在区
		if (dto.getStreet() !=0){
			tableName =tableName +
					", (select id from t_districtsetting " +
					" connect by prior id=belongto start with id ="+dto.getStreet()+") district ";
       		//appendStringWithDistrictID("addr.districtid",dto.getStreet(), selectStatement);
        }
		else{	//根据操作员所在组织的管理区域做限制条件
			tableName =tableName +
				", (select id from t_districtsetting " +
				" connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) district ";
		}
		
    	begin.append(" from "+tableName);
    }
    
    
    private void constructSelectQueryBody(CommonQueryConditionDTO dto) {
    	
    	selectStatement.append(" where 1=1 ");
    	
    	//if (dto.getStreet() !=0)  //所在区
    		selectStatement.append(" and district.Id= addr.districtid "); 
    	
    	if(dto.getFiliale()!=0) //组织机构
    		selectStatement.append(" and organization.orgid=a.createorgid ");
    	
    	//受理单编号
    	if(dto.getNo()!=null &&!"".equals(dto.getNo())){
			if(dto.getNo().indexOf(";")!=-1){
				makeSQLByIntFieldIn("a.id", dto.getNo(), selectStatement);
			}else{
				makeSQLByStringField("a.id", dto.getNo(), selectStatement);
			}
	    }
    	
        //受理单类型
    	makeSQLByStringFieldIn("a.type", dto.getType(), selectStatement);
    	
        //受理单状态
   		makeSQLByStringFieldIn("a.status", dto.getStatus(), selectStatement);
    	
        //操作员信息
    	 if (HelperCommonUtil.StringHaveContent(dto.getOperator())){
    		selectStatement.append(" and a.id in (select log.csiid from t_operator sub_oper,t_csiprocesslog log where log.operatorid =sub_oper.operatorid ");
			selectStatement.append(" and sub_oper.operatorname like '%").append(dto.getOperator()).append("%')");
    	 }
         //回访标志
    	 makeSQLByStringField("a.callbackflag", dto.getSpareStr1(), selectStatement);
   
    	 //付费标志
    	 makeSQLByStringField("a.paymentstatus", dto.getSpareStr2(), selectStatement);
    
         //用户证号  侯12-04修改,集团客户查询时,包括了子客户
    	 if(dto.getSpareStr5()!=null&&!dto.getSpareStr5().equals("")){
    		 appendStringWithGroupCustomerID("a.customerid",dto.getSpareStr5(),selectStatement);
    	 }
    	 //区分customerstyle 2007/06/26 由于性能问题 杨晨注释掉
    	 /*if(dto.getSpareStr12()!=null&&!dto.getSpareStr12().equals("")){
    		 appendStringWithGroupCustomerStyle("a.CUSTOMERID",dto.getSpareStr12(),selectStatement);
    	 }*/
    	 
    	 //取消原因
    	 makeSQLByStringField("a.statusreason", dto.getSpareStr9(), selectStatement);
         //受理原因
    	 makeSQLByStringField("a.createreason", dto.getSpareStr14(), selectStatement);
    	 
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
        		 selectStatement.append("and ( a.paymentstatus='D' And a.Status ='F') ");  
        		 if(dto.getNo()!=null &&!"".equals(dto.getNo()))
        			 selectStatement.append(" or (a.installationtype ='S' and a.paymentstatus='W' And a.status ='F' And a.id="+dto.getNo()+")");  
        		 else
        			 selectStatement.append(" or (a.installationtype ='S' and a.paymentstatus='W' And a.status ='F' )");  
        	 }
         }
         
        //以下内容是对客户/地址的查询条件的组合
 		selectStatement.append(" and ");
 		//***selectStatement.append(" a.a_customerid in (select CUSTOMERID from T_CUSTOMER customer ,T_ADDRESS addr");
 		selectStatement.append(" a.customeraddressid = addr.ADDRESSID");
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr7())) {
 			selectStatement.append(" and addr.DETAILADDRESS like '%" + dto.getSpareStr7() + "%' ");
        }
	   	 //区分customerstyle 2007/06/26 杨晨追加
	   	 if(dto.getSpareStr12()!=null&&CommonConstDefinition.CUSTOMERSTYLE_GROUP.equals(dto.getSpareStr12())){
	   		selectStatement.append(" and a.customerstyle ='"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"'");
	   	 }else{
	   		selectStatement.append(" and (a.customerstyle<>'"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"' Or a.customerstyle Is Null)");
	   		 
	   	 }
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr6())) {
            selectStatement.append(" and a.customername='");
            selectStatement.append(dto.getSpareStr6());
            selectStatement.append("'");
 		}
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr8())) {
            selectStatement.append(" and (a.customertelephone like '%");
            selectStatement.append(dto.getSpareStr8());
            selectStatement.append("%' or a.customermobile like '%" + dto.getSpareStr8() + "%') ");
        }
 		//客户类型条件
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr10())) {
            selectStatement.append(" and a.customertype='");
            selectStatement.append(dto.getSpareStr10());
            selectStatement.append("'");
        }
 		//对区域条件的整理
 		//所属组织
        try {
       	 //根据选择的区域条件来组织区域检索条件
       	 //if (dto.getStreet() !=0){
       	//	appendStringWithDistrictID("addr.districtid",dto.getStreet(), selectStatement);
        // }
       	
       	if (HelperCommonUtil.StringHaveContent(dto.getSpareStr26())&&(dto.getSpareStr26().equalsIgnoreCase("agentBk"))){
       	 selectStatement.append(" and a.customer_opensourcetype='")
       	 .append(CommonConstDefinition.OPENSOURCETYPE_PROXY).append("'");
       	 
        }
        
       	//根据输入的组织机构来建立组织管理区域的检索条件，输入dto.getFiliale为0的时候以当前错作员的组织为基准
//			appendStringWithOrgGovernedDistrict("addr.districtid",dto.getFiliale(),selectStatement);
       	//根据输入的组织机构来查询该组织及下属组织创建的受理单,如果输入为零,则取当前操作员组织及其下属组织创建的受理单
       
       	//selectStatement.append(" and addr.districtid=districtset.id");
		//appendStringWithOrgAndSubOrg("a.createorgid",dto.getFiliale(),selectStatement);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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