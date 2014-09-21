<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

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
    double txtForceDepostMoney = (double) ((request.getParameter("txtForceDepostMoney") == null) ? 0.0 : Double.parseDouble(request.getParameter("txtForceDepostMoney")));
    StringBuffer lstBankMop = Postern.getBankMopBuffer();
    pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop()); 

    String title ="业务帐户系统外过户－录入新客户信息";
       
   	String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
   	int    catvIdLength =Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",10);
%>


<Script language=JavaScript>
<!--
var listBankMop=["9#9"<%=lstBankMop%>];
var proxy ="<%=CommonKeys.OPENSOURCETYPE_PROXY%>";

function IndexOfBankMop(str){
    for (var i=0; i<listBankMop.length; i++){
        if (listBankMop[i] == str) return i;
    }

    return -1;
}

function back_submit(){
   document.frmPost.action ="service_interaction_info.do";
   document.frmPost.submit();
}


function check_frm(){
   if (check_Blank(document.frmPost.txtName, true, "姓名"))
	    return false;

   if (check_Blank(document.frmPost.txtCustType, true, "用户类型"))
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

	 if (check_Blank(document.frmPost.txtPostcode, true, "邮编"))
	    return false;	
	
   if (check_Blank(document.frmPost.txtOpenSourceType, true, "来源渠道"))
	    return false;

   if (document.frmPost.txtOpenSourceType.value ==proxy){
       if (check_Blank(document.frmPost.txtOpenSourceID, true, "来源渠道ID"))
	         return false;
   }

   if (check_Blank(document.frmPost.txtAccountType, true, "帐户类型"))
	     return false;

   if (check_Blank(document.frmPost.txtAccountName, true,"帐户名"))
       return false;
        
   if (document.frmPost.txtSocialSecCardID.value=='' && (document.frmPost.txtCardType.value=='' && document.frmPost.txtCardID.value=='')) {
        alert("请输入证件信息或者社保卡号！");
        document.frmPost.txtCardType.focus();
        return false;
   } else if (document.frmPost.txtSocialSecCardID.value=='') {
       if (document.frmPost.txtCardType.value=='') {
            alert("请选择证件类型！");
            document.frmPost.txtCardType.focus();
            return false;
       } else if (document.frmPost.txtCardID.value=='') {
            alert("请输入证件号!");
            document.frmPost.txtCardID.focus();
            return false;
       }
    }
       
    if (check_Blank(document.frmPost.txtForceDepostMoney, true, "押金"))
	  		return false;
       
    if (!check_Float(document.frmPost.txtForceDepostMoney,true,"押金"))
          return false;
    
    var groupbrain ="<%=CommonKeys.OPENSOURCETYPE_GROUPBARGAIN%>";
    if (document.frmPost.txtOpenSourceType.value ==groupbrain){
       if (check_Blank(document.frmPost.txtGroupBargainDetailNo,true,"团购券号"))
           return false;
    } else{
       if (document.frmPost.txtGroupBargainDetailNo.value !=""){
          alert("非团购购买形式不能填写团购券号!");
          return false;
       }
    }
    
    if (check_Blank(document.frmPost.txtMopID, true, "付费类型"))
	     return false;

    if (IndexOfBankMop(document.frmPost.txtMopID.value)>=0){
        if (trim(document.frmPost.txtBankAccount.value)=='' || trim(document.frmPost.txtBankAccountName.value) ==''){
            alert("这种付费类型必须输入银行帐户及银行帐户名！");
            if (trim(document.frmPost.txtBankAccount.value)==''){
               document.frmPost.txtBankAccount.focus();
               return false;
            }
            if (trim(document.frmPost.txtBankAccountName.value)==''){
				      document.frmPost.txtBankAccountName.focus();
				      return false;
		       }
         }
     } else {
          if (trim(document.frmPost.txtBankAccount.value) != '' || trim(document.frmPost.txtBankAccountName.value) != '') {
              alert("这种付费类型不允许输入银行帐户及帐户户名!");
              return false;
          }
     }
     if (!check_csiReason())  return false;
     
     if (!check_Bidimconfig()) return false;
        
    return true;
}

