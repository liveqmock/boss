package com.dtv.oss.service.dao.csr;
/*
 * Created on 2004-8-9
 * @author Mac Wang
 */

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.dto.AccountItemDTO;

/**
 * author:david.Yang
 */
public class AccountItemDAO extends GenericDAO {

    public AccountItemDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
            AccountItemDTO dto = DtoFiller.fillAccountItemDTO(rs);
            list.add(dto);
        }
        return list;
    }
}