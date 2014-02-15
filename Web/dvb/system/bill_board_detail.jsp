<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerComplainDTO,
                 com.dtv.oss.dto.CustComplainProcessDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
				 com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">公告信息明晰</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 
<form name="frmPost" action="cust_complain_query.do" method="post" > 
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >

<rs:hasresultset>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   <input type="hidden" name="txtCurrentWorkOrgID" value="<tbl:write name="oneline" property="currentWorkOrgID"/>" >  
   <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="dtLastmod"/>" >
    
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td colspan="4" class="import_tit" align="center">公告信息明晰</td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">记录号</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="seqNo" size="25" value="<tbl:write name="oneline" property="seqNo"/>"  class="textgray" readonly >
	  </td>
      <td class="list_bg2" width="17%"  align="right">发布人</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="publishPerson" size="25" value="<tbl:write name="oneline" property="publishPerson"/>"  class="textgray" readonly>
	  </td>
    </tr>

     <tr>   
      <td class="list_bg2" width="17%"  align="right">发布原因</td>
      <td class="list_bg1" width="83%" colspan="3"  align="left">
		<input type="text" name="publishReason" size="82" value="<tbl:write name="oneline" property="publishReason"/>"  class="textgray" readonly>
	  </td>
    </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">重要度</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="grade" size="25" value="<d:getcmnname typeName="SET_G_BILLBOARDGRADE" match="oneline:grade" />"  class="textgray" readonly >
	  </td>
       <td class="list_bg2" width="17%"  align="right">发布时间</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="publishDate" size="25" value="<tbl:writedate name="oneline" property="publishDate" />" class="textgray" readonly >
	  </td>
    </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">有效期起始日期</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="dateFrom" size="25" value="<tbl:writedate name="oneline" property="dateFrom"/>"  class="textgray" readonly >
	  </td>
       <td class="list_bg2" width="17%"  align="right">有效期结束日期</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="dateTo" size="25" value="<tbl:writedate name="oneline" property="dateTo"/>" class="textgray" readonly >
	  </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">状态</td>
      <td class="list_bg1" width="83%" colspan="3" align="left">	
      <input type="text" name="status" size="25" value="<d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" />"  class="textgray" readonly >		 	
	  </td>
    </tr>
    <tr>
         <td colspan="4" class="import_tit" align="center">公告信息</td>
     </tr>
    <tr>
         <td colspan="4" class="import_tit" align="center"><tbl:write name="oneline" property="name" /></td>
     </tr>
      <tr>
         <td colspan="4"  align="center">
			<textarea name="description" length="5" cols=82 rows=9  readonly ><tbl:write name="oneline" property="description" /></textarea>
		</td>
     </tr>
     <tr> 
      <td colspan="4" align="center">
      	<table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>
           	<td><img src="img/button2_r.gif" width="22" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="<bk:backurl property="bill_board_query.do" />" class="btn12">返    回</a></td> 
	   		<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	   		 </tr>
        </table>
       </td>
     </tr>
</table>
</lgc:bloop>
</rs:hasresultset> 
</form> 

    
   