<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO,com.dtv.oss.dto.OperatorDTO" %>
<%@ page import="com.dtv.oss.web.util.CurrentOperator,com.dtv.oss.web.util.LogonWebCurrentOperator" %>

<script language=javascript>
<!--
function query_submit(){
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
	if (document.frmPost.txtCreateTime3.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime3, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime4.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime4, true, "��ʼ����")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	if(!compareDate(document.frmPost.txtCreateTime3,document.frmPost.txtCreateTime4,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
		
	if (check_Blank(document.frmPost.selSortType, true, "��Ʒ��״̬"))
		return false;	
	if (check_Blank(document.frmPost.txtDistrictID, true, "����"))
		return false;
	return true;
}

function ChangeOpenSourceType()
{
     document.FrameUS.submit_update_select('osttoid', document.frmPost.txtOpenSourceType.value, 'txtOpenSourceID', '');

}
//-->
</script>

<div id="updselwaitlayer" style="position:absolute; left:700px; top:104px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr><td width="100%"><div align="center"><font size="2">���ڻ�ȡ���ݡ�����</font></td></tr>
    </table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0" ></iframe>

<form name="frmPost" method="post" action="saled_product_package_stat.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">��Ʒ������ͳ��</td>
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
    <td class="list_bg2"><div align="right">��Ʒ��״̬*</div></td>
    <%
    Map mapType2 = new HashMap();
    mapType2.put("T", "��ʷ��Ʒ��");
    mapType2.put("N", "��ǰ��Ч��Ʒ��");
    pageContext.setAttribute("SortTypeList", mapType2);
    %>        
    <td class="list_bg1"><tbl:select name="selSortType" set="SortTypeList" match="selSortType"  width="23" /> </td>
    <td class="list_bg2"><div align="right">�û�����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustType" mapName="SET_C_CUSTOMERTYPE" match="txtCustType" width="23" /> </td>
  </tr>
    
  <tr>
    <td class="list_bg2"><div align="right">����*</div></td>
    <td class="list_bg1">
    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
   
    </td>
    <td class="list_bg2"></td>
    <td class="list_bg1"></td>
  </tr>
    
  <tr>
    <td class="list_bg2"><div align="right">����ʱ��</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right">���ʱ��</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime3)" onblur="lostFocus(this)" name="txtCreateTime3" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime3" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime3,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime4)" onblur="lostFocus(this)" name="txtCreateTime4" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime4" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime4,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
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
<rs:hasresultset>
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head"><div align="center">��֯/����</div></td>
<%
	Map mapShow=null;
	//����ʷ��Ʒ��
	if("T".equals(request.getParameter("selSortType"))){
		mapShow=Postern.getHistoryAllProductPackageIDAndName();
        }
        //��ǰ��Ч��Ʒ��
        else if("N".equals(request.getParameter("selSortType")))
        	mapShow=Postern.getAllProductPackageIDAndName();

        if(mapShow==null)
        	mapShow=new HashMap();
        	
	long allValue[];
	int colCount = 0;
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new long[colCount];
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
%>
			<td class="list_head"><div align="center"><%=mapShow.get(strKey)%></div></td>
<%
		}
	}
	else
		allValue = new long[1];
%>
			<td class="list_head"><div align="center">�ܼ�</div></td>
		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12"><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	HashMap mapValue = dto.getKeyValue();
	long dAllValue = 0;
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
				long dValue = 0;
				if((mapValue.get(strKey) != null) && (mapValue.get(strKey) != ""))
				{
					dValue = Math.round(Double.parseDouble((String)mapValue.get(strKey))*10)/10;
				}
				allValue[i] = allValue[i]+ dValue;
				dAllValue = dAllValue + dValue;
%>
		<td align="center" class="t12"><%=dValue%></td>
<%
		}
	}
%>
		<td align="center" class="t12"><%=dAllValue%></td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12">�ܼ�</td>
<%
	long dSumAll = 0;
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<mapShow.size();i++)
		{
			long dValue = Math.round(allValue[i]*100)/100;
			dSumAll = dSumAll + dValue;
%>
		<td align="center" class="t12"> <%=dValue%> </td>
<%
		}
	}
%>					
	<td align="center" class="t12"> <%=dSumAll%> </td>
  </tr>
    <tr>
    <td colspan="<%=colCount+2%>" class="list_foot"></td>
  </tr>
</table>		
</rs:hasresultset>
</form>  
         

      