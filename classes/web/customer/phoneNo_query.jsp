<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.dto.ResourcePhoneNoDTO" %>

<Script language=JavaScript>
function choose_confirm(){
   var check_flag =false;
	if(document.frmPost.signradio.length ==2)
	{
	   if (document.frmPost.signradio[1].checked){    
	       self.opener.document.frmPost.phoneNo.value =document.frmPost.phoneno[1].value;
	       self.opener.document.frmPost.itemID.value =document.frmPost.itemID[1].value;
	       if(self.opener.document.frmPost.checkSelect != null)
	       {
	       	self.opener.document.frmPost.checkSelect.value ="1";
	       }
	       check_flag =true;   
	       window.close();
	       return;
		}
	}
	else
	{
		for (i=1 ;i< document.frmPost.signradio.length ;i++){
	       if (document.frmPost.signradio[i].checked){
	         
	           self.opener.document.frmPost.phoneNo.value =document.frmPost.phoneno[i].value;
	           self.opener.document.frmPost.itemID.value =document.frmPost.itemID[i].value;
	           if(self.opener.document.frmPost.checkSelect != null)
	           {
	           	self.opener.document.frmPost.checkSelect.value ="1";
	           }
	           check_flag =true;   
	           window.close();
	           return;
	       }
	   }
	}
   if (check_flag==false){
      alert("请选择电话号码！");
   }
}
</Script>


 <form name="frmPost" method="post" action="phoneNo_query.do" >
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">电话号码查询</td>
      </tr>
     </table>
     <br>
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
      </table>
      <br>
   
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
      <input type="hidden" name="signradio" value="">
      <input type="hidden" name="phoneno" value="" >
      <input type="hidden" name="itemID" value="" >
      
      <tbl:hiddenparameters pass="districtID" />
      <table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">     
           <td>选择</td> 
           <td>电话号码</td>
           <td>号码级别</td>
           <td>地区</td>
           <td>区号</td>     
           <td>国家号</td>
         </tr>    
        <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
          <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
           <td align="center"><input type="radio" name="signradio" value=""><input type="hidden" name="itemID" value='<tbl:write name="oneline" property="itemID"/>' ></td>
           <td align="center">           
             <tbl:write name="oneline" property="PhoneNo"/>
             <input type="hidden" name="phoneno" value='<tbl:write name="oneline" property="PhoneNo"/>' >
           </td>
           <td  align="center">
             	<d:getcmnname typeName="SET_R_PHONENOGRADE"	match="oneline:grade" />
           </td>
           <td  align="center">
             	<tbl:WriteDistrictInfo name="oneline" property="DistrictId" />
           </td>
           <td  align="center">
             <input type="hidden" name="areacode" value='<tbl:write name="oneline" property="AreaCode"/>' >
             <tbl:write name="oneline" property="AreaCode"/>
           </td>
           <td  align="center">
             <input type="hidden" name="countrycode" value='<tbl:write name="oneline" property="CountryCode"/>' >
             <tbl:write name="oneline" property="CountryCode"/>
           </td>
          </tbl:printcsstr>         
        </lgc:bloop>    
		    <tr>
		      <td colspan="7" class="list_foot"></td>
		    </tr>

        <rs:hasresultset>
          <tr class="trline" >
              <td align="right" class="t12" colspan="7" >
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
         </rs:hasresultset>                   
	</table>   
       </form>  
<BR>

<rs:hasresultset>
   <table align="center" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="next" type="button" class="button" onClick="javascript:choose_confirm()" value="确 认"></td>
        <td><img src="img/button_r.gif" width="22" height="20"></td>
        <td width="20" ></td>
        <td><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="prev" type="button" class="button" onClick="javascript:window.close()" value="关闭窗口"></td>
        <td><img src="img/button2_l.gif" width="11" height="20"></td>
       </tr>
    </table>
  
</rs:hasresultset> 

