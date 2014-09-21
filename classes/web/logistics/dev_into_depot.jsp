<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ page import="com.dtv.oss.web.util.smartUpload.Request"%>
<%@ page import="com.dtv.oss.web.util.FileUpload" %>
<%@ page import="com.dtv.oss.web.util.FileUtility" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%
   String voddeviceModelStr =Postern.getVodDeviceModel();
   //解析上传文件要用到，在webaction中使用过后remove掉
   session.setAttribute(CommonKeys.FILE_UPLOAD_PAGE_CONTEXT,pageContext);
   String txtBatchNo ="";
   System.out.println("request.getContentType()---->"+request.getContentType());
   
   System.out.println("##--->"+request.getSession().getAttribute("txtDeviceModel"));
   
   String contentType = request.getContentType();
/*
   if ((contentType !=null) &&
        contentType.startsWith("multipart/form-data")) {

      FileUpload upload=new FileUpload(request,1024000,"xls");	
        //	处理文件上传
	  	String   filePath = upload.saveFile();
	  	Request  dataRequest=upload.getRequest();                
      txtBatchNo =dataRequest.getParameter("txtBatchNo") ;
      
   }
   */
   System.out.println("------txtBatchNo------>"+txtBatchNo);
   
   
%>

<script language="javascript">
//检查头记录信息是否完整
function check_options()
{
	if (check_Blank(document.frmPost.txtDeviceClass, true, "设备类型"))
		return false;
	if (check_Blank(document.frmPost.txtDeviceModel, true, "设备型号"))
		return false;	
	if (check_Blank(document.frmPost.txtDeviceProvider, true, "设备制造商"))
		return false;
	if (check_Blank(document.frmPost.txtBatchNo, true, "批号"))
		return false;				
	if (check_Blank(document.frmPost.txtGuaranteeLength, true, "保修期"))
		return false;		
	if (check_Blank(document.frmPost.txtDepotID, true, "仓库"))
		return false;		
	if (check_Blank(document.frmPost.txtInAndOut, true, "是否立即出库"))
		return false;	
	if("N"==document.frmPost.txtInAndOut.value && document.frmPost.txtOutOrgID.value!=""){
		alert("非立即出库不能录入组织信息！");
		return false;
	}
	if("Y"==document.frmPost.txtInAndOut.value && document.frmPost.txtOutOrgID.value==""){
		alert("立即出库必须录入组织信息！");
		return false;
	}	
	//if (check_Blank(document.frmPost.txtStatusReason, true, "入库原因"))
	//	return false;	
			
  	if (document.frmPost.txtGuaranteeLength.value!=""){  	
      		if (!check_Num(document.frmPost.txtGuaranteeLength, true, "保修期"))
			return false;
  	}
  	return true;
}

function check_devices(isAlert){
	if (check_Blank(document.frmPost.txtTerminalDevices, isAlert, "设备编号"))
		return false;
	return true;
}
            
function check_frm(){
	if(!check_options())
		return false;
	if (!check_devices(true))
		return false;
	return true;
}

function assign_vodfrm(){
	 document.vod_frmPost.txtDeviceModel.value =document.frmPost.txtDeviceModel.value;
	 document.vod_frmPost.txtDeviceClass.value =document.frmPost.txtDeviceClass.value;
	 document.vod_frmPost.txtDeviceProvider.value =document.frmPost.txtDeviceProvider.value;
	 document.vod_frmPost.txtGuaranteeLength.value =document.frmPost.txtGuaranteeLength.value;
	 document.vod_frmPost.txtDepotID.value =document.frmPost.txtDepotID.value;
	 document.vod_frmPost.txtBatchNo.value =document.frmPost.txtBatchNo.value;
	 document.vod_frmPost.txtInAndOut.value =document.frmPost.txtInAndOut.value;
	 document.vod_frmPost.txtOutOrgID.value =document.frmPost.txtOutOrgID.value;
	 document.vod_frmPost.txtPurposeStrList.value =document.frmPost.txtPurposeStrList.value;
	 document.vod_frmPost.txtComments.value =document.frmPost.txtComments.value;
	 document.vod_frmPost.txtPurposeStrListValue.value =document.frmPost.txtPurposeStrListValue.value;
	 
}

function device_import(){
	 if (check_options()){
	 	  if(document.vod_frmPost.txtFileName.value==null||document.vod_frmPost.txtFileName.value==""){
	       alert("没有有效的数据文件！");
	    }else{
	    	 assign_vodfrm();
	       document.vod_frmPost.action ="vod_into_depot.do";
         document.vod_frmPost.submit();
      }
	 }
}

