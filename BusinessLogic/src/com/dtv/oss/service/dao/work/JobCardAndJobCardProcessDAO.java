package com.dtv.oss.service.dao.work;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.*;
import com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class JobCardAndJobCardProcessDAO extends GenericDAO {

    public JobCardAndJobCardProcessDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
            while(rs.next()) {
            JobCard2JobCardProcessWrap wrap = new JobCard2JobCardProcessWrap();
            JobCardDTO dto ;
            JobCardProcessDTO jobCardProcessDto;
            dto = DtoFiller.fillJobCardDTO(rs);
            jobCardProcessDto = DtoFiller.fillJobCardProcessDTO(rs,"JCP_");
            wrap.setJobCardDto(dto);
			wrap.setJobCardProcessDto(jobCardProcessDto);
            list.add(wrap);
           }
        return list;
    }
}