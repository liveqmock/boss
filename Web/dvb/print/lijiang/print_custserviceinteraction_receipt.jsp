<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
         com.dtv.oss.dto.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.util.TimestampUtility" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.web.util.WebOperationUtil" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>&nbsp;</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<body>
<TABLE width="680" align="center" height="40">
    <TR>
        <TD align="center">
        <!--
            <B><SPAN style="FONT-SIZE: 14pt; FONT-FAMILY: 黑体; mso-hansi-font-family: 宋体">北京歌华有线用户付费频道订购单</SPAN></B>
        -->    
        <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312"> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</SPAN>
        </TD>
    </TR>
</TABLE>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
 <%
   com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
  	CustomerDTO custDTO=wrap.getCustDto();
  	pageContext.setAttribute("oneline", custDTO);
 	 //客户地址
  	AddressDTO custAddressDTO=wrap.getAddrDto();
	 //受理单
    CustServiceInteractionDTO csiDTO=Postern.getCsiDTOByCSIID(WebUtil.StringToInt(request.getParameter("csiID")));
    //帐户
    AccountDTO accDTO=Postern.getAccountDto(csiDTO.getAccountID());
    //邮寄帐单地址
    AddressDTO acctAddressDto=Postern.getAddressDtoByID(accDTO.getAddressID());
    String SCNO="";
    String stbNo="";
    Collection protoCol =Postern.getProtocolDTOCol(custDTO.getCustomerID());
    if (CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDP.equals(csiDTO.getType()) && protoCol.isEmpty()){
        SCNO = Postern.getSerialNoByCommentsAndClass(csiDTO.getComments(),"SC");
        stbNo =Postern.getSerialNoByCommentsAndClass(csiDTO.getComments(),"STB");
    } else{
       //根据受理单取得本次相关的设备信息
       java.util.Collection clo=Postern.getDeviceIDByCSIID(csiDTO.getId());
       java.util.Iterator it=clo.iterator();
       while(it.hasNext()){
        	TerminalDeviceDTO terminalDeviceDTO=Postern.getTerminalDeviceByID(WebUtil.StringToInt(it.next().toString()));
        	if("SC".equals(terminalDeviceDTO.getDeviceClass())){
       		   if (!"".equals(SCNO)){
    		        SCNO =SCNO +"/"+terminalDeviceDTO.getSerialNo();
    		     }else{
    			      SCNO=terminalDeviceDTO.getSerialNo();
    		     }
    	    }
    	    if("STB".equals(terminalDeviceDTO.getDeviceClass())){
             if (!"".equals(stbNo)){
                stbNo =stbNo +"/"+terminalDeviceDTO.getSerialNo();
             }else{
         	      stbNo =terminalDeviceDTO.getSerialNo();
             }
          }
       }
       if (CommonKeys.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType()) && protoCol.isEmpty()){
          Map productMap =new HashMap();
          productMap = Postern.getDeviceSerialNoByCSIandClass(csiDTO.getId(),"SC");
          if (productMap.isEmpty()){
            productMap = Postern.getDeviceSerialNoByCSIandClass(csiDTO.getId(),"STB");  
          } 
          if (!productMap.isEmpty()){
            Iterator itMap = productMap.keySet().iterator();
            String key = (String) itMap.next();
            ArrayList value = (ArrayList) productMap.get(key);
            SCNO =(String)value.get(0);
          }
       }
    }
    
    if (SCNO ==null || "".equals(SCNO)){
        SCNO =stbNo;
    }
    if (SCNO ==null) SCNO ="";
    if(!"".equals(SCNO)) SCNO=SCNO+"(设备号)";
      
    Collection acctItemList=Postern.getAllFeeListByCsiAndType(csiDTO.getId(),"M");
    String  payMoney =Postern.getSumPaymentValue(csiDTO.getId());
    if (payMoney ==null) payMoney="0.00";
    
    double pay_money = Double.parseDouble(payMoney);
  
    //格式化：
    DecimalFormat df = new DecimalFormat("0.00"); 
      
    
    if (pay_money <0) pay_money = -1*pay_money;
    String chinese_pay_money =WebOperationUtil.convertToChineseNumber(pay_money);
    
    Map  operatorMp =Postern.getAllOperator();
    String operatorName =(String)operatorMp.get(""+csiDTO.getCreateOperatorId());
    
    String fphm =""+csiDTO.getCreateOperatorId()+"--"+ csiDTO.getId();
    
    
