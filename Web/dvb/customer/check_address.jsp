   <%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
   <%@ taglib uri="osstags" prefix="d" %>
   <%@ page import="java.util.*" %>
   <%@ page import="com.dtv.oss.dto.CustomerDTO" %>
   <%@ page import="com.dtv.oss.util.Postern" %>
   <%@ page import="com.dtv.oss.web.util.CommonKeys" %>
   <%@ page import="com.dtv.oss.dto.ProductPackageDTO" %>

   
   <%
     String open_flag =request.getParameter("OpenFlag");
     String txtGroupBargainDetailNo =(request.getParameter("txtGroupBargainDetailNo") ==null ) ? "" :request.getParameter("txtGroupBargainDetailNo") ;
   	System.out.println("open_flag="+open_flag);
   	System.out.println("txtServiceCodeList="+request.getParameter("txtServiceCodeList"));
   %>
      
   <Script language=JavaScript>
     var open_flag =<%=open_flag%>;
     var booking =<%=CommonKeys.ACTION_OF_BOOKING%>; 
     var bookingAccount =<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>;
     var shopAccount =<%=CommonKeys.ACTION_OF_SHOPACCOUNT%>;
     var bookingAgent =<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>;
     var groupBargainDetailNo ='<%=txtGroupBargainDetailNo%>';
     function check_frm(){
     	  if (groupBargainDetailNo ==''){
     	     if (add_openCampaign()){
	             add_productAndGeneralCampaign();
	             document.frm_Post.ProductList.value=document.frm_Post.MutiProductList.value+document.frm_Post.SingleProductList.value;
	             document.frm_Post.CampaignList.value=document.frm_Post.OpenCampaignList.value+document.frm_Post.GeneralCampaignList.value;
	          }else{
	          	 return false;
	          }
	      }
 	      return true;
     }
     
     function frm_submit() {
	     if (check_frm()) {
	       if (open_flag ==booking || open_flag ==bookingAgent){
	         //document.frm_Post.action ="open_confirm.do" ;
	          document.frm_Post.action ="book_select_phone_number.do" ;
	       } else {
	          document.frm_Post.action="open_create_product_terminate_device.do";
	       }
	       document.frm_Post.submit();
	     }
     }
     
    function back_submit() {
       if (document.frm_Post.MutiProductList && open_flag !=bookingAccount) {
     	     document.frm_Post.MutiProductList.value ="";
     	     document.frm_Post.SingleProductList.value ="";
     	     document.frm_Post.OpenCampaignList.value ="";
     	     document.frm_Post.GeneralCampaignList.value ="";
     	  }
	      document.frm_Post.action="open_create_info.do?OpenFlag="+open_flag;
 	      document.frm_Post.submit();
     }
        
     function view_package_detail(strID,strName){
       window.open('list_product_package_detail.do?txtPackageID='+strID+'&txtPackageName='+strName,'','toolbar=no,location=no,status=no,menubar=no,scrollbar=no,width=330,height=250');
     }
   </Script>
   <form name="frm_Post" method="post" action="" >
   <%   
     //获得用户详细地址校验信息
    java.util.List checkDetailAddressList = (java.util.List)Postern.getCheckDetailAddress(request.getParameter("txtDetailAddress"));
   %>
      <table align="center" width="780" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <%
         if(checkDetailAddressList != null){
      %> 
        <tr> 
            <td colspan="3" class="import_tit" align="left"><font size="3">该详细地址曾用于以下用户：</font></td>
        </tr>
        <tr class="list_head"> 
            <td width="33%" align="center" class="list_head">用户证号</td>
            <td width="33%" align="center" class="list_head">用户类型</td>
            <td width="33%" align="center" class="list_head">用户状态</td>
        </tr>
       <%
        for(int i=0;i<checkDetailAddressList.size();i++){
            pageContext.setAttribute("customerMSG", (CustomerDTO)checkDetailAddressList.get(i));
       %>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
          <td width="33%" align="center"><font size="2">
              <tbl:write name="customerMSG" property="CustomerID"/>
          </font></td>
          <td width="33%" align="center"><font size="2">
              <d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="customerMSG:customerType" />
          </font></td>
          <td width="33%" align="center"><font size="2">
              <d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="customerMSG:status" />
          </font></td>
        </tbl:printcsstr>
    <%
        }
     } else {
     
    /**
      *  <tr> 
      *     <td class="import_tit" width="100%"  align="center"> 
      *      该详细地址(<tbl:writeparam name="txtDetailAddress" />)没有用于现有用户(<tbl:writeparam name="txtName" />)
      *     </td>
      *  </tr>
      */

     }
    %>
    </table>
    
    <%   
     //获得许可证号校验信息
    java.util.List checkCatvidList = (java.util.List)Postern.getCheckCatvid(request.getParameter("txtCatvID"));
   %>
      <table align="center" width="780" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <%
         if(checkCatvidList != null){
      %> 
        <tr> 
            <td colspan="3" class="import_tit" align="left"><font size="3">该许可证号曾用于以下用户：</font></td>
        </tr>
        <tr class="list_head"> 
            <td width="33%" align="center" class="list_head">用户证号</td>
            <td width="33%" align="center" class="list_head">用户类型</td>
            <td width="33%" align="center" class="list_head">用户状态</td>
        </tr>
       <%
        for(int i=0;i<checkCatvidList.size();i++){
            pageContext.setAttribute("customerMSG", (CustomerDTO)checkCatvidList.get(i));
       %>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
          <td width="33%" align="center"><font size="2">
              <tbl:write name="customerMSG" property="CustomerID"/>
          </font></td>
          <td width="33%" align="center"><font size="2">
              <d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="customerMSG:customerType" />
          </font></td>
          <td width="33%" align="center"><font size="2">
              <d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="customerMSG:status" />
          </font></td>
        </tbl:printcsstr>
    <%
        }
     } 
    %>
    </table>
    
      
    
      <tbl:hiddenparameters pass="txtCatvID/txtGroupBargainDetailNo/txtReferBookingSheetID/txtCsiCreateReason/txtCsiType" />
      <tbl:hiddenparameters pass="txtCustomerSerialNo/txtCustType/txtGender/txtName/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtEmail" />
      <tbl:hiddenparameters pass="txtCounty/txtDetailAddress/txtPostcode/txtOpenSourceType/txtOpenSourceID/txtComments/txtLoginID/txtLoginPwd" />
      <tbl:hiddenparameters pass="txtPreferedTime/txtPreferedDate/txtSocialSecCardID/txtNationality/txtFaxNumber" />
      <tbl:hiddenparameters pass="txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName"  />
      <tbl:hiddenparameters pass="txtContractNo/txtBuyMode/OpenFlag/txtIsInstall/txtBirthday/txtForceDepostMoney/txtServiceCodeList/phoneNo" />
			<tbl:hiddenparameters pass="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>"/>
      <tbl:hiddenparameters pass="txtAgentName/txtAgentTelephone/txtAgentCardType/txtAgentCardId" />
				
      <tbl:dynamicservey serveyType="M"  showType="hide" /> 
      <input type="hidden" name="ProductList" value="">
      <input type="hidden" name="CampaignList" value="">
      <input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_PRODUCTPG_CAMPAINPG%>" >

    <%
      	String txtOpenSourceType =(request.getParameter("txtOpenSourceType") ==null) ? "" :request.getParameter("txtOpenSourceType"); 
        if (txtGroupBargainDetailNo.equals("")) {
    %>
        <table width="780" border="0" cellspacing="5" cellpadding="5">
          <tr>
            <td align="center" class="import_tit">
              <font size="3"><strong>产品包选择</strong></font>
            </td>
          </tr>
         </table>
         <%
            request.setAttribute("openCampaignFlag","true"); 
         %>
         <jsp:include page="/catch/list_campaignAndproduct.jsp" />
         
    <%
         } else  {    
    %>
       <table align="center" width="780" border="0" cellspacing="1" cellpadding="3" class="list_bg">
          <tr> 
            <td class="import_tit" width="98%" colspan="5"  align="center">团购的产品信息</td>
          </tr>
          <tr class="list_head"> 
            <td  align="center" class="list_head">产品包ID</td>
            <td  align="center" class="list_head">产品包名称</td>
            <td  align="center" class="list_head">产品包描述</td>
            <td  align="center" class="list_head">有效起始时间</td>
            <td  align="center" class="list_head">有效截止时间</td>
          </tr>

        <%
           ArrayList list = Postern.getProductPackageDTOByGroupDetail(txtGroupBargainDetailNo);
           Iterator currentPackageIter=list.iterator();
           while(currentPackageIter.hasNext()){
               ProductPackageDTO dto =(ProductPackageDTO)currentPackageIter.next();
               request.setAttribute("oneline",dto);
        %>
   
           <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
              <td align="center" >
                <a href="javascript:view_package_detail('<tbl:write name="oneline" property="packageID"/>','<tbl:write name="oneline" property="packageName" />')" class="link12" ><tbl:writenumber name="oneline" property="packageID" digit="7" /></a>
              </td>
              <td align="center" ><tbl:write name="oneline" property="packageName"/></td>
              <td align="center" ><tbl:write name="oneline" property="description"/></td>
              <td align="center" ><tbl:writedate name="oneline" property="dateFrom"/></td>
              <td align="center" ><tbl:writedate name="oneline" property="dateTo"/></td>  
            </tbl:printcsstr>
        <%
           } 
        %>
        </table>
     <%
         }
     %>
     
  </form>  
      
      <table align="center" border="0" cellspacing="0" cellpadding="0" >
        <tr><td height="20">&nbsp;</td></tr>
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
           <td width="20" ></td>
           <td><img src="img/button_l.gif" width="11" height="20"></td>
           <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>
        </tr>
        
      </table>
 

   
