<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.BidimConfigSettingDTO" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.dto.BidimConfigSettingValueDTO" %>
<%@ page import="com.dtv.oss.dto.BidimConfigSettingWithValueDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

 
<script language=javascript>
 
var deleteValuesString="";
var newAppendedValuesString="";
var valueSeperator="@*@";
var itemSeperator="@@";
 
function check_frm(){
	if (check_Blank(document.frmPost.txtName, true, "��������")){
    	return false;
    	}    
	if (check_Blank(document.frmPost.txtConfigType, true, "��������")){
    	return false;
    	}
	if (check_Blank(document.frmPost.txtValueType, true, "ȡֵ����")){
		return false;
	} 
	if (check_Blank(document.frmPost.txtStatus, true, "״̬")){
		return false; 
	}	
	if (check_Blank(document.frmPost.txtAllowNull, true, "����Ϊ��")){
		return false;
	}
	return true;
}

function config_modify_submit(){
	 
   config_new_submit();
	 
}

function config_new_submit(){
	if (check_frm()){			
		document.frmPost.new_appended_values.value=newAppendedValuesString;
		 
		document.frmPost.to_be_deleted_values.value=deleteValuesString;
    document.frmPost.modify_type.value="new";
    }else{
       	return;
    }
    document.frmPost.submit();
}

function back_submit(){
	url="<bk:backurl property='bidim_config_query.do'/>";
	if(url=="")
		url="bidim_config_query.screen";
    document.location.href=url;
}
 

 
function changePrefed(){
	if (document.frmPost.txtValueType.value =="M"||document.frmPost.txtValueType.value =="D"){       
       	document.all("prefered").style.display ="none";  
       	document.all("valuesTable").style.display ="none";     
  } else{
      	document.all("prefered").style.display ="";
  } 
}

function changeSubType(){    
	document.FrameUS.submit_update_select('configsubtype', document.frmPost.txtConfigType.value, 'txtConfigSubType', '');   
}

function addOneValue(){
	var strCode=document.frmPost.txtValueCode.value;
	var strDescription=document.frmPost.txtValueDescription.value
	var strStatus=document.frmPost.txtValueStatus.value
	var strDefalt=document.frmPost.txtDefaltValue.value;
	var strPriority=document.frmPost.txtPriority.value;
        
	
	
        if (!check_Num(document.frmPost.txtPriority, true, "����˳��"))
		return false;
	if(strCode==null || strCode==""){
		alert("ȡֵ���벻��Ϊ�գ�");
		return;
	}
	if(strStatus==null || strStatus==""){
		alert("״̬����Ϊ�գ�");
		return;
	}
	if(strDescription==null || strDescription==""){
		alert("������Ϣ����Ϊ�գ�");
		return;
	}
	if(strDefalt==null || strDefalt==""){
		alert("Ĭ��ֵ����Ϊ�գ�");
		return;
	}
	 
 
  
	var valuesString=valueSeperator+"";
	valuesString+=itemSeperator+strCode;
	valuesString+=itemSeperator+strDescription;
	valuesString+=itemSeperator+strStatus;
	valuesString+=itemSeperator+strPriority;
	valuesString+=itemSeperator+strDefalt;
	
	newAppendedValuesString=newAppendedValuesString+valuesString;

	var newRow=document.createElement("<tr class=\"list_bg1\" onmouseover=\"this.className=\'list_over\'\" onmouseout=\"this.className=\'list_bg1\'\">");
	document.all.valuesTable.tBodies[0].appendChild(newRow);

	var newCell0=document.createElement("<td align=center></td>");
	newRow.appendChild(newCell0);
	newCell0.innerText="";

	var newCell1=document.createElement("<td align=center></td>");
	newRow.appendChild(newCell1);
	newCell1.innerText=strCode;

	var newCell2=document.createElement("<td align=center></td>");
	newRow.appendChild(newCell2);
	newCell2.innerText=strDescription;

	var newCell3=document.createElement("<td align=center></td>");
	newRow.appendChild(newCell3);
	if(strStatus=="V"){
		newCell3.innerText="��Ч";
	}else{
		newCell3.innerText="��Ч";
	}
       
	var newCell4=document.createElement("<td align=center></td>");
	newRow.appendChild(newCell4);
	newCell4.innerText=strPriority;
	 var newCell5=document.createElement("<td align=center></td>");
	newRow.appendChild(newCell5);
	if(strDefalt=="Y"){
		newCell5.innerText="��";
	}else{
		newCell5.innerText="��";
	} 	
	var newCell6=document.createElement("<td align=center></td>");
	newRow.appendChild(newCell6);	
	var delBtn=document.createElement("<input type=\"button\" value=\"ɾ&nbsp;��\" onclick=\"javascript:deleteOneValue()\"/>");	
	newCell6.appendChild(delBtn);	
}

