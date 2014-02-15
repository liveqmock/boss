package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.TerminateDeviceDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;


public class SCDeviceListhandler extends ValueListHandler {
	
	private TerminateDeviceDAO dao=null;
	
	private String tableName = "T_TERMINALDEVICE";
	
	//查询条件的dto
	private CommonQueryConditionDTO dto=null;
	
	public SCDeviceListhandler(){
		dao=new TerminateDeviceDAO();
	}
	
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(SCDeviceListhandler.class,LogLevel.DEBUG,"终端设备查询...");
        if (dto instanceof CommonQueryConditionDTO) 
        	this.dto = (CommonQueryConditionDTO)dto;
        else {
        	LogUtility.log(SCDeviceListhandler.class,LogLevel.DEBUG,"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //构造查询字符串
        constructSelectQueryString(this.dto);
        //执行数据查询
        executeSearch(dao,true,true);
    }


	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from T_TERMINALDEVICE");
      
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
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr2()))
		{
			String startSN = dto.getSpareStr2();
			selectStatement.append(" and SERIALNO>='" + startSN + "' ");
		}

		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr3()))
		{
			String startSN = dto.getSpareStr3();
			selectStatement.append(" and SERIALNO<='" + startSN + "' ");
		}
		selectStatement.append(" and Status in('W','S') ");
		
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr4()))
		{
			String strFlag = dto.getSpareStr4();
System.out.println("listhandle type=" + strFlag);			
			if("auth".equals(strFlag))
				selectStatement.append(" and preauthorization='N' ");
			if("disauth".equals(strFlag))
				selectStatement.append(" and preauthorization='Y' ");
			if("dismatch".equals(strFlag))
				selectStatement.append(" and matchflag='Y' ");
			
		}

System.out.println(selectStatement);
//		appendOrderByString(selectStatement);
		
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
/*	
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		
		if ((orderByString == null) ||
				orderByString.trim().equals(""))
			selectStatement.append(" order by DEVICEID desc");
		else {
			selectStatement.append(" order by " + orderByString + orderByAscend);
		}
		
	}*/
}
