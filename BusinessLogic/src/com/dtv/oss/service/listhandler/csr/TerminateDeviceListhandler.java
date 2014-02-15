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
	
	//��ѯ������dto
	private CommonQueryConditionDTO dto=null;
	
	public TerminateDeviceListhandler(){
		dao=new TerminateDeviceDAO();
	}
	
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(TerminateDeviceListhandler.class,LogLevel.DEBUG,"�ն��豸��ѯ...");
        if (dto instanceof CommonQueryConditionDTO) 
        	this.dto = (CommonQueryConditionDTO)dto;
        else {
        	LogUtility.log(TerminateDeviceListhandler.class,LogLevel.DEBUG,"����Ĳ��Ҳ�������...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //�����ѯ�ַ���
        constructSelectQueryString(this.dto);
        //ִ�����ݲ�ѯ
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
	        //�豸�ͺ�
		    if(devieModel.indexOf(",")>0){
				selectStatement.append(" and DEVICEMODEL in ("+devieModel+")");
		    }else{
				selectStatement.append(" and DEVICEMODEL ="+devieModel+" ");
		    }
		}
		

		//�豸���
		makeSQLByStringField("DEVICECLASS",dto.getSpareStr3(),selectStatement);
		//�ֿ�ID
		makeSQLByIntField("DEPOTID",HelperCommonUtil.String2Int(dto.getSpareStr2()),selectStatement);
		//�豸״̬
		makeSQLByStringField("STATUS",dto.getSpareStr4(),selectStatement);
		
		//�豸ƥ���־ add by david.Yang
		makeSQLByStringField("MATCHFLAG",dto.getSpareStr8(),selectStatement);
		
		//Mac��ַ
		makeSQLByStringField("MACADDRESS",dto.getSpareStr10(),selectStatement,"like");
		//�ڲ�Mac��ַ
		makeSQLByStringField("INTERMACADDRESS",dto.getSpareStr11(),selectStatement,"like");
		
		if(dto.getCustomerID()>0){
			selectStatement.append(" and deviceid in (select t.deviceid from t_customerproduct t where t.customerid ="+dto.getCustomerID()+")");	
		}
		//��ʼ��ź���ֹ���
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
		
		//�豸��; add by jason.zhou 2007-5-17
		String strDevicePurpose=BusinessUtility.getDevicePurposeStrListByCSICreateReason(dto.getSpareStr6(),dto.getSpareStr7(),devieModel);
		if(strDevicePurpose!=null && strDevicePurpose.trim().length()>0){
			String [] purposeArray=strDevicePurpose.split(",");
			LogUtility.log(TerminateDeviceListhandler.class, LogLevel.DEBUG, "�õ�����;�б�Ϊ��" + strDevicePurpose);
		
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
		//���õ�ǰ���ݲ�ѯsql
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
