<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.InvoiceDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<script language=javascript>
function view_detail_click(strId){
	self.location.href="bill_view.do?txtInvoiceNo="+strId;
}
function chek_frm(){
 	if (document.frmPost.txtCreateTimeFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeFrom, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTimeTo.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeTo, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTimeFrom,document.frmPost.txtCreateTimeTo,"结束日期必须大于等于开始日期")){
        	return false;
        }
    if(!checkPlainNum(document.frmPost.txtCustomerID,true,9,"客户证号"))
		return false;
	if(!checkPlainNum(document.frmPost.txtAccountID,true,9,"帐户号"))
		return false;
	return true;
 }
function query_submit(){
    if(chek_frm()){
    	document.frmPost.submit();
    }
    
}
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" width="820">
  <tr>
    <td align="right" width="50%"><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">账单查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="bill_query.do" method="post" >    
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" align="right" width="17%">账单条形码</td>
    <td class="list_bg1" align="left"  width="83%" colspan="3"><font size="2">
            <input type="text" name="txtBarCode" size="48"  value="<tbl:writeparam name="txtBarCode" />" ></font></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right" width="17%">客户证号</td>
     <td class="list_bg1" width="33%" align="left">
    	 <input name="txtCustomerID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtCustomerID" />">
    </td>
    <td class="list_bg2" width="17%" align="right">帐户号</td>
    <td class="list_bg1" width="33%" align="left">
    	<input name="txtAccountID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtAccountID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">所属区域</td>
    <td class="list_bg1" align="left">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
        <td class="list_bg2" align="right">客户地址</td>
    <td class="list_bg1" align="left">
    	<input name="txtAddress" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtAddress" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">账单来源</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtInvoiceSourceType" mapName="SET_F_INVOICESOURCETYPE"  match="txtInvoiceSourceType"  width="23" />
    </td>
    <td class="list_bg2" align="right">账务周期</td>
    <td class="list_bg1" align="left">
    	<input name="txtInvoiceCycleName" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtInvoiceCycleName" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">创建时间</td>
    <td class="list_bg1" > 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeFrom)" onblur="lostFocus(this)" type="text" class="text" size="12" maxlength="10" name="txtCreateTimeFrom" value="<tbl:writeparam name="txtCreateTimeFrom"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             -- 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeTo)" onblur="lostFocus(this)" type="text" class="text" size="12" maxlength="10" name="txtCreateTimeTo" value="<tbl:writeparam name="txtCreateTimeTo" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
   </td>
   <td class="list_bg2" align="right">状态</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtStatus" mapName="SET_F_INVOICESTATUS"  match="txtStatus"  width="23" />
    </td>
  </tr> 
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table></td>
	</tr>
</table> 
	 
<input type="hidden" name="txtFrom" size="22" value="1">
<input type="hidden" name="txtTo" size="22" value="10">
 <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr class="list_head">      
          <td class="list_head">账单号</td>
          <td class="list_head">客户证号</td>
          <td class="list_head">客户姓名</td>
          <td class="list_head">帐户号</td>
          <td class="list_head">账单状态</td>
          <td class="list_head">账单来源</td>
          <td class="list_head">账务周期</td>
          <td class="list_head">账单金额</td>
          <td class="list_head">BCF</td>
          <td class="list_head">账单生成日期</td> 
          <td class="list_head">账单支付日期</td>       
     </tr>    
     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
      <%
        InvoiceDTO dto = (InvoiceDTO) pageContext.getAttribute("oneline");
        String strBillingCycleName = Postern.getBillingCycleNameByID(dto.getInvoiceCycleId());
        if (strBillingCycleName==null) strBillingCycleName="";
        String customerName =Postern.getCustomerNameById(dto.getCustID());
        String status=dto.getStatus(); 
     %> 
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="InvoiceNo"/>')" class="link12"><tbl:writenumber name="oneline" digit="7" property="InvoiceNo"/></a></td>
          <td align="center" ><tbl:write name="oneline" property="custID"/></td>
          <td align="center" ><%=customerName%></td>
          <td align="center" ><tbl:write name="oneline" property="acctID"/></td>
          <td align="center" >
          <%
             if(status.equalsIgnoreCase("w")||status.equalsIgnoreCase("o")){
          %>
             <font color="red"><d:getcmnname typeName="SET_F_INVOICESTATUS" match="oneline:status" /></font>
          <% 
            } else{
          %> 
            <d:getcmnname typeName="SET_F_INVOICESTATUS" match="oneline:status" />
          <%
            }
          %>
          </td>
          <td align="center" ><d:getcmnname typeName="SET_F_INVOICESOURCETYPE" match="oneline:invoiceSourceType" /></td>
          <td align="center" ><%=strBillingCycleName%></td>
          <td align="center" ><tbl:write name="oneline" property="Amount"/></td>
          <td align="center" ><tbl:write name="oneline" property="Bcf"/></td>
          <td align="center" ><tbl:writedate name="oneline" property="createDate"/><br><tbl:writedate name="oneline" property="createTime" onlyTime="true"/></td>
          <td align="center" ><tbl:writedate name="oneline" property="payDate"  /><br><tbl:writedate name="oneline" property="payDate" onlyTime="true"/></td>
       </tbl:printcsstr>
     </lgc:bloop>       
    <tr>
        <td colspan="11" class="list_foot"></td>
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