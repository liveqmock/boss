/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent;

/**
 * @author Leon Liu
 *
 * Provide default implementation to avoid fussy code for the interface methods 
 */
public abstract class AbstractEJBEvent implements EJBEvent {
	private String  commandClassName = null;
	private int operatorID = 0;
	protected int actionType = -1;
	private String remoteHostAddress ="";

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.ejbevent.EJBEvent#getEJBCommandClassName()
	 */
	public String getEJBCommandClassName() {
		// TODO Auto-generated method stub
		return commandClassName;
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.ejbevent.EJBEvent#setEJBCommandClassName(java.lang.String)
	 */
	public void setEJBCommandClassName(String ejbCommandClassName) {
		// TODO Auto-generated method stub
	    commandClassName = ejbCommandClassName;
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.ejbevent.EJBEvent#getOperatorID()
	 */
	public int getOperatorID() {
		// TODO Auto-generated method stub
		return operatorID;
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.ejbevent.EJBEvent#setOperatorID(int)
	 */
	public void setOperatorID(int i) {
		// TODO Auto-generated method stub
	    operatorID = i;
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.ejbevent.EJBEvent#getRemoteHostAddress()
	 */
	public String getRemoteHostAddress() {
		// TODO Auto-generated method stub
		return remoteHostAddress;
	}

	/* (non-Javadoc)
	 * @see com.dtv.oss.service.ejbevent.EJBEvent#setRemoteHostAddress(java.lang.String)
	 */
	public void setRemoteHostAddress(String remoteHostAddr) {
		// TODO Auto-generated method stub
	    remoteHostAddress = remoteHostAddr;
	}

    /**
     * @return Returns the actionType.
     */
    public int getActionType() {
        return actionType;
    }
    /**
     * @param actionType The actionType to set.
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

}
