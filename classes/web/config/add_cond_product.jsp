<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
 

<Script language=javascript>
<!--
  
 function check_form(){
    if (check_Blank(document.frmPost.txtProductList, true, "已经定购产品"))
        return false;
        
  
           
   
    if (document.frmPost.txtProductNum.value != '') {
          if (!check_Num(document.frmPost.txtProductNum, true, "产品数量"))
            return false;
       
       }
   
    return true;

}
   

function add_submit(){
	if(check_form()) 
	document.frmPost.submit();
}
 
function back_submit(){
	url="product_cond_campaign.screen?txtCampaignID=<tbl:writeparam name="txtCampaignID"/>&txtCampaignType=<tbl:writeparam name="txtCampaignType"/>";
	document.location.href=url;
}

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

 

//-->
</SCRIPT>
 

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">产品条件管理-添加</td>
  </tr>
</table>
 
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

<form name="frmPost" method="post" action="config_cond_product.do">
    
    <input type="hidden" name="txtCampaignID" size="20" value="<tbl:writeparam name="txtCampaignID"/>">
    <input type="hidden" name="txtActionType" size="20" value="create_cond">
   
    <input type="hidden" name="func_flag" value="1015">
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    
        
	<tr> 
	     <td class="list_bg2" align="right">定购产品*</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtProductListValue" type="text" readonly maxlength="200" size="82" value="<tbl:writeparam name="txtProductListValue"/>">
	   	<input name="txtProductList" type="hidden" value="<tbl:writeparam name="txtProductList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('PRODUCT','txtProductList',document.frmPost.txtProductList.value,'','','')"> 
            </td>  
         </tr>
        <tr>
            <td class="list_bg2" ><div align="right">全选标志</div></td>
            <td class="list_bg1"><d:selcmn name="txtHasAllProductFlag" mapName="SET_G_YESNOFLAG" match="txtHasAllProductFlag" width="23" /></td>
            <td class="list_bg2"><div align="right">产品数量</div></td>
            <td class="list_bg1"><input type="text" name="txtProductNum" size="25"  value="<tbl:writeparam name="txtProductNum" />" >
          </td>
            
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">新捕获标志</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCaptureProductFlag" mapName="SET_G_YESNOFLAG" match="txtNewCaptureProductFlag" width="23" /></td>
            <td class="list_bg2"><div align="right">存在标志</div></td>
            <td class="list_bg1"><d:selcmn name="txtExistingProductFlag" mapName="SET_G_YESNOFLAG" match="txtExistingProductFlag" width="23" /></td>
        </tr>
    
         
        
    
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>	
		        
		        
		         <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
                        <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            
		  	<td width="20" ></td>		
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保  存" onclick="javascript:add_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			 
			 
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>

 
  
</form>
