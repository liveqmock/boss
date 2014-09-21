<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.dto.CustomerComplainDTO,
                 com.dtv.oss.dto.CustComplainProcessDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
				 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>

<%
	String actiontype=request.getParameter("actiontype");
	String title="";
	String txtCustomerId="";
	if("transfer".equals(actiontype)){
		title="�ͻ�Ͷ����ת";
	}else if("query".equals(actiontype)){
		title="�ͻ�Ͷ��";
	}else if("manualend".equals(actiontype)){
		title="�ֹ�����Ͷ�ߵ�";
	}else if("callback".equals(actiontype)){
		title="Ͷ�߻ط�";
	}else if("process".equals(actiontype)){
		title="Ͷ�ߴ���";
	}else if("custQuery".equals(actiontype)){
		txtCustomerId=request.getParameter("txtCustomerId");
		title="�ͻ�Ͷ��";
	}

%>
<script language=javascript>
	function check_transfertype(){
	var str=document.frmPost.selType!=null?document.frmPost.selType.value:""; 
		if(  'auto'==str && (''==document.frmPost.txtAutoNextOrgID.value||0==document.frmPost.txtAutoNextOrgID.value)){
			alert("��ָ����ת���ţ�");
			return false;
		}
		if('manual'==str){
			if(''==document.frmPost.txtNextOrgID.value){
			       	alert("�ֹ���ת��ָ����ת���ţ�");
			       	return false;
		       	}
		}
	document.frmPost.transferType.value=str;
	return true;
	}
	function cust_back(custid){	
		self.location.href="cust_complain_queryAction.do?actiontype=custQuery&txtFrom=1&txtTo=10&txtCustomerId="+custid+"&txtQueryType="+document.frmPost.txtQueryType.value;	
	}
	
	function cust_camplain_transfer(){
		if(check_transfertype()){
		document.all.frmPost.action="cust_camplain_transferAction.do";
		document.all.frmPost.submit(); 
		}		
	}
	function cust_camplain_manualEnd(){
		if(document.frmPost.txtDescriptionManual.value==""){
			alert("�����������Ϣ��");
			return;
		}
		document.all.frmPost.action="cust_camplain_manualEndAction.do";
		document.all.frmPost.submit();
	}
	function cust_camplain_callback(callback){
		if(!check_Bidimconfig())
			return;
		document.all.frmPost.action="cust_camplain_callbackAction.do?callback="+callback;
		document.all.frmPost.submit();
	}
	function cust_camplain_process(){
		l=document.getElementsByName("txtProResult") ;
		var str; 
		for(i=0;i<l.length;i++)  
		{  
			if(l[i].checked){
			  str=l[i].value;
			  document.all.frmPost.action="customer_complain_process.do?txtProResult="+str;
			  document.all.frmPost.submit();
			} 
		}
		
	}
	function frmSubmit(url){
		document.frmPost.action = url;
	        document.frmPost.submit();
	}
	function frmBack(str){
		self.location.href = "cust_complain_queryAction.do?actiontype=query&txtFrom=1&txtTo=10&txtQueryType="+str;		
	}
	function mod_organization(){
		var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	  	window.open("transfer_org_list.do?strRole=C","��ת����",strFeatures);
	}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><%=title%></td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 
<form name="frmPost" action="cust_complain_queryAction.do" method="post" > 
<input type="hidden" name="transferType" value="<tbl:writeparam name="transferType"/>">
<input type="hidden" name="txtQueryType" value="<tbl:writeparam name="txtQueryType"/>">
<%String str=request.getParameter("txtCustComplainId");%>

