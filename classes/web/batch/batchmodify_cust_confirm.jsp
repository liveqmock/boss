<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<script language=javascript>
   function prepare_before_submit(){
    	var l=document.getElementsByName("strCustId") ;
    	document.frmPost.strCustIds.value=l[0].value;
    	for(i=1;i<l.length;i++){
    		document.frmPost.strCustIds.value+=(";"+l[i].value);
    	}
   }
   
   function back_submit(){
    	 var strCust=document.getElementsByName("strCustId") ;
    	 if (strCust[0] !=null){
    	    prepare_before_submit();
    	 }
    	 document.frmPost.action = "batchmodify_cust_query.do?txtFrom=1&txtTo=200";
       document.frmPost.submit();
   }  
   
   function check_form(){
   	  //Ŀ����������
	    if (document.frmPost.txtObject_DistrictID.value ==''){
	       alert("Ŀ����������ѡ����Ϊ��!");
	       return false;
	    }
	    
	    if (document.frmPost.txtaddrOption.value !=''){
	    	 if (document.frmPost.txtaddrOption.value =='A'){
	    	 	  if (document.frmPost.txtObject_DetailAddress.value ==''){
	    	 	  	 alert("Ŀ�갲װ��ַ�޸����ݲ���Ϊ��!");
	    	 	  	 return false;
	    	 	  }
	    	 }
	    	 if (document.frmPost.txtaddrOption.value =='D'){
	    	 	  if (document.frmPost.txtSource_DetailAddress.value ==''){
   	    	 	   alert("ԭ��װ��ַ���ݲ���Ϊ��!");
	             return false;
	          }
	    	 }
	    	 if (document.frmPost.txtaddrOption.value =='R'){
	    	 	  if (document.frmPost.txtSource_DetailAddress.value ==''){
	    	 	  	 alert("ԭ��װ��ַ���ݲ���Ϊ��!");
	    	 	  	 return false;
	    	 	  }
	    	 	  if (document.frmPost.txtObject_DetailAddress.value ==''){
	    	 	  	 alert("Ŀ�갲װ��ַ�޸����ݲ���Ϊ��!");
	    	 	  	 return false;
	    	 	  }
	    	 }
	    }else{
	    	 if (document.frmPost.txtSource_DetailAddress.value !='' || document.frmPost.txtObject_DetailAddress.value !=''){
	    	 	   alert("��ַ�޸Ĳ�������Ϊ�գ�");
	    	 	   return false;
	    	 }
	    }
      return true;
   }
   function confirm_submit(){
   	  if (check_form()){
   		   prepare_before_submit();
   		   document.frmPost.action ="batchmodify_cust_op.do";
   		   document.frmPost.submit();
   	  }
   }

</script>
<%
  Map addrOption =new HashMap();
  addrOption.put("A","���");
  addrOption.put("D","ɾ��");
  addrOption.put("R","�滻");
  pageContext.setAttribute("addrOption",addrOption);
%>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�����޸Ŀͻ�ȷ��</td>
  </tr>
</table> 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>               
<form name="frmPost" method="post" action="batchmodify_cust_op.do">
	<input type="hidden" name="strCustIds" value="">
	<input type="hidden" name="confirm_post"  value="true" >
  <input type="hidden" name="func_flag" value=""> 
  <tbl:hiddenparameters pass="txtDistrictID/txtCustomerType/txtDetailAddress/txtComments" />
	<table  width="98%" border="0" cellpadding="6" cellspacing="1" class="list_bg" >
   <tr> 
   	 <td class="list_head" width="40" nowrap>���</td>
   	 <td class="list_head" width="80" nowrap>�ͻ�֤��</td>
     <td class="list_head" width="80" nowrap>����</td>
     <td class="list_head" width="80" nowrap>����</td>
     <td class="list_head" width="80" nowrap>֤����</td>
     <td class="list_head" width="120" nowrap>��������</td>
     <td class="list_head" width="170" nowrap>��ַ</td>
     <td class="list_head" width="80" nowrap>��������</td>
     <td class="list_head" width="120" nowrap>��ע</td>
   </tr>
   <%
      int no =0;
   %>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   <%
     com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
     pageContext.setAttribute("oneline", wrap.getCustDto());
     pageContext.setAttribute("custaddr",  wrap.getAddrDto());
     no =no+1;
   %>
   <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
   	 <td align="center"><%=no%><input type="hidden" name="strCustId" value="<%=wrap.getCustDto().getCustomerID()%>">    </td>
   	 <td align="center"><tbl:write name="oneline" property="customerID" /></td>
     <td align="center"><tbl:write name="oneline" property="name" /></td>
     <td align="center"><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="oneline:customerType" /></td>
     <td align="center"><tbl:write name="oneline" property="CardID" /></td>
     <td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
     <td align="center"><tbl:write name="custaddr" property="detailAddress" /></td>
     <td align="center"><tbl:writedate name="oneline" property="CreateTime" /></td>
     <td align="center"><tbl:write name="oneline" property="comments" /></td>
   </tbl:printcsstr>
   </lgc:bloop>
 	<tr>
	  <td colspan="9" class="list_foot"></td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="98%">
<tr>
    <td class="list_bg2" align="right" width="17%">Ŀ����������</td>
    <td class="list_bg1" align="left"  width="33%">
    	<input type="hidden" name="txtObject_DistrictID" value="<tbl:writeparam name="txtObject_DistrictID" />">
	    <input type="text" name="txtObject_CountyDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtObject_DistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtObject_DistrictID','txtObject_CountyDesc')">
    </td>
    <td class="list_bg2" width=17% align="right">Ŀ��ͻ�����</td>
    <td class="list_bg1" width=33% align="left">
    	<d:selcmn name="txtObject_CustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtObject_CustomerType" width="20" />
    </td>
 </tr>
 <tr>
    <td class="list_bg2" align="right" width="17%">��ַ�޸Ĳ���</td>
    <td class="list_bg1" align="left"  width="83%" colspan="3">
    	<tbl:select name="txtaddrOption" set="addrOption" match="txtaddrOption" width="20" />
    </td> 
 </tr>
 <tr>
 	  <td class="list_bg2" align="right" width="17%">ԭ��װ��ַ����</td>
    <td class="list_bg1" align="left"  width="33%">
    	<input name="txtSource_DetailAddress" type="text" class="text" maxlength="100" size="22"  value="<tbl:writeparam name="txtSource_DetailAddress" />">
    </td>
    <td class="list_bg2" align="right" width="17%">Ŀ�갲װ��ַ�޸�����</td>
    <td class="list_bg1" align="left"  width="33%">
    	<input name="txtObject_DetailAddress" type="text" class="text" maxlength="100" size="22"  value="<tbl:writeparam name="txtObject_DetailAddress" />">
    </td>
 </tr>
</table>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		 <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="��һ��"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td> 
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:confirm_submit()" value="ȷ ��"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>   
<tbl:generatetoken />              
</form> 