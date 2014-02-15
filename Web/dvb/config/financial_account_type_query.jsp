<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %> 
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>
<%	
	 
	Map nameMap = Postern.getAcctItemTypeForConfig();
	pageContext.setAttribute("AcctItemTypeForConfig",nameMap);
	
	AcctItemTypeDTO filterDto = (AcctItemTypeDTO)request.getAttribute("QueryFilter");
	if(filterDto == null){
		filterDto = new AcctItemTypeDTO();
	}
	pageContext.setAttribute("QueryFilter",filterDto);
	System.out.println(filterDto);
	
	
%>
<script language=javascript>

function query_submit(){	 
		 
		document.frmPost.submit();	 
}

function view_detail(strId){
	document.frmPost.action="account_type_editing.do";
	document.frmPost.txtAcctItemTypeID.value=strId;
	document.frmPost.submit();
} 
 
function ChangeSubType(){    
    document.FrameUS.submit_update_select('configsubtype', document.frmPost.txtConfigType.value, 'txtConfigSubType', '');   
}

function create_config_submit() {   
   var strUrl="account_type_editing.do?editing_type=new";
   document.frmPost.action=strUrl;
   document.frmPost.submit();
}

function changeSummaryToStatus(){
	var value=document.frmPost.txtSummaryAiFlag.value;
	if(value==null){
		value='N';
	}
	if(value=='N'){
		document.frmPost.txtSummaryTo.disabled =false;
	}else{
		document.frmPost.txtSummaryTo.disabled=true;
	}
}

</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
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
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table  border="0" align="center" cellpadding="0" cellspacing="10">
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td  class="title">配置帐目类型</td>
  				</tr>
  			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  				<tr>
    				<td><img src="img/mao.gif" width="1" height="1"></td>
  				</tr>
			</table>
			<form name="frmPost" method="post" action="account_type_brief_rusult.do" > 
   				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">      
        			<tr>
						<td class="list_bg2" align="right" >帐目类型标识</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtAcctItemTypeID1" value="<tbl:writeparam name="txtAcctItemTypeID1" />"></td>
						<td class="list_bg2" align="right" >帐目类型名称</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtAcctItemTypeName" value="<tbl:writeparam name="txtAcctItemTypeName" />"></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >费用类型</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE" match="txtFeeType" width="23" /></td>
						<td class="list_bg2" align="right" >归类帐目标志</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtSummaryAiFlag" mapName="SET_G_YESNOFLAG" match="txtSummaryAiFlag" width="23" onchange="javascript:changeSummaryToStatus()" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >特殊销帐标识</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtSpecialSetoffFlag" mapName="SET_G_YESNOFLAG" match="txtSpecialSetoffFlag" width="23" /></td>
						<td class="list_bg2" align="right" >归类到</td>
						<td class="list_bg1" align="left" ><tbl:select name="txtSummaryTo" set="AcctItemTypeForConfig" match="txtSummaryTo" width="23" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >显示级别</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtShowLevel" mapName="SET_F_AITSHOWLEVEL" match="txtShowLevel" width="23" /></td>
						<td class="list_bg2" align="right" >状态</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" /></td>
					</tr> 
      			</table>
      			<script>
					changeSummaryToStatus();
				</script>
      			 
       			<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  				<tr align="center">
	  					<td class="list_bg1">
							<table  border="0" cellspacing="0" cellpadding="0">
		  						<tr>
		  						       <td><img src="img/button_l.gif" border="0" ></td>
			          				        <td background="img/button_bg.gif"><a href="javascript:query_submit()" class="btn12">查 询</a></td>
			          				        <td><img src="img/button_r.gif" border="0" ></td>
									 
									<td width=20 > <td>
									<td><img src="img/button_l.gif" border="0" ></td>
			          				<td background="img/button_bg.gif"><a href="javascript:create_config_submit()" class="btn12">增加帐目类型</a></td>
			          				<td><img src="img/button_r.gif" border="0" ></td>
		  						</tr>
	  						</table> 
	 					</td>
  					</tr>
  				</table>
  				<input type="hidden" name="txtAcctItemTypeID"  value=""/>
  				<input type="hidden" name="editing_type"  value="view_update"/>
  				<input type="hidden" name="txtFrom"  value="1"/>
    			         <input type="hidden" name="txtTo"  value="10"/>
    			 
 				<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   					<tr>
    					<td><img src="img/mao.gif" width="1" height="1"></td>
  					</tr>
 				</table>
 				<rs:hasresultset>
 				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         			<tr class="list_head">
           				<td class="list_head">帐目类型标识 </td>
           				<td class="list_head">帐目类型名称 </td>
           				<td class="list_head">费用类型 </td>
           				<td class="list_head">归类帐目标记 </td>
            			<td class="list_head">归类到 </td>
           				<td class="list_head">状态 </td>         
           				<td class="list_head">特殊销帐标识 </td>
           				<td class="list_head">显示级别 </td>
        			</tr>
					
					<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
	 				<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	 				<%
	 					AcctItemTypeDTO oneDto = (AcctItemTypeDTO)pageContext.getAttribute("oneline");
	 					String summaryTo = oneDto.getSummaryTo();	
	 					if(summaryTo!=null){
	 						summaryTo=summaryTo.trim();
	 					}
						String acctItemTypeName = (String)nameMap.get(summaryTo);
						if(acctItemTypeName == null){
							acctItemTypeName = "";
						}
	 				%>
	     				<td align="center"><a href="javascript:view_detail('<tbl:write name="oneline" property="acctItemTypeID"/>')" class="link12" ><tbl:write name="oneline" property="acctItemTypeID" /></a></td>
      	     			<td align="center"><tbl:write name="oneline" property="acctItemTypeName"/></td>
      	     			<td align="center"><d:getcmnname typeName="SET_F_BRFEETYPE" match="oneline:feeType"/></td>   
      	     			<td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:summaryAiFlag"/></td> 
      	     			<td align="center"><%=acctItemTypeName%></td>
      	      			<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status"/></td>
      	     			<td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:specialSetOffFlag"/></td>      	     			
      	     			<td align="center"><d:getcmnname typeName="SET_F_AITSHOWLEVEL" match="oneline:showLevel"/></td>
    				</tr>
					</lgc:bloop> 
					<tr>
    					<td colspan="8" class="list_foot"></td>
  					</tr>
				 
            		<tr>
              			<td align="right" class="t12" colspan="8" >
                 			第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 			<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 			共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
                 		<rs:notfirst>
                   			<img src="img/dot_top.gif" width="17" height="11">
                   			<a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   			<img src="img/dot_pre.gif" width="6" height="11">
                   			<a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页 </a>
                 		</rs:notfirst>          
                		<rs:notlast>
                 				&nbsp;
                 			<img src="img/dot_next.gif" width="6" height="11">
                 			<a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页 </a>
                 			<img src="img/dot_end.gif" width="17" height="11">
                 			<a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页 </a>
                		</rs:notlast>
                			&nbsp;
                			转到
               				<input type="text" name="txtPage" class="page_txt">页 
               				<a href="javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 			<input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               				</a>
             			</td>
         			</tr>
				</table> 
				</rs:hasresultset>
				 		
			</form>
    	</td>
  	</tr>	  
</table>  