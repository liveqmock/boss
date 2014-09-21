<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%
	String backUrl=(String)request.getAttribute("back_url");
	if(backUrl==null||backUrl.trim().length()==0){
		backUrl="menu_bidim_config.do";
	}	
%>
<SCRIPT language="JavaScript">
function back(){
    document.location.href= "<%= backUrl %>"
}
</SCRIPT language="JavaScript">    
      
<TABLE border="0" align="center" cellspacing="0" cellpadding="0" width="100%">
	<TR>
		<TD align="center">
			<table width="50%" border="0" align="center" cellpadding="0" cellspacing="10">
			  	<tr>
					<td class="title" align="center" valign="middle">
						<img src="img/list_tit.gif" width="19" height="19">&nbsp;&nbsp;提示信息
					</td>
			  	</tr>
			  	<tr>
			    	<td colspan="2" height="8"></td>
			  	</tr>
			</table>
		</TD>
	</TR>
	<TR>		
		<TD>
      		<table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        		<tr>
          			<td align="center"><img src="img/mao.gif" width="1" height="1"></td>
        		</tr>
      		</table>
      	</TD>  
   	</TR> 
 		<lgc:haserror>
 	<TR>
 		<TD>
 		<br>    
 			<table width="50%" border="0" cellspacing="10" cellpadding="0" align="center" >
        		<tr align="center">
          			<td width="182" align="center" ><img src="img/icon_error.gif" width="182" height="182" ></td>
          				<td class="ok" align="center" >
        					<font color="red" ><i>出现错误：</i></font>
							<br><tbl:errmsg />
						</td>
        		</tr>
      		</table>
      	</TD>
    </TR>
		</lgc:haserror>
		<lgc:hasnoterror>
	<TR>
		<TD>
      		<table width="50%" border="0" cellspacing="10" cellpadding="0" align="center" >
        		<tr align="center">
          			<td width="182" align="center" ><img src="img/icon_ok.gif" width="182" height="182" align="center" ></td>
          			<td class="ok" align="center" >
          				操作成功。
					</td>
        		</tr>
      		</table>
      	<TD>
    </TR>
		</lgc:hasnoterror>    
	<TR>
		<TD>
		<br>
      		<table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        		<tr>
          			<td align="center"><img src="img/mao.gif" width="1" height="1"></td>
        		</tr>
      		</table>
      	</TD>
      </TR>
      <TR>
		<TD>
		<BR>
       		<table align="center"  border="0" cellspacing="0" cellpadding="0">
         		<tr align="center">
         		<td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button"
						value="返&nbsp;回" onclick="javascript:back()"></td>
            		<td><img src="img/button_r.gif" width="22" height="20"></td>
            		<td width="20" ></td>  
        		</tr>
      		</table>
      	</TD>
    </TR>
</table>
