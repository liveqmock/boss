<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.*,com.dtv.oss.web.util.WebUtil,java.text.SimpleDateFormat" %>
<%@ page import="com.dtv.oss.dto.DeviceModelDTO" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>

<%
   String actionType=request.getParameter("txtActionType");
   String title=null;
   String sspanList=(request.getParameter("sspanList") == null) ? "" :request.getParameter("sspanList");
   String txtDeviceFee=(request.getParameter("txtDeviceFee") == null) ? "0" :request.getParameter("txtDeviceFee");
   String txtProdID =request.getParameter("txtProdID");
   Map productList = Postern.getDescendAndAscendProductMap(WebUtil.StringToInt(txtProdID),"A");
   if(actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+""))
   productList.put(txtProdID,Postern.getProductNameByID(Integer.valueOf(txtProdID).intValue()));
   pageContext.setAttribute("productList" ,productList);
   boolean vod_deviceModel_flag =false;
   String voddeviceModelStr =Postern.getVodDeviceModel();
   String[] voddeviceModel =voddeviceModelStr.split(",");
   List deviceModelList =Postern.getDeviceModelDTOByProductID(Integer.valueOf(txtProdID).intValue());
   Iterator deviceModelItr =deviceModelList.iterator();
   while (deviceModelItr.hasNext()){
      DeviceModelDTO deviceModelDto =(DeviceModelDTO)deviceModelItr.next();
      for (int i=0; i<voddeviceModel.length;i++){
          if (deviceModelDto.getDeviceModel().equals(voddeviceModel[i])){
               vod_deviceModel_flag =true;
               break;
          } 
      }
   }

   String oldSerialNo =(request.getParameter("txtSerialNo")==null) ? "" :request.getParameter("txtSerialNo");
   TerminalDeviceDTO dto=Postern.getTerminalDeviceBySerialNo(oldSerialNo);
   if (dto.getMACAddress() !=null && !(dto.getMACAddress().equals(""))){
       oldSerialNo =oldSerialNo+"("+dto.getMACAddress()+")";
   }
   if(actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+"")){
		title="更换设备－选择新设备";
		
	}
	else{
		title="设备升级－选择新设备";
	}
	%>

<script language=javascript>
function query_device_item(txtProductId,deviceClass,txtDevicemodel,inputObject){
	if(!check_csiReason())
		return;
	
	//获得选择的产品ID
	productID=txtProductId;
	if(frmPost.txtObjectProduct){
		if(frmPost.txtObjectProduct.value!="")
			productID=frmPost.txtObjectProduct.value;
	}
		
	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	txtCsiCreateReason="";
	oldDeviceClass =deviceClass;
	if(document.frmPost.txtCsiCreateReason)
		 txtCsiCreateReason=document.frmPost.txtCsiCreateReason.value;
	
	if (document.frmPost.CMdevice !=null && document.frmPost.CMdevice.checked){
		  deviceClass='<%=CommonKeys.DeviceClass_CM%>';	
	}
	var result=showModalDialog("list_terminate_device.do?txtFrom=1&txtTo=10&txtStatus=W&txtDeviceClass="+deviceClass+"&txtProductId="+productID+"&txtDevicemodel="+txtDevicemodel + "&txtCsiType=DS&txtCsiCreateReason="+txtCsiCreateReason,window,strFeatures);
	deviceClass =oldDeviceClass;
	if (result!=null) document.frmPost.elements[deviceClass].value=result;
	getMacInmac(deviceClass);
}

function getMacInmac(deviceClass){
	var inputid="input"+deviceClass;
	var divid="div"+deviceClass;
	document.frmPost.elements[inputid].value="";
	document.getElementById(divid).innerHTML="";
	if (document.frmPost.CMdevice ==null || (document.frmPost.CMdevice.checked==false)){
     document.FrameVendorDeviceUS.submit_update_vendor_device(document.frmPost.elements[deviceClass].value,deviceClass);
  }
}

function frm_next(csiPayOption){
	 if (check_frm()){
		  document.frmPost.txtcsiPayOption.value =csiPayOption;	  
		  if (check_fee()){
	       document.frmPost.action="customer_device_swap_pay.screen";
	    }else{
	 	     document.frmPost.action="customer_device_swap_confirm.do";
	 	     document.frmPost.confirm_post.value="true";
	    }
	    document.frmPost.submit();
	 }
}

function frm_finish(csiPayOption){
	 if (check_frm()){
	    document.frmPost.txtcsiPayOption.value =csiPayOption;
	    document.frmPost.action="customer_device_swap_confirm.do";
	    document.frmPost.confirm_post.value="true";
	    document.frmPost.submit();
	 }
}

