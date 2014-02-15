package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CatvTerminalDAO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HelperCommonUtil;

public class CatvTerminalListHandler extends ValueListHandler {
	
    private GenericDAO dao = null;
    private String selectCriteria = "";
    private CommonQueryConditionDTO dto = null;
    private String tableName = "t_catvterminal catv left join t_customer cust on catv.id = cust.catvid";
    
    public CatvTerminalListHandler() {
	  	this.dao = new CatvTerminalDAO();
	}
    
    public void setCriteria(Object dto)  throws ListHandlerException{
    	LogUtility.log(CatvTerminalListHandler.class,LogLevel.DEBUG,"终端查询..setCriteria.");  
        if (dto instanceof CommonQueryConditionDTO) 
        	this.dto = (CommonQueryConditionDTO)dto;
        else {
        	LogUtility.log(CatvTerminalListHandler.class,LogLevel.DEBUG,"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");	
        }
               
        
        //构造查询字符串
        constructSelectQueryString(this.dto);   
        //执行数据查询
        executeSearch(dao,true,true);
    }

    private void constructSelectQueryString(CommonQueryConditionDTO dto) {
    	LogUtility.log(CatvTerminalListHandler.class,LogLevel.DEBUG,"终端查询..constructSelectQueryString."); 
        StringBuffer begin = new StringBuffer();
        if(dto.getSpareStr21()!=null){ //只查询终端标志
        	begin.append("select catv.Id,catv.catvtermtype,catv.conbatchid,catv.RecordSource,catv.status,catv.statusreason,catv.postcode,catv.DistrictID,catv.DetailAddress,catv.portNo,catv.FiberNodeID,catv.cabletype,catv.bidirectionflag,catv.Createdate,catv.activedate,catv.pausedate,catv.dt_create,catv.dt_lastmod,catv.canceldate,catv.comments from t_catvterminal catv ");
		}else{ //联合查询客户表
			begin.append("select catv.Id,catv.catvtermtype,catv.conbatchid,catv.RecordSource,catv.status,catv.statusreason,catv.postcode,catv.DistrictID,catv.DetailAddress,catv.portNo,catv.FiberNodeID,catv.cabletype,catv.bidirectionflag,catv.Createdate,catv.activedate,catv.pausedate,catv.dt_create,catv.dt_lastmod,catv.canceldate,cust.name as comments ,cust.customerid "  + " from " + tableName);
		}
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		//selectStatement.append(" where 1=1 and cust.status <> 'C' "); //客户状态不为取消

		 /*
		 * Search Condition:
		 * ID,CatvID,County
		 * DetailedAddress, status, statusreason
		 */
		if(dto.getSpareStr1()!=null)
			makeSQLByStringField("catv.id", dto.getSpareStr1(), selectStatement);
		
		/**
		if (dto.getSpareStr2() != null && !dto.getSpareStr2().trim().equals("")) {
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr2())){
			String[] arrIDs = dto.getSpareStr2().split(";");
			if (arrIDs.length > 1) {
				appendString("catv.CatvCode >= '" + arrIDs[0] + "'", selectStatement);
				appendString("catv.CatvCode <= '" + arrIDs[1] + "'", selectStatement);
			} else
				makeSQLByStringFieldSuffix("a.CatvCode", dto.getSpareStr2(), selectStatement, "like");
		}
		}
		**/
		
		//makeSQLByStringField("catv.CatvCode", dto.getSpareStr2(), selectStatement);
		makeSQLByStringField("catv.DetailAddress", dto.getSpareStr3(), selectStatement, "like");
		if(dto.getSpareStr4()!=null) makeSQLByIntField("catv.PortNo", Integer.parseInt(dto.getSpareStr4()), selectStatement);
		if(dto.getSpareStr5()!=null){
			appendString( " districtid in (select ID from t_districtsetting WHERE STATUS='V' connect by prior ID=BELONGTO start with ID="+dto.getSpareStr5()+") ", selectStatement);
			//makeSQLByIntField("catv.DistrictID", Integer.parseInt(dto.getSpareStr5()), selectStatement);
		}	
		if(dto.getSpareStr6()!=null) makeSQLByIntField("catv.FiberNodeID", Integer.parseInt(dto.getSpareStr6()), selectStatement);
		makeSQLByStringField("catv.status", dto.getSpareStr7(), selectStatement);
		makeSQLByStringField("catv.CableType", dto.getSpareStr8(), selectStatement);
		makeSQLByStringField("catv.RecordSource", dto.getSpareStr9(), selectStatement);
		makeSQLByStringField("catv.BiDirectionFlag", dto.getSpareStr10(), selectStatement);
		makeSQLByStringField("catv.CatvTermType", dto.getSpareStr22(), selectStatement);
		
		if(dto.getSpareStr11()!=null){
			appendString( " catv.CreateDate >= to_timestamp('"+dto.getSpareStr11()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		if(dto.getSpareStr12()!=null){
			appendString( " catv.CreateDate <= to_timestamp('"+dto.getSpareStr12()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		
		if(dto.getSpareStr13()!=null){
			appendString( " catv.PauseDate >= to_timestamp('"+dto.getSpareStr13()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		if(dto.getSpareStr14()!=null){
			appendString( " catv.PauseDate <= to_timestamp('"+dto.getSpareStr14()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		
		if(dto.getSpareStr15()!=null){
			appendString( " catv.ActiveDate >= to_timestamp('"+dto.getSpareStr15()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		if(dto.getSpareStr16()!=null){
			appendString( " catv.ActiveDate <= to_timestamp('"+dto.getSpareStr16()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		
		if(dto.getSpareStr17()!=null){
			appendString( " catv.CancelDate >= to_timestamp('"+dto.getSpareStr17()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		if(dto.getSpareStr18()!=null){
			appendString( " catv.CancelDate <= to_timestamp('"+dto.getSpareStr18()+"','YYYY-MM-DD-HH24:MI:SSxff') ", selectStatement);
		}
		
		if(dto.getSpareStr19()!=null){
			makeSQLByStringField("cust.name", dto.getSpareStr19(), selectStatement, "like");
		}
		
		appendOrderByString(selectStatement,dto.getSpareStr20());
        // 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement.toString()));
    }

	private void appendOrderByString(StringBuffer selectStatement,String orderby) {
			if(orderby != null){
				if("ID_desc".equals(orderby))
					selectStatement.append(" order by catv.id desc");
				if("ID_asc".equals(orderby))
					selectStatement.append(" order by catv.id asc");
				if("address_desc".equals(orderby))
					selectStatement.append(" order by catv.detailaddress desc");
				if("address_asc".equals(orderby))
					selectStatement.append(" order by catv.detailaddress asc");
			}
			else{
				selectStatement.append(" order by catv.id desc");
			}
	}
    
    
}
