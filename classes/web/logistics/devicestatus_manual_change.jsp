<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<script language=javascript>
function check_frm()
{
	if (check_Blank(document.frmPost.txtOperationNo, true, "修改单编号"))
		return false;

	//if (check_Blank(document.frmPost.txtFromID, true, "设备所在地"))
	//	return false;
		
	if (check_Blank(document.frmPost.txtFromDeviceStatus, true, "设备原状态"))
		return false;

	if (document.frmPost.txtFromDeviceStatus.value=="C"){
		alert("不能操作客户设备!");
		document.frmPost.txtFromDeviceStatus.focus();
		return false;
	}
		
	if (check_Blank(document.frmPost.txtToDeviceStatus, true, "设备目标状态"))
		return false;
	
	if (document.frmPost.txtFromDeviceStatus.value==document.frmPost.txtToDeviceStatus.value){
		alert("设备目标状态不能和原状态相同!");
		document.frmPost.txtToDeviceStatus.focus();
		return false;
	}
	
	return true;
}
function frm_submit()
{       
        
        document.frmPost.txtStatus.value=document.frmPost.txtFromDeviceStatus.value;
	if (check_frm()) document.frmPost.submit();
}

function ChangeSubcompany()
{
    
    //document.FrameUS.submit_update_select('subctocounty', document.frmPost.txtOrgID.value, 'txtCounty', 'txtStreetStationID');
}
</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
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
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备状态修改-录入头信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<%
Map mapFromDeviceStatus = Postern.getHashKeyValueByName("SET_D_DEVICESTATUS");
mapFromDeviceStatus.remove("L");
mapFromDeviceStatus.remove("C");
pageContext.setAttribute("mapFromDeviceStatus", mapFromDeviceStatus);
%>
<form name="frmPost" method="post" action="devicestatus_choice_info.do" >
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
       <tr>
            <td width="40%" class="list_bg2" align="right">修改单编号*</td>
            <td width="60%" class="list_bg1" align="left"> 
            <input type="text" name="txtOperationNo" size="25"  value="<tbl:writeparam name="txtOperationNo" />" ></td>
         </tr>
         
        <tr>
            <td class="list_bg2" align="right" >设备原状态*</td>
            <td class="list_bg1" align="left"> 
            <tbl:select name="txtFromDeviceStatus" set="mapFromDeviceStatus" match="txtFromDeviceStatus" width="23" /> 
            </td>
         </tr>
        <tr>
            <td class="list_bg2" align="right" >设备目标状态*</td>
            <td class="list_bg1" align="left">
            <d:selcmn name="txtToDeviceStatus" mapName="SET_D_DEVICESTATUS" match="txtToDeviceStatus" width="23" /> 
            </td>
         </tr>
         <tr>
           <td class="list_bg2" align="right" >描述</td>
           <td class="list_bg1" align="left">
            <input type="text" name="txtDescription" size="25"  value="<tbl:writeparam name="txtDescription" />" ></font></td>
         </tr>
      </table>      
	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">     
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_submit()" class="btn12">下一步</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
      </tr>
   </table>
	   </td>
	</tr>
    </table>      
  <input type="hidden" name="txtFrom" size="20" value="1">
  <input type="hidden" name="txtTo" size="20" value="10">
  <input type="hidden" name="txtStatus" value="">
  <input type="hidden" name="txtDepotID" value="" >
  </form>  
  </td>
  </tr>
  </table>