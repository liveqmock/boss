<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.LogisticsSettingDTO"%>
<%@ page import="com.dtv.oss.web.util.LogonWebCurrentOperator"%>
<%@ page import="com.dtv.oss.web.util.CurrentOperator"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>

<SCRIPT language="JavaScript">
 

function form_submit()
{
       if(check_frmCheck())
        document.frmPost.submit();
     
}
function check_frmCheck(){
		
		if(check_Blank(document.frmPost.txtInAndOut, true, "入库的同时是否立即出库")) 
           return false;
		
        if(document.frmPost.txtInAndOut.value=="Y"){
       
	   if(check_Blank(document.frmPost.txtOrgDescrpition, true, "立即出库到的组织机构")) 
           return false;
        }
      return true;
    }
function ChangeSubType(){    
     if(document.frmPost.txtInAndOut.value =="Y"){
      document.frmPost.selDistButton.disabled=false;
     } else {
     
      document.frmPost.selDistButton.disabled=true;
      document.frmPost.txtOutOrgnization.value="";
      }
}
</SCRIPT>

<form name="frmPost" method="post" action="logisticssetting_modify.do">

	<input type="hidden" name="txtActionType" size="20" value="MODIFY">

<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">设备管理配置信息修改</td>
	</tr>
</table>
<rs:hasresultset>
	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0" background="img/line_01.gif">
		<tr>
			<td><img src="img/mao.gif" width="1" height="1"></td>
		</tr>
	</table>
	<br>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
		<%			
					//取产品列表
					Map mapProduct = Postern.getAllSProducts();
					pageContext.setAttribute("preauthProduct", mapProduct);

					//取出库到的组织机构树
				 	LogonWebCurrentOperator wrapOper = (LogonWebCurrentOperator)CurrentOperator.GetCurrentOperator(pageContext.getSession());
					OperatorDTO dtoOper = (OperatorDTO)wrapOper.getCurrentOperator();
					Map mapOrgTree=Postern.getOutOrgTree(dtoOper.getOrgID());
					pageContext.setAttribute("orgTree", mapOrgTree);
					
					LogisticsSettingDTO dto=(LogisticsSettingDTO)pageContext.getAttribute("oneline");
					String strInAndOut=dto.getInAndOut();
					String strOrg=dto.getOutOrgnization()+"";
					String strMatchAndPreauth=dto.getMatchAndPreauth();
					String strProduct1=dto.getPreauthProductid1()+"";
					String strProduct2=dto.getPreauthProductid2()+"";
					String strProduct3=dto.getPreauthProductid3()+"";
					String strProduct4=dto.getPreauthProductid4()+"";
					String strProduct5=dto.getPreauthProductid5()+"";
					String strStatus=dto.getStatus();
					 
				%>
		<table width="810" align="center" border="0" cellspacing="1"
			cellpadding="3">
			<input type="hidden" name="txtSeqNo" size="20"
				value="<tbl:write name="oneline" property="seqNo"/>">
			<tr>
				<td class="list_bg2" align="right" width="20%">入库的同时是否立即出库*</td>
				<td class="list_bg1" width="30%"><d:selcmn name="txtInAndOut"
					mapName="SET_G_YESNOFLAG" match="txtInAndOut" width="23"
					defaultValue="<%=strInAndOut%>" onchange="ChangeSubType()" /></td>
				 <td class="list_bg2"><div align="right">立即出库到的组织机构</div></td>
    	                         <td class="list_bg1">   
    	                       
                                 <input type="hidden" name="txtOutOrgnization" value="<tbl:write name="oneline" property="outOrgnization" />">
	                          <input type="text" name="txtOrgDescrpition" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="outOrgnization" />">
	                            <% if ("N".equals(strInAndOut)){
	                         
	                            %>      
                                  <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,O,S','txtOutOrgnization','txtOrgDescrpition')" disabled=true>
                                  <%}  else if("Y".equals(strInAndOut)){
                                  
                                  %>
                                   <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,O,S','txtOutOrgnization','txtOrgDescrpition')">
                                   <%}%>
                         </td>
   	
				 
			</tr>
			<tr>
				<td class="list_bg2" align="right">配对的同时是否立即预授权</td>
				<td class="list_bg1"><d:selcmn name="txtMatchAndPreauth"
					mapName="SET_G_YESNOFLAG" match="txtMatchAndPreauth" width="23"
					defaultValue="<%=strMatchAndPreauth %>" /></td>
				<td class="list_bg2" align="right">预授权的产品1</td>
				<td class="list_bg1">
					<tbl:select name="txtProduct1" 
						set="preauthProduct" match="txtProduct1" width="23" 
						defaultValue="<%=strProduct1%>" />
				</td>
			</tr>   
			<tr>
				<td class="list_bg2" align="right">预授权的产品2</td>
				<td class="list_bg1"><tbl:select name="txtProduct2"
					set="preauthProduct" match="txtProduct2" width="23" 
					defaultValue="<%=strProduct2%>" /></td>
				<td class="list_bg2" align="right">预授权的产品3</td>
				<td class="list_bg1"><tbl:select name="txtProduct3"
					set="preauthProduct" match="txtProduct3" width="23" 
					defaultValue="<%=strProduct3%>" /></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">预授权的产品4</td>
				<td class="list_bg1"><tbl:select name="txtProduct4"
					set="preauthProduct" match="txtProduct4" width="23" 
					defaultValue="<%=strProduct4%>" /></td>
				<td class="list_bg2" align="right">预授权的产品5</td>
				<td class="list_bg1"><tbl:select name="txtProduct5"
					set="preauthProduct" match="txtProduct5" width="23" 
					defaultValue="<%=strProduct5%>" /></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">状态</td>
				<td class="list_bg1" colspan="3"><d:selcmn name="txtStatus"
					mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" 
					defaultValue="<%=strStatus%>"/></td>
			</tr>
		</table>
	</lgc:bloop>

    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button"
				value="确  认" onclick="javascript:form_submit()"></td>
			<td><img src="img/button_r.gif" width="22" height="20"></td>
		</tr>
	</table>
	   </td>
	</tr>
    </table>    

	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0" background="img/line_01.gif">
		<tr>
			<td><img src="img/mao.gif" width="1" height="1"></td>
		</tr>
	</table>
	</table>

</rs:hasresultset></form>
