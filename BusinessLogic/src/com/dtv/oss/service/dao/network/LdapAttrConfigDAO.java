package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.LdapAttrConfigDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
/**
 * 设备管理配置
 * @author Chen jiang
 *
 */
public class LdapAttrConfigDAO  extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	LdapAttrConfigDTO dto = DtoFiller.fillLdapAttrConfigDTO(rs);
	            list.add(dto);		
	        }
	        LogUtility.log(this.getClass(), LogLevel.DEBUG,
			"查询DAO得到list.size():"+list.size());
	        return list;
	}

}
