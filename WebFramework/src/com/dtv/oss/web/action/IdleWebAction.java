package com.dtv.oss.web.action;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.web.action.WebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.service.ejbevent.EJBEvent;

/**
 * <p>Title: BOSS P4</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * Sometimes you maybe need the web action doing nothing.
 * IdleWebAction will be the right one for this.
 * @author Horace Lin
 * @version 1.0
 */
public class IdleWebAction extends WebAction{
  public EJBEvent perform(HttpServletRequest request) throws WebActionException {

          return null;

  }

}