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
	
	//��ѯ������dto
	private CommonQueryConditionDTO dto=null;
	
	public SCDeviceListhandler(){
		dao=new TerminateDeviceDAO();
	}
	
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(SCDeviceListhandler.class,LogLevel.DEBUG,"�ն��豸��ѯ...");
        if (dto instanceof CommonQueryConditionDTO) 
        	this.dto = (CommonQueryConditionDTO)dto;
        else {
        	LogUtility.log(SCDeviceListhandler.class,LogLevel.DEBUG,"����Ĳ��Ҳ�������...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //�����ѯ�ַ���
        constructSelectQueryString(this.dto);
        //ִ�����ݲ�ѯ
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
	        //�豸�ͺ�
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
		//���õ�ǰ���ݲ�ѯsql
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
