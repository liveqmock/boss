<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>

<%
    int customerid=Integer.valueOf(request.getParameter("txtCustomerID")).intValue();
    pageContext.setAttribute("ServiceAccountList", Postern.getNoCancelServiceAccountIDMapByCustID(customerid));
    String strParentKey=request.getParameter("txtSubtype");
    Map  configMap=Postern.getConfigCustomizeFeeMap("SET_W_CUSTOMIZEFEECONFIGURATION",strParentKey);
    pageContext.setAttribute("SET_W_CUSTOMIZEFEECONFIGURATION", configMap);
%>

<script language=javascript>
<!--
function check_frm(){
    if (check_Blank(document.frmPost.txtJobType, true, "工单类型"))
    		return false;
    if(document.frmPost.txtJobType.value=="C"){
        if (check_Blank(document.frmPost.txtSubtype, true, "工单子类型"))
    		return false;
	}
	if (check_Blank(document.frmPost.txtDistrictID, true, "区域"))
		return false;
	if (check_Blank(document.frmPost.txtDetailAddress, true, "详细地址"))
		return false;
	if (check_Blank(document.frmPost.txtManualCreateReason, true, "创建原因"))
		return false;	
	if(document.frmPost.txtPreferedDate!=null&& document.frmPost.txtPreferedDate.value!=""){
        if (!check_TenDate(document.frmPost.txtPreferedDate, true, "预约上门日期")) 
        	return false;
        if (check_Blank(document.frmPost.txtPreferedTime, true, "预约上门时间段"))
        	return false;
        if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"预约上门日期必须在今天以后"))
        	return false;
	}
	if((document.frmPost.txtOldDistrictID!=null&& document.frmPost.txtOldDistrictID.value!="")||
	    (document.frmPost.txtOldDetailAddress!=null&& document.frmPost.txtOldDetailAddress.value!="")){
	    if (check_Blank(document.frmPost.txtOldDistrictID, true, "原区域"))
            return false;
        if (check_Blank(document.frmPost.txtOldDetailAddress, true, "原详细地址"))
        	return false; 
	}
	if(!check_Num(document.frmPost.txtAddPortNumber,true,"新增端口数"))return false;
	return true;	
}
function frm_next(){
	if (check_frm()) {
	    if (check_Blank(document.frmPost.txtAccount, true, "付费帐户"))
		    return false;
		document.frmPost.txtScreenDirect.value="calculatefee";  
	    document.frmPost.action="manual_open_jobcard_calcalatefee.do";
		document.frmPost.submit();
	}
}

