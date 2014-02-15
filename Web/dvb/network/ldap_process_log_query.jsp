<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
 
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.LdapProcessLogDTO"%>

<script language=javascript>
function check_form(){
            if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "��ʼʱ��"))
            {
                
        	return false;	
            }
            if (!check_Num(document.frmPost.txtEventID, true, "�¼�ID"))
		return false;
	 if (!check_Num(document.frmPost.txtDeviceID, true, "�豸ID"))
		return false;	
	 
	if (!check_Num(document.frmPost.txtQueueID, true, "QueueID"))
		return false;		
          if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "��ֹʱ��"))
            {
                
        	return false;	
            }
            if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}   	
	 
       	
	return true;
}
function back_submit()
{
	self.location.href="ldap_interface_index.screen";
} 

function view_detail_click(aiNO) {
    self.location.href = "ldap_process_log_detail.do?txtSeqNo="+aiNO;
 }
 function view_command_click(aiNO) {
    self.location.href = "ldap_queue_detail.do?txtQueueID="+aiNO;
 }

function query_submit()
{
        if (check_form()) document.frmPost.submit();
}

 
 
</script>
 <br>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
  <TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">LDAP�ӿ���־��¼��ѯ</td>
  </tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<%
	pageContext.setAttribute("allSysyemEvent",Postern.getAllSystemEvent());
	 
	 
	if(Postern.getAllCommandName()!=null)
	pageContext.setAttribute("allCommandName",Postern.getAllCommandName());
	
	else 
	pageContext.setAttribute("allCommandName","");
	
%>
<form name="frmPost" action="ldap_log_query_result.do" method="post" >    
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tr>   
         
           <td class="list_bg2"><div align="right">�¼�ID</div></td>
           <td class="list_bg1" align="left">
           <input type="text" name="txtEventID" size="25"  maxlength="10"  value="<tbl:writeparam name="txtEventID"/>" >
     	  </td>
     	   <td class="list_bg2"><div align="right">QueueID</div></td>
           <td class="list_bg1" align="left">
               <input type="text" name="txtQueueID" size="25" maxlength="10"  value="<tbl:writeparam name="txtQueueID"/>" >
     	  </td> 
    </tr>
     <tr>  
          <td class="list_bg2"><div align="right">������</div></td>
           <td class="list_bg1" align="left">
             <d:selcmn name="txtProcessResult" mapName="SET_N_LDAP_LOGPROCESSRESULT" match="txtProcessResult" width="23" />
     	  </td> 
         <td class="list_bg2"><div align="right">��־����</div></td>
         <td class="list_bg1"  align="left">
          <tbl:select name="txtCommandName" set="allCommandName" match="txtCommandName" width="23" />
         </td> 
    </tr>
    
       
    
     <tr>  
        <td class="list_bg2"><div align="right">�豸ID</div></td>
        <td class="list_bg1" align="left">
          <input type="text" name="txtDeviceID" size="25" maxlength="10"  value="<tbl:writeparam name="txtDeviceID"/>" >
     	</td>
        <td  class="list_bg2"><div align="right">����ʱ��</div></td>
           <td class="list_bg1" align="left">  
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
             </td> 
    </tr>
      
   </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		         <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
                       <td><img src="img/button2_l.gif" width="11" height="20"></td>
                       <td width="20" ></td>  
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
         
   
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
<rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
 
         <tr class="list_head">
         <td   class="list_head"><div align="center">��ˮ��</div></td>
          <td    class="list_head"><div align="center">QueueID</div></td>      
          <td    class="list_head"><div align="center">�¼�ID</div></td>         
          <td   class="list_head"><div align="center">��������</div></td>
          <td   class="list_head"><div align="center">�ͻ�ID</div></td>
          <td   class="list_head"><div align="center">�豸ID</div></td>
          <td   class="list_head"><div align="center">������</div></td>
          <td    class="list_head"><div align="center">����ʱ��</div></td>     
           
     </tr> 
        
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
  
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
           <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqno"/>')" class="link12">
              <div align="center">
               <tbl:writenumber name="oneline" property="seqno" digit="9" />
             </div>     
          </td>
           
                <td align="center"><tbl:writenumber name="oneline"  property="queueID" digit="9"  hideZero="true"/></td> 
      		<td align="center"><tbl:write name="oneline"  property="eventID"/></td> 
      		 
      		<td align="center"><tbl:write name="oneline"  property="commandName"/></td>
      		 		
      		<td align="center"><tbl:write name="oneline"  property="CustomerID"/></td>
      		 
      		<td align="center"><tbl:write name="oneline"  property="deviceID"/></td>
      		<td align="center"><d:getcmnname typeName="SET_N_LDAP_LOGPROCESSRESULT" match="oneline:ProcessResult"/></td>   
      		<td align="center"><tbl:writedate name="oneline" property="createTime" includeTime="true" /></td>
      	 </tbl:printcsstr>
</lgc:bloop> 
   
     <tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="8" >
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
               <a href="javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>   
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
 

      
