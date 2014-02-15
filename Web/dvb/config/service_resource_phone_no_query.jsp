<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.ResourcePhoneNoDTO" %>
<%
	String queryType = (String)request.getAttribute("query_type");
	if(queryType==null){
		queryType="entrance";
	}
	//String resourceName = (String)getAttribute("resourceName");
	//if(resourceName==null){
	//	resourceName="";
	//}
%>
<script language="javascript" >
function detailQuery(){
	document.frmPost.submit();
}

function AllChoose(){
	if (document.frmPost.allchoose.checked){
		if (document.frmPost.partchoose[0]){
			for (i=0 ;i< document.frmPost.partchoose.length ;i++){
				if(!document.frmPost.partchoose[i].disabled){
					document.frmPost.partchoose[i].checked =true;
				}
			}
		}else if(document.frmPost.partchoose!=null){
			if(!document.frmPost.partchoose.disabled){
				document.frmPost.partchoose.checked =true;
			}
		}
	}else{
		if (document.frmPost.partchoose[0]){
			for (i=0 ;i< document.frmPost.partchoose.length ;i++){
				document.frmPost.partchoose[i].checked =false;
			}
		}else if(document.frmPost.partchoose!=null){
			document.frmPost.partchoose.checked =false;
		}
	}   
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
       	}else{
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

function delObjects(){
   if(!hasSelect()){
    	alert("没有选择资源名称，不能提交")
    	return;
   }
   var itemIDs ="" ;
   var checkedflag =false;
   if (document.frmPost.partchoose[0]){
       for (i=0 ;i< document.frmPost.partchoose.length ;i++){
           if (document.frmPost.partchoose[i].checked){
              if (checkedflag){
                  itemIDs =itemIDs+","+document.frmPost.partchoose[i].value;
              }
              if (checkedflag ==false){
                  itemIDs =document.frmPost.partchoose[i].value;
                  checkedflag =true;
              }  
            }
       }
   } else{
       if (document.frmPost.partchoose.checked){
          itemIDs =document.frmPost.partchoose.value;
       } 
   }
   document.frmPost.txtPhoneNoItemIDs.value =itemIDs;
   document.frmPost.action ="config_service_resource_modify.do?modify_type=phone_no_delete_multiply&txtPhoneNoItemIDs="+itemIDs;
   document.frmPost.submit();
}

function view_update(){
	
}

</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td align="center"><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">查询电话号码资源信息</td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>
<form name="frmPost" method="post" action="config_service_resource_detail_query.do" >
    <input type="hidden" name="txtFrom"  value="1">
    <input type="hidden" name="txtTo"  value="10">
    <input type="hidden" name="query_type" value="query" >
    <input type="hidden" name="txtPhoneNoItemIDs" value="" >    
    <%
    	ResourcePhoneNoDTO conditionDto = (ResourcePhoneNoDTO)request.getAttribute("QueryConditionDto");
    	if(conditionDto==null){
    		conditionDto = new ResourcePhoneNoDTO();
    	}
    	pageContext.setAttribute("QueryCondition",conditionDto);
    	String resourceName=conditionDto.getResourceName();
    %>
    <input type="hidden" name="txtResourceName" value="<tbl:write name='QueryCondition' property='resourceName' />" />
    <input type="hidden" name="query_type" value="result" />
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
    	<tr>
    		<td class="list_bg2" align="right" width="20%" > 资源名称 </td>					
			<td class="list_bg1" align="left" width="30%" ><input type="text" value="<tbl:write name='QueryCondition' property='resourceName' />" size="25" disabled=true /></td>
			<td class="list_bg2" align="right" width="20%" > 号码状态</td>
			<td class="list_bg1" align="left" width="30%" ><d:selcmn name="txtStatus" mapName="SET_R_PHONENOSTATUS" match="QueryCondition:status" width="23" /> </td>
    	</tr>
    	<tr>
    		<td class="list_bg2" align="right" > 国家代码 </td>					
			<td class="list_bg1" align="left" ><input type="text" name="txtCountryCode" value="<tbl:write name='QueryCondition' property='countryCode'/>" size="25" /></td>
			<td class="list_bg2" align="right" > 地区代码 </td>
			<td class="list_bg1" align="left" > <input type="text" name="txtAreaCode" value="<tbl:write name='QueryCondition' property='areaCode'/>" size="25" /></td>
    	</tr>
    	<tr>
				<td class="list_bg2" align="right" >区域</td>
				<td class="list_bg1" align="left" >
	        <input type="hidden" name="txtDistrictId" value="<tbl:writeparam name="txtDistrictId" />">
	        <input type="text" name="txtDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictId" />" class="text">
          <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('T','txtDistrictId','txtDistrictDesc')">
				</td>						
				<td class="list_bg2" align="right" >号码级别</td>
				<td class="list_bg1" align="left" ><d:selcmn name="txtGrade" mapName="SET_R_PHONENOGRADE" match="txtGrade" width="23" /></td>
    	</tr>
    	<tr>
    		<td class="list_bg2" align="right" > 电话号码区间 </td>					
			<td class="list_bg1" align="left" colspan=2> <input type="text" name="txtPhoneNoBegin" value="<tbl:write name='QueryCondition' property='phoneNoBegin'/>" width=20 />  -----------  <input type="text" name="txtPhoneNoEnd" value="<tbl:write name='QueryCondition' property='phoneNoEnd'/>" width=20 /> </td>
			
			<td class="list_bg1" align="center" ><table align="center"  border="0" cellspacing="0" cellpadding="0" >
		    		<tr>
		    			<td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
						<td><input name="next" type="button" class="button" onClick="detailQuery()" value="查询"></td>
						<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>  
		            </tr>
	            </table></td>
    	</tr>
    	
    </table>
    <br>
    <table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  		<tr>
    		<td><img src="img/mao.gif" width="1" height="1"></td>
  		</tr>
	</table>
	<br>
<rs:hasresultset>	
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr class="list_head">
	        <td class="list_head" width="5%"><div align="center"><input type="checkbox" name="allchoose" value="" onclick="AllChoose()"></div></td>
	        <td class="list_head" width="7%">ID</td>	        
	        <td class="list_head" width="15%">国家代码 </td>
	        <td class="list_head" width="10%">地区代码 </td>
	        <td class="list_head" width="20%">区域 </td>
	        <td class="list_head" width="10%">号码级别 </td>
	        <td class="list_head" width="15%">号码 </td>
	        <td class="list_head">状态 </td>
	        <td class="list_head" >备注 </td>        
      	</tr>
     	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
      	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      		<%
      			ResourcePhoneNoDTO dto = (ResourcePhoneNoDTO)pageContext.getAttribute("oneline");
      			boolean canBeDeleted = (dto.getStatus()==null) ? false : "A".equals(dto.getStatus());      			
      		%>
      		<%if(canBeDeleted){%>
            <td align="center" class="t12">
	      		<input type="checkbox" name="partchoose" value="<tbl:write name="oneline" property="itemID"/>" >
	    	</td>  
	    	<%}else{%>
	    	<td align="center" class="t12">
	    		<input type="checkbox" name="partchoose" value="<tbl:write name="oneline" property="itemID"/>" disabled=true >
	    	</td>
	    	<%}%>
            <td align="center" ><tbl:write name="oneline" property="itemID" /></td>
            <td align="center" ><tbl:write name="oneline" property="countryCode"/></td>
            <td align="center" ><tbl:write name="oneline" property="areaCode" /></td>
            <td align="center" ><tbl:WriteDistrictInfo name="oneline" property="districtId" /></td>
            <td align="center" ><d:getcmnname typeName="SET_R_PHONENOGRADE"	match="oneline:grade" /></td>
            <td align="center" ><tbl:write name="oneline" property="phoneNo" /></td>
            <td align="center" ><d:getcmnname typeName="SET_R_PHONENOSTATUS" match="oneline:status"/></td>
            <td align="center" ><tbl:write name="oneline" property="comments"/></td>
      	</tbl:printcsstr>
      	</lgc:bloop>      	
      	<tr>
    		<td colspan="9" class="list_foot"></td>
 	  	</tr> 	  	   
      	<tr>
    		<td colspan="8">
 	  	<table  border="0" align="right" cellpadding="0" cellspacing="8">
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
               </td>
               <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)">
               </td>
      	</tr>    
      	</table>
    		</td>
 	  	</tr> 	  	   
 	  	</table>
	  	
      	<tbl:generatetoken /> 
      	</rs:hasresultset>   


      <table width="100%" align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>
         <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>
            <%if(!"entrance".equals(queryType)){%>
         	<td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td background="img/button_bg.gif"><a href="config_service_resource_detail_entrance.do?txtResourceName=<%=resourceName%>"  class="btn12">返  回 </a></td>
		    <td><img src="img/button2_l.gif" width="11" height="20"></td>    
            <td width="20" ></td>          
			<td><img src="img/button_l.gif" border="0" ></td>
			<td><input name="next" type="button" class="button" onClick="delObjects()" value="删  除"></td>
			<td><img src="img/button_r.gif" border="0" ></td>  
			<%}else{%>
         	<td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td background="img/button_bg.gif"><a href="config_service_resource_detail_entrance.do?txtResourceName=<%=resourceName%>"  class="btn12">返  回 </a></td>
		    <td><img src="img/button2_l.gif" width="11" height="20"></td>    
			<%}%>
        </tr>
      </table>	
        </tr>
      </table>	
      <input type="hidden" name="confirm_post"  value="true" >
       
</form>
