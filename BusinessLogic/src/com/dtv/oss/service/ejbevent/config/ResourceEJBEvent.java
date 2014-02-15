package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.CatvJobCardConfigDTO;
import com.dtv.oss.dto.FiberNodeDTO;
import com.dtv.oss.dto.FiberReceiverDTO;
import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;
/*
 * 网络资源事件
 */
public class ResourceEJBEvent extends AbstractEJBEvent {
	 
	/*
	 * 用一个dto就行了,ActionType用父类的,
	 */
	private MachineRoomDTO mrDto;
    private FiberTransmitterDTO ftDto;
    private FiberReceiverDTO frDto;
	private FiberNodeDTO fnDto;
	private CatvJobCardConfigDTO catvJobCardDto;
    
	 
	public ResourceEJBEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FiberNodeDTO getFnDto() {
		return fnDto;
	}
	public void setFnDto(FiberNodeDTO fnDto) {
		this.fnDto = fnDto;
	}
	public FiberReceiverDTO getFrDto() {
		return frDto;
	}
	public void setFrDto(FiberReceiverDTO frDto) {
		this.frDto = frDto;
	}
	public FiberTransmitterDTO getFtDto() {
		return ftDto;
	}
	public void setFtDto(FiberTransmitterDTO ftDto) {
		this.ftDto = ftDto;
	}
	public MachineRoomDTO getMrDto() {
		return mrDto;
	}
	public void setMrDto(MachineRoomDTO mrDto) {
		this.mrDto = mrDto;
	}
	public CatvJobCardConfigDTO getCatvJobCardDto() {
		return catvJobCardDto;
	}
	public void setCatvJobCardDto(CatvJobCardConfigDTO catvJobCardDto) {
		this.catvJobCardDto = catvJobCardDto;
	}

	 

	 
}
