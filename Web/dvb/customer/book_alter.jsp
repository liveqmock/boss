<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.util.Organization" %>
<%@ page import="com.dtv.oss.dto.OperatorDTO" %>
<%@ page import="com.dtv.oss.web.util.WebCurrentOperatorData" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>

<%
    StringBuffer lstBankMop = Postern.getBankMopBuffer();
    
    pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop()); 
    String txtCsiStatus =(request.getParameter("txtCsiStatus") ==null) ? "" : request.getParameter("txtCsiStatus"); 
    boolean isagentFlag =false;
    
    int csiId =(request.getParameter("txtCsiId") ==null) ? 0 :Integer.parseInt(request.getParameter("txtCsiId"));
    String opensourcetype = Postern.getOpenSourceTypeByCsiID(csiId);
    if (opensourcetype !=null && (opensourcetype.equals("P") || opensourcetype.equals("X") ||opensourcetype.equals("Y")))
        isagentFlag =true;

    String title ="";
    if (isagentFlag) 
        title ="代理商预约--修改信息";  
    else 
        title ="预约－修改信息";
        
   String openSourceID = (request.getParameter("txtOpenSourceID") == null) ? ""
			: request.getParameter("txtOpenSourceID");
	 String openSourceName = Postern.getOrgNameByID(WebUtil
			.StringToInt(openSourceID));
   if (openSourceName ==null) openSourceName ="";
   
   String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
   int    catvIdLength =Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",10);
%>


<Script language=JavaScript>
<!--
var listBankMop=["9#9"<%=lstBankMop%>];
var selfInstall ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL%>" ;
var install ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL%>";

function IndexOfBankMop(str)
{
    for (var i=0; i<listBankMop.length; i++)
    {
        if (listBankMop[i] == str) return i;
    }

    return -1;
}

function back_submit(){
   document.frmPost.action ="menu_open_for_booking.do";
   document.frmPost.submit();
}

function changePrefed(){
   if (document.frmPost.txtIsInstall.value ==selfInstall){
      document.all("prefered").style.display ="none";
      document.frmPost.txtPreferedDate.value ="";
      document.frmPost.txtPreferedTime.value ="";
   } else{
      document.all("prefered").style.display ="";
   }
 
}



function check_frm(){
   if (check_Blank(document.frmPost.txtName, true, "姓名"))
	    return false;

   if (check_Blank(document.frmPost.txtPreferedTime, true, "预约上门时间段"))
	    return false;
   
   if (check_Blank(document.frmPost.txtPreferedDate, true, "预约上门日期"))
	    return false;
   if (!check_TenDate(document.frmPost.txtPreferedDate, true, "预约上门日期")) 
      return false;
        
   if (check_Blank(document.frmPost.txtType, true, "用户类型"))
	    return false;

   if (document.frmPost.txtTelephone.value=='' && document.frmPost.txtMobileNumber.value=='') {
	    alert("固定电话和移动电话不能同时为空!");
	    document.frmPost.txtTelephone.focus();
	    return false;
    }

   if (check_Blank(document.frmPost.txtCounty, true, "所在区"))
	     return false;	

   if (check_Blank(document.frmPost.txtDetailAddress, true, "详细地址"))
	     return false;	
	
   
   if (check_Blank(document.frmPost.txtAccountType, true, "付费类型"))
	     return false;  
    
   if (check_Blank(document.frmPost.txtMopID, true, "付费类型"))
	     return false;
	      
	 if (!check_Bidimconfig()) return false;
      
   if (!check_Blank(document.frmPost.txtBirthday, false, "出生年月")){
		  if (!check_TenDate(document.frmPost.txtBirthday, true, "出生年月")) 
		    	return false;
	 }
	  
	 if (!check_csiReason()) 
	      return false;
	  
	 return true;    
}

function check_addressOrcatv(){
   if (check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")&& check_Blank(document.frmPost.txtDetailAddress, false, "详细地址")){
	alert("请输入至少一个查询条件");		
	return false;
   }
   if(!check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")){
       if(document.frmPost.txtCatvID.value.length!=<%=catvIdLength%>){
	  alert("<%=catvFieldName%>必须输入<%=catvIdLength%>位");
	  return false;	
	}
    }
    if(!check_Blank(document.frmPost.txtDetailAddress, false, "详细地址")){
	if(document.frmPost.txtDetailAddress.value.length<=3){
	   alert("详细地址必须至少4位");
	   return false;		
	}
    }		
    return true;
}

