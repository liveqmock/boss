package com.dtv.oss.service.dao.logistics;

import com.dtv.oss.dto.FapiaoTransitionDetailDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: BOSS_P5 </p>
 * <p>Description: 封装发票流转记录查询结果</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SHDV</p>
 * @author 
 * @version 1.0
 */


public class FapiaoTransDetailDAO extends GenericDAO
{

    public FapiaoTransDetailDAO()
    {
    }

    protected List prepareResult(ResultSet rs)
        throws SQLException
    {
        ArrayList list = new ArrayList();
        FapiaoTransitionDetailDTO dto;
        for(; rs.next(); list.add(dto))
        {
            dto = new FapiaoTransitionDetailDTO();
            DtoFiller.fillFapiaoTransitionDetailDTO(dto, rs);
        }

        return list;
    }
}