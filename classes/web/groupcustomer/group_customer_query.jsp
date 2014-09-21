<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>
function check_form(){
   if (document.frmPost.txtCreateStartDate.value != ''){
		  if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "开始日期")){
			   return false;
		  }
	}
	if (document.frmPost.txtCreateEndDatevalue != ''){
		  if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "结束日期")){
			   return false;
		  }
	}
	if (!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"结束日期必须大于等于开始日期")){
		 return false;
	}
	if(!check_Num(document.frmPost.txtCustomerID,true,"客户证号"))
	  return false;
	  
	return true;
}

function query_submit(){
   if (check_form()) document.frmPost.submit();
}

function view_detail_click(strId){
	self.location.href="groupcustomer_view.do?txtCustomerID="+strId;
}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">集团客户查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="groupcustomer_query_result.do" method="post" >    
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=17% align="right">集团客户姓名</td>
    <td class="list_bg1" width=33% align="left"><input name="txtName" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtName" />"></td>
    <td class="list_bg2" width=17% align="right">集团客户证号</td>
    <td class="list_bg1" width=33% align="left"><input name="txtCustomerID" type="text" class="text" maxlength="10" size="22" value="<tbl:writeparam name="txtCustomerID" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" width=17% align="right">合同编号</td>
    <td class="list_bg1" width=33% align="left"><input name="txtContractNo" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtContractNo" />"></td>
    <td class="list_bg2" width=17% align="right">创建日期</td>
    <td class="list_bg1" valign="middle">
        <d:datetime name="txtCreateStartDate" size="10" myClass="text" match="txtCreateStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
        -
        <d:datetime name="txtCreateEndDate" size="10" myClass="text" match="txtCreateEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
	  </td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr align="center">
		  <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
				<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
				<td><input name="Reset" type="Reset" class="button" value="重 置"  ></td>
				<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
				<td width="20" ></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		   </tr>
      </table>
      </td>
		</tr>
	</table>        
<input type="hidden" name="txtFrom" size="22" value="1">
<input type="hidden" name="txtTo" size="22" value="10">
<input type="hidden" name="txtCustomerStyle" value="G" >
<input type="hidden" name="txtGroupCustID" value="0" >
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
    <td class="list_head">集团客户证号</td>
    <td class="list_head">集团客户名称</td>
    <td class="list_head">类型</td>
    <td class="list_head">证件号</td>
    <td class="list_head">所属分区</td>
    <td class="list_head">地址</td>
    <td class="list_head">创建日期</td>
    <td class="list_head">状态</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getCustDto());
    pageContext.setAttribute("custaddr",  wrap.getAddrDto());
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td><a href="javascript:view_detail_click('<tbl:write name="oneline" property="customerID" />')" class="link12" ><tbl:write name="oneline" property="customerID" /></a></td>
    <td><tbl:write name="oneline" property="name" /></td>
    <td><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="oneline:customerType" /></td>
    <td><tbl:write name="oneline" property="CardID" /></td>
    <td><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
    <td><tbl:write name="custaddr" property="detailAddress" /></td>
    <td><tbl:writedate name="oneline" property="CreateTime" /></td>
    <td><d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="oneline:status" /></td>
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <td colspan="8" class="list_foot"></td>
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
</form> 
