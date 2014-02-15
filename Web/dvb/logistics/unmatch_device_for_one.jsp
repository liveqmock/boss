<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>
<script language=javascript>
function check_frm()
{
		if (check_Blank(document.frmPost.txtUnMatchBatchID, true, "操作批号"))
		return false;
		
		return true;
	} 
function unmatch_submit()
{
  if(check_frm()&&confirm("确定要解除配对关系吗?")){
	document.frmPost.action="unmatch_device_for_one.do";
	document.frmPost.submit();
}
} 
function back_submit()
{

	document.frmPost.action="<bk:backurl property="dev_detail.do" />";
	document.frmPost.submit();
	
}
</script>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">解除配对信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
	
	<tbl:hiddenparameters pass="txtDeviceID" />
<%    
        String deviceId=request.getParameter("txtMatchDeviceID");
        TerminalDeviceDTO dto=Postern.getTerminalDeviceByID(WebUtil.StringToInt(deviceId));
	String serialNo=dto.getSerialNo(); 
	//设备类型
	String strClass = Postern.getDeviceClassDesc(dto.getDeviceClass()); 
	String strModel = Postern.getDeviceModelDesc(dto.getDeviceModel());
	String strAddressType = dto.getAddressType();
        if (strAddressType==null) strAddressType="";
	String  strAddress = null;
 	String status=dto.getStatus();
	String matchFlag=dto.getMatchFlag();
	 Map mapDepots = Postern.getAllDepot();
	 if (strAddressType.equals("D"))  //仓库
	{
		strAddress = (String)mapDepots.get(String.valueOf(dto.getAddressID()));
	}
	else if (strAddressType.equals("T"))  //组织机构
	{
		strAddress = Postern.getOrgNameByID(dto.getAddressID());
	}
	else if (strAddressType.equals("B"))  //用户
	{
		strAddress = Postern.getCustomerNameById(dto.getAddressID());
	}
	 
	if (strAddress==null) strAddress="";
	 //源设备dto
	 String preDeviceId=request.getParameter("txtDeviceID");
         TerminalDeviceDTO dto1=Postern.getTerminalDeviceByID(WebUtil.StringToInt(preDeviceId));
         String status1=dto1.getStatus();
         String matchFlag1=dto1.getMatchFlag();
	
	 
%>
     

    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">源设备信息</font></td>
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">源设备ID</div></td>
            <td class="list_bg1"><input type="text" name="txtDeviceID" size="25"  value="<tbl:writeparam name="txtDeviceID"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">源设备序列号</div></td>
            <td class="list_bg1"><input type="text" name="txtSerialNo" size="25" value="<tbl:writeparam name="txtSerialNo"/>" class="textgray" readonly >  
        </tr>
        <tr> 
    	  <td class="list_bg2"><div align="right">源设备类型</div></td>
          <td class="list_bg1">
          <input type="text" name="txtClass" size="25"  value="<tbl:writeparam name="txtClass"/>" class="textgray" readonly ></td>
          <td class="list_bg2"><div align="right">源设备型号</div></td>
          <td class="list_bg1">
          <input type="text" name="txtModel" size="25" maxlength="10" value="<tbl:writeparam name="txtModel"/>" class="textgray" readonly >
          </td>
      </tr>
      <tr>  
       <td class="list_bg2"><div align="right">源设备状态</div></td>
        <td class="list_bg1">
        <input type="text" name="txtStatus" size="25"  value="<d:getcmnname typeName="SET_D_DEVICESTATUS" match="<%=status1%>"/>" class="textgray" readonly >          
         </td>
          <td class="list_bg2"><div align="right">源设备配对标志</div></td>
        <td class="list_bg1">
        <input type="text" name="txtMatchFlag" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="<%=matchFlag1%>"/>" class="textgray" readonly >          
         </td>
      </tr>
      <tr> 
        <td class="list_bg2"><div align="right">源设备属主类型</div></td>
         <td class="list_bg1">
        <input type="text" name="txtZoneStationName" size="25"  value="<tbl:writeparam name="txtZoneStationName"/>" class="textgray" readonly >
         </td>
        <td class="list_bg2"><div align="right">源设备属主</div></td>
        <td class="list_bg1">
        <input type="text" name="txtAddress" size="25" maxlength="10" value="<tbl:writeparam name="txtAddress"/>" class="textgray" readonly >
        </td>
      </tr>
     <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">配对设备信息</font></td>
        </tr>
      <tr>
        <td class="list_bg2"><div align="right">配对设备ID</div></td>
        <td class="list_bg1">
        <input type="text" name="txtMatchDeviceID" size="25"  value="<tbl:writeparam name="txtMatchDeviceID"/>" class="textgray" readonly ></td>
        <td class="list_bg2"><div align="right">配对设备序列号</div></td>
        <td class="list_bg1">
        <input type="text" name="txtMatchSerialNo" size="25" maxlength="10" value="<%=serialNo%>" class="textgray" readonly >          
        </td>
      </tr>
       <tr> 
    	  <td class="list_bg2"><div align="right">配对设备类型</div></td>
          <td class="list_bg1">
          <input type="text" name="txtMatchClass" size="25"  value="<%=strClass%>" class="textgray" readonly ></td>
          <td class="list_bg2"><div align="right">配对设备型号</div></td>
          <td class="list_bg1">
         <input type="text" name="txtMatchDeviceModel" size="25"  value="<%=strModel%>" class="textgray" readonly ></td>
          </td>
      </tr>
      <tr>  
       <td class="list_bg2"><div align="right">配对设备状态</div></td>
        <td class="list_bg1">
        <input type="text" name="txtMatchStatus" size="25"  value="<d:getcmnname typeName="SET_D_DEVICESTATUS" match="<%=status%>"/>" class="textgray" readonly >          
         </td>
          <td class="list_bg2"><div align="right">配对设备配对标志</div></td>
        <td class="list_bg1">
        <input type="text" name="txtMatchFlag" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="<%=matchFlag%>"/>" class="textgray" readonly >          
         </td>
      </tr>
      <tr> 
        <td class="list_bg2"><div align="right">配对设备属主类型</div></td>
         <td class="list_bg1">
        <input type="text" name="txtMatchZoneStationName" size="25"  value="<d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="<%=strAddressType%>"/>" class="textgray" readonly >
         </td>
        <td class="list_bg2"><div align="right">配对设备属主</div></td>
        <td class="list_bg1">
        <input type="text" name="txtMatchAddress" size="25" maxlength="10" value="<%=strAddress%>" class="textgray" readonly >
        </td>
      </tr>
      <tr> 
       <td class="list_bg2" align="right">操作批号*</td>
       <td class="list_bg1" colspan="3">
        <input type="text" name="txtUnMatchBatchID" size="25"  value="<tbl:writeparam name="txtUnMatchBatchID" />" >
        </td>
    </tr>    
   
    </table>
    <input type="hidden" name="func_flag" size="22" value="1008">
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	   <td class="list_bg1">
	      <table  border="0" cellspacing="0" cellpadding="0">
	         <tr>
			     <td><img src="img/button2_r.gif" border="0" ></td>
			        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
			       <td><img src="img/button2_l.gif" border="0" ></td>
			        <td width="20"></td>	         	
		     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		     <td background="img/button_bg.gif"><a href="javascript:unmatch_submit()" class="btn12">解除配对</a></td>
		     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		 </tr>
	      </table>
	   </td>
	</tr>
    </table>
</form>
</td>
</tr>
</table>