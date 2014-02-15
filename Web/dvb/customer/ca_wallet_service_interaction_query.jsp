<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,java.math.BigDecimal,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.ServiceAccountDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
function query_submit()
{
	if (!checkPlainNum(document.frmPost.txtServiceAccountId,false,64,"业务帐户ID"))
		return false;
	if (!checkPlainNum(document.frmPost.txtWalletId,false,64,"小钱包序号"))
		return false;
	document.frmPost.submit();
}

function wallet_manager(a,b,c,d,e)
{
	document.frmPostWallet.txtServiceAccountId.value=a;
	document.frmPostWallet.txtServiceAccountName.value=b;
	document.frmPostWallet.txtStatus.value=c;
	document.frmPostWallet.txtDtLastmod.value=d;
	document.frmPostWallet.txtScSerialNo.value=e;
	document.frmPostWallet.submit();
}

function wallet_create(said,scSerialNo){
	document.frmPostWallet.txtServiceAccountID.value = said;
	document.frmPostWallet.txtScSerialNo.value = scSerialNo;
	document.frmPostWallet.action = "menu_ippv_create.do";
	document.frmPostWallet.txtActionType.value = "walletCreate";
	document.frmPostWallet.submit();
}

</script>	

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">业务帐户查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPostWallet" action="ca_wallet_query.do" method="post" >
<input type="hidden" name="txtServiceAccountId" >
<input type="hidden" name="txtServiceAccountID" >
<input type="hidden" name="txtServiceAccountName" >
<input type="hidden" name="txtStatus" >
<input type="hidden" name="txtDtLastmod" >
<input type="hidden" name="txtScSerialNo" >
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />">
<input type="hidden" name="txtActionType" >
</form>

<%
	pageContext.setAttribute("CAWalletDefineMap" ,Postern.getCAWalletDefineMap());
	pageContext.setAttribute("AllSAName" ,Postern.getAllSAName());
%>
<form name="frmPost" action="ca_wallet_service_interaction_query.do" method="post" >
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />">
  	<tr>
    	<td class="list_bg2"><div align="right">业务帐户ID</div></td>
    	<td class="list_bg1">
    	<input name="txtServiceAccountId" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtServiceAccountId" />"></td>
    	<td class="list_bg2"><div align="right">设备序列号</div></td>
    	<td class="list_bg1">
    	<input name="txtScSerialNo" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtScSerialNo" />"></td>
    </tr>
  	<tr>
    	<td class="list_bg2"><div align="right">小钱包序号</div></td>
    	<td class="list_bg1">
    	<input name="txtWalletId" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtWalletId" />"></td>
    	<td class="list_bg2"><div align="right">小钱包名称</div></td>
    	<td class="list_bg1">
    		<tbl:select name="txtCaWalletCode" set="CAWalletDefineMap" match="txtCaWalletCode" width="23" />
    </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <!--<pri:authorized name="bank_deduction_header_query.do">-->
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
		  <!--</pri:authorized>-->
	  </table></td>
	</tr>
</table>
	<input type="hidden" name="txtFrom" size="22" value="1">
	<input type="hidden" name="txtTo" size="22" value="10">
	<input type="hidden" name="txtActionType" value="wallet_charge">

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">业务帐户ID</td>
    <td class="list_head">业务名称 </td>
    <td class="list_head">状态 </td>
    <td class="list_head">创建日期 </td>
    <td class="list_head">设备序列号</td>
    <td class="list_head">操作 </td>
  </tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
					ServiceAccountDTO dto = (ServiceAccountDTO) pageContext.getAttribute("oneline");
					String strUrl="service_account_query_result_by_sa.do?txtCustomerID="+dto.getCustomerID()+"&txtServiceAccountID="+dto.getServiceAccountID();
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
    <td align="center"><tbl:write name="oneline" property="serviceAccountID"/></td>
    <td align="center"><d:getcmnname typeName="AllSAName" match="oneline:serviceID"/></td>
    <td align="center"><d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="oneline:status"/></td>
    <td align="center" width="10%"><tbl:writedate name="oneline" property="createTime" includeTime="true" /></td>
    <td align="center"><tbl:write name="oneline" property="description"/></td>
    <td align="center">
    				<a href="javascript:wallet_create('<tbl:write name="oneline" property="serviceAccountID"/>','<tbl:write name="oneline" property="description"/>')" class="link12" >创&nbsp;建</a>
    				<a href="javascript:wallet_manager('<tbl:write name="oneline" property="serviceAccountID"/>','<d:getcmnname typeName="AllSAName" match="oneline:serviceID"/>','<d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="oneline:status"/>','<tbl:write name="oneline" property="dtLastmod"/>','<tbl:write name="oneline" property="description"/>')" class="link12" >小钱包管理</a>  
    </td>
  </tr>
  
	</tbl:printcsstr>
	</lgc:bloop>
	<tbl:generatetoken />

  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
  
            <tr>
              <td align="right" class="t12" colspan="7" >
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
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>   

	</rs:hasresultset>
	</form>               
</td>
</tr>
</table>