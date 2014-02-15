package com.dtv.oss.service.ejbevent.batch;

import java.util.Collection;
import java.util.HashMap;

import com.dtv.oss.dto.ExportCustomerHeadDTO;
import com.dtv.oss.dto.FoundCustomerHeadDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;

public class ExportCustomerEJBEvent extends CsrAbstractEJbevent  {
	/*模拟建档用户使用使用数据 begin */
	private FoundCustomerHeadDTO foundCustomerHeadDto;
	private Collection           foundCustomerDetailCol;
	public Collection getFoundCustomerDetailCol() {
		return foundCustomerDetailCol;
	}
	public void setFoundCustomerDetailCol(Collection foundCustomerDetailCol) {
		this.foundCustomerDetailCol = foundCustomerDetailCol;
	}
	
	public FoundCustomerHeadDTO getFoundCustomerHeadDto() {
		return foundCustomerHeadDto;
	}
	public void setFoundCustomerHeadDto(FoundCustomerHeadDTO foundCustomerHeadDto) {
		this.foundCustomerHeadDto = foundCustomerHeadDto;
	}
	
	/*模拟建档用户使用使用数据 end */
	
	/*整转用户使用数据  begin*/
	private  ExportCustomerHeadDTO exportCustomerHeadDto;
	private  Collection exportCustomerDetailCol;
	private  HashMap    repeatCatvIDMp =new HashMap();
	public ExportCustomerHeadDTO getExportCustomerHeadDto() {
		return exportCustomerHeadDto;
	}
	public void setExportCustomerHeadDto(ExportCustomerHeadDTO exportCustomerHeadDto) {
		this.exportCustomerHeadDto = exportCustomerHeadDto;
	}
	public Collection getExportCustomerDetailCol() {
		return exportCustomerDetailCol;
	}
	public void setExportCustomerDetailCol(Collection exportCustomerDetailCol) {
		this.exportCustomerDetailCol = exportCustomerDetailCol;
	}
	public HashMap getRepeatCatvIDMp() {
		return repeatCatvIDMp;
	}
	/*整转用户使用数据  end*/
}
