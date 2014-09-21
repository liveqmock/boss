<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.util.WebQueryUtility" %>
<%@ page import="com.dtv.oss.web.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>


<script language=javascript>
function query_submit()
{ 	
		if (check_Blank(document.frmPost.txtStartDate, true, "��ʼʱ��"))
		return ;
		if (check_Blank(document.frmPost.txtEndDate, true, "��ֹʱ��"))
		return ;
		if(document.frmPost.txtEndDate.value<document.frmPost.txtStartDate.value)
		{
			alert("��ʼʱ�䲻�ܴ��ڽ�ֹʱ��!");
			document.frmPost.txtStartDate.focus();
			return ;
		}
		if(document.frmPost.txtEndDate.value==document.frmPost.txtStartDate.value)
		{
			if (document.frmPost.hourEnd.value <document.frmPost.hourBegin.value)
			{
				alert("��ʼʱ��㲻�ܴ��ڽ���ʱ���!");
				document.frmPost.hourBegin.focus();
				return ;
			}
		}
		
		document.frmPost.pageNumber.value = 1;
		document.frmPost.txtAct.value="query";
		document.frmPost.txtTo.value = 10;
		document.frmPost.submit();
}


</script>
<%
String strTitle = "�������������ݲ�ѯ";
StringBuffer printInfo = new StringBuffer(1024);
String tableTitle = "1;��������;0;0;0;0;�ͻ�֤��;0;1;0;1;���û�֤��;0;2;0;2;����;0;3;0;3;������;0;4;0;4;��ϸ��ַ;0;5;0;5;�ɷѽ�ֹ����;0;6;0;6;";                 
//��ѯ��table��view��������,ע�⣺�����ñ���
String tableName = "v_datedowidth";
String pageCode = "date_dowidth_query";
//��������
int colNum = 7;
//����������excel�е����ͣ����֣�n;�ַ���s
	String colTypes = "s;s;s;s;s;s;s"; 
//ָ��ָ������Ҫ���ص���,�ԷֺŸ���,ע���Էֺſ�ʼ���ԷֺŽ���,��1��ʼ,ע��:1���кţ����û�в���Ҫ�����ֶ�,����null��""������
String notReturnList = ";1;9;10;";
String [] tabTitle = tableTitle.split(";");
List dataList = new ArrayList();
dataList.add("��ͷ");
int txtTo = WebUtil.StringToInt(request.getParameter("txtTo"));
//��ǰҳ��
int pageNumber = WebUtil.StringToInt(request.getParameter("pageNumber"));
if(pageNumber==0)pageNumber=1;

//�����ѯ����
StringBuffer queryCondition = new StringBuffer(" where 1=1");
String condition="";


//���м�¼��
int allRecordCount = 0;
//��ǰҳ��¼��
int thisRecordCount = 0;
//����ҳ��
int allPageCount = 0;

if("query".equals(request.getParameter("txtAct")))
{
	//�����ѯ���� begin

	
	 printInfo.append("     ����ʱ���:").append(request.getParameter("txtStartDate")+"�� "+request.getParameter("hourBegin")+"ʱ ~ "+
																 request.getParameter("txtEndDate")+"�� "+request.getParameter("hourEnd")+"ʱ");
		if("24".equals(request.getParameter("hourBegin")))
			queryCondition.append(" and createtime >to_date('"+request.getParameter("txtStartDate")+" 23:59:59','YYYY-MM-DD HH24:mi:ss')");
		else
			queryCondition.append(" and createtime >to_date('"+request.getParameter("txtStartDate")+" "+request.getParameter("hourBegin")+":00:00','YYYY-MM-DD HH24:mi:ss')");
		if("24".equals(request.getParameter("hourEnd")))
			queryCondition.append(" and createtime <=to_date('"+request.getParameter("txtEndDate")+" 23:59:59','YYYY-MM-DD HH24:mi:ss')");
		else
			queryCondition.append(" and createtime <=to_date('"+request.getParameter("txtEndDate")+" "+request.getParameter("hourEnd")+":00:00','YYYY-MM-DD HH24:mi:ss')");
	
	int orgid=Postern.getOrgIDByOperatorID(((WebCurrentOperatorData)session.getAttribute(WebKeys.OPERATOR_SESSION_NAME)).GetCurrentOperatorID()+"");
		queryCondition.append(" and createorgid in (select  e.orgid from t_organization e  connect by prior e.orgid =e.parentorgid start with e.orgid ="+orgid+")");
		queryCondition.append(" order by ��������");
	condition = queryCondition.toString();
	//�����ѯ���� end
	//System.out.println("__condition="+condition);
	dataList = WebQueryUtility.queryResult(tableName,condition,txtTo,pageNumber,notReturnList);
	
	
	//������ݴ��� begin
	
	thisRecordCount = dataList.size()-2;
	allRecordCount = ((Integer)dataList.get(dataList.size()-1)).intValue();
	
	if(allRecordCount == 0)pageNumber=0;
	//ҳ��
	 allPageCount = (allRecordCount%txtTo==0?allRecordCount/txtTo:allRecordCount/txtTo+1);

	 printInfo.append("     �ܼ�¼��:").append(allRecordCount+"��");
}
if(request.getParameter("hourEnd") ==null)
{
	//request.setParameter("hourEnd","24");
	pageContext.setAttribute("hourEnd", "24");
}

