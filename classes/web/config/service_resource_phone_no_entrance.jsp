<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.ServiceResourceDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>
<%		
	String backUrl = "config_resource_query.do";
%>
<script>
function queryDetail(){
	var resourceName=document.frmPost.txtResourceName.value;
	document.frmPost.action="config_service_resource_detail_query.do?query_type=entrance&txtResourceName="+resourceName;
	document.frmPost.submit();
}
function addDetail(){
	var resourceName=document.frmPost.txtResourceName.value;
	var resourceType=document.frmPost.txtResourceType.value;
	document.frmPost.action="config_service_resource_detail_editing.do?editing_type=new&txtResourceName="+resourceName+"&txtResourceType="+resourceType;
	document.frmPost.submit();
}
function backToEditingForUpdated(){
	var resourceName=document.frmPost.txtResourceName.value;
	document.frmPost.action="config_service_resource_editing.do?editing_type=update&txtResourceName="+resourceName;
	document.frmPost.submit();
}
</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td class="title">电话号码资源管理</td>
  				</tr>
			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
				<tr>
			    	<td><img src="img/mao.gif" width="1" height="1"></td>
			  	</tr>
			</table>
			<br>			 
			<form name="frmPost" method="post" action="" > 
			<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
				<%
			     	ServiceResourceDTO dto = (ServiceResourceDTO) pageContext.findAttribute("oneline");
			     	if(dto == null){
			     		dto = new ServiceResourceDTO();
			     	}
			     	String resourceName = dto.getResourceName();
			     	if(resourceName==null){
			     		resourceName="";
			     	}
			     	pageContext.setAttribute("OnlyOneObject",dto);
			     	Integer newNumber = null;
			     	Integer usableNumber = null;
			     	Integer usedNumber = null;
			     	Integer lockedNumber = null;
			     	Integer cancelNumber = null;			     	
			    	Map nameMap = Postern.getResourceObjectNumByStatus(resourceName);
			    	if(nameMap !=null && !nameMap.isEmpty()){
			    		newNumber = (Integer)nameMap.get("N");
				     	usableNumber = (Integer)nameMap.get("A");
				     	usedNumber = (Integer)nameMap.get("U");
				     	lockedNumber = (Integer)nameMap.get("I");
				     	cancelNumber = (Integer)nameMap.get("L");
			    	}
			    	if(newNumber==null){
			    		newNumber = new Integer(0);
			    	}
			    	if(usableNumber==null){
			    		usableNumber = new Integer(0);
			    	}
			    	if(usedNumber==null){
			    		usedNumber = new Integer(0);
			    	}
			    	if(lockedNumber==null){
			    		lockedNumber = new Integer(0);
			    	}
			    	if(cancelNumber==null){
			    		cancelNumber = new Integer(0);
			    	}
			    	/*
			    		N 新建
				        A 可用
				        U 已用
				        I 取消
				        L 锁定
			    	*/
			     	long dtLastmod = (dto.getDtLastmod()==null)? System.currentTimeMillis() : dto.getDtLastmod().getTime();
				%>
				<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<tr>
						<td class="list_bg2" align="right" style="border-width:0" width="20%" >资源名称</td>						
						<td class="list_bg1" align="left" style="border-width:0" width="30%" ><input type="text" value="<tbl:write name='OnlyOneObject' property='resourceName' />" size=25 disabled="true" /></td>						
						<td class="list_bg2" align="right" style="border-width:0" width="20%" >资源类型</td>
						<td class="list_bg1" align="left" style="border-width:0" width="30%" ><d:selcmn name="xxx" mapName="SET_R_RESOURCETYPE" match="OnlyOneObject:resourceType" width="23" disabled="true" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" style="border-width:0">状态</td>
						<td class="list_bg1" align="left" colspan=3 style="border-width:0"><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="OnlyOneObject:status" width="23" disabled="true" /></td>						
						
					</tr>	
					<tr>
						<td class="list_bg2" align="right" width="20%" >资源描述</td>
						<td class="list_bg1" align="left" colspan="3" width="80%" ><input type="text" size=83 name="txtResourceDesc" value="<tbl:write name='OnlyOneObject' property='resourceDesc' />" width="100%" disabled="true" /></td>
					</tr>
					
					<tr width="100%" cellpadding="0" >
						<td colspan="5" width="100%" cellpadding="0" >

						</td>
					</tr>	
					<tr>
						<td align="right" colspan=4 > </td>
					</tr>
				</table>
				<br>
				<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<tr>
						<td align="center" class="list_head" >创建</td>
						<td align="center" class="list_head" >可用</td>
						<td align="center" class="list_head" >已用</td>
						<td align="center" class="list_head" >锁定</td>
						<td align="center" class="list_head" >取消</td>
					</tr>
					<tr>
						<td align="center" class="list_bg1" ><%= newNumber%> </td>
						<td align="center" class="list_bg1" ><%= usableNumber%>  </td>
						<td align="center" class="list_bg1" ><%= usedNumber%> </td>
						<td align="center" class="list_bg1" ><%= lockedNumber%> </td>
						<td align="center" class="list_bg1" ><%= cancelNumber%> </td>
					</tr>
					<tr>
    				<td colspan="5" class="list_foot"></td>
 	  			</tr> 	
				</table>
				<br>
				<!--<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="0" class="list_bg">
					<tr>
						<td colspan=5 >
							<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
								<tr>
							    	<td><img src="img/mao.gif" width="1" height="1"></td>
							  	</tr>
							</table>
						</td>
					</tr>
					<tr class="list_head">
						<td class="list_bg1" width="5%">创建</td>
						<td class="list_bg1" width="5%">可用</td>
						<td class="list_bg1" width="5%">已用</td>
						<td class="list_bg1" width="5%">锁定</td>
						<td class="list_bg1" width="5%">取消</td>
					</tr>
					<tr class="list_bg1" >
						<td class="list_bg1" width="5%" align="center" ><input type="text" style="text-align:center"  value="<%= newNumber%>" readonly /></td>
						<td class="list_bg1" width="5%" align="center" ><input type="text" style="text-align:center"  value="<%= usableNumber%>" readonly /></td>
						<td class="list_bg1" width="5%" align="center" ><input type="text" style="text-align:center"  value="<%= usedNumber%>" readonly /></td>
						<td class="list_bg1" width="5%" align="center" ><input type="text" style="text-align:center"  value="<%= lockedNumber%>" readonly /></td>
						<td class="list_bg1" width="5%" align="center" ><input type="text" style="text-align:center"  value="<%= cancelNumber%>" readonly /></td>
					</tr>	
					<tr>
						<td colspan=5 ></td>
					</tr>	
					<tr >
						<td colspan=5 >
							<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
								<tr>
							    	<td><img src="img/mao.gif" width="1" height="1"></td>
							  	</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan=5 >&nbsp</td>
					</tr>
				</table>-->	
				<input type="hidden" name="txtResourceName" value="<tbl:write name='OnlyOneObject' property='resourceName' />" />
				<input type="hidden" name="txtResourceType" value="<tbl:write name='OnlyOneObject' property='resourceType' />" />
				</lgc:bloop>   											
				<table align="center" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><img src="img/button2_r.gif" width="22" height="20"></td>
			    		<td background="img/button_bg.gif"><a href="javascript:backToEditingForUpdated()"  class="btn12">返&nbsp;回 </a></td>
		            	<td><img src="img/button2_l.gif" width="11" height="20"></td>
					    <td width="20" ></td>
					    <td><img src="img/button_l.gif" border="0" ></td>								
						<td background="img/button_bg.gif"><a href="javascript:addDetail()" class="btn12" >新增电话号码资源</a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>					   
					    <td width="20" ></td>          
					    <td><img src="img/button_l.gif" border="0" ></td>
					    <td background="img/button_bg.gif"><a href="javascript:queryDetail()" class="btn12">查询电话号码资源</a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>
					</tr>
				</table>
				<br>
				<!--<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
					<tr>
						<td><img src="img/mao.gif" width="1" height="1"></td>
				   	</tr>
				</table>-->
			</form>
		</td>
	</tr>
</table>
									