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
 * ����̳���smartUpload,���P5,����Щ��ʼ����װ,������Щ���ػ��ķ���,
 * ��ʼ���ɹ���,��ʹ��smartUpload��һ�й���.
 * @author 260327h
 *
 */
public class FileUpload extends SmartUpload{

	/**
	 * ��ʼ���ļ��ϴ����smartUpload,
	 * @param request HttpServletRequest,��ʼ����ҪpageContent,����session��,
	 * keyΪCommonKeys.FILE_UPLOAD_PAGE_CONTEXT,������jspҳ�����ú�,
	 * @param maxFileSize �ϴ��ļ���С����,
	 * @param allowedFileList �����ϴ����ļ�����.
	 * @throws WebActionException
	 */
	public FileUpload(HttpServletRequest request,long maxFileSize,String allowedFileList) throws WebActionException {
		super();
		String contentType = request.getContentType();

		LogUtility.log(FileUpload.class, LogLevel.DEBUG, "contentType:"+contentType);
		if (contentType == null)
			throw new WebActionException("���л����쳣!");
		int s = contentType.indexOf(";");
		if (s >= 0)
			contentType = contentType.split(";")[0];

		LogUtility.log(FileUpload.class, LogLevel.DEBUG, "contentType:"+contentType);
		if (!contentType.equalsIgnoreCase("multipart/form-data")) {
			throw new WebActionException("���л����쳣!");
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
			throw new WebActionException("���л����쳣!");
		}

		request.getSession().removeAttribute(
				CommonKeys.FILE_UPLOAD_PAGE_CONTEXT);
		LogUtility.log(FileUpload.class, LogLevel.DEBUG, "�ļ��ϴ������ʼ���ɹ�!");

	}


	/**
	 * �����ļ�,������ֻ�ϴ�һ���ļ������,�����ļ�����·��.
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
