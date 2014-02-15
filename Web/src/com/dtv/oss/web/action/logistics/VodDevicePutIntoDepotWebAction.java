package com.dtv.oss.web.action.logistics;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.VodstbDeviceImportHeadDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DevicePutIntoDepotEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.smartUpload.Request;

public class VodDevicePutIntoDepotWebAction extends GeneralWebAction {
	boolean confirmPost = false;

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
		DevicePutIntoDepotEJBEvent ejbEvent = new DevicePutIntoDepotEJBEvent(LogisticsEJBEvent.VOD_DEVICE_PUT_INTO_DEPOT);
        ejbEvent.setDoPost(confirmPost);
		if (!confirmPost){
		    FileUpload upload=new FileUpload(request,1024000,"xls");	
            //	�����ļ��ϴ�
			String   filePath = upload.saveFile();
			Request  dataRequest=upload.getRequest();
			if (!WebUtil.StringHaveContent(dataRequest.getParameter("txtDeviceModel")))
				throw new WebActionException("û�и����豸�ͺ�");
			if (!WebUtil.StringHaveContent(dataRequest.getParameter("txtDeviceClass")))
				throw new WebActionException("û�и����豸���");
			if (!WebUtil.StringHaveContent(dataRequest
					.getParameter("txtDeviceProvider")))
				throw new WebActionException("û�и����豸������");
			if (!WebUtil.StringHaveContent(dataRequest
					.getParameter("txtGuaranteeLength")))
				throw new WebActionException("û�и���������");
			if (!WebUtil.StringHaveContent(dataRequest.getParameter("txtDepotID")))
				throw new WebActionException("û��ѡ��ֿ�");
			if (!WebUtil.StringHaveContent(dataRequest.getParameter("txtBatchNo")))
				throw new WebActionException("û�и�����ⵥ���");
			if (!WebUtil.StringHaveContent(dataRequest.getParameter("txtInAndOut")))
				throw new WebActionException("û��¼�����������");
			if ("Y".equals(dataRequest.getParameter("txtInAndOut")) && !WebUtil.StringHaveContent(dataRequest.getParameter("txtOutOrgID")))
				throw new WebActionException("�����������¼����֯��Ϣ");
			
		    
		    VodstbDeviceImportHeadDTO dto = new VodstbDeviceImportHeadDTO();
			dto.setDeviceClass(dataRequest.getParameter("txtDeviceClass"));
			dto.setDeviceModel(dataRequest.getParameter("txtDeviceModel"));
			dto.setProviderID(WebUtil.StringToInt(dataRequest.getParameter("txtDeviceProvider")));
			dto.setBatchNo(dataRequest.getParameter("txtBatchNo"));
			dto.setGuaranteeLength(WebUtil.StringToInt(dataRequest.getParameter("txtGuaranteeLength")));
			dto.setDepotID(WebUtil.StringToInt(dataRequest.getParameter("txtDepotID")));
			dto.setInAndOut(dataRequest.getParameter("txtInAndOut"));
			dto.setOutOrgID(WebUtil.StringToInt(dataRequest.getParameter("txtOutOrgID")));
			dto.setPurposestrList(dataRequest.getParameter("txtPurposeStrList"));
			dto.setPurposeStrListValue(dataRequest.getParameter("txtPurposeStrListValue"));
			dto.setComments(dataRequest.getParameter("txtComments"));
			dto.setStatus("W");
			
			request.getSession().setAttribute("BatchNo", dto.getBatchNo());
			ejbEvent.setVodstbDeviceHead(dto);
			Collection vodDCols=FileUtility.parseXlsFileToVodDevice(filePath);
			ejbEvent.setVodDevicePutInfoDepotList(vodDCols);
			
			request.getSession().setAttribute("txtDeviceModel", dataRequest.getParameter("txtDeviceModel"));
		}else{
			VodstbDeviceImportHeadDTO dto = new VodstbDeviceImportHeadDTO();
			dto.setBatchID(WebUtil.StringToInt(request.getParameter("bathID")));
			ejbEvent.setVodstbDeviceHead(dto);
		}
		return ejbEvent;
	}

}
