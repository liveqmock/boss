<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
 
function package_detail_submit(){

   document.frmPost.action ="config_segment_package.do";
   fillList();
   document.frmPost.submit();    
	  
}
function fillList()
{
        document.frmPost.SegmentList.value="";
	 
	if (document.frames.FrameMarketSegment.listID!=null)
	{
        if (document.frames.FrameMarketSegment.listID.length > 1) {
	    for (i=0;i<document.frames.FrameMarketSegment.listID.length;i++)
		if (document.frames.FrameMarketSegment.listID[i].checked)
		{
			if (document.frmPost.SegmentList.value!="")
		        document.frmPost.SegmentList.value = document.frmPost.SegmentList.value + ";";
			document.frmPost.SegmentList.value=document.frmPost.SegmentList.value + document.frames.FrameMarketSegment.listID[i].value;
		}
        } else {
            if (document.frames.FrameMarketSegment.listID.checked) {
                document.frmPost.SegmentList.value=document.frames.FrameMarketSegment.listID.value + ";";
            } else {
                document.frmPost.SegmentList.value = '';
            }
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
    <td class="title">产品包市场分区信息</td>
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
     <tr> 
          <td  class="list_bg2"><div align="right">产品包ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtPackageID" size="25"  value="<tbl:write name="oneline" property="packageID" />" class="textgray" readonly >
           </td>   
            <td  class="list_bg2"><div align="right">产品包名称</div></td>         
            <td class="list_bg1" align="left">
           <input type="text" name="txtPackageName" size="25" value="<tbl:write name="oneline" property="packageName" />" class="textgray" readonly >
           </td>         
       </tr>
      
        <tr>  
             <td class="list_bg2"><div align="right">有效开始时间</div></td>
             <td class="list_bg1" align="left">
             <input type="text" name="txtDateFrom" size="25" maxlength="10" class="textgray" readonly value="<tbl:writedate name="oneline" property="DateFrom" />" > 
             </td>
             <td class="list_bg2"><div align="right">有效结束时间</div></td>
               <td class="list_bg1" align="left">
             <input type="text" name="txtDateTo" size="25" maxlength="10" class="textgray" readonly value="<tbl:writedate name="oneline" property="DateTo" />"> 
     	  </td>
      </tr>
   </table>
   
 <input type="hidden" name="func_flag" value="5089" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="SegmentList" value="">
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr>
      <td class="import_tit" align="left" colspan="4"><font size="3">选择市场分区列表</font></td>
    </tr>
   <tr> 
   <%
     String packageID=request.getParameter("txtPackageID");
   %>
      <td class="list_bg2" align="center" colspan="4"><font size="2">
        <iframe SRC="list_segment_for_package.do?&txtPackageId=<%=packageID%>" name="FrameMarketSegment" width="600" height="300"></iframe>
      </font></td> 
    </tr>
   </table> 
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
                
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="package_query_result.do/product_package_detail.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>   
            <td width="20" ></td>
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="确&nbsp;认" onclick="javascript:package_detail_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>  
            </td>
      </table>   
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
   </lgc:bloop>
</form>
