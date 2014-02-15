<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,java.math.BigDecimal,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.CAWalletDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="java.sql.Timestamp" %>

<script language=javascript>
function query_submit()
{

	document.frmPost.submit();
}
function wallet_manager(a,b,c,d,e,f,g,h)
{
	document.frmPostCharge.txtServiceAccountId.value=a;
	document.frmPostCharge.txtServiceAccountName.value=b;
	document.frmPostCharge.txtStatus.value=c;
	document.frmPostCharge.txtDtLastmod.value=d;
	document.frmPostCharge.txtWalletId.value=e;
	document.frmPostCharge.txtCaWalletName.value=f;
	document.frmPostCharge.txtTotalPoint.value=g;
	document.frmPostCharge.status.value=h;
	document.frmPostCharge.submit();
}
function wallet_create(said,scSerialNo){
	document.frmPostCharge.txtServiceAccountID.value = said;
	document.frmPostCharge.txtScSerialNo.value = scSerialNo;
	document.frmPostCharge.action = "menu_ippv_create.do";
	document.frmPostCharge.txtActionType.value = "walletCreate";
	document.frmPostCharge.submit();
}
function wallet_charge(said,caWalletCode,walletId,scSerialNo){
	document.frmPostCharge.txtServiceAccountID.value = said;
	document.frmPostCharge.txtCAWalletCode.value = caWalletCode;
	document.frmPostCharge.txtWalletId.value = walletId;
	document.frmPostCharge.txtScSerialNo.value = scSerialNo;
	document.frmPostCharge.action = "ippv_create.do";
	document.frmPostCharge.txtActionType.value = "walletCharge";
	document.frmPostCharge.submit();
}
</script>	

<form name="frmPostCharge" action="ca_wallet_charge_record_query.do" method="post" >

<input type="hidden" name="txtServiceAccountId" >
<input type="hidden" name="txtServiceAccountName" >
<input type="hidden" name="txtStatus" >
<input type="hidden" name="txtDtLastmod" >
<input type="hidden" name="txtWalletId" >
<input type="hidden" name="txtCaWalletName" >
<input type="hidden" name="txtTotalPoint" >
<input type="hidden" name="status" >
<input type="hidden" name="txtCAWalletCode" value="" >
<input type="hidden" name="txtServiceAccountID" value = "">
<input type="hidden" name="txtActionType" value = "">
<input type="hidden" name="txtCsiType" value ="CAC">
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />">
<input type="hidden" name="txtScSerialNo" value="">
</form>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">小钱包管理</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<%
	pageContext.setAttribute("CAWalletDefineMap" ,Postern.getCAWalletDefineMap());
%>
<form name="frmPost" action="ca_wallet_charge_record_query.do" method="post" >
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  	<tr>
    	<td class="list_bg2"><div align="right">业务帐户ID</div></td>
    	<td class="list_bg1">
    	<input name="txtServiceAccountId" value="<tbl:writeparam name="txtServiceAccountId" />" readonly class="textgray" size="25" maxlength="10"></td>
    	<td class="list_bg2"><div align="right">业务名称</div></td>
    	<td class="list_bg1">
    	<input name="txtServiceAccountName" value="<tbl:writeparam name="txtServiceAccountName" />"  readonly class="textgray" size="25" maxlength="10"></td>
    </tr>
  	<tr>
    	<td class="list_bg2"><div align="right">状态</div></td>
    	<td class="list_bg1">
    	<input name="txtStatus" value="<tbl:writeparam name="txtStatus" />" readonly class="textgray" size="25" maxlength="10"></td>
    	<td class="list_bg2"><div align="right">最后更新时间</div></td>
    	<td class="list_bg1">
    	<%
    	String DtLastmod = request.getParameter("txtDtLastmod").substring(0,19);
    	%>
    	<input name="txtDtLastmod" value="<%=DtLastmod%>"  readonly class="textgray" size="25" maxlength="10" ></td>
    </tr>
<%
	String ServiceAccountID = request.getParameter("txtServiceAccountId");
    String productDesc = "";
    if(!"".equals(ServiceAccountID)){
    productDesc = Postern.getCustomerProductDescByServiceAccountID(Integer.parseInt(ServiceAccountID));
    //pageContext.setAttribute("oneline",Postern.getCAWalletInfoByServiceAccountID(Integer.parseInt(ServiceAccountID)) );
    }
%>
	<tr>
		<td class="list_bg2"><div align="right">客户产品列表</div></td>
	  	<td class="list_bg1" colspan="3">
	  	<%=productDesc%></td>
	</tr>
</table>

	<input type="hidden" name="txtFrom" size="22" value="1">
	<input type="hidden" name="txtTo" size="22" value="10">

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>


<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">ID</td>
    <td class="list_head">小钱包名称</td>
    <td class="list_head">设备序列号</td>
    <td class="list_head">状态</td>
    <td class="list_head">创建时间</td>
    <td class="list_head">已充值点数</td>
    <td class="list_head">操作</td>
  </tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
					CAWalletDTO dto = (CAWalletDTO) pageContext.getAttribute("oneline");
					String strStatus = dto.getStatus();
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
    <td align="center"><tbl:write name="oneline" property="walletId"/></td>
    <td align="center"><d:getcmnname typeName="CAWalletDefineMap" match="oneline:caWalletCode"/></td>
    <td align="center"><tbl:write name="oneline" property="scSerialNo"/></td>
    <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status"/></td>
    <td align="center" width="10%"><tbl:writedate name="oneline" property="dtCreate" includeTime="true" /></td>
	<td align="center"><tbl:write name="oneline" property="totalPoint"/></td>
    <td align="center">
        <% if("V".equals(strStatus)){ %>
    	<a href = "javascript:wallet_charge(<%=ServiceAccountID%>,'<tbl:write name="oneline" property="caWalletCode"/>','<tbl:write name="oneline" property="walletId"/>','<tbl:write name="oneline" property="scSerialNo"/>')">充&nbsp值</a>
    	<% } %>
    	<a href="javascript:wallet_manager('<tbl:writeparam name="txtServiceAccountId" />','<tbl:writeparam name="txtServiceAccountName" />','<tbl:writeparam name="txtStatus" />','<tbl:writeparam name="txtDtLastmod" />','<tbl:write name="oneline" property="walletId"/>','<d:getcmnname typeName="CAWalletDefineMap" match="oneline:caWalletCode"/>','<tbl:write name="oneline" property="totalPoint"/>','<d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status"/>')" class="link12" >充值记录</a>
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
	
	<table width="98%" border="0" align="center" cellpadding="5"
                                cellspacing="1" class="list_bg">
                                <tr align="center">
                                        <td >
                                        <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                				<td width="20"></td>
                                                                <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
                                                                <td background="img/button_bg.gif"><a href="<bk:backurl property='ca_wallet_service_interaction_query.do' />" class="btn12">返&nbsp;回</a>
                                                                </td>
                                                                <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
                                                				
                                                				<td width="20"></td>
                                                                <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
                                                                <td background="img/button_bg.gif"><a href="javascript:wallet_create(<%=ServiceAccountID%>,'<tbl:writeparam name="txtScSerialNo"/>')" class="btn12">创&nbsp;建</a>
                                                                </td>
                                                                <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
                                                                
                                                </tr>
                                        </table>
                                        </td>
                                </tr>
                        </table>
                        
	</form>               
</td>
</tr>
</table>