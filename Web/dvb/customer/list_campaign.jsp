<%@ page import="com.dtv.oss.dto.CampaignDTO,
                 java.util.Date"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import=" com.dtv.oss.web.util.CommonKeys" %>
<%@ taglib uri="osstags" prefix="d" %>

<% 
   int station =0;
   String  tmpCampaign ="";
   String  clickName="";
   String  txtCampaignType = (request.getParameter("txtCampaignType")==null) ? "" : request.getParameter("txtCampaignType");
   if (txtCampaignType.equals(CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN)){
      tmpCampaign ="套餐";
   } else if (txtCampaignType.equals(CommonKeys.CAMPAIGNCAMPAIGNTYPE_NORMAL)){ 
      tmpCampaign ="普通促销优惠";
   }
   String matchCampaign=request.getParameter("match");
%>

<script language=javascript>
  function view_package_detail(strId){
     window.open('list_campaign_detail.do?txtCampaignID='+strId,'','toolbar=no,location=no,status=no,menubar=no,scrollbar=no,width=380,height=300');
  }
  
  function singlechoose(place){
     var campainType ="<%=txtCampaignType%>";
     var setFood ="<%=CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN%>";
     if (campainType ==setFood){
       var listId = document.getElementsByName("listID");
       for (i=0;i<listId.length ;i++){
           if (i !=place ){
             listId[i].checked =false;
           }
       }
     }
  }
  function refreshFrame(obligationFlag){
  	document.frmPost.txtObligationFlag.value=obligationFlag;
  	document.frmPost.submit();
  }
</script>

<form name="frmPost" method="post" action="list_campaign.do">
  <tbl:hiddenparameters pass="txtCampaignType" />
  <input type="hidden" name="txtObligationFlag" value="">	
         	
  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
       <tr class="list_head">
          <td width="10%" class="list_head">&nbsp;</td>
          <td width="30%" class="list_head">是否保底封顶促销</td>
          <td class="list_head" align="center"><%=tmpCampaign%>活动</td>

        </tr>
       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
           <% clickName ="singlechoose("+station+")";  %>
           <td align="center" ><tbl:checkbox name="listID"  value="oneline:campaignID" match="<%=matchCampaign%>"  multipleMatchFlag="true" onclick="<%=clickName%>" /></td>
           <td align="center" ><d:getcmnname  typeName="SET_G_YESNOFLAG" match="oneline:obligationFlag" /></td>
           <td align="center" ><a href="JavaScript:view_package_detail('<tbl:write name="oneline" property="campaignID" />')"><tbl:write name="oneline" property="campaignName" /></a></td>
        </tbl:printcsstr>
        <% station = station+1 ; %>
       </lgc:bloop>
  </table>
  
</form>