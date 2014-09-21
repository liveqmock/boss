<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.wrap.work.CustomerProblem2CPProcessWrap" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.dto.CustProblemProcessDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>

function check_frm()
{
 
 if (check_Blank(document.frmPost.txtWorkResultReason, true, "终止原因"))
		return false;
   return true;
}
function frm_submit()
{
    if (check_frm())document.frmPost.submit();
    
} 
 
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">终止报修单</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" action="teminate_rep_op.do" >

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 

<%
        
        CustomerProblem2CPProcessWrap wrap = (CustomerProblem2CPProcessWrap)pageContext.getAttribute("oneline");
	CustomerProblemDTO dto=wrap.getCpDto();
	CustProblemProcessDTO cppDto=wrap.getCppDto();
	 String strAddress = Postern.getAddressById(dto.getAddressID());
	 AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressID());
          pageContext.setAttribute("oneline", dto);
          pageContext.setAttribute("cpp", cppDto);
          pageContext.setAttribute("custaddr", addrDto);
          Map mapOrgName = Postern.getOrgNameMapByOrgRole("R");
          if (mapOrgName!=null)
          pageContext.setAttribute("ServiceRepOrg",mapOrgName.values());
	if (strAddress==null) strAddress="";
    
%>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
          <td class="list_bg2"><div align="right">报修单编号</div></td>
          <td class="list_bg1">
	  <input type="text" name="txtId" size="25"  value="<tbl:write name="oneline" property="Id" />" class="textgray" readonly   >
	  <td class="list_bg2"><div align="right">所属区域</div></td>
	   <td class="list_bg1"> 
	    <input type="text" name="txtCounty" size="25" maxlength="50" class="textgray" readonly value="<tbl:WriteDistrictInfo name="custaddr" property="DistrictID" />" class="text">
	   
	   </td>
	</tr>
	
	<tr>
	  <td class="list_bg2" ><div align="right">报修类别</div></td>
	  <td class="list_bg1"> 
	  <input type="text" name="txtProblemCategory" size="25"  value="<d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:problemCategory" />"  class="textgray" readonly  >
	 </td>
	   <td class="list_bg2"><div align="right">地址</div></td>
	  <td class="list_bg1">
	  <input type="text" name="txtAddressId" size="25"  value="<%=strAddress%>" class="textgray" readonly >
	</td> 
	</tr>
	 <tr>
        <td class="list_bg2"><div align="right">终止原因</div></td>
	 <td class="list_bg1" colspan="3"> 
	 <d:selcmn name="txtWorkResultReason" mapName="SET_W_JOBRESULTREASON" match="txtWorkResultReason" width="23" />
	 </td>
	 </tr>  
	<tr>
	  <td valign="middle" class="list_bg2" align="right" >故障描述</td>
		<td class="list_bg1" colspan="3"><font size="2">
		<textarea name="txtProblemDesc"  class="textareagray" readonly  cols="80" rows=3><tbl:write  name="oneline" property="ProblemDesc" /></textarea>
		</font></td>
	    
	 </tr>  
       
</table>	  
      <input type="hidden" name="func_flag" value="1115">
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
   </table>
  <br>    
    
   
  <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
        <td><img src="img/button2_r.gif" width="22" height="20"></td>
             <td background="img/button_bg.gif"  ><a href="<bk:backurl property='cp_query_for_terminate.do/cp_query_detail.do' />" class="btn12">返&nbsp;回</a></td>             
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
              <td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">终止维修</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
         
        </tr>
   </table>    
  
</lgc:bloop>   
<tbl:generatetoken />  
 </form>  

 
 

   

 