<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="java.util.*"%>

<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>

<%
	String noCol = request.getParameter("txtTerminalDevices").trim();
	System.out.println("noCol"+noCol);
	String[] lineList =noCol.split("\n");
	String fieldDesc=request.getParameter("checkModelDesc").trim();
	//取得所有的字段描述信息
	String[] fieldDescList = fieldDesc.split("\\|");
	int iColSpan=fieldDescList.length+2;
	
	//新增用于处理供应商设备入库标志
	if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType"))))
		iColSpan++;
	int iNotExistDeviceNum=0;
	String strNotExistSerialNos=request.getParameter("txtNotExistSerialNo");
	if(strNotExistSerialNos==null)strNotExistSerialNos="";
%>

<script language=javascript>
<!--
function create_submit(){   
      	document.frmPost.submit();
}
	 
function back_submit(){
      	document.frmPost.action="dev_into_depot.screen";
	document.frmPost.submit();
}
 
function formreset(){
    document.frmPost.reset();
}

function superTrim(para){
	if(para==null||para=='')
		return '';
	var newStr=para.substring(para.length-1,para.length);
	while(newStr=='\n'||newStr==' '||newStr.charCodeAt(0)==13){
		para=para.substring(0,para.length-1);
		newStr=para.substring(para.length-1,para.length);
	}
	newStr=para.substring(0,1);
	while(newStr=='\n'||newStr==' '||newStr.charCodeAt(0)==13){
		para=para.substring(1,para.length);
		newStr=para.substring(0,1);
	}
	return para;
}

//删除该记录
function del_one_deviceInfo(deviceInfo){
	deviceInfo="" + deviceInfo;
	var existSerialNo=document.frmPost.txtNotExistSerialNo.value;
	var replaceReg=";" + deviceInfo;
	document.frmPost.txtNotExistSerialNo.value=existSerialNo.replace(replaceReg,"");
	
	var sCol=document.frmPost.txtTerminalDevices.value;
	sCol=superTrim(sCol);
	var arrDs=sCol.split('\n');
	sCol="";
	for(i=0;i<arrDs.length;i++){
		var curVar=superTrim(arrDs[i]);
		var curOne=curVar.split('|');
		var curDeviceInfo=superTrim(curOne[0]);
		if(curVar!=deviceInfo){
			if(sCol!="")sCol=sCol+"\n";
			sCol=sCol + curVar;
		}
	}
	document.frmPost.txtTerminalDevices.value=sCol;
	
	document.frmPost.action="dev_into_depot_confirm.screen";
	document.frmPost.submit();
}
//-->
</script>

<form name="frmPost" action="device_into_depot_confirm.do" method="post">

<table border="0" align="center" cellpadding="0" cellspacing="10">
    <tr>
	<td><img src="img/list_tit.gif" width="19" height="19"></td>
	<td class="title">设备序列号预览</td>
    </tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr class="list_head">
		<td class="list_head"><div align="center">序号</div></td>
		<%	//生成标题
			int rec=0;
			for (int j = 0; j < fieldDescList.length; j++) {
		%>
		<td class="list_head"><div align="center"><%=fieldDescList[j]%></div></td>
		<%	}	%>
		<td class="list_head"><div align="center">保修期(月)</div></td>
		
		<%
			if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType")))){
		%>
		<td class="list_head">操作</td>
		<%	}	 %>
		
	</tr>
	
		<%	//行循环
			for (int l = 0; l < lineList.length; l++) {
				String[] serialNoList = lineList[l].split("\\|");			
				if(serialNoList.length!=fieldDescList.length)
					continue;				
				rec++;
		%>
	<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		<td align="center"><%=rec%></td>
		
		<%
				//列循环
				for (int i = 0; i < fieldDescList.length; i++) {
					String curNO="";
					if(i<serialNoList.length)
						curNO = serialNoList[i];
		%>
		<td align="center">
		<%				if(strNotExistSerialNos.indexOf(";" +lineList[l].trim() +";")>-1){
		%>
		<font color="red">
		<%				}%>
		<%=curNO%>
		<%				if(strNotExistSerialNos.indexOf(";" +lineList[l].trim() +";")>-1){
		%>
		</font>
		<%				}%>
		</td>
		<%		}	%>
		<td align="center"><tbl:writeparam name="txtGuaranteeLength" /></td>
		
		<!--显示是否能编辑-->
		<%
					if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType")))){
						if(strNotExistSerialNos.indexOf(";" +lineList[l].trim() +";")>-1){
							iNotExistDeviceNum++;
		%>
		<td align="center"><a href="javascript:del_one_deviceInfo('<%=lineList[l].trim() %>');">删除</a></td>
		<%				}
						else{ 
		%>
		<td></td>
		<%				}
					}	
		%>
	</tr>
		<%	}	%>
	<tr>
		<td colspan="<%=iColSpan%>" class="list_foot"></td>
	</tr>
	
	<tr class="list_bg2">
		<%
			if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType")))){
		%>
		<td colspan="<%=iColSpan%>" align="right" class="t12">
			序列号为红色的设备表示在供应商设备号码资源库中不存在，您可以进行删除操作<br>
			总共录入&nbsp;<%=lineList.length %>&nbsp;个设备，其中在供应商设备号码资源库中找到&nbsp;<%=(lineList.length-iNotExistDeviceNum) %>&nbsp;个</td>
		<%	}
			else{ 
		%>
		<td colspan="<%=iColSpan%>" align="right" class="t12">总计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=rec%></td>
		<%	}	%>
	</tr>
</table>

<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
		<td class="list_bg1">
		<table align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="img/button2_r.gif" border="0"></td>
				<td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
				<td><img src="img/button2_l.gif" border="0"></td>
				<td width="20"></td>
				<td><img src="img/button_l.gif" border="0"></td>
				<td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">提&nbsp;交</a></td>
				<td><img src="img/button_r.gif" border="0"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
 
<tbl:generatetoken />
<tbl:hiddenparameters pass="txtDeviceClass/txtDeviceModel/txtStatusReason/txtDeviceProvider/txtBatchNo" />
<tbl:hiddenparameters pass="txtGuaranteeLength/txtDepotID/txtComments/seriallength/checkModelField/checkModelDesc" />
<tbl:hiddenparameters pass="txtTerminalDevices/txtOutOrgID/txtInAndOut/txtActionType/txtNotExistSerialNo" />
<tbl:hiddenparameters pass="txtPurposeStrList/txtPurposeStrListValue" /> 
<input type="hidden" name="func_flag" value="1003"></form>

<%
	  System.out.println("____txtPurposeStrListValue="+request.getParameter("txtPurposeStrListValue"));
		System.out.println("____txtPurposeStrList="+request.getParameter("txtPurposeStrList"));
%>











