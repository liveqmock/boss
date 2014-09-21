<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.dto.ServiceAccountDeviceDTO" %>
<%@ page import="java.util.*" %>
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

function hasSelect(){
    var flag =false;
    var errorServiceAcctIds ='';
    if (document.frmPost.saId_indexs!=null){
    	if(document.frmPost.saId_indexs.length!=null){
       		for (i=0 ;i< document.frmPost.saId_indexs.length ;i++){
           		if (document.frmPost.saId_indexs[i].checked){
              		flag =true;
              }
           	}
       }else{
       		if (document.frmPost.saId_indexs.checked){
              flag =true;
          }
       }
    }
    if(!flag){
    	alert("没有选择记录，无法批量操作！")
    	return false;
    }
    return true;
}
 
function next_submit(){
	 if(!hasSelect()){
    	return;
   }
	 document.frmPost.action="batchRenewProtocol_chooseSoftPackage.do";
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
<TABLE border="0" cellspacing="0" cellpadding="0" width="1000" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">协议客户批量续费--->选择业务账户</td>
  </tr>
</table>
<table width="1000"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" action="batchRenewProtocol_chooseServiceAccount.do" method="post" > 
	 <input type="hidden" name="txtCsiType" value="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDP%>" >
	 <input type="hidden" name="txtSoftFlag" value="true" >
	 <input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />" >
	 <input type="hidden" name="txtCustType" value="<tbl:writeparam name="txtCustType" />" >
	 <input type ="hidden" name="txtActionType" value="ProtoclPrePayment" >
	 
   <table width="1000"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
       
    <tr>  
      <td class="list_bg2" align="right">设备类型</td>
      <td class="list_bg1">
<% 
    Map mapDeviceClasses = Postern.getAllDeviceClasses();
    pageContext.setAttribute("AllDeviceClasses",mapDeviceClasses);
    String[] saId_indexs =request.getParameterValues("saId_indexs");
    
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
       <td colspan="3" class="list_bg1" align="left">
      	 <tbl:select name="txtProductID" set="AllProductIDAndName" match="txtProductID" width="23" />
       </td>   
    </tr>
    
    </table>
    <table width="1000"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
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
      <input type="hidden" name="txtTo" size="20" value="200">

  <table width="1000"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
   <rs:hasresultset>
    <table width="1000"  border="0" align="center" cellpadding="6" cellspacing="1" class="list_bg">
        <tr class="list_head">
          <td class="list_head" nowrap width="10"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'saId_indexs', this.checked)"></td> 
          <td class="list_head" nowrap width="50">业务帐户ID</td>
          <td class="list_head" nowrap width="80">业务名称</td>
          <td class="list_head" nowrap width="30">状态</td>          
          <td class="list_head" nowrap width="70">创建日期</td>
          <td class="list_head" nowrap width="170">设备类型:设备序列号</td>
          <td class="list_head" width="690">产品包</td>
        </tr> 
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<% 
   ServiceAccountDeviceDTO dto=(ServiceAccountDeviceDTO)pageContext.getAttribute("oneline");
   int saID=dto.getServiceAccountID();
   int seviceID=dto.getServiceID();
   String serviceName=Postern.getServiceNameByID(seviceID);
   String status=Postern.getStatusByAcctServiceID(saID);
   String packageName=Postern.getPackageNameByServiceAccountID(saID);
   boolean checkedNo=false;
   if (saId_indexs !=null){
      for(int i=0;i<saId_indexs.length;i++){
	  	   if(saId_indexs[i].equals(String.valueOf(saID))){
	  	 	    checkedNo=true;
	  	 	    break;
	   	   }
	    }
	 }
%>
	     <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      	  <td align="center">
      	  	<input type="checkbox" name="saId_indexs" value="<%=saID%>" <%if(checkedNo){%>checked<%}%>>
      	  </td>
      	  <td align="center"><a href="javascript:view_detail_click(<%=saID%>,<%=seviceID%>)" class="link12" ><%=saID%></a></td>
      	  <td align="center"><%=serviceName%></td>
      	  <td align="center"><d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="<%=status%>" /></td>
      	  <td align="center"><tbl:write name="oneline" property="createTime"/></td>
       	  <td align="center" >
       	  <%
       	    List devList=Postern.getDeviceInfoByServiceAccountID(saID);
            Iterator itr=devList.iterator();
		        while(itr.hasNext()){
		        TerminalDeviceDTO tdto=(TerminalDeviceDTO)itr.next();
		        String dClass = tdto.getDeviceClass();
		        String className="";
		        if(!"C".equals(tdto.getStatus())){      //SET_C_CUSTOMERPRODUCTPSTATUS: C	取消
		        if(dClass!=null)
		           className = Postern.getClassNameByDeviceClass(dClass);
		       %>
		        <%=className %>  : <%=tdto.getSerialNo()%><br>
	        	<%} }%>
          </td>
          <td align="left"><%=packageName%></td>
    	 </tbl:printcsstr>
</lgc:bloop>  
 <tr>
    <td colspan="7" class="list_foot"></td>
  </tr>
        <tr>
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
<table width="1000"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="100%">
<tr>
<td>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:next_submit()" value="下一步"></td>
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
 

     
      
      
      
      
      