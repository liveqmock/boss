package com.dtv.oss.service.listhandler.monistat;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CsiStatForHuairouHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz = CsiStatForHuairouHandler.class;
	private List rowCountList =new ArrayList();
	public CsiStatForHuairouHandler(){
		dao=new GenericImpDAO(rowCountList);
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO)dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
		constructSelectQueryString(dto);
		if (dto.getSpareStr2().equals("out")){
			dao.setSpeedFlag(false);
		}else{
			dao.setFrom(getFrom());
		    dao.setTo(getTo());
			dao.setSpeedFlag(true);
			System.out.println("getFrom()----------------->"+getFrom());
			System.out.println("getTo()---------------->"+getTo());
		}
		//执行数据查询
		executeSearch(dao);

		setTotalRecordSize(((Integer)(rowCountList.get(0))).intValue());
	}
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		StringBuffer begin = new StringBuffer();
		String sql =" select " 
				   +"(select (select name from t_districtsetting ds where ds.id =dis.belongto ) || '->' || dis.name from t_districtsetting dis where dis.id=data0) data0 "
			       +",data1,data2,data3,data4,data5,data6,"
			       +" data7,data8,data9,data10,data11,data12,"
			       +" data13,data14,data15,data16,data17,data18,"
			       +" data19,data20,data21,data22,data23,data24,"
			       +" data25,data26,data27,data28,data29 "
			       +" from huairou_stat "
                   +" where  batchStr ='"+dto2.getSpareStr1()+"'"  
			       +" union all "
			       +" select "
			       +" '合  计：' data0 "
			       +",sum(nvl(data1,0)) data1,sum(nvl(data2,0)) data2,sum(nvl(data3,0)) data3, sum(nvl(data4,0)) data4,sum(nvl(data5,0)) data5,sum(nvl(data6,0)) data6,"
			       +" sum(nvl(data7,0)) data7,sum(nvl(data8,0)) data8,sum(nvl(data9,0)) data9, sum(nvl(data10,0)) data10,sum(nvl(data11,0)) data11,sum(nvl(data12,0)) data12,"
			       +" sum(nvl(data13,0)) data13,sum(nvl(data14,0)) data14,sum(nvl(data15,0)) data15, sum(nvl(data16,0)) data16,sum(nvl(data17,0)) data17,sum(nvl(data18,0)) data18,"
			       +" sum(nvl(data19,0)) data19,sum(nvl(data20,0)) data20,sum(nvl(data21,0)) data21, sum(nvl(data22,0)) data22,sum(nvl(data23,0)) data23,sum(nvl(data24,0)) data24,"
                   +" sum(nvl(data25,0)) data25,sum(nvl(data26,0)) data26,sum(nvl(data27,0)) data27, sum(nvl(data28,0)) data28,sum(nvl(data29,0)) data29 "
			       +" from huairou_stat "
                   +" where  batchStr ='"+dto2.getSpareStr1()+"'"  ;
		
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(sql));
	}


}
