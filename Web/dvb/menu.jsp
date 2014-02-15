<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>
<%@ page import="com.dtv.oss.dto.*,com.dtv.oss.util.Postern" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
 
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<script language=javascript>

 
function frm11_submit()
{
    if(document.form1.txtSystemModule.value=="N"){
     document.form1.action="interface_enter.do";
     } else {
    document.form1.action="customer_support_enter.do"; 
    document.form1.target="_top"
    }
    document.form1.submit();
    
} 
 
</script>
<%
HttpSession curSession=request.getSession();
if(curSession==null || pageContext.getSession().getAttribute(WebKeys.OPERATOR_SESSION_NAME)==null){

%>
<form name="menuform">
</form>
<script language=javascript>
 <!--
    document.menuform.action="signout.do"; 
    document.menuform.target="_top"
    document.menuform.submit();
//-->
</script>
<%
}%>

<%
	Map allModuleMap = Postern.getCommonSettingDataCache("SET_G_WEBMODULE","false","false",0);
	pageContext.setAttribute("allModule",allModuleMap);
  	String systemModulName=(String)request.getSession().getAttribute("SET_G_SYSTEMPLATFORM");
  	 
  	
		 
  	if (systemModulName==null ||"".equals(systemModulName))
		systemModulName="B";
	//pageContext.getSession().removeAttribute("dynamicRootNode");
	Map menuMap=(Map)request.getSession().getServletContext().getAttribute(CommonKeys.SYSTEM_CONFIG_MENU_KEY);
	Collection menuCollect=(Collection)menuMap.get(systemModulName);
%>

<table width="1003" border="0" cellspacing="0" cellpadding="0" background="img/mnu_bg.gif">
	<form name="form1">
	 <input type="hidden" name="from" value="interface"/>
     <tr>
	     <td width="14">&nbsp;</td>
	    
       <td width="50">
       	<tbl:select name="txtSystemModule" set="allModule" match="<%=systemModulName%>" width="8" onchange="javascript:frm11_submit();" />
          <!--d:selcmn name="txtSystemModule" mapName="SET_G_WEBMODULE" match="<%=systemModulName%>" width="8" onchange="javascript:frm11_submit();" /-->   
        </td>
         
        <td>
        	<table border="0" cellspacing="0" cellpadding="0">
			    <tr>
			 	<td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
			 		<%
			 			int colSize=menuCollect.size();
			 			int layerIndex=1;
			 			Iterator colIterator=menuCollect.iterator();
			 			while(colIterator.hasNext()){
			 				String showlayer=new String("'Layer"+layerIndex+"','','hide'");
			 				String showparam=WebUtil.compositeMenuParam(colSize,layerIndex);
			 				MenuConfigurationDTO menuDto=(MenuConfigurationDTO)colIterator.next();
			 		%>
			        <td width="106" align="center">
			        <table  border="0" cellspacing="4" cellpadding="0"  onMouseOut="MM_showHideLayers(<%=showlayer%>)" onMouseOver="MM_showHideLayers(<%=showparam%>)">
			          <tr>
			            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
			            <td width="93" align="center"><%=menuDto.getMenuName()%></td>
			          </tr>
			        </table>
			        </td>
			        <td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
			        <%
			        	layerIndex++;
			 			}
			 		
			 		%>
			   </tr>
			  </table>
			<%
			colIterator=menuCollect.iterator();
			int showwidth=127;
			layerIndex=1;  //重新设置值用来在后面的循环中使用
			while(colIterator.hasNext()){
				String layerName="Layer"+layerIndex;
				String hidderlayer=new String("'Layer"+layerIndex+"','','hide'");
				String showlayer=new String("'Layer"+layerIndex+"','','show'");
				MenuConfigurationDTO menuDto=(MenuConfigurationDTO)colIterator.next();
				
			%>
			<div id="<%=layerName%>" onMouseOut="MM_showHideLayers(<%=hidderlayer%>)" onMouseOver="MM_showHideLayers(<%=showlayer%>)" style="position:absolute; left:<%=showwidth%>px; top:92px; width:<%=menuDto.getMenuWidth()%>px; visibility:hidden" >
			      <table width="<%=menuDto.getSubMenuWidth()%>" border="0" cellpadding="3" cellspacing="1" class="menu" align="center" >
			      	<%
			      		List childMenuList=menuDto.getChildMenuList();
			      		Iterator childIter=childMenuList.iterator();
			      		int width = 0;
			      		int heigth = 0;
			      		while(childIter.hasNext()){
			      			MenuConfigurationDTO childDto=(MenuConfigurationDTO)childIter.next();
			      			String action="";
			      			if(childDto.getParameterList()==null ||"".equals(childDto.getParameterList()))
			      				action=childDto.getMenuAction();
			      			else
			      				action=childDto.getMenuAction()+"?"+childDto.getParameterList();
			      			if(action==null || "".equals(action))
			      				action="#";
			      			heigth++;
			      			width = childDto.getMenuName().length()>width?childDto.getMenuName().length():width;
			      	%>
			        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
			          <td valign="middle" nowrap><a href="<%=action%>" ><span class="submenu"> - <%=childDto.getMenuName()%></span></a></td>
			        </tr>
			        <%
			        }
			        	width = width*12+22;
			        	if(width<110)width=110;
			        	if(width<menuDto.getSubMenuWidth())width=menuDto.getSubMenuWidth();
			        	heigth = heigth*24;
			        	//if(hight<menuDto.getMenuHeigth())hight=menuDto.getMenuHeigth();

			        %>
			      </table>
			   <!--iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:<%=menuDto.getSubMenuWidth()%>px; height:<%=menuDto.getMenuHeigth()%>px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe-->
				 <iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:<%=width%>px; height:<%=heigth%>px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
			</div>
			<%
				showwidth+=menuDto.getMenuWidth();
				layerIndex++;
			}
			%>
        </td>
     </tr>
   </form>
</table>

