<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.service.component.ImmediateCountFeeInfo" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO" %>
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page import="java.util.*" %>
<%
	String paidList=(String)request.getAttribute("packageList");
	String includeParameter=(String)request.getAttribute("includeParameter");
	String pidList=(String)request.getAttribute("productList");
	String alterFeeFlag=Postern.getAlterFeeFlag((String)request.getAttribute("PayCsiType"));
	if(alterFeeFlag==null||"".equals(alterFeeFlag))alterFeeFlag=request.getParameter("PayCsiType");
	String acctshowFlag =(String)request.getAttribute("acctshowFlag");
	if(acctshowFlag==null||"".equals(acctshowFlag))acctshowFlag=request.getParameter("acctshowFlag");
	String confirmFlag =(String)request.getAttribute("confirmFlag");
	String rel=request.getParameter("reload");
%>
<SCRIPT language="JAVASCRIPT">

function fee_window()
{
   var strFeatures = "top=120px,left=120px,width=750px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
   var vUrl='fee_adjust.do?pList=<%=pidList%>&paidList=<%=paidList%>';
   window.open(vUrl,"费用调整明细",strFeatures);
}
<!--20140222 添加发票信息-->
function fapiao_window()
{
    var strFeatures = "top=120px,left=120px,width=750px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
    var vUrl='add_fapiao_serialno.do?pList=<%=pidList%>&paidList=<%=paidList%>';
    window.open(vUrl,"录入发票",strFeatures);
}
<!-- end 20140222 添加发票信息-->
function window_reload(){
	document.FrameUS.location="fee_adjust_reload.screen?PayCsiType=<%=alterFeeFlag%>&acctshowFlag=<%=acctshowFlag%>&reload=true";
}
function div_reload(){
	document.getElementById("feeDiv").innerHTML=document.FrameUS.feeDiv.innerHTML;
}
</SCRIPT>

<table align="center" width="100%" border="0" cellspacing="0" cellpadding="0" class="list_bg">
	<tr height="25"> 
		<td width="100" class="import_tit">
		</td>	
		<td class="import_tit">
			<table align="center" border="0" width="100%" cellspacing="0" cellpadding="0">
	      <tr> 
	         <td class="import_tit" align="center"><font size="3">帐户费用清单</font></td>
	      </tr>
			</table>
		</td>
		<td width="200" align="center" class="import_tit">
		<%
		  if("Y".equals(alterFeeFlag)&&(("FALSE".equalsIgnoreCase(confirmFlag) && !"FEE".equalsIgnoreCase(includeParameter)) || ("FEE".equalsIgnoreCase(includeParameter)))){
		%>
			<table align="center" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
                <td><img src="img/button_l.gif" border="0" width="11"></td>
                <td background="img/button_bg.gif"><a href="javascript:fapiao_window()" class="btn12">录入发票号码</a></td>
                <td><img src="img/button_r.gif" border="0" width="22"></td>
	        <td><img src="img/button_l.gif" border="0" width="11"></td>
	        <td background="img/button_bg.gif"><a href="javascript:fee_window()" class="btn12">调整费用</a></td>
	        <td><img src="img/button_r.gif" border="0" width="22"></td>
				</tr>
			</table>
		<%}else{%>
            <table align="center" border="0" cellspacing="0" cellpadding="0">
            <tr>
            <td><img src="img/button_l.gif" border="0" width="11"></td>
            <td background="img/button_bg.gif"><a href="javascript:fapiao_window()" class="btn12">录入发票号码</a></td>
            <td><img src="img/button_r.gif" border="0" width="22"></td>
            </tr>
            </table>
        <%}%>
		</td>
	</tr> 
</table>      
<div id="feeDiv">

<%
	 request.setAttribute("productList",request.getAttribute("productList"));
   double generalfeeToatl =0;
   double forceFeeTotal =0;
   int acctItemCount =0;
