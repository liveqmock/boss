package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * 查询费用记录DAO
 * author     ：Jason.Zhou 
 * date       : 2006-2-13
 * description:
 * @author 250713z
 *
 */
public class FeeRecordAllDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		
		ArrayList list=new ArrayList();
		
		while(rs.next()){
			AccountItemDTO dto=DtoFiller.fillAccountItemDTO(rs);
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
