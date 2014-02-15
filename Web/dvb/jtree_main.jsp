<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="operator" prefix="oper"%>

<%@ page import="com.dtv.oss.web.util.*"%>

<%
	 String strShowType=request.getParameter("showType");
	 String strType=request.getParameter("dsType");
	 String strId=request.getParameter("dsID");
	 String strDesc=request.getParameter("dsDesc");
	 String labelType=request.getParameter("labType");
	 System.out.println("strShowType"+strShowType);
	 System.out.println("strType"+strType);
	 System.out.println("strId"+strId);
	 System.out.println("strDesc"+strDesc);
	  System.out.println("labelType"+labelType);
%>
<script language ="javascript">
	function ok_submit(){
		var vid=document.frames("dFrame").document.getElementById('districtID').value;
		 
		  s =vid.split(",");
		  
		  var sr='';
		  for(i=0;i<s.length;i++){
		 
		    if(s[i]!='')
		      sr+=s[i]+",";
		   
		  } 
		 
		 sf= sr.substring(0,sr.length - 1);
		 
		  vid=sf;
		  
	         
		var vdesc=document.frames("dFrame").document.getElementById('districtDesc').value ;
		 m=vdesc.split(",");
		 var rt='';
		 
		  for(i=0;i<m.length;i++){
		   if(document.districtPost.txtFlag.value=="checkbox"){
		    tt= m[i].split("◇");
		    
		    m[i]=tt[tt.length-1];
		   }
		    if(m[i]!='')
		      rt+=m[i]+",";
		   
		  } 
		 mm=rt.substring(0,rt.length - 1);
		 
		 vdesc=mm;
		 
	 //	alert("vid:"+vid+"\nvdesc"+vdesc);
		if(vid==null||vid==''||vdesc==null||vdesc==''){
			alert('没有选择任何内容');
			return;
		}
//		window.opener.frmPost.elements['<%=strId%>'].value=vid;
//		window.opener.frmPost.elements['<%=strDesc%>'].value=vdesc;
		var idobj=eval('window.opener.frmPost.<%=strId%>');
		idobj.value=vid;
		var descobj=eval('window.opener.frmPost.<%=strDesc%>');
		descobj.value=vdesc;
		cancel_submit();		
	}

	function cancel_submit(){
		window.close();
	}
	
	function reset_submit(){
//		window.opener.frmPost.elements['<%=strId%>'].value='';
//		window.opener.frmPost.elements['<%=strDesc%>'].value='';
		var idobj=eval('window.opener.frmPost.<%=strId%>');
		idobj.value='';
		var descobj=eval('window.opener.frmPost.<%=strDesc%>');
		descobj.value='';
		window.close();
	}
</script>
<form name="districtPost" method="post" action="">
	
  <input type="hidden" name="txtFlag" value="<%=labelType%>">

<table align="center" width="320px" border="0" cellspacing="0"	cellpadding="5" >
  <tr>
    <td align="center">
    	<br>
			<table border="0" align="center" cellpadding="0" cellspacing="0" >
			  <tr height="20">
			    <td><img src="img/list_tit.gif" width="16" height="16"></td>
			    <td class="title" >
			    	<%if(strShowType.equalsIgnoreCase("district")||strShowType.startsWith("dist")){%>
			    	地区选择
			    	<%}else if(strShowType.equalsIgnoreCase("organization")||strShowType.startsWith("org")){%>
			    	组织机构选择
			    	<%}%>
			    </td>
			  </tr>
			</table>	    	
    </td>
  </tr>
  <tr>
    <td>
			<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
			  <tr>
			    <td><img src="img/mao.gif" width="1" height="1"></td>
			  </tr>
			</table>
    </td>
  </tr>
	<tr>
		<td id="backtd" valign="top" >
			<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
			  <tr>
			    <td width="20px"></td>
			    <td>
						<iframe src="javascripttree_center.screen?showType=<%=strShowType%>&dsType=<%=strType%>&labeType=<%=labelType%>" name="dFrame" width=340px height=280px frameborder="0">
						</iframe>
			    </td>
			  </tr>
			</table>			
		</td>
	</tr>
  <tr>
    <td>
    	<br>
	  	<table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
           <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="关  闭" onclick="javascript:cancel_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>   
            <td width="20" ></td>  
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="取  消" onclick="javascript:reset_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
            <td width="20" ></td>   
            
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="确  认" onclick="javascript:ok_submit()"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>          

            

            

              
        </tr>
      </table>	 
    </td>
  </tr>
  <tr>
    <td>
    </td>
  </tr>
</table>
</form>
