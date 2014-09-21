<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<Script language=JavaScript>
<%
	String txtAllowPause=request.getParameter("txtAllowPause");
	String txtAllowTransition=request.getParameter("txtAllowTransition");
	String txtAllowTransfer=request.getParameter("txtAllowTransfer");
	String txtAllowClose=request.getParameter("txtAllowClose");
	String txtAllowAlter=request.getParameter("txtAllowAlter");
%>
<!--
function check_frm(){
	if (check_Blank(document.frmPost.txtAllowPause, true, "�Ƿ�������ͣ"))
		return false;
	if (check_Blank(document.frmPost.txtAllowTransition, true, "�Ƿ�����Ǩ��"))
		return false;
	if (check_Blank(document.frmPost.txtAllowTransfer, true, "�Ƿ��������"))
		return false;
	if (check_Blank(document.frmPost.txtAllowClose, true, "�Ƿ���������"))
		return false;
	
	return true;
}
function frm_submit(){
	if (check_frm())  
	    document.frmPost.txtActionType.value="modifyCampaignMap";
		document.frmPost.submit();
}



function frm_add_submit(custID,dateFrom,dateTo){
	document.frmPost.action="manual_grant_campaign.do?txtDateFrom=" + dateFrom +"&txtDateTo=" + dateTo;
	document.frmPost.submit();
}

//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ���Ʒ����ά��</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="cust_campaign_detail_modify_op.do" >
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true" >
   <%
      CustomerCampaignDTO dto = (CustomerCampaignDTO)pageContext.getAttribute("oneline");
      String txtStatus =dto.getStatus();
      String txtCampaignName=request.getParameter("txtCampaignName");
      if(txtCampaignName==null)txtCampaignName="";
      String strCustName="";
      strCustName=Postern.getCustomerNameByID(dto.getCustomerID());
      if(strCustName==null)strCustName="";
      String strGroupBarginName="";
      strGroupBarginName=Postern.getCustCampaignOrGroupBarginNameByCCID(dto.getGroupBargainID());
      if(strGroupBarginName==null) strGroupBarginName="";
    %> 
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
         <tr>
         	<td class="list_bg2" width="17%"  align="right">�ͻ�����ID</td>
          <td class="list_bg1" width="33%"  align="left">
             <input type="text" name="txtCcID" size="25" maxlength="10" value="<tbl:write name="oneline" property="ccID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" width="17%"  align="right">���������</td>
          <td class="list_bg1" width="33%"  align="left">
             <input type="text" name="txtCampaignName" size="25" maxlength="10" value="<%=txtCampaignName %>" class="textgray" readonly >
          </td>
         </tr>
         <tr>
          <td class="list_bg2" align="right">�˻���</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAccoutName" size="25" maxlength="10" value="<%=request.getParameter("txtAccountName")%>" class="textgray" readonly >
             <input type="hidden" name="txtAccoutID" size="25" maxlength="10" value="<tbl:write name="oneline" property="accountID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">ҵ���˻�ID</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtServiceAccountID" size="25" maxlength="20" value="<tbl:write name="oneline" property="serviceAccountID" />" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">��ʼ����</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtDateFrom" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="dateFrom" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">��������</td>
          <td class="list_bg1"  align="left">
            <input type="text" name="txtDateTo" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="dateTo" />" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">״̬</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtStatus" size="25" maxlength="20" value="<d:getcmnname typeName="SET_M_CUSTOMERCAMPAIGNSTATUS" match="<%=txtStatus%>" /> " class="textgray" readonly > </td>     
          <td class="list_bg2" align="right">�Ƿ�������</td>
          <td class="list_bg1"  align="left"><d:selcmn name="txtAllowAlter" mapName="SET_G_YESNOFLAG" match="txtAllowClose" width="23" /></td>        
        </tr>
        <tr>
          <td class="list_bg2" align="right">�Ƿ�������ͣ</td>
          <td class="list_bg1"  align="left"><d:selcmn name="txtAllowPause" mapName="SET_G_YESNOFLAG" match="txtAllowPause" width="23" /></td>
          <td class="list_bg2" align="right">�Ƿ�����Ǩ��</td>
          <td class="list_bg1"  align="left"><d:selcmn name="txtAllowTransition" mapName="SET_G_YESNOFLAG" match="txtAllowTransition" width="23" /></td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">�Ƿ��������</td>
          <td class="list_bg1"  align="left"><d:selcmn name="txtAllowTransfer" mapName="SET_G_YESNOFLAG" match="txtAllowTransfer" width="23" /></td>
          <td class="list_bg2" align="right">�Ƿ�����ȡ��</td>
          <td class="list_bg1"  align="left"><d:selcmn name="txtAllowClose" mapName="SET_G_YESNOFLAG" match="txtAllowClose" width="23" /></td> 
        </tr>
		    <tr>
		  	 <td class="list_bg2" ><div align="right">��ע</div></td>
		  	 <td class="list_bg1" colspan="3" >
		     <input type="text" name="txtComments" size="80" value="<tbl:write name="cust" property="comments" />" >
		     </td>
		    </tr>
      </table>
      
      <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"    >
      <input type="hidden" name="txtCCID"   value="<tbl:write name="oneline" property="ccID" />"    >
      <input type="hidden" name="txtActionType" value="modCustCampaign" >
      <input type="hidden" name="func_flag" value="667">
      <input type="hidden" name="txtStatus" value="">
      
    </lgc:bloop>  
     
    <BR>  
      
    <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_campaign_detail.do" />" class="btn12">��&nbsp;��</a></td> 
	   <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
           <td width="20" ></td>
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td background="img/button_bg.gif"><a href="javascript:frm_submit()" class="btn12">�����޸�</a></td> 
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
      </table>
      <tbl:hiddenparameters pass="txtCustomerID" />
</form>