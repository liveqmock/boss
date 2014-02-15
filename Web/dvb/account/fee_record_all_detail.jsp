<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>

<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO"%>

<br>

<form name="frmPost" method="post" action="">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">费用记录详细信息</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>

 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >

  <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true" >
  <%
       AccountItemDTO dto = (AccountItemDTO)pageContext.getAttribute("oneline");
       pageContext.setAttribute("DTO",dto);
    
       String strOpName=Postern.getOperatorNameByID(dto.getOperatorID());
       if(strOpName==null)
    	   strOpName="";
    	   
       String strCustName=Postern.getCustomerNameByID(dto.getCustID());
       if(strCustName==null)
       	   strCustName="";
       	   
       /*String strOrgName=Postern.getOrgNameByCustomerID(dto.getCustID());
       if(strOrgName==null)
       	   strOrgName="";*/
       	  
       String strProductName=Postern.getProductNameByPSID(dto.getPsID());
       if(strProductName==null)
           strProductName="";
           
       String strAcctItemTypeName=Postern.getAcctItemTypeByAcctItemTypeID(dto.getAcctItemTypeID());
       	 if(strAcctItemTypeName==null)
    	     strAcctItemTypeName="";
    	
    	String strBillingCycleName=Postern.getBillingCycleNameByID(dto.getBillingCycleID());
    	    if(strBillingCycleName==null)
    	        strBillingCycleName="";
      String districtDesc=Postern.getDistrictDescByCustomerID(dto.getCustID()); 
      
  %>
  <tr>
    <td class="list_bg2"><div align="right">流水号</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="aiNO" /></td>
    <td class="list_bg2"><div align="right">金额</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="value" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">创建时间</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="createTime" /></td>
    <td class="list_bg2"><div align="right">状态</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:status" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">客户证号</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="custID" /></td>
    <td class="list_bg2"><div align="right">客户姓名</div></td>
    <td class="list_bg1"><%=strCustName %></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">帐户号</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="acctID" /></td>
    <td class="list_bg2"><div align="right">业务帐户</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="serviceAccountID" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">产品</div></td>
    <td class="list_bg1"><%=strProductName%></td>
    <td class="list_bg2"><div align="right">PSID</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="psID" /></td>
  </tr>
 
  <tr>
    <td class="list_bg2"><div align="right">组织机构</div></td>
    <td class="list_bg1"><tbl:WriteOrganizationInfo name="DTO" property="orgID" /></td>
    <td class="list_bg2"><div align="right">操作员</div></td>
    <td class="list_bg1"><%=strOpName%></td>
  </tr>
 
  <tr>
    <td class="list_bg2"><div align="right">费用类型</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_BRFEETYPE" match="DTO:feeType" /></td>
    <td class="list_bg2"><div align="right">帐目类型</div></td>
    <td class="list_bg1"><%=strAcctItemTypeName%></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">计费周期</div></td>
    <td class="list_bg1"><%=strBillingCycleName%></td>
    <td class="list_bg2"><div align="right">BrID</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="brID" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">日期1</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="date1" /></td>
    <td class="list_bg2"><div align="right">日期2</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="date2" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">关联单据</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTREFERTYPE" match="DTO:referType" /></td>
    <td class="list_bg2"><div align="right">单据号</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="referID" /></td>
  </tr>
    <td class="list_bg2"><div align="right">调帐标志</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="DTO:adjustmentFlag" /></td>
    <td class="list_bg2"><div align="right">调帐单号</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="adjustmentNO" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">销帐标志</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_SETOFFFLAG" match="DTO:setOffFlag" /></td>
    <td class="list_bg2"><div align="right">销帐金额</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="setOffAmount" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">入帐标志</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="DTO:invoiceFlag" /></td>
    <td class="list_bg2"><div align="right">帐单号</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="invoiceNO" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">创建来源</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="DTO:creatingMethod" /></td>
    <td class="list_bg2"><div align="right">源记录号</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="sourceRecordID" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">强制预存标志</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="DTO:forcedDepositFlag" /></td>
    <td class="list_bg2"><div align="right">批号</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="batchNO" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">CCID</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="ccID" /></td>
    <td class="list_bg2"><div align="right">费用摊消计划ID</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="feeSplitPlanID" /></td>
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">费用付费周期类型</div></td>
  	<td class="list_bg1"><d:getcmnname typeName="SET_F_RFBILLINGCYCLEFLAG" match="DTO:rfBillingCycleFlag" /></td>
    <td class="list_bg2"><div align="right">客户所在区域</div></td>
    <td class="list_bg1" ><%=districtDesc%></td>
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">备注 </div></td>
    <td class="list_bg1" colspan="3">
        <tbl:write  name="DTO" property="comments" />
    </td>
  </tr>	 
 </lgc:bloop>
  
 </table>
 
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<BR>

<table border="0" cellspacing="0" cellpadding="0">
  <tr>
  
    <bk:canback url="fee_record_all_query.do/fee_detailrecord_conditionQuery.do" >  
            <td width="20" ></td>         
            <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
            <td background="img/button_bg.gif" ><a href="<bk:backurl property="fee_record_all_query.do/fee_detailrecord_conditionQuery.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
    </bk:canback> 
</tr>
</table>     
 
 </form>