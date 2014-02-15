<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<script language=javascript>
function create(){
    document.frmPost.submit();    
}
 
function back_submit(){
	document.frmPost.action="device_disauth.do";
	document.frmPost.submit();
} 
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">������Ԥ��Ȩ��Ϣȷ��</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" action="device_disauth_confirm.do" method="post" >    
    <input type="hidden" name="confirm_post" value="true">
    <input type="hidden" name="func_flag" value="1014">

<% 
    CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
    ArrayList deviceList=(ArrayList)RepCmd.getPayload();
    Collection errorCols =(Collection)deviceList.get(0);
    Collection okCols =(Collection)deviceList.get(1);
%>    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">
       	   <td class="list_head" colspan="3">�ļ�������Ϣ</td>
       </tr>
       <tr class="list_head">
        	 <td class="list_head">�豸���к�</td>
           <td class="list_head">�豸�ͺ�</td>
           <td class="list_head">������Ϣ</td>  
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
            <td class="list_bg2" align="right" colspan="3">�ܹ�<%=errorCols.size()%>����¼</td>
        </tr>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">
       	   <td class="list_head" colspan="2">�ļ���ȷ��Ϣ</td>
       </tr>
       <tr class="list_head">
        	 <td class="list_head">�豸���к�</td>
           <td class="list_head">�豸�ͺ�</td>
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
            <td class="list_bg2" align="right" colspan="3">�ܹ�<%=okCols.size()%>����¼</td>
        </tr>
    </table>
   
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">  
        <tr> 
          <td class="list_bg2" align="right" >��������*</td>
          <td class="list_bg1" width="75%">
            <input type="text" name="txtBatchID" size="20"  value="<tbl:writeparam name="txtBatchID" />" >
          </td>
        </tr>  
        <tr>
	        <td class="list_bg2" align="right" >��ע</td>    
	        <td class="list_bg1" >
	         <textarea name="txtDesc"  length="5" cols=75 rows=4><tbl:writeSpeCharParam name="txtDesc" /></textarea>
	        </td>		
        </tr>
   </table>
    
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr align="center">
	   <td class="list_bg1">   
       <table border="0" cellspacing="0" cellpadding="0" align=center>
          <tr>
		        <td><img src="img/button2_r.gif" border="0" ></td>
		        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">��&nbsp;��</a></td>
		        <td><img src="img/button2_l.gif" border="0" ></td>
		   <% if (okCols.size()>0){ %> 
		        <td width="20"></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:create()" class="btn12">ȷ&nbsp;��</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
       <% } %>   
          </tr>
       </table>
	   </td>
    </tr>
</table>     
<tbl:generatetoken />
</form>  