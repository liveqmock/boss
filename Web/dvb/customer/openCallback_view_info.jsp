<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import=" com.dtv.oss.util.Postern ,
                  java.util.* " %>

<form name="frmPost" method="post" action="callback_create_op.do" >
      <input type="hidden" name="txtSaveStates" value=""/>
      <table align="center" width="820" border="0" cellspacing="1" cellpadding="1" class="list_bg" >
      <table  border="0" align="center" cellpadding="0" cellspacing="5">
       <tr>
         <td align="center" class="title1" colspan="4">
           <img src="img/list_tit.gif" width="19" height="19"><font size="4"><strong>&nbsp;查看开户受理单明细</strong></font>
         </td>
       </tr>
      </table>
      <br>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
           <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="820"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
         <td valign="middle" class="list_bg2" align="right" width="18%" >受理单编号</td>
         <td width="32%" class="list_bg1"><font size="2">
            <tbl:writeparam name="txtID" />
         </font></td>
         <td valign="middle" class="list_bg2" align="right" width="18%" >类型</td>
         <td width="32%" class="list_bg1"><font size="2">
           <tbl:writeparam name="txtTypeShow" />
         </font></td>
       </tr>
       <tr>
	       <td valign="middle" class="list_bg2" align="right" >联系人</td>
	       <td class="list_bg1"><font size="2">
	          <tbl:writeparam name="txtContactPerson" />
	       </font></td>
	       <td valign="middle" class="list_bg2" align="right" >性别</td>
	       <td class="list_bg1"><font size="2">
	          <tbl:writeparam name="txtGeneder" />
	       </font></td>
       </tr>
       <tr>
	       <td valign="middle" class="list_bg2" align="right" >联系电话</td>
	       <td class="list_bg1" colspan="3"><font size="2">
	         <tbl:writeparam name="txtContactPhone" />
	       </font></td>
       </tr>
       <%
           String txtType =request.getParameter("txtType");
           int txtID = (request.getParameter("txtID")==null) ? 0 : Integer.parseInt(request.getParameter("txtID"));
           Map mp =new HashMap();
           if (txtType.equals("UO") || txtType.equals("BK") || txtType.equals("OS") || txtType.equals("OB")|| txtType.equals("BU")){
               mp = Postern.getCallbackInfo("O", txtID);
           }
          pageContext.setAttribute("mp",mp);
          String tt="mp";
       %>
       
	<%
	 if(mp!=null && !mp.isEmpty()){
	%>
       <tbl:dynamicservey serveyType="C" serveySubType="O" showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" match="<%=tt%>"/>
	<%
	}
	%>  
      </table>
      <br>
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td width="20" ></td>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td background="img/button_bg.gif" ><a href="<bk:backurl property="service_interaction_view.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" width="12" height="20"></td>
       </tr>
     </table>
</form>