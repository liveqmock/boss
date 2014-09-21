<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern,java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.stat.DeviceSwapStatDTO"%>
<%@ page import="com.dtv.oss.web.taglib.util.QueryPageProp"%>

<script language=javascript>

function query_submit(){
	document.frmPost.action="device_swap_stat.do";
	document.frmPost.txtTo.value = document.frmPost.txtSelTo.value;
	setCookie("device_swap_stat.do",document.frmPost.txtTo.value);
	if(checkDate()){
		document.frmPost.submit();
	}
}
function checkDate(){
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "起始时间")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "结束时间")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"结束时间必须大于等于起始时间")){
		
		return false;
	}
	if (check_Blank(document.frmPost.txtOrgID, true, "区/站"))
		return false;
	return true;
}

function OnBlur(){
	if(check_Blank(document.frmPost.txtSelTo, true, "页面记录数")){
		document.frmPost.txtSelTo.focus();
		return false;
	}
	if(!checkPlainNum(document.frmPost.txtSelTo,true,3,"页面记录数")){
		document.frmPost.txtSelTo.focus();
		return false;
	}
	if(document.frmPost.txtSelTo.value == 0 || document.frmPost.txtSelTo.value == 1){
		alert("页面记录数不能为0或1。");
		document.frmPost.txtSelTo.focus();
		return false;
	}
}

function downloadExcel(){
	var desc = "设备置换统计" + "("+document.frmPost.txtOrgDesc.value+")";
	if(document.frmPost.txtCreateTime1.value != "" && document.frmPost.txtCreateTime2.value != "")
		desc = desc + "(从" + document.frmPost.txtCreateTime1.value + "到" + document.frmPost.txtCreateTime2.value + ")";
	else if(document.frmPost.txtCreateTime1.value != "")
		desc = desc + "(大于" + document.frmPost.txtCreateTime1.value + ")";
	else if(document.frmPost.txtCreateTime2.value != "")
		desc = desc + "(小于" + document.frmPost.txtCreateTime2.value + ")";
	download(document.frmPost,desc);
}


// 写入cookie
function setCookie(name,value)   
{
        var Days = 30;   
        var exp = new Date();   //new   Date("December   31,   9998");
        exp.setTime(exp.getTime() + Days*24*60*60*1000);   
        document.cookie = name + "="+ escape(value) + ";expires=" + exp.toGMTString();   
}   
  
//读取cookie
function getCookie(name)   
{
		//获取cookie字符串
		var strCookie=document.cookie;
		//将多cookie切割为多个名/值对
		var arrCookie=strCookie.split("; ");
		//遍历cookie数组，处理每个cookie对
		for(var i=0;i<arrCookie.length;i++){
      		var arr=arrCookie[i].split("=");
      		//找到名称为userId的cookie，并返回它的值
      		if(name==arr[0]){
             	return arr[1];
            	break;
      		}
		}
		return null;
}

</script>


<%
	int index = 1;
	QueryPageProp beanProp = (QueryPageProp) pageContext.getRequest().getAttribute(WebKeys.CURRENT_RESULTSET_PROPERTY);
	if(beanProp!=null){
		int iVal = beanProp.getCurPageNo();
		int pagesize = 0;
		String txtSelTo = (String)request.getParameter("txtSelTo");
		if(txtSelTo!=null && !"".equals(txtSelTo))
			pagesize = WebUtil.StringToInt(txtSelTo);
		index = (iVal-1)*pagesize + 1;
	}
%>

<form name="frmPost" method="post" action="device_swap_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备置换统计</td>
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
    <td class="list_bg2" align="right">区/站*</td>
	<td class="list_bg1">
		<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
		<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtOrgDesc" />" class="text">
		<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S,D,P','txtOrgID','txtOrgDesc')">
	</td>

    <td class="list_bg2"><div align="right">置换时间</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
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
			
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td background="img/button_bg.gif"  height="20" >
			<a href="javascript:downloadExcel()" class="btn12">导出查询结果</a></td>
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
</table>

<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
		<tr class="list_head">
			<td class="list_head" nowrap><div align="center">序号</div></td>
			<td class="list_head" nowrap><div align="center">客户姓名</div></td>
			<td class="list_head" nowrap><div align="center">设备置换原因</div></td>
			<td class="list_head" nowrap><div align="center">置换前设备名称</div></td>
			<td class="list_head" nowrap><div align="center">置换前设备编号</div></td>
			<td class="list_head" nowrap><div align="center">置换后设备名称</div></td>
			<td class="list_head" nowrap><div align="center">置换后设备编号</div></td>
			<td class="list_head" nowrap><div align="center">费用</div></td>
			<td class="list_head" nowrap><div align="center">操作员</div></td>
			<td class="list_head" nowrap><div align="center">置换时间</div></td>
			<td class="list_head" nowrap><div align="center">操作时间</div></td>
		</tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
		<td align="center" class="t12" nowrap><%=index++%></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="customername" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="createreason" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="olddevicemodel" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="olddeviceid" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="devicemodel" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="deviceid" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="value" /></td>
		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="operatorname" /></td>
		<td align="center" class="t12" nowrap><tbl:writedate name="oneline" property="workdate" /></td>
		<td align="center" class="t12" nowrap><tbl:writedate name="oneline" property="createtime" /></td>
  </tbl:printcsstr>
</lgc:bloop>
</table>
</div>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
			<!--
			<tr>
				<td colspan="25" class="list_foot"></td>
			</tr>
			-->
        	<tr>
				<td align="right" class="t12" colspan="25" >
				 <%if(request.getParameter("txtSelTo")==null){%>
			     页面记录数：<input type="text" name="txtSelTo" size="4" maxlength="3" value="10" class="text" onkeydown="if(event.keyCode==13) query_submit();" onblur="OnBlur()">
	    		 <%}else{%>
	    		 页面记录数：<input type="text" name="txtSelTo" size="4" maxlength="3" value="<%=request.getParameter("txtSelTo")%>" class="text" onkeydown="if(event.keyCode==13) query_submit();" onblur="OnBlur()">
	    		 <%}%>
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

</form>

<script language=javascript>
	
	
	var cookieValue = getCookie("device_swap_stat.do");
	if(cookieValue!=null)
		document.frmPost.txtSelTo.value = cookieValue;
	else
		document.frmPost.txtSelTo.value = 10;
		
	document.frmPost.txtTo.value = document.frmPost.txtSelTo.value;
</script> 
         

      