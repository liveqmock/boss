<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO,com.dtv.oss.dto.OperatorDTO" %>
<%@ page import="com.dtv.oss.web.util.CurrentOperator,com.dtv.oss.web.util.LogonWebCurrentOperator" %>

<script language=javascript>
<!--
function query_submit(){
	document.frmPost.action="customer_product_stat.do";
	if(checkDate()){ 
		document.frmPost.submit();
	}
}
function checkDate(){
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "��������")){
			return false;
		}
	}


	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}

	
  	if (check_Blank(document.frmPost.txtDistrictID, true, "����"))
		return false;
	
	
	return true;
}

//����˵����typeΪ���ͣ�typeNameΪ���֣�����value���֣���subTypeValueΪ�ò�����ֵ
function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}
//-->
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function save_query_result(fname,isOpen){
	var intable=document.getElementById("inTable");
	if(intable!=null){
	intable.border=1;
	var fc=document.getElementById("fileContent");
	document.frmPost.excelTable.value=fc.innerHTML;
	intable.border=0;
	document.frmPost.excelFileName.value=fname;
	document.frmPost.action="excel_download.screen";
	document.frmPost.submit();
	}
}
//-->
</SCRIPT>
<div id="updselwaitlayer" style="position:absolute; left:700px; top:104px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr><td width="100%"><div align="center"><font size="2">���ڻ�ȡ���ݡ�����</font></td></tr>
    </table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0" ></iframe>

<form name="frmPost" method="post" action="customer_product_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10"-->
<input type="hidden" name="txtActionType" size="20" value="all">

<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ���Ʒͳ��</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	 <tr>
	  <td class="list_bg2"><div align="right">�ͻ�����</div></td>
    	<td class="list_bg1"><d:selcmn  name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="23" /> 
   </td>
   	 
    <td class="list_bg2"><div align="right">����*</div></td>
    <td class="list_bg1">
    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">��������</div></td>
    <td class="list_bg1"><d:selcmn  name="txtServiceType" mapName="SET_C_USER_TYPE" match="txtServiceType" width="23" /> </td>
    <td class="list_bg2"><div align="right">�ͻ���Ʒ״̬</div></td>
		<td class="list_bg1"><d:selcmn  name="txtCPStatus" mapName="SET_C_CUSTOMERPRODUCTPSTATUS" match="txtCPStatus" width="23" /></td>	
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">����ͳ�Ʋ�Ʒ<br/>(Ĭ��Ϊȫ��)</div></td>
    	<td class="list_bg1" colspan="3">
    	<textarea name="txtCPProductIDListValue" cols="82" rows="2"  readonly class="textareashow"><tbl:writeparam name="txtCPProductIDListValue" /></textarea>
	   	<input name="txtCPProductIDList" type="hidden" value="<tbl:writeparam name="txtCPProductIDList" />">
	   	<input name="checkbutton" type="button" class="button" value="ѡ��" onClick="javascript:open_select('PRODUCT','txtCPProductIDList',document.frmPost.txtCPProductIDList.value,'','','')"> 
    	</td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">��Ʒ���</div></td>
    <td class="list_bg1"><d:selcmn  name="txtProductClass" mapName="SET_P_PRODUCTCLASS" match="txtProductClass" width="23" /> </td>
    <td class="list_bg2"><div align="right">����ʱ������</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			<pri:authorized name="customer_product_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="������" onclick="javascript:save_query_result('�ͻ���Ʒͳ��',true)"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			</pri:authorized>
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
</table>
<rs:hasresultset>
<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
	<tr class="list_head">
		<td class="list_head" width="2%" rowspan="2" nowrap><div align="center">����</div></td>
			<%
			
				Map mapShow = Postern.getAllSAName(request.getParameter("txtCPProductIDList"),request.getParameter("txtProductClass"));
				
				System.out.println("___"+Postern.containsHardProduct(request.getParameter("txtCPProductIDList")));
				if(Postern.containsHardProduct(request.getParameter("txtCPProductIDList"))&&("H".equals(request.getParameter("txtProductClass"))||"".equals(request.getParameter("txtProductClass"))))
				{
					mapShow.put("0", "Ӳ��");
				}
				
				Map allMap = Postern.getProductGroupByServiceID(request.getParameter("txtCPProductIDList"),"S");
				
				Map tempMap = Postern.getHardProductMap("H",null,request.getParameter("txtCPProductIDList"));
				allMap.put("0",tempMap);

				if(mapShow == null)mapShow = new HashMap();
				if(allMap == null)allMap = new HashMap();
				Object mapShowKey[]= mapShow.keySet().toArray();
				Arrays.sort(mapShowKey);
				int colCount = mapShowKey.length;
				if("H".equals(request.getParameter("txtProductClass"))){
					colCount = 1;
				}
				for (int i=0;i<colCount;i++)
				{
					Map secMap = (Map)allMap.get(mapShowKey[i]);
					if(secMap != null){
						%><td class="list_head" align="center" colspan="<%=secMap.size()%>" nowrap><%=mapShow.get(mapShowKey[i])%></td><%
					}
				}
			%>
		<td class="list_head" width="4%" rowspan="2" nowrap><div align="center">�ܼ�</div></td>
	</tr>
	<tr class="list_head">
			<%
			int maxNum = 0;
			int allColNum = 0;
			for(int i=0;i<colCount;i++){
				Map secMap = (Map)allMap.get(mapShowKey[i]);
			if(secMap != null){
				Object secMapKey[]= secMap.keySet().toArray();
				if(maxNum < secMap.size())maxNum = secMap.size();
				allColNum += secMap.size();
				
					for(int j=0;j<secMap.size();j++){
						%><td class="list_head" align="center"><%=secMap.get(secMapKey[j])%></td><%
					}
				}
			}
				long [][] allValue = new long[colCount][maxNum];
			%>
		<tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12" nowrap><tbl:write name="oneline" property="name" /></td>
