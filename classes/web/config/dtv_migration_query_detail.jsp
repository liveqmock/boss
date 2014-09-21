<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri"%>
<%@ page import="com.dtv.oss.dto.DtvMigrationAreaDTO"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
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
	
	<!-- ���Ϊ��-->
	  if (check_Blank(document.frmPost.areaName, true, "ƽ��С������"))
        return false;
        
    if (check_Blank(document.frmPost.txtRegionalAreaId, true, "��������"))
        return false;
	
    if (check_Blank(document.frmPost.migrationTeamName, true, "ƽ��ʩ����λ����"))
        return false;	

    if (check_Blank(document.frmPost.batchStartDate, true, "����ƽ�ƿ�ʼ����"))
        return false;		

    if (check_Blank(document.frmPost.batchEndDate, true, "����ƽ�ƽ�������"))
        return false;		

    if (check_Blank(document.frmPost.planMigrationRoomNum, true, "�ƻ�ƽ���û���"))
        return false;		
        
    if (check_Blank(document.frmPost.createDate, true, "����ʱ��"))
        return false;	

    if (check_Blank(document.frmPost.dtLastmod, true, "����޸�ʱ��"))
        return false;	
    <!-- У������-->    
    if (document.frmPost.batchStartDate.value != ''){
		if (!check_TenDate(document.frmPost.batchStartDate, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.batchEndDate.value != ''){
		if (!check_TenDate(document.frmPost.batchEndDate, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.batchStartDate,document.frmPost.batchEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){
        	return false;
    }
        
   <!-- ���ָ�ʽ�� ��Ϊ0 -->
   
   if (isNaN(document.frmPost.planMigrationRoomNum.value)){
			alert('�ƻ�ƽ���û���ӦΪ����');	
	 		return false;
	 }
	 
	 var s=document.frmPost.planMigrationRoomNum.value;
   var ss=s.split(".");
   if(ss.length>1){
     	alert('�ƻ�ƽ���û���ӦΪ����');
     	return false;
   }
     
	 if (document.frmPost.planMigrationRoomNum.value==0){
			alert('�ƻ�ƽ���û�������Ϊ0');	
	 		return false;   
   }
   
   <!-- ����У�� -->
   
    if (document.frmPost.areaName.value.length>50){
    	alert('ƽ��С�����Ƴ��Ȳ��ܳ���50');
    	return false;
    }
    if (document.frmPost.areaCode.value.length>50){
    	alert('ƽ��С����ų��Ȳ��ܳ���50');
    	return false;
    }
    if (document.frmPost.migrationTeamName.value.length>50){
    	alert('ƽ��ʩ����λ���Ƴ��Ȳ��ܳ���50');
    	return false;
    }    
    if (document.frmPost.planMigrationRoomNum.value.length>10){
    	alert('�ƻ�ƽ���û������Ȳ��ܳ���10');
    	return false;
    }    
    if (document.frmPost.description.value.length>500){
    	alert('������Ϣ���Ȳ��ܳ���500');
    	return false;
    }  
        
    return true;

}
 
 
 
function frm_modify()
{
         
           if(check_form()){
           
         
            document.frmPost.action="dtv_migration_modify.do";
        
            document.frmPost.submit();
            }
       
}
  
//-->
</SCRIPT>


<form name="frmPost" method="post">


	<input type="hidden" name="Action" value="update">

	<input type="hidden" name="func_flag" value="4081">

 <table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">ά��ƽ��С����Ϣ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">

			<tr>
				<td class="list_bg2">
					<div align="right">
						ƽ��С���ڲ�ID
					</div>
				</td>
				<td class="list_bg1">
					<font size="2"> <input type="text" name="id" size="25"
							maxlength="50" value="<tbl:write name="oneline" property="id"/>"
							class="textgray" readonly> </font>
				</td>

				<td class="list_bg2">
					<div align="right">
						ƽ��С�����
					</div>
				</td>
				<td class="list_bg1">
					<font size="2"> <input type="text" name="areaCode" size="25"
							maxlength="50"
							value="<tbl:write name="oneline" property="areaCode"/>"
							class="textgray" readonly> </font>
				</td>



			</tr>

			<tr>
				<td class="list_bg2">
					<div align="right">
						ƽ��С������
					</div>
				</td>
				<td class="list_bg1">
					<font size="2"> <input type="text" name="areaName" size="25"
							maxlength="50"
							value="<tbl:write name="oneline" property="areaName"/>">
					</font>
				</td>


				<td class="list_bg2">
					<div align="right">
						ƽ��ʩ����λ����
					</div>
				</td>
				<td class="list_bg1">
					<font size="2"> <input type="text" name="migrationTeamName"
							size="25" maxlength="50"
							value="<tbl:write name="oneline" property="migrationTeamName" />">
					</font>
				</td>


			</tr>
			<tr>

				<td class="list_bg2">
					<div align="right">
						��������
					</div>
				</td>
				<td class="list_bg1">

					<input type="hidden" name="txtRegionalAreaId"
						value="<tbl:write name="oneline" property="regionalAreaId" />">
					<input type="text" name="txtRegionalAreaDescrpition" size="25"
						maxlength="50" readonly
						value="<tbl:WriteDistrictInfo name="oneline" property="regionalAreaId" />">

					<input name="selDistButton" type="button" class="button" value="ѡ��"
						onClick="javascript:sel_district('P,T,S','txtRegionalAreaId','txtRegionalAreaDescrpition')">

				</td>

				<td class="list_bg2">
					<div align="right">
						�ƻ�ƽ���û���
					</div>
				</td>
				<td class="list_bg1">
					<font size="2"> <input type="text"
							name="planMigrationRoomNum" size="25" maxlength="50"
							value="<tbl:write name="oneline" property="planMigrationRoomNum" />">
					</font>
				</td>


			</tr>
			<tr>

				<td class="list_bg2">
					<div align="right">
						����ƽ�ƿ�ʼ����
					</div>
				</td>
				<td class="list_bg1">
					<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.batchStartDate)" onblur="lostFocus(this)" type="text" name="batchStartDate" size="25"						value="<tbl:writedate name="oneline" property="batchStartDate"/>" >					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.batchStartDate,'Y')"						src="img/calendar.gif" style=cursor:hand border="0" />
				</td>


				<td class="list_bg2">
					<div align="right">
						����ƽ�ƽ�������*
					</div>
				</td>
				<td class="list_bg1">
					<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.batchEndDate)" onblur="lostFocus(this)" type="text" name="batchEndDate" size="25"						value="<tbl:writedate name="oneline" property="batchEndDate"/>">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.batchEndDate,'Y')"						src="img/calendar.gif" style=cursor:hand border="0" />
				</td>
			</tr>

			<tr>


<%
DtvMigrationAreaDTO dto=(DtvMigrationAreaDTO)pageContext.findAttribute("oneline");
String operName=Postern.getOperatorNameByID(dto.getCreateOpId().intValue());
%>
				<td class="list_bg2">
					<div align="right">
						������
					</div>
				</td>
				<td class="list_bg1">
					<font size="2"> <input type="text" name="createOpId"
							size="25" maxlength="50"
							value="<%=operName%>"
							class="textgray" readonly> </font>
				</td>



				<td class="list_bg2">
					<div align="right">
						״̬*
					</div>
				</td>
				<td class="list_bg1" >
					<font size="2"> <d:selcmn name="status"
							mapName="SET_M_CUSTOMERCAMPAIGNSTATUS" match="oneline:status"
							width="23" /> </font>
				</td>



			</tr>

			<tr>

				<td class="list_bg2">
					<div align="right">
						��������
					</div>
				</td>
				<td class="list_bg1">
					<font size="2"> <input type="text" name="createDate"
							size="25" maxlength="50"
							value="<tbl:writedate name="oneline" property="createDate"/>" class="textgray" readonly>
					</font>
				</td>

				<td class="list_bg2">
					<div align="right">
						����޸�����
					</div>
				</td>
				<td class="list_bg1">
					<font size="2"> <input type="text" name="dtLastmod"
							size="25" maxlength="50"
							value="<tbl:write name="oneline" property="dtLastmod"/>" class="textgray" readonly>
					</font>
				</td>



			</tr>

        <tr> 
          <td  class="list_bg2"><div align="right">����</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="description" size="84" value="<tbl:write name="oneline" property="description"/>">
              </font></td>
        </tr>

		</table>


	</lgc:bloop>
	
	
	<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

	<br>

	
	
	 <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
                <td background="img/button_bg.gif"><a href="<bk:backurl property="dtv_migration_query.do/ippv_query.do/menu_campaign_query.do/group_bargain_query_result.do/menu_taocan_query.do/campaign_query.do/taocan_query.do" />" class="btn12">����</a></td>
                <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="10" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_modify()" class="btn12">����</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
           
           
          </tr>
    </table>  

</form>