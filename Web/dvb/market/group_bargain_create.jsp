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
          ���ڻ�ȡ���ݡ�����
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
      <td class="title">�����Ź�ȯͷ��Ϣ</td>
     </tr>
   </table>
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
     <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
     </tr>
  </table>
  <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
          <td class="list_bg2"><div align="right">�Ź���ͬ��*</div></td>
          <td colspan="3" class="list_bg1"><font size="2">
          <input type="text" name="txtBargainNo" size="25"  value="<tbl:writeparam name="txtBargainNo"/>" maxlength="50">
          </font></td>
          
        </tr>
         <tr>
            <td class="list_bg2"><div align="right">һ���Է�Ԥ���ܶ�*</div></td>
            <td  class="list_bg1"><font size="2">
          <input type="text" name="txtPrepayImmediateFee" size="25"  value="<tbl:writeparam name="txtPrepayImmediateFee"/>">
          </font></td>
           <td class="list_bg2"><div align="right">Ԥ���ܶ�*</div></td>
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
          <td class="list_bg2"><div align="right">����˾ </div></td>
          <td class="list_bg1">
          <tbl:select name="txtOrgID" set="SubCompany" match="txtOrgID" width="23" onchange="ChangeSubCompany()"/>
          </td>
           <td class="list_bg2"><div align="right">������ </div></td>
           <td class="list_bg1">
          <d:selsubproxy name="txtAgentId" parentMatch="txtOrgID" match="txtAgentId" width="23" defaultIndex="1"/>
         </td>  
        </tr>
        <tr>
         <td class="list_bg2"><div align="right">��Ч����ʼʱ��*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
          <td class="list_bg2"><div align="right">��Ч�ڽ�ֹʱ��*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" >          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
        </tr>
        <tr>
          <td class="list_bg2"><div align="right">������ֹ����*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNormalTimeTo)" onblur="lostFocus(this)" type="text" name="txtNormalTimeTo" size="25" value="<tbl:writeparam name="txtNormalTimeTo"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNormalTimeTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
           </td>
          <td class="list_bg2"><div align="right">����������*</div></td>
          <td class="list_bg1">
          <input type="text" name="txtTotalSheets" maxlength="6" size="25" value="<tbl:writeparam name="txtTotalSheets"/>">
          </td>
        </tr>
        <tr>
           <td class="list_bg2"><div align="right">��ʼ���*</div></td>
           <td class="list_bg1">
          <input type="text" name="txtStartFrom" size="25" maxlength="6" value="<tbl:writeparam name="txtStartFrom"/>">
           </td>
          <td class="list_bg2"><div align="right">�Ź�ȯ��ŷ�ʽ*</div></td>
           <td class="list_bg1">
          <input type="text" name="txtFormat" value="<tbl:writeparam name="txtFormat"/>" size="25" maxlength="50"/>
          	<font color="red">(��'ABC***')</font>
          </td>
          
        </tr>
        <tr>
           <td class="list_bg2"><div align="right">֧����ʽ*</div></td>
           <td class="list_bg1">
           <tbl:select name="txtMopID" set="MethodOfPayment" match="txtMopID" width="23" />
           </td>
          <td class="list_bg2"><div align="right">�Ƿ��з����Ż�*</div></td>
           <td class="list_bg1">
           <d:selcmn name="txtIsCampaign" mapName="SET_G_YESNOFLAG" match="txtIsCampaign" width="23"/>
          </td>
        </tr>
       
        <tr>
         <td class="list_bg2"><div align="right">������Ϣ</div></td>
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
			<td><input name="Submit" type="button" class="button" value="�� ��" onclick="javascript:create_submit()"></td>
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
   alert("�Ź�ȯ��ŷ�ʽ*�ĸ���һ��Ҫ����ʼ��ŵĳ������")
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
       alert("���ֻ��Ϊ����");
        return false;
      }
     }
 return true;
 }

function check_form() {
     if (check_Blank(document.frmPost.txtBargainNo, true, "��ͬ��"))
        return false;
    if (check_Blank(document.frmPost.txtNormalTimeTo, true, "������ֹ����")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtNormalTimeTo, true, "������ֹ����"))
            return false;
    }
    if (check_Blank(document.frmPost.txtDateFrom, true, "��Ч����ʼʱ��")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtDateFrom, true, "��Ч����ʼʱ��"))
            return false;
    }
    if (check_Blank(document.frmPost.txtDateTo, true, "��Ч�ڽ�ֹʱ��")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtDateTo, true, "��Ч�ڽ�ֹʱ��"))
            return false;
    }
    if (!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"��Ч����ʼʱ��Ӧ����Ч�ڽ�ֹʱ��֮ǰ��")) {
        return false;
    }
    if (!compareDate(document.frmPost.txtNormalTimeTo,document.frmPost.txtDateTo,"������ֹ����Ӧ����Ч�ڽ�ֹʱ��֮ǰ��")) {
        return false;
    }
    if (!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtNormalTimeTo,"������ֹ����Ӧ����Ч����ʼʱ��֮��")) {
        return false;
    }
    if (!compareDate(document.frmPost.nowdate,document.frmPost.txtNormalTimeTo,"������ֹʱ���Ѿ����ڣ�")) {
        return false;
    }

    if (check_Blank(document.frmPost.txtStartFrom, true, "��ʼ")) {
        return false;
    } 
    if (check_Blank(document.frmPost.txtTotalSheets, true, "����������")) {
        return false;
    } else {
        if (!check_Num(document.frmPost.txtTotalSheets, true, "����������"))
            return false;
    }
    if (check_Blank(document.frmPost.txtFormat,true,"��ʽ")) {
        return false;
    }
    if (!check_Float(document.frmPost.txtPrepayImmediateFee,true,"��Ԥ����һ���Է����ܶ�")) {
        return false;
    }
     if (check_Blank(document.frmPost.txtPrepayImmediateFee,true,"��Ԥ����һ���Է����ܶ�")) {
        return false;
    }
      if (!check_Float(document.frmPost.txtPrepayDepositFee,true,"Ԥ����ܶ�")) {
        return false;
    }
     if (check_Blank(document.frmPost.txtPrepayDepositFee,true,"Ԥ����ܶ�")) {
        return false;
    }
 if (check_Blank(document.frmPost.txtMopID,true,"֧����ʽ")) {
        return false;
    }
     if (check_Blank(document.frmPost.txtIsCampaign,true,"�Ƿ��з����Ż�")) {
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