<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri"%>
<%@ page import="com.dtv.oss.dto.DevicePreauthRecordDTO"%>
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
    <td class="title">机顶盒/智能卡批量配对记录--明细信息</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

		
<%
int batchId=(Integer.valueOf(request.getParameter("txtBatchId"))).intValue();


DeviceBatchPreauthDTO oneDto=Postern.getDeviceBatchPreauthDTOByBatchID(batchId);

pageContext.setAttribute("DeviceBatchPreauthDTO",oneDto);

String operName=Postern.getOperatorNameByID(oneDto.getOpId());

String operationKey=oneDto.getOperationType();
String operationValue=Postern.getCommomsettingData_ValueByKeyAndName("SET_D_DEVICEPREAUTH_OPERATIONTYPE",operationKey);

String statusKey=oneDto.getStatus();
String statusValue=Postern.getCommomsettingData_ValueByKeyAndName("SET_G_GENERALSTATUS",statusKey);



%>

		<table width="100%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">

			<tr>
				<td class="list_bg2" width="17%">
					<div align="right">
						记录ID
					</div>
				</td>
				<td class="list_bg1" width="33%">
					<font size="2"> <input type="text" name="batchId" size="25"
							maxlength="50" value="<tbl:write name="DeviceBatchPreauthDTO" property="batchId"/>"
							 class="textgray" readonly> </font>
				</td>

				<td class="list_bg2" width="17%">
					<div align="right">
						记录单号
					</div>
				</td>
				<td class="list_bg1" width="33%">
					<font size="2"> <input type="text" name="referSheetSerialNO" size="25"
							maxlength="50"
							value="<tbl:write name="DeviceBatchPreauthDTO" property="referSheetSerialNO"/>"
							 class="textgray" readonly> </font>
				</td>



			</tr>

			<tr>
				<td class="list_bg2" width="17%">
					<div align="right">
						创建时间
					</div>
				</td>
				<td class="list_bg1" width="33%">
					<font size="2"> <input type="text" name="createTime" size="25"
							maxlength="50"
							value="<tbl:writedate name="DeviceBatchPreauthDTO" property="createTime"/>" class="textgray" readonly>
					</font>
				</td>


				<td class="list_bg2" width="17%">
					<div align="right">
						操作员
					</div>
				</td>
				<td class="list_bg1" width="33%">
					<font size="2"> <input type="text" name="opId"
							size="25" maxlength="50"
							value="<%=operName%>" class="textgray" readonly>
					</font>
				</td>


			</tr>
			<tr>

				<td class="list_bg2" width="17%">
					<div align="right">
						操作类型
					</div>
				</td>
				<td class="list_bg1" width="33%">
					<font size="2"> <input type="text" name="operationType"
							size="25" maxlength="50"
							value="<%=operationValue%>" class="textgray" readonly>
					</font>

				</td>

				<td class="list_bg2" width="17%">
					<div align="right">
						配对数
					</div>
				</td>
				<td class="list_bg1" width="33%">
					<font size="2"> <input type="text"
							name="totalDeviceNum" size="25" maxlength="50"
							value="<tbl:write name="DeviceBatchPreauthDTO" property="totalDeviceNum" />" class="textgray" readonly>
					</font>
				</td>


			</tr>
			<tr>

				<td class="list_bg2" width="17%">
					<div align="right">
						数据文件
					</div>
				</td>
				<td class="list_bg1" width="33%">
					<font size="2"> <input type="text"
							name="fileName" size="25" maxlength="50"
							value="<tbl:write name="DeviceBatchPreauthDTO" property="fileName" />" class="textgray" readonly>
					</font>
				</td>


				<td class="list_bg2" width="17%">
					<div align="right">
						记录状态
					</div>
				</td>
				<td class="list_bg1" width="33%">
					<font size="2"> <input type="text" name="status"
							size="25" maxlength="50"
							value="<%=statusValue%>" class="textgray" readonly>
					</font>
				</td>
			</tr>
			
			
			
			<tr>

				<td class="list_bg2" >
					<div align="right">
						描述
					</div>
				</td>
				<td class="list_bg1" colspan="3">
					<font size="2"> <input type="text"
							name="description" size="83" maxlength="83"
							value="<tbl:write name="DeviceBatchPreauthDTO" property="description" />" class="textgray" readonly>
					</font>
				</td>
				
			</tr>





		</table>
		
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	
	
		         <tr>
           <td colspan="8" class="import_tit" align="center"><font size="3">设备配对明细</font></td>
        </tr> 
         <tr class="list_head">
          <td class="list_head" ><div align="center">流水号</div></td>
          <td class="list_head" ><div align="center">机顶盒型号</div></td>
          <td class="list_head"><div align="center">机顶盒序列号</div></td>
          <td class="list_head" ><div align="center">智能卡型号</div></td>
          <td class="list_head"><div align="center">智能卡序列号</div></td>
          <td  class="list_head" ><div align="center">操作类型</div></td>
          <td  class="list_head" ><div align="center">创建时间</div></td>
          <td  class="list_head" ><div align="center">状态</div></td>
          
        </tr>

  
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	
   
  <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      <td align="center"><tbl:write name="oneline" property="seqNo"/></td>
      <td align="center"><tbl:write name="oneline" property="scdeviceModel"/></td>
      <td align="center"><tbl:write name="oneline" property="scSerialNo"/></td>
      <td align="center"><tbl:write name="oneline" property="stbDeviceModel"/></td>
      <td align="center"><tbl:write name="oneline" property="stbSerialNO"/></td>
      
      

      	
      <td align="center"><d:getcmnname typeName="SET_D_DEVICEPREAUTH_OPERATIONTYPE" match="oneline:operationtype" /></td>

      <td align="center"><tbl:writedate name="oneline" property="createTime" includeTime="true" /></td> 
      <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" /></td>     
  </tr>
    

			
	</lgc:bloop> 	
		
		
   
<tr>
    <td colspan="9" class="list_foot"></td>
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
        	
        	   <bk:canback url="device_batchMatch_query_result.do">
        	   	
        	   	
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
                <td background="img/button_bg.gif"><a href="<bk:backurl property="device_batchMatch_query_result.do" />" class="btn12">返&nbsp;回</a></td>
                <td><img src="img/button2_l.gif" border="0" ></td>
                
             </bk:canback>
         
           
        </tr>
    </table>  

</form>
