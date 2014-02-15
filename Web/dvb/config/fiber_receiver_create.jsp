<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
 

 
function check_frm()
{
	
    if (check_Blank(document.frmPost.txtFiberReceiverCode, true, "编号"))
	    return false;
    if (check_Blank(document.frmPost.txtFiberTransmitterId, true, "光发机编号"))
	    return false;
    
	 
     
	    
     return true;
}
 
 
function create_submit(){
  if (check_frm()){
	    document.frmPost.action="create_fiber_receiver.do";
	    document.frmPost.Action.value="CREATE";
	    document.frmPost.submit();
	  }
}

</script>
 <%
 
  
    Map transCode = Postern.getFiberTransmitterCodeMap();
    pageContext.setAttribute("CODE",transCode);
    
    

%>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">光接收机创建</td>
    
  </tr>
   
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 <form name="frmPost" method="post" >
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<input type="hidden" name="func_flag" value="1039" > 
	<input type="hidden" name="Action" value="" > 
	<tr>
          <td class="list_bg2"><div align="right">编号*</div></td>
          <td class="list_bg1"> 
            <input type="text" name="txtFiberReceiverCode" maxlength="20" size="25" value="<tbl:writeparam name="txtFiberReceiverCode" />" >
           </td>      
          
           <td  class="list_bg2"><div align="right">光发机编号*</div></td>
           
           <td class="list_bg1">
           <tbl:select name="txtFiberTransmitterId" set="CODE" match="txtFiberTransmitterId" width="23"/></td>
          
         
        </tr>
      <tr>
        <td class="list_bg2" align="right">所属区域</td>
        <td class="list_bg1" align="left">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
         </td>
          
          
           <td  class="list_bg2"><div align="right">详细地址</div></td>
           <td class="list_bg1">
           <input type="text" name="txtDetailAddress" maxlength="100" size="25" value="<tbl:writeparam name="txtDetailAddress" />" >
         </td>
         </tr>  
         <tr>
          <td class="list_bg2"><div align="right">描述信息</div></td>
           <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtDescription" maxlength="100" size="85"   value="<tbl:writeparam name="txtDescription" />" >
          </td>    
          
         
      </tr>  
  </table>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
     <tr>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="fiberreceiver_query.do" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">保&nbsp;存</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
            
         </tr>
        
   </table>   
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
 
   
 
	 
    </td>
  </tr>
</form>  
</table>  
 
