<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
 
<%@ page import="com.dtv.oss.dto.UserPointsExchangeActivityDTO" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtName, true, "�һ���Ʒ����"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
		return false;

	if (check_Blank(document.frmPost.txtAmount, true, "����")||!check_Num(document.frmPost.txtAmount, true, "����"))
		return false;

	return true;
}
function goods_modify_submit(){
    if (check_frm()){
  if (window.confirm('��ȷ��Ҫ�޸ĸû�����Ϣ��?')){
	    document.frmPost.action="goods_edit_done.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	}
}
function process_goods_submit(strId)
{
	self.location.href="query_points_goods.do?txtActivityID="+strId;
} 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">���ֶһ���Ʒ��ϸ��Ϣ</td>
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
	        <td class="list_bg2" align="right">�һ���ƷID</td>
		 <td class="list_bg1">
			<input type="text" name="txtID" size="25"  value="<tbl:write name="oneline" property="Id" />" class="textgray" readonly >
		 </td>
		<td class="list_bg2" align="right">�һ���Ʒ����*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtName" size="25"  value="<tbl:write name="oneline" property="name" />" >
		</td>
	</tr>
	  
	<tr>
		<td class="list_bg2" align="right">״̬*</td>
		 <td class="list_bg1">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="23" />
		 </td>
		
		<td class="list_bg2" align="right">��Ʒ����*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtAmount" size="25"  value="<tbl:write name="oneline" property="amount" />" >
		</td>
	</tr>
        
       <tr>   
		<td class="list_bg2" align="right">����</td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="80"  maxlength="120" value="<tbl:write name="oneline" property="descr" />" >
		</td>
	</tr>
  </table>
<input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
<input type="hidden" name="txtActivityID"   value="<tbl:write name="oneline" property="ActivityId" />">
<input type="hidden" name="func_flag" value="5053" >
<input type="hidden" name="Action" value="">
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"> 
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="query_points_goods.do" />" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>         	
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:goods_modify_submit()" class="btn12">��&nbsp; ��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
</td>
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
