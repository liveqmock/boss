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
		if (check_Blank(document.frmPost.txtStartDate, true, "开始时间"))
		return ;
		if (check_Blank(document.frmPost.txtEndDate, true, "截止时间"))
		return ;
		if(document.frmPost.txtEndDate.value<document.frmPost.txtStartDate.value)
		{
			alert("开始时间不能大于截止时间!");
			document.frmPost.txtStartDate.focus();
			return ;
		}
		if(document.frmPost.txtEndDate.value==document.frmPost.txtStartDate.value)
		{
			if (document.frmPost.hourEnd.value <document.frmPost.hourBegin.value)
			{
				alert("开始时间点不能大于结束时间点!");
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
String strTitle = "延庆日受理数据查询";
StringBuffer printInfo = new StringBuffer(1024);
String tableTitle = "1;受理类型;0;0;0;0;客户证号;0;1;0;1;老用户证号;0;2;0;2;姓名;0;3;0;3;所在区;0;4;0;4;详细地址;0;5;0;5;缴费截止日期;0;6;0;6;";                 
//查询的table或view的明名字,注意：不能用别名
String tableName = "v_datedowidth";
String pageCode = "date_dowidth_query";
//定义列数
int colNum = 7;
//定义数据在excel中的类型：数字－n;字符－s
	String colTypes = "s;s;s;s;s;s;s"; 
//指定指定不需要返回的列,以分号隔开,注意以分号开始并以分号结束,从1开始,注意:1是行号；如果没有不需要返回字段,给个null或""都可以
String notReturnList = ";1;9;10;";
String [] tabTitle = tableTitle.split(";");
List dataList = new ArrayList();
dataList.add("表头");
int txtTo = WebUtil.StringToInt(request.getParameter("txtTo"));
//当前页码
int pageNumber = WebUtil.StringToInt(request.getParameter("pageNumber"));
if(pageNumber==0)pageNumber=1;

//构造查询条件
StringBuffer queryCondition = new StringBuffer(" where 1=1");
String condition="";


//所有记录数
int allRecordCount = 0;
//当前页记录数
int thisRecordCount = 0;
//共有页数
int allPageCount = 0;

if("query".equals(request.getParameter("txtAct")))
{
	//构造查询条件 begin

	
	 printInfo.append("     受理时间段:").append(request.getParameter("txtStartDate")+"日 "+request.getParameter("hourBegin")+"时 ~ "+
																 request.getParameter("txtEndDate")+"日 "+request.getParameter("hourEnd")+"时");
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
		queryCondition.append(" order by 受理类型");
	condition = queryCondition.toString();
	//构造查询条件 end
	//System.out.println("__condition="+condition);
	dataList = WebQueryUtility.queryResult(tableName,condition,txtTo,pageNumber,notReturnList);
	
	
	//结果数据处理 begin
	
	thisRecordCount = dataList.size()-2;
	allRecordCount = ((Integer)dataList.get(dataList.size()-1)).intValue();
	
	if(allRecordCount == 0)pageNumber=0;
	//页数
	 allPageCount = (allRecordCount%txtTo==0?allRecordCount/txtTo:allRecordCount/txtTo+1);

	 printInfo.append("     总记录数:").append(allRecordCount+"条");
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
    <td class="list_bg2" align="right">开始时间</td>
    <td class="list_bg1"> 
            <input type="text" class="text" size="12" maxlength="10" name="txtStartDate" value="<tbl:writeparam name="txtStartDate"/>"><IMG onclick="calendar(document.frmPost.txtStartDate)" src="img/calendar.gif" style=cursor:hand border="0">
             -- 
             <tbl:select name="hourBegin" set="hourMap1" match="hourBegin"/>时
            
   </td>
   <td class="list_bg2" align="right">截止时间</td>
    <td class="list_bg1"> 
            <input type="text" class="text" size="12" maxlength="10" name="txtEndDate" value="<tbl:writeparam name="txtEndDate"/>"><IMG onclick="calendar(document.frmPost.txtEndDate)" src="img/calendar.gif" style=cursor:hand border="0">
             -- 
            <tbl:select name="hourEnd" set="hourMap2" match="hourEnd"/>时
            
   </td>
  </tr> 
</table>
    
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	   <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
		  </table>
	  </td>
	  <%if(allRecordCount>0){%>
	  <td class="list_bg1">
		   <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="导&nbsp;出" onclick="javascript:out_submit()"></td>
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
                 第<span class="en_8pt"><%=pageNumber%></span>页
                 <span class="en_8pt">/</span>
                 共<span class="en_8pt"><%=allPageCount%></span>页
                 共有<span class="en_8pt"><%=allRecordCount%></span>条记录 &nbsp;&nbsp;            
                 <%
                 if(pageNumber > 1){%>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:first_page()" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_page()" >上一页</a>
                 <%}if(pageNumber != allPageCount){%>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_page()" >下一页</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:last_page()" >末页</a>
                <%}%>
                &nbsp;
                转到
               <input type="text" name="txtPage" class="page_txt">页 
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
			alert(gotopage+"不是合法的数字,请在转到[]页中输入合法的数字!");
			document.frmPost.txtPage.focus();
			return;
		}
		var gopage = Number(gotopage);
		if(gopage<1 || gopage>'<%=allPageCount%>')
		{
			alert(gopage+"超出页码范围!");
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
				//说明有字符不是数字 
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
				//说明有字符不是数字 
				return false; 
			} 
		} 
	}
	//说明是数字 
	return true; 
}

</script>