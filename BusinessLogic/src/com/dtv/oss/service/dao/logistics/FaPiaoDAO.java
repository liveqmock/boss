package com.dtv.oss.service.dao.logistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * <p>Title: BOSS_P5 </p>
 * <p>Description: 封装发票查询结果</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SHDV</p>
 * @author 
 * @version 1.0
 */

public class FaPiaoDAO extends GenericDAO {
  protected List prepareResult(ResultSet rs) throws SQLException {
    ArrayList list = new ArrayList();
    while(rs.next()) {
                    list.add(DtoFiller.fillFaPiaoDTO(rs));
    }
    return list;
}

}