function create_submit(){       
	if (check_frm()&&check_serials()) 
		document.frmPost.submit();
} 
 
function formreset(){
    document.frmPost.reset();
}
 
var tmpSelectValue;
function check_select(a){
	if(check_devices(false)){
		if(confirm("确认要放弃原有的输入内容,重新输入吗?")){
			document.frmPost.txtTerminalDevices.value="";
			return true;
		}else{
			a.value=tmpSelectValue;
			return false;	
		}
	}
	return true;
}

function select_onclick(a){
	tmpSelectValue=a.value;
}
 
function ChangeDeviceClass(){
	if(check_select(document.frmPost.txtDeviceClass))
    		document.FrameUS.submit_update_select('devclasstomodelnewstatus',document.frmPost.txtDeviceClass.value, 'txtDeviceModel', '');
}

function ChangeDeviceProvider(){
	if(check_select(document.frmPost.txtDeviceModel))
    		document.FrameUS.submit_update_select('devprovider',document.frmPost.txtDeviceModel.value, 'txtDeviceProvider', '');
  voddeviceModelStr ='<%=voddeviceModelStr%>';
  voddeviceModel =voddeviceModelStr.split(",");
  vodcontextFlag =false;
  deviceModelValue =document.frmPost.txtDeviceModel.value;
  for (i=0;i<voddeviceModel.length;i++){
  	  if (deviceModelValue ==voddeviceModel[i]){
  	  	  vodcontextFlag =true;
  	  	  break;
  	  }
  }

  if (vodcontextFlag){
 		  document.all("vod_into_flag").style.display ="none";
 		  document.all("vod_into_flag_1").style.display ="none";
 		  document.all("vod_into_flag_2").style.display ="";
  }else{
 	    document.all("vod_into_flag").style.display ="";
 	    document.all("vod_into_flag_1").style.display ="";
 	    document.all("vod_into_flag_2").style.display ="none";
  }
}

//检查输入内容的格式及长度
function check_modelField(checkValue){
	var fieldValue=document.frmPost.checkModelField.value;
	var fieldDesc=document.frmPost.checkModelDesc.value;
	var modelName=document.frmPost.txtDeviceModel.value;
	
	if(fieldValue!=null&&fieldValue!=""){
		var fieldNames=fieldValue.split("|");
		var fieldNumber=fieldNames.length+1;
		var arrCheckValues=checkValue.split("|");
		if(arrCheckValues.length!=fieldNumber){
			alert(modelName+'的序列号必须输入\n"'+fieldDesc+'",\n共'+fieldNumber+'项内容,以"|"分隔.');
			return false;
		}
		if(!check_serialLength(modelName,arrCheckValues[0]))
			return false;
	}else{
		if(!check_serialLength(modelName,checkValue))
			return false;
	}
	return true;
}
	
function check_serialLength(modelName,para){
	var serLength=document.frmPost.seriallength.value;	
	checkValue=trim(para);
	if(checkValue.length!=serLength){
	   alert(modelName+"的序列号长度应该为"+serLength);
	   return false;
	}	
	return true;
}

//判断有没有重复值
function mutiSerialNo(mySerialNo,cNO){
	var fieldDesc=document.frmPost.checkModelDesc.value;
	var arrFieldDesc=fieldDesc.split('|');
	var arrNo=cNO.split('|');	
	var arrDevices=mySerialNo.split('\n');
	
	for(i=0;i<arrDevices.length;i++){
		var curDevice=arrDevices[i].split('|');
		arrDevices[i]=curDevice;
	}
	
	/*把输入框中分解了，逐个判断*/
	for(i=0;i<arrNo.length;i++){
		var curNo=arrNo[i];
		if(curNo==''){
	   		alert(arrFieldDesc[i]+'的值没有输入!');
	   		return false;
		}
		
		for(j=0;j<arrDevices.length;j++){
			var curCompareNO='';
			try{
				curCompareNO=arrDevices[j][i];
			}
			catch(ex){}
			curCompareNO=superTrim(curCompareNO);
			curNo=superTrim(curNo);
		  	if(curCompareNO==curNo){
		  		//alert('i:'+i+'\nJ:'+j+'\nbreakNO:'+breakNO+'\nisOne:'+isOne+'\ncurNo:'+curNo+'\ncurCompareNO:'+curCompareNO+'\n'+(curCompareNO==curNo));
		    		alert(arrFieldDesc[i]+'输入重复!');
		    		return false;
		  	}
		}
	  
  	}
   	return true;
}
 
