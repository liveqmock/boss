<%@ page import="com.dtv.oss.web.util.LogonWebCurrentOperator"%>
<%@ page import="com.dtv.oss.web.util.CurrentOperator"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.tree.GenerateTree"%>
<%@ page import="com.dtv.oss.util.TimestampUtility" %>

<%
String action=request.getParameter("action");
String para=request.getParameter("para");
String targetNodeId=request.getParameter("targetNodeId");
String img=request.getParameter("img");
%>
<script language="JavaScript" type="text/JavaScript">
<!--
function frmSubmit(a,p,who,img){
	var ac=<%=action%>;
	var pa=<%=para%>;
	if(a)ac=a;
	if(p)pa=p;
	var para=document.frmPost.action;
	if(ac||pa){
		para+='?';
		if(ac){
			para+='action='+ac;
		}
		if(pa){
			para+='&para='+pa;
		}
		if(who){
			para+='&targetNodeId='+who;
		}
		if(img){
			para+='&img='+img;
		}
	}
	document.frmPost.action=para;
	document.frmPost.submit();
}

//-->
</script>
<%
LogonWebCurrentOperator wrapOper = (LogonWebCurrentOperator) CurrentOperator.GetCurrentOperator(session);
OperatorDTO dtoOper = (OperatorDTO) wrapOper.getCurrentOperator();
int orgId=dtoOper.getOrgID();
if ("distTree".equalsIgnoreCase(action)&&para!=null&&!"".equals(para)){
	 int districtId=Integer.parseInt(para);
	 String nodeCol=GenerateTree.getDistrictSettingNode(districtId,orgId);
%>

<div id='distTree'>
<%=nodeCol %>	
</div>
<script language="JavaScript" type="text/JavaScript">
<!--
parent.loadNodeInfo(document.getElementById('distTree').innerHTML,'<%=targetNodeId%>','<%=img%>');
//-->
</script>
<%
  }
%>
<form name="frmPost" method="post" action="ajaxRequest.screen">
	
</form>
  
 
