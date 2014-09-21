<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*,java.math.BigDecimal" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.AccountAdjustmentDTO"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.AccountAdjust2ReferRecordWrap" %>

<script language=javascript>
   function view_adjust_account_detail(seqNO){
	var strURL="adjust_account_detail.do?txtSeqNO="+seqNO+"&txtActionType=detail";
	self.location.href=strURL;
   }
   
   function query_submit(){
	if(checkData())
		document.frmPost.submit();
   }
   
   function checkData(){
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"结束日期必须大于等于开始日期")){
		
		return false;
	}
 
	return true;
   }
</script>
<%
//暂时没有实现预存抵扣调帐,先临时拼一个MAP,
LinkedHashMap AdjustTypeMap=new LinkedHashMap();
AdjustTypeMap.put("C","支付调帐");
AdjustTypeMap.put("F","费用调帐");
AdjustTypeMap.put("P","预存调帐");
pageContext.setAttribute("AdjustType",AdjustTypeMap);
%>
<form name="frmPost" method="post" action="custAcct_adjust_account_query.do">

<input type="hidden" name="txtFrom"  value="1">
<input type="hidden" name="txtTo"  value="10">
<input type="hidden" name="txtCustID" value="<tbl:writeparam name="txtCustID" />" >
<input type="hidden" name="txtAcctID" value="<tbl:writeparam name="txtAcctID" />" >

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">调帐记录查询</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">调帐原因</div></td>
    <td class="list_bg1"><d:selcmn name="txtReason" mapName="SET_F_ACCOUNTADJUSTMENTREASON"  match="txtReason"  width="23" /></td>
    <td class="list_bg2"><div align="right">调帐类型</div></td>
    <td class="list_bg1"><d:selcmn name="txtAdjustmentType" mapName="SET_F_ACCOUNTADJUSTMENTTYPE"  match="txtAdjustmentType"  width="23" />
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2" align="right">组织机构</td>
   		 <td class="list_bg1">
	    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
			<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
			<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
	    </td>	
    <td class="list_bg2"><div align="right">预存调整标记</div></td>
    <td class="list_bg1"><d:selcmn name="txtAdjustmentPrepaymentFlag" mapName="SET_G_YESNOFLAG"  match="txtAdjustmentPrepaymentFlag"  width="23" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">调帐对象</div></td>
    <td class="list_bg1"><tbl:select name="txtReferRecordType" set="AdjustType" width="23" match="txtReferRecordType" />
    <td class="list_bg2"><div align="right">调帐对象ID</div></td>
    <td class="list_bg1"><input name="txtReferRecordID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtReferRecordID" />"></td>
  </tr>
 
  <tr>
    <td class="list_bg2"><div align="right">状态</div></td>
    <td class="list_bg1"><d:selcmn name="txtStatus" mapName="SET_F_ACCOUNTADJUSTMENTSTATUS"  match="txtStatus"  width="23" /></td>
    <td class="list_bg2"><div align="right">创建日期</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  <tr>
    
    <td class="list_bg1" colspan="4">
        <table  border="0" cellspacing="0" cellpadding="0" align="center">
	    <tr>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	    </tr>
	 </table>
    </td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<% QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)request.getAttribute("ResponseQueryResult");
   BigDecimal fl=(BigDecimal)RepCmd.getExtraPayLoad();
   double totalAmount=0;
   if(fl!=null)
   	totalAmount=fl.doubleValue();
%>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" width="60" nowrap>流水号</td>
    <td class="list_head" width="80" nowrap>创建日期</td>
		<td class="list_head" width="70" nowrap>对象类型</td>      
    <td class="list_head" width="82" nowrap>金额</td>
    <td class="list_head" width="82" nowrap>状态</td>
    <td class="list_head" width="70" nowrap>调帐类型</td>
    <td class="list_head"  nowrap>调帐原因</td>
    <td class="list_head" width="70" nowrap>组织机构</td>
    <td class="list_head" width="70" nowrap>操作员</td>
  </tr>

   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%

    AccountAdjust2ReferRecordWrap wrapDTO = (AccountAdjust2ReferRecordWrap)pageContext.getAttribute("oneline");
    AccountAdjustmentDTO dto=wrapDTO.getAcctAdjDTO();
    pageContext.setAttribute("AccountAdjustmentDTO",dto);
    
    String strOpName="";
    strOpName=Postern.getOperatorNameByID(dto.getCreateOpID());
    if(strOpName==null)
    	strOpName="";
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
 
    <td align="center"><a href="javascript:view_adjust_account_detail('<tbl:write name="AccountAdjustmentDTO" property="seqNo"/>')" class="link12" ><tbl:write name="AccountAdjustmentDTO" property="seqNo"/></a></td>
    <td align="center"><tbl:writedate name="AccountAdjustmentDTO" property="createTime" /><br><tbl:writedate name="AccountAdjustmentDTO" property="createTime" onlyTime="true"/></td>	
    <td align="center"><d:getcmnname typeName="SET_F_ADJUSTMENTREFERRECORDTYPE" match="AccountAdjustmentDTO:ReferRecordType" /></td>    
    <td align="center"><%=WebUtil.bigDecimalFormat(wrapDTO.getReferAmount())%></td>
    <td align="center"><d:getcmnname typeName="SET_F_ACCOUNTADJUSTMENTSTATUS" match="AccountAdjustmentDTO:status" /></td>
    <td align="center"><d:getcmnname typeName="SET_F_ACCOUNTADJUSTMENTTYPE" match="AccountAdjustmentDTO:adjustmentType" /></td>
    <td align="center"><d:getcmnname typeName="SET_F_ACCOUNTADJUSTMENTREASON" match="AccountAdjustmentDTO:reason" /></td>
    <td align="center"><tbl:WriteOrganizationInfo name="AccountAdjustmentDTO" property="createOrgID"/></td> 	
    <td align="center"><%=strOpName %></td>
</tbl:printcsstr>
</lgc:bloop>

 
  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>

</table>
<table  border="0" align="right" cellpadding="0" cellspacing="9">
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
                  
<BR>

</form>  