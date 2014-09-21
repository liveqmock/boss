<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.NewCustomerMarketInfoDTO,
                 com.dtv.oss.dto.NewCustomerInfoDTO,
                 com.dtv.oss.dto.CampaignDTO,
                 com.dtv.oss.dto.ProductPackageDTO " %>

<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.CustServiceInteractionWrap" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>



<table width="98%" border="0" cellspacing="5" cellpadding="5">
<tr> 
  <td width="100%"><div align="center"> 
      <p align="center" class="title1">正在获取预约信息，请稍候。。。</strong></p>
    </div></td>
  
</tr>
</table>
      
<form name="frmPost" method="post" action="" >
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">      
<%
    CustServiceInteractionWrap wrap = (CustServiceInteractionWrap)pageContext.getAttribute("oneline");
    System.out.println("::::::::===phone="+wrap.getCsiDto().getServiceCodeList());
    pageContext.setAttribute("oneline", wrap.getCsiDto());
    pageContext.setAttribute("onecust", wrap.getNciDto());
    pageContext.setAttribute("custaddr", wrap.getCustAddrDto());
    pageContext.setAttribute("oneacct", wrap.getNcaiDto());
    pageContext.setAttribute("acctaddr", wrap.getAcctAddrDto());
    
    String strOpenCampaignList = "";
    String strMutiProductList = "";
    String strSingleProductList = "";
    String strGeneralCampaignList = "";
       
    Collection lst = CsrBusinessUtility.getPackagesByCSIID(wrap.getCsiDto().getId());
    Iterator it = lst.iterator();
    
    while (it.hasNext())
    {
        Integer iId = (Integer)it.next();
        ProductPackageDTO dto =Postern.getProductPackageByPackageID(iId.intValue());
        if ("S".equals(dto.getPackageClass())){
            strSingleProductList =strSingleProductList +dto.getPackageID()+";";
        } else {
        	  strMutiProductList =strMutiProductList +dto.getPackageID()+";";
        }
    }
    
    lst = CsrBusinessUtility.getCampaignsByCSIID(wrap.getCsiDto().getId());   
    it = lst.iterator();
    while (it.hasNext())
    {
        Integer iId = (Integer)it.next();
        CampaignDTO dto =Postern.getCampaignDTOByCampaignID(iId.intValue());
        if (CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN.equals(dto.getCampaignType())) {
           strOpenCampaignList =strOpenCampaignList +dto.getCampaignID() +";" ;
        } else {
        	 strGeneralCampaignList =strGeneralCampaignList +dto.getCampaignID() +";" ;
        }   
    }
    
    String strGroupDetailNo = null;
    if (wrap.getCsiDto().getGroupCampaignID()!=0){
        com.dtv.oss.dto.GroupBargainDetailDTO gbdDto = Postern.getGroupBargainDetailByID(wrap.getCsiDto().getGroupCampaignID());
        if (gbdDto!=null) strGroupDetailNo = gbdDto.getDetailNo();
    }
    if (strGroupDetailNo == null) strGroupDetailNo = "";
    
    if (WebUtil.StringHaveContent(strGroupDetailNo)){
        strOpenCampaignList = "";
        strMutiProductList = "";
        strSingleProductList = "";
        strGeneralCampaignList = "";
    }

    int customerId =((NewCustomerInfoDTO)wrap.getNciDto()).getId();
      
