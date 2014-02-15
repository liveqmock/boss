package com.dtv.oss.web.action.batch;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FoundCustomerHeadDTO;
import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.ExportCustomerEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebCurrentOperatorData;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.smartUpload.Request;

public class UploadForfoundCustomerWebAction extends GeneralWebAction {
	boolean confirmPost = false;

    protected boolean needCheckToken() {
	    return confirmPost;
	}
    
	public void doStart(HttpServletRequest request) {
		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}
	}
	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		ExportCustomerEJBEvent event =new ExportCustomerEJBEvent();
		event.setDoPost(confirmPost);
		// 设置动作类型
		event.setActionType(EJBEvent.UPLOAD_FOR_FoundCustomer);
		try {
		   if (!confirmPost){
		       WebCurrentOperatorData dtoOp =(WebCurrentOperatorData)request.getSession().getAttribute(WebKeys.OPERATOR_SESSION_NAME);
		       OperatorDTO dtoOper = (OperatorDTO)dtoOp.getCurrentOperator();
		    
			   FileUpload upload=new FileUpload(request,1024000,"xls");		
			   //处理文件上传
			   String filePath = upload.saveFile();
			   Request  dataRequest=upload.getRequest();
			   FoundCustomerHeadDTO foundCustomerHeadDto=new FoundCustomerHeadDTO();
			   foundCustomerHeadDto.setComments(dataRequest.getParameter("txtComments"));
			   event.setFoundCustomerHeadDto(foundCustomerHeadDto);
			   //清除操作员的垃圾数据,并解析上传的文件
			   Collection foundCustomerDetailCol=FileUtility.parseXlsFileTofoundCustomer(dtoOper.getOperatorID(),filePath,dtoOper.getOrgID());
			   event.setFoundCustomerDetailCol(foundCustomerDetailCol);   
		   }else{
			   FoundCustomerHeadDTO dto =new FoundCustomerHeadDTO();
			   dto.setBatchNo(WebUtil.StringToInt(request.getParameter("bathNo")));
		       event.setFoundCustomerHeadDto(dto);
		   }
		} catch (Exception e) {
			 e.printStackTrace();		
			 throw new WebActionException(e.getMessage());
		}
		return event;
	}
}
