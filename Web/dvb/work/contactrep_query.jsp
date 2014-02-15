<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>

<script language=javascript>
function query_submit()
{
	if(checkDate()){
		document.frmPost.submit();
	}
}

function view_detail_click(strId)
{
	self.location.href="contactrep_view.do?txtJobCardID="+strId;
}
function view_cj_detail_click(strId)
{
	self.location.href="job_card_view.do?txtJobCardID="+strId;
}
function checkDate(){
 
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
	 if(!checkPlainNum(document.frmPost.txtJobCardID,true,9,"工单编号"))
		return false; 
	 if(!checkPlainNum(document.frmPost.txtCustomerID,true,9,"客户证号"))
		return false; 
	            
	return true;
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">维修预约查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

 
<form name="frmPost" method="post" action="contactrep_query_result.do" >
<input type="hidden" name="actiontype" value="contactrep">
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
                 
           <td  class="list_bg2"><div align="right">创建时间</div></td>
           <td class="list_bg1"> 
            <o:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <o:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
             </td> 
             <td  class="list_bg2"><div align="right">工单编号</div></td> 
             <td class="list_bg1"  valign="middle">
              <input type="text" name="txtJobCardID" maxlength="9" size="25" value="<tbl:writeparam name="txtJobCardID" />" >
         </td>
        </tr>
        <tr> 
          <td  class="list_bg2"><div align="right">所在区域</div></td> 
          <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
         </td>
          <td class="list_bg2"><div align="right">客户证号</div></td>          
         <td class="list_bg1">
        <input type="text" name="txtCustomerID" maxlength="9" size="25" value="<tbl:writeparam name="txtCustomerID" />" >
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
	  </table> 
	 </td>
  </tr>
  </table>
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
      <input type="hidden" name="txtType" size="20" value="R">
      <input type="hidden" name="txtStatus" size="20" value="W;F">
      <input type="hidden" name="func_flag" size="20" value="1003">
      
       
    
  <rs:hasresultset>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
  <br>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head" nowrap>工单号</td>
          <td class="list_head" nowrap>客户证号</td>
          <td class="list_head" nowrap>联系人</td>
          <td class="list_head" nowrap>联系地址</td>
           <td class="list_head" nowrap>所在区域</td>
           <td class="list_head" width="18%" nowrap>工作内容</td>
          <td class="list_head" nowrap>状态</td>
          <td class="list_head" nowrap></td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
<%
     com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap wrap = (com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
     JobCardDTO dto=wrap.getJobCardDto();
     pageContext.setAttribute("oneline", wrap.getJobCardDto());
     pageContext.setAttribute("process", wrap.getJobCardProcessDto());
     String strAddress = Postern.getAddressById(dto.getAddressId());
     AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressId());
     pageContext.setAttribute("custaddr", addrDto);
     if (strAddress == null) strAddress="";
	 
%>
	 
	<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		  <td align="center">
		    <a href="javascript:view_cj_detail_click('<tbl:write name="oneline" property="JobCardId"/>')" class="link12" ><tbl:writenumber name="oneline" property="JobCardId" digit="7"/></a></td>
		    <td align="center"><tbl:writenumber name="oneline" property="CustId" digit="8" hideZero="true"/></td>
      		    <td align="center"><tbl:write name="oneline" property="ContactPerson"/></td>
      		    <td align="center"><%=strAddress%></td>
      		     <td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
      		     <td align="center"><tbl:write name="oneline" property="description"/></td>
      		    <td align="center"><o:getcmnname typeName="SET_W_JOBCARDSTATUS" match="oneline:Status" /></td>
      		    <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="JobCardId"/>')" class="link12" >预约维修时间</a></td>
    		</tr>
</lgc:bloop>  
 
<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
  <tr>
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
</table>   
 
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
 