function changeSubtype()
{
    if(document.frmPost.txtJobType.value=="C"){
        document.FrameUS.submit_update_select('jobtypetosubtype', document.frmPost.txtJobType.value, 'txtSubtype', '');
        document.frmPost.txtSubtype.disabled="";
   }else{
        document.FrameUS.submit_update_select('jobtypetosubtype', document.frmPost.txtJobType.value, 'txtSubtype', '');
        document.frmPost.txtSubtype.disabled="true"
   }
}
function changeCustomizeFee()
{
    
    document.FrameUS.submit_update_select('subtypetocustomizefee', document.frmPost.txtSubtype.value, 'txtCustomizeFee', '');
    <%String configValue=Postern.getSystemSettingValue("SET_C_AUTOGET_ORIGINADDR_FOR_CREATEJOBCARD");
     if(configValue==null)configValue="";
    %> 
    if(checkContain(document.frmPost.txtSubtype.value,'<%=configValue%>')){
        document.FrameUA.submit_update_select(document.frmPost.txtCustomerID.value);
    }else{
        parent.document.frmPost.txtOldDistrictID.value ="";
        parent.document.frmPost.txtOldDistrictDesc.value ="";
        parent.document.frmPost.txtOldDetailAddress.value ="";
    }
    if(document.frmPost.txtSubtype.value=="Z" || document.frmPost.txtSubtype.value=="M" ){
        document.frmPost.txtAddPortNumber.disabled="";
        document.frmPost.txtAddPortNumber.value="1";
    }else{
        document.frmPost.txtAddPortNumber.value="";
        document.frmPost.txtAddPortNumber.disabled="true";
    }
}
function select_oldaddress()
{
      var  customerID =document.frmPost.txtCustomerID.value;
      var  strFeatures = "width=500px,height=300px,resizable=no,toolbar=no,scrollbars=yes";
      window.open("customer_move_old_address_select.screen?txtCustomerID="+customerID,"",strFeatures);
}
function checkContain(macthStr,originStr){
    var isMatch=false;
    if(originStr!=null && originStr!="" && macthStr!=null && macthStr!=""){
        while(originStr.length!=0){
            var lengthIndex=originStr.indexOf("/");
            if(lengthIndex!=-1){
                if(macthStr==originStr.substring(0,lengthIndex)){
                    isMatch=true;
                    break;
                }else{
                    originStr=originStr.substring(lengthIndex+1);
                }
            }else{
                if(macthStr==originStr){
                    isMatch=true;
                    break;
                }else{
                    isMatch=false;
                    break;
                }
            } 
        }       
    }
    return isMatch;   
}
-->
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
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<iframe SRC="update_address.screen" name="FrameUA" width="0" height="0" frameborder="0" scrolling="0">
</iframe>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
		<table  border="0" align="center" cellpadding="0" cellspacing="10" >
		  <tr>
		    <td><img src="img/list_tit.gif" width="19" height="19"></td>
		    <td class="title">手工开单</td>
		  </tr>
		</table>
		<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		  <tr>
		    <td><img src="img/mao.gif" width="1" height="1"></td>
		  </tr>
		</table>
		<br>
		<form name="frmPost"  method="post" action="">
		<input type="hidden" name="confirm_post" value="FALSE">
		<input type="hidden" name="txtCreateJobForCust"   value="<tbl:writeparam name="txtCreateJobForCust" />" >
		<input type="hidden" name="txtCustomerType"   value="<tbl:writeparam name="txtCustomerType" />" >
        <input type="hidden" name="txtOpenSourceType"   value="<tbl:writeparam name="txtOpenSourceType" />" >
        <input type="hidden" name="txtAddressID" value="<tbl:writeparam name="txtAddressID" />">
        <input type="hidden" name="txtCatvID" value="<tbl:writeparam name="txtCatvID" />">
        <input type="hidden" name="txtScreenDirect" value="">
		<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
		      <td colspan="4" class="import_tit" align="center"><font size="3">客户联系信息</font></td>
	        </tr>
	        <tr> 
		      <td colspan="4" class="list_bg2" align="left" ><font color="red">提示：上门处理需要详细填写下面所有信息</font></td>
	        </tr>
			<tr> 
                <td class="list_bg2"><div align="right">客户证号</div></td>
                <td class="list_bg1"> <input type="text" name="txtCustomerID" class="textgray" readonly size="25" value="<tbl:writeparam name="txtCustomerID" />"></td>
                <td class="list_bg2"><div align="right">客户姓名</div></td>
                <td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName" />"  ></td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">固定电话</div></td>
                <td class="list_bg1"> <input type="text" name="txtTelephone"  size="25" value="<tbl:writeparam name="txtTelephone" />"></td>
                <td class="list_bg2"><div align="right">移动电话</div></td>
                <td class="list_bg1"><input type="text" name="txtTelephoneMobile" size="25"  value="<tbl:writeparam name="txtTelephoneMobile" />"  ></td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">预约上门时间</div></td>
                <td class="list_bg1"> 
                <input type="text" name="txtPreferedDate"  size="25" value="<tbl:writeparam name="txtPreferedDate" />">
                <IMG onclick="calendar(document.frmPost.txtPreferedDate)" src="img/calendar.gif" style=cursor:hand border="0">
                </td>
                <td class="list_bg2"><div align="right">预约上门时间段</div></td>
                <td class="list_bg1"><d:selcmn name="txtPreferedTime" mapName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" width="23" defaultFlag ="true" showAllFlag="false" /></td>
			</tr>
			<tr> 
		      <td colspan="4" class="import_tit" align="center"><font size="3">派工信息</font></td>
	        </tr>
			<tr> 
                <td class="list_bg2"><div align="right">工单类型*</div></td>
                <td class="list_bg1"> 
                <input type="hidden" name="txtJobType"   value="C" >
                <input type="text" name="txtJobTypeShow" class="textgray" readonly size="25" value="模拟电视施工">
                <!--
                <d:selcmn name="txtJobType" mapName="SET_W_JOBCARDTYPE" style="width: 187px" onchange="changeSubtype()"  match="txtJobType" defaultFlag ="true" showAllFlag="false"  empty="false" />
                -->
                </td>
                <td class="list_bg2"><div align="right">工单子类型</div></td>
                <td class="list_bg1"><d:selcmn name="txtSubtype" mapName="SET_W_JOBCARDSUBTYPE" width="23"  onchange="changeCustomizeFee()" match="txtSubtype" defaultFlag ="true" showAllFlag="false" judgeGradeFlag="true"   /></td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">区域*</div></td>
                <td class="list_bg1">
						<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
						<input type="text" name="txtDistrictDesc" size="25" maxlength="50" readonly
							value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
						<input name="selDistButton" type="button" class="button" value="选择"
							onClick="javascript:sel_district('leaf','txtDistrictID','txtDistrictDesc')">
				</td>
                <td class="list_bg2"><div align="right">详细地址*</div></td>
                <td class="list_bg1"><input type="text" name="txtDetailAddress" size="25"  value="<tbl:writeparam name="txtDetailAddress" />"></td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">付费帐户</div></td>
                <td class="list_bg1"> 
                    <d:selAccByCustId name="txtAccount" mapName="self" canDirect="true" match="txtAccount" empty="false" style="width: 187px" />
                </td>
                <td class="list_bg2"><div align="right">创建原因</div></td>
                <td class="list_bg1"><d:selcmn name="txtManualCreateReason" mapName="SET_W_JOBCARDCREATEREASON" style="width: 187px"  match="txtManualCreateReason" defaultFlag ="true" showAllFlag="false" judgeGradeFlag="true"  empty="false"/></td>
			</tr>
			<tr> 
		    	<td valign="middle" class="list_bg2" align="right">
					业务帐户
				</td>
				<td class="list_bg1">
					<tbl:select name="txtServiceAccountID" set="ServiceAccountList" match="txtServiceAccountID" style="width: 187px" empty="false" />
				</td>
                <td class="list_bg2"><div align="right">新增端口数</div></td>
                <td class="list_bg1"> 
                <input type="text" name="txtAddPortNumber" size="25"  value="<tbl:writeparam name="txtAddPortNumber" />">
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">原区域</div></td>
                <td class="list_bg1">
						<input type="hidden" name="txtOldDistrictID" value="<tbl:writeparam name="txtOldDistrictID" />">
						<input type="text" name="txtOldDistrictDesc" size="25" maxlength="50" readonly
							value="<tbl:WriteDistrictInfo property="txtOldDistrictID" />" class="text">
						<input name="selDistButton" type="button" class="button" value="选择"
							onClick="javascript:sel_district('leaf','txtOldDistrictID','txtOldDistrictDesc')">
				</td>
                <td class="list_bg2"><div align="right">原详细地址</div></td>
                <td class="list_bg1"><input type="text" name="txtOldDetailAddress" size="25"  value="<tbl:writeparam name="txtOldDetailAddress" />">
                <!--<input type="button" class="button" value="取得地址" onClick="javascript:select_oldaddress()">-->
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">费用</div></td>
                <td class="list_bg1" colspan="3">
                    <tbl:select name="txtCustomizeFee" set="SET_W_CUSTOMIZEFEECONFIGURATION" match="txtCustomizeFee" width="23" /> 
                
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">备注</div></td>
                <td class="list_bg1" colspan="3">
                <textarea name="txtComments"  length="5" cols=83 rows=3><tbl:writeSpeCharParam name="txtComments" /></textarea>
                </td>
			</tr>
        </TABLE>	
	    </form> 
	    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
          <tr>
            <td><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
        </table>
	    <table align="center" border="0" cellspacing="0" cellpadding="0">
            <BR>
            <tr>
               <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
               <td><input name="next" type="button" class="button" onClick="javascript:frm_next()" value="下一步"></td>
               <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
            </tr>
       </table>     
    </TD>
</TR>
</TABLE>
<script language=javascript>
<!--
if(document.frmPost.txtSubtype.value!="Z"&& document.frmPost.txtSubtype.value!="M"){
    document.frmPost.txtAddPortNumber.value="";
    document.frmPost.txtAddPortNumber.disabled="true";  
}
else
    document.frmPost.txtAddPortNumber.disabled="";
-->
</script>