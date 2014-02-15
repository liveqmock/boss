/*
 * Created on 2005-12-6
 *
 * @author whq
 * 
 * 一次性费用计算接口
 * 该类用来实际计算一次性费用, 针对受理单
 */
package com.dtv.oss.service.util.ImmediateFee;

import java.sql.*;
import java.util.*;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.dto.*;

public class ImmediateFeeCalculator implements java.io.Serializable {
    //  only for debug purpose, need set to false
    static boolean DEBUGMODE = false;

    private static Class clazz = ImmediateFeeCalculator.class;
    private ImmediateFeeCalculator4Event eventCalculator;
    private CsiToEventClass csi2EventMap;
    
    public ImmediateFeeCalculator(CsiToEventClass csi2EventMap, ImmediateFeeCalculator4Event eventCalculator) {
        this.eventCalculator = eventCalculator;
        this.csi2EventMap = csi2EventMap;
    }
    
    public static ImmediateFeeCalculator getDefaultImmediateFeeCalculator() {
        return new ImmediateFeeCalculator(new CsiToEventClassByConfiguration(), new ImmediateFeeCalculator4EventImpl());
    }
    
    /**
     * 计算客户报修时产生的一次性费用
     * @param customerDto,必填信息：客户类型
     * @param cpDto,必填信息：ProblemLevel, ProblemCategory, FeeClass 
     * @return Collection of AccountItemDTO, 如果没有费用则返回一个空集合
     */
    public Collection calculateImmediateFee4CP(CustomerDTO customerDto, CustomerProblemDTO cpDto) {
        if (ImmediateFeeCalculator.DEBUGMODE) {
            System.out.println(customerDto);
            System.out.println(cpDto);
        } else {
            LogUtility.log(ImmediateFeeCalculator.class,LogLevel.DEBUG,"calculateImmediateFee4CP customerDto:"+customerDto);
            LogUtility.log(ImmediateFeeCalculator.class,LogLevel.DEBUG,"calculateImmediateFee4CP cpDto:"+cpDto);
        }
        
        if ((customerDto == null) || (cpDto == null))
            throw new IllegalArgumentException("customerDto和cpDto都不能为null。");
        String problemLevel = cpDto.getProblemLevel();
        if (isNull(problemLevel))
            throw new IllegalArgumentException("cpDto中ProblemLevel不能为null或空。");
        String problemCategory = cpDto.getProblemCategory();
        //now the eventReason stores in FeeClass filed of CustomerProblemDTO
        String eventReason = cpDto.getFeeClass();
        
        Collection accountItemList = new ArrayList();
        Collection colEventClass = csi2EventMap.getEventClass4CP(problemLevel, problemCategory);
        Iterator it = colEventClass.iterator();
        AccountItemDTO aiDto = null;
        while (it.hasNext()) {
            int eventClass = Integer.parseInt((String)it.next());
            try {
                aiDto = eventCalculator.getImmediateFee4CP(eventClass, eventReason, customerDto);
            } catch (SQLException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new RuntimeException("DB error");
            }
            if (aiDto != null) accountItemList.add(aiDto);
        }
        return accountItemList;
    }
    
    /**
     * To check if strField is null or empty
     * @param strField
     * @return
     */
    private boolean isNull(String strField) {
        return (strField == null) || strField.trim().equals("");
    }
    
