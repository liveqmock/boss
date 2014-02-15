package com.dtv.oss.service.listhandler.monistat;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class PayFromBlankReportHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz =PayFromBlankReportHandler.class;
	private List rowCountList =new ArrayList();
	public PayFromBlankReportHandler(){
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

		if (dto.getSpareStr1().equals("out")){
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
		begin.append(" Select xxx.* From ( ");
		begin.append(" Select cust.customerid,");
        begin.append(" cust.name,");
        begin.append(" (Select t8.value From t_commonsettingdata t8 Where t8.name ='SET_C_CUSTOMERTYPE' and t8.key =cust.customertype) custType,");
        begin.append(" (Select td.name From t_districtsetting td Where td.belongto = 1 Connect By td.id = Prior td.belongto Start With td.id = addr.districtid) address, "); 
        begin.append(" (Select t7.name From t_districtsetting t7 Where t7.id =(Select belongto From t_districtsetting Where id =subview.id)) || '◇' || (Select t3.name From t_districtsetting t3 Where t3.id =subview.id) || '◇' || addr.detailaddress detailaddress, ");  
        begin.append(" nvl(Case when cust.telephone is not null and cust.telephonemobile is not null then cust.telephone || '/' || cust.telephonemobile  else cust.telephone ||  cust.telephonemobile end,' ') tel,");
        begin.append(" CHANGE_MONEY_TO_DATE(cust.customerid) ServiceEndDate, ");
        begin.append(" (Select t1.value From t_commonsettingdata t1 Where t1.name ='SET_C_SERVICEACCOUNTSTATUS' and t1.key =servAcct.Status) ServiceaccountStatus, ");
        begin.append(" (Select tm.name From t_methodofpayment tm Where tm.mopid = acc.mopid) bankname ");
        //区域
        begin.append(" From (Select dir.id From t_districtsetting dir Connect By Prior dir.id =dir.belongto Start With dir.id ="+dto2.getSpareStr8()+") subview,  ");
        begin.append(" t_Serviceaccount servAcct,t_address addr,t_customer cust,t_account acc ");
        begin.append(" Where cust.addressid =addr.addressid ");
        begin.append(" And addr.districtid =subview.id ");
        begin.append(" And cust.customerid = acc.customerid ");
        begin.append(" And cust.customerid =servAcct.Customerid ");
        begin.append(" And acc.status != 'C' ");
        begin.append(" And cust.status !='C' ");
        //安装地址
        makeSQLByStringField("addr.detailaddress", dto.getSpareStr2(),begin, "like");
        //客户证号
		makeSQLByIntField("cust.CustomerID", dto.getCustomerID(), begin);
		//客户姓名
		makeSQLByStringField("cust.Name", dto.getSpareStr3(), begin,"like");
		//客户类型
		makeSQLByStringField("cust.CustomerType", dto.getSpareStr4(), begin);
		//业务账户
		if(dto.getSpareStr5()!=null&&!dto.getSpareStr5().equals("")){
		   makeSQLByStringField("servAcct.status", dto.getSpareStr5(), begin);
		}
        begin.append(" And acc.mopid  In (select t.mopid from t_methodofpayment t where t.paytype ='BP') ");
        begin.append(" And cust.customerid In ");
        begin.append(" (Select * From ");
        begin.append("   (Select tc.customerid ");
        begin.append("    From t_customer tc,t_account ta ");
        begin.append("    Where tc.customerid = ta.customerid ");
        begin.append("    And ta.mopid In (select t.mopid from t_methodofpayment t where t.paytype ='BP') ");
        //银行预缴创建时间
        begin.append("    And tc.createtime >= to_date('"+dto2.getSpareStr6()+"','yyyy-mm-dd') ");
        begin.append("    And tc.createtime <= to_date('"+dto2.getSpareStr7()+"','yyyy-mm-dd') ");
        begin.append("    Minus ");
        begin.append("    Select tcsi.customerid From t_newcustaccountinfo tna,t_oldcustaccountinfo toa,t_custserviceinteraction tcsi Where tcsi.id = tna.csiid And tna.csiid = toa.csiid And tna.mopid In (select t.mopid from t_methodofpayment t where t.paytype ='BP') And tna.mopid<> toa.mopid ");
        begin.append("   ) ");
        begin.append("   Union All ");
        begin.append("   Select tcsi.customerid From t_newcustaccountinfo tna,t_oldcustaccountinfo toa,t_custserviceinteraction tcsi Where tcsi.id = tna.csiid And tna.csiid = toa.csiid And tna.mopid In (select t.mopid from t_methodofpayment t where t.paytype ='BP') And tna.mopid<> toa.mopid ");
        //银行预缴创建时间
        begin.append("   And tcsi.createtime >= to_date('"+dto2.getSpareStr6()+"','yyyy-mm-dd') ");
        begin.append("   And tcsi.createtime <= to_date('"+dto2.getSpareStr7()+"','yyyy-mm-dd') ");
        begin.append(" ) ");
        begin.append(") xxx Order By xxx.customerid ");

        // 设置当前数据查询sql
		setRecordDataQueryBuffer(begin);
	}

}
