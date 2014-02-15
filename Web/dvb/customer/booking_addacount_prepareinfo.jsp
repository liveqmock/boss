<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.ProductPackageDTO,
                 com.dtv.oss.dto.CustServiceInteractionDTO " %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%		
    String strOpenCampaignList = "";
    String strMutiProductList = "";
    String strSingleProductList = "";
    String strGeneralCampaignList = "";
%>
 
<form name="frmPost" method="post" action="">  
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
   	CustServiceInteractionDTO csidto = (CustServiceInteractionDTO)pageContext.getAttribute("oneline");
   	
    Collection lst = CsrBusinessUtility.getPackagesByCSIID(csidto.getId());
    Iterator it = lst.iterator();
    while (it.hasNext()){
         Integer iId = (Integer)it.next();
         ProductPackageDTO dto =Postern.getProductPackageByPackageID(iId.intValue());
         if ("S".equals(dto.getPackageClass())){
            strSingleProductList =strSingleProductList +dto.getPackageID()+";";
         } else {
        	  strMutiProductList =strMutiProductList +dto.getPackageID()+";";
         }
    }
    
    Collection lst1 = CsrBusinessUtility.getCampaignsByCSIID(csidto.getId());   
    Iterator it1 = lst1.iterator();
    while (it1.hasNext()){
         Integer iId = (Integer)it1.next();
         CampaignDTO dto =Postern.getCampaignDTOByCampaignID(iId.intValue());
         if (CommonKeys.CAMPAIGNCAMPAIGNTYPE_OPEN.equals(dto.getCampaignType())) {
            strOpenCampaignList =strOpenCampaignList +dto.getCampaignID() +";" ;
         } else {
        	  strGeneralCampaignList =strGeneralCampaignList +dto.getCampaignID() +";" ;
         }   
    }
    
    String txtServiceCodeList = "";
   if(csidto != null){
   	 txtServiceCodeList = csidto.getServiceCodeList();
   }
   if(txtServiceCodeList == null)txtServiceCodeList = "";
   
   CustomerDTO  custDto =Postern.getCustomerByID(csidto.getCustomerID());
%>

<input type="hidden" name="txtContactPerson" value="<tbl:write name="oneline" property="contactPerson" />" >
<input type="hidden" name="txtContactPhone" value="<tbl:write name="oneline" property="contactPhone" />" >
<input type="hidden" name="txtPreferedDate" value="<tbl:writedate name="oneline" property="preferedDate" />" >
<input type="hidden" name="txtPreferedTime" value="<tbl:write name="oneline" property="preferedTime" />" >
<input type="hidden" name="txtAccount" value="<tbl:write name="oneline" property="accountID" />" >
<input type="hidden" name="txtCustomerID" value="<tbl:write name="oneline" property="customerID" />">
<input type="hidden" name="txtID" value="<tbl:write name="oneline" property="id" />">
<input type="hidden" name="txtCsiDtLastMod" value="<tbl:write name="oneline" property="dtLastmod" />" >
<input type="hidden" name="txtStatus" value="<tbl:write name="oneline" property="status" />" >
<input type="hidden" name="txtCustType" value="<%=(custDto.getCustomerType()==null) ? "" :custDto.getCustomerType()%>" >
<input type="hidden" name="txtOpenSourceType" value="<%=(custDto.getOpenSourceType()==null) ? "" :custDto.getOpenSourceType()%>" >
<input type="hidden" name="txtCsiType" value="<tbl:write name="oneline" property="type" />" >
<input type="hidden" name="txtCsiCreateReason" value="<tbl:write name="oneline" property="createReason" />" >

<input type="hidden" name="OpenCampaignList" value="<%=strOpenCampaignList%>" >
<input type="hidden" name="MutiProductList" value="<%=strMutiProductList%>" >	 
<input type="hidden" name="SingleProductList" value="<%=strSingleProductList%>" >	 
<input type="hidden" name="GeneralCampaignList" value="<%=strGeneralCampaignList%>" >	

<input type="hidden" name="txtServiceCodeList" value="<%=txtServiceCodeList%>" >
</lgc:bloop>
</form>
<Script language=Javascript>
   document.frmPost.action ="booking_addacount_info.screen";
   document.frmPost.submit(); 
</Script>