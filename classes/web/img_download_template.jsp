<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="com.dtv.oss.util.*"%>
<%@ page import="java.sql.*"%>

<%
	//显示打印的表格图片
	response.reset(); 
	response.setContentType("image/jpeg"); 
	String action = request.getParameter("action");					//系统公共图片
	String printConfigID = request.getParameter("printConfigID");   //打印背景图片
	//InputStream in = null;
	byte buf[] = null;
	if("imgListTop".equals(action)){									//系统公共图片，从缓存Postern中取出
		buf=(byte[])(Postern.getSystemImgCache()).get("imgListTop");
	}
	if("imgLoginLogo".equals(action)){									//系统公共图片，从缓存Postern中取出
		buf=(byte[])(Postern.getSystemImgCache()).get("imgLoginLogo");
	}
	if("imgTitle".equals(action)){										//系统公共图片，从缓存Postern中取出
		buf=(byte[])(Postern.getSystemImgCache()).get("imgTitle");
	}
	
	if(printConfigID != null && !"".equals(printConfigID)){         //用于打印背景图片
		buf=Postern.getPrintConfigImgByID(Integer.parseInt(printConfigID));
	}
	
	if("atv".equals(action)){										//业务类型图标，从缓存Postern中取出
		buf=(byte[])(Postern.getServiceIconsCache()).get("atv");
	}
	if("dtv".equals(action)){										//业务类型图标，从缓存Postern中取出
		buf=(byte[])(Postern.getServiceIconsCache()).get("dtv");
	}
	if("itv".equals(action)){										//业务类型图标，从缓存Postern中取出
		buf=(byte[])(Postern.getServiceIconsCache()).get("itv");
	}
	if("band".equals(action)){										//业务类型图标，从缓存Postern中取出
		buf=(byte[])(Postern.getServiceIconsCache()).get("band");
	}
	if("voip".equals(action)){										//业务类型图标，从缓存Postern中取出
		buf=(byte[])(Postern.getServiceIconsCache()).get("voip");
	}
	if("inc".equals(action)){										//业务类型图标，从缓存Postern中取出
		buf=(byte[])(Postern.getServiceIconsCache()).get("inc");
	}
	
	
	/**
    if(in!=null){
		ServletOutputStream sout = response.getOutputStream();
		int len; 
		byte buf[]=new byte[1024]; 
		while ((len=in.read(buf,0,1024))!=-1){
			sout.write(buf,0,len); 
		}
		sout.flush(); 
		//输入完毕，清除缓冲 
		sout.close(); 
	}
	**/
	
	ServletOutputStream sout = response.getOutputStream();
	sout.write(buf);
	sout.flush(); 
	sout.close(); 
%>