<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<%
  //是否起用设备用途：
	String devicePurpose = Postern.getSystemsettingValueByName("SET_D_DEVICEPURPOSE");
	String txtZoneStation = request.getParameter("txtZoneStation");
%>

<script language=javascript>
function check_form(){
    
    if (document.frmPost.txtDeviceID.value!="")
    {  	
       	if (!check_Num(document.frmPost.txtDeviceID, true, "设备号"))
		return false;
    }
    //if (document.frmPost.txtDepotID.value!="" && document.frmPost.txtOrgID.value!="")
    //{  	
    //   	alert("不同同时选择仓库和所属组织！");
	  //    return false;
    //}
    		
    return true;
}

function query_submit()
{
	if("B"==document.frmPost.txtZoneStation.value)
			document.frmPost.txtAddressID.value = document.frmPost.txtOrgDesc.value;
  if (check_form()) document.frmPost.submit();
}

function view_detail_click(strId)
{
	self.location.href="dev_detail.do?txtDeviceID="+strId;
}

function ChangeDeviceClass()
{
    
    document.FrameUS.submit_update_select('devclasstomodel', document.frmPost.txtDeviceClass.value, 'txtDeviceModel', '');
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


<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
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

 
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<TABLE border="0" cellspacing="0" cellpadding="0" width="822" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
  
<form name="frmPost" action="dev_query_result.do" method="post" >    
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
      <tr>
       <td class="list_bg2"><div align="right">设备ID</div></td>
        <td class="list_bg1">
         <input type="text" name="txtDeviceID" maxlength="10" size="25" value="<tbl:writeparam name="txtDeviceID" />" >
      </td>
      <td class="list_bg2"><div align="right">批号</div></td>
       <td class="list_bg1">
        <input type="text" name="txtBatchNo" maxlength="50" size="25" value="<tbl:writeparam name="txtBatchNo" />" >
      </td> 
    </tr>
    <tr>  
      <td class="list_bg2"><div align="right">设备类型</div></td>
       <td class="list_bg1">
<%
    Map mapDepots = Postern.getAllDepot();
    
    Map mapDeviceClasses = Postern.getAllDeviceClasses();
    pageContext.setAttribute("AllDeviceClasses",mapDeviceClasses);
    
%>
        <tbl:select name="txtDeviceClass" set="AllDeviceClasses" match="txtDeviceClass" width="23" onchange="ChangeDeviceClass()" />
      </td>
      <td class="list_bg2"><div align="right">设备型号 </div></td>
      <td class="list_bg1">
        <d:seldevicemodel name="txtDeviceModel" deviceClass="txtDeviceClass"  match="txtDeviceModel" width="23"/>
     </td>
    </tr>  
    <tr>  
       <td class="list_bg2"><div align="right">序列号</div></td>
      <td colspan="4" class="list_bg1" align="left">
          <input type="text" name="txtSerialNoBegin" maxlength="25" size="25" value="<tbl:writeparam name="txtSerialNoBegin" />" >
          ---<input type="text" name="txtSerialNoEnd" maxlength="25" size="25" value="<tbl:writeparam name="txtSerialNoEnd" />" >
           <font size="2" color="red" >(只填写第一个框则为精确查询)</font>
      </td>
         
    </tr>
  
    <tr>
    <td class="list_bg2"><div align="right">CM MAC地址</div></td>
    <td class="list_bg1"><input name="txtMacAddress" type="text" class="text" size="25" maxlength="25" value="<tbl:writeparam name="txtMacAddress" />"></td>
    <td class="list_bg2"><div align="right">终端MAC地址</div></td>
    <td class="list_bg1"><input name="txtInterMacAddress" type="text" class="text" size="25" maxlength="25" value="<tbl:writeparam name="txtInterMacAddress" />"></td>
  </tr>
   
    <tr> 
       <td class="list_bg2"><div align="right">是否二手</div></td>
       <td class="list_bg1">
        <d:selcmn name="txtUseFlag" mapName="SET_D_USEFLAG" match="txtUseFlag" width="23" />
      </td>  
      <td class="list_bg2"><div align="right">状态</div></td>
      <td class="list_bg1">
        <d:selcmn name="txtStatus" mapName="SET_D_DEVICESTATUS" match="txtStatus" width="23" />
      </td>  
    </tr>        
      <tr> 
       <td class="list_bg2"><div align="right">是否预授权</div></td>
       <td class="list_bg1">
        <d:selcmn name="txtPreAuthFlag" mapName="SET_G_YESNOFLAG" match="txtPreAuthFlag" width="23" />
      </td>  
       <td class="list_bg2"><div align="right">是否配对</div></td>
       <td class="list_bg1">
        <d:selcmn name="txtMatchFlag" mapName="SET_G_YESNOFLAG" match="txtMatchFlag" width="23" />
      </td>  
    </tr> 
    
    
    
    <!--
    <tr> 
       <td class="list_bg2"><div align="right">仓库</div></td>
      <td class="list_bg1">
        <d:seldepotbyoper name="txtDepotID" match="txtDepotID" width="23" />
      	</td>
      <td class="list_bg2" align="right">所属组织</td>
      <td class="list_bg1">
    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
	    <input type="text" name="txtOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
	    <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('all','txtOrgID','txtOrgDesc')">
    </td>		
    </tr> 
    -->
    
    
    <tr> 
        <td class="list_bg2"><div align="right">地址类型</div></td>
         <td class="list_bg1">
         <d:selcmn name="txtZoneStation" mapName="SET_D_DEVICEADDRESSTYPE" match="txtZoneStation" width="23" onchange="change_zone2(document.frmPost.txtZoneStation.value,false)" />
         </td>
        <!--根据属主类型显示仓库或分公司-->
        <td class="list_bg2"><div>&nbsp;</div><div align="right">地址名称</div></td>
	      <td class="list_bg1"><div id="modelFieldNameDesc" style="color:red">请填写客户ID</div>
	    	<input type="hidden" name="txtAddressID" value="<tbl:writeparam name="txtAddressID" />">
	    	<d:seldepotbyoper name="txtDepotID" match="txtDepotID" width="23" onchange="change_zone3()" />
		    <input type="text" id="txtOrgDesc" name="txtOrgDesc" size="25" maxlength="50" value="<tbl:WriteOrganizationInfo property="txtAddressID" />" >
		    <input id="selOrgButton" name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('all','txtAddressID','txtOrgDesc')">
		    
    </td>			
      </tr>
    
    
    
    
    <%if("Y".equals(devicePurpose)){%>
		<tr>
		<td  class="list_bg2" align="right">设备用途</td>
	         <td class="list_bg1" colspan = "3">
	    <input name="txtPurposeStrListValue" type="text" class="text" readonly maxlength="200" size="22" value="<tbl:writeparam name="txtPurposeStrListValue" />">
	    	
	    <input name="txtPurposeStrList" type="hidden" value="<tbl:writeparam name="txtPurposeStrList" />">
	   	<input name="checkbutton" type="button" class="button" value="选择" onClick="javascript:open_select('SET_D_DEVICEUSEFORPURPOSE','txtPurposeStrList',document.frmPost.txtPurposeStrList.value,'','','')"> 
	   </td>	 
		</tr>
		<%}%>      
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<pri:authorized name="dev_query_result.export">
				<td width="20" ></td>  
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="javascript:download(document.frmPost,'设备查询')" class="btn12">导出查询结果</a></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		</pri:authorized>
		  </tr>
	  </table> 
	  </td>
  </tr>
  </table>   
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">

  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
  <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
     
          <td class="list_head" >设备ID</td>
          <td class="list_head">设备类型</td>
          <td class="list_head">设备型号</td>          
          <td class="list_head">序列号</td>
          <td class="list_head">置位</td>
          <td class="list_head">配对</td>
           <td class="list_head">预授权</td>
           <!--
          <td class="list_head">地址类型</td>
          -->
          <td class="list_head">地址</td>  
          <td class="list_head">状态</td>
          <td class="list_head">CM MAC地址</td>  
          <td class="list_head">终端MAC地址</td>
        </tr> 
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	TerminalDeviceDTO dto = (TerminalDeviceDTO)pageContext.getAttribute("oneline");
	String strClass = Postern.getDeviceClassDesc(dto.getDeviceClass());
	String strModel = Postern.getDeviceModelDesc(dto.getDeviceModel());
	String strAddressType = dto.getAddressType();
	if (strAddressType==null) strAddressType="";
	String strAddress = null;
	
	if (strAddressType.equals("D"))  //仓库
	{
		strAddress = (String)mapDepots.get(String.valueOf(dto.getAddressID()));
	}
	else if (strAddressType.equals("T"))  //组织机构
	{
		strAddress = Postern.getOrganizationDesc(dto.getAddressID());
	}
	else if (strAddressType.equals("B"))  //用户
	{
		strAddress = Postern.getCustomerNameById(dto.getAddressID());
	}
	
	if (strAddress==null) strAddress="";
	
				    
%>
	<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      	  <td align="center" nowrap><a href="javascript:view_detail_click('<tbl:write name="oneline" property="DeviceID"/>')" class="link12" ><tbl:writenumber name="oneline" property="DeviceID" digit="7" /></a></td>
      	  <td align="center" nowrap><%=strClass%></td>
      	  <td align="center" nowrap><tbl:write name="oneline" property="DeviceModel"/></td>
      	  <td align="center" nowrap><tbl:write name="oneline" property="SerialNo"/></td>
      	  <td align="center" width="20"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:CaSetFlag"/></td>
      	  
      	  <td align="center" width="20"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:matchFlag"/></td>
      	  <td align="center" width="30"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:preAuthorization"/></td>
      	  <!--
      	  <td align="center" width="30"><d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="oneline:AddressType"/></td>
      	  -->
      	  <td align="center" width="100"><%=strAddress%></td>                        
      	  <td align="center" nowrap><d:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:Status"/></td>
      	  <td align="center" width="100" nowrap><tbl:write name="oneline" property="mACAddress"/></td>
      	  <td align="center" width="100" nowrap><tbl:write name="oneline" property="interMACAddress"/></td>
    	</tr>
</lgc:bloop>  
 <tr>
    <td colspan="12" class="list_foot"></td>
  </tr>
   
  
 
  
   <tr>
              <td align="right" class="t12" colspan="12" >
                 第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
                </rs:notlast>
                &nbsp;
                转到
               <input type="text" name="txtPage" class="page_txt">页 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>
 
  </rs:hasresultset> 
	
</form> 
    </td>
  </tr>
</table>

<script language=javascript>
change_zone("<%=txtZoneStation%>");
function change_zone(txtZoneStation)
{
	if("D"==txtZoneStation)//属主类型是仓库
	{
		document.all["selOrgButton"].style.display="none";
		document.frmPost.txtOrgDesc.readOnly=true;
		document.getElementById('modelFieldNameDesc').innerHTML='';
		document.frmPost.txtDepotID.style.width=188;
		document.frmPost.txtOrgDesc.style.width=0;
	}
	else if("T"==txtZoneStation)
	{
		document.all["selOrgButton"].style.display="";
		document.frmPost.txtOrgDesc.readOnly=true;
		document.getElementById('modelFieldNameDesc').innerHTML='';
		document.frmPost.txtDepotID.style.width=0;
			document.frmPost.txtOrgDesc.style.width=188;
	}
	else if("B"==txtZoneStation)
	{	
		document.all["selOrgButton"].style.display="none";
		document.frmPost.txtOrgDesc.readOnly=false;
		document.getElementById('modelFieldNameDesc').innerHTML='请填写客户证号';
		document.frmPost.txtOrgDesc.value = document.frmPost.txtAddressID.value;
		document.frmPost.txtOrgDesc.innerText = document.frmPost.txtAddressID.value;
		document.frmPost.txtDepotID.style.width=0;
			document.frmPost.txtOrgDesc.style.width=188;
	}
	else 
	{
	  document.frmPost.txtOrgDesc.readOnly=false;
	  document.all["selOrgButton"].style.display="none";
		document.frmPost.txtAddressID.value = "";
		document.getElementById('modelFieldNameDesc').innerHTML='';
		document.frmPost.txtDepotID.style.width=0;
	}
}

function get_select_text(selObj){   
  var retValue = "";   
  for(i = 0; i<selObj.options.length;i++)   
  {   
     if(selObj.options[i].selected)  
     { 
        retValue=selObj.options[i].text;
        break;   
     }
  }
  return retValue;
}   

function change_zone2(txtZoneStation,firEnter){
	//var customerId=document.frmPost.txtCustomerID.value;
	var strTitle = "";
	if("D"==txtZoneStation)//属主类型是仓库
	{
			strTitle = "仓库选择";
			document.frmPost.txtDepotID.style.width=188;
			document.frmPost.txtOrgDesc.style.width=0;
			document.all["selOrgButton"].style.display="none";
	}
	else if("T"==txtZoneStation)//属主类型是组织机构
	{
			strTitle = "组织机构选择";
			document.frmPost.txtDepotID.style.width=0;
			document.frmPost.txtOrgDesc.style.width=188;
			document.all["selOrgButton"].style.display="";
			document.frmPost.txtDepotID.value = "";
	}
  else if("B"==txtZoneStation)
  {
  		strTitle = "客户ID录入";
  		document.frmPost.txtDepotID.style.width=0;
			document.frmPost.txtOrgDesc.style.width=188;
			document.all["selOrgButton"].style.display="none";
			document.frmPost.txtDepotID.value = "";
			document.getElementById('modelFieldNameDesc').innerHTML='请填写客户证号';
			document.frmPost.txtOrgDesc.innerText = "";
			document.frmPost.txtOrgDesc.readOnly=false;
  }
  else
  {
    document.all["selOrgButton"].style.display="none";
		document.frmPost.txtAddressID.value = "";
		document.frmPost.txtOrgDesc.value = "";
		document.frmPost.txtOrgDesc.innerText = "";
		document.frmPost.txtOrgDesc.readOnly=true;
		document.getElementById('modelFieldNameDesc').innerHTML='';
		document.frmPost.txtDepotID.style.width=0;
		document.frmPost.txtOrgDesc.style.width=188;
		document.frmPost.txtDepotID.value = "";
    return;
  }
	var strFeatures = "top=120px,left=120px,width=350px,height=350px,resizable=no,toolbar=no,scrollbars=no";
	window.open("change_zone.screen?txtZoneStation="+txtZoneStation+"&strTitle="+strTitle,strTitle,strFeatures);
}

function change_zone3(){
document.frmPost.txtAddressID.value = document.frmPost.txtDepotID.value;
}

</script>  
 

     
      
      
      
      
      