    private void convert(ImmediateFeeList immediateFeeList) {
        Collection returnFeeList = immediateFeeList.getReturnFeeList();
        if (returnFeeList != null || !returnFeeList.isEmpty()) {
            Iterator it4fee = returnFeeList.iterator();
            while (it4fee.hasNext()) {
				
                ReturnFee rf = (ReturnFee)it4fee.next();
                AccountItemDTO acDto = new AccountItemDTO();
				if(rf.getValue()==0.0f) continue;
                acDto.setValue(rf.getValue());
				acDto.setFeeType(rf.getFeeType());
                acDto.setAcctItemTypeID(rf.getAcctItemTypeID());
                acDto.setProductID(rf.getProductID());
                acDto.setStatus(com.dtv.oss.service.util.CommonConstDefinition.AISTATUS_VALIDATE);
                acDto.setFeeSplitPlanID(rf.getFspID());
                acDto.setRfBillingCycleFlag(rf.getRfBillingCycleFlag());
                acDto.setAcctID(rf.getAccountID());
                acDto.setCustID(rf.getCustomerID());
                acDto.setServiceAccountID(rf.getServiceAccountID());
                acDto.setCcID(rf.getCCID());
                if (rf.getForcedDepositFlag() == null)
                    acDto.setForcedDepositFlag("N");
                else
                    acDto.setForcedDepositFlag(rf.getForcedDepositFlag());
                
                if (acDto.getForcedDepositFlag().equals("Y"))
                    immediateFeeList.setTotalValuePreDeposit(immediateFeeList.getTotalValuePreDeposit() + rf.getValue());

				
                immediateFeeList.getAcctItemList().add(acDto);
            }
        }
    }
    /**
     * 计算业务流程中涉及到的一次性费用
     * @param csiDto			CustServiceInteractionDTO, 
     * 				受理单的信息（受理单类型Type，受理单安装类型InstallationType，事件原因StatusReason）
     * @param billingObjectCol  计费对象列表
     * @return					ImmediateFeeList对象
     * 
     * @throws RuntimeException such as DB error
     * @throws IllegalArgumentException 参数不正确
     */
    public ImmediateFeeList calculateImmediateFee(CustServiceInteractionDTO csiDto,  
            					  Collection billingObjectCol) throws ServiceException
    {
    	if (ImmediateFeeCalculator.DEBUGMODE) {
            System.out.println(csiDto);
        } else {
            LogUtility.log(ImmediateFeeCalculator.class,LogLevel.DEBUG,"calculateImmediateFee csiDto:"+csiDto);
            
        }
        if (csiDto == null)
            throw new IllegalArgumentException("csiDto不能为null。");
        String csiType = csiDto.getType();
        if (isNull(csiType))
            throw new IllegalArgumentException("csiDto中Type不能为null或空。");
        if(billingObjectCol == null) 
            throw new IllegalArgumentException("billingObjectCol不能为null。");
        
		String installOnOrgPort = null;
		Iterator itBO = billingObjectCol.iterator();
		if(itBO.hasNext())
		{
			installOnOrgPort = ((BillingObject)itBO.next()).getInstallOnOrgPort();
		}
			
        
        /*　团购对应的套餐，暂时不考虑
        if (csiType.equals("OS") || csiType.equals("OB")) {
	   setCampaignIDByGroupBargainID(productInfoList);
	}*/
	
	//受理单安装类型，上门安装/自安装等 ， 在需要安装的场合才判断,例如开户/新增业务帐户
	String installType = csiDto.getInstallationType();
	//事件原因
	String eventReason =null;
	if(csiDto.getStatusReason()!=null&&!"".equalsIgnoreCase(csiDto.getStatusReason()))
		eventReason=csiDto.getStatusReason();
	else
		eventReason=csiDto.getCreateReason();

    	ImmediateFeeList immediateFeeList = new ImmediateFeeList();
    	Collection colEventClass = csi2EventMap.getEventClass(csiType, installType,installOnOrgPort);
        Iterator it = colEventClass.iterator();
        ImmediateFeeList immediateFeeForEachEvent = null;
        ImmediateFeeList cf = null;
        try
        {
            //计算套餐费率
            cf = eventCalculator.getCampaignFee(billingObjectCol);
        }
        catch(SQLException ex)
        {
            LogUtility.log(clazz, LogLevel.ERROR, ex);
            throw new RuntimeException("DB error");
        }


        while (it.hasNext()) {
            int eventClass = Integer.parseInt((String)it.next());
            try {                
                
                immediateFeeForEachEvent = eventCalculator.getImmediateFee(eventClass, eventReason, billingObjectCol);
                if( immediateFeeForEachEvent != null)
                {
                    immediateFeeList.getReturnFeeList().addAll(immediateFeeForEachEvent.getReturnFeeList());
                    System.out.println(immediateFeeList.getReturnFeeList());

                }
                
            } catch (SQLException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new RuntimeException("DB error");
            }
        }
        if( cf!= null )
            immediateFeeList.getReturnFeeList().addAll(cf.getReturnFeeList());
        System.out.println(immediateFeeList.getReturnFeeList());
        convert(immediateFeeList);
System.out.println("--------------------------------------------");
System.out.println(immediateFeeList.getAcctItemList());
//        immediateFeeList.setTotalValueAlreadyPay(immediateFeeList.getTotalValueAlreadyPay() + immediateFeeForEachEvent.getTotalValueAlreadyPay());
  //      immediateFeeList.setTotalValueMustPay(immediateFeeList.getTotalValueMustPay() + immediateFeeForEachEvent.getTotalValueMustPay());
//        immediateFeeList.setTotalValuePreDeposit(immediateFeeList.getTotalValuePreDeposit() + immediateFeeForEachEvent.getTotalValuePreDeposit());
//        immediateFeeList.getReturnFeeList().addAll(immediateFeeForEachEvent.getReturnFeeList());
//        immediateFeeList.getAcctItemList().addAll(immediateFeeForEachEvent.getAcctItemList());
        //immediateFeeList.getPaymentList().addAll(immediateFeeForEachEvent.getPaymentList());

		try {
				eventCalculator.calcuGroupBargain(csiDto,billingObjectCol,immediateFeeList);
            } catch (SQLException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new RuntimeException("DB error");
            }
System.out.println(immediateFeeList.getPaymentList());		
//        if (csiType.equals("OS") || csiType.equals("OB"))
  //          generatePaymentRecord(immediateFeeList);
    	return immediateFeeList;
    }