function NameReturnClick(){
      if (event.keyCode==13&& document.frmPost.txtSerialNo.value!="" && check_options()) {
          
          lenth=document.frmPost.txtSerialNo.value;
          serialCol=document.frmPost.txtTerminalDevices.value;
                 	
          <%if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType")))){%>   
          var arrCheckValues=lenth.split("|");        
          if(check_serialLength(document.frmPost.txtDeviceModel.value,arrCheckValues[0]) && mutiSerialNo(serialCol,lenth))
          	document.FrameVendorDeviceUS.submit_update_vendor_device(document.frmPost.txtDeviceClass.value,document.frmPost.txtDeviceModel.value,lenth);          	
          <%}
            else{ %>
          	
          
          if(check_modelField(lenth)&&mutiSerialNo(serialCol,lenth)){ 			
          	if(serialCol!="")
			serialCol=serialCol+"\n";
          	serialCol+=lenth;
          	document.frmPost.txtTerminalDevices.value=serialCol;
          	document.frmPost.txtSerialNo.value="";
          	check_serials();
          }
          <%}%>
        }  
} 

//检查大方框里的内容
function check_serials(){
	var sCol=document.frmPost.txtTerminalDevices.value;
	var res=true;
	
	sCol=superTrim(sCol);
	var arrDs=sCol.split('\n');
	
	var oldLength=0;
	do{
		oldLength=arrDs.length;
		sCol='';
		//分解成两维数组
		for(i=0;i<arrDs.length;i++){
			arrDs[i]=superTrim(arrDs[i]);
			
			//反每个设备各序列号整理
			var curD=arrDs[i].split('|');
			for(j=0;j<curD.length;j++){
				curD[j]=superTrim(curD[j]);
				if(j==0||arrDs[i]=='')
					arrDs[i]=curD[j];
				else
					arrDs[i]=arrDs[i]+'|'+curD[j];
			}
			
			//顺便去掉中间多的回车换行等。
			if(arrDs[i]!=''){
				if(i==0 || sCol=='')
					sCol+=arrDs[i];
				else
					sCol=sCol + '\n' + arrDs[i];
			}
			
		}
		
		sCol=superTrim(sCol);
		arrDs=sCol.split('\n');
	}while(oldLength!=arrDs.length)
	
	//检查有效性,取当前的设备和当前设备前面的设备集合比较
	var compareCol='';
	//记录重复的行
	var re=arrDs.length;
	for(k=0;k<arrDs.length;k++){
		var curD=arrDs[k];
		//alert('arrDs.length:'+arrDs.length+'\ncurentindex:'+k);
		if(!check_modelField(curD)||!mutiSerialNo(compareCol,curD)){
			document.frmPost.txtSerialNo.value=curD;
			re=k;
			res=false;
			break;
		}
		if(k==0 || compareCol =='')
			compareCol+=curD;
		else
			compareCol=compareCol+'\n'+curD;
			//alert('arrDs.length:'+arrDs.length+'\ncurentindex:'+k);
	}
	
	sCol='';
	//过滤掉重复的内容,哪一行重复,则不加入到新集合中,将更新后的内容重新写入集合框.
	for(i=0;i<arrDs.length;i++){
		var curD=arrDs[i];
		if(re==i)
			continue;
		if(i==0 || sCol=='')
			sCol+=arrDs[i];
		else
			sCol=sCol + '\n' + arrDs[i];
	}
	document.frmPost.txtTerminalDevices.value=sCol;
	
	return res;
}

function superTrim(para){
	if(para==null||para=='')
		return '';
	var newStr=para.substring(para.length-1,para.length);
	while(newStr=='\n'||newStr==' '||newStr.charCodeAt(0)==13){
		para=para.substring(0,para.length-1);
		newStr=para.substring(para.length-1,para.length);
	}

	newStr=para.substring(0,1);
	while(newStr=='\n'||newStr==' '||newStr.charCodeAt(0)==13){
		para=para.substring(1,para.length);
		newStr=para.substring(0,1);
	}

	return para;
}

function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}

</script>
<%
  //是否起用设备用途：
	String devicePurpose = Postern.getSystemsettingValueByName("SET_D_DEVICEPURPOSE");
%>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:150; width:250px; height:24px; display:none">
<table width="100%" border="0" cellspacing="1" cellpadding="3">
	<tr>
	<td width="100%"><div align="center"><font size="2"> 正在获取内容。。。 </font></td>
	</tr>
</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>
<iframe SRC="update_vendor_device.screen" name="FrameVendorDeviceUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>
<%
	String titile="新设备入库";
	if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType"))))
		titile="供应商设备入库";
		
		
