package com.dtv.oss.web.action.logistics;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FaPiaoDTO;
import com.dtv.oss.dto.FapiaoVolumnDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.FaPiaoEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebUtil;

/*
 * ��Ʊ����
 */
public class FapiaoPutIntoDepotWebAction extends GeneralWebAction {
	boolean doPost = false;

	protected boolean needCheckToken() {
		return doPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true")) {
				doPost = true;

			}
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		FaPiaoEJBEvent theEvent = new FaPiaoEJBEvent();
		FaPiaoDTO faPiaoDto = new FaPiaoDTO();
		FapiaoVolumnDTO fapiaoVolumnDto = new FapiaoVolumnDTO();
		try {
		if (!WebUtil.StringHaveContent(request.getParameter("txtIsVolumnManage")))
			throw new WebActionException("��������:�Ƿ����÷�Ʊ�����δ֪!");
		//���÷�Ʊ�����
		if("Y".equals(request.getParameter("txtIsVolumnManage")))
		{
			if (!WebUtil.StringHaveContent(request.getParameter("txtVolumnSN")))
				throw new WebActionException("��������:��Ʊ�����к�δ֪!");
		}
		if (!WebUtil.StringHaveContent(request.getParameter("txtType")))
			throw new WebActionException("��������:��Ʊ����δ֪!");
		
		//��Ʊ�����к�
		if(WebUtil.StringHaveContent(request.getParameter("txtVolumnSN")))
			fapiaoVolumnDto.setVolumnSn(request.getParameter("txtVolumnSN"));
		//��Ʊ����
		if(WebUtil.StringHaveContent(request.getParameter("txtType")))
			faPiaoDto.setType(request.getParameter("txtType"));
		// ���뷽ʽ: "1"--�ļ����� "2"--�ֹ�����
		if(WebUtil.StringHaveContent(request.getParameter("txtMakeStyle")))
			theEvent.setMakeStyle(request.getParameter("txtMakeStyle"));
		// Ŀ���ַID (����Ĳֿ�ID)
		if(WebUtil.StringHaveContent(request.getParameter("txtDepotID")))
			faPiaoDto.setAddressID(WebUtil.StringToInt(request.getParameter("txtDepotID")));
		// �������������: "1"--�������� "0"--����������
		if("1".equals(request.getParameter("txtIsDirUse")))
			theEvent.setIsDirUse(true);
		// ������֯����
		if(WebUtil.StringHaveContent(request.getParameter("txtOutOrgID")))
			theEvent.setOutOrgID(WebUtil.StringToInt(request.getParameter("txtOutOrgID")));
		
		List serailNoList = new ArrayList();
		// ��Ʊ���к�
		if(WebUtil.StringHaveContent(request.getParameter("txtSerailNos")))
		{
			String[] serialsArray = request.getParameter("txtSerailNos").split("\r\n");
			
			for (int i = 0; i < serialsArray.length; i++) {
				if(serialsArray[i]!=null && !"".equals(serialsArray[i].trim()))
					serailNoList.add(serialsArray[i].trim());
			}
		}
		theEvent.setSerailNoList(serailNoList);

		//�ļ���
		if(WebUtil.StringHaveContent(request.getParameter("txtFilePath")))
			theEvent.setFileName(request.getParameter("txtFilePath"));
		//��Ʊ�Ƿ񰴲���� "Y"--��
		if("Y".equals(request.getParameter("txtIsVolumnManage")))
			theEvent.setIsVolumnManage(true);
		
			// �����ύ,�����������
			if (!doPost) {
				// ���ļ�����,��Ҫupload�ļ�
				if ("1".equals(theEvent.getMakeStyle())) {
					// �������ϴ����ļ���СΪ 1024000 , �����ϴ����ļ���ʽΪ:csv ��ʽ��
					FileUpload upload = new FileUpload(request, 1024000, "csv");
					String filePath = upload.saveFile();
					// ��Ʊ���к�
					theEvent.setSerailNoList(parseFile(filePath));
					// ���ط������ϵ��ļ�·����ȷ��ҳ��ʹ��
					 request.setAttribute("txtFilePath", filePath);
					LogUtility.log(FileUpload.class, LogLevel.DEBUG, "___�������="
							+ theEvent.getSerailNoList());
				}
			}
			//��ȷ��ҳ��
			else
			{
				List temList = new ArrayList();
				//��Ʊ���к�
				if(WebUtil.StringHaveContent(request.getParameter("txtNoCols")))
				{
					String[] serialsArray = request.getParameter("txtNoCols").split("\r\n");
					
					for (int i = 0; i < serialsArray.length; i++) {
						if(serialsArray[i]!=null && !"".equals(serialsArray[i].trim()))
							temList.add(serialsArray[i].trim());
					}
				}
				theEvent.setSerailNoList(temList);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		theEvent.setActionType(LogisticsEJBEvent.FAPIAO_IN);
		theEvent.setDoPost(doPost);
		theEvent.setFaPiaoDTO(faPiaoDto);
		theEvent.setFapiaoVolumnDTO(fapiaoVolumnDto);
		return theEvent;

	}

	private List parseFile(String filePath) throws WebActionException {
		return FileUtility.parseCSVFileWithFapiaoIn(filePath);
	}

}
