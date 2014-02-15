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
			//�����ļ��ϴ�
			String filePath = upload.saveFile();
			//ÿ�δ���ĵ���˵��
			GeHuaUploadCustBatchHeaderDTO custBatchHeaderDTO=new GeHuaUploadCustBatchHeaderDTO();
			Request  dataRequest=upload.getRequest();
			//�����ļ���
			int lastIndex=filePath.lastIndexOf(File.separatorChar);
			custBatchHeaderDTO.setComments(dataRequest.getParameter("txtComments")+"  �����ļ���"+filePath.substring(lastIndex+1)+"��");
			custBatchHeaderDTO.setJobType(dataRequest.getParameter("txtJobType"));
			event.setCustBatchHeaderDTO(custBatchHeaderDTO);
			Collection custInfoCol=parseFile(filePath);
			event.setCustInfoCol(custInfoCol);
			//���ö�������
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
