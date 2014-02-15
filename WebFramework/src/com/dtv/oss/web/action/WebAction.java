package com.dtv.oss.web.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.web.util.TokenProcessor;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * This is the base class of web action
 * All of classes inheriting it must rewrite the method of perform
 * @author Horace Lin
 * @version 1.0
 */
public abstract class WebAction {

    protected ServletContext context;

    public void setServletContext(ServletContext context) {
        this.context  = context;
    }

    /**
     * An instance of TokenProcessor to use for token functionality.
     * @TODO We can make this variable protected and remove Action's token methods
     * or leave it private and allow the token methods to delegate their calls.
     */
    private static TokenProcessor token = TokenProcessor.getInstance();


    public void doStart(HttpServletRequest request){}
    public abstract EJBEvent perform(HttpServletRequest request) throws WebActionException;
    public void doEnd(HttpServletRequest request, CommandResponse cmdResponse)throws WebActionException{}


    /*
    //re-encode chinese character to let system know, or it'll become unrecognizable
    protected String parseCH(String s) throws UnsupportedEncodingException {
        if (s == null) return null;
        return s;
    }
    //the problem about Chinese charater had been solved with adding the following sentenses in weblogic.xml
    <charset-params>
    <input-charset>
      <resource-path>*</resource-path>
      <java-charset-name>GBK</java-charset-name>
    </input-charset>
    <charset-mapping>
      <iana-charset-name>Shift-JIS</iana-charset-name>
      <java-charset-name>GBK</java-charset-name>
    </charset-mapping>
    </charset-params>
    */

   /**
     * Generate a new transaction token, to be used for enforcing a single
     * request for a particular transaction.
     *
     * @param request The request we are processing
     */
    protected String generateToken(HttpServletRequest request) {
        return token.generateToken(request);
    }

    /**
     * Return <code>true</code> if there is a transaction token stored in
     * the user's current session, and the value submitted as a request
     * parameter with this action matches it.  Returns <code>false</code>
     * under any of the following circumstances:
     * <ul>
     * <li>No session associated with this request</li>
     * <li>No transaction token saved in the session</li>
     * <li>No transaction token included as a request parameter</li>
     * <li>The included transaction token value does not match the
     *     transaction token in the user's session</li>
     * </ul>
     *
     * @param request The servlet request we are processing
     */
    protected boolean isTokenValid(HttpServletRequest request) {

        return token.isTokenValid(request, false);

    }


    /**
     * Return <code>true</code> if there is a transaction token stored in
     * the user's current session, and the value submitted as a request
     * parameter with this action matches it.  Returns <code>false</code>
     * <ul>
     * <li>No session associated with this request</li>
     * <li>No transaction token saved in the session</li>
     * <li>No transaction token included as a request parameter</li>
     * <li>The included transaction token value does not match the
     *     transaction token in the user's session</li>
     * </ul>
     *
     * @param request The servlet request we are processing
     * @param reset Should we reset the token after checking it?
     */
    protected boolean isTokenValid(
        HttpServletRequest request,
        boolean reset) {

        return token.isTokenValid(request, reset);
    }


    /**
     * Reset the saved transaction token in the user's session.  This
     * indicates that transactional token checking will not be needed
     * on the next request that is submitted.
     *
     * @param request The servlet request we are processing
     */
    protected void resetToken(HttpServletRequest request) {
        token.resetToken(request);
    }

    /**
     * Save a new transaction token in the user's current session, creating
     * a new session if necessary.
     *
     * @param request The servlet request we are processing
     */

    protected void saveToken(HttpServletRequest request) {
        token.saveToken(request);
    }

}