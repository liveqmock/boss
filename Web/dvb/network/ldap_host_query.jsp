<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
function back_submit()
{
	self.location.href="ldap_interface_index.screen";
} 

function view_detail_click(strId)
{
	self.location.href="ldap_host_detail.do?txtHostID="+strId;
}
function create_submit() {
    
   document.frmPost.action="ldap_host_create.screen";
   document.frmPost.submit();
}
 

</script>
<br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">LDAP�����б�</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post">
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
            <td class="list_head">����ID</td>
            <td class="list_head">��������</td>
            <td class="list_head">IP��ַ</td>
            <td class="list_head">�ӿ�Э������</td>
            <td class="list_head">״̬</td>
          </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
       
          <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="hostID"/>')" class="link12" ><tbl:writenumber name="oneline" property="hostID" digit="7"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="hostName"/></td>
      	      <td align="center"><tbl:write name="oneline" property="ipAddress"/></td>
      	       <td align="center"><tbl:write name="oneline" property="protocolType"/></td>
      	    
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>   
         </tr>    	    
</lgc:bloop>  

<tr>
    <td colspan="5" class="list_foot"></td>
  </tr>
</table>
<br>
  
 <table align="center" border="0" cellspacing="0" cellpadding="0">
 <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:create_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
 
         
   </table>   
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
 
   
 
	 
    </td>
  </tr>
</form>  
</table>  
 
