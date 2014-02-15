/*
 * Created on 2004-8-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.system;
import com.dtv.oss.dto.OperatorDTO;
/**
 * @author 240322y
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperatorEJBEvent extends SystemEJBEvent{
	private OperatorDTO operator;
	public OperatorEJBEvent(int actionType, OperatorDTO operator){
		this.actionType = actionType;
		this.operator = operator;
	}	
	
	public OperatorDTO getOperator() {
		return operator;
	}

	public void setOperator(OperatorDTO operator) {
		this.operator = operator;
	}
	
}
