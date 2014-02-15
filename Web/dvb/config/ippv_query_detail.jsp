<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.dto.CAWalletDefineDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<SCRIPT LANGUAGE="JavaScript">
<!--
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
 

 
 
function check_form(){
    if (check_Blank(document.frmPost.caWalletCode, true, "Ǯ������"))
        return false;
        
    if (document.frmPost.caWalletCode.value.length>20){
    	alert('Ǯ�����볤�Ȳ��ܳ���20');
    	return false;
    	}

          
    if (check_Blank(document.frmPost.caWalletName, true, "Ǯ������"))
        return false;
        
   if (document.frmPost.caWalletName.value.length>50){
    	alert('Ǯ�����Ƴ��Ȳ��ܳ���50');
    	return false;
    }
    if (check_Blank(document.frmPost.deviceModelList, true, "�����豸"))
        return false;
     if (check_Blank(document.frmPost.required, true, "�Ƿ����"))
        return false;
     if (check_Blank(document.frmPost.rate, true, "�һ���"))
        return false;
     if (document.frmPost.rate.value==0){
	alert('�һ��ʲ���Ϊ0');	
	 return false;
     }
     if (isNaN(document.frmPost.rate.value)){
	alert('�һ���ӦΪ����');	
	 return false;
     }
     var s=document.frmPost.rate.value;
     var ss=s.split(".");
     if(ss.length<2&&s.length>10){
     	alert('�һ��ʳ��ȴ����������10λ');
     	return false;
     }
     if(ss.length>1){
	if(ss[0].length>10){
	
		alert('�һ��ʳ��ȴ����������10λ');
		return  false;
	}
	if(ss[1].length>2){
		alert('�һ��ʳ��ȴ���С�����2λ');
		return  false;
	}
     }
    return true;

}
 
 
 
function frm_modify()
{
         
           if(check_form()){
           
         
            document.frmPost.action="ippv_modify.do";
        
            document.frmPost.submit();
            }
       
}
  
//-->
</SCRIPT>
 

<form name="frmPost" method="post">
 

 <input type="hidden" name="Action" value="update">
 
<input type="hidden" name="func_flag" value="4001" >
   
 <table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">IPPVǮ��ά��</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
    
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<% 
CAWalletDefineDTO dto = (CAWalletDefineDTO) pageContext.findAttribute("oneline");
String deviceModel=dto.getDeviceModelList();

%>

 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
                 
        <tr> 
          <td class="list_bg2" ><div align="right">Ǯ�����</div></td>
          <td class="list_bg1" ><font size="2">                               
              <input type="text" name="caWalletCode" size="25"  value="<tbl:write name="oneline" property="caWalletCode"/>" class="textgray" readonly >
              </font></td>
          <td  class="list_bg2"><div align="right">Ǯ������*</div></td>
          <td class="list_bg1"><font size="2">
              <input type="text" name="caWalletName" size="25"  maxlength="50" value="<tbl:write name="oneline" property="caWalletName"/>">
              </font></td>   
        </tr>
          <tr>
         <td class="list_bg2"><div align="right">�����豸*</div></td>
          <td class="list_bg1">
	   	<input name="deviceModelListValue" type="text"  maxlength="200" size="25" readonly value="<%=deviceModel%>">
	   	<input name="deviceModelList" type="hidden" value="<tbl:write name="oneline" property="deviceModelList" />" >
	   	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('devicemodel','deviceModelList',document.frmPost.deviceModelList.value,'','','')"> 
	  </td>
	   
	   
          <td class="list_bg2"><div align="right">�Ƿ����*</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
           <d:selcmn name="required" mapName="SET_G_YESNOFLAG" match="oneline:required" width="23"/>
          </font></td>
        </tr>
        <tr>
	 <td  class="list_bg2" align="right">�һ���*</td>
	   <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="rate" size="25"  maxlength="50" value="<tbl:write name="oneline" property="rate"/>">
              </font></td>	   
          
        </tr>
        
        <input type="hidden" name="dtLastmod" value="<tbl:write name="oneline" property="dtLastmod" />">

</table>

     
 </lgc:bloop>

<br>   
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
                <td background="img/button_bg.gif"><a href="<bk:backurl property="ippv_query.do" />" class="btn12">����</a></td>
                <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="10" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_modify()" class="btn12">����</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
           
           
          </tr>
    </table>        
 
</form>