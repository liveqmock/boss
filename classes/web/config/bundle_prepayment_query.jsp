<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*" %>

<Script language=javascript>
<!--
  
  
 

 
function create_submit(){
	document.frmPost.action="add_bundle_payment.do";
	document.frmPost.txtActionType.value="create_bundle_prepayment";
	document.frmPost.submit();
}
function modify_submit(){
	document.frmPost.action="modify_bundle_prepayment.do";
	document.frmPost.txtActionType.value="modify_bundle_prepayment";
	document.frmPost.submit();
}
 

 
function back(){
 
    var campaignID=document.frmPost.txtCampaignID.value;
    
   
     document.location.href= "campaign_detail.do?txtCampaignID="+campaignID;
     
} 
function delete_object(strFlag){
    if( confirm("ȷ��Ҫɾ���ü�¼��?") ){
        document.frmOther.action="delete_bundle_payment.do?txtRfBillingCycleFlag="+strFlag;
        document.frmOther.txtActionType.value="deletepaymentmethod";
         
        document.frmOther.submit();
    }
	
} 
function ChangeAcctItem()
{
    
    document.FrameUS.submit_update_select('feetype', document.frmPost.txtFeeType.value, 'txtAcctItemTypeId', '');
}
 
//-->
</SCRIPT>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr> 
          <td width="100%"><div align="center">     
          <font size="2">
          ���ڻ�ȡ���ݡ�����
          </font>
          </td>
        </tr>
    </table>  
</div>

 
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<form name="frmPost" method="post">
    
	   
	 <tbl:hiddenparameters pass="txtCampaignID" />
	  
	 <input type="hidden" name="txtActionType" value="">
 
<% 
   
   int campaignID=0;
   if(!(request.getParameter("txtCampaignID")==null || "".equals(request.getParameter("txtCampaignID"))))
   	campaignID=Integer.parseInt(request.getParameter("txtCampaignID"));
   	
   CampaignDTO cDTO=Postern.getCampaignDTOByCampaignID(campaignID);
   Map planMap = Postern.getPlanMap();
    pageContext.setAttribute("PLANMAP",planMap);
    Map acctItmeMap = Postern.getAllFeeType();
    pageContext.setAttribute("ITEMMAP",acctItmeMap);
   
   pageContext.setAttribute("camDTO",cDTO);
    String title="";
    BundlePrepaymentDTO  bundlePreDto  =   Postern.getBundlePrepaymentDTO(campaignID);
    if(bundlePreDto!=null){
     pageContext.setAttribute("oneline",bundlePreDto);
      title="�ײ�Ԥ�������޸�";
      } else{
         pageContext.setAttribute("oneline",new BundlePrepaymentDTO());
      title="�ײ�Ԥ�����ô���";
      }
    
    
    
    
     
 %>
<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><%=title%></td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>



