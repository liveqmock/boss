<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CatvTerminalDTO" %>


<Script language=Javascript>

</Script>

<form name="frmPost" method="post" action="open_create_info.do" >
    <table width="98%" border="0" cellspacing="5" cellpadding="5" align="center">
      <tr>
        <td colspan="4" class="import_tit" >门店开户－<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></td>
      </tr>
    </table>
    <BR>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
   <%
    CatvTerminalDTO dto = (CatvTerminalDTO)pageContext.getAttribute("oneline");

    String strCounty = "";
    
    String strStreetStation = "";   
    
   %>   	
      <table align="center" width="98%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
        <tr> 
          <td valign="middle" class="list_bg2" width="17%"  align="right">&nbsp;<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></td>
          <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="oneline" property="CatvID" />
          </font></td>
          <td valign="middle" class="list_bg2" width="17%" align="right">区(县)</td>
          <td width="33%" class="list_bg1"><font size="2">
             <%=strCounty%>
           </font></td>
        </tr>
        <tr> 
          <td valign="middle" class="list_bg2" align="right">街道(镇)</td>
          <td class="list_bg1"><font size="2">
             <%=strStreetStation%>
          </font></td>
          <td valign="middle" class="list_bg2" align="right">光节点</td>
          <td class="list_bg1"><font size="2">&nbsp;
          </font></td>
        </tr>
        <tr> 
          <td valign="middle" class="list_bg2" align="right">详细地址</td>
          <td class="list_bg1"><font size="2">
             <tbl:write name="oneline" property="DetailedAddress"/>
          </font></td>
          <td valign="middle"  width="17%" class="list_bg2" align="right">邮编 </td>
          <td width="33%" class="list_bg1"><font size="2">        
            <tbl:write name="oneline" property="Postcode"/>
          </font></td>
        </tr>
      </table>
       <BR>
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>        
           <td width="20" ></td>
           <td><img src="img/button_l.gif" width="11" height="20"></td>
           <td>
           <input name="prev" type="button" class="button" onClick="javascript:window.history.back()" value="返&nbsp;回">
           </td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
           
        </tr>
      </table>
</lgc:bloop>        
</form>
         