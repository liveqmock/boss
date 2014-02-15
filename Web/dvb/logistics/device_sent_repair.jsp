<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>

<script language="javascript">
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
		
	 
	 
	 
   
  return true;
}

function check_devices(isAlert){

	if (check_Blank(document.frmPost.txtTerminalDevices, isAlert, "设备编号"))
		return false;
 
	return true;
}
            
function check_frm()
{
	if(!check_options())
		return false;
	if (!check_devices(true))
		return false;
 
	return true;
}

function create_submit()
{
	 
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
 
function ChangeDeviceClass()
{
	if(check_select(document.frmPost.txtDeviceClass))
    document.FrameUS.submit_update_select('devclasstomodel',document.frmPost.txtDeviceClass.value, 'txtDeviceModel', '');
}
function ChangeDeviceProvider()
{
	if(check_select(document.frmPost.txtDeviceModel))
    document.FrameUS.submit_update_select('devprovider',document.frmPost.txtDeviceModel.value, 'txtDeviceProvider', '');


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
		//update by chaoqiu 2007-10-26 11:25  去掉对mac地址等其他信息输入的限制 from bug 545
		//if(arrCheckValues.length!=fieldNumber){
		//	alert(modelName+'的编号必须输入\n"'+fieldDesc+'",\n共'+fieldNumber+'项内容,以"|"分隔.');
		//	return false;
		//}
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
			}catch(ex){}
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
					/*去掉尾巴上多余的换行*/
					//serialCol=superTrim(serialCol);

          if(check_modelField(lenth)&&mutiSerialNo(serialCol,lenth)){ 
					
          if(serialCol!="")
						serialCol=serialCol+"\n";
          serialCol+=lenth;
          document.frmPost.txtTerminalDevices.value=serialCol;
          document.frmPost.txtSerialNo.value="";
          
          check_serials();

          }
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
			
			//反每个设备各编号整理
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

/*  -_-#     */
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

</script>

<div id="updselwaitlayer"
	style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
<table width="100%" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td width="100%">
		<div align="center"><font size="2"> 正在获取内容。。。 </font>
		</td>
	</tr>
</table>
</div>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0"
	frameborder="0" scrolling="0"> </iframe>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820">
	<TR>
		<TD>
		<table border="0" align="center" cellpadding="0" cellspacing="10">
			<tr>
				<td><img src="img/list_tit.gif" width="19" height="19"></td>
				<td class="title">设备送修信息录入</td>
			</tr>
		</table>
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<br>
		<form name="frmPost"  method="post" action="device_rep_first.do">    
			<input type="hidden" name="seriallength" value='<tbl:writeparam   name="seriallength" />'> 
			<input  type="hidden" name="checkModelField" value='<tbl:writeparam   name="checkModelField" />'> 
			<input   type="hidden" name="checkModelDesc"  value='<tbl:writeparam   name="checkModelDesc" />'>

		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr>
				<td class="list_bg2" width="17%">
				<div align="right">设备类型*</div>
				</td>
				<td class="list_bg1" width="33%"><%OperatorDTO operDto = CurrentLogonOperator.getOperator(request);
			String currentOrgType = Postern.getOrganizationTypeByID(operDto
					.getOrgID());
			Map mapDepots = null;
			if (!CommonConstDefinition.ORGANIZATIONORGTYPE_GENERALCOMPANY
					.equals(currentOrgType)) {
				mapDepots = Postern.getDepotByOwnerID(operDto.getOrgID());
			} else {
				mapDepots = Postern.getAllDepot();
			}
			pageContext.setAttribute("AllDepots", mapDepots);
			Map mapDeviceClasses = Postern.getAllDeviceClasses();
			pageContext.setAttribute("AllDeviceClasses", mapDeviceClasses);

		%> <tbl:select name="txtDeviceClass" set="AllDeviceClasses"
					match="txtDeviceClass" width="23" onchange="ChangeDeviceClass()"
					onclick="select_onclick(this)" /></td>
				<td class="list_bg2" width="17%">
				<div align="right">设备型号*</div>
				</td>
				<td class="list_bg1" width="33%"><d:seldevicemodel name="txtDeviceModel"
					deviceClass="txtDeviceClass" match="txtDeviceModel" width="23"
					onchange="ChangeDeviceProvider()" onclick="select_onclick(this)" /></td>

			</tr>

			<tr>
				<td class="list_bg2">
				<div align="right">设备制造商*</div>
				</td>
				<td class="list_bg1"><d:seldeviceprovider name="txtDeviceProvider"
					deviceModel="txtDeviceModel" match="txtDeviceProvider" width="23" />
				</td>
				<td class="list_bg2">
				<div align="right">批号*</div>
				</td>
				<td class="list_bg1"><input type="text" name="txtBatchNo" size="25"
					value="<tbl:writeparam name="txtBatchNo" />" maxlength="50"></td>
			</tr>

			 
			 
			<tr>
				<td class="list_bg2">
				<div align="right">设备扫描输入框</div>
				</td>
				<td class="list_bg1" colspan="3" align="left">
					<div id="modelFieldNameDesc"
					style="color:red">扫描后按回车</div>
					<input type="text"
					name="txtSerialNo" size="83"
					value="<tbl:writeparam name="txtSerialNo" />"
					onkeydown="NameReturnClick()"> </td>

			</tr>

			<tr>
				<td class="list_bg2">
				<div align="right">设备序列号*
				</td>
				<td class="list_bg1" colspan="3" align="left">
					<div id="modelFieldNameDesc"
					style="color:red">设备序列号以“回车”隔开</div>
					<textarea
					name="txtTerminalDevices" length="5" cols=82 rows=9><tbl:writeSpeCharParam
					name="txtTerminalDevices" /></textarea></td>
			</tr>
			<tr>
      <td class="list_bg2"><div align="right">备注</div></td>
        <td class="list_bg1" colspan="3">
        <input type="text" name="txtComments" size="86" maxlength="2000" value="<tbl:writeparam name="txtComments" />" >          
      </td>
     </tr>
		</table>

		<!--   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>-->
		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr align="center">
				<td class="list_bg1">
				<table border="0" cellspacing="0" cellpadding="0" align=center>
					<tr>
						<td><img src="img/button_l.gif" border="0"></td>
						<td background="img/button_bg.gif"><a
							href="javascript:create_submit()" class="btn12">下一步</a></td>
						<td><img src="img/button_r.gif" border="0"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</form>
<script language='javascript'>
	var inStr=document.getElementById('modelFieldNameDesc').innerHTML;
	var fieldValue=document.frmPost.checkModelField.value;
	var fieldDesc=document.frmPost.checkModelDesc.value;
	
	//update by chaoqiu 2007-10-26 11:25  去掉对mac地址等其他信息输入的限制 from bug 545
	//if(fieldValue!=''&&inStr.indexOf(trim(fieldDesc))==-1)
	//	document.getElementById('modelFieldNameDesc').innerHTML='扫描后按回车,输入格式如:"'+fieldDesc+'"';
</script>
</table>














