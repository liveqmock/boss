<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.util.DistrictSetting"%>
<SCRIPT language="JavaScript">
  function back_submit(){
  	 document.frmPost.action ="menu_export_customer.do";
  	 document.frmPost.submit();
  }
  function frm_submit(){
  	 if (window.confirm("是否提交，生成正式数据！")){
  	 	  document.frmPost.submit();
  	 }
  }

</SCRIPT>
<%
  int batchNo =0;
  HashMap repeatCatvIDMp =new HashMap();
  CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");    
  if (CmdRep!=null){
     ArrayList  list = (ArrayList)CmdRep.getPayload();
     batchNo =((Integer)list.get(0)).intValue();
     repeatCatvIDMp =(HashMap)list.get(1);
  }
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">整转用户生成</td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" method="post" action="export_customer_confirm.do" >
<input type="hidden" name="confirm_post" value="true">
<input type="hidden" name="bathNo" value="<%=batchNo%>">
<input type="hidden" name="func_flag" value="6014">
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<tr>
     <td align="center"><font size="2" color="red">请仔细核对以下信息与Excel中导入的数据是否一致</font></td>
  </tr>
  <tr>
  	 <td>&nbsp;</td>
  </tr>
  <tr>
     <td>
       <table  border="0" cellspacing="0" cellpadding="0" align="center">
       	<%
       	   if (!repeatCatvIDMp.isEmpty()){
       	%>
       	<tr>
		  	   <td colspan="2">-------------------------------------</td>
		    </tr>
       	<%
       	      Iterator catvidItr =repeatCatvIDMp.keySet().iterator();
       	      while (catvidItr.hasNext()){
       	         String catvID =(String)catvidItr.next();
       	         Integer repeatCt =(Integer)repeatCatvIDMp.get(catvID);
       	%>
       	<tr>
		  	   <td width="50%" align="right"><%=catvID%>重复总数： </td>
		  	   <td width="50%" align="left">&nbsp;<%=repeatCt.intValue()%></td>
		  	</tr>
		  	<%
		  	       }
		  	   }
		  	   int allCount = Postern.getExportCustomerCountByBatchNo(batchNo);
		  	   int ct =0;
		  	%> 
		  	<tr>
		  	   <td colspan="2">-------------------------------------</td>
		    </tr>
		  	<%
		  	   Map mp =Postern.getExportCustomerSortDetail(batchNo);
		  	   Iterator itr =mp.keySet().iterator();
		  	   while (itr.hasNext()){
		  	      ct =ct +1;
		  	      String distName =(String)itr.next();
		  	      Integer recordCount =(Integer)mp.get(distName);
		  	%>
		  	<tr>
		  	   <td width="50%" align="right">&nbsp;<%=distName%> ： </td>
		  	   <td width="50%" align="left">&nbsp;<%=recordCount.intValue()%> </td>
		  	</tr>
		  	<%
		  	   }
		  	   if (ct >1){
		  	%>
		  	<tr>
		  	   <td colspan="2">&nbsp;</td>
		    </tr>
		  	<tr>
		  	   <td width="50%" align="right">共处理记录：</td>
		  	   <td width="50%" align="left">&nbsp;<%=allCount%></td>
		  	</tr>
		    <%
		       }
		    %>
		  	</table>
		  </td>
		</tr>
</table>
<br>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1">
	<tr align="center">
	  <td>
			<table border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    
			    <td width="20" ></td>		
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			    <td><input name="Submit" type="button" class="button" value="上一步" onclick="javascript:back_submit()"></td>
			    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			   
			    <td width="20" ></td>		
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			    <td><input name="Submit" type="button" class="button" value="确认" onclick="javascript:frm_submit()"></td>
			    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  
				</tr>
			</table>     
 		</td>
  </tr>
</table>       
 <tbl:generatetoken />
</form>
		
		  	