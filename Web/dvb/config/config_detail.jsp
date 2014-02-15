<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.BidimConfigSettingDTO" %>
 

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.dto.BidimConfigSettingValueDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtName, true, "配置名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

	 
		
	if (check_Blank(document.frmPost.txtValueType, true, "取值类型"))
		return false;
	 
	if (check_Blank(document.frmPost.txtAllowNull, true, "允许为空"))
		return false;
        
     		
	 

	return true;
}
function config_modify_submit(){
  if (window.confirm('您确认要修改该配置信息吗?')){
    if (check_frm()){
	    document.frmPost.action="config_edit_done.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	}
}
function process_item_submit(strId)
{
	self.location.href="query_config_item.do?txtSettingID="+strId;
} 
function changePrefed(){
   if (document.frmPost.txtValueType.value =="M"){
       
       document.all("prefered").style.display ="none";
      
   } else{
      document.all("prefered").style.display ="";
   }
 
}
 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">配置详细信息</td>
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
    String status=null;
    String valueType = null;
  %>  
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
 <%
     BidimConfigSettingDTO dto = (BidimConfigSettingDTO) pageContext.findAttribute("oneline");
     status = dto.getStatus();
     valueType = dto.getValueType();
     Map nameMap = null;
     nameMap=Postern.getAllService();
     int id = dto.getId();
     pageContext.setAttribute("SERVNAME",nameMap);
     
 %>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        <td class="list_bg2">配置ID</td>
		 <td class="list_bg1">
			<input type="text" name="txtID" size="25"  value="<tbl:write name="oneline" property="Id" />" class="textgray" readonly >
		 </td>
		<td class="list_bg2">配置名称*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtName" size="25"  value="<tbl:write name="oneline" property="name" />" >
		</td>
	</tr>
	  
	<tr>
		<td class="list_bg2">业务类型</td>
		 <td class="list_bg1">
		         <tbl:select name="txtServiceId" set="SERVNAME" match="oneline:serviceId" width="23" />   
			 
		 </td>
		
		<td class="list_bg2">取值类型*</td>
		 <td class="list_bg1">
		  <d:selcmn name="txtValueType" mapName="SET_C_BIDIMCONFIGSETTINGVALUETYPE" match="oneline:valueType" width="23" disabled="true" onchange="changePrefed()"/>
		  </td>
		
	</tr>
        <tr>
		<td class="list_bg2">状态*</td>
		<td class="list_bg1">
		 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="23" />
		</td>
		<td class="list_bg2">允许为空*</td>
		<td class="list_bg1">
		 <d:selcmn name="txtAllowNull" mapName="SET_G_YESNOFLAG" match="oneline:allowNull" width="23" />
		</td>
	</tr>
       <tr>   
		<td class="list_bg2">描述</td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="80"  maxlength="120" value="<tbl:write name="oneline" property="description" />" >
		</td>
	</tr>
  </table>
    <% 
            if("S".equals(valueType)){
          %>
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr> 
	      <td colspan="4" class="import_tit" align="center"><font size="3">配置选项值</font></td>
      </tr>
       <tr class="list_head">
          <td class="list_head">取值代码</td>
           <td class="list_head">取值描述</td>
           <td class="list_head">状态</td>
        </tr>
      <%
         
          Collection col=Postern.getAllValusDtoByID(id);
          if(col!=null && !col.isEmpty()){
            Iterator valueIter=col.iterator();
		while(valueIter.hasNext()){
                BidimConfigSettingValueDTO valueDto=(BidimConfigSettingValueDTO) valueIter.next();
                 pageContext.setAttribute("detailLine",valueDto);
          
          
          
      %>
      <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     
      	     
      	     <td align="center"><tbl:write name="detailLine" property="code"/></td>
      	     <td align="center"><tbl:write name="detailLine" property="description"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="detailLine:Status"/></td>  
      	     			
      	    
    	</tr>
      <%}}}%>
      </table>
<input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
<input type="hidden" name="func_flag" value="5059" >
<input type="hidden" name="Action" value="">
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:config_modify_submit()" class="btn12">修&nbsp; 改</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
          <% 
            if("V".equals(status) && "S".equals(valueType)){
          %>
          <td id ="prefered">
         <table align="center" border="0" cellspacing="0" cellpadding="0">
          <tr>
           
          <td><img src="img/button_l.gif" border="0" ></td>  
          <td background="img/button_bg.gif">  <a href="javascript:process_item_submit('<tbl:write name="oneline" property="Id"/>')" class="btn12">维护配置选项</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
         
          </tr></table></td>
            <%}%>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="config_query_result.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          
	
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