function device_swap_next(){	
     	if (check_frm()){   
   	    	document.frmPost.action ="customer_device_swap_fee.do"; 
	      	document.frmPost.submit();	
	}
}
function check_frm(){
	 if(!check_csiReason())
	 	return false;
	 if (check_Blank(document.frmPost.txtObjectProduct, true, "目标产品"))
		return false;
	 if (!check_Blank(document.frmPost.txtDeviceFee, false, "设备费")){     
		if (!check_Float(document.frmPost.txtDeviceFee,true,"设备费"))
			return false;
	 }
	 if(!check_TenDate(document.frmPost.txtWorkDate, false, "更换时间"))
		return false;
		
	 for(i=0;i<document.frmPost.length;i++){
		  var element=document.frmPost.elements[i];
		  if(element.title=="deviceSerialNo"){
			   document.frmPost.txtNewDeviceSerialNo.value=element.value;
		  }	
		  if(element.title=="inputID"){
			   document.frmPost.sspanList.value=element.value;
		  }
	 }
	 if (document.frmPost.txtNewDeviceSerialNo.value =='') {
        	alert("新设备序列号的值不能为空");
        	return false;
   	} 
   	
   	if(!compareDateValue(document.frmPost.txtWorkDate.value,(new Date()).Format("yyyy-MM-dd"),"更换时间必须小于等于当前时间")){
		
		return false;
	}
	
   return true;
}


// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) 
{
		var o = {
			"M+" : this.getMonth()+1, //月份 
			"d+" : this.getDate(), //日 
			"h+" : this.getHours(), //小时 
			"m+" : this.getMinutes(), //分 
			"s+" : this.getSeconds(), //秒 
			"q+" : Math.floor((this.getMonth()+3)/3), //季度 
			"S" : this.getMilliseconds() //毫秒 
		}; 
		if(/(y+)/.test(fmt)) 
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		for(var k in o) 
		if(new RegExp("("+ k +")").test(fmt)) 
		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
		return fmt; 
} 

