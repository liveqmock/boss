<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.NewCustomerInfoDTO,
                 com.dtv.oss.dto.ProductPackageDTO,
                 com.dtv.oss.dto.CampaignDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.NewCustomerMarketInfoDTO,
                 com.dtv.oss.dto.CustServiceInteractionDTO" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>


<table width="98%" border="0" cellspacing="5" cellpadding="5">
<tr> 
  <td width="100%"><div align="center"> 
      <p align="center" class="title1">正在获取预约信息，请稍候。。。</strong></p>
    </div></td>
  
</tr>
</table>

<form name="frmPost" method="post" action="book_product_modify.screen" >
<% CustServiceInteractionDTO csidto=Postern.getCsiDTOByCSIID(WebUtil.StringToInt(request.getParameter("txtCSIID")));
    String strOpenCampaignList = "";
    String strMutiProductList = "";
    String strSingleProductList = "";
    String strGeneralCampaignList = "";
  Collection lst = CsrBusinessUtility.getPackagesByCSIID(csidto.getId());
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
    
    lst = CsrBusinessUtility.getCampaignsByCSIID(csidto.getId());   
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
    
    
   CustomerDTO custDTO = null;
	 String custType = null;
   String openSourceType = null;
   String txtCsiType = null;
   String customerID = request.getParameter("txtCustomerID");
   if(customerID != null)
   {
    custDTO = Postern.getCustomerByID(WebUtil.StringToInt(customerID));
    custType = custDTO.getCustomerType();
    openSourceType = custDTO.getOpenSourceType();
   }
   txtCsiType = csidto.getType();
   
%>  
     <input type="hidden" name="txtContactPerson"  value="<%=csidto.getContactPerson()%>" >
     <input type="hidden" name="txtContactPhone"  value="<%=csidto.getContactPhone()%>"  >
     <input type="hidden" name="txtScheduleTime" value="<%=WebUtil.TimestampToString(csidto.getScheduleTime(),"yyyy-MM-dd")%>" />
     <input type="hidden" name="txtAccount" value="<%=csidto.getAccountID()%>">
     
     <%int said=Postern.getServiceAccountIDByCSIID(csidto.getId());%>
     <input type="hidden" name ="txtServiceAccountID" value="<%=said%>">
     <input type="hidden" name ="txtID" value="<%=csidto.getId()%>">
     <input type="hidden" name="txtDtLastmod" value="<%=csidto.getDtLastmod()%>" />
    
     <input type="hidden" name="MutiProductList" value="<%=strMutiProductList%>" >	 
     <input type="hidden" name="SingleProductList" value="<%=strSingleProductList%>" >	 
     <input type="hidden" name="GeneralCampaignList" value="<%=strGeneralCampaignList%>" >
     
      <input type="hidden" name="txtCustType" value="<%=custType%>">
	 		<input type="hidden" name="txtOpenSourceType" value="<%=openSourceType%>">
	 		<input type="hidden" name="txtCsiType" value="<%=txtCsiType%>">
     
     <input type="hidden" name="txtCsiCreateReason" value="<%=csidto.getCreateReason()%>">
     <tbl:hiddenparameters pass="txtAccount/txtCSIID/txtCustomerID/txtDistrictID" />	 
</form>

<Script language=Javascript>
  document.frmPost.submit();   
</Script>