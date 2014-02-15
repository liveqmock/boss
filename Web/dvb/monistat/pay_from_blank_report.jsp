<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.dtv.oss.util.*" %>

<script language=javascript>
	 function check_form(){
	 	  //所属区域
	 	  if (document.frmPost.txtDistrictID.value ==''){
	 	     alert("所属区域选择不能为空!");
	 	     return false;
	 	  }
	 	  if (document.frmPost.txtStatBeginTime.value ==''){
	 	  	 alert("银行预缴创建时间开始日期不能为空！");
	 	  	 return false;
	 	  }
	 	  if (document.frmPost.txtStatEndTime.value ==''){
	 	  	 alert("银行预缴创建时间结束日期不能为空！");
	 	  	 return false;
	 	  }
	 	  
	    //统计时间段
      if (!check_TenDate(document.frmPost.txtStatBeginTime, true, "银行预缴创建时间开始日期")){
			    return false;
		  }
 
      if (!check_TenDate(document.frmPost.txtStatEndTime, true, "银行预缴创建时间结束日期")){
			    return false;
		  }
		  
	    if (!compareDate(document.frmPost.txtStatBeginTime,document.frmPost.txtStatEndTime,"银行预缴创建结束日期必须大于等于银行预缴创建开始日期")){	
		       return false;
	    }
	    return true;
	 }
	 function query_submit(){
	 	  document.frmPost.txtAct.value="query";
      if (check_form()) document.frmPost.submit();
	 }
	 function out_submit(){ 
	 	  if (check_form()){
	 	  	 var tempValue ="";
	 	  	 var txtDistrictID =document.frmPost.txtDistrictID.value;
	  	   tempValue ="&txtDistrictID="+txtDistrictID;
	  	   var txtDetailAddress =document.frmPost.txtDetailAddress.value;
	  	   if(txtDetailAddress !=''){
	          tempValue =tempValue+"&txtDetailAddress="+txtDetailAddress;
	       }
	       var txtCustomerID =document.frmPost.txtCustomerID.value;
	  	   if(txtCustomerID !=''){
	          tempValue =tempValue+"&txtCustomerID="+txtCustomerID;
	       }
	       var txtName =document.frmPost.txtName.value;
	  	   if(txtName !=''){
	          tempValue =tempValue+"&txtName="+txtName;
	       }
	       
	       var txtCustomerType =document.frmPost.txtCustomerType.value;
	  	   if(txtCustomerType !=''){
	          tempValue =tempValue+"&txtCustomerType="+txtCustomerType;
	       }
	       
	       var txtSaStatus =document.frmPost.txtSaStatus.value;
	  	   if(txtSaStatus !=''){
	          tempValue =tempValue+"&txtSaStatus="+txtSaStatus;
	       } 
	       
	       var txtStatBeginTime =document.frmPost.txtStatBeginTime.value;
	       tempValue =tempValue+"&txtStatBeginTime="+txtStatBeginTime;
	       var txtStatEndTime =document.frmPost.txtStatEndTime.value;
	       tempValue =tempValue+"&txtStatEndTime="+txtStatEndTime;
	       
         window.open('pay_from_blank_excel.do?pageCode=payFromblank_excel&txtAct=out'+tempValue,'',
                 'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');		

	 	  }
	 }
	 function clear_select(id,name){
	    var idobj=eval("document.frmPost."+id);
	    idobj.value="";
	    var nameobj =eval("document.frmPost."+name);
	    nameobj.value="";
   }
	 
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">银行代缴费统计</td>
  </tr>
