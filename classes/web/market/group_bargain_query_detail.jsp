<%@ page import="com.dtv.oss.dto.wrap.CreateGroupBargainResult,
                 com.dtv.oss.dto.*"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="operator" prefix="oper" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>


<%


       CreateGroupBargainResult resultWrap = null;
       CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");
       String campaignName ="";  
       
       if (CmdRep!=null)
       {
                 try
                 {
                    resultWrap = (CreateGroupBargainResult)CmdRep.getPayload();
                    pageContext.setAttribute("oneline",resultWrap.getGroupBargain());
                    System.out.println("*****************the groupbargain dto is "+resultWrap.getGroupBargain());
                    campaignName = Postern.getCampaignNameByID(resultWrap.getGroupBargain().getCampaignId());

                    pageContext.setAttribute("detail",resultWrap.getDetailList());
                 }
                 catch (Exception ex)
                 {}


       }
       
     
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
var str_add = "0;0;0;0;0;";

function window.onload() {
    document.frmPost.nowdate.value=getDate();
}

function ChangeSubCompany()
{
    document.FrameUS.submit_update_select('subagent', document.frmPost.txtOrgID.value, 'txtAgentId', '');
}

function frm_modify()
{
    
    
    if (check_form()) {
        document.frmPost.action="group_bargain_modify.do";
        document.frmPost.Action.value="update";
	    document.frmPost.submit();
    }
}

function frm_delete()
{
	document.frmPost.action="group_bargain_delete.do";
    document.frmPost.Action.value="delete";
	document.frmPost.submit();
}

function frm_sell()
{
     
    if (check_form()) {
    	document.frmPost.action="group_bargain_sell.do";
        document.frmPost.Action.value="sale";
	    document.frmPost.submit();
    }
}

