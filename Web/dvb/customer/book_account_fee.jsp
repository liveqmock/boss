<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>

<form name="frmPost" method="post" action="" >     
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">新增业务帐户--收费</td>
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
<%
    String productAttribute =(request.getAttribute(WebKeys.REQUEST_ATTRIBUTE) ==null) ? "0" : "1";
    String lstProdPkg = request.getParameter("ProductList");
    String lstCamp = request.getParameter("CampaignList");
    
    String strPkgName = "";
    String strCampName = "";
    
    int iTmp;
    int iGroupBargainDetailID = 0;
    Collection colPackage = new ArrayList();
    Collection colCampaign = new ArrayList();
    
    //与类型类型对应的设备
    String terminalDeviceList=(request.getParameter("TerminalDeviceList")==null) ? "" : request.getParameter("TerminalDeviceList");
    String deviceProductIds=(request.getParameter("DeviceProductIds")==null) ? "" :request.getParameter("DeviceProductIds");
    
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

%>         
     <tr> 
         <td colspan="4" class="import_tit" align="center"><font size="3">产品包购买信息
            <A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="展开产品属性配置信息" src="img/icon_top.gif" border=0></A>
         </font></td>
     </tr>
       
     <tr> 
         <td width="25%" align="right" class="list_bg2"><font size="2">
         产品包
         </font></td> 
         <td width="75%" colspan="3" class="list_bg1"><font size="2">
            <%=strPkgName%> 
          </font></td>
      </tr>
      <tr> 
          <td width="25%" align="right" class="list_bg2"><font size="2">
          优惠活动
          </font></td> 
          <td width="75%" colspan="3" class="list_bg1"><font size="2">
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
          <td class="list_bg1"  align="left" colspan="3" ><font size="2">
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
   <% } %>               
         </table>  
   
       <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" id="mnu1" style="display:">
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">产品属性配置信息</font></td>
         </tr>
		     <tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td></tr>
<%
//产品属性配置
    	String campaignList ="";
    if(lstCamp != null && lstCamp.length()>0 && ';'==lstCamp.charAt(lstCamp.length()-1))
				campaignList = lstCamp.substring(0,lstCamp.length()-1);
     String campaignProList = Postern.getBundle2CampaignPackageColStr(campaignList);
		 String allProductList = campaignProList+lstProdPkg;
		 System.out.println("+++allProductList="+allProductList);
    	if (WebUtil.StringHaveContent(allProductList))
        {
            String[] aProdPkg = allProductList.split(";");
            for (int i=0; i<aProdPkg.length; i++)
            {
               iTmp = WebUtil.StringToInt(aProdPkg[i]);
               String strPkgID = Integer.toString(iTmp);
%>
	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=strPkgID%>"  showType="label" tdWordStyle="list_bg2"  tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
<%               
            }
        }    
%>           
      </table>
       <pri:authorized name="manual_product_agreement_info.do" >
	   <jsp:include page="./agreement_info.jsp" flush="true" /> 
	   </pri:authorized>
     <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU%>" acctshowFlag ="true" />		 
      
     <tbl:hiddenparameters pass="ProductList/CampaignList/DeviceClassList/DeviceProductIds/TerminalDeviceList/txtCustomerID/txtIsInstall/txtAccount/txtPreferedTime/txtPreferedDate/phoneNo/itemID" />
     <tbl:hiddenparameters pass="txtForceDepostMoney/txtID/txtCustType/txtOpenSourceType/txtCsiCreateReason/sspanList/txtServiceCodeList/txtDistrictID" />
     <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" /> 
     <input type="hidden" name="txtActionType" value="<%=CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT%>" />
     <input type="hidden" name="func_flag" value="1" />
     <input type="hidden" name="confirm_post" value="false" >
     <input type="hidden" name="txtcsiPayOption" > 
     <input type="hidden" name="txtConfirmBackFlag" value="" />
		<input type="hidden" name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" value="<tbl:writeparam name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" />" >   
     
</form>       
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <jsp:include page = "/fee/common_control.jsp"/>
	</tr>
</table> 
  
<Script language=JavaScript>
function back_submit(){
	 product_attr ="<%=productAttribute%>";
   if (product_attr =="0"){
   	    document.frmPost.txtActionType.value="<%=CommonKeys.CHECK_PRODUCTPG_AND_INVOICE%>";
        document.frmPost.action ="book_create_customer_account.do";
   } else{
   	    document.frmPost.action ="book_create_account.screen";
   }
   document.frmPost.submit();
}

function frm_next(csiPayOption){
    <pri:authorized name="manual_product_agreement_info.do" >
    if(!oragnization_Param_agreement())
        return false;
    </pri:authorized>
	 if (check_ProductProperty()){
	    document.frmPost.txtcsiPayOption.value =csiPayOption;
	    if (check_fee()){
	       document.frmPost.action="book_account_pay.screen";
	    }else{
	 	    //document.frmPost.action="book_account_confirm.do";
	 	    //document.frmPost.confirm_post.value="true";
	 	    document.frmPost.action="book_account_confirm.screen";
	 	    document.frmPost.confirm_post.value="false";
	 	    document.frmPost.txtConfirmBackFlag.value="caculatefee";
	   }
	   document.frmPost.submit();
	 }
}

function frm_finish(csiPayOption){
		   
   <pri:authorized name="manual_product_agreement_info.do" >
   if(!oragnization_Param_agreement())
        return false;
   </pri:authorized>
	if (check_ProductProperty()){
	   document.frmPost.txtcsiPayOption.value =csiPayOption;
	   //document.frmPost.action="book_account_confirm.do";
	   //document.frmPost.confirm_post.value="true";
	   document.frmPost.confirm_post.value="false";
	   document.frmPost.action="book_account_confirm.screen";
	   document.frmPost.txtConfirmBackFlag.value="caculatefee";
	   document.frmPost.submit();
	}
}

function check_ProductProperty()
{
	for(i=0;i<document.frmPost.length;i++)
	{
		var element=document.frmPost.elements[i];
		if((""+element.type)=="text" ||(""+element.type)=="password")
		{
			var strEleName = "" + element.name;
			var arrEleName = element.name.split("_");
			if( arrEleName[0] == "txtProductProperty")
			{
				if(element.value=="")
				{
					alert("产品属性输入不完整，请重新检查！");
					element.focus();
					return false;
				}
			}
			
		}
	}
	return true;
}
</Script>    
     