%>
<TABLE width="680" align="left"   cellspacing="1" cellpadding="1" >
<tr>
<td>
<table width="680" cellspacing="1" cellpadding="1">
<TR height="25">
    <TD width="170" align="right" colspan="2">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;<%=fphm%></SPAN>
    </TD>
    <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
     <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
     <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
</TR>
<TR height="25">
    <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="85" >
        <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD width="85" >
    
    	<SPAN style="FONT-SIZE: 11pt; FONT-FAMILY: 楷体_GB2312"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%></SPAN>
    </TD>
    <TD align="right" width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
     <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="170" colspan="2">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>  
</TR>
<TR height="15" >
    <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD width="175" colspan=2 nowarp>
    	<SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;&nbsp;客户证号：<tbl:write name="oneline" property="customerID"/></SPAN>
    
    </TD>
    <TD width="150" nowarp>
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">客户名称：<tbl:write name="oneline" property="name"/></SPAN>
    </TD>
    
    <TD  width="180" colspan="3">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;&nbsp;客户地址：<%=custAddressDTO.getDetailAddress() %></SPAN>
    </TD> 
     
</TR>

<TR height="150">
	<TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
   </TD>
   <TD width="600" align="center" colspan="7">
   	 <TABLE width="600" valign="top" cellspacing="0" cellpadding="3" style="line-height:10px;">     
        
	 <%
	   //费用类型合并
	   Map acctItemTypeMap =new LinkedHashMap();
     Iterator acctIter=acctItemList.iterator();
		 while(acctIter.hasNext()){
			  AccountItemDTO acctItemDto =(AccountItemDTO)acctIter.next();
			  AcctItemTypeDTO itemTypeDto =Postern.getAcctItemTypeDTOByAcctItemTypeID(acctItemDto.getAcctItemTypeID());
			  String acctItemTypeName =itemTypeDto.getAcctItemTypeName();
			  String validBeginDate =(acctItemDto.getDate1()==null) ? "" :WebUtil.TimestampToString(acctItemDto.getDate1(),"yyyy-MM-dd");
			  String validEndDate =(acctItemDto.getDate2()==null) ? "" :WebUtil.TimestampToString(acctItemDto.getDate2(),"yyyy-MM-dd");
			  String validDate =validBeginDate+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ validEndDate;
			  if (validDate.equals("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")){
			      validDate ="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+csiDTO.getPoint();
			  }
		%>
		<TR>
    <TD>
       <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=acctItemTypeName%></SPAN>
    </TD>
    <TD >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=acctItemDto.getValue()%></SPAN>
    </TD>
    <TD>
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=validDate%></SPAN>
    </TD>
    </TR>
		<%
		 }
		 
		 double prePayAmount =0;
		 Collection payList =Postern.getAllPaymentListByCsiAndType(csiDTO.getId(),"M");
		 Iterator   payItr =payList.iterator();
		 while (payItr.hasNext()){
		    PaymentRecordDTO payDto =(PaymentRecordDTO)payItr.next();
		    if ("P".equals(payDto.getPayType())){ 
		       prePayAmount =prePayAmount +payDto.getAmount();
		    }
		 }
		 if (prePayAmount !=0){
    %>
    <TR>
    <TD>
       <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;预存费</SPAN>
    </TD>
    <TD >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=prePayAmount%></SPAN>
    </TD>
    <TD>
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;</SPAN>
    </TD>
    </TR>
    <%
     }
      
     String monthCount = request.getParameter("txtUsedMonth");
     String ret ="";
     if (CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDP.equals(csiDTO.getType()) || 
         CommonKeys.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType())){
         /*
        Map productMap =new HashMap();
        productMap = Postern.getDeviceSerialNoByCSIandClass(csiDTO.getId(),"SC");
        if (productMap.isEmpty()){
            productMap = Postern.getDeviceSerialNoByCSIandClass(csiDTO.getId(),"STB");  
        } 
        if (!productMap.isEmpty()){
            Iterator itMap = productMap.keySet().iterator();
            while (itMap.hasNext()) {
		           	String key = (String) itMap.next();
			          ArrayList value = (ArrayList) productMap.get(key);
			          ret =ret +value.size()+"张智能卡订购了"+Postern.getProductNameByID(Integer.parseInt(key))+"节目";
			          //ret =ret +"<br>";
			      }
			    }
			   if(!"".equals(monthCount))
        	ret = ret+","+monthCount+"个月<br>"; 
        */
       //得到用户 产品 及月数
		    int userCount = 0;
		    String productName="";
		    String[] saId_indexs =request.getParameterValues("saId_indexs");
		    if(saId_indexs!= null)userCount=saId_indexs.length;
		    String productList = request.getParameter("ProductList");
		    if(productList!=null && !"".equals(productList.trim())){
		    	String[] aProductID = request.getParameter("ProductList").split(";");
		    	if ((aProductID != null) && (aProductID.length > 0)) {
					for (int i = 0; i < aProductID.length; i++){
					    if(aProductID[i] != null && !"".equals(aProductID[i])){
					        if(!"".equals(productName))productName=productName+",";
					      	productName = productName+Postern.getPackagenameByID(WebUtil.StringToInt(aProductID[i]));
					      }
							
						}
				  }
         }
         //取套餐
        else{
         String campaignList = request.getParameter("CampaignList");
           if(campaignList!=null){
		    	String[] aCampaignID = request.getParameter("CampaignList").split(";");
		    	if ((aCampaignID != null) && (aCampaignID.length > 0)) {
					for (int i = 0; i < aCampaignID.length; i++){
					    if(aCampaignID[i] != null && !"".equals(aCampaignID[i])){
					        if(!"".equals(productName))productName=productName+",";
					      	productName = productName+Postern.getCampaignNameByID(WebUtil.StringToInt(aCampaignID[i]));
					      }
							
						}
				  }
         }
        }
        if(userCount>0 && !"".equals(productName)) ret=userCount+"张智能卡订购了"+productName;
        if(monthCount!= null && !"".equals(monthCount))
        	ret = ret+","+monthCount+"个月<br>"; 
     }
     //门店开户
     else if(CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO.equals(csiDTO.getType())){
       int userCount=0;
       String saids = request.getParameter("saids");
	     if(saids!=null)
	     {
	       if(saids.startsWith(","))saids=saids.substring(1);
	    	 if(saids.split(",").length>0)userCount=saids.split(",").length;
	     }
	     String productName=Postern.getCsiPackage(csiDTO.getId());
	     //monthCount = request.getParameter("txtBuyNum");
	     if(!"".equals(productName)){
		     ret =ret +userCount+"张智能卡订购了"+productName;
		     if(monthCount != null)
		     	ret = ret+","+monthCount+"个月<br>";
	     }
     }
     
     
     
     if (!"".equals(ret)){
    %>
    <TR>
      <TD colspan="3">
       <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=ret%></SPAN>
      </TD>
    </TR>
    <%
      }
    %>
     <TR>
      <TD colspan="3">
       <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=SCNO%></SPAN>
      </TD>
    </TR>
      </TABLE>
    </td>
  </TR>

