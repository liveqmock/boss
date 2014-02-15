<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>
<Script language=JavaScript>
<!--
-->
</Script>
<form name="frmPost" method="post" action="book_account_docreate.do" >     
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">新增业务帐户--确认</td>
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
    
	  String txtID=(String)request.getParameter("txtID");
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
<%   }  %>               
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
       <jsp:include page="./agreement_info.jsp?showAgreementType=label" flush="true" />       
      </pri:authorized>
     <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU%>" acctshowFlag ="true" confirmFlag="true" />		 
      
      <tbl:hiddenparameters pass="ProductList/CampaignList/DeviceProductIds/DeviceClassList/TerminalDeviceList/txtCustomerID/txtIsInstall/txtAccount/txtPreferedTime/txtPreferedDate/phoneNo/itemID" />
      <tbl:hiddenparameters pass="txtForceDepostMoney/txtID/txtCustType/txtOpenSourceType/txtCsiCreateReason/sspanList/txtServiceCodeList" />
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" /> 

      <input type="hidden" name="txtActionType" value="<%=CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT%>" />
      <input type="hidden" name="func_flag" value="1" />
      <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >   
      <input type="hidden" name="confirm_post" value="true" />
			<input type="hidden" name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" value="<tbl:writeparam name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" />" >   

</form> 
    <br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input id="prev" name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input id="next" name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确&nbsp;定"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>   
     
<Script language=JavaScript>
 
function frm_submit()
{
	document.frmPost.submit();
 	document.getElementById('prev').disabled=true;
 	document.getElementById('next').disabled=true;	
}

function back_submit()
{
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
		document.frmPost.action="book_account_fee.screen";
	}else{
		document.frmPost.action="book_account_pay.screen";
	}
	document.frmPost.confirm_post.value ="false";
	document.frmPost.submit();
}

</Script>      