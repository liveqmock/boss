<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.OrganizationDTO" %>
<%@ page import="java.util.*"%>  
<%@ page import="com.dtv.oss.util.Postern"%>
 <%@ page import="com.dtv.oss.web.util.CommonKeys" %>
 <%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
 
<script language=javascript>
function query_submit()
{
        document.frmPost.submit();
}
function frmPost_close(){
   var orgID = eval('window.opener.frmPost.txtAutoNextOrgID');
   var orgName = eval('window.opener.frmPost.txtNextOrgName');
   var count =0;
   l_orgID=document.getElementsByName("choosedID") ;
   l_orgName=document.getElementsByName("choosedName") ;
	for(i=0;i<l_orgID.length;i++){  
		if(l_orgID[i].checked){
	           orgID.value = l_orgID[i].value;
	           orgName.value = l_orgName[i].value;
	           ++count;
		} 
	}
   if(orgID.value==''){
   	if(count <= 0){
   		return;
   	}else{
	   	alert("未选择有效的流转部门！");
	   	return;
	}
   }
	window.close();
}

</script>

 
<TABLE border="0" cellspacing="0" cellpadding="0"  width="480">
<TR>
	<TD>
 
 
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr align="center">
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">流转部门列表</td>
  </tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="" method="post" >   
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" width="20%"></td>
    <td class="list_head" width="20%" nowrap>部门编号</td>
    <td class="list_head" nowrap>部门名称</td>
  </tr>
  <%	String role=request.getParameter("strRole");
  	String subrole = request.getParameter("strSubRole");
  	String orgrole=(role==null?"":role);
  	Collection orgCol = Postern.getCanBeTransferedToOrgID(CurrentLogonOperator.getOperator(request).getOrgID(),orgrole,subrole);
	for (Iterator itr = orgCol.iterator();itr.hasNext();){ 
		OrganizationDTO dto=(OrganizationDTO)itr.next();
		String orgName=Postern.getOrganizationDesc(dto.getOrgID());%>
 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'"> 
    <td align="center"><input type="radio" name="choosedID" value="<%=dto.getOrgID()%>"/></td>
    <td align="center"><%=dto.getOrgID()%></td>
    <td align="center"><input type="hidden" name="choosedName" value="<%=orgName%>" /><%=orgName%></td>
  </tr>
  <%}%>
  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
</table> 
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0" >
	<tr >
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:window.close()" value="关闭窗口"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frmPost_close()" value="确 定"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>     
</form>              
</TD>
</TR>
</TABLE>