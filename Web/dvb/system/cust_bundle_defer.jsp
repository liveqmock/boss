<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO,
		com.dtv.oss.dto.AccountDTO" %>
<%@ page import="com.dtv.oss.util.Postern,
		java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<Script language=JavaScript>
<!--
function check_frm(){
	if (check_Blank(document.frmPost.txtEndDate, true, "新套餐有效期(结束)"))
		return false;
		
	if (check_Blank(document.frmPost.txtComments, true, "备注"))
		return false;
		
	return true;
}

function bundle_defer()
{
	if(check_frm()){
		if(confirm("确定要延期客户套餐？")){
			document.frmPost.txtActionType.value="bundleDefer";
			document.frmPost.submit();
		}
	}
}
//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户套餐延期</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<% 
int accountID=Integer.parseInt(request.getParameter("txtAccoutID"));
String accountName=((AccountDTO)(Postern.getAccountDto(accountID))).getAccountName();
int  ccId =WebUtil.StringToInt(request.getParameter("txtCcID"));
CustomerCampaignDTO custCampaignDto =Postern.getCustomerCampaignByccID(ccId);
%>
<form name="frmPost" method="post" action="cust_bundle_defer_op.do" >
<input type="hidden" name="txtActionType" value="" >
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">客户套餐基本信息</font></td>
     </tr>
  </table>

      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
          <tr>
          <td class="list_bg2" align="right" width="17%">促销套餐ID</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25"  value="<tbl:writeparam name="txtCcID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">套餐名称</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtCampaignName" size="25"  value="<tbl:writeparam name="txtCampaignName"/>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right" >创建时间</td>
          <td class="list_bg1"  align="left" >
             <input type="text" name="txtDtCreate" size="25"  value="<tbl:writeparam name="txtDtCreate"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">状态</td>
          <td class="list_bg1"  align="left" >
          	<input type="text" name="txtStatus" size="25" value="<tbl:writeparam name="txtStatus"/>" class="textgray" readonly >    
          </td>
         </tr>
        <tr>
          <td class="list_bg2" align="right">账户名</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutName" size="25"  value="<%=accountName%>" class="textgray" readonly >
          <input type="hidden" name="txtAccoutID" value="<tbl:writeparam name="txtAccoutID"/>" >
          </td>
          <td class="list_bg2" align="right">业务账户ID</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:writeparam name="txtServiceAccoutID"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">协议期（开始）</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">协议期（结束）</td>
          <td class="list_bg1"  align="left">
            <input type="text" name="txtDateTo" size="25"  value="<tbl:writeparam name="txtDateTo"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">套餐付费方式</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtPaymentType" size="25"  value="<tbl:writeparam name="txtPaymentType"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">NID</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtNbrDate" size="25"  value="<tbl:writeparam name="txtNbrDate"/>" class="textgray" readonly >      
          </td>
        </tr>         
      </table>   
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">延期信息</font></td>
     </tr>
  </table>
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">新套餐有效期(结束)*</td>
          <td class="list_bg1"  align="left" >
          	<d:datetime name="txtEndDate" size="25" myClass="text" match="txtEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right" width="17%">备注*</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtComments" size="80"  value="" class="text" >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right" width="17%">老备注信息</td>
          <td class="list_bg1"  align="left" >
          <textarea name="txtOldComments"  length="5" cols=83 readOnly><%=(custCampaignDto.getComments()==null) ? "" :custCampaignDto.getComments()%></textarea>
          </td>
        </tr>
		  </table>
    <BR>  
      
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	  <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_bundle_detail.do" />" class="btn12">返&nbsp;&nbsp;回</a></td> 
	  <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
	  <td width="20" ></td>
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:bundle_defer()" class="btn12">确&nbsp;&nbsp;定</a></td> 
	  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	</tr>
</table>

</form>

