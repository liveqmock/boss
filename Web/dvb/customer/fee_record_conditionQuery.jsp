<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO" %>
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<!-- 付费记录查询 -->
<Script language="JavaScript" >
 function view_detail_click(aiNO) {
    self.location.href = "fee_detailrecord_query.do?txtID="+aiNO;
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
	return true;
 }
 function query_submit(){
    if (chek_frm()){
       document.frmPost.action ="fee_detailrecord_conditionQuery.do";
       document.frmPost.submit();
    }
 }
</Script>


<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户费用记录列表</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" method="post" action="fee_detailrecord_conditionQuery.do" >
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
    <tbl:hiddenparameters pass="txtCustomerID/txtAccountID" />
    
    <table width="804" align="center" border="0" cellspacing="1" cellpadding="1" class="list_bg">
        <tr>
        	<td valign="middle" class="list_bg2" align="right" width="17%" >日期</td>
	        <td width="33%" class="list_bg1"><font size="2">
             <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeFrom)" onblur="lostFocus(this)" type="text" class="text" size="12" maxlength="10" name="txtCreateTimeFrom" value="<tbl:writeparam name="txtCreateTimeFrom"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
              --
             <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeTo)" onblur="lostFocus(this)" type="text" class="text" size="12" maxlength="10" name="txtCreateTimeTo" value="<tbl:writeparam name="txtCreateTimeTo" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            </font></td>
              <td valign="middle" class="list_bg2" align="right" width="17%" >单据类型</td>
             <td width="33%" class="list_bg1" ><font size="2">
           <d:selcmn name="ReferType" mapName="SET_F_FTREFERTYPE"  match="ReferType"  width="25" />
         </td>
        </tr>
         <tr>
        	
          <td valign="middle" class="list_bg2" align="right" width="17%" >单据号</td>
	        <td width="33%"  colspan="3" class="list_bg1"><font size="2">
             <input type="text"  size="25" maxlength="20" name="txtReferID" value="<tbl:writeparam name="txtReferID"/>" >  	
          </td>
        </tr>
        
        <tr> 
          <td align="center" width="100%" class="list_bg1" colspan="4">
            <table  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                <td><input name="Submit2" type="button" class="button" onClick="javascript:query_submit()" value="查 询"></td>
                <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
              </tr>
            </table>
         </td>
        </tr>
    </table>
    <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	     <td><img src="img/mao.gif" width="1" height="1"></td>
	   </tr>
    </table>
    <tbl:hiddenparameters pass="txtInvoiceNo" />
    
  <rs:hasresultset>
    <table width="804" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">
          <td class="list_head" width="60" nowrap>流水号</td>
          <td class="list_head" width="60" nowrap>创建日期</td>
          <td class="list_head" width="60" nowrap>费用类型</td>
          <td class="list_head" nowrap>账目类型</td>
          <td class="list_head" width="60" nowrap>金额</td>
          <td class="list_head" width="60" nowrap>状态</td>
          <td class="list_head" width="60" nowrap>计费周期</td>
          <td class="list_head" width="60" nowrap>帐单号</td>
          <td class="list_head" width="60" nowrap>单据类型</td>
          <td class="list_head" width="60" nowrap>单据号</td>
          <td class="list_head" width="60" nowrap>创建来源</td>
       </tr>
     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
       <%
          AccountItemDTO accountItemDto =(AccountItemDTO)pageContext.getAttribute("oneline");
          
          AcctItemTypeDTO  acctItemTypeDto=Postern.getAcctItemTypeDTOByAcctItemTypeID(accountItemDto.getAcctItemTypeID());
          String acctItemTypeName =acctItemTypeDto.getAcctItemTypeName();
          String strBillingCycleName = Postern.getBillingCycleNameByID(accountItemDto.getBillingCycleID());
           if(strBillingCycleName==null)
    	        strBillingCycleName="";
       %>
     
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" nowrap><a href="javascript:view_detail_click('<tbl:write name="oneline" property="AiNO"/>')" class="link12">
                <tbl:writenumber name="oneline" property="AiNO" digit="9" />
          </td>
          <td align="center" nowrap><tbl:writedate name="oneline" property="CreateTime"/><br><tbl:writedate name="oneline" property="createTime" onlyTime="true"/></td>
          <td align="center" ><d:getcmnname typeName="SET_F_BRFEETYPE" match="oneline:feeType" /></td>
          <td align="center" ><%=acctItemTypeName%></td>
          <td align="center" nowrap><tbl:write name="oneline" property="Value" /></td>  
          <td align="center" nowrap><d:getcmnname typeName="SET_F_FTSTATUS" match="oneline:Status" /></td>  
          <td align="center" nowrap><%=strBillingCycleName%></td>
          <td align="center" nowrap><tbl:write name="oneline" property="InvoiceNO"/></td>
          <td align="center" ><d:getcmnname typeName="SET_F_FTREFERTYPE" match="oneline:referType" /></td>   
          <td align="center" nowrap><tbl:write name="oneline" property="referID"/></td>
          <td align="center" ><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="oneline:CreatingMethod" /></td>
       </tbl:printcsstr>
     </lgc:bloop>
     <tr>
        <td colspan="11" class="list_foot"></td>
     </tr> 
   </table>
   
 <table  border="0" align="right" cellpadding="0" cellspacing="11">
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
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
  </table>
   
  </rs:hasresultset>
</form>