package com.dtv.oss.service.dao;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.factory.HomeFactory;
import com.dtv.oss.service.factory.HomeFactoryException;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class TotalSizeDAO4SCNA {

    public TotalSizeDAO4SCNA() {
    }

    public int executeSelect(String selectString) throws SQLException {
        Connection con = null;
        Statement stmt= null;
        int totalSize = 0;
        try {
            DataSource ds = HomeFactory.getFactory().getDataSource4ScnA();
            con = ds.getConnection();
            stmt = con.createStatement();
            LogUtility.log(TotalSizeDAO4SCNA.class,LogLevel.DEBUG,"executeSelect",selectString);
            ResultSet rs = stmt.executeQuery(selectString);
            while (rs.next()) {
            	totalSize = rs.getInt(1);
            }
            stmt.close();

        }catch(HomeFactoryException he) {
        	LogUtility.log(TotalSizeDAO4SCNA.class,LogLevel.WARN,"executeSelect",he);
        }
        finally {
            con.close();
        }
        return totalSize;
    }
}