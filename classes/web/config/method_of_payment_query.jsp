<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
 

function view_detail_click(strId)
{
	self.location.href="method_payment_detail.do?txtMopID="+strId;
}
function method_payment_create_submit() {
    
   document.frmPost.action="method_of_payment_create.screen";
   document.frmPost.submit();
}
 

</script>
 
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">付费方式列表</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" >
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
            <td class="list_head">记录ID</td>
            <td class="list_head">名称</td>
            <td class="list_head">支付类型</td>
            <td class="list_head">是否付费计划</td>
            <td class="list_head">是否支付方式</td> 
            <td class="list_head">合作伙伴</td>
             <td class="list_head">是否归入现金帐户</td>
            <td class="list_head">状态</td>
          </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <%
           String partName=null;
           MethodOfPaymentDTO dto = (MethodOfPaymentDTO) pageContext.findAttribute("oneline");
           int partId = dto.getPartnerID();
            partName =  Postern.getOrgNameByID(partId);
            if(partName == null) partName="";
           %>
          <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="mopID"/>')" class="link12" ><tbl:writenumber name="oneline" property="mopID" digit="7"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="name"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_F_MOPPAYTYPE" match="oneline:payType"/></td>
      	      <td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:accountflag"/></td>
      	      <td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:paymentflag"/></td>
      	       <td align="center"><%=partName%></td>
      	       <td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:cashFlag"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>   
         </tr>    	    
</lgc:bloop>  

<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>

<br>
  
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:method_payment_create_submit()" class="btn12">新&nbsp; 增</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
   </table>   
<br>

 
   
 
	 
    </td>
  </tr>
</form>  
</table>  
 
