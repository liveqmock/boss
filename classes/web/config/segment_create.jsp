<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtName, true, "分区名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
		
    if (check_Blank(document.frmPost.txtDescription, true, "描述"))
		return false;

   
    return true;
}
 
function segment_create_submit(){
  if (check_frm()){
	    document.frmPost.action="create_market_segment.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
}
 
  function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}
 
 

 
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建市场分区信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 <tbl:generatetoken />  
      
 
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        
		<td class="list_bg2" align="right">分区名称*</td>
		<td class="list_bg1">
		  <input type="text" name="txtName" maxlength="50" size="25" value="<tbl:writeparam name="txtName" />" >
		 </td>
		 <td class="list_bg2" align="right">分区状态*</td>
		 <td class="list_bg1">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		 </td>
	</tr>
	 
          
	 <tr>   
		<td class="list_bg2" align="right">描述*</td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="80"  maxlength="100" value="<tbl:writeparam name="txtDescription" />" >
		</td>
	</tr>
	 <td class="list_bg2"><div align="right">对应的行政区域</div></td>
            <td class="list_bg1" colspan="3">     
         <input name="txtDistrictListValue" type="text" class="text" readonly maxlength="200" size="80" value="<tbl:writeparam name="txtDistrictListValue"/>">
	   <input name="txtDistrictList" type="hidden" value="<tbl:writeparam name="txtDistrictList"/>">
      <input name="selDistButton" type="button" class="button" value="请选择" onClick="javascript:sel_districts('S,T,P','txtDistrictList','txtDistrictListValue','checkbox')">
   </td>
	 
       	
	 
        
        
      
  </table>
 <input type="hidden" name="func_flag" value="200" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="confirm" value ="true"/>
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        
            
        <tr>
         <td><img src="img/button2_r.gif" width="22" height="20"></td>
             <td background="img/button_bg.gif"  ><a href="<bk:backurl property='menu_market_segment.do' />" class="btn12">返&nbsp;回</a></td>           
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
              <td width="20" ></td>
            
           
           <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td background="img/button_bg.gif"><a href="javascript:segment_create_submit()" class="btn12">确&nbsp;认</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          </tr>
      </table>   
      
        
   
</form>
