/*
 * Created on 2005-11-9
 * 
 * @author whq
 * 
 * 该类利用查询结果来填充CustomerProblem2CPProcessWrap对象
 */
package com.dtv.oss.service.dao.work;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.*;
import com.dtv.oss.dto.wrap.work.CustomerProblem2CPProcessWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class CustomerProblem2CPProcessDAO extends GenericDAO {

    public CustomerProblem2CPProcessDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
            while(rs.next()) {
            CustomerProblem2CPProcessWrap wrap = new CustomerProblem2CPProcessWrap();
            CustomerProblemDTO cpDto;
        	CustProblemProcessDTO cppDto;
            cpDto = DtoFiller.fillCustomerProblemDTO(rs);
            cppDto = DtoFiller.fillCustProblemProcessDTO(rs, "cpp_");
            
            wrap.setCpDto(cpDto);
            wrap.setCppDto(cppDto);
            list.add(wrap);
           }
        return list;
    }
}