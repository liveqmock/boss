<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<form name="frmPost" method="post" action="group_bargain_create_op.do">
   <tbl:generatetoken />  
   <table  border="0" align="center" cellpadding="0" cellspacing="10" >
     <tr>
      <td><img src="img/list_tit.gif" width="19" height="19"></td>
      <td class="title">创建团购券头信息</td>
     </tr>
   </table>
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
     <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
     </tr>
  </table>
  <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
          <td class="list_bg2"><div align="right">团购合同号*</div></td>
          <td colspan="3" class="list_bg1"><font size="2">
          <input type="text" name="txtBargainNo" size="25"  value="<tbl:writeparam name="txtBargainNo"/>" maxlength="50">
          </font></td>
          
        </tr>
         <tr>
            <td class="list_bg2"><div align="right">一次性费预付总额*</div></td>
            <td  class="list_bg1"><font size="2">
          <input type="text" name="txtPrepayImmediateFee" size="25"  value="<tbl:writeparam name="txtPrepayImmediateFee"/>">
          </font></td>
           <td class="list_bg2"><div align="right">预存总额*</div></td>
          <td  class="list_bg1"><font size="2">
          <input type="text" name="txtPrepayDepositFee" size="25"  value="<tbl:writeparam name="txtPrepayDepositFee"/>">
          </font></td>
        </tr>
         <% 
         Map mapSubcompany = new HashMap();
         mapSubcompany = Postern.getParentCompanys();
         pageContext.setAttribute("SubCompany",mapSubcompany);
         Map mopIdMap = Postern.getAllMethodOfPaymentsForMarket();
          pageContext.setAttribute("MethodOfPayment",mopIdMap);
         String campaignID = request.getParameter("txtClassID");
         
         %>
         <tr>
          <td class="list_bg2"><div align="right">发起公司 </div></td>
          <td class="list_bg1">
          <tbl:select name="txtOrgID" set="SubCompany" match="txtOrgID" width="23" onchange="ChangeSubCompany()"/>
          </td>
           <td class="list_bg2"><div align="right">代理商 </div></td>
           <td class="list_bg1">
          <d:selsubproxy name="txtAgentId" parentMatch="txtOrgID" match="txtAgentId" width="23" defaultIndex="1"/>
         </td>  
        </tr>
        <tr>
         <td class="list_bg2"><div align="right">有效期起始时间*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
          <td class="list_bg2"><div align="right">有效期截止时间*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" >          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
        </tr>
        <tr>
          <td class="list_bg2"><div align="right">开户截止日期*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNormalTimeTo)" onblur="lostFocus(this)" type="text" name="txtNormalTimeTo" size="25" value="<tbl:writeparam name="txtNormalTimeTo"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNormalTimeTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
           </td>
          <td class="list_bg2"><div align="right">发放总张数*</div></td>
          <td class="list_bg1">
          <input type="text" name="txtTotalSheets" maxlength="6" size="25" value="<tbl:writeparam name="txtTotalSheets"/>">
          </td>
        </tr>
        <tr>
           <td class="list_bg2"><div align="right">起始编号*</div></td>
           <td class="list_bg1">
          <input type="text" name="txtStartFrom" size="25" maxlength="6" value="<tbl:writeparam name="txtStartFrom"/>">
           </td>
          <td class="list_bg2"><div align="right">团购券编号方式*</div></td>
           <td class="list_bg1">
          <input type="text" name="txtFormat" value="<tbl:writeparam name="txtFormat"/>" size="25" maxlength="50"/>
          	<font color="red">(如'ABC***')</font>
          </td>
          
        </tr>
        <tr>
           <td class="list_bg2"><div align="right">支付方式*</div></td>
           <td class="list_bg1">
           <tbl:select name="txtMopID" set="MethodOfPayment" match="txtMopID" width="23" />
           </td>
          <td class="list_bg2"><div align="right">是否有费用优惠*</div></td>
           <td class="list_bg1">
           <d:selcmn name="txtIsCampaign" mapName="SET_G_YESNOFLAG" match="txtIsCampaign" width="23"/>
          </td>
        </tr>
       
        <tr>
         <td class="list_bg2"><div align="right">描述信息</div></td>
		  <td class="list_bg1" colspan="3">
          <input type="text" name="txtDescription" size="78" value="<tbl:writeparam name="txtDescription"/>" maxlength="200">
          </td>
        </tr>
        
        
       
        
      </table>
      
      <input type="hidden" name="nowdate"/>
      <input type="hidden" name="confirm" value ="true"/>
      <input type="hidden" name="callCommand" value =""/>
      <input type="hidden" name="txtClassID" value="<%=campaignID%>"/>
      <input type="hidden" name="txtGroupBargainClassID" value="<%=campaignID%>" >
      
      
       <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			
			
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="创 建" onclick="javascript:create_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
       </tr>
   </table>   
       
