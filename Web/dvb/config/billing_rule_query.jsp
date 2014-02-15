<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.BillingRuleDTO,
               java.util.*" %>
 

<script language=javascript>
function query_submit()
{
	 
		document.frmPost.submit();
	 
}

function view_detail_click(strId)
{
	self.location.href="billing_rule_view.do?txtID="+strId;
}
 
function recacumulate_rule_submit()
{
	self.location.href="recacu_rule.do?func_flag="+202;
}
 
 
 
function create_config_submit() {
   
   document.frmPost.action="billing_rule_create.screen";
   document.frmPost.submit();
}
function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}
function ChangeReason()
{
    
    document.FrameUS.submit_update_select('geteventreason', document.frmPost.txtEventClass.value, 'txtEventReason', '');
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
    <td  class="title">计费规则管理-查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  <%
     Map envetMap = null;
     envetMap=Postern.getBillingSystemEvent("B");
     pageContext.setAttribute("EVENTMAP",envetMap);
     Map productMap = null;
     productMap = Postern.getAllProductIDAndName();
     pageContext.setAttribute("PRODUCTMAP",productMap);
     Map productPackageMap = null;
     productPackageMap = Postern.getAllProductPackageIDAndName();
     pageContext.setAttribute("PRODUCTPACKAGEMAP",productPackageMap);
     Map custList = Postern.getBillingRuleByCustType("C");
     pageContext.setAttribute("CUSTLIST",custList);
      Map accountList = Postern.getBillingRuleByCustType("A");
     pageContext.setAttribute("ACCOUNTLIST",accountList);
      Map  campaignList = Postern.getBillingRuleByCustType("M");
     pageContext.setAttribute("CAMPAIGNLIST",campaignList);
     Map catvTermType = Postern.getBillingRuleByCustType("TT");
        
     pageContext.setAttribute("CATVTERMTYPE",catvTermType);
     Map cableType = Postern.getBillingRuleByCustType("CT");
     
      pageContext.setAttribute("CABLETYPE",cableType);
      
       Map doubleFlag= Postern.getBillingRuleByCustType("BT");
      
     pageContext.setAttribute("DOUBLEFLAG",doubleFlag);
     
     //S	业务帐户类型列表
       Map serviceAccountList= Postern.getBillingRuleByCustType("S");
     pageContext.setAttribute("SERVICEACCOUNTLIST",serviceAccountList);
     
     //G	市场分区列表
       Map marketList = Postern.getBillingRuleByCustType("G");
     pageContext.setAttribute("MARKETLIST",marketList);
     
  %>

 
<form name="frmPost" method="post" action="billing_rule_query.do" >
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr> 
          <td  class="list_bg2"><div align="right">计费规则ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtID" maxlength="10" size="27"  value="<tbl:writeparam name="txtID" />" >
           </td>   
            <td class="list_bg2"><div align="right">事件类型</div></td>
	    <td class="list_bg1" align="left">
               <tbl:select name="txtEventClass" set="EVENTMAP" match="txtEventClass" width="25" onchange="ChangeReason()" />   
            </td>  
            
      </tr>
       <tr> 
         
           <td class="list_bg2"><div align="right">事件原因</div></td>
	   <td class="list_bg1" align="left">
		<d:selRes name="txtEventReason" parentMatch="txtEventClass" match="txtEventReason" width="25" />
	   </td>
	  <td class="list_bg2"><div align="right">运营商产品</div></td>
	   <td class="list_bg1" align="left" >
           <tbl:select name="txtProductID" set="PRODUCTMAP" match="txtProductID" width="25" />
	  </td>
      </tr>
      
      <tr> 
            <td class="list_bg2"><div align="right">运营商产品包</div></td>
	       <td class="list_bg1" align="left">
		 <tbl:select name="txtProductPackageID" set="PRODUCTPACKAGEMAP" match="txtProductPackageID" width="25" />
	      </td>
	       <td class="list_bg2"><div align="right">目标运营商产品</div></td>
	        <td class="list_bg1" align="left">
	   	<tbl:select name="txtDestProductID" set="PRODUCTMAP" match="txtDestProductID" width="25" />
	     </td>	   
         </tr>
          <tr> 
             
               <td class="list_bg2"><div align="right">客户类型条件列表</div></td>
	       <td class="list_bg1" align="left">
		  <tbl:select name="txtCustType" set="CUSTLIST" match="txtCustType" width="25" />
	      </td> 
	        <td class="list_bg2"><div align="right">帐户类型条件列表</div></td>
	       <td class="list_bg1" align="left">
		 <tbl:select name="txtAcctItem" set="ACCOUNTLIST" match="txtAcctItem" width="25" />
	   	 
	      </td>
         </tr>
         
         <tr>   
               <td class="list_bg2"><div align="right">业务帐户类型条件列表</div></td>
	      <td class="list_bg1" align="left">
		  <tbl:select name="txtUserType" set="SERVICEACCOUNTLIST" match="txtCustType" width="25" />
	      </td> 
               <td class="list_bg2"><div align="right">市场分区条件列表</div></td>
	       <td class="list_bg1" align="left" >
		 <tbl:select name="txtMarketSegmentID" set="MARKETLIST" match="txtAcctItem" width="25" />
	      </td>
         </tr>   
         
         <tr> 
              
             
	      <td class="list_bg2"><div align="right">促销活动条件列表</div></td>
	       <td class="list_bg1" align="left">
		 <tbl:select name="txtCampaign" set="CAMPAIGNLIST" match="txtCampaign" width="25" />
	   	 
	      </td> 
	         <td class="list_bg2"><div align="right">终端类型计费条件</div></td>
	       <td class="list_bg1" align="left">
		  <tbl:select name="txtCatvTermType" set="CATVTERMTYPE" match="txtCatvTermType" width="25" />
	      </td> 
	      
         </tr> 
         <tr> 
             
              <td class="list_bg2"><div align="right">线缆类型计费条件</div></td>
	       <td class="list_bg1" align="left">
	       <tbl:select name="txtCableType" set="CABLETYPE" match="txtCableType" width="25" />
		 
	   	 
	      </td>
	        <td class="list_bg2"><div align="right">双向网标记计费条件</div></td>
	       <td class="list_bg1" align="left">
		 <tbl:select name="txtDoubleFlag" set="DOUBLEFLAG" match="txtDoubleFlag" width="25" /> 
	   	 
	      </td>
         </tr>
            <tr> 
              
              
	        <td class="list_bg2"><div align="right">状态</div></td>
                <td class="list_bg1" colspan="3" align="left" >
                <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="25" /> 
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
                         <td background="img/button_bg.gif"><a href="javascript:create_config_submit()" class="btn12">新&nbsp;增</a></td>
                        <td><img src="img/button_r.gif" border="0" ></td>
                         <td width="20" ></td>
			 <td><img src="img/button_l.gif" border="0" ></td>
                         <td background="img/button_bg.gif"><a href="javascript:recacumulate_rule_submit()" class="btn12">重新计算计费规则</a></td>
                        <td><img src="img/button_r.gif" border="0" ></td>
        
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     <input type="hidden" name="varID" value=""/>
     
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
  
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
           <td class="list_head" width="7%">ID</td>
           <td class="list_head" width="10%">事件类型</td>
           <td class="list_head" width="10%">事件原因</td>
           <td class="list_head" width="12%">运营商产品</td>
           <td class="list_head" width="12%">运营商产品包</td>
            <td class="list_head" width="12%">客户类型条件</td>
            <td class="list_head" width="12%">市场活动条件</td>
             <td class="list_head" width="12%">帐户类型条件</td>
            <td class="list_head" width="7%">金额</td>
            <td class="list_head" width="10%">状态</td>
            
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
 String packgeName=null;
 String custTypeName=null;
 String campaignName=null;
 String productName=null;
 String acctName=null;
 BillingRuleDTO brDto = (BillingRuleDTO)pageContext.getAttribute("oneline");
 int eventClass = brDto.getEventClass();
 String eventName = Postern.getEventNameByEventClass(eventClass);
 int productID = brDto.getProductId();
  productName = Postern.getProductNameByID(productID);
  if(productName == null)
  productName ="";
 int packageID = brDto.getPackageId();
   packgeName = Postern.getPackagenameByID(packageID);
 if(packgeName == null)
   packgeName ="";
 int custTypeId = brDto.getBrcntIdCustType();
   custTypeName = Postern.getCntNameByID(custTypeId);
  if(custTypeName == null)
  custTypeName ="";
 int acctId = brDto.getBrcntIdAcctType();
   acctName  = Postern.getCntNameByID(acctId);
  if(acctName == null)
  acctName="";
 int  campaign = brDto.getBrcntIdCampaign();
    campaignName  = Postern.getCntNameByID(campaign);
    if(campaignName == null)
    campaignName="";
   String reason=brDto.getEventReason();
  
   String reasonName =Postern.getReasonByReasonCode(reason,eventClass);
    System.out.println("************reasonName**************"+reasonName);
   if(reasonName==null)
   reasonName="";
    
 
 
 %>
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      	      <td align="center"><%=eventName%></td>
      	      <td align="center"><%=reasonName%></td>
      	      <td align="center"><%=productName%></td>
      	      <td align="center"><%=packgeName%></td>
      	      <td align="center"><%=custTypeName%></td>
      	      <td align="center"><%=campaignName%> </td>     
      	      <td align="center"><%=acctName%></td>    
      	      <td align="center"><tbl:write name="oneline" property="value"/></td>  
      	        
      	     
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>      			
      	    
    	</tr>
</lgc:bloop>  

<tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="10" >
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
</table>   
 
	 
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
 
