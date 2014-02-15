<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.*,com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>

<%
   String actionType=request.getParameter("txtActionType");
   String title=null;
   if(actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+"")){
		title="更换设备－收费信息";
		
	}
	else{
		title="设备升级－收费信息";
	}
   String sspanList=(request.getParameter("sspanList") == null) ? "" :request.getParameter("sspanList");
   String txtDeviceFee=(request.getParameter("txtDeviceFee") == null) ? "0" :request.getParameter("txtDeviceFee");

   String txtObjectProduct =request.getParameter("txtObjectProduct");
   String strObjectProductName=Postern.getProductNameByID(Integer.valueOf(txtObjectProduct).intValue());
   if(strObjectProductName==null)strObjectProductName="";
   
   String oldSerialNo =(request.getParameter("txtSerialNo")==null) ? "" :request.getParameter("txtSerialNo");
   TerminalDeviceDTO oldDto=Postern.getTerminalDeviceBySerialNo(oldSerialNo);
   if (oldDto.getMACAddress() !=null && !(oldDto.getMACAddress().equals(""))){
       oldSerialNo =oldSerialNo+"("+oldDto.getMACAddress()+")";
   }
   
   String newSerialNo =(request.getParameter("txtNewDeviceSerialNo")==null) ? "" : request.getParameter("txtNewDeviceSerialNo") ;
   String old_SerialNo =(request.getParameter("txtSerialNo")==null) ? "" :request.getParameter("txtSerialNo");
   TerminalDeviceDTO newDto=Postern.getTerminalDeviceBySerialNo(newSerialNo);
   if (CommonKeys.DeviceClass_CM.equals(newDto.getDeviceClass())){
         newSerialNo =old_SerialNo +"("+newDto.getSerialNo()+")";
   }else{
   	  String[] vodModel =Postern.getVodDeviceModel().split(",");
   	  boolean  vodFlag =false;
   	  for (int i=0; i<vodModel.length;i++){
   	     if (vodModel[i].equals(newDto.getDeviceModel())){
   	         vodFlag =true;
   	         break;
   	     }
   	  }
   	  if (vodFlag){
   	      if (newDto.getMACAddress() !=null && !(newDto.getMACAddress().equals(""))){
   	          newSerialNo =newDto.getSerialNo()+"("+newDto.getMACAddress()+")"; 
   	      }else{
   	  	     newSerialNo =newDto.getSerialNo()+"("+oldDto.getMACAddress()+")";
   	  	  }
   	  }
   }
%>
<script language=javascript>
function check_frm(){
	return true;
}
function frm_next(csiPayOption){
	 if (check_frm()){
		  document.frmPost.txtcsiPayOption.value =csiPayOption;
		  if (check_fee()){
	       document.frmPost.action="customer_device_swap_pay.screen";
	    }else{
	 	     //document.frmPost.action="customer_device_swap_confirm.do";
	 	     //document.frmPost.confirm_post.value="true";
	 	     document.frmPost.action="customer_device_swap_confirm.screen";
	 	     document.frmPost.confirm_post.value="false";
	 	     document.frmPost.txtConfirmBackFlag.value="caculatefee";
	    }
	    document.frmPost.submit();
	 }
}

function frm_finish(csiPayOption){
	 if (check_frm()){
	    document.frmPost.txtcsiPayOption.value =csiPayOption;
	    //document.frmPost.action="customer_device_swap_confirm.do";
	    //document.frmPost.confirm_post.value="true";
	    document.frmPost.action="customer_device_swap_confirm.screen";
	    document.frmPost.confirm_post.value="false";
	    document.frmPost.txtConfirmBackFlag.value="caculatefee";
	    document.frmPost.submit();
	 }
}
function back_submit(){
	document.frmPost.action="customer_device_swap.screen";
	document.frmPost.submit();
}
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
     <td class="title"><strong><%=title %></strong></td>
  </tr>
</table>
 <table width="780"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150; width:250px; height:24px; display:none"></div>
<iframe SRC="get_mac_intermac.screen" name="FrameVendorDeviceUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>

