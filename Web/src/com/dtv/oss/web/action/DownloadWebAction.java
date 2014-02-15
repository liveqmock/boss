
package com.dtv.oss.web.action;


import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.download.ExcelCreate;
import com.dtv.oss.web.util.download.ExcelCreateFactory;
import com.dtv.oss.web.exception.WebActionException;
public abstract class DownloadWebAction extends QueryWebAction {
	private boolean isDown=false;
	private String filename="";
	private String filetype="";
	private String subType;
	private Object queryParameter;
    public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public Object getQueryParameter() {
		return queryParameter;
	}
	public void setQueryParameter(Object queryParameter) {
		this.queryParameter = queryParameter;
	}

	public void doStart(HttpServletRequest request)
    {
		LogUtility.log(DownloadWebAction.class, LogLevel.DEBUG, "开始★★★★★★★★★"+new Date());

    	super.doStart(request);
    	
    	String val2=request.getParameter(WebKeys.ISDOWNPARANAME);
    	if("true".equals(val2)){
    		isDown=true;
    	}else{
    		isDown=false;
    	}
    	filename=request.getParameter(WebKeys.DOWNLOADWEBACTION_FILENAME);
    	filetype=request.getParameter(WebKeys.DOWNLOADWEBACTION_FILETYPE);
    	
    }
    public EJBEvent perform(HttpServletRequest request) throws WebActionException {
//		LogUtility.log(this.getClass(), LogLevel.DEBUG, "perform★★★★★★★★★");
    	EJBEvent event=super.perform(request);
    	if(event!=null&&event instanceof QueryEJBEvent) {
    		QueryEJBEvent qevent=(QueryEJBEvent)event;
    		qevent.setDown(isDown);
//			LogUtility.log(this.getClass(), LogLevel.DEBUG, "perform.isWrap★★★★★★★★★"+isWrap);
		}
    	return event;
    }
    private int getFileType(String ft){
    	if("csv".equalsIgnoreCase(ft)){
    		return ExcelCreateFactory.EXCEL_CREATE_CSV;
    	}else if("Html".equalsIgnoreCase(ft)){
    		return ExcelCreateFactory.EXCEL_CREATE_HTML;
    	}
  		return ExcelCreateFactory.EXCEL_CREATE_CSV;
  	}
    private String checkFileName(int index, String fileName) {
    	if(fileName==null||"".equals(fileName)){
    		fileName="文件下载";
    	}
		int end = fileName.lastIndexOf(".");
		if(end>0){
			fileName = fileName.substring(0, end);
		}
		switch (index) {
		case ExcelCreateFactory.EXCEL_CREATE_CSV:
			fileName = fileName + ".csv";
			break;
		case ExcelCreateFactory.EXCEL_CREATE_HTML:
			fileName = fileName + ".xls";
			break;
		}
		return fileName;
	}
    private String getUrl(HttpServletRequest request){
        String fullURL = request.getRequestURI();
        // get the screen name
        String selectedURL = null;
        int lastPathSeparator = fullURL.lastIndexOf("/") + 1;
        if (lastPathSeparator != -1) {
            selectedURL = fullURL.substring(lastPathSeparator, fullURL.length());
        }
        return selectedURL;
    }
    private String getTempFileName(HttpServletRequest request){
    	String str=request.getRemoteHost()+new Date().getTime()+Math.random();
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "doEnd.getTempFileName★★★★★★★★★"+str);
    	return str;
    }
	/**
	 * 调用excel生成器,生成文件内容,放在request中.
	 * @throws WebActionException 
	 */
	public void doEnd(HttpServletRequest request, CommandResponse cmdResponse){
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "doEnd.isDown★★★★★★★★★"+isDown);
		if(isDown){
			Object obj=cmdResponse.getPayload();
//			LogUtility.log(this.getClass(), LogLevel.DEBUG, "doEnd.cmdResponse.getPayload()★★★★★★★★★"+cmdResponse.getPayload());
			int ft=getFileType(filetype);
			ExcelCreate ge=ExcelCreateFactory.getExcelCreate(ft);
			File file=null;
			try {
				String querySql="";
//				request.setAttribute(WebKeys.DOWNLOADWEBACTION_FILECONTENT, ge.getExcelContent(obj));
				if(obj!=null&&obj instanceof List){
					List qList=(List)obj;
					for(int i=0;i<qList.size();i++){
						Object cur=qList.get(i);
						if(cur instanceof String){
							querySql=(String)cur;
						}
					}
					qList.clear();
				}
				ge.init(getUrl(request),subType,querySql,queryParameter);
				String genFileName=getTempFileName(request);
//				LogUtility.log(this.getClass(), LogLevel.DEBUG, "doEnd.ExcelCreate.getQuerySql★★★★★★★★★",ge.getQuerySql());
//				LogUtility.log(this.getClass(), LogLevel.DEBUG, "doEnd.ExcelCreate.getQueryDao★★★★★★★★★",ge.getQueryDao());
				file=ge.getExcelFile(FileUtility.getSystemPath("download")+File.separator+genFileName);
				request.setAttribute(WebKeys.DOWNLOADWEBACTION_FILE, file);
				String fName=ge.getFileName();
				if(fName!=null&&!"".equals(fName.trim())){
		    		filename=fName;
		    	}
		    	if(filename==null||"".equals(filename.trim())){
		    		filename="文件下载";
		    	}
				filename=checkFileName(ft,filename);
				request.setAttribute(WebKeys.DOWNLOADWEBACTION_FILENAME, filename);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtility.log(this.getClass(), LogLevel.ERROR, "★★★★★★★★★"+e.getMessage());
				request.setAttribute(WebKeys.DOWNLOADWEBACTION_FILENAME, "error.txt");
				request.setAttribute(WebKeys.DOWNLOADWEBACTION_FILECONTENT, e.getMessage());
				request.removeAttribute(WebKeys.DOWNLOADWEBACTION_FILE);
				if(file!=null){
					file.delete();
				}
			}
			if(obj!=null&&obj instanceof List){
				List list=(List) obj;
				list.clear();
			}
		}
		LogUtility.log(DownloadWebAction.class, LogLevel.DEBUG, "结束★★★★★★★★★"+new Date());
	    super.doEnd(request,cmdResponse);
    }
	//下载的时候不加标签,
	protected void updateBookmarkPool(HttpServletRequest request) {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "updateBookmarkPool★★★★★★★★★updateBookmarkPool");
		if(!isDown){
			super.updateBookmarkPool(request);
		}
	}
}
