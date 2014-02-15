package com.dtv.oss.service.dao.work;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.BatchJobItemDTO;
import com.dtv.oss.dto.wrap.work.Schedule2CP2CCWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ScheduleDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		while(rs.next()){
			Schedule2CP2CCWrap dto =new Schedule2CP2CCWrap();
			BatchJobDTO jobDto=DtoFiller.fillBatchJobDTO(rs);
			BatchJobItemDTO jobItemDto =DtoFiller.fillBatchJobItemDTO(rs,"ITEM_");
			ArrayList itemlist = new ArrayList();
			itemlist.add(jobItemDto);
			dto.setBatchJobDTO(jobDto);
			dto.setBatchJobItemDTOList(itemlist);
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
