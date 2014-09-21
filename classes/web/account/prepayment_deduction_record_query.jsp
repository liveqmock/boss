<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*,java.math.BigDecimal" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.PrepaymentDeductionRecordDTO"%>

<script language=javascript>
function query_submit(){
	if(checkData())
		document.frmPost.submit();
}

function prepayment_deduction_record_detail(seqNO){
	var strURL="prepayment_deduction_record_detail.do?txtSeqNO="+seqNO+"&txtActionType=detail";
	self.location.href=strURL;
}

function checkData(){
	if (document.frmPost.txtCreateStartDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDateDatePart, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDateDatePart, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDateDatePart,document.frmPost.txtCreateEndDateDatePart,"结束日期必须大于等于开始日期")){
		
		return false;
	}
 	if(!checkPlainNum(document.frmPost.txtCustID,true,9,"客户证号"))
		return false;
	if(!checkPlainNum(document.frmPost.txtAcctID,true,9,"帐户号"))
		return false;
	if(!checkPlainNum(document.frmPost.txtReferSheetID,true,9,"关联单据号"))
		return false;
		
	return true;
}
</script>

<form name="frmPost" method="post" action="prepayment_deduction_record_query.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预存抵扣记录查询</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" align="right" width="17%">客户证号</td>
    <td class="list_bg1" align="left" width="33%">
       <input name="txtCustID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtCustID" />">
    </td>
    <td class="list_bg2" align="right" width="17%">帐户号</td>
    <td class="list_bg1" align="left" width="33%">
       <input name="txtAcctID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtAcctID" />">
    </td>
  </tr>
  
  <tr>
     <td class="list_bg2" align="right">组织机构</td>
   		 <td class="list_bg1">
	    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
			<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
			<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
	    </td>	
    <td class="list_bg2" align="right">客户所在区域</td>
    <td class="list_bg1" align="left">
        <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	<input type="text" name="txtDistrictIDDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
        <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtDistrictIDDesc')">
    </td>
  </tr>
  <tr>
  	<td class="list_bg2" align="right">预存类型</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtPrePaymentType" mapName="SET_F_PREPAYMENTTYPE"  match="txtPrePaymentType"  width="23" />
    </td>
    <td class="list_bg2" align="right">创建来源</td>
    <td class="list_bg1" align="left" >
       <d:selcmn name="txtCreatingMethod" mapName="SET_F_FTCREATINGMETHOD"  match="txtCreatingMethod"  width="23" />
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">抵扣方式</td>
    <td class="list_bg1" align="left">
       <d:selcmn name="txtReferRecordType" mapName="SET_F_PDR_REFERRECORDTYPE"  match="txtReferRecordType"  width="23" />
    </td>
    <td class="list_bg2" align="right">抵扣对象</td>
    <td class="list_bg1" align="left">
       <input name="txtReferRecordID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtReferRecordID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">关联单据类型</td>
    <td class="list_bg1" align="left">
       <d:selcmn name="txtReferSheetType" mapName="SET_F_FTREFERTYPE"  match="txtReferSheetType"  width="23" />
    </td>
    <td class="list_bg2" align="right">关联单据号</td>
    <td class="list_bg1" align="left">
       <input name="txtReferSheetID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtReferSheetID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">创建时间</td>
    <td class="list_bg1" colspan="3"> 
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
   </td> 
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<rs:hasresultset>

<% QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)request.getAttribute("ResponseQueryResult");
   BigDecimal fl=(BigDecimal)RepCmd.getExtraPayLoad();
   double totalAmount=0;
   if(fl!=null)
   	totalAmount=fl.doubleValue();
%>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" width="60" nowrap>流水号</td>
    <td class="list_head" width="60" nowrap>创建时间</td>
    <td class="list_head" width="60" nowrap>客户证号</td>
    <td class="list_head" nowrap>客户姓名</td>
    <td class="list_head" width="60" nowrap>客户帐号</td>
    <td class="list_head" width="60" nowrap>预存类型</td>
    <td class="list_head" width="60" nowrap>金额</td> 
    <td class="list_head" width="60" nowrap>状态</td>
    <td class="list_head" width="60" nowrap>抵扣方式</td>
    <td class="list_head" width="60" nowrap>抵扣对象</td>
    <td class="list_head" width="60" nowrap>创建来源</td>
  </tr>

   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    PrepaymentDeductionRecordDTO dto = (PrepaymentDeductionRecordDTO)pageContext.getAttribute("oneline");
    pageContext.setAttribute("DTO",dto);
        	
    String strCustName=Postern.getCustomerNameByID(dto.getCustId());
    if(strCustName==null)
    	strCustName="";
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
 
    <td align="center"><a href="javascript:prepayment_deduction_record_detail('<tbl:write name="DTO" property="seqNo"/>')" class="link12" ><tbl:write name="DTO" property="seqNo"/></a></td>
    <td align="center"><tbl:writedate name="DTO" property="createTime"/><br><tbl:writedate name="DTO" property="createTime" onlyTime="true"/></td>
    <td><tbl:write name="DTO" property="custId"/></td>  
    <td><%=strCustName%></td>  
    <td><tbl:write name="DTO" property="acctId"/></td>
    <td align="center"><d:getcmnname typeName="SET_F_PREPAYMENTTYPE" match="DTO:prepaymentType" /></td>
    <td align="center"><tbl:write name="DTO" property="amount"/></td>
    <td align="center"><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:status" /></td>
    <td align="center"><d:getcmnname typeName="SET_F_PDR_REFERRECORDTYPE" match="DTO:referRecordType" /></td>
    <td align="center"><tbl:write name="DTO" property="referRecordId"/></td>
    <td align="center"><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="DTO:creatingMethod" /></td>
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    	<td colspan="11">总计：<%=WebUtil.bigDecimalFormat(totalAmount) %></td>
    </tbl:printcsstr>
  </tr>
  
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
<BR>

</form>  
         

      