%>

<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
 
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title"><%=strTitle%></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="<%=pageCode+".do"%>" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <%
    Map mapType1 = new LinkedHashMap();
    for(int i=0;i<=24;i++)
    {
    	mapType1.put(i+"", i+"");
    }
    pageContext.setAttribute("hourMap1", mapType1);
    
    Map mapType2 = new LinkedHashMap();
    for(int i=0;i<=24;i++)
    {
    	mapType2.put(i+"", i+"");
    }
    pageContext.setAttribute("hourMap2", mapType2);
    %>
  <tr>
    <td class="list_bg2" align="right">��ʼʱ��</td>
    <td class="list_bg1"> 
            <input type="text" class="text" size="12" maxlength="10" name="txtStartDate" value="<tbl:writeparam name="txtStartDate"/>"><IMG onclick="calendar(document.frmPost.txtStartDate)" src="img/calendar.gif" style=cursor:hand border="0">
             -- 
             <tbl:select name="hourBegin" set="hourMap1" match="hourBegin"/>ʱ
            
   </td>
   <td class="list_bg2" align="right">��ֹʱ��</td>
    <td class="list_bg1"> 
            <input type="text" class="text" size="12" maxlength="10" name="txtEndDate" value="<tbl:writeparam name="txtEndDate"/>"><IMG onclick="calendar(document.frmPost.txtEndDate)" src="img/calendar.gif" style=cursor:hand border="0">
             -- 
            <tbl:select name="hourEnd" set="hourMap2" match="hourEnd"/>ʱ
            
   </td>
  </tr> 
</table>
    
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	   <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
		  </table>
	  </td>
	  <%if(allRecordCount>0){%>
	  <td class="list_bg1">
		   <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="��&nbsp;��" onclick="javascript:out_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
		   </table>
	  </td>
	  <%}%>
	</tr>
 </table> 
       
      <input type="hidden" name="txtFrom"  value="1">
      <input type="hidden" name="txtTo"  value="10">
      <input type="hidden" name="txtAct"  value="">
      <input type="hidden" name="txtPageCode"  value="<%=pageCode%>">
      
      <input type="hidden" name="pageNumber"  value="<%=pageNumber%>">
    <%if("query".equals(request.getParameter("txtAct"))){
    int c = 0;
    %>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   		<tr>
    		<td><img src="img/mao.gif" width="1" height="1"></td>
  		</tr>
		</table>
  <br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">

        <tr class="list_head">
          <td class="list_head" nowrap><%=tabTitle[(c++)*5+1]%></td>
          <td class="list_head" nowrap><%=tabTitle[(c++)*5+1]%></td> 
          <td class="list_head" nowrap><%=tabTitle[(c++)*5+1]%></td> 
          <td class="list_head" nowrap><%=tabTitle[(c++)*5+1]%></td>        
          <td class="list_head" nowrap><%=tabTitle[(c++)*5+1]%></td>
          <td class="list_head" nowrap><%=tabTitle[(c++)*5+1]%></td>
          <td class="list_head" nowrap><%=tabTitle[(c++)*5+1]%></td>
         
        </tr>
           

        <% 
       
  for(int i=1;i<thisRecordCount+1;i++)
  {
     List eachList = (List)dataList.get(i);
     
%>
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		    			<%for(int j=0;j<colNum;j++){%>
		     			<td align="center"><%=eachList.get(j)%></td>
      		    <%}%>    
    </tr>
<%}%>
  <tr>
    <td colspan="<%=colNum%>" class="list_foot"></td>
  </tr>
     
     <tr>
		
	
              <td align="right" class="t12" colspan="<%=colNum%>" >
                 ��<span class="en_8pt"><%=pageNumber%></span>ҳ
                 <span class="en_8pt">/</span>
                 ��<span class="en_8pt"><%=allPageCount%></span>ҳ
                 ����<span class="en_8pt"><%=allRecordCount%></span>����¼ &nbsp;&nbsp;            
                 <%
                 if(pageNumber > 1){%>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:first_page()" >��ҳ </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_page()" >��һҳ</a>
                 <%}if(pageNumber != allPageCount){%>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_page()" >��һҳ</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:last_page()" >ĩҳ</a>
                <%}%>
                &nbsp;
                ת��
               <input type="text" name="txtPage" class="page_txt">ҳ 
               <a href="javascript:goto_XX()" >
               <img src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>  
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<%}%>
  </td>
  </tr>
  </table>