function check_submit()
{
   if (check_addressOrcatv()) {
      var  catvId =document.frmPost.txtCatvID.value;
      var  detailAddress =document.frmPost.txtDetailAddress.value;
      var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
      window.open("catv_terminal_query.do?txtFrom=1&txtTo=10&catvId="+catvId+"&detailAddress="+detailAddress,"<%=catvFieldName%>查询",strFeatures);
   }
}


function frm_submit(submit_flag){ 
    //submit_flag :0  为修改  1：为确定
    if (add_openCampaign()){
	     add_productAndGeneralCampaign();
	     document.frmPost.ProductList.value=document.frmPost.MutiProductList.value+document.frmPost.SingleProductList.value;
	     document.frmPost.CampaignList.value=document.frmPost.OpenCampaignList.value+document.frmPost.GeneralCampaignList.value;
	  }else{
	  	return false;
	  }
  	if (check_frm()){
	     if (submit_flag ==1){
	        if (window.confirm('您确认要将该预约单生成工单吗?')){
	          // document.frmPost.action = "book_alter_confirm_op.do?ActionFlag="+<%=CommonKeys.CONFIRM_OF_BOOKING%>;
	           if(check_transfertype()){
		           document.frmPost.action = "book_select_phone_number.screen?txtPage=agentConfirm";
		           document.frmPost.submit();
		   }
	        }
	     } 
	     
	    if (submit_flag ==0){
	       if (window.confirm('您确认要修改该预约单吗?')){ 	       	
	          //document.frmPost.action = "book_alter_op.do?ActionFlag="+<%=CommonKeys.MODIFY_OF_BOOKING%>;
	          document.frmPost.action = "book_select_phone_number.screen?txtPage=bookingModify";
	          document.frmPost.submit();
	       }   
	    }  
	 }   
}
function check_transfertype(){
	var str=document.frmPost.selType!=null?document.frmPost.selType.value:""; 
		if(  'auto'==str && (''==document.frmPost.txtAutoNextOrgID.value||0==document.frmPost.txtAutoNextOrgID.value)){
			alert("无法匹配合适的处理部门，请手工流转并指定流转部门！");
			return false;
		}
		if('manual'==str){
			if(''==document.frmPost.txtNextOrgID.value){
			       	alert("手工流转需指定流转部门！");
			       	return false;
		       	}
		}
	document.frmPost.transferType.value=str;
	return true;
}
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	window.open("transfer_org_list.do?strRole=I","流转部门",strFeatures);
}
//-->
</Script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>

<form name="frmPost" method="post" action="" >
<input type="hidden" name="transferType" value="<tbl:writeparam name="transferType"/>"> 
    <input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_CUSTOMERINFO%>" />
    <tbl:hiddenparameters pass="txtCsiId/txtCustomerId/txtAddressId/txtBillAddressId/txtAcctID/txtCsiStatus" />
    <tbl:hiddenparameters pass="txtNewCustomerDtLastmod/txtCsiDtLastmod/txtCustAddrLastmod/txtNewAcctLastmod/txtAcctAddrLastmod" />
    <tbl:hiddenparameters pass="txtServiceCodeList/oldHasPhoneNo" />
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title"><%=title%></td>
      </tr>
    </table>
    <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>
 
    <table width="780"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
           <td colspan="4" class="import_tit" align="center"><font size="3">客户信息</font></td>
       </tr>
       <tr>
	        <td valign="middle" class="list_bg2" align="right" width="17%">客户类型*</td>
	        <td class="list_bg1" width="33%"><font size="2">
	          <d:selcmn name="txtType" mapName="SET_C_CUSTOMERTYPE"  match="txtType" judgeGradeFlag="true" width="23" />
	        </font></td>
	        <td valign="middle" class="list_bg2" align="right" width="17%" >姓名*</td>
	        <td width="33%" class="list_bg1"><font size="2">
	         <input type="text" name="txtName" size="25" maxlength="20" value="<tbl:writeparam name="txtName" />"  class="text">
	       </font></td>
       </tr>
       <tr>
	        <td valign="middle" class="list_bg2" align="right" width="17%">固定电话*</td>
	        <td class="list_bg1" width="33%"><font size="2">
	          <input type="text" name="txtTelephone" size="25" maxlength="20" value="<tbl:writeparam name="txtTelephone"/>" class="text" >
	        </font></td>
	        <td valign="middle" class="list_bg2" align="right" width="17%">移动电话 </td>
	        <td class="list_bg1" width="33%"><font size="2">
	          <input type="text" name="txtMobileNumber" size="25" maxlength="20" value="<tbl:writeparam name="txtMobileNumber" />" class="text" >
	       </font></td>
       </tr>
