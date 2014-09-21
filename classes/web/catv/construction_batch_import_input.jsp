<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%
//上传文件要用到，在webaction中使用过后remove掉
session.setAttribute(CommonKeys.FILE_UPLOAD_PAGE_CONTEXT,pageContext);
java.util.LinkedHashMap terminalStatus=new java.util.LinkedHashMap();
terminalStatus.put("N","开通");
terminalStatus.put("I","新建");
pageContext.setAttribute("terminalStatus",terminalStatus);
%>

<script language=javascript>
function file_submit(){
	if(!check_form())
 { 
		return;
	}
	
	if(document.frmPost.txtFileName.value==""){
	    alert("没有有效的数据文件！");
	    return;   
	}
	var getUrl=formToGetUrl('frmPost');
	document.frmPost.action="construction_batch_import_ipnut.do?"+getUrl;
  document.frmPost.submit();
}
function check_form(){

	if (check_Blank(document.frmPost.txtConstructionName, true, "小区名称"))
		return false;	
	if (document.frmPost.txtDistrict.value==""){
		alert("所在区域信息不能为空.");
		document.frmPost.selDistButton.focus;
		return false;	
	}
	if (check_Blank(document.frmPost.txtCatvTerminalType, true, "终端类型"))
		return false;	
	if (check_Blank(document.frmPost.txtCableType, true, "电缆类型"))
		return false;	
	if (check_Blank(document.frmPost.txtBiDirectionFlag, true, "是否双向网"))
		return false;	
	if (check_Blank(document.frmPost.txtPostCode, true, "邮编"))
		return false;	

	if (!check_Num(document.frmPost.txtTatolHouseNumber, true, "小区总户数"))
		return false;	
		
	return true;
}

function formToGetUrl(formName){
//	var inputs=document.getElementsByName(formName)[0].getElementsByTagName("input");
	var inputs=document.getElementsByName(formName)[0].elements;
	var getString='';
	for(i=0;i<inputs.length;i++){	
		var vi=inputs[i];
		getString+=vi.name+'='+vi.value+'&';
 	}  
 	return getString.substring(0,getString.length-1);
}

function query_fiber_node(){
	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	var strUrl="fiber_node_query.do?txtFrom=1&txtTo=10";
	var result=showModalDialog(strUrl,window,strFeatures);
	if (result!=null){
		var index = result.indexOf(";");
		if(index != -1){
		var resultID = result.substring(0,result.indexOf(";"));
		var resultDesc = result.substring(result.indexOf(";")+1,result.length);
		
	 document.frmPost.txtFiberNodeID.value=resultID;
	 document.frmPost.txtFiberNode.value=resultDesc;
	 }
	 }
} 	
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">手工录入网络终端建设信息-信息录入</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="construction_batch_import_ipnut.do?txtActionType=fileUpload" method="post" enctype="multipart/form-data"> 
<input type="hidden" name="txtActionType" value="fileUpload">
<input type="hidden" name="txtFrom" value="1">
<input type="hidden" name="txtTo" value="10">
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=17% align="right">网络建设单号</td>
    <td class="list_bg1" width=33% align="left"><input type="text" name="txtSheetNo" size="25" value="<tbl:writeparam name="txtSheetNo"/>" maxlength="50" ></td>
    <td class="list_bg2" width=17% align="right">小区名称*</td>
    <td class="list_bg1" width=33% align="left"><input type="text" name="txtConstructionName" size="25" value="<tbl:writeparam name="txtConstructionName"/>" maxlength="50" ></td>
  </tr>
  <tr>
    <td class="list_bg2"  align="right">所在区域*</td>
    <td class="list_bg1"  align="left">
     	<input type="hidden" name="txtDistrict" value="<tbl:writeparam name="txtDistrict" />">
						<input type="text" name="txtDistrictDesc" size="25" maxlength="50" readonly
							value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="text">
						<input name="selDistButton" type="button" class="button" value="选择"
							onClick="javascript:sel_district('all','txtDistrict','txtDistrictDesc')">
    </td>
    <td class="list_bg2"  align="right">工程负责单位</td>
    <td class="list_bg1"  align="left"><input type="text" name="txtBuilderName" size="25" value="<tbl:writeparam name="txtBuilderName"/>" maxlength="50" >
	  </td>
	</tr>
  <tr>
    <td class="list_bg2"  align="right">终端类型*</td>
    <td class="list_bg1"  align="left">
    	<d:selcmn name="txtCatvTerminalType" mapName="SET_A_CATVTERMTYPE" match="txtCatvTerminalType" width="23" />
	  </td>
    <td class="list_bg2"  align="right">终端状态*</td>
    <td class="list_bg1"  align="left">
    	<tbl:select name="txtCatvTerminalStatus" set="terminalStatus" match="txtCatvTerminalStatus" width="23" />
	  </td>
	</tr>
  <tr>
    <td class="list_bg2" align="right">电缆类型*</td>
    <td class="list_bg1" align="left">  
     	<d:selcmn name="txtCableType" mapName="SET_A_CABLETYPE" match="txtCableType" width="23" />
    </td>
    <td class="list_bg2" align="right">是否双向网*</td>
    <td class="list_bg1" align="left">
     	<d:selcmn name="txtBiDirectionFlag" mapName="SET_G_YESNOFLAG" match="txtBiDirectionFlag" width="23" />
    </td>
	</tr>	
  <tr>
    <td class="list_bg2" align="right">邮编*</td>
    <td class="list_bg1" align="left"> 
     	<input type="text" name="txtPostCode" size="25" value="<tbl:writeparam name="txtPostCode"/>" maxlength="6" >
    </td>
    <td class="list_bg2" align="right">小区总户数</td>
    <td class="list_bg1" align="left">
     	<input type="text" name="txtTatolHouseNumber" size="25" value="<tbl:writeparam name="txtTatolHouseNumber"/>" maxlength="10" >
    </td>
	</tr>
  <tr>
    <td class="list_bg2" align="right">地址录入前辍</td>
    <td class="list_bg1" align="left"> 
     	<input type="text" name="txtAddressPrefix" size="25" value="<tbl:writeparam name="txtAddressPrefix"/>" maxlength="200" >
    </td>
    <td class="list_bg2" align="right">所属光节点</td>
    <td class="list_bg1" align="left"> 
			<input name="txtFiberNodeID" type="hidden" size="25" value="<tbl:writeparam name="txtFiberNodeID"/>">
     	<input type="text" name="txtFiberNode" size="25" value="<tbl:writeparam name="txtFiberNode"/>" maxlength="50" readonly>
			<input name="selFiberButton" type="button" class="button" value="选择" onClick="javascript:query_fiber_node()">     		
    </td>
	</tr>

  <tr>
    <td class="list_bg2" align="right">数据文件*</td>
    <td class="list_bg1" align="left" colspan="3">
    	<input type="file" name="txtFileName" size="50" >
    </td>
	</tr>
  <tr>
    <td class="list_bg2" align="right">备注</td>
    <td class="list_bg1" align="left" colspan="3">
    	<input type="text" name="txtDesc" size="83" value="<tbl:writeparam name="txtDesc"/>" maxlength="200" >
    </td>
	</tr>
</table>
<br>

	<table align="center" border="0" cellspacing="0" cellpadding="0" height="20">
		<tr>
			<td align="center" >  
				<table align="center" border="0" cellspacing="0" cellpadding="0" height="20">
					<tr height="20">
						<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
						<td height="20"><input name="next" type="button" class="button" onClick="javascript:file_submit()" value="下一步"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
					</tr>
				</table>  
				</td>
			</tr>	
		</table>  
</form> 
