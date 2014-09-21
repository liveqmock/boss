<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.util.Postern,
       java.util.*,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
function query_submit()
{
	 
	    
		document.frmPost.submit();
	 
}
 
	
function view_detail_click(strId,strId1)
{
	self.location.href="device_purpose_detail_config.do?txtSeqNo="+strId+"&txtCsiType="+strId1;
}
function create_csireason_submit()
{
	self.location.href="csi_reason_device_create.screen";
}
function ChangeSubType(){    
    document.FrameUS.submit_update_select('configcsireason', document.frmPost.txtCsiType.value, 'txtCsiCreateReason', '');   
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
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">设备用途记录查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="csi_reason_device_query.do" >
<%
    Map reasonMap = Postern.getCsiReason();
   
    pageContext.setAttribute("REASONMAP",reasonMap);

%>
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr>
          <td class="list_bg2"><div align="right">受理单类型</div></td>
          <td class="list_bg1"> 
            <o:selcmn name="txtCsiType" mapName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="txtCsiType" width="23" onchange="ChangeSubType()"/>
           </td>
           <td class="list_bg2"><div align="right">受理原因 </div></td>
	      <td class="list_bg1" align="left">
	      	<o:selcsireason name="txtCsiCreateReason" paraClass="txtCsiType" match="txtCsiCreateReason" width="23"  />
            </td>           
            
         </tr>
          <tr> 
          
           
         <td  class="list_bg2"><div align="right">状态</div></td>
           <td class="list_bg1" colspan="3">
            <o:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
            
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
                         <td background="img/button_bg.gif"><a href="javascript:create_csireason_submit()" class="btn12">新&nbsp;增</a></td>
                        <td><img src="img/button_r.gif" border="0" ></td>
                         
        
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
      <input type="hidden" name="txtFrom"  value="1">
      <input type="hidden" name="txtTo"  value="10">
    
      
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  <br>
   <rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head">流水号</td>
          <td class="list_head">受理单类型</td>
          <td class="list_head">创建原因</td>
            <td class="list_head">设备用途</td>
           <td class="list_head" >状态</td>
        </tr>

       
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%
 CsiTypeReason2DeviceDTO dto = (CsiTypeReason2DeviceDTO) pageContext.findAttribute("oneline");
  String keyValue="";
  String reason=dto.getCsiCreateReason();
  if(reason!=null)
    keyValue = (String)reasonMap.get(dto.getCsiCreateReason());
  if(keyValue==null)
     keyValue="";
  String custTypeList = dto.getReferPurpose();
         String totalValue="";
         System.out.println("the ReferPurpose is "+ custTypeList);
                 if(custTypeList!=null)
                 { 
                     String[] custArray=custTypeList.split(",");
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueListByNameKey("SET_D_DEVICEUSEFORPURPOSE",custArray[j]);
                     if(totalValue=="")
                      totalValue=value;
                     else 
                       totalValue=totalValue+","+value;
                     }
                     
                 }

%>
 
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		   <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqNo"/>','<tbl:write name="oneline" property="csiType"/>')" class="link12" ><tbl:write  name="oneline" property="seqNo"/></a></td>
		  <td align="center"><o:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="oneline:csiType" /></td>
		   <td align="center"><% if (reason!=null && keyValue!=null) {%>
		   <tbl:write name="oneline" property="csiCreateReason"/>:<%=keyValue%>
		   <%}  else  {%>
		    <tbl:write name="oneline" property="csiCreateReason"/>
		    <%} %>
		   </td>     
		    
      		    <td align="center"><%=totalValue%></td>
      		     <td align="center"><o:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" /></td>
    		</tr>
 
</lgc:bloop>
  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
   
  
   <tr class="trline" >
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