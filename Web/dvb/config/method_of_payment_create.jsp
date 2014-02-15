<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
 function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}
 

 
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.name, true, "名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

   if (check_Blank(document.frmPost.txtPayType, true, "支付类型"))
		return false;
   if (check_Blank(document.frmPost.txtAccountflag, true, "是否付费计划"))
		return false;
		
   if (check_Blank(document.frmPost.txtPaymentflag, true, "是否支付方式"))
		return false;	
 if (check_Blank(document.frmPost.txtCashFlag, true, "是否归入现金帐户"))
		return false;
		
  

	return true;
} 
 
function method_payment_create_submit() {
    if(check_frm()){
   document.frmPost.action="create_method_of_payment.do";
    document.frmPost.Action.value="create";
   document.frmPost.submit();
}
}
function back_submit()
{
	self.location.href="method_of_payment_query.do";
} 


</script>
<br>
<table width="100%" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">新增付费方式</td>
  </tr>
  </table>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" >
 
 
 
 
 <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3">
	<input type="hidden" name="func_flag" value="115" > 
	<input type="hidden" name="Action" value="" > 
	<tr>
	        <td  class="list_bg2"><div align="right">名称*</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="name"   size="25"  maxlength="50" value="<tbl:writeparam name="name" />" >
                 </td>        
		 <td  class="list_bg2"><div align="right">支付类型*</div></td>         
                 <td class="list_bg1" align="left">
                <d:selcmn name="txtPayType" mapName="SET_F_MOPPAYTYPE" match="txtPayType" width="23" />
                </td> 
                
       </tr>
	 
	<tr>
		 <td class="list_bg2"><div align="right">是否付费计划*</div></td>
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtAccountflag" mapName="SET_G_YESNOFLAG" match="txtAccountflag" width="23" />
                  <td class="list_bg2"><div align="right">是否支付方式*</div></td>
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtPaymentflag" mapName="SET_G_YESNOFLAG" match="txtPaymentflag" width="23" />
		 
	</tr>
       <% 
          Map orgName = new HashMap();
          orgName = Postern.getPaymentProxyOrg();
          pageContext.setAttribute("SubOrgName",orgName);
       %>
	<tr>
	  <td class="list_bg2"><div align="right">合作伙伴</div></td>
          <td class="list_bg1" align="left">
          <tbl:select name="txtPartID" set="SubOrgName" match="txtPartID" width="23"/>
          <td class="list_bg2"><div align="right">是否归入现金帐户*</div></td>
          <td class="list_bg1" align="left">
          <d:selcmn name="txtCashFlag" mapName="SET_G_YESNOFLAG" match="txtCashFlag" width="23" />
	</tr>
	<tr>   
		 <td  class="list_bg2" align="right">使用场合</td>
	         <td class="list_bg1" colspan="3">
	   	<input name="txtCsiTypeListValue" type="text" class="text" maxlength="200" size="85" value="<tbl:writeparam name="txtCsiTypeListValue" />" >
	   	<input name="txtCsiTypeList" type="hidden" value="<tbl:writeparam name="txtCsiTypeList" />" >
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_F_METHODOFPAYMENTCSITYPELIST','txtCsiTypeList',document.frmPost.txtCsiTypeList.value,'','','')"> 
	   </td>	   
          </tr>
	<tr>
	      <td class="list_bg2"><div align="right">状态*</div></td>
               <td  colspan="3" class="list_bg1">
               <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
               </td>
        </tr>
        <tr>
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3"><font size="2"><input name="txtDescription"
			type="text" class="text" maxlength="200" size="85"
			value="<tbl:writeparam name="txtDescription" />"></font></td>
	</tr>
          
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
 
 <table align="center" border="0" cellspacing="0" cellpadding="0">
 <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="确&nbsp;认" onclick="javascript:method_payment_create_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
 
         
   </table>   
 
<br>

 
   
 
	 
    </td>
  </tr>
</form>  
</table>  
 
