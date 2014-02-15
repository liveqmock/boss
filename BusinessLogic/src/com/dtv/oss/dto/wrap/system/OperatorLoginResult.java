/*
 * Created on 2004-9-2
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.dto.wrap.system;

import java.io.Serializable;
import com.dtv.oss.dto.OperatorDTO;
import java.util.Map;
/**
 * @author 240322y
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
public class OperatorLoginResult implements Serializable {
	private OperatorDTO operator;
	private Map privileges;
	
	public OperatorLoginResult() {
	}

	public OperatorLoginResult(OperatorDTO operator, Map privileges) {
		super();
		this.operator = operator;
		this.privileges = privileges;
	}

	/**
	 * @return Returns the operator.
	 */
	public OperatorDTO getOperator() {
		return operator;
	}

	/**
	 * @param operator The operator to set.
	 */
	public void setOperator(OperatorDTO operator) {
		this.operator = operator;
	}

	/**
	 * @return Returns the privileges.
	 */
	public Map getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges The privileges to set.
	 */
	public void setPrivileges(Map privileges) {
		this.privileges = privileges;
	}

}
