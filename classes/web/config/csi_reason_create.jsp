<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
 


<script language=javascript>
function check_frm()
{
	       if (check_Blank(document.frmPost.txtDisplayName, true, "显示内容"))
		return false;
               if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
		if (check_Blank(document.frmPost.txtCsiType, true, "受理单类型"))
		return false;
		if (check_Blank(document.frmPost.txtAction, true, "处理动作"))
		return false;
    
              if (check_Blank(document.frmPost.txtCanEmptyFlag, true, "是否可选"))
		return false;

   return true;
}
function value_modify_submit(){
   
    if (check_frm()){
	    document.frmPost.action="csi_reason_create.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
	 
}
function back_submit(){
	url="<bk:backurl property='csi_reason_query.do' />";
	if(url=="")
		url="csi_reason_query.do?txtFrom=1&txtTo=10";
    document.location.href= url;

}

 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建受理单类型原因配置</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
  
      
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	         <td class="list_bg2">显示内容*</td>
		 <td class="list_bg1"> 
			 <input type="text" name="txtDisplayName" size="25" maxlength="25" value="<tbl:writeparam name="txtDisplayName" />" >
		 </td>
		<td class="list_bg2">状态*</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		 </td>
	</tr>
	<tr>
	     <td class="list_bg2">受理单类型*</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtCsiType" mapName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="txtCsiType" width="23" />
		 </td>
		 <td class="list_bg2">处理动作*</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtAction" mapName="SET_V_CSIPROCESSLOGACTION" match="txtAction" width="23" />
		 </td>
	</tr>
	<tr>
	       
		 <td class="list_bg2">是否可选*</td>
		 <td class="list_bg1" colspan="3"> 
			 <d:selcmn name="txtCanEmptyFlag" mapName="SET_G_YESNOFLAG" match="txtCanEmptyFlag" width="23" />
		 </td>	
	</tr>
        
        
  </table>
    <br>   
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
 
<input type="hidden" name="func_flag" value="133" >
<input type="hidden" name="Action" value="">
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返  回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:value_modify_submit()" class="btn12">保&nbsp; 存</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
     
 
</form>
