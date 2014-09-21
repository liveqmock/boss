<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.*"%>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.ServiceAccountWrap,com.dtv.oss.web.util.WebUtil"%>

<Script language=JavaScript>
<!--
function ChangeServiceAccountID()
{
	var txtServiceAccountID = document.frmPost.txtServiceAccountID.value;
	if(txtServiceAccountID=="")
		txtServiceAccountID = 0;
  document.FrameUS.submit_update_select('serviceaccount', document.frmPost.txtCustomerID.value+";"+txtServiceAccountID, 'txtCustProductID', '');
  
}
function query_submit()
{
	  document.frmPost.action = "customer_billing_rule_query.do";
   
    //document.frmTree.tableId.value ="6";
    document.frmPost.txtTo.value =500;
    document.frmPost.submit();
  
}
function customer_billing_rule_vindicate(strId){
	self.location.href="customer_billing_rule_update.screen?txtCBRID="+strId+"&isList=true";
}
function create_submit(strId){
	self.location.href = "customer_billing_rule_create.screen?txtCustomerID="+strId+"&isList=true";
	//document.frmPost.submit();
}
-->
</Script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> 正在获取内容。。。 </font>
			</td>
		</tr>
	</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>

<%
int custId= WebUtil.StringToInt(request.getParameter("txtCustomerID"));
int serviceAccountID = WebUtil.StringToInt(request.getParameter("txtServiceAccountID"));
int productID = WebUtil.StringToInt(request.getParameter("txtProductID"));
%>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820">
   <TR>
     <TD>
	<table border="0" align="center" cellpadding="0" cellspacing="10">
	   <tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">客户个性化费率</td>
	    </tr>
	</table>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	    <tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	    </tr>
	</table>
	<br>
	<form name="frmPost" method="post" action="">
		 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
       
    <tr>  
      <td class="list_bg2">业务帐户ID</td>
       <td class="list_bg1">
<%
    Map mapServiceAccount = Postern.getServiceAccountMapByCustID(custId,true,"<>'C'");
    pageContext.setAttribute("mapServiceAccount",mapServiceAccount);
    
%>
        <tbl:select name="txtServiceAccountID" set="mapServiceAccount" match="txtServiceAccountID" width="23" onchange="ChangeServiceAccountID()" />
      </td>
      <td class="list_bg2">产品名称</td>
      <td class="list_bg1">
<%
    //Map mapProduct = Postern.getProductMapByCustIDAndServiceAccountID(custId,serviceAccountID,null);
    //pageContext.setAttribute("mapProduct",mapProduct);
     Map mapCustProduct = Postern.getPsMapByCon(custId,serviceAccountID,0,true,"<>'C'");
     pageContext.setAttribute("mapCustProduct",mapCustProduct);
%>
        <tbl:select name="txtCustProductID" set="mapCustProduct" match="txtCustProductID" width="23" />
     </td>
    </tr>  
    <tr>  
       <td class="list_bg2">客户产品ID</td>
      <td colspan="" class="list_bg1" align="left">
<%
    //Map mapCustProduct = Postern.getPsMapByCon(custId,serviceAccountID,productID,true,"<>'C'");
    //pageContext.setAttribute("mapCustProduct",mapCustProduct);