</TABLE>
</td>
</tr>

<TR height="35">
<TD colspan="8" >
	  <table width="680"  valign="top" cellspacing="1" cellpadding="1" style="line-height:11px;">   
        <TR>
        	 <TD width="80" >
              <SPAN style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
           </TD>
           <TD  nowarp width="340">
           	<!--
            <SPAN  style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;<%=payMoney%></SPAN>
            -->
            <SPAN  style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">合计(大写)：<%=chinese_pay_money%></SPAN>
           </TD>
           
           <TD align="left" nowarp>
           	<!--
            <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=operatorName%></SPAN>
            -->
            <SPAN  style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">小写：<%="￥"+df.format(Double.parseDouble(payMoney))%></SPAN>
           </TD>
           <!--
           <TD align="right" width="85" >
            <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%></SPAN>
           </TD>
           -->
         </TR>
         <TR>
        	 <TD width="80" >
              <SPAN style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
           </TD>
           <TD  align="left" nowarp width="340">
            <SPAN  style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">开票单位(盖章)：</SPAN>
           </TD>
           
           <TD align="left" nowarp>
            <SPAN style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">开票人：<%=operatorName%></SPAN>
          
           </TD>
         </TR>
         	
        </tr>
        </TABLE>
    <TD>
</TR>
</TABLE>
</td>
</tr>
</table>
</lgc:bloop> 
</body>
</html>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>

      
