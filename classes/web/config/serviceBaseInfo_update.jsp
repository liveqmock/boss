<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>

<script language="JavaScript" >
  function back_submit(){
      document.location.href= "config_serviceInfo_query.do";
  }
  
  function relation_submit(){
      document.frmPost.action ="config_serviceRelation_query.do";
      document.frmPost.submit();
  }
 
  function check_frm(){
     if (check_Blank(document.frmPost.txtServiceName, true, "业务名称"))
	return false;
     if (check_Blank(document.frmPost.txtStatus, true, "状态"))
	return false;
     if (check_Blank(document.frmPost.txtDateFrom, true, "有效期起始"))
	return false;
     if (!check_TenDate(document.frmPost.txtDateFrom, true, "有效期起始")) 
        return false;
     if (check_Blank(document.frmPost.txtDateTo, true, "有效期截止"))
	return false;
     if (!check_TenDate(document.frmPost.txtDateTo, true, "有效期截止")) 
        return false;
     if(replaceStr(document.frmPost.txtDateFrom.value,"0") > replaceStr(document.frmPost.txtDateTo.value,"0")){
	alert("有效期截止不能小于有效期起始的时间！");
	return false;
     }       
     return true;      
  }
  
  function update_submit(){
      if (check_frm()){
      	 document.frmPost.ActionFlag.value="update_service";
         document.frmPost.action ="config_serviceInfo_update.do";
         document.frmPost.submit();
      }
  }
  
function del_submit(){
   document.frmPost.ActionFlag.value="del_service";
   var actionUrl ="config_serviceInfo_del.do?txtServiceIds=";
   actionUrl+=document.frmPost.txtServiceID.value;
   document.frmPost.action=actionUrl;   
   document.frmPost.submit();
}

</script>

<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
   <table  border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
       <td><img src="img/list_tit.gif" width="19" height="19"></td>
       <td  class="title">业务操作--信息更新</td>
     </tr>
   </table>
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
   </table>
   <br>
   <form name="frmPost" method="post" action="" >
     <input type="hidden" name="txtServiceID" value ="<tbl:writeparam name="txtServiceID" />" > 
     <input type="hidden" name="txtDtLastmod" value ="<tbl:writeparam name="txtDtLastmod" />" >
     <input type="hidden" name ="ActionFlag" value ="update_service" >
     <input type="hidden" name ="func_flag" value ="20001" >
     <input type="hidden" name="confirm_post"  value="true" >
     <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
           <td  class="list_bg2" align="right">业务名称*</td>
            <td class="list_bg1"> 
                <input type="text" name="txtServiceName" size="25" maxlength="50" value="<tbl:writeparam name="txtServiceName"/>" class="text" >   
            </td>             
           <td  class="list_bg2" align="right">状态*</td>
           <td class="list_bg1"> 
               <d:selcmn name="txtStatus" mapName="SET_P_SERVICESTATUS"  match="txtStatus"  width="23" />
           </td>     
        </tr>
        <tr>
          <td class="list_bg2" align="right"> 有效期起始* </td>
          <td class="list_bg1"><font size="2">
	     <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" maxlength="10" value="<tbl:writeparam name="txtDateFrom" />" class="text" >	     <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td> 
	  </font></td>      
          <td  class="list_bg2" align="right"> 有效期截止*</td> 
          <td class="list_bg1"><font size="2">
	     <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" maxlength="10" value="<tbl:writeparam name="txtDateTo" />" class="text" >	     <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td> 
	  </font></td>
        </tr>
        <tr>
          <td class="list_bg2" align="right"> 业务描述 </td>
          <td class="list_bg1" colspan="3"><font size="2">
              <textarea cols=80 rows=3 name="txtDescription" style="overflow:auto" ><tbl:writeparam name="txtDescription" /></textarea>
          </font></td>
        </tr>
       </table>
       <tbl:generatetoken /> 
    </form>
</td></tr>
</table>
 <BR>  
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
         <td><img src="img/button2_r.gif" width="22" height="20"></td> 
         <td><input name="next" type="button" class="button" onClick="back_submit()" value="返  回"></td>
         <td><img src="img/button2_l.gif" width="11" height="20"></td>
         <td width="20" ></td>  
         <td><img src="img/button_l.gif" width="11" height="20"></td>
         <td><input name="next" type="button" class="button" onClick="update_submit()" value="修  改"></td>
         <td><img src="img/button_r.gif" width="22" height="20"></td> 
           <td width="20" ></td> 
           <!--
         <td><img src="img/button_l.gif" width="11" height="20"></td>
         <td><input name="next" type="button" class="button" onClick="del_submit()" value="删  除"></td>
         <td><img src="img/button_r.gif" width="22" height="20"></td>
         <td width="20" ></td>  
         -->
         <td><img src="img/button_l.gif" width="11" height="20"></td>
         <td><input name="next" type="button" class="button" onClick="relation_submit()" value="业务关系"></td>
         <td><img src="img/button_r.gif" width="22" height="20"></td>
        
                
      </tr>
   </table>	