<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<script language=javascript>
<!--
function check_form(){

	if (check_Blank(document.frmPost.txtPhoneNo, true, "�绰����"))
		return false;
	if(!check_Num(document.frmPost.txtPhoneNo,true,"�绰����"))
		return false;
	return true;
}

function query_submit()
{
        if (check_form()) document.frmPost.submit();
}

function view_detail_click(strId)
{
	self.location.href="customer_view.do?txtCustomerID="+strId;
}

function back_query()
{
	self.location.href="menu_customer_query.do";
}
-->
</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          ���ڻ�ȡ���ݡ�����
          </font>
          </td>
        </tr>
    </table>
</div>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ��绰�����ѯ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="phoneno_query_result.do" method="post" >    
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=18%><div align="right">�绰����*</div></td>
    <td class="list_bg1" width=30%><input name="txtPhoneNo" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtPhoneNo" />"></td>
    <td class="list_bg2" width=18% colspan=2>
		<table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
		</table>
	 </td>
	</tr>
</table>
	  <input type="hidden" name="txtFrom" size="22" value="1">
      <input type="hidden" name="txtTo" size="22" value="10">
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
    <td class="list_head">�ͻ�֤��</td>
    <td class="list_head">����</td>
    <td class="list_head">ҵ���ʻ�ID</td>
    <td class="list_head">ҵ���ʻ�״̬</td>
    <td class="list_head">�绰����</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    com.dtv.oss.dto.wrap.customer.CustomerSAWrap wrap = (com.dtv.oss.dto.wrap.customer.CustomerSAWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getCustDto());
    pageContext.setAttribute("sa",  wrap.getSaDto());
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td><a href="javascript:view_detail_click('<tbl:write name="oneline" property="customerID" />')" class="link12" ><tbl:write name="oneline" property="customerID" /></a></td>
    <td><tbl:write name="oneline" property="name" /></td>
    <td><tbl:write name="sa" property="serviceAccountID" /></td>
    <td><d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="sa:status" /></td>
    <td><tbl:write name="sa" property="serviceCode" /></td>
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>
<table  border="0" align="center" cellpadding="0" cellspacing="8">
  <tr>
    <td>��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ<span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" /><span>ҳ</td>
    <td>��<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">��ҳ</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">��һҳ</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">��һҳ</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">ĩҳ</a></td>
</rs:notlast>
    <td align="right">��ת��</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>ҳ</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
</table>

</rs:hasresultset>                  
</TD>
</TR>
</TABLE>
</form> 
