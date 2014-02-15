<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
function check_form(){
    if(!checkPlainNum(document.frmPost.txtID,true,9,"单据号"))
		return false;
	if(!checkPlainNum(document.frmPost.txtJobCardId,true,9,"工单编号"))
		return false;   	
	return true;
}

function query_submit(){
    if (check_form()) document.frmPost.submit();
}

function create_submit(strId,forwardFlag){
   var openFlag =document.frmPost.OpenFlag.value;
	 self.location.href="open_create_prepare_for_booking.do?txtID="+strId+"&forwardFlag="+forwardFlag+"&OpenFlag="+openFlag;
}
function detail_view(strId){
	 self.location.href="service_interaction_view.do?txtID="+strId;
}

</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预约开户－未开户预约单查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="service_interaction_info.do" >
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="10">
   <input type="hidden" name="txtStatus"  value="<%=CommonKeys.CUSTSERVICEINTERACTION_STATUS_PROCESS%>" >
   <input type="hidden" name="OpenFlag" value="<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>" >
   <input type="hidden" name="txtCustomerID" value="0" >
   <table width="820"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
     <tr>
       <td class="list_bg2" align="right" width="17%">单据号</td>
       <td class="list_bg1" align="left" width="33%">
         <input type="text" name="txtID" maxlength="9" size="25" value="<tbl:writeparam name="txtID" />" class="text">
       </td>
       <td class="list_bg2" align="right" width="17%">创建时间</td>
       <td class="list_bg1" align="left" width="33%"> 
         <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateStartDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateStartDate" value="<tbl:writeparam name="txtCreateStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
         - 
         <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateEndDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateEndDate" value="<tbl:writeparam name="txtCreateEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
       </td>
     </tr>
     <tr>
       <td class="list_bg2" align="right">联系人姓名</td>
       <td class="list_bg1" align="left">
         <input type="text" name="txtName" maxlength="20" size="25" value="<tbl:writeparam name="txtName" />" class="text">
       </td>
       <td class="list_bg2" align="right">联系电话</td>
       <td class="list_bg1" align="left">
         <input type="text" name="txtContactPhone" maxlength="100" size="25" value="<tbl:writeparam name="txtContactPhone" />" class="text" >
       </td>
     </tr>
     <tr>
       <td class="list_bg2" align="right">来源渠道</td>
       <td class="list_bg1" align="left">
         <d:selcmn name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="23" onchange="ChangeOpenSourceType()" />          
       </td>
       <td class="list_bg2" align="right">来源渠道ID</td>
       <td class="list_bg1" align="left">
         <d:selopensourceid name="txtOpenSourceID" parentMatch="txtOpenSourceType" match="txtOpenSourceID" width="23" />
       </td>   
     </tr>
    <tr>
       <td class="list_bg2" align="right">所在区</td>
       <td class="list_bg1" align="left">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
       </td>
       <td class="list_bg2" align="right">详细地址</td>
       <td class="list_bg1" align="left">
         <input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress" />" class="text">
       </td>   
     </tr>
     <tr>
       <td class="list_bg2" align="right">工单编号</td>
       <td class="list_bg1" align="left">
         <input type="text" name="txtJobCardId" size="25" maxlength="9" value="<tbl:writeparam name="txtJobCardId"  />" class="text" >
       </td>
       <td colspan="2" align="center" class="list_bg1">
         <table  border="0" cellspacing="0" cellpadding="0">
           <tr>
            <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit2" type="button" class="button" onClick="javascript:query_submit()"  value="查 询"></td>
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
   <rs:hasresultset>
   <br>
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr class="list_head">
       <td class="list_head">预约单编号</td>
       <td class="list_head">姓名</td>
       <td class="list_head">详细地址</td>
       <td class="list_head">状态</td>
       <td class="list_head">相关工单号</td>
       <td class="list_head">处理情况</td>
       <td class="list_head">操作</td>
     </tr>
     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
     <%
        com.dtv.oss.dto.wrap.customer.CustServiceInteractionWrap wrap = (com.dtv.oss.dto.wrap.customer.CustServiceInteractionWrap)pageContext.getAttribute("oneline");
    
        pageContext.setAttribute("oneline", wrap.getCsiDto());
        pageContext.setAttribute("onecust", wrap.getNciDto());
        pageContext.setAttribute("custaddr", wrap.getCustAddrDto());
    
       String strJCStatus = Postern.getJobCardStatusByID(wrap.getCsiDto().getReferJobCardID());
     %>                                      
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
         <td align="center" ><a href="javascript:detail_view('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7" /></a></td>
         <td align="center" ><tbl:write name="onecust" property="Name"/></td>
         <td align="center" ><tbl:write name="custaddr" property="DetailAddress"/></td>
         <td align="center" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="oneline:status" /></td>
         <td align="center" ><tbl:writenumber name="oneline" property="ReferJobCardID" digit="7"  /></td>
         <td align="center" ><d:getcmnname typeName="SET_W_JOBCARDSTATUS" match="<%=strJCStatus%>"/></td>
         <td align="center" ><A href="javascript:create_submit('<tbl:write name="oneline" property="Id"/>','1')" class="link12">开户</A></td>
       </tbl:printcsstr>
     </lgc:bloop>
     <tr>
         <td colspan="7" class="list_foot"></td>
     </tr>
     
     <tr class="trline" >
         <td align="right" class="t12" colspan="7" >
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
             
       <tr class="trline" >
         <td colspan="7">
         <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
          <tr>
            <td><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
         </table>
       </td></tr>
      </table> 
      </rs:hasresultset>   
</form>