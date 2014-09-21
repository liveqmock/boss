<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ page import="com.dtv.oss.dto.CampaignDTO"%>

<%@ page
	import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys,java.util.*"%>

<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">

	<tr class="list_head">
		<td width="10%" class="list_head">
			&nbsp;
		</td>
		<td width="20%" class="list_head" align="center">
			开户套餐活动名称
		</td>
		<td width="20%" class="list_head" align="center">
			套餐活动时间
		</td>
		<td width="50%" class="list_head" align="center">
			套餐活动具体内容
		</td>
	</tr>
	<%
		String custType = request.getParameter("txtCustType");
		String openSourceType = request.getParameter("txtOpenSourceType");
		String openFlag = request.getParameter("OpenFlag");
		String county = request.getParameter("txtCounty");
		String csiType = request.getParameter("txtCsiType");

		if (openFlag != null) {
			if (openFlag.equals(CommonKeys.ACTION_OF_BOOKING)
			|| openFlag.equals(CommonKeys.ACTION_OF_BOOKINGAGENT))
				csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK;
			else if (openFlag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT))
				csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_OB;
			else if (openFlag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT))
				csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS;
		}

		//String campaignType ="'"+ CommonKeys.CAMPAIGNCAMPAIGNTYPE_GROUPBARGAIN_OR_OPEN+"','"+CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN+"'";
		String campaignType = "'" + CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN
				+ "'";
		ArrayList campaignList = Postern.getCampaignDTOByCondition(
				campaignType, custType, openSourceType, csiType, null);//null 为county
		//ArrayList campaignList =Postern.getCampaignDTO(campaignType);
		Iterator campaignIter = campaignList.iterator();
		while (campaignIter.hasNext()) {
			CampaignDTO campaignDto = (CampaignDTO) campaignIter.next();
//			String strnewpackageIds = Postern
//			.getpackagecondByCampaignID(campaignDto.getCampaignID());
			String strnewpackageIds = Postern
			.getBundle2CampaignPackageColStr(campaignDto.getCampaignID()+"");
			
			pageContext.setAttribute("oneline", campaignDto);
	%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
		<%
					String clickName = "campaignchoose('" + strnewpackageIds
					+ "')";
		%>
		<td align="center">
			<input type="hidden" name="sub_listID1" value="<%=strnewpackageIds%>">
			<tbl:checkbox name="listID1" value="oneline:campaignID" match="OpenCampaignList" multipleMatchFlag="true"
				onclick="<%=clickName%>" />
		</td>
		<td align="center">
			<input type="hidden" name="listBundleName" value="<tbl:write name="oneline" property="campaignName" />">
			<tbl:write name="oneline" property="campaignName" />
		</td>
		<td align="center">
			从
			<tbl:writedate name="oneline" property="DateFrom" />
			到
			<tbl:writedate name="oneline" property="DateTo" />
		</td>
		<td align="center">
			<tbl:write name="oneline" property="Description" />
		</td>
	</tbl:printcsstr>
	<%
	}
	%>
  <tr>
    <td colspan="4" class="list_foot"></td>
  </tr>	
</table>





