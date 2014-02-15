<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.web.util.WebKeys,
                 com.dtv.oss.dto.DeviceClassDTO" %>
                 
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>

<%
    String productAttribute =(request.getAttribute(WebKeys.REQUEST_ATTRIBUTE) ==null) ? "0" : "1";

%>

<Script language=JavaScript>
<!--  

function back_submit(){
   product_attr ="<%=productAttribute%>";
   if (product_attr =="0"){
      document.frmPost.txtActionType.value="<%=CommonKeys.CHECK_PRODUCTPG_CAMPAINPG%>";
	    document.frmPost.action="open_create_product_terminate_device.do";
	 } else {
	 	  document.frmPost.txtActionType.value ="";
	    document.frmPost.action="check_address.do";
	 }
	 document.frmPost.submit();
}

function frm_next(csiPayOption){
	   	<pri:authorized name="manual_product_agreement_info.do" >
	    if(!oragnization_Param_agreement())
	        return false;
	    </pri:authorized>
	   	document.frmPost.txtcsiPayOption.value =csiPayOption;
	    if (check_fee()){
	       document.frmPost.action="open_create_product_pay.screen";
	    }else{
	 	     //document.frmPost.action="open_submit.do";
	 	     document.frmPost.action="open_confirm.do";
	 	     document.frmPost.txtConfirmBackFlag.value="caculatefee";
	 	     document.frmPost.confirm_post.value="false";
	    }
	    document.frmPost.submit();
}
function frm_finish(csiPayOption){
  	<pri:authorized name="manual_product_agreement_info.do" >
    if(!oragnization_Param_agreement())
        return false;
    </pri:authorized>
	 if (check_ProductProperty()){
	    document.frmPost.txtcsiPayOption.value =csiPayOption;
	    //document.frmPost.action="open_submit.do";
	    document.frmPost.action="open_confirm.do";
	    document.frmPost.txtConfirmBackFlag.value="caculatefee";
	    document.frmPost.confirm_post.value="false";
	    document.frmPost.submit();
	 }
}
-->
</Script>
<form name="frmPost" method="post" action="open_confirm.do" >     
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">开户－费用信息</td>
        </tr>
      </table>
      <br>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <br>
  
      <table align="center" width="820" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">用户信息</font></td>
         </tr>
      </table>   
      <table align="center" width="820" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
	      <tr> 
          <td width="17%" class="list_bg2" align="right">用户类型*</td>
          <td width="33%" class="list_bg1"><font size="2">
              <d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustType"   />
          </font></td>
          <td width="17%" class="list_bg2" align="right" >性别</td>
          <td width="33%" class="list_bg1"><font size="2">
              <d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="txtGender"   />
            </font></td>  
         </tr>
         <tr> 
          <td class="list_bg2" align="right" >姓名*</td>
          <td class="list_bg1"><font size="2">
              <tbl:writeparam name="txtName" />
              </font></td>  
          <td class="list_bg2" align="right" >出生日期</td>
          <td class="list_bg1"><font size="2">
              <tbl:writeparam name="txtBirthday" />
            </font></td>  
         </tr>
   <%
       String strGroupBargainDetailNo = request.getParameter("txtGroupBargainDetailNo");
       String lstProdPkg = request.getParameter("ProductList");
       String lstCamp = request.getParameter("CampaignList");
    
       String strPkgName = "";
       String strCampName = "";
    
       int iTmp;
       Collection colPackage = new ArrayList();
    
       //与类型类型对应的设备
       String terminalDeviceList=(request.getParameter("TerminalDeviceList")==null) ? "" : request.getParameter("TerminalDeviceList");
       String deviceProductIds=(request.getParameter("DeviceProductIds")==null) ? "" :request.getParameter("DeviceProductIds");
    
       if (WebUtil.StringHaveContent(strGroupBargainDetailNo)){
           CsrBusinessUtility.fillPackageColByGroupBargainDetailNo(colPackage, strGroupBargainDetailNo);
            
           //获取团购的产品包
           Iterator it = colPackage.iterator();
           while (it.hasNext()){
              Integer iPkgId = (Integer)it.next();
              strPkgName = strPkgName + Postern.getPackagenameByID(iPkgId.intValue()) +"(团购号:"+strGroupBargainDetailNo+")";
           }
           strCampName =Postern.getCampaignNameByBargainDetailNo(strGroupBargainDetailNo);
       }else{
          if (WebUtil.StringHaveContent(lstProdPkg)){
             String[] aProdPkg = lstProdPkg.split(";");
             for (int i=0; i<aProdPkg.length; i++){
                 iTmp = WebUtil.StringToInt(aProdPkg[i]);
                 strPkgName = strPkgName + String.valueOf(i+1) + ":" + Postern.getPackagenameByID(iTmp)+" ";
             }
          }    
        
         if (WebUtil.StringHaveContent(lstCamp)){
            String[] aCamp = lstCamp.split(";");
            for (int i=0; i<aCamp.length; i++){
               iTmp = WebUtil.StringToInt(aCamp[i]);
               strCampName = strCampName + String.valueOf(i+1) + ":" + Postern.getCampaignNameByID(iTmp)+" ";
            }   
         }   
      }
   %>         
      </table>
      
      <table align="center" width="820" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">产品包购买信息</font></td><td align=right class="import_tit"><A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="展开产品属性配置信息" src="img/icon_top.gif" border=0></A></td>
         </tr>
         <tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td><td width="20"></td></tr>
         <tr> 
            <td  align="right" class="list_bg2"><font size="2">
            产品包
            </font></td> 
          <td colspan="4" class="list_bg1"><font size="2">
            <%=strPkgName%> 
          </font></td>
        </tr>
        <tr> 
          <td  align="right" class="list_bg2"><font size="2">
          优惠活动
          </font></td> 
          <td colspan="4" class="list_bg1"><font size="2">
           <%=strCampName%>  
          </font></td>         
        </tr>
        <%	
         String [] aTerminalDevice=terminalDeviceList.split(";");
         String [] aTerminalProductId =deviceProductIds.split(";");

    	   for (int i=0;i<aTerminalDevice.length;i++) { 	
    	     if (aTerminalProductId[i] ==null || aTerminalProductId[i].equals("")) continue;
			     DeviceClassDTO deviceClassDto =Postern.getDeviceClassByProductID(Integer.parseInt(aTerminalProductId[i]));
    	 %>
         <tr> 
          <td class="list_bg2" align="right"><%=deviceClassDto.getDescription()%></td>
          <td class="list_bg1"  align="left" colspan="4" ><font size="2">
	         	<%=aTerminalDevice[i]%>
          </font></td>
         </tr>
       <%
       	}
         
       if(request.getParameter("phoneNo")!=null) {       
       %>
         
		    <tr> 
          <td  align="right" class="list_bg2"><font size="2">
          电话号码
          </font></td> 
          <td colspan="4" class="list_bg1"><font size="2">
           <%=request.getParameter("phoneNo")%>  
          </font></td>         
         </tr>      
       <%}%>            
     </table>  
     
     
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" id="mnu1" style="display:">
         <!--
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">产品属性配置信息</font></td>
         </tr>
         -->
		     <tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td></tr>





