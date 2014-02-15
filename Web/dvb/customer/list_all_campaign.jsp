
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="java.util.Date"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys,
								 com.dtv.oss.dto.CampaignDTO,
                 com.dtv.oss.dto.CustomerCampaignDTO,
                 com.dtv.oss.util.Postern" %>


<script language=javascript>
 function cust_campaign_detail_click(ccID,campaignType){
 var typeBundle='<%=CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN%>';
 var typeCampaign='<%=CommonKeys.CAMPAIGNCAMPAIGNTYPE_NORMAL%>';
// opener.document.frmPost.txtCCID.value=ccID;
 	//document.frmPost.txtActionType.value="campaign";
 	//document.frmPost.txtCCID.value=ccID;
 	if(campaignType==typeBundle){
	//	opener.document.frmPost.txtActionType.value="bundle";
 		opener.document.frmPost.action="cust_bundle_detail.do?txtCCID="+ccID+"&txtActionType=bundle&txtServiceAccountID=0";
	}else if(campaignType==typeCampaign){
//		opener.document.frmPost.txtActionType.value="campaign";
 		opener.document.frmPost.action="cust_campaign_detail.do?txtCCID="+ccID+"&txtActionType=campaign&txtServiceAccountID=0";
	}
 	opener.document.frmPost.submit();
 	self.close();
	//parent.location.href="cust_campaign_detail.do?txtActionType=campaign&txtCCID="+ ccID ;
}
</script>
<form name="frmPost" method="post" action="">
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">客户产品套餐列表</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
       <tr class="list_head">
          <td class="list_head">客户套餐ID</td>
          <td class="list_head" align="center">套餐名称</td>
          <td class="list_head" align="center">优惠开始时间</td>
          <td class="list_head" align="center">优惠结束时间</td>
          <td class="list_head" align="center">状态</td>
       </tr>
       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
           <%
             CustomerCampaignDTO ccDTO =(CustomerCampaignDTO)pageContext.getAttribute("oneline");
             int campainID = ccDTO.getCampaignID();
             CampaignDTO campaignDto = Postern.getCampaignDTOByCampaignID(campainID);
             String capName = campaignDto.getCampaignName();
             String campaignType = campaignDto.getCampaignType();
           %>
           <tbl:printcsstr style1="list_bg1" style2="list_bg2"
						overStyle="list_over">
					 <td align="center" width="75" nowrap><a class="link12" href="javascript:cust_campaign_detail_click('<tbl:write name="oneline" property="ccID"/>','<%=campaignType%>');"><tbl:write name="oneline" property="ccID"/></a></td>  
           <td align="center" width="120" ><%=capName%></td> 
           <td align="center" nowrap><tbl:writedate name="oneline" property="DateFrom" /><br><tbl:writedate name="oneline" property="DateFrom" onlyTime="true"/></td>
           <td align="center" nowrap><tbl:writedate name="oneline" property="DateTo" /><br><tbl:writedate name="oneline" property="DateTo" onlyTime="true"/></td>
           <td align="center"><d:getcmnname typeName="SET_M_CUSTOMERCAMPAIGNSTATUS"	match="oneline:status" /></td>
           </tbl:printcsstr>
       </lgc:bloop>
  </table>
 </form>