</table>
<table width="1000"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="pay_from_blank_report.do" >
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="500">
   <input type="hidden" name="txtAct" value="">
   <table width="1000"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   	  <tr>
   	 	<td class="list_bg2" align="right" width="17%">客户所属区域*</td>
      <td class="list_bg1" align="left"  width="33%">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
      </td>
      <td class="list_bg2" align="right" width="17%">安装地址</td>
      <td class="list_bg1" align="left" width="33%">
      	<input name="txtDetailAddress" type="text" class="text" maxlength="100" size="22"  value="<tbl:writeparam name="txtDetailAddress" />">
      </td>
     </tr>
     <tr>
     	<td class="list_bg2" width=17% align="right">客户证号</td>
      <td class="list_bg1" width=33% align="left">
      	<input name="txtCustomerID" type="text" class="text" maxlength="9" size="22" value="<tbl:writeparam name="txtCustomerID" />">
	    </td>
      <td class="list_bg2" width=17% align="right">客户姓名</td>
      <td class="list_bg1" width=33% align="left">
      	<input name="txtName" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtName" />">
      </td>
     </tr>
     <tr>
      <td class="list_bg2" width=17% align="right">客户类型</td>
      <td class="list_bg1" width=33% align="left">
       	<d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="20" />
      </td>
      <td class="list_bg2" width=17% align="right">业务账户状态</td>
      <%
        Map saStatusList =new LinkedHashMap();
        saStatusList.put("N", "正常");
        saStatusList.put("H", "暂停");
        saStatusList.put("I", "初始");
        saStatusList.put("C", "取消");
        pageContext.setAttribute("SastatusList",saStatusList);
      %>
   	  <td class="list_bg1" width=33% align="left">
  		 <tbl:select name="txtSaStatus" set="SastatusList" match="txtSaStatus" width="20" />   
  	  </td>
     </tr>
     <tr>
      <td class="list_bg2" align="right" width="17%">银行预缴创建时间*</td>
      <td class="list_bg1" align="left" width="83%" colspan="3">
    	   <input name="txtStatBeginTime" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtStatBeginTime" />">
    	   <IMG onclick="calendar(document.frmPost.txtStatBeginTime)" src="img/calendar.gif" style=cursor:hand border="0">
    	   ---
    	   <input name="txtStatEndTime" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtStatEndTime" />">
    	   <IMG onclick="calendar(document.frmPost.txtStatEndTime)" src="img/calendar.gif" style=cursor:hand border="0">
      </td> 
     </tr>
   </table> 
   <table width="1000"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	   <tr align="center">
	      <td class="list_bg1">
	      <table  border="0" cellspacing="0" cellpadding="0">
		      <tr>
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			      <td><input name="search" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
		  	    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  <%
			  Collection returnList =null;
			  QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
		    if (RepCmd !=null) returnList = (Collection) RepCmd.getPayload();
			     if (returnList !=null && !returnList.isEmpty()){
			%>
			      <td width="20" ></td>
			      <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
            <td align="center"><input name="Submit2" type="button" class="button" onClick="javascript:out_submit()"  value="导 出"></td>
            <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      <%
         } else {
       %>
            <td colspan="4">&nbsp;</td>
       <%
         }
       %>
		      </tr>
  	    </table>
        </td>
    </tr>
    </table>
    <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	     <tr>
	       <td><img src="img/mao.gif" width="1" height="1"></td>
	     </tr>
    </table>
    <rs:hasresultset>
    <br>   
    <table width="1000"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">   
         	<td class="list_head" >客户证号 </td>
          <td class="list_head" >客户姓名 </td> 
          <td class="list_head" >客户类型 </td>  
          <td class="list_head" >区域 </td>      
          <td class="list_head" >详细地址 </td>
          <td class="list_head" >电话 </td>
          <td class="list_head" >服务截止期 </td>
          <td class="list_head" >业务账户状态 </td>
          <td class="list_head" >付费银行 </td>
       </tr>
        <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
       	<%
       	  Collection resultCols = (Collection)pageContext.getAttribute("oneline");
       	  Iterator resultIter =resultCols.iterator();
       	  while (resultIter.hasNext()) {
        %>
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
          <td align="center" ><%=resultIter.next()%></td>  
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>	  
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
         </tbl:printcsstr>
       <%
           }    
       %>
        </lgc:bloop>
       <tr>
         <td colspan="9" class="list_foot"></td>
       </tr>
      <tr class="trline" >
        <td align="right" class="t12" colspan="9" >
          第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
          <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
          共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
          <rs:notfirst>
          <img src="img/dot_top.gif" width="17" height="11">
          <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
          <img src="img/dot_pre.gif" width="6" height="11">
          <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
          </rs:notfirst>
          
          <rs:notlast>
          &nbsp;
          <img src="img/dot_next.gif" width="6" height="11">
          <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
          <img src="img/dot_end.gif" width="17" height="11">
          <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
          </rs:notlast>
          &nbsp;
          转到
          <input type="text" name="txtPage" class="page_txt">页 
          <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
               <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
          </a>
        </td>
      </tr>    
      </table> 
    </rs:hasresultset>
</form>   