%>          
     <input type="hidden" name="txtGroupBargainDetailNo"  value="<%=strGroupDetailNo%>"  >
     <tbl:hidden name="txtCatvID" value="onecust:CatvID" />
     <tbl:hidden name="txtOpenSourceType" value="onecust:OpenSourceType" />
     <tbl:hidden name="txtOpenSourceID" value="onecust:OpenSourceID" />
     <input type="hidden" name="txtPreferedDate" value="<tbl:writedate name="oneline" property="PreferedDate" />"  >
     <tbl:hidden name="txtPreferedTime" value="oneline:PreferedTime" />
     <tbl:hidden name="txtReferBookingSheetID" value="oneline:Id" />
     <tbl:hidden name="txtCsiStatus" value="oneline:Status" />
     <tbl:hidden name="txtCsiCreateReason" value="oneline:createReason" />
     <tbl:hidden name="txtAccountType" value="oneacct:AccountType" />
     <tbl:hidden name="txtAddressFlag" value="oneacct:AddressFlag" />
     	
     <tbl:hidden name="txtAgentName" value="oneline:agentName" />
     <tbl:hidden name="txtAgentTelephone" value="oneline:agentTelephone" />
     <tbl:hidden name="txtAgentCardType" value="oneline:agentCardType" />
     <tbl:hidden name="txtAgentCardId" value="oneline:agentCardID" />     	
     
     <input type="hidden" name="txtBirthday" value="<tbl:writedate name="onecust" property="Birthday" />" >    
     <input type="hidden" name="txtBirthday" value="<tbl:writedate name="onecust" property="Birthday" />"  >
     <input type="hidden" name="txtNationality" value="<tbl:write name="onecust" property="nationality" />" >
     <input type="hidden" name="txtFaxNumber" value="<tbl:write name="onecust" property="faxNumber" />" >
     <input type="hidden" name="txtName" value="<tbl:write name="onecust" property="name" />"  > 
     <input type="hidden" name="txtGender"  value="<tbl:write name="onecust" property="Gender" />"  >
     <tbl:hidden name="txtCustType" value="onecust:Type" />
     <input type="hidden" name="txtSocialSecCardID" value="<tbl:write name="onecust" property="SocialSecCardID"/>"/>
     <tbl:hidden name="txtCardType" value="onecust:cardType" />     
     <input type="hidden" name="txtCardID" value="<tbl:write name="onecust" property="cardID"/>"/>
     <tbl:hidden name="txtOccupation" value="onecust:Occupation" />
     <input type="hidden" name="txtEmail" value="<tbl:write name="onecust" property="Email"/>"/>
     <input type="hidden" name="txtTelephone" value="<tbl:write name="onecust" property="Telephone" />"  >
     <input type="hidden" name="txtMobileNumber" value="<tbl:write name="onecust" property="MobileNumber" />"  >
     <input type="hidden" name="txtLoginID" value="<tbl:write name="onecust" property="loginID" />" >
     <input type="hidden" name="txtLoginPwd" value="<tbl:write name="onecust" property="loginPWD" />" >
      <tbl:hidden name="txtOrgID" value="onecust:OrgID" />
      <tbl:hidden name="txtCounty" value="custaddr:DistrictID" />
      <tbl:hidden name="txtbillCounty" value="acctaddr:DistrictID" />
      <tbl:hidden name="txtStreetStationID" value="onecust:StreetStationID" />
      <input type="hidden" name="txtComments" value="<tbl:write name="oneline" property="Comments" />"  >
      	 
     <input type="hidden" name="txtDetailAddress" value="<tbl:write name="custaddr" property="detailAddress" />"  >
     <input type="hidden" name="txtPostcode" value="<tbl:write name="custaddr" property="postcode" />"  >
     
	
     <input type="hidden" name="txtBillPostcode" value="<tbl:write name="acctaddr" property="Postcode" />"  >
     <input type="hidden" name="txtBillDetailAddress" value="<tbl:write name="acctaddr" property="DetailAddress" />"  >       
     <tbl:hidden name="txtMopID" value="oneacct:MopID" />
     <input type="hidden" name="txtBankAccountName" value="<tbl:write name="oneacct" property="BankAccountName" />"  >
     <input type="hidden" name="txtBankAccount" value="<tbl:write name="oneacct" property="BankAccount" />"  >
     <input type="hidden" name="txtAccountName" value="<tbl:write name="oneacct" property="AccountName" />"  >
      
      <input type="hidden" name="OpenCampaignList" value="<%=strOpenCampaignList%>" >
      <input type="hidden" name="MutiProductList" value="<%=strMutiProductList%>" >	 
      <input type="hidden" name="SingleProductList" value="<%=strSingleProductList%>" >	 
      <input type="hidden" name="GeneralCampaignList" value="<%=strGeneralCampaignList%>" >	 

      <input type="hidden" name="OpenFlag" value="<tbl:writeparam name="OpenFlag"/>" >
		 
		  <input type="hidden" name="ProductList" value="">
      <input type="hidden" name="CampaignList" value="">
      <input type="hidden" name="txtServiceCodeList" value="<tbl:write name="oneline" property="ServiceCodeList" />"  >
      

    <%
	     ArrayList marketInfoList =Postern.getCustMarketInfoByCustId(customerId);
       for (int i=0; i<marketInfoList.size(); i++){
	        NewCustomerMarketInfoDTO dto =(NewCustomerMarketInfoDTO)marketInfoList.get(i);
	        String marketInfoName ="txtDynamicServey_"+dto.getInfoSettingId();
	        int marketInfoValue =dto.getInfoValueId();
	        String infoValue="";
	        if(marketInfoValue==0){
	        	infoValue=dto.getMemo();
	        }else{
	        	infoValue=marketInfoValue+"";
	        }
	  %>
	  <input type="hidden" name="<%=marketInfoName%>" value="<%=infoValue%>" />
	  <%
	    } 	   
  %>
   </lgc:bloop> 
</form>

<Script language=Javascript>
    document.frmPost.action ="open_create_info.do" ;
    document.frmPost.ProductList.value=document.frmPost.MutiProductList.value+document.frmPost.SingleProductList.value;
	  document.frmPost.CampaignList.value=document.frmPost.OpenCampaignList.value+document.frmPost.GeneralCampaignList.value;
    document.frmPost.submit(); 
</Script>