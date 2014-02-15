<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,java.util.*" %>
<%@ page import="com.dtv.oss.dto.OperatorDTO,com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%@ page import="com.dtv.oss.web.util.CurrentOperator,com.dtv.oss.web.util.LogonWebCurrentOperator" %>
<%@ page import="com.dtv.oss.dto.QueryDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>


<%!
	public String getValue(Map map,String keyList,boolean haveWrap){
		String result="";
		if(keyList==null || "".equals(keyList)){
			return result;
		}
		if(map==null || map.isEmpty())
			return result;
		if(haveWrap)
			keyList=keyList.replaceAll("'","");

		
		String keys[]=keyList.split(",");
		System.out.println(keys);
		
		for(int i=0;i<keys.length;i++){
			if(map.containsKey(keys[i])){
				if(!"".equals(result))
					result=result + ",";
					
				if(((String)map.get(keys[i])).indexOf("|")!=-1)
				 	result=result + ((String)map.get(keys[i])).substring(1);
				else
					result=result + (String)map.get(keys[i]);
			}
		}

		result=result.replaceAll("-","");
		
		return result;
	};
	
	public String getRemoveWrap(String keyList){
		String result="";
		if(keyList==null || "".equals(keyList))
			return result;
		else
			result=keyList.replaceAll("'","");
	
		return result;
	};
%>

<table width="98%" border="0" cellspacing="5" cellpadding="5">
<tr> 
  <td width="100%"><div align="center"> 
      <p align="center" class="title1">正在获取批量查询信息，请稍候。。。</strong></p>
    </div></td>
  
</tr>
</table>

<form name="frmPost" method="post" action="batch_query_show_detail.screen" >

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">      
<%
    QueryDTO dto = (QueryDTO)pageContext.getAttribute("oneline");
    pageContext.setAttribute("DTO", dto);
    
    Map map=null;
    String result="";
%> 
         
