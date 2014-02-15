<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import=" com.dtv.oss.util.Postern ,
                  java.util.* " %>
<script language=javascript>
<!--
function callback_save_submit()
{
	if(check_select()){
		document.frmPost.txtSaveStates.value="true"
		document.frmPost.submit();
	}
}

function callback_transient_submit()
{
	if(check_select()){
		document.frmPost.txtSaveStates.value="false"
		document.frmPost.submit();
	}
}

function check_select(){
	if (!check_Bidimconfig()) return false;
	return true;
}
//-->
</script>
<form name="frmPost" method="post" action="callback_create_op.do" >
      <input type="hidden" name="txtSaveStates" value=""/>
      <table  border="0" align="center" cellpadding="0" cellspacing="5">
       <tr>
         <td align="center" class="title1" colspan="4">
           <img src="img/list_tit.gif" width="19" height="19"><font size="4"><strong>&nbsp;&nbsp;录入回访信息</strong></font>
         </td>
       </tr>
      </table>
      <br>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
     </table>
     <br>
     <table width="820"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
         <td valign="middle" class="list_bg2" align="right" width="17%" >受理单编号</td>
         <td width="33%" class="list_bg1"><font size="2">
            <input type="text" name="txtID" size="25"  value="<tbl:writeparam name="txtID" />" class="textgray" readonly >
         </font></td>
         <td valign="middle" class="list_bg2" align="right" width="17%" >类型</td>
         <td width="33%" class="list_bg1"><font size="2">
           <input type="text" name="txtTypeShow" size="25"  value="<tbl:writeparam name="txtTypeShow" />" class="textgray" readonly   >
         </font></td>
       </tr>
       <tr>
	       <td valign="middle" class="list_bg2" align="right" >联系人</td>
	       <td class="list_bg1"><font size="2">
	         <input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />" >
	         <input type="text" name="txtContactPerson" size="25"  value="<tbl:writeparam name="txtContactPerson" />" class="textgray" readonly   >
	       </font></td>
	       <td valign="middle" class="list_bg2" align="right" >性别</td>
	       <td class="list_bg1"><font size="2">
	         <input type="text" name="txtGenderShow" size="25"  value="<tbl:writeparam name="txtGeneder" />" class="textgray" readonly   >
	       </font></td>
       </tr>
       <tr>
	       <td valign="middle" class="list_bg2" align="right" >联系电话</td>
	       <td class="list_bg1" colspan="3"><font size="2">
	         <input type="text" name="txtContactPhone" size="25"  value="<tbl:writeparam name="txtContactPhone" />" class="textgray" readonly   >
	       </font></td>
       </tr>
       <%
          Map mp =new HashMap();
          String txtType =request.getParameter("txtType");
          int txtID = (request.getParameter("txtID")==null) ? 0 : Integer.parseInt(request.getParameter("txtID"));
          if (txtType.equals("UO") || txtType.equals("BK") || txtType.equals("OS") || txtType.equals("OB")){
              mp = Postern.getCallbackInfo("O", txtID);
          }
          pageContext.setAttribute("mp",mp);
       %>
       
       <tbl:dynamicservey serveyType="C"  serveySubType="O" showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" match="mp" defaultFlag="true" checkScricptName ="check_Bidimconfig()" />
        
      </table>
      <br>
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td width="20" ></td>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td background="img/button_bg.gif" ><a href="<bk:backurl property="service_interaction_view.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" width="12" height="20"></td>
           <td width="20" ></td>  
           <td><img src="img/button_l.gif" width="12" height="20"></td>
           <td background="img/button_bg.gif"  ><a href="javascript:callback_save_submit()" class="btn12">完&nbsp;成</a></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>  
           <td width="20" ></td>
           <td><img src="img/button_l.gif" width="12" height="20"></td>
           <td background="img/button_bg.gif"  ><a href="javascript:callback_transient_submit()" class="btn12">回访暂存</a></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>    
       </tr>
      <input type="hidden" name="func_flag" value="500">
      <input type="hidden" name="Action" value="callback">
      <input type="hidden" name="confirm_post"  value="true" >
      <tbl:hiddenparameters pass="txtDtLastmod" />
      <tbl:generatetoken />
</form>