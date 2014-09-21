<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileReader"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.dtv.oss.web.util.WebKeys"%>
<% 
	System.out.println("进入文件下载screen.");
	Object fileNameObj = request.getAttribute(WebKeys.DOWNLOADWEBACTION_FILENAME);
	String filename="";
	if(fileNameObj!=null){
		filename=(String)fileNameObj;
	}
	System.out.println("进入文件下载screen.filename "+filename);
	filename=new String(filename.getBytes("GB2312"),"iso8859-1");
	response.setContentType("application/x-msdownload"); 
	response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\""); 
	String fileContent = null; 
	Object objContent=request.getAttribute(WebKeys.DOWNLOADWEBACTION_FILECONTENT);
	Object objFile=request.getAttribute(WebKeys.DOWNLOADWEBACTION_FILE);
	OutputStream os= response.getOutputStream();
	if(objContent!=null){
		fileContent=(String)objContent;
		os.write(fileContent.getBytes());
		os.flush();
	}else if(objFile!=null){
		java.io.File file=(java.io.File)objFile;
		System.out.println("进入文件下载screen.file.GetAbsolutePathName(): "+file.getAbsolutePath());
		FileInputStream fi=new FileInputStream(file);
		byte[]buf=new byte[1024];
		while(fi.read(buf)!=-1){
			os.write(buf);
			buf=new byte[1024];
			os.flush();
		}
		fi.close();
		file.delete();
	}else{
		fileContent="没有有效的内容";
	}
	os.close();
%>
