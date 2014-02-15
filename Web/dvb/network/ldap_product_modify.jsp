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
    if (check_Blank(document.frmPost.txtProdcutName, true, "LDAP产品名称"))
	    return false;
    if (check_Blank(document.frmPost.txtDescription, true, "LDAP产品描述"))
	    return false;
    
 	        
  if (check_Blank(document.frmPost.txtStatus, true, "状态"))
	    return false;
 
	    		    	    
	    
	return true;
}

function ldap_product_edit_submit(){
 if (window.confirm('您确认要修改LDAP产品信息吗?')){
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
	 
	<!-- 定义当前操作 -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">LDAP产品信息维护</td>
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
	          <td  class="list_bg2"><div align="right">LDAP产品名称</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtProdcutName" size="25"  value="<tbl:write name="oneline" property="productName" />" class="textgray" readonly >
                  </td>       
		 <td  class="list_bg2"><div align="right">状态*</div></td>         
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="25"/>
                 </td>        
	</tr> 
	 
	 
        
	  <tr>
		 <td  class="list_bg2"><div align="right">LDAP产品描述</div></td>         
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
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保&nbsp;存" onclick="javascript:ldap_product_edit_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	

	 

</form>
