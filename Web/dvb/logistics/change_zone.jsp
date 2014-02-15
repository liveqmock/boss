<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%
	String txtZoneStation = request.getParameter("txtZoneStation");
	String strTitle = request.getParameter("strTitle");
	

%>




<Script language=JavaScript>
<!--
function frm_submit(){
if("D"=="<%=txtZoneStation%>")
	depot_select();
else if("T"=="<%=txtZoneStation%>")
	org_select();
else if("B"=="<%=txtZoneStation%>")
  cust_select();
	
}

function depot_select(){
	if (check_Blank(document.frmPost.txtDepotID, true, "运入仓库"))
	{	
  	return false;
  }	
  opener.document.all["selOrgButton"].style.display="none";
  if(opener.document.frmPost.txtDepotID != null)
  {
  	opener.document.frmPost.txtDepotID.disabled = false;
  	opener.document.frmPost.txtDepotID.selectedIndex=document.frmPost.txtDepotID.selectedIndex;
  }
  opener.document.frmPost.txtOrgDesc.readOnly=true;
  opener.document.frmPost.txtOrgDesc.innerText = get_select_text(document.frmPost.txtDepotID);
  opener.document.frmPost.txtAddressID.value = document.frmPost.txtDepotID.value;
  opener.document.getElementById('modelFieldNameDesc').innerHTML='';
  window.close();
}

function org_select(){
	if (check_Blank(document.frmPost.txtAddressID, true, "组织机构"))
	{	
  	return false;
  }	
  if(opener.document.frmPost.txtDepotID != null)
  {
  	opener.document.frmPost.txtDepotID.disabled = true;
  	opener.document.frmPost.txtDepotID.selectedIndex = 0;
  }
  opener.document.all["selOrgButton"].style.display="";
  opener.document.frmPost.txtOrgDesc.readOnly=true;
  opener.document.frmPost.txtAddressID.value = document.frmPost.txtAddressID.value;
  opener.document.frmPost.txtOrgDesc.innerText = document.frmPost.txtOrgDesc.value;
  opener.document.getElementById('modelFieldNameDesc').innerHTML='';
  window.close();
}

function cust_select(){
	if (check_Blank(document.frmPost.txtAddressID, true, "客户ID"))
	{	
  	return false;
  }	
  if(opener.document.frmPost.txtDepotID != null)
  {
    opener.frmPost.txtDepotID.disabled = true;
    opener.document.frmPost.txtDepotID.selectedIndex = 0;
  }
		opener.document.all["selOrgButton"].style.display="none";
		opener.document.frmPost.txtOrgDesc.readOnly=false;
		opener.document.frmPost.txtAddressID.value = document.frmPost.txtAddressID.value;
		opener.document.frmPost.txtOrgDesc.innerText = document.frmPost.txtAddressID.value;
		opener.document.getElementById('modelFieldNameDesc').innerHTML='请填写客户证号';
		window.close();
}

function colse_click(){
	window.close();	
}

function get_select_text(selObj){   
  var retValue = "";   
  for(i = 0; i<selObj.options.length;i++)   
  {   
     if(selObj.options[i].selected)  
     { 
        retValue=selObj.options[i].text;
        break;   
     }
  }
  return retValue;
}   
//-->
</Script>


<form name="frmPost" method="post" action="customer_type_modify.do" >
    
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title"><%=strTitle%></td>
      </tr>
    </table>
    <br>
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>
 
    <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
    <%if("D".equals(txtZoneStation)){%>
       <tr>
	       <td class="list_bg2"><div align="right">运入仓库</div></td>
         <td class="list_bg1">
         <d:seldepotbyoper name="txtDepotID" match="txtDepotID" width="23" />
         </td>
       </tr>
       <%}if("T".equals(txtZoneStation)){%>
       <tr>
	       <td class="list_bg2"><div align="right">组织机构</div></td>
	       <td class="list_bg1">
	         <input type="hidden" name="txtAddressID" value="<tbl:writeparam name="txtAddressID" />">
			     <input type="text" id="txtOrgDesc" name="txtOrgDesc" size="25" maxlength="50" value="<tbl:WriteOrganizationInfo property="txtAddressID" />" >
			     <input id="selOrgButton" name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('all','txtAddressID','txtOrgDesc')">
        </td>	
       </tr>
       <%}if("B".equals(txtZoneStation)){%>
       <tr>
	       <td class="list_bg2"><div align="right">客户ID</div></td>
         <td class="list_bg1">
         <input type="text" name="txtAddressID" value="<tbl:writeparam name="txtAddressID" />">
         </td>
       </tr>
       <%}%>
	  </table>  

		<BR>  
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
      
        <tr>  
          <td width="20" ></td>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
	        <td><input name="aaa" type="button" class="button" onClick="colse_click()" value="关  闭"></td>           
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="bbb" type="button" class="button" onClick="frm_submit()" value="确  定"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
        </tr>
     </table>
</form>      
		 


