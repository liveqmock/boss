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
String confirmMessage="您确认要退费吗";
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
        <td class="title">维修工单取消确认</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
<form name="frmPost" method="post" action="cancel_for_repair.do" > 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        	<td class="list_bg2" width="17%"><div align="right">工单编号</div></td>
        	<td class="list_bg1" width="33%">
			<tbl:writeparam name="txtJobCardID" />
		</td>
		<td class="list_bg2" width="17%"><div align="right">报修单编号</div></td>
        	<td class="list_bg1" width="33%">
			<tbl:writeparam name="txtReferSheetID" />
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">创建时间</div></td>
		<td class="list_bg1"  > 
			<tbl:writeparam name="txtActionTime"/>
		</td>
		 
			<tbl:csiReason csiType="BK" name="txtWorkResultReason" action="C" showType="label"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtWorkResultReason"  controlSize="25" tdControlColspan="1" />
		
	</tr>
        <tr>
		<td class="list_bg2" ><div align="right">描述信息</div></td>
		<td class="list_bg1" colspan="3"> 
			<tbl:writeparam name="txtMemo" />
		</td>
	</tr>
	
	<tr>
           <td class="import_tit" colspan="4"  align="center" >费用清单</td>          
        </tr>  
  </table>
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
          <tr class="list_head">    
            <td class="list_head" width="17%">帐目类型</td>
            <td class="list_head" width="33%">金额</td>
            <td class="list_head" width="17%">状态</td>
            <td class="list_head" width="33%">创建时间</td>
           </tr>
<%
    Collection colFeeList = Postern.getAllFeeListByCsiAndType(WebUtil.StringToInt(request.getParameter("txtReferSheetID")), "P");
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
           <td valign="middle" class="list_bg2" align="right" width="17%" >应收总计</td>
           <td colspan="4" class="list_bg1"><font size="2">
	    <input type="text" name="txtTotalFeeDeserved" size="25"  value="<%=WebUtil.bigDecimalFormat(fTotalFee)%>" class="textgray" readonly >
          </font></td>
         
         </tr>
         </table>
         <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="fulltable" >
         <tr>
           <td class="import_tit" colspan="4"  align="center" >支付清单</td>          
         </tr>
         </table>
         <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">
          <td  class="list_head"  width="17%">付费方式</td>
          <td  class="list_head"  width="20%">金额</td>
          <td  class="list_head"  width="23%">收款人</td>
          <td  class="list_head"  width="20%">收费机构</td>        
          <td  class="list_head"  width="20%">创建时间</td>
       </tr>
         
<%
    Collection colPaymentList = Postern.getAllPaymentListByCsiAndType(WebUtil.StringToInt(request.getParameter("txtReferSheetID")),"P");
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
                
            if (paymentDto.getCashFlag().equals("Y"))       //现金       
            {
                fTotalReturnFee += paymentRecordDto.getAmount();
            }
            String opName =Postern.getOperatorNameByID(paymentRecordDto.getOpID());
            if (opName ==null) opName="";
            String orgName = Postern.getOrgNameByID(paymentRecordDto.getOrgID());
            if (orgName == null) orgName = "";
            
              //付费方式
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
          <td valign="middle" class="list_bg2" align="right" width="17%" >实收现金总计</td>
          <td colspan="4" class="list_bg1"><font size="2">
	    <input type="text" name="txtTotalReturnFee" size="25"  value="<%=WebUtil.bigDecimalFormat(fTotalReturnFee)%>" class="textgray" readonly ><font color="red"></font>
          </font></td>
         </tr>
</table>
<table align="center" width="98%" border="0" cellspacing="1" cellpadding="3" class="fulltable" >
         <tr>
          <td class="import_tit" colspan="4"  align="center" >实退费用</td>
         </tr>
	 <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >实退现金总额</td>
          <td colspan="3" class="list_bg1"><font size="2">
	    <input type="text" name="txtTotalReturnFee" size="25"  value="<%=WebUtil.bigDecimalFormat(fTotalReturnFee)%>" class="textgray" readonly >
          </font></td>
         </tr>
      </table>

  <br>
     <tbl:hiddenparameters pass = "txtCustomerID/txtJobCardID/txtReferSheetID"/>
  <input type="hidden" name="txtWorkResult" value="" > 
   <input type="hidden" name="func_flag"  value="1007"> 
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="contactrep_view.do" />" class="btn12">上一步</a></td>           
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">确认取消</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
      </table>    
</form>    
       <br>
  </td>
  </tr>
  </table>   
