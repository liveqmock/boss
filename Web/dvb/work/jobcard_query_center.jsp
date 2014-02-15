<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<script language=javascript>
function check_form(){
 
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
	if (document.frmPost.txtFinishedStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtFinishedStartDate, true, "完成开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtFinishedEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtFinishedEndDate, true, "完成结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtFinishedStartDate,document.frmPost.txtFinishedEndDate,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	 if(!checkPlainNum(document.frmPost.txtJobCardID,true,9,"工单编号"))
		return false; 
	 if(!checkPlainNum(document.frmPost.txtCustomerID,true,9,"客户证号"))
		return false;             
	return true;
}

function query_submit()
{
        if (check_form()){
        	if(query_all()){
			document.frmPost.txtQueryType.value = 'queryAll';			
		}else{
			document.frmPost.txtQueryType.value = 'queryPart';
		}
		document.frmPost.submit();	
        }  
}
function query_all(){
	if(document.frmPost.queryType.checked == true)
		return true;
	return false;
}
function view_detail_click(strId)
{
	self.location.href="job_card_view.do?txtJobCardID="+strId;
}
function changeSubType(){
		document.FrameUS.submit_update_select('jobtypetosubtype', document.frmPost.txtType.value, 'txtSubType', '');
}
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
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
    <td class="title">工单查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="jobcard_query_result.do" method="post" >  
 <input type="hidden" name="txtQueryType" value="<tbl:writeparam name="txtQueryType"/>">   
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">工单编号</div></td>
    <td class="list_bg1">
    <input name="txtJobCardID" type="text" class="text" maxlength="9"  value="<tbl:writeparam name="txtJobCardID" />" size="25">
    </td>
    <td class="list_bg2"><div align="right">客户证号</div></td>       
    <td class="list_bg1">
        <input type="text" name="txtCustomerID" maxlength="9"  value="<tbl:writeparam name="txtCustomerID" />" size="25">
    </td>   
  </tr>
  
  <tr>
     <td class="list_bg2"><div align="right">工单类型</div></td>
     <td class="list_bg1"  > 
	<d:selcmn name="txtType" mapName="SET_W_JOBCARDTYPE"  match="txtType" width="25" onchange="changeSubType()"/>
     </td>
     <td class="list_bg2"><div align="right">工单子类型</div></td>
     <td class="list_bg1"  > 
     <d:selcmn name="txtSubType" mapName="SET_W_JOBCARDSUBTYPE" match="txtSubType" width="25" />
     </td>
   
  </tr>
  <tr>
        <td class="list_bg2"><div align="right">客户类型</div></td>
         <td class="list_bg1">
             <d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="25" />  
         </td> 
         <td  class="list_bg2"><div align="right">所在区域</div></td> 
          <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
         </td>
  </tr>
   <tr>
          <td  class="list_bg2"><div align="right">创建时间</div></td> 
           <td class="list_bg1"> 
                
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
             </td> 
              <td  class="list_bg2"><div align="right">预约上门时间</div></td>
            <td class="list_bg1"> 
            <d:datetime name="txtPreferedStartDate" size="10" match="txtPreferedStartDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
            <d:datetime name="txtPreferedEndDate" size="10" match="txtPreferedEndDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
             </td>            
             </tr>
              <tr>
          
            <td  class="list_bg2"><div align="right">完成时间</div></td>
            <td  class="list_bg1"> 
            <d:datetime name="txtFinishedStartDate" size="10" match="txtFinishedStartDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtFinishedStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
            <d:datetime name="txtFinishedEndDate" size="10" match="txtFinishedEndDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtFinishedEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
             </td> 
            <td class="list_bg2"><div align="right">处理失败原因</div></td>
    	    <td class="list_bg1"><d:selcmn name="txtWorkResultReason" mapName="SET_W_INSTALLFAILREASON" match="txtWorkResultReason" width="25" /></td>
                      
         </tr>
     	<tr>
          
            <td class="list_bg2"><div align="right">状态</div></td>
     	    <td class="list_bg1" colspan="3">
              <d:selcmn name="txtStatus" mapName="SET_W_JOBCARDSTATUS" match="txtStatus" width="25" />
            </td>          
         </tr>
         <tr>
         	<td class="list_bg1" colspan="4" align="center"> 
	 		<input type="checkbox" name="queryType" >&nbsp查询所有   
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">工单编号</td>
    <td class="list_head">工单类型</td>
    <td class="list_head">工单子类型</td>
    <td class="list_head">客户证号</td>
    <td class="list_head">联系人</td>
    <td class="list_head">联系电话</td>
    <td class="list_head">联系地址</td>
    <td class="list_head">所属分区</td>
    <td class="list_head">状态</td>
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
     String custID = dto.getCustId()==0?"":dto.getCustId()+"";
	 
%>
 

 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
    <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="JobCardId" />')" class="link12" ><tbl:writenumber name="oneline" property="JobCardId" digit="7"/></a></td>
    <td align="center"><d:getcmnname typeName="SET_W_JOBCARDTYPE" match="oneline:jobType" /></td>
    <td align="center"><d:getcmnname typeName="SET_W_JOBCARDSUBTYPE" match="oneline:subType" /></td>
    <td align="center"><%=custID%></td>
    <td align="center"><tbl:write name="oneline" property="ContactPerson"/></td>
    <td align="center"><tbl:write name="oneline" property="ContactPhone"/></td>  
    <td align="center"><%=strAddress%></td>
     <td  align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
    <td align="center"><d:getcmnname typeName="SET_W_JOBCARDSTATUS" match="oneline:status" /></td>
  </tr>
 

</lgc:bloop>

  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
 
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
</form>              
</TD>
</TR>
</TABLE>
<script language=javascript>
function window_open(){
	if(document.frmPost.txtQueryType.value == 'queryAll')
		document.frmPost.queryType.checked = true;
}
window_open();

</script> 
