<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.dto.OrganizationDTO" %>


<%@ page import="com.dtv.oss.dto.BatchJobDTO,com.dtv.oss.dto.BatchJobItemDTO"%>
<%@ page import="com.dtv.oss.dto.wrap.work.Schedule2CP2CCWrap"%>


<script language=javascript>
function query_submit(){
	document.frmPost.submit();
}

function view_schedule_detail(batchid){
	var strURL="schedule_show_detail.do?txtBatchID="+batchid+"&txtActionType=detail&showmodel=customer";
	strURL=strURL+ "&txtCustomerID=" + document.frmPost.txtCustomerID.value;
	self.location.href=strURL;
}

</script>

<form name="frmPost" method="post" action="schedule_show.do">

<input type="hidden" name="txtActionType" value="customer" >
<tbl:hiddenparameters pass="txtCustomerID" />
<input type="hidden" name="txtFrom"  value="1">
<input type="hidden" name="txtTo"    value="10">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户排程任务列表</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
  	


    <td class="list_head">批处理号</td>
    <td class="list_head">PSID</td>
    <td class="list_head">产品名称</td>
    <td class="list_head">产品数量</td>
    <td class="list_head">创建时间</td>
    <td class="list_head">设定执行时间</td>
    <td class="list_head">实际执行时间</td>
    <td class="list_head">任务状态</td>
    <td class="list_head">任务类型</td>
  </tr>

   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    Schedule2CP2CCWrap wrap = (Schedule2CP2CCWrap)pageContext.getAttribute("oneline");
    BatchJobDTO dto = wrap.getBatchJobDTO();
    Collection  lst =wrap.getBatchJobItemDTOList();
    BatchJobItemDTO itemDto=new BatchJobItemDTO();
    Iterator iterst = lst.iterator();
    if (iterst.hasNext()){
        itemDto =(BatchJobItemDTO)iterst.next();
    }
    
    pageContext.setAttribute("BatchJobDTO",dto);
    pageContext.setAttribute("BatchJobItemDTO",itemDto);
    
    
    String strProdName = Postern.getProductNameByPSID(itemDto.getPsId());
    if(strProdName == null)
    	strProdName = "";
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
 
    
    <td align="center"><a href="javascript:view_schedule_detail('<tbl:write name="BatchJobDTO" property="batchId"/>')" class="link12" ><tbl:write name="BatchJobDTO" property="batchId"/></a></td>
    <td align="center"><tbl:write name="BatchJobItemDTO" property="psId"/></td>
    <td align="center"><%=strProdName%></td>
    <td align="center"><tbl:write name="BatchJobDTO" property="totoalRecordNo"/></td>    
    <td align="center"><tbl:writedate name="BatchJobDTO" property="createTime" /><br><tbl:writedate name="BatchJobDTO" property="createTime" onlyTime="true"/></td>
    <td align="center"><tbl:writedate name="BatchJobDTO" property="scheduleTime" /><br><tbl:writedate name="BatchJobDTO" property="scheduleTime" onlyTime="true"/></td>
    <td align="center"><tbl:writedate name="BatchJobDTO" property="executeStartTime" /><br><tbl:writedate name="BatchJobDTO" property="executeStartTime" onlyTime="true"/></td>
    <td align="center"><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="BatchJobDTO:status" /></td>
    <td align="center"><d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="BatchJobDTO:jobType" /></td>   
</tbl:printcsstr>
</lgc:bloop>

  <tr> 
    <td colspan="10" class="list_foot"></td>
  </tr>

</table>
<table  border="0" align="right" cellpadding="0" cellspacing="10">
  <tr>
    <td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    <td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
</rs:notlast>
    <td align="right">跳转到</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>页</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table>
   
</form>  
         

      