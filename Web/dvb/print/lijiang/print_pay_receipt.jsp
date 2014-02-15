<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="operator" prefix="oper" %>
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
        <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312"> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</SPAN>
        </TD>
    </TR>
</TABLE>

<%
    String txtCustomerID =request.getParameter("txtCustomerID");
    int custid = Integer.parseInt(txtCustomerID);
    String addrDetail =Postern.getAddressByCustomerID(custid);
    CustomerDTO custDto =Postern.getCustomerByID(custid);
    String txtAcctPayIDList =request.getParameter("txtAcctPayIDList");
    String[] idList =txtAcctPayIDList.split(",");
    Collection prePayList =new ArrayList();
    Collection acctItemList =new ArrayList();
    double payMoney =0;
    String fphm ="";
    for (int i=0;i<idList.length;i++){
        PaymentRecordDTO dto =Postern.getPayRecordDTO(Integer.parseInt(idList[i]));
        payMoney =payMoney +dto.getAmount();
        if (fphm.equals("")){
           fphm =dto.getFaPiaoNo();
        }else{
      	   fphm =fphm +"/"+dto.getFaPiaoNo();
      	}
      	if ("P".equals(dto.getPayType())){
           prePayList.add(dto);
        }else{
        	 if ("M".equals(dto.getReferType())){
        	    acctItemList.addAll(Postern.getAllFeeListByCsiAndType(dto.getReferID(),"M"));
        	 }
        }
    }
    
    
    if (payMoney <0) payMoney = -1*payMoney;
    String chinese_pay_money =WebOperationUtil.convertToChineseNumber(payMoney);
    
    DecimalFormat df = new DecimalFormat("0.00"); 
    
%>
<TABLE width="680" align="left"   cellspacing="1" cellpadding="1" >
<tr>
<td>
<table width="680" cellspacing="1" cellpadding="1" >
<TR height="25">
    <TD width="595" align="left" colspan="7">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;<%=fphm%></SPAN>
    </TD>
</TR>
<TR height="25">
    <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="95" >
        <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD width="85" align="left">
    	<SPAN style="FONT-SIZE: 11pt; FONT-FAMILY: 楷体_GB2312"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%></SPAN>
    </TD>
    <TD align="right" width="130" align="left">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="205" colspan="3">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>  
</TR>
<TR height="15" >
    <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD width="175" colspan=2 nowarp>
    	<SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;&nbsp;客户证号：<%=custDto.getCustomerID()%></SPAN>
    
    </TD>
    <TD width="150" nowarp>
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">客户名称：<%=custDto.getName()%></SPAN>
    </TD>
    
    <TD  width="180" colspan="3">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;&nbsp;客户地址：<%=addrDetail %></SPAN>
    </TD> 
     
</TR>
<!--
<TR height="35">
    <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;<%=custDto.getCatvID()%></SPAN>
    </TD>
    <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;<%=custDto.getName()%></SPAN>
    </TD>
     <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="170" colspan="2">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>  
</TR>
<TR height="35">
    <TD  width="85">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD width="595" colspan="7" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=addrDetail%></SPAN>
    </TD>
</TR>
<TR height="35">
    <TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD  width="425" align="left" colspan="5">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=chinese_pay_money%></SPAN>
    </TD>
    <TD align="right" width="85">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
    <TD align="right" width="85">
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
    </TD>
</TR>
-->
<TR height="150">
	<TD width="85" >
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
   </TD>
   <TD width="600" align="center" colspan="7">
   	 <TABLE width="600" valign="top" cellspacing="0" cellpadding="3" style="line-height:10px;">        
	 <%
	   String ret ="";
	   Collection csiIdCol =new ArrayList();
     Iterator acctIter=acctItemList.iterator();
		 while(acctIter.hasNext()){
			  AccountItemDTO acctItemDto =(AccountItemDTO)acctIter.next();
			  AcctItemTypeDTO itemTypeDto =Postern.getAcctItemTypeDTOByAcctItemTypeID(acctItemDto.getAcctItemTypeID());
			  String acctItemTypeName =itemTypeDto.getAcctItemTypeName();
			  String validBeginDate =(acctItemDto.getDate1()==null) ? "" :WebUtil.TimestampToString(acctItemDto.getDate1(),"yyyy-MM-dd");
			  String validEndDate =(acctItemDto.getDate2()==null) ? "" :WebUtil.TimestampToString(acctItemDto.getDate2(),"yyyy-MM-dd");
			  String validDate =validBeginDate+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ validEndDate;
			  
			  String SCNO="";
			  String stbNo="";
			  //受理单
        CustServiceInteractionDTO csiDTO=Postern.getCsiDTOByCSIID(acctItemDto.getReferID());
        
        Collection protoCol =Postern.getProtocolDTOCol(csiDTO.getCustomerID());
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
        
           if (!csiIdCol.contains(String.valueOf(csiDTO.getId()))){
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
          
             if (CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDP.equals(csiDTO.getType()) || 
                 (CommonKeys.CUSTSERVICEINTERACTIONTYPE_PN.equals(csiDTO.getType()) && !protoCol.isEmpty())){
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
			                ret =ret +"<br>";
			            }
               }
             }
             csiIdCol.add(String.valueOf(csiDTO.getId()));
           }
        }
                
        if (SCNO ==null || "".equals(SCNO)){
           SCNO =stbNo;
        }
        if (SCNO ==null) SCNO ="";
        
        if(!"".equals(SCNO)) SCNO=SCNO+"(设备号)";
        
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
    <TD>
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=SCNO%></SPAN>
    </TD>
    </TR>
		<%
		 }

		 double prePayAmount =0;
		 Iterator   prePayItr =prePayList.iterator();
		 while (prePayItr.hasNext()){
		    PaymentRecordDTO payDto =(PaymentRecordDTO)prePayItr.next();
  	    prePayAmount =prePayAmount +payDto.getAmount();
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
    <TD>
    <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;</SPAN>
    </TD>
    </TR>
    <%
     }
      if (!"".equals(ret)){
    %>
    <TR>
      <TD colspan="4">
       <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;&nbsp;<%=ret%></SPAN>
      </TD>
    </TR>
    <%
      }
    %>    
      </TABLE>
    </td>
  </TR>

</TABLE>
</td>
</tr>

<TR height="35">
<TD colspan="8" >
	  <table width="680"  valign="top" cellspacing="1" cellpadding="1" style="line-height:11px;" >   
        <TR>
        	 <TD width="80" >
              <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
           </TD>
           <TD  nowarp width="340">
           	<!--
            <SPAN  style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;<%=payMoney%></SPAN>
            -->
            <SPAN  style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">合计(大写)：<%=chinese_pay_money%></SPAN>
           </TD>
           
           <TD align="left" nowarp>
            <SPAN  style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">小写：<%="￥"+df.format(payMoney)%></SPAN>
           </TD>
           <!--
           <TD align="right" width="85" >
            <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%></SPAN>
           </TD>
           -->
         </TR>
         <TR>
        	 <TD width="80" >
              <SPAN style="FONT-SIZE: 9pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
           </TD>
           <TD  align="left" nowarp width="340">
            <SPAN  style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">开票单位(盖章)：</SPAN>
           </TD>
           
           <TD align="left" nowarp>
            <SPAN style="FONT-SIZE: 10pt; FONT-FAMILY: 楷体_GB2312">开票人：<oper:curoper property="OperatorName" /></SPAN>
          
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
</body>
</html>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>

      
