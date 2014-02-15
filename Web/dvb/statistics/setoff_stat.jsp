<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>

<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO"%>

<%!
	public Map getValidateAcctItemTypeByReponse(Collection col){
		Map map=new HashMap();
		
		if(col!=null && col.size()>0){
			Iterator itCol=col.iterator();
			while(itCol.hasNext()){
				CommonStatDTO dto=(CommonStatDTO)itCol.next();
				Map valueKey=dto.getKeyValue();
				Iterator it=valueKey.keySet().iterator();
				while(it.hasNext()){
					String str=(String)it.next();
					if(!map.containsKey(str))
						map.put(str,Postern.getAcctItemTypeByAcctItemTypeID(str));
				}	
			}
		}
		return map;
	}
%>

<script language=javascript>
function query_submit(){
	if(checkData())
		document.frmPost.submit();
}

function checkData(){
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
 	
 	if(document.frmPost.txtPlusReferType.value==''){
 		alert("��ѡ��ʵ�չ�������");
 		document.frmPost.txtPlusReferType.focus();
 		return false;
 	}
 	 if(document.frmPost.txtOrgID.value!='' && document.frmPost.txtDistrictID.value!=''){
 		alert("��֯��������ͬʱ��ѡ��");
 		return false;
 	}
 	
	return true;
}
</script>

<form name="frmPost" method="post" action="setoff_stat.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�������ͳ��</td>
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
    
    <td class="list_bg2"><div align="right">ʵ�չ�������*</div></td>
    <td class="list_bg1"><d:selcmn name="txtPlusReferType" mapName="SET_F_SETOFFREFERTYPE"  match="txtPlusReferType"  width="23" /> </td>
    <td class="list_bg2"><div align="right">����ʱ��</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">��֯</div></td>
    <td class="list_bg1"><d:selorg  name="txtOrgID" set="SET_S_ORGANIZATION" match="txtOrgID" width="23" /> </td>
    <td class="list_bg2"><div align="right">����</div></td>
    <td class="list_bg1">
    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
   
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


<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">������Ŀ����</td>
    
    <%	
    	Map mopMap = null;
    	if("P".equals(request.getParameter("txtPlusReferType")))
    		mopMap = Postern.getAllMOPOnlyIdAndName();
    	else if("D".equals(request.getParameter("txtPlusReferType")))
    		mopMap=Postern.getHashKeyValueByName("SET_F_PREPAYMENTTYPE");
    	else if("F".equals(request.getParameter("txtPlusReferType"))){
    		QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)request.getAttribute("ResponseQueryResult");
		Collection collection = (Collection) RepCmd.getPayload();
		mopMap=getValidateAcctItemTypeByReponse(collection);
    	}
    		
    	double allValue[];
    	if (!mopMap.isEmpty())
	{
		Iterator itKey = mopMap.keySet().iterator();
		allValue = new double[mopMap.size()];
		for (int i=0,m=mopMap.size();i<m;i++)
		{
				String strKey = (String)itKey.next();
	%>
		<td class="list_head"><div align="center"><%=mopMap.get(strKey)%></div></td>
	<%
		}
	}
	else
		allValue = new double[1];    	
    %>
    <td class="list_head">�ܼ�</td>
  </tr>

   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    
    <td align="center" class="t12"><tbl:write name="oneline" property="name" /></td>
    <%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	HashMap mapValue = dto.getKeyValue();
	double dAllValue = 0;
	if (!mopMap.isEmpty())
	{
		Iterator itKey = mopMap.keySet().iterator();
		//����ͳ��
		for (int i=0,m=mopMap.size();i<m;i++)
		{
				String strKey = (String)itKey.next();
				double dValue = 0;
				if((mapValue.get(strKey) != null) && (mapValue.get(strKey) != ""))
				{
					dValue = Double.parseDouble((String)mapValue.get(strKey));
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

  <tr>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    	<td>�ܼ�</td>
    	<%
		double dSumAll = 0;
		if (!mopMap.isEmpty())
		{
			Iterator itKey = mopMap.keySet().iterator();
			for (int i=0;i<mopMap.size();i++)
			{
				double dValue = Math.round(allValue[i]*100)/100.0;
				dSumAll = dSumAll + dValue;
	%>
		<td align="center" class="t12"> <%=dValue%> </td>
	<%
			}
		}
	%>					
	<td align="center" class="t12"> <%=dSumAll%> </td>
    	
    </tbl:printcsstr>
  </tr>
  
  <tr>
    <td colspan="11" class="list_foot"></td>
  </tr>

</table>

 </rs:hasresultset>                 
<BR>

</form>  
         

      