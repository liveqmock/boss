package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.TerminateDeviceDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;


public class TerminateDeviceListhandler extends ValueListHandler {
	
	private TerminateDeviceDAO dao=null;
	
	final String tableName=" T_TERMINALDEVICE ";
	
	//查询条件的dto
	private CommonQueryConditionDTO dto=null;
	
	public TerminateDeviceListhandler(){
		dao=new TerminateDeviceDAO();
	}
	
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(TerminateDeviceListhandler.class,LogLevel.DEBUG,"终端设备查询...");
        if (dto instanceof CommonQueryConditionDTO) 
        	this.dto = (CommonQueryConditionDTO)dto;
        else {
        	LogUtility.log(TerminateDeviceListhandler.class,LogLevel.DEBUG,"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //构造查询字符串
        constructSelectQueryString(this.dto);
        //执行数据查询
        executeSearch(dao,true,true);
    }


	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
      
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		
		String devieModel ="";
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())){
		    String[] deviceModels =dto.getSpareStr1().split(";");
		    for (int i=0 ;i< deviceModels.length;i++){
		    	if (devieModel.equals("")){
		    	    devieModel ="'"+deviceModels[i]+"'";
		    	}else{
		    		devieModel =devieModel+","+"'"+deviceModels[i]+"'";
		    	}
		    }
	        //设备型号
		    if(devieModel.indexOf(",")>0){
				selectStatement.append(" and DEVICEMODEL in ("+devieModel+")");
		    }else{
				selectStatement.append(" and DEVICEMODEL ="+devieModel+" ");
		    }
		}
		

		//设备类别
		makeSQLByStringField("DEVICECLASS",dto.getSpareStr3(),selectStatement);
		//仓库ID
		makeSQLByIntField("DEPOTID",HelperCommonUtil.String2Int(dto.getSpareStr2()),selectStatement);
		//设备状态
		makeSQLByStringField("STATUS",dto.getSpareStr4(),selectStatement);
		
		//设备匹配标志 add by david.Yang
		makeSQLByStringField("MATCHFLAG",dto.getSpareStr8(),selectStatement);
		
		//Mac地址
		makeSQLByStringField("MACADDRESS",dto.getSpareStr10(),selectStatement,"like");
		//内部Mac地址
		makeSQLByStringField("INTERMACADDRESS",dto.getSpareStr11(),selectStatement,"like");
		
		if(dto.getCustomerID()>0){
			selectStatement.append(" and deviceid in (select t.deviceid from t_customerproduct t where t.customerid ="+dto.getCustomerID()+")");	
		}
		//起始序号和终止序号
		if (HelperCommonUtil.StringHaveContent(dto.getBeginStr())&&HelperCommonUtil.StringHaveContent(dto.getEndStr())){
			selectStatement.append(" and SERIALNO >= '" +dto.getBeginStr() +"' ");
			selectStatement.append(" and SERIALNO <= '" +dto.getEndStr() +"' ");
		}
		else{
			if (HelperCommonUtil.StringHaveContent(dto.getBeginStr()))
				makeSQLByStringField("SERIALNO",dto.getBeginStr(),selectStatement);
			if (HelperCommonUtil.StringHaveContent(dto.getBeginStr()))
				makeSQLByStringField("SERIALNO",dto.getEndStr(),selectStatement);
		}
		
		//设备用途 add by jason.zhou 2007-5-17
		String strDevicePurpose=BusinessUtility.getDevicePurposeStrListByCSICreateReason(dto.getSpareStr6(),dto.getSpareStr7(),devieModel);
		if(strDevicePurpose!=null && strDevicePurpose.trim().length()>0){
			String [] purposeArray=strDevicePurpose.split(",");
			LogUtility.log(TerminateDeviceListhandler.class, LogLevel.DEBUG, "得到的用途列表为：" + strDevicePurpose);
		
			if(purposeArray!=null){
				selectStatement.append(" and ( PurposeStrList is null or PurposeStrList ='' ");
				for(int i=0;i<purposeArray.length;i++){
					selectStatement.append(" or instr(PurposeStrList,'," + purposeArray[i] +",')>0 ");
				}
				selectStatement.append(" )");
			}
		}

		appendOrderByString(selectStatement);
		
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		
		if ((orderByString == null) ||
				orderByString.trim().equals(""))
			selectStatement.append(" order by DEVICEID desc");
		else {
			selectStatement.append(" order by " + orderByString + orderByAscend);
		}
		
	} 
}
