<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
com.dtv.oss.web.util.WebUtil,
       java.util.Iterator,
     com.dtv.oss.dto.* "%> 


<script language=javascript>
function check_frm()
{
	
 
    
    if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
		return false;
    if (check_Blank(document.frmPost.txtKey, true, "��"))
		return false;
    if (check_Blank(document.frmPost.txtValue, true, "ֵ"))
		return false;
    if (!check_Num(document.frmPost.txtPriority, true, "���ȼ�"))
		return false;		
   return true;
}
function value_modify_submit(){
   
    if (check_frm()){
	    document.frmPost.action="csi_reason_item_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	 
}
function back_submit(){
         ss=document.frmPost.txtReferSeqNo1.value;
         
	url="csi_reason_item.screen?txtReferSeqNo="+ss;
	document.location.href=url;
} 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">����ԭ����Ե���ϸ�޸�</td>
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
     String seqNo =  request.getParameter("txtSeqNo");
     CsiActionReasonDetailDTO dto= Postern.getCsiActionReasonDetailDTO(WebUtil.StringToInt(seqNo));
     int referSeqNo=dto.getReferSeqNo();
     pageContext.setAttribute("oneline",dto);
  %>
      
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	         <td class="list_bg2">��ˮ��</td>
		 <td class="list_bg1"> 
		         <input type="text" name="txtSeqNo" size="25" maxlength="25" value="<tbl:write name="oneline" property="seqNo" />" class="textgray" readonly >
			 
		 </td>
		<td class="list_bg2">ͷ��¼ID</td>
		 <td class="list_bg1"> 
			<input type="text"  name="txtReferSeqNo" size="25" maxlength="25" value="<tbl:write name="oneline" property="referSeqNo" />" class="textgray" readonly >
		 </td>
		 
	</tr>
	  <tr>
	         <td class="list_bg2">��*</td>
		 <td class="list_bg1"> 
		         <input type="text" name="txtKey" size="25" maxlength="5" value="<tbl:write name="oneline" property="key" />" >
			 
		 </td>
		<td class="list_bg2">ֵ*</td>
		 <td class="list_bg1"> 
			<input type="text"  name="txtValue" size="25" maxlength="25" value="<tbl:write name="oneline" property="value" />"  >
		 </td>
	</tr>
	<tr>
	     <td class="list_bg2">���ȼ�</td>
		 <td class="list_bg1"> 
			 <input  type="text"  name="txtPriority" size="25" maxlength="10" value="<tbl:writenumber  hidezero="true" name="oneline" property="priority" />"  >
		 </td>
		 <td class="list_bg2">�Ƿ�ΪĬ��ֵ</td>
		 <td class="list_bg1"> 
			 <d:selcmn name="txtDefaultValueFlag" mapName="SET_G_YESNOFLAG" match="oneline:defaultValueFlag" width="23" />
		 </td>
	</tr>
	<tr>
	       
		<td class="list_bg2">״̬*</td>
		 <td class="list_bg1" colspan="3"> 
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23" />
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
<input type="hidden" name="txtReferSeqNo1" value="<%=referSeqNo%>" >
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">��&nbsp;��</a></td>
          
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>   
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:value_modify_submit()" class="btn12">��&nbsp; ��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
     
 
</form>
