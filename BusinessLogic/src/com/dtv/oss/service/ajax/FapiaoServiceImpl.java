package com.dtv.oss.service.ajax;

import com.dtv.oss.ajax.entity.LjFapiao;
import com.dtv.oss.domain.AccountItem;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lili on 2014/9/21.
 */
public class FapiaoServiceImpl implements IFapiaoService {
    public int getFapiaoCount(String fapiaodaima, String fapiaohaoma) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int result=0;
        try {
            StringBuffer strSql = new StringBuffer();
            strSql.append("SELECT count(batch) count FROM t_ljfapiao  WHERE state='W' ");
            if(fapiaodaima!=null&&fapiaodaima.trim().length()>0){
                strSql.append(" and fapiaodaima like '"+fapiaodaima+"%'");
            }
            if(fapiaohaoma!=null&&fapiaohaoma.trim().length()>0){
                strSql.append(" and fapiaohaoma like '"+fapiaohaoma+"%'");
            }
            con = DBUtil.getConnection();
            stmt = con.prepareStatement(strSql.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
               result=rs.getInt("count");
            }
        } catch (SQLException e) {
            LogUtility.log(FapiaoServiceImpl.class, LogLevel.WARN,
                    "getProductIDList", e);
        } finally {
            if (rs !=null) {
                try{
                    rs.close();
                }catch (SQLException e){
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
        return result;
    }

    public List getFapiaoList(int startIdx, int endIdx, String fapiaodaima, String fapiaohaoma) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList currentDataList = new ArrayList();
        try {
            StringBuffer strSql = new StringBuffer();
            strSql.append("SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (SELECT * FROM T_LJFAPIAO where   state='W' ");
            if(fapiaodaima!=null&&fapiaodaima.trim().length()>0){
                strSql.append(" and fapiaodaima like '"+fapiaodaima+"%'");
            }
            if(fapiaohaoma!=null&&fapiaohaoma.trim().length()>0){
                strSql.append(" and fapiaohaoma like '"+fapiaohaoma+"%'");
            }
             strSql.append(        ") A WHERE ROWNUM <=? ) WHERE RN >? ");

            con = DBUtil.getConnection();
            stmt = con.prepareStatement(strSql.toString());
            stmt.setInt(1,endIdx);
            stmt.setInt(2,startIdx);

            rs = stmt.executeQuery();
            while (rs.next()) {
                LjFapiao ljFapiao=new LjFapiao();
                ljFapiao.setBatch(rs.getString("batch"));
                ljFapiao.setFapiaohaoma(rs.getString("fapiaohaoma"));
                ljFapiao.setFapiaodaima(rs.getString("fapiaodaima"));
                ljFapiao.setState(rs.getString("state"));
                currentDataList.add(ljFapiao);
            }
        } catch (SQLException e) {
            LogUtility.log(FapiaoServiceImpl.class, LogLevel.WARN,
                    "getProductIDList", e);
        } finally {
            if (rs !=null) {
                try{
                    rs.close();
                }catch (SQLException e){
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
        return currentDataList;
    }

    private int occupyFapiao( String fapiaodaima, String fapiaohaoma) {
        Connection con = null;
        PreparedStatement stmt = null;
        int result=0;
        try {
            StringBuffer strSql = new StringBuffer();
            strSql.append("update t_ljfapiao set state='D' where 1=1 ");

            if(fapiaodaima!=null&&fapiaodaima.trim().length()>0){
                strSql.append(" and fapiaodaima= ?"+fapiaodaima);
            }
            if(fapiaohaoma!=null&&fapiaohaoma.trim().length()>0){
                strSql.append(" and fapiaohaoma= ?"+fapiaohaoma);
            }
            con = DBUtil.getConnection();
            stmt = con.prepareStatement(strSql.toString());
            int haomaidx=1;
            if(fapiaodaima!=null&&fapiaodaima.trim().length()>0){
                stmt.setString(1,fapiaodaima);
                haomaidx=2;
            }
            if(fapiaohaoma!=null&&fapiaohaoma.trim().length()>0){
                stmt.setString(haomaidx,fapiaohaoma);
            }
            result = stmt.executeUpdate();

        } catch (SQLException e) {
            LogUtility.log(FapiaoServiceImpl.class, LogLevel.WARN,
                    "getProductIDList", e);
        } finally {

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
        return result;
    }
}
