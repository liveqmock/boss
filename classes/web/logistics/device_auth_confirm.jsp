<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%
  //取得默认的产品列表
	String defaultProductID = Postern.getCheckedProductIDList();
	String defaultProductName = Postern.getDefaultAuthProductNameList();
  String txtProductIDListValue = request.getParameter("txtProductIDListValue");
	String txtProductIDList = request.getParameter("txtProductIDList");
	if(txtProductIDListValue == null || "".equals(txtProductIDListValue)){
	    txtProductIDListValue = defaultProductName;
	    txtProductIDList = defaultProductID;
	}
%>
<script language=javascript>
<!--
//参数说明：type为类型，typeName为名字（不含value名字），subTypeValue为该参数的值
function open_select_product(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="product_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	param=param + "&txtProductIDList="+document.frmPost.txtProductIDList.value;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;

	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=600,height=320,screenX=600,screenY=400";
	window.open(param,"",windowFeatures);
}

function create(){
	document.frmPost.submit();    
}
 
function back_submit(){
	document.frmPost.action="device_auth.do";
	document.frmPost.submit();
} 
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">机卡预授权信息确认</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" action="device_auth_confirm.do" method="post" >
    <input type="hidden" name="confirm_post" value="true">
    <input type="hidden" name="func_flag" value="1007">

<% 
    CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
    ArrayList deviceList=(ArrayList)RepCmd.getPayload();
    Collection errorCols =(Collection)deviceList.get(0);
    Collection okCols =(Collection)deviceList.get(1);
%>    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">
       	   <td class="list_head" colspan="3">文件错误信息</td>
       </tr>
       <tr class="list_head">
        	 <td class="list_head">设备序列号</td>
           <td class="list_head">设备型号</td>
           <td class="list_head">错误信息</td>  
        </tr> 
<%
    Iterator errorItr =errorCols.iterator();
    while (errorItr.hasNext()){
       TerminalDeviceDTO  dto = (TerminalDeviceDTO)errorItr.next();
       pageContext.setAttribute("oneline", dto);
%>
	     <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	         <td align="center"><tbl:write name="oneline" property="serialNo" /></td>
	         <td align="center"><tbl:write name="oneline" property="deviceModel" /></td>
     	     <td align="center"><tbl:write name="oneline" property="comments" /></td>
     	 </tr>
 <%}%> 
   	   <tr>
	        <td colspan="3" class="list_foot"></td>
	      </tr>		
        <tr>
            <td class="list_bg2" align="right" colspan="3">总共<%=errorCols.size()%>条记录</td>
        </tr>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">
       	   <td class="list_head" colspan="2">文件正确信息</td>
       </tr>
       <tr class="list_head">
        	 <td class="list_head">设备序列号</td>
           <td class="list_head">设备型号</td>
        </tr> 
<%
    Iterator okItr =okCols.iterator();
    while (okItr.hasNext()){
       TerminalDeviceDTO  dto = (TerminalDeviceDTO)okItr.next();
       pageContext.setAttribute("oneline", dto);
%>
	     <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	         <input type="hidden" name="serialNo" value="<tbl:write name="oneline" property="serialNo" />" >
	         <td align="center"><tbl:write name="oneline" property="serialNo" /></td>
	         <td align="center"><tbl:write name="oneline" property="deviceModel" /></td>
     	 </tr>
 <%}%> 
   	   <tr>
	        <td colspan="2" class="list_foot"></td>
	      </tr>		
        <tr>
            <td class="list_bg2" align="right" colspan="3">总共<%=okCols.size()%>条记录</td>
        </tr>
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">  
        <tr> 
          <td class="list_bg2" align="right" >操作批号*</td>
          <td class="list_bg1" width="75%">
            <input type="text" name="txtBatchID" size="20"  value="<tbl:writeparam name="txtBatchID" />" >
          </td>
        </tr>  
        <tr>
	        <td class="list_bg2" align="right" >备注</td>    
	        <td class="list_bg1" >
	         <textarea name="txtDesc"  length="5" cols=75 rows=4><tbl:writeSpeCharParam name="txtDesc" /></textarea>
	        </td>		
        </tr>
        <tr>     
          <td class="list_bg2" align="right" >预授权产品*</td>
          <td class="list_bg1" width="75%">
            <input type="text" name="txtProductIDListValue" size=50 value="<%=txtProductIDListValue %>" >
	          <input name="txtProductIDList" type="hidden" value="<%=txtProductIDList %>">
           <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select_product('PRODUCT','txtProductIDList',document.frmPost.txtProductIDList.value,'','','')"> 
          </td>
        </tr>
    </table>
    
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr align="center">
	   <td class="list_bg1">   
       <table border="0" cellspacing="0" cellpadding="0" align=center>
          <tr>
		        <td><img src="img/button2_r.gif" border="0" ></td>
		        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
		        <td><img src="img/button2_l.gif" border="0" ></td>
		   <% if (okCols.size()>0){ %> 
		        <td width="20"></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:create()" class="btn12">确&nbsp;认</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
       <% } %>   
          </tr>
       </table>
	</td>
    </tr>
</table>     
<tbl:generatetoken />
</form>  