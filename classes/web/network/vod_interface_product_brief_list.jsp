<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %> 
<%@ page import="com.dtv.oss.dto.VODInterfaceProductDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>
<%	
	Map allAcctItemType = Postern.getAllAcctItemType();
	pageContext.setAttribute("AllAcctItemType",allAcctItemType);
	Map allProduct = Postern.getAllProductIDAndName();
%>
<script language=javascript>
 
function view_detail(id){
	
	self.location.href="vod_interface_product_editing.do?txtSmsProductID="+id+"&editing_type=update";
} 

function back(){
	self.location.href="vod_interface_index.do";
}

function new_editing(){
	self.location.href="vod_interface_product_editing.do?editing_type=new";
}
 
</script>

 <br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td> 
 <table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">VOD产品列表</td>
	</tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr>
    	   <td><img src="img/mao.gif" width="1" height="1"></td>
  	</tr>
 </table>
<form name="frmPost" method="post">
<input type="hidden" name="txtFrom"  value="1"/>
<input type="hidden" name="txtTo"  value="10"/>
	<rs:hasresultset>
 	  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
					
         	<tr class="list_head">
           		<td class="list_head" width="20%">SMS产品名称</td>
           		<td class="list_head" width="35%">VOD授权代码</td>
           		<td class="list_head" width="15%">是否创建业务帐户</td>
           		<td class="list_head" width="20%">费用账目类型</td>
            		<td class="list_head" width="8%">状态</td>
        	</tr>
	       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
		<%
			VODInterfaceProductDTO dto = (VODInterfaceProductDTO)pageContext.getAttribute("oneline");
			String acctItemTypeID = dto.getAcctItemTypeID();						
			String acctItemTypeName = (String)allAcctItemType.get(acctItemTypeID);
			if(acctItemTypeName == null){
				acctItemTypeName = "";
			}
			long smsProductID = dto.getSmsProductID();
			String productName = (String)allProduct.get(String.valueOf(smsProductID));
			if(productName == null){
				productName = "";
			}
		%>
	 		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     			<td align="center"><a href="javascript:view_detail('<tbl:write name="oneline" property="smsProductID"/>')" class="link12" ><%=productName%></a></td>
      	     			<td align="center"><tbl:write name="oneline" property="billingCodeList"/></td>
      	     			<td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:newsaflag"/></td>
      	     			 
      	     			<td align="center"><%=acctItemTypeName%></td>      	     			
      	     			<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status"/></td>
    			</tr>
			</lgc:bloop> 
			<tr>
    				<td colspan="5" class="list_foot"></td>
  			</tr>
				 
            		 <tr>
              <td align="right" class="t12" colspan="5" >
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
				<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  					<tr>
    					<td><img src="img/mao.gif" width="1" height="1"></td>
 					</tr>
 				</table>
 				
 				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  				<tr align="center">
	  					<td>
							<table  border="0" cellspacing="0" cellpadding="0">							
		  						<tr>
		  							<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
									<td><input name="Submit" type="button" class="button" value="返回" onclick="javascript:back()"/></td>
									<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
									<td width=20 ></td>
									<td><img src="img/button_l.gif" border="0" ></td>
									<td><input name="Submit" type="button" class="button" value="新增" onclick="javascript:new_editing()"/></td>
									<td><img src="img/button_r.gif" border="0" ></td>
		  						</tr>
	  						</table> 
	 					</td>
  					</tr>
  				</table>		
			  </td>
  </tr>
</form>  
</table>  