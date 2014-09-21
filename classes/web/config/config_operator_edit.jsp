<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>
 <%@ page import="com.dtv.oss.dto.*" %> 
 <%@ page import="com.dtv.oss.service.util.CryptoUtility" %> 

<script language=javascript>
function check_frm()
{
	
 	
    if (check_Blank(document.frmPost.txtOperatorName, true, "����Ա����"))
	    return false;
    if (check_Blank(document.frmPost.txtLevel, true, "��Ȩ����"))
	    return false;	    
    if (check_Blank(document.frmPost.txtLoginPwd, true, "����"))
	    return false;
   if (check_Blank(document.frmPost.txtLoginID, true, "��¼�ʺ�"))
	    return false;
	    
	    
     return true;
}
 
function confirm_password()
{
	
 	
    if (document.frmPost.txtLoginPwd.value == document.frmPost.txtSecondLoginPwd.value)
	    return true;
    else {
     alert("���벻һ��,����������");
     document.frmPost.txtLoginPwd.focus();
     return false;
     }
} 
 
 
function operator_modify_submit(){
 if (window.confirm('��ȷ��Ҫ�޸ĸò���Ա��?')){
  if (check_frm()&& confirm_password()){
	    document.frmPost.action="config_modify_operator.do";
	    document.frmPost.Action.value="MODIFY";
	    document.frmPost.submit();
	  }
}
}
 
