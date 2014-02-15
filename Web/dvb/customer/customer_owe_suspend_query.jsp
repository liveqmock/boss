<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.util.Postern"%>

<script language=javascript>
function check_form(){
	 if (check_Blank(document.frmPost.txtDistrictID, true, "所属区域")){
	    	return false;
	 }
	 if (document.frmPost.txtPauseStartDate.value != ''){
		  if (!check_TenDate(document.frmPost.txtPauseStartDate, true, "开始日期")){
		    	return false;
		  }
	 }
	 if (document.frmPost.txtPauseEndDate != ''){
		  if (!check_TenDate(document.frmPost.txtPauseEndDate, true, "结束日期")){
			   return false;
		  }
	  }
	  if(!compareDate(document.frmPost.txtPauseStartDate,document.frmPost.txtPauseEndDate,"结束日期必须大于等于开始日期")){
				return false;
	  }
	  if (document.frmPost.txtAcctBalance1.value !=''){
	  	 if (!check_Money(document.frmPost.txtAcctBalance1,true,"查询起始金额")){
	  	 	  return false;
	  	 }
	  }
	  if (document.frmPost.txtAcctBalance2.value !=''){
	  	 if (!check_Money(document.frmPost.txtAcctBalance2,true,"查询结束金额")){
	  	 	  return false;
	  	 }
	  }
	  
	  if (document.frmPost.txtAcctBalance1.value !='' && document.frmPost.txtAcctBalance2.value !=''){
	     if (document.frmPost.txtAcctBalance1.value*1 > document.frmPost.txtAcctBalance2.value*1){
	        alert("查询起始金额大于查询结束金额");
	        return false;
	     }	
	  }
	  
	  if (document.frmPost.txtServiceAcctStatus.value !='' || document.frmPost.txtProductId.value !=''){
	  	if (document.frmPost.txtAcctBalance1.value =='' && document.frmPost.txtAcctBalance2.value ==''){
	  		 alert("请填写帐户总金额的范围！");
	  		 return false;
	  	}
	  }
	  return true;
}

function query_submit(){
    document.frmPost.txtAct.value="query";
    if (check_form()){
	   	 document.getElementById('search').disabled=true;
	   	 if (document.getElementById('export') !=null){
	   	 	  document.getElementById('export').disabled=true;
	   	 }
    	 document.frmPost.submit();
    }
}

</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">停机-缴费情况客户查询</td>
  </tr>
</table>
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="customer_owe_suspend_query.do" >
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="10">
   <input type="hidden" name="txtAct" value="">
   <table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr>
        <td class="list_bg2" width=17% align="right">客户姓名</td>
        <td class="list_bg1" width=33% align="left"><input name="txtName" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtName" />"></td>
        <td class="list_bg2" width=17% align="right">客户类型</td>
        <td class="list_bg1" width=33% align="left"><d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="20" /></td>
      </tr>
      <tr>
        <td class="list_bg2" align="right"  width="17%" >所属区域*</td>
        <td class="list_bg1" align="left"  width="33%">
    	    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	        <input type="text" name="txtCountyDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	        <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
        </td>
        <td class="list_bg2" align="right"  width="17%" >安装地址</td>
        <td class="list_bg1" align="left"  width="33%"><input name="txtDetailAddress" type="text" class="text" maxlength="100" size="22"  value="<tbl:writeparam name="txtDetailAddress" />"></td>
      </tr>
      <tr>
        <td class="list_bg2" align="right">暂停日期</td>
        <td class="list_bg1" align="left" ><d:datetime name="txtPauseStartDate" size="10" myClass="text" match="txtPauseStartDate"  onclick="calendar(document.frmPost.txtPauseStartDate)" picURL="img/calendar.gif" style="cursor:hand" />
              -
             <d:datetime name="txtPauseEndDate" size="10" myClass="text" match="txtPauseEndDate"  onclick="calendar(document.frmPost.txtPauseEndDate)" picURL="img/calendar.gif" style="cursor:hand" />
	      </td>
	      <td class="list_bg2"  align="right">停机原因</td>
        <td class="list_bg1">
	      <%
           Map   csiReasonMap=new HashMap();
           csiReasonMap.put("主动暂停","主动暂停");
           csiReasonMap.put("强制暂停","强制暂停");
           pageContext.setAttribute("serviceaccount_pause_reason", csiReasonMap);
	      %>           
        <tbl:select name="txtCsiCreateReason" set="serviceaccount_pause_reason" match="txtCsiCreateReason" width="20" />  
        </td>
	    </tr>
	    <tr>
		    <td class="list_bg2" width=17% align="right">收费终端状态</td>
        <%
          Map mapStatus = new LinkedHashMap();
          mapStatus.put("N", "正常");
          mapStatus.put("H", "暂停");
          mapStatus.put("I", "初始");
          pageContext.setAttribute("mapStatus", mapStatus);
        %>
        <td class="list_bg1" width=33% align="left">
           <tbl:select name="txtServiceAcctStatus" set="mapStatus" match="txtServiceAcctStatus" width="20"/>
        </td>
	      <td class="list_bg2" align="center" colspan="2">
        	<input name="txtAcctBalance1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtAcctBalance1" />">
        	 <= 账户总金额 <=
          <input name="txtAcctBalance2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtAcctBalance2" />">
	      </td>
	    </tr>
	    <tr>
	    	<td class="list_bg2" width="17%" align="right">产品名称</td>
	    	<%
	    	  Map productMap=Postern.getAllSellProductIDAndName("6","S");
	    	  pageContext.setAttribute("productMap",productMap);
	    	  pageContext.setAttribute("AllMOPList", Postern.getOpeningMop());
	    	%>
	    	<td class="list_bg1" width=33% align="left">
          <tbl:select name="txtProductId" set="productMap" match="txtProductId" width="20"/>
        </td>
        <td class="list_bg2" width="17%" align="right">付费类型</td>
        <td class="list_bg1" width=33% align="left">
          <tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" />
        </td>
	    </tr>
	    <tr>
	    	<td class="list_bg2" width="17%" align="right">查询方式</td>
        <%
          Map queryStyle =new HashMap();
          queryStyle.put("C","按 客户姓名 客户证号");
          pageContext.setAttribute("queryStyle",queryStyle);
          String txtqueryStyle =(request.getParameter("txtqueryStyle")==null) ? "1" :request.getParameter("txtqueryStyle");
        %>
        <td class="list_bg1" width="33%" align="left">
        	<tbl:select name="txtqueryStyle" set="queryStyle" match="<%=txtqueryStyle%>" width="20" />
        </td>
        <td class="list_bg2" width="17%" align="right">&nbsp;</td>
        <td class="list_bg1" width="33%" align="left">&nbsp;</td>
   </table>  
   <table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
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
            <td align="center"><input name="export" type="button" class="button" onClick="javascript:out_submit()"  value="导 出"></td>
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
    <table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">        
         <td class="list_head">客户证号</td>
         <td class="list_head">客户姓名</td>
         <td class="list_head">业务帐户</td>
         <td class="list_head">客户类型</td>
         <td class="list_head">详细地址</td>
         <td class="list_head">停机原因</td>
         <td class="list_head">联系电话</td>
         <td class="list_head">帐户总金额</td>
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
          <% 
             resultIter.next(); //客户备注不在页面上显示
          %>
         </tbl:printcsstr>
       <%
           }    
       %>
        </lgc:bloop>
    <tr>
      <td colspan="8" class="list_foot"></td>
    </tr>
    <tr class="trline" >
       <td align="right" class="t12" colspan="7" >
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
<%
  
