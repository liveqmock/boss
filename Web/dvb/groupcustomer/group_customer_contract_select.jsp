<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="operator" prefix="op" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO"%>
<%@ page import="com.dtv.oss.dto.PackageLineDTO" %>
<%@ page import="com.dtv.oss.dto.ContractDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>

function check_form(){
       	var l=document.getElementsByName("selType") ;
	var str; 
	for(i=0;i<l.length;i++){  
		if(l[i].checked){
			str=l[i].value;
		} 
	}
	if(str=='0'){
		if(document.frmPost.txtContractNo.value==''){
		       	alert("合同号不能为空！");
		       	return false;
	       	}
	}
	if(document.frmPost.txtAccount.value==''){
	       	alert("帐户不能为空！");
	       	return false;
       	}
       	document.frmPost.selStatus.value=str;
       	return true;
}

function frm_submit()
{
	if(check_form()){
		if(document.frmPost.txtType.value=="N"){
			document.frmPost.action="open_group_subcustomer.do";
		}else if(document.frmPost.txtType.value=="Y"){
			document.frmPost.action="group_customer_batch_import.do";
		}else{
			alert("请选择是否批量开户");
			return false;
		}
	document.frmPost.submit();
	}
}
function query_contract(gcid){
	var strFeatures = "width=840px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("contract_query_result_list.do?txtGroupCustID="+gcid,"合同查询",strFeatures);
}
function open_contractno(){
	document.frmPost.selOrgButton.disabled=false;
	document.frmPost.txtContractNo.value="";
}
function hidden_contractno(){
	document.frmPost.txtContractNo.value=document.frmPost.strPreContractNo.value;
	document.frmPost.selOrgButton.disabled=true;
}
</script>
<form name="frmPost" method="post" action="" >
<input type="hidden" name="selStatus" value="<tbl:writeparam name="selStatus"/>">
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">子客户开户合同选择</td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<%
	String txtGroupCustID=request.getParameter("txtGroupCustID");
	
	//得到集团客户的来源渠道,子客户开户的时候取集团客户的来源渠道作为自己的来源渠道
	String txtOpenSourceType = Postern.getCustomerByID(WebUtil.StringToInt(txtGroupCustID)).getOpenSourceType();

  String strContractNo=Postern.getContractNoByGroupCustID(txtGroupCustID);
  String cNo=request.getParameter("txtContractNo");

  if(cNo==null||cNo=="")
  	cNo=strContractNo;
%>
 <input type="hidden" name="strPreContractNo" value="<%=strContractNo%>">  
 <input type="hidden" name="txtOpenSourceType" value="<%=txtOpenSourceType%>">
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td  width="15%" class="list_bg1" align="right"></td>
          <td  align="left" class="list_bg1" ><input type="radio" name="selType" value="1" checked onClick="javascript:hidden_contractno()">使用集团客户开户合同</td>                     
        </tr>
        <tr>
          <td  width="15%" class="list_bg1" align="right"></td>
          <td  align="left" class="list_bg1" ><input type="radio" name="selType" value="0" onClick="javascript:open_contractno()" >使用新合同（选择此项需要在下面指定新开用户的合同号）</td>                     
        </tr>
        
        <tr>  
           <td  width="20%" class="list_bg2" align="right">合同编号</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtContractNo" maxlength="10" size="25"  value="<%=cNo%>" class="text" readonly>  
             <input name="selOrgButton" type="button" class="button" value="查询" onClick="javascript:query_contract(<%=txtGroupCustID%>)" disabled="true">
           </font>
           </td>  
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">帐户</td>
           <td  class="list_bg1" align="left"><font size="2">
           <d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" width="23" style="width:185px;"/>  
           </font>
           </td>  
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">批量开户</td>
           <td  class="list_bg1" align="left"><font size="2">
             <select name="txtType" width="23" style="width:185px;">
             	<option value="N" <% if(("N").equals(request.getParameter("txtType"))) out.println("selected");%>>否</option>
		<option value="Y" <% if(("Y").equals(request.getParameter("txtType"))) out.println("selected");%>>是</option>
	  </select>
           </font>
           </td>  
           <input type="hidden" name="txtGroupCustID" value="<%=txtGroupCustID%>">
        </tr>
     </table>       
</form>   
       <br>
        <table align="center" border="0" cellspacing="0" cellpadding="0" >
	<tr >
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
    </table>
    
     
    <br>  
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	     <td><img src="img/mao.gif" width="1" height="1"></td>
	   </tr>
    </table>
<script language=javascript>
function window_open(){
	var selstatus=document.frmPost.selStatus.value;
	var l=document.getElementsByName("selType") ;
	for(i=0;i<l.length;i++){  
		if(l[i].value==selstatus){
			l[i].checked=true;
		} 
	}
	if(selstatus=="0"){
		document.frmPost.selOrgButton.disabled=false;
	}
}
window_open();

</script>