<%
//产品属性配置
String packageList="";
    if (WebUtil.StringHaveContent(strGroupBargainDetailNo)){
    	//获取团购的产品包
        Iterator it = colPackage.iterator();
        while (it.hasNext()){
            Integer iPkgId = (Integer)it.next();
            packageList+=iPkgId.toString()+";";
%>
	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=iPkgId.toString()%>"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25" headStyle="list_head"/>
<%            
        }
    }
    else{
		String campaignList ="";
		if(lstCamp != null && lstCamp.length()>0 && ';'==lstCamp.charAt(lstCamp.length()-1)){
			campaignList = lstCamp.substring(0,lstCamp.length()-1);
		}
        String campaignProList = Postern.getBundle2CampaignPackageColStr(campaignList);
		String allProductList = campaignProList+lstProdPkg;
		if (WebUtil.StringHaveContent(allProductList)){
			String[] aProdPkg = allProductList.split(";");
			for (int i=0; i<aProdPkg.length; i++){
				iTmp = WebUtil.StringToInt(aProdPkg[i]);
				String strPkgID = Integer.toString(iTmp);
				packageList+=strPkgID+";";
%>

	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=strPkgID%>"  showType="label" tdWordStyle="list_bg2"  tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
<%               
            }
        }    
    }

%>         
	</table>
	   <pri:authorized name="manual_product_agreement_info.do" >
	   <jsp:include page="./agreement_info.jsp" flush="true" /> 
	   </pri:authorized>

      <tbl:hiddenparameters pass="txtCatvID/txtGroupBargainDetailNo/txtReferBookingSheetID/txtCsiCreateReason/txtCsiType" />
      <tbl:hiddenparameters pass="txtCustomerSerialNo/txtCustType/txtGender/txtName/txtBirthday/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtEmail/txtLoginID/txtLoginPwd" />
      <tbl:hiddenparameters pass="txtCounty/txtDetailAddress/txtPostcode/txtOpenSourceType/txtOpenSourceID/txtComments" />
      <tbl:hiddenparameters pass="txtContractNo/txtPreferedTime/txtPreferedDate/txtSocialSecCardID/txtNationality/txtFaxNumber" />
      <tbl:hiddenparameters pass="txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />
      <tbl:hiddenparameters pass="ProductList/CampaignList/DeviceProductIds/DeviceClassList/TerminalDeviceList/sspanList/OpenFlag/txtIsInstall/txtForceDepostMoney/phoneNo/itemID/txtGrade/txtServiceCodeList" />
      <tbl:hiddenparameters pass="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>"/>
			<tbl:hiddenparameters pass="txtAgentName/txtAgentTelephone/txtAgentCardType/txtAgentCardId" />
				      	
      <input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_FREEINFO%>" />
      <input type="hidden" name="txtConfirmBackFlag" value="" />
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" />   
      <tbl:dynamicservey serveyType="M"  showType="hide" />
      <input type="hidden" name="txtcsiPayOption" >  
      <input type="hidden" name="confirm_post"  value="false" >
      
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
        <tr><td> 
        	<%
        	   String open_flag =request.getParameter("OpenFlag");
        	   if (open_flag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT)){
          %>
              <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OB%>" acctshowFlag ="false" packageList="<%=packageList%>" /> 
          <%
             } else{
       	  %>
       	      <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS%>" acctshowFlag ="false" packageList="<%=packageList%>" /> 
       	  <%
             }
          %> 
        </td></tr> 
      </table>

</form> 

<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr> 
    <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
    <td height="20">
	    <input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步">
    </td>
    <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
    
    <jsp:include page = "/fee/common_control.jsp"/>
</tr>
</table>    



      