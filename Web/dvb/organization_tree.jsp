<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="operator" prefix="oper" %>

<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.tree.DynamicRootNode" %>
<%@ page import="com.dtv.oss.web.util.*" %>
<STYLE TYPE="text/css">
	A:link {
		  FONT-SIZE:9pt; COLOR:#FFFFFF; TEXT-DECORATION:None
	}
	A:visited {
		  FONT-SIZE:9pt; COLOR:#FFFF80; TEXT-DECORATION:None
	}
	A:active {
		  FONT-SIZE:9pt; COLOR:#0000FF; TEXT-DECORATION:None
	}
	A:hover {
		  FONT-SIZE:9pt; COLOR:#0099FF; TEXT-DECORATION:None
	}
</STYLE>
<form name="frmPost" method="post" action="treeAction.do">
<!-- ר�ű���ͼƬ�� -->
<table width="180"  height="600" border="0" cellspacing="0" cellpadding="0" background="img/left_bg.gif">
      <tr><td valign="top"> 
<!-- ר�ű���ͼƬ�� -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
       <td align="center" valign="top" style="background:url(img/left_top.gif) no-repeat top center; "><img src="img/mao.gif" width="5" height="5">
       <table width="170"  border="0" cellpadding="0" cellspacing="0" bgcolor="#F1F1ED" style="border:1px solid #999999; ">
       <lgc:notempty name="<%=WebKeys.OPERATOR_SESSION_NAME%>" scope="session" >
              <tr>
              <td align="center">
                <table  border="0" cellspacing="5" cellpadding="0">
                    <tr>
                      <td><img src="img/ico_01.gif" width="13" height="12"></td>
                      <td>��ǰ����Ա��</td>
                      <td class="blue en_9pt"><oper:curoper property="OperatorName" /></td>
                    </tr>
                </table>
              </td>
              </tr>
              <tr>
                <td background="img/line_01.gif"><img src="img/mao.gif" width="1" height="1"></td>
              </tr>
              <tr>
                <td><table width="100%"  border="0" cellpadding="0" cellspacing="5">
                    <tr>
                      <td align="center" class="blue"><oper:curoper typeName="orgname" /></td>
                    </tr>
                </table></td>
              </tr>
       </lgc:notempty>
       <lgc:empty name="<%=WebKeys.OPERATOR_SESSION_NAME%>" scope="session" >
              <tr>
                <td><table width="100%"  border="0" cellpadding="0" cellspacing="5" >
                    <tr>
                      <td align="center" class="blue">û�е�ǰ����Ա</td>
                    </tr>
                </table></td>
              </tr>
       </lgc:empty>
       </table>

 	   </td>
	</tr>
	<tr>
		<td align="left" valign="top" >
			<table class="tree">	
			<tbl:TreeWrite/>
			</table>
		</td>
	</tr>
</table>
<!-- ר�ű���ͼƬ�� -->
	</td></tr>
</table>
<!-- ר�ű���ͼƬ�� -->

</form>