%>
<input type="text" name="txtPsID" size="25" value="<tbl:writeparam name="txtPsID" />"  ></td>
      <td class="list_bg2"></td>
      <td class="list_bg1"></td>   
    </tr> 
    
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1">
	  	<table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		  	<pri:authorized name="customer_billing_rule_create.screen" >
				  <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
					<td><input name="Submit" type="button" class="button" value="新 增" onclick="javascript:create_submit('<tbl:writeparam name="txtCustomerID" />')"></td>
					<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </pri:authorized>
			<td width="22" height="20"></td>
			
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	   </table> 
	  </td>
  </tr>
  </table> 
  <tbl:hiddenparameters pass="txtCustomerID" />
			<input type="hidden" name="txtCCID" value="">
		  <input type="hidden" name="txtFrom" value="1">
      <input type="hidden" name="txtTo" value="500">
		  <input type="hidden" name="func_flag" value=""> 
		  <input type="hidden" name="txtActionType" size="22" value="">
			<input type="hidden" name="txtDoPost" size="22" value="FALSE">
   <rs:hasresultset>
			<BR>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" background="img/line_01.gif">
				<tr>
					<td><img src="img/mao.gif" width="1" height="1"></td>
				</tr>
			</table>
			<BR>
			<table width="98%" border="0" align="center" cellpadding="5"
				cellspacing="1" class="list_bg">
				<tr class="list_head">
					<td class="list_head" nowrap>流水号</td>
					<td class="list_head" nowrap>PSID</td>
					<td class="list_head" nowrap>产品名称</td>
					<td class="list_head" nowrap>业务帐户</td>
					<td class="list_head" nowrap>创建时间</td>
					<td class="list_head" nowrap>费用类型</td>
					<td class="list_head" nowrap>帐目类型</td>
					<td class="list_head" nowrap>费率</td>
					<td class="list_head" nowrap>有效期(起)</td>
					<td class="list_head" nowrap>有效期(止)</td>
					<td class="list_head" nowrap>状态</td>
				</tr>
				<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
					<%
					CustomerBillingRuleDTO custBillingRuleDto = (CustomerBillingRuleDTO) pageContext
							.getAttribute("oneline");
							System.out.println("+++"+custBillingRuleDto);
					CustomerProductDTO psDto = Postern.getCustomerProductDTOByPSID(custBillingRuleDto.getPsID());
					pageContext.setAttribute("ps",psDto);
					String proName = Postern.getProductNameByPSID(custBillingRuleDto.getPsID());
					String acctItemType = Postern.getAcctItemTypeByAcctItemTypeID(custBillingRuleDto.getAcctItemTypeID()); 
					if(acctItemType == null)acctItemType=custBillingRuleDto.getAcctItemTypeID();                 
					%>
					<tbl:printcsstr style1="list_bg1" style2="list_bg2"
						overStyle="list_over">
						<td align="center" width="50" nowrap><a href="javascript:customer_billing_rule_vindicate('<tbl:write name="oneline" property="id" />')" class="link12" ><tbl:write name="oneline" property="id" /></a></td>
						<td align="center" width="50" nowrap><tbl:write name="oneline" property="psID" /></td>
						<td align="center" width="80" nowrap><%=proName%></td>
						<td align="center" width="50" nowrap><tbl:write name="ps" property="serviceAccountID" /></td>
						<td align="center" width="60" nowrap><tbl:writedate name="oneline" property="dtCreate" /><br><tbl:writedate name="oneline" property="dtCreate" onlyTime="true"/></td>
						<td align="center" width="50" nowrap><d:getcmnname typeName="SET_F_BRFEETYPE"	match="oneline:FeeType" /></td>
						<td align="center" width="90" nowrap><%=acctItemType%></td>
						<td align="center" width="50" nowrap><tbl:write name="oneline" property="Value" /></td>
						<td align="center" width="60" nowrap><tbl:writedate name="oneline" property="startDate" /></td>
						<td align="center" width="60" nowrap><tbl:writedate name="oneline" property="endDate" /></td>
						<td align="center" width="30" nowrap><d:getcmnname typeName="SET_G_GENERALSTATUS"	match="oneline:status" /></td>
						
					</tbl:printcsstr>
				</lgc:bloop>
				<tbl:generatetoken />
				<tr>
					<td colspan="11" class="list_foot"></td>
				</tr>
			 
  			 <tr>
              <td align="right" class="t12" colspan="11" >
                 第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
                </rs:notlast>
                &nbsp;
                转到
               <input type="text" name="txtPage" class="page_txt">页 
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/>
             </td>
         </tr>
			</table>

		</rs:hasresultset>
</form>