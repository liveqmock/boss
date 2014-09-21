<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
function check_form(){
       	if(document.frmPost.txtCustomerID.value==''){
       		alert("�ͻ��Ų���Ϊ�գ�");
       		return false;
       	}
       	if(document.frmPost.txtContactPerson.value==''){
       		alert("��������ϵ�ˣ�");
       		return false;
       	}
       	if(document.frmPost.txtType.value==''){
       		alert("Ͷ�����Ͳ���Ϊ�գ�");
       		return false;
       	}
       	if(document.frmPost.txtContent.value==''){
       		alert("Ͷ�����ݲ���Ϊ�գ�");
       		return false;
       	}
       	if(document.frmPost.txtContent.value.length>500){
       		alert("Ͷ������̫����");
       		return false;
       	}
       	if(document.frmPost.txtRequest.value.length>200){
       		alert("Ͷ������̫����");
       		return false;
       	}
       	if(document.frmPost.txtPreProcess.checked){
       		document.frmPost.action="customer_complain_create.do?pre=y";
       		document.frmPost.txtAction.value="PS";
       	}else{
       		document.frmPost.action="customer_complain_create.do?pre=n";       		
       	}
	return true;
}

function frm_submit()
{
	if(check_form()&&check_transfertype()){
	       document.frmPost.submit();
	}
}
function isNumber(oNum) 
   { 
  if(!oNum) return false; 
  var strP=/^\d+(\.\d+)?$/; 
  if(!strP.test(oNum)) return false; 
  try{ 
  if(parseFloat(oNum)!=oNum) return false; 
  } 
  catch(ex) 
  { 
   return false; 
  } 
  return true; 
   } 
