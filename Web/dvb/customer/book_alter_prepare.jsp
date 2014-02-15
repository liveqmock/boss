<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.NewCustomerInfoDTO,
                 com.dtv.oss.dto.ProductPackageDTO,
                 com.dtv.oss.dto.CampaignDTO,
                 com.dtv.oss.dto.NewCustomerMarketInfoDTO" %>
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

<form name="frmPost" method="post" action="book_alter.screen" >
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">      
<%
    CustServiceInteractionWrap wrap = (CustServiceInteractionWrap)pageContext.getAttribute("oneline");
    
    pageContext.setAttribute("oneline", wrap.getCsiDto());
System.out.println("getAgentName=============="+wrap.getCsiDto().getAgentName());
    pageContext.setAttribute("onecust", wrap.getNciDto());
    pageContext.setAttribute("custaddr", wrap.getCustAddrDto());
    pageContext.setAttribute("oneacct", wrap.getNcaiDto());
    pageContext.setAttribute("acctaddr", wrap.getAcctAddrDto());
    
    String strOpenCampaignList = "";
    String strMutiProductList = "";
    String strSingleProductList = "";
    String strGeneralCampaignList = "";
    
    String txtServiceCodeList = wrap.getCsiDto().getServiceCodeList();
    if(txtServiceCodeList == null || "null".equals(txtServiceCodeList))
    {
    	txtServiceCodeList = "";
    }
       
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
    if (wrap.getCsiDto().getGroupCampaignID()!=0)
    {
        com.dtv.oss.dto.GroupBargainDetailDTO gbdDto = Postern.getGroupBargainDetailByID(wrap.getCsiDto().getGroupCampaignID());
        if (gbdDto!=null) strGroupDetailNo = gbdDto.getDetailNo();
    }
    if (strGroupDetailNo == null) strGroupDetailNo = "";
    
    if (WebUtil.StringHaveContent(strGroupDetailNo))
    {
        strOpenCampaignList = "";
        strMutiProductList = "";
        strSingleProductList = "";
        strGeneralCampaignList = "";
    }

    int customerId =((NewCustomerInfoDTO)wrap.getNciDto()).getId();
    
    
    //得到所有产品包：
 String allProductList = "";
if (!strGroupDetailNo.equals("")){
     ArrayList ProductPackagelist = Postern.getProductPackageDTOByGroupDetail(strGroupDetailNo);
     Iterator currentPackageIter=ProductPackagelist.iterator();
     System.out.println("ProductPackagelist==============="+ProductPackagelist);
     while(currentPackageIter.hasNext()){
           ProductPackageDTO dto =(ProductPackageDTO)currentPackageIter.next();
           allProductList =allProductList+ dto.getPackageID()+";";     
     }
}
else
{

if(strOpenCampaignList != null && strOpenCampaignList.length()>0 && ';'==strOpenCampaignList.charAt(strOpenCampaignList.length()-1))
	strOpenCampaignList = strOpenCampaignList.substring(0,strOpenCampaignList.length()-1);
String campaignPackageList = Postern.getBundle2CampaignPackageColStr(strOpenCampaignList);

	allProductList = campaignPackageList+strSingleProductList+strMutiProductList;
System.out.println("allProductList=============="+allProductList);
}
if(allProductList != null && allProductList.length()>0 && ';'==allProductList.charAt(allProductList.length()-1))
	allProductList = allProductList.substring(0,allProductList.length()-1);

//T_Service.ServiceID == 3，语音业务，需要选择电话号码 要提供电话号码的修改
String oldHasPhoneNo = "n";
String strServiceIDs = Postern.getServiceIDByProductPackageIDs(allProductList);
int n=0;
String itemID="";
if(strServiceIDs != null && !("".equals(strServiceIDs)))
{
	String serviceIDs[] = strServiceIDs.split(";");
	for(int i=0;i<serviceIDs.length;i++)
	{
		if("3".equals(serviceIDs[i]))
			oldHasPhoneNo = "y";
	}
}

System.out.println("getAgentName=============="+wrap.getCsiDto().getAgentName());
   
