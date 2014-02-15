<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.ServiceDependencyDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language="javascript" >
function AllChoose(){
     if (document.frmPost.allchoose.checked){
        if (document.frmPost.partchoose[0]){
            for (i=0 ;i< document.frmPost.partchoose.length ;i++){
              document.frmPost.partchoose[i].checked =true;
           }
       }
       else if(document.frmPost.partchoose!=null){
       		document.frmPost.partchoose.checked =true;
       }
     }
      else {
        if (document.frmPost.partchoose[0]){
            for (i=0 ;i< document.frmPost.partchoose.length ;i++){
               document.frmPost.partchoose[i].checked =false;
            }
       }
       else if(document.frmPost.partchoose!=null){
       		document.frmPost.partchoose.checked =false;
       }
     }   
 }
 
function add_submit(){
    document.frmPost.action ="config_serviceRelation_add.screen";
    document.frmPost.submit();
}

function back_submit(serviceID){
   document.frmPost.action ="config_serviceInfo_prepare.do?txtServiceID="+serviceID;
   document.frmPost.submit();
}

function hasSelect(){
    var flag =false;
    if (document.frmPost.partchoose!=null){
    	if(document.frmPost.partchoose.length!=null){
       		for (i=0 ;i< document.frmPost.partchoose.length ;i++){
           		if (document.frmPost.partchoose[i].checked){
               			flag =true;
               			break;
               		}
           	}
       	}
       	else{
       		if (document.frmPost.partchoose.checked){
               		flag =true;
               	}
       	}
    }
    if(!flag){
    	return false;
    }
    return true;
}
function del_submit(){
   if(!hasSelect()){
       alert("没有选择业务关系信息，不能提交")
       return;
   }
   var referServiceIds ="" ;
   var checkedflag =false;
   if (document.frmPost.partchoose[0]){
       for (i=0 ;i< document.frmPost.partchoose.length ;i++){
           if (document.frmPost.partchoose[i].checked){
              if (checkedflag){
                  referServiceIds =referServiceIds+","+document.frmPost.partchoose[i].value;
              }
              if (checkedflag ==false){
                  referServiceIds =document.frmPost.partchoose[i].value;
                  checkedflag =true;
              }  
            }
       }
   } else{
       if (document.frmPost.partchoose.checked){
          referServiceIds =document.frmPost.partchoose.value;
       } 
   }
   document.frmPost.txtReferServiceId.value =referServiceIds;
   document.frmPost.action ="config_serviceInfo_del.do";
   document.frmPost.submit();
}

</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td align="center"><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">业务关系信息维护</td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>
<form name="frmPost" method="post" action="config_serviceRelation_query.do" >
    <input type="hidden" name="txtFrom"  value="1">
    <input type="hidden" name="txtTo"  value="10">
    <input type="hidden" name="txtServiceID" value="<tbl:writeparam name="txtServiceID" />" >
    <input type="hidden" name="txtReferServiceId" value="" >
    <input type="hidden" name ="ActionFlag" value ="del_serviceDependence" >
    <input type="hidden" name ="func_flag" value ="20002" >
   
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr class="list_head">
        <td class="list_head" width="5%"><div align="center"><input type="checkbox" name="allchoose" value="" onclick="AllChoose()"></div></td>
        <td class="list_head" width="19%">业务ID</td>
        <td class="list_head" width="19%">业务名称</td>
        <td class="list_head" width="19%">关联业务ID</td>
        <td class="list_head" width="19%">关联业务名称</td>
        <td class="list_head" width="19%">关系类型</td>
      </tr>
      <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
         <%
            ServiceDependencyDTO dto =(ServiceDependencyDTO)pageContext.getAttribute("oneline");
         %>
         <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
            <td align="center" class="t12">
	      <input type="checkbox" name="partchoose" value="<tbl:write name="oneline" property="referServiceId"/>" >
	    </td>  
            <td align="center" ><tbl:writenumber name="oneline" property="serviceId" digit="7"/></td>
            <%
              String serviceName = Postern.getServiceNameByID(dto.getServiceId());
            %>
            <td align="center" ><%=serviceName%></td>
            <td align="center" ><tbl:writenumber name="oneline" property="referServiceId" digit="7" /></td>
            <%
             String dependServiceName =Postern.getServiceNameByID(dto.getReferServiceId());
            %>
            <td align="center" ><%=dependServiceName%></td>
            <td align="center" ><d:getcmnname typeName="SET_P_SERVICEDEPENDENCYTYPE" match="oneline:type" /></td>
         </tbl:printcsstr>
      </lgc:bloop>
     
     <rs:hasresultset>
        <tr class="trline" >
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
       </rs:hasresultset>         
       <tr class="trline" >
         <td colspan="6">
         <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
          <tr>
            <td><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
         </table>
       </td></tr>
      </table> 
      <BR>  
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
          <td><img src="img/button2_r.gif" width="22" height="20"></td> 
         <td><input name="next" type="button" class="button" onClick="back_submit(<tbl:writeparam name="txtServiceID" />)" value="返  回"></td>
         <td><img src="img/button2_l.gif" width="11" height="20"></td>
         <td width="20" ></td>  
            
             <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="next" type="button" class="button" onClick="add_submit()" value="增  加"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>    
            
            <td width="20" ></td>  
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="next" type="button" class="button" onClick="del_submit()" value="删  除"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>    
            
                       
        </tr>
      </table>	
      <input type="hidden" name="confirm_post"  value="true" >
      <tbl:generatetoken /> 
</form>
