package com.dtv.oss.service.dao.work;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.wrap.work.Schedule2CP2CCWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ScheduleAllDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		Schedule2CP2CCWrap dto=null;
		BatchJobDTO batchJobDTO=null;
		
		while(rs.next()){
			dto=new Schedule2CP2CCWrap();
			batchJobDTO = DtoFiller.fillBatchJobDTO(rs);
			dto.setBatchJobDTO(batchJobDTO);
			
			list.add(dto);
		}
		
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
