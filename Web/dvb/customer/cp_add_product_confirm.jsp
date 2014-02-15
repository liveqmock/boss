<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.WebKeys,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%
    String productAttribute =(request.getAttribute(WebKeys.REQUEST_ATTRIBUTE) ==null) ? "0" : "1";
    
    
    String deviceProductDescriptions=(request.getParameter("DeviceProductDescriptions")==null) ? "" :request.getParameter("DeviceProductDescriptions");    
    String terminalDeviceList=(request.getParameter("TerminalDeviceList")==null) ? "" : request.getParameter("TerminalDeviceList");      
    

%>
<form name="frmPost" method="post" action="" >     
	
	
	
		
   <input type="hidden" name="DeviceProductDescriptions" value="<%=deviceProductDescriptions%>">
      
   <input type="hidden" name="TerminalDeviceList" value="<%=terminalDeviceList%>">
   
   
   
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title">收费信息－信息确认</td>
        </tr>
      </table>
      <br>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
       </table>
       <br>
  
<%
    String lstProdPkg = request.getParameter("ProductList");
    String lstCamp = request.getParameter("CampaignList");
    
    String strPkgName = "";
    String strCampName = "";
    
    int iTmp;
    Collection colPackage = new ArrayList();
    Collection colCampaign = new ArrayList();
    
	String[] aProdPkg=null;
	if (WebUtil.StringHaveContent(lstProdPkg))
	{
		aProdPkg = lstProdPkg.split(";");
		for (int i=0; i<aProdPkg.length; i++)
		{
		   iTmp = WebUtil.StringToInt(aProdPkg[i]);
		   strPkgName = strPkgName + String.valueOf(i+1) + ":" + Postern.getPackagenameByID(iTmp)+" ";
		}
	}    
	
	String[] aCamp=null;
	if (WebUtil.StringHaveContent(lstCamp))
	{
		aCamp = lstCamp.split(";");
		for (int i=0; i<aCamp.length; i++)
		{
		   iTmp = WebUtil.StringToInt(aCamp[i]);
		   strCampName = strCampName + String.valueOf(i+1) + ":" + Postern.getCampaignNameByID(iTmp)+" ";
		}   
	}   
   
%> 
      <table align="center" width="820" border="0" cellspacing="1" cellpadding="3" class="list_bg" >

         <tr> 
           <td colspan="3" class="import_tit" align="center"><font size="3">产品包购买信息</font></td>
		   <td width="19" align=right class="import_tit"><A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="展开产品属性配置信息" src="img/icon_top.gif" border=0></A></td>
         </tr>
		</table>
	  <table align="center" width="820" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
         
          <tr> 
            <td width="17%" align="right" class="list_bg2"><font size="2">
            产品包
            </font></td> 
          <td colspan="3" class="list_bg1"><font size="2">
            <%=strPkgName%> 
          </font></td>
         </tr>
         <tr> 
          <td  align="right" class="list_bg2"><font size="2">
          优惠活动
          </font></td> 
          <td colspan="3" class="list_bg1"><font size="2">
           <%=strCampName%>  
          </font></td>         
         </tr>
   <%	
      
      String [] aTerminalDevice=terminalDeviceList.split(";");
      String [] aDeviceProductDescription =deviceProductDescriptions.split(";");

    	for (int i=0;i<aTerminalDevice.length;i++) { 		
    	     if (aDeviceProductDescription[i] ==null || aDeviceProductDescription[i].equals("")) continue;
			  

    %>
       <tr> 
          <td class="list_bg2" align="right"><%=aDeviceProductDescription[i]%></td>
          <td class="list_bg1"  align="left" colspan="3" ><font size="2">
		         <%=aTerminalDevice[i]%>
            </font></td>
       </tr>
    <%
    }
    %>
   </table>  

<!-- 产品属性设置部分start -->
      <table align="center" width="820" border="0" cellspacing="1" cellpadding="3" id="mnu1" class="list_bg" style="display:">
			<tr>
				<td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td>
			</tr>
			<tr> 
				<td colspan="4" class="import_tit" align="center"><font size="3">产品属性配置信息</font></td>
			</tr>
			<%
				if (WebUtil.StringHaveContent(lstProdPkg)){
					aProdPkg = lstProdPkg.split(";");
					for (int i=0; i<aProdPkg.length; i++){
							 iTmp = WebUtil.StringToInt(aProdPkg[i]);
               String strPkgID = Integer.toString(iTmp);
						%>
						<tbl:productproperty serveyName="productProperty" packageID="<%=strPkgID%>"  showType="label" tdWordStyle="list_bg2"  tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
						<%
					}
				}    

			%>
      </table>  
<!-- 产品属性设置部分end -->
       <pri:authorized name="manual_product_agreement_info.do" >
       <jsp:include page="./agreement_info.jsp?showAgreementType=label" flush="true" />       
       </pri:authorized>
	
      <tbl:hiddenparameters pass="ProductList/CampaignList/txtCustomerID/txtServiceAccountID/txtAccount/txtPreferedTime/txtPreferedDate/txtProductId/txtDeviceModel/txtDeviceClass" />
            
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList/TerminalDeviceList/sspanList/DeviceClassList/DeviceProductIds" />
			<tbl:hiddenparameters pass="txtCustType/txtOpenSourceType/OpenFlag/txtCsiType/txtCsiCreateReason" />
    	<tbl:hiddenparameters pass="txtSelectTerminalDeviceFlag" />
    	<tbl:hiddenparameters pass="txtUsedMonth" /> 
      <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_PN%>" acctshowFlag ="true" confirmFlag="true" />		 
      <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >
      <input type="hidden" name="txtActionType" value="<%=CommonKeys.ADD_PRODUCT%>" />
      <input type="hidden" name="txtDoPost" value="FALSE" >
      <input type="hidden" name="func_flag" value="1013" >

    <br>
    <table align="center" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	     <td><img src="img/button2_r.gif" width="22" height="20"></td>
	     <td><input id="prev" name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
	     <td><img src="img/button2_l.gif" width="11" height="20"></td>
	     <td width="20" ></td>
	     <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	     <td><input id="next" name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确 认"></td>
	     <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	   </tr>
    </table>   
<input type="hidden" name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" value="<tbl:writeparam name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" />" >      
</form> 
<Script language=JavaScript>
<!--  

function frm_submit(){
	document.frmPost.action="cp_add_product_op.do";
	document.frmPost.txtDoPost.value="true";
	document.frmPost.submit();
 	document.getElementById('prev').disabled=true;
 	document.getElementById('next').disabled=true;	
}

function back_submit(){
   	//document.frmPost.action="cp_add_product_pay.screen";
   	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>')
		document.frmPost.action="cp_add_product_caculate.screen";
         else
                document.frmPost.action="cp_add_product_pay.screen";
         document.frmPost.txtDoPost.value ="false";
	document.frmPost.submit();
}
//-->
</Script>



      