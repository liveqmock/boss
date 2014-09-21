<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>

<%@ page import="com.dtv.oss.dto.QueryDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>

<script language=javascript>
function query_submit(){
	if (check_frm()){
		document.frmPost.submit();
	}
}

function view_batch_detail(batchid){
	var strURL="batch_query_show_detail.do?txtQueryID="+batchid+"&txtActionType=batch&txtTemplateFlag=all&txtShowFlag=Y";
	self.location.href=strURL;
}

function check_frm(){
	if (!check_TenDate(document.frmPost.txtCreateTime1, true, "��¼������ʼʱ��")) 
			return false;
	if (!check_TenDate(document.frmPost.txtCreateTime2, true, "��¼������ֹʱ��")) 
			return false;
	if (!check_TenDate(document.frmPost.txtScheduleTime1, true, "Ԥ��ִ����ʼʱ��")) 
			return false;
	if (!check_TenDate(document.frmPost.txtScheduleTime2, true, "Ԥ��ִ�н�ֹʱ��")) 
			return false;
	if (!check_TenDate(document.frmPost.txtPerformTime1, true, "ʵ��ִ����ʼʱ��")) 
			return false;
	if (!check_TenDate(document.frmPost.txtPerformTime2, true, "ʵ��ִ�н�ֹʱ��")) 
			return false;
				
	return true;
}

function batch_query_create_submit(){
	document.frmPost.action="batch_query_create.screen";
	document.frmPost.submit();
}
function batch_query_model_create_submit(){
	document.frmPost.action="menu_batch_query_model_query.do";
	document.frmPost.submit();
}

</script>

<form name="frmPost" method="post" action="batch_query_query.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="batch">
<input type="hidden" name="txtTemplateFlag" size="20" value="<%=CommonConstDefinition.YESNOFLAG_NO%>">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">������ѯ����----��ѯ</td>
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
    <td class="list_bg2"><div align="right">��ѯ����</div></td>
    <td class="list_bg1"><input name="txtQueryName" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtQueryName" />"></td>
    <td class="list_bg2"><div align="right">���������</div></td>
    <td class="list_bg1"><d:selcmn name="txtQueryType" mapName="SET_B_QUERYTYPE"  match="txtQueryType"  width="23" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">ִ�з�ʽ</div></td>
    <td class="list_bg1"><d:selcmn name="txtScheduleType" mapName="SET_B_SCHEDULETYPE"  match="txtScheduleType"  width="23" /></td>
    <td class="list_bg2"><div align="right">״̬</div></td>
    <td class="list_bg1"><d:selcmn name="txtStatus" mapName="SET_B_QUERYSTATUS"  match="txtStatus"  width="23" />
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">��ѯ������ʼʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right">��ѯ������ֹʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">Ԥ��ִ����ʼʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtScheduleTime1)" onblur="lostFocus(this)" name="txtScheduleTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtScheduleTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right">Ԥ��ִ�н�ֹʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtScheduleTime2)" onblur="lostFocus(this)" name="txtScheduleTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtScheduleTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">ʵ��ִ����ʼʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPerformTime1)" onblur="lostFocus(this)" name="txtPerformTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtPerformTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPerformTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right">ʵ��ִ�н�ֹʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPerformTime2)" onblur="lostFocus(this)" name="txtPerformTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtPerformTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPerformTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  
  <!--
  <tr>
    <td class="list_bg2"><div align="right">�Ƿ�Ϊģ��</div></td>
    <td class="list_bg1" colspan="3"><d:selcmn name="templateFlag" mapName="SET_G_YESNOFLAG"  match="templateFlag"  width="23" /></td>
  </tr>
  -->
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="ֱ�Ӵ���" onclick="javascript:batch_query_create_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��ģ�崴��" onclick="javascript:batch_query_model_create_submit()"></td>
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


<rs:hasresultset>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">��ѯID</td>
    <td class="list_head">��ѯ����</td>
    <td class="list_head">���������</td>
    <td class="list_head">״̬</td>
    <td class="list_head">������</td>
    <td class="list_head">����ʱ��</td>
    <td class="list_head">ִ�з�ʽ</td>
    <td class="list_head">Ԥ��ִ��ʱ��</td>
    <td class="list_head">ʵ��ִ��ʱ��</td>
  </tr>

   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    QueryDTO dto = (QueryDTO)pageContext.getAttribute("oneline");
    String templateFlag = dto.getTemplateFlag();
    if(templateFlag==null)templateFlag=CommonConstDefinition.YESNOFLAG_NO;
    dto.setTemplateFlag(templateFlag);
    pageContext.setAttribute("QueryDTO",dto);
    
    String strOpName="";
    strOpName=Postern.getOperatorNameByID(dto.getCreateOperatorId());
    if(strOpName==null)
    	strOpName="";
    
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
 
    <td align="center"><a href="javascript:view_batch_detail('<tbl:write name="QueryDTO" property="queryId"/>')" class="link12" ><tbl:write name="QueryDTO" property="queryId"/></a></td>
    <td align="left"><tbl:write name="QueryDTO" property="queryName"/></td>  
    <td align="center"><d:getcmnname typeName="SET_B_QUERYTYPE" match="QueryDTO:queryType" /></td>
    <td align="center"><d:getcmnname typeName="SET_B_QUERYSTATUS" match="QueryDTO:status" /></td>
    <td align="center"><%=strOpName %></td>
    <td align="center"><tbl:writedate name="QueryDTO" property="createTime" includeTime="false"/>
    		       <br>
    		       <tbl:writedate name="QueryDTO" property="createTime" onlyTime="true"/>	
    </td>
    <td align="center"><d:getcmnname typeName="SET_B_SCHEDULETYPE" match="QueryDTO:scheduleType" /></td>
    <td align="center"><tbl:writedate name="QueryDTO" property="scheduleTime" /><br><tbl:writedate name="QueryDTO" property="scheduleTime" onlyTime="true"/></td>
    <td align="center"><tbl:writedate name="QueryDTO" property="performTime" /><br><tbl:writedate name="QueryDTO" property="performTime" onlyTime="true"/></td>
    
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
</table>

<table  border="0" align="center" cellpadding="0" cellspacing="8">
  <tr>
    <td>��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ<span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" /><span>ҳ</td>
    <td>��<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">��ҳ</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">��һҳ</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">��һҳ</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">ĩҳ</a></td>
</rs:notlast>
    <td align="right">��ת��</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>ҳ</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
  </table>
  
 </rs:hasresultset>                 


</form>  
         

      