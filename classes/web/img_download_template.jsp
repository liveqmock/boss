<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="com.dtv.oss.util.*"%>
<%@ page import="java.sql.*"%>

<%
	//��ʾ��ӡ�ı��ͼƬ
	response.reset(); 
	response.setContentType("image/jpeg"); 
	String action = request.getParameter("action");					//ϵͳ����ͼƬ
	String printConfigID = request.getParameter("printConfigID");   //��ӡ����ͼƬ
	//InputStream in = null;
	byte buf[] = null;
	if("imgListTop".equals(action)){									//ϵͳ����ͼƬ���ӻ���Postern��ȡ��
		buf=(byte[])(Postern.getSystemImgCache()).get("imgListTop");
	}
	if("imgLoginLogo".equals(action)){									//ϵͳ����ͼƬ���ӻ���Postern��ȡ��
		buf=(byte[])(Postern.getSystemImgCache()).get("imgLoginLogo");
	}
	if("imgTitle".equals(action)){										//ϵͳ����ͼƬ���ӻ���Postern��ȡ��
		buf=(byte[])(Postern.getSystemImgCache()).get("imgTitle");
	}
	
	if(printConfigID != null && !"".equals(printConfigID)){         //���ڴ�ӡ����ͼƬ
		buf=Postern.getPrintConfigImgByID(Integer.parseInt(printConfigID));
	}
	
	if("atv".equals(action)){										//ҵ������ͼ�꣬�ӻ���Postern��ȡ��
		buf=(byte[])(Postern.getServiceIconsCache()).get("atv");
	}
	if("dtv".equals(action)){										//ҵ������ͼ�꣬�ӻ���Postern��ȡ��
		buf=(byte[])(Postern.getServiceIconsCache()).get("dtv");
	}
	if("itv".equals(action)){										//ҵ������ͼ�꣬�ӻ���Postern��ȡ��
		buf=(byte[])(Postern.getServiceIconsCache()).get("itv");
	}
	if("band".equals(action)){										//ҵ������ͼ�꣬�ӻ���Postern��ȡ��
		buf=(byte[])(Postern.getServiceIconsCache()).get("band");
	}
	if("voip".equals(action)){										//ҵ������ͼ�꣬�ӻ���Postern��ȡ��
		buf=(byte[])(Postern.getServiceIconsCache()).get("voip");
	}
	if("inc".equals(action)){										//ҵ������ͼ�꣬�ӻ���Postern��ȡ��
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
		//������ϣ�������� 
		sout.close(); 
	}
	**/
	
	ServletOutputStream sout = response.getOutputStream();
	sout.write(buf);
	sout.flush(); 
	sout.close(); 
%>