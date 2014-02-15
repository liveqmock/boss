<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CampaignDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.CPCampaignMapDTO,
                 com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<Script language=JavaScript>
<!--
function frm_check(){
	if (check_Blank(document.frmPost.txtAllowPause, true, "是否允许暂停"))
		return false;
	if (check_Blank(document.frmPost.txtAllowTransition, true, "是否允许迁移"))
		return false;
	if (check_Blank(document.frmPost.txtAllowTransfer, true, "是否允许过户"))
		return false;
	if (check_Blank(document.frmPost.txtAllowClose, true, "是否允许销户"))
		return false;
		
	return true;
}

function frm_submit(){
	if(frm_check())
		document.frmPost.submit();
}

function frm_cancel_submit(){
	if(confirm("取消优惠将影响费用计算，您确定要取消优惠吗？")){
		document.frmPost.txtStatus.value="C";
		document.frmPost.submit();
	}
}
//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户产品促销明细信息维护</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="cp_campaign_detail_op.do" >  
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true" >
   <%
      CPCampaignMapDTO dto = (CPCampaignMapDTO)pageContext.getAttribute("oneline");
      CustomerCampaignDTO custCampaignDto =Postern.getCustomerCampaignByccID(dto.getCcId());
      if (custCampaignDto ==null) custCampaignDto =new CustomerCampaignDTO();
      pageContext.setAttribute("CustomerCampaignDTO",custCampaignDto);

      String strCpName=Postern.getProductNameByPSID(dto.getCustProductID());
      if(strCpName==null)strCpName="";
      String strCampaignName=Postern.getCampaignNameByID(custCampaignDto.getCampaignID());
      if(strCampaignName==null)strCampaignName="";                              
      String strGroupBarginName=Postern.getCustCampaignOrGroupBarginNameByCCID(custCampaignDto.getGroupBargainID());
      if(strGroupBarginName==null)strGroupBarginName="";
   %> 
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
         
         <tr>
          <td class="list_bg2" width="17%"  align="right">序号</td>
          <td class="list_bg1" width="33%"  align="left">
             <input type="text" name="txtID" size="25" maxlength="10" value="<tbl:write name="oneline" property="id" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" width="17%"  align="right">合同号</td>
          <td class="list_bg1" width="33%"  align="left">
            <input type="text" name="txtContractNo" size="25" maxlength="20" value="<tbl:write name="CustomerCampaignDTO" property="contractNo" />" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">客户产品ID</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtCustProductID" size="25" maxlength="10" value="<tbl:write name="oneline" property="custProductID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">客户产品</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtCustProductName" size="25" maxlength="20" value="<%=strCpName %>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">促销活动ID</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtCampaignID" size="25" maxlength="10" value="<tbl:write name="CustomerCampaignDTO" property="campaignID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">促销活动</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtCampaignName" size="25" maxlength="20" value="<%=strCampaignName %>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">团购活动ID</td>
          <td class="list_bg1"  align="left">
              <input type="text" name="txtGroupBargainID" size="25" maxlength="10" value="<tbl:write name="CustomerCampaignDTO" property="groupBargainID" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">团购活动</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtGroupBargainName" size="25" maxlength="20" value="<%=strGroupBarginName %>" class="textgray" readonly >
          </td> 
        </tr>
	<tr>
          <td class="list_bg2" align="right">客户促销活动记录号</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtCCID" size="25" maxlength="10" value="<tbl:write name="CustomerCampaignDTO" property="ccId" />" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">有效期的起始时间</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtTimeStart" size="25" maxlength="20" value="<tbl:writedate name="CustomerCampaignDTO" property="dateFrom" />" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">有效期的截止时间</td>
          <td class="list_bg1"  align="left" colspan="3">
            <input type="text" name="txtTimeEnd" size="25" maxlength="10" value="<tbl:writedate name="CustomerCampaignDTO" property="dateTo" />" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">是否允许暂停</td>
          <td class="list_bg1"  align="left">
            <d:selcmn name="txtAllowPause" mapName="SET_G_YESNOFLAG" match="CustomerCampaignDTO:allowPause" width="23" />
          </td>
          <td class="list_bg2" align="right">是否允许迁移</td>
          <td class="list_bg1"  align="left">
             <d:selcmn name="txtAllowTransition" mapName="SET_G_YESNOFLAG" match="CustomerCampaignDTO:allowTransition" width="23" />
          </td> 
        </tr>
        <tr class="viewline">
          <td class="list_bg2" align="right">是否允许过户</td>
          <td class="list_bg1"  align="left">
              <d:selcmn name="txtAllowTransfer" mapName="SET_G_YESNOFLAG" match="CustomerCampaignDTO:allowTransfer" width="23" />
          </td>
          <td class="list_bg2" align="right">是否允许销户</td>
          <td class="list_bg1"  align="left">
              <d:selcmn name="txtAllowClose" mapName="SET_G_YESNOFLAG" match="CustomerCampaignDTO:allowClose" width="23" />
          </td> 
        </tr>
      </table>
      
      <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"    >
      <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="acctaddr" property="DtLastmod" />"    >
      <input type="hidden" name="txtActionType" value="modCPCampaignMap" >
      <input type="hidden" name="func_flag" value="666">
      <input type="hidden" name="txtStatus" value="">
    </lgc:bloop>  
     
     <BR>  
      
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_campaign_detail.do" />" class="btn12">返&nbsp;回</a></td> 
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>  
           <td width="20" ></td>
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td background="img/button_bg.gif"><a href="javascript:frm_submit()" class="btn12">修&nbsp;改</a></td> 
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
           <td width="20" ></td>
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td background="img/button_bg.gif"><a href="javascript:frm_cancel_submit()" class="btn12">取消促销</a></td> 
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
           <td width="20" ></td>          
        </tr>
      </table>
      <tbl:hiddenparameters pass="txtCustomerID" />
</form>