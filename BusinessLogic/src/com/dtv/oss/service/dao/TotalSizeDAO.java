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
public class TotalSizeDAO {

    public TotalSizeDAO() {
    }

    public int executeSelect(String selectString) throws SQLException {
        Connection con = null;
        Statement stmt= null;
        ResultSet rs =null;
        int totalSize = 0;
        try {
            DataSource ds = HomeFactory.getFactory().getDataSource();
            con = ds.getConnection();
            stmt = con.createStatement();
            LogUtility.log(TotalSizeDAO.class,LogLevel.DEBUG,"executeSelect",selectString);
            rs = stmt.executeQuery(selectString);
            while (rs.next()) {
            	totalSize = rs.getInt(1);
            }
        }catch(HomeFactoryException he) {
        	LogUtility.log(TotalSizeDAO.class,LogLevel.WARN,"executeSelect",he);
        }
        finally {
        	if (rs !=null) {
        		try {
        			rs.close();
				} catch (SQLException e) {	
				}
        	}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {	
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
        return totalSize;
    }
}