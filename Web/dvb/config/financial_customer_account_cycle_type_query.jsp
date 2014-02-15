<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 
<%@ page import="com.dtv.oss.dto.CustAcctCycleCfgDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>

<%	
	String parameters="";		
	String strFrom=(String)request.getAttribute("txtFrom");
	String strTo=(String)request.getAttribute("txtTo");
	String strPage=(String)request.getAttribute("txtPage");
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
%>
<script language=javascript>
function view_detail(strId){
	var strUrl="customer_account_cycle_type_editing.do?editing_type=view_update&txtSeqNo="+strId;
	self.location.href=strUrl;
} 
 
function ChangeSubType(){    
    document.FrameUS.submit_update_select('configsubtype', document.frmPost.txtConfigType.value, 'txtConfigSubType', '');   
}

function create_config_submit() {   
   var strUrl="customer_account_cycle_type_editing.do?editing_type=new";
   self.location.href=strUrl;
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
			<form name="frmPost" method="post" action="customer_account_cycle_type_brief.do" >
			<table  border="0" align="center" cellpadding="0" cellspacing="10">
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td  class="title">配置客户帐务周期类型</td>
  				</tr>
  			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  				<tr>
    				<td><img src="img/mao.gif" width="1" height="1"></td>
  				</tr>
			</table>       			
    			<input type="hidden" name="txtFrom" size="20" value="1"/>
    			<input type="hidden" name="txtTo" size="20" value="10"/>
 				<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   					<tr>
    					<td><img src="img/mao.gif" width="1" height="1"></td>
  					</tr>
 				</table>
 				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         			<tr class="list_head">
           				<td class="list_head">序号 </td>
           				<td class="list_head">帐务周期类型 </td>
           				<td class="list_head">客户类型 </td>
           				<td class="list_head">帐户类型 </td>
        			</tr>
					<rs:hasresultset>
					<%
						Map allBillingCycleType = Postern.getAllAccountCycleType();
						String id = null;
						String name = null;
						CustAcctCycleCfgDTO dto = null;
					%>
					<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
					<%
						dto = (CustAcctCycleCfgDTO)pageContext.getAttribute("oneline");
						if(dto ==null){
							name = "";
						}else{
							id = String.valueOf(dto.getBillingCycleTypeId());
							name = (String)allBillingCycleType.get(id);
						}
						
					%>
	 				<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     				<td align="center"><a href="javascript:view_detail('<tbl:write name="oneline" property="seqNo"/>')" class="link12" >
	     					<tbl:writenumber name="oneline" property="seqNo" digit="7"/></a></td>
      	     			<td align="center"><%=name%></td>
      	     			<td align="center"><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="oneline:customerType"/></td>   
      	     			<td align="center"><d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="oneline:accountType"/></td> 
    				</tr>
					</lgc:bloop> 
					<tr>
    					<td colspan="4" class="list_foot"></td>
  					</tr>
				 
            		        <tr>
              			<td align="right" class="t12" colspan="4" >
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
				 
  				<table align="center" border="0" cellspacing="0" cellpadding="0">
        			<tr>
           				<td><img src="img/button_l.gif" border="0" ></td>
           				<!--<input type="hidden" name="editing_type" value="new_config" /> -->
          				<td background="img/button_bg.gif"><a href="javascript:create_config_submit()" class="btn12">新增客户帐务周期</a></td>
          				<td><img src="img/button_r.gif" border="0" ></td>
          				<td width="20" ></td>
        			</tr>					
				</table>
			</form>
    	</td>
  	</tr>	  
</table>  