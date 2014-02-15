/*
 * Created on 2005-9-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

/**
 * @author Leon Liu
 *
 * 组件类的super 类
 */
public abstract class AbstractService {
    private int operatorID;
    /**
     * @return Returns the operatorID.
     */
    public int getOperatorID() {
        return operatorID;
    }
    /**
     * @param operatorID The operatorID to set.
     */
    public void setOperatorID(int operatorID) {
        this.operatorID = operatorID;
    }
}
