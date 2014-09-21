<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.VOIPCmdProDTO"%>

<SCRIPT language="JavaScript">
function back_submit(){
	url="<bk:backurl property='voip_cmdprocess_query.do'/>";
    document.location.href=url;
}
</SCRIPT>

<form name="frmPost" method="post" >

    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    	<td colspan="2" height="8"></td>
     </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">�������ʷ��¼��ϸ</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br> 
<%
Map mapSystemEvent=Postern.getAllSystemEvent();
%>
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
	<%
	VOIPCmdProDTO dto=(VOIPCmdProDTO)pageContext.getAttribute("oneline");
	String eventName=Postern.getEventNameByEventID(dto.getEventID());
	if(eventName==null)eventName="";
	%> 
	<tr>
	          <td  class="list_bg2"><div align="right">���к�</div></td>         
                  <td class="list_bg1" align="left">
                  <input type="text" name="txtseqNo" size="25"  value="<tbl:write name="oneline" property="seqNo" />" class="textgray" readonly >
                  </td>       
		  <td class="list_bg2"><div align="right">����ID</div></td>
                 <td class="list_bg1" align="left">
                  <input type="text" name="txtifID" size="25"  value="<tbl:write name="oneline" property="ifID" />" class="textgray" readonly >
              	 </td>       
 	</tr> 
	 <tr>
		<td  class="list_bg2"><div align="right">��������</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txtifName" size="25"  value="<tbl:write name="oneline" property="ifName" />" class="textgray" readonly >
                </td> 
                 <td  class="list_bg2"><div align="right">�������ID</div></td>         
                 <td class="list_bg1" align="left">
				<input type="text" name="txtqueueID" size="25"  value="<tbl:write name="oneline" property="queueID" />" class="textgray" readonly >
                </td>        
       </tr>
	 <tr>
		<td  class="list_bg2"><div align="right">�¼�ID</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txteventID" size="25"  value="<tbl:write name="oneline" property="eventID" />" class="textgray" readonly >
                </td> 
                 <td  class="list_bg2"><div align="right">��������</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtcmdName" size="25"  value="<tbl:write name="oneline" property="cmdName" />" class="textgray" readonly >
                 </td>        
       </tr>
       
	 <tr>
		<td  class="list_bg2"><div align="right">�绰����</div></td>         
                <td class="list_bg1" align="left" colspan="3">
                <input type="text" name="txtphoneNo" size="25"  value="<tbl:write name="oneline" property="phoneNo" />" class="textgray" readonly >
                </td> 
                         
       </tr>
       <tr>
		<td  class="list_bg2"><div align="right">����ID</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txttranID" size="25"  value="<tbl:write name="oneline" property="tranID" />" class="textgray" readonly >
                </td> 
                 <td  class="list_bg2"><div align="right">��������</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtsendDate" size="25"  value="<tbl:write name="oneline" property="sendDate" />" class="textgray" readonly >
                 </td>        
       </tr>
       <tr>
		<td  class="list_bg2"><div align="right">����ʱ��</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txtsendTime" size="25"  value="<tbl:write name="oneline" property="sendTime" />" class="textgray" readonly >
                </td> 
                 <td  class="list_bg2"><div align="right">��������</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtrevDate" size="25"  value="<tbl:write name="oneline" property="revDate" />" class="textgray" readonly >
                 </td>        
       </tr>
       <tr>
		<td  class="list_bg2"><div align="right">����ʱ��</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txtrevTime" size="25"  value="<tbl:write name="oneline" property="revTime" />" class="textgray" readonly >
                </td> 
                 <td  class="list_bg2"><div align="right">�¼�����</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txttranID" size="25"  value="<%=eventName%>" class="textgray" readonly >
                 </td>        
       </tr>
       <tr>
		<td  class="list_bg2"><div align="right">����ϵͳ������</div></td>         
                <td class="list_bg1" align="left" colspan="3">
                <textarea name="txtTerminalDevices"   cols=82 rows=9 readonly style="background:E1E1E1;" ><tbl:write name="oneline" property="cmdCode" /></textarea>
                </td> 
      
       </tr>
 </lgc:bloop>  
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>

<br>
	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td><input name="Submit" type="button" class="button" value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>     
        </tr>
      </table>
</form>
