package com.dtv.oss.service.ejbevent.config;

import java.util.Collection;

import com.dtv.oss.dto.BossConfigurationDTO;
import com.dtv.oss.dto.ManualTransferSettingDTO;
import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.dto.SystemSettingDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;

public class ConfigSystemEJBEvent extends CsrAbstractEJbevent {

    
    private BossConfigurationDTO bcDto;
    private Collection col;
    private OpGroupDTO opGroupDto;
    private ManualTransferSettingDTO manuTransSettingDto;
    
    
	public ManualTransferSettingDTO getManuTransSettingDto() {
		return manuTransSettingDto;
	}
	public void setManuTransSettingDto(ManualTransferSettingDTO manuTransSettingDto) {
		this.manuTransSettingDto = manuTransSettingDto;
	}
	/**
	 * @return Returns the sysSettingDto.
	 */
	public SystemSettingDTO getSysSettingDto() {
		return sysSettingDto;
	}
	/**
	 * @param sysSettingDto The sysSettingDto to set.
	 */
	public void setSysSettingDto(SystemSettingDTO sysSettingDto) {
		this.sysSettingDto = sysSettingDto;
	}
    private OperatorDTO operDto;
    private SystemSettingDTO sysSettingDto;
    
  
	/**
	 * @return Returns the operDto.
	 */
	public OperatorDTO getOperDto() {
		return operDto;
	}
	/**
	 * @param operDto The operDto to set.
	 */
	public void setOperDto(OperatorDTO operDto) {
		this.operDto = operDto;
	}
	/**
	 * @return Returns the col.
	 */
	public Collection getCol() {
		return col;
	}
	/**
	 * @param col The col to set.
	 */
	public void setCol(Collection col) {
		this.col = col;
	}
	/**
	 * @return Returns the bcDto.
	 */
	public BossConfigurationDTO getBcDto() {
		return bcDto;
	}
	/**
	 * @param bcDto The bcDto to set.
	 */
	public void setBcDto(BossConfigurationDTO bcDto) {
		this.bcDto = bcDto;
	}
	 
	 
	/**
	 * @return Returns the opGroupDto.
	 */
	public OpGroupDTO getOpGroupDto() {
		return opGroupDto;
	}
	/**
	 * @param opGroupDto The opGroupDto to set.
	 */
	public void setOpGroupDto(OpGroupDTO opGroupDto) {
		this.opGroupDto = opGroupDto;
	}

}
