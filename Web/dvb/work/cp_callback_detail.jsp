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
    <td class="title">查看回访信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" action="rep_callback_create_op.do" >

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 

<%
        
        CustomerProblem2CPProcessWrap wrap = (CustomerProblem2CPProcessWrap)pageContext.getAttribute("oneline");
	CustomerProblemDTO dto=wrap.getCpDto();
	 
	 String strAddress = Postern.getAddressById(dto.getAddressID());
	 AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressID());
          pageContext.setAttribute("oneline", dto);
         
          
          pageContext.setAttribute("custaddr", addrDto);
          
	 if (strAddress==null) strAddress="";
	 
          Map repCallBackMap = Postern.getServeyResultByCustIdForRealUser(dto.getId(),"t_callbackinfo","refersourceid");
          
          pageContext.setAttribute("serveyCallBackMap",repCallBackMap);
 %>
  
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
          <td class="list_bg2"><div align="right">报修单编号</div></td>
          <td class="list_bg1">
	  <input type="text" name="txtId" size="25"  value="<tbl:write name="oneline" property="Id" />" class="textgray" readonly   >
	  <td class="list_bg2"><div align="right">用户证号</div></td>
	  <td class="list_bg1">
	  <input type="text" name="txtCustID" size="25"  value="<tbl:write name="oneline" property="CustID" />" class="textgray" readonly   >
	</td> 
	</tr>
	<tr>
	   <td class="list_bg2"><div align="right">联系电话</div></td>
	   <td class="list_bg1"> 
	   <input type="text" name="txtContactPhone" size="25"  value="<tbl:write name="oneline" property="contactphone" />"  class="textgray" readonly  >
	   </td>
	   <td class="list_bg2"><div align="right">所属分公司</div></td>
	   <td class="list_bg1"> 
	   <input type="text" name="txtCounty" size="25" class="textgray" readonly value="<d:getdistrictname match="custaddr:districtID" />">
	   </td>
	 </tr>
	<tr>
	  <td class="list_bg2"><div align="right">报修类别</div></td>
	  <td class="list_bg1"> 
	  <input type="text" name="txtProblemCategory" size="25"  value="<d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:ProblemCategory" />"  class="textgray" readonly  >
	 </td>
	   <td class="list_bg2"><div align="right">地址</div></td>
	  <td class="list_bg1">
	  <input type="text" name="txtAddressId" size="25"  value="<%=strAddress%>" class="textgray" readonly >
	</td> 
	</tr>
	<tr>
	<td>
	<%
	 if(repCallBackMap!=null && !repCallBackMap.isEmpty()){
	 %>
	   <tbl:dynamicservey serveyType="C"  serveySubType="R"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" match="serveyCallBackMap"/>
  	<%
	     }
	%>
  </td>
	</tr>  
          
</table>

 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
   </table>     
      
 <br>	     
  <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           
         <td><img src="img/button2_r.gif" width="22" height="20"></td>
             <td background="img/button_bg.gif"  ><a href="<bk:backurl property='cp_query_detail.do/cp_query_for_callback.do' />" class="btn12">返&nbsp;回</a></td>             
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
             
           
      </tr>
   </table>  
   
   
   
  
</lgc:bloop>   
 
 </form>  

 
 

   

 