function deleteOneValue(){
  if (window.confirm('��ȷ��Ҫɾ����������Ϣ��?')){ 
	  var curRow=event.srcElement.parentElement.parentElement;
	  var txtID=curRow.cells(0).innerText;
	  var txtCode=curRow.cells(1).innerText;
	  var txtDescription=curRow.cells(2).innerText;
	  var txtValueStatus=curRow.cells(3).innerText;
	  if(txtValueStatus=="��Ч"){
		  txtValueStatus="V";
	  }else{
		  txtValueStatus="I";
	  }
	 
	  var txtPriority=curRow.cells(4).innerText;
	    
	  var txtDefaltValue=curRow.cells(5).innerText;
	  
	  if(del_defaultBlank(txtDefaltValue)=="��"){
		  txtDefaltValue="Y";
	  }else{
		  txtDefaltValue="N";
	  }		
	  
	  var varValueString=valueSeperator+txtID;
	  varValueString+=itemSeperator+txtCode;
	  varValueString+=itemSeperator+txtDescription;
	  varValueString+=itemSeperator+txtValueStatus;
	  varValueString+=itemSeperator+txtPriority;
	  varValueString+=itemSeperator+txtDefaltValue;
	  if(txtID==null || txtID==""){<!-- for new appended rows-->		
		var index1=newAppendedValuesString.indexOf(varValueString);		
		if((index1>-1) && (index1<newAppendedValuesString.length-1)){			
			var index2=index1+varValueString.length;			
			var subOne=newAppendedValuesString.substring(0,index1);			
			var subTwo=newAppendedValuesString.substring(index2,newAppendedValuesString.length);			
			newAppendedValuesString=subOne+subTwo;			
		}
	}else{<!-- for already exist rows-->
		deleteValuesString+=varValueString;	
		 
	}	
	 document.all.valuesTable.deleteRow(curRow.rowIndex);
 }
}

function del_defaultBlank(myInput)
 {
 var myValue="";
  for(var i=0; i<myInput.length;i++)
    {
      var c=myInput.charAt(i);
      if (c!=' ') myValue=myValue+c;	
    }
   return myValue; 
}
</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
<table width="100%" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td width="100%"><div align="center">
			<font size="2">	���ڻ�ȡ����... </font>
		</td>
	</tr>
</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>

