<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO,
                 com.dtv.oss.dto.CustomerDTO,
								 com.dtv.oss.dto.CustServiceInteractionDTO,
                 com.dtv.oss.dto.ProductPackageDTO " %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%		
    String  txtID =request.getParameter("txtID");  
    String strOpenCampaignList = "";
    String strMutiProductList = "";
    String strSingleProductList = "";
    String strGeneralCampaignList = "";
    
    Collection lst = CsrBusinessUtility.getPackagesByCSIID(WebUtil.StringToInt(txtID));
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
    
    Collection lst1 = CsrBusinessUtility.getCampaignsByCSIID(WebUtil.StringToInt(txtID));   
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
    
   CustServiceInteractionDTO csiDto = Postern.getCsiDTOByCSIID(WebUtil.StringToInt(txtID));
   String txtServiceCodeList = "";
   if(csiDto != null)
   {
   	 txtServiceCodeList = csiDto.getServiceCodeList();
   }
   if(txtServiceCodeList == null)txtServiceCodeList = "";
   
   String txtCustomerID =request.getParameter("txtCustomerID");
   CustomerDTO  custDto =Postern.getCustomerByID(WebUtil.StringToInt(txtCustomerID));
%>
<form name="frmPost" method="post" action="">
<input type="hidden" name="txtID" value="<tbl:writeparam name="txtID" />" > 
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />" > 
<input type="hidden" name="txtAccount" value="<tbl:writeparam name="txtAccount" />" > 

<input type="hidden" name="OpenCampaignList" value="<%=strOpenCampaignList%>" >
<input type="hidden" name="MutiProductList" value="<%=strMutiProductList%>" >	 
<input type="hidden" name="SingleProductList" value="<%=strSingleProductList%>" >	 
<input type="hidden" name="GeneralCampaignList" value="<%=strGeneralCampaignList%>" >	
<input type="hidden" name="txtCsiType" value="<%=csiDto.getType()%>" >
<input type="hidden" name="txtCustType" value="<%=custDto.getCustomerType()%>" >
<input type="hidden" name="txtOpenSourceType" value="<%=custDto.getOpenSourceType()%>" >
<input type="hidden" name="txtCsiCreateReason" value="<%=csiDto.getCreateReason()%>" >

<input type="hidden" name="txtServiceCodeList" value="<%=txtServiceCodeList%>" >
</form>
<Script language=Javascript>
   document.frmPost.action ="book_create_account.screen";
   document.frmPost.submit(); 
</Script>