<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
       java.util.*,
     com.dtv.oss.dto.* "%> 


<script language=javascript>
function check_frm()
{
	
 
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
if (check_Blank(document.frmPost.txtToStatus, true, "目标状态"))
		return false;


   return true;
}
function value_modify_submit(){
   
    if (check_frm()){
	    document.frmPost.action="swap_device_status_edit.do";
	    document.frmPost.Action.value="update";
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
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备更换状态管理记录修改</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
  
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >  
 
 <%
     SwapDeviceReason2StatusDTO dto = (SwapDeviceReason2StatusDTO) pageContext.findAttribute("oneline");
                   
    String segmentIDstr=dto.getReasonStrList();
                 String sagmentName ="";
               if(segmentIDstr!=null ){
                 String[] segmentArray=segmentIDstr.split(",");
                  for(int j=0;j<segmentArray.length;j++){
                       Map segmentMap = Postern.getCsiReasonByDevice();
                       String nameValue=(String)segmentMap.get(segmentArray[j]);
                       
                     if(sagmentName=="")
                      sagmentName=nameValue;
                     else 
                       sagmentName=sagmentName+","+nameValue;
                     }
                  if (sagmentName==null)
                     sagmentName="";
                
 }
 
 %>
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
          
          <td class="list_bg2" ><div align="right">流水号</div></td>
          <td class="list_bg1" ><font size="2">                               
              <input type="text" name="txtSeqNo" size="25"  value="<tbl:write name="oneline" property="seqNo"/>" class="textgray" readonly >
              </font></td>
          <td  class="list_bg2" align="right">设备更换原因列表</td>
	   <td class="list_bg1">
	   	<input name="txtReasonStrListValue" type="text" class="text" maxlength="200" size="25" value="<%=sagmentName%>" readonly >
	   	<input name="txtReasonStrList" type="hidden" value="<tbl:write name="oneline"  property="reasonStrList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SWAPDEVICEREASON','txtReasonStrList',document.frmPost.txtReasonStrList.value,'','','')"> 
	   </td>
	   
         </tr>
         <tr>
          <td  class="list_bg2"><div align="right">目标状态*</div></td>
           <td class="list_bg1">
             <d:selcmn name="txtToStatus" mapName="SET_D_DEVICESTATUS" match="oneline:toStatus" width="23" />
            
         </td>	   
         <td  class="list_bg2"><div align="right">状态*</div></td>
           <td class="list_bg1">
            <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23" />
            
         </td>
       </tr>  
	 
        
        
  </table>
    <br>   
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
  <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />">    
 </lgc:bloop>
<input type="hidden" name="func_flag" value="136" >
<input type="hidden" name="Action" value="">
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="swap_device_status_query.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>   
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:value_modify_submit()" class="btn12">保&nbsp; 存</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
     
 
</form>
