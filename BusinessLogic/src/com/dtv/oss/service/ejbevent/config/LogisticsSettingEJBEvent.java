package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.LogisticsSettingDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;
/*
 * 仓库操作数据封装
 */
public class LogisticsSettingEJBEvent extends CsrAbstractEJbevent {

	public  static final int LOGISTICSSETTING_CREATE=1; 
	public static final int LOGISTICSSETTING_DELETE=2; 
	public static final int LOGISTICSSETTING_MODIFY=3; 

	private LogisticsSettingDTO dto;

	public LogisticsSettingEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogisticsSettingEJBEvent(LogisticsSettingDTO dto, int actionType) {
		super();
		this.dto = dto;
		this.actionType = actionType;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the dto.
	 */
	public LogisticsSettingDTO getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            The dto to set.
	 */
	public void setDto(LogisticsSettingDTO dto) {
		this.dto = dto;
	}

}
