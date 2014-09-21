<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.VoiceFriendPhoneNoDTO"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<SCRIPT language="JavaScript">

function delete_submit(strCustomerID)
{
	if(checkSeq()){
		if(confirm("确实要删除亲情号码吗？")){
			document.frmPost.action="friend_phoneno_delete.do";
   			document.frmPost.submit();
		}
	}else {
		alert("请选择要删除的亲情号码！")
		return false;
	}
}
function creat_submit()
{
	document.frmPost.action="friend_phoneno_creat.screen";
   	document.frmPost.submit();
}
function  checkSeq(){
	var count=0;
	if (document.frmPost.txtSeqNO.length!=null){
		for(var i=0;i<document.frmPost.txtSeqNO.length;i++){
			if(document.frmPost.txtSeqNO[i].checked){
				count++;
			}
		}
		if(count<=0){
			return false;
		}
		return true;
	}else{
		if(document.frmPost.txtSeqNO.checked)
			return true;
		return false;
	}
	
}
</SCRIPT>
<%
    String txtServiceAccountID= request.getParameter("txtServiceAccountID");
	String txtCustomerID = request.getParameter( "txtCustomerID");
	int cusPhoneNO=WebUtil.StringToInt((request.getParameter( "txtCusPhoneNO"))==null? "0" : (request.getParameter( "txtCusPhoneNO")));
	if(cusPhoneNO==0){
		cusPhoneNO=Postern.getServiceCodeByAcctServiceID(WebUtil.StringToInt(txtServiceAccountID));
	}
%>
<form name="frmPost" action="" method="post" >
<input type="hidden" name="txtServiceAccountID" value="<%=txtServiceAccountID%>">
<input type="hidden" name="txtCustomerID" value="<%=txtCustomerID%>">
<input type="hidden" name="txtCusPhoneNO" value="<%=cusPhoneNO%>">
<input type="hidden" name="txtActionType" value="<%=CommonKeys.FRIEND_PHONENO_DELETE%>" />
<input type="hidden" name="func_flag" value="118">

<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">亲情号码</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<rs:hasresultset>
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head"><input type="checkbox" name="selall"
						onclick="javascript:javascript:select_all_by_name(document.frmPost,'txtSeqNO', this.checked)"></td>
			<td class="list_head"><P align="center">序列号</P></td>
			<td class="list_head"><P align="center">用户电话号码</P></td>
			<td class="list_head"><P align="center">国际代码</P></td>
			<td class="list_head"><P align="center">地区代码</P></td>
			<td class="list_head"><P align="center">号  码</P></td>
		</tr>
		<%	  
		VoiceFriendPhoneNoDTO phoneDTO=(VoiceFriendPhoneNoDTO)pageContext.getAttribute("oneline");
		
		%>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

			<tbl:printcsstr style1="list_bg1" style2="list_bg2"	overStyle="list_over">
				<td align="center"><input type="checkbox" name="txtSeqNO"  value="<tbl:write name="oneline" property="seqno" />"  /></td>
				<td align="center"><tbl:write name="oneline" property="seqno" /></td>
				<td align="center"><%=cusPhoneNO%></td>
				<td align="center"><tbl:write name="oneline" property="countryCode"/></td>
				<td align="center"><tbl:write name="oneline" property="areaCode" /></td>
				<td align="center"><tbl:write name="oneline" property="phoneNo"/></td>
			</tbl:printcsstr>
			<input type="hidden" name="txtPhoneNo" value="<tbl:write name="oneline" property="phoneNo"/>">
		</lgc:bloop>
		<tr>
			<td colspan="7" class="list_foot"></td>
		</tr>
	</table>
	<br>
	<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>  
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:delete_submit()" value="删  除">
	   </td>
	   <td><img src="img/button2_l.gif" width="13" height="20"></td>

	   
	   <td width="20" >&nbsp;</td> 
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:creat_submit()" value="新  增">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	</tr>
</table>
</rs:hasresultset>
</form>
