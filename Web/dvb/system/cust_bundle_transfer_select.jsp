<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<Script language=JavaScript>
<!--

function setValue(vNewCampaignName){
	document.frmPost.txtCampaignDateTo.value=document.frmPost.txtDateTo.value;
	document.frmPost.txtNewCampaignName.value=vNewCampaignName;
}
function check_form(){
	if (!checkSelected()){
	 	return false;  
	}
	if(!check_csiReason()){
		return false;
	}
	if (check_Blank(document.frmPost.txtCampaignDateTo, true, "���ײ���Ч��(����)"))
		return false;
	if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtCampaignDateTo,"���ײͽ���������Ч�ڱ����ڽ����Ժ�"))
	   return false;	
	return true;
}
function checkSelected(){
	var m=0;
	if (document.frmPost.txtCampaignID !=null){
		if(document.frmPost.txtCampaignID.type=='radio'&&document.frmPost.txtCampaignID.checked){
			return true;
		}
		for( i=0; i<document.frmPost.txtCampaignID.length; i++){
			if (document.frmPost.txtCampaignID[i].checked){
				m=m+1;	
			}
		}
	}
	if (m==0){
		alert("��ѡ����Ч��Ŀ���ײ�");
		return false;
	}
	return true;
}
function button_next(){
	if(check_form()){
		document.frmPost.action="cust_bundle_transfer_product.do";
		document.frmPost.submit();	
	}
}
function view_campaign_detail(strId){
	window.open('list_campaign_detail.do?txtCampaignID='+strId,'','toolbar=no,location=no,status=no,menubar=no,scrollbar=no,width=380,height=300');
}
//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ��ײ�ת��-���ײ�ѡ��</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">�ϵĿͻ��ײ���Ϣ</font></td>
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
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:writeparam name="txtServiceAccoutID"/>" class="textgray" readonly >
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
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">�µ��ײ���Ϣ</font></td>
     </tr>
  </table>	
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">���ײ�ת������</td>
          <td class="list_bg1"  align="left" width="33%">
          		<input type="text" name="txtTransferDate" size="25"  value="" class="textgray" readonly >      
          </td>
          <td class="list_bg2" align="right" width="17%">���ײ���Ч��(����)</td>
          <td class="list_bg1"  align="left" width="33%">
		      	  <input type="text" name="txtCampaignDateTo" value="<tbl:writeparam name="txtCampaignDateTo"/>" class="textgray" readonly >      
          </td> 
        </tr>
        <tr>
           <tbl:csiReason csiType="BDS" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" controlSize="25" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdControlColspan="3" forceDisplayTD="true"/>
        </tr>
      </table>
<Script language=JavaScript>
	document.frmPost.txtTransferDate.value=getCurrentDate();
</Script>	      
<%
int ccid=Integer.parseInt(request.getParameter("txtCcID"));
CustomerCampaignDTO ccDto=Postern.getCustomerCampaignByccID(ccid);
Map targetCampaignMap=Postern.getCustomerBundleTransferTarget(ccDto.getCampaignID());
System.out.println("targetCampaignMap"+targetCampaignMap);
if(targetCampaignMap!=null&&!targetCampaignMap.isEmpty()){
%>      
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">ѡ���µ��ײ���Ϣ</font></td>
     </tr>
  </table>
  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
       <tr class="list_head">
          <td width="10%" class="list_head">ѡ��</td>
          <td width="20%" class="list_head" align="center">��ϵ</td>
          <td width="70%" class="list_head" align="center">�ײ�����</td>
        </tr>
        <%
        String strCamID=request.getParameter("txtCampaignID");
        for(Iterator it=targetCampaignMap.keySet().iterator();it.hasNext();){
        CampaignDTO cDto=(CampaignDTO)it.next();
        String relationType=(String)targetCampaignMap.get(cDto);
        pageContext.setAttribute("oneline", cDto);
        pageContext.setAttribute("relationType", relationType);
        boolean isChecked=strCamID!=null&&(cDto.getCampaignID()==Integer.parseInt(strCamID));
        %>
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
           <td align="center" ><input type="radio" name="txtCampaignID" value="<tbl:write name="oneline" property="campaignID" />" onclick="JavaScript:setValue('<tbl:write name="oneline" property="campaignName" />')" <%=isChecked?"checked=true":""%>>
           </td>
           <td align="center" ><d:getcmnname typeName="SET_P_PACKAGERELATIONTYPE" match="relationType" />
           </td>
           <td align="center" ><a href="JavaScript:view_campaign_detail('<tbl:write name="oneline" property="campaignID" />')"><tbl:write name="oneline" property="campaignName" /></a></td>
        </tbl:printcsstr>
        <%}%>
  <tr>
    <td colspan="3" class="list_foot"></td>
  </tr>       
  </table>
<%}else{%>
			<table width="100%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td >
					<font color="red">û�п�ת����Ŀ���ײ�</font>
					</td>
				</tr>
			</table>
<%}%>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	  <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_bundle_detail.do" />" class="btn12">��&nbsp;&nbsp;��</a></td> 
	  <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
	  <td width="20" ></td>
	  <lgc:hasnoterror>
		<%if(targetCampaignMap!=null&&!targetCampaignMap.isEmpty()){%>      
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:button_next()" class="btn12">��һ��</a></td> 
	  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
		<%}%>
	  </lgc:hasnoterror>
	</tr>
</table>
<input type="hidden" name="txtNewCampaignName"  value="" >
<input type="hidden" name="txtActionType"  value="bundleTransfer" >
<input type="hidden" name="txtDoPost"  value="false" >
<input type="hidden" name="txtCustomerID"  value="<tbl:writeparam name="txtCustomerID"/>" >
          
</form>