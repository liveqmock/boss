<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.ContractDTO" %>
<script language=javascript>

function query_submit()
{
	document.frmPost.submit();
}

function view_detail_click(strId)
{
	self.location.href="contract_detail.do?txtContractNo="+strId;
}

function view_open_click(strId)
{	
	if(strId!=null&&strId!=""){
		document.frmPost.action="group_customer_open_info.do?txtContractNo="+strId;
		document.frmPost.submit();	
	}
}

</script>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">集团客户开户合同查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="group_customer_open_contract_query_result.do" method="post" >    
		  <input type="hidden" name="txtFrom" size="22" value="1">
      <input type="hidden" name="txtTo" size="22" value="10">
      <input type="hidden" name="txtActionType" value="openInfo">
      <input type="hidden" name="txtStatus" value="<%=CommonKeys.CONTRACTSTATUS_EFFECT%>">
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=18%><div align="right">合同编号</div></td>
    <td class="list_bg1" width=30%><input name="txtContractNo" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtContractNo" />"></td>
    <td class="list_bg2" width=18%><div align="right">合同名称</div></td>
    <td class="list_bg1" width=30%><input name="txtName" type="text" class="text" maxlength="10" size="22" value="<tbl:writeparam name="txtName" />"></td>
  </tr>
</table>

	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr align="center">
		  <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
	  	</table></td>
		</tr>
	</table>      
<br>      
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<rs:hasresultset>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">合同编号</td>
    <td class="list_head">合同名称</td>
    <td class="list_head">有效起始日期</td>
    <td class="list_head">有效结束日期</td>
    <td class="list_head">月租费总额</td>
    <td class="list_head">一次性费总额</td>
    <td class="list_head">预付总额</td>
    <td class="list_head">用户数</td>
    <td class="list_head">状态</td>
    <td class="list_head">动作</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	<%
	String status = "";
	ContractDTO contractDTO = (ContractDTO)pageContext.getAttribute("oneline");
	if(contractDTO != null)
		status = contractDTO.getStatus();
	%>
    <td><a href="javascript:view_detail_click('<tbl:write name="oneline" property="contractNo" />')" class="link12" ><tbl:write name="oneline" property="contractNo" /></a></td>
    <td align="center"><tbl:write name="oneline" property="name" /></td>
    <td><tbl:writedate name="oneline" property="datefrom" /></td>
    <td ><tbl:writedate name="oneline" property="dateto" /></td>
    <td align="right"><tbl:write name="oneline" property="rentFeeTotal" /></td>
    <td align="right"><tbl:write name="oneline" property="oneOffFeeTotal" /></td>
    <td align="right"><tbl:write name="oneline" property="prepayAmount" /></td>
    <td align="center"><tbl:write name="oneline" property="userCount" /></td>
    <td align="center"><d:getcmnname typeName="SET_C_CONTRACTSTATUS" match="oneline:status" /></td>
    <td align="center">
    <%if(CommonKeys.CONTRACTSTATUS_E.equals(status)){%>
    	<a href="javascript:view_open_click('<tbl:write name="oneline" property="contractNo" />')" class="link12" >开户</a>
    <%}%>
    </td>
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
</table>
<table  border="0" align="right" cellpadding="0" cellspacing="8">
  <tr>
    <td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    <td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
</rs:notlast>
    <td align="right">跳转到</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>页</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table>

</rs:hasresultset>                  
</TD>
</TR>
</TABLE>
</form> 