<form name="frmPost" method="post" action="" >
     <table width="780"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">      
        <tr>     
          <td valign="middle" class="list_bg2"  align="right" width="17%">产品序列号</td>
          <td class="list_bg1" width="33%"><font size="2">
            <input type="text" name="txtPsID" size="25"  value="<tbl:writeparam name="txtPsID" />" class="textgray" readonly >
          </font></td>
          <td  valign="middle" class="list_bg2"  align="right">旧设备号</td>
          <td class="list_bg1" ><font size="2">
             <input type="text" name="txtOldDeviceID" size="25"  value="<tbl:writeparam name="txtDeviceID" />" class="textgray" readonly   >
          </font></td>
        </tr>
        <tr>
          <td  valign="middle" class="list_bg2"  align="right">旧设备序列号</td>
          <td class="list_bg1" ><font size="2">
            <%=oldSerialNo%>
          </font></td>
          <td  valign="middle" class="list_bg2"  align="right">设备购买时间</td>
          <td class="list_bg1" ><font size="2">
             <input type="text" name="txtCreateTime" size="25"  value="<tbl:writeparam name="txtCreateTime" />" class="textgray" readonly   >
          </font></td>
        </tr> 
        <tr>
          <td class="list_bg2" width="17%" align="right">设备费</td>
    	  <td class="list_bg1" width="33%">
    	     <input name="txtDeviceFee" type="text" class="textgray" maxlength="8" size="25" value="<%=txtDeviceFee%>" readonly >
          </td>
          <%if(actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+"")){ %>
          <tbl:csiReason csiType="DS" name="txtCsiCreateReason" action="N" match="txtCsiCreateReason" forceDisplayTD="true" showType="label"  tdWordStyle="list_bg2" tdControlStyle="list_bg1"  />
          <%}else{ %>
           <tbl:csiReason csiType="DU" name="txtCsiCreateReason" action="N" match="txtCsiCreateReason" forceDisplayTD="true" showType="label"  tdWordStyle="list_bg2" tdControlStyle="list_bg1"  />
          <%} %>
        </tr> 
        <tr>
          <td  valign="middle" class="list_bg2"  align="right">目标产品</td>
          <td class="list_bg1"><font size="2"><%=strObjectProductName %></font></td>
          <td  valign="middle" class="list_bg2"  align="right">更换时间*</td>
          <td class="list_bg1"><font size="2">
          	<input type="text" name="txtWorkDate" size="25"  value="<tbl:writeparam name="txtWorkDate" />" class="textgray" readonly   >
          </font></td>
        </tr> 
        <input type="hidden" name="txtProductID" value="<tbl:writeparam name="txtProdID" />" >
        <tr>
          <td  valign="middle" class="list_bg2"  align="right">新设备序列号</td>
          <td class="list_bg1" colspan="3"><font size="2">
            <%=newSerialNo%>
          </td>
          <!--
            <td  valign="middle" class="list_bg1"  align="left" colspan="2"> <tbl:writeparam name="sspanList" /></td>
           -->
        </tr> 
        <tr>
			  	<td class="list_bg2" ><div align="right">备注</div></td>
			  	<td class="list_bg1" colspan="3" >
			     <input type="text" name="txtComments"  size="74" maxlength="250" value="<tbl:writeparam name="txtComments" />" class="textgray" readonly >
			    </td>
  		  </tr>                
      </table>
      <table width="790"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">      
        <tr> 
        	<td width="100%">
            <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_DS%>" acctshowFlag ="true"  />		 		   
          </td>
        </tr>
      </table>
 
      <input type="hidden" name="func_flag" value="502">
      <input type="hidden" name="txtcsiPayOption" >
      <input type="hidden" name="confirm_post"  value="false">
      <input type="hidden" name="txtConfirmBackFlag" value="" />
      <tbl:hiddenparameters pass="txtServiceAccountID/txtActionType/txtDeviceClass/txtSerialNo/txtDeviceID/txtProdID/txtCustomerID/txtAccountID/txtObjectProduct/txtDeviceFee/txtCsiCreateReason/sspanList" />
      <tbl:hiddenparameters pass="txtNewDeviceSerialNo" /> 	
     
    <lgc:hasnoterror>  
      <BR>   	 	    
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>  
          <td><img src="img/button2_r.gif" width="22" height="20"></td>  
          <td background="img/button_bg.gif">
	           <a href="javascript:back_submit();" class="btn12">上一步</a></td>
          <td><img src="img/button2_l.gif" width="13" height="20"></td>
  
         <jsp:include page = "/fee/common_control.jsp"/>   
        </tr>
      </table>  
      
    </lgc:hasnoterror>
    <lgc:haserror>  
	    <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>  
          <td><img src="img/button2_r.gif" width="22" height="20"></td>  
          <td background="img/button_bg.gif">
	          <a href="<bk:backurl property="customer_product_view.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" width="13" height="20"></td>  
        </tr>
      </table>  
    </lgc:haserror>      
      
    <tbl:generatetoken />
      
</form>