</script>
 
 
 
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">����Ա�޸�</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
 
  
   <%
     OperatorDTO dto = (OperatorDTO) pageContext.getAttribute("oneline");
     String enPwd = dto.getLoginPwd();
     String originalPwd = CryptoUtility.depwd(enPwd);
     int orgID = dto.getOrgID();
      String internalFlag= dto.getInternalUserFlag();
     String orgName = Postern.getOrgNameByOrgID(orgID);
     if (orgName == null) orgName ="";
  
        if("Y".equals(internalFlag)){
          
          %>
       <tr> 
         <td  class="list_bg2"><div align="right">����ԱID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOperatorID" size="25" value="<tbl:write name="oneline" property="OperatorID"/>" class="textgray" readonly>
           </td>  
           <td class="list_bg2"><div align="right">ϵͳ�ڲ��û���־</div></td>
           <td class="list_bg1" align="left" >
            <input type="text" name="txtInternalUserFlagName"   size="25"  class="textgray" readonly value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:internalUserFlag"/>" >
          
       </tr>
       <tr>
          <td  class="list_bg2"><div align="right">����Ա����*</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOperatorName" size="25" maxlength="25"  class="textgray" readonly value="<tbl:write name="oneline" property="OperatorName"/>" >
           </td>  
         <td class="list_bg2"><div align="right">��¼�ʺ�*</div></td>
           <td class="list_bg1" align="left">
          <input type="text" name="txtLoginID"  size="25" maxlength="50"   class="textgray" readonly  value="<tbl:write name="oneline" property="LoginID"/>">
          </td>
          
      </tr>
      
      <tr>
          <td class="list_bg2"><div align="right">��&nbsp;&nbsp;��*</div></td>
           <td class="list_bg1" align="left">
           <input name="txtLoginPwd" type="password"   maxlength="50" value="<%=originalPwd%>" style="width:187px;"></td>
          </td>
           <td class="list_bg2"><div align="right">����ȷ��</div></td>
           <td class="list_bg1" align="left">
           <input name="txtSecondLoginPwd" type="password"  size="25"   value="<%=originalPwd%>" style="width:187px;"></td>
           </td>
        
        
      
      <tr>
       <td class="list_bg2"><div align="right">��Ȩ����</div></td>
           <td class="list_bg1" align="left" >
           <input type="text" name="txtLevelName" size="25"   class="textgray" readonly
           value="<d:getcmnname  typeName="SET_S_OPERATORLEVEL" match="oneline:levelID"/>" ></td>
              <input type="hidden" name="txtLevel"   value="<tbl:write name="oneline" property="levelID" />">
          </td>     
       <td class="list_bg2" align="right">������֯</td>
            <td class="list_bg1">
    	    <input type="hidden" name="txtOrgID" value="<tbl:write name="oneline" property="orgID"/>">
	    <input type="text" name="txtOrgName" size="25" maxlength="50" readonly  class="textgray" value="<tbl:WriteOrganizationInfo name="oneline" property="orgID"/>" >
	    
       </td>  
         
         
      </tr>
       <tr>
        <td class="list_bg2"><div align="right">״̬</div></td>
           <td class="list_bg1" align="left">
            <input type="text" name="txtStatusName" size="25"   class="textgray" readonly
           value="<d:getcmnname  typeName="SET_G_GENERALSTATUS" match="oneline:Status"/> "> </td>
         <input type="hidden" name="txtStatus"  value="<tbl:write name="oneline" property="status" />">
          </td>  
          <td class="list_bg2"><div align="right">����Ա����</div></td>
           <td class="list_bg1" align="left">
          <input type="text" name="txtOperatorDesc" maxlength="100" size="83"  readonly  class="textgray" value="<tbl:write name="oneline" property="OperatorDesc"/>">
          </td>    
          
         
      </tr>
<%} else {%>
      <tr> 
         <td  class="list_bg2"><div align="right">����ԱID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOperatorID" size="25" value="<tbl:write name="oneline" property="OperatorID"/>" class="textgray" readonly>
           </td>  
           <td class="list_bg2"><div align="right">ϵͳ�ڲ��û���־</div></td>
           <td class="list_bg1" align="left" >
            <input type="text" name="txtInternalUserFlagName"   size="25"  class="textgray" readonly value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:internalUserFlag"/>" >
          
       </tr>
       <tr>
          <td  class="list_bg2"><div align="right">����Ա����*</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOperatorName" size="25" maxlength="25" value="<tbl:write name="oneline" property="OperatorName"/>" >
           </td>  
         <td class="list_bg2"><div align="right">��¼�ʺ�*</div></td>
           <td class="list_bg1" align="left">
          <input type="text" name="txtLoginID"  size="25" maxlength="25"  value="<tbl:write name="oneline" property="LoginID"/>">
          </td>
          
      </tr>
      
      <tr>
          <td class="list_bg2"><div align="right">��&nbsp;&nbsp;��*</div></td>
           <td class="list_bg1" align="left">
           <input name="txtLoginPwd" type="password"   maxlength="25" value="<%=originalPwd%>" style="width:187px;"></td>
          </td>
           <td class="list_bg2"><div align="right">����ȷ��</div></td>
           <td class="list_bg1" align="left">
           <input name="txtSecondLoginPwd" type="password"  size="25"   value="<%=originalPwd%>" style="width:187px;"></td>
           </td>
        
       </tr>
      <tr>
          <td class="list_bg2"><div align="right">��Ȩ����</div></td>
           <td class="list_bg1" align="left" >
           <d:selcmn name="txtLevel" mapName="SET_S_OPERATORLEVEL" match="oneline:levelID" width="23" /></td>
               
           
          <td class="list_bg2" align="right">������֯</td>
            <td class="list_bg1">
    	    <input type="hidden" name="txtOrgID" value="<tbl:write name="oneline" property="orgID"/>">
	    <input type="text" name="txtOrgName" size="25" maxlength="50" readonly  class="text" value="<tbl:WriteOrganizationInfo name="oneline" property="orgID"/>" >
	    <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,D,P,B,O,S','txtOrgID','txtOrgName')">
          </td>
         
      </tr>
       <tr>
       
          <td class="list_bg2"><div align="right">״̬</div></td>
           <td class="list_bg1" align="left">
           <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="23" />
          </td>
          <td class="list_bg2"><div align="right">����Ա����</div></td>
           <td class="list_bg1" align="left">
          <input type="text" name="txtOperatorDesc" maxlength="100" size="25"   value="<tbl:write name="oneline" property="OperatorDesc"/>">
          </td>    
          
         
      </tr>
       <%}%> 
 </table>
 
 <input type="hidden" name="func_flag" value="198" >
  <input type="hidden" name="Action" value=""/>
   
   <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
  <input type="hidden" name="txtInternalUserFlag"   value="<tbl:write name="oneline" property="internalUserFlag" />">
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="operator_query.do/all_operator_query_result.do" />" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
            
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:operator_modify_submit()" class="btn12">��&nbsp;��</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
           <td width="20" ></td>
            <%
           if(!"Y".equals(internalFlag)){
          
          %>
            <td><img src="img/button_l.gif" border="0" ></td>
              
            <td background="img/button_bg.gif"><a href="operator_to_privilege_config.screen?txtOrgName=<%=orgName%>&txtOperatorID=<tbl:write name="oneline" property="operatorID"/>
&txtOperatorName=<tbl:write name="oneline" property="operatorName"/>&txtOperatorDesc=<tbl:write name="oneline" property="operatorDesc"/>" class="btn12">��������Ա��</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>  
            
           
        <%}%>    
	
        </tr>
      </table>   
  
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
    </lgc:bloop>      
</form>
