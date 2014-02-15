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
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "��ʼʱ��")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "����ʱ��")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"����ʱ�������ڵ�����ʼʱ��")){
		
		return false;
	}
	if (check_Blank(document.frmPost.txtOrgID, true, "��/վ"))
		return false;
	return true;
}

function OnBlur(){
	if(check_Blank(document.frmPost.txtSelTo, true, "ҳ���¼��")){
		document.frmPost.txtSelTo.focus();
		return false;
	}
	if(!checkPlainNum(document.frmPost.txtSelTo,true,3,"ҳ���¼��")){
		document.frmPost.txtSelTo.focus();
		return false;
	}
	if(document.frmPost.txtSelTo.value == 0 || document.frmPost.txtSelTo.value == 1){
		alert("ҳ���¼������Ϊ0��1��");
		document.frmPost.txtSelTo.focus();
		return false;
	}
}

function downloadExcel(){
	var desc = "�豸�û�ͳ��" + "("+document.frmPost.txtOrgDesc.value+")";
	if(document.frmPost.txtCreateTime1.value != "" && document.frmPost.txtCreateTime2.value != "")
		desc = desc + "(��" + document.frmPost.txtCreateTime1.value + "��" + document.frmPost.txtCreateTime2.value + ")";
	else if(document.frmPost.txtCreateTime1.value != "")
		desc = desc + "(����" + document.frmPost.txtCreateTime1.value + ")";
	else if(document.frmPost.txtCreateTime2.value != "")
		desc = desc + "(С��" + document.frmPost.txtCreateTime2.value + ")";
	download(document.frmPost,desc);
}


// д��cookie
function setCookie(name,value)   
{
        var Days = 30;   
        var exp = new Date();   //new   Date("December   31,   9998");
        exp.setTime(exp.getTime() + Days*24*60*60*1000);   
        document.cookie = name + "="+ escape(value) + ";expires=" + exp.toGMTString();   
}   
  
//��ȡcookie
function getCookie(name)   
{
		//��ȡcookie�ַ���
		var strCookie=document.cookie;
		//����cookie�и�Ϊ�����/ֵ��
		var arrCookie=strCookie.split("; ");
		//����cookie���飬����ÿ��cookie��
		for(var i=0;i<arrCookie.length;i++){
      		var arr=arrCookie[i].split("=");
      		//�ҵ�����ΪuserId��cookie������������ֵ
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
    <td class="title">�豸�û�ͳ��</td>
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
    <td class="list_bg2" align="right">��/վ*</td>
	<td class="list_bg1">
		<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
		<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtOrgDesc" />" class="text">
		<input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S,D,P','txtOrgID','txtOrgDesc')">
	</td>

    <td class="list_bg2"><div align="right">�û�ʱ��</div></td>
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
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td background="img/button_bg.gif"  height="20" >
			<a href="javascript:downloadExcel()" class="btn12">������ѯ���</a></td>
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
			<td class="list_head" nowrap><div align="center">���</div></td>
			<td class="list_head" nowrap><div align="center">�ͻ�����</div></td>
			<td class="list_head" nowrap><div align="center">�豸�û�ԭ��</div></td>
			<td class="list_head" nowrap><div align="center">�û�ǰ�豸����</div></td>
			<td class="list_head" nowrap><div align="center">�û�ǰ�豸���</div></td>
			<td class="list_head" nowrap><div align="center">�û����豸����</div></td>
			<td class="list_head" nowrap><div align="center">�û����豸���</div></td>
			<td class="list_head" nowrap><div align="center">����</div></td>
			<td class="list_head" nowrap><div align="center">����Ա</div></td>
			<td class="list_head" nowrap><div align="center">�û�ʱ��</div></td>
			<td class="list_head" nowrap><div align="center">����ʱ��</div></td>
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
			     ҳ���¼����<input type="text" name="txtSelTo" size="4" maxlength="3" value="10" class="text" onkeydown="if(event.keyCode==13) query_submit();" onblur="OnBlur()">
	    		 <%}else{%>
	    		 ҳ���¼����<input type="text" name="txtSelTo" size="4" maxlength="3" value="<%=request.getParameter("txtSelTo")%>" class="text" onkeydown="if(event.keyCode==13) query_submit();" onblur="OnBlur()">
	    		 <%}%>
                 ��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ
                 <span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" />ҳ
                 ����<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >��ҳ </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >��һҳ</a>
                 </rs:notfirst>

                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >��һҳ</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >ĩҳ</a>
                </rs:notlast>
                &nbsp;
                ת��
               <input type="text" name="txtPage" class="page_txt">ҳ 
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
         

      