%>

<script language=javascript>
   function out_submit(){  
   	  document.getElementById("search").disabled=true;
      document.getElementById("export").disabled=true; 
	  	var tempValue ="";
	  	var txtName =trim(document.frmPost.txtName.value);
	  	if (txtName !=null) tempValue ="&txtName="+txtName;
	  	var txtCustomerType =trim(document.frmPost.txtCustomerType.value);
	  	if (txtCustomerType !=null) tempValue=tempValue+"&txtCustomerType="+txtCustomerType;
	  	var txtDistrictID =document.frmPost.txtDistrictID.value;
	  	tempValue=tempValue+"&txtDistrictID="+txtDistrictID;
	  	var txtCountyDesc =document.frmPost.txtCountyDesc.value;
	  	tempValue=tempValue+"&txtCountyDesc="+txtCountyDesc;
	  	var txtDetailAddress =trim(document.frmPost.txtDetailAddress.value);
	  	if (txtDetailAddress !=null) tempValue=tempValue+"&txtDetailAddress="+txtDetailAddress;
	  	var txtPauseStartDate =trim(document.frmPost.txtPauseStartDate.value);
	  	if (txtPauseStartDate !=null) tempValue=tempValue+"&txtPauseStartDate="+txtPauseStartDate;
	  	var txtPauseEndDate =trim(document.frmPost.txtPauseEndDate.value);
	  	if (txtPauseEndDate !=null) tempValue=tempValue+"&txtPauseEndDate="+txtPauseEndDate;
	  	var txtCsiCreateReason =trim(document.frmPost.txtCsiCreateReason.value);
	  	if (txtCsiCreateReason !=null) tempValue=tempValue+"&txtCsiCreateReason="+txtCsiCreateReason;
	  	var txtServiceAcctStatus =trim(document.frmPost.txtServiceAcctStatus.value);
	  	if (txtServiceAcctStatus !=null) tempValue=tempValue+"&txtServiceAcctStatus="+txtServiceAcctStatus;
	  	var txtAcctBalance1 =trim(document.frmPost.txtAcctBalance1.value);
	  	if (txtAcctBalance1 !=null) tempValue=tempValue+"&txtAcctBalance1="+txtAcctBalance1;
	  	var txtAcctBalance2 =trim(document.frmPost.txtAcctBalance2.value);
	  	if (txtAcctBalance2 !=null) tempValue=tempValue+"&txtAcctBalance2="+txtAcctBalance2;
	  	var txtqueryStyle =document.frmPost.txtqueryStyle.value;
	  	if (txtqueryStyle !=null) tempValue=tempValue+"&txtqueryStyle="+txtqueryStyle;
	  	var txtProductId =document.frmPost.txtProductId.value;
	  	if (txtProductId !=null) tempValue=tempValue+"&txtProductId="+txtProductId;
	  	var txtMopID =document.frmPost.txtMopID.value;
	  	if (txtMopID !=null) tempValue =tempValue+"&txtMopID="+txtMopID;
	  	
      newopen=window.open('out_to_owe_suspend_excel.do?pageCode=out_to_owe_suspend_excel&txtAct=out'+tempValue,'',
                 'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');		 
  
      timer = setInterval('wen()',10);    
   }
   function wen(){
      if(newopen.closed){ 
         document.getElementById("search").disabled=''; 
         document.getElementById("export").disabled='';
         clearInterval(timer); 
      }else{
      	if (newopen.open){
      	   if (newopen.document.body !=null){ 
      	   	  //  newopen.document.body.innerHTML ="<script>";
      	  	   var error_excel_flag = newopen.document.getElementById("error_excel_flag");
      	  	   if (error_excel_flag ==null || error_excel_flag.value !='true'){
      	  	   	  newopen.opener =null;
      	  	   	  newopen.close();   
      	  	   }   	                          
      	   //	   alert(newopen.document.body.innerHTML);
              
          }
        }
      }  
    }
   
</script>