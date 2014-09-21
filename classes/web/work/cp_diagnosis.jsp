<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>
function frm_submit(){
	if(frm_check1())
		document.frmPost.submit();	
}
function frm_check1(){
	if(!check_Bidimconfig()){		
		return false;
	}
	if(document.frmPost.txtDiagnosisResult.value == ''){
		alert("请选择诊断结果！");
		return false;
	}	
	return true;
}
function frm_check2(){ 

	if(document.frmPost.txtDiaName.value != ''){
		var str = document.frmPost.txtDiaName.value;
		var strValue = str.split(";");	
		//var flag = false;
		for(i=0;i<strValue.length;i++){
			var txtName="txtDiaName"+"_"+strValue[i];
			//alert(txtName);		
			var txtE = document.getElementsByName(txtName);
			//alert("&&&"+txtE[0].value+"***");
			if(txtE[0].type == "checkbox"||txtE[0].type == "radio"){
			ff = false;
				for(j=0;j<txtE.length;j++){ 					 
					if(txtE[j].checked == true){
						ff = true;
					} 
				}
				if(ff == false){
					//alert(txtName+" "+txtE[0].type+"请填写诊断内容！");
					return false;
				}
			}else if( txtE[0].value ==''&&(txtE[0].type=="select-one"||txtE[0].type == "text")){
				//alert(txtName+" "+txtE[0].type+"请填写诊断内容！");
				return false;
			} 
							 			
		}
		}
	return true;
}
</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
   <td align="center" valign="top">
    <table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">报修单诊断</td>
      </tr>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
    </table>
    <br>
<form name="frmPost" method="post" action="cp_diagnosis.do">
<input type="hidden" name="confirm_post" value="FALSE">
<input type="hidden" name="txtProblemLevel" value="S">
 <input type="hidden" name="txtActionType" value="diagnosis">
 
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
 <%
   CustomerProblemDTO cpDto=(CustomerProblemDTO)pageContext.getAttribute("oneline");
   int said=cpDto.getCustServiceAccountID();
 %>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">报修单编号</div></td>
        <td class="list_bg1">
	<input type="text" name="txtID" size="25"  value="<tbl:write name="oneline" property="Id" />" class="textgray" readonly   >
	</td>
	<td class="list_bg2"><div align="right">业务帐户</div></td>
	<td class="list_bg1">
         <input type="text" name="txtServiceAccountID" size="25"  value="<tbl:write name="oneline" property="custServiceAccountID"/>" class="textgray" readonly   >
	</td>
	</tr>
	<tr>
	  <td class="list_bg2"><div align="right">收费类别</div></td>
	  <td class="list_bg1"> 
		<input type="text" name="txtFeeClass" size="25"  value="<d:getcmnname typeName="SET_C_CPFEECLASS" match="oneline:FeeClass"/>" class="textgray" readonly>   
           </font></td>
		 
	<td class="list_bg2"><div align="right">报修类别</div></td>
	<td class="list_bg1">
	 <input type="text" name="txtProblemCategoryShow"  size="25"  value="<d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:ProblemCategory"/>" class="textgray" readonly > 
	</td>
		 
	</tr>
	<tr>		 
	    <td class="list_bg2"><div align="right">故障描述</div></td>
		<td class="list_bg1" colspan="3"> 
			<textarea class="textareagray" readonly  cols="80" rows="3" name="txtProblemDesc" ><tbl:write  name="oneline" property="ProblemDesc" /></textarea>
            	</td>		 
	</tr>
	<%List deviceList=Postern.getDeviceInfoByServiceAccountID(said);
	  for(Iterator itr=deviceList.iterator();itr.hasNext();){
	  	TerminalDeviceDTO dto=(TerminalDeviceDTO)itr.next();
	  	String macAddress=((dto.getMACAddress()==null)?"":dto.getMACAddress());
	  	String interMACAddress=((dto.getInterMACAddress()==null)?"":dto.getInterMACAddress());%>
	<tr>
		  <td class="list_bg2"><div align="right">设备ID</div></td>
		  <td class="list_bg1"> 
			<input type="text" name="txtDeviceID" size="25"  value="<%=dto.getDeviceID()%>" class="textgray" readonly>   
	           </font></td>		 
		<td class="list_bg2"><div align="right">设备序列号</div></td>
		<td class="list_bg1">
		 <input type="text" name="txtSerialNo"  size="25"  value="<%=dto.getSerialNo()%>" class="textgray" readonly > 
		</td>		 
	</tr>
	<tr>
	  <td class="list_bg2"><div align="right">CM MAC地址</div></td>
	  <td class="list_bg1"> 
		<input type="text" name="txtMACAddress" size="25"  value="<%=macAddress%>" class="textgray" readonly>   
           </font></td>		 
	<td class="list_bg2"><div align="right">终端MAC地址</div></td>
	<td class="list_bg1">
	 <input type="text" name="txtInterMACAddress"  size="25"  value="<%=interMACAddress%>" class="textgray" readonly > 
	</td>		 
	</tr>
	<%}%>	
<input type="hidden" name="txtAddressId" value="<tbl:write name="oneline" property="addressID"/>">
<input type="hidden" name="txtAccount" value="<tbl:write name="oneline" property="custAccountID"/>">

  <tr> 
	   <td colspan="4" class="import_tit" align="center"><font size="3">诊断信息</font></td>
  </tr>
  <tr>
  <td colspan="4">
  <%  
	     int  saId =  WebUtil.StringToInt(request.getParameter("txtServiceAccountID"));
	     int serviceID = Postern.getServiceIDByAcctServiceID(saId);	     	
	  %>
  <tbl:dynamicservey serveyName="txtDiaName"  serviceID="<%=String.valueOf(serviceID)%>"  serveyType="D"  serveySubType="N"  showType="text" tdWordStyle="list_bg2" 
     tdControlStyle="list_bg1" controlSize="25"  checkScricptName ="check_Bidimconfig()"/> 
  </td>
  </tr> 
  <tr>		 
		<td class="list_bg2" width="17%"><div align="right">诊断结果</div></td>
		<td class="list_bg1" colspan="3"> 
			<d:selcmn name="txtDiagnosisResult" mapName="SET_W_ROLEORGANIZATIONDIAGNOSISRESULT" match="txtDiagnosisResult" width="23" />
            	</td>		 
	</tr> 
	<tr>		 
		<td class="list_bg2" width="17%"><div align="right">备&nbsp&nbsp注</div></td>
		<td class="list_bg1" colspan="3"> 
			<textarea name="txtMemo"  length="5" cols=80 rows=3><tbl:writeSpeCharParam name="txtMemo" /></textarea>
            	</td>		 
	</tr>
  </table>
  
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
             <td background="img/button_bg.gif"  ><a href="<bk:backurl property='cp_query_detail.do/cp_diagnosis_query.do' />" class="btn12">返&nbsp;回</a></td>           
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
              <td width="20" ></td>      
          <td><img src="img/button_l.gif" border="0" ></td> 
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">下一步</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>         
      </tr>
</table>  
	</lgc:bloop>    
       <br>    
 </form>  

 
 

   

 