%>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820">
  <TR>
	<TD>
	<table border="0" align="center" cellpadding="0" cellspacing="10">
	    <tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title"><%=titile %></td>
	    </tr>
	</table>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	    <tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	    </tr>
	</table>
	<br>
	
	<form name="frmPost" action="dev_into_depot_confirm.screen" method="post" >
	    <input type="hidden" name="seriallength" value='<tbl:writeparam   name="seriallength" />'> 
	    <input type="hidden" name="checkModelField" value='<tbl:writeparam   name="checkModelField" />'> 
	    <input type="hidden" name="checkModelDesc"  value='<tbl:writeparam   name="checkModelDesc" />'>
	    <input type="hidden" name="txtNotExistSerialNo" value='<tbl:writeparam   name="txtNotExistSerialNo" />'> 
	    <input type="hidden" name="txtActionType" value='<tbl:writeparam   name="txtActionType" />'> 
	    
	    
	  <table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr>
		<td class="list_bg2" width="17%"><div align="right">设备类型*</div></td>
		<td class="list_bg1" width="33%">
		<%
			//OperatorDTO operDto = CurrentLogonOperator.getOperator(request);
			//String currentOrgType = Postern.getOrganizationTypeByID(operDto.getOrgID());
			Map mapDepots = null;
			//if (!CommonConstDefinition.ORGANIZATIONORGTYPE_GENERALCOMPANY.equals(currentOrgType)) {
			//	mapDepots = Postern.getDepotByOwnerID(operDto.getOrgID());
			//} else {
				mapDepots = Postern.getAllDepot();
			//}
			pageContext.setAttribute("AllDepots", mapDepots);
			Map mapDeviceClasses = Postern.getAllDeviceClasses();
			pageContext.setAttribute("AllDeviceClasses", mapDeviceClasses);

		%> 
		<tbl:select name="txtDeviceClass" set="AllDeviceClasses" match="txtDeviceClass" width="23" onchange="ChangeDeviceClass()" onclick="select_onclick(this)" /></td>
		<td class="list_bg2" width="17%"><div align="right">设备型号*</div></td>
		<td class="list_bg1" width="33%"><d:seldevicemodel name="txtDeviceModel" deviceClass="txtDeviceClass" match="txtDeviceModel" width="23" onchange="ChangeDeviceProvider()" onclick="select_onclick(this)" /></td>
	        </tr>
 	        
 	        <tr>
		<td class="list_bg2"><div align="right">设备制造商*</div></td>
		<td class="list_bg1"><d:seldeviceprovider name="txtDeviceProvider" deviceModel="txtDeviceModel" match="txtDeviceProvider" width="23" /></td>
		<td class="list_bg2"><div align="right">批号*</div></td>
		<td class="list_bg1"><input type="text" name="txtBatchNo" size="25" value="<tbl:writeparam name="txtBatchNo" />" maxlength="50"></td>
		</tr>

		<tr>
		<td class="list_bg2" nowrap><div align="right">保修期的长度(单位:月)*</div></td>
		<td class="list_bg1"><input type="text" name="txtGuaranteeLength" size="25" value="<tbl:writeparam   name="txtGuaranteeLength" />" maxlength="10"></td>
		<td class="list_bg2"><div align="right">仓库*</div></td>
		<td class="list_bg1"><tbl:select name="txtDepotID" set="AllDepots" match="txtDepotID" width="23" /></td>
		</tr>
			
		<tr>
		<td class="list_bg2"><div align="right">是否立即出库*</div></td>
		<td class="list_bg1"><d:selcmn name="txtInAndOut" mapName="SET_G_YESNOFLAG" match="txtInAndOut" width="23" /></td>
		<td class="list_bg2"><div align="right">出库到组织</div></td>
		<td class="list_bg1">
	    	    <input type="text" name="txtOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOutOrgID" />" class="text">
	    	    <input type="hidden" name="txtOutOrgID" value="<tbl:writeparam name="txtOutOrgID" />">
	    	    <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('all','txtOutOrgID','txtOrgDesc')">
		</td>
		</tr>
		
		<%if("Y".equals(devicePurpose)){%>
		<tr>
		<!--
		<td class="list_bg2"><div align="right">入库原因*</div></td>
		<td class="list_bg1"><d:selcmn name="txtStatusReason" mapName="SET_D_SHIPINREASON" match="txtStatusReason" width="23" defaultFlag ="true" /> </td>	 
		-->
			<!--应根据全局配置决定是否显示设备用途-->
		
		<td  class="list_bg2" align="right">设备用途</td>
	        <td class="list_bg1" colspan="3">
	    	<input name="txtPurposeStrListValue" type="text" class="text" readonly maxlength="200" size="22" value="<tbl:writeparam name="txtPurposeStrListValue" />">
	    	<input name="txtPurposeStrList" type="hidden" value="<tbl:writeparam name="txtPurposeStrList" />">
	   	<input name="checkbutton" type="button" class="button" value="选择" onClick="javascript:open_select('SET_D_DEVICEUSEFORPURPOSE','txtPurposeStrList',document.frmPost.txtPurposeStrList.value,'','','')"> 
	   	</td>	 
		</tr>
		<%} else{%>
		<!--
			<tr>
		<td class="list_bg2"><div align="right">入库原因*</div></td>
		<td class="list_bg1" colspan="3"><d:selcmn name="txtStatusReason" mapName="SET_D_SHIPINREASON" match="txtStatusReason" width="23" defaultFlag ="true" /> </td>	 
		</tr>
		-->
		<%}%>
		
		<tr>
		<td class="list_bg2" align="right">备注</td>
		<td class="list_bg1" colspan="3" align="left"><input type="text" name="txtComments" size="83" value="<tbl:writeparam name="txtComments" />" maxlength="200"></td>
		</tr>
	 </table>	

   <table id="vod_into_flag" width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			  <tr>
				   <td class="list_bg2" align="right" width="17%">设备扫描输入框</td>
				   <td class="list_bg1" colspan="3" align="left"><div id="modelFieldNameDesc" style="color:red">扫描后按回车</div>
				        <input type="text" name="txtSerialNo" size="83" value="<tbl:writeparam name="txtSerialNo" />" onkeydown="NameReturnClick()"> 
				   </td>
				</tr>
	 		  <tr>
				   <td class="list_bg2" align="right">设备序列号*</td>
					 <td class="list_bg1" colspan="3" align="left"><div id="modelFieldNameDesc" style="color:red">设备序列号以“回车”隔开</div>
					    <textarea name="txtTerminalDevices" length="5" cols=82 rows=9><tbl:writeSpeCharParam name="txtTerminalDevices" /></textarea>
					 </td>
				</tr>
		</table>
		
		<table id="vod_into_flag_1" width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
				<tr align="center">
				<td class="list_bg1" align="center">
				  <table border="0" cellspacing="0" cellpadding="0" align=center>
					<tr>
					<td><img src="img/button_l.gif" border="0"></td>
					<td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">下一步</a></td>
					<td><img src="img/button_r.gif" border="0"></td>
					</tr>
	        </table>
	      </td>
        </tr>
     </table>
 </form>
 <form id="vod_into_flag_2" style="display:none"  name="vod_frmPost" action="" method="post" enctype="multipart/form-data"> 
 	   <input type ="hidden" name ="txtDeviceModel" value="" />
	   <input type ="hidden" name ="txtDeviceClass" value="" />
	   <input type ="hidden" name ="txtDeviceProvider" value="" />
	   <input type ="hidden" name ="txtGuaranteeLength" value="" />
	   <input type ="hidden" name ="txtDepotID" value="" />
	   <input type ="hidden" name ="txtBatchNo" value="" />
	   <input type ="hidden" name ="txtInAndOut" value="" />
	   <input type ="hidden" name ="txtOutOrgID" value="" />
	   <input type ="hidden" name ="txtPurposeStrList" value="" />
	   <input type ="hidden" name ="txtComments" value="" />
	   <input type ="hidden" name ="txtPurposeStrListValue" value="" />

     <table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			  <tr>
				   <td class="list_bg2" align="right" width="17%">文件导入</td>
				   <td class="list_bg1" align="left">
				       <input type="file" name="txtFileName" size="50"  maxlength="200"  onkeypress='return false;' onpaste="return false;"/>
				   </td>
				</tr>
				<tr>
					<td class="list_bg1" colspan="2" align="center">
				  <table border="0" cellspacing="0" cellpadding="0" align=center>
					<tr>
					<td><img src="img/button_l.gif" border="0"></td>
					<td background="img/button_bg.gif"><a href="javascript:device_import()" class="btn12">导 入</a></td>
					<td><img src="img/button_r.gif" border="0"></td>
					</tr>
	        </table>
	      </td>
				</tr>
     </table>
    <tbl:generatetoken /> 
</form>
     </td>
     </tr>
</table>


	 
	 

	
<script language='javascript'>
	var inStr=document.getElementById('modelFieldNameDesc').innerHTML;
	var fieldValue=document.frmPost.checkModelField.value;
	var fieldDesc=document.frmPost.checkModelDesc.value;
	
	if(fieldValue!=''&&inStr.indexOf(trim(fieldDesc))==-1)
		document.getElementById('modelFieldNameDesc').innerHTML='扫描后按回车,输入格式如:"'+fieldDesc+'"';
</script>















