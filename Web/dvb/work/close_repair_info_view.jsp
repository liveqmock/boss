<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>

function check_frm()
{
    if (check_Blank(document.frmPost.txtMemo, true, "备注信息"))
		return false;
   return true;
}
function frm_submit()
{
    if (check_frm()){
    document.frmPost.action="close_repair_info.do";
    document.frmPost.submit();
    }
} 
function back_submit(){

          document.frmPost.action="close_repair_info_query_result.do";
	  document.frmPost.submit();
} 
 

 
</script>
 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">结束维修工单</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
 

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%
    com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap   wrap=(com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getJobCardDto());
    pageContext.setAttribute("jobcardprocess",  wrap.getJobCardProcessDto());
    JobCardDTO dto=wrap.getJobCardDto();
    pageContext.setAttribute("jobcardprocess",  wrap.getJobCardProcessDto());
    String strAddress = Postern.getAddressById(dto.getAddressId());
    if (strAddress==null) strAddress="";
    
%>
<form name="frmPost" method="post" action="close_repair_info.do" >
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">工单编号</div></td>
        <td class="list_bg1">
	<input type="text" name="txtJobCardID" size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
	<td class="list_bg2"><div align="right">详细地址</div></td>
		 <td class="list_bg1">
			<input type="text" name="txtAddressId" size="25"  value="<%=strAddress%>" class="textgray" readonly   >
		 </td> 
	</tr>
	 
	<tr>
		<td class="list_bg2" ><div align="right">联系人*</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtContactPerson" size="25"  value="<tbl:write name="oneline" property="contactPerson" />"  class="textgray" readonly  >
		 </td>
		<td class="list_bg2"><div align="right">联系电话*</div></td>
		<td class="list_bg1"> <input type="text" name="txtContactPhone" size="25"  value="<tbl:write name="oneline" property="contactPhone" />"  class="textgray" readonly  >
		</td>
	</tr>
	 
	<tr>
	 <td class="list_bg2"><div align="right">备注信息*</div></td>
	<td class="list_bg1" colspan="3"> 
	<input type="text" name="txtMemo" size="83" maxlength="200" value="<tbl:write name="jobcardprocess" property="Memo" />">
	</td>
	</tr>
        
  </table>
      
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>  
   <input type="hidden" name="func_flag" value="1009" >  
 
</lgc:bloop> 
 <tbl:generatetoken />
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
        <td><img src="img/button2_r.gif" width="22" height="20"></td>  
        <td background="img/button_bg.gif">
	<a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
        <td><img src="img/button2_l.gif" width="13" height="20"></td>
       
             <td width="20" ></td>
            
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">结束维修</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
        
                
 
         
        </tr>
      </table>    
 
     
     
  
 </form>  

 
 

   

 