    /**
     * 计算业务流程中涉及到的一次性费用
     * @param csiDto			CustServiceInteractionDTO, 
     * 							受理单的信息（受理单类型Type，受理单安装类型InstallationType，事件原因StatusReason）
     * @param customerDto		CustomerDTO，包括客户类型等信息
     * @param accountList		AccountDTO的集合，其中每个DTO代表一个账户信息，包括账户类型等
     * @param serviceAccountList 业务账户信息，ServiceAccountDTO的集合
     * @param productInfoList	客户产品相关信息, 见ProductInfo类说明
     * 
     * @return					ImmediateFeeList对象
     * 
     * @throws RuntimeException such as DB error
     * @throws IllegalArgumentException 参数不正确
     */
    /*public ImmediateFeeList calculateImmediateFee(CustServiceInteractionDTO csiDto,  
            									  CustomerDTO customerDto, 
            									  Collection accountList,
            									  Collection serviceAccountList,
    											  Collection productInfoList) {
        if (ImmediateFeeCalculator.DEBUGMODE) {
            System.out.println(csiDto);
            System.out.println(customerDto);
            System.out.println(accountList);
            System.out.println(productInfoList);
        } else {
            LogUtility.log(ImmediateFeeCalculator.class,LogLevel.DEBUG,"calculateImmediateFee csiDto:"+csiDto);
            LogUtility.log(ImmediateFeeCalculator.class,LogLevel.DEBUG,"calculateImmediateFee customerDto:"+customerDto);
            LogUtility.log(ImmediateFeeCalculator.class,LogLevel.DEBUG,"calculateImmediateFee accountList:"+((ArrayList)accountList).get(0));
            LogUtility.log(ImmediateFeeCalculator.class,LogLevel.DEBUG,"calculateImmediateFee productInfoList:"+productInfoList);    
        }
        
        if (csiDto == null)
            throw new IllegalArgumentException("csiDto不能为null。");
        String csiType = csiDto.getType();
        if (isNull(csiType))
            throw new IllegalArgumentException("csiDto中Type不能为null或空。");
        //受理单安装类型，上门安装/自安装等 ， 在需要安装的场合才判断,例如开户/新增业务帐户
		String installType = csiDto.getInstallationType();
		//事件原因
		String eventReason = csiDto.getStatusReason();
		
		if (csiType.equals("OS") || csiType.equals("OB")) {
		    setCampaignIDByGroupBargainID(productInfoList);
		}
		    
        ImmediateFeeList immediateFeeList = new ImmediateFeeList();
        Collection colEventClass = csi2EventMap.getEventClass(csiType, installType);
        Iterator it = colEventClass.iterator();
        ImmediateFeeList immediateFeeForEachEvent = null;
        while (it.hasNext()) {
            int eventClass = Integer.parseInt((String)it.next());
            try {
                immediateFeeForEachEvent = eventCalculator.getImmediateFee(eventClass, eventReason, customerDto, accountList, serviceAccountList, productInfoList);
            } catch (SQLException e) {
                LogUtility.log(clazz, LogLevel.ERROR, e);
                throw new RuntimeException("DB error");
            }
            immediateFeeList.setTotalValueAlreadyPay(immediateFeeList.getTotalValueAlreadyPay() + immediateFeeForEachEvent.getTotalValueAlreadyPay());
            immediateFeeList.setTotalValueMustPay(immediateFeeList.getTotalValueMustPay() + immediateFeeForEachEvent.getTotalValueMustPay());
            immediateFeeList.setTotalValuePreDeposit(immediateFeeList.getTotalValuePreDeposit() + immediateFeeForEachEvent.getTotalValuePreDeposit());
            immediateFeeList.getReturnFeeList().addAll(immediateFeeForEachEvent.getReturnFeeList());
            immediateFeeList.getAcctItemList().addAll(immediateFeeForEachEvent.getAcctItemList());
            immediateFeeList.getPaymentList().addAll(immediateFeeForEachEvent.getPaymentList());
        }
        if (csiType.equals("OS") || csiType.equals("OB"))
            generatePaymentRecord(immediateFeeList);
        return immediateFeeList;
    }*/
    
