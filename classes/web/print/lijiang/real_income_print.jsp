<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.RealIncomeAndFeeCountWrap" %>
<%@ page import="com.dtv.oss.util.Postern, 
                 java.util.*,
                 java.text.DecimalFormat,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.util.TimestampUtility" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>丽江有线电视实收项目清单</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<body>
<TABLE width="660" align="center">
	 <tr>
	 	  <td>&nbsp;</td>
	 </tr>
	  <tr>
	 	  <td>&nbsp;</td>
	 </tr>
	 <tr>
	 	  <td>&nbsp;</td>
	 </tr>
   <TR>
      <TD align="center">
          <B><SPAN style="FONT-SIZE: 14pt; FONT-FAMILY: 黑体; mso-hansi-font-family: 宋体">云南丽江有线电视实收项目清单</SPAN></B>
      </TD>
   </TR>
</table>


<table  width="600"  align="center" style="border:1 solid black"   cellspacing="1" cellpadding="1">
<%
  Map printInfomap =(Map)request.getAttribute("RealIncomePrintInfo");
  if (printInfomap !=null && !printInfomap.isEmpty()){	    
%>
  <tr>
   	 <td colspan="6" align="center" style="border-bottom:1 solid black">
   	 	 <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">查询条件</SPAN>
   	 </td>
  </tr>
<%
    for (Iterator it=printInfomap.entrySet().iterator();it.hasNext();){
        Map.Entry e =(Map.Entry)it.next();
%>
	<tr>
		<td align="center"  width="25%" style="border-right:1 solid black;border-bottom:1 solid black"><%=e.getKey()%></td>
		<td align="center"  width="25%" style="border-right:1 solid black;border-bottom:1 solid black" colspan="2"><%=e.getValue()%></td>
<%
      if (it.hasNext()){
         Map.Entry e1 =(Map.Entry)it.next();
%>
    <td align="center"  width="25%" style="border-right:1 solid black;border-bottom:1 solid black"><%=e1.getKey()%></td>
		<td align="center"  width="25%" style="border-bottom:1 solid black" colspan="2"><%=e1.getValue()%></td>
<%
      } else {
%>
    <td align="center"  width="25%" style="border-right:1 solid black;border-bottom:1 solid black">&nbsp;</td>
		<td align="center"  width="25%" style="border-bottom:1 solid black" colspan="2">&nbsp;</td>
<%
       }  
%>
  </tr>
<% 
     }
   }
%>
	
  <tr>
    <td colspan="6" align="center" style="border-bottom:1 solid black">
    	<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">查询分类合计</SPAN>
    </td>
  </tr>
  <tr>
    <td align="center"  width="25%" style="border-right:1 solid black;border-bottom:1 solid black">帐目类型</td>
    <td align="center"  width="5%" style="border-right:1 solid black;border-bottom:1 solid black">笔数</td>
    <td align="center"  width="20%" style="border-right:1 solid black;border-bottom:1 solid black">小计</td>
    <td align="center"  width="25%" style="border-right:1 solid black;border-bottom:1 solid black">帐目类型</td>
    <td align="center"  width="5%" style="border-right:1 solid black;border-bottom:1 solid black">笔数</td>
    <td align="center"  width="20%" style="border-bottom:1 solid black">小计</td>
  </tr>
  
<%
  QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
  Map  map=RepCmd.getMap();
  if (map!=null && !map.isEmpty()){
     Iterator mapIterator=map.keySet().iterator();
     String key="";
     RealIncomeAndFeeCountWrap value=null;
     while (mapIterator.hasNext()){
	      key=(String)mapIterator.next();
		    value=(RealIncomeAndFeeCountWrap)map.get(key);
%>
   <tr>
     <td align="center"  style="border-right:1 solid black;border-bottom:1 solid black"><%=key%></td>
     <td align="center"  style="border-right:1 solid black;border-bottom:1 solid black"><%=value.getRecordCount()%> </td>
	   <td align="center"  style="border-right:1 solid black;border-bottom:1 solid black"><%=value.getValue()%> </td>
<%
       if (mapIterator.hasNext()){
          key=(String)mapIterator.next();
	        value=(RealIncomeAndFeeCountWrap)map.get(key);
%>
    <td align="center" style="border-right:1 solid black;border-bottom:1 solid black"><%=key%></td>
	  <td align="center" style="border-right:1 solid black;border-bottom:1 solid black"><%=value.getRecordCount()%> </td>
	  <td align="center" style="border-bottom:1 solid black"><%=value.getValue()%> </td>
<% 
       } else { 
%>
	  <td style="border-right:1 solid black;border-bottom:1 solid black" ><br></td>
	  <td style="border-right:1 solid black;border-bottom:1 solid black"><br></td>
	  <td style="border-bottom:1 solid black"><br></td>
<%
       }
%>
  </tr>
<% 
     }
   }
  Double fl=(Double)RepCmd.getExtraPayLoad();
  double totalAmount=0;
  if(fl!=null) totalAmount=fl.doubleValue();
  DecimalFormat to= new DecimalFormat("0.00");
%>
  <tr>
  	<td align="center"  width="25%" style="border-right:1 solid black;">总计：</td>
  	<td align="center"  width="25%" style="border-right:1 solid black;" colspan="2"><%=to.format(totalAmount) %></td>
   	<td align="center"  width="25%" style="border-right:1 solid black;">打印日期：</td>
   	<td align="center"  width="25%" colspan="2"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%></td>
  </tr>
</table>

<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>