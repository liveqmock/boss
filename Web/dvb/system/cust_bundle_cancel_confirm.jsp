<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>

<%@ page import="com.dtv.oss.dto.ServiceAccountDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,com.dtv.oss.web.util.CommonKeys" %>
<Script language=JavaScript>
<!--
function bundle_cancel(){
	document.frmPost.action="cust_bundle_cancel_op.do";
	document.frmPost.submit();	
}
	function back_submit(){
		if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
			document.frmPost.action="cust_bundle_cancel_fee.do";
		}else{
			document.frmPost.action="cust_bundle_cancel_pay.screen";
		}		
//		document.frmPost.action='cust_bundle_cancel_fee.do';
		document.frmPost.txtDoPost.value="false";
		document.frmPost.submit();
	}
//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ��ײ�ȡ��-ȷ����Ϣ</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<%
int ccid=Integer.parseInt(request.getParameter("txtCcID"));
ArrayList cpInfoList=Postern.getAllCustomerProductInfoWithCustomerCampaign(ccid);
HashMap saMap=new HashMap();
HashMap terMap=new HashMap();
String saids="";

for(int i=0;i<cpInfoList.size();i++){
	Object[] arrInfo=(Object[])cpInfoList.get(i);
	CustomerProductDTO cpDto=(CustomerProductDTO)arrInfo[0];
	ProductDTO pDto=(ProductDTO)arrInfo[1];
	ServiceAccountDTO saDto=(ServiceAccountDTO)arrInfo[2];
	Integer cpmid=(Integer)arrInfo[3];
	if(!saMap.containsKey(saDto)){
		Object[] arrSaInfo=new Object[3];
		arrSaInfo[0]=new Integer(1);//ҵ���ʻ��²�Ʒ�����ۼ�,
		if(cpmid!=null&&cpmid.intValue()!=0){
			arrSaInfo[1]=new Integer(1);//ҵ���ʻ����ײͲ�Ʒ�ۼ�
		}else{
			arrSaInfo[1]=new Integer(0);
		}
		saMap.put(saDto,arrSaInfo);
		saids=saids+saDto.getServiceAccountID()+",";
	}else{
		Object[] arrSaInfo=(Object[])saMap.get(saDto);
		Integer allSum=(Integer)arrSaInfo[0];
		arrSaInfo[0]=new Integer(allSum.intValue()+1);
			Integer ccSum=(Integer)arrSaInfo[1];
		if(cpmid!=null&&cpmid.intValue()!=0){
			arrSaInfo[1]=new Integer(ccSum.intValue()+1);
		}
	}
	if(arrInfo[4]!=null&&cpmid!=null&&cpmid.intValue()!=0){
		terMap.put(cpDto,arrInfo[4]);
	}
}
%>
<form name="frmPost" method="post" action="" >
	  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">�ͻ��ײ���Ϣ</font></td>
     </tr>
  </table>	
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">�ͻ��ײ�ID</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25"  value="<tbl:writeparam name="txtCcID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">�ײ�����</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtCampaignName" size="25"  value="<tbl:writeparam name="txtCampaignName"/>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right" width="17%">����ʱ��</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtDtCreate" size="25"  value="<tbl:writeparam name="txtDtCreate"/>" class="textgray" readonly >
          </td> 
          <td class="list_bg2" align="right">״̬</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtStatus" size="25" value="<tbl:writeparam name="txtStatus"/>" class="textgray" readonly >    
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">�˻�ID</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutID" size="25"  value="<tbl:writeparam name="txtAccoutID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">ҵ���˻�ID</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<%=saids%>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">Э���ڣ���ʼ��</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">Э���ڣ�������</td>
          <td class="list_bg1"  align="left">
            <input type="text" name="txtDateTo" size="25"  value="<tbl:writeparam name="txtDateTo"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">�ײ͸��ѷ�ʽ</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtPaymentType" size="25"  value="<tbl:writeparam name="txtPaymentType"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">NID</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtNbrDate" size="25"  value="<tbl:writeparam name="txtNbrDate"/>" class="textgray" readonly >      
          </td>
        </tr>                
      </table>   
<%if(saMap!=null&&!saMap.isEmpty()){%>
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">�漰��ҵ���ʻ�</font></td>
     </tr>
  </table>	
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >     
	<tr class="list_head"> 
		<td width="10%" class="list_head"><div align="center">ҵ���ʻ�ID</div></td>
		<td width="25%" class="list_head"><div align="center">����</div></td>                    
		<td width="8%" class="list_head"><div align="center">״̬</div></td> 
		<td width="18%" class="list_head"><div align="center">�����ͻ���Ʒ����</div></td>         
		<td width="18%" class="list_head"><div align="center">��ȡ���Ĳ�Ʒ��</div></td>
		<td class="list_head"><div align="center">��ҵ���ʻ��Ĳ���</div></td>
	</tr> 
<%
Iterator saIt=saMap.keySet().iterator();
while (saIt.hasNext()){
	ServiceAccountDTO saDto=(ServiceAccountDTO)saIt.next();
	Object[]arrSaInfo=(Object[])saMap.get(saDto);
	Integer allSum=(Integer)arrSaInfo[0];
	Integer ccSum=(Integer)arrSaInfo[1];
	String isClose=allSum.intValue()==ccSum.intValue()?"ȡ��":"����";
	pageContext.setAttribute("oneline",saDto);
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	<td align="center" ><tbl:write name="oneline" property="serviceAccountID"/></td>
	<td align="center" ><tbl:write name="oneline" property="ServiceAccountName"/></td>
	<td align="center" ><d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="oneline:status" /></td>
	<td align="center" ><%=allSum%></td>
	<td align="center" ><%=ccSum%></td>
	<td align="center" ><%=isClose%></td>
</tbl:printcsstr>
<%}%>
  <tr>
    <td colspan="6" class="list_foot"></td>
  </tr>
</table>
<%}%>
      