<%if(request.getParameter("txtAgentName")!=null&&!"".equals(request.getParameter("txtAgentName"))){%>       
			 <tr>
					<td valign="middle" class="list_bg2" align="right">
						代理人姓名
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtAgentName" size="25" maxlength="20"
								value="<tbl:writeparam name="txtAgentName"/>" class="text"> </font>
					</td>
					<td valign="middle" class="list_bg2" align="right">
						代理人电话
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtAgentTelephone" size="25" maxlength="20"
								value="<tbl:writeparam name="txtAgentTelephone" />" class="text"> </font>
					</td>
			</tr>
			<tr>
					<td valign="middle" class="list_bg2" align="right">
						代理人证件类型
					</td>
					<td class="list_bg1">
						<d:selcmn name="txtAgentCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtAgentCardType" width="23" defaultFlag ="true" showAllFlag="false" />
					</td>
					<td valign="middle" class="list_bg2" align="right">
						代理人证件号
					</td>
					<td class="list_bg1">
						<font size="2"> <input type="text" name="txtAgentCardId" size="25" maxlength="20"
								value="<tbl:writeparam name="txtAgentCardId" />" class="text"> </font>
					</td>
			 </tr>       
<%}%>			 
       <tr>
	       <td class="list_bg2" class="list_bg2" align="right" width="17%">所属区域*</td>
         <td class="list_bg1" width="33%">
    	       <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	           <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
             <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('leaf','txtCounty','txtCountyDesc')">
         </td>
         <td valign="middle" class="list_bg2" align="right" width="17%">详细地址*</td>
         <td class="list_bg1" width="33%"><font size="2">
            <input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress" />" class="text">
         </font></td>
	     </tr>	 
       <tr>
	        <td valign="middle" class="list_bg2" align="right" width="17%">邮编</td>
          <td class="list_bg1" width="33%"><font size="2">
             <input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtPostcode" />" class="text">
          </font></td>
          <tbl:csiReason csiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" 
				     	  tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" forceDisplayTD="true" />
       </tr>
	     <tr>
          <td  valign="middle" class="list_bg2"  align="right" width="17%" >预约上门日期*</div></td>
          <td  width="33%"  class="list_bg1"><font size="2">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedDate)" onblur="lostFocus(this)" type="text" name="txtPreferedDate" size="25"  value="<tbl:writeparam name="txtPreferedDate" />" class="text">            <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
          <td  class="list_bg2" align="right" width="17%">预约上门时间段*</td>
          <td  class="list_bg1"  width="33%"><font size="2">
             <d:selcmn name="txtPreferedTime" mapName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" width="23" />
          </font></td>
       </tr>
       <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%">来源渠道*</td>
          <td class="list_bg1" width="33%"><font size="2"> 
                                       
            <input type="hidden" name="txtOpenSourceType"   value="<tbl:writeparam name="txtOpenSourceType" />" >      
            <input type="text" name="txtOpenSourceType1" size="25"
							value="<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="<%=opensourcetype%>"/>" class="textgray" readOnly>
						<input type="hidden" name="txtOpenSourceType" value="<%=opensourcetype%>">
          </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%">来源渠道ID*</td>
          <td class="list_bg1" width="33%"><font size="2">         
            <input type="text" name="txtOpenSourceID1" size="25" value="<%=openSourceName%>" class="textgray" readOnly>
						<input type="hidden" name="txtOpenSourceID" value="<%=openSourceID%>">
          </font></td>
        </tr>
       <tr> 
         <td valign="middle" class="list_bg2"  align="right" width="17%"><%=catvFieldName%></td>
         <td class="list_bg1" width="33%"><font size="2">
            <input type="text" name="txtCatvID" size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID" />" class="text">
         </font>
             <input name="checkbutton" type="button" class="button" value="检查" onClick="javascript:check_submit()">
         </td>
    	   <td valign="middle" class="list_bg2" align="right" width="17%"> 团购券号</td>
	       <td class="list_bg1" width="33%"><font size="2">
	       <%
	          if (isagentFlag) {
	       %>
	             <input type="text" name="txtGroupBargainDetailNo" size="25"  value="<tbl:writeparam name="txtGroupBargainDetailNo" />"  class="textgray" readOnly>
	       <%
	          }else{
	       %>
	            <input type="text" name="txtGroupBargainDetailNo" size="25"  value="<tbl:writeparam name="txtGroupBargainDetailNo" />"  class="text">
	       <%   
	          }
	       %>
	       </font></td> 
       </tr>
        <tr class="viewline">
        	<td valign="middle" class="list_bg2" align="right" width="17%" >性别</td>
	        <td width="33%" class="list_bg1"><font size="2">
		       <d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="txtGender" width="23" />
	        </font></td>
	        <td valign="middle" class="list_bg2" align="right" width="17%">国籍</td>
	        <td class="list_bg1" width="33%"><font size="2">
	          <d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" />
	        </font></td>
        </tr>
        <tr>
	        <td valign="middle" class="list_bg2" align="right" width="17%">社保卡编号</td>
	        <td class="list_bg1" width="33%"><font size="2">
	          <input type="text" name="txtSocialSecCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />" class="text">
	        </font></td>
	        <td valign="middle" class="list_bg2" align="right" width="17%">EMAIL地址</td>
	        <td class="list_bg1" width="33%"><font size="2">
	          <input type="text" name="txtEmail" size="25" maxlength="100" value="<tbl:writeparam name="txtEmail" />"  class="text">
	        </font></td>
        </tr>
        <tr>
	        <td valign="middle" class="list_bg2" align="right" width="17%">传真</td>
	        <td class="list_bg1" width="33%"><font size="2">
	          <input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />" class="text">
	        </font></td>
	        <td valign="middle" class="list_bg2" align="right">
						出生年月
					</td>
					<td class="list_bg1">
						<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtBirthday)" onblur="lostFocus(this)" type="text" name="txtBirthday" size="25" maxlength="10" value="<tbl:writeparam name="txtBirthday" />"							class="text">						<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtBirthday,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
					</td>
	      </tr>
	      <tr>
	       <td valign="middle" class="list_bg2" align="right" width="17%">备注</td>
	       <td class="list_bg1" width="83%" colspan="3"><font size="2">
	         <input type="text" name="txtComments" size="83" maxlength="100" value="<tbl:writeparam name="txtComments" />" class="text">
	       </font></td>
	     </tr>
     </table>
     <%  
        //客户市场信息
        Map newcustmarkMap = Postern.getServeyResultByCustIdForRealUser(csiId,"T_NEWCUSTOMERMARKETINFO","CSIID");
        System.out.println("newcustmarkMap========"+newcustmarkMap);
        if (newcustmarkMap !=null && !newcustmarkMap.isEmpty()){
           pageContext.setAttribute("newserveyMarketMap",newcustmarkMap);
    %>
       <table align="center" width="780" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
   	      <tbl:dynamicservey serveyType="M"  showType="text" tdHeight="30" tdWidth1="17%" tdWidth2="33%" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25"  match="newserveyMarketMap"  checkScricptName ="check_Bidimconfig()"/>
        </table>
    <% 
       }
    %>
       <table align="center" width="780" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
         <tr> 
	         <td colspan="4" class="import_tit" align="center"><font size="3">帐户基本信息(帐单地址如不填写则默认为用户地址)</font></td>
         </tr>
        <tr>
	         <td valign="middle" class="list_bg2" align="right" width="17%">帐户类型*</td>
           <td class="list_bg1" width="33%"><font size="2">                                  
              <d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType" width="23" />
	         </font></td>
	         <td valign="middle" class="list_bg2" align="right" width="17%">帐户名*</td>
	         <td class="list_bg1" width="33%"><font size="2"> 
	           <input type="text" name="txtAccountName" size="25" maxlength="25" value="<tbl:writeparam name="txtAccountName" />" class="text">
	         </font></td>
	     </tr>      
	       <tr>
	         <td valign="middle" class="list_bg2" align="right" width="17%">付费类型*</td>
	         <td class="list_bg1" width="33%"><font size="2">
	           <tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" />
	         </font></td>	
	         <td valign="middle" class="list_bg2" align="right" width="17%">银行帐户</td>
	         <td class="list_bg1" width="33%"><font size="2">
	           <input type="text" name="txtBankAccount" size="25" maxlength="25" value="<tbl:writeparam name="txtBankAccount" />" class="text">
	         </font></td>
         </tr>
	       <tr>
	         <td valign="middle" class="list_bg2" align="right" width="17%">银行帐户户名</td>
      	   <td class="list_bg1" width="33%"><font size="2">
	           <input type="text" name="txtBankAccountName" size="25" maxlength="25" value="<tbl:writeparam name="txtBankAccountName" />" class="text">
	         </font></td> 
	         <td valign="middle" class="list_bg2"  width="17%"align="right">帐单所在区</td>
           <td class="list_bg1" width="33%">
    	       <input type="hidden" name="txtbillCounty" value="<tbl:writeparam name="txtbillCounty" />">
	           <input type="text" name="txtbillCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtbillCounty" />" class="text">
             <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all;leaf','txtbillCounty','txtbillCountyDesc')">
           </td>
	      </tr>
         <tr> 
	         <td class="list_bg2" align="right" width="17%">帐单寄送地址</td>
	         <td class="list_bg1" width="33%"><font size="2">
	             <input type="text" name="txtBillDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtBillDetailAddress" />" class="text">  
	         </font></td>
	         <td class="list_bg2" align="right" width="17%">帐单寄送邮编</td>
	         <td class="list_bg1" width="33%"><font size="2">
	             <input type="text" name="txtBillPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtBillPostcode" />" class="text">  
	         </font></td>
         </tr>

	  </table>  
     
      <br>
      <table width="780" border="0" cellspacing="5" cellpadding="5">
          <tr>
            <td align="center" class="import_tit">
              <font size="3"><strong>产品包购买信息</strong></font>
            </td>
          </tr>
      </table>
      <jsp:include page="/catch/list_campaignAndproduct.jsp" />
      <BR> 
      <%String systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_PRECISEWORK");
      if (isagentFlag&&systemSettingPrecise!=null&&("Y").equals(systemSettingPrecise)){ 
	String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
	if(systemSettingValue == null || ("N").equals(systemSettingValue)){%>
	<table width="780"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单手工流转</font><input type="hidden" name="selType" value="manual"></td>
		        </tr>

			<tr>
			    <td class="list_bg2"><div align="right">流转部门</td>
			    <td class="list_bg1" > 
				 <input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
			    	 <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
			    	 <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,S,O','txtNextOrgID','txtOrgDesc')">
			    </td>		
			</tr>		
	</table> 
    <%}else if(systemSettingValue != null && ("Y").equals(systemSettingValue)){
       String districtID = (request.getParameter("txtCounty") == null ? "0":request.getParameter("txtCounty"));
       int autoOrgid=Postern.getAutoNextOrgID("I",null,null,null,null,WebUtil.StringToInt(districtID),0);%>
	<table width="780"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单自动流转</font><input type="hidden" name="selType" value="auto"></td>
		        </tr>
 
			<tr>
			    <td class="list_bg2"><div align="right">流转部门</td>
			    <td class="list_bg1" > 
			 	<input type="hidden" name="txtAutoNextOrgID" value="<%=autoOrgid%>">
		    		<input type="text" name="txtNextOrgName" size="25" maxlength="50" readonly value="<%=Postern.getOrganizationDesc(autoOrgid)%>" class="text">
		    		<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:mod_organization()">
			    </td>		
			</tr>		
	</table>
    <%}}%> 
      <input type="hidden" name="ProductList" value="">
      <input type="hidden" name="CampaignList" value="">
      <input type="hidden" name="func_flag" value="110" >
      <input type="hidden" name="confirm_post" value="true" >
      <tbl:hiddenparameters pass="txtCustType/txtOpenSourceType/txtCsiType" />
      <tbl:generatetoken />
