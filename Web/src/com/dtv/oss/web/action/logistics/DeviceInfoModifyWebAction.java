package com.dtv.oss.web.action.logistics;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 * @author Wesley Shu
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DeviceTransitionDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.DeviceStockEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class DeviceInfoModifyWebAction extends GeneralWebAction{
 
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
		if(!WebUtil.StringHaveContent(request.getParameter("txtDeviceID")))
            throw new WebActionException("û����ص��豸��Ϣ��ʶ");
   	 DeviceStockEJBEvent event= new DeviceStockEJBEvent(); 
   	TerminalDeviceDTO dtDto=new TerminalDeviceDTO();
   	DeviceTransitionDTO devTransDTO = new DeviceTransitionDTO();
   	dtDto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDeviceDtLastmod")));
   	 //�豸ID
   	 dtDto.setDeviceID(WebUtil.StringToInt(request.getParameter("txtDeviceID")));
   	 //���к�
   	 dtDto.setSerialNo(request.getParameter("txtSerialNo"));
   	 //����
   	dtDto.setDeviceClass(request.getParameter("txtDeviceClass"));
   	//�ͺ�
   	dtDto.setDeviceModel(request.getParameter("txtDeviceModel"));
   	//����
   	devTransDTO.setBatchNo(request.getParameter("txtBatchID"));
   	//�ֿ�
   	dtDto.setDepotID(WebUtil.StringToInt(request.getParameter("txtDepotID")));
   	//��������
   	dtDto.setAddressType(request.getParameter("txtZoneStation"));
   	//����
   	dtDto.setAddressID(WebUtil.StringToInt(request.getParameter("txtAddressID")));
   	//����
   	dtDto.setLeaseBuy(request.getParameter("txtLeaseBuy"));
   	//��������
   	dtDto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
   	//���
   	dtDto.setMatchFlag(request.getParameter("txtMatchFlag"));
   	//����豸��
   	dtDto.setMatchDeviceID(WebUtil.StringToInt(request.getParameter("txtMatchDeviceID")));
   	//��λ���
   	dtDto.setCaSetFlag(request.getParameter("txtCaSetFlag"));
   	//��λ����
   	dtDto.setCaSetDate(WebUtil.StringToTimestamp(request.getParameter("txtCaSetDate")));
   	//���޽�ֹ����
   	dtDto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
   	//�����ڵĳ���
   	dtDto.setGuaranteeLength(WebUtil.StringToInt(request.getParameter("txtGuaranteeLength")));
   	//CM MAC��ַ
   	dtDto.setMACAddress(request.getParameter("txtMacAddress"));
   	//�ն�MAC��ַ
   	dtDto.setInterMACAddress(request.getParameter("txtInterMacAddress"));
   	//�Ƿ����
   	dtDto.setUseFlag(request.getParameter("txtUseFlag"));
   	//״̬
   	dtDto.setStatus(request.getParameter("txtStatus"));
   	//�豸Ԥ��Ȩ
   	dtDto.setPreAuthorization(request.getParameter("txtPreAuthorization"));
   	//�豸��;
   	String purposeStrList = request.getParameter("txtPurposeStrList");
   	//ƽ��ϵͳ�ڲ���Ȩ��
   	dtDto.setOkNumber(request.getParameter("txtOkNumber"));
   	//��ע
   	dtDto.setComments(request.getParameter("txtComments"));
   	if(WebUtil.StringHaveContent(purposeStrList))
   	{
   		if(!purposeStrList.startsWith(","))
   		{
   			purposeStrList = ","+purposeStrList;
   		}
   		if(!purposeStrList.endsWith(","))
   		{
   			purposeStrList = purposeStrList+",";
   		}
   	}
   	dtDto.setPurposeStrList(purposeStrList);
   	
   	event.setTerDeviceDTO(dtDto);
   	event.setDevTransDTO(devTransDTO);
   	event.setActionType(LogisticsEJBEvent.DEVICE_MODIFY);
   	 
     	return event;
   }

}