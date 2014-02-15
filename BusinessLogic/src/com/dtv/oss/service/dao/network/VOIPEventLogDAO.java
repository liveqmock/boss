package com.dtv.oss.service.dao.network;
/**

 * @author 260904l
 *
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dtv.oss.dto.wrap.VOIPEventWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class VOIPEventLogDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			VOIPEventWrap wrap = new VOIPEventWrap();
			wrap.setSysEventDTO(DtoFiller.fillSystemEventDTO(rs));
			list.add(wrap);
		}
		return list;
	}

}
