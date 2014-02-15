<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>

<Script language=JavaScript>
  function back_submit()
	{
	   document.frmPost.action="batch_query_create.screen";
	   document.frmPost.submit();
 	}
  
	function check_frm()
	{
	   return true;
	}
	
	function frm_submit()
	{

	   document.frmPost.action="batch_query_create.do";
	   if (check_frm()) document.frmPost.submit();
	}


</Script>

<%
String title = "批量查询操作创建----信息确认";
if(CommonConstDefinition.YESNOFLAG_YES.equals(request.getParameter("templateFlag")))
	title = "批量查询模板操作创建----信息确认";
%>

<form name="frmPost" method="post" action="batch_query_create.do">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><%=title %></td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>


 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >
      <tr>
	  <td colspan="4" class="import_tit" align="center"><font size="2">批量查询操作基本信息</font></td>
      </tr>
     
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >结果集类型*</td>
	   <td width="33%" class="list_bg1"><font size="2"><d:getcmnname typeName="SET_B_QUERYTYPE" match="txtQueryType" /></font></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >查询操作名称*</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtQueryName" /><font size="2"></font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2"  ><div align="right">查询操作描述</div></td>
	   <td class="list_bg1"  colspan="3"><font size="2"><tbl:writeparam name="txtQueryDesc" /></font></td>
	</tr>
	
	<tr>
	   <td valign="middle" class="list_bg2" align="right" >执行方式*</td>
	   <td class="list_bg1"><font size="2"><d:getcmnname typeName="SET_B_SCHEDULETYPE" match="txtScheduleType" />
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >设定执行时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtScheduleTime" /> </font></td>
	 </tr>
 </table>
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>


<jsp:include page="batch_confirm_common.jsp" />

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1">
	<tr align="center">
	  <td>
			<table border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    
			    <td width="20" ></td>		
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			    <td><input name="Submit" type="button" class="button" value="上一步" onclick="javascript:back_submit()"></td>
			    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			   
			    <td width="20" ></td>		
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			    <td><input name="Submit" type="button" class="button" value="确认" onclick="javascript:frm_submit()"></td>
			    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  
				</tr>
			</table>     
 		</td>
  </tr>
</table>       

      <!--头记录参数区-->
      <tbl:hiddenparameters pass="txtQueryType/txtQueryName/txtQueryDesc/txtScheduleType/txtScheduleTime/txtTemplateFlag/templateFlag" />
      <!--客户信息参数区-->
      <tbl:hiddenparameters pass="txtCustTypeList/txtCustStatusList/txtCustCreateTime1/txtCustCreateTime2" />
      <tbl:hiddenparameters pass="txtCustOpenSourceTypeList/txtCustOpenSourceIDList/txtCustCurrentPoints1/txtCustCurrentPoints2" />
      <tbl:hiddenparameters pass="txtCustTotalPoints1/txtCustTotalPoints2/txtCustName/txtCustAddress" />
      <tbl:hiddenparameters pass="txtCustomerID/txtCustDistrictIDList" />
      <!--帐户信息参数区-->
      <tbl:hiddenparameters pass="txtAccountTypeList/txtAccountStatusList/txtAccountCashBalance1/txtAccountCashBalance2" />
      <tbl:hiddenparameters pass="txtAccountTokenBalance1/txtAccountTokenBalance2/txtAccountCreateTime1/txtAccountCreateTime2" />
      <tbl:hiddenparameters pass="txtAccountDistirctIDList/txtAccountMOPIDList/txtAccountInvoiceCreateTime1/txtAccountInvoiceCreateTime2" />
      <tbl:hiddenparameters pass="txtAccountInvoiceStatusList/txtAccountAddress/txtAccountStatusListValue/txtAccountStatusList" />
      <tbl:hiddenparameters pass="txtAccountBankAccountStatusListValue/txtAccountBankAccountStatusList" />
      <!--产品信息参数区-->
      <tbl:hiddenparameters pass="txtCPStatusList/txtCPProductIDList/txtCPCreateTime1/txtCPCreateTime2" />
      <tbl:hiddenparameters pass="txtCPCampaignStartTime1/txtCPCampaignStartTime2/txtCPCampaignEndTime1/txtCPCampaignEndTime2" />
      <tbl:hiddenparameters pass="txtCPCampaignIDList/txtCPClassIDListValue/txtCPClassIDList" />
              
      <input type="hidden" name="txtActionType" value="create" >
      <input type="hidden" name="func_flag"  value="8001" >
      <tbl:generatetoken />
</form>