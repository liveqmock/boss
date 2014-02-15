<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>
 <%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator" %>


<script language=javascript>
  var serialCol="";
  
function check_frm()
{
	 
	if (check_Blank(document.frmPost.txtBatchNo, true, "批号"))
		return false;		
	 
	if (check_Blank(document.frmPost.txtDepotID, true, "仓库"))
		return false;		
	 
	if (check_Blank(document.frmPost.txtSerialNoCollection, true, "序列号"))
		return false;	
	if (check_Blank(document.frmPost.txtStatusReason, true, "设备重新入库的原因"))
		return false;		
	return true;
	}	
	 
function register_submit()
{
      if(check_frm())
        
       document.frmPost.submit();
}
 
function mutiSerialNo(mySerialNo){
 if(mySerialNo.indexOf(document.frmPost.txtSerialNo.value)!=-1)
 {
   alert("该序列号码已经扫描过");
   return false;
   }
   else
  return true;
   
}

function NameReturnClick(){     
      
      if (event.keyCode==13&& document.frmPost.txtSerialNo.value!="") {
          lenth=document.frmPost.txtSerialNo.value;
          if(mutiSerialNo(serialCol)){ 
          serialCol+=lenth+"\n";
         
          document.frmPost.txtSerialNoCollection.value=serialCol;
          document.frmPost.txtSerialNo.value="";
          }
         
        }  
      
}
 
</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr> 
          <td width="100%"><div align="center">     
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>  
</div>

 
 
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备回库信息录入</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
  
<form name="frmPost"  method="post" action="device_return_first.do">    
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
  <%
    	OperatorDTO operDto = CurrentLogonOperator.getOperator(request);
	String currentOrgType=Postern.getOrganizationTypeByID(operDto.getOrgID());
	Map mapDepots=null;
	//if(!CommonConstDefinition.ORGANIZATIONORGTYPE_GENERALCOMPANY.equals(currentOrgType)){
    	//	mapDepots = Postern.getDepotByOwnerID(operDto.getOrgID());
    	//}else{
    		mapDepots = Postern.getAllDepot();
    	//}
    	pageContext.setAttribute("AllDepots",mapDepots);
   
    
%>
   <tr> 
    <td class="list_bg2" align="right">操作批号*</td>
    <td class="list_bg1">
      <input type="text" name="txtBatchNo" size="25"  value="<tbl:writeparam name="txtBatchNo" />" >
    </td>
    <td class="list_bg2"><div align="right">仓库*</div></td>
      <td class="list_bg1">  
      <tbl:select name="txtDepotID" set="AllDepots" match="txtDepotID" width="25" />   
    </td>
   </tr>    
   <tr>
				<td class="list_bg2">
				<div align="right">设备重新入库的原因*</div></td>
				<td class="list_bg1" colspan="3">
				 <d:selcmn name="txtStatusReason" mapName="SET_D_STOCKINREASON" match="txtStatusReason" width="25" /> 
				 </td>
				 
				 
 </tr>
    <tr>
	 <td class="list_bg2"><div align="right">序列号扫描框</div></td>
	 <td class="list_bg1" colspan="3" align="left">
	   <input type="text" name="txtSerialNo" size="25" value="<tbl:writeparam name="txtSerialNo" />" onkeydown="NameReturnClick()">
	    <font size="2" color="red">
	   &nbsp;&nbsp;扫描后按回车
	   </font>
          </td>	
          
      </tr> 
       
      <tr>
	 <td class="list_bg2"><div align="right">序列号列表*</td>
	 <td class="list_bg1" colspan="3" align="left">
	 <div id="modelFieldNameDesc"
					style="color:red">序列号以“回车”隔开</div>
	 <textarea name="txtSerialNoCollection"  length="5" cols=80 rows=9><tbl:writeSpeCharParam name="txtSerialNoCollection" /></textarea>
         </td>		
      </tr>
      <tr>
      <td class="list_bg2"><div align="right">备注</div></td>
        <td class="list_bg1" colspan="3">
        <input type="text" name="txtComments" size="86" maxlength="2000" value="<tbl:writeparam name="txtComments" />" >          
      </td>
     </tr>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="录 入" onclick="javascript:register_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
	  
  </tr>
  </table>   
     
  
  <input type="hidden" name="seriallength"  value="">
  <input type="hidden" name="confirm_post"  value="false">
 
</form> 
    </td>
  </tr>
  
  
</table>   
 

     
      
      
      
      
      