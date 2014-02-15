package com.dtv.oss.service.listhandler;

import com.dtv.oss.service.dao.ExtraDAO;

import java.sql.SQLException;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company: Digivision</p>
 * User: thurm zhang
 * Date: 2005-3-7
 * Time: 8:58:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class ExtraValueListHandler extends ValueListHandler {
    protected String selectQueryString4Object = "";
    private Object extraObj1;

    public Object getExtraObj1() {
        return extraObj1;
    }

    public void setExtraObj1(Object extraObj1) {
        this.extraObj1 = extraObj1;
    }

    protected void getExtraObjectBySQL(String sql) throws ListHandlerException {
        try {
            ExtraDAO extraDAO = new ExtraDAO();
            setExtraObj1(extraDAO.executeSelect(sql));
        } catch (SQLException e) {
            throw new ListHandlerException(e.getMessage());
        }
    }
}
