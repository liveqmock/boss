<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
 

<Script language=javascript>
<!--
  
 

function add_submit(){
	 
	document.frmPost.submit();
}
 
function back_submit(){
	url="campaign_detail.do?txtCampaignID=<tbl:writeparam name="txtCampaignID"/>";
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
    <td class="title">������������-���</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

 
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

<form name="frmPost" method="post" action="config_cond_campaign.do">
    
    <input type="hidden" name="txtCampaignID" size="20" value="<tbl:writeparam name="txtCampaignID"/>">
    <input type="hidden" name="txtActionType" size="20" value="create_cond">
    <input type="hidden" name="func_flag" value="1015">
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    
        <tr>
	    <td class="import_tit" align="center" colspan="4"><font size="2">������Ʒѡ��</font></td>
	</tr>
	<tr> 
	     <td class="list_bg2" align="right">�Ѿ�������Ʒ</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtProductListValue" type="text" class="text" maxlength="200" size="80" value="<tbl:writeparam name="txtProductListValue"/>">
	   	<input name="txtProductList" type="hidden" value="<tbl:writeparam name="txtProductList"/>">
	   	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('PRODUCT','txtProductList',document.frmPost.txtProductList.value,'','','')"> 
            </td>  
         </tr>
        <tr>
            <td class="list_bg2" width="25%"><div align="right">ȫѡ��־</div></td>
            <td class="list_bg1"><d:selcmn name="txtHasAllProductFlag" mapName="SET_G_YESNOFLAG" match="txtHasAllProductFlag" width="23" /></td>
            <td class="list_bg2" width="25%"><div align="right">��Ʒ����</div></td>
            <td class="list_bg1" width="25%"><input type="text" name="txtProductNum" size="10"  value="<tbl:writeparam name="txtProductNum" />" >
          </td>
            
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">�²����־</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCaptureProductFlag" mapName="SET_G_YESNOFLAG" match="txtNewCaptureProductFlag" width="23" /></td>
            <td class="list_bg2"><div align="right">���ڱ�־</div></td>
            <td class="list_bg1"><d:selcmn name="txtExistingProductFlag" mapName="SET_G_YESNOFLAG" match="txtExistingProductFlag" width="23" /></td>
        </tr>
    
        <tr>
            <td class="import_tit" align="center" colspan="4"><font size="2">������Ʒ��ѡ��</font></td>
        </tr>
          <tr> 
	     <td class="list_bg2" align="right">������Ʒ��</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtProductPackageListValue" type="text" class="text" maxlength="200" size="80" value="<tbl:writeparam name="txtProductPackageListValue"/>">
	   	<input name="txtProductPackageList" type="hidden" value="<tbl:writeparam name="txtProductPackageList"/>">
	   	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('PRODUCTPACKAGE','txtProductPackageList',document.frmPost.txtProductPackageList.value,'','','')"> 
          </td>  
          </tr>
        <tr>
            <td class="list_bg2" width="25%"><div align="right">ȫѡ��־</div></td>
            <td class="list_bg1"><d:selcmn name="txtHasAllPackageFlag" mapName="SET_G_YESNOFLAG" match="txtHasAllPackageFlag" width="23" /></td>
            <td class="list_bg2" width="25%"><div align="right">��Ʒ������</div></td>
            <td class="list_bg1" width="25%"><input type="text" name="txtPackageNum" size="10"  value="<tbl:writeparam name="txtPackageNum" />" >
          </td>
            
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">�¹����־</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewPurchaseFlag" mapName="SET_G_YESNOFLAG" match="txtNewPurchaseFlag" width="23" /></td>
            <td class="list_bg2"><div align="right">���ڱ�־</div></td>
            <td class="list_bg1"><d:selcmn name="txtExistingPackageFlag" mapName="SET_G_YESNOFLAG" match="txtExistingPackageFlag" width="23" /></td>
        </tr>
         <tr>
            <td class="import_tit" align="center" colspan="4"><font size="2">�г��ѡ��</font></td>
        </tr>
          <tr> 
	     <td class="list_bg2" align="right">�����г��</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtCampaignIDListValue" type="text" class="text" maxlength="200" size="80" value="<tbl:writeparam name="txtCampaignIDListValue"/>">
	   	<input name="txtCampaignIDList" type="hidden" value="<tbl:writeparam name="txtCampaignIDList"/>">
	   	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('CAMPAIGN','txtCampaignIDList',document.frmPost.txtCampaignIDList.value,'','','')"> 
          </td>  
          </tr>
        <tr>
            <td class="list_bg2" width="25%"><div align="right">ȫѡ��־</div></td>
            <td class="list_bg1"><d:selcmn name="txtHasAllCampaignFlag" mapName="SET_G_YESNOFLAG" match="txtHasAllCampaignFlag" width="23" /></td>
            <td class="list_bg2" width="25%"><div align="right">�����</div></td>
            <td class="list_bg1" width="25%"><input type="text" name="txtCampaignNum" size="10"  value="<tbl:writeparam name="txtCampaignNum" />" >
          </td>
            
        </tr>
         <tr>
            <td class="list_bg2"><div align="right">�²����־</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCaptureFlag" mapName="SET_G_YESNOFLAG" match="txtNewCaptureFlag" width="23" /></td>
            <td class="list_bg2"><div align="right">���ڱ�־</div></td>
            <td class="list_bg1"><d:selcmn name="txtExistingCampaignFlag" mapName="SET_G_YESNOFLAG" match="txtExistingCampaignFlag" width="23" /></td>
        </tr>
         <tr>
            <td class="import_tit" align="center" colspan="4"><font size="2">�г�����ѡ��</font></td>
        </tr>
         <tr> 
            <td  class="list_bg2" align="right">�����г�����</td>
	    <td class="list_bg1" colspan="3">
	        <input name="txtMarketSegmentListValue" type="text" class="text" maxlength="200" size="80" value="<tbl:writeparam name="txtMarketSegmentListValue"/>">
	   	<input name="txtMarketSegmentList" type="hidden" value="<tbl:writeparam name="txtMarketSegmentList"/>">
	   	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('MARKETSEGMENT','txtMarketSegmentList',document.frmPost.txtMarketSegmentList.value,'','','')"> 
            </td>
         </tr>
    
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>	
		        
		        
		         <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
                        <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            
		  	<td width="20" ></td>		
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��  ��" onclick="javascript:add_submit()"></td>
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
