<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>
  <%@ page import="com.dtv.oss.dto.*" %> 

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtOpGroupName, true, "����Ա������"))
	    return false;
     return true;
}
 
function opgroup_modify_submit(){
 if (window.confirm('��ȷ��Ҫ�޸ĸò���Ա����?')){
  if (check_frm()){
	    document.frmPost.action="modify_opgroup.do";
	    document.frmPost.Action.value="MODIFY";
	    document.frmPost.submit();
	  }
}
}
 
    
 
 

 
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          ���ڻ�ȡ���ݡ�����
          </font>
          </td>
        </tr>
    </table>
</div>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">����Ա���޸�</td>
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
<%
     OpGroupDTO dto = (OpGroupDTO) pageContext.getAttribute("oneline");
     String systemFlag= dto.getSystemFlag();
%> 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr> 
          <td  class="list_bg2"><div align="right">����Ա��ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOpGroupID" size="25"  value="<tbl:write name="oneline" property="OpGroupID" />" class="textgray" readonly >
           </td> 
            <td  class="list_bg2"><div align="right">����Ա������*</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOpGroupName" size="25"  maxlength="50" value="<tbl:write name="oneline" property="OpGroupName" />" >
           </td>         
            
      </tr>
      <tr>  
           
           <td  class="list_bg2"><div align="right">����Ա�鼶��</div></td>         
             <td class="list_bg1" align="left">
             <d:selcmn name="txtOpGroupLevel" mapName="SET_S_OPGROUPLEVEL" match="oneline:OpGroupLevel" width="23" />
             </td> 
              <td class="list_bg2"><div align="right">ϵͳ�ڲ����־</div></td>
           <td class="list_bg1" align="left" >
            <input type="text" name="txtSystemFlagName" size="25"  class="textgray" readonly value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:systemFlag"/>" >
            
          </td>          
      </tr>
       <tr>
          <td class="list_bg2"><div align="right">����Ա������</div></td>
           <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtDescription" maxlength="100" size="83"   value="<tbl:write name="oneline" property="opGroupDesc"/>">
          </td>    
          
         
      </tr>
      
 </table>
 <input type="hidden" name="func_flag" value="179" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
   <input type="hidden" name="txtSystemFlag"   value="<tbl:write name="oneline" property="systemFlag" />">
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table> 
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="opgroup_query.do" />" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>  
            <%
           if(!"Y".equals(systemFlag)){
          
          %>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:opgroup_modify_submit()" class="btn12">��&nbsp;��</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
            <td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
             
            <td background="img/button_bg.gif"><a href="operator_group_privilege.screen?txtOpGroupID=<tbl:write name="oneline" property="opGroupID"/>
&txtOpGroupName=<tbl:write name="oneline" property="opGroupName"/>&txtOpGroupDesc=<tbl:write name="oneline" property="opGroupDesc"/>" class="btn12">����Ա��Ȩ��</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
            <td width="20" ></td>  
             <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="operator_group_member.screen?txtOpGroupID=<tbl:write name="oneline" property="opGroupID"/>
&txtOpGroupName=<tbl:write name="oneline" property="opGroupName"/>&txtOpGroupDesc=<tbl:write name="oneline" property="opGroupDesc"/>" class="btn12">����Ա���Ա</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
            <%}%>
         </tr>
      </table>   
    
       <br>      
   </lgc:bloop>
</form>
