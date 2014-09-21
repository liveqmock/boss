<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.BrconditionDTO,
                 java.util.*"%>
 

<SCRIPT language="JavaScript">
function check_frm ()
{
    if (check_Blank(document.frmPost.txtHostName, true, "��������"))
	    return false;
    if (check_Blank(document.frmPost.txtIpAddress, true, "IP��ַ"))
	    return false;
   if (check_Blank(document.frmPost.txtProtocolType, true, "�ӿ�Э������"))
	    return false;
    if (check_Blank(document.frmPost.txtPort, true, "�˿�"))
	    return false;	
 	        
  if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
	    return false;
  if (check_Blank(document.frmPost.txtLoginID, true, "��¼�˺�"))
	    return false;
 if (check_Blank(document.frmPost.txtLoginPWD, true, "��¼����"))
	    return false;
 if (check_Blank(document.frmPost.txtcmentrydir, true, "��Ŀ¼·��"))
	    return false;
	    		    	    
	    
	return true;
}

function ldap_host_edit_submit(){
 if (window.confirm('��ȷ��Ҫ�޸�������Ϣ��?')){
  if (check_frm()){
	    document.frmPost.action="ldap_host_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
}
}
 
  
 

</SCRIPT>

<form name="frmPost" method="post">
	 
	
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="225" >
	 
	<!-- ���嵱ǰ���� -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">LDAP������Ϣά��</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

<table width="100%" align="center" border="0" cellspacing="1" cellpadding="3">
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
	 
 
	<tr>
	          <td  class="list_bg2"><div align="right">����ID</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtHostID" size="25"  value="<tbl:write name="oneline" property="hostID" />" class="textgray" readonly >
                  </td>       
		 <td class="list_bg2"><div align="right">��������*</div></td>
                 <td class="list_bg1" align="left">
                   <input type="text" name="txtHostName" size="25"  value="<tbl:write name="oneline" property="hostName" />">
                  </td>       
                  
	</tr> 
	 <tr>
		<td  class="list_bg2"><div align="right">�ӿ�Э������*</div></td>         
                <td class="list_bg1" align="left">
                  <input type="text" name="txtProtocolType" size="25"  value="<tbl:write name="oneline" property="protocolType" />">
                 
                </td> 
                 <td  class="list_bg2"><div align="right">IP��ַ*</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtIpAddress" size="25"  value="<tbl:write name="oneline" property="ipAddress" />">
                 
                 </td>        
       </tr>
	 <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
	 <tr>
		<td  class="list_bg2"><div align="right">�˿�*</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txtPort" size="25"  value="<tbl:write name="oneline" property="port" />">
                 
                </td> 
                <td  class="list_bg2"><div align="right">��¼�˺�*</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txtLoginID" size="25"  value="<tbl:write name="oneline" property="loginID" />">
                 
                </td> 
       </tr>
       
	<tr>
	  <td class="list_bg2"><div align="right">��¼����*</div></td>
          <td class="list_bg1" align="left">
           <input type="text" name="txtLoginPWD" size="25"  value="<tbl:write name="oneline" property="loginPWD" />">
          <td class="list_bg2"><div align="right">��Ŀ¼·��*</div></td>
          <td class="list_bg1" align="left">
          <input type="text" name="txtcmentrydir" size="25"  value="<tbl:write name="oneline" property="cmentrydir" />">
	</tr>
	  <tr>
		 <td  class="list_bg2"><div align="right">״̬*</div></td>         
                 <td class="list_bg1" align="left" colspan="3">
                 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23"/>
                 </td>        
	</tr>
	 
	  </lgc:bloop>  
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="ldap_host_query.do" />" class="btn12">��&nbsp;��</a></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
                      
            <td width="20" ></td>  
            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
	    value="��&nbsp;��" onclick="javascript:ldap_host_edit_submit()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>    
             
                  
        </tr>
      </table>	

</form>
