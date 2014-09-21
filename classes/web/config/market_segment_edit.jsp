<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>  
<%@ page import="com.dtv.oss.util.Postern" %>  
<%@ page import="com.dtv.oss.dto.MarketSegmentDTO" %>
 

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
function segment_modify_submit(){
  if (window.confirm('您确认要修改该市场分区吗?')){
    if (check_frm()){
	    document.frmPost.action="segment_edit_done.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	}
}
function process_district_submit(strId)
{
	self.location.href="query_district.do?txtMarketSegmentID="+strId;
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
    <td class="title">市场分区详细信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
  
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
<%
        MarketSegmentDTO dto = (MarketSegmentDTO) pageContext.findAttribute("oneline");
                 int segmentId = dto.getId();
                 List districtList=Postern.getDistrictIDListByID(segmentId);
                 String sagmentName ="";
                 String districtStr="";
                 Map districtMap = Postern.getAllDistrictName();
                
                 if(districtList!=null){
                  Iterator disIte = districtList.iterator();
		      while (disIte.hasNext()) {
			Integer disID = (Integer) disIte.next();
			districtStr=districtStr+disID+",";
			
			String nameValue=(String)districtMap.get(disID.toString());
			 
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
	        <td class="list_bg2" align="right">分区ID</td>
		 <td class="list_bg1">
			<input type="text" name="txtID" size="25"  value="<tbl:write name="oneline" property="Id" />" class="textgray" readonly >
		 </td>
		<td class="list_bg2" align="right">分区名称*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtName" size="25" maxlength="50"  value="<tbl:write name="oneline" property="name" />" >
		</td>
	</tr>
	  
	<tr>
		<td class="list_bg2" align="right">状态*</td>
		 <td class="list_bg1" colspan="3">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="25" />
		 </td>
		
		 
	</tr>
        
       <tr>   
		<td class="list_bg2" align="right">描述*</td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="83"  maxlength="100" value="<tbl:write name="oneline" property="description" />" >
		</td>
	</tr>
	 <tr> 
	 
	     <td class="list_bg2"><div align="right">对应的行政区域</div></td>
            <td class="list_bg1" colspan="3">     
         <input type="hidden" name="txtDistrictList" value="<%=districtStr%>">
	    <input type="text" name="txtDistrictListValue" size="80" maxlength="50" readonly value="<%=sagmentName%>" class="text">
      <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_districts('S,T,P','txtDistrictList','txtDistrictListValue','checkbox')">
   </td>
             
         </tr>
  </table>
  <!--
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr> 
	      <td colspan="4" class="import_tit" align="center"><font size="3">对应的行政区域</font></td>
      </tr>
       <tr class="list_head">
         <td class="list_head">行政区域ID</td>
           <td class="list_head">行政区域</td>
        </tr>
      <%
          int id=WebUtil.StringToInt(request.getParameter("txtID"));
          List col=Postern.getDistrictIDListByID(id);
          if(col!=null){
            Iterator valueIter=col.iterator();
		while(valueIter.hasNext()){
                Integer districtId=(Integer) valueIter.next();
                 String name = Postern.getStrictNameByID(districtId.intValue()); 
          
          
          
      %>
      <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     
      	     
      	     <td align="center"><%=districtId%></td>
      	     <td align="center"><%=name%></td>
      	     			
      	    
    	</tr> 
      <%}}%>
      
      </table>
      -->
<input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
 
<input type="hidden" name="func_flag" value="5071" >
<input type="hidden" name="Action" value="">
 
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      <br>
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="menu_market_segment.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>
            
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:segment_modify_submit()" class="btn12">修&nbsp; 改</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
          <!--
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:process_district_submit('<tbl:write name="oneline" property="Id"/>')" class="btn12">维护区域信息</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          -->
	  
        </tr>
      </table>   
     
        
</lgc:bloop>   
</form>
