<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.WebQueryUtility" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>


<script language=javascript>
function query_submit(){ 	
	  if (check_Blank(document.frmPost.txtDistrictID, true, "区域"))
		    return ;
		document.frmPost.action = "customer_info_stat_query.do";
		document.frmPost.submit();
}


</script>
<%
   int districtID =WebUtil.StringToInt(request.getParameter("txtDistrictID"));
   int colNum =4;
   Map reportMp = new LinkedHashMap();
   Map distTreeMap =Postern.getDistrictSettingForCreateTree();
   
   int hrdistinctId =(Postern.getSystemSettingValue("SET_HRDISTICNTID_FOR_STATIC")==null) ? -2 :Integer.parseInt(Postern.getSystemSettingValue("SET_HRDISTICNTID_FOR_STATIC"));
	 int yqdistinctId =(Postern.getSystemSettingValue("SET_YQDISTINCTID_FOR_STATIC")==null) ? -3 :Integer.parseInt(Postern.getSystemSettingValue("SET_YQDISTINCTID_FOR_STATIC"));

   ArrayList yqCol =(ArrayList)distTreeMap.get(new Integer(yqdistinctId));
   ArrayList hrCol =(ArrayList)distTreeMap.get(new Integer(hrdistinctId));
   
   if ((yqCol !=null &&yqCol.contains(new Integer(districtID))) || districtID ==yqdistinctId){
       reportMp= WebQueryUtility.getReportCustomerInfo(yqdistinctId);   
   } else if ((hrCol !=null &&hrCol.contains(new Integer(districtID))) || districtID ==hrdistinctId){
   	   reportMp= WebQueryUtility.getReportCustomerInfo(hrdistinctId); 
   }
   colNum =colNum+reportMp.size();
   int[] sumInt =new int[colNum];
%>

<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
 
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">歌华有线用户情况统计表</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="customer_info_stat_query.do" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
            <td class="list_bg2" align="right">所在区域*</td> 
            <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	          <input type="text" name="txtDistrictDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtDistrictDesc" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_districts('all','txtDistrictID','txtDistrictDesc')">
            </td>
       </tr>
   </table>
    
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	   <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
		  </table>
	  </td>
    <rs:hasresultset>
	  <td class="list_bg1">
		   <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="导&nbsp;出" onclick="javascript:out_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
		   </table>
	  </td>
   </rs:hasresultset> 
	</tr>
 </table>  
     
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr>
    		<td><img src="img/mao.gif" width="1" height="1"></td>
   	</tr>
 </table>
 <br>
 <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
     <tr class="list_head">
         <td class="list_head" >区域名称</td>
         <td class="list_head" >总户数</td> 
         <%
           Iterator it=reportMp.keySet().iterator();
           while (it.hasNext()){
              String columnName =(String)it.next();
         %>
         <td class="list_head" ><%=columnName%></td> 
         <%
           }
         %>
         <td class="list_head" >本月退网户数</td>
         <td class="list_head" >本月新增户数</td>
       </tr>
       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
      <%
          Collection resultCols = (Collection)pageContext.getAttribute("oneline");
       	  Iterator resultIter =resultCols.iterator();
       	  while (resultIter.hasNext()) {
       %>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
       <%
           for (int i=0;i<colNum;i++){
              Object resultValue =resultIter.next();
              if (i!=0) sumInt[i] =sumInt[i]+Integer.parseInt(resultValue.toString());
       %>
          <td align="center" ><%=resultValue%></td>  
       <%
           }
       %>
       </tbl:printcsstr>
       <%
          }
       %>
       </lgc:bloop>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
           <td align="center" >合&nbsp;&nbsp;计</td> 
       <%
           for (int i=1;i<sumInt.length;i++){
       %>
           <td align="center" ><%=sumInt[i]%></td>    
       <%
           }
       %> 
        </tbl:printcsstr> 
  
  <tr>
    <td colspan="<%=colNum%>" class="list_foot"></td>
  </tr>    
</table>  
</rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

</td></tr>
</table>

<script language=javascript>
function out_submit(){ 
	 if (check_Blank(document.frmPost.txtDistrictID, true, "区域"))
		    return ;  
   var  txtdistrictID =document.frmPost.txtDistrictID.value;
   var  txtDistrictDesc =document.frmPost.txtDistrictDesc.value;
   window.open('customer_info_stat_excel.do?pageCode=customer_info_stat_query_excel&txtDistrictID='+txtdistrictID+"&txtDistrictDesc="+txtDistrictDesc,'',
                 'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');		
}
</script>