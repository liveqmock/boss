<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>

<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.CommonKeys,
                 java.util.*" %>
                               
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
      <tr class="list_head">
         <td width="10%" class="list_head">&nbsp;</td>
         <td width="20%" class="list_head" align="center">优惠活动名称</td>
         <td width="20%" class="list_head" align="center">优惠活动时间</td>
         <td width="50%" class="list_head" align="center">优惠活动具体内容</td>
      </tr>
      <%
      	
      	String custType = request.getParameter("txtCustType");
      	String openSourceType = request.getParameter("txtOpenSourceType");
      	String openFlag = request.getParameter("OpenFlag");
      	String county = request.getParameter("txtCounty");
      	String csiType = request.getParameter("txtCsiType");
      	
      	if(openFlag != null)
      	{
      		if(openFlag.equals(CommonKeys.ACTION_OF_BOOKING) || openFlag.equals(CommonKeys.ACTION_OF_BOOKINGAGENT))
      			csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK;
      		else if(openFlag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT))
      			csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_OB;
      		else if(openFlag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT))
      			csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS;
      	}
            	
      	
        String campaignType ="'"+CommonKeys.CAMPAIGNCAMPAIGNTYPE_NORMAL+"'";
        //ArrayList campaignList =Postern.getCampaignDTO(campaignType);
        ArrayList campaignList =Postern.getCampaignDTOByCondition(campaignType,custType,openSourceType,csiType,null);//null 为county
        Iterator  campaignIter =campaignList.iterator();
        while (campaignIter.hasNext()){
              CampaignDTO  campaignDto =(CampaignDTO)campaignIter.next();
              String strnewpackageIds =Postern.getpackagecondByCampaignID(campaignDto.getCampaignID());
              pageContext.setAttribute("oneline",campaignDto);
      %>
         <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
           <% String clickName ="campaignchoose('"+strnewpackageIds+"')";   %>
           <td align="center" >
              <input type="hidden" name="sub_listID4" value ="<%=strnewpackageIds%>" >
              <tbl:checkbox name="listID4"  value="oneline:campaignID" match="GeneralCampaignList"  multipleMatchFlag="true" onclick="<%=clickName%>" />
           </td>
           <td align="center" ><tbl:write name="oneline" property="campaignName" /></td>
           <td align="center" >从<tbl:writedate name="oneline" property="DateFrom" />到<tbl:writedate name="oneline" property="DateTo" /></td>
           <td align="center"><tbl:write name="oneline" property="Description" /></td>
         </tbl:printcsstr>
      <%
         }
      %>
</table>
