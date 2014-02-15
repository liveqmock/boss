package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.MachineRoomDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.MachineRoomDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class MachineRoomListHandler extends ValueListHandler {
    private MachineRoomDAO dao = null;
    final private String tableName = "t_MachineRoom t";


	private MachineRoomDTO dto = null;

	public MachineRoomListHandler() {
	  	this.dao = new MachineRoomDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(MachineRoomListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof MachineRoomDTO) 
       	 this.dto = (MachineRoomDTO)dto1;
       else {
       	LogUtility.log(MachineRoomListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(MachineRoomDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	makeSQLByIntField("t.id",dto.getId(),selectStatement);
        makeSQLByStringField("t.MachineRoomCode",dto.getMachineRoomCode(),selectStatement);
        makeSQLByStringField("t.MachineRoomName",dto.getMachineRoomName(),selectStatement,"like");
        if(dto.getDistrictId()>0)
          appendStringWithDistrictID("t.districtid", dto.getDistrictId(),
				selectStatement);
       // makeSQLByIntField("t.districtid",dto.getDistrictId(),selectStatement);
        makeSQLByStringField("t.DetailAddress",dto.getDetailAddress(),selectStatement,"like");
		 	
		
		selectStatement.append(" order by t.id desc");     
       
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}