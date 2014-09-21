
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.ContractDTO" %>

<%
//解析上传文件要用到，在webaction中使用过后remove掉
session.setAttribute(CommonKeys.FILE_UPLOAD_PAGE_CONTEXT,pageContext);

String contractNo=request.getParameter("txtContractNo");

ContractDTO contractDTO=Postern.getContractDTOByContractNo(contractNo);
String contractName=contractDTO.getName();
if(contractName==null)contractName="";
String contractDesc=contractDTO.getDescription();
if(contractDesc==null)contractDesc="";

StringBuffer packageListDesc=new StringBuffer();
StringBuffer packageList=new StringBuffer();
Map packageMap=Postern.getProductPackageListWithContractNO(contractNo);
Iterator it =packageMap.keySet().iterator();
while(it.hasNext()){
	String key=(String)it.next();
	String value=(String)packageMap.get(key);
	packageList.append(key).append(";");
	//packageListDesc.append(key).append(",");
	 if(!"".equals(packageListDesc.toString()))
	 		packageListDesc.append(";");
	packageListDesc.append(value);
}
String accountID=request.getParameter("txtAccount");
String accountName=Postern.getAcctNameById(Integer.parseInt(accountID));
//System.out.println("packageListDesc:"+packageListDesc.toString());
%>

<script language=javascript>
function file_submit(){
	if(document.frmPost.txtFileName.value==""){
	    alert("没有有效的数据文件！");
	    return;   
	}
	document.frmPost.action="group_customer_batch_import_fee.do?txtActionType=fileUpload&txtContractNo="
	+"<tbl:writeparam name="txtContractNo"/>&txtAccount=<tbl:writeparam name="txtAccount"/>&txtGroupCustID=<tbl:writeparam name="txtGroupCustID"/>&txtOpenSourceType=<tbl:writeparam name="txtOpenSourceType"/>";
  document.frmPost.submit();
}
function back_submit(){
	document.frmPost.action="group_preparefor_contract_select.do?"
	+"txtGroupCustID=<tbl:writeparam name="txtGroupCustID" />"
	+"&txtContractNo=<tbl:writeparam name="txtContractNo"/>"
	+"&txtAccount=<tbl:writeparam name="txtAccount"/>"
	+"&selStatus=<tbl:writeparam name="selStatus"/>"
	+"&txtType=<tbl:writeparam name="txtType"/>";
	document.frmPost.submit();
	//history.back();
}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">子客户信息批量录入</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="group_customer_batch_import_fee.do?txtActionType=fileUpload" method="post" enctype="multipart/form-data">    

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=17% align="right">合同编号</td>
    <td class="list_bg1" width=33% align="left"><tbl:writeparam name="txtContractNo" /></td>
    <td class="list_bg2" width=17% align="right">合同名称</td>
    <td class="list_bg1" width=33% align="left"><%=contractName%></td>
		<input type="hidden" name="txtName" value="<%=contractName %>">
  </tr>
  <tr>
    <td class="list_bg2"  align="right">合同描述</td>
    <td class="list_bg1"  align="left" colspan="3"><%=contractDesc%></td>
	</tr>
  <tr>
    <td class="list_bg2"  align="right">产品包</td>
    <td class="list_bg1"  align="left" colspan="3"><%=packageListDesc.toString() %>
			<input type="hidden" name="txtPackageListDesc" value="<%=packageListDesc.toString() %>">
			<input type="hidden" name="txtPackageList" value="<%=packageList.toString() %>">
	  </td>
	</tr>
  <tr>
    <td class="list_bg2" align="right">帐户</td>
    <td class="list_bg1" align="left" colspan="3">
    	<%=accountName%>
			<input type="hidden" name="txtAccountName" value="<%=accountName%>">
    </td>
	</tr>
  <tr>
    <td class="list_bg2" align="right">数据文件</td>
    <td class="list_bg1" align="left" colspan="3">
    	<input type="file" name="txtFileName" size="50"/>
    </td>
	</tr>
</table>
<br>
				<table align="center" border="0" cellspacing="0" cellpadding="0" height="20">
					<tr height="20">
						<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
						<td height="20"><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
						<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
						<td width="20"></td>
						<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
						<td height="20"><input name="next" type="button" class="button" onClick="javascript:file_submit()" value="下一步"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
					</tr>
				</table>  
</form> 
