package com.dtv.oss.service.dao.logistics;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.service.dao.GenericDAO;

import java.util.ArrayList;
 

/**
 * 供应商设备查找封装DAO
 * 封装的对象为Ter
 */
public class VendorDeviceDAO extends GenericDAO {
	
  protected List prepareResult(ResultSet rs) throws SQLException {
    ArrayList list = new ArrayList();
    while(rs.next()) {
          TerminalDeviceDTO dto = new TerminalDeviceDTO();
          
          dto.setSerialNo(rs.getString("SERIALNO"));
          dto.setAddressID(rs.getInt("ADDRESSID"));
          dto.setAddressType(rs.getString("ADDRESSTYPE"));
          dto.setDeviceClass(rs.getString("DEVICECLASS"));
          dto.setDeviceModel(rs.getString("DEVICEMODEL"));
          dto.setGuaranteeLength(rs.getInt("GUARANTEELENGTH"));
          dto.setInterMACAddress(rs.getString("INTERMACADDRESS"));
          dto.setMACAddress(rs.getString("MACADDRESS"));
          dto.setMatchDeviceID(rs.getInt("MATCHDEVICEID"));
          dto.setMatchFlag(rs.getString("MATCHFLAG"));

          list.add(dto);
    }
    return list;
}

}