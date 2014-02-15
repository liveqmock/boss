<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 
<%@ page import="com.dtv.oss.dto.BidimConfigSettingValueDTO" %>

<%	
	String parameters=null;
	String hasQueried = (String)request.getAttribute("has_queried");
	if(hasQueried!=null && hasQueried.trim().equals("true")){
		String strConfigType=(String)request.getAttribute("txtConfigType");
		if(strConfigType==null){
			strConfigType="";
		}
		String strConfigSubType=(String)request.getAttribute("txtConfigSubType");
		if(strConfigSubType==null){
			strConfigSubType="";
		}
		String strValueType=(String)request.getAttribute("txtValueType");
		if(strValueType==null){
			strValueType="";
		}
		String strStatus=(String)request.getAttribute("txtStatus");
		if(strStatus==null){
			strStatus="";
		}
		String strFrom=(String)request.getAttribute("txtFrom");
		String strTo=(String)request.getAttribute("txtTo");
		String strPage=(String)request.getAttribute("txtPage");
		parameters="query_type=result&txtConfigType="+strConfigType+"&txtConfigSubType="+strConfigSubType;
		parameters+=("&txtValueType="+strValueType+"&txtStatus="+strStatus);
		if(strFrom ==null){
			strFrom="0";
		}
		if(strTo == null){
			strTo=String.valueOf(Integer.valueOf(strFrom).intValue() + 10);
		}
		parameters+=("&txtFrom="+strFrom+"&txtTo="+strTo);
		if(strPage!=null){
			parameters+=("&txtPage="+strPage);
		}
	}else{
		parameters="query_type=entrance";
	}
%>
<script language=javascript>

function query_submit(){	 
		document.frmPost.submit();	 
}

function view_detail(strId){
	var strUrl="bidim_config_editing.do?editing_type=view_detail_update_config&txtID="+strId;
	strUrl+="&";
	strUrl+="<%=parameters%>";
	self.location.href=strUrl;
} 
 
function ChangeSubType(){    
    document.FrameUS.submit_update_select('configsubtype', document.frmPost.txtConfigType.value, 'txtConfigSubType', '');   
}

function create_config_submit()  
 
{
	location.href = "bidim_config_create.screen";
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
    				<td  class="title">配置信息查询</td>
  				</tr>
  			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  				<tr>
    				<td><img src="img/mao.gif" width="1" height="1"></td>
  				</tr>
			</table>
			<form name="frmPost" method="post" action="bidim_config_query.do" > 
   				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">      
        			<tr> 
          				<td  class="list_bg2"><div align="right">配置信息类型 </div></td>         
          				<td class="list_bg1" align="left">
           					<d:selcmn name="txtConfigType" mapName="SET_C_BIDIMCONFIGSETTINGCONFIGTYPE" match="txtConfigType" width="23" onchange="ChangeSubType()"/>
           				</td>      
           				<td class="list_bg2"><div align="right">配置信息子类型 </div></td>
	      				<td class="list_bg1" align="left">
	      				  <d:selsubtype name="txtConfigSubType" paraClass="txtConfigType" match="txtConfigSubType" width="23"  />
	      					                
             		               </td>           
        			</tr>
          			<tr>          
             			<td  class="list_bg2"><div align="right">取值类型 </div></td>         
             			<td class="list_bg1" align="left">
             				<d:selcmn name="txtValueType" mapName="SET_C_BIDIMCONFIGSETTINGVALUETYPE" match="txtValueType" width="23" />
             			</td>      
             			<td class="list_bg2"><div align="right">状态 </div></td>
               			<td class="list_bg1" align="left">
                			<d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />           
     					</td>
						<input type="hidden" name="query_type" size="20" value="result" />
    				</tr>          
      			</table>
       			<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  				<tr align="center">
	  					<td class="list_bg1">
							<table  border="0" cellspacing="0" cellpadding="0">
		  						<tr>
									 
									
           				                      <td><img src="img/button_l.gif" width="11" height="20"></td>
          				                      <td background="img/button_bg.gif"><a href="javascript:query_submit()" class="btn12">查  询</a></td>
          				                       <td><img src="img/button_r.gif" width="22" height="20"></td>     
          				                    <td width="20" ></td>  
           				                      <td><img src="img/button_l.gif" width="11" height="20"></td>
          				                     <td background="img/button_bg.gif"><a href="javascript:create_config_submit()" class="btn12">新  增</a></td>
          				                       <td><img src="img/button_r.gif" width="22" height="20"></td>      
          				
		  						</tr>
	  						</table> 
	 					</td>
  					</tr>
  				</table>
    			<input type="hidden" name="txtFrom" size="20" value="1"/>
    			<input type="hidden" name="txtTo" size="20" value="10"/>
 				<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   					<tr>
    					<td><img src="img/mao.gif" width="1" height="1"></td>
  					</tr>
 				</table>
 				<rs:hasresultset>
 				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         			<tr class="list_head">
           				<td class="list_head">配置ID</td>
           				<td class="list_head">名称 </td>
           				<td class="list_head">配置信息类型 </td>
           				<td class="list_head">配置信息子类型 </td>
            			<td class="list_head">取值类型 </td>
           				<td class="list_head">状态 </td>        
        			</tr>
					
					<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
	 				<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     				<td align="center"><a href="javascript:view_detail('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      	     			<td align="center"><tbl:write name="oneline" property="name"/></td>
      	     			<td align="center"><d:getcmnname typeName="SET_C_BIDIMCONFIGSETTINGCONFIGTYPE" match="oneline:configType"/></td>   
      	     			<td align="center"><d:getcmnname typeName="SET_C_BIDIMCONFIGSETTINGCONFIGSUBTYPE" match="oneline:configSubType"/></td> 
      	      			<td align="center"><d:getcmnname typeName="SET_C_BIDIMCONFIGSETTINGVALUETYPE" match="oneline:valueType"/></td>
      	     			<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>      	     			
    				</tr>
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
               				<a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
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