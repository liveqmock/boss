<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
 
 

<script language=javascript>
function check_frm()
{
	
  if (check_Blank(document.frmPost.txtName, true, "��������"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
		return false;
   if (check_Blank(document.frmPost.txtAmount, true, "����")||!check_Num(document.frmPost.txtAmount, true, "����"))
		return false;
	 
	 return true;
}
function goods_create_submit(){
   
    if (check_frm()){
	    document.frmPost.action="create_poin_goods.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
	}
 
 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�������ֶһ���Ʒ��Ϣ</td>
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
      
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        <td class="list_bg2" align="right">��Ʒ����*</td>
		 <td class="list_bg1">
		        <input type="text" name="txtName" size="25" value="<tbl:writeparam name="txtName" />" >
			 
		 </td>
		<td class="list_bg2" align="right">��Ʒ����*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtAmount" size="25"   value="<tbl:writeparam name="txtAmount" />" >
		</td>
	</tr>
	 <tr>
		<td class="list_bg2" align="right">״̬*</td>
		<td class="list_bg1" colspan="3">
		 
		 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		</td>
	</tr>
       <tr>   
		<td class="list_bg2" align="right">����</td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="80"  maxlength="120" value="<tbl:writeparam name="txtDescription" />" >
		</td>
	</tr>
  </table>
 
  <input type="hidden" name="func_flag" value="5059" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="confirm" value ="true"/>
  <input type="hidden" name="vartxtActivityID" value="<tbl:writeparam name="txtActivityID" />"/>
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
          <td background="img/button_bg.gif"><a href="javascript:goods_create_submit()" class="btn12">ȷ&nbsp;��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>

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
   <tbl:hiddenparameters pass="txtActivityID" />
</form>
