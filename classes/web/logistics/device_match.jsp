<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%
  //�Ƿ�����������Ȩ��
	String matchAndPreAuth = Postern.getLogisticsSettingPreAuthImm();
  //ȡ��Ĭ�ϵĲ�Ʒ�б�
	String defaultProductID = Postern.getCheckedProductIDList();
	String defaultProductName = Postern.getDefaultAuthProductNameList();
        String txtProductIDListValue = request.getParameter("txtProductIDListValue");
	String txtProductIDList = request.getParameter("txtProductIDList");
	if(txtProductIDListValue == null || "".equals(txtProductIDListValue))
	{
	    txtProductIDListValue = defaultProductName;
	    txtProductIDList = defaultProductID;
	}
%>

<script language=javascript>
<!--
function   text2_onkeypress()   
{
  if(event.keyCode==13)
  {
	if (check_Blank(document.frmPost.txtSCDeviceInput, true, "���ܿ���"))
		return false;
	 document.frmPost.txtSTBDeviceInput.focus();
  }  
}   


function   text1_onkeypress()   
{
  if(event.keyCode==13)
  {
	//���հ�
	if (check_Blank(document.frmPost.txtSCDeviceInput, true, "���ܿ���"))
		return false;
	if (check_Blank(document.frmPost.txtSTBDeviceInput, true, "�����к�"))
		return false;
        document.frmPost.txtSerialNoCollection.value = document.frmPost.txtSerialNoCollection.value + document.frmPost.txtSCDeviceInput.value + "/" + document.frmPost.txtSTBDeviceInput.value + "\r\n";

	//��տؼ�
	document.frmPost.txtSTBDeviceInput.value="";
	document.frmPost.txtSCDeviceInput.value="";
	//���ý���
	document.frmPost.txtSCDeviceInput.focus();
  }  
}   
function next_step()
{
    if(check_frm()){
        document.frmPost.action ="device_match_check.do";
    document.frmPost.submit();    
  }
}

function check_frm()
{
        if("Y"=="<%=matchAndPreAuth %>")
            if(check_Blank(document.frmPost.txtProductIDListValue,true,"Ԥ��Ȩ��Ʒ"))
		return false;
	if (check_Blank(document.frmPost.txtBatchID, true, "��������"))
		return false;
	if (check_Blank(document.frmPost.txtSerialNoCollection, true, "���豸/����豸���к��б�"))
		return false;

	return true;
}
//����˵����typeΪ���ͣ�typeNameΪ���֣�����value���֣���subTypeValueΪ�ò�����ֵ
function open_select_product(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="product_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	param=param + "&txtProductIDList="+document.frmPost.txtProductIDList.value;
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;

	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=600,height=320,screenX=600,screenY=400";
	window.open(param,"",windowFeatures);
}
//-->
</script>


<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><font size="3">�������</font></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 <form name="frmPost" method="post" >    
  
  
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tr> 
    <td class="list_bg2" align="right" >��������*</td>
    <td class="list_bg1" width="75%">
        <input type="text" name="txtBatchID" size="20"  value="<tbl:writeparam name="txtBatchID" />" >
        </td>
    </tr>    
    <tr>
        <td class="list_bg2" align="right" ></td>
	<td class="list_bg1">���ܿ�������ɨ����밴�س���</td>
    </tr>
</table>
<table width="98%" align="center" border="0" cellpadding="5" cellspacing="1" class="list_bg">
<tr>
    <td class="list_bg2"  width="25%"  align="right" >���ܿ��豸ɨ�������</td>
    <td class="list_bg1" width="25%">
        <input type="text" name="txtSCDeviceInput" size="20"  value="<tbl:writeparam name="txtSCDeviceInput" />" onkeypress="return   text2_onkeypress()" >
    </td>
    <td class="list_bg2" width="25%" align="right" >�������豸ɨ�������</td>
    <td class="list_bg1" width="25%">
        <input type="text" name="txtSTBDeviceInput" size="20"  value="<tbl:writeparam name="txtSTBDeviceInput" />" onkeypress="return   text1_onkeypress()" >
    </td>
</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr>
        <td class="list_bg2" align="right" ></td>
	<td class="list_bg1">���豸/����豸��"�س�"����</td>
    </tr>

    <tr>
    <td class="list_bg2" align="right" >���豸/����豸���к��б�*</td>    
    <td class="list_bg1" >
	<textarea name="txtSerialNoCollection"  length="5" cols=75 rows=9><tbl:writeSpeCharParam name="txtSerialNoCollection" /></textarea>
    </td>		
    </tr>

    <tr>
    <td class="list_bg2" align="right" >��ע</td>    
    <td class="list_bg1" >
	<textarea name="txtDesc"  length="5" cols=75 rows=2><tbl:writeSpeCharParam name="txtDesc" /></textarea>
    </td>    
    </tr>
    <%if("Y".equals(matchAndPreAuth)){%>
	<tr>     
        <td class="list_bg2" align="right" >Ԥ��Ȩ��Ʒ</td>
        <td class="list_bg1" width="75%">
        <input type="text" name="txtProductIDListValue" size=50 value="<%=txtProductIDListValue %>" >
	<input name="txtProductIDList" type="hidden" value="<%=txtProductIDList %>">
        <input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select_product('PRODUCT','txtProductIDList',document.frmPost.txtProductIDList.value,'','','')"> 
	</tr>
    <%}%>
    </table>


    <table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr align="center">
	<td class="list_bg1">   
        <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:next_step()" class="btn12">��һ��</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
        </table>
	</td>
    </tr>
    </table>
</form>  

     
 

 

  
 
 

     
      
      
      
      
      