<script language=javascript>
function out_submit()
{   
		var strTitle = "<%=strTitle%>";
		var printInfo = "<%=printInfo.toString()%>";
		var tableName = "<%=tableName%>";
		var condition = "<%=condition%>";
		var tableTitle = "<%=tableTitle%>";
		var notReturnList = "<%=notReturnList%>";
		var colTypes = "<%=colTypes%>";
		var txtPageCode = document.frmPost.txtPageCode.value;
    window.open('out_to_excel.do?strTitle='+strTitle+'&tableName='+tableName+'&condition='+condition+'&tableTitle='+tableTitle+'&txtPageCode='+txtPageCode+'&printInfo='+printInfo+'&notReturnList='+notReturnList+'&colTypes='+colTypes,'','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');		
}

function next_page()
{ 	
		document.frmPost.txtAct.value="query";
		document.frmPost.txtTo.value = 10;
		document.frmPost.pageNumber.value = '<%=pageNumber+1%>';
		document.frmPost.submit();
}
function first_page()
{ 	
		document.frmPost.txtAct.value="query";
		document.frmPost.txtTo.value = 10;
		document.frmPost.pageNumber.value = 1;
		document.frmPost.submit();
}
function previous_page()
{ 	
		document.frmPost.txtAct.value="query";
		document.frmPost.txtTo.value = 10;
		document.frmPost.pageNumber.value = '<%=pageNumber-1%>';
		document.frmPost.submit();
}
function last_page()
{ 	
		document.frmPost.txtAct.value="query";
		document.frmPost.txtTo.value = 10;
		document.frmPost.pageNumber.value = '<%=allPageCount%>';
		document.frmPost.submit();
}
function goto_XX()
{ 	
		var gotopage = document.frmPost.txtPage.value;
		if(!fucCheckNUM(gotopage))
		{
			alert(gotopage+"���ǺϷ�������,����ת��[]ҳ������Ϸ�������!");
			document.frmPost.txtPage.focus();
			return;
		}
		var gopage = Number(gotopage);
		if(gopage<1 || gopage>'<%=allPageCount%>')
		{
			alert(gopage+"����ҳ�뷶Χ!");
			document.frmPost.txtPage.focus();
			return;
		}
		document.frmPost.txtAct.value="query";
		document.frmPost.txtTo.value = 10;
		document.frmPost.pageNumber.value = gopage;
		document.frmPost.submit();
}

function fucCheckNUM(NUM) 
{ 
	var i,j,strTemp; 
	strTemp="0123456789"; 
	if ( NUM.length== 0) 
	{
		return false;
	}
	if(NUM.charAt(0)=='-')
	{
		for (i=1;i<NUM.length;i++) 
		{ 
			j=strTemp.indexOf(NUM.charAt(i)); 
			if (j==-1) 
			{ 
				//˵�����ַ��������� 
				return false; 
			} 
		} 
	}
	else
	{
		for (i=0;i<NUM.length;i++) 
		{ 
			j=strTemp.indexOf(NUM.charAt(i)); 
			if (j==-1) 
			{ 
				//˵�����ַ��������� 
				return false; 
			} 
		} 
	}
	//˵�������� 
	return true; 
}

</script>