    /**
     * 在开户时通过传入的团购券ID去设置对应的促销ID
     * @param productInfoList
     */
    private void setCampaignIDByGroupBargainID(Collection productInfoList) {
        if ((productInfoList == null) || productInfoList.isEmpty())
            throw new IllegalArgumentException("productInfoList不能为null。");
        int groupBargainID = 0;
        int campaignID = 0;
        Iterator productInfoIterator = productInfoList.iterator();
        if (productInfoIterator.hasNext()) {
            ProductInfo pi = (ProductInfo)productInfoIterator.next();
            groupBargainID = pi.getGroupBargainID();
        }
        if (groupBargainID == 0) return;
        Connection dbConn = null;
        Statement stmt =null;
        ResultSet rs =null;
        if (ImmediateFeeCalculator.DEBUGMODE)
            dbConn = ImmediateFeeCalculator.getDirectJdbcConnection();
        else
            dbConn = DBUtil.getConnection();
        if (dbConn == null) throw new RuntimeException("DB Connection error.");
        try {
            stmt = dbConn.createStatement();
            String strSql = "select campaignid from t_groupbargain where id=" + groupBargainID;
            rs = stmt.executeQuery(strSql);
            if (rs.next()) {
                campaignID = rs.getInt("campaignid");
            }
            
            if (campaignID == 0) return;
            
            java.util.Iterator it = productInfoList.iterator();
            while (it.hasNext()) {
                ProductInfo productInfo = (ProductInfo)it.next();
                productInfo.setCampaignID(campaignID);
            }
        } catch (SQLException e) {
            LogUtility.log(clazz, LogLevel.ERROR, e);
            throw new RuntimeException("DB error");
        } finally {
        	if (rs != null)
               try {
                  rs.close();
               } catch (SQLException e1) {}
            if (stmt != null)
               try {
            	   stmt.close();
               } catch (SQLException e1) {}     
            if (dbConn != null)
                try {
                    dbConn.close();
                } catch (SQLException e1) {}
        }
    }

    /*
     * 比较 开户时产生的费用列表中的 非强制预存费 部分的总金额 ，与的金额大小
     * 根据比较的不同结果，产生对应的PaymentRecord记录
     * 对开户(OB,OS)计费时调用该方法
     */
    private void generatePaymentRecord(ImmediateFeeList immediateFeeList) {
	    
        //团购活动已经预付的金额
	    double currentValue4DepositFee = immediateFeeList.getTotalValueAlreadyPay();
	    //开户时产生的费用列表中的 非强制预存费 部分的总金额
	    double mustPayFee = immediateFeeList.getTotalValueMustPay();
	    int mopid = 0;
	    String groupBargainID = "";
	    String status = "";
	    String prePaymentType = "";
	    
	    Collection colInput = immediateFeeList.getPaymentList();
	    if (Math.abs(currentValue4DepositFee) < 0.001f) {
	        immediateFeeList.getPaymentList().removeAll(colInput);
	        return;
	    }
	    
	    Iterator it = colInput.iterator();
	    while (it.hasNext()) {
	        PaymentRecordDTO input = (PaymentRecordDTO)it.next();
	        mopid = input.getMopID();
	        groupBargainID = input.getTicketNo();
	        status = input.getStatus();
	        prePaymentType = input.getPrepaymentType();
	    }
	    immediateFeeList.getPaymentList().removeAll(colInput);
	    
	    if (Double.compare(mustPayFee, currentValue4DepositFee) >= 0) {
            
            PaymentRecordDTO paymentDto = new PaymentRecordDTO();
    		paymentDto.setMopID(mopid);
    		paymentDto.setPayType("C");
    		paymentDto.setTicketType("TG");
    		paymentDto.setTicketNo(String.valueOf(groupBargainID));
    		paymentDto.setAmount(currentValue4DepositFee);
    		paymentDto.setStatus(status);
    		paymentDto.setPrepaymentType(prePaymentType);
    		immediateFeeList.getPaymentList().add(paymentDto);	            
    		
        } else {	
            if (Math.abs(mustPayFee) > 0.001f) {
	            PaymentRecordDTO paymentDto = new PaymentRecordDTO();
	    		paymentDto.setMopID(mopid);
	    		paymentDto.setPayType("C");
	    		paymentDto.setTicketType("TG");
	    		paymentDto.setTicketNo(String.valueOf(groupBargainID));
	    		paymentDto.setAmount(mustPayFee);
	    		paymentDto.setStatus(status);
	    		paymentDto.setPrepaymentType(prePaymentType);
	    		immediateFeeList.getPaymentList().add(paymentDto);	            
            }
            
            PaymentRecordDTO prePaymentDto = new PaymentRecordDTO();
            prePaymentDto.setMopID(mopid);
            prePaymentDto.setPayType("P");
            prePaymentDto.setTicketType("TG");
            prePaymentDto.setTicketNo(String.valueOf(groupBargainID));
            prePaymentDto.setAmount(currentValue4DepositFee-mustPayFee);
            prePaymentDto.setPrepaymentType(prePaymentType);
            prePaymentDto.setStatus(status);
            
    		immediateFeeList.getPaymentList().add(prePaymentDto);	            
    	} 
	    
    }
    
