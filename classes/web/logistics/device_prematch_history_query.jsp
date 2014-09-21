<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri"%>
<%@ page import="com.dtv.oss.dto.DevicePrematchRecordDTO"%>
<%@ page import="com.dtv.oss.dto.DeviceBatchPreauthDTO"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.util.Postern"%>

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
        
    return true;

}
   
//-->
</SCRIPT>


<form name="frmPost" method="post">
	
 <table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">智能卡批量配对记录--明细信息</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>



<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	
        
        
         <tr class="list_head">

          <td class="list_head" ><div align="center">流水号</div></td>
          <td class="list_head" ><div align="center">机顶盒型号</div></td>
          <td class="list_head"><div align="center">机顶盒序列号</div></td>
          <td class="list_head" ><div align="center">智能卡型号</div></td>
          <td class="list_head"><div align="center">智能卡序列号</div></td>
          <td  class="list_head" ><div align="center">操作类型</div></td>
          <td class="list_head" ><div align="center">批次号</div></td>
         	<td class="list_head" ><div align="center">操作单号</div></td>  
          <td  class="list_head" ><div align="center">创建时间</div></td>
          <td  class="list_head" ><div align="center">状态</div></td>
          <td  class="list_head" ><div align="center">操作员</div></td>
                    
        </tr>

  
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	
<%
DevicePrematchRecordDTO dto=(DevicePrematchRecordDTO)pageContext.getAttribute("oneline");
DeviceBatchPreauthDTO oneDto=Postern.getDeviceBatchPreauthDTOByBatchID(dto.getBatchId());

String operatorName=Postern.getOperatorNameByID(dto.getOpId());
String refersheetserialno=oneDto.getReferSheetSerialNO();

		
%>
	
   
  <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">

      <td align="center"><tbl:write name="oneline" property="seqNo"/></td>
      <td align="center"><tbl:write name="oneline" property="stbDeviceModel"/></td>
		  <td align="center"><tbl:write name="oneline" property="stbSerialNO"/></td>
      <td align="center"><tbl:write name="oneline" property="scdeviceModel"/></td>
		  <td align="center"><tbl:write name="oneline" property="scSerialNo"/></td>
      <td align="center"><d:getcmnname typeName="SET_D_DEVICEPREAUTH_OPERATIONTYPE" match="oneline:operationtype" /></td>
      	
  	 <td align="center"><tbl:write name="oneline" property="batchId"/></td>
  	 	<td align="center"><%= refersheetserialno %></td>
      <td align="center"><tbl:writedate name="oneline" property="createTime" includeTime="true" /></td> 
      <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" /></td>   
     <td align="center"><%= operatorName %></td>  
  </tr>
    

			
	</lgc:bloop> 	
		
		
   
<tr>
    <td colspan="11" class="list_foot"></td>
  </tr>
</table>
	
	
	<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

	<br>

	
	
	 <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
        	
        	   <bk:canback url="dev_detail.do">
        	   	
        	   	
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
                <td background="img/button_bg.gif"><a href="<bk:backurl property="dev_detail.do" />" class="btn12">返&nbsp;回</a></td>
                <td><img src="img/button2_l.gif" border="0" ></td>
                
             </bk:canback>
         
           
        </tr>
    </table>  

</form>