<%if(cpInfoList!=null&&!cpInfoList.isEmpty()){%>
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">�漰�Ŀͻ���Ʒ</font></td>
     </tr>
  </table>	
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >     
	<tr class="list_head"> 
		<td width="10%" class="list_head"><div align="center">PSID</div></td>
		<td width="10%" class="list_head"><div align="center">ҵ���ʻ�ID</div></td>                    
		<td width="30%" class="list_head"><div align="center">��Ʒ����</div></td> 
		<td width="10%" class="list_head"><div align="center">�����ʻ�</div></td>         
		<td width="10%" class="list_head"><div align="center">״̬</div></td>
		<td class="list_head"><div align="center">�Կͻ���Ʒ�Ĳ���</div></td>
	</tr> 
<%
for(int i=0;i<cpInfoList.size();i++){
	Object[] arrInfo=(Object[])cpInfoList.get(i);
	CustomerProductDTO cpDto=(CustomerProductDTO)arrInfo[0];
	ProductDTO pDto=(ProductDTO)arrInfo[1];
	Integer cpmid=(Integer)arrInfo[3];
	String isClose=cpmid.intValue()!=0?"ȡ��":"����";
	pageContext.setAttribute("cpDto",cpDto);
	pageContext.setAttribute("pDto",pDto);
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	<td align="center" ><tbl:write name="cpDto" property="psID"/></td>
	<td align="center" ><tbl:write name="cpDto" property="serviceAccountID"/></td>
	<td align="center" ><tbl:write name="pDto" property="productName"/></td>
	<td align="center" ><tbl:write name="cpDto" property="accountID"/></td>
	<td align="center" ><d:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS" match="cpDto:status" /></td>
	<td align="center" ><%=isClose%></td>
</tbl:printcsstr>
<%}%>
  <tr>
    <td colspan="6" class="list_foot"></td>
  </tr>
</table>
<%}%>

<%if(terMap!=null&&!terMap.isEmpty()){%>
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">�漰��Ӳ���豸</font></td>
     </tr>
  </table>	
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >     
	<tr class="list_head"> 
		<td width="8%" class="list_head"><div align="center">�豸ID</div></td>
		<td width="8%" class="list_head"><div align="center">PSID</div></td>
		<td width="15%" class="list_head"><div align="center">�豸����</div></td>                    
		<td width="20%" class="list_head"><div align="center">�ͺ�</div></td> 
		<td width="15%" class="list_head"><div align="center">���к�</div></td>         
		<td width="6%" class="list_head"><div align="center">״̬</div></td>
		<td class="list_head"><div align="center">MAC��ַ</div></td>
		<td class="list_head"><div align="center">�ڲ�MAC��ַ</div></td>
	</tr> 
<%
Iterator terIt=terMap.keySet().iterator();
while (terIt.hasNext()){
	CustomerProductDTO cpDto=(CustomerProductDTO)terIt.next();
	TerminalDeviceDTO terDto=(TerminalDeviceDTO)terMap.get(cpDto);
	pageContext.setAttribute("cpDto",cpDto);
	pageContext.setAttribute("terDto",terDto);
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	<td align="center" ><tbl:write name="terDto" property="deviceID"/></td>
	<td align="center" ><tbl:write name="cpDto" property="psID"/></td>
	<td align="center" ><tbl:write name="terDto" property="deviceClass"/></td>
	<td align="center" ><tbl:write name="terDto" property="deviceModel"/></td>
	<td align="center" ><tbl:write name="terDto" property="serialNo"/></td>
	<td align="center" ><d:getcmnname typeName="SET_D_DEVICESTATUS" match="terDto:status" /></td>
	<td align="center" ><tbl:write name="terDto" property="mACAddress"/></td>
	<td align="center" ><tbl:write name="terDto" property="interMACAddress"/></td>
</tbl:printcsstr>
<%}%>
  <tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>
<%}%>    
                                                                                                                      
 <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDC%>" acctshowFlag ="true" confirmFlag="true" />		 
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">��һ��</a></td> 
	  <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
	  <td width="20" ></td>
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:bundle_cancel()" class="btn12">ȷ&nbsp;&nbsp;��</a></td> 
	  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	</tr>
</table>
<input type="hidden" name="txtActionType"  value="bundleCancel" >
<input type="hidden" name="txtDoPost"  value="true" >
<tbl:hiddenparameters pass="txtCustomerID/txtcsiPayOption/txtCsiCreateReason/txtIsReturn/txtDeviceFee"/>          
<tbl:generatetoken />	          
</form>

