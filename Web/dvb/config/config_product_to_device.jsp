<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.OpGroupDTO,
               java.util.*" %>
 
<%String checkFlags = request.getParameter("checkFlags");
if (checkFlags == null)
    checkFlags = "";%>
    
 <form name="frmPost" method="post">
   <table border="0" align="center" cellpadding="0" cellspacing="10">
    <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">硬件产品所关联的设备型号信息管理</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 
<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">产品信息</td>
     </tr>
     </table>
 <table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    	
    	<tr>
            <td class="list_bg2"><div align="right">产品ID</div></td>
            <td class="list_bg1"><input type="text" name="txtProductID" size="25"  value="<tbl:writeparam name="txtProductID" />" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">产品类型</div></td>
            <td class="list_bg1"><input type="text" name="ProductClass" size="25"  value="<d:getcmnname typeName="SET_P_PRODUCTCLASS" match="txtProductClass"/>" class="text" readonly >
            <input type="hidden" name="txtProductClass" value="<tbl:writeparam name="txtProductClass"/>"></td>
        </tr>

        <tr>
            <td class="list_bg2"><div align="right">产品名称</div></td>
            <td class="list_bg1"><input type="text" name="txtProductName" size="25"  value="<tbl:writeparam name="txtProductName"/>" class="text" readonly ></td>
            <td class="list_bg2"><div align="right">新建业务帐户标志</div></td>
            <td class="list_bg1"><input type="text" name="NewSAFlag" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="txtNewSAFlag"/>" class="text" readonly >
            <input type="hidden" name="txtNewSAFlag" value="<tbl:writeparam name="txtNewSAFlag"/>" ></td>
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">有效期起始</div></td>
            <td class="list_bg1"><input type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom" />" class="text" readonly >
	    <td class="list_bg2"><div align="right">有效期截止</div></td>
            <td class="list_bg1"><input type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo" />" class="text" readonly >	      
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">状态</div></td>
            <td class="list_bg1" colspan="3"><input type="text" name="Status" size="25"  value="<d:getcmnname typeName="SET_P_PRODUCTSTATUS" match="txtStatus"/>" class="text" readonly ></td>
            <input type="hidden" name="txtStatus" value="<tbl:writeparam name="txtStatus"/>" >
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">产品描述</div></td>
            <td class="list_bg1" colspan="3"><textarea name="txtDescription"  cols="80" rows="4" readonly ><tbl:writeparam name="txtDescription" /></textarea></td>
        </tr> 
    </table>
    <table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
     <tr>
          <td class="list_bg2" height=30><div align="center">已经关联的设备型号</div></td>
          <td class="list_bg2" ></td> 
          <td class="list_bg2" ><div align="center">可选的设备型号</div></td>
     </tr>
     <tr>
          <td class="list_bg2" align="center" ><font size="2">
          <iframe SRC="connected_device_class.screen?txtProductID=<tbl:writeparam name="txtProductID" />&checkFlags=<%=checkFlags%>"
            id="ConnectedDevice" name="ConnectedDevice" width="350" height="300">
  	      </iframe><br><br>
          </font></td>
           <td width=40 class="list_bg2">
	       <table align="center" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	    	<td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
						value="添&nbsp;加" onclick="javascript:add_device()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
	           
	           </tr><tr>
	           <td height=20 ></td>
	           </tr>
	           <tr>
	           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
						value="删&nbsp;除" onclick="javascript:delete_device()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td>      
	           
	         </tr>
	      </table>   
      </td>
          <td class="list_bg2" align="center" colspan="2"><font size="2">
          <iframe SRC="optional_device_class.screen?txtProductID=<tbl:writeparam name="txtProductID" />&checkFlags=<%=checkFlags%>"
            id="OptionalDevice" name="OptionalDevice" width="350" height="300">
  	      </iframe> <br><br>
          </font></td>
     </tr>
      </table> 
      <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
                        
           <td><img src="img/button2_r.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="<bk:backurl property="product_manage_view.do"/>" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" border="0" ></td>
         </tr>
      </table> 
      <br>  
      <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      
      
 
<tbl:generatetoken />
<input type="hidden" name="deviceID" value=""/>
<input type="hidden" name="checkFlags" value=""/>
<input type="hidden" name="Action" value=""/>
<input type="hidden" name="func_flag" value="110"/>
</form>

<Script language="javascript">
<!--
function checkForm() {
    document.frmPost.deviceID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.ConnectedDevice.txtDeviceModel != null) {
        if (document.frames.ConnectedDevice.txtDeviceModel.length > 1) {
            for (i = 0; i < document.frames.ConnectedDevice.txtDeviceModel.length; i++) {
                if (document.frames.ConnectedDevice.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.ConnectedDevice.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.deviceID.value=document.frames.ConnectedDevice.txtDeviceModel[i].value+ ";" + document.frmPost.deviceID.value ;
                }
            }
        } else {
            if (document.frames.ConnectedDevice.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.ConnectedDevice.Flags.value;
                document.frmPost.deviceID.value = document.frames.ConnectedDevice.txtDeviceModel.value;
            }
        }
        if (document.frmPost.deviceID.value == '') {
            alert("请选定设备型号！");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}
function checkAddForm() {
    document.frmPost.deviceID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.OptionalDevice.txtDeviceModel != null) {
        if (document.frames.OptionalDevice.txtDeviceModel.length > 1) {
            for (i = 0; i < document.frames.OptionalDevice.txtDeviceModel.length; i++) {
                if (document.frames.OptionalDevice.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.OptionalDevice.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.deviceID.value=document.frames.OptionalDevice.txtDeviceModel[i].value+ ";" + document.frmPost.deviceID.value ;
                }
            }
        } else {
            if (document.frames.OptionalDevice.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.OptionalDevice.Flags.value;
                document.frmPost.deviceID.value = document.frames.OptionalDevice.txtDeviceModel.value;
            }
        }
        if (document.frmPost.deviceID.value == '') {
            alert("请选定设备型号！");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}

function delete_device() {
    if (checkForm()) {
        document.frmPost.Action.value="DeletDeviceModel";
        document.frmPost.action="delete_devicemodel.do";
        document.frmPost.submit();
    }
}

function add_device() {
    if (checkAddForm()) {
        document.frmPost.Action.value="AddDeviceModel";
        document.frmPost.action="add_devicemodel.do";
        document.frmPost.submit();
    }
}
//-->
</Script>