/*旧的代码,优先取request中费用值,当费用调整后,重新请求一个.do会在request中重新设值,覆盖掉调整的费用.
   CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
   if (RepCmd ==null) RepCmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
   */
   CommandResponse RepCmd = null;
   //当头一次调用本页面时,取reqeust值,
   if("fee".equalsIgnoreCase(includeParameter)){
   	 RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
   }
   //request中没有值,取session,session没有设一个新的对像,.
   if (RepCmd ==null) RepCmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
	 if (RepCmd ==null){
	    //RepCmd = new CommandResponse(new ArrayList());
  		ArrayList nullFee=new ArrayList();
	  	nullFee.add(new ImmediateCountFeeInfo());
			RepCmd = new CommandResponse(nullFee);			
	 } 
	 	
   request.getSession().setAttribute(CommonKeys.FEE_SESSION_NAME,RepCmd);
   request.setAttribute("ResponseFromEJBEvent",RepCmd);
   String  forceFeeFlag =(request.getAttribute("forceFeeFlag") ==null) ? "false" :(String)request.getAttribute("forceFeeFlag");
   if ("true".equalsIgnoreCase(acctshowFlag)){
%>
<!--20140222 输出提示串 -->
<%!String prtFapiaoHaoma,prtFapiaoSerialNo;%>
<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" isOne="true">
   <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >	
	    <tr class='list_head'> 
        <td width="20%" class='list_head'   align="center" >帐户ID</td>
        <td width="20%" class='list_head'   align="center" >帐户名称</td>
        <td width="20%" class='list_head'   align="center" >现金余额</td> 
        <td width="20%" class='list_head'   align="center" >虚拟货币余额</td> 
        <td width="20%" class='list_head'   align="center" >帐户金额总计</td>  
     </tr>	
     <%
        ImmediateCountFeeInfo immeCountFeeInfo = (ImmediateCountFeeInfo)pageContext.getAttribute("oneline");
        
     %>
     <input type="hidden" name="insideAcctPreCashDoposit" value="<tbl:write name="oneline" property="preCashDoposit" />" >
     <input type="hidden" name="insideAcctPreTokenDoposit" value="<tbl:write name="oneline" property="preTokenDoposit" />" >
     <tr> 
    	 <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="accountid" /></td>
       <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="accountName" /></td>
       <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="preCashDoposit" /></td> 
       <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="preTokenDoposit" /></td> 
       <td width="20%" class='list_bg1'   align="center" ><%=WebUtil.bigDecimalFormat(immeCountFeeInfo.getPreCashDoposit()+immeCountFeeInfo.getPreTokenDoposit())%></td>  
     </tr>
   </table> 
</lgc:bloop>
<%
   }

%>

<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" isOne="true">
<%
   ImmediateCountFeeInfo immeCountFeeInfo = (ImmediateCountFeeInfo)pageContext.getAttribute("oneline");
   Collection acctItemList = immeCountFeeInfo.getAcctItemList();
   CommandResponse acctItemImp= new CommandResponse(acctItemList);
   request.setAttribute("acctItemImp",acctItemImp);
   Collection groupBargainPayedList =immeCountFeeInfo.getGropBargainPayedList();
   CommandResponse groupBargainPayedImp= new CommandResponse(groupBargainPayedList);
   request.setAttribute("groupBargainPayedImp", groupBargainPayedImp);
   if (acctItemList !=null && acctItemList.size() >0){ 
%>
    <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >    
	     <tr class='list_head'> 
       		<td width="20%" class='list_head'   align="center" >账目类型</td>
       		<td width="20%" class='list_head'   align="center" >费用类型</td>
       		<td width="20%" class='list_head'   align="center" >产品</td> 
       		<td width="20%" class='list_head'   align="center" >金额(元)</td>

    	 </tr>
      <lgc:bloop requestObjectName="acctItemImp" item="acctItem">  
       <%
          acctItemCount =acctItemCount +1;
          AccountItemDTO acctItemDto =(AccountItemDTO)pageContext.getAttribute("acctItem");
           //20140222 发票号码
           String fapiaoSerialNo=acctItemDto.getFapiaoSerialNo();
           String fapiaoHaoma=acctItemDto.getFapiaoHaoma();
           prtFapiaoHaoma=fapiaoHaoma;
           prtFapiaoSerialNo=fapiaoSerialNo;
          AcctItemTypeDTO acctItemTypeDto = Postern.getAcctItemTypeDTOByAcctItemTypeID(acctItemDto.getAcctItemTypeID());
          String  feeType = acctItemTypeDto.getAcctItemTypeName();
          String  productName =Postern.getProductNameByID(acctItemDto.getProductID());
          String  forcedDepositFlag =(acctItemDto.getForcedDepositFlag()==null) ? "" :acctItemDto.getForcedDepositFlag();        

          if (forcedDepositFlag.equals("N")){
		          //generalfeeToatl=(double)(Math.floor(generalfeeToatl*100+acctItemDto.getValue()*100+0.01)/100);
					generalfeeToatl =generalfeeToatl +acctItemDto.getValue();
					
          }else{ 
		          //forceFeeTotal=(double)(Math.floor(forceFeeTotal*100+acctItemDto.getValue()*100+0.01)/100);
              forceFeeTotal =forceFeeTotal +acctItemDto.getValue();
          }
          //System.out.println("acctItemDto.getValue()>"+acctItemCount+":::"+acctItemDto.getValue());
          //System.out.println("generalfeeToatl>"+acctItemCount+":::"+generalfeeToatl);
       %>
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >             
         <td  valign="middle" align="center" ><%=(feeType==null) ? "" :feeType %></td>
         <td  valign="middle" align="center" ><d:getcmnname typeName="SET_F_BRFEETYPE" match="<%=acctItemTypeDto.getFeeType()%>" /></td>
         <td  valign="middle" align="center" ><%=(productName ==null) ? "" : productName%></td>          
         <td  valign="middle" align="center" ><tbl:write name="acctItem" property="Value" /></td>

      </tbl:printcsstr>
     </lgc:bloop>
        <tr class='list_head'><td colspan="5">发票代码：<%=prtFapiaoSerialNo%> 发票号码:<%=prtFapiaoHaoma%> <span style="color:red">(如果与右上角发票代码号码不一致,发票无效)</span></td></tr>
    </table>
<% 
   } 
