<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="operator" prefix="oper"%>

<%@ page import="com.dtv.oss.web.util.*"%>
<STYLE TYPE="text/css">
	.fatherDivStyle {
		  FONT-SIZE:11pt; COLOR:#FFFFFF; TEXT-DECORATION:None
		  margin: 0px 0px 0px 0px;  padding: 0px 0px 0px 0px 
		  outline: double 5px 
		  cursor:hand
	}
	.childDivStyle {
		  FONT-SIZE:11pt; COLOR:#FFFFFF; TEXT-DECORATION:None
		  margin: 0px 0px 0px 0px;  padding: 0px 0px 0px 0px 
		  background-color: #ff80ff
		  outline: double 5px 
		  cursor:hand
	}
	.onMouseOverStyle {
		  FONT-SIZE:11pt; COLOR:#FFFF80; TEXT-DECORATION:None
	}
	.onMouseOutStyle {
		  FONT-SIZE:11pt; COLOR:#0099FF; TEXT-DECORATION:None
	}
</STYLE>
<%
	 String strType=request.getParameter("dsType");
	 String strShowType=request.getParameter("showType");
	 
	 String labelType = request.getParameter("labeType");

	System.out.println("showType"+strShowType);
	System.out.println("***********111labelType"+labelType);
%>
<script language="JavaScript" type="text/JavaScript">
<!--
function getChildNode(who,img,pk,nodeName){
	 var distTree ="distTree";
	 if (nodeName.indexOf(distTree)==0 && distTree.indexOf(nodeName)==0){
	 	   document.frames("ajaxRequest").frmSubmit('distTree',pk,who,img);
	 }
}
function loadNodeInfo(info,who,img){
	if(img&&who&&info){
     var childNode=document.getElementById(who);//修改的内容,注意
     var nodeImg  =document.getElementById(img);//修改的内容,注意
     childNode.innerHTML=info;
     if(childNode.style.display=="none") {
        childNode.style.display="block";
        nodeImg.src = openImg;
     }else {
        childNode.style.display="none";
        nodeImg.src = closeImg;
     }
  }
}
//-->
</script>
<form name="districtPost" method="post" action="">
	


<table width="100%" align="center" border="0" cellspacing="0"	cellpadding="5" >


	<tr >
		<td width="100%" id="backtd" valign="top" >
			<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="5px">
			  <tr>
			    <td align="left" width="80%">
						<input type="hidden" name="districtID">
						<input type="hidden" name="districtDesc">
	
						
						<tbl:JavaScriptTreeWrite type="<%=strShowType%>" labeltype="<%=labelType%>" needCreatRadioType="<%=strType%>" retrunID="document.districtPost.districtID" retrunDesc="document.districtPost.districtDesc" />
			    
			    	<iframe src="ajaxRequest.screen" name="ajaxRequest" width="0px" height="0px" frameborder="0"></iframe>
			    	
			    </td>
			  </tr>
			</table>			
		</td>
	</tr>

</table>

</form>