<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>
 <%@ page import="com.dtv.oss.dto.*" %> 
 

<script language=javascript>
function check_frm()
{
	
    if (check_Blank(document.frmPost.txtFiberTransmitterCode, true, "光发射机编号"))
	    return false;
	    if (check_Blank(document.frmPost.txtMachineRoomId, true, "机房名称"))
	    return false;
    
	 
     
	    
     return true;
}
 
 
 
 
function modify_submit(){
 if (window.confirm('您确认要修改该光发射机吗?')){
  if (check_frm()){
	    document.frmPost.action="config_modify_fibertransmitter.do";
	    document.frmPost.Action.value="MODIFY";
	    document.frmPost.submit();
	  }
}
}
 
</script>
 
 
 
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">维护光发射机信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
 
<form name="frmPost" method="post" >   
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
 <%
 
  
    Map receiverCode = Postern.getRoomNameMap();
    pageContext.setAttribute("CODE",receiverCode);
    
      FiberTransmitterDTO dto = (FiberTransmitterDTO) pageContext.getAttribute("oneline");
      int fiberReceiverId=dto.getMachineRoomId();						
      String idStr = fiberReceiverId+"";

%>
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
 
   
       <tr> 
         <td  class="list_bg2"><div align="right">记录ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtID" size="25" value="<tbl:write name="oneline" property="id"/>" class="textgray" readonly>
           </td>  
           <td class="list_bg2"><div align="right">光发射机编号*</div></td>
           <td class="list_bg1" align="left" >
            <input type="text" name="txtFiberTransmitterCode"   size="25"  maxlength="20"  value="<tbl:write name="oneline" property="fiberTransmitterCode"/>" >
          
       </tr>
      <tr>
       <td class="list_bg2" align="right">所属区域</td>
            <td class="list_bg1">
    	    <input type="hidden" name="txtDistrictID" value="<tbl:write name="oneline" property="districtId"/>">
	 
	     <input type="text" name="txtDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo name="oneline" property="districtId" />"  >
             <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtDistrictDesc')">
      </td>
         
          <td class="list_bg2"><div align="right">详细地址</div></td>
           <td class="list_bg1" align="left">
           <input type="text" name="txtDetailAddress"   size="25"   maxlength="100"  value="<tbl:write name="oneline" property="detailAddress"/>" >
           </td>
      </tr>
       <tr>
       <td class="list_bg2" align="right">机房名称*</td>
            <td class="list_bg1" colspan="3">
               <tbl:select name="txtMachineRoomId" set="CODE" match="txtMachineRoomId" width="23" defaultValue="<%=idStr%>"/></td>	
    	  
	    
     
         
        
      </tr>
       <tr>
          <td class="list_bg2"><div align="right">描述信息</div></td>
           <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtDescription" maxlength="100" size="85"   value="<tbl:write name="oneline" property="description"/>">
          </td>    
          
         
      </tr>
 
     
      
      
      
      
         
     
 </table>
 
 <input type="hidden" name="func_flag" value="1041" >
  <input type="hidden" name="Action" value=""/>
   
   <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="fibertransmitter_query.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
            
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:modify_submit()" class="btn12">保&nbsp;存</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
           <td width="20" ></td>
        
        </tr>
      </table>   
  
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
    </lgc:bloop>      
</form>
