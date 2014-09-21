<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.DeviceBatchPreauthDTO" %>

   

<Script language=JavaScript>
	
function open_select_product(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="product_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;

	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;

	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=480,height=300,screenX=600,screenY=400";
	window.open(param,"",windowFeatures);
}

function check_form(){
	       
  <!-- У������-->    
  if (document.frmPost.txtCreateTimeBegin.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeBegin, true, "��ʼʱ��")){
			return false;
		}

	}
	
  if (document.frmPost.txtCreateTimeEnd.value != ''){
			if (!check_TenDate(document.frmPost.txtCreateTimeEnd, true, "����ʱ��")){
			return false;
		}	

	}	
	
	if(!compareDate(document.frmPost.txtCreateTimeBegin,document.frmPost.txtCreateTimeEnd,"����ʱ��������ڱ�����ڵ��ڴ���ʱ�俪ʼ����"))
				return false;
	
	
        
   <!-- ���ָ�ʽ-->
   
   if (isNaN(document.frmPost.txtBatchId.value)){
			alert('��¼IDӦΪ����');	
	 		return false;
	 }
   if (isNaN(document.frmPost.txtDeviceID.value)){
			alert('�豸IDӦΪ����');	
	 		return false;
	 }  
	
	
	
	
	
	
       	
	return true;
}

function query_submit()
{
        if (check_form()) document.frmPost.submit();
}
function view_detail_click(strId)
{
	
	self.location.href="device_batchPreAuth_detail.do?txtBatchId="+strId;	
	
}
</Script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">���ܿ�����Ԥ��Ȩ��¼��ѯ</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
   <form name="frmPost" method="post" action="device_batchPreAuth_query_result.do" >
     <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
     	
<%
   
    
    Map authOperationType = Postern.getAllPreAuth();
    pageContext.setAttribute("AuthOperationType",authOperationType);



%>      	   	
     	
     	
      <tr >
        <td  class="list_bg2" width="17%"><div align="right">��¼ID</div></td>
        <td class="list_bg1" width="33%"><font size="2"> 
        <input type="text" size="25" maxlength="10" name="txtBatchId" value="<tbl:writeparam name="txtBatchId"/>">
        </font>
         </td>
          <td  class="list_bg2" width="17%"><div align="right">�豸ID</div></td>
        <td class="list_bg1" width="33%"><font size="2"> 
        <input type="text" size="25" maxlength="50" name="txtDeviceID" value="<tbl:writeparam name="txtDeviceID"/>">
        </font>
         </td>
      </tr> 
       <tr> 
                 <td  class="list_bg2" width="17%"><div align="right">���к�</div></td>
                     <td class="list_bg1" width="33%"><font size="2"> 
                        <input type="text" size="25" maxlength="10" name="txtSerialNo" value="<tbl:writeparam name="txtSerialNo"/>">
                     </font>
                 </td> 
                 <td class="list_bg2" align="right" width="17%">��������</td>

      
                        <td class="list_bg1"><font size="2" width="33%"> 
                            <tbl:select name="txtOperationType" set="AuthOperationType" match="txtOperationType" width="23"/> 
                           </font>
                </td> 
     <tr> 
        <td  class="list_bg2"><div align="right">Ԥ/����Ȩ��Ʒ</div></td>	 
        <td class="list_bg1" colspan="3">
       				  <input type="text" name="txtProductIDListValue" size="25" value="<tbl:writeparam name="txtProductIDListValue"/>">
	      				<input name="txtProductIDList" type="hidden" value="<tbl:writeparam name="txtProductIDList"/>">
       				  <input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select_product('PRODUCT','txtProductIDList',document.frmPost.txtProductIDList.value,'','','')"> 
 				</td>            
         
    </tr> 

       
        <tr>        
           <td  class="list_bg2"><div align="right">����ʱ��</div></td>
          <td class="list_bg1" colspan="3"><font size="2"> 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeBegin)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtCreateTimeBegin" value="<tbl:writeparam name="txtCreateTimeBegin"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeBegin,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             -- 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeEnd)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtCreateTimeEnd" value="<tbl:writeparam name="txtCreateTimeEnd" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeEnd,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          	</font> </td> 
            
       </tr>        
     
     </table>
     <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
  </tr>
  </table>   
            <input type="hidden" name="txtFrom" size="20" value="1">
            <input type="hidden" name="txtTo" size="20" value="10">
            
            <input type="hidden" name="func_flag" value="9991" >
            
            
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
  
   <rs:hasresultset>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head" ><div align="center">��¼ID</div></td>
          <td class="list_head"><div align="center">��¼����</div></td>
          <td  class="list_head" ><div align="center">��������</div></td>
          <td  class="list_head" ><div align="center">Ԥ/����Ȩ��Ʒ</div></td>
          <td  class="list_head" ><div align="center">�豸��Ŀ</div></td>
          <td  class="list_head" ><div align="center">����ʱ��</div></td>
        </tr>

  
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	
<%
DeviceBatchPreauthDTO dto=(DeviceBatchPreauthDTO)pageContext.getAttribute("oneline");
String productNameList=Postern.getProductNameListByBatchId(dto.getBatchId());
%>	
	
   
  <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="batchId"/>')" class="link12" ><tbl:write name="oneline" property="batchId"/></a></td>
      <td align="center"><tbl:write name="oneline" property="referSheetSerialNO"/></td>
      <td align="center"><d:getcmnname typeName="SET_D_DEVICEPREAUTH_OPERATIONTYPE" match="oneline:operationType" /></td>
      <td align="center"><%= productNameList %></td>
      <td align="center"><tbl:write name="oneline" property="totalDeviceNum"/></td>
      <td align="center"><tbl:writedate name="oneline" property="createTime" includeTime="true" /></td>      
     </tr>
    
</lgc:bloop>    
<tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
</table>

 <table  border="0" align="right" cellpadding="0" cellspacing="8">
  <tr>
    <td>��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ<span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" /><span>ҳ</td>
    <td>��<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">��ҳ</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">��һҳ</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">��һҳ</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">ĩҳ</a></td>
</rs:notlast>
    <td align="right">��ת��</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>ҳ</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  </rs:hasresultset> 
	
</form> 
    </td>
  </tr>
  
  
</table>   
 