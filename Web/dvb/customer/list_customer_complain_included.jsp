<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.CustomerComplainDTO,
                 com.dtv.oss.dto.CustComplainProcessDTO" %>
<form name="frmPost1" method="post" action="list_customer_complain_pro.do" target="_self">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<tbl:hiddenparameters pass="txtCustComplainId" />
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
     <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="98%" class="import_tit" align="center">客户投诉处理记录</td>
	</tr>
      </table> 
<rs:hasresultset>
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head" nowrap align="center">ID</td>
			<td class="list_head" nowrap align="center">处理部门</td>
			<td class="list_head" nowrap align="center">操作人</td>
			<td class="list_head" nowrap align="center">处理动作</td>
			<td class="list_head" nowrap align="center">处理结果</td>
			<td class="list_head" nowrap align="center">创建时间</td>
			<td class="list_head" nowrap align="center">说明</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<%CustComplainProcessDTO dto=(CustComplainProcessDTO)pageContext.getAttribute("oneline");
				  String orgName=Postern.getOrgNameByID(dto.getCurrentOrgId());
				  String operatorName=Postern.getOperatorNameByID(dto.getCurrentOperatorId());%>
				<td align="center"><tbl:write name="oneline" property="id" /></td>
				<td align="center"><%=orgName%></td>
				<td align="center"><%=operatorName%></td>
				<td align="center"><d:getcmnname typeName="SET_C_CUSTCOMPLAINPROCESSLOGACTION" match="oneline:action"/></td>
				<td align="center"><d:getcmnname typeName="SET_C_CUSTOMERCOMPLAINPROCESSSTATUS" match="oneline:status"/></td>
				<td align="center"><tbl:write name="oneline" property="createDate" /></td>
				<td align="center"><tbl:write name="oneline" property="description" /></td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
			<td colspan="9" class="list_foot"></td>
		</tr>
	</table>
	<table border="0" align="right" cellpadding="0" cellspacing="8">
		<tr>
			<td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span
				class="en_8pt">/</span>共<span class="en_8pt"><rs:prop
				property="pageamount" /><span>页</td>
			<td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
			<rs:notfirst>
				<td align="right"><img src="img/dot_top.gif" width="17px" height="11px"></td>
				<td><a
					href="javascript:firstPage_onClick(document.frmPost1, <rs:prop property="firstto" />)"
					class="link12">首页</a></td>
				<td align="right"><img src="img/dot_pre.gif" width="6px" height="11px"></td>
				<td><a
					href="javascript:previous_onClick(document.frmPost1, <rs:prop property="prefrom" />, <rs:prop property="preto" />)"
					class="link12">上一页</a></td>
			</rs:notfirst>
			<rs:notlast>
				<td align="right"><img src="img/dot_next.gif" width="6px" height="11px"></td>
				<td><a
					href="javascript:next_onClick(document.frmPost1, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)"
					class="link12">下一页</a></td>
				<td align="right"><img src="img/dot_end.gif" width="17px" height="11px"></td>
				<td><a
					href="javascript:lastPage_onClick(document.frmPost1, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)"
					class="link12">末页</a></td>
			</rs:notlast>
			<td align="right">跳转到</td>
			<td><input name="txtPage" type="text" class="page_txt"></td>
			<td>页</td>
			<td><input name="imageField" type="image" src="img/button_go.gif"
				width="28px" height="15px" border="0"
				onclick="javascript:return goto_onClick(document.frmPost1, <rs:prop property="pageamount" />)"></td>
		</tr>
	</table>
</rs:hasresultset> <br>
</form>