<table align="center" width="100%" border="0"  style="display:none">
<tr><td>
 
      <!--头记录参数区-->
      <tbl:hidden name="txtQueryType" value="DTO:queryType" />
      <tbl:hidden name="txtQueryName" value="DTO:queryName" />
      <tbl:hidden name="txtQueryDesc" value="DTO:queryDesc" />
      <tbl:hidden name="txtScheduleType" value="DTO:scheduleType" />
      <input type="hidden" name="txtScheduleTime" value="<tbl:writedate name="DTO" property="scheduleTime" includeTime="true" />">
 	
      <tbl:hidden name="txtQueryID" value="DTO:queryId" />
      <tbl:hidden name="txtStatus" value="DTO:status" />
      <tbl:hidden name="txtCreateOperatorID" value="DTO:createOperatorId" />
      <input type="hidden" name="txtCreateTime" value="<tbl:writedate name="DTO" property="createTime" includeTime="true"/>">      
      <input type="hidden" name="txtPerformTime" value="<tbl:writedate name="DTO" property="performTime" includeTime="true"/>">
      <input type="hidden" name="txtDtLastmod" value="<tbl:write name="DTO" property="dtLastmod"/>">
      <!--客户信息参数区--> 
      <input type="hidden" name="txtCustTypeList" value="<%=getRemoveWrap(dto.getCustTypeList()) %>" >
      <%
      	   map=Postern.getHashKeyValueByName("SET_C_CUSTOMERTYPE");     	   
      	   result=this.getValue(map,dto.getCustTypeList(),true);   
      %>
      <input type="hidden" name="txtCustTypeListValue" value="<%=result %>">
      
      <input type="hidden" name="txtCustStatusList" value="<%=getRemoveWrap(dto.getCustStatusList()) %>" >
       <%
      	   map=Postern.getHashKeyValueByName("SET_C_CUSTOMERSTATUS");
      	   result=this.getValue(map,dto.getCustStatusList(),true);   
      %>
      <input type="hidden" name="txtCustStatusListValue" value="<%=result %>">
      
      <input type="hidden" name="txtCustCreateTime1" value="<tbl:writedate name="DTO" property="custCreateTime1" />">      
      <input type="hidden" name="txtCustCreateTime2" value="<tbl:writedate name="DTO" property="custCreateTime2" />">
      <input type="hidden" name="txtCustOpenSourceTypeList" value="<%=getRemoveWrap(dto.getCustOpenSourceTypeList()) %>" >
      <%
      	   map=Postern.getHashKeyValueByName("SET_C_CUSTOPENSOURCETYPE");
      	   System.out.println("here0000");
      	   System.out.println("null :" + dto.getCustOpenSourceTypeList());
      	   result=this.getValue(map,dto.getCustOpenSourceTypeList(),true);   
      	   System.out.println("here1111");
      %>
      <input type="hidden" name="txtCustOpenSourceTypeListValue" value="<%=result %>">
      
      <input type="hidden" name="txtCustOpenSourceIDList" value="<%=getRemoveWrap(dto.getCustOpenSourceIdList()) %>" >
      <%
      	   System.out.println("here222");
           String parentType=dto.getCustOpenSourceTypeList();
           if(parentType==null)
           	parentType="";
           parentType=parentType.replaceAll("'","");
      	   map=Postern.getOrgIDAndOrgNameByParentOrgIDs(parentType);
      	   result=this.getValue(map,dto.getCustOpenSourceIdList(),true);   
      %>
      <input type="hidden" name="txtCustOpenSourceIDListValue" value="<%=result %>">
      
      <tbl:hidden name="txtCustCurrentPoints1" value="DTO:custCurrentPoints1" />
      <tbl:hidden name="txtCustCurrentPoints2" value="DTO:custCurrentPoints2" />      
      <tbl:hidden name="txtCustTotalPoints1" value="DTO:custTotalPoints1" />
      <tbl:hidden name="txtCustTotalPoints2" value="DTO:custTotalPoints2" />
      <tbl:hidden name="txtCustName" value="DTO:custName" />
      <tbl:hidden name="txtCustAddress" value="DTO:custAddress" />
      <tbl:hidden name="txtCustomerID" value="DTO:customerId" />
      <input type="hidden" name="txtCustDistrictIDList" value="<%=getRemoveWrap(dto.getCustDistrictIdList()) %>" >
      <%
           LogonWebCurrentOperator wrapOper = (LogonWebCurrentOperator)CurrentOperator.GetCurrentOperator(pageContext.getSession());
  	   if (wrapOper!=null)
           {
           	OperatorDTO dtoOper = (OperatorDTO)wrapOper.getCurrentOperator();
           	map=Postern.getChargeDistrictSettingByOpOrgID(dtoOper.getOrgID());
           }
      	   result=this.getValue(map,dto.getCustDistrictIdList(),true);   
      %>
      <input type="hidden" name="txtCustDistrictIDListValue" value="<%=result %>">
      
      <!--帐户信息参数区-->
      <input type="hidden" name="txtAccountTypeList" value="<%=getRemoveWrap(dto.getAccountTypeList()) %>" >
      <%
      	   map=Postern.getHashKeyValueByName("SET_F_ACCOUNTTYPE");
      	   result=this.getValue(map,dto.getAccountTypeList(),true);   
      %>
      <input type="hidden" name="txtAccountTypeListValue" value="<%=result %>">
      
      <input type="hidden" name="txtAccountStatusList" value="<%=getRemoveWrap(dto.getAccountStatusList()) %>" >
      <%
      	   map=Postern.getHashKeyValueByName("SET_F_ACCOUNTSTATUS");
      	   result=this.getValue(map,dto.getAccountStatusList(),true);   
      %>
      <input type="hidden" name="txtAccountStatusListValue" value="<%=result %>">
      
      <tbl:hidden name="txtAccountCashBalance1" value="DTO:accountCashBalance1" />
      <tbl:hidden name="txtAccountCashBalance2" value="DTO:accountCashBalance2" />
      <tbl:hidden name="txtAccountTokenBalance1" value="DTO:accountTokenBalance1" />
      <tbl:hidden name="txtAccountTokenBalance2" value="DTO:accountTokenBalance2" />
      <input type="hidden" name="txtAccountCreateTime1" value="<tbl:writedate name="DTO" property="accountCreateTime1" />">      
      <input type="hidden" name="txtAccountCreateTime2" value="<tbl:writedate name="DTO" property="accountCreateTime2" />">
      <input type="hidden" name="txtAccountDistirctIDList" value="<%=getRemoveWrap(dto.getAccountDistrictIdList()) %>" >
      <%

           LogonWebCurrentOperator wrapOper2 = (LogonWebCurrentOperator)CurrentOperator.GetCurrentOperator(pageContext.getSession());
  	   if (wrapOper2!=null)
           {
           	OperatorDTO dtoOper = (OperatorDTO)wrapOper2.getCurrentOperator();
           	map=Postern.getChargeDistrictSettingByOpOrgID(dtoOper.getOrgID());
           }
      	   result=this.getValue(map,dto.getAccountDistrictIdList(),true);   
      %>
      <input type="hidden" name="txtAccountDistirctIDListValue" value="<%=result %>">
      
      <input type="hidden" name="txtAccountMOPIDList" value="<%=getRemoveWrap(dto.getAccountMopIdList()) %>" >
      <%
      	   map=Postern.getOpeningMop();
      	   result=this.getValue(map,dto.getAccountMopIdList(),true);   
      %>
      <input type="hidden" name="txtAccountMOPIDListValue" value="<%=result %>">
      
      <input type="hidden" name="txtAccountInvoiceCreateTime1" value="<tbl:writedate name="DTO" property="accountInvoiceCreateTime1" />">      
      <input type="hidden" name="txtAccountInvoiceCreateTime2" value="<tbl:writedate name="DTO" property="accountInvoiceCreateTime2" />">
      <input type="hidden" name="txtAccountInvoiceStatusList" value="<%=getRemoveWrap(dto.getAccountInvoiceStatusList()) %>" >
      <%
      	   map=Postern.getHashKeyValueByName("SET_F_INVOICESTATUS");
      	   result=this.getValue(map,dto.getAccountInvoiceStatusList(),true);   
      %>
      <input type="hidden" name="txtAccountInvoiceStatusListValue" value="<%=result %>">

      <input type="hidden" name="txtAccountBankAccountStatusList" value="<%=getRemoveWrap(dto.getBankAccountStatusList()) %>" >
      <%
      	   map=Postern.getHashKeyValueByName("SET_F_ACCOUNTBANKACCOUNTSTATUS");
      	   result=this.getValue(map,dto.getBankAccountStatusList(),true);   
      %>
      <input type="hidden" name="txtAccountBankAccountStatusListValue" value="<%=result %>">
      
      <tbl:hidden name="txtAccountAddress" value="DTO:accountAddress" />

      <!--产品信息参数区-->
      <input type="hidden" name="txtCPStatusList" value="<%=getRemoveWrap(dto.getCpStatusList()) %>" >
      <%
      	   map=Postern.getHashKeyValueByName("SET_C_CUSTOMERPRODUCTPSTATUS");
      	   result=this.getValue(map,dto.getCpStatusList(),true);   
      %>
      <input type="hidden" name="txtCPStatusListValue" value="<%=result %>">
      
      <input type="hidden" name="txtCPProductIDList" value="<%=getRemoveWrap(dto.getCpProductIdList()) %>" >
      <%
      	   map=Postern.getAllProductIDAndName();
      	   result=this.getValue(map,dto.getCpProductIdList(),true);   
      %>
      <input type="hidden" name="txtCPProductIDListValue" value="<%=result %>">
      
      <input type="hidden" name="txtCPCreateTime1" value="<tbl:writedate name="DTO" property="cpCreateTime1" />">      
      <input type="hidden" name="txtCPCreateTime2" value="<tbl:writedate name="DTO" property="cpCreateTime2" />">
      <input type="hidden" name="txtCPCampaignStartTime1" value="<tbl:writedate name="DTO" property="cpCampaignStartTime1" />">      
      <input type="hidden" name="txtCPCampaignStartTime2" value="<tbl:writedate name="DTO" property="cpCampaignStartTime2" />">
      <input type="hidden" name="txtCPCampaignEndTime1" value="<tbl:writedate name="DTO" property="cpCampaignEndTime1" />">      
      <input type="hidden" name="txtCPCampaignEndTime2" value="<tbl:writedate name="DTO" property="cpCampaignEndTime2" />"> 
      <tbl:hidden name="txtTemplateFlag" value="DTO:templateFlag" />   
      <input type="hidden" name="txtCPCampaignIDList" value="<%=getRemoveWrap(dto.getCpCampaignIdList()) %>" >
      <%
      	   map=Postern.getAllCampaignIDAndName();
      	   result=this.getValue(map,dto.getCpCampaignIdList(),true);   
      %>
      <input type="hidden" name="txtCPCampaignIDListValue" value="<%=result %>">

      <input type="hidden" name="txtCPClassIDList" value="<%=getRemoveWrap(dto.getProductClassList()) %>" >
      <%
      	   map=Postern.getHashKeyValueByName("SET_P_PRODUCTCLASS");
      	   result=this.getValue(map,dto.getProductClassList(),true);   
      %>
      <input type="hidden" name="txtCPClassIDListValue" value="<%=result %>">
      
      <tbl:hidden name="txtDtLastmod" value="DTO:dtLastmod" /> 
</td></tr>
</table>

   </lgc:bloop> 
   
</form>

<Script language=Javascript>
var  txtShowFlag="<tbl:writeparam name="txtShowFlag" />";
var templateFlag="<tbl:writeparam name="templateFlag" />";

if(txtShowFlag!="Y")//根据模板创建批量查询
{	
	if(templateFlag=="" || "<%=CommonConstDefinition.YESNOFLAG_NO%>"==templateFlag)
		document.frmPost.txtTemplateFlag.value = "<%=CommonConstDefinition.YESNOFLAG_NO%>"; 
	document.frmPost.action = "batch_query_create.screen"; 
	document.frmPost.submit();  
}else//批量查询维护
{
	document.frmPost.action = "batch_query_show_detail.screen?templateFlag=all"; 
	document.frmPost.submit(); 
}  
</Script>