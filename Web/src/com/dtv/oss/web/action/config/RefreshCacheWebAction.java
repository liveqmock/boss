package com.dtv.oss.web.action.config;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Chen jiang
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.WebAction;
import com.dtv.oss.web.exception.WebActionException;

public class RefreshCacheWebAction extends WebAction {

    public EJBEvent perform(HttpServletRequest request) throws WebActionException {
            Postern.reInit();
            return null;

    }

}