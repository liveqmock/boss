<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%
	String ccid=request.getParameter("txtCCID");
%>
<Script language=JavaScript>
<!--
function check_frm(){
	return true;
}
function frm_submit(){
	if (check_frm())  
	    document.frmPost.txtActionType.value="modifyCampaignMap";
		document.frmPost.submit();
}
function campaign_modify(){
	if (check_frm())  
	{
		document.frmPost.txtActionType.value="campaign";
		document.frmPost.txtStatus.value="";
		document.frmPost.action = "cust_campaign_modify.do";
		document.frmPost.submit();
	}
	
	 //	self.location.href ="cust_campaign_modify.do?txtActionType=campaign&txtCCID=<%=ccid%>";
}
function campaign_cancel(){
		document.frmPost.txtActionType.value="campaignCancle";
		document.frmPost.action = "cust_campaign_cancel.do";
		document.frmPost.submit();
}

//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ�����</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="cust_campaign_detail.do" >
	<input type="hidden" name="txtActionType" value="">
	<input type="hidden" name="txtCCID" value="<%=ccid%>">
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">�ͻ�����������Ϣ</font></td>
     </tr>
  </table>
  <%boolean isIncludeProduct=false;%>
   <lgc:bloop  requestObjectName="ResponseQueryResult" item="oneline" isOne="true" >
   <%
      CustomerCampaignDTO dto = (CustomerCampaignDTO)pageContext.getAttribute("oneline");
      String txtStatus =dto.getStatus();
      String txtCampaignName=Postern.getCampaignNameByID(dto.getCampaignID());
      if(txtCampaignName==null)txtCampaignName="";
      String strCreateOpName="";
      strCreateOpName=Postern.getOperatorNameByID(dto.getCreatreOpID());
      if(strCreateOpName==null)strCreateOpName="";
      ArrayList cpMapList=Postern.getCPCampaignListByCustCampaignID(dto.getCcID());
      if(cpMapList!=null&&!cpMapList.isEmpty()){
      	isIncludeProduct=true;
      }
      	
    %> 
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
          <tr>
          <td class="list_bg2" align="right" width="17%">�ͻ�����ID</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25" value="<tbl:write name="oneline" property="ccID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">����ʱ��</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtDtCreate" size="25" value="<tbl:write name="oneline" property="dtCreate" />" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">��������</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtCampaignName" size="25" value="<%=txtCampaignName %>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">״̬</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtStatus" size="25" value="<d:getcmnname typeName="SET_M_CUSTOMERCAMPAIGNSTATUS" match="<%=txtStatus%>" /> " class="textgray" readonly >      
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">������</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtCsiID" size="25" value="<tbl:write name="oneline" property="csiID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">������ʱ��</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtDtLastmod" size="25" value="<tbl:write name="oneline" property="dtLastmod" />" class="textgray" readonly >      
          </td>
        </tr>
         <tr>
          <td class="list_bg2" align="right">�˻�ID</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutID" size="25"  value="<tbl:write name="oneline" property="accountID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">ҵ���˻�ID</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:write name="oneline" property="serviceAccountID" />" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">��Ч�ڣ���ʼ��</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtDateFrom" size="25" value="<tbl:writedate name="oneline" property="dateFrom" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">��Ч�ڽ���</td>
          <td class="list_bg1"  align="left">
            <input type="text" name="txtDateTo" size="25" value="<tbl:writedate name="oneline" property="dateTo" />" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" width="17%"  align="right">������</td>
          <td class="list_bg1" width="33%"  align="left">
             <input type="text" name="txtCreatreOpID" size="25" value="<%=strCreateOpName%>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">��������</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtCreateOrg" size="25" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="createOrgID" />" class="textgray">
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">�Ƿ�������ͣ</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAllowPause" size="25" value="<d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:allowPause"  />" class="textgray" readonly></td>
          <input type="hidden" name="allowPause" value="<tbl:write name="oneline" property="allowPause" />">
        
          <td class="list_bg2" align="right">�Ƿ�����Ǩ��</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAllowTransition" size="25" value="<d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:allowTransition"  />" class="textgray" readonly></td> 
          <input type="hidden" name="allowTransition" value="<tbl:write name="oneline" property="allowTransition" />">
        </tr>
        <tr>
          <td class="list_bg2" align="right">�Ƿ��������</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAllowTransfer" size="25" value="<d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:allowTransfer"  />" class="textgray" readonly></td>
          <input type="hidden" name="allowTransfer" value="<tbl:write name="oneline" property="allowTransfer" />">
          <td class="list_bg2" align="right">�Ƿ���������</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAllowClose" size="25" value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:allowClose"  />" class="textgray" readonly></td> 
          <input type="hidden" name="allowClose" value="<tbl:write name="oneline" property="allowClose" />">
        </tr>
        <tr>
          <td class="list_bg2" align="right">�Ƿ�������</td>
          <td class="list_bg1"  align="left" colspan="3" >
          <input type="text" name="txtAllowAlter" size="25" value="<d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:allowAlter" />" class="textgray" readonly></td>        
          <input type="hidden" name="allowAlter" value="<tbl:write name="oneline" property="allowAlter" />">
        </tr>
        <tr>
          <td class="list_bg2" align="right">��ע</td>
          <td class="list_bg1"  align="left" colspan="3" >
 			<input type="text" name="txtComments" size="83" value="<tbl:write name="oneline" property="comments" />" class="textgray" readonly >
          </td>
        </tr>
      </table>   
      
     
    <BR>  
      
    <table align="center"  border="0" cellspacing="0" cellpadding="0">
       <tr>
       	<bk:canback url="cust_campaign_query.do/service_account_query_result_by_sa.do">
         <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
			   <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_campaign_query.do/service_account_query_result_by_sa.do/service_account_pause_view.do/service_account_resume_view.do" />" class="btn12">��&nbsp;��</a></td> 
			   <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
			  </bk:canback>  
		   	<d:displayControl id="button_cust_campaign_detail_cancel" bean="oneline">	       <td width="20" ></td>
	       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			   <td background="img/button_bg.gif"><a href="javascript:campaign_cancel()" class="btn12">ȡ&nbsp;��</a></td> 
			   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
			  </d:displayControl>
		   	<d:displayControl id="button_cust_campaign_detail_modify" bean="oneline">			   <td width="20" ></td>
	       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			   <td background="img/button_bg.gif"><a href="javascript:campaign_modify()" class="btn12">ά&nbsp;��</a></td> 
			   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
			  </d:displayControl>
			 </tr>
    </table>
    </lgc:bloop>  
    
<br>
<%if(isIncludeProduct){%>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="820">
  <tr>
    <td aglin="center">
      <iframe name=head width="100%" height="300" frameborder=0 scrolling=no src="list_cust_campaign_product.do?txtActionType=campaignMap&txtCCID=<%=ccid%>" ></iframe>
    </td>
  </tr>
</table>
<%}%>
</form>

