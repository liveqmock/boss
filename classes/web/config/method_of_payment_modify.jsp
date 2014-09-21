<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.*,
                 java.util.*"%>
 

<SCRIPT language="JavaScript">

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
 
function check_frm ()
{
    if (check_Blank(document.frmPost.name, true, "����"))
	    return false;
    if (check_Blank(document.frmPost.txtPayType, true, "֧������"))
	    return false;
   if (check_Blank(document.frmPost.txtAccountflag, true, "�Ƿ񸶷Ѽƻ�"))
	    return false;
    if (check_Blank(document.frmPost.txtPaymentflag, true, "�Ƿ�֧����ʽ"))
	    return false;	
 	        
  if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
	    return false;
if (check_Blank(document.frmPost.txtCashFlag, true, "�Ƿ�����ֽ��ʻ�"))
	    return false;		    	    
	    
	return true;
}

function method_edit_submit(){
 if (window.confirm('��ȷ��Ҫ�޸ĸø��ѷ�ʽ��?')){
  if (check_frm()){
	    document.frmPost.action="method_of_payment_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
}
}
 function back_submit()
{
	self.location.href="method_of_payment_query.do";
} 
  
 

</SCRIPT>

<form name="frmPost" method="post">
	 
	
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="115" >
	 
	<!-- ���嵱ǰ���� -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">���ѷ�ʽ�޸�</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
<%	 
    MethodOfPaymentDTO dto= (MethodOfPaymentDTO) pageContext.findAttribute("oneline");
    String csiTypeList = dto.getCsiTypeList();
             String totalValue1="";
             System.out.println("the type of the csi is "+ csiTypeList);
                 if(csiTypeList!=null)
                 { 
                     String[] custArray=csiTypeList.split(",");
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueByNameKey("SET_F_METHODOFPAYMENTCSITYPELIST",custArray[j]);
                     if(totalValue1=="")
                      totalValue1=value;
                     else 
                       totalValue1=totalValue1+","+value;
                     }
                     
                 }
                 %>
	<tr>
	          <td  class="list_bg2"><div align="right">��¼ID</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtMopID" size="25"  value="<tbl:write name="oneline" property="mopID" />" class="textgray" readonly >
                  </td>       
		 <td class="list_bg2"><div align="right">����*</div></td>
                 <td class="list_bg1" align="left">
                   <input type="text" name="name" size="25"   maxlength="30" value="<tbl:write name="oneline" property="name" />">
                  </td>       
                  
	</tr> 
	 <tr>
		<td  class="list_bg2"><div align="right">֧������*</div></td>         
                <td class="list_bg1" align="left">
                 <d:selcmn name="txtPayType" mapName="SET_F_MOPPAYTYPE" match="oneline:payType" width="23"/>
                 
                </td> 
                 <td  class="list_bg2"><div align="right">�Ƿ񸶷Ѽƻ�*</div></td>         
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtAccountflag" mapName="SET_G_YESNOFLAG" match="oneline:accountflag" width="23"/>
                 </td>        
       </tr>
       <tr>   
		 <td  class="list_bg2" align="right">ʹ�ó���</td>
	         <td class="list_bg1" colspan="3">
	   	<input name="txtCsiTypeListValue" type="text" class="text" maxlength="200" size="85" value="<%=totalValue1%>">
	   	<input name="txtCsiTypeList" type="hidden" value="<tbl:write name="oneline"  property="csiTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('SET_F_METHODOFPAYMENTCSITYPELIST','txtCsiTypeList',document.frmPost.txtCsiTypeList.value,'','','')"> 
	   </td>	   
          </tr>
	 <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
	 <tr>
		<td  class="list_bg2"><div align="right">�Ƿ�֧����ʽ*</div></td>         
                <td class="list_bg1" align="left">
                 <d:selcmn name="txtPaymentflag" mapName="SET_G_YESNOFLAG" match="oneline:paymentflag" width="23"/>
                 
                </td> 
                 <td  class="list_bg2"><div align="right">״̬*</div></td>         
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23"/>
                 </td>        
       </tr>
         <% 
          Map orgName = new HashMap();
          orgName = Postern.getPaymentProxyOrg();
          pageContext.setAttribute("SubOrgName",orgName);
       %>
	<tr>
	  <td class="list_bg2"><div align="right">�������</div></td>
          <td class="list_bg1" align="left">
          <tbl:select name="txtPartID" set="SubOrgName" match="oneline:partnerID" width="23"/>
          <td class="list_bg2"><div align="right">�Ƿ�����ֽ��ʻ�*</div></td>
          <td class="list_bg1" align="left">
          <d:selcmn name="txtCashFlag" mapName="SET_G_YESNOFLAG" match="oneline:CashFlag" width="23" />
	</tr>
	  <tr>
		<td class="list_bg2" align="right">����</td>
		<td class="list_bg1" colspan="3"><font size="2">
		<input name="txtDescription" type="text" class="text" maxlength="100" size="85"
			value="<tbl:write name="oneline" property="Description" />" ></font></td>
			   
	</tr>
	 
	  </lgc:bloop>  
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp; ��" onclick="javascript:method_edit_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
 </table>   
 
	 

</form>
