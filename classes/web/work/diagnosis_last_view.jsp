<!-- 创建报修单 -->
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
 
 <%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %> 
<%@ taglib uri="bookmark" prefix="bk" %> 
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
 
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">专业诊断信息</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <%
          String jobCardStr=request.getParameter("txtReferJobCardID");
         
          int custProId=WebUtil.StringToInt(jobCardStr);
          
          Map diagnosisMap = Postern.getServeyResultByCustIdForRealUser(custProId,"t_diagnosisinfo","refersourceid");
          
          pageContext.setAttribute("serveyDiagnosisMap",diagnosisMap);
 %>
	 
	 <tr> 
	    <td class="list_bg2" align="right" width="17%">维修工单号</td>
	    <td class="list_bg1" align="left" width="33%">
	    	<%=custProId%>
	    </td>
	    <td class="list_bg2" align="right" width="17%">&nbsp;</td>
	    <td class="list_bg1" align="left" width="33%">&nbsp;</td>
	 </tr>
	 <tr>
	   <tbl:dynamicservey serveyType="D"  serveySubType="P"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25"  match="serveyDiagnosisMap" />
   </tr>
</table>
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           
           <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"  ><a href="<bk:backurl property="job_card_view.do/sheetrep_view.do/cp_query_detail.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
       </tr>

 </table>
	 
	 
	
</form>
 
 

 
</TD>
</TR>
</TABLE>