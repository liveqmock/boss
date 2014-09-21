<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
<%@ page import="com.dtv.oss.web.util.WebUtil" %> 
<%@ page import="com.dtv.oss.dto.UserPointsExchangeCondDTO" %>

<script language=javascript>

function check_frm()
{

    if (check_Blank(document.frmPost.txtGoodTypeID, true, "�һ���Ʒ����"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
		return false;

	return true;
}
function back_submit()
{
	self.location.href='<bk:backurl property="query_points_cond.do" />';
} 
function cond_create_submit(){
    if (check_frm()){
	    document.frmPost.action="cond_create_done.do";
	    document.frmPost.submit();
	}
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
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">���ֶһ���������</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
<%
	int activityID=Integer.parseInt(request.getParameter("txtActivityID"));
  Map goodsMap = null;
  goodsMap = Postern.getGoodNameByActivityID(activityID);
  pageContext.setAttribute("GOODNAME",goodsMap);
%>

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
		<td class="list_bg2" align="right" width="17%">�һ���Ʒ����*</td>
		<td class="list_bg1" width="33%">
		   <tbl:select name="txtGoodTypeID" set="GOODNAME" match="txtGoodTypeID" width="23" />   
		 </td>
		 <td valign="middle" class="list_bg2" align="right" width="17%">�ʻ����</td>
	          <td  class="list_bg1" width="33%">
	   	  <input name="txtAccountClassListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtAccountClassListValue" />" />
	   	  <input name="txtAccountClassList" type="hidden" value="<tbl:writeparam name="txtAccountClassList" />" >
	   	  <input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('SET_F_ACCOUNTCLASS','txtAccountClassList',document.frmPost.txtAccountClassList.value,'','','')"> 
	          </td>	   
	</tr>
	  
	<tr>
		
		 <td valign="middle" class="list_bg2" align="right" >֧������</td>
	          <td  class="list_bg1">
	   	  <input name="txtMopIDListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtMopIDListValue" />" />
	   	  <input name="txtMopIDList" type="hidden" value="<tbl:writeparam name="txtMopIDList" />" >
	   	  <input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('ALLMOPLIST','txtMopIDList',document.frmPost.txtMopIDList.value,'','','')"> 
	   </td>	   
	        <td class="list_bg2" align="right">��������</td>
		 <td class="list_bg1">
		 <input type="text" name="txtPointRange1" size="25"  value="<tbl:writeparam name="txtPointRange1" />">
		 </td>
	</tr>
	  
       <tr>
		<td class="list_bg2" align="right">��������</td>
		<td class="list_bg1">
		 <input type="text" name="txtPointRange2" size="25"  value="<tbl:writeparam name="txtPointRange2" />">
		 </td>
		<td class="list_bg2" align="right">״̬*</td>
		 <td class="list_bg1">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		 </td>
	</tr>
       	
	<tr>
		 <td valign="middle" class="list_bg2" align="right" >�ͻ�����</td>
	          <td  class="list_bg1">
	   	  <input name="txtCustTypeListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCustTypeListValue" />" />
	   	  <input name="txtCustTypeList" type="hidden" value="<tbl:writeparam name="txtCustTypeList" />" >
	   	  <input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
		<td class="list_bg2" align="right"></td>
		 <td class="list_bg1">
		 </td>
	</tr>
        
        
      
  </table>
<tbl:hiddenparameters pass="txtActivityID" />  
<input type="hidden" name="txtDtLastmod"   value="">
<input type="hidden" name="txtActivityID"   value="">
<input type="hidden" name="txtHiddenValues" value="txtActivityID/txtCondID/txtGoodTypeID/txtAccountClassList/txtMopIDList/txtPointRange1/txtPointRange2/txtStatus/txtCustTypeList/txtCustTypeListValue/txtMopIDListValue/txtAccountClassListValue" >
<input type="hidden" name="txtActionType" value="cond_create">
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
 <table align="center" border="0" cellspacing="0" cellpadding="0">
 <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="ȷ&nbsp;��" onclick="javascript:cond_create_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
 	   </td>
	</tr>
</table>  
   </table>   
     
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
</form>