function query_customer(){
	if(document.frmPost.txtCustomerID.value==""){
		alert("�ͻ��Ų���Ϊ�գ�");
		return;
	}
	var strcustomerid = document.frmPost.txtCustomerID.value;
	if(!isNumber(strcustomerid)){
		alert("�ͻ���ӦΪ���֣�");
		return;
	}
	var strFeatures = "width=600px,height=260px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("query_complain_customer.do?newCustomerID="+strcustomerid,"�ͻ���ѯ",strFeatures);
}
function check_transfertype(){
	var str=document.frmPost.selType!=null?document.frmPost.selType.value:""; 
		if(  'auto'==str && (''==document.frmPost.txtAutoNextOrgID.value||0==document.frmPost.txtAutoNextOrgID.value)){
			alert("�޷�ƥ����ʵĴ����ţ����ֹ���ת��ָ����ת���ţ�");
			return false;
		}
		if('manual'==str){
			if(''==document.frmPost.txtNextOrgID.value){
			       	alert("�ֹ���ת��ָ����ת���ţ�");
			       	return false;
		       	}
		}
	document.frmPost.transferType.value=str;
	return true;
}
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("transfer_org_list.do?strRole=C","��ת����",strFeatures);
}
</script>
<form name="frmPost" method="post" action="customer_complain_create.do" >
<input type="hidden" name="transferType" value="<tbl:writeparam name="transferType"/>">
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ�Ͷ����Ϣ¼��</td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

   <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">�ͻ���Ϣ</td>
     </tr>
     </table>
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="17%" align="right" class="list_bg2">�ͻ�ID</td>
          <td width="33%" class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtCustomerID" maxlength="10" size="25"  value="<tbl:writeparam name="txtCustomerID" />" class="text">
          </font> </td>                      
          <td width="17%" class="list_bg2" align="right">�ͻ�����</td>          
          <td width="33%" class="list_bg1" align="left"><font size="2"> 
            <input type="text" readonly name="txtCustomerName" maxlength="10" size="25"  value="<tbl:writeparam name="txtCustomerName" />" class="text">
            <input name="selOrgButton" type="button" class="button" value="��ѯ" onClick="javascript:query_customer()"> 
          </font> </td>
        </tr> 
        <tr>  
           <td  class="list_bg2" align="right">��ϵ��*</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtContactPerson" maxlength="10" size="25"  value="<tbl:writeparam name="txtContactPerson" />" class="text">  
           </font></td>  
           <td  class="list_bg2" align="right">��ϵ�绰</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtContactPhone" maxlength="10" size="25"  value="<tbl:writeparam name="txtContactPhone" />" class="text">               
           </font></td>
        </tr>
     </table>
      <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">Ͷ����Ϣ</td>
	</tr>
    </table>
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="17%" align="right" class="list_bg2">Ͷ������*</td>
          <td  class="list_bg1" colspan="3"> <d:selcmn name="txtType" mapName="SET_C_CUSTOMERCOMPLAINTYPE" match="txtType" width="23" />
          </td>                      
        </tr> 
        <tr>  
           <td  width="17%" class="list_bg2" align="right">��Ͷ����֯</td>
           <td  width="33%" class="list_bg1" align="left"><font size="2">
           <input type="hidden" name="txtComplainedOrgID" value="<tbl:writeparam name="txtComplainedOrgID" />">
		   <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtComplainedOrgID" />" class="text">
			 <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,D,B,O,S','txtComplainedOrgID','txtOrgDesc')">
           </font></td>  
           <td  width="17%" class="list_bg2" align="right">��Ͷ�߸���</td>
           <td  width="33%" class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtComplainedPerson" maxlength="10" size="25"  value="<tbl:writeparam name="txtComplainedPerson" />" class="text">               
           </font></td>
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">Ͷ������*&nbsp&nbsp<br>(������250��)</td>
           <td  class="list_bg1" align="left" colspan="3"><font size="2">
             <textarea name="txtContent"  cols=82 rows=6 ><tbl:writeparam name="txtContent" /></textarea>  
           </font></td>  
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">Ͷ������&nbsp&nbsp<br>(������100��)</td>
           <td  class="list_bg1" align="left" colspan="3"><font size="2">
             <textarea name="txtRequest"  cols=82 rows=4 ><tbl:writeparam name="txtRequest" /></textarea>  
           </font></td>  
        </tr>
     </table> 
      <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center"><input type="checkbox" name="txtPreProcess"  value="preprocess"  />Ͷ��Ԥ��������ɹ������ᵥ��</td>
	</tr>
      </table> 
        <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>  
           <td  width="17%" class="list_bg2" align="right">������</td>
             <td  class="list_bg1" align="left" colspan="3"><d:selcmn name="txtAction" mapName="SET_C_CUSTCOMPLAINPROCESSACTION" match="txtAction" width="23" /></td>   
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">˵����Ϣ</td>
           <td  class="list_bg1" align="left" colspan="3"><font size="2">
             <textarea name="txtMemo" length="5" cols=82 rows=4 ><tbl:write name="oneline" property="txtMemo" /></textarea>  
           </font></td>  
        </tr>
       </table>
       <%String systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_PRECISEWORK");
	if(systemSettingPrecise!=null&&("Y").equals(systemSettingPrecise)){
	String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
	if(systemSettingValue == null || ("N").equals(systemSettingValue)){%>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center">Ͷ���ֹ���ת<input type="hidden" name="selType" value="manual"></td>
		        </tr>		  
			<tr>
			    <td class="list_bg2" width="17%"><div align="right">��ת����</td>
			    <td class="list_bg1" > 
				 <input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
			    	 <input type="text" name="txtNextOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
			    	 <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,D,S,O','txtNextOrgID','txtNextOrgDesc')">
			    </td>		
			</tr>		
	</table> 
      <%}else if(systemSettingValue != null && ("Y").equals(systemSettingValue)){ %>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center">Ͷ���Զ���ת<input type="hidden" name="selType" value="auto"></td>
		        </tr>  
			<tr>
			    <td class="list_bg2" width="17%"><div align="right">��ת����</td>
			    <td class="list_bg1" > 
			 	<input type="hidden" name="txtAutoNextOrgID" value="">
		    		<input type="text" name="txtNextOrgName" size="25" maxlength="50" readonly value="" class="text">
		    		<input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:mod_organization()">
			    </td>		
			</tr>		
	</table>
     <%}}%>   
       <tbl:generatetoken />
	<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		  <tr>
		     <td><img src="img/mao.gif" width="1" height="1"></td>
		   </tr>
	</table> 	
       <br>  
	<table align="center" border="0" cellspacing="0" cellpadding="0" >
		<tr >
		   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="ȷ ��"></td>
		   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
		</tr>
	</table>     
</form>
 
    
