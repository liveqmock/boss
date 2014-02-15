<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>  

<%
	pageContext.setAttribute("AllDeviceModelList" ,Postern.getDeviceModelByClass("SC"));

%>
<script language=javascript>
<!--
function checkAll()
{
    if (document.frmPost.chkSerialNo!=null)
    {
            if (document.frmPost.chkSerialNo.length > 1) 
	    {
	        for (i=0;i<document.frmPost.chkSerialNo.length;i++)
                    document.frmPost.chkSerialNo[i].checked=document.frmPost.allcheck.checked;
	    } 
	    else 
	    {
            	document.frmPost.chkSerialNo.checked=document.frmPost.allcheck.checked;
            }
    }
}

function fillList()
{
        document.frmPost.scList.value="";
	document.frmPost.scIDList.value="";
	if (document.frmPost.chkSerialNo!=null)
	{
            if (document.frmPost.chkSerialNo.length > 1) 
	    {
	        for (i=0;i<document.frmPost.chkSerialNo.length;i++)
		    if (document.frmPost.chkSerialNo[i].checked)
		    {
			document.frmPost.scList.value=document.frmPost.scList.value + document.frmPost.chkSerialNo[i].value  + "\r\n";
			document.frmPost.scIDList.value=document.frmPost.scIDList.value+document.frmPost.DeviceID[i].value  + "\r\n";
    	    	    }
	    } 
	    else 
	    {
            	if (document.frmPost.chkSerialNo.checked) {
                    document.frmPost.scList.value=document.frames.frmPost.chkSerialNo.value + "\r\n";
		    document.frmPost.scIDList.value=document.frmPost.DeviceID.value +"\r\n";
	        } else {
                document.frmPost.scList.value = '';
            	}
            }
	}
}

function ok_submit()
{
    fillList();
    var hiddenValue=document.frmPost.scList.value;
    var hiddenName="" + document.frmPost.typeName.value;
    window.opener.frmPost.elements[hiddenName].value= window.opener.frmPost.elements[hiddenName].value + hiddenValue;
    var hiddenValue2 = document.frmPost.scIDList.value;
    var hiddenName2 ="" + document.frmPost.deviceIDList.value;
	window.opener.frmPost.elements[hiddenName2].value= window.opener.frmPost.elements[hiddenName2].value + hiddenValue2;
    self.close();
    
}
function   check_form()
{
    return true;
}   
//-->
</script>

<form name="frmPost" action="sc_query_result.do" method="post" onsubmit="return check_form()">

<table  width="450" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19" align="right"></td>
    <td align="center" class="title">智能卡序列号选择</td>
  </tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
 <td  class="querypart" > 
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3" >
    <tr>   
      <td class="list_bg2" align="right">型号</td>
      <td colspan="3" class="list_bg1" align="left">
      	 <tbl:select name="txDeviceModel" set="AllDeviceModelList" match="txDeviceModel"  />
      </td>
    </tr>
    <tr>  
      <td class="list_bg2" align="right">序列号</td>
      <td colspan="3" class="list_bg1" align="left">
          <input type="text" name="txtStartSCSN" value=""> -- <input type="text" name="txtEndSCSN" value="">
      </td>
      </td>

    </tr>

   </table>
  </td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="submit" class="button" value="查&nbsp;询" ></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20" ></td>
		  </tr>
	  </table></td>
	</tr>
</table> 


<table width="100%" border="0" cellpadding="2" cellspacing="1" class="list_bg" >
  <tr class="list_head">
    <td class="list_head" width="20" nowrap><input type=checkbox onclick="checkAll()" name="allcheck"></td>
    <td class="list_head" width="20" nowrap>ID</td>
    <td class="list_head" width="60" nowrap>类型</td>
    <td class="list_head" width="60" nowrap>型号</td>
    <td class="list_head" width="200" nowrap>序列号</td>
  </tr>

<rs:hasresultset>
<%
String defaultDevice = request.getParameter("defaultDevice");
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
              TerminalDeviceDTO terminalDeviceDto =(TerminalDeviceDTO)pageContext.getAttribute("oneline");
              TerminalDeviceDTO dto =new TerminalDeviceDTO();

if(defaultDevice==null || "".equals(defaultDevice))
{

}
else
{

              if (defaultDevice.indexOf(String.valueOf(terminalDeviceDto.getSerialNo()))>=0){
                   dto.setSerialNo(terminalDeviceDto.getSerialNo());

              }else{
                   dto.setSerialNo(terminalDeviceDto.getSerialNo());

              }
}
              pageContext.setAttribute("onebox",dto);                 
%>

    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	<td align="center" ><tbl:checkbox name="chkSerialNo"  value="oneline:SerialNo" match="<%=defaultDevice%>" multipleMatchFlag="true" /></td>  
        <td align="center"><tbl:write name="oneline" property="DeviceID" /></td>
        <td align="center"><tbl:write name="oneline" property="DeviceClass" /></td>
        <td align="center"><tbl:write name="oneline" property="DeviceModel" /></td>
        <td align="center"><tbl:write name="oneline" property="SerialNo" /></td>
	<input type="hidden" name="DeviceID" value="<tbl:write name="oneline" property="DeviceID" />">
    </tbl:printcsstr>
</lgc:bloop>

<tr>
					<td colspan="11" class="list_foot"></td>
			</tr>
        	<tr>
              <td align="right" class="t12" colspan="11" >
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
    <input type="hidden" name="txtFrom" size="22" value="1">
    <input type="hidden" name="txtTo" size="22" value="10"> 


<input type="hidden" name="scList" value="">
<input type="hidden" name="scIDList" value="">
<input type="hidden" name="typeName" value="<%=request.getParameter("typeName") %>">
<input type="hidden" name="typeValue" value="<%=request.getParameter("typeValue") %>">
<input type="hidden" name="defaultDevice" value="<%=request.getParameter("defaultDevice") %>">
<input type="hidden" name="deviceIDList" value="<%=request.getParameter("deviceIDList") %>">
<input type="hidden" name="processFlag" value="<%=request.getParameter("processFlag") %>">

</form>


<table border="0" cellspacing="0" cellpadding="0">
	<br><br><br>
  <tr>
    <td width="200" ></td>		
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="确认" onclick="javascript:ok_submit()"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
    <td width="20" ></td>	
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="关闭窗口" onclick="javascript:self.close();"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

  </tr>
</table>