function frm_submit(){
  if (check_frm()) document.frmPost.submit();
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

function ChangeOpenSourceType()
{
     document.FrameUS.submit_update_select('osttoid', document.frmPost.txtOpenSourceType.value, 'txtOpenSourceID', '');

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

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  ></iframe>

    <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title"><%=title%></td>
      </tr>
    </table>
    <br>
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>
 
    <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
           <td colspan="4" class="import_tit" align="center"><font size="3">用户信息</font></td>
       </tr>
       <tr>
       		<td valign="middle" class="list_bg2" align="right" >客户类型*</td>
		  	  <td class="list_bg1"><font size="2">
		        <d:selcmn name="txtCustType" mapName="SET_C_CUSTOMERTYPE"  match="txtCustType"  width="23" />
		  	  </font></td>
		      <td valign="middle" class="list_bg2" align="right" width="17%" >姓名*</td>
		      <td width="33%" class="list_bg1"><font size="2">
		        <input type="text" name="txtName" size="25" maxlength="20" value="<tbl:writeparam name="txtName" />"  class="text">
		      </font></td>    
       </tr>
       <tr>
		  	 <td valign="middle" class="list_bg2" align="right" >证件类型*</td>
		  	 <td class="list_bg1"><font size="2">
		        <d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" width="23" />	
	       </font></td>
		   	 <td valign="middle" class="list_bg2" align="right" >证件号*</td>
		   	 <td class="list_bg1"><font size="2">
		       <input type="text" name="txtCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtCardID" />" class="text">
		   	 </font></td>
       </tr>
       <tr>
		  	 <td valign="middle" class="list_bg2" align="right" >固定电话*</td>
		  	 <td class="list_bg1"><font size="2">
		       <input type="text" name="txtTelephone" size="25" maxlength="50" value="<tbl:writeparam name="txtTelephone"/>" class="text" >
		  	 </font></td>
		  	 <td valign="middle" class="list_bg2" align="right" >移动电话 </td>
	 		   <td class="list_bg1"><font size="2">
	     	   <input type="text" name="txtMobileNumber" size="25" maxlength="50" value="<tbl:writeparam name="txtMobileNumber" />" class="text" >
	 		   </font></td>
       </tr>
       <tr> 
       		<td valign="middle" class="list_bg2"  ><div align="right">所在区*</div></td>
         	<td class="list_bg1" ><font size="2">
			       <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	  		     <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
             <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all;leaf','txtCounty','txtCountyDesc')">
           	</font>
          </td>
         	<td valign="middle" class="list_bg2"  ><div align="right">详细地址*</div></td>
         	<td class="list_bg1" ><font size="2">
            <input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress" />" class="text">
         	</font></td>
       </tr>
       <tr>
	    	   <td valign="middle" class="list_bg2" align="right">邮编*</td>
           <td class="list_bg1" ><font size="2">
              <input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtPostcode" />" class="text">
           </font></td>
           <tbl:csiReason csiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UT%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" 
				     	  tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" forceDisplayTD="true" />           
       </tr>
       <tr>
          <td valign="middle" class="list_bg2"><div align="right">来源渠道*</div></td>
          <td class="list_bg1"><font size="2">         
            <d:selcmn name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="23" onchange="ChangeOpenSourceType()" />          
          </font></td>
          <td valign="middle" class="list_bg2"><div align="right">来源渠道ID</div></td>
          <td class="list_bg1"><font size="2">         
            <d:selopensourceid name="txtOpenSourceID" parentMatch="txtOpenSourceType" match="txtOpenSourceID" width="23" />
          </font></td>
       </tr>
       <tr>
			    <td valign="middle" class="list_bg2"  ><div align="right">&nbsp;<%=catvFieldName%></div></td>
         	<td class="list_bg1"><font size="2">
            <input type="text" name="txtCatvID" size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID" />" class="text">
         	</font>
             <input name="checkbutton" type="button" class="button" value="检查" onClick="javascript:check_submit()">
         	</td>
	   		  <td valign="middle" class="list_bg2"><div align="right"> 团购券号</div></td>
	   		  <td class="list_bg1"><font size="2">
	          <input type="text" name="txtGroupBargainDetailNo" size="25"  value="<tbl:writeparam name="txtGroupBargainDetailNo" />" class="text" readonly > 
	   		  </font></td> 	   
		   </tr>
       <tr>
          <td valign="middle" class="list_bg2" align="right">押金</td>
          <td class="list_bg1"><font size="2">
             <input type="text" name="txtForceDepostMoney" size="25" maxlength="10" value="<%=WebUtil.bigDecimalFormat(txtForceDepostMoney)%>" class="text" >
          </font></td>
          <td valign="middle" class="list_bg2" align="right">协议编号</td>
					<td class="list_bg1">
					  	<font size="2"> <input type="text" name="txtContractNo" size="25" value="<tbl:writeparam name="txtContractNo" />" class="text"> </font>
					</td>
      </tr>
		  <tr>
          <td valign="middle" class="list_bg2" align="right" >国籍</td>
	    	  <td class="list_bg1"><font size="2">
	         	 <d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" defaultFlag ="true" showAllFlag="false" />
	    	  </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%" >性别</td>
		      <td width="33%" class="list_bg1"><font size="2">
			       <d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="txtGender" width="23" />
		      </font></td>
	    </tr>
      <tr>
         	<td valign="middle" class="list_bg2" align="right" >社保卡编号</td>
	        <td class="list_bg1"><font size="2">
	     	     <input type="text" name="txtSocialSecCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />" class="text">
	        </font></td>
	        <td valign="middle" class="list_bg2" align="right" >出生年月 </td>
		 	    <td class="list_bg1"><font size="2">
		         <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtBirthday)" onblur="lostFocus(this)" type="text" name="txtBirthday" size="25" maxlength="10" value="<tbl:writeparam name="txtBirthday" />" class="text" >		         <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtBirthday,'N')" src="img/calendar.gif" style=cursor:hand border="0"></font></td> 
		 	     </font></td>
       </tr>
       <tr>
	   	    <td valign="middle" class="list_bg2" align="right" >EMAIL地址</td>
	    	  <td class="list_bg1"><font size="2">
	           <input type="text" name="txtEmail" size="25" maxlength="100" value="<tbl:writeparam name="txtEmail" />"  class="text">
	     	  </font></td>
	    	  <td valign="middle" class="list_bg2" align="right" >传真</td>
	    	  <td class="list_bg1"><font size="2">
	      	    <input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />" class="text">
	    	  </font></td>
        </tr>
        <tr> 
	 				 	<td valign="middle" class="list_bg2" align="right">备注</td>
	   		    <td class="list_bg1" colspan="3"><font size="2">
	            <input type="text" name="txtComments" size="83" maxlength="100" value="<tbl:writeparam name="txtComments" />" class="text">
	   		    </font></td>
        </tr>
           
          <tbl:dynamicservey serveyType="M"  showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25"  checkScricptName ="check_Bidimconfig()" />
          
	    <tr> 
	      <td colspan="4" class="import_tit" align="center"><font size="3">帐户基本信息(帐单地址如不填写则默认为用户地址)</font></td>
      </tr>
      <tr>
	        <td valign="middle" class="list_bg2" align="right" >帐户类型*</td>
	        <td class="list_bg1"><font size="2">                                  
		   <d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType" width="23" defaultFlag ="true" showAllFlag="false" />
	        </font></td>
	        <td valign="middle" class="list_bg2" align="right" >帐户名*</td>
	        <td class="list_bg1"><font size="2"> 
	           <input type="text" name="txtAccountName" size="25" maxlength="25" value="<tbl:writeparam name="txtAccountName" />" class="text">
	        </font></td>
	    </tr>
	    <tr>
	       <td valign="middle" class="list_bg2" align="right" >付费类型*</td>
	       <td class="list_bg1"><font size="2">
	         <tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" />
	       </font></td>	
	       <td valign="middle" class="list_bg2" align="right" >银行帐户</td>
	       <td class="list_bg1"><font size="2">
	          <input type="text" name="txtBankAccount" size="25" maxlength="25" value="<tbl:writeparam name="txtBankAccount" />" class="text">
	       </font></td>
	    </tr>
	    <tr>       
	       <td valign="middle" class="list_bg2" align="right" >银行帐户户名</td>
	       <td class="list_bg1"><font size="2">
		        <input type="text" name="txtBankAccountName" size="25" maxlength="25" value="<tbl:writeparam name="txtBankAccountName" />" class="text">
	       </font></td>
	       <td valign="middle" class="list_bg2"  ><div align="right">帐单所在区</div></td>
         <td class="list_bg1" ><font size="2">
            <input type="hidden" name="txtbillCounty" value="<tbl:writeparam name="txtbillCounty" />">
	          <input type="text" name="txtbillCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtbillCounty" />" class="text">
            <input name="selDistButton1" type="button" class="button" value="选择" onClick="javascript:sel_district('all;leaf','txtbillCounty','txtbillCountyDesc')">
         </font></td>
      </tr>
	    <tr> 
	      <td class="list_bg2" align="right" >帐单寄送地址</td>
	      <td class="list_bg1" ><font size="2">
	        <input type="text" name="txtBillDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtBillDetailAddress" />" class="text">  
	      </font></td>
	      <td class="list_bg2" align="right" >帐单寄送邮编</td>
	      <td class="list_bg1" ><font size="2">
	          <input type="text" name="txtBillPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtBillPostcode" />" class="text">  
	      </font></td>
	    </tr>
 
 </table>  
 