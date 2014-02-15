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

	if (check_Blank(document.frmPost.txtDeviceClass, true, "�豸����"))
		return false;

	if (check_Blank(document.frmPost.txtDeviceModel, true, "�豸�ͺ�"))
		return false;
	
	if (check_Blank(document.frmPost.txtDeviceProvider, true, "�豸������"))
		return false;

	if (check_Blank(document.frmPost.txtBatchNo, true, "����"))
		return false;		
		
	 
	 
	 
   
  return true;
}

function check_devices(isAlert){

	if (check_Blank(document.frmPost.txtTerminalDevices, isAlert, "�豸���"))
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
		if(confirm("ȷ��Ҫ����ԭ�е���������,����������?")){
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

//����������ݵĸ�ʽ������
function check_modelField(checkValue){
	var fieldValue=document.frmPost.checkModelField.value;
	var fieldDesc=document.frmPost.checkModelDesc.value;
	var modelName=document.frmPost.txtDeviceModel.value;
	
	if(fieldValue!=null&&fieldValue!=""){
		var fieldNames=fieldValue.split("|");
		var fieldNumber=fieldNames.length+1;
		var arrCheckValues=checkValue.split("|");
		//update by chaoqiu 2007-10-26 11:25  ȥ����mac��ַ��������Ϣ��������� from bug 545
		//if(arrCheckValues.length!=fieldNumber){
		//	alert(modelName+'�ı�ű�������\n"'+fieldDesc+'",\n��'+fieldNumber+'������,��"|"�ָ�.');
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
	   alert(modelName+"�����кų���Ӧ��Ϊ"+serLength);
	   return false;
	}	
	return true;
}
//�ж���û���ظ�ֵ
function mutiSerialNo(mySerialNo,cNO){
	var fieldDesc=document.frmPost.checkModelDesc.value;
	var arrFieldDesc=fieldDesc.split('|');

	var arrNo=cNO.split('|');
	
	var arrDevices=mySerialNo.split('\n');
	
	for(i=0;i<arrDevices.length;i++){
		var curDevice=arrDevices[i].split('|');
		arrDevices[i]=curDevice;
	}
	
	/*��������зֽ��ˣ�����ж�*/
	for(i=0;i<arrNo.length;i++){
		var curNo=arrNo[i];
		if(curNo==''){
	   alert(arrFieldDesc[i]+'��ֵû������!');
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
		    alert(arrFieldDesc[i]+'�����ظ�!');
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
					/*ȥ��β���϶���Ļ���*/
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

//���󷽿��������
function check_serials(){
	var sCol=document.frmPost.txtTerminalDevices.value;
	var res=true;
	
	sCol=superTrim(sCol);
	var arrDs=sCol.split('\n');
	
	var oldLength=0;
	do{
		oldLength=arrDs.length;
		sCol='';
		//�ֽ����ά����
		for(i=0;i<arrDs.length;i++){
			arrDs[i]=superTrim(arrDs[i]);
			
			//��ÿ���豸���������
			var curD=arrDs[i].split('|');
			for(j=0;j<curD.length;j++){
				curD[j]=superTrim(curD[j]);
				if(j==0||arrDs[i]=='')
					arrDs[i]=curD[j];
				else
					arrDs[i]=arrDs[i]+'|'+curD[j];
			}
			
			//˳��ȥ���м��Ļس����еȡ�
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
	
	//�����Ч��,ȡ��ǰ���豸�͵�ǰ�豸ǰ����豸���ϱȽ�
	var compareCol='';
	//��¼�ظ�����
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
	//���˵��ظ�������,��һ���ظ�,�򲻼��뵽�¼�����,�����º����������д�뼯�Ͽ�.
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
		<div align="center"><font size="2"> ���ڻ�ȡ���ݡ����� </font>
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
				<td class="title">�豸������Ϣ¼��</td>
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
				<div align="right">�豸����*</div>
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
				<div align="right">�豸�ͺ�*</div>
				</td>
				<td class="list_bg1" width="33%"><d:seldevicemodel name="txtDeviceModel"
					deviceClass="txtDeviceClass" match="txtDeviceModel" width="23"
					onchange="ChangeDeviceProvider()" onclick="select_onclick(this)" /></td>

			</tr>

			<tr>
				<td class="list_bg2">
				<div align="right">�豸������*</div>
				</td>
				<td class="list_bg1"><d:seldeviceprovider name="txtDeviceProvider"
					deviceModel="txtDeviceModel" match="txtDeviceProvider" width="23" />
				</td>
				<td class="list_bg2">
				<div align="right">����*</div>
				</td>
				<td class="list_bg1"><input type="text" name="txtBatchNo" size="25"
					value="<tbl:writeparam name="txtBatchNo" />" maxlength="50"></td>
			</tr>

			 
			 
			<tr>
				<td class="list_bg2">
				<div align="right">�豸ɨ�������</div>
				</td>
				<td class="list_bg1" colspan="3" align="left">
					<div id="modelFieldNameDesc"
					style="color:red">ɨ��󰴻س�</div>
					<input type="text"
					name="txtSerialNo" size="83"
					value="<tbl:writeparam name="txtSerialNo" />"
					onkeydown="NameReturnClick()"> </td>

			</tr>

			<tr>
				<td class="list_bg2">
				<div align="right">�豸���к�*
				</td>
				<td class="list_bg1" colspan="3" align="left">
					<div id="modelFieldNameDesc"
					style="color:red">�豸���к��ԡ��س�������</div>
					<textarea
					name="txtTerminalDevices" length="5" cols=82 rows=9><tbl:writeSpeCharParam
					name="txtTerminalDevices" /></textarea></td>
			</tr>
			<tr>
      <td class="list_bg2"><div align="right">��ע</div></td>
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
							href="javascript:create_submit()" class="btn12">��һ��</a></td>
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
	
	//update by chaoqiu 2007-10-26 11:25  ȥ����mac��ַ��������Ϣ��������� from bug 545
	//if(fieldValue!=''&&inStr.indexOf(trim(fieldDesc))==-1)
	//	document.getElementById('modelFieldNameDesc').innerHTML='ɨ��󰴻س�,�����ʽ��:"'+fieldDesc+'"';
</script>
</table>














