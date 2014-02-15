<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtName, true, "配置名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
    if (check_Blank(document.frmPost.txtConfigType, true, "配置信息类型"))
	    return false;
    
    if (check_Blank(document.frmPost.txtAllowNull, true, "允许为空"))
	    return false; 
    if (check_Blank(document.frmPost.txtValueType, true, "取值类型"))
	    return false; 
	    		
	 

	return true;
}
 
function config_create_submit(){
  if (check_frm()){
	    document.frmPost.action="create_config.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
}
function ChangeSubType()
{
    
    document.FrameUS.submit_update_select('configsubtype', document.frmPost.txtConfigType.value, 'txtConfigSubType', '');
    
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
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建二维配置信息</td>
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
 <%    
     Map nameMap = null; 
     nameMap=Postern.getAllService();
     pageContext.setAttribute("SERVNAME",nameMap);
 %>
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr> 
          <td  class="list_bg2"><div align="right">配置信息类型*</div></td>         
          <td class="list_bg1" align="left">
           <d:selcmn name="txtConfigType" mapName="SET_C_BIDIMCONFIGSETTINGCONFIGTYPE" match="txtConfigType" width="23" onchange="ChangeSubType()"/>
           </td>      
           <td class="list_bg2"><div align="right">配置信息子类型</div></td>
	         <td class="list_bg1" align="left">
	            <select name="txtConfigSubType"><option value="" >-----------------------</option></select>	
           </td>
      </tr>
      <tr>
	       <td class="list_bg2">配置名称</td>
		<td class="list_bg1">
		  <input type="text" name="txtName" size="25" value="<tbl:writeparam name="txtName" />" >
		 </td>
		 <td class="list_bg2">状态*</td>
		 <td class="list_bg1">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		 </td>
	</tr>
	 <tr>
		<td class="list_bg2">业务类型</td>
		 <td class="list_bg1">
		         <tbl:select name="txtServiceID" set="SERVNAME" match="txtServiceID" width="23" />   
			 
		 </td>
		
		<td class="list_bg2">取值类型*</td>
		 <td class="list_bg1">
		  <d:selcmn name="txtValueType" mapName="SET_C_BIDIMCONFIGSETTINGVALUETYPE" match="txtValueType" width="23" />
		  </td>
		
	</tr>
       <tr>
		 
		<td class="list_bg2">允许为空*</td>
		<td class="list_bg1" colspan="3">
		 <d:selcmn name="txtAllowNull" mapName="SET_G_YESNOFLAG" match="txtAllowNull" width="23" />
		</td>
	</tr>
          
	 <tr>   
		<td class="list_bg2">描述</td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="80"  maxlength="120" value="<tbl:writeparam name="txtDescription" />" >
		</td>
	</tr>
	  
      
	 
        
        
      
  </table>
 <input type="hidden" name="func_flag" value="5097" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="confirm" value ="true"/>
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:config_create_submit()" class="btn12">确&nbsp;认</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
            <td width="20" ></td>
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
   
</form>