</form>

 

<SCRIPT language="JavaScript">
<!--
 
function ChangeSubCompany()
{
    document.FrameUS.submit_update_select('subagent', document.frmPost.txtOrgID.value, 'txtAgentId', '');
}
function checkLengh(){
  
 var numLength= document.frmPost.txtStartFrom.value.length;
  
 var famat = document.frmPost.txtFormat.value;
 var otherLen = famat.lastIndexOf('*') - famat.indexOf("*") + 1;
 if(numLength!=otherLen){
   alert("团购券编号方式*的个数一定要跟起始编号的长度相等")
   return false;
 }
   return true;
 }
 
 function check_validity(){
  
 var numLength= document.frmPost.txtStartFrom.value.length;
 var myNum = document.frmPost.txtStartFrom;
 
 for(var i=0;i<numLength;i++)
   {
      var c=myNum.value.charAt(i);
      if(c<'0'||c>'9'){
       alert("编号只能为数字");
        return false;
      }
     }
 return true;
 }

function check_form() {
     if (check_Blank(document.frmPost.txtBargainNo, true, "合同号"))
        return false;
    if (check_Blank(document.frmPost.txtNormalTimeTo, true, "开户截止日期")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtNormalTimeTo, true, "开户截止日期"))
            return false;
    }
    if (check_Blank(document.frmPost.txtDateFrom, true, "有效期起始时间")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtDateFrom, true, "有效期起始时间"))
            return false;
    }
    if (check_Blank(document.frmPost.txtDateTo, true, "有效期截止时间")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtDateTo, true, "有效期截止时间"))
            return false;
    }
    if (!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"有效期起始时间应在有效期截止时间之前！")) {
        return false;
    }
    if (!compareDate(document.frmPost.txtNormalTimeTo,document.frmPost.txtDateTo,"开户截止日期应在有效期截止时间之前！")) {
        return false;
    }
    if (!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtNormalTimeTo,"开户截止日期应在有效期起始时间之后！")) {
        return false;
    }
    if (!compareDate(document.frmPost.nowdate,document.frmPost.txtNormalTimeTo,"开户截止时间已经过期！")) {
        return false;
    }

    if (check_Blank(document.frmPost.txtStartFrom, true, "起始")) {
        return false;
    } 
    if (check_Blank(document.frmPost.txtTotalSheets, true, "发放总张数")) {
        return false;
    } else {
        if (!check_Num(document.frmPost.txtTotalSheets, true, "发放总张数"))
            return false;
    }
    if (check_Blank(document.frmPost.txtFormat,true,"格式")) {
        return false;
    }
    if (!check_Float(document.frmPost.txtPrepayImmediateFee,true,"已预付的一次性费用总额")) {
        return false;
    }
     if (check_Blank(document.frmPost.txtPrepayImmediateFee,true,"已预付的一次性费用总额")) {
        return false;
    }
      if (!check_Float(document.frmPost.txtPrepayDepositFee,true,"预存费总额")) {
        return false;
    }
     if (check_Blank(document.frmPost.txtPrepayDepositFee,true,"预存费总额")) {
        return false;
    }
 if (check_Blank(document.frmPost.txtMopID,true,"支付方式")) {
        return false;
    }
     if (check_Blank(document.frmPost.txtIsCampaign,true,"是否有费用优惠")) {
        return false;
    }
    
   return true;
}

function create_submit() {
      if (check_form() && checkLengh()&&check_validity()) {
        
        document.frmPost.submit();
    }
}
//-->
</SCRIPT>