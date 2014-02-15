<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 
<%@ page import="com.dtv.oss.util.Postern" %>

<%@ page import="com.dtv.oss.dto.UserPointsCumulatedRuleDTO" %>

<script language=javascript>

function check_frm()
{
	
 	
    if (!check_Num(document.frmPost.txtID, true, "流水号"))
	    return false;
    
	    
	    
     return true;
}
function query_submit()
{
	  if(check_frm())
		document.frmPost.submit();
	 
}

function view_detail_click(strId)
{
	self.location.href="cumulated_detail.do?txtID="+strId;
}
 

 
 
function detail_delete(id) {
     
     if( confirm("确定要删除该记录吗?") ){
    document.frmPost.vartxtDetailID.value = id
    document.frmPost.action="config_cumulated_delete_detail.do?Action=deleteDetail";
    document.frmPost.submit();
    }
} 
function create_camulate_rule_submit() {
    
   document.frmPost.action="config_points_cumulated_create.screen";
   document.frmPost.submit();
}
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">积分累加规则查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="query_points_acumulate_rule.do" >
 <%
     Map envetMap = null;
     envetMap=Postern.getAllSystemEvent();
     pageContext.setAttribute("EVENTNAME",envetMap);
     Map productMap = null;
     productMap = Postern.getAllProductIDAndName();
     pageContext.setAttribute("PRODUCTMAP",productMap);
 %>
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr> 
          <td  class="list_bg2"><div align="right">流水号</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtID" maxlength="10" size="25"  value="<tbl:writeparam name="txtID" />" >
           </td>      
           <td class="list_bg2"><div align="right">事件类型</div></td>
	   <td class="list_bg1" align="left">
               <tbl:select name="txtCondEvent" set="EVENTNAME" match="txtCondEvent" width="23" />   
            </td>
          </tr>
        </tr>
         <tr>  
            <td  class="list_bg2"><div align="right">产品名称</div></td>         
             <td class="list_bg1" align="left">
             <tbl:select name="txtProductID" set="PRODUCTMAP" match="txtProductID" width="23" />   
             </td>      
             <td class="list_bg2"><div align="right">状态</div></td>
               <td class="list_bg1" align="left">
                <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
           
     	</td>
    </tr>
        
          
      </table>
       <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			 <td width="20" ></td>
          
			 <td><img src="img/button_l.gif" border="0" ></td>
                         <td background="img/button_bg.gif"><a href="javascript:create_camulate_rule_submit()" class="btn12">创&nbsp;建</a></td>
                       <td><img src="img/button_r.gif" border="0" ></td>
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     <input type="hidden" name="vartxtDetailID" value=""/>
     
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
  
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
           <td class="list_head">流水号</td>
           <td class="list_head">客户类型</td>
           <td class="list_head">产品名称</td>
           <td class="list_head">累加上限</td>
           <td class="list_head">累加下限</td>
           <td class="list_head">增加积分</td>
            <td class="list_head">事件类型</td>
           <td class="list_head">状态</td>
           <td class="list_head"><div align="center">操作</div></td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
         UserPointsCumulatedRuleDTO dto = (UserPointsCumulatedRuleDTO) pageContext.findAttribute("oneline");
         String custTypeList = dto.getCustTypeList();
         String totalValue="";
         System.out.println("the type of the customer is "+ custTypeList);
                 if(custTypeList!=null)
                 { 
                     String[] custArray=custTypeList.split(",");
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueListByNameKey("SET_C_CUSTOMERTYPE",custArray[j]);
                     if(totalValue=="")
                      totalValue=value;
                     else 
                       totalValue=totalValue+","+value;
                     }
                     
                 }
           String productName ="";
           int productID=dto.getProductID();
           if (productID>0)
            productName =Postern.getProductNameByID(productID);  
            if(productName == null)
            productName = ""; 
            int condEvent = dto.getCondEvent();
            String eventName = Postern.getEventNameByEventClass(condEvent);
            String condVal1="";
            String condVal2="";
            if(dto.getCondValue1()>0)
                   condVal1=String.valueOf(dto.getCondValue1());
             if(dto.getCondValue2()>0)
                   condVal2= String.valueOf(dto.getCondValue2());        
                 %>
 
	 <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	     <td align="center" width="8%" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      	     <td align="center"  ><%=totalValue%></td>
      	    <td align="center" width="8%" ><%=productName%></td>
      	     <td align="center" width="8%" ><%=condVal1%></td>
      	      <td align="center" width="8%" ><%=condVal2%></td>
      	      <td align="center" width="8%" ><tbl:write name="oneline" property="addedPoints"/></td>
      	      <td align="center" width="8%" ><%=eventName%></td>
      	     <td align="center" width="5%" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>      			
      	     <td align="center" width="5%" >
             <A href="javascript:detail_delete('<tbl:write name="oneline" property="id"/>')">删除</A</td>
    </tbl:printcsstr>
</lgc:bloop>  

<tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
 <tr>
              <td align="right" class="t12" colspan="9" >
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
               <a href="javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>   
 
   
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
 
