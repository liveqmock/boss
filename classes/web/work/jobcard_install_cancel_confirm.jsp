<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%> 
<%
String confirmMessage="��ȷ��Ҫ�˷���";
%>
<script language=javascript>

function frm_submit(){
	/*if(!check_csiReason())
		return;*/
 	document.frmPost.submit();
}
 
</script>

 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">��װ����ȡ��ȷ��</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
<form name="frmPost" method="post" action="cancel_booking_install_op.do" > 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        	<td class="list_bg2" width="17%"><div align="right">�������</div></td>
        	<td class="list_bg1" width="33%">
			<tbl:writeparam name="txtJobCardID" />
		</td>
		<td class="list_bg2" width="17%"><div align="right">�������</div></td>
        	<td class="list_bg1" width="33%">
			<tbl:writeparam name="txtReferSheetID" />
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">����ʱ��</div></td>
		<td class="list_bg1"  > 
			<tbl:writeparam name="txtActionTime"/>
		</td>
		 
			<tbl:csiReason csiType="BK" name="txtWorkResultReason" action="C" showType="label"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtWorkResultReason"  controlSize="25" tdControlColspan="1" />
		
	</tr>
        <tr>
		<td class="list_bg2" ><div align="right">������Ϣ</div></td>
		<td class="list_bg1" colspan="3"> 
			<tbl:writeparam name="txtMemo" />
		</td>
	</tr>
  <%
	CustServiceInteractionDTO csiDTO=Postern.getCsiDTOByCSIID(WebUtil.StringToInt(request.getParameter("txtReferSheetID")));
	String showGroupMsg="";
	double bargainCash=0;
	if(csiDTO.getGroupCampaignID()>0){
		GroupBargainDetailDTO bargainDetailDto= Postern.getGroupBargainDetailByID(csiDTO.getGroupCampaignID());
		GroupBargainDTO bargainDTO=Postern.getGroupBargainByID(bargainDetailDto.getGroupBargainID());
		if(Postern.isCash(bargainDTO.getMopId()) && "D".equals(csiDTO.getPaymentStatus())){
			bargainCash=bargainDTO.getPrepayImmediateFee()+bargainDTO.getPrepayDepositFee();
		}
		showGroupMsg="(�Ź�ȯ�ֽ�֧����Ԥ���ܼ�:"+bargainCash+")";
	%>
    	<tr>
	  <td valign="middle" class="list_bg2" align="right" width="17%" >�Ź�ȯ��</td>
	  <td colspan="3" class="list_bg1"><font size="2">
	     <input type="text" name="txtDetailNo" size="25"  value="<%=bargainDetailDto.getDetailNo()%>" class="textgray" readonly   ><font color="red">(���Ź�ȯҪ�˻����ͻ�)</font>
	  </font></td>
	</tr>	
	<%
		}
	%> 
	<tr>
           <td class="import_tit" colspan="4"  align="center" >�����嵥</td>          
        </tr>  
  </table>
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
          <tr class="list_head">    
            <td class="list_head" width="17%">��Ŀ����</td>
            <td class="list_head" width="33%">���</td>
            <td class="list_head" width="17%">״̬</td>
            <td class="list_head" width="33%">����ʱ��</td>
           </tr>
<%
    Collection colFeeList = Postern.getAllCsiFeeListByCsiAndType(WebUtil.StringToInt(request.getParameter("txtReferSheetID")), "P;V;L");
    double fTotalFee = 0;
    double fTotalReturnFee = 0;
    System.out.println(colFeeList);
    if (colFeeList!=null)
    {
        Iterator it = colFeeList.iterator();
        
        while (it.hasNext())
        {
            AccountItemDTO cf = (AccountItemDTO)it.next();    
            AcctItemTypeDTO acctemTypeDto =Postern.getAcctItemTypeDTOByAcctItemTypeID(cf.getAcctItemTypeID());         
            fTotalFee += cf.getValue();           
%>            
         <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
         <td align="center" ><%=acctemTypeDto.getAcctItemTypeName()%></td>
         <td align="center" ><%=WebUtil.bigDecimalFormat(cf.getValue())%></td>
         <td align="center" ><o:getcmnname typeName="SET_F_FTSTATUS" match="<%=cf.getStatus()%>" /></td>
         <td align="center" ><tbl:writedate name="oneline" property="createTime"/><%=cf.getCreateTime()%></td>           
     	</tbl:printcsstr>
<%
            
        }    
    }
%>

         <tr>
           <td valign="middle" class="list_bg2" align="right" width="17%" >Ӧ���ܼ�</td>
           <td colspan="4" class="list_bg1"><font size="2">
	    <input type="text" name="txtTotalFeeDeserved" size="25"  value="<%=WebUtil.bigDecimalFormat(fTotalFee)%>" class="textgray" readonly >
          </font></td>
         
         </tr>
         </table>
         <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="fulltable" >
         <tr>
           <td class="import_tit" colspan="4"  align="center" >֧���嵥</td>          
         </tr>
         </table>
         <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">
          <td  class="list_head"  width="17%">���ѷ�ʽ</td>
          <td  class="list_head"  width="20%">���</td>
          <td  class="list_head"  width="23%">�տ���</td>
          <td  class="list_head"  width="20%">�շѻ���</td>        
          <td  class="list_head"  width="20%">����ʱ��</td>
       </tr>
         
