package com.dtv.oss.web.action.batch;

import java.io.File;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.GeHuaUploadCustBatchHeaderDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.GehuaBatchMessageEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.smartUpload.Request;

public class UploadForBatchSuspendWebAction extends GeneralWebAction {
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		GehuaBatchMessageEJBEvent event =new GehuaBatchMessageEJBEvent();
		try {
			FileUpload upload=new FileUpload(request,1024000,"csv");
			upload.setCharset("GBK");
			//处理文件上传
			String filePath = upload.saveFile();
			//每次处理的导入说明
			GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO=new GeHuaUploadCustBatchHeaderDTO();
			Request  dataRequest=upload.getRequest();
			//保存文件名
			int lastIndex=filePath.lastIndexOf(File.separatorChar);
			custBatchHeaderDTO.setComments(dataRequest.getParameter("txtComments")+"  导入文件【"+filePath.substring(lastIndex+1)+"】");
			custBatchHeaderDTO.setJobType(dataRequest.getParameter("txtJobType"));
			event.setCustBatchHeaderDTO(custBatchHeaderDTO);
			Collection custInfoCol=parseFile(filePath);
			event.setCustInfoCol(custInfoCol);
			//设置动作类型
			event.setActionType(EJBEvent.UPLOAD_FOR_BATCH_SUSPEND);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		return event;
	}
	private Collection parseFile(String filePath) throws WebActionException{
		return FileUtility.parseCSVFileToCustInfo(filePath);
	} 

}