<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td class="title">������ϸ��Ϣ </td>
  				</tr>
			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
				<tr>
			    	<td><img src="img/mao.gif" width="1" height="1"></td>
			  	</tr>
			</table>
			<br>			 
			<form name="frmPost" method="post" action="bidim_config_create.do" > 
			<%
			       Map nameMap = null;
			     	nameMap=Postern.getAllService();
			     	 
			     	pageContext.setAttribute("SERVNAME",nameMap);
			     	%>
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
					 <tr>
					    
					   <td class="list_bg2"><div align="right"> ��������* </div></td>
					   <td class="list_bg1" align="left" colspan="3">
					    	<input type="text" name="txtName" size="83" maxlength="25"  value="<tbl:writeparam name="txtName"/>">
					   </td>
					</tr>
					<tr> 
						<td class="list_bg2"><div align="right"> ��������* </div></td>
					 	<td class="list_bg1" align="left">
				                 <d:selcmn name="txtConfigType" mapName="SET_C_BIDIMCONFIGSETTINGCONFIGTYPE" match="txtConfigType" width="23" onchange="changeSubType()"/>
					 	 </td>
						<td class="list_bg2"><div align="right"> ���������� </div></td>
					 	<td class="list_bg1" align="left">
					 	  
					  	 <select name="txtConfigSubType"><option value="" >-----------------------</option></select>	
					  	 
					  	 
					 	</td>
					</tr>  
					<tr> 
					 	<td class="list_bg2"><div align="right"> ҵ������ </div></td>
					 	<td class="list_bg1" align="left">        
					     	<tbl:select name="txtServiceId" set="SERVNAME" match="txtServiceId" width="23" /> 
					 	</td>
						<td class="list_bg2"><div align="right"> ȡֵ����* </div></td>
					 	<td class="list_bg1" align="left">
					  		<d:selcmn name="txtValueType" mapName="SET_C_BIDIMCONFIGSETTINGVALUETYPE" match="txtValueType" width="23" onchange="changePrefed()"/>
					  	</td>
					</tr>
					<tr>
						<td class="list_bg2"><div align="right"> ״̬* </div></td>
						<td class="list_bg1" align="left">
					 		<d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
						</td>
						<td class="list_bg2"><div align="right"> ����Ϊ��* </div></td>
						<td class="list_bg1" align="left">
					 		<d:selcmn name="txtAllowNull" mapName="SET_G_YESNOFLAG" match="txtAllowNull" width="23" />
						</td>
					</tr>
					<tr>   
						<td class="list_bg2"><div align="right"> ���� </div></td>
						<td class="list_bg1"  align="left" colspan="3"> 
					 		<input type="text" name="txtDescription" size="83"  maxlength="120" value="<tbl:writeparam name="txtDescription"/>">
						</td>
					</tr>
				</table>  
				<br>
				<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
				<tr>
			    	<td><img src="img/mao.gif" width="1" height="1"></td>
			  	</tr>
			        </table>
			<br>
			<table id="prefered" width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
					<tr> 
						<td colspan="5" class="import_tit" align="center"><font size="3">����ѡ��ֵ </font></td>
					</tr>
					<tr>
					   	<td class="list_bg2"><div align="right"> ȡֵ����* </div></td>
					   	<td class="list_bg1">
					   		<input type="text" name="txtValueCode" size="25" value="" >
					   	</td>
					  	<td class="list_bg2"><div align="right">״̬* </div></td>
					  	<td class="list_bg1"> 
					   		<d:selcmn name="txtValueStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
					  	</td>
					  	<td class="list_bg1"> 
					   		<input type="button" name="addButton" value="��&nbsp;��" onclick="javascript:addOneValue()"/>
					  	</td>
					</tr>
					<tr>   
						<td class="list_bg2"><div align="right"> ȡֵ����* </div></td>
						<td class="list_bg1" > 
							<input type="text" name="txtValueDescription" size="25" value="" />  
						</td>
						<td class="list_bg2"><div align="right"> �Ƿ�Ĭ��ֵ* </div></td>
						<td class="list_bg1" > 
							<d:selcmn name="txtDefaltValue" mapName="SET_G_YESNOFLAG" match="txtDefaltValue" width="23" />
						</td>
						<td class="list_bg1" > </td> 
					</tr>
					<tr>   
						<td class="list_bg2"><div align="right">����˳��</div></td>
						<td class="list_bg1" colspan="3"> 
							<input type="text" name="txtPriority" size="25" value="" />  
						</td>
						 
						<td class="list_bg1" > </td> 
					</tr>
			 </table>  
			 <table id="valuesTable" width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">    
					<tr class="list_head">
					    <td class="list_head" > ID </td>
					    <td class="list_head" > ȡֵ���� </td>
					    <td class="list_head" > ȡֵ���� </td>
					    <td class="list_head" > ״̬ </td>
					    <td class="list_head">����˳�� </td>
					     <td class="list_head">�Ƿ�Ĭ��ֵ </td>
					    <td class="list_head">���� </td>
					</tr>
				  
				 
				</table>
				<input type="hidden" name="new_appended_values" value=""/>
				<input type="hidden" name="to_be_deleted_values" value=""/>
				<input type="hidden" name="modify_type" value=""/>
				 

				<br>
				 
				<table align="center" border="0" cellspacing="0" cellpadding="0">
					<tr>
					    <td><img src="img/button2_r.gif" width="22" height="20"></td>
			                    <td><input name="Submit" type="button" class="button"
					    value="��&nbsp;��" onclick="javascript:back_submit()"></td>
                                            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            
                                        
					    <td width="20" ></td>          
					    <td><img src="img/button_l.gif" border="0" ></td>
					    <td background="img/button_bg.gif"><a href="javascript:config_modify_submit()" class="btn12">ȷ&nbsp;��</a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>
					</tr>
				</table>
				 
			</form>
		</td>
	</tr>
</table>

