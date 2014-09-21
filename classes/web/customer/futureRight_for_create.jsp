<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>
function check_frm()
{
    if (check_Blank(document.frmPost.txtValue, true, "金额*"))
	return false;
    if (check_Blank(document.frmPost.txtLockTodate, true, "期权锁定日期*"))
	return false;
    if (!check_TenDate(document.frmPost.txtLockTodate, true, "期权锁定日期*")) 
        return false;
  	if (check_Blank(document.frmPost.txtRefersheetId, true, "单据号*"))
		return false;
	if (check_Blank(document.frmPost.txtCustomerID, true, "客户ID*"))
		return false;
    return true;
}
function frm_submit()
{
    if (check_frm()) {
	document.frmPost.action ="futureRight_create.do";
	document.frmPost.submit();
    }
}
function back_submit(){
    document.frmPost.action ="futureRight_query_result.do";
    document.frmPost.submit();
}

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">期权创建</td>
  </tr>
</table>
<form name="frmPost" method="post" action="" >      
     <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
        <tr>
          <td width="17%" class="list_bg2" align="right">单据号*</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtRefersheetId" size="25" maxlength="10" value="<tbl:writeparam name="txtRefersheetId" />" class="text"  >
          </td>
          <td width="17%" class="list_bg2" align="right">客户ID*</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID" />" class="textgray" readonly>
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">金额*</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtValue" size="25" maxlength="10" value="<tbl:writeparam name="txtValue" />" class="text"  >
          </td>
          <td width="17%" class="list_bg2" align="right">期权锁定日期*</td>
          <td width="33%"  class="list_bg1" align="left">
          <d:datetime name="txtLockTodate" size="25" match="txtLockTodate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtLockTodate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
          </td> 
         </tr>
         <tr>
          <td width="17%" class="list_bg2" align="right">描述</td>
          <td width="33%"  class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtDescription" size="80" maxlength="100" value="<tbl:writeparam name="txtDescription" />" class="text"  >
          </td>
         </tr>
      </table>
      <BR>  
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>
         	<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>        	
          	<td><input name="Submit" type="button" class="button" value="返    回" onclick="javascript:back_submit()"></td>
		  	<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>		  	
         	<td width="20" ></td>
         	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
          	<td><input name="Submit" type="button" class="button" value="创    建" onclick="javascript:frm_submit()"></td>
		  	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>   	
        </tr>
      </table>
      <input type="hidden" name="func_flag" value="5003">
      <input type="hidden" name="operatFlag" value="Create">
      <input type="hidden" name="confirm_post"  value="true" >
      <tbl:hiddenparameters pass="txtCreateStartDate/txtCreateEndDate/txtExcuteStartDate/txtExcuteEndDate/txtCancelStartDate/txtCancelEndDate" />
      <tbl:generatetoken />
 </form>
           
