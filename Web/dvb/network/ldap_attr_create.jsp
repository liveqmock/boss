<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
 

 
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtAttrName, true, "��������"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
		return false;

   if (check_Blank(document.frmPost.txtFixedFlag, true, "�Ƿ�̶�ֵ"))
		return false;
  
		
    
  

	return true;
} 
 
function create_submit() {
    if(check_frm()){
     document.frmPost.action="ldap_attr_create.do";
     document.frmPost.Action.value="create";
     document.frmPost.submit();
}
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
    <td  class="title">����LDAPS�����ֶ�ȡֵ����</td>
  </tr>
  </table>
  <table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>

 
<form name="frmPost" method="post" >
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<input type="hidden" name="func_flag" value="232" > 
	<input type="hidden" name="Action" value="" > 
	<tr>
	        <td  class="list_bg2"><div align="right">��������*</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtAttrName"  size="22"  value="<tbl:writeparam name="txtAttrName" />" >
                 </td>        
		 <td  class="list_bg2"><div align="right">�Ƿ�̶�ֵ*</div></td>         
                 <td class="list_bg1" align="left">
                  <d:selcmn name="txtFixedFlag" mapName="SET_G_YESNOFLAG" match="txtFixedFlag" width="23"/>
                </td> 
                
       </tr>
	 
	<tr>
		 <td class="list_bg2"><div align="right">�̶�ֵ</div></td>
                 <td class="list_bg1" align="left">
               <input type="text" name="txtFixedValue"  size="22"  value="<tbl:writeparam name="txtFixedValue" />" >
                  <td class="list_bg2"><div align="right">ǰ׺</div></td>
                 <td class="list_bg1" align="left">
               <input type="text" name="txtPrefix"  size="22"  value="<tbl:writeparam name="txtPrefix" />" >
		 
	</tr>
       
	 
	<tr>
	  <td class="list_bg2"><div align="right">ȡֵ����</div></td>
          <td class="list_bg1" align="left">
          <input type="text" name="txtLength"  size="22"  value="<tbl:writeparam name="txtLength" />" >
          <td class="list_bg2"><div align="right">״̬*</div></td>
          <td class="list_bg1" align="left">
           <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23"/>
	</tr>
	 
	 
          
</table>
<BR>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
     <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>  
            <td background="img/button_bg.gif"><a href="<bk:backurl property="ldap_attr_query.do" />" class="btn12">��&nbsp;��</a></td>
            <td><img src="img/button2_l.gif" width="13" height="20"></td>
            <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">��&nbsp;��</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
           
           
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
 
