<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.web.util.CommonKeys" %>
 <%
 //这个配置用来配置是否同时启用单独的模拟电视业务
 String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_USER_SINGLE_SIMULATION_TV_MANAGE");
 
 %>

<script language=javascript>
  
function query_submit(){ 
    document.frmPost.submit();
}

function view_detail_click(strId,serviceid){
<%
     if(systemSettingValue != null &&("Y").equals(systemSettingValue)){
%> 
	if(serviceid==6){
		self.location.href="catv_service_account_query.do?txtServiceAccountID="+strId+"&txtOpenTreeServiceAccountID="+strId;
	}else{
		self.location.href="service_account_query_result_by_sa.do?txtServiceAccountID="+strId+"&txtOpenTreeServiceAccountID="+strId;
	}
	<%} else {%>
	self.location.href="service_account_query_result_by_sa.do?txtServiceAccountID="+strId+"&txtOpenTreeServiceAccountID="+strId;
	<%}%>
}

function ChangeDeviceClass(){
    document.FrameUS.submit_update_select('devclasstomodel', document.frmPost.txtDeviceClass.value, 'txtDeviceModel', '');
}

function hasSelect(optype){
    var flag =false;
    var errorServiceAcctIds ='';
    if (document.frmPost.Index!=null){
    	if(document.frmPost.Index.length!=null){
       		for (i=0 ;i< document.frmPost.Index.length ;i++){
           		if (document.frmPost.Index[i].checked){
              		flag =true;
              		if (optype ==0){
              			  if (document.frmPost.serviceAcctStatus[i].value =='S' || document.frmPost.serviceAcctStatus[i].value =='H'){
              			  	  errorServiceAcctIds =errorServiceAcctIds +document.frmPost.Index[i].value+",";
              			  }
              		} else{
              			  if (document.frmPost.serviceAcctStatus[i].value =='N'){
              			  	 errorServiceAcctIds =errorServiceAcctIds +document.frmPost.Index[i].value+",";
              			  }
              		}  		
              }
           	}
       }else{
       		if (document.frmPost.Index.checked){
              flag =true;
              if (optype ==0){
              	  if (document.frmPost.serviceAcctStatus.value =='S' || document.frmPost.serviceAcctStatus.value =='H'){
              	  	  errorServiceAcctIds =errorServiceAcctIds +document.frmPost.Index.value+",";
              	  }
              }else{
              	  if (document.frmPost.serviceAcctStatus.value =='N'){
              			  errorServiceAcctIds =errorServiceAcctIds +document.frmPost.Index.value+",";
              		}
              }
          }
       }
    }
    if(!flag){
    	alert("没有选择记录，无法批量操作！")
    	return false;
    }
    if (optype ==0){
       if (errorServiceAcctIds !=''){
    	     alert(errorServiceAcctIds+"等业务帐户的状态非正常,不能做暂停请重新选择！");
    	     return false;
       }
    }else{
    	 if (errorServiceAcctIds !=''){
    	     alert(errorServiceAcctIds+"等业务帐户的状态非暂停,不能做恢复请重新选择！");
    	     return false;
       }
    }
    return true;
 }
 
function pause_submit(){
	 if(!hasSelect(0)){
    	return;
   }
   document.frmPost.txtActionType.value='<%=CommonKeys.BATCH_SERVICE_ACCOUNT_PAUSE%>';
	 document.frmPost.action="batch_pause_resume_serviceAccount.do";
	 document.frmPost.submit();
}
function resume_submit(){
	 if(!hasSelect(1)){
    	return;
   }
	 document.frmPost.txtActionType.value='<%=CommonKeys.BATCH_SERVICE_ACCOUNT_RESUME%>';
	 document.frmPost.action="batch_pause_resume_serviceAccount.do";
	 document.frmPost.submit();
}
</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr> 
          <td width="100%"><div align="center">     
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>  
</div>

 
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">业务账户查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
  
<form name="frmPost" action="dev_query_result_ss.do" method="post" > 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
       
    <tr>  
      <td class="list_bg2" align="right">设备类型</td>
      <td class="list_bg1">
<%
    
    int custId= WebUtil.StringToInt(request.getParameter("txtCustomerID"));
	  CustomerDTO custDTO = Postern.getCustomerByID(custId);
    Map mapDeviceClasses = Postern.getAllDeviceClasses();
    pageContext.setAttribute("AllDeviceClasses",mapDeviceClasses);
    String[] saId_indexs =request.getParameterValues("Index");
    
%>
        <tbl:select name="txtDeviceClass" set="AllDeviceClasses" match="txtDeviceClass" width="23" onchange="ChangeDeviceClass()" />
      </td>
      <td class="list_bg2" align="right">设备型号</td>
      <td class="list_bg1">
        <d:seldevicemodel name="txtDeviceModel" deviceClass="txtDeviceClass"  match="txtDeviceModel" width="23"/>
     </td>
    </tr>  
    <tr>  
       <td class="list_bg2" align="right">设备序列号</td>
      <td colspan="" class="list_bg1" align="left">
        <input type="text" name="txtSerialNoBegin" maxlength="25" size="25" value="<tbl:writeparam name="txtSerialNoBegin" />" >
     </td>
      <td class="list_bg2" align="right">服务号码</td>
      <td class="list_bg1">
        <input type="text" name="txtServiceCode" maxlength="25" size="25" value="<tbl:writeparam name="txtServiceCode" />" >
     </td>   
    </tr> 
    