<%
    Collection colPaymentList = Postern.getAllCsiPaymentListByCsiAndType(WebUtil.StringToInt(request.getParameter("txtReferSheetID")));
    String paymentName ="";
    
    if (colPaymentList!=null)
    {
        Iterator it = colPaymentList.iterator();
        Map  payMap =Postern.getAllMethodOfPayments();
        while (it.hasNext())
        {
            PaymentRecordDTO paymentRecordDto = (PaymentRecordDTO)it.next();
            MethodOfPaymentDTO paymentDto =(MethodOfPaymentDTO)payMap.get(String.valueOf(paymentRecordDto.getMopID()));
            paymentName =paymentDto.getName();
                
            if (paymentDto.getCashFlag().equals("Y"))       //�ֽ�       
            {
                fTotalReturnFee += paymentRecordDto.getAmount();
            }
            String opName =Postern.getOperatorNameByID(paymentRecordDto.getOpID());
            if (opName ==null) opName="";
            String orgName = Postern.getOrgNameByID(paymentRecordDto.getOrgID());
            if (orgName == null) orgName = "";
            
              //���ѷ�ʽ
            Map mapBankMop=Postern.getAllMethodOfPayments();
            MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get(String.valueOf(paymentRecordDto.getMopID()));
            String strMopName = null;
            if (dtoMOP!=null) strMopName=dtoMOP.getName();
            if (strMopName==null) strMopName="";
%>
         <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><%=strMopName%></td>
          <td align="center" ><%=WebUtil.bigDecimalFormat(paymentRecordDto.getAmount())%></td>
          <td align="center" ><%=opName%></td>
          <td align="center" ><%=orgName%></td>        
           <td align="center" ><%=paymentRecordDto.getCreateTime()%></td>

       </tbl:printcsstr>
<%
      	} 
            
    }
%>
	 <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >ʵ���ֽ��ܼ�</td>
          <td colspan="4" class="list_bg1"><font size="2">
	    <input type="text" name="txtTotalReturnFee" size="25"  value="<%=WebUtil.bigDecimalFormat(fTotalReturnFee-bargainCash)%>" class="textgray" readonly ><font color="red"><%=showGroupMsg%></font>
          </font></td>
         </tr>
</table>
<table align="center" width="98%" border="0" cellspacing="1" cellpadding="3" class="fulltable" >
         <tr>
          <td class="import_tit" colspan="4"  align="center" >ʵ�˷���</td>
         </tr>
	 <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >ʵ���ֽ��ܶ�</td>
          <td colspan="3" class="list_bg1"><font size="2">
	    <input type="text" name="txtTotalReturnFee" size="25"  value="<%=WebUtil.bigDecimalFormat(fTotalReturnFee-bargainCash)%>" class="textgray" readonly >
          </font></td>
         </tr>
      </table>
<%
    Collection lstDevice = Postern.getAllDeviceByCSIID(WebUtil.StringToInt(request.getParameter("txtReferSheetID")),"C");
    if(lstDevice!=null && !lstDevice.isEmpty()){
    confirmMessage=confirmMessage+"(��ȷ���û��Ѿ��黹�豸)";
    pageContext.setAttribute("AllCustomerDeviceList", lstDevice);
%>
 <table width="98%%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       
    <tr>
      <td colspan="4" class="import_tit" align="center"><font size="3">Ҫ�˻����豸</font></td>
    </tr>         
    <tr class="list_head">
	    <td class="list_head" ><div align="center">ID</div></td>
	    <td class="list_head" ><div align="center">����</div></td>
	    <td class="list_head"><div align="center">�ͺ�</div></td>          
	    <td class="list_head"><div align="center">���к�</div></td>
    
    </tr> 
	<lgc:loop property="AllCustomerDeviceList" item="onedevice" >
	<%
		TerminalDeviceDTO dto = (TerminalDeviceDTO)pageContext.getAttribute("onedevice");
		String strClass = Postern.getDeviceClassDesc(dto.getDeviceClass());
		String strModel = Postern.getDeviceModelDesc(dto.getDeviceModel());					    
	%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		  <input type="hidden" name="txtDeviceID"  value="<tbl:write name="onedevice" property="DeviceID" />" >
	      	  <td align="center"><tbl:writenumber name="deviceId" property="DeviceID" digit="7" /></td>
	      	  <td align="center"><%=strClass%></td>
	      	  <td align="center"><%=strModel%></td>
	      	  <td align="center"><tbl:write name="onedevice" property="SerialNo"/></td>
	</tbl:printcsstr>  	 
	</lgc:loop>  
	<tr>
    	<td colspan="10" class="list_foot"></td>
    </tr>
</table>  
<%}%>	
  <br>
     <tbl:hiddenparameters pass = "txtCustomerID/txtJobCardID"/>
  <input type="hidden" name="txtWorkResult" value="" > 
   <input type="hidden" name="func_flag"  value="111"> 
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="job_card_view_for_contact_of_install.do" />" class="btn12">��һ��</a></td>           
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">ȷ��ȡ��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
      </table>    
</form>    
       <br>
  </td>
  </tr>
  </table>   
