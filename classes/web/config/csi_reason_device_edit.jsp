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

   return true;
}
function value_modify_submit(){
   
    if (check_frm()){
	    document.frmPost.action="csi_reason_device_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	 
}
function ChangeSubType(){    
    document.FrameUS.submit_update_select('configcsireason', document.frmPost.txtCsiType.value, 'txtCsiCreateReason', '');   
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
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
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
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
 
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备用途记录修改</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
  
   <%
     String csiType1=request.getParameter("txtCsiType");
      
     
    Map reasonMap = Postern.getCsiReasonByType(csiType1);
    pageContext.setAttribute("REASONMAP",reasonMap);
     
%>   
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 <%
  CsiTypeReason2DeviceDTO dto = (CsiTypeReason2DeviceDTO) pageContext.findAttribute("oneline");
  String custTypeList = dto.getReferPurpose();
         String totalValue="";
         System.out.println("the ReferPurpose is "+ custTypeList);
                 if(custTypeList!=null)
                 { 
                     String[] custArray=custTypeList.split(",");
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueListByNameKey("SET_D_DEVICEUSEFORPURPOSE",custArray[j]);
                     if(totalValue=="")
                      totalValue=value;
                     else 
                       totalValue=totalValue+","+value;
                     }
                     
                 }
 
 %> 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr>
	       
		 <td class="list_bg2"><div align="right">流水号</div></td>
		 <td class="list_bg1"> 
			 <input type="text" name="txtSeqNo" size="23"  class="textgray" readonly value="<tbl:write name="oneline" property="seqNo"/>">
		 </td>	
		  <td  class="list_bg2"><div align="right">状态*</div></td>
                  <td class="list_bg1">
            <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23" />
	</tr>
        
        <tr>
          <td class="list_bg2"><div align="right">受理单类型</div></td>
          <td class="list_bg1"> 
            <d:selcmn name="txtCsiType" mapName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="oneline:CsiType" width="25" onchange="ChangeSubType()"/> 
           </td>
            <td  class="list_bg2"><div align="right">受理原因</div></td>
           <td class="list_bg1">
           	 
           <tbl:select name="txtCsiCreateReason" set="REASONMAP" match="oneline:csiCreateReason" width="23"  />
         </td>      
         </tr>
          <tr>   
		 <td  class="list_bg2" align="right">设备用途</td>
	         <td class="list_bg1" colspan="3">
	   	<input name="txtReferPurposeListValue" type="text" class="text" maxlength="200" size="83" value="<%=totalValue%>" readonly >
	   	<input name="txtReferPurposeList" type="hidden" value="<tbl:write name="oneline"  property="referPurpose"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_D_DEVICEUSEFORPURPOSE','txtReferPurposeList',document.frmPost.txtReferPurposeList.value,'','','')"> 
	   </td>	   
             
           </tr>
          
            
         
     
	<tr>
	       
		 <td class="list_bg2"><div align="right">描述</div></td>
		 <td class="list_bg1" colspan="3"> 
			 <input type="text" name="txtComments" size="83" maxlength="500" value="<tbl:write name="oneline" property="comments"/>">
		 </td>	
	</tr>
        
        
  </table>
    <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />">
   </lgc:bloop>
    <br>   
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
 
<input type="hidden" name="func_flag" value="135" >
<input type="hidden" name="Action" value="">
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="csi_reason_device_query.do?txtFrom=1&txtTo=10" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>   
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:value_modify_submit()" class="btn12">保&nbsp; 存</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
     
 
</form>
