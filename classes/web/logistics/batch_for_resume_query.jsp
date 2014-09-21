<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="/WEB-INF/privilege.tld" prefix="pri" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.dto.custom.GeHuaUploadCustBatchHeaderDTO" %>
<%@ page import="com.dtv.oss.dto.BatchJobDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<Script language=JavaScript>
function chek_frm(){
 	if (document.frmPost.txtImportDateFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtImportDateFrom, true, "导入日期起时点"))
			return false;
	}
	if (document.frmPost.txtImportDateTo.value != '')
	{
		if (!check_TenDate(document.frmPost.txtImportDateTo, true, "导入日期结束点"))
			return false;
	}
	if(!compareDate(document.frmPost.txtImportDateFrom,document.frmPost.txtImportDateTo,"导入日期起时点不能大于导入日期结束点"))
        	return false;
   return true;
	
}
 
function query_submit(){
    if(chek_frm()){
    	document.frmPost.submit();
    }
}

function resume_click(strBatchNo)
{
  //链接到批量开通任务创建页
	self.location.href="batchJob_for_resume.do?txtBatchNo="+strBatchNo+"&txtJobType=UR";
}

function export_click(strBatchNo)
{
  document.frmPost.txtNo.value=strBatchNo;
  document.frmPost.txtSubType.value="detail";
  
	download(document.frmPost,'批量操作查询执行结果');
}
</Script>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">批量开通信息查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
   <form name="frmPost" method="post" action="batch_for_resume_query.do" >
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
      <input type="hidden" name="txtNo"  value="">
      <input type="hidden" name="txtJobType"  value="UR">
      <input type="hidden" name="txtSubType"  value="">
  <br>
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr>   
         <td class="list_bg2" align="right">导入日期</td>
		    <td class="list_bg1" > 
		            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtImportDateFrom)" onblur="lostFocus(this)" type="text" class="text" size="12" maxlength="10" name="txtImportDateFrom" value="<tbl:writeparam name="txtImportDateFrom"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtImportDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
		             -- 
		            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtImportDateTo)" onblur="lostFocus(this)" type="text" class="text" size="12" maxlength="10" name="txtImportDateTo" value="<tbl:writeparam name="txtImportDateTo" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtImportDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
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
  
   <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head" width="10%" nowrap>记录号</td>
          <td class="list_head" width="10%">操作员</td>
          <td class="list_head" width="10%">组织机构</td>
          <td class="list_head" width="10%" nowrap>创建时间</td>
          <td class="list_head" width="20%">导入说明</td>
          <td class="list_head" width="27%">任务处理情况</td>
          <td class="list_head" width="8%" nowrap></td>
          <td class="list_head" width="5%" nowrap></td>
        </tr>

  
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	GeHuaUploadCustBatchHeaderDTO theDto = (GeHuaUploadCustBatchHeaderDTO)pageContext.getAttribute("oneline");
	if(theDto == null) theDto = new GeHuaUploadCustBatchHeaderDTO();
  
  String strOrg = Postern.getOrganizationDesc(theDto.getCreateOrgID());
  String operatorName = Postern.getOperatorNameByID(theDto.getCreateOpID());
  
  int batchID=theDto.getBatchID();
  if(strOrg==null)strOrg="";
  if(operatorName==null)operatorName="";
%>
   
  <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      <td align="center" nowrap><tbl:write name="oneline" property="batchNo"/></td>
      <td align="center" nowrap><%=operatorName%></td>  
      <td align="center"><%=strOrg%></td>
      <td align="center"><tbl:writedate name="oneline" property="createTime" includeTime="true" /></td>
      <td align="center" ><tbl:write name="oneline" property="comments"/></td>
      <td align="center" >
      <%
        if(batchID!=0){
            BatchJobDTO batchJobDTO=Postern.getBatchJobDTOByBatchID(batchID);
            if(batchJobDTO!=null){
            pageContext.setAttribute("JobDTO",batchJobDTO);
      %>
            <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td align="right" width="40%" nowrap>&nbsp;&nbsp;执行方式:</td>
                <td align="left" width="60%"><d:getcmnname typeName="SET_B_SCHEDULETYPE" match="JobDTO:scheduleType" /><BR>
                </td>
            </tr>
            <tr>
                <td align="right" nowrap>&nbsp;&nbsp;执行状态:</td>
                <td align="left"><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="JobDTO:status" /><BR></td>
            </tr>
            <%
            if("S".equals(batchJobDTO.getScheduleType())){
            %> 
            <tr>
                <td align="right" nowrap>设定执行时间:</td>
                <td align="left"><tbl:writedate name="JobDTO" property="scheduleTime" includeTime="true" /><BR></td>
            </tr>
            <%}
            if("S".equals(batchJobDTO.getStatus())||"F".equals(batchJobDTO.getStatus())){
            %>
            <tr>
                <td align="right" nowrap>执行开始时间:</td>
                <td align="left"><tbl:writedate name="JobDTO" property="executeStartTime"  includeTime="true"/><BR></td>
            </tr>
            <tr>
            <td align="right" nowrap>执行结束时间:</td>
            <td align="left"><tbl:writedate name="JobDTO" property="executeEndTime" includeTime="true"/><BR></td>
            </tr>
      <%
            }
      %>
            </table>
      <%
        }
        }else{
      %>
      没有创建任务
      <%
      }
      %>
      </td>
      <!--是两个链接-->
      <td align="center" nowrap>
      <%if(batchID==0){%>
      <pri:authorized name="batchJob_for_resume.do">
      <a href="javascript:resume_click('<tbl:write name="oneline" property="batchNo"/>')" class="link12" >开通</a>
      </pri:authorized>
      <%}%>
      <br>
      </td>
      
      <td align="center" nowrap>
      <pri:authorized name="batch_for_resume_query.export">
      <a href="javascript:export_click('<tbl:write name="oneline" property="batchNo"/>')" class="link12" >导出</a>
      </pri:authorized>
      </td> 
     </tr>
    
</lgc:bloop>    
<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>

 <table  border="0"  cellpadding="0" cellspacing="8" align="right">
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  </rs:hasresultset> 		
		
</form> 
    </td>
  </tr>
</table>   
 