<rs:hasresultset>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   <%
	 	CustomerComplainDTO customerComplainDTO=(CustomerComplainDTO)pageContext.getAttribute("oneline");
   		String type=customerComplainDTO.getType();
	 	String custName=Postern.getCustomerNameByID(customerComplainDTO.getCustomerId()); 
	 	String txtStatus="";
	 	if(customerComplainDTO.getStatus()!=null)
	 		txtStatus=customerComplainDTO.getStatus();
	 	String callbackName="δ�ط�";
	 	if(customerComplainDTO.getCallBackFlag()!=null&&customerComplainDTO.getCallBackFlag().equals("Y"))
	 		callbackName="�ѻط�";
	 	Map mp=new HashMap();
	 	mp=Postern.getCallbackInfo(CommonKeys.CALLBACKINFOTYPE_C,customerComplainDTO.getCustComplainId());
	 	pageContext.setAttribute("mp",mp);
	    String tt="mp";
	    String orgname=Postern.getProviderNameByID(customerComplainDTO.getComplainedOrgId());
	    String curorgname=Postern.getProviderNameByID(customerComplainDTO.getCurrentWorkOrgID());
	    boolean vflag = Postern.buttonCanBeVisibleForComplain(customerComplainDTO.getCustComplainId(),CurrentLogonOperator.getOperator(request).getOrgID());
       	    pageContext.setAttribute("canBeVisible", vflag+"");
   %>
   <input type="hidden" name="txtCurrentWorkOrgID" value="<tbl:write name="oneline" property="currentWorkOrgID"/>" >  
   <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="dtLastmod"/>" >
   <%if("process".equals(actiontype)){%><input type="hidden" name="func_flag" value="5101">
   <%}else{%><input type="hidden" name="func_flag" value="210" ><%}%>
   <input type="hidden" name="actiontype" value="<%=actiontype%>" >
    
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td colspan="4" class="import_tit" align="center">�ͻ���Ϣ</td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">�ͻ�ID</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtCustomerId" size="25" value="<tbl:write name="oneline" property="customerId"/>"  class="textgray" readonly >
	  </td>
      <td class="list_bg2" width="17%"  align="right">�ͻ�����</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtCustomerName" size="25" value="<%=custName%>"  class="textgray" readonly>
	  </td>
    </tr>

     <tr>   
      <td class="list_bg2" width="17%"  align="right">��ϵ��</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtContactPerson" size="25" value="<tbl:write name="oneline" property="contactPerson"/>"  class="textgray" readonly>
	  </td>
      <td class="list_bg2" width="17%"  align="right">��ϵ�绰</td>
      <td class="list_bg1" width="33%"  align="left" >
		<input type="text" name="txtContactPhone" size="25" value="<tbl:write name="oneline" property="contactPhone"/>"  class="textgray" readonly >
	  </td>
    </tr>
 	<tr>
         <td colspan="4" class="import_tit" align="center">Ͷ����Ϣ</td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">Ͷ�߼�¼ID</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtCustComplainId" size="25" value="<tbl:write name="oneline" property="custComplainId"/>"  class="textgray" readonly >
	  </td>
       <td class="list_bg2" width="17%"  align="right">Ͷ������</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtType" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERCOMPLAINTYPE" match="<%=type%>" /> " class="textgray" readonly >
	  </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">Ͷ������</td>
      <td class="list_bg1" width="83%" colspan="3" align="left">
	  	<textarea name="txtContent" length="5" cols=82 rows=9  readonly ><tbl:write name="oneline" property="content" /></textarea>
	  </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">Ͷ������</td>
      <td class="list_bg1" width="83%" colspan="3" align="left">		
	 	<textarea name="txtRequest" length="5" cols=82 rows=9  readonly ><tbl:write name="oneline" property="request" /></textarea>
	  </td>
    </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">��Ͷ����֯</td>
      <td class="list_bg1" width="33%"  align="left"><input type="hidden" name="txtComplainedOrgId" value="<tbl:write name="oneline" property="complainedOrgId"/>" >
      <input type="text" name="txtComplainedOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="complainedOrgId" />" class="textgray">  
      </td>
      <td class="list_bg2" width="17%"  align="right">��Ͷ�߸���</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtComplainedPerson" size="25" value="<tbl:write name="oneline" property="complainedPerson"/>"  class="textgray" readonly >
	  </td>
    </tr>
     <tr>         
      <td class="list_bg2" width="17%"  align="right">����״̬</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtStatus" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERCOMPLAINSTATUS" match="<%=txtStatus%>"  /> "  class="textgray" readonly >
	  </td>
	  <td class="list_bg2" width="17%"  align="right">Ͷ�ߴ���ʱ��</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtDtCreate" size="25" value="<tbl:writedate name="oneline" property="dtCreate"/>"  class="textgray" readonly >
	  </td>
    </tr>
    <%if (actiontype.equals("custQuery")||"query".equals(actiontype)){ %>
    <tr>         
      <td class="list_bg2" width="17%"  align="right">��ǰ������</td>
      <td class="list_bg1" width="83%"  colspan="3" align="left">
		<input type="text" name="txtCurrentWorkOrgName" size="25" value="<tbl:WriteOrganizationInfo name="oneline" property="currentWorkOrgID" />"  class="textgray" readonly >
	  </td>
	  
    </tr> 
    
    <%}
    if(actiontype.equals("custQuery")||actiontype.equals("query")){ %>
    <%if(customerComplainDTO.getCallBackFlag()!=null&&"Y".equals(customerComplainDTO.getCallBackFlag())){
    %>
     <tr>
         <td colspan="4" class="import_tit" align="center">�ط���Ϣ</td>
     </tr>
      <tr>         
      <td class="list_bg2" width="17%"  align="right">�طñ�־</td>
      <td class="list_bg1" width="33%"  align="left"><%=callbackName%></td>
	  <td class="list_bg2" width="17%"  align="right">�ط�����</td>
      <td class="list_bg1" width="33%"  align="left"><tbl:writedate name="oneline" property="callBackDate"/>
	  </td>	  
    </tr>
    
   <tbl:dynamicservey serveyType="C" serveySubType="C"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" match="<%=tt%>"/>
    <%}else{ %>
    
    <%} %>
       </table>
<br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr>
    	<td><img src="img/mao.gif" width="1" height="1"></td>
  	</tr>
  </table> 
  <br>
  <%if(actiontype.equals("query")){ 
  	%>
    <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        <td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="javascript:frmBack('<tbl:writeparam name="txtQueryType"/>')" class="btn12">��    ��</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	<pri:authorized name="cust_complain_process_query.do">
	<d:displayControl id="button_customercomplain_query_detail_process" bean="oneline,canBeVisible">
	<td width="20" ></td>
	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="cust_complain_detail.do?actiontype=process&txtCustComplainId=<tbl:write name="oneline" property="custComplainId"/>" class="btn12">Ͷ�ߴ���</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	</d:displayControl>
	</pri:authorized>
	<pri:authorized name="cust_complain_manualend.do">
	<d:displayControl id="button_customercomplain_query_detail_manualend" bean="oneline,canBeVisible">
	<td width="20" ></td>
	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="cust_complain_detail.do?actiontype=manualend&txtCustComplainId=<tbl:write name="oneline" property="custComplainId"/>" class="btn12">�ֹ�����Ͷ��</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	</d:displayControl>
	</pri:authorized>
	<pri:authorized name="cust_camplain_transferAction.do">
	<d:displayControl id="button_customercomplain_query_detail_transfer" bean="oneline,canBeVisible">
	<td width="20" ></td>
	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="cust_complain_detail.do?actiontype=transfer&txtCustComplainId=<tbl:write name="oneline" property="custComplainId"/>" class="btn12">Ͷ����ת</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	</d:displayControl>
	</pri:authorized>
	<pri:authorized name="cust_camplain_callbackAction.do">
	<d:displayControl id="button_customercomplain_query_detail_callback" bean="oneline,canBeVisible">
	<td width="20" ></td>
	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="cust_complain_detail.do?actiontype=callback&txtCustComplainId=<tbl:write name="oneline" property="custComplainId"/>" class="btn12">Ͷ�߻ط�</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td>
	</d:displayControl>
	</pri:authorized> 	
      </tr>
  </table>
  <%} %>
  <%if(actiontype.equals("custQuery")){ %>
    <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        <td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="javascript:cust_back('<%=txtCustomerId%>');" class="btn12">��    ��</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	<pri:authorized name="cust_complain_process_query.do">
	<d:displayControl id="button_customercomplain_query_detail_process" bean="oneline,canBeVisible">
	<td width="20" ></td>
	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="cust_complain_detail.do?actiontype=process&txtCustComplainId=<tbl:write name="oneline" property="custComplainId"/>" class="btn12">Ͷ�ߴ���</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	</d:displayControl>
	</pri:authorized>
	<pri:authorized name="cust_complain_manualend.do">
	<d:displayControl id="button_customercomplain_query_detail_manualend" bean="oneline,canBeVisible">
	<td width="20" ></td>
	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="cust_complain_detail.do?actiontype=manualend&txtCustComplainId=<tbl:write name="oneline" property="custComplainId"/>" class="btn12">�ֹ�����Ͷ��</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	</d:displayControl>
	</pri:authorized>
	<pri:authorized name="cust_camplain_transferAction.do">
	<d:displayControl id="button_customercomplain_query_detail_transfer" bean="oneline,canBeVisible">
	<td width="20" ></td>
	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="cust_complain_detail.do?actiontype=transfer&txtCustComplainId=<tbl:write name="oneline" property="custComplainId"/>" class="btn12">Ͷ����ת</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	</d:displayControl>
	</pri:authorized>
	<pri:authorized name="cust_camplain_callbackAction.do">
	<d:displayControl id="button_customercomplain_query_detail_callback" bean="oneline,canBeVisible">
	<td width="20" ></td>
	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	<td background="img/button_bg.gif"><a href="cust_complain_detail.do?actiontype=callback&txtCustComplainId=<tbl:write name="oneline" property="custComplainId"/>" class="btn12">Ͷ�߻ط�</a></td> 
	<td><img src="img/button2_l.gif" width="11" height="20"></td>
	</d:displayControl>
	</pri:authorized> 
      </tr>
  </table>
  <%} %>
  
    <%} %>

   <%if("transfer".equals(actiontype)){
     //int autoOrgid=Postern.getAutoNextOrgID("C",null,null,null,null,Postern.getDistrictIDByCustomerID(customerComplainDTO.getCustomerId()+""),0);
   %> 
   <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">   
	 
	<!--  <tr> 
	    <td colspan="4" class="import_tit" align="center"><font size="2">Ͷ���Զ���ת</font></td>
        </tr>  -->  
	 <tr>
	    <td class="list_bg2"><div align="right">��ת����*</td>
	    <td class="list_bg1" colspan="3">     
		 <input type="hidden" name="txtAutoNextOrgID" value="<tbl:writeparam name="txtAutoNextOrgID" />" >
		 <input type="text" name="txtNextOrgName" size="30" maxlength="50" readonly value="<tbl:writeparam name="txtNextOrgName"/>" class="text">
		 <input name="selOrgButton" type="button" class="button" value="�޸�" onClick="javascript:mod_organization()">
		 <input type="hidden" name="selType" value="auto">
	    </td>		
	  </tr> 
	 <tr>		 
      	<td class="list_bg2" width="17%"  align="right">˵����Ϣ</td>
     	<td class="list_bg1" width="83%" colspan="3"  align="left">
     		<textarea name="txtDescriptionTrans" length="5" cols=82 rows=9><tbl:writeSpeCharParam name="txtDescription" /></textarea>
	  	</td>
  	 </tr>
  </table>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr>
    	<td><img src="img/mao.gif" width="1" height="1"></td>
  	</tr>
  </table> 
  <br>
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        	<td><img src="img/button2_r.gif" width="22" height="20"></td>    
	   		<td background="img/button_bg.gif"><a href="javascript:history.back()" class="btn12">��    ��</a></td> 
	   		<td><img src="img/button2_l.gif" width="11" height="20"></td> 
           	<%if(txtStatus.equals("D")){ %>
           	<td width="20" ></td>    
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:cust_camplain_transfer()" class="btn12">ȷ    ��</a></td> 
	   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
	   		<%} %>
      </tr>
  </table>
  <%} if("manualend".equals(actiontype)){%>
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td colspan="4" class="import_tit" align="center">�ֹ�����</td>
     </tr>
	 <tr>		 
      	<td class="list_bg2" width="17%"  align="right">˵����Ϣ</td>
     	<td class="list_bg1" width="83%" colspan="3"  align="left">
     		<textarea name="txtDescriptionManual" length="5" cols=82 rows=9 ><tbl:writeSpeCharParam name="txtDescription" /></textarea>
	  	</td>
  	 </tr>
  </table>

  <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr>
    	<td><img src="img/mao.gif" width="1" height="1"></td>
  	</tr>
  </table> 
  <br>

  <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        	<td><img src="img/button2_r.gif" width="22" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:history.back()" class="btn12">��    ��</a></td> 
	   		<td><img src="img/button2_l.gif" width="11" height="20"></td> 
           	<%if(txtStatus.equals("D")){ %>
           	<td width="20" ></td>    
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:cust_camplain_manualEnd()" class="btn12">ȷ    ��</a></td> 
	   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
	   		<%} %>
      </tr>
  </table>
 <%} if("callback".equals(actiontype)){%><input type="hidden" name="func_flag" value="210">
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td colspan="4" class="import_tit" align="center">�ط���Ϣ</td>
     </tr>	
      <tr>         
      <td class="list_bg2" width="17%"  align="right"></td>
      <td class="list_bg1" width="33%"  align="left"></td>
	  <td class="list_bg2" width="17%"  align="right"></td>
      <td class="list_bg1" width="33%"  align="left"></td>
    </tr> 
    <%if(customerComplainDTO.getCallBackFlag()!=null&&"Y".equals(customerComplainDTO.getCallBackFlag())){
    %>
    <tbl:dynamicservey serveyType="C"  serveySubType="C"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" match="<%=tt%>"/>
    <%}else{ %>
      	<tbl:dynamicservey serveyType="C" serveySubType="C"  showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" match="<%=tt%>"  checkScricptName ="check_Bidimconfig()" />
    <%} %>
  	
  </table>
  <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr>
    	<td><img src="img/mao.gif" width="1" height="1"></td>
  	</tr>
  </table> 
  <br>
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        	<td><img src="img/button2_r.gif" width="22" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:history.back()" class="btn12">��    ��</a></td> 
	   		<td><img src="img/button2_l.gif" width="11" height="20"></td> 
           	<%if(!txtStatus.equals("D")){ %>
           	<td width="20" ></td>    
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:cust_camplain_callback('callback')" class="btn12">ȷ    ��</a></td> 
	   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
	   		<td width="20" ></td>    
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:cust_camplain_callback('wait')" class="btn12">��    ��</a></td> 
	   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
	   		<%} %>
      </tr>
  </table>
  <%}if("process".equals(actiontype)){
  %>
   <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">Ͷ�ߴ���</td>
	</tr>
	
      </table> 
        <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>  
           <td  class="list_bg2" align="right" width="17%">������*</td>
           <td  class="list_bg1" align="left" width="33%"><font size="2">
             <input type="radio" name="txtProResult" maxlength="10" value="S" size="25"  checked>����ɹ�  
           </font></td> 
           <td  class="list_bg1" align="left" colspan="2" width="50%"><font size="2">
             <input type="radio" name="txtProResult" maxlength="10" value="F" size="25" >����ʧ��  
           </font></td> 
        </tr>
        <tr>  
           <td  class="list_bg2" align="right" width="17%">������</td>
           <td  class="list_bg1" align="left" colspan="3"><d:selcmn name="txtAction" mapName="SET_C_CUSTCOMPLAINPROCESSACTION" match="txtAction" width="23" /></td>  
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">˵����Ϣ</td>
           <td  class="list_bg1" align="left" colspan="3"><font size="2">
             <textarea name="txtMemo" length="5" cols=82 rows=9 ><tbl:write name="oneline" property="txtMemo" /></textarea>  
           </font></td>  
        </tr>
     </table> 
     <br>
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        	<td><img src="img/button2_r.gif" width="22" height="20"></td>
	   	<td background="img/button_bg.gif"><a href="javascript:history.back()" class="btn12">��    ��</a></td> 
	   	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
           	<td width="20" ></td>    
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   	<td background="img/button_bg.gif"><a href="javascript:cust_camplain_process()" class="btn12">ȷ    ��</a></td> 
	   	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
      </tr>
  </table> 
  <%} %>
</lgc:bloop>
</rs:hasresultset> 
<tbl:generatetoken />
</form> 
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td>
      <iframe name=head width=100% height=640 frameborder=0 scrolling=no src="list_customer_complain_pro.do?txtFrom=1&txtTo=10&txtCustComplainId=<%=str%>" ></iframe>
    </td>
  </tr>
</table>