package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;
import com.dtv.oss.dto.DeviceModelDTO;
import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;
/*
 * �豸�ͺŲ������ݷ�װ
 */
public class DeviceModelEJBEvent extends CsrAbstractEJbevent {
	//��Щ������־����ejbevent��û��,����������,���ܺ�ejbevent�µ�ֵ��ͻ,
	public final static int DEVICEMODEL_CREATE=1;
	public final static int DEVICEMODEL_UPDATE=2;
	public final static int DEVICEMODEL_DELETE=3;
	public final static int DEVICEREASON_UPDATE=4;
	public final static int DEVICEREASON_CREATE=5;
	public final static int SWAPDEVICE_UPDATE=6;
	public final static int SWAPDEVICE_CREATE=7;
	/*
	 * ��һ��dto������,ActionType�ø����,
	 */
	private DeviceModelDTO dto;
	
	private CsiTypeReason2DeviceDTO reason2DeviceDto;
	
	private SwapDeviceReason2StatusDTO swapDto; 
	
	//��������ɾ���б� 
	private String[] list;
	

	/**
	 * @return Returns the list.
	 */
	public String[] getList() {
		return list;
	}

	/**
	 * @param list The list to set.
	 */
	public void setList(String[] list) {
		this.list = list;
	}

	public DeviceModelEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceModelEJBEvent(DeviceModelDTO dto, int actionType) {
		super();
		this.dto = dto;
		this.actionType = actionType;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the dto.
	 */
	public DeviceModelDTO getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            The dto to set.
	 */
	public void setDto(DeviceModelDTO dto) {
		this.dto = dto;
	}

	public CsiTypeReason2DeviceDTO getReason2DeviceDto() {
		return reason2DeviceDto;
	}

	public void setReason2DeviceDto(CsiTypeReason2DeviceDTO reason2DeviceDto) {
		this.reason2DeviceDto = reason2DeviceDto;
	}

	public SwapDeviceReason2StatusDTO getSwapDto() {
		return swapDto;
	}

	public void setSwapDto(SwapDeviceReason2StatusDTO swapDto) {
		this.swapDto = swapDto;
	}

}