%>          
     <input type="hidden" name="txtGroupBargainDetailNo"  value="<%=strGroupDetailNo%>"  >
     <tbl:hidden name="txtCatvID" value="onecust:CatvID" />
     <tbl:hidden name="txtOpenSourceType" value="onecust:OpenSourceType" />
     <tbl:hidden name="txtOpenSourceID" value="onecust:OpenSourceID" />
     <input type="hidden" name="txtPreferedDate" value="<tbl:writedate name="oneline" property="PreferedDate" />"  >
     <tbl:hidden name="txtPreferedTime" value="oneline:PreferedTime" />
     <input type="hidden" name="txtCustomerId" value="<%=customerId%>" />
     <tbl:hidden name="txtCsiId" value="oneline:Id" />
     <tbl:hidden name="txtCsiStatus" value="oneline:Status" />
     	
     <tbl:hidden name="txtCsiType" value="oneline:Type" />
     <tbl:hidden name="txtCustType" value="onecust:Type" />

     <tbl:hidden name="txtAgentName" value="oneline:agentName" />
     <tbl:hidden name="txtAgentTelephone" value="oneline:agentTelephone" />
     <tbl:hidden name="txtAgentCardType" value="oneline:agentCardType" />
     <tbl:hidden name="txtAgentCardId" value="oneline:agentCardID" />

     <input type="hidden" name="txtCsiDtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />" >
     <input type="hidden" name="txtNewCustomerDtLastmod"   value="<tbl:write name="onecust" property="DtLastmod" />"    >
     <input type="hidden" name="txtCustAddrLastmod" value="<tbl:write name="custaddr" property="DtLastmod" />" >
     <input type="hidden" name="txtNewAcctLastmod"  value="<tbl:write name="oneacct" property="DtLastmod" />" >
     <input type="hidden" name="txtAcctAddrLastmod" value="<tbl:write name="acctaddr" property="DtLastmod" />" >
     
     <tbl:hidden name="txtAccountType" value="oneacct:AccountType" />
     <tbl:hidden name="txtAddressFlag" value="oneacct:AddressFlag" />
   
     <input type="hidden" name="txtName" value="<tbl:write name="onecust" property="name" />"  > 
     <input type="hidden" name="txtGender"  value="<tbl:write name="onecust" property="Gender" />"  >
     <tbl:hidden name="txtType" value="onecust:type" />
     <input type="hidden" name="txtSocialSecCardID" value="<tbl:write name="onecust" property="SocialSecCardID"/>"/>
     <input type="hidden" name="txtBirthday" value="<tbl:writedate name="onecust" property="Birthday" />"  >
     <tbl:hidden name="txtCardType" value="onecust:cardType" />     
     <input type="hidden" name="txtCardID" value="<tbl:write name="onecust" property="cardID"/>"/>
     <tbl:hidden name="txtOccupation" value="onecust:Occupation" />
     <input type="hidden" name="txtEmail" value="<tbl:write name="onecust" property="Email"/>"/>
     <input type="hidden" name="txtTelephone" value="<tbl:write name="onecust" property="Telephone" />"  >
     <input type="hidden" name="txtMobileNumber" value="<tbl:write name="onecust" property="MobileNumber" />"  >
     <tbl:hidden name="txtOrgID" value="onecust:OrgID" />
     <tbl:hidden name="txtNationality" value="onecust:nationality" />
     <tbl:hidden name="txtFaxNumber" value="onecust:faxNumber" />
     <tbl:hidden name="txtCounty" value="custaddr:DistrictID" />
     <tbl:hidden name="txtbillCounty" value="acctaddr:DistrictID" />
     <tbl:hidden name="txtStreetStationID" value="onecust:StreetStationID" />
     <tbl:hidden name="txtCsiCreateReason" value="oneline:createReason" />
     <tbl:hidden name="txtBirthday" value="onecust:birthday" />
     <input type="hidden" name="txtComments" value="<tbl:write name="oneline" property="Comments" />"  >
      	 
     <input type="hidden" name="txtDetailAddress" value="<tbl:write name="custaddr" property="detailAddress" />"  >
     <input type="hidden" name="txtPostcode" value="<tbl:write name="custaddr" property="postcode" />"  >
     <input type="hidden" name="txtAddressId" value="<tbl:write name="custaddr" property="AddressID" />" >   
	
     <input type="hidden" name="txtBillPostcode" value="<tbl:write name="acctaddr" property="Postcode" />"  >
     <input type="hidden" name="txtBillDetailAddress" value="<tbl:write name="acctaddr" property="DetailAddress" />"  >    
     <input type="hidden" name="txtBillAddressId" value="<tbl:write name="custaddr" property="AddressID" />" >    
     <tbl:hidden name="txtMopID" value="oneacct:MopID" />
     <tbl:hidden name="txtAcctID" value="oneacct:Id" />
     <input type="hidden" name="txtBankAccountName" value="<tbl:write name="oneacct" property="BankAccountName" />"  >
     <input type="hidden" name="txtBankAccount" value="<tbl:write name="oneacct" property="BankAccount" />"  >
     <input type="hidden" name="txtAccountName" value="<tbl:write name="oneacct" property="AccountName" />"  >
      
     <input type="hidden" name="OpenCampaignList" value="<%=strOpenCampaignList%>" >
     <input type="hidden" name="MutiProductList" value="<%=strMutiProductList%>" >	 
     <input type="hidden" name="SingleProductList" value="<%=strSingleProductList%>" >	 
     <input type="hidden" name="GeneralCampaignList" value="<%=strGeneralCampaignList%>" >
     
     <input type="hidden" name="txtServiceCodeList" value="<%=txtServiceCodeList%>" >
     <input type="hidden" name="oldHasPhoneNo" value="<%=oldHasPhoneNo%>" >	
     
      

    

   </lgc:bloop> 
</form>

<Script language=Javascript>
		document.frmPost.submit();   
</Script>