<%

    Map mapProductIDAndName = Postern.getAllProductIDAndName();
    pageContext.setAttribute("AllProductIDAndName",mapProductIDAndName);
    
%>
    <tr>  
       <td class="list_bg2" align="right">产品</td>
       <td colspan="" class="list_bg1" align="left">
      	 <tbl:select name="txtProductID" set="AllProductIDAndName" match="txtProductID" width="23" />
       </td>   
     
       <td class="list_bg2" align="right">状态</td>
       <td class="list_bg1">
<%
    Map statusMap = new LinkedHashMap();
    statusMap.put("N","正常");
    statusMap.put("H;S","暂停");
    statusMap.put("I","初始");
    pageContext.setAttribute("statusMap",statusMap);
%>
        <tbl:select name="txtStatus1" set="statusMap" match="txtStatus1" width="23"/>
       </td>
    </tr>
    
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
  </tr>
  </table>   
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="150">
      <input type="hidden" name="txtCustomerID" value="<%=custId%>">
      <input type="hidden" name="txtActionType" value="">

  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
   <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="6" cellspacing="1" class="list_bg">
        <tr class="list_head">
          <td class="list_head" width="5%"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'Index', this.checked)"></td> 
          <td class="list_head" width="10%">业务帐户ID</td>
          <td class="list_head" width="10%">业务名称</td>
          <td class="list_head" width="7%">状态</td>          
          <td class="list_head" width="10%">创建日期</td>
          <td class="list_head" width="25%">产品</td>
          <td class="list_head" width="21%">设备类型  :   设备序列号</td>
          <td class="list_head" width="15%">基本产品到期时间</td>
        </tr> 
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<% 
   ServiceAccountDeviceDTO dto=(ServiceAccountDeviceDTO)pageContext.getAttribute("oneline");
   int saID=dto.getServiceAccountID();
   String serviceCode=dto.getServiceCode();
   int seviceID=dto.getServiceID();
   String serviceName=Postern.getServiceNameByID(seviceID);
   String status=Postern.getStatusByAcctServiceID(saID);
   String productName=Postern.getProductByServiceAccountID("'S'",saID);
   boolean checkedNo=false;
   if (saId_indexs !=null){
      for(int i=0;i<saId_indexs.length;i++){
	  	   if(saId_indexs[i].equals(String.valueOf(saID))){
	  	 	    checkedNo=true;
	  	 	    break;
	   	   }
	    }
	 }
	 List devList=Postern.getDeviceInfoByServiceAccountID(saID);
   Iterator itr=devList.iterator();
   //获得基本产品到期时间
   String dateTo=Postern.getBaseProductDateTo(custDTO.getCustomerType(),saID);
%>
	     <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      	  <td align="center">
      	  	<input type="checkbox" name="Index" value="<%=saID%>" <%if(checkedNo){%>checked<%}%>>
      	  	<input type="hidden" name="serviceAcctStatus" value="<%=status%>" >
      	  </td>
      	  <td align="center"><a href="javascript:view_detail_click(<%=saID%>,<%=seviceID%>)" class="link12" ><%=saID%></a></td>
      	  <td align="center"><%=serviceName%></td>
      	  <td align="center"><d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="<%=status%>" /></td>
      	  <td align="center"><tbl:writedate name="oneline" property="createTime"/></td>
      	  <td align="center"><%=productName%></td>
      	  <td align="left" >
       	        <%
		 while(itr.hasNext()){
		    TerminalDeviceDTO tdto=(TerminalDeviceDTO)itr.next();
		    String dClass = tdto.getDeviceClass();
		    String className="";
		    if(!"C".equals(tdto.getStatus())){                       //SET_C_CUSTOMERPRODUCTPSTATUS: C	取消
		    if(dClass!=null)
		     className = Postern.getClassNameByDeviceClass(dClass);
		 %>
		 <%=className %>  : <%=tdto.getSerialNo()%><br>
		<%}}%>
          </td>
          <td align="center"><%=dateTo%></td>
    	 </tbl:printcsstr>
</lgc:bloop>  
 <tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
        <tr>
             <td align="right" class="t12" colspan="6" >
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<TABLE width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" >
	<TR>
	   <td  width="17%" align="right" >请选择有效付费帐户*  </td>
	   <td  width="33%" align="left" ><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" style="width:expression((this.offsetWidth>187)?'auto':187)" /></td>
  </TR>
</TABLE>
<br>
<table width="100%">
<tr>
<td>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:pause_submit()" value="批量暂停"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	   <td width="20" ></td>
     <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
     <td><input name="next" type="button" class="button" onClick="javascript:resume_submit()" value="批量恢复"></td>
     <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</td>
</tr>
</table> 
 </rs:hasresultset> 

</form> 
    </td>
  </tr>
 
</table>   
 

     
      
      
      
      
      