</form>      
 
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
      
        <tr>  
          <td width="20" ></td>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>         
	        <td background="img/button_bg.gif"  ><a href="<bk:backurl property="service_interaction_view.do" />" class="btn12">返  回</a></td>           
          <td><img src="img/button2_l.gif" width="12" height="20"></td>
          <%
           boolean modifyFlag =false;
           if (txtCsiStatus.equals("N") || txtCsiStatus.equals("W")){
               modifyFlag =true;
           }
                       
           if (modifyFlag){     
          %>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="next" type="button" class="button" onClick="frm_submit(0)" value="修  改"></td>
          <td><img src="img/button2_l.gif" width="12" height="20"></td>  
          <%
            }
           OperatorDTO operatorDto =(OperatorDTO)((WebCurrentOperatorData)request.getSession().getAttribute(WebKeys.OPERATOR_SESSION_NAME)).getCurrentOperator();     
           Organization org = Postern.getOrganizationByID(operatorDto.getOrgID());
           if (txtCsiStatus.equals("N") && (!"P".equals(org.getOrgType()) && !"S".equals(org.getOrgSubType()))){
          %>  
          <td width="20"></td>    
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="next" type="button" class="button" onClick="frm_submit(1)" value="确  认"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
          <%
            }
          %>

        </tr>
     </table>
	<br>	 