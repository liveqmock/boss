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
  if (window.confirm('您确认要修改该配置信息吗?')){
    if (check_frm()){
	    document.frmPost.action="csi_reason_edit_done.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	}
}
 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">受理单类型原因配置修改</td>
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
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        <td class="list_bg2">流水号</td>
		 <td class="list_bg1">
			<input type="text" name="txtSeqNo" size="25"  value="<tbl:write name="oneline" property="seqNo" />" class="textgray" readonly >
		 </td>
		<td class="list_bg2">状态*</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23" />
		 </td>
		 </tr>
	</tr>
	     <td class="list_bg2">受理单类型*</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtCsiType" mapName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="oneline:csiType" width="23" />
		 </td>
		 <td class="list_bg2">处理动作*</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtAction" mapName="SET_V_CSIPROCESSLOGACTION" match="oneline:action" width="23" />
		 </td>
		 </tr>
	<tr>
	       <td class="list_bg2">显示内容*</td>
		 <td class="list_bg1"> 
			 <input type="text" name="txtDisplayName" size="25" maxlength="25" value="<tbl:write name="oneline" property="displayName" />" >
		 </td>
		 <td class="list_bg2">是否可选*</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtCanEmptyFlag" mapName="SET_G_YESNOFLAG" match="oneline:canEmptyFlag" width="23" />
		 </td>	
	</tr>
        
        
  </table>
    <br>   
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
<input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
 
<input type="hidden" name="func_flag" value="133" >
<input type="hidden" name="Action" value="">
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="csi_reason_query.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>   
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:value_modify_submit()" class="btn12">保&nbsp; 存</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
     
        
</lgc:bloop>   
</form>
