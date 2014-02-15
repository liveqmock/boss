/**
 *
 * <p>Title: BOSS 2</p>
 * <p>Description: BOSS second iterative development project</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai DigiVision Technology Co. Ltd</p>
 * @author Leon Liu
 * @version 1.2
 */
package com.dtv.oss.service.command;

import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.controller.EJBEventProcessor;
import com.dtv.oss.service.ejbevent.EJBEvent;

import javax.naming.Context;

/**
 *  This is the super class of software architecture in service tier,
 *  which is used to implement the business logic.
 */
public abstract class Command {
    private boolean verbose = false;
    protected EJBEventProcessor processor = null;;
    //HomeFactory homeFac = null;
    protected Context ctx = null;

    public boolean getVerbose() { return verbose; }
    public void setVerbose(boolean val) { verbose = val; }

    /**
     * This method is used to initialize some important private property,like
     * EJBEventProcessor, its sub-class can use this property to do something.
     * This method is called by framework.
     * @param processor: EJBEventProcessor
     * @param ctx: Context
     */
    public void init(EJBEventProcessor processor, Context ctx) {
        this.processor = processor;
        this.ctx = ctx;
    }

    /**
     * Before do the real job, prepare something if any
     */
    public void doStart() {};

    /**
     *  This method is used to complete the business logic in sub-class
     * @param ev: business related data
     * @return: CommandResponse contains return value, null is allowed
     * @throws CommandException
     */
    public abstract CommandResponse execute(EJBEvent ev) throws CommandException;

    /**
     * do some clean up job if any
     * add param ev by Mac 2004/2/3
     */
    public void doEnd(EJBEvent ev) {}
	protected void printMessage(String message) {
		if (getVerbose()) System.out.println(message);
	}
}