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
    if (check_Blank(document.frmPost.txtProdcutName, true, "LDAP��Ʒ����"))
	    return false;
    if (check_Blank(document.frmPost.txtDescription, true, "LDAP��Ʒ����"))
	    return false;
    
 	        
  if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
	    return false;
 
	    		    	    
	    
	return true;
}

function ldap_product_edit_submit(){
 if (window.confirm('��ȷ��Ҫ�޸�LDAP��Ʒ��Ϣ��?')){
  if (check_frm()){
	    document.frmPost.action="ldap_product_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
}
}
function back_submit(){
	url="<bk:backurl property='ldap_product_query.do' />";
    document.location.href= url;

} 
  
 

</SCRIPT>

<form name="frmPost" method="post">
	 
	
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="226" >
	 
	<!-- ���嵱ǰ���� -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">LDAP��Ʒ��Ϣά��</td>
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
	          <td  class="list_bg2"><div align="right">LDAP��Ʒ����</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtProdcutName" size="25"  value="<tbl:write name="oneline" property="productName" />" class="textgray" readonly >
                  </td>       
		 <td  class="list_bg2"><div align="right">״̬*</div></td>         
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="25"/>
                 </td>        
	</tr> 
	 
	 
        
	  <tr>
		 <td  class="list_bg2"><div align="right">LDAP��Ʒ����</div></td>         
                 <td class="list_bg1" align="left" colspan="3">
                  <input type="text" name="txtDescription" size="78" maxlength="200"  value="<tbl:write name="oneline" property="Description" />">
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
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:ldap_product_edit_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	

	 

</form>
