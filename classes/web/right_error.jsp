<%@ taglib uri="logic" prefix="lgc" %>

        <BR>
        <BR>
        <BR>
<lgc:notempty name="SystemPrivilegeList" scope="session" skip="true" >    
        <table border=0 cellpadding=2 width="100%">
  	<caption><font color="red">��û�иò����Ĳ���Ȩ�ޣ�</font></caption>
  	        <tr><td align="center" colspan=2><font color="red">&nbsp;</font></td></tr>
  	        
  	        <tr><td align="center" colspan=2><input type="button" onclick="history.go(-1)" value="�� ��" ></td></tr>
  	</table>
</lgc:notempty>
        <table border=0 cellpadding=2 width="100%">
  	<caption><font color="red">�����µ�¼��</font></caption>
  	  <tr><td>&nbsp;</td></tr>
  	  
  	</table>
  	
  	<table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>       
           <td><img src="img/button_l.gif" border="0" ></td>
           <td><input name="Submit" type="button" class="button" value="���µ�¼" onclick="window.location='login.screen'"></td>
           <td><img src="img/button_r.gif" border="0" ></td>
           <td width="20" ></td>
        </tr>
      </table>