function check_form() {
    if (check_Blank(document.frmPost.txtBargainNo, true, "��ͬ��"))
        return false;
   
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
    if (!compareDate(document.frmPost.nowdate,document.frmPost.txtNormalTimeTo,"������ֹʱ���Ѿ����ڣ�")) {
        return false;
    }
    if (!compareDate(document.frmPost.txtNormalTimeTo,document.frmPost.txtDateTo,"������ֹ����Ӧ����Ч�ڽ�ֹʱ��֮ǰ��")) {
        return false;
    }
    if (!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtNormalTimeTo,"������ֹ����Ӧ����Ч����ʼʱ��֮��")) {
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

 

function detail_delete(id) {
    
    document.detail_frmPost.vartxtGroupBargainID.value = document.frmPost.txtID.value;
    document.detail_frmPost.vartxtDetailID.value = id
     
    document.detail_frmPost.action="group_bargain_delete_detail.do?Action=deleteDetail";
	document.detail_frmPost.submit();
}
function back_submit(){
	url="<bk:backurl property='group_bargain_query_result.do'/>";
	if(url=="")
		url="group_bargain_query_result.do?txtFrom=1&txtTo=10";
    document.location.href= url;
}


//-->
</SCRIPT>
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
<form name="frmPost" method="post">
      <input type="hidden" name="txtOperatorID" value="<oper:curoper property="OperatorID" />"/>
      <input type="hidden" name="Action"/>
      <input type="hidden" name="dtLastmod" value="<tbl:write name="oneline" property="dtLastmod" />">
      <input type="hidden" name="nowdate"/>
      <input type="hidden" name="func_flag" value="1107"/>
      

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�Ź�ȯ��ϸ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

      <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
          <td class="list_bg2" width="17%" align="right">��¼ID</td>
          <td class="list_bg1" width="33%" align="left"><font size="2">
             <input type="text" name="txtID" size="25"  value="<tbl:write name="oneline" property="Id"/>" class="textgray" readonly >
          </font></td>
          <td class="list_bg2" width="17%" align="right" >��ͬ��</td>
          <td class="list_bg1" width="33%" align="left"><font size="2">
          <input type="text" name="txtBargainNo" size="25"  value="<tbl:write name="oneline" property="BargainNo"/>" class="textgray" readonly >
          </font></td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">״̬</td>
          <td class="list_bg1" align="left"><font size="2">
          <input type="text" name="txtStatus" size="25" value="<d:getcmnname typeName="SET_M_GROUPBARGAINSTATUS" match="oneline:status" />" class="textgray" readonly>
          </font></td>
	  <td class="list_bg2"  align="right">��������</td>
          <td  class="list_bg1"  align="left"><font size="2">
            <input type="text" name="txtCreateTime" size="25" class="textgray" readonly value="<tbl:writedate name="oneline" property="CreateTime"/>" >
          </font></td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">����������</td>
          <td class="list_bg1" align="left"><font size="2">
            <input type="text" name="txtTotalSheets" size="25" value="<tbl:write name="oneline" property="TotalSheets"/>" class="textgray" readonly>
          </font></td>
          <td class="list_bg2" align="right">��ʹ������</td>
          <td class="list_bg1" align="left"><font size="2">
            <input type="text" name="txtUsedSheets" size="25" value="<tbl:write name="oneline" property="usedSheets"/>" class="textgray" readonly>
          </font></td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">����������</td>
          <td class="list_bg1" align="left"><font size="2">
          <input type="text" name="txtAbortSheets" size="25" value="<tbl:write name="oneline" property="abortSheets"/>" class="textgray" readonly>
          </font></td>
          <td class="list_bg2" align="right">�Żݻ��</td>
          <td  class="list_bg1" align="left"><font size="2">
           <input type="text" name="campaignName" size="25" value="<%=campaignName%>" class="textgray" readonly>
          </font></td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">Ԥ���ܶ�</td>
          <td class="list_bg1" align="left"><font size="2">
           <input type="text" name="txtPrepayDepositFee" size="25"  value="<tbl:write name="oneline" property="PrepayDepositFee"/>" class="textgray" readonly>
          </font></td>
          <td class="list_bg2" align="right">һ���Է�Ԥ���ܶ�</td>
          <td class="list_bg1" align="left"><font size="2">
           <input type="text" name="txtPrepayImmediateFee" size="25"  value="<tbl:write name="oneline" property="prepayImmediateFee"/>" class="textgray" readonly>
          </font></td>
       </tr>
       <tr>
           <td class="list_bg2"  align="right">������ֹ����*</td>
          <td class="list_bg1"  align="left"><font size="2">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNormalTimeTo)" onblur="lostFocus(this)" type="text" name="txtNormalTimeTo" size="25"  value="<tbl:writedate name="oneline" property="NormalTimeTo"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNormalTimeTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
          <td class="list_bg2" align="right">��Ч����ʼʱ��*</td>
          <td class="list_bg1" align="left"><font size="2">
           <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25"  value="<tbl:writedate name="oneline" property="DataFrom"/>">           <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
         
        </tr>
        <%
         Map mapSubcompany = new HashMap();
         mapSubcompany = Postern.getParentCompanys();
         pageContext.setAttribute("SubCompany",mapSubcompany);
           Map mopIdMap = Postern.getAllMethodOfPaymentsForMarket();
          pageContext.setAttribute("MethodOfPayment",mopIdMap);
         %>
         <tr>
          <td class="list_bg2" align="right">��Ч�ڽ�ֹʱ��*</td>
          <td class="list_bg1" align="left"><font size="2">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writedate name="oneline" property="DateTo"/>" >            <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
          <td class="list_bg2" align="right">����˾ </td>
          <td class="list_bg1" align="left">
          <tbl:select name="txtOrgID" set="SubCompany" match="oneline:OrgId" width="23" onchange="ChangeSubCompany()"/>
          </td>
           
        <tr>
        <td class="list_bg2" align="right">������ </td>
           <td class="list_bg1" align="left">
            
           <d:selsubproxy name="txtAgentId" parentMatch="oneline:OrgId" match="oneline:agentId" width="23"/>
         </td>  
           <td class="list_bg2" align="right">֧����ʽ*</td>
           <td class="list_bg1" align="left">
           <tbl:select name="txtMopID" set="MethodOfPayment" match="oneline:MopId" width="23" />
           </td>
         
        </tr> 
          <td class="list_bg2" align="right">�Ƿ��Ż�*</td>
           <td class="list_bg1" align="left" colspan="3"><font size="2">
            <d:selcmn name="txtIsCampaign" mapName="SET_G_YESNOFLAG" match="oneline:isCampaign" width="23"/>
           </font></td> 
	<tr>
          <td class="list_bg2" align="right">������Ϣ</td>
	  <td class="list_bg1" align="left" colspan="3"><font size="2">
          <input type="text" name="txtDescription" size="82" maxlength="200" value="<tbl:write name="oneline" property="Description"/>" >
          </font></td>
         </tr>
 
      </table>
      <input type="hidden" name="ProductList" value=""/>

      <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
        <%
            GroupBargainDTO dto = (GroupBargainDTO) pageContext.findAttribute("oneline");
             
            if (dto != null && dto.getStatus() != null && dto.getStatus().equals("N")) {
        %>
 
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
           <td background="img/button_bg.gif"><a href="javascript:frm_modify()" class="btn12">�޸�</a></td>
           <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
 
           
  
           <td width="20">&nbsp;</td>
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
           <td background="img/button_bg.gif"><a href="javascript:frm_delete()" class="btn12">ȡ��</a></td>
           <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
 

 
           <td width="20">&nbsp;</td>
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_sell()" class="btn12">����</a></td>
          <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
 
 
           <%
            }%>
        </tr>
      </table>

 
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      <td class="list_head"><div align="center">ID</div></td>
      <td class="list_head"><div align="center">�Ź�ȯ���</div></td>
      <td class="list_head"><div align="center">�û�֤�� </div></td>
      <td class="list_head"><div align="center">ʹ������ </div></td>
      <td class="list_head"><div align="center">״̬ </div></td>
      <td class="list_head"><div align="center">���� </div></td>
   </tbl:printcsstr>
     <lgc:loop property="detail" item="onedetail">
    <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
       <td align="center"><INPUT TYPE="text" NAME="txtDetailID" size="15" value="<tbl:write name="onedetail" property="id"/>" class="textgray" readonly ></td>
       <td align="center"><tbl:write name="onedetail" property="detailNo"/></td>
	 <td align="center"><tbl:writenumber name="onedetail" property="userID" digit="8" hideZero="true"/></td>
	  <td align="center"><tbl:writedate name="onedetail" property="usedDate"/></td>
	  <td align="center"><d:getcmnname typeName="SET_M_GROUPBARGAINDETAILSTATUS" match="onedetail:status" />
      </td>
        <td align="center">
      <%
        GroupBargainDetailDTO dto1 = (GroupBargainDetailDTO) pageContext.findAttribute("onedetail");
        if("W".equals(dto1.getStatus())){
      %>
	<A href="javascript:detail_delete('<tbl:write name="onedetail" property="id"/>')">ɾ��</A>
 <%}%>
	  </td>
    </tr>
    <input type="hidden" name="txtDetailNo" value="<tbl:write name="onedetail" property="detailNo"/>">
    <input type="hidden" name="txtDetailDtLastmod" value="<tbl:write name="onedetail" property="DtLastmod" />">
    </lgc:loop>
    <tr>
    	<td colspan="8" class="list_foot"></td>
 </tr>
    </table>
    <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
	  
        </tr>
      </table>   
     
      
 	 
 
 <input type="hidden" name="func_flag" value="30"/>
 <input type="hidden" name="campaignID" value="<tbl:writeparam name="campaignID" />"/>
</form>

<form name="detail_frmPost" method="post">
<input type="hidden" name="txtID" value=""/>
<input type="hidden" name="vartxtDetailID" value=""/>
<input type="hidden" name="vartxtDetailNo" value=""/>
<input type="hidden" name="vartxtDetailStatus" value=""/>
 
<input type="hidden" name="vartxtDetailDtLastmod" value=""/>
<input type="hidden" name="vartxtGroupBargainID" value=""/>
 

</form>