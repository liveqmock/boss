<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
				 com.dtv.oss.web.util.CommonKeys" %>
<%
	boolean isGroupToSingle=false;
	String actionType=request.getParameter("txtActionType");
	if(actionType!=null&&actionType.equals("groupToSingle")){
		isGroupToSingle=true;
	}
	
	String customerID=request.getParameter("txtGroupCustID");
	String txtGroupCustName=Postern.getCustomerNameById(WebUtil.StringToInt(request.getParameter("txtGroupCustID")));
	System.out.println("**********"+txtGroupCustName);
%>
<script language=javascript>
function toSingle_submit(val){
	if(val==null||val==""){
		val=document.frmPost.txtCustomerID.value;
	}
	if(val!=null&&val!=""){
		document.frmPost.action="group_customer_info_prepare.do?txtCustomerID="+val;
		document.frmPost.submit();
	}
}
function group_cust_query(){
	document.frmPost.submit();
}
function open_group_cust(customerID,childID,contractNo){
	self.location.href="group_cust_open.do?txtCustomerID="+customerID+"&txtChildCustID="+childID+"&txtContractNo="+contractNo;
}
function view_detail_click(strId){
	if(strId==null||strId==""){
		strId=document.frmPost.txtCustomerID.value;
	}
	if(strId!=null&&strId!=""){
		document.frmPost.action="group_leaf_customer_view.do?txtCustomerID="+strId;
		document.frmPost.submit();
	}
}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">子客户查询</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 
<form name="frmPost" action="group_cust_query.do" method="post" > 
   <tbl:hiddenparameters pass="txtActionType" />
	<input type="hidden" name="forwardFlag" value="30" >
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td colspan="4" class="import_tit" align="center"><font size="3">客户信息</font></td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">集团客户ID</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text"  name="txtGroupCustIDDIS" size="25" value="<tbl:writeparam name="txtGroupCustID"/>" disabled>
		<input type="hidden" name="txtGroupCustID" size="25" value="<tbl:writeparam name="txtGroupCustID"/>" >
	  </td>
      <td class="list_bg2" width="17%"  align="right">集团客户名称</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtGroupCustNameDIS" size="25" value="<%=txtGroupCustName%>" disabled>
		<input type="hidden" name="txtGroupCustName" size="25" value="<%=txtGroupCustName%>" >
	  </td>
    </tr>

     <tr>   
      <td class="list_bg2" width="17%"  align="right">子客户ID</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtCustomerID" size="25" value="<tbl:writeparam name="txtCustomerID"/>" >
	  </td>
      <td class="list_bg2" width="17%"  align="right">子客户姓名</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtName" size="25" value="<tbl:writeparam name="txtName"/>" >
	  </td>
    </tr>
    <%
         String groupCustId=request.getParameter("txtGroupCustID");
         Map mapContract = null;
          mapContract = Postern.getContractNoByCustId(WebUtil.StringToInt(request.getParameter("txtGroupCustID")));
          System.out.println("*********$$$$$*******"+request.getParameter("groupCustId"));
          pageContext.setAttribute("AllCONTRACT", mapContract);
        
    %>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">合同名称</td>
      <td class="list_bg1" width="33%" colspan="3" align="left">
      <tbl:select name="txtContractNo" set="AllCONTRACT" match="txtContractNo" width="23"/></td>
   </tr>
    <tr> 
      <td class="list_bg2" colspan="4" align="center">
      	<table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:group_cust_query()" class="btn12">查    询</a></td> 
	   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
         </tr>
        </table>
       </td>
     </tr>
   </table>
   <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	    <td><img src="img/mao.gif" width="1" height="1"></td>
	  </tr>
  </table>   
  <br>  
  <rs:hasresultset>
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
  </table>
   <table width="100%" border="0" cellpadding="2" cellspacing="1" class="list_bg" align="center" >     
     <tr> 
          <td width="6%"  class="list_head"  align="center">子客户ID</td>
          <td width="10%" class="list_head"  align="center">子客户姓名</td>                    
          <td width="12%"  class="list_head"  align="center">集团客户名称</td> 
          <td class="list_head"  align="center">子客户所在区</td>         
          <td class="list_head"  align="center">子客户地址</td>
          <td width="10%"  class="list_head"  align="center">开户日期</td>
          <td width="5%"  class="list_head"  align="center">状态</td>
          <td width="10%"  class="list_head"  align="center">操作</td>
     </tr> 
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	CustomerDTO customerDTO=(CustomerDTO)pageContext.getAttribute("oneline");
	String groupName=Postern.getCustomerNameById(customerDTO.getGroupCustID());
	String contractNo=Postern.getContractNoByGroupCustID(WebUtil.IntToString(customerDTO.getGroupCustID(),0));
	AddressDTO addrDTO=Postern.getAddressDtoByID(customerDTO.getAddressID());
	pageContext.setAttribute("addr",addrDTO);
%>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	 <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="customerID" />')" class="link12" ><tbl:write name="oneline" property="customerID" /></a></td>
       	 <td align="center" ><tbl:write name="oneline" property="name"/></td>
      	 <td align="center" ><%=groupName%></td>
      	 <td align="center" ><tbl:WriteDistrictInfo name="addr" property="districtID" /></td>
      	 <td align="center" ><tbl:write name="addr" property="detailAddress"/></td>
      	 <td align="center" ><tbl:writedate name="oneline" property="dtCreate"/></td>
      	 <td align="center" ><d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="oneline:status" /></td>
      	 <td align="center" >
      	 	<%if(isGroupToSingle){%>
	      	 	<a href="javascript:toSingle_submit('<tbl:write name="oneline" property="customerID" />');" >转个人客户</a> 
      	 	<%}else{%>
  	    	 	<a href="javascript:open_group_cust('<%=customerID%>','<tbl:write name="oneline" property="customerID" />','<%=contractNo%>');" >新开用户</a> 
      	 	<%}%>	
      	 </td>
    </tbl:printcsstr>
</lgc:bloop> 
  <tr>
    <td colspan="12" class="list_foot"></td>
  </tr>
</table> 
  <table  border="0" align="right" cellpadding="0" cellspacing="8">
       <tr>
          <td align="right" class="t12" colspan="12" >
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
 <input type="hidden" name="txtFrom" value="1">
<input type="hidden" name="txtTo" value="10"> 
 </rs:hasresultset>
</form> 
    
   