	static Connection getDirectJdbcConnection(){
		Connection con = null;
		
		try{
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		  String url = "jdbc:oracle:thin:@127.0.0.1:1521:shdv";
		  con = DriverManager.getConnection(url, "testscn", "testscn");
//		  String url = "jdbc:oracle:thin:@192.168.0.18:1521:CMS";
//		  con = DriverManager.getConnection(url, "boss_zyg", "boss_zyg");
		  con.setAutoCommit(false);
		} catch(Exception e){
		    e.printStackTrace();
		    System.out.println(e);
		}
		
		return con;
	}  
	
    /**
     * old interface definition, deprecated
     * 计算业务流程中涉及到的一次性费用
     * @param csiType 			受理单类型
     * @param installType		受理单安装类型，上门安装/自安装等 ， 在需要安装的场合才判断,例如开户/新增业务帐户
     * @param eventReason		事件原因, 即受理单中statusReason取值
     * @param custType			客户类型
     * @param acctType			账户类型
     * @param productInfoList	客户产品相关信息, 见ProductInfo类说明
     * @return					ImmediateFeeList对象
     * @throws RuntimeException such as DB error
     * @throws IllegalArgumentException 参数不正确
     */
//    private ImmediateFeeList calculateImmediateFee(String csiType, 
//            									  String installType, 
//            									  String eventReason, 
//            									  String custType, 
//            									  String acctType, 
//    											  Collection productInfoList) {
//        ImmediateFeeList immediateFeeList = new ImmediateFeeList();
//        Collection colEventClass = csi2EventMap.getEventClass(csiType, installType);
//        Iterator it = colEventClass.iterator();
//        ImmediateFeeList immediateFeeForEachEvent = null;
//        while (it.hasNext()) {
//            int eventClass = Integer.parseInt((String)it.next());
//            try {
//                immediateFeeForEachEvent = eventCalculator.GetImmediateFee(eventClass, eventReason, custType, acctType, productInfoList);
//            } catch (SQLException e) {
//                LogUtility.log(clazz, LogLevel.ERROR, e);
//                throw new RuntimeException("DB error");
//            }
//            immediateFeeList.setTotalValueAlreadyPay(immediateFeeList.getTotalValueAlreadyPay() + immediateFeeForEachEvent.getTotalValueAlreadyPay());
//            immediateFeeList.setTotalValueMustPay(immediateFeeList.getTotalValueMustPay() + immediateFeeForEachEvent.getTotalValueMustPay());
//            immediateFeeList.setTotalValuePreDeposit(immediateFeeList.getTotalValuePreDeposit() + immediateFeeForEachEvent.getTotalValuePreDeposit());
//            immediateFeeList.getReturnFeeList().addAll(immediateFeeForEachEvent.getReturnFeeList());
//        }
//        return immediateFeeList;
//    }
}