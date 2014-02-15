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
  
  
function check_form(){	
	return true;
}

function query_submit(){
        if (check_form()) document.frmPost.submit();
}
function add_submit(){
	document.frmOther.action="campaign_payment_award_add.screen";
	document.frmOther.submit();
}

function view_submit(seqNo){
	document.frmOther.action="agmt_campaign_view.screen?txtbundleID="+seqNo;
	 
	document.frmOther.submit();
}

 
function back(){
    var campaignID=document.frmOther.txtCampaignID.value;
    
    
     document.location.href= "campaign_detail.do?txtCampaignID="+campaignID;
  }   
function delete_object(strID){
    if( confirm("ȷ��Ҫɾ���ü�¼��?") ){
        document.frmOther.action="delete_payment_award.do?seqNo="+strID;
        document.frmOther.txtActionType.value="deletePaymentAward";
         
        document.frmOther.submit();
    }
	 
}  
 
//-->
</SCRIPT>

<form name="frmOther" method="post">
	<input type="hidden" name="txtCampaignID" size="20" value="<tbl:writeparam name="txtCampaignID"/>">
	 <input type="hidden" name="txtActionType" size="20" value="">
	 
	<input type="hidden" name="txtSeqNo" size="20" value="">
</form>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�������-��ѯ</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<% 
   
   int campaignID=0;
   if(!(request.getParameter("txtCampaignID")==null || "".equals(request.getParameter("txtCampaignID"))))
   	campaignID=Integer.parseInt(request.getParameter("txtCampaignID"));
   	
   CampaignDTO cDTO=Postern.getCampaignDTOByCampaignID(campaignID);
 String status=cDTO.getStatus();
   
   pageContext.setAttribute("camDTO",cDTO);
 %>

<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	<tr>
	    <td class="import_tit" align="center" colspan="4"><font size="2">���Ϣ</font></td>
	</tr>
	
        <tr>
            <td class="list_bg2" width="25%"><div align="right">�ID</div></td>
            <td class="list_bg1" width="25%"><tbl:write name="camDTO" property="campaignID" /></td>
            <td class="list_bg2" width="25%"><div align="right">����</div></td>
            <td class="list_bg1" width="25%"><tbl:write name="camDTO" property="campaignName" /></td>
        </tr>
       
        <tr>
            <td class="list_bg2"><div align="right">��Ч�ڿ�ʼʱ��</div></td>
            <td class="list_bg1"><tbl:writedate name="camDTO" property="dateFrom" /></td>
            <td class="list_bg2"><div align="right">��Ч�ڽ���ʱ��</div></td>
            <td class="list_bg1"><tbl:writedate name="camDTO" property="dateTo" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">״̬</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="camDTO:status"/></td>
            <td class="list_bg2"></td>
            <td class="list_bg1"></td>
        </tr> 
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

<form name="frmPost" method="post">
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		         <td><img src="img/button2_r.gif" width="20" height="20"></td>
	                 <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back()"></td>
                         <td><img src="img/button2_l.gif" width="11" height="20"></td>
		        
		  	  <% if (!"I".equals(status)){ %>
			<td width="20" ></td>	
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ��" onclick="javascript:add_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<%}%>
			 
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>
    <br>

 

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">��¼ID</td>
    <td class="list_head">֧����ʽ</td>
    <td class="list_head">�������</td>
    <td class="list_head">����ʱ��</td>
    <td class="list_head">����</td>
  </tr>
   <%
         
        List  dtoList =   Postern.getCampaignPaymentAwardDTOList(campaignID);
        
        if (dtoList!=null){
          Iterator ite = dtoList.iterator();
          while (ite.hasNext()){ 
          CampaignPaymentAwardDTO campaignPaymentAwardDTO= (CampaignPaymentAwardDTO) ite.next();
          System.out.println(campaignPaymentAwardDTO);
          String mopName = Postern.getNameByMopIDAndPaymentFlag(campaignPaymentAwardDTO.getMopID());
          pageContext.setAttribute("oneline",campaignPaymentAwardDTO);
          
    %>
 
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
  	<td align="center"><tbl:write name="oneline" property="seqNo"/></td>
    <td align="center"><%=mopName%></td>
    <td align="right"><tbl:write name="oneline" property="value"/></td>
    <td align="center"><tbl:writedate name="oneline" property="dtCreate"/></td>
	<td align="center" ><a href="javascript:delete_object('<tbl:write name="oneline" property="seqNo"/>')"  class="link12">ɾ��</a></td>
</tbl:printcsstr>
 <%
         }
     }
     %>
</table>
 
   
                  
<BR>
  
</form>
