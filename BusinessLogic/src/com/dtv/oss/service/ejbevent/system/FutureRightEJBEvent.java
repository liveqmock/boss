package com.dtv.oss.service.ejbevent.system;

import com.dtv.oss.dto.FutureRightDTO;

public class FutureRightEJBEvent extends SystemEJBEvent {

	private FutureRightDTO futureRightDto;
	public static final int FUTURERIGHT_CREATE 							= 1; //创建
	public static final int FUTURERIGHT_CANCEL 						= 2; //取消
	public static final int FUTURERIGHT_ENCASH					= 3; //
	
	
	public FutureRightEJBEvent(int actionType, FutureRightDTO futureRightDto) {
		super();
		this.actionType = actionType;
		this.futureRightDto = futureRightDto;
    }
	public FutureRightDTO getFutureRightDto() {
		return futureRightDto;
	}
	public void setFutureRightDto(FutureRightDTO futureRightDto) {
		this.futureRightDto = futureRightDto;
	}

}
