package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.PhoneNoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author Stone Liang
 * Created on 2006-5-26
 */
public class PhoneNoListHandler extends ValueListHandler {
    private PhoneNoDAO dao = null;
    private String selectCriteria = "";
    private ResourcePhoneNoDTO dto = null;
    final private String tableName = "t_resource_phoneno a";

    public PhoneNoListHandler() {
	  	this.dao = new PhoneNoDAO();
	}
    
    public void setCriteria(Object dto)  throws ListHandlerException{
    	LogUtility.log(PhoneNoListHandler.class,LogLevel.DEBUG,"电话号码查询...");  
        if (dto instanceof ResourcePhoneNoDTO) 
        	this.dto = (ResourcePhoneNoDTO)dto;
        else {
        	LogUtility.log(PhoneNoListHandler.class,LogLevel.DEBUG,"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");	
        }
               
        
        //构造查询字符串
        constructSelectQueryString(this.dto);   
        //执行数据查询
        executeSearch(dao,true,true);
    }

    private void constructSelectQueryString(ResourcePhoneNoDTO dto) {
        StringBuffer begin = new StringBuffer();
       	begin.append("select a.* "  + " from " + tableName);
   
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		
		makeSQLByStringField("grade",dto.getGrade(),selectStatement);
		//增加地区选择
//		makeSQLByIntField("DISTRICTID",dto.getDistrictId(),selectStatement);
		if (dto.getDistrictId() != 0) {
			selectStatement.append(" and DISTRICTID in ");
			selectStatement
					.append("(Select id  From T_DISTRICTSETTING bbb Where ((Select Count(*) from t_resource_phoneno aa Where aa.districtid=bbb.Id)>0) connect by prior belongto=Id start with ID=");
			selectStatement.append(dto.getDistrictId());
			selectStatement.append(")");
		}
		if (HelperCommonUtil.StringHaveContent(dto.getPhoneNo()))
				makeSQLByStringFieldSuffix("a.phoneno", dto.getPhoneNo(), selectStatement, "like");
		appendString("a.status = '" + CommonConstDefinition.PHONENO_STATUS_AVAILABLE + "'", selectStatement);
		
		appendOrderByString(selectStatement);
        // 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement.toString()));
    	LogUtility.log(PhoneNoListHandler.class,LogLevel.DEBUG,"查询电话的sql"+begin.toString());
    }

	private void appendOrderByString(StringBuffer selectStatement) {
			selectStatement.append(" order by a.itemid desc");
	}
    
    
}
