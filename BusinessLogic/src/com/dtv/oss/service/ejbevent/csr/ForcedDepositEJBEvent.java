/*
 * Created on 2005-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.csr;

import java.util.*;

/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ForcedDepositEJBEvent extends CsrAbstractEJbevent {
	public ForcedDepositEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	//Ñº½ð
	private Collection  forcedDepositCol;
	public Collection getForcedDepositCol() {
		return this.forcedDepositCol;
	}
	public void setForcedDepositCol(Collection forcedDepositCol) {
		this.forcedDepositCol = forcedDepositCol;
	}
}
