package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.FiberTransmitterDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class FiberTransmitterListHandler extends ValueListHandler {
    private FiberTransmitterDAO dao = null;
    final private String tableName = "t_FiberTransmitter t";


	private FiberTransmitterDTO dto = null;

	public FiberTransmitterListHandler() {
	  	this.dao = new FiberTransmitterDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(FiberTransmitterListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof FiberTransmitterDTO) 
       	 this.dto = (FiberTransmitterDTO)dto1;
       else {
       	LogUtility.log(FiberTransmitterListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(FiberTransmitterDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	makeSQLByIntField("t.id",dto.getId(),selectStatement);
        makeSQLByIntField("t.MachineRoomId",dto.getMachineRoomId(),selectStatement);
        makeSQLByStringField("t.FiberTransmitterCode",dto.getFiberTransmitterCode(),selectStatement);
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