<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	<tr>
	    <td class="import_tit" align="center" colspan="4"><font size="2">�ײͻ��Ϣ</font></td>
	</tr>
	
        <tr>
            <td class="list_bg2" width="25%"><div align="right">�ײͻID</div></td>
            <td class="list_bg1" width="25%"><tbl:write name="camDTO" property="campaignID" /></td>
            <td class="list_bg2" width="25%"><div align="right">����</div></td>
            <td class="list_bg1" width="25%"><tbl:write name="camDTO" property="campaignName" /></td>
        </tr>
       
        <tr>
            <td class="list_bg2"><div align="right">��Ч��ʼʱ��</div></td>
            <td class="list_bg1"><tbl:writedate name="camDTO" property="dateFrom" /></td>
            <td class="list_bg2"><div align="right">��Ч����ʱ��</div></td>
            <td class="list_bg1"><tbl:writedate name="camDTO" property="dateTo" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">״̬</div></td>
            <td class="list_bg1" colspan="3"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="camDTO:status"/></td>
            
        </tr> 
        <tr>
	    <td class="import_tit" align="center" colspan="4"><font size="2">�ײ�Ԥ����Ϣ</font></td>
	</tr>
        <%
         if(bundlePreDto!=null){
          
        %>
         <tr>
            <td class="list_bg2" width="25%"><div align="right">����̯���ƻ�����</div></td>
            <td class="list_bg1" width="25%">
              <tbl:select name="txtBundlePrepaymentPlanId" set="PLANMAP" match="oneline:bundlePrepaymentPlanId" width="23" />
           </td>
            <td class="list_bg2" width="25%"><div align="right">�ײ�Ԥ��ʱ��</div></td>
            <td class="list_bg1" width="25%">
             <input type="text" name="txtTimeUnitLengthNumber"  maxlength="10" size="25" value="<tbl:write name="oneline" property="timeUnitLengthNumber"/>">
             </td>
           
           </td>
            
            
        </tr>
          <tr>
           <td class="list_bg2" width="25%"><div align="right">Ԥ��ʱ����λ</div></td>
            <td class="list_bg1" width="25%">
             <d:selcmn name="txtTimeUnitLengthType" mapName="SET_M_CAMPAIGNTIMELENGTHUNITTYPE" match="oneline:timeUnitLengthType" width="25" /></td>
           
             
            <td class="list_bg2" width="25%"><div align="right">��������</div></td>
            <td class="list_bg1" width="25%">
             <d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE" match="oneline:feeType" width="25"  onchange="ChangeAcctItem()"/></td>
            </td>
           
           
        </tr>
        <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />">
        <tr>
            <td class="list_bg2" width="25%"><div align="right">��Ŀ����</div></td>
            <td class="list_bg1" width="25%">
             <tbl:select name="txtAcctItemTypeId" set="ITEMMAP" match="oneline:acctItemTypeId" width="23" />
            </TD>
             <td class="list_bg2" width="25%"><div align="right">���</div></td>
            <td class="list_bg1" width="25%">
             <input type="text" name="txtAmount"  maxlength="10" size="25" value="<tbl:write name="oneline" property="amount"/>">
            </TD>
        </tr>
        
        <%}
          else {%>
          
           <tr>
            <td class="list_bg2" width="25%"><div align="right">����̯���ƻ�����</div></td>
            <td class="list_bg1" width="25%">
              <tbl:select name="txtBundlePrepaymentPlanId" set="PLANMAP" match="txtBundlePrepaymentPlanId" width="23" />
           </td>
            <td class="list_bg2" width="25%"><div align="right">�ײ�Ԥ��ʱ��</div></td>
            <td class="list_bg1" width="25%">
             <input type="text" name="txtTimeUnitLengthNumber"  maxlength="10" size="25" value="<tbl:writeparam name="txtTimeUnitLengthNumber" />" >
           </td>
        </tr>
         <tr>
             <td class="list_bg2" width="25%"><div align="right">Ԥ��ʱ����λ</div></td>
            <td class="list_bg1" width="25%">
             <d:selcmn name="txtTimeUnitLengthType" mapName="SET_M_CAMPAIGNTIMELENGTHUNITTYPE" match="txtTimeUnitLengthType" width="25" /></td>
            
            <td class="list_bg2" width="25%"><div align="right">��������</div></td>
            <td class="list_bg1" width="25%">
             <d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE" match="txtFeeType" width="25"  onchange="ChangeAcctItem()"/></td>
            </td>
           
           
        </tr>
        <tr>
           
             
            <td class="list_bg2" width="25%"><div align="right">��Ŀ����</div></td>
            <td class="list_bg1" width="25%">
             <tbl:select name="txtAcctItemTypeId" set="ITEMMAP" match="txtAcctItemTypeId" width="23" />
            </TD>
            <td class="list_bg2" width="25%"><div align="right">���</div></td>
            <td class="list_bg1" width="25%">
             <input type="text" name="txtAmount"  maxlength="10" size="25" value="<tbl:writeparam name="txtAmount" />" >
            </TD>
        </tr>
      
          <%}%>
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
<input type="hidden" name="func_flag" size="20" value="1021">
    
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		     
		         <td><img src="img/button2_r.gif" width="20" height="20"></td>
	                 <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back()"></td>
                         <td><img src="img/button2_l.gif" width="11" height="20"></td>
		        
		  	 <%
         if(bundlePreDto!=null){
         
        %>
			<td width="20" ></td>	
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ��" onclick="javascript:modify_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<%} else {%>
			<td width="20" ></td>	
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ��" onclick="javascript:create_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<%}%>
			 
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
    
 
   
                  
<BR>
  
</form>
