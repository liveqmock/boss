<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
 


<script language=javascript>
function check_frm()
{
	
 
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
    if (check_Blank(document.frmPost.txtKey, true, "键"))
		return false;
    if (check_Blank(document.frmPost.txtValue, true, "值"))
		return false;
    if (!check_Num(document.frmPost.txtPriority, true, "优先级"))
		return false;		
   return true;
}
function value_modify_submit(){
   
    if (check_frm()){
	    document.frmPost.action="csi_reason_item_create.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
	 
}
function back_submit(){
	url="csi_reason_item.screen?txtReferSeqNo=<tbl:writeparam name="txtReferSeqNo1"/>";
	document.location.href=url;
} 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">受理单原因配对的明细创建</td>
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
	         <td class="list_bg2">键*</td>
		 <td class="list_bg1"> 
			 <input type="text" name="txtKey" size="25" maxlength="5" value="<tbl:writeparam name="txtKey" />" >
		 </td>
		<td class="list_bg2">值*</td>
		 <td class="list_bg1"> 
			<input type="text"  name="txtValue" size="25" maxlength="25" value="<tbl:writeparam name="txtValue" />" >
		 </td>
	</tr>
	<tr>
	     <td class="list_bg2">优先级</td>
		 <td class="list_bg1"> 
			 <input  type="text"  name="txtPriority" size="25" maxlength="10" value="<tbl:writeparam name="txtPriority" />" >
		 </td>
		 <td class="list_bg2">是否为默认值</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtDefaultValueFlag" mapName="SET_G_YESNOFLAG" match="txtDefaultValueFlag" width="23" />
		 </td>
	</tr>
	<tr>
	       
		<td class="list_bg2">状态*</td>
		 <td class="list_bg1" colspan="3"> 
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		 </td>
	</tr>
        
        
  </table>
    <br>   
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
 
<input type="hidden" name="func_flag" value="134" >
<input type="hidden" name="Action" value="">
<input type="hidden" name="txtReferSeqNo" value="<tbl:writeparam name="txtReferSeqNo1" />" >
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
          
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>   
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:value_modify_submit()" class="btn12">保&nbsp; 存</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
     
 
</form>
