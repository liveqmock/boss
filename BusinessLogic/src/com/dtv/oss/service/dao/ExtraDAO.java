package com.dtv.oss.service.dao;

import com.dtv.oss.dto.wrap.customer.RealIncomeAndFeeCountWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company: Digivision</p>
 * User: thurm zhang
 * Date: 2005-3-7
 * Time: 9:03:50
 * To change this template use File | Settings | File Templates.
 */
public class ExtraDAO {
    public Object executeSelect(String selectString) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        Object extraObj = null;
        try {
            DataSource ds = HomeFactory.getFactory().getDataSource();
            con = ds.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);
            if (rs.next()) {
                extraObj = rs.getObject(1);
            }
            stmt.close();
            rs.close();
        } catch (HomeFactoryException he) {
        	LogUtility.log(ExtraDAO.class,LogLevel.WARN,"executeSelect",he);
        } catch (SQLException sqle) {
        	LogUtility.log(ExtraDAO.class,LogLevel.WARN,"executeSelect",sqle);
            throw sqle;
        } finally {
            con.close();
        }

        return extraObj;
    }
    
    public HashMap executeSelectForSumByGroup(String selectString) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        HashMap linkedHashMap = new LinkedHashMap();
        try {
            DataSource ds = HomeFactory.getFactory().getDataSource();
            con = ds.getConnection();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);
            while (rs.next()) {
            	RealIncomeAndFeeCountWrap wrap =new RealIncomeAndFeeCountWrap();
            	wrap.setValue(rs.getDouble(2));
            	wrap.setRecordCount(rs.getInt(3));
            	linkedHashMap.put(rs.getString(1), wrap);
            }
            stmt.close();
            rs.close();
        } catch (HomeFactoryException he) {
        	LogUtility.log(ExtraDAO.class,LogLevel.WARN,"executeSelect For SumByGroup",he);
        } catch (SQLException sqle) {
        	LogUtility.log(ExtraDAO.class,LogLevel.WARN,"executeSelect For SumByGroup",sqle);
            throw sqle;
        } finally {
            con.close();
        }

        return linkedHashMap;
    }
}
