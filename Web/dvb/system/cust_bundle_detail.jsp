<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%
	String ccid=request.getParameter("txtCCID");
%>
<Script language=JavaScript>
<!--
function bundle_defer(){
	document.frmPost.action="cust_bundle_defer.do";
	document.frmPost.submit();	
}
function bundle_modify(){
	document.frmPost.action="cust_bundle_modify.do";
	document.frmPost.submit();	
}
function bundle_transition(){
	document.frmPost.action="cust_bundle_transfer_select.do";
	document.all.txtActionType.value="bundleTransfer";
	document.frmPost.submit();	
}
function bundle_cancel(){
	document.frmPost.action="cust_bundle_cancel_info.do";
	document.all.txtActionType.value="bundleCancel";
	document.frmPost.submit();	
}
function bundle_prepayment(){
	document.frmPost.action="cust_bundle_prepayment_method.do";
	document.all.txtActionType.value="BundlePrePayment";
	document.frmPost.submit();	
}


//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户套餐</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="cust_bundle_detail.do" >
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">客户套餐基本信息</font></td>
     </tr>
  </table>
   <lgc:bloop  requestObjectName="ResponseQueryResult" item="oneline" isOne="true" >
   <%
      CustomerCampaignDTO dto = (CustomerCampaignDTO)pageContext.getAttribute("oneline");
      CampaignDTO cmapaignDto=Postern.getCampaignDTOByCampaignID(dto.getCampaignID());
      pageContext.setAttribute("cmapaignDto",cmapaignDto);

      String strCreateOpName="";
      strCreateOpName=Postern.getOperatorNameByID(dto.getCreatreOpID());
      if(strCreateOpName==null)strCreateOpName="";
     
    %> 
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
          <tr>
          <td class="list_bg2" align="right" width="17%">客户套餐ID</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25"  value="<tbl:write name="oneline" property="ccID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">创建时间</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtDtCreate" size="25"  value="<tbl:write name="oneline" property="dtCreate" />" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">套餐名称</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtCampaignName" size="25"  value="<tbl:write name="cmapaignDto" property="CampaignName" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">状态</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtStatus" size="25" value="<d:getcmnname typeName="SET_M_CUSTOMERCAMPAIGNSTATUS" match="oneline:status"  /> " class="textgray" readonly >    
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">受理单号</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtCsiID" size="25"  value="<tbl:write name="oneline" property="csiID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">最后更新时间</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtDtLastmod" size="25"  value="<tbl:write name="oneline" property="dtLastmod" />" class="textgray" readonly >      
          </td>
        </tr>
         <tr>
          <td class="list_bg2" align="right">账户ID</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutID" size="25"  value="<tbl:write name="oneline" property="accountID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">业务账户ID</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:write name="oneline" property="serviceAccountID" />" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">协议期（开始）</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtDateFrom" size="25"  value="<tbl:writedate name="oneline" property="dateFrom" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">协议期（结束）</td>
          <td class="list_bg1"  align="left">
            <input type="text" name="txtDateTo" size="25"  value="<tbl:writedate name="oneline" property="dateTo" />" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">是否自动延伸</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtAutoExtendFlagDesc" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:AutoExtendFlag"/>" class="textgray" readonly >
             <input type="hidden" name="txtAutoExtendFlag" value="<tbl:write name="oneline" property="AutoExtendFlag" />">

          </td>
          <td class="list_bg2" align="right">预付截至日期</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtPrePaidTo" size="25"  value="<tbl:writedate name="oneline" property="PrePaidTo" />" class="textgray" readonly >      
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">套餐付费方式</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtPaymentType" size="25"  value="<d:getcmnname typeName="SET_F_RFBILLINGCYCLEFLAG" match="cmapaignDto:rfBillingCycleFlag"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">NID</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtNbrDate" size="25"  value="<tbl:writedate name="oneline" property="NbrDate" />" class="textgray" readonly >      
          </td>
        </tr>        
        <tr>
          <td class="list_bg2" align="right">创建人</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtCreatreOpID" size="25"  value="<%=strCreateOpName%>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">创建机构</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtCreateOrg" size="25" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="createOrgID" />" class="textgray">
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">是否允许暂停</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAllowPauseDesc" size="25"  value="<d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:allowPause"  />" class="textgray" readonly>
          <input type="hidden" name="txtAllowPause" value="<tbl:write name="oneline" property="allowPause" />">
          </td>
        
          <td class="list_bg2" align="right">是否允许迁移</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAllowTransitionDesc" size="25"  value="<d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:allowTransition"  />" class="textgray" readonly>
          <input type="hidden" name="txtAllowTransition" value="<tbl:write name="oneline" property="allowTransition" />">
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">是否允许过户</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAllowTransferDesc" size="25"  value="<d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:allowTransfer"  />" class="textgray" readonly>
          <input type="hidden" name="txtAllowTransfer" value="<tbl:write name="oneline" property="allowTransfer" />">
          </td>
          <td class="list_bg2" align="right">是否允许销户</td>
          <td class="list_bg1"  align="left">
          <input type="text" name="txtAllowCloseDesc" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:allowClose"  />" class="textgray" readonly>
          <input type="hidden" name="txtAllowClose" value="<tbl:write name="oneline" property="allowClose" />">
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">是否允许变更</td>
          <td class="list_bg1"  align="left" colspan="3" >
          <input type="text" name="txtAllowAlterDesc" size="25" value="<d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:allowAlter" />" class="textgray" readonly>
          <input type="hidden" name="txtAllowAlter" value="<tbl:write name="oneline" property="allowAlter" />">
          </td>        
        </tr>
        <tr>
          <td class="list_bg2" align="right">备注</td>
          <td class="list_bg1"  align="left" colspan="3" >
 			<input type="text" name="txtComments" size="83" value="<tbl:write name="oneline" property="comments" />" class="textgray" readonly >
          </td>
        </tr>
      </table>   
      <input type="hidden" name="txtCustomerID"  value="<tbl:write name="oneline" property="customerID" />" >
     
    <BR>  
      
		<table align="center"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<bk:canback url="cust_bundle_query.do/service_account_query_result_by_sa.do">
				<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
			  <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_bundle_query.do/service_account_query_result_by_sa.do" />" class="btn12">返&nbsp;回</a></td> 
			  <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
				</bk:canback>
		   	<d:displayControl id="button_cust_bundle_defer" bean="oneline">
			  <td width="20" ></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td background="img/button_bg.gif"><a href="javascript:bundle_defer()" class="btn12">套餐延期</a></td> 
			  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
			  </d:displayControl>
		   	<d:displayControl id="button_cust_bundle_transition" bean="oneline">
			  <td width="20" ></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td background="img/button_bg.gif"><a href="javascript:bundle_transition()" class="btn12">套餐转换</a></td> 
			  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
			  </d:displayControl>
		   	<d:displayControl id="button_cust_bundle_modify" bean="oneline">
			  <td width="20" ></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td background="img/button_bg.gif"><a href="javascript:bundle_modify()" class="btn12">维&nbsp;护</a></td> 
			  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
			  </d:displayControl>
		   	<d:displayControl id="button_cust_bundle_cancel" bean="oneline">
			  <td width="20" ></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td background="img/button_bg.gif"><a href="javascript:bundle_cancel()" class="btn12">取消套餐</a></td> 
			  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
			  </d:displayControl>
		   	<d:displayControl id="button_cust_bundle_prepayment" bean="oneline">
			  <td width="20" ></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td background="img/button_bg.gif"><a href="javascript:bundle_prepayment()" class="btn12">套餐续费</a></td> 
			  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
			  </d:displayControl>
			</tr>
		</table>
</lgc:bloop>  

<input type="hidden" name="txtActionType"  value="" >

<br>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="820">
  <tr>
    <td aglin="center">
      <iframe name=head width="100%" height="600" frameborder=0 scrolling=no src="list_cust_campaign_product.do?txtActionType=bundleMap&txtCCID=<%=ccid%>" ></iframe>
    </td>
  </tr>
</table>
</form>