function compareDateValue(startvale,endvale,message){
	returnedValue=false;
	if(startvale!='' && endvale !=''){
		//startvale=startDate.value;
		starty=startvale.substring(0,startvale.indexOf("-"))*1;  
	    starte=startvale.substr(startvale.indexOf("-")+1);
	    startm=starte.substring(0,starte.indexOf("-"))*1;
	    startd=starte.substr(starte.indexOf("-")+1)*1;
		//endvale=endDate.value;
		endy=endvale.substring(0,endvale.indexOf("-"))*1;  
	    ende=endvale.substr(endvale.indexOf("-")+1);
	    endm=ende.substring(0,ende.indexOf("-"))*1;
	    endd=ende.substr(ende.indexOf("-")+1)*1;
		if(starty<endy)
			returnedValue=true;
		else if (starty==endy)
		{
			if (startm<endm)
				returnedValue=true;
			else if (startm==endm)
			{
				if(startd<=endd)
					returnedValue=true;
			}
			
		}
	}
	else returnedValue=true;
	
	if(!returnedValue){
		alert(message);
		document.frmPost.txtWorkDate.focus();
	}
	return returnedValue;	
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
<input type="hidden" name="txtActionType" size="22" value="<%=actionType %>">
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
          <td class="list_bg2" width="17%" align="right">设备费*</td>
    	   <td class="list_bg1" width="33%">
    	     <input name="txtDeviceFee" type="text" class="text" maxlength="8" size="25" value="<%=txtDeviceFee%>">
          </td>
          <%if(actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+"")){ %>
	  <tbl:csiReason csiType="DS" name="txtCsiCreateReason" action="N" showType="text"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtCsiCreateReason"  controlSize="25" />
        <%}else{ %>
         <tbl:csiReason csiType="DU" name="txtCsiCreateReason" action="N" showType="text"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtCsiCreateReason"  controlSize="25" />
        <%} %>
        </tr> 
        
        <tr>
          <td  valign="middle" class="list_bg2"  align="right">目标产品*</td>
          <td class="list_bg1"><font size="2">
            <tbl:select name="txtObjectProduct" set="productList" match="txtObjectProduct" width="25" defaultValue="<%=txtProdID%>" />
          </font></td>
          
          <td  valign="middle" class="list_bg2"  align="right">更换时间*</td>
          <td class="list_bg1"><font size="2">
          <%if(actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+"")){ %>
          	<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtWorkDate)" onblur="lostFocus(this)" type="text" name="txtWorkDate" size="25" value="<tbl:writeparam name="txtWorkDate"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtWorkDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          <%}else{ %>
          <input name="txtWorkDate" type="text" class="text" class="textgray" readonly  maxlength="8" size="25" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>">
          <%} %>
          </font></td>
        </tr> 
 
        <input type="hidden" name="txtProductID" value="<tbl:writeparam name="txtProdID" />" >
        <input type="hidden" name="txtNewDeviceSerialNo" value="" >
        <%
          	String strDeviceClass = request.getParameter("txtDeviceClass");
          	
          	String divID="div"+strDeviceClass;
            String inputID="input"+strDeviceClass;
            String txtNewDeviceSerialNo =(request.getParameter("txtNewDeviceSerialNo") ==null) ? "" :request.getParameter("txtNewDeviceSerialNo");
            if (txtNewDeviceSerialNo.equals("")){
        %>
        <tr>
          <td  valign="middle" class="list_bg2"  align="right">新设备序列号*</td>
          <td class="list_bg1" ><font size="2">
            <input type="text"  name="<%=strDeviceClass%>" size="25" title="deviceSerialNo" value=""   class="text" onchange="javascript:getMacInmac('<%=strDeviceClass%>');">
            <input type=button value="选择" class="button" onclick="javascript:query_device_item('<%=txtProdID%>','<%=strDeviceClass%>','',this);">
            </font></td>
            <td  valign="middle" class="list_bg1"  align="left" ><font size="2">
            	  <input name="<%=inputID%>" title="inputID" type="hidden" >
	              <span  id="<%=divID%>" name="sspan"></span> </font> </td>
	        <%
	          if (vod_deviceModel_flag&&actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+"")){
	        %>
	          <td  valign="middle" class="list_bg1"  align="left" >
	           <input type="checkbox" name="CMdevice" onclick="javascript:getMacInmac('<%=strDeviceClass%>');" value="1" <%if (request.getParameter("CMdevice")!=null) { %> checked <%}%> >换Cable Model</input>
	          </td>
	        <%
	          }else{
	        %>
	         <td  valign="middle" class="list_bg1"  align="left" >&nbsp;</td>
	        <%
	          }
	        %>
        </tr>   
        <%
           } else {
        %>
          <tr>
          <td  valign="middle" class="list_bg2"  align="right">新设备序列号*</td>
          <td class="list_bg1" ><font size="2">
            <input type="text"  name="<%=strDeviceClass%>" size="25" title="deviceSerialNo" value="<%=txtNewDeviceSerialNo%>"   class="text" onchange="javascript:getMacInmac('<%=strDeviceClass%>');"> 
            <input type=button value="选择" class="button" onclick="javascript:query_device_item('<%=txtProdID%>','<%=strDeviceClass%>','',this);">
          </font></td>
          <td  valign="middle" class="list_bg2"  align="left" ><font size="2">	  
            <input name="<%=inputID%>" title="inputID" type="hidden" value="<%=sspanList%>" >
	          <span  id="<%=divID%>" name="sspan"><%=sspanList%></span></font></td>
	       <%
	          if (vod_deviceModel_flag&&actionType.equalsIgnoreCase(CommonKeys.DEVICE_SWAP+"")){
	       %>
	        <td  valign="middle" class="list_bg1"  align="left" >
	           <input type="checkbox" name="CMdevice" onclick="javascript:getMacInmac('<%=strDeviceClass%>');" value="1" <%if (request.getParameter("CMdevice")!=null) { %> checked <%}%> >换Cable Model</input>
	        </td>
	       <%
	          }else{
	        %>
	         <td  valign="middle" class="list_bg1"  align="left" >&nbsp;</td>
	        <%
	          }
	        %>    
        </tr>   
       <%
          }
       %>
      <tr>
			  	<td class="list_bg2"  align="right">备注</td>
			  	<td class="list_bg1" colspan="3" >
			     <input type="text" name="txtComments"  size="74" maxlength="250" value="<tbl:writeparam name="txtComments" />" >
			    </td>
  		  </tr>   
      </table> 
      <input type="hidden" name="func_flag" value="502">
      <input type="hidden" name="txtcsiPayOption" >
      <input type="hidden" name="confirm_post"  value="false">
      <input type="hidden" name="txtHiddenMacInmac" value="" >
      <input type="hidden" name="sspanList" value="">
      <tbl:hiddenparameters pass="txtServiceAccountID/txtDeviceClass/txtSerialNo/txtDeviceID/txtProdID/txtCustomerID/txtAccountID" />
     
      <BR>   	 	    
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>  
          <td><img src="img/button2_r.gif" width="22" height="20"></td>  
          <td background="img/button_bg.gif">
	           <a href="<bk:backurl property="customer_product_view.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" width="13" height="20"></td>
          <td width="20" ></td>  
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td background="img/button_bg.gif"  ><a href="javascript:device_swap_next()" class="btn12">下一步</a></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td> 
        </tr>
      </table>  
      
</form>


<%
	//String WorkDate = request.getParameter("txtWorkDate");
	//if(WorkDate == null){
%>
<script language=javascript>
	//document.frmPost.txtWorkDate.value=(new Date()).Format("yyyy-MM-dd");
</script>
<%//}%>




