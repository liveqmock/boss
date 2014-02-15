<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri"%>
<%@ page import="com.dtv.oss.dto.CampaignDTO"%>
<%@ page import="com.dtv.oss.util.Postern"%>

<SCRIPT LANGUAGE="JavaScript">
<!--
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

 
 
function check_form(){
	
	<!-- 检查为空-->
	  if (check_Blank(document.frmPost.areaName, true, "平移小区名称"))
        return false;
        
    if (check_Blank(document.frmPost.txtRegionalAreaId, true, "所在区域"))
        return false;
	
    if (check_Blank(document.frmPost.migrationTeamName, true, "平移施工单位名称"))
        return false;	

    if (check_Blank(document.frmPost.batchStartDate, true, "批量平移开始日期"))
        return false;		

    if (check_Blank(document.frmPost.batchEndDate, true, "批量平移结束日期"))
        return false;		

    if (check_Blank(document.frmPost.planMigrationRoomNum, true, "计划平移用户数"))
        return false;		
    <!-- 校验日期-->    
    if (document.frmPost.batchStartDate.value != ''){
		if (!check_TenDate(document.frmPost.batchStartDate, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.batchEndDate.value != ''){
		if (!check_TenDate(document.frmPost.batchEndDate, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.batchStartDate,document.frmPost.batchEndDate,"结束日期必须大于等于开始日期")){
        	return false;
    }

   <!-- 数字格式及 不为0 -->
   
   if (isNaN(document.frmPost.planMigrationRoomNum.value)){
			alert('计划平移用户数应为数字');	
	 		return false;
	 }
	 
	 var s=document.frmPost.planMigrationRoomNum.value;
   var ss=s.split(".");
   if(ss.length>1){
     	alert('计划平移用户数应为整数');
     	return false;
     }
	 if (document.frmPost.planMigrationRoomNum.value==0){
			alert('计划平移用户数不能为0');	
	 		return false;   
   }
   
   <!-- 长度校验 -->
   
    if (document.frmPost.areaName.value.length>50){
    	alert('平移小区名称长度不能超过50');
    	return false;
    }
    if (document.frmPost.areaCode.value.length>50){
    	alert('平移小区编号长度不能超过50');
    	return false;
    }
    if (document.frmPost.migrationTeamName.value.length>50){
    	alert('平移施工单位名称长度不能超过50');
    	return false;
    }    
    if (document.frmPost.planMigrationRoomNum.value.length>10){
    	alert('计划平移用户数长度不能超过10');
    	return false;
    }     
    if (document.frmPost.description.value.length>500){
    	alert('描述信息长度不能超过500');
    	return false;
    }  
        
    return true;

}
 
function frm_create()
{
           if(check_form()){
           
            document.frmPost.action="dtv_migration_create.do";
           
            document.frmPost.submit();
            }
       
}

 

//-->
</SCRIPT>

<div id="updselwaitlayer"
	style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%">
				<div align="center">
					<font size="2"> 正在获取内容。。。 </font>
				</div>
			</td>
		</tr>
	</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0"
	frameborder="0" scrolling="0">
</iframe>


<form name="frmPost" method="post" action="dtv_migration_create.do">


	<input type="hidden" name="func_flag" value="4080">
	<input type="hidden" name="Action" value="create">

	<%
	String url = "menu_dtv_migration.do";
	%>

	<table border="0" align="center" cellpadding="0" cellspacing="10">
		<tr>
			<td>
				<img src="img/list_tit.gif" width="19" height="19">
			</td>
			<td class="title">
				录入平移小区信息
			</td>
		</tr>
	</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>



	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">

		<tr>
			<td class="list_bg2">
				<div align="right">
					平移小区名称*
				</div>
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="areaName" size="25"
						maxlength="50" value="<tbl:writeparam name="areaName"/>">
				</font>
			</td>
			<td class="list_bg2">
				<div align="right">
					平移小区编号
				</div>
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="areaCode" size="25"
						maxlength="50" value="<tbl:writeparam name="areaCode"/>">
				</font>
			</td>
		</tr>
		<tr>
			
					

				<td class="list_bg2">
					<div align="right">
						所在区域*
					</div>
				</td>
				<td class="list_bg1">

					<input type="hidden" name="txtRegionalAreaId"
						value="<tbl:write property="txtRegionalAreaId" />">
					<input type="text" name="txtRegionalAreaDescrpition" size="25"
						maxlength="50" readonly
						value="<tbl:WriteDistrictInfo property="txtRegionalAreaId" />">

					<input name="selDistButton" type="button" class="button" value="选择"
						onClick="javascript:sel_district('P,T,S','txtRegionalAreaId','txtRegionalAreaDescrpition')">

				</td>


			<td class="list_bg2">
				<div align="right">
					平移施工单位名称*
				</div>
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="migrationTeamName"
						size="25" maxlength="50"
						value="<tbl:writeparam name="migrationTeamName"/>"> </font>
			</td>
		</tr>
		<tr>

			<td class="list_bg2">
				<div align="right">
					批量平移开始日期*
				</div>
			</td>
			<td class="list_bg1">
				<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.batchStartDate)" onblur="lostFocus(this)" type="text" name="batchStartDate" size="25"					value="<tbl:writeparam name="batchStartDate"/>" >				<IMG onclick="MyCalendar.SetDate(this,document.frmPost.batchStartDate,'Y')"					src="img/calendar.gif" style=cursor:hand border="0" />
				</font>
			</td>


			<td class="list_bg2">
				<div align="right">
					批量平移结束日期*
				</div>
			</td>
			<td class="list_bg1">
				<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.batchEndDate)" onblur="lostFocus(this)" type="text" name="batchEndDate" size="25"					value="<tbl:writeparam name="batchEndDate"/>">				<IMG onclick="MyCalendar.SetDate(this,document.frmPost.batchEndDate,'Y')"					src="img/calendar.gif" style=cursor:hand border="0" />
				</font>
			</td>
		</tr>

		<tr>

			<td class="list_bg2">
				<div align="right">
					计划平移用户数*
				</div>
			</td>
			<td class="list_bg1" colspan="3">
				<font size="2"> <input type="text"
						name="planMigrationRoomNum" size="25" maxlength="50"
						value="<tbl:writeparam name="planMigrationRoomNum"/>"> </font>
			</td>




		</tr>

		<tr>
			<td class="list_bg2">
				<div align="right">
					描述
				</div>
			</td>
			<td class="list_bg1" colspan="3">
				<font size="2"> <input type="text" name="description"
						size="84" value="<tbl:writeparam name="description" />"> </font>
			</td>
		</tr>

	</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

	<br>
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif">
            
            <a href=<%=url%> class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  
          
          <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_create()" class="btn12">保&nbsp;存</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          </tr>
    </table> 

</form>