<%

	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	HashMap mapValue = dto.getKeyValue();
	long dAllValue = 0;
	for (int i=0;i<colCount;i++)
	{
			HashMap mapValueMulti = (HashMap)mapValue.get(mapShowKey[i]);
			Map secMap = (Map)allMap.get(mapShowKey[i]);
			if(secMap != null){
			Object secMapKey[]= secMap.keySet().toArray();
				for(int j=0;j<secMap.size();j++)
				{
					long dValue = 0;
				  if(mapValueMulti != null && mapValueMulti.get(secMapKey[j])!=null && !"".equals(mapValueMulti.get(secMapKey[j])))
				  {
						dValue = Math.round(Double.parseDouble((String)mapValueMulti.get(secMapKey[j]))*10)/10;
					}
					allValue[i][j] = allValue[i][j]+ dValue;
					dAllValue = dAllValue + dValue;
					%>
					<td align="center" class="t12" nowrap><%=dValue%></td>
					<%
				} 
				}
		}
%>
		<td align="center" class="t12" nowrap><%=dAllValue%></td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12" nowrap>�ܼ�</td>
<%
	long dSumAll = 0;
	for (int i=0;i<colCount;i++)
	{
		Map secMap = (Map)allMap.get(mapShowKey[i]);
		if(secMap != null)
		{
			for(int j=0;j<secMap.size();j++)
			{
				long dValue = Math.round(allValue[i][j]*100)/100;
				dSumAll = dSumAll + dValue;
				%>
				<td align="center" class="t12" nowrap> <%=dValue%> </td>
				<%
			}
		}
	}
%>					
	<td align="center" class="t12" nowrap><%=dSumAll%></td>
  </tr>
    <tr>
    <td colspan="<%=allColNum+2%>" class="list_foot"></td>
  </tr>
</table>	
</div>		
</rs:hasresultset>
</form>  
         

      