<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.BrconditionDTO,
                 java.util.*"%>
 

<SCRIPT language="JavaScript">
function check_frm ()
{
    if (check_Blank(document.frmPost.txtEventClassID, true, "事件名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

   if (check_Blank(document.frmPost.txtConditionID, true, "条件名称"))
		return false;
   if (check_Blank(document.frmPost.txtCommandName, true, "LDAP命令名称"))
		return false;
		
   if (check_Blank(document.frmPost.txtPriority, true, "优先级"))
		return false;	
	    		    	    
	    
	return true;
}

function edit_submit(){
 if (window.confirm('您确认要修改LDAP事件命令映射关系吗?')){
  if (check_frm()){
	    document.frmPost.action="ldap_eventcmdmap_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
}
}
 
  
 

</SCRIPT>

<form name="frmPost" method="post">
	 
	
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="228" >
	 
	<!-- 定义当前操作 -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">LDAP事件命令映射关系维护</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

<table width="100%" align="center" border="0" cellspacing="1" cellpadding="3">
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
	<%
	pageContext.setAttribute("allSysyemEvent",Postern.getAllSystemEvent());
	 pageContext.setAttribute("allCondName",Postern.getCondMap());
	  pageContext.setAttribute("allCommandName",Postern.getAllCommandName());
	
%> 
 
	<tr>
	          <td  class="list_bg2"><div align="right">记录ID</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtMapID" size="25"  value="<tbl:write name="oneline" property="mapID" />" class="textgray" readonly >
                  </td>       
		 <td class="list_bg2"><div align="right">事件名称*</div></td>
                 <td class="list_bg1" align="left">
                  <tbl:select name="txtEventClassID" set="allSysyemEvent" match="oneline:eventClassID" width="23" />
                  </td>       
                  
	</tr> 
	 <tr>
		<td  class="list_bg2"><div align="right">条件名称*</div></td>         
                <td class="list_bg1" align="left">
                 <tbl:select name="txtConditionID" set="allCondName" match="oneline:conditionID" width="23" />
                 
                </td> 
                 <td  class="list_bg2"><div align="right">LDAP命令名称*</div></td>         
                 <td class="list_bg1" align="left">
                  <tbl:select name="txtCommandName" set="allCommandName" match="oneline:commandName" width="23" />
                 
                 </td>        
       </tr>
	 <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
	 <tr>
		<td  class="list_bg2"><div align="right">优先级*</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txtPriority" size="25"  value="<tbl:write name="oneline" property="priority" />">
                 
                </td> 
                 <td  class="list_bg2"><div align="right">状态*</div></td>         
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23"/>
                 </td>        
       </tr>
       
	 
	 
	  </lgc:bloop>  
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
          <td><img src="img/button2_r.gif" width="22" height="20"></td>  
         
            <td background="img/button_bg.gif"><a href="<bk:backurl property="ldap_eventcmdmap_query.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" width="13" height="20"></td>
            
            <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:edit_submit()" class="btn12">保&nbsp;存</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
         </tr>
      </table>	

</form>
