<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>

<SCRIPT language="JavaScript">

function query_submit(strCustomerID){
	if (document.frmPost.txtCreateStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtCreateEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "结束日期")){
			return false;
		}
	}
    if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"结束日期必须大于等于开始日期")){

    	return false;
    }
	if (document.frmPost.txtPreferedStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPreferedStartDate, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtPreferedEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPreferedEndDate, true, "结束日期")){
			return false;
		}
	}
    if(!compareDate(document.frmPost.txtPreferedStartDate,document.frmPost.txtPreferedEndDate,"结束日期必须大于等于开始日期")){

    	return false;
    }
	if(document.frmPost.txtID.value=="")
		document.frmPost.action="query_book_account.do?txtCustomerID="+strCustomerID;
   	document.frmPost.submit();
}
function create_account_click(strCustID,strID,strAccountID)
{
	self.location.href="book_create_account_prepare.do?txtCustomerID="+strCustID+"&txtID="+strID+"&txtAccount="+strAccountID;
}
function add_booking(){
	document.frmPost.action="booking_addacount_book.screen";
	document.frmPost.submit();
}
function modify_book_info(txtID){
 	self.location.href="booking_addacount_prepare_info.do?txtID="+txtID;
}
</SCRIPT>
<%
    int custId= WebUtil.StringToInt(request.getParameter("txtCustomerID"));
    CustomerDTO custDTO = Postern.getCustomerByID(custId);
    pageContext.setAttribute("cust", custDTO);
%>
<form name="frmPost" action="query_book_account.do" method="post" >
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<input type="hidden" name="txtCustType"   value="<tbl:write name="cust" property="customerType" />" >
<input type="hidden" name="txtOpenSourceType"   value="<tbl:write name="cust" property="OpenSourceType" />" >
<input type="hidden" name="txtCounty"   value="<%=Postern.getAddressCountyByCustomerID(custId)%>" >
<input type="hidden" name="txtCustomerID" value="<%=custId%>">
<input type="hidden" name="txtCsiType" value="BU">

<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">预约增机单查询</td>
	</tr>
</table>
<table width="780" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<table width="780" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">单据号</td>
		<td class="list_bg1" width="33%"><font size="2"><input name="txtID" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtID" />"></font></td>
		<td class="list_bg2" align="right" width="17%">工单编号</td>
		<td class="list_bg1" width="33%"><input name="txtReferjobcardid" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtReferjobcardid" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">创建时间</td>
		<td class="list_bg1">    
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />         </td>
		<td class="list_bg2" align="right">状态</td>
		<td class="list_bg1"><d:selcmn name="txtStatus" mapName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="txtStatus" width="23" />
		</td>
	</tr>
	<tr>
	  <td  class="list_bg2" align="right">预约上门时间</td>
    <td class="list_bg1"> 
            <d:datetime name="txtPreferedStartDate" size="10" match="txtPreferedStartDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
            <d:datetime name="txtPreferedEndDate" size="10" match="txtPreferedEndDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
    </td>
    <td class="list_bg2" align="right">受理原因</td>
    <td class="list_bg1" align="left"><font size="2">
            <tbl:selectCommonMethodByPostern name="txtCsiReason" posternMethod="getCsiReasonByCsitype" parentMatch="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU%>" match="txtCsiReason"  width="23" />
    </font></td>
	</tr>
</table>

<table width="780"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1">
   	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
					   value="查&nbsp;询" onclick="javascript:query_submit(<%=custId%>)"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>          
 		      	<td width="20" >&nbsp;</td>  
            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
					   value="新&nbsp;增" onclick="javascript:add_booking()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
</table>    
<table width="780" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<rs:hasresultset>
	
	<table width="780" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head" align="center" width="110" nowrap>预约单编号</td>
			<td class="list_head" align="center" width="110" nowrap>创建时间</td>
			<td class="list_head" align="center" width="110" nowrap>状态</td>
			<td class="list_head" align="center" width="110" nowrap>相关工单</td>
			<td class="list_head" align="center" width="120" nowrap>处理情况</td>
			<td class="list_head" align="center" width="110" nowrap>创建人</td>
			<td class="list_head" align="center" width="110" nowrap>操作</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
		<%	  
		    CustServiceInteractionDTO cudto=(CustServiceInteractionDTO)pageContext.getAttribute("oneline");
			  String jcStatus=Postern.getJobCardStatusByID(cudto.getReferJobCardID());

				// wangfang 2008.05.28
			  CsiProcessLogDTO confirmLogDTO =Postern.getCsiProcessLogActionDTO(cudto.getId());
			  String oprName =Postern.getOperatorNameByID(cudto.getCreateOperatorId());
        if (oprName == null) oprName = "";
        boolean linkFlag =false;
        if ((cudto.getStatus().equals("P")) && (confirmLogDTO == null))
        {
            linkFlag =true;
        }
		%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"	overStyle="list_over">
				<td align="center"><a href="javascript:modify_book_info('<tbl:write name="oneline" property="id" />')"><tbl:write name="oneline" property="id" /></a></td>
					<td align="center"><tbl:writedate name="oneline" property="createTime" /><br><tbl:writedate name="oneline" property="createTime" onlyTime="true"/></td>
				<td align="center"><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="oneline:Status"/></td>
				<td align="center"><tbl:write name="oneline" property="referJobCardID"/></td>
				<td align="center"><d:getcmnname typeName="SET_W_JOBCARDSTATUS" match="<%=jcStatus%>"/></td>
				<td align="center"><%=oprName%></td>
				<td align="center"><%if(linkFlag){%><a href="javascript:create_account_click(<%=custId%>,'<tbl:write name="oneline" property="id" />','<tbl:write name="oneline" property="accountID" />')">创建业务帐户</a><%}%></td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
			<td colspan="7" class="list_foot"></td>
		</tr>
	</table>
	<table border="0" align="right" cellpadding="0" cellspacing="8">
		<tr>
              <td align="right" class="t12" colspan="9" >
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
<br>
      <br>
</form>
