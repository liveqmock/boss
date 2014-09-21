<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<BR>
<%
       
       int iFlag=WebUtil.StringToInt(request.getParameter("func_flag"));
       String sName="";
       String sAdditionalInfo="具体处理情况如下：";
       Collection lst = null;
       
       CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");    
       if (CmdRep!=null)
       {
                 try
                 {
                     lst = (Collection)CmdRep.getPayload();
                 }
                 catch (Exception ex)
                 {}
       } 
    	     
         
       
       switch (iFlag)
       {
         
         case 102:              
             sName="出库信息";
             
             break;
         
         case 106:              
             sName="调拨信息";
             
             break; 
         case 107:              
             sName="入库信息";
             
             break; 
         case 108:              
             sName="送修信息";
  
                  
         case 110:              
             sName="报废信息";
             
             break;
         case 109:              
             sName="修改状态信息";
             
             break;
        
       }
       
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">提示信息</td>
  </tr>
</table>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<br>
		<lgc:haserror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_error.gif" width="182"
						height="182"></td>
					<td class="ok"><font color="red"><i>操作不成功，错误信息如下:</i></font>
					<br>
					<tbl:errmsg /></td>
				</tr>
			</table>
		</lgc:haserror> <lgc:hasnoterror>
			<table width="50%" border="0" cellspacing="10" cellpadding="0">
				<tr align="center">
					<td width="182"><img src="img/icon_ok.gif" width="182" height="182"></td>
					<td class="ok">
						创建<%=sName%>成功。<%=sAdditionalInfo%><br>
						      <table width="100%" border="0" cellspacing="1" cellpadding="3">
<%
     if (lst!=null)
     {
         Iterator it = lst.iterator();
         while (it.hasNext())
         {
%>  
        <tr> 
          <td width="80%" align="center" ><%=it.next()%></td>
          <td width="20%">&nbsp;</td>                
        </tr>
<%
         }
     }
   
%>  
      </table>
						</td>
				</tr>
			</table>
		</lgc:hasnoterror> <br>
		<br>
		<table width="50%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		

         
      <BR>
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          
<lgc:haserror>          
<%
       switch (iFlag)
       {
         
         case 102:
%>
       
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="menu_deviceoutstock.do" class="btn12">创建新出库信息</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
<%
          break; 
         
          case 106:
%>
       
          <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="menu_devicetransition_query.do" class="btn12">创建新调拨信息</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
<%
          break;
          case 107:
%>
       
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="menu_deviceinstock_query.do" class="btn12">创建新入库信息</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
<%
          break; 
          case 108:
%>
       
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="devicerep_query.do" class="btn12">创建新送修信息</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
<%
          break; 
          case 110:
%>
       
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="deviceinv_query.do" class="btn12">创建新报废信息</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
<%
          break; 
          case 109:
%>
       
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="devicestatus_query.do" class="btn12">创建新状态修改信息</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
<%
          break;  
          
       }
%>
</lgc:haserror>

<lgc:hasnoterror> 
<%
       switch (iFlag)
       {
         
          case 102:
%>
       
          <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="menu_deviceoutstock.do" class="btn12">创建新出库信息</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
<%
         break; 
         
          case 106:
%>
       
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="menu_devicetransition_query.do" class="btn12">创建新调拨信息</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
<%
          break;
          case 107:
%>
       
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="menu_deviceinstock_query.do" class="btn12">创建新入库信息</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
<%
         break;
         case 108:
%>
       
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="devicerep_query.do" class="btn12">创建新送修信息</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
<%
         break;
          case 110:
%>
       
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="deviceinv_query.do" class="btn12">创建新报废信息</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
<%
          break;
          
          case 109:
%>
       
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="devicestatus_query.do" class="btn12">创建新状态修改信息</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
<%
          break; 
          
          
       }
%>
</lgc:hasnoterror>
          

        </tr>
      </table>
      
      