%>
</lgc:bloop> 
<%
//generalfeeToatl = ((int)(generalfeeToatl*100))/100f;
//forceFeeTotal =((int)(forceFeeTotal*100))/100f;
String acctItemTotalStyle ="";
if (acctItemCount %2 ==0) 
     acctItemTotalStyle ="list_bg1";
else 
     acctItemTotalStyle ="list_bg2"; 

request.setAttribute("defaultGeneralFeeTotal",new Double(WebUtil.bigDecimalFormat(generalfeeToatl).doubleValue()));
request.setAttribute("defaultForceFeeTotal",new Double(WebUtil.bigDecimalFormat(forceFeeTotal).doubleValue()));
%>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
  <tr class='<%=acctItemTotalStyle%>'>
 <%
    //if (forceFeeFlag.equalsIgnoreCase("true") || (forceFeeTotal!=0)){
    if (forceFeeFlag.equalsIgnoreCase("true") || Math.abs(forceFeeTotal)>0.0001){
 %>
    <td  valign="middle" align="right" width="33%">费用应收总计:
        <input type="text" style="text-align:right" size="12" name="generalFeeTotal" value="<%=WebUtil.bigDecimalFormat(generalfeeToatl)%>" class="textgray" readonly>
    </td>
    <td  valign="middle" align="right" width="33%">强制预存总计:
      	<input type="text" style="text-align:right" size="12" name="forceFeeTotal" value="<%=WebUtil.bigDecimalFormat(forceFeeTotal)%>" class="textgray" readonly>
    </td>
    <td  valign="middle" align="right" width="34%">应收金额总计:
     	 <input type="text" style="text-align:right" size="12" name="acctFeeSum" value="<%=WebUtil.bigDecimalFormat(forceFeeTotal+generalfeeToatl)%>" class="textgray" readonly>
    </td>
 <%
    } else{
 %>
    <td  valign="middle" align="right"> 总计待付金额: 
        <input type="text" size="12" style="text-align:right" name="generalFeeTotal" value="<%=WebUtil.bigDecimalFormat(generalfeeToatl)%>" class="textgray" readonly> 
    </td>
 <%
   }
 %>
   </tr>
</table>
</div>
<iframe SRC="" name="FrameUS" id="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>
<%if("true".equals(rel)){%>
<SCRIPT language="JAVASCRIPT">
function window.onload(){
	parent.div_reload();
}
</SCRIPT>
<%}%>


