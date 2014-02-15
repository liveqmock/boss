package com.dtv.oss.web.action;

import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.exception.TokenInvalidException;
import com.dtv.oss.service.ejbevent.EJBEvent;

import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebKeys;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * This class implements use of the token and capture exceptions throwed from encapsulating data.
 * Alougth it finishs the mechanism of token, but it doesn't invoke checking token itself.
 * the class which inherits it have to override the method of needCheckToken()
 * @author Horace Lin
 * @version 1.0
 */

public abstract class GeneralWebAction extends WebAction{

    protected String getResponseAttributeName()
    {
        return "ResponseFromEJBEvent";
    }

    protected boolean needCheckToken()
    {
        return false;
    }

    protected boolean checkValidBeforePerform(HttpServletRequest request) throws WebActionException{

        if (needCheckToken())
        {
            //check token
            if (!isTokenValid(request))
            {
                throw new TokenInvalidException("该操作不允许；或者您的操作已被受理，请不要重复提交");
            }

            //After checking token, the current token must be removed.
            resetToken(request);
        }

        return true;
    }

    protected abstract EJBEvent encapsulateData(HttpServletRequest request) throws Exception;

    public EJBEvent perform(HttpServletRequest request) throws WebActionException {
        WebPrint.PrintDebugInfo(this.getClass().getName(), "perform method enter ...");
        WebPrint.PrintRequestParameter(this.getClass().getName(),request);

        if (!checkValidBeforePerform(request))
        {
            WebPrint.PrintErrorInfo(this.getClass().getName(), "Before performing, the action is invalid by checking!");
            return null;
        }

        try
        {
            return encapsulateData(request);

        }catch(WebActionException we) {
            WebPrint.SetRequestExceptionErrorInfo(this.getClass().getName(), request, we);
        }catch(NullPointerException ne) {
            WebPrint.SetRequestExceptionErrorInfo(this.getClass().getName(), request, ne);
        }catch(Exception e) {
            //normal process for unknown exception
            WebPrint.SetRequestExceptionErrorInfo(this.getClass().getName(), request, e);
        }

        return null;
    }

    protected  void afterExceptionHappen(HttpServletRequest request)
    {
        if (needCheckToken())
        {
          //if exception which is not caused by TokenInvalidException is happened, a token will be regenerated.
          if (! (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)instanceof
                 TokenInvalidException))
            saveToken(request);
        }
    }

    protected  void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse) {}

    public void doEnd(HttpServletRequest request, CommandResponse cmdResponse)
    {
        WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd method enter ...");

        //check whether some exceptions are happened
        if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)!=null)
        {
            afterExceptionHappen(request);
            WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd Exception out");
        	//2005/12/11由杨晨删除----start-------
            //return;
        	//2005/12/11由杨晨删除----end---------
        }

        if (cmdResponse != null)
        {
            request.setAttribute(getResponseAttributeName(), cmdResponse);
            afterSuccessfulResponse(request, cmdResponse);
        }
        else
            WebPrint.PrintErrorInfo(this.getClass().getName(), "cmdResponse is null...");

        WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd out");

    }

}