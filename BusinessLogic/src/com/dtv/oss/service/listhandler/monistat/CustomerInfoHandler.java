package com.dtv.oss.service.listhandler.monistat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CustomerInfoHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz = CustomerInfoHandler.class;
	private List rowCountList =new ArrayList();
	public CustomerInfoHandler(){
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
		dao.setSpeedFlag(false);
		
		//执行数据查询
		executeSearch(dao);

		setTotalRecordSize(((Integer)(rowCountList.get(0))).intValue());
	}
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		StringBuffer begin = new StringBuffer();
		String sql ="select dist.name,sum(v_1.总户数),";
		Iterator it=dto2.getReportMap().keySet().iterator();
        while (it.hasNext()){
        	String columnName =(String)it.next();
        	sql =sql +"sum(v_1."+columnName+"),";
        }
        sql =sql +" sum(v_1.本月退网户数),sum(v_1.本月新增户数) ";
        sql =sql +" from ( SELECT addr.districtid ,count(distinct(case when cust.status !='C' then cust.customerid end)) 总户数,";
		Iterator it_v1=dto2.getReportMap().keySet().iterator();
        while (it_v1.hasNext()){
       	    String columnName =(String)it_v1.next();
       	    String columnValue =(String)dto2.getReportMap().get(columnName);
       	    sql  =sql + pasesqlStr(columnName,columnValue);
        }
	    sql =sql
	        +" count(case when cp.status='C' and to_char(cp.dt_lastmod,'YYYY-MM-DD') >= to_char(add_months(sysdate,-1),'YYYY-MM-DD') "
	        +"       then cp.status end) 本月退网户数, "
	        +" count(case when (cp.status !='C')  and to_char(cp.createtime,'YYYY-MM-DD') >= to_char(add_months(sysdate,-1),'YYYY-MM-DD') "
	        +"      then cp.status end) 本月新增户数 "
            +" FROM t_customer cust,t_address addr,t_customerproduct cp, "
	        +" (select pr.custid,nvl(sum(pr.amount),0) amountSum from T_PaymentRecord pr where  pr.status = 'V' group by pr.custid ) pay_view, "
	        +" (select ai.custid,nvl(sum(ai.value),0)  valueSum  from T_AccountItem ai where ai.feetype<>'P' and ai.status in ('V','1','3') group by ai.custid) acctItem_view "
	        +" where cust.addressid = addr.addressid "
	        +" and cust.customerid = cp.customerid "
	        +" and acctItem_view.custid(+) =cust.customerid "
	        +" and pay_view.custid(+) =cust.customerid "
	        +" group by addr.districtid) v_1,"
	        +" (select t.id,t.name from t_districtsetting t where belongto ="+dto2.getDistrict()+") dist "
	        +" where dist.id =(select t.id  from t_districtsetting t "
	        +"                where belongto ="+dto2.getDistrict()+" connect by prior belongto =id start with id =v_1.districtid) "
	        +" group by dist.name ";
			    	
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(sql));
	}
		
	private String pasesqlStr(String colName,String colValue){
		String[] colstrs =colValue.split(";");
		String retStr ="";  
		boolean  or_flag =false;
		for (int i=0;i<colstrs.length;i++){
			if (!retStr.equals("")){
				retStr =retStr +" or (";
				or_flag =true;
			}
		
			String[] sqlvalues =colstrs[i].split("-");
			String   productidStr =sqlvalues[0].substring(1,sqlvalues[0].length()-1);
			String   productstatusStr =sqlvalues[1].substring(1,sqlvalues[1].length()-1);
			String   custypeStr =sqlvalues[2].substring(1,sqlvalues[2].length()-1);
			String   operStr =sqlvalues[3].substring(1,sqlvalues[3].length()-1);
			String   midStr ="";
			boolean  and_flag =false;
			
            //	客户产品
			if (productidStr !=null && !productidStr.trim().equals("")){
				midStr ="";
				String[] pdStrs =productidStr.split(",");
				for (int j=0;j<pdStrs.length;j++){
					if (midStr.equals("")){
						midStr =midStr +  "(cp.productid "+parseOperator(pdStrs[j],false);
					} else{
						midStr =midStr +  " or cp.productid "+parseOperator(pdStrs[j],false);
					}
				}
				and_flag =true;
				retStr =retStr +midStr+") " ;
			}
					    
			//  客户产品状态
			if (productstatusStr !=null && !productstatusStr.trim().equals("")){
				midStr ="";
				String[] pdstatusStr =  productstatusStr.split(",");
				for (int j=0;j<pdstatusStr.length;j++){
					if (midStr.equals("")){
						midStr =midStr +  "(cp.status "+parseOperator(pdstatusStr[j],true);
					} else{
						midStr =midStr +  " or cp.status "+parseOperator(pdstatusStr[j],true);
					}
				}
				if (and_flag) 
					retStr =retStr +" and " +midStr+") " ;
				else
					retStr =retStr +midStr+") " ;
				and_flag =true;
		     }
				
		     //客户类型
		     if (custypeStr !=null && !custypeStr.trim().equals("")){
		    	 midStr ="";
				 String[] custypeStrs =  custypeStr.split(",");
				 for (int j=0;j<custypeStrs.length;j++){
					if (midStr.equals("")){
						midStr =midStr +  "(cust.customertype "+parseOperator(custypeStrs[j],true);
					} else{
						midStr =midStr +  " or cust.customertype "+parseOperator(custypeStrs[j],true);
					}
				}
				if (and_flag) 
					retStr =retStr +" and " +midStr+") " ;
				else
					retStr =retStr +midStr+") " ;
				and_flag =true;
		     }
				
			 //帐户余额运算关系
		     if (operStr !=null && !operStr.trim().equals("")){
			    midStr = "(nvl(pay_view.amountSum,0) -nvl(acctItem_view.valueSum,0) "+operStr;
			    if (and_flag) 
					retStr =retStr +" and " +midStr+") " ;
				else
					retStr =retStr +midStr+") " ;
		     }
		     if (or_flag) retStr =retStr +")";
		}
		retStr =" count(case when " +retStr +" then cp.status end) "+colName+",";
		return retStr;
	}
	
    private String parseOperator(String operValue,boolean StrFlag){
    	String operatorStr ="";
    	if (operValue.trim().indexOf("!=")>=0){
    		if (StrFlag)
    		    operatorStr ="!='"+operValue.substring(2,operValue.trim().length())+"'";
    		else
    			operatorStr ="!="+operValue.substring(2,operValue.trim().length());
    	} else{
    		if (StrFlag)
    		    operatorStr ="='"+operValue +"'";
    		else
    			operatorStr ="="+operValue ;
    	}
    	
    	return operatorStr;
    }

}
