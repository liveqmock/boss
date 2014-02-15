package com.dtv.oss.web.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.smartUpload.File;
import com.dtv.oss.web.util.smartUpload.Files;
import com.dtv.oss.web.util.smartUpload.SmartUpload;
/**
 * 该类继承了smartUpload,针对P5,做了些初始化封装,并加了些本地化的方法,
 * 初始化成功后,可使用smartUpload的一切功能.
 * @author 260327h
 *
 */
public class FileUpload extends SmartUpload{

	/**
	 * 初始化文件上传组件smartUpload,
	 * @param request HttpServletRequest,初始化需要pageContent,放在session中,
	 * key为CommonKeys.FILE_UPLOAD_PAGE_CONTEXT,必需在jsp页面设置好,
	 * @param maxFileSize 上传文件大小限制,
	 * @param allowedFileList 允许上传的文件类型.
	 * @throws WebActionException
	 */
	public FileUpload(HttpServletRequest request,long maxFileSize,String allowedFileList) throws WebActionException {
		super();
		String contentType = request.getContentType();

		LogUtility.log(FileUpload.class, LogLevel.DEBUG, "contentType:"+contentType);
		if (contentType == null)
			throw new WebActionException("运行环境异常!");
		int s = contentType.indexOf(";");
		if (s >= 0)
			contentType = contentType.split(";")[0];

		LogUtility.log(FileUpload.class, LogLevel.DEBUG, "contentType:"+contentType);
		if (!contentType.equalsIgnoreCase("multipart/form-data")) {
			throw new WebActionException("运行环境异常!");
		}

		LogUtility.log(FileUpload.class, LogLevel.DEBUG, "contentType:"+contentType);
		try {
			PageContext pageContext = (PageContext) request.getSession()
					.getAttribute(CommonKeys.FILE_UPLOAD_PAGE_CONTEXT);

			initialize(pageContext.getServletConfig(), request,
					(HttpServletResponse) pageContext.getResponse());
			setMaxFileSize(maxFileSize);
			setAllowedFilesList(allowedFileList);
		} catch (ServletException e) {
			e.printStackTrace();
			throw new WebActionException("运行环境异常!");
		}

		request.getSession().removeAttribute(
				CommonKeys.FILE_UPLOAD_PAGE_CONTEXT);
		LogUtility.log(FileUpload.class, LogLevel.DEBUG, "文件上传组件初始化成功!");

	}


	/**
	 * 保存文件,适用于只上传一个文件的情况,返回文件保存路径.
	 * @return
	 * @throws IOException
	 * @throws WebActionException
	 */
	public String saveFile() throws WebActionException {
		String fileName = "";
		String filePath = FileUtility.getSystemPath(CommonKeys.FILE_UPLOAD_PATH);

		System.out.println("filePath:" + filePath);

		try {

			upload();

			Files files = getFiles();
			File file = null;
			for (int i = 0; i < files.getCount(); i++) {
				file = files.getFile(i);
				if (file.isMissing())
					return "";
				fileName = filePath + java.io.File.separator
						+ file.getFileName();
				file.saveAs(fileName, SmartUpload.SAVE_PHYSICAL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		System.out.println("fileName:" + fileName);

		return fileName;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
