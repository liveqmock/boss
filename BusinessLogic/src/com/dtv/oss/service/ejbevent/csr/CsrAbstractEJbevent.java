/*
 * Created on 2005-10-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.csr;
import com.dtv.oss.service.ejbevent.*;
import com.dtv.oss.dto.*;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CsrAbstractEJbevent extends AbstractEJBEvent {
	//true:实际提交 ;false:不提交，只做预判
	private boolean doPost;
	//受理单数据对象
	private CustServiceInteractionDTO csiDto;
	
	public boolean isDoPost() {
		return doPost;
	}
	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
	}
	public CustServiceInteractionDTO getCsiDto() {
		return csiDto;
	}
	public void setCsiDto(CustServiceInteractionDTO csiDto) {
